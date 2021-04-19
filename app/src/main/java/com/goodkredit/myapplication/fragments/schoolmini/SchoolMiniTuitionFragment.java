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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniActivity;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.adapter.schoolmini.tuitionfee.TuitionHeaderAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniGradesDB;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniGrades;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniTuitionFeeDB;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniStudents;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniTuitionFee;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniPaymentResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniTuitionFeeResponse;
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


public class SchoolMiniTuitionFragment extends BaseFragment implements View.OnClickListener {
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

    //TUITION
    private TextView txv_id;
    private TextView txv_name;
    private TextView txv_courseyrlvllbl;
    private TextView txv_courseyrlvl;

    private RecyclerView rv_tuitionheader;
    private TuitionHeaderAdapter tuitionheaderadapter;

    //LIST
    private ArrayList<SchoolMiniStudents> schoolMiniStudentsList = new ArrayList<>();
    private List<SchoolMiniTuitionFee> schoolminituitionlist = new ArrayList<>();

    private LinearLayout header_container;
    private LinearLayout rv_tuitionheader_container;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    //PENDING REQUEST
    private TextView txv_content;
    private RelativeLayout layout_req_via_payment_channel;

    private SchoolMiniPayment schoolminipayment;
    private String vouchersession = "";
    private String merchantreferenceno = "";
    private double totalamountcheck = 0.00;

    private String soaid = "";
    private String schoolyear = "";
    private String semester = "";
    private String semestralfee = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_tuition, container, false);
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
        rv_tuitionheader_container = (LinearLayout) view.findViewById(R.id.rv_tuitionheader_container);
        rv_tuitionheader = (RecyclerView) view.findViewById(R.id.rv_tuitionheader);

        //NO RESULT
        noresult = (LinearLayout) view.findViewById(R.id.noresult);
        txv_noresult = (TextView) view.findViewById(R.id.txv_noresult);

        //PENDING REQUEST
        txv_content = (TextView) view.findViewById(R.id.txv_content);
        layout_req_via_payment_channel = (RelativeLayout) view.findViewById(R.id.layout_req_via_payment_channel);
        view.findViewById(R.id.btn_cancel_request).setOnClickListener(this);
        view.findViewById(R.id.btn_paynow_request).setOnClickListener(this);
        view.findViewById(R.id.btn_close).setOnClickListener(this);
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

        rv_tuitionheader.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_tuitionheader.setNestedScrollingEnabled(false);

        tuitionheaderadapter = new TuitionHeaderAdapter(getViewContext(), SchoolMiniTuitionFragment.this);
        rv_tuitionheader.setAdapter(tuitionheaderadapter);

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

    private void showCancelRequestWarningDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("Are you sure you want to cancel your payment request?")
                .negativeText("Cancel")
                .positiveText("Proceed")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getCancelledSession();
                    }
                })
                .show();
    }

    private void showCancellationSuccessfulDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("You have cancelled your transaction.")
                .cancelable(false)
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        layout_req_via_payment_channel.setVisibility(View.GONE);
                    }
                })
                .show();
    }

    //---------------API---------------
    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Fetching School Information", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //getStudentTuitionFee(getStudentTuitionFeeCallBack);
            getStudentTuitionFeeV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //GETSTUDENTACCOUNTS
    private void getStudentTuitionFee(Callback<SchoolMiniTuitionFeeResponse> getStudentTuitionFeeCallBack) {
        Call<SchoolMiniTuitionFeeResponse> getstudenttuitionfee = RetrofitBuild.getSchoolAPIService(getViewContext())
                .getStudentTuitionFee(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        schoolid,
                        studentid
                );

        getstudenttuitionfee.enqueue(getStudentTuitionFeeCallBack);
    }

    private final Callback<SchoolMiniTuitionFeeResponse> getStudentTuitionFeeCallBack =
            new Callback<SchoolMiniTuitionFeeResponse>() {

                @Override
                public void onResponse(Call<SchoolMiniTuitionFeeResponse> call, Response<SchoolMiniTuitionFeeResponse> response) {
                    try {
                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                mdb.truncateTable(mdb, SchoolMiniTuitionFeeDB.TABLE_TUITIONFEE);

                                schoolminituitionlist = response.body().getSchoolMiniTuition();

                                for (SchoolMiniTuitionFee schoolminituitionfee : schoolminituitionlist) {
                                    mdb.insertSchoolMiniTuitionFee(mdb, schoolminituitionfee);
                                }

                                if (mdb.getSchoolMiniTuitionFee(mdb).size() > 0) {
                                    header_container.setVisibility(View.VISIBLE);
                                    rv_tuitionheader_container.setVisibility(View.VISIBLE);
                                    noresult.setVisibility(View.GONE);
                                    tuitionheaderadapter.setSchoolTuitionYearData(mdb.getSchoolMiniTuitionFeeGroupBySchoolYear(mdb, studentid));
                                } else {
                                    header_container.setVisibility(View.GONE);
                                    rv_tuitionheader_container.setVisibility(View.GONE);
                                    noresult.setVisibility(View.VISIBLE);
                                    txv_noresult.setText("NO BALANCE POSTED YET");
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
                public void onFailure(Call<SchoolMiniTuitionFeeResponse> call, Throwable t) {
                    showErrorToast();
                    hideProgressDialog();
                }
            };

    public void getSessionTuitionStatus() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            soaid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_PASSEDSOAID);
            schoolyear = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_PASSEDSCHOOLYEAR);
            semester = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_PASSEDSEMESTER);
            semestralfee = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_PASSEDSEMESTRALFEE);

            //checkTuitionFeePaymentStatus(checkTuitionFeePaymentStatusCallBack);
            checkTuitionFeePaymentStatusV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void checkTuitionFeePaymentStatus(Callback<SchoolMiniPaymentResponse> checkTuitionFeePaymentStatusCallBack) {
        Call<SchoolMiniPaymentResponse> checkTuitionFeePaymentStatus = RetrofitBuild.getSchoolAPIService(getViewContext())
                .checkTuitionFeePaymentStatus(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        schoolid,
                        studentid,
                        soaid
                );

        checkTuitionFeePaymentStatus.enqueue(checkTuitionFeePaymentStatusCallBack);
    }

    private final Callback<SchoolMiniPaymentResponse> checkTuitionFeePaymentStatusCallBack =
            new Callback<SchoolMiniPaymentResponse>() {

                @Override
                public void onResponse(Call<SchoolMiniPaymentResponse> call, Response<SchoolMiniPaymentResponse> response) {
                    try {
                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                layout_req_via_payment_channel.setVisibility(View.GONE);
                                Bundle args = new Bundle();
                                args.putString("STUDENTID", studentid);
                                args.putString("COURSE", course);
                                args.putString("YEARLEVEL", yearlevel);
                                args.putString("FIRSTNAME", firstname);
                                args.putString("MIDDLENAME", middlename);
                                args.putString("LASTNAME", lastname);
                                args.putString("MOBILENUMBER", mobilenumber);
                                args.putString("EMAILADDRESS", emailaddress);
                                args.putString("SCHOOLYEAR", schoolyear);
                                args.putString("SEMESTER", semester);
                                args.putString("SEMESTRALFEE", semestralfee);
                                args.putString("SOAID", soaid);
                                SchoolMiniActivity.start(mContext, 4, args);
                            } else if (response.body().getStatus().equals("006")) {
                                schoolminipayment = response.body().getSchoolMiniPayment();
                                totalamountcheck = schoolminipayment.getTotalAmount();
                                schoolminipayment = response.body().getSchoolMiniPayment();
                                vouchersession = schoolminipayment.getVoucherSessionID();
                                merchantreferenceno = schoolminipayment.getMerchantReferenceNo();
                                txv_content.setText(response.body().getMessage());
                                layout_req_via_payment_channel.setVisibility(View.VISIBLE);
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
                public void onFailure(Call<SchoolMiniPaymentResponse> call, Throwable t) {
                    showErrorToast();
                    hideProgressDialog();
                }
     };

    //CANCEL SESSION
    private void getCancelledSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            cancelSchoolPaymentPartnerRequest(cancelSchoolPaymentPartnerRequestCallBack);
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    //PAYSTUDENTTUITIONFEE
    private void cancelSchoolPaymentPartnerRequest(Callback<GenericResponse> payStudentTuitionFeeResponseCallBack) {

        Call<GenericResponse> paystudenttuitionfee = RetrofitBuild.getSchoolAPIService(getViewContext())
                .cancelSchoolPaymentPartnerRequest(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        schoolid,
                        soaid,
                        merchantreferenceno,
                        studentid
                );

        paystudenttuitionfee.enqueue(payStudentTuitionFeeResponseCallBack);
    }

    private final Callback<GenericResponse> cancelSchoolPaymentPartnerRequestCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        showCancellationSuccessfulDialog();
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
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorToast();
            t.printStackTrace();
            t.getLocalizedMessage();
            hideProgressDialog();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close: {
                layout_req_via_payment_channel.setVisibility(View.GONE);
                break;
            }
            case R.id.btn_cancel_request: {
                showCancelRequestWarningDialog();
                break;
            }
            case R.id.btn_paynow_request: {
                SchoolMiniBillingReferenceActivity.start(getViewContext(), schoolminipayment, String.valueOf(totalamountcheck), "schoolMiniTuitionFragment");
                break;
            }
        }
    }


    /*
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * */

    private void getStudentTuitionFeeV2() {
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
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getStudentTuitionFeeV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getStudentTuitionFeeV2Object();

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

    private void getStudentTuitionFeeV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .getStudentTuitionFeeV2(authenticationid, sessionid, param);

        call.enqueue(getStudentTuitionFeeV2CallBack);
    }

    private Callback<GenericResponse> getStudentTuitionFeeV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            hideProgressDialog();

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    schoolminituitionlist = new Gson().fromJson(data, new TypeToken<List<SchoolMiniTuitionFee>>() {}.getType());

                    mdb.truncateTable(mdb, SchoolMiniTuitionFeeDB.TABLE_TUITIONFEE);

                    for (SchoolMiniTuitionFee schoolminituitionfee : schoolminituitionlist) {
                        mdb.insertSchoolMiniTuitionFee(mdb, schoolminituitionfee);
                    }

                    if (mdb.getSchoolMiniTuitionFee(mdb).size() > 0) {
                        header_container.setVisibility(View.VISIBLE);
                        rv_tuitionheader_container.setVisibility(View.VISIBLE);
                        noresult.setVisibility(View.GONE);
                        tuitionheaderadapter.setSchoolTuitionYearData(mdb.getSchoolMiniTuitionFeeGroupBySchoolYear(mdb, studentid));
                    } else {
                        header_container.setVisibility(View.GONE);
                        rv_tuitionheader_container.setVisibility(View.GONE);
                        noresult.setVisibility(View.VISIBLE);
                        txv_noresult.setText("NO BALANCE POSTED YET");
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

    private void checkTuitionFeePaymentStatusV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("schoolid", schoolid);
                parameters.put("studentid", studentid);
                parameters.put("soaid", soaid);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "checkTuitionFeePaymentStatusV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                checkTuitionFeePaymentStatusV2Object();

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

    private void checkTuitionFeePaymentStatusV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .checkTuitionFeePaymentStatusV2(authenticationid, sessionid, param);

        call.enqueue(checkTuitionFeePaymentStatusV2CallBack);
    }

    private Callback<GenericResponse> checkTuitionFeePaymentStatusV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            hideProgressDialog();

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    layout_req_via_payment_channel.setVisibility(View.GONE);
                    Bundle args = new Bundle();
                    args.putString("STUDENTID", studentid);
                    args.putString("COURSE", course);
                    args.putString("YEARLEVEL", yearlevel);
                    args.putString("FIRSTNAME", firstname);
                    args.putString("MIDDLENAME", middlename);
                    args.putString("LASTNAME", lastname);
                    args.putString("MOBILENUMBER", mobilenumber);
                    args.putString("EMAILADDRESS", emailaddress);
                    args.putString("SCHOOLYEAR", schoolyear);
                    args.putString("SEMESTER", semester);
                    args.putString("SEMESTRALFEE", semestralfee);
                    args.putString("SOAID", soaid);
                    SchoolMiniActivity.start(mContext, 4, args);

                } else {
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else if (response.body().getStatus().equals("006")) {
                        schoolminipayment = new Gson().fromJson(data, new TypeToken<SchoolMiniPayment>(){}.getType());
                        totalamountcheck = schoolminipayment.getTotalAmount();
                        vouchersession = schoolminipayment.getVoucherSessionID();
                        merchantreferenceno = schoolminipayment.getMerchantReferenceNo();
                        txv_content.setText(decryptedMessage);
                        layout_req_via_payment_channel.setVisibility(View.VISIBLE);
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
