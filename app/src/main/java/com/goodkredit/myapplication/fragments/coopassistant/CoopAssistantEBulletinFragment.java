package com.goodkredit.myapplication.fragments.coopassistant;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.coopassistant.bulletin.CoopAssistantEBulletinHeaderAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.coopassistant.CoopMiniBulletinDB;
import com.goodkredit.myapplication.model.GenericBulletin;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantLoanTransactions;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.responses.GKBulletinResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantEBulletinFragment extends BaseFragment {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    private String servicecode = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    private NestedScrollView home_body;

    //BULLETIN
    private List<GenericBulletin> coopAssistantBulletinList;

    private LinearLayout header_container;
    private LinearLayout rv_bulletin_container;
    private RecyclerView rv_bulletin;
    private CoopAssistantEBulletinHeaderAdapter rv_bulletin_adapter;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    //MEMBER LIST
    private List<CoopAssistantMembers> coopMembersList = new ArrayList<>();

    //Security Update
    private String authenticationid;
    private String keyEncryption;
    private String param;
    private String index;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_ebulletin, container, false);
        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        home_body = view.findViewById(R.id.home_body);

        header_container = view.findViewById(R.id.header_container);
        rv_bulletin_container = view.findViewById(R.id.rv_bulletin_container);
        rv_bulletin = view.findViewById(R.id.rv_bulletin);

        //NO RESULT
        noresult = view.findViewById(R.id.noresult);
        txv_noresult = view.findViewById(R.id.txv_noresult);

    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

        coopMembersList = PreferenceUtils.getCoopMembersListPreference(mContext, CoopAssistantMembers.KEY_COOPMEMBERINFORMATION);

        mdb = new DatabaseHandler(getViewContext());

        rv_bulletin.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_bulletin.setNestedScrollingEnabled(false);

        rv_bulletin_adapter = new CoopAssistantEBulletinHeaderAdapter(getViewContext(), CoopAssistantEBulletinFragment.this, coopMembersList);
        rv_bulletin.setAdapter(rv_bulletin_adapter);

        callApi();
    }

    private void callApi() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Fetching Coop Information", "Please wait...");
//            getMiniAppBulletin(getMiniAppBulletinResponseCallBack);
            getMiniAppBulletinV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getMiniAppBulletin(Callback<GKBulletinResponse> getMiniAppBulletinResponseCallBack) {
        Call<GKBulletinResponse> getminiappbulletin = RetrofitBuild.getBulletinAPIService(getViewContext())
                .getMiniAppBulletin(sessionid,
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        "coop",
                        servicecode
                );

        getminiappbulletin.enqueue(getMiniAppBulletinResponseCallBack);
    }

    private final Callback<GKBulletinResponse> getMiniAppBulletinResponseCallBack = new
            Callback<GKBulletinResponse>() {

                @Override
                public void onResponse(Call<GKBulletinResponse> call, Response<GKBulletinResponse> response) {
                    try {
                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                mdb.truncateTable(mdb, CoopMiniBulletinDB.TABLE_COOP_BULLETIN);

                                coopAssistantBulletinList = response.body().getGKBulletin();

                                for (GenericBulletin genericBulletin : coopAssistantBulletinList) {
                                    mdb.insertCoopAssistantBulletin(mdb, genericBulletin);
                                }

                                if (mdb.getCoopAssistantBulletin(mdb).size() > 0) {
                                    header_container.setVisibility(View.GONE);
                                    rv_bulletin_container.setVisibility(View.VISIBLE);

                                    if(!coopMembersList.isEmpty()){
                                        rv_bulletin_adapter.updateData(mdb.getCoopAssistantMiniBulletinGroupByDate(mdb));
                                    } else{
                                        rv_bulletin_adapter.updateData(mdb.getCoopAssistantMiniBulletinGroupByDatePublic(mdb));
                                    }

                                    Logger.debug("vanhttp", "ispublic ====== " + new Gson().toJson(mdb.getCoopAssistantMiniBulletinGroupByDatePublic(mdb)));
                                }
                                else {
                                    header_container.setVisibility(View.GONE);
                                    rv_bulletin_container.setVisibility(View.GONE);
                                    noresult.setVisibility(View.VISIBLE);
                                    txv_noresult.setText("NO BULLETIN POSTED YET");
                                }

                            } else {
                                showErrorToast(response.body().getMessage());
                            }
                        } else {
                            showErrorToast();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GKBulletinResponse> call, Throwable t) {
                    showErrorToast();
                    hideProgressDialog();
                }
            };

    /*
    * SECURITY UPDATE
    *
    * */
    private void getMiniAppBulletinV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("schoolid","coop");
        parameters.put("servicecode",servicecode);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getMiniAppBulletinV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        getMiniAppBulletinV2Object(getMiniAppBulletinV2Callback);


    }
    private void getMiniAppBulletinV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .getMiniAppBulletinV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getMiniAppBulletinV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    mdb.truncateTable(mdb, CoopMiniBulletinDB.TABLE_COOP_BULLETIN);

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    coopAssistantBulletinList = new Gson().fromJson(data,new TypeToken<List<GenericBulletin>>(){}.getType());

                    for (GenericBulletin genericBulletin : coopAssistantBulletinList) {
                        mdb.insertCoopAssistantBulletin(mdb, genericBulletin);
                    }

                    if (mdb.getCoopAssistantBulletin(mdb).size() > 0) {
                        header_container.setVisibility(View.GONE);
                        rv_bulletin_container.setVisibility(View.VISIBLE);

                        if(!coopMembersList.isEmpty()){
                            rv_bulletin_adapter.updateData(mdb.getCoopAssistantMiniBulletinGroupByDate(mdb));
                        } else{
                            rv_bulletin_adapter.updateData(mdb.getCoopAssistantMiniBulletinGroupByDatePublic(mdb));
                        }
                        Logger.debug("vanhttp", "ispublic ====== " + new Gson().toJson(mdb.getCoopAssistantMiniBulletinGroupByDatePublic(mdb)));
                    }
                    else {
                        header_container.setVisibility(View.GONE);
                        rv_bulletin_container.setVisibility(View.GONE);
                        noresult.setVisibility(View.VISIBLE);
                        txv_noresult.setText("NO BULLETIN POSTED YET");
                    }


                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }

        }
        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorToast();
            t.printStackTrace();
        }
    };

}
