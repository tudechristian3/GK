package com.goodkredit.myapplication.fragments.schoolmini;

import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniStudentDB;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniConfig;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDetails;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniStudents;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniConfigResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniDetailsResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniStudentsResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
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

public class SchoolMiniMenuFragment extends BaseFragment implements View.OnClickListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //LOGO
    private ImageView imv_school_logo;
    private String school_link;
    private String schoolname = "";
    private String servicestatus = "";

    //MENU
    private LinearLayout btn_grades;
    private LinearLayout btn_tuition;
    private LinearLayout btn_bulletin;
    private LinearLayout btn_inbox;
    private LinearLayout btn_profile;
    private LinearLayout btn_dtr;

    private TextView txv_grades;
    private TextView txv_tuitionfee;
    private TextView txv_bulletin;
    private TextView txv_support;
    private TextView txv_profile;
    private TextView txv_dtr;

    //SCHOOL
    private LinearLayout school_menu_container;
    private LinearLayout imv_school_logo_container;

    private String schoolid = "";


    //ERROR
    private FrameLayout error_main_container;
    private RelativeLayout error_sub_container;
    private LinearLayout errormsg_container;
    private TextView txv_errormsg;

    //LIST
    private List<SchoolMiniDetails> schoolMiniDetailsList = new ArrayList<>();
    private ArrayList<SchoolMiniStudents> schoolMiniStudentsList = new ArrayList<>();

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    //PASSED DATA
    private String studentid = "";
    private String yearlevel = "";
    private String course = "";
    private String firstname = "";
    private String middlename = "";
    private String lastname = "";
    private String mobilenumber = "";
    private String emailaddress = "";
    private String address = "";

    //SCHOOL DIVIDER
    private View btn_grades_divider;
    private View btn_tuition_divider;
    private View btn_bulletin_divider;
    private View btn_support_divider;
    private View btn_profile_divider;
    private View btn_dtr_divider;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_menu, container, false);
        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        //LOGO
        imv_school_logo = (ImageView) view.findViewById(R.id.imv_school_logo);

        //SCHOOL
        imv_school_logo_container = (LinearLayout) view.findViewById(R.id.imv_school_logo_container);
        school_menu_container = (LinearLayout) view.findViewById(R.id.school_menu_container);

        //MENU
        btn_grades = (LinearLayout) view.findViewById(R.id.btn_grades);
        btn_grades.setOnClickListener(this);
        btn_tuition = (LinearLayout) view.findViewById(R.id.btn_tuition);
        btn_tuition.setOnClickListener(this);
        btn_bulletin = (LinearLayout) view.findViewById(R.id.btn_bulletin);
        btn_bulletin.setOnClickListener(this);
        btn_inbox = (LinearLayout) view.findViewById(R.id.btn_inbox);
        btn_inbox.setOnClickListener(this);
        btn_profile = (LinearLayout) view.findViewById(R.id.btn_profile);
        btn_profile.setOnClickListener(this);
        btn_dtr = (LinearLayout) view.findViewById(R.id.btn_dtr);
        btn_dtr.setOnClickListener(this);



        txv_grades = (TextView) view.findViewById(R.id.txv_grades);
        txv_tuitionfee = (TextView) view.findViewById(R.id.txv_tuitionfee);
        txv_bulletin = (TextView) view.findViewById(R.id.txv_bulletin);
        txv_support = (TextView) view.findViewById(R.id.txv_support);
        txv_profile = (TextView) view.findViewById(R.id.txv_profile);
        txv_dtr = (TextView) view.findViewById(R.id.txv_dtr);


        btn_grades_divider = (View) view.findViewById(R.id.btn_grades_divider);
        btn_tuition_divider = (View) view.findViewById(R.id.btn_tuition_divider);
        btn_bulletin_divider = (View) view.findViewById(R.id.btn_bulletin_divider);
        btn_support_divider = (View) view.findViewById(R.id.btn_support_divider);
        btn_profile_divider = (View) view.findViewById(R.id.btn_profile_divider);
        btn_dtr_divider = (View) view.findViewById(R.id.btn_dtr_divider);


        //ERROR
        error_main_container = view.findViewById(R.id.error_main_container);
        error_sub_container = view.findViewById(R.id.error_sub_container);
        errormsg_container = view.findViewById(R.id.errormsg_container);
        txv_errormsg = (TextView) view.findViewById(R.id.txv_errormsg);

    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        schoolid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCSERVICECODE);
        servicestatus = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTSTATUS);

        //API CALL
        getSession();

    }

    //GETS THE LOGO OF THE SCHOOL
    private void gettingDetailsLogo() {
        try {
            Glide.with(getViewContext())
                    .load(school_link)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    )
                    .into(imv_school_logo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SCHOOL GENERIC ERROR MESSAGE
    private void schoolGenericErrorMessage(String message) {
        if (message.trim().equals("") || message.trim().equals(".")) {
            showErrorToast();
            hideProgressDialog();
            school_menu_container.setVisibility(View.GONE);
            error_main_container.setVisibility(View.VISIBLE);
            txv_errormsg.setText("An error has occurred. Please try again.");
        } else {
            showErrorToast(message);
            hideProgressDialog();
            school_menu_container.setVisibility(View.GONE);
            error_main_container.setVisibility(View.VISIBLE);
            txv_errormsg.setText(message);
        }
    }

    //SCHOOL POSITION OF GLOBAL ERROR LAYOUT
    private void schoolMiniGlobalErrorLayout(boolean centerornot) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) errormsg_container.getLayoutParams();

        if (centerornot) {
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            errormsg_container.setLayoutParams(layoutParams);
        } else {
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
            errormsg_container.setLayoutParams(layoutParams);
        }
    }

    //---------------API---------------
    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Fetching School Information", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getSchoolDetails(getSchoolDetailsResponseCallback);
            getSchoolDetailsV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //GETSCHOOLDETAILS
    private void getSchoolDetails(Callback<SchoolMiniDetailsResponse> getSchoolDetailsResponseCallback) {
        Call<SchoolMiniDetailsResponse> getschooldetails = RetrofitBuild.getSchoolAPIService(getViewContext())
                .getSchoolDetails(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        schoolid
                );

        getschooldetails.enqueue(getSchoolDetailsResponseCallback);
    }


    private final Callback<SchoolMiniDetailsResponse> getSchoolDetailsResponseCallback = new
            Callback<SchoolMiniDetailsResponse>() {

                @Override
                public void onResponse(Call<SchoolMiniDetailsResponse> call, Response<SchoolMiniDetailsResponse> response) {
                    try {
                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                imv_school_logo_container.setVisibility(View.VISIBLE);
                                schoolMiniDetailsList = response.body().getSchoolMiniDetails();
                                CacheManager.getInstance().saveSchoolDetails(schoolMiniDetailsList);

                                if (CacheManager.getInstance().getSchoolDetailsList().size() > 0) {
                                    for (SchoolMiniDetails schooldetails : schoolMiniDetailsList) {
                                        school_link = schooldetails.getSchoolLogo();
                                        schoolname = schooldetails.getSchoolName();
                                    }

                                    PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME);
                                    PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, schoolname);

                                    ((BaseActivity) getViewContext()).setupToolbarWithTitle(CommonFunctions.replaceEscapeData(schoolname));

                                    gettingDetailsLogo();
                                    showProgressDialog("Fetching Student Information", "Please wait...");
                                    //getStudentAccounts(getStudentAccountsCallBack);
                                    getStudentAccountsV2();
                                }
                                schoolMiniGlobalErrorLayout(false);
                            } else if (response.body().getStatus().equals("006")) {
                                imv_school_logo_container.setVisibility(View.GONE);
                                school_menu_container.setVisibility(View.GONE);
                                error_main_container.setVisibility(View.VISIBLE);
                                txv_errormsg.setText("School service does not exist or not active. Please try again later.");
                                schoolMiniGlobalErrorLayout(true);
                            } else {
                                schoolGenericErrorMessage(response.body().getMessage());
                                schoolMiniGlobalErrorLayout(true);
                            }
                        } else {
                            schoolGenericErrorMessage("");
                            schoolMiniGlobalErrorLayout(true);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        schoolGenericErrorMessage("");
                        schoolMiniGlobalErrorLayout(true);
                    }
                }

                @Override
                public void onFailure(Call<SchoolMiniDetailsResponse> call, Throwable t) {
                    schoolGenericErrorMessage("");
                    schoolMiniGlobalErrorLayout(true);
                }
            };

    //GETSTUDENTACCOUNTS
    private void getStudentAccounts(Callback<SchoolMiniStudentsResponse> getStudentAccountsCallBack) {
        Call<SchoolMiniStudentsResponse> getstudentaccounts = RetrofitBuild.getSchoolAPIService(getViewContext())
                .getStudentAccounts(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        schoolid,
                        userid
                );

        getstudentaccounts.enqueue(getStudentAccountsCallBack);
    }

    private final Callback<SchoolMiniStudentsResponse> getStudentAccountsCallBack =
            new Callback<SchoolMiniStudentsResponse>() {

                @Override
                public void onResponse(Call<SchoolMiniStudentsResponse> call, Response<SchoolMiniStudentsResponse> response) {
                    try {
                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                mdb.truncateTable(mdb, SchoolMiniStudentDB.TABLE_STUDENTS);

                                schoolMiniStudentsList = response.body().getSchoolMiniStudents();

                                for (SchoolMiniStudents schoolministudents : schoolMiniStudentsList) {
                                    mdb.insertSchoolMiniStudents(mdb, schoolministudents);
                                }

                                if (mdb.getSchoolMiniStudents(mdb).size() == 1) {
                                    school_menu_container.setVisibility(View.GONE);
                                } else if (mdb.getSchoolMiniStudents(mdb).size() > 1) {
                                    school_menu_container.setVisibility(View.GONE);
                                } else {
                                    school_menu_container.setVisibility(View.GONE);
                                    error_main_container.setVisibility(View.VISIBLE);
                                    txv_errormsg.setText("Student not found. Please contact support.");
                                }
                                if (servicestatus.trim().toUpperCase().equals("OFFLINE")) {
                                    school_menu_container.setVisibility(View.GONE);
                                    error_main_container.setVisibility(View.VISIBLE);
                                    txv_errormsg.setText("It seems that the school is offline. Please try again later.");
                                } else {
                                    //getMiniAppConfig(getMiniAppConfigCallBack);
                                    getMiniAppConfigV2();
                                }
                            } else if (response.body().getStatus().equals("007")) {
                                if (servicestatus.trim().toUpperCase().equals("OFFLINE")) {
                                    school_menu_container.setVisibility(View.GONE);
                                    error_main_container.setVisibility(View.VISIBLE);
                                    txv_errormsg.setText("It seems that the school is offline. Please try again later.");
                                } else {
                                    school_menu_container.setVisibility(View.GONE);
                                    error_main_container.setVisibility(View.VISIBLE);
                                    txv_errormsg.setText("It seems that you are not connected. Please register in the school registrars office.");
                                }
                            } else {
                                schoolGenericErrorMessage(response.body().getMessage());
                            }
                        } else {
                            schoolGenericErrorMessage("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        schoolGenericErrorMessage("");
                    }
                }

                @Override
                public void onFailure(Call<SchoolMiniStudentsResponse> call, Throwable t) {
                    schoolGenericErrorMessage("");
                }
            };

    //GETMINIAPPCONFIG
    private void getMiniAppConfig(Callback<SchoolMiniConfigResponse> getMiniAppConfigCallBack) {
        Call<SchoolMiniConfigResponse> getminiappconfig = RetrofitBuild.getSchoolAPIService(getViewContext())
                .getMiniAppConfig(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        schoolid
                );

        getminiappconfig.enqueue(getMiniAppConfigCallBack);
    }

    private final Callback<SchoolMiniConfigResponse> getMiniAppConfigCallBack =
            new Callback<SchoolMiniConfigResponse>() {

                @Override
                public void onResponse(Call<SchoolMiniConfigResponse> call, Response<SchoolMiniConfigResponse> response) {
                    try {
                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                List<SchoolMiniConfig> schoolMiniConfigList = response.body().getSchoolminiconfiglist();
                                school_menu_container.setVisibility(View.VISIBLE);
                                error_main_container.setVisibility(View.GONE);

                                for (SchoolMiniConfig schoolminiconfig : schoolMiniConfigList) {
                                    switch (schoolminiconfig.getServiceConfigName()) {

                                        case "Grades": {
                                            if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                                btn_grades.setVisibility(View.VISIBLE);
                                                btn_grades_divider.setVisibility(View.VISIBLE);
                                                txv_grades.setText(schoolminiconfig.getServiceConfigName());
                                            } else {
                                                btn_grades.setVisibility(View.GONE);
                                                btn_grades_divider.setVisibility(View.GONE);
                                            }
                                            break;
                                        }
                                        case "School Fee": {
                                            if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                                btn_tuition.setVisibility(View.VISIBLE);
                                                btn_tuition_divider.setVisibility(View.VISIBLE);
                                                txv_tuitionfee.setText("School Fees");
                                            } else {
                                                btn_tuition.setVisibility(View.GONE);
                                                btn_tuition_divider.setVisibility(View.GONE);
                                            }
                                            break;
                                        }
                                        case "Bulletin": {
                                            if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                                btn_bulletin.setVisibility(View.VISIBLE);
                                                btn_bulletin_divider.setVisibility(View.VISIBLE);
                                                txv_bulletin.setText(schoolminiconfig.getServiceConfigName());
                                            } else {
                                                btn_bulletin.setVisibility(View.GONE);
                                                btn_bulletin_divider.setVisibility(View.GONE);
                                            }
                                            break;
                                        }
                                        case "Support": {
                                            if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                                btn_inbox.setVisibility(View.VISIBLE);
                                                btn_support_divider.setVisibility(View.VISIBLE);
                                                txv_support.setText(schoolminiconfig.getServiceConfigName());
                                            } else {
                                                btn_inbox.setVisibility(View.GONE);
                                                btn_support_divider.setVisibility(View.GONE);
                                            }
                                            break;
                                        }
                                        case "Profile": {
                                            if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                                btn_profile.setVisibility(View.VISIBLE);
                                                btn_profile_divider.setVisibility(View.VISIBLE);
                                                txv_profile.setText(schoolminiconfig.getServiceConfigName());
                                            } else {
                                                btn_profile.setVisibility(View.GONE);
                                                btn_profile_divider.setVisibility(View.GONE);
                                            }
                                            break;
                                        }

                                        case "DTR": {
                                            if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                                btn_dtr.setVisibility(View.VISIBLE);
                                                btn_dtr_divider.setVisibility(View.VISIBLE);
                                                txv_dtr.setText("Student Attendant");
                                            } else {
                                                btn_dtr.setVisibility(View.GONE);
                                                btn_dtr_divider.setVisibility(View.GONE);
                                            }
                                            break;
                                        }
                                    }
                                }

                                if (btn_grades.getVisibility() == View.GONE &&
                                        btn_tuition.getVisibility() == View.GONE &&
                                        btn_bulletin.getVisibility() == View.GONE &&
                                        btn_inbox.getVisibility() == View.GONE &&
                                        btn_profile.getVisibility() == View.GONE &&
                                        btn_dtr.getVisibility() == View.GONE
                                        ) {
                                    error_main_container.setVisibility(View.VISIBLE);
                                    txv_errormsg.setText(R.string.school_service_disabled);
                                }
                            } else {
                                schoolGenericErrorMessage(response.body().getMessage());
                            }
                        } else {
                            schoolGenericErrorMessage("");
                        }
                    } catch (Exception e) {
                        schoolGenericErrorMessage("");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<SchoolMiniConfigResponse> call, Throwable t) {
                    schoolGenericErrorMessage("");
                }
            };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_grades: {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (mdb.getSchoolMiniStudents(mdb).size() == 1) {

                    for (SchoolMiniStudents schoolMiniStudents : schoolMiniStudentsList) {
                        studentid = schoolMiniStudents.getStudentID();
                        yearlevel = schoolMiniStudents.getYearLevel();
                        course = schoolMiniStudents.getCourse();
                        firstname = schoolMiniStudents.getFirstName();
                        middlename = schoolMiniStudents.getMiddleName();
                        lastname = schoolMiniStudents.getLastName();
                        mobilenumber = schoolMiniStudents.getMobileNumber();
                        emailaddress = schoolMiniStudents.getEmailAddress();
                    }

                    Bundle args = new Bundle();
                    args.putString("STUDENTID", studentid);
                    args.putString("YEARLEVEL", yearlevel);
                    args.putString("COURSE", course);
                    args.putString("FIRSTNAME", firstname);
                    args.putString("MIDDLENAME", middlename);
                    args.putString("LASTNAME", lastname);
                    args.putString("MOBILENUMBER", mobilenumber);
                    args.putString("EMAILADDRESS", emailaddress);
                    SchoolMiniActivity.start(getViewContext(), 1, args);
                } else if (mdb.getSchoolMiniStudents(mdb).size() > 1) {
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("STUDENTSLIST", schoolMiniStudentsList);
                    args.putString("TYPE", "Grades");
                    SchoolMiniActivity.start(getViewContext(), 2, args);
                }
                break;
            }
            case R.id.btn_tuition: {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (mdb.getSchoolMiniStudents(mdb).size() == 1) {
                    for (SchoolMiniStudents schoolMiniStudents : schoolMiniStudentsList) {
                        studentid = schoolMiniStudents.getStudentID();
                        yearlevel = schoolMiniStudents.getYearLevel();
                        course = schoolMiniStudents.getCourse();
                        firstname = schoolMiniStudents.getFirstName();
                        middlename = schoolMiniStudents.getMiddleName();
                        lastname = schoolMiniStudents.getLastName();
                        mobilenumber = schoolMiniStudents.getMobileNumber();
                        emailaddress = schoolMiniStudents.getEmailAddress();
                    }
                    Bundle args = new Bundle();
                    args.putString("STUDENTID", studentid);
                    args.putString("YEARLEVEL", yearlevel);
                    args.putString("COURSE", course);
                    args.putString("FIRSTNAME", firstname);
                    args.putString("MIDDLENAME", middlename);
                    args.putString("LASTNAME", lastname);
                    args.putString("MOBILENUMBER", mobilenumber);
                    args.putString("EMAILADDRESS", emailaddress);
                    SchoolMiniActivity.start(getViewContext(), 3, args);
                } else if (mdb.getSchoolMiniStudents(mdb).size() > 1) {
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("STUDENTSLIST", schoolMiniStudentsList);
                    args.putString("TYPE", "Tuition");
                    SchoolMiniActivity.start(getViewContext(), 2, args);
                }
                break;
            }
            case R.id.btn_bulletin: {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Bundle args = new Bundle();
                SchoolMiniActivity.start(getViewContext(), 6, args);
                break;
            }
            case R.id.btn_inbox: {
                Bundle args = new Bundle();
                SchoolMiniActivity.start(getViewContext(), 7, args);
                break;
            }

            case R.id.btn_profile: {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (mdb.getSchoolMiniStudents(mdb).size() == 1) {
                    for (SchoolMiniStudents schoolMiniStudents : schoolMiniStudentsList) {
                        studentid = schoolMiniStudents.getStudentID();
                        yearlevel = schoolMiniStudents.getYearLevel();
                        course = schoolMiniStudents.getCourse();
                        firstname = schoolMiniStudents.getFirstName();
                        middlename = schoolMiniStudents.getMiddleName();
                        lastname = schoolMiniStudents.getLastName();
                        mobilenumber = schoolMiniStudents.getMobileNumber();
                        emailaddress = schoolMiniStudents.getEmailAddress();
                        address = schoolMiniStudents.getStreetAddress() + ", "
                                + schoolMiniStudents.getCity() + ", "
                                + schoolMiniStudents.getProvince() + ", "
                                + schoolMiniStudents.getCountry();
                    }

                    Bundle args = new Bundle();
                    args.putString("STUDENTID", studentid);
                    args.putString("YEARLEVEL", yearlevel);
                    args.putString("COURSE", course);
                    args.putString("FIRSTNAME", firstname);
                    args.putString("MIDDLENAME", middlename);
                    args.putString("LASTNAME", lastname);
                    args.putString("MOBILENUMBER", mobilenumber);
                    args.putString("ADDRESS", address);
                    args.putString("EMAILADDRESS", emailaddress);
                    SchoolMiniActivity.start(getViewContext(), 5, args);
                } else if (mdb.getSchoolMiniStudents(mdb).size() > 1) {
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("STUDENTSLIST", schoolMiniStudentsList);
                    args.putString("TYPE", "Profile");
                    SchoolMiniActivity.start(getViewContext(), 2, args);
                }

                break;
            }

            case R.id.btn_dtr: {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (mdb.getSchoolMiniStudents(mdb).size() == 1) {
                    String datetimeuploaded = "";

                    for (SchoolMiniStudents schoolMiniStudents : schoolMiniStudentsList) {
                        studentid = schoolMiniStudents.getStudentID();
                        yearlevel = schoolMiniStudents.getYearLevel();
                        course = schoolMiniStudents.getCourse();
                        firstname = schoolMiniStudents.getFirstName();
                        middlename = schoolMiniStudents.getMiddleName();
                        lastname = schoolMiniStudents.getLastName();
                        mobilenumber = schoolMiniStudents.getMobileNumber();
                        emailaddress = schoolMiniStudents.getEmailAddress();
                        datetimeuploaded = schoolMiniStudents.getDateTimeUploaded();
                    }

                    Bundle args = new Bundle();
                    args.putString("STUDENTID", studentid);
                    args.putString("YEARLEVEL", yearlevel);
                    args.putString("COURSE", course);
                    args.putString("FIRSTNAME", firstname);
                    args.putString("MIDDLENAME", middlename);
                    args.putString("LASTNAME", lastname);
                    args.putString("MOBILENUMBER", mobilenumber);
                    args.putString("EMAILADDRESS", emailaddress);
                    args.putString("DATETIMEUPLOAD", datetimeuploaded);
                    SchoolMiniActivity.start(getViewContext(), 12, args);
                } else if (mdb.getSchoolMiniStudents(mdb).size() > 1) {
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("STUDENTSLIST", schoolMiniStudentsList);
                    args.putString("TYPE", "Dtr");
                    SchoolMiniActivity.start(getViewContext(), 2, args);
                }
                break;
            }
        }
    }


    /*
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * */
    private void getSchoolDetailsV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("schoolid", schoolid);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getSchoolDetailsV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getSchoolDetailsV2Object();

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

    private void getSchoolDetailsV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .getSchoolDetailsV2(authenticationid, sessionid, param);

        call.enqueue(getSchoolDetailsV2CallBack);
    }

    private Callback<GenericResponse> getSchoolDetailsV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            hideProgressDialog();

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    schoolMiniDetailsList = new Gson().fromJson(data, new TypeToken<List<SchoolMiniDetails>>() {}.getType());

                    imv_school_logo_container.setVisibility(View.VISIBLE);
                    CacheManager.getInstance().saveSchoolDetails(schoolMiniDetailsList);

                    if (CacheManager.getInstance().getSchoolDetailsList().size() > 0) {
                        for (SchoolMiniDetails schooldetails : schoolMiniDetailsList) {
                            school_link = schooldetails.getSchoolLogo();
                            schoolname = schooldetails.getSchoolName();
                        }
                        PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME);
                        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, schoolname);

                        ((BaseActivity) getViewContext()).setupToolbarWithTitle(CommonFunctions.replaceEscapeData(schoolname));

                        gettingDetailsLogo();
                        showProgressDialog("Fetching Student Information", "Please wait...");
                        getStudentAccountsV2();
                    }
                    schoolMiniGlobalErrorLayout(false);
                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else if (response.body().getStatus().equals("006")) {
                        imv_school_logo_container.setVisibility(View.GONE);
                        school_menu_container.setVisibility(View.GONE);
                        error_main_container.setVisibility(View.VISIBLE);
                        txv_errormsg.setText("School service does not exist or not active. Please try again later.");
                        schoolMiniGlobalErrorLayout(true);
                    } else {
                        schoolGenericErrorMessage(decryptedMessage);
                        schoolMiniGlobalErrorLayout(true);
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

    private void getStudentAccountsV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("schoolid", schoolid);
                parameters.put("mobileno", userid);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getStudentAccountsV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getStudentAccountsV2Object();

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

    private void getStudentAccountsV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .getStudentAccountsV2(authenticationid, sessionid, param);

        call.enqueue(getStudentAccountsV2CallBack);
    }

    private Callback<GenericResponse> getStudentAccountsV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            hideProgressDialog();

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    schoolMiniStudentsList = new Gson().fromJson(data, new TypeToken<List<SchoolMiniStudents>>() {}.getType());

                    mdb.truncateTable(mdb, SchoolMiniStudentDB.TABLE_STUDENTS);

                    for (SchoolMiniStudents schoolministudents : schoolMiniStudentsList) {
                        mdb.insertSchoolMiniStudents(mdb, schoolministudents);
                    }

                    if (mdb.getSchoolMiniStudents(mdb).size() == 1) {
                        school_menu_container.setVisibility(View.GONE);
                    } else if (mdb.getSchoolMiniStudents(mdb).size() > 1) {
                        school_menu_container.setVisibility(View.GONE);
                    } else {
                        school_menu_container.setVisibility(View.GONE);
                        error_main_container.setVisibility(View.VISIBLE);
                        txv_errormsg.setText("Student not found. Please contact support.");
                    }
                    if (servicestatus.trim().toUpperCase().equals("OFFLINE")) {
                        school_menu_container.setVisibility(View.GONE);
                        error_main_container.setVisibility(View.VISIBLE);
                        txv_errormsg.setText("It seems that the school is offline. Please try again later.");
                    } else {
                        getMiniAppConfigV2();
                    }

                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else if (response.body().getStatus().equals("007")) {
                        if (servicestatus.trim().toUpperCase().equals("OFFLINE")) {
                            school_menu_container.setVisibility(View.GONE);
                            error_main_container.setVisibility(View.VISIBLE);
                            txv_errormsg.setText("It seems that the school is offline. Please try again later.");
                        } else {
                            school_menu_container.setVisibility(View.GONE);
                            error_main_container.setVisibility(View.VISIBLE);
                            txv_errormsg.setText("It seems that you are not connected. Please register in the school registrars office.");
                        }
                    } else {
                        schoolGenericErrorMessage(decryptedMessage);
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

    private void getMiniAppConfigV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
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
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getMiniAppConfigV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getMiniAppConfigV2Object();

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

    private void getMiniAppConfigV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .getMiniAppConfigV2(authenticationid, sessionid, param);

        call.enqueue(getMiniAppConfigV2CallBack);
    }

    private Callback<GenericResponse> getMiniAppConfigV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            hideProgressDialog();

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    List<SchoolMiniConfig> schoolMiniConfigList = new Gson().fromJson(data, new TypeToken<List<SchoolMiniConfig>>() {}.getType());

                    school_menu_container.setVisibility(View.VISIBLE);
                    error_main_container.setVisibility(View.GONE);

                    for (SchoolMiniConfig schoolminiconfig : schoolMiniConfigList) {
                        switch (schoolminiconfig.getServiceConfigName()) {

                            case "Grades": {
                                if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                    btn_grades.setVisibility(View.VISIBLE);
                                    btn_grades_divider.setVisibility(View.VISIBLE);
                                    txv_grades.setText(schoolminiconfig.getServiceConfigName());
                                } else {
                                    btn_grades.setVisibility(View.GONE);
                                    btn_grades_divider.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case "School Fee": {
                                if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                    btn_tuition.setVisibility(View.VISIBLE);
                                    btn_tuition_divider.setVisibility(View.VISIBLE);
                                    txv_tuitionfee.setText("School Fees");
                                } else {
                                    btn_tuition.setVisibility(View.GONE);
                                    btn_tuition_divider.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case "Bulletin": {
                                if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                    btn_bulletin.setVisibility(View.VISIBLE);
                                    btn_bulletin_divider.setVisibility(View.VISIBLE);
                                    txv_bulletin.setText(schoolminiconfig.getServiceConfigName());
                                } else {
                                    btn_bulletin.setVisibility(View.GONE);
                                    btn_bulletin_divider.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case "Support": {
                                if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                    btn_inbox.setVisibility(View.VISIBLE);
                                    btn_support_divider.setVisibility(View.VISIBLE);
                                    txv_support.setText(schoolminiconfig.getServiceConfigName());
                                } else {
                                    btn_inbox.setVisibility(View.GONE);
                                    btn_support_divider.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case "Profile": {
                                if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                    btn_profile.setVisibility(View.VISIBLE);
                                    btn_profile_divider.setVisibility(View.VISIBLE);
                                    txv_profile.setText(schoolminiconfig.getServiceConfigName());
                                } else {
                                    btn_profile.setVisibility(View.GONE);
                                    btn_profile_divider.setVisibility(View.GONE);
                                }
                                break;
                            }

                            case "DTR": {
                                if (schoolminiconfig.getStatus().equals("ACTIVE")) {
                                    btn_dtr.setVisibility(View.VISIBLE);
                                    btn_dtr_divider.setVisibility(View.VISIBLE);
                                    txv_dtr.setText("Student Attendant");
                                } else {
                                    btn_dtr.setVisibility(View.GONE);
                                    btn_dtr_divider.setVisibility(View.GONE);
                                }
                                break;
                            }
                        }
                    }

                    if (btn_grades.getVisibility() == View.GONE &&
                            btn_tuition.getVisibility() == View.GONE &&
                            btn_bulletin.getVisibility() == View.GONE &&
                            btn_inbox.getVisibility() == View.GONE &&
                            btn_profile.getVisibility() == View.GONE &&
                            btn_dtr.getVisibility() == View.GONE
                    ) {
                        error_main_container.setVisibility(View.VISIBLE);
                        txv_errormsg.setText(R.string.school_service_disabled);
                    }

                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        schoolGenericErrorMessage(decryptedMessage);
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
