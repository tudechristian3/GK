package com.goodkredit.myapplication.activities.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.settings.SupportHelpTopicAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.support.GetHelpTopicsResponse;
import com.goodkredit.myapplication.responses.support.GetHelpTopicsResponseData;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ban_Lenovo on 3/6/2017.
 */

public class SupportHelpTopicActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private AQuery aq;
    private ProgressDialog mProgressDialog;
    private Gson gson;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SupportHelpTopicAdapter adapter;

    private DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_topic);

        init();
    }

    private void init() {
        gson = new Gson();
        db = new DatabaseHandler(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setInverseBackgroundForced(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        //mProgressDialog.setTitle("Fetching topics");
        mProgressDialog.setMessage("Fetching topics\nPlease wait...");

        aq = new AQuery(getViewContext());
        setupToolbar();

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.helpTopicRefreshLayout);
        refreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.helpTopicList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(this, null, true, true));
        adapter = new SupportHelpTopicAdapter(getViewContext(), db.getHelpTopicsResponseData(db));
        recyclerView.setAdapter(adapter);

        if (CommonVariables.HELP_TOPIC_ISFIRSTLOAD) {
            fetchHelpTopics();
        }
    }

    private void fetchHelpTopics() {

        Map<String, Object> params = new HashMap<>();
        params.put("imei", PreferenceUtils.getImeiId(getViewContext()));
        params.put("borrowerid", PreferenceUtils.getBorrowerId(getViewContext()));
        params.put("userid", PreferenceUtils.getUserId(getViewContext()));

        aq.progress(mProgressDialog).ajax(CommonVariables.GET_HELP_TOPIC, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                if (object != null) {
                    String json = String.valueOf(object);
                    log("HELPTOPIC === " + json);
                    GetHelpTopicsResponse response = gson.fromJson(json, GetHelpTopicsResponse.class);
                    if (response.getStatus().equals("000")) {
                        //insert to db
                        if (!response.getData().isEmpty()) {
                            db.deleteHelpTopic(db);
                            for (GetHelpTopicsResponseData data : response.getData()) {
                                db.insertHelpTopics(db, data);
                            }
                            CommonVariables.HELP_TOPIC_ISFIRSTLOAD = false;
                        } else {
                            showToast("No data yet.", GlobalToastEnum.NOTICE);
                        }
                    } else {
                        showToast(response.getMesssage(), GlobalToastEnum.NOTICE);
                    }
                } else {
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) != 0) {
                        showToast("Connection maybe slow. Please try again", GlobalToastEnum.NOTICE);
                    } else {
                        showToast("No internet connection. Please check", GlobalToastEnum.NOTICE);
                    }
                }
                adapter.clearData();
                adapter.update(db.getHelpTopicsResponseData(db));
                refreshLayout.setRefreshing(false);
            }
        });
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, SupportHelpTopicActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        fetchHelpTopics();
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
