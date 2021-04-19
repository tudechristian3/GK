package com.goodkredit.myapplication.fragments.schoolmini;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniActivity;
import com.goodkredit.myapplication.adapter.schoolmini.support.SchoolMiniSupportFAQAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.SupportFAQ;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.support.GetSupportFAQResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolMiniFAQFragment extends BaseFragment implements View.OnClickListener, ExpandableListView.OnGroupExpandListener {

    private DatabaseHandler mdb;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String helptopic;

    //SCHOOL
    private String schoolid = "";

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private TextView txvHelpTopic;

    private ArrayList<String> expandableListTitle;
    private HashMap<String, String> expandableListDetail;

    private SchoolMiniSupportFAQAdapter mAdapter;
    private ExpandableListView listview;

    private Button btnMessageUs;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_support_new_case, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        //empty layout
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        txvHelpTopic = (TextView) view.findViewById(R.id.txvHelpTopic);

        listview = (ExpandableListView) view.findViewById(R.id.faq_list);
        listview.setOnGroupExpandListener(this);
        listview.setChildIndicator(null);
        listview.setGroupIndicator(null);

        btnMessageUs = (Button) view.findViewById(R.id.btnMessageUs);
        btnMessageUs.setOnClickListener(this);

        //NO RESULT
        noresult = (LinearLayout) view.findViewById(R.id.noresult);
        txv_noresult = (TextView) view.findViewById(R.id.txv_noresult);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        schoolid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCSERVICECODE);

        helptopic = getArguments().getString("HELPTOPIC");

        expandableListTitle = new ArrayList<>();
        expandableListDetail = new HashMap<>();

        txvHelpTopic.setText(helptopic);

        getSession();
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//
//            mTvFetching.setText("Fetching FAQ's.");
//            mTvWait.setText(" Please wait...");
//            mLlLoader.setVisibility(View.VISIBLE);
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
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
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//                    getUniversalSupportFAQ(getUniversalSupportFAQSession);
//                }
//            } else {
//                hideProgressDialog();
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mTvFetching.setText("Fetching FAQ's.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getUniversalSupportFAQ(getUniversalSupportFAQSession);
            getUniversalSupportFAQV2();

        } else {
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getUniversalSupportFAQ(Callback<GetSupportFAQResponse> getUniversalSupportFAQCallBack) {
        Call<GetSupportFAQResponse> getuniversalsupportfaq = RetrofitBuild.getSupportAPIService(getViewContext())
                .getUniversalSupportFAQ(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        helptopic,
                        schoolid,
                        schoolid
                        );
        getuniversalsupportfaq.enqueue(getUniversalSupportFAQCallBack);
    }

    private final Callback<GetSupportFAQResponse> getUniversalSupportFAQSession = new Callback<GetSupportFAQResponse>() {

        @Override
        public void onResponse(Call<GetSupportFAQResponse> call, Response<GetSupportFAQResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    List<SupportFAQ> supportFAQList = new ArrayList<>();
                    supportFAQList = response.body().getSupportFAQList();

                    expandableListTitle.clear();
                    expandableListDetail.clear();

                    if(supportFAQList.size() > 0) {
                        listview.setVisibility(View.VISIBLE);
                        noresult.setVisibility(View.GONE);

                        for (SupportFAQ supportFAQ : supportFAQList) {
                            expandableListTitle.add(supportFAQ.getQuestion());
                            expandableListDetail.put(supportFAQ.getQuestion(), supportFAQ.getAnswer());
                        }

                        mAdapter = new SchoolMiniSupportFAQAdapter(getViewContext(), expandableListTitle, expandableListDetail);
                        listview.setAdapter(mAdapter);
                    }
                    else {
                        listview.setVisibility(View.GONE);
                        noresult.setVisibility(View.VISIBLE);
                        txv_noresult.setText("NO FAQ POSTED YET.");
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSupportFAQResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                SchoolMiniActivity.start(getViewContext(),7,null);
                ((SchoolMiniActivity) getViewContext()).displayView(7, null);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMessageUs: {
                Bundle args = new Bundle();
                args.putString("HELPTOPIC", helptopic);
                args.putString("STATUS", "OPEN");
                args.putString("THREADID", ".");
                args.putString("SUBJECT", "-");
                ((SchoolMiniActivity) getViewContext()).displayView(9, args);
                break;
            }
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


    /*
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * */


    private void getUniversalSupportFAQV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("helptopic", helptopic);
                parameters.put("schoolid", schoolid);
                parameters.put("servicecode", schoolid);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getUniversalSupportFAQV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getUniversalSupportFAQV2Object();

            } else {
                hideProgressDialog();
                showNoInternetToast();
            }

        } catch (Exception e) {
            e.printStackTrace();
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getUniversalSupportFAQV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .getUniversalSupportFAQV2(authenticationid, sessionid, param);

        call.enqueue(getUniversalSupportFAQV2CallBack);
    }

    private Callback<GenericResponse> getUniversalSupportFAQV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            mLlLoader.setVisibility(View.GONE);

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    List<SupportFAQ> supportFAQList = new ArrayList<>();
                    supportFAQList =  new Gson().fromJson(data, new TypeToken<List<SupportFAQ>>() {}.getType());

                    expandableListTitle.clear();
                    expandableListDetail.clear();

                    if(supportFAQList.size() > 0) {
                        listview.setVisibility(View.VISIBLE);
                        noresult.setVisibility(View.GONE);

                        for (SupportFAQ supportFAQ : supportFAQList) {
                            expandableListTitle.add(supportFAQ.getQuestion());
                            expandableListDetail.put(supportFAQ.getQuestion(), supportFAQ.getAnswer());
                        }

                        mAdapter = new SchoolMiniSupportFAQAdapter(getViewContext(), expandableListTitle, expandableListDetail);
                        listview.setAdapter(mAdapter);
                    }
                    else {
                        listview.setVisibility(View.GONE);
                        noresult.setVisibility(View.VISIBLE);
                        txv_noresult.setText("NO FAQ POSTED YET.");
                    }

                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        listview.setVisibility(View.GONE);
                        noresult.setVisibility(View.VISIBLE);
                        txv_noresult.setText("NO FAQ POSTED YET.");
                    }
                }
            } else {
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    };
}
