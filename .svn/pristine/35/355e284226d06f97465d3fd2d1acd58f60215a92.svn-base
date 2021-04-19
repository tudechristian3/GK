package com.goodkredit.myapplication.activities.vouchers.rfid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.rfid.RFIDDBHelper;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.rfid.RFIDInfo;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.rfid.GetRFIDInformationResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RFIDDetailsActivity extends BaseActivity implements View.OnClickListener {

    private List<RFIDInfo> mGridData = new ArrayList<>();
    private RFIDInfo rfid = null;

    private Switch sw_rfid;
    private Switch sw_limit;
    private TextView txv_card_no;
    private TextView txv_rfid_status;
    private TextView txv_limit_status;
    private EditText edt_current_balance;
    private ImageView img_bal_close;
    private Button btn_update_limit;

    //API PARAMETERS
    private DatabaseHandler mdb;
    private String sessionid;
    private String imei;
    private String borrowerid;
    private String userid;
    private String authcode;
    private String rfidpin;
    private String rfidamount;

    private String str_spin;
    private String str_balance;
    private String str_key = "";
    private boolean sw_limit_bool_state;
    private String sw_limit_state;
    private String sw_rfid_state;
    private String from;
    private String rfidposition;
    private String limitposition;
    private String activebalance;
    private String activespin;

    private int withLimit = 0;

    private String str_active_spin;
    private String position;

    private LinearLayout layout_limit;
    private LinearLayout layout_rfid_details;

    //WITH PIN
    private LinearLayout linear_withpin_container;
    private Switch sw_withpin;
    private TextView txv_withpin_status;
    private EditText edt_rfid_spin;
    private ImageView img_spin_close;
    private EditText edt_minimum_amount;
    private ImageView img_min_close;
    private Button btn_update_withpin;

    private int withPin = 0;
    private String sw_withpin_state = "";
    private String str_minamount = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid_details);

        init();
        initData();
    }

    private void init() {
        setupToolbar();

        sw_rfid = (Switch) findViewById(R.id.sw_rfid);
        sw_limit = (Switch) findViewById(R.id.sw_limit);
        txv_card_no = (TextView) findViewById(R.id.txv_rfid_cardno);
        txv_rfid_status = (TextView) findViewById(R.id.txv_rfid_status);
        txv_limit_status = (TextView) findViewById(R.id.txv_limit_status);
        edt_current_balance = (EditText) findViewById(R.id.edt_current_balance);

        img_bal_close = (ImageView) findViewById(R.id.img_bal_close);


        btn_update_limit = (Button) findViewById(R.id.btn_update_limit);
        layout_limit = (LinearLayout) findViewById(R.id.layout_limit);
        layout_rfid_details = (LinearLayout) findViewById(R.id.layout_rfid_details);

        //WITH PIN
        linear_withpin_container = (LinearLayout) findViewById(R.id.linear_withpin_container);
        sw_withpin = findViewById(R.id.sw_withpin);
        txv_withpin_status = findViewById(R.id.txv_withpin_status);
        edt_rfid_spin = (EditText) findViewById(R.id.edt_rfid_spin);
        img_spin_close = (ImageView) findViewById(R.id.img_spin_close);
        edt_minimum_amount = findViewById(R.id.edt_minimum_amount);
        img_min_close = (ImageView) findViewById(R.id.img_min_close);
        img_min_close.setOnClickListener(this);

        btn_update_withpin = findViewById(R.id.btn_update_withpin);

        edt_rfid_spin.setCursorVisible(false);
        edt_current_balance.setCursorVisible(false);
        edt_minimum_amount.setCursorVisible(false);

        img_bal_close.setOnClickListener(this);
        img_spin_close.setOnClickListener(this);
        btn_update_limit.setOnClickListener(this);
        edt_rfid_spin.setOnClickListener(this);
        edt_current_balance.setOnClickListener(this);
        edt_minimum_amount.setOnClickListener(this);
        btn_update_withpin.setOnClickListener(this);
    }

    private void initData() {
        //COMMON
        mdb = new DatabaseHandler(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());

        rfid = getIntent().getParcelableExtra(RFIDInfo.KEY_RFIDINFO);
        from = getIntent().getStringExtra(RFIDInfo.KEY_RFIDFROM);
        rfidposition = getIntent().getStringExtra(RFIDInfo.KEY_RFIDPOSITION);
        position = getIntent().getStringExtra(RFIDInfo.KEY_POSITION);

        if (from.equals("activerfid")) {
            limitposition = getIntent().getStringExtra(RFIDInfo.KEY_LIMITPOSITION);
            activebalance = getIntent().getStringExtra(RFIDInfo.KEY_ACTIVEBALANCE);
            activespin = getIntent().getStringExtra(RFIDInfo.KEY_ACTIVESPIN);
        } else if (from.equals("inactiverfid")) {
            limitposition = getIntent().getStringExtra(RFIDInfo.KEY_LIMITPOSITION);
        }

        txv_card_no.setText(CommonFunctions.replaceEscapeData(rfid.getRFIDCardNumber()));

        str_spin = rfid.getRFIDPIN();

        str_balance = String.valueOf(rfid.getAmountLimitBalance());
        edt_current_balance.setText(CommonFunctions.currencyFormatter(str_balance));

        str_minamount = String.valueOf(rfid.getPINMinAmount());
        edt_minimum_amount.setText(CommonFunctions.currencyFormatter(str_minamount));

        if (str_spin.trim().equals("") || str_spin.trim().equals(".")) {
            edt_rfid_spin.setText(CommonFunctions.replaceEscapeData(""));
        } else {
            edt_rfid_spin.setText(CommonFunctions.replaceEscapeData(str_spin));
        }

        edt_current_balance.setSelection(edt_current_balance.getText().length());
        edt_rfid_spin.setSelection(edt_rfid_spin.getText().length());

        txv_card_no.requestFocus();


        if (from.equals("activerfid")) {
            sw_rfid.setChecked(true);
            layout_rfid_details.setVisibility(View.VISIBLE);
            txv_rfid_status.setText("UNLOCK");

            sw_rfid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        sw_rfid_state = "ACTIVE";
                        layout_rfid_details.setVisibility(View.VISIBLE);
                        txv_rfid_status.setText("UNLOCK");
                        txv_rfid_status.setTextColor(getResources().getColor(R.color.color_908F90));
                        PreferenceUtils.saveBooleanPreference(getViewContext(), rfidposition, true);
                        sw_limit.setChecked(false);
                        txv_limit_status.setText("DISABLED");
                        txv_limit_status.setTextColor(getResources().getColor(R.color.color_error_red));

                    } else {
                        sw_rfid_state = "INACTIVE";
                        layout_rfid_details.setVisibility(View.GONE);
                        txv_rfid_status.setText("LOCK");
                        txv_rfid_status.setTextColor(getResources().getColor(R.color.color_error_red));
                        PreferenceUtils.saveBooleanPreference(getViewContext(), rfidposition, false);
                        sw_limit.setChecked(false);
                        txv_limit_status.setText("DISABLED");
                        txv_limit_status.setTextColor(getResources().getColor(R.color.color_error_red));
                    }

                    getSession("updaterfidstatus");
                }
            });


        } else if (from.equals("inactiverfid")) {
            sw_rfid.setChecked(false);
            layout_rfid_details.setVisibility(View.GONE);
            txv_rfid_status.setTextColor(getResources().getColor(R.color.color_error_red));

            if (rfid.getStatus().equals("BLOCKED")) {
//                sw_rfid.setEnabled(false);
                txv_rfid_status.setText("BLOCKED");
            } else {
                txv_rfid_status.setText("LOCK");
            }

            sw_rfid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        sw_rfid.setEnabled(true);
                        sw_rfid_state = "ACTIVE";
                        layout_rfid_details.setVisibility(View.VISIBLE);
                        txv_rfid_status.setText("UNLOCK");
                        txv_rfid_status.setTextColor(getResources().getColor(R.color.color_908F90));
                        PreferenceUtils.saveBooleanPreference(getViewContext(), rfidposition, true);
                    } else {
                        sw_rfid.setEnabled(true);
                        sw_rfid_state = "INACTIVE";
                        layout_rfid_details.setVisibility(View.GONE);
                        txv_rfid_status.setText("LOCK");
                        txv_rfid_status.setTextColor(getResources().getColor(R.color.color_error_red));
                        PreferenceUtils.saveBooleanPreference(getViewContext(), rfidposition, false);
                    }

                    if (rfid.getStatus().equals("BLOCKED")) {
                        sw_rfid.setEnabled(false);
                        sw_rfid_state = "INACTIVE";
                        layout_rfid_details.setVisibility(View.GONE);
                        txv_rfid_status.setText("BLOCKED");
                        txv_rfid_status.setTextColor(getResources().getColor(R.color.color_error_red));
                        PreferenceUtils.saveBooleanPreference(getViewContext(), rfidposition, false);

                        showToast("This RFID is blocked. Please contact your sponsor.", GlobalToastEnum.WARNING);
                    } else {
                        getSession("updaterfidstatus");
                    }
                }
            });
        }


        withLimit = rfid.getIsWithLimit();

        if (withLimit > 0) {
            sw_limit.setChecked(true);
            layout_limit.setVisibility(View.VISIBLE);
            txv_limit_status.setText("ENABLED");
            txv_rfid_status.setTextColor(getResources().getColor(R.color.color_908F90));
        } else {
            sw_limit.setChecked(false);
            layout_limit.setVisibility(View.GONE);
            txv_limit_status.setText("DISABLED");
            txv_limit_status.setTextColor(getResources().getColor(R.color.color_error_red));
        }

        sw_limit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sw_limit.setChecked(true);
                    layout_limit.setVisibility(View.VISIBLE);
                    txv_limit_status.setText("ENABLED");
                    txv_limit_status.setTextColor(getResources().getColor(R.color.color_908F90));
                    sw_limit_state = "1";
                    PreferenceUtils.saveBooleanPreference(getViewContext(), limitposition, true);
                } else {
                    sw_limit.setChecked(false);
                    layout_limit.setVisibility(View.GONE);
                    txv_limit_status.setText("DISABLED");
                    txv_limit_status.setTextColor(getResources().getColor(R.color.color_error_red));
                    sw_limit_state = "0";
                    PreferenceUtils.saveBooleanPreference(getViewContext(), limitposition, false);
                }

                getSession("onofflimit");
            }
        });

        
        withPin = rfid.getIsWithPIN();

        if (withPin > 0) {
            sw_withpin.setChecked(true);
            linear_withpin_container.setVisibility(View.VISIBLE);
            txv_withpin_status.setText("ENABLED");
            txv_withpin_status.setTextColor(getResources().getColor(R.color.color_908F90));
        } else {
            sw_withpin.setChecked(false);
            linear_withpin_container.setVisibility(View.GONE);
            txv_withpin_status.setText("DISABLED");
            txv_withpin_status.setTextColor(getResources().getColor(R.color.color_error_red));
        }

        sw_withpin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sw_withpin.setChecked(true);
                    linear_withpin_container.setVisibility(View.VISIBLE);
                    txv_withpin_status.setText("ENABLED");
                    sw_withpin_state = "1";
                    txv_withpin_status.setTextColor(getResources().getColor(R.color.color_908F90));
                } else {
                    sw_withpin.setChecked(false);
                    linear_withpin_container.setVisibility(View.GONE);
                    txv_withpin_status.setText("DISABLED");
                    sw_withpin_state = "0";
                    txv_withpin_status.setTextColor(getResources().getColor(R.color.color_error_red));
                }

                getSession("onoffwithpin");
            }

        });

    }

    //API
    private void getSession(String key) {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (key.equals("updatebalance")) {
                str_key = "updatebalance";
                updateRFIDBalance(updateRFIDBalanceSession);
            } else if (key.equals("rfidinfo")) {
                getRFIDInformation(getRFIDInformationSession);
            } else if (key.equals("onofflimit")) {
                str_key = "onofflimit";
                onOffRFIDBalanceLimit(onOffRFIDBalanceLimitSession);
            } else if (key.equals("updaterfidstatus")) {
                updateRFIDStatus(updateRFIDStatusSession);
            } else if (key.equals("onoffwithpin")) {
                str_key = "onoffwithpin";
                onOffRFIDWithPIN();
            } else if (key.equals("updatewithpin")) {
                str_key = "updatewithpin";
                updateRFIDWithPIN();
            }

        } else {
            showNoInternetToast();
        }
    }

    private void updateRFIDBalance(Callback<GenericResponse> updateRFIDBalanceCallback) {
        Call<GenericResponse> updatebalance = RetrofitBuild.getRFIDAPIService(getViewContext())
                .updateRFIDBalanceCall(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        authcode,
                        rfid.getRFIDCardNumber(),
                        rfidamount,
                        "ANDROID");
        updatebalance.enqueue(updateRFIDBalanceCallback);
    }

    private final Callback<GenericResponse> updateRFIDBalanceSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    getSession("rfidinfo");
                    showToast("Balance Successfully Updated.", GlobalToastEnum.INFORMATION);

                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                showErrorGlobalDialogs(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void getRFIDInformation(Callback<GetRFIDInformationResponse> getRFIDInformationCallback) {
        Call<GetRFIDInformationResponse> getrfidinformation = RetrofitBuild.getRFIDAPIService(getViewContext())
                .getRFIDInformationCall(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        authcode,
                        "0",
                        CommonVariables.devicetype);
        getrfidinformation.enqueue(getRFIDInformationCallback);
    }

    private final Callback<GetRFIDInformationResponse> getRFIDInformationSession = new Callback<GetRFIDInformationResponse>() {
        @Override
        public void onResponse(Call<GetRFIDInformationResponse> call, Response<GetRFIDInformationResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    mdb.truncateTable(mdb, RFIDDBHelper.TABLE_NAME);

                    List<RFIDInfo> list;
                    list = response.body().getRfidInformationList();

                    for (RFIDInfo rfidobj : list) {
                        mdb.insertRFIDInfo(mdb, rfidobj);

                        if (rfidobj.getRFIDCardNumber().equals(rfid.getRFIDCardNumber())) {
                            rfid = rfidobj;
                            if (str_key.equals("updatebalance") || str_key.equals("onofflimit")) {
                                str_balance = CommonFunctions.wholenumberFormatter(String.valueOf(rfidobj.getAmountLimitBalance()));
                                edt_current_balance.setText(CommonFunctions.currencyFormatter(str_balance));
                                edt_current_balance.setCursorVisible(false);
                            } else if (str_key.equals("updatewithpin") || str_key.equals("onoffwithpin")) {
                                str_spin = rfidobj.getRFIDPIN();
                                if (str_spin.trim().equals("") || str_spin.trim().equals(".")) {
                                    edt_rfid_spin.setText("");
                                } else {
                                    edt_rfid_spin.setText(str_spin);
                                }

                                edt_rfid_spin.setCursorVisible(false);
                                //PreferenceUtils.saveStringPreference(getViewContext(), activespin, str_spin);

                                str_minamount = CommonFunctions.wholenumberFormatter(String.valueOf(rfidobj.getPINMinAmount()));
                                if (str_minamount.equals("") || str_minamount.trim().equals("0.00")) {
                                    edt_minimum_amount.setText("0.00");
                                } else {
                                    Logger.debug("checkhttp","HELLO?" + edt_minimum_amount);
                                    edt_minimum_amount.setText(CommonFunctions.currencyFormatter(str_minamount));
                                }
                                edt_minimum_amount.setCursorVisible(false);
                            }

                            break;
                        }

                    }

                } else if (response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                showErrorGlobalDialogs(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<GetRFIDInformationResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void onOffRFIDBalanceLimit(Callback<GenericResponse> onOffRFIDBalanceLimitCallback) {
        Call<GenericResponse> onofflimit = RetrofitBuild.getRFIDAPIService(getViewContext())
                .onOffRFIDBalanceLimitCall(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        authcode,
                        rfid.getRFIDCardNumber(),
                        sw_limit_state,
                        "ANDROID");
        onofflimit.enqueue(onOffRFIDBalanceLimitCallback);
    }

    private final Callback<GenericResponse> onOffRFIDBalanceLimitSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    getSession("rfidinfo");
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                showErrorGlobalDialogs(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void updateRFIDStatus(Callback<GenericResponse> updateRFIDStatusCallback) {
        Call<GenericResponse> updaterfidstatus = RetrofitBuild.getRFIDAPIService(getViewContext())
                .updateRFIDStatusCall(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        authcode,
                        rfid.getRFIDCardNumber(),
                        sw_rfid_state,
                        "ANDROID");
        updaterfidstatus.enqueue(updateRFIDStatusCallback);
    }

    private final Callback<GenericResponse> updateRFIDStatusSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

//                    getSession("rfidinfo");

                } else if (response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                showErrorGlobalDialogs(response.body().getMessage());
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    //WITH PIN
    private void onOffRFIDWithPIN() {
        Call<GenericResponse> onOffRFIDWithPIN = RetrofitBuild.getRFIDAPIService(getViewContext())
                .onOffRFIDWithPIN(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        authcode,
                        rfid.getRFIDCardNumber(),
                        sw_withpin_state,
                        "ANDROID");
        onOffRFIDWithPIN.enqueue(onOffRFIDWithPINCallBack);
    }

    private final Callback<GenericResponse> onOffRFIDWithPINCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    getSession("rfidinfo");
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            t.getLocalizedMessage();
            showNoInternetToast();
        }
    };

    private void updateRFIDWithPIN() {
        Call<GenericResponse> updateRFIDWithPIN = RetrofitBuild.getRFIDAPIService(getViewContext())
                .updateRFIDWithPIN(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        authcode,
                        rfid.getRFIDCardNumber(),
                        rfidpin,
                        str_minamount,
                        "ANDROID");
        updateRFIDWithPIN.enqueue(updateRFIDWithPINCallBack);
    }

    private final Callback<GenericResponse> updateRFIDWithPINCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    getSession("rfidinfo");
                    showToast(response.body().getMessage(), GlobalToastEnum.INFORMATION);
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            t.getLocalizedMessage();
            showNoInternetToast();
        }
    };

    //OTHERS
    public static void start(Context context) {
        Intent intent = new Intent(context, RFIDDetailsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_bal_close: {
                edt_current_balance.setText("");
                break;
            }
            case R.id.img_spin_close: {
                edt_rfid_spin.setText("");
                break;
            }

            case R.id.img_min_close: {
                edt_minimum_amount.setText("");
                break;
            }
            case R.id.btn_update_withpin: {
                String inputtedspin = edt_rfid_spin.getText().toString().trim();
                String inputtedminamount = edt_minimum_amount.getText().toString().trim();

                if (inputtedminamount.trim().equals("") || inputtedminamount.trim().equals(".")) {
                    inputtedminamount = "0.00";
                }

                if (inputtedminamount.contains(".00")) {
                    inputtedminamount = inputtedminamount.substring(0, inputtedminamount.length() - 3);
                    if (inputtedminamount.contains(",")) {
                        inputtedminamount = inputtedminamount.replace(",", "");
                    }
                }

                if (str_minamount.contains(".00")) {
                    str_minamount = str_minamount.substring(0, str_minamount.length() - 3);
                    if (str_minamount.contains(",")) {
                        str_minamount = str_minamount.replace(",", "");
                    }
                } else if (str_minamount.contains(".0")) {
                    str_minamount = str_minamount.substring(0, str_minamount.length() - 2);
                }

                if (inputtedspin.length() != 4) {
                    showToast("You entered invalid SPIN. Please try again.", GlobalToastEnum.WARNING);
                } else {
                    if (inputtedspin.equals("0000") || inputtedspin.equals("1111") || inputtedspin.equals("2222") ||
                            inputtedspin.equals("3333") || inputtedspin.equals("4444") || inputtedspin.equals("5555") ||
                            inputtedspin.equals("6666") || inputtedspin.equals("7777") || inputtedspin.equals("8888") ||
                            inputtedspin.equals("9999") || inputtedspin.equals("0123") || inputtedspin.equals("1234") ||
                            inputtedspin.equals("2345") || inputtedspin.equals("3456") || inputtedspin.equals("4567") ||
                            inputtedspin.equals("5678") || inputtedspin.equals("6789") || inputtedspin.equals("7890")) {

                        showToast("Please enter a unique security PIN.", GlobalToastEnum.WARNING);
                    } else {
                        str_minamount = inputtedminamount;
                        rfidpin = inputtedspin;
                        getSession("updatewithpin");
                    }
                }

                break;
            }

            case R.id.btn_update_limit: {

                String inputtedbalance = edt_current_balance.getText().toString().trim();

                if (inputtedbalance.contains(".00")) {
                    inputtedbalance = inputtedbalance.substring(0, inputtedbalance.length() - 3);
                    if (inputtedbalance.contains(",")) {
                        inputtedbalance = inputtedbalance.replace(",", "");
                    }
                }

                if (str_balance.contains(".00")) {
                    str_balance = str_balance.substring(0, str_balance.length() - 3);
                    if (str_balance.contains(",")) {
                        str_balance = str_balance.replace(",", "");
                    }
                } else if (str_balance.contains(".0")) {
                    str_balance = str_balance.substring(0, str_balance.length() - 2);
                }

                if (inputtedbalance.equals("0") || inputtedbalance.equals("")) {
                    showToast("You entered an invalid amount. Please try again.", GlobalToastEnum.WARNING);
                } else if (inputtedbalance.equals(str_balance)) {
                    showToast("You entered the same amount. Please try again.", GlobalToastEnum.WARNING);
                } else {
                    rfidamount = edt_current_balance.getText().toString().trim();
                    getSession("updatebalance");
                }

                break;
            }
            case R.id.edt_rfid_spin: {
                edt_rfid_spin.setCursorVisible(true);
                break;
            }
            case R.id.edt_current_balance: {
                edt_current_balance.setText("");
                edt_current_balance.setCursorVisible(true);
                break;
            }

            case R.id.edt_minimum_amount: {
                edt_minimum_amount.setText("");
                edt_minimum_amount.setCursorVisible(true);
                break;
            }

        }

    }
}
