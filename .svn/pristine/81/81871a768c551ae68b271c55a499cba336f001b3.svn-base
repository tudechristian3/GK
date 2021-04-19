package com.goodkredit.myapplication.fragments.skycable;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.SkycableAccounts;
import com.goodkredit.myapplication.bean.SkycableSOA;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.responses.skycable.CallLinkSkycableAccountResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SkycableLinkAccountFragment extends BaseFragment implements View.OnClickListener {

    private TextView txvLinkAccount;

    private Button btnSubmit;
    private EditText edtSkycableAccountNo;
    private EditText edtSkycableAccountName;
    private EditText edtMobileNo;

    private boolean isLoading = false;
    private DatabaseHandler mdb;
    private String mobileno;
    private String skycableaccountno;
    private String skycableaccountname;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //DIALOG
    private MaterialDialog mDialog;
    private TextView txvMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_link_account, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ((SkyCableActivity) getViewContext()).displayView(3, null);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(View view) {
        edtSkycableAccountNo = (EditText) view.findViewById(R.id.edtSkycableAccountNo);
        edtSkycableAccountName = (EditText) view.findViewById(R.id.edtSkycableAccountName);
        edtMobileNo = (EditText) view.findViewById(R.id.edtMobileNo);
        edtMobileNo.setBackgroundColor(ContextCompat.getColor(getViewContext(), R.color.color_CCCCCC));
        edtMobileNo.setEnabled(false);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        txvLinkAccount = (TextView) view.findViewById(R.id.txvLinkAccount);
        txvLinkAccount.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "LINK ACCOUNT"));

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //SETUP DIALOGS
        setUpStatusDialog();
    }

    private void setUpStatusDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_skycable_link_account_status, false)
                .build();
        View view = mDialog.getCustomView();

        TextView txvCloseDialog = (TextView) view.findViewById(R.id.txvCloseDialog);
        txvCloseDialog.setOnClickListener(this);
        txvMessage = (TextView) view.findViewById(R.id.txvMessage);

    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        V2Subscriber v2Subscriber = mdb.getSubscriber(mdb);
        edtMobileNo.setText(v2Subscriber.getMobileNumber());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit: {

                if (edtMobileNo.getText().toString().trim().length() > 0 &&
                        edtSkycableAccountNo.getText().toString().trim().length() > 0 &&
                        edtSkycableAccountName.getText().toString().trim().length() > 0) {

                    mobileno = getMobileNumber(edtMobileNo.getText().toString().trim());
                    if (mobileno.equals("INVALID")) {
                        showError("Invalid Mobile Number");
                    } else {
                        skycableaccountno = edtSkycableAccountNo.getText().toString().trim();
                        skycableaccountname = edtSkycableAccountName.getText().toString().trim();
                        getSession();
                    }

                } else {
                    showError("Please input all required fields.");
                }

                break;
            }
            case R.id.refreshnointernet: {

                break;
            }
            case R.id.txvCloseDialog: {
                mDialog.dismiss();
                ((SkyCableActivity) getViewContext()).displayView(1, null);
                break;
            }
        }
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
//            mTvFetching.setText("Linking Account..");
//            mTvWait.setText(" Please wait...");
//            mLlLoader.setVisibility(View.VISIBLE);
//
//            isLoading = true;
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
//        } else {
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showError(getString(R.string.generic_internet_error_message));
//        }
//    }
//
//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    callLinkSkycableAccount(callLinkSkycableAccountSession);
//
//                }
//            } else {
//                isLoading = false;
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            mTvFetching.setText("Linking Account..");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            isLoading = true;

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            callLinkSkycableAccount(callLinkSkycableAccountSession);
        } else {
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void callLinkSkycableAccount(Callback<CallLinkSkycableAccountResponse> callLinkSkycableAccountCallback) {
        Call<CallLinkSkycableAccountResponse> calllink = RetrofitBuild.callLinkSkycableAccountService(getViewContext())
                .callLinkSkycableAccountCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        skycableaccountno,
                        skycableaccountname,
                        mobileno);
        calllink.enqueue(callLinkSkycableAccountCallback);
    }

    private final Callback<CallLinkSkycableAccountResponse> callLinkSkycableAccountSession = new Callback<CallLinkSkycableAccountResponse>() {

        @Override
        public void onResponse(Call<CallLinkSkycableAccountResponse> call, Response<CallLinkSkycableAccountResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    if (mDialog != null) {
                        mDialog.show();
                        txvMessage.setText(response.body().getMessage());
                    }

                    List<SkycableAccounts> skycableAccountslist = response.body().getSkycableAccounts();
                    List<SkycableSOA> skycableSOAList = response.body().getSkycableSOAList();

                    if (mdb != null) {
                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PPV_ACCOUNTS);
                        for (SkycableAccounts skycableAccounts : skycableAccountslist) {
                            mdb.insertSkycableAccounts(mdb, skycableAccounts);
                        }

                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_SOA);
                        for (SkycableSOA skycableSOA : skycableSOAList) {
                            mdb.insertSkycableSOA(mdb, skycableSOA);
                        }
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<CallLinkSkycableAccountResponse> call, Throwable t) {
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

//    private void linkSkycableAccount(Callback<LinkSkycableAccountResponse> linkSkycableAccountCallback) {
//        Call<LinkSkycableAccountResponse> linkskycableaccount = RetrofitBuild.linkSkycableAccountService(getViewContext())
//                .linkSkycableAccountCall(sessionid,
//                        imei,
//                        userid,
//                        borrowerid,
//                        authcode,
//                        skycableaccountno,
//                        skycableaccountname,
//                        mobileno);
//        linkskycableaccount.enqueue(linkSkycableAccountCallback);
//    }
//
//    private final Callback<LinkSkycableAccountResponse> linkSkycableAccountSession = new Callback<LinkSkycableAccountResponse>() {
//
//        @Override
//        public void onResponse(Call<LinkSkycableAccountResponse> call, Response<LinkSkycableAccountResponse> response) {
//            mLlLoader.setVisibility(View.GONE);
//            ResponseBody errorBody = response.errorBody();
//
//            if (errorBody == null) {
//                if (response.body().getStatus().equals("000")) {
//
//                    if (mDialog != null) {
//                        mDialog.show();
//                        txvMessage.setText(response.body().getMessage());
//                    }
//
//                    List<SkycableAccounts> skycableAccountslist = response.body().getSkycableAccountsList();
//                    List<SkycableSOA> skycableSOAList = response.body().getSkycableSOAList();
//
//                    if (mdb != null) {
//                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PPV_ACCOUNTS);
//                        for (SkycableAccounts skycableAccounts : skycableAccountslist) {
//                            mdb.insertSkycableAccounts(mdb, skycableAccounts);
//                        }
//
//                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_SOA);
//                        for(SkycableSOA skycableSOA : skycableSOAList){
//                            mdb.insertSkycableSOA(mdb, skycableSOA);
//                        }
//                    }
//
//                } else {
//                    showError(response.body().getMessage());
//                }
//            } else {
//                showError();
//            }
//
//        }
//
//        @Override
//        public void onFailure(Call<LinkSkycableAccountResponse> call, Throwable t) {
//            isLoading = false;
//            mLlLoader.setVisibility(View.GONE);
//            Toast.makeText(getViewContext(), "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
//        }
//    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

}
