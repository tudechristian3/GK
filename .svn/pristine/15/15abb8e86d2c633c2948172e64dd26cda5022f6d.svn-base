package com.goodkredit.myapplication.activities.vouchers.grouping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.adapter.vouchers.HorizontalUnusedRVAdapter;
import com.goodkredit.myapplication.adapter.vouchers.HorizontalUsedRVAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GetValidateGroupVoucherResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.SpacesItemDecoration;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 4/24/2017.
 */

public class GroupVoucherActivity extends BaseActivity implements View.OnDragListener, View.OnClickListener {

    public static final String KEY_GROUP_VOUCHER_CODE = "voucher_code";
    public static final String KEY_IS_EDIT_MODE = "edit_mode";
    public static final String KEY_GROUP_NAME = "group_name";
    public static final String KEY_GROUP_VOUCHER_SESSION = "group_voucher_session";

    private RecyclerView mRvIndivVouchersList;
    private RecyclerView mRvGroupVouchersList;

    private HorizontalUnusedRVAdapter mUnusedAdapter;
    private HorizontalUsedRVAdapter mUsedAdapter;

    private DatabaseHandler db;

    private ImageView mDropBox;
    private RelativeLayout mDropBorder;
    private TextView mDropText;
    private TextView mSubTotal;
    private TextView mTvGroupName;
    private TextView emptyvoucherPayment;
    private RelativeLayout mTuts;
    private TextView mTotalVoucher;

    private String sessionidval;
    private String mImei;
    private String mUserId;
    private String mBorrowerId;
    private String mMobileNumber;
    private String mVoucherSession;
    private String mVoucherCode;
    private String mVoucherPin;
    private String mGroupName = "";
    private String mGroupVoucherCode;

    private ArrayList<Voucher> mGroupedVouchers;

    private Gson gson;
    private AQuery aq;

    private Toolbar toolbar;

    private boolean isClosingSession = false;

    private CountDownTimer mTimer;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLE FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_voucher);
        overridePendingTransition(R.anim.slide_left_in, R.anim.fade_out);
        initialize();
    }

    private void initialize() {

        try {
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("Group Voucher");

            db = new DatabaseHandler(this);
            gson = new Gson();
            aq = new AQuery(getViewContext());

            //UNIFIED SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            emptyvoucherPayment = findViewById(R.id.emptyvoucherPayment);
            mTuts = findViewById(R.id.gv_tutorial);

            LinearLayoutManager indivLinearLayoutManager = new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false);
            LinearLayoutManager groupLinearLayoutManager = new LinearLayoutManager(getViewContext(), LinearLayoutManager.HORIZONTAL, false);
            mRvIndivVouchersList = findViewById(R.id.individualVouchersList);
            mRvGroupVouchersList = findViewById(R.id.groupVouchersList);

            mRvIndivVouchersList.setLayoutManager(indivLinearLayoutManager);
            mRvGroupVouchersList.setLayoutManager(groupLinearLayoutManager);

            mRvIndivVouchersList.addItemDecoration(new SpacesItemDecoration(10));
            mRvGroupVouchersList.addItemDecoration(new SpacesItemDecoration(10));

            mGroupedVouchers = new ArrayList<>();

            if (db.getIndividualVouchers(db).size() < 1) {
                emptyvoucherPayment.setVisibility(View.VISIBLE);
                mRvIndivVouchersList.setVisibility(View.GONE);
            } else {
                emptyvoucherPayment.setVisibility(View.GONE);
                mRvIndivVouchersList.setVisibility(View.VISIBLE);
            }

            mUnusedAdapter = new HorizontalUnusedRVAdapter(GroupVoucherActivity.this, db.getIndividualVouchers(db));
            mRvIndivVouchersList.setAdapter(mUnusedAdapter);
            if (getIntent().hasExtra(KEY_IS_EDIT_MODE)) {
                mGroupVoucherCode = getIntent().getStringExtra(KEY_GROUP_VOUCHER_CODE);
                mGroupedVouchers = db.getVouchersFromGroupedVouchers(db, mGroupVoucherCode);
                mVoucherSession = getIntent().getStringExtra(KEY_GROUP_VOUCHER_SESSION);
            } else {
                mVoucherSession = ".";
            }
            mUsedAdapter = new HorizontalUsedRVAdapter(GroupVoucherActivity.this, mGroupedVouchers);
            mRvGroupVouchersList.setAdapter(mUsedAdapter);

            mImei = PreferenceUtils.getImeiId(getViewContext());
            mUserId = PreferenceUtils.getUserId(getViewContext());
            mBorrowerId = PreferenceUtils.getBorrowerId(getViewContext());
            mMobileNumber = PreferenceUtils.getUserId(getViewContext());
            // mVoucherSession = ".";

            mDropBox = findViewById(R.id.droparea);
            aq.id(mDropBox).image("https://s3-us-west-1.amazonaws.com/goodkredit/group-vouchers.png", false, true);
            mDropBox.setOnDragListener(this);

            mDropBorder = findViewById(R.id.dropareawrap);
            mDropText = findViewById(R.id.dropareatext);
            mTvGroupName = findViewById(R.id.group_name);
            mTotalVoucher = findViewById(R.id.total_vouchers);

            mSubTotal = findViewById(R.id.subtotal);

            if (!getIntent().hasExtra(KEY_IS_EDIT_MODE)) {
                //showAskGroupNameDialog();
            } else {
                mGroupName = getIntent().getStringExtra(KEY_GROUP_NAME);
                mTvGroupName.setText(mGroupName);
                toolbar.setTitle(mGroupName);
                mTotalVoucher.setText(String.valueOf(mGroupedVouchers.size()));
                setSubTotal(mGroupedVouchers);
            }

            findViewById(R.id.btn_done).setOnClickListener(this);
            findViewById(R.id.gv_tutorial).setOnClickListener(this);

            mTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    mTuts.performClick();
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, GroupVoucherActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String groupvouchercode, String groupname, boolean isEditMode, String groupvouchersession) {
        Intent intent = new Intent(context, GroupVoucherActivity.class);
        intent.putExtra(KEY_GROUP_VOUCHER_CODE, groupvouchercode);
        intent.putExtra(KEY_IS_EDIT_MODE, isEditMode);
        intent.putExtra(KEY_GROUP_NAME, groupname);
        intent.putExtra(KEY_GROUP_VOUCHER_SESSION, groupvouchersession);
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
    public boolean onDrag(View v, DragEvent event) {

        // TODO Auto-generated method stub
        final int action = event.getAction();

        switch (action) {

            case DragEvent.ACTION_DRAG_STARTED:
                mDropText.setTextColor(Color.parseColor("#25bed9"));
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                mDropBorder.setBackgroundResource(R.drawable.border_dash);
                mDropText.setTextColor(Color.parseColor("#FFFFFFFF"));
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                mDropBorder.setBackgroundResource(R.color.colorPrimaryDark);
                mDropText.setTextColor(Color.parseColor("#25bed9"));
                break;
            case DragEvent.ACTION_DROP: {

                mVoucherCode = mUnusedAdapter.getClickedVoucher().getVoucherCode();
                mVoucherPin = mUnusedAdapter.getClickedVoucher().getVoucherPIN();

                if (mTvGroupName.getText().toString().trim().equals("") || mTvGroupName.getText().toString().trim().isEmpty())
                    //showAskGroupNameDialog();
                    showAskGroupNameDialogNew();
                else {
                    if (mUsedAdapter.getData().isEmpty() || mUsedAdapter.getData().size() == 0)
                        mVoucherSession = ".";
                    verifySession();
                }
                return (true);
            }
            case DragEvent.ACTION_DRAG_ENDED: {
                mDropBorder.setBackgroundResource(R.drawable.border_dash);
                mDropText.setTextColor(Color.parseColor("#FFFFFFFF"));
                return (true);
            }

            default:
                break;
        }

        return true;
    }

    private void verifySession() {

        try {
            int status = CommonFunctions.getConnectivityStatus(getViewContext());
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {
                showProgressDialog(getViewContext(), "Validating voucher", "Please wait ...");
                if (!isClosingSession)
                    //new HttpAsyncTask().execute(CommonVariables.GROUP_VOUCHER);
                groupVoucherV2();

            }
        } catch (Exception e) {
            hideProgressDialog();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done: {
                //onBackPressed();
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                finish();
                break;
            }
            case R.id.gv_tutorial: {
                mTimer.cancel();
                mTuts.setVisibility(View.GONE);
                if (!getIntent().hasExtra(KEY_IS_EDIT_MODE)) {
                    //showAskGroupNameDialog();
                    showAskGroupNameDialogNew();
                } else {
                    mGroupName = getIntent().getStringExtra(KEY_GROUP_NAME);
                    mTvGroupName.setText(mGroupName);
                    toolbar.setTitle(mGroupName);
                    mTotalVoucher.setText(String.valueOf(mGroupedVouchers.size()));
                    setSubTotal(mGroupedVouchers);
                }
                break;
            }
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {

            String json = "";

            try {
                String authcode = CommonFunctions.getSha1Hex(mImei + mUserId + sessionid);
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", mBorrowerId);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", mImei);
                jsonObject.accumulate("userid", mUserId);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("vouchercode", mVoucherCode);
                jsonObject.accumulate("voucherpin", mVoucherPin);
                jsonObject.accumulate("vouchersession", mVoucherSession);
                jsonObject.accumulate("devicetype", "ANDROID");
                jsonObject.accumulate("groupname", mGroupName);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                CommonFunctions.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);
        }

        @Override
        protected void onPostExecute(String result) {
            hideProgressDialog();
            try {
                if (result == null) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    showToast("Invalid Entry.", GlobalToastEnum.ERROR);
                } else if (result.equals("002")) {
                    showToast("Invalid Session.", GlobalToastEnum.ERROR);
                } else if (result.equals("003")) {
                    showToast("Invalid Guarantor ID.", GlobalToastEnum.ERROR);
                } else if (result.equals("error")) {
                    showToast("Cannot connect to server. Please try again.", GlobalToastEnum.ERROR);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    //processResult(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public void setSubTotal(ArrayList<Voucher> arrayList) {
        double subtotal = 0;
        if (!arrayList.isEmpty())
            for (Voucher voucher : arrayList) {
                subtotal += voucher.getRemainingBalance();
            }
        mTotalVoucher.setText(String.valueOf(arrayList.size()));
        mSubTotal.setText(CommonFunctions.currencyFormatter(String.valueOf(subtotal)));
    }

    public void refreshIndivListAfterRemovel(Voucher voucher) {
        mUnusedAdapter.getData().add(voucher);
        mUnusedAdapter.notifyDataSetChanged();
    }

    public String getVoucherSession() {
        return mVoucherSession;
    }

    private void showAskGroupNameDialog() {
        try {
            final MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                    .customView(R.layout.dialog_ask_group_name, false)
                    .cancelable(true)
                    .positiveText("OK")
                    .negativeText("CANCEL")
                    .show();

            View view = dialog.getCustomView();
            final EditText edtGroupName = view.findViewById(R.id.dialogGroupName);
            int newGroupCount = 0;
            for (Voucher voucher : db.getGroupedVouchers(db)) {
                if (voucher.getGroupName().contains("New Group"))
                    newGroupCount++;
            }
            if (newGroupCount > 0) {
                edtGroupName.setHint("New Group " + newGroupCount);
                mGroupName = "New Group " + newGroupCount;
            } else {
                edtGroupName.setHint("New Group");
                mGroupName = "New Group";
            }

            View positive = dialog.getActionButton(DialogAction.POSITIVE);
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtGroupName.getText().toString().trim().length() <= 0) {

                    } else {
                        mGroupName = edtGroupName.getText().toString().trim();
                    }
                    mTvGroupName.setText(mGroupName);
                    toolbar.setTitle(mGroupName);
                    dialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAskGroupNameDialogNew() {
        try {
            GlobalDialogs dialog = new GlobalDialogs(getViewContext());

            dialog.createDialog("Set Group Name",
                    "", "CANCEL", "OK", ButtonTypeEnum.DOUBLE,
                    false, false, DialogTypeEnum.EDITTEXT);

            View closebtn = dialog.btnCloseImage();
            closebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            List<String> editTextDataType  = new ArrayList<>();
            editTextDataType.add(String.valueOf(GlobalDialogsEditText.VARCHAR));

            LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
                    LinearLayout.VERTICAL,
                    new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.CENTER,
                            R.drawable.underline, 12, "Enter subject here ..."));


            int newGroupCount = 0;
            for (Voucher voucher : db.getGroupedVouchers(db)) {
                if (voucher.getGroupName().contains("New Group"))  {
                    newGroupCount++;
                }
            }

            if (newGroupCount > 0) {
                mGroupName = "New Group " + newGroupCount;
            } else {
                mGroupName = "New Group";
            }

            View btndoubleone = dialog.btnDoubleOne();
            btndoubleone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            View btndoubletwo = dialog.btnDoubleTwo();
            btndoubletwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int count = editTextContainer.getChildCount();
                    for (int i = 0; i < count; i++) {
                        View editView = editTextContainer.getChildAt(i);
                        if (editView instanceof EditText) {
                            EditText editItem = (EditText) editView;
                            String taggroup = editItem.getTag().toString();
                            if(taggroup.equals("TAG 0")) {
                                editItem.setHint(mGroupName);
                                if (editItem.getText().toString().trim().length() > 0) {
                                    mGroupName = editItem.getText().toString();
                                }
                            }
                        }
                    }

                    mTvGroupName.setText(mGroupName);
                    toolbar.setTitle(mGroupName);
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }

    }

    @Override
    public void onBackPressed() {
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        finish();
    }

    private void showDissolvingWarning() {
        new MaterialDialog.Builder(getViewContext())
                .title("Dissolve Group")
                .content("You are about to dissolve this group. Do you want to proceed?")
                .cancelable(false)
                .positiveText("PROCEED")
                .negativeText("CANCEL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        isClosingSession = true;
                        Intent intent = new Intent(getViewContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        startActivity(intent);
                    }
                })
                .show();
    }

    /**
     *  SECURITY UPDATE
     *  AS OF
     *  OCTOBER 2019
     * **/

    private void groupVoucherV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();

            parameters.put("imei",mImei);
            parameters.put("userid",mUserId);
            parameters.put("borrowerid",mBorrowerId);
            parameters.put("vouchercode",mVoucherCode);
            parameters.put("voucherpin", mVoucherPin);
            parameters.put("vouchersession", mVoucherSession);
            parameters.put("groupname", mGroupName);


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "groupVoucherV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            groupVoucherV2Object(groupVoucherV2Callback);

        }else{
            CommonFunctions.hideDialog();
            showNoInternetToast();
        }
    }
    private void groupVoucherV2Object(Callback<GenericResponse> groupVoucher) {
        Call<GenericResponse> call = RetrofitBuilder.getVoucherV2API(getViewContext())
                .groupVoucherV2(
                        authenticationid,sessionid,param
                );
        call.enqueue(groupVoucher);
    }
    private Callback<GenericResponse> groupVoucherV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody =  response.errorBody();
            hideProgressDialog();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    mUnusedAdapter.setData(mUnusedAdapter.getCurrentData(mUnusedAdapter.getClickedPosition()));
                    mUnusedAdapter.notifyDataSetChanged();

                    mGroupedVouchers.add(0, mUnusedAdapter.getClickedVoucher());
                    mUsedAdapter.setData(mGroupedVouchers);
                    mUsedAdapter.notifyItemInserted(0);
                    mRvGroupVouchersList.smoothScrollToPosition(0);

                    mVoucherSession = data;
                    setSubTotal(mUsedAdapter.getData());

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else{
                    showErrorGlobalDialogs(message);
                }
            }else{
                showErrorGlobalDialogs();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            t.printStackTrace();
            showErrorGlobalDialogs();
        }
    };
}
