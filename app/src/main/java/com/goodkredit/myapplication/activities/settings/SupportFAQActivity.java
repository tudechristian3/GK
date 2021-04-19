package com.goodkredit.myapplication.activities.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.settings.SupportFAQAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.support.GetFAQResponse;
import com.goodkredit.myapplication.responses.support.GetFAQResponseData;
import com.goodkredit.myapplication.responses.support.GetHelpTopicsResponseData;
import com.goodkredit.myapplication.responses.support.GetSupportMessagesResponseData;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ban_Lenovo on 3/7/2017.
 */

public class SupportFAQActivity extends BaseActivity implements View.OnClickListener, ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener {

    private ExpandableListView listview;
    private SupportFAQAdapter adapter;

    private GetHelpTopicsResponseData mHelpTopicObject;

    private AQuery aq;
    private Gson gson;
    private DatabaseHandler db;
    private ProgressDialog mProgressDialog;

    private ArrayList<String> expandableListTitle;
    private HashMap<String, ArrayList<GetFAQResponseData>> expandableListDetail;

    private TextView faqTopic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        setupToolbar();

        mHelpTopicObject = getIntent().getParcelableExtra("HELP_CATEGORY_OBJECT");
        aq = new AQuery(this);
        db = new DatabaseHandler(this);
        gson = new Gson();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setInverseBackgroundForced(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Fetching FAQ\nPlease wait...");

        faqTopic = (TextView) findViewById(R.id.faqTopic);
        faqTopic.setText(mHelpTopicObject.getHelpTopic());
        findViewById(R.id.faqMessageUs).setOnClickListener(this);

        listview = (ExpandableListView) findViewById(R.id.faq_list);
        listview.setOnGroupExpandListener(this);
        listview.setChildIndicator(null);
        listview.setGroupIndicator(null);

        expandableListTitle = new ArrayList<>();
        expandableListDetail = new HashMap<>();
        expandableListTitle.clear();
        expandableListDetail.clear();

        for (int loop = 0; loop < db.getFAQResponseData(db, mHelpTopicObject.getHelpTopic()).size(); loop++) {
            expandableListTitle.add(db.getFAQResponseData(db, mHelpTopicObject.getHelpTopic()).get(loop).getQuestion());
            expandableListDetail.put(db.getFAQResponseData(db, mHelpTopicObject.getHelpTopic()).get(loop).getQuestion(),
                    db.getFAQResponseData(db, mHelpTopicObject.getHelpTopic()));
        }

        adapter = new SupportFAQAdapter(getViewContext(), expandableListTitle, expandableListDetail);
        listview.setAdapter(adapter);

        if (CommonVariables.FAQ_ISFIRSTLOAD) {
            fetchFAQ();
        }
    }

    public static void start(Context context, GetHelpTopicsResponseData obj) {
        Intent intent = new Intent(context, SupportFAQActivity.class);
        intent.putExtra("HELP_CATEGORY_OBJECT", obj);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchFAQ() {
        Map<String, Object> params = new HashMap<>();
        params.put("imei", PreferenceUtils.getImeiId(getViewContext()));
        params.put("borrowerid", PreferenceUtils.getBorrowerId(getViewContext()));
        params.put("userid", PreferenceUtils.getUserId(getViewContext()));
        params.put("helptopic", mHelpTopicObject.getHelpTopic());

        aq.progress(mProgressDialog).ajax(CommonVariables.GET_FAQ, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                if (object != null) {
                    String json = String.valueOf(object);
                    log(CommonVariables.GET_FAQ + " " + json);
                    GetFAQResponse response = gson.fromJson(json, GetFAQResponse.class);
                    if (response.getStatus().equals("000")) {
                        //insert to db
                        if (!response.getData().isEmpty()) {
                            db.deleteFAQ(db);
                            for (GetFAQResponseData data : response.getData()) {
                                db.insertFAQ(db, data);
                            }
                            CommonVariables.FAQ_ISFIRSTLOAD = false;
                        } else {
                            showToast("No data yet.", GlobalToastEnum.NOTICE);
                        }
                    } else {
                        showToast(response.getMessage(), GlobalToastEnum.NOTICE);
                    }
                } else {
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) != 0) {
                        showToast("Connection maybe slow. Please try again", GlobalToastEnum.NOTICE);
                    } else {
                        showToast("No internet connection. Please check", GlobalToastEnum.NOTICE);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.faqMessageUs:
                finish();
                SupportSendMessageActivity.start(getViewContext(), "FAQ", mHelpTopicObject.getHelpTopic(), ".", new GetSupportMessagesResponseData(".", ".",
                        ".", ".", mHelpTopicObject.getHelpTopic(), ".", ".", db.getStringAccountInformationInColumn(db, "mobile"),
                        PreferenceUtils.getBorrowerId(getViewContext()), db.getStringAccountInformationInColumn(db, "firstname"),
                        PreferenceUtils.getImeiId(getViewContext()), db.getStringAccountInformationInColumn(db, "email"), ".", "OPEN", mHelpTopicObject.getPriority(), mHelpTopicObject.getLogo()));
                break;
        }
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        try {
            listview.getChildAt(groupPosition).setBackgroundResource(R.color.bluegray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        try {
            listview.getChildAt(groupPosition).setBackgroundResource(R.color.whiteTrans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}