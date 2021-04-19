package com.goodkredit.myapplication.activities.dropoff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.NumberTextWatcherForThousand;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.dropoff.DropOffMerchants;
import com.goodkredit.myapplication.responses.dropoff.SearchDropOffMerchantsResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeNewDropOffActivity extends BaseActivity implements View.OnClickListener {

    private DropOffMerchants dropOffMerchants = null;

    //SEARCH MERCHANTS
    private EditText edt_search_box;
    private ImageView img_search;
    private LinearLayout layout_merchantdetails;

    private LinearLayout layout_amounttodropoff;
    private View view1;
    private ImageView img_merchantlogo;
    private TextView txv_merchantname;
    private TextView txv_merchantaddress;
    private EditText edt_amounttodropoff;
    private EditText edt_purpose;
    private EditText edt_notes;
    private Button btn_dropoff;
    private EditText edtReferenceNo;

    private String merchantaddress;

    private MaterialDialog materialDialog;

    private DatabaseHandler mdb;
    private String imei;
    private String sessionid;
    private String authcode;
    private String borrowerid;
    private String userid;
    private String devicetype;


    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_drop_off);

        init();
        initData();
    }

    private void init() {
        setupToolbar();

        edt_search_box = findViewById(R.id.edt_search_box);
        edt_search_box.requestFocus();

        img_search = findViewById(R.id.img_search);

        txv_merchantname = findViewById(R.id.txv_merchantname);
        txv_merchantaddress = findViewById(R.id.txv_merchantaddress);
        layout_merchantdetails = findViewById(R.id.layout_merchantdetails);
        layout_amounttodropoff = findViewById(R.id.layout_amounttodropoff);
        view1 = findViewById(R.id.view);

        img_merchantlogo = findViewById(R.id.img_merchantlogo);
        edt_amounttodropoff = findViewById(R.id.edt_amounttodropoff);
        edt_amounttodropoff.addTextChangedListener(new NumberTextWatcherForThousand(edt_amounttodropoff));
        edt_purpose = findViewById(R.id.edt_purpose);
        edt_notes = findViewById(R.id.edt_notes);

        edtReferenceNo = findViewById(R.id.edtReferenceNo);

        edt_purpose.setInputType(InputType.TYPE_NULL);
        edt_purpose.setOnClickListener(this);
        img_search.setOnClickListener(this);

        btn_dropoff = findViewById(R.id.btn_dropoff);
        btn_dropoff.setOnClickListener(this);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        devicetype = "ANDROID";

        layout_merchantdetails.setVisibility(View.GONE);
        layout_amounttodropoff.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);

        edt_search_box.requestFocus();
        searchMerchant();
    }

    private void searchMerchant() {
        edt_search_box.requestFocus();
        edt_search_box.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (textView.getText().toString().length() > 0) {
                        edt_search_box.requestFocus();
                        if (edt_search_box.getText().toString().trim().isEmpty()) {
                            showToast("Please enter a valid keyword.", GlobalToastEnum.WARNING);
                        } else {
                            showProgressDialog(getViewContext(),"","Searching merchant...");
                            getSession();
                        }

                    }
                }
                return false;
            }
        });

    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                    .getRegisteredSession(imei, userid);
//            call.enqueue(callsession);
//        } else {
//            showError(getString(R.string.generic_internet_error_message));
//        }
//    }
//
//    private final Callback<String> callsession = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("<!DOCTYPE html>")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = responseData;
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    searchDropOffMerchants(searchDropOffMerchantsSession);
//                }
//            } else {
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //searchDropOffMerchants(searchDropOffMerchantsSession);
            searchDropOffMerchantsV2();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void searchDropOffMerchants(Callback<SearchDropOffMerchantsResponse> searchDropOffMerchantsCallback) {
        Call<SearchDropOffMerchantsResponse> searchdropoffmerchants = RetrofitBuild.getDropOffAPIService(getViewContext())
                .searchDropOffMerchantsCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        edt_search_box.getText().toString().trim(),
                        edt_search_box.getText().toString().trim(),
                        devicetype);

        searchdropoffmerchants.enqueue(searchDropOffMerchantsCallback);
    }

    private final Callback<SearchDropOffMerchantsResponse> searchDropOffMerchantsSession = new Callback<SearchDropOffMerchantsResponse>() {
        @Override
        public void onResponse(Call<SearchDropOffMerchantsResponse> call, Response<SearchDropOffMerchantsResponse> response) {

            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    List<DropOffMerchants> list = response.body().getDropOffMerchantsList();

                    if (list.size() > 0) {
                        if (list.size() == 1) {
                            dropOffMerchants = list.get(0);

                            layout_merchantdetails.setVisibility(View.VISIBLE);
                            layout_amounttodropoff.setVisibility(View.VISIBLE);
                            view1.setVisibility(View.VISIBLE);

                            merchantaddress = dropOffMerchants.getStreetAddress().concat(", ".concat(dropOffMerchants.getCity())).concat(", ").concat(dropOffMerchants.getCountry());

                            if (dropOffMerchants.getMerchantLogo().equals(".") || dropOffMerchants.getMerchantLogo() == null) {
                                Glide.with(getViewContext())
                                        .load(R.drawable.dropoff_header)
                                        .into(img_merchantlogo);
                            } else {
                                Glide.with(getViewContext())
                                        .load(dropOffMerchants.getMerchantLogo())
                                        .into(img_merchantlogo);
                            }

                            txv_merchantname.setText(CommonFunctions.replaceEscapeData(dropOffMerchants.getMerchantName()));
                            txv_merchantaddress.setText(CommonFunctions.replaceEscapeData(merchantaddress));

                        } else {
                            edt_search_box.requestFocus();
                            Intent intent = new Intent(getViewContext(), DropOffMerchantsActivity.class);
                            intent.putExtra("merchantid", CommonFunctions.replaceEscapeData(edt_search_box.getText().toString()));
                            startActivityForResult(intent, 100);
                        }
                    } else {
                        showToast("No merchant for this keyword.", GlobalToastEnum.WARNING);
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<SearchDropOffMerchantsResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    public static void start(Context context) {
        Intent intent = new Intent(context, MakeNewDropOffActivity.class);
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
            case R.id.img_search: {
                edt_search_box.requestFocus();
                if (edt_search_box.getText().toString().trim().isEmpty()) {
                    showToast("Please enter a valid keyword.", GlobalToastEnum.WARNING);
                } else {
                    showProgressDialog(getViewContext(),"","Searching merchant...");
                    getSession();
                }
                break;
            }
            case R.id.edt_purpose: {
                purposeDialog();
                break;
            }

            case R.id.btn_dropoff: {

                String amount = NumberTextWatcherForThousand.trimCommaOfString(edt_amounttodropoff.getText().toString());
                String purpose = edt_purpose.getText().toString();
                String notes = edt_notes.getText().toString();
                String referenceno;

                if(edtReferenceNo.getText().toString().trim().isEmpty()){
                    referenceno = ".";
                } else{
                    referenceno = edtReferenceNo.getText().toString().trim();
                }

                if (amount.isEmpty() || purpose.isEmpty()) {
                    showToast("Please fill all required fields.", GlobalToastEnum.WARNING);
                } else {

                    if (amount.equals("0")) {
                        showToast("Please input a valid amount.", GlobalToastEnum.WARNING);
                    } else {
                        DropOffConfirmationActivity.start(getViewContext(), dropOffMerchants, notes, amount, purpose, referenceno);
                    }
                }

                break;
            }
        }
    }

    //CREATES A DIALOG FOR PURPOSE
    private void purposeDialog() {
        materialDialog = new MaterialDialog.Builder(getViewContext())
                .title("Choose purpose")
                .items(new String[]{"Collection", "Payment", "Others"})
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        edt_purpose.setText(text);
                        return false;
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {

            dropOffMerchants = data.getParcelableExtra(DropOffMerchants.KEY_DROPOFFMERCHANTS);

            layout_merchantdetails.setVisibility(View.VISIBLE);
            layout_amounttodropoff.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);

            merchantaddress = dropOffMerchants.getStreetAddress().concat(", ".concat(dropOffMerchants.getCity())).concat(", ").concat(dropOffMerchants.getCountry());

            if (dropOffMerchants.getMerchantLogo().equals(".") || dropOffMerchants.getMerchantLogo() == null) {
                Glide.with(getViewContext())
                        .load(R.drawable.dropoff_header)
                        .into(img_merchantlogo);
            } else {
                Glide.with(getViewContext())
                        .load(dropOffMerchants.getMerchantLogo())
                        .into(img_merchantlogo);
            }

            txv_merchantname.setText(CommonFunctions.replaceEscapeData(dropOffMerchants.getMerchantName()));
            txv_merchantaddress.setText(CommonFunctions.replaceEscapeData(merchantaddress));

            edt_search_box.setText(CommonFunctions.replaceEscapeData(dropOffMerchants.getMerchantName()));
            edt_search_box.requestFocus();
            edt_search_box.setSelection(edt_search_box.getText().length());
        }
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
    */

    private void searchDropOffMerchantsV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("merchantid", edt_search_box.getText().toString().trim());
            parameters.put("merchantname", edt_search_box.getText().toString().trim());
            parameters.put("devicetype",CommonVariables.devicetype);


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "searchDropOffMerchantV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            searchDropOffMerchantsV2Object(searchDropOffMerchantsV2Session);

        }else{
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void searchDropOffMerchantsV2Object(Callback<GenericResponse> searchDropOffMerchantsCallback) {
        Call<GenericResponse> searchdropoffmerchants = RetrofitBuilder.getDropOffV2API(getViewContext())
                .searchDropOffMerchantV2(authenticationid,sessionid,param);
        searchdropoffmerchants.enqueue(searchDropOffMerchantsCallback);
    }

    private final Callback<GenericResponse> searchDropOffMerchantsV2Session = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                String message =  CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    List<DropOffMerchants> list = new Gson().fromJson(data, new TypeToken<List<DropOffMerchants>>(){}.getType());

                    if (list.size() > 0) {
                        if (list.size() == 1) {
                            dropOffMerchants = list.get(0);

                            layout_merchantdetails.setVisibility(View.VISIBLE);
                            layout_amounttodropoff.setVisibility(View.VISIBLE);
                            view1.setVisibility(View.VISIBLE);

                            merchantaddress = dropOffMerchants.getStreetAddress().concat(", ".concat(dropOffMerchants.getCity())).concat(", ").concat(dropOffMerchants.getCountry());

                            if (dropOffMerchants.getMerchantLogo().equals(".") || dropOffMerchants.getMerchantLogo() == null) {
                                Glide.with(getViewContext())
                                        .load(R.drawable.dropoff_header)
                                        .into(img_merchantlogo);
                            } else {
                                Glide.with(getViewContext())
                                        .load(dropOffMerchants.getMerchantLogo())
                                        .into(img_merchantlogo);
                            }

                            txv_merchantname.setText(CommonFunctions.replaceEscapeData(dropOffMerchants.getMerchantName()));
                            txv_merchantaddress.setText(CommonFunctions.replaceEscapeData(merchantaddress));

                        } else {
                            edt_search_box.requestFocus();
                            Intent intent = new Intent(getViewContext(), DropOffMerchantsActivity.class);
                            intent.putExtra("merchantid", CommonFunctions.replaceEscapeData(edt_search_box.getText().toString()));
                            startActivityForResult(intent, 100);
                        }
                    } else {
                        showToast("No merchant for this keyword.", GlobalToastEnum.WARNING);
                    }

                }else if(response.body().getStatus().equals("003")){
                    showAutoLogoutDialog(message);
                }
                else {
                    showError(message);
                }
            } else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };


}
