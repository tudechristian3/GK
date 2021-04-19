package com.goodkredit.myapplication.fragments.rewards;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.csbrewards.CSBSettingsChangeMobileConfimationActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.responses.GKProcessRequestResponse;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CSBChangeMobileFragment extends BaseFragment implements View.OnClickListener {

    private MaterialDialog mDialog = null;
    private TextView tv_new_mob_num;
    private String str_new_mob_num;
    private DatabaseHandler db;

    private String borrowerName;
    private String borrowerEmail;

    private LinearLayout layoutNoRequest;
    private RelativeLayout layoutRequested;

    private String sessionid = "";

    public static CSBChangeMobileFragment newInstance() {
        CSBChangeMobileFragment fragment = new CSBChangeMobileFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_csb_settings_change_mobile, container, false);
        view.findViewById(R.id.btn_csb_settings_update).setOnClickListener(this);
        tv_new_mob_num = view.findViewById(R.id.tv_new_mob_number);

        layoutNoRequest = view.findViewById(R.id.layout_not_requested);
        layoutRequested = view.findViewById(R.id.layout_requested);

        db = new DatabaseHandler(getViewContext());

        sessionid = PreferenceUtils.getSessionID(getViewContext());

        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));
                borrowerName = cursor.getString(cursor.getColumnIndex("firstname")) + " " + cursor.getString(cursor.getColumnIndex("lastname"));
                borrowerEmail = cursor.getString(cursor.getColumnIndex("email"));
            } while (cursor.moveToNext());
        }

        checkCSBChangeMobileRequest();

        return view;

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_csb_settings_update) {
            str_new_mob_num = tv_new_mob_num.getText().toString();
            if (isMobileNumberValid(str_new_mob_num))
                showMobileNumberVerificationDialog(str_new_mob_num);
            else
                showMobileNumberInvalidDialog();
        }
    }


    private boolean isMobileNumberValid(String str) {
        boolean bool = false;
        if (!str.isEmpty()) {
            if (str.length() == 10) {
                if (String.valueOf(str.charAt(0)).equals("9")) {
                    setNumber(str);
                    bool = true;
                }
            } else if (str.length() == 11) {
                if (str.substring(0, 2).equals("09")) {
                    setNumber(str);
                    bool = true;
                }
            } else if (str.length() == 12) {
                if (str.substring(0, 3).equals("639")) {
                    setNumber(str);
                    bool = true;
                }
            } else if (str.length() < 10) {
                // showMobileNumberInvalidDialog();
                bool = false;
            }
        } else if (str.isEmpty()) {
            bool = false;
        }

        return bool;
    }

    private void setNumber(String number) {
        tv_new_mob_num.setText(CommonFunctions.userMobileNumber(number,true));
    }

    private void showMobileNumberVerificationDialog(String mobileNumber) {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .customView(R.layout.dialog_csb_settings_change_mobile, true)
                .cancelable(false)
                .negativeText("EDIT")
                .positiveText("OK")
                .negativeColor(ContextCompat.getColor(getViewContext(), R.color.color_csb_purple))
                .positiveColor(ContextCompat.getColor(getViewContext(), R.color.color_csb_purple))
                .show();

        View negative = mDialog.getActionButton(DialogAction.NEGATIVE);
        View positive = mDialog.getActionButton(DialogAction.POSITIVE);
        View view = mDialog.getCustomView();


        TextView tvNewMobileNo = view.findViewById(R.id.tv_new_mobile_number);
        tvNewMobileNo.setText("+" + CommonFunctions.userMobileNumber(mobileNumber,true));


        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                mDialog = null;
            }
        });

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                mDialog = null;
                getSession();
//
            }
        });
    }

    private void showMobileNumberInvalidDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("Mobile number is invalid. Please check.")
                .positiveText("OK")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        tv_new_mob_num.setText("639");
                    }
                })
                .show();
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            createSession(getSessionCallback);
//            showProgressDialog("Verifying mobile.", "Please wait...");
//        } else {
//            showError("You are not connected to the internet.");
//        }
//    }
//
//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            try {
//                ResponseBody errBody = response.errorBody();
//                if (errBody == null) {
//                    if (!response.body().isEmpty()
//                            && !response.body().contains("<!DOCTYPE html>")
//                            && !response.body().equals("001")
//                            && !response.body().equals("002")
//                            && !response.body().equals("003")
//                            && !response.body().equals("004")
//                            && !response.body().equals("error")) {
//                        sessionid = response.body();
//                        requestMobileChange();
//                    } else {
//                        hideProgressDialog();
//                        showError();
//                    }
//                } else {
//                    hideProgressDialog();
//                    showError();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Verifying mobile.", "Please wait...");
            requestMobileChange();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void requestMobileChange() {
        Call<GenericResponse> call = RetrofitBuild.getRewardsAPIService(getViewContext())
                .requestCSBChangeMobile(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        sessionid,
                        borrowerid,
                        str_new_mob_num,
                        borrowerName,
                        borrowerEmail
                );

        call.enqueue(requestMobileChangeCallback);
    }

    private Callback<GenericResponse> requestMobileChangeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        CSBSettingsChangeMobileConfimationActivity.start(getViewContext(), false, str_new_mob_num, "CHANGE MOBILE NUMBER");
                    } else if (response.body().getStatus().equals("008")) {
                        CSBSettingsChangeMobileConfimationActivity.start(getViewContext(), true, str_new_mob_num, "CHANGE MOBILE NUMBER");
                    } else {
                        showError(response.body().getMessage());
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showError();
        }
    };

    private void checkCSBChangeMobileRequest() {
        showProgressDialog("", "Please wait...");
        Call<GKProcessRequestResponse> call = RetrofitBuild.getRewardsAPIService(getViewContext())
                .checkCSBChangeMobileRequest(
                        imei,
                        userid,
                        "CHANGE MOBILE NUMBER",
                        borrowerid
                );

        call.enqueue(checkCSBChangeMobileRequestCallback);
    }

    private Callback<GKProcessRequestResponse> checkCSBChangeMobileRequestCallback = new Callback<GKProcessRequestResponse>() {
        @Override
        public void onResponse(Call<GKProcessRequestResponse> call, Response<GKProcessRequestResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        layoutNoRequest.setVisibility(View.GONE);
                        layoutRequested.setVisibility(View.VISIBLE);
                    } else {
                        layoutNoRequest.setVisibility(View.VISIBLE);
                        layoutRequested.setVisibility(View.GONE);
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GKProcessRequestResponse> call, Throwable t) {
            hideProgressDialog();
            getActivity().onBackPressed();
        }
    };

}
