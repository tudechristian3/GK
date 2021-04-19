package com.goodkredit.myapplication.activities.fairchild;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.promo.ScanPromoActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.fairchild.FairChildMembersDB;
import com.goodkredit.myapplication.model.fairchild.FairChildMembers;
import com.goodkredit.myapplication.responses.fairchild.FairChildMembersResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FairChildAttendanceActivity extends BaseActivity implements View.OnClickListener {
    //LOCAL DB
    private DatabaseHandler mdb;

    //COMMON
    private String imei;
    private String userid;
    private String borrowerid;
    private String sessionid;
    private String authcode;
    private String devicetype;
    private String limit;

    public static FairChildAttendanceActivity finishfairChildAttendanceActivity;

    private ImageView imv_logo;
    private TextView txv_logo;
    private TextView btn_action;

    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, FairChildAttendanceActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fairchild_attendance);

            setupToolbar();

            //All Initializations
            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //-----------------METHODS----------------------------
    private void init() {
        imv_logo = (ImageView) findViewById(R.id.imv_logo);
        txv_logo = (TextView) findViewById(R.id.txv_logo);
        btn_action = (TextView) findViewById(R.id.btn_action);
        btn_action.setOnClickListener(this);
    }

    private void initData() {
        //COMMON
        mdb = new DatabaseHandler(getViewContext());

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        finishfairChildAttendanceActivity = this;

        //SETUP TOOLBAR TITLE
        setUpToolBarTitle();

        //DISPLAY THE LOGO
        showLogo();

        getSession();
    }

    private void setUpToolBarTitle() {
        String title = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceName");

        if(!title.trim().equals("")) {
            setTitle(CommonFunctions.replaceEscapeData(title));
        } else {
            setTitle("FAIR CHILD");
        }
    }

    private void showLogo() {
        Glide.with(getViewContext())
                .load(R.drawable.fairchildlogo)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .override(512, 512)
                        .fitCenter())
                .into(imv_logo);
    }

    //---------------------API----------------------
//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            showProgressDialog(getViewContext(), "Processing request", "Please wait...");
//            createSession(getSessionCallback);
//        } else {
//            showWarningToast();
//        }
//    }
//
//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            ResponseBody errBody = response.errorBody();
//            try {
//                if (errBody == null) {
//                    sessionid = response.body().toString();
//                    if (!sessionid.isEmpty()) {
//                        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                        validateFairChildMembers();
//                    } else {
//                        hideProgressDialog();
//                        showWarningToast();
//                    }
//                } else {
//                    hideProgressDialog();
//                    showWarningToast();
//                }
//            } catch (Exception e) {
//                hideProgressDialog();
//                showWarningToast();
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showWarningToast();
//            hideProgressDialog();
//        }
//    };

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Processing request", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            validateFairChildMembers();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void validateFairChildMembers() {
        Call<FairChildMembersResponse> validateFairChildMemmber = RetrofitBuild.getFairChildAPIService(getViewContext())
                .validateFairChildMembers(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode
                );

        validateFairChildMemmber.enqueue(validateFairChildMembersCallBack);

    }

    private final Callback<FairChildMembersResponse> validateFairChildMembersCallBack =
            new Callback<FairChildMembersResponse>() {

                @Override
                public void onResponse(Call<FairChildMembersResponse> call, Response<FairChildMembersResponse> response) {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        hideProgressDialog();

                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                mdb.truncateTable(mdb, FairChildMembersDB.TABLE_FAIRCHILDMEMBERS);
                                List<FairChildMembers> fairChildMembersList = response.body().getFairChildMembersList();
                                for(FairChildMembers fairChildMembers : fairChildMembersList) {
                                    mdb.insertFairChildMembers(mdb, fairChildMembers);
                                }
                                finish();
                                Intent intent = new Intent(getViewContext(), FairChildVoteInstructionsActivity.class);
                                startActivity(intent);
                            } else if (response.body().getStatus().equals("201")) {
                                btn_action.setVisibility(View.VISIBLE);
                            } else if (response.body().getStatus().equals("202")) {
                                finish();
                                Bundle args = new Bundle();
                                args.putString("FROM","WHITELIST");
                                FairChildDeadEndActivity.start(getViewContext(),  args);
                            } else if (response.body().getStatus().equals("203")) {
                                finish();
                                Bundle args = new Bundle();
                                args.putString("FROM","ALREADYVOTED");
                                FairChildDeadEndActivity.start(getViewContext(),  args);
                            } else if (response.body().getStatus().equals("204")) {
                                finish();
                                Bundle args = new Bundle();
                                args.putString("FROM","NOTYETSTARTED");
                                FairChildDeadEndActivity.start(getViewContext(),  args);
                            } else if (response.body().getStatus().equals("205")) {
                                finish();
                                Bundle args = new Bundle();
                                args.putString("FROM","ALREADYENDED");
                                FairChildDeadEndActivity.start(getViewContext(),  args);
                            } else if (response.body().getStatus().equals("206")) {
                                finish();
                                Bundle args = new Bundle();
                                args.putString("FROM","NOTAMEMBER");
                                FairChildDeadEndActivity.start(getViewContext(),  args);
                            } else {
                                showErrorGlobalDialogs(response.body().getMessage());
                            }
                        } else {
                            showNoInternetToast();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideProgressDialog();
                        showNoInternetToast();
                    }
                }

                @Override
                public void onFailure(Call<FairChildMembersResponse> call, Throwable t) {
                    t.printStackTrace();
                    hideProgressDialog();
                    showNoInternetToast();
                }
     };


    //-------------------OTHERS------------------------------
    @Override
    public void onResume() {
        super.onResume();
        Boolean isFailed = PreferenceUtils.getBooleanPreference(getViewContext(), PreferenceUtils.KEY_PROMO_FAIR_STATUS);
        if (isFailed) {
            PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_PROMO_FAIR_STATUS);
            getSession();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_FAIRCHILD_FROM);
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action:
                PreferenceUtils.saveBooleanPreference(getViewContext(), PreferenceUtils.KEY_FAIRCHILD_FROM, true);
                ScanPromoActivity.start(getViewContext());
                break;
        }
    }
}
