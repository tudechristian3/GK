package com.goodkredit.myapplication.fragments.coopassistant;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.adapter.coopassistant.member.CoopAssistantLoanApplicationAdapter;
import com.goodkredit.myapplication.adapter.coopassistant.nonmember.CoopAssistantLoanServicesAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantLoanType;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantLoanFragment extends BaseFragment implements View.OnClickListener {
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

    private TextView txv_header;

    //LOAN TYPE
    private List<CoopAssistantLoanType> coopLoanTypeList = new ArrayList<>();

    //MEMBER LIST
    private List<CoopAssistantMembers> coopMembersList = new ArrayList<>();

    //NON MEMBER
    private LinearLayout loan_services_container;
    private RecyclerView rv_loan_services;
    private CoopAssistantLoanServicesAdapter rv_loan_servicesadapter;

    //MEMBER
    private LinearLayout loan_application_container;
    private RecyclerView rv_loan_application;
    private CoopAssistantLoanApplicationAdapter rv_loan_applicationadater;

    //ACTION
    private LinearLayout btn_action_container;
    private TextView btn_action;

    private ArrayList<CoopAssistantLoanType> passedAssistantLoanList;
    private String loanid = "";

    private CardView layout_view_loan_transactions;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    //NEW VARIABLE FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_loan, container, false);
        try {
            init(view);
            initData();
            scrollonTop(view);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        home_body = view.findViewById(R.id.home_body);

        txv_header = view.findViewById(R.id.txv_header);

        //NON MEMBER
        loan_services_container = view.findViewById(R.id.loan_services_container);
        rv_loan_services = view.findViewById(R.id.rv_loan_services);

        //MEMBER
        loan_application_container = view.findViewById(R.id.loan_application_container);
        rv_loan_application = view.findViewById(R.id.rv_loan_application);

        //ACTION
        btn_action_container = view.findViewById(R.id.btn_action_container);
        btn_action = view.findViewById(R.id.btn_action);
        btn_action.setText("APPLY");
        btn_action.setOnClickListener(this);

        layout_view_loan_transactions = view.findViewById(R.id.layout_view_loan_transactions);
        layout_view_loan_transactions.setOnClickListener(this);

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
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        mdb = new DatabaseHandler(getViewContext());

        //NON MEMBER
        rv_loan_services.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_loan_services.setNestedScrollingEnabled(false);
        rv_loan_servicesadapter = new CoopAssistantLoanServicesAdapter(getViewContext());
        rv_loan_services.setAdapter(rv_loan_servicesadapter);

        //MEMBER
        rv_loan_application.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_loan_application.setNestedScrollingEnabled(false);
        rv_loan_applicationadater = new CoopAssistantLoanApplicationAdapter(getViewContext(), CoopAssistantLoanFragment.this);
        rv_loan_application.setAdapter(rv_loan_applicationadater);

        getLoanType();
    }

    //LOAN TYPE
    private void getLoanType() {
        coopMembersList = PreferenceUtils.getCoopMembersListPreference(mContext, CoopAssistantMembers.KEY_COOPMEMBERINFORMATION);
        coopLoanTypeList = PreferenceUtils.getCoopAssistantLoanTypeListPreference(getViewContext(),CoopAssistantLoanType.KEY_COOPLOANTYPE);

        if(coopMembersList.size() > 0) {
            getActivity().setTitle("Loan Application");
            layout_view_loan_transactions.setVisibility(View.VISIBLE);
            loan_application_container.setVisibility(View.VISIBLE);

            if(coopLoanTypeList.size() > 0) {
                txv_header.setVisibility(View.VISIBLE);
                txv_header.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "Select the Desired Loan: "));
                btn_action_container.setVisibility(View.VISIBLE);
                noresult.setVisibility(View.GONE);
                rv_loan_applicationadater.updateData(coopLoanTypeList);
            } else {
                txv_header.setVisibility(View.GONE);
                btn_action_container.setVisibility(View.GONE);
                noresult.setVisibility(View.VISIBLE);
                txv_noresult.setText("No Loans are currently available. Please try again later.");
            }
        } else {
            getActivity().setTitle("Loan Services");
            loan_services_container.setVisibility(View.VISIBLE);
            layout_view_loan_transactions.setVisibility(View.GONE);
            btn_action_container.setVisibility(View.GONE);

            if(coopLoanTypeList.size() > 0) {
                txv_header.setVisibility(View.VISIBLE);
                txv_header.setText(V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_BOLD, "Loan Services: "));
                noresult.setVisibility(View.GONE);
                rv_loan_servicesadapter.updateData(coopLoanTypeList);
            } else {
                txv_header.setVisibility(View.GONE);
                noresult.setVisibility(View.VISIBLE);
                txv_noresult.setText("No Loans are currently available. Please try again later.");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action: {
                if (!SClick.check(SClick.BUTTON_CLICK)) return;
                try{
                    for (CoopAssistantLoanType loanType : passedAssistantLoanList) {
                        loanid = loanType.getLoanID();
                    }
                    validateIfHasPendingLoanRequest();
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            }

            case R.id.layout_view_loan_transactions: {
                
//                Intent intent = new Intent(getViewContext(), CoopAssistantLoanTransactionsFragment.class);
//                startActivity(intent);

                CoopAssistantHomeActivity.start(getViewContext(), 14, null);
                break;
            }
        }
    }

    //CALLS DURING THE SELECTION
    public void selectCoopDetails(ArrayList<CoopAssistantLoanType> coopdata) {
        Collections.reverse(coopdata);
        passedAssistantLoanList = coopdata;
    }

    //SCROLLS ON TOP
    private void scrollonTop(View view) {
//        final NestedScrollView main = (NestedScrollView) findViewById(R.id.scrollmaincontainer);
        final NestedScrollView main = view.findViewById(R.id.home_body);
        main.post(new Runnable() {
                      public void run() {
                          main.smoothScrollTo(0, 0);
                      }
                  }
        );
    }

    private void validateIfHasPendingLoanRequest(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Validating request", "Please wait...");
            //validatePendingLoanRequest(validatePendingLoanRequestSession);
            validateIfHasPendingLoanRequestV2();
        } else{
            showErrorGlobalDialogs();
            hideProgressDialog();
        }
    }
    private void validatePendingLoanRequest (Callback<GenericResponse> validatePendingLoanRequestCallback ){

        Call<GenericResponse> validatepending = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .validateIfHasPendingLoanRequestCall(imei,
                        sessionid,
                        userid,
                        authcode,
                        borrowerid,
                        servicecode,
                        loanid,
                        CommonVariables.devicetype);
        validatepending.enqueue(validatePendingLoanRequestCallback);
    }

    private final Callback<GenericResponse> validatePendingLoanRequestSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                hideProgressDialog();

                if(errorBody == null){
                    if(response.body().getStatus().equals("000")){

//                    validateInformationBeforeProceeding();
                        if(passedAssistantLoanList != null) {
                            if(passedAssistantLoanList.size() > 0) {

                                Bundle args = new Bundle();
                                args.putParcelableArrayList("LoanTypeSelected", passedAssistantLoanList);
                                CoopAssistantHomeActivity.start(getViewContext(), 13, args);

                            } else {
                                showToast("Please select an option.", GlobalToastEnum.NOTICE);
                            }
                        } else {
                            showToast("Please select an option.", GlobalToastEnum.NOTICE);
                        }

                    } else if(response.body().getStatus().equals("203")){

                        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

                        dialog.createDialog("",CommonFunctions.replaceEscapeData(response.body().getMessage()),
                                "Close", "", ButtonTypeEnum.SINGLE,
                                false, false, DialogTypeEnum.WARNING);

                        dialog.hideCloseImageButton();

                        View singlebtn = dialog.btnSingle();
                        singlebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                    } else if(response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else{
                        showErrorGlobalDialogs(response.body().getMessage());

                    }

                } else{
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
                hideProgressDialog();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };

    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/
    private void validateIfHasPendingLoanRequestV2(){

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("loanid", loanid);
        parameters.put("devicetype", CommonVariables.devicetype);


        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "validateIfHasPendingLoanRequestV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        validateIfHasPendingLoanRequestV2Object(validateIfHasPendingLoanRequestV2Callback);


    }
    private void validateIfHasPendingLoanRequestV2Object(Callback<GenericResponse> genericResponseCallback){
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .validateIfHasPendingLoanRequestV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  validateIfHasPendingLoanRequestV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                hideProgressDialog();

                if(errorBody == null){
                    String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                    if(response.body().getStatus().equals("000")){
                        if(passedAssistantLoanList != null) {
                            if(passedAssistantLoanList.size() > 0) {

                                Bundle args = new Bundle();
                                args.putParcelableArrayList("LoanTypeSelected", passedAssistantLoanList);
                                CoopAssistantHomeActivity.start(getViewContext(), 13, args);

                            } else {
                                showToast("Please select an option.", GlobalToastEnum.NOTICE);
                            }
                        } else {
                            showToast("Please select an option.", GlobalToastEnum.NOTICE);
                        }

                    } else if(response.body().getStatus().equals("203")){

                        GlobalDialogs dialog = new GlobalDialogs(getViewContext());
                        dialog.createDialog("",CommonFunctions.replaceEscapeData(message),
                                "Close", "", ButtonTypeEnum.SINGLE,
                                false, false, DialogTypeEnum.WARNING);

                        dialog.hideCloseImageButton();

                        View singlebtn = dialog.btnSingle();
                        singlebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                    } else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else{
                        showErrorGlobalDialogs(message);
                    }

                } else{
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
                hideProgressDialog();
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
