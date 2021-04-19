package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantLoanTransactions;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantLoanTransactionDetailsFragment extends BaseFragment implements View.OnClickListener {

    private CoopAssistantLoanTransactions loantransactions;

    private LinearLayout layout_coop_datetimeapproved;
    private LinearLayout layout_coop_remarks;
    private TextView txv_coop_loantype;
    private TextView txv_coop_requestid;
    private TextView txv_coop_datetimerequested;
    private TextView txv_coop_datetimeapproved;
    private TextView txv_coop_memberid;
    private TextView txv_coop_membername;
    private TextView txv_coop_loanamount;
    private TextView txv_coop_actedby;
    private TextView txv_coop_loanstatus;
    private TextView txv_coop_remarks;
    private LinearLayout layout_otherdetails;

    private LinearLayout layout_cancel;

    private String imei = "";
    private String sessionid = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";
    private String servicecode = "";
    private String requestid = "";
    private String remarks = "";
    private String str_otherdetails = "";

    //CANCEL DIALOG
    private EditText dialogEdt;
    private String dialogString = "";

    //NEW VARIABLE FOR SECURITY
    private String authenticationid;
    private String keyEncryption;
    private String index;
    private String param;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_loan_transaction_details, container, false);

        init(view);
        initdata();

        return view;
    }

    private void init(View view) {
        layout_coop_datetimeapproved = view.findViewById(R.id.layout_coop_datetimeapproved);
        layout_coop_remarks = view.findViewById(R.id.layout_coop_remarks);
        txv_coop_loantype = view.findViewById(R.id.txv_coop_loantype);
        txv_coop_requestid = view.findViewById(R.id.txv_coop_requestid);
        txv_coop_datetimerequested = view.findViewById(R.id.txv_coop_datetimerequested);
        txv_coop_datetimeapproved = view.findViewById(R.id.txv_coop_datetimeapproved);
        txv_coop_memberid = view.findViewById(R.id.txv_coop_memberid);
        txv_coop_membername = view.findViewById(R.id.txv_coop_membername);
        txv_coop_loanamount = view.findViewById(R.id.txv_coop_loanamount);
        txv_coop_actedby = view.findViewById(R.id.txv_coop_actedby);
        txv_coop_loanstatus = view.findViewById(R.id.txv_coop_loanstatus);
        txv_coop_remarks = view.findViewById(R.id.txv_coop_remarks);
        layout_otherdetails = view.findViewById(R.id.layout_otherdetails);

        layout_cancel = view.findViewById(R.id.layout_cancel);
        layout_cancel.setOnClickListener(this);
    }

    private void initdata() {
        try {
            sessionid = PreferenceUtils.getSessionID(getViewContext());
            imei = PreferenceUtils.getImeiId(getViewContext());
            userid = PreferenceUtils.getUserId(getViewContext());
            borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

            loantransactions = getArguments().getParcelable(CoopAssistantLoanTransactions.KEY_COOP_LOANTRANSACTIONS);

            if(loantransactions.getRequestStatus().equals("APPROVED")){
                layout_coop_datetimeapproved.setVisibility(View.VISIBLE);
                txv_coop_loanstatus.setTextColor(ContextCompat.getColor(mContext, R.color.coopassist_green));
                layout_cancel.setVisibility(View.GONE);

                if(loantransactions.getRemarks() != null) {
                    if(loantransactions.getRemarks().isEmpty() || loantransactions.getRemarks().equals(".")){
                        layout_coop_remarks.setVisibility(View.GONE);
                    } else {
                        layout_coop_remarks.setVisibility(View.VISIBLE);
                    }
                } else {
                    layout_coop_remarks.setVisibility(View.GONE);
                }

            } else if(loantransactions.getRequestStatus().equals("DECLINED")
                    || loantransactions.getRequestStatus().equals("CANCELLED")){
                layout_coop_datetimeapproved.setVisibility(View.GONE);
                txv_coop_loanstatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_error_red));
                layout_cancel.setVisibility(View.GONE);

                if(loantransactions.getRemarks() != null) {
                    if(loantransactions.getRemarks().isEmpty() || loantransactions.getRemarks().equals(".")){
                        layout_coop_remarks.setVisibility(View.GONE);
                    } else {
                        layout_coop_remarks.setVisibility(View.VISIBLE);
                    }
                } else {
                    layout_coop_remarks.setVisibility(View.GONE);
                }
            } else if(loantransactions.getRequestStatus().equals("PENDING")) {
                layout_cancel.setVisibility(View.VISIBLE);
            } else {
                layout_coop_datetimeapproved.setVisibility(View.GONE);
                layout_cancel.setVisibility(View.GONE);
            }
            requestid = CommonFunctions.replaceEscapeData(loantransactions.getRequestID());

            txv_coop_loantype.setText(CommonFunctions.replaceEscapeData(loantransactions.getLoanName()));
            txv_coop_requestid.setText(CommonFunctions.replaceEscapeData(loantransactions.getRequestID()));
            txv_coop_datetimerequested.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(loantransactions.getDateTimeRequested())));
            txv_coop_datetimeapproved.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(loantransactions.getDateTimeApproved())));
            txv_coop_memberid.setText(CommonFunctions.replaceEscapeData(loantransactions.getMemberID()));
            txv_coop_membername.setText(CommonFunctions.replaceEscapeData(loantransactions.getMemberName()));
            txv_coop_loanamount.setText(CommonFunctions.currencyFormatter(loantransactions.getLoanAmount()));
            txv_coop_actedby.setText(CommonFunctions.replaceEscapeData(loantransactions.getActedBy()));
            txv_coop_loanstatus.setText(CommonFunctions.replaceEscapeData(loantransactions.getRequestStatus()));
            txv_coop_remarks.setText(CommonFunctions.replaceEscapeData(loantransactions.getRemarks()));

            //OTHERDETAILS
            str_otherdetails = loantransactions.getRequestXMLDetails();
            addXMLDetails();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addXMLDetails() {
        try {
            if(str_otherdetails != null) {
                if (!str_otherdetails.trim().equals("") && !str_otherdetails.trim().equals("NONE") && !str_otherdetails.equals(".")) {

                    layout_otherdetails.setVisibility(View.VISIBLE);
                    //String kycinfo = CommonFunctions.parseXML(str_otherdetails, "kycotherinfo");
                    String count = CommonFunctions.parseXML(str_otherdetails, "count");

                    if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {
                        for (int i = 1; i <= Integer.parseInt(count); i++) {
                            String field = CommonFunctions.parseXML(str_otherdetails, String.valueOf(i));
                            String[] fieldarr = field.split(":::");

                            try {
                                if (!field.equals("NONE") && !field.equals("")) {
                                    if (fieldarr.length > 0) {
                                        String keyname = fieldarr[0];
                                        String keyvalue = fieldarr[1];

                                        JSONObject obj = new JSONObject();
                                        obj.put("keyname", keyname);
                                        obj.put("keyvalue", keyvalue);

                                        if (keyvalue.contains("http")) {
                                            createPhotoLabel(keyname, keyvalue);
                                        } else {
                                            createDoubleTextView(keyname, keyvalue);
                                        }

                                    }
                                }
                            } catch (Error e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    layout_otherdetails.setVisibility(View.GONE);
                }
            } else {
                layout_otherdetails.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createDoubleTextView(String keyname, String keyvalue) {

        try {
            if (!keyname.trim().equals("") && !keyvalue.trim().equals("")) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );

                layoutParams.setMargins(0, 5, 0, 5);

                LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                0.5f
                        );


                LinearLayout containerLayout = new LinearLayout(getViewContext());
                containerLayout.setOrientation(LinearLayout.HORIZONTAL);

                TextView txv_name = new TextView(getViewContext());
                txv_name.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_BOLD));
                txv_name.setPadding(5,5,5,5);
                txv_name.setText(keyname);
                txv_name.setLayoutParams(containerParams);
                containerLayout.addView(txv_name, containerParams);

                TextView txv_value = new TextView(getViewContext());
                txv_value.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
                txv_value.setGravity(Gravity.END);
                txv_value.setPadding(5,5,5,5);
                txv_value.setText(keyvalue);
                txv_value.setLayoutParams(containerParams);
                containerLayout.addView(txv_value, containerParams);

                layout_otherdetails.addView(containerLayout, layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPhotoLabel(String keyname, String keyvalue) {

        try {
            if (!keyname.trim().equals("") && !keyvalue.trim().equals("")) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );

                layoutParams.setMargins(0, 5, 0, 5);

                LinearLayout containerLayout = new LinearLayout(getViewContext());
                containerLayout.setOrientation(LinearLayout.HORIZONTAL);

                TextView txv_name = new TextView(getViewContext());
                txv_name.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_BOLD));
                txv_name.setPadding(5,5,5,5);
                txv_name.setGravity(Gravity.CENTER_VERTICAL);
                txv_name.setText(keyname);
                containerLayout.addView(txv_name, new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                0.5f
                        ));

                //IMAGEVIEW
                LinearLayout imageLayoutContainer = new LinearLayout(getViewContext());
                imageLayoutContainer.setBackgroundResource(R.drawable.border);
                imageLayoutContainer.setOrientation(LinearLayout.VERTICAL);

                ImageView imv_value = new ImageView(getViewContext());
                imv_value.setPadding(10, 10, 10, 10);


                Glide.with(mContext)
                        .load(keyvalue)
                        .apply(new RequestOptions()
                                .fitCenter()
                                .placeholder(R.drawable.generic_placeholder_gk_background)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        )
                        .into(imv_value);

                imageLayoutContainer.addView(imv_value, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        CommonFunctions.converttoDP(getViewContext(), 90)));

                containerLayout.addView(imageLayoutContainer,  new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                0.5f
                        ));

                layout_otherdetails.addView(containerLayout, layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_cancel) {
            if (!SClick.check(SClick.BUTTON_CLICK)) return;
            cancelDialog();
        }
    }

    //CANCELLATION
    @SuppressLint("ClickableViewAccessibility")
    private void cancelDialog() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "",
                "Cancel", "OK", ButtonTypeEnum.DOUBLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //TEXTVIEW
        LinearLayout textViewMainContainer = dialog.getTextViewMessageContainer();
        textViewMainContainer.setPadding(15, 15, 15, 0);

        List<String> declineList = new ArrayList<>();
        declineList.add("Reason for Cancellation:");

        LinearLayout textViewContainer = dialog.setContentTextView(declineList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.CENTER));

        //EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(15, 0, 15, 15);

        List<String> editTextDataType = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.CUSTOMVARCHAR));

        LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.CENTER,
                        R.drawable.border, 300, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;

                editItem.setScroller(new Scroller(getViewContext()));
                editItem.setSingleLine(false);
                editItem.setLines(5);
                editItem.setMinLines(3);
                editItem.setMaxLines(5);
                editItem.setVerticalScrollBarEnabled(true);
                editItem.setMovementMethod(new ScrollingMovementMethod());

                editItem.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        return false;
                    }
                });

                editItem.setBackgroundResource(R.drawable.border);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    dialogEdt = editItem;
                    dialogEdt.addTextChangedListener(dialogTextWatcher);
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

        View dblbtnone = dialog.btnDoubleOne();
        dblbtnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View dblbtntwo = dialog.btnDoubleTwo();
        dblbtntwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!dialogString.trim().equals("")) {
                    dialog.dismiss();
                    remarks = dialogString;
                    getSession();
                } else {
                    showToast("Reason for cancellation is required.", GlobalToastEnum.WARNING);
                }
            }
        });
    }

    //OTHERS
    private TextWatcher dialogTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            dialogString = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void getSession(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            //cancelLoanRequest();
            cancelCoopLoanRequestV2();
        } else{
            showNoInternetToast();
        }
    }

    private void cancelLoanRequest() {
        Call<GenericResponse> cancelpayment = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .cancelCoopLoanRequestCall(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        servicecode,
                        requestid,
                        remarks,
                        CommonVariables.devicetype
                );

        cancelpayment.enqueue(cancelCoopPaymentChannelRequestSession);
    }

    private final Callback<GenericResponse> cancelCoopPaymentChannelRequestSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {

                        showCancellationSuccessfulDialog();

                    } else if (response.body().getStatus().equals("104")) {

                        showAutoLogoutDialog(response.body().getMessage());

                    } else {
                        showCancellationErrorDialog(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                hideProgressDialog();
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            hideProgressDialog();
        }
    };

    void showCancellationSuccessfulDialog() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "Your request has been successfully cancelled.",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.SUCCESS);

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getActivity().onBackPressed();
            }
        });
    }

    private void showCancellationErrorDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", message,
                "OK", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                PreferenceUtils.saveBooleanPreference(getViewContext(), "cooploantransactiondetails", true);
                getActivity().onBackPressed();
            }
        });
    }

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    private void cancelCoopLoanRequestV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("requestid", requestid);
        parameters.put("remarks", remarks);
        parameters.put("devicetype", CommonVariables.devicetype);


        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "cancelCoopLoanRequestV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        cancelCoopLoanRequestV2Object(cancelCoopLoanRequestV2Callback);


    }

    private void cancelCoopLoanRequestV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .cancelCoopLoanRequestV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  cancelCoopLoanRequestV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    showCancellationSuccessfulDialog();
                } else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            } else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };


}
