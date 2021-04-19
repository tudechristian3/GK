package com.goodkredit.myapplication.activities.sendmoney;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;

public class SendMoneyActivity extends BaseActivity implements View.OnClickListener {

    private static final int RESULT_PICK_CONTACT = 850;

    private EditText edtName;
    private EditText edtPhoneNum;
    private EditText edtAmount;

    private String mName;
    private String mNumber;
    private String mAmount;

    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        init();
    }

    private void init() {
        setupToolbar();

        findViewById(R.id.btn_view_partners).setOnClickListener(this);
        findViewById(R.id.btn_choose_contact).setOnClickListener(this);
        findViewById(R.id.btn_send_money).setOnClickListener(this);

        edtName = findViewById(R.id.edt_receiver_name);
        edtPhoneNum = findViewById(R.id.edt_receiver_mobile);
        edtAmount = findViewById(R.id.edt_amount);
    }

    public static void start(Context context, GKService service) {
        Intent intent = new Intent(context, SendMoneyActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_view_partners: {
                hideSoftKeyboard();
                ClaimCentersActivity.start(getViewContext());
                break;
            }
            case R.id.btn_choose_contact: {
                hideSoftKeyboard();
                openContacts();
                break;
            }
            case R.id.btn_send_money: {
                try {

                    mName = edtName.getText().toString().trim();
                    mNumber = CommonFunctions.userMobileNumber(edtPhoneNum.getText().toString().trim(),true);
                    mAmount = edtAmount.getText().toString().trim();

                    if (isMobileNumberValid(mNumber)) {
                        if (isAmountValid(mAmount)) {
                            showServiceChargeDialog(Double.parseDouble(mAmount), 1000);
                        } else {
                            showError("Amount is zero. Please check.");
                        }
                    } else {
                        showMobileNumberInvalidDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void openContacts() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null;
            String name = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int phoneName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(phoneName);
            setDataFromContacts(phoneNo, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDataFromContacts(String phoneNo, String name) {
        edtName.setText(name);
        edtPhoneNum.setText("+" + CommonFunctions.userMobileNumber(phoneNo,true));
    }

    private boolean checkEntries() {
        boolean bool = false;
        try {
            mName = edtName.getText().toString().trim();
            mNumber = CommonFunctions.userMobileNumber(edtPhoneNum.getText().toString().trim(),true);
            mAmount = edtAmount.getText().toString().trim();

            if (Double.parseDouble(mAmount) <= 0)
                bool = false;
            else if (mName.equals("") || mName.isEmpty())
                bool = false;
            else if (mNumber.equals("") || mNumber.isEmpty())
                bool = false;
            else
                bool = true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return bool;
    }

    private void showServiceChargeDialog(double amount, double servicecharge) {

        double totalamounttopay = 0;

        TextView popok;
        TextView popcancel;
        TextView popamountpaid;
        TextView popservicecharge;
        TextView popotherchargeval;
        TextView poptotalamount;
        TextView popmobileval;
        TextView popname;
        TextView popnameval;

        dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_billspayment_preconfirmation);

        dialog.findViewById(R.id.tblrow5).setVisibility(View.VISIBLE);
        dialog.findViewById(R.id.tblrow6).setVisibility(View.VISIBLE);

        popamountpaid = dialog.findViewById(R.id.popamounttopayval);
        popservicecharge = dialog.findViewById(R.id.popservicechargeval);
        poptotalamount = dialog.findViewById(R.id.poptotalval);
        popotherchargeval = dialog.findViewById(R.id.popotherchargeval);
        popotherchargeval.setVisibility(View.GONE);
        popok = dialog.findViewById(R.id.popok);
        popcancel = dialog.findViewById(R.id.popcancel);
        popmobileval = dialog.findViewById(R.id.popmobileval);
        popname = dialog.findViewById(R.id.popnamelbl);
        popnameval = dialog.findViewById(R.id.popnameval);

        //calculate
        totalamounttopay = amount + servicecharge;
        mAmount = String.valueOf(totalamounttopay);

        //set value
        popamountpaid.setText(CommonFunctions.currencyFormatter(String.valueOf(amount)));
        popservicecharge.setText(CommonFunctions.currencyFormatter(String.valueOf(servicecharge)));
        poptotalamount.setText(CommonFunctions.currencyFormatter(String.valueOf(totalamounttopay)));
        popmobileval.setText(String.valueOf("+" + mNumber));
        popnameval.setText(mName);

        dialog.show();

        //click close
        popcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });

        popok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                //createSession();
            }

        });
    }

    private boolean isMobileNumberValid(String str) {
        boolean bool = false;
        if (!str.isEmpty()) {
            if (str.length() == 10) {
                if (String.valueOf(str.charAt(0)).equals("9")) {
                    mNumber = CommonFunctions.userMobileNumber(str,true);
                    bool = true;
                }
            } else if (str.length() == 11) {
                if (str.substring(0, 2).equals("09")) {
                    mNumber = CommonFunctions.userMobileNumber(str,true);
                    bool = true;
                }
            } else if (str.length() == 12) {
                if (str.substring(0, 3).equals("639")) {
                    mNumber = CommonFunctions.userMobileNumber(str,true);
                    bool = true;
                }
            } else if (str.length() < 10) {
                showMobileNumberInvalidDialog();
                bool = false;
            }
        } else if (str.isEmpty()) {
            bool = false;
        }

        return bool;
    }

    private void showMobileNumberInvalidDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("Mobile number is invalid. Please check.")
                .positiveText("OK")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        edtPhoneNum.setText("639");
                    }
                })
                .show();
    }

    private boolean isAmountValid(String amount) {
        if (amount.isEmpty() || amount.equals("0")) {
            return false;
        } else {
            return true;
        }
    }
}
