package com.goodkredit.myapplication.activities.vouchers.payoutone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.vouchers.AccreditedBanks;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherPayoutOneAddBankAccountActivity extends BaseActivity implements View.OnClickListener {

    private EditText edt_bankname;
    private EditText edt_accountno;
    private EditText edt_accountname;

    //PARAMETERS
    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
    private String bank;
    private String accountname;
    private String accountno;

    //IS GK NEGOSYO
    private String resellerid = "";
    private String distributorid = "";
    private boolean checkIfReseller = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_payoutone_add_bank_account);

        setupToolbar();

        init();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_payoutone_proceed, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        edt_bankname = findViewById(R.id.edt_bankname);
        edt_accountno = findViewById(R.id.edt_accountno);
        edt_accountname = findViewById(R.id.edt_accountname);

    }

    private void initData() {

        Intent intent = getIntent();

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        sessionid = PreferenceUtils.getSessionID(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        bank = intent.getStringExtra(AccreditedBanks.KEY_ACCREDITEDBANKS_BANKNAME);

        edt_bankname.setText(CommonFunctions.replaceEscapeData(bank));

    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            setUpProgressLoaderMessageDialog("Processing request... Please wait.");
            addSubscriberBankAccount(addSubscriberBankAccountSession);
        }else{
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    }

    private void addSubscriberBankAccount (Callback<GenericResponse> addSubscriberBankAccountCallback ){
        accountname = CommonFunctions.replaceEscapeData(edt_accountname.getText().toString());
        accountno = edt_accountno.getText().toString();
        Call<GenericResponse> addbankaccount = RetrofitBuild.getVoucherV3Service(getViewContext())
                .addSubscriberBankAccount(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        edt_bankname.getText().toString(),
                        accountno,
                        accountname,
                        CommonVariables.devicetype);
        addbankaccount.enqueue(addSubscriberBankAccountCallback);
    }

    private final Callback<GenericResponse> addSubscriberBankAccountSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            setUpProgressLoaderDismissDialog();
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){

                    if (distributorid.equals("") || distributorid.equals(".")
                            || resellerid.equals("") || resellerid.equals(".")) {
                        checkIfReseller = false;
                    } else {
                        checkIfReseller = true;
                    }

                    //SHOW SUCCESS DIALOG
                    GlobalDialogs dialog = new GlobalDialogs(getViewContext());

                    dialog.createDialog("SUCCESS", response.body().getMessage()+ " Thank you for using GoodKredit!",
                            "Add Another", "", ButtonTypeEnum.SINGLE, checkIfReseller, false, DialogTypeEnum.SUCCESS);

                    View closebtn = dialog.btnCloseImage();
                    closebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(getViewContext(), VoucherPayoutOneDepositToBankActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                    View singlebtn = dialog.btnSingle();
                    singlebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            addAnotherResult();

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
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            showErrorGlobalDialogs();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.payoutone_proceed) {
            if(!edt_accountname.getText().toString().trim().equals("") &&
                    !edt_accountno.getText().toString().trim().equals("")){
                showConfirmDialog();
            } else{
                showToast("Please fill all fields.", GlobalToastEnum.WARNING);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmDialog(){
        MaterialDialog materialDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_confirm_add_bank_account, true)
                .build();

        View view = materialDialog.getCustomView();

        TextView txv_bankname = view.findViewById(R.id.txv_bankname);
        TextView txv_accountname = view.findViewById(R.id.txv_accountname);
        TextView txv_accountno = view.findViewById(R.id.txv_accountno);

        txv_bankname.setText(CommonFunctions.replaceEscapeData(bank));
        txv_accountname.setText(CommonFunctions.replaceEscapeData(edt_accountname.getText().toString().trim()));
        txv_accountno.setText(edt_accountno.getText().toString().trim());
        materialDialog.show();

        TextView txvCloseDialog = (TextView) view.findViewById(R.id.txvConfirmCloseDialog);
        txvCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        TextView txvProceed = (TextView) view.findViewById(R.id.txvConfirmProceed);
        txvProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
                getSession();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setResult(RESULT_CANCELED);
    }

    public void addAnotherResult() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onClick(View view) {

    }
}
