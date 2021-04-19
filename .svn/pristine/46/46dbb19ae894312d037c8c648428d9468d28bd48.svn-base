package com.goodkredit.myapplication.fragments.schoolmini;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.schoolmini.bulletin.BulletinHeaderAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniBulletinDB;
import com.goodkredit.myapplication.model.GenericBulletin;
import com.goodkredit.myapplication.responses.GKBulletinResponse;
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

public class SchoolMiniBulletinFragment extends BaseFragment {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //SCHOOL
    private String schoolid = "";
    private String studentid = "";
    private String course = "";
    private String yearlevel = "";
    private String firstname = "";
    private String middlename = "";
    private String lastname = "";
    private String mobilenumber = "";
    private String emailaddress = "";
    private String address = "";

    //BULLETIN
    private List<GenericBulletin> genericBulletinList = new ArrayList<>();

    private LinearLayout header_container;
    private LinearLayout rv_bulletin_container;
    private RecyclerView rv_bulletin;
    private BulletinHeaderAdapter bulletinheaderadapter;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_bulletin, container, false);
        try {
            init(view);

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        //BULLETIN
        header_container = (LinearLayout) view.findViewById(R.id.header_container);
        rv_bulletin_container = (LinearLayout) view.findViewById(R.id.rv_bulletin_container);
        rv_bulletin = (RecyclerView) view.findViewById(R.id.rv_bulletin);

        //NO RESULT
        noresult = (LinearLayout) view.findViewById(R.id.noresult);
        txv_noresult = (TextView) view.findViewById(R.id.txv_noresult);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        schoolid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCSERVICECODE);

        rv_bulletin.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_bulletin.setNestedScrollingEnabled(false);

        bulletinheaderadapter = new BulletinHeaderAdapter(getViewContext(), SchoolMiniBulletinFragment.this);
        rv_bulletin.setAdapter(bulletinheaderadapter);

        //API CALL
        getSession();
    }

    //---------------API---------------
    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Fetching School Information", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getMiniAppBulletin(getMiniAppBulletinResponseCallBack);
            getMiniAppBulletinV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    /*
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * */
    private void getMiniAppBulletinV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
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
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getMiniAppBulletinV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getMiniAppBulletinV2Object();

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

    private void getMiniAppBulletinV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .getMiniAppBulletinV2(authenticationid, sessionid, param);

        call.enqueue(getMiniAppBulletinV2CallBack);
    }

    private Callback<GenericResponse> getMiniAppBulletinV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            hideProgressDialog();

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    genericBulletinList = new Gson().fromJson(data, new TypeToken<List<GenericBulletin>>() {}.getType());

                    mdb.truncateTable(mdb, SchoolMiniBulletinDB.TABLE_SCHOOL_BULLETIN);

                    for (GenericBulletin schoolminibulletin : genericBulletinList) {
                        mdb.insertSchoolMiniBulletin(mdb, schoolminibulletin);
                    }

                    if (mdb.getSchoolMiniBulletin(mdb).size() > 0) {
                        header_container.setVisibility(View.GONE);
                        rv_bulletin_container.setVisibility(View.VISIBLE);
                        bulletinheaderadapter.updateDataHeader(mdb.getSchoolMiniBulletinGroupByDate(mdb));
                    }
                    else {
                        header_container.setVisibility(View.GONE);
                        rv_bulletin_container.setVisibility(View.GONE);
                        noresult.setVisibility(View.VISIBLE);
                        txv_noresult.setText("NO BULLETIN POSTED YET");
                    }
                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else {
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showNoInternetToast();
        }
    };
}
