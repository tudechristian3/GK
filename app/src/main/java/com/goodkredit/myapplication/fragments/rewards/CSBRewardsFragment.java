package com.goodkredit.myapplication.fragments.rewards;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.activities.transactions.ViewOthersTransactionsActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 11/28/2017.
 */

public class CSBRewardsFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvPoints;
    private DatabaseHandler db;

    private double mPoints = 0;

    private final String DB_COL_BORROWERID = "borrowerid";
    private final String DB_COL_MOBILE = "mobile";
    private final String DB_COL_IMEI = "imei";

    private RelativeLayout mNonMemberLayout;
    private RelativeLayout mFaileConnectLayout;
    private LinearLayout mMemberLayout;

    private String sessionid = "";

    //ACTIVATE SIM CARD
    private String mobileno;
    private String passmobileno;
    private String authid;

    private TextView csb_link_activatesimcard;
    private TextView csb_nonlink_activatesimcard;
    private TextView csb_failed_activatesimcard;

    private EditText edt_verifynumber;
    private String str_verifynumber;

    private EditText edt_verifyotp;
    private String str_verifyotp;

    private GlobalDialogs dialog;

    public static CSBRewardsFragment newInstance() {
        CSBRewardsFragment fragment = new CSBRewardsFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        db = new DatabaseHandler(getViewContext());
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex(DB_COL_BORROWERID));
                userid = cursor.getString(cursor.getColumnIndex(DB_COL_MOBILE));
                imei = cursor.getString(cursor.getColumnIndex(DB_COL_IMEI));
            } while (cursor.moveToNext());
        }
        cursor.close();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_csb_rewards, container, false);

        tvPoints = (TextView) view.findViewById(R.id.tv_points);
        mNonMemberLayout = (RelativeLayout) view.findViewById(R.id.non_member_layout);
        mFaileConnectLayout = (RelativeLayout) view.findViewById(R.id.failed_to_connect);
        mMemberLayout = (LinearLayout) view.findViewById(R.id.points_layout);

        //ACTIVATE SIM CARD
        csb_link_activatesimcard = (TextView) view.findViewById(R.id.csb_link_activatesimcard);
        csb_nonlink_activatesimcard = (TextView) view.findViewById(R.id.csb_nonlink_activatesimcard);
        csb_failed_activatesimcard = (TextView) view.findViewById(R.id.csb_failed_activatesimcard);

        csb_link_activatesimcard.setText(Html.fromHtml("<u>Activate Sim Card</u>"));
        csb_nonlink_activatesimcard.setText(Html.fromHtml("<u>Activate Sim Card</u>"));
        csb_failed_activatesimcard.setText(Html.fromHtml("<u>Activate Sim Card</u>"));

        csb_link_activatesimcard.setOnClickListener(this);
        csb_nonlink_activatesimcard.setOnClickListener(this);
        csb_failed_activatesimcard.setOnClickListener(this);

        view.findViewById(R.id.refresh_points).setOnClickListener(this);
        view.findViewById(R.id.btn_convert_to_voucher).setOnClickListener(this);
        view.findViewById(R.id.btn_view_history).setOnClickListener(this);

        fetchCSBRewards();
        //mMemberLayout.setVisibility(View.VISIBLE);
        //setTvPoints("0");

        return view;
    }

//    private void fetchCSBRewards() {
//        showProgressDialog("Fetching CSB Rewards.", "Please wait...");
//        createSession(getSessionCallback);
//    }
//
//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            ResponseBody errBody = response.errorBody();
//
//            if (errBody == null) {
//                sessionid = response.body().toString();
//                if (!sessionid.isEmpty()) {
//                    getCSBRewards();
//                } else {
//                    hideProgressDialog();
//                    showError();
//                }
//            } else {
//                hideProgressDialog();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//        }
//    };

    private void fetchCSBRewards() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Fetching CSB Rewards.", "Please wait...");
            getCSBRewards();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getCSBRewards() {
        Call<GenericResponse> call = RetrofitBuild.getRewardsAPIService(getViewContext())
                .getCSBRewards(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        sessionid,
                        borrowerid,
                        userid
                );
        call.enqueue(getCSBRewardsCallback);
    }

    private Callback<GenericResponse> getCSBRewardsCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                if (response.body().getData().isEmpty()) {
                    if (response.body().getStatus().equals("1")) {
                        isShow = false;
                        mNonMemberLayout.setVisibility(View.VISIBLE);
                        mFaileConnectLayout.setVisibility(View.GONE);
                        mMemberLayout.setVisibility(View.GONE);
                    } else if (response.body().getStatus().equals("0")) {
                        isShow = true;
                        mNonMemberLayout.setVisibility(View.GONE);
                        mFaileConnectLayout.setVisibility(View.GONE);
                        mMemberLayout.setVisibility(View.VISIBLE);
                        setTvPoints("0");
                    } else {
                        mNonMemberLayout.setVisibility(View.GONE);
                        mMemberLayout.setVisibility(View.GONE);
                        mFaileConnectLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    isShow = true;
                    mNonMemberLayout.setVisibility(View.GONE);
                    mMemberLayout.setVisibility(View.VISIBLE);
                    setTvPoints(response.body().getData());
                    try {
                        mPoints = Double.parseDouble(response.body().getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    getActivity().invalidateOptionsMenu();
                }
            } else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            setTvPoints("0");
            showError();
            isShow = false;
            getActivity().invalidateOptionsMenu();
        }
    };

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (isShow)
            menu.findItem(R.id.csb_settings).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    private void setTvPoints(String points) {
        tvPoints.setText(CommonFunctions.pointsFormatter(points));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh_points: {
                fetchCSBRewards();
                break;
            }
            case R.id.btn_convert_to_voucher: {
                if (mPoints > 0)
                    VoucherPrepaidRequestActivity.start(getViewContext(), 0, VirtualVoucherProductFragment.BY_CSB_REWARDS, mPoints);
                else
                    showError("You do not have any points to convert. Thank you.");
                break;
            }
            case R.id.btn_view_history: {
                ViewOthersTransactionsActivity.start(getViewContext(), 7);
                break;
            }
            case R.id.csb_link_activatesimcard: {
                dialog = new GlobalDialogs(getViewContext());
                activateSimCard();
                break;
            }
            case R.id.csb_nonlink_activatesimcard: {
                dialog = new GlobalDialogs(getViewContext());
                activateSimCard();
                break;
            }
            case R.id.csb_failed_activatesimcard: {
                dialog = new GlobalDialogs(getViewContext());
                activateSimCard();
                break;
            }
        }
    }

    private void verifyOTP() {

        dialog.createDialog("", "",
                "Activate", "", ButtonTypeEnum.SINGLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //ACTIVATE SIM CARD LABEL
        LinearLayout otpContainer = dialog.getTextViewMessageContainer();
        otpContainer.setPadding(15, 15, 15, 0);

        List<String> otplbllist = new ArrayList<>();
        String otplbl = "Enter Activation Code";
        otplbllist.add(otplbl);

        dialog.setContentCustomMultiTextView(otplbllist, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_045C84, 16, Gravity.TOP | Gravity.CENTER));

        //ACTIVATE SIM CARD EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(35, 0, 35, 20);

        List<String> edt_otp = new ArrayList<>();
        edt_otp.add(String.valueOf(GlobalDialogsEditText.CUSTOMNUMBER));

        LinearLayout editTextContainer = dialog.setContentCustomMultiEditText(edt_otp,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.START,
                        R.drawable.border, 19, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setPadding(20, 20, 20, 20);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    edt_verifyotp = editItem;
                    edt_verifyotp.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                    edt_verifyotp.addTextChangedListener(verifyOTPTextWatcher);
                }
            }
        }

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSession();
            }
        });
    }

    private void activateSimCard (){

        dialog.createDialog("", "",
                "PROCEED", "", ButtonTypeEnum.SINGLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //VERIFY MOBILE NUMBER
        LinearLayout enternumberContainer = dialog.getTextViewMessageContainer();
        enternumberContainer.setPadding(15, 15, 15, 0);

        List<String> enternumberlbllist = new ArrayList<>();
        String enternumberlbl = "Please enter the mobile number for activation. SMS verification is required.";
        enternumberlbllist.add(enternumberlbl);

        dialog.setContentCustomMultiTextView(enternumberlbllist, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_045C84, 16, Gravity.TOP | Gravity.CENTER));

        //ACTIVATE SIM CARD EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(35, 0, 35, 20);

        List<String> edt_enternumber = new ArrayList<>();
        edt_enternumber.add(String.valueOf(GlobalDialogsEditText.CUSTOMNUMBER));

        LinearLayout editTextContainer = dialog.setContentCustomMultiEditText(edt_enternumber,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.START,
                        R.drawable.border, 19, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setPadding(20, 20, 20, 20);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    edt_verifynumber = editItem;
                    edt_verifynumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                    edt_verifynumber.addTextChangedListener(verifyMobileTextWatcher);
                }
            }
        }

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Logger.debug("vanhttp", "this is otp: " + str_verifynumber);

                mobileno = edt_verifynumber.getText().toString().trim();

                if(!mobileno.equals("")){
                    if(mobileno.length() == 11 && mobileno.substring(0,2).equals("09")){
                        dialog.dismiss();
                        passmobileno = "63" + mobileno.substring(1);
                        preSimActivation();

                    } else{
                        showToast("Invalid Mobile Number.", GlobalToastEnum.WARNING);
                    }
                } else {
                    showToast("Invalid Mobile Number.", GlobalToastEnum.WARNING);
                }
            }
        });
    }

    private void preSimActivation() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            showProgressDialog("Checking request.", "Please wait...");

            preSimCSBActivation(preSimCSBActivationSession);
        } else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void preSimCSBActivation (Callback<GenericResponse> preSimCSBActivationCallback) {
        Call<GenericResponse> presimcsbactivation = RetrofitBuild.getRewardsAPIService(getViewContext())
                .preSimCSBActivationCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        passmobileno,
                        "ANDROID");

        presimcsbactivation.enqueue(preSimCSBActivationCallback);
    }

    private final Callback<GenericResponse> preSimCSBActivationSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    hideProgressDialog();

                    authid = response.body().getData();
                    verifyOTP();

                } else{
                    hideProgressDialog();
                    showToast(response.body().getMessage(), GlobalToastEnum.WARNING);
                }
            } else {
                hideProgressDialog();
                showError(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {

        }
    };

    private void getSession() {

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            showProgressDialog("Activating Sim Card.", "Please wait...");

            activateCSBSimCard(activateCSBSimCardCallback);

        } else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void activateCSBSimCard (Callback<GenericResponse> activateCSBSimCardCallback) {

        Call<GenericResponse> activatecsbsimcard = RetrofitBuild.getRewardsAPIService(getViewContext())
                .activateCSBSimCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        passmobileno,
                        authid,
                        str_verifyotp,
                        "ANDROID");

        activatecsbsimcard.enqueue(activateCSBSimCardCallback);
    }

    private final Callback<GenericResponse> activateCSBSimCardCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){
                    hideProgressDialog();
                    dialog.dismiss();

                    dialog.createDialog("SUCCESS", response.body().getMessage(),
                            "Close", "", ButtonTypeEnum.SINGLE,
                            false, false, DialogTypeEnum.SUCCESS);

                    dialog.showContentTitle();

                    View closebtn = dialog.btnCloseImage();
                    closebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setUpProgressLoaderDismissDialog();
                            dialog.dismiss();
                        }
                    });

                    View singlebtn = dialog.btnSingle();
                    singlebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setUpProgressLoaderDismissDialog();
                            dialog.dismiss();
                        }
                    });

                } else{
                    hideProgressDialog();
                    showToast(response.body().getMessage(), GlobalToastEnum.WARNING);
                }
            } else {
                hideProgressDialog();
                showError(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private TextWatcher verifyMobileTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            str_verifynumber = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() == 11){
                String num = edt_verifynumber.getText().toString().trim().substring(0,2);
                if(num.equals("09")){
                    //do nothing
                    Logger.debug("vanhttp", "I'M HERE.");
                } else{
                    showToast("Invalid Mobile Number", GlobalToastEnum.WARNING);
                }
            }
        }
    };

    private TextWatcher verifyOTPTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            str_verifyotp = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean isShow = false;
}
