package com.goodkredit.myapplication.fragments.schoolmini;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.schoolmini.grades.GradesHeaderAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniConfig;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniGradesDB;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniGrades;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniStudents;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniGradesResponse;
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

public class SchoolMiniGradesFragment extends BaseFragment {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

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

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //MAIN CONTAINER
    private NestedScrollView home_body;
    private LinearLayout maincontainer;

    //GRADES
    private TextView txv_id;
    private TextView txv_name;
    private TextView txv_courseyrlvllbl;
    private TextView txv_courseyrlvl;

    private RecyclerView rv_gradesheader;
    private GradesHeaderAdapter gradesheaderadapter;

    //LIST
    private ArrayList<SchoolMiniStudents> schoolMiniStudentsList = new ArrayList<>();
    private List<SchoolMiniGrades> schoolminigradeslist = new ArrayList<>();

    private LinearLayout header_container;
    private LinearLayout rv_gradesheader_container;

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
        View view = inflater.inflate(R.layout.fragment_schoolmini_grades, container, false);
        try {
            init(view);

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        txv_id = (TextView) view.findViewById(R.id.txv_id);
        txv_name = (TextView) view.findViewById(R.id.txv_name);
        txv_courseyrlvllbl = (TextView) view.findViewById(R.id.txv_courseyrlvllbl);
        txv_courseyrlvl = (TextView) view.findViewById(R.id.txv_courseyrlvl);

        header_container = (LinearLayout) view.findViewById(R.id.header_container);
        rv_gradesheader_container = (LinearLayout)  view.findViewById(R.id.rv_gradesheader_container);
        rv_gradesheader = (RecyclerView) view.findViewById(R.id.rv_gradesheader);

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

        if (getArguments() != null) {
            studentid = getArguments().getString("STUDENTID");
            course = getArguments().getString("COURSE");
            yearlevel = getArguments().getString("YEARLEVEL");
            firstname = getArguments().getString("FIRSTNAME");
            middlename = getArguments().getString("MIDDLENAME");
            lastname = getArguments().getString("LASTNAME");
            mobilenumber = getArguments().getString("MOBILENUMBER");
            emailaddress = getArguments().getString("EMAILADDRESS");
        }

        rv_gradesheader.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_gradesheader.setNestedScrollingEnabled(false);

        gradesheaderadapter = new GradesHeaderAdapter(getViewContext(), SchoolMiniGradesFragment.this);
        rv_gradesheader.setAdapter(gradesheaderadapter);

        //DISPLAYS STUDENT INFORMATION
        setStudentInformation();

        //API CALL
        getSession();
    }

    private void setStudentInformation() {
        txv_id.setText(studentid);
        txv_name.setText(firstname + " " + middlename + " " + lastname);
        if(course.trim().equals(".")) {
            txv_courseyrlvllbl.setText("Year: ");
            txv_courseyrlvl.setText(yearlevel);
        }
        else {
            txv_courseyrlvllbl.setText("Course/Year: ");
            txv_courseyrlvl.setText(course + " " + yearlevel);
        }
    }

    //---------------API---------------
    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Fetching School Information", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getStudentGrades(getStudentGradesCallBack);
            getStudentGradesV2();
        } else {
            hideProgressDialog();
            showToast("Seems you are not connected to the internet. Please check your connection and try again" +
                    ". Thank you.", GlobalToastEnum.WARNING);
        }
    }

    //GET STUDENT GRADES
    private void getStudentGrades(Callback<SchoolMiniGradesResponse> getStudentGradesCallBack) {
        Call<SchoolMiniGradesResponse> getstudentgrades = RetrofitBuild.getSchoolAPIService(getViewContext())
                .getStudentGrades(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        schoolid,
                        studentid
                );

        getstudentgrades.enqueue(getStudentGradesCallBack);
    }

    private final Callback<SchoolMiniGradesResponse> getStudentGradesCallBack =
            new Callback<SchoolMiniGradesResponse>() {

                @Override
                public void onResponse(Call<SchoolMiniGradesResponse> call, Response<SchoolMiniGradesResponse> response) {
                    try {
                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                mdb.truncateTable(mdb, SchoolMiniGradesDB.TABLE_GRADES);

                                schoolminigradeslist = response.body().getSchoolMiniGrades();

                                for (SchoolMiniGrades schoolminigrades : schoolminigradeslist) {
                                    mdb.insertSchoolMiniGrades(mdb, schoolminigrades);
                                }

                                if (mdb.getSchoolMiniGrades(mdb).size() > 0) {
                                    header_container.setVisibility(View.VISIBLE);
                                    rv_gradesheader_container.setVisibility(View.VISIBLE);
                                    noresult.setVisibility(View.GONE);
                                    gradesheaderadapter.setSchoolGradesYearData(mdb.getSchoolMiniGradesGroupBySchoolYear(mdb, studentid));
                                }
                                else {
                                    header_container.setVisibility(View.GONE);
                                    rv_gradesheader_container.setVisibility(View.GONE);
                                    noresult.setVisibility(View.VISIBLE);
                                    txv_noresult.setText("NO GRADES POSTED YET");
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
                public void onFailure(Call<SchoolMiniGradesResponse> call, Throwable t) {
                    showErrorToast();
                    hideProgressDialog();
                }
      };

    /*
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * */
    private void getStudentGradesV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("schoolid", schoolid);
                parameters.put("studentid", studentid);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getStudentGradesV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getStudentGradesV2Object();

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

    private void getStudentGradesV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .getStudentGradesV2(authenticationid, sessionid, param);

        call.enqueue(getStudentGradesV2CallBack);
    }

    private Callback<GenericResponse> getStudentGradesV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            hideProgressDialog();

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    schoolminigradeslist = new Gson().fromJson(data, new TypeToken<List<SchoolMiniGrades>>() {}.getType());

                    mdb.truncateTable(mdb, SchoolMiniGradesDB.TABLE_GRADES);

                    for (SchoolMiniGrades schoolminigrades : schoolminigradeslist) {
                        mdb.insertSchoolMiniGrades(mdb, schoolminigrades);
                    }

                    if (mdb.getSchoolMiniGrades(mdb).size() > 0) {
                        header_container.setVisibility(View.VISIBLE);
                        rv_gradesheader_container.setVisibility(View.VISIBLE);
                        noresult.setVisibility(View.GONE);
                        gradesheaderadapter.setSchoolGradesYearData(mdb.getSchoolMiniGradesGroupBySchoolYear(mdb, studentid));
                    }
                    else {
                        header_container.setVisibility(View.GONE);
                        rv_gradesheader_container.setVisibility(View.GONE);
                        noresult.setVisibility(View.VISIBLE);
                        txv_noresult.setText("NO GRADES POSTED YET");
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
