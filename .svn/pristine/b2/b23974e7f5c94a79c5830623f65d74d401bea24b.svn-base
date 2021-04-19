package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantPaymentOptionsActivity;
import com.goodkredit.myapplication.activities.schoolmini.SchoolMiniBillingReferenceActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantBills;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantSOABillsResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.NumberTextWatcherForThousand;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantBillDetailsFragment extends BaseFragment implements View.OnClickListener {

    private CoopAssistantBills coopbills = null;

    private String imei;
    private String userid;
    private String borrowerid;
    private String servicecode;
    private String sessionid;


    private Button btn_paynow;
    private TextView txv_soaid;
    private TextView txv_accountname;
    private TextView txv_coop_statement;
    private TextView txv_duedate;
    private TextView txv_billamount;
    private TextView txv_coop_asof;
    private TextView txv_coop_particulars;
    private LinearLayout amount_container;
    private EditText edt_inputtedamount;

    //PENDING REQUEST
    private TextView txv_pending_content;
    private RelativeLayout layout_pending_req_via_payment_channel;
    private LinearLayout layout_pending_paynow;
    private LinearLayout layout_pending_cancelrequest;
    private TextView btn_pending_paynow;
    private TextView btn_pending_cancelrequest;
    private ImageView btn_pending_close;
    private TextView txv_coop_viewpdf;

    private List<CoopAssistantBills> cooplistvalidation = new ArrayList<>();

    //PENDING CANCELLATION REMARKS
    private MaterialDialog mDialog;
    private EditText edt_remarks;
    private String remarks = "";
    private String billingid = "";
    private String coopname = "";
    private String billingamount = "";

    //CANCEL DIALOG
    private EditText dialogEdt;
    private String dialogString = "";

    //MEMBER LIST
    private List<CoopAssistantMembers> coopMembersList = new ArrayList<>();
    private boolean memberStatus = false;

    //NEW VARIABLES FOR SECURITY
    private String cancelIndex;
    private String cancelAuth;
    private String cancelKey;
    private String cancelParam;

    //for validate payment request
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_bills_details, container, false);

        init(view);
        initData();
        return view;
    }

    private void init(View view) {
        btn_paynow = view.findViewById(R.id.btn_coop_soa_paynow);
        txv_soaid = view.findViewById(R.id.txv_coop_soaid);
        txv_accountname = view.findViewById(R.id.txv_coop_accountname);
        txv_coop_statement = view.findViewById(R.id.txv_coop_statement);
        txv_duedate = view.findViewById(R.id.txv_coop_duedate);
        txv_billamount = view.findViewById(R.id.txv_coop_billamount);
        txv_coop_asof = view.findViewById(R.id.txv_coop_asof);
        txv_coop_particulars = view.findViewById(R.id.txv_coop_particulars);
        txv_coop_viewpdf = (TextView)view.findViewById(R.id.txv_coop_viewpdf);
        amount_container = view.findViewById(R.id.amount_container);
        edt_inputtedamount = view.findViewById(R.id.edt_coop_inputtedamount);
        edt_inputtedamount.addTextChangedListener(new NumberTextWatcherForThousand(edt_inputtedamount));

        //PENDING REQUEST
        txv_pending_content = (TextView) view.findViewById(R.id.txv_content);
        layout_pending_req_via_payment_channel = (RelativeLayout) view.findViewById(R.id.layout_req_via_payment_channel);
        layout_pending_paynow = (LinearLayout) view.findViewById(R.id.layout_payment_channel_paynow);
        layout_pending_cancelrequest = (LinearLayout) view.findViewById(R.id.layout_payment_channel_cancel);
        btn_pending_cancelrequest = (TextView) view.findViewById(R.id.btn_cancel_request);
        btn_pending_paynow = (TextView) view.findViewById(R.id.btn_paynow_request);
        btn_pending_close = (ImageView) view.findViewById(R.id.btn_close);
        btn_pending_paynow.setOnClickListener(this);
        btn_pending_close.setOnClickListener(this);
        btn_pending_cancelrequest.setOnClickListener(this);
        txv_coop_viewpdf.setOnClickListener(this);
        btn_paynow.setOnClickListener(this);
    }

    private void initData() {
        try{

            imei = PreferenceUtils.getImeiId(getViewContext());
            userid = PreferenceUtils.getUserId(getViewContext());
            borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
            sessionid = PreferenceUtils.getSessionID(getViewContext());
            servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

            coopbills = getArguments().getParcelable(CoopAssistantBills.KEY_COOPBILLS);

            coopMembersList = PreferenceUtils.getCoopMembersListPreference(mContext, CoopAssistantMembers.KEY_COOPMEMBERINFORMATION);

            txv_soaid.setText(CommonFunctions.replaceEscapeData(coopbills.getSOAID()));
            txv_accountname.setText(CommonFunctions.replaceEscapeData(coopbills.getAccountName()));
            txv_coop_statement.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(coopbills.getStatementDate())));
            txv_duedate.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(coopbills.getDueDate())));
            txv_billamount.setText(CommonFunctions.currencyFormatter(String.valueOf(coopbills.getBillAmount())));
            txv_coop_particulars.setText(CommonFunctions.replaceEscapeData(coopbills.getParticulars()));
            txv_coop_asof.setText("As of ".concat(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(coopbills.getDateTimeIN()))));

            checkMemberStatus();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkMemberStatus() {
        for(CoopAssistantMembers coopAssistantMembers : coopMembersList) {
            String status = coopAssistantMembers.getStatus();

            memberStatus = status.equals("ACTIVE");
        }

        displayEnterAmount();
    }

    private void displayEnterAmount() {
        if(memberStatus) {
            amount_container.setVisibility(View.VISIBLE);
            btn_paynow.setVisibility(View.VISIBLE);
        } else {
            amount_container.setVisibility(View.GONE);
            btn_paynow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_coop_soa_paynow: {
                if (!SClick.check(SClick.BUTTON_CLICK,2000)) return;

                //validateIfHasPendingPaymentRequest();

                if(edt_inputtedamount.getText().toString().isEmpty()){
                    edt_inputtedamount.setError("Invalid amount.");
                    edt_inputtedamount.requestFocus();
                }else{
                    showProgressDialog("","Validating request... Please wait...");
                    validateIfHasPendingPaymentRequestV2();
                }
                break;
            }

            case R.id.btn_close: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;

                layout_pending_req_via_payment_channel.setVisibility(View.GONE);
                break;
            }

            case R.id.btn_cancel_request: {

                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                cancelDialog();

                break;
            }
            case R.id.btn_paynow_request: {

                PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME, coopname);
                SchoolMiniBillingReferenceActivity.start(getViewContext(), new SchoolMiniPayment(".", billingid, Double.valueOf(billingamount)),
                        billingamount, "");

                break;
            }
            case R.id.txv_coop_viewpdf : {
                if (coopbills.getNotes1().isEmpty() || coopbills.getNotes1().equals(null) ||
                  coopbills.getNotes1().equals(".")) {
                    showErrorToast("No PDF Available.");
                    return;
                }else{
                    viewPdf(coopbills.getNotes1());
                }
                break;
            }
        }
    }

    private void viewPdf(String paramString) {
        Log.d("okhttp",paramString);
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(paramString));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(i);
        } catch (ActivityNotFoundException ex) {
            i.setPackage(null);
            startActivity(Intent.createChooser(i, "Select Browser"));
        }
    }


    private void validateIfHasPendingPaymentRequest() {

        Call<CoopAssistantSOABillsResponse> validateIfHasPendingPaymentRequest = RetrofitBuild.getCoopAssistantAPI(mContext)
                .validateIfHasPendingPaymentRequest(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        servicecode,
                        "BILL",
                        CommonVariables.devicetype
                );

        validateIfHasPendingPaymentRequest.enqueue(validateIfHasPendingPaymentRequestCallBack);
    }

    private final Callback<CoopAssistantSOABillsResponse> validateIfHasPendingPaymentRequestCallBack = new Callback<CoopAssistantSOABillsResponse>() {
        @Override
        public void onResponse(Call<CoopAssistantSOABillsResponse> call, Response<CoopAssistantSOABillsResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {

                        cooplistvalidation = response.body().getCoopAssistantSOABillsList();

                        if(cooplistvalidation != null){

                            if(cooplistvalidation.isEmpty()){
                                layout_pending_req_via_payment_channel.setVisibility(View.GONE);

                                try{

                                    String input = NumberTextWatcherForThousand.trimCommaOfString(edt_inputtedamount.getText().toString().trim());

                                    if(input.equals("")){
                                        showToast("Please input a valid amount.", GlobalToastEnum.WARNING);
                                    } else{
                                        Bundle args = new Bundle();

                                        args.putString("FROM", "LoanBillsPayment");
                                        args.putString(CoopAssistantBills.KEY_COOP_BILLS_INPUTTED_AMOUNT, input);
                                        args.putParcelable(CoopAssistantBills.KEY_COOPBILLS, coopbills);

                                        CoopAssistantPaymentOptionsActivity.start(getViewContext(), args);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            } else{

                                for(CoopAssistantBills requestinfo : cooplistvalidation){
                                    billingid = requestinfo.getBillingID();
                                    billingamount = String.valueOf(requestinfo.getTotalAmountToPay());
                                }

                                if(billingid.substring(0,3).equals("771") || billingid.substring(0,3).equals("991")
                                        || billingid.substring(0,3).equals("551")){
                                    layout_pending_paynow.setVisibility(View.GONE);
                                    txv_pending_content.setText("You have a pending payment transaction. If you think you completed " +
                                            "this transaction, please contact support,if not just cancel the request.");

                                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                                    );
                                    layout_pending_cancelrequest.setLayoutParams(param);

                                } else{

                                    layout_pending_paynow.setVisibility(View.VISIBLE);
                                    txv_pending_content.setText("You have a pending payment request, kindly settle it first or cancel " +
                                            "your previous request.");

                                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f
                                    );
                                    layout_pending_cancelrequest.setLayoutParams(param);
                                }

                                layout_pending_req_via_payment_channel.setVisibility(View.VISIBLE);
                            }
                        }

                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                showErrorGlobalDialogs();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<CoopAssistantSOABillsResponse> call, Throwable t) {
            showErrorGlobalDialogs();
        }
    };

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
                dialog.dismiss();

                if (!dialogString.trim().equals("")) {
                    remarks = dialogString;
                } else {
                    remarks = ".";
                }

                cancelRequest();

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

    private void cancelRequest(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            showProgressDialog("", "Please wait...");
            //cancelCoopPaymentChannelRequest();
            cancelRequestPaymentsV2();
        } else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }

//    private void cancelCoopPaymentChannelRequest() {
//        Call<GenericResponse> cancelpayment = RetrofitBuild.getCoopAssistantAPI(getViewContext())
//                .cancelCoopPaymentChannelRequestCall(
//                        sessionid,
//                        imei,
//                        userid,
//                        borrowerid,
//                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
//                        servicecode,
//                        "BILL",
//                        billingid,
//                        remarks,
//                        CommonVariables.devicetype
//                );
//
//        cancelpayment.enqueue(cancelCoopPaymentChannelRequestSession);
//    }
//    private final Callback<GenericResponse> cancelCoopPaymentChannelRequestSession = new Callback<GenericResponse>() {
//        @Override
//        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
//            try {
//                hideProgressDialog();
//                ResponseBody errorBody = response.errorBody();
//                if (errorBody == null) {
//                    if (response.body().getStatus().equals("000")) {
//
//                        layout_pending_req_via_payment_channel.setVisibility(View.GONE);
//                        showCancellationSuccessfulDialog();
//
//                    } else if (response.body().getStatus().equals("104")) {
//
//                        showAutoLogoutDialog(response.body().getMessage());
//
//                    } else {
//                        showErrorGlobalDialogs(response.body().getMessage());
//                    }
//                } else {
//                    showErrorGlobalDialogs();
//                }
//            } catch (Exception e) {
//                hideProgressDialog();
//                showErrorGlobalDialogs();
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            showErrorGlobalDialogs();
//            hideProgressDialog();
//        }
//    };

    private void showCancellationSuccessfulDialog() {
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
            }
        });
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * **/

    private void cancelRequestPaymentsV2(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("paymenttype", "BILL");
        parameters.put("txnno", billingid);
        parameters.put("remarks", remarks);
        parameters.put("devicetype", CommonVariables.devicetype);


        //depends on the authentication type 
        LinkedHashMap indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        cancelIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", cancelIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API 
        cancelAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        cancelKey = CommonFunctions.getSha1Hex(cancelAuth + sessionid + "cancelRequestPaymentsV2");
        cancelParam = CommonFunctions.encryptAES256CBC(cancelKey, String.valueOf(paramJson));

        cancelRequestPaymentsV2Object(cancelRequestPaymentsV2Callback);


    }
    private void cancelRequestPaymentsV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .cancelRequestPaymentsV2(cancelAuth,sessionid,cancelParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  cancelRequestPaymentsV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(cancelKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    layout_pending_req_via_payment_channel.setVisibility(View.GONE);
                    showCancellationSuccessfulDialog();

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
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


    //Validate IF HAS PENDING PAYMENT REQUEST
    private void validateIfHasPendingPaymentRequestV2(){

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            //Please refer to corresponding parameters
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("servicecode", servicecode);
            parameters.put("paymenttype", "BILL");
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
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "validateIfHasPendingPaymentRequestV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            validateIfHasPendingPaymentRequestV2Object(validateIfHasPendingPaymentRequestV2Callback);
        }else{
            hideProgressDialog();
            showNoInternetToast();
        }

    }
    private void validateIfHasPendingPaymentRequestV2Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .validateIfHasPendingPaymentRequestV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  validateIfHasPendingPaymentRequestV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            hideProgressDialog();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    cooplistvalidation = new Gson().fromJson(data,new TypeToken<List<CoopAssistantBills>>(){}.getType());

                    if(cooplistvalidation != null){

                        if(cooplistvalidation.size() <= 0){
                            layout_pending_req_via_payment_channel.setVisibility(View.GONE);

                            try{

                                String input = NumberTextWatcherForThousand.trimCommaOfString(edt_inputtedamount.getText().toString().trim());

                                if(input.equals("")){
                                    showToast("Please input a valid amount.", GlobalToastEnum.WARNING);
                                } else{
                                    Bundle args = new Bundle();

                                    args.putString("FROM", "LoanBillsPayment");
                                    args.putString(CoopAssistantBills.KEY_COOP_BILLS_INPUTTED_AMOUNT, input);
                                    args.putParcelable(CoopAssistantBills.KEY_COOPBILLS, coopbills);

                                    CoopAssistantPaymentOptionsActivity.start(getViewContext(), args);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        } else{

                            for(CoopAssistantBills requestinfo : cooplistvalidation){
                                billingid = requestinfo.getBillingID();
                                billingamount = String.valueOf(requestinfo.getTotalAmountToPay());
                            }

                            if(billingid.substring(0,3).equals("771") || billingid.substring(0,3).equals("991")
                                    || billingid.substring(0,3).equals("551")){
                                layout_pending_paynow.setVisibility(View.GONE);
                                txv_pending_content.setText("You have a pending payment transaction. If you think you completed " +
                                        "this transaction, please contact support,if not just cancel the request.");

                                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                                );
                                layout_pending_cancelrequest.setLayoutParams(param);

                            } else{

                                layout_pending_paynow.setVisibility(View.VISIBLE);
                                txv_pending_content.setText("You have a pending payment request, kindly settle it first or cancel " +
                                        "your previous request.");

                                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f
                                );
                                layout_pending_cancelrequest.setLayoutParams(param);
                            }

                            layout_pending_req_via_payment_channel.setVisibility(View.VISIBLE);
                        }
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
            t.printStackTrace();
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };



}
