package com.goodkredit.myapplication.activities.vouchers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.entities.GroupRegenPINData;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.grouping.GroupVoucherActivity;
import com.goodkredit.myapplication.adapter.vouchers.GroupDetailsGridViewAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.barcodes.BarcodeVPagerAdapter;
import com.goodkredit.myapplication.fragments.barcodes.FragmentBarcodeFormatCODABAR;
import com.goodkredit.myapplication.fragments.barcodes.FragmentBarcodeFormatCode39;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GetRegenGroupPINResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.view.ExpandableHeightGridView;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.blurry.Blurry;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 5/24/2017.
 */

public class GroupedVouchersActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String VOUCHERS = "grouped_vouchers";
    private static final String TAG = "GroupedVouchersActivity";

    private static final String VOUCHER = "voucher";

    private ImageView mImgVoucherImage;
    private TextView mTvVoucherName;
    private TextView mTvNofVoucher;
    private TextView mTvTotalAmount;

    private TextView mTvVoucherCode;
    private TextView mTvVoucherPIN;
    private ImageView mBarcode;
    private TextView mTvBarcodeValue;

    private ProgressBar mProgressBar;

    private DatabaseHandler db;
    private Gson gson;
    private ArrayList<Voucher> mVoucherArrayList;

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    private String borrowerid = "";
    private String imei = "";
    private String sessionidval = "";
    private String userid = "";

    private String mPIN;
    private String newgroupname = "";

    private CommonFunctions cf;

    private Voucher mVoucher;

    private Context mContext;

    private ExpandableHeightGridView mGridView;
    private GroupDetailsGridViewAdapter mGridAdapter;

    private CoordinatorLayout rootView;

    private ViewPager vPager;
    private RelativeLayout left;
    private RelativeLayout right;
    private TextView whitebarcodeValue;

    private RelativeLayout barcodesLayout;
    private RelativeLayout barcodesFormats;
    private boolean isClicked = false;
    MaterialDialog dialog;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLE FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String regenIndex;
    private String regenAuthenticationid;
    private String regenKeyEncryption;
    private String regenParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_grouped_voucher_details);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (barcodesFormats.getVisibility() == View.VISIBLE) {
                barcodesFormats.setVisibility(View.GONE);
                Blurry.delete(rootView);
                isClicked = false;
                disableEnableControls(true, rootView);
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (barcodesFormats.getVisibility() == View.VISIBLE) {
            barcodesFormats.setVisibility(View.GONE);
            Blurry.delete(rootView);
            isClicked = false;
            disableEnableControls(true, rootView);
        } else {
            finish();
        }
    }


    private void init() {

        mVoucher = getIntent().getParcelableExtra(VOUCHERS);
        setupToolbarWithTitle(mVoucher.getGroupName());

        db = new DatabaseHandler(getViewContext());
        gson = new Gson();
        mContext = getViewContext();

        borrowerid = PreferenceUtils.getBorrowerId(mContext);
        imei = PreferenceUtils.getImeiId(mContext);
        userid = PreferenceUtils.getUserId(mContext);

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mVoucherArrayList = db.getVouchersFromGroupedVouchers(db, mVoucher.getGroupVoucherCode());
        rootView = (CoordinatorLayout) findViewById(R.id.rootView);
        mImgVoucherImage = (ImageView) findViewById(R.id.gvd_voucherImage);
        mTvVoucherName = (TextView) findViewById(R.id.gvd_groupName);
        mTvNofVoucher = (TextView) findViewById(R.id.gvd_nofVoucher);
        mTvTotalAmount = (TextView) findViewById(R.id.gvd_totalAmount);

        mTvVoucherCode = (TextView) findViewById(R.id.gvd_vouchercode);
        mTvVoucherPIN = (TextView) findViewById(R.id.gvd_voucherpin);
        mBarcode = (ImageView) findViewById(R.id.barcode);
        mTvBarcodeValue = (TextView) findViewById(R.id.barcodeValue);

        mProgressBar = (ProgressBar) findViewById(R.id.gvd_regen_progress);

        findViewById(R.id.gvd_regen_pin).setOnClickListener(this);
        findViewById(R.id.gvd_edit_mode).setOnClickListener(this);
        findViewById(R.id.renamegroup).setOnClickListener(this);


        String vouchercodeval = mVoucher.getGroupVoucherCode();
        String vcode = vouchercodeval.substring(0, 2) + "-" + vouchercodeval.substring(2, 6) + "-" + vouchercodeval.substring(6, 11) + "-" + vouchercodeval.substring(11, 12);

        Glide.with(getViewContext())
                .load(CommonVariables.s3link + "group-vouchers.png")
                .into(mImgVoucherImage);

        mTvVoucherName.setText(mVoucher.getGroupName());

        mTvNofVoucher.setText(String.valueOf(mVoucherArrayList.size()));
        mTvTotalAmount.setText(CommonFunctions.currencyFormatter(getTotal(mVoucherArrayList)));
        mTvVoucherCode.setText(vcode);
        mTvVoucherPIN.setText(mVoucher.getGroupVoucherPIN());
        mBarcode.setImageBitmap(getBarcodeBitmap(mVoucher.getGroupBarCode()));
        mTvBarcodeValue.setText(mVoucher.getGroupBarCode());

        mGridView = (ExpandableHeightGridView) findViewById(R.id.gridView);
        mGridView.setExpanded(true);
        mGridAdapter = new GroupDetailsGridViewAdapter(getViewContext(), mVoucherArrayList);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(this);

        vPager = (ViewPager) findViewById(R.id.viewpager);
        left = (RelativeLayout) findViewById(R.id.left);
        right = (RelativeLayout) findViewById(R.id.right);
        whitebarcodeValue = (TextView) findViewById(R.id.whitebarcodeValue);

        barcodesLayout = (RelativeLayout) findViewById(R.id.barCodeLayout);
        barcodesLayout.setOnClickListener(this);

        barcodesFormats = (RelativeLayout) findViewById(R.id.barcodeLayout);
        barcodesFormats.setOnClickListener(this);

        findViewById(R.id.left).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);

        whitebarcodeValue.setText(mTvBarcodeValue.getText().toString());
    }

    public Voucher getVoucher() {
        return mVoucher;
    }

    private String getTotal(ArrayList<Voucher> arrayList) {
        double subtotal = 0;
        if (!arrayList.isEmpty())
            for (Voucher voucher : arrayList) {
                subtotal += voucher.getRemainingBalance();
            }
        return String.valueOf(subtotal);
    }

    private Bitmap getBarcodeBitmap(String groupvouchercode) {
        Bitmap bitmap = null;
        try {
            bitmap = encodeAsBitmap(groupvouchercode, BarcodeFormat.CODE_128, 750, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /*********************
     * ALL FOR BARCODE
     ******************/
    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {

        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    private void disableEnableControls(boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gvd_regen_pin: {
                mPIN = mTvVoucherPIN.getText().toString().trim();
                verifySession("");
                break;
            }
            case R.id.gvd_edit_mode: {
                GroupVoucherActivity.start(getViewContext(), mVoucher.getGroupVoucherCode(), mVoucher.getGroupName(), true, mVoucher.getGroupVoucherSession());
                break;
            }
            case R.id.renamegroup: {
                //showAskGroupNameDialog();
                showAskGroupNameDialogNew();
                break;
            }

            case R.id.barCodeLayout: {
                barcodesFormats.setVisibility(View.VISIBLE);
                setupViewPager(vPager);
                if (!isClicked) {
                    isClicked = true;
                    Blurry.with(getApplicationContext())
                            .radius(2)
                            .sampling(8)
                            .color(Color.argb(80, 0, 0, 0))
                            .async()
                            .onto(rootView);
                    disableEnableControls(false, rootView);
                }
                break;
            }
            case R.id.barcodeLayout: {
                barcodesFormats.setVisibility(View.GONE);
                Blurry.delete(rootView);
                isClicked = false;
                disableEnableControls(true, rootView);
                break;
            }
            case R.id.left: {
                if (vPager.getCurrentItem() == 0) {
                    vPager.setCurrentItem(0, true);
                } else {
                    vPager.setCurrentItem(0, true);
                }
                break;
            }

            case R.id.right: {
                if (vPager.getCurrentItem() == 1) {
                    vPager.setCurrentItem(1, true);
                } else {
                    vPager.setCurrentItem(1, true);
                }
                break;
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        BarcodeVPagerAdapter adapter = new BarcodeVPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FragmentBarcodeFormatCODABAR.newInstance(mTvBarcodeValue.getText().toString()), mTvBarcodeValue.getText().toString());
        adapter.addFragment(FragmentBarcodeFormatCode39.newInstance(mTvBarcodeValue.getText().toString()), mTvBarcodeValue.getText().toString());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getViewContext(), AvailableVoucherDetails.class);
        intent.putExtra("VOUCHER_OBJECT", mVoucherArrayList.get(position));
        getViewContext().startActivity(intent);
    }

    public String getCode() {
        return mTvBarcodeValue.getText().toString();
    }

    //create session
    private void verifySession(final String flag) {
        try {
            int status = cf.getConnectivityStatus(mContext);
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {
                if (flag.equals("RENAMEGROUP")) {
                    cf.showDialog(this, "", "Processing. Please wait ...", false);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                }

                if (flag.equals("RENAMEGROUP")) {
                    //call AsynTask to perform network operation on separate thread
                    //new HttpAsyncTask1().execute(CommonVariables.RENAMEGROUP);
                    renameGroupV2();

                } else {
                    //call AsynTask to perform network operation on separate thread
                    //new HttpAsyncTask().execute(CommonVariables.REGEN_GROUPED_VOUCHER_PIN);
                    regenerateGroupVoucherPINV2();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //send request
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = cf.getSha1Hex(imei + userid + sessionid);
            try {

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("vouchercode", mVoucher.getGroupVoucherCode());
                jsonObject.accumulate("voucherpin", mPIN);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
            }

            return cf.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            mProgressBar.setVisibility(View.GONE);

            try {
                if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    GetRegenGroupPINResponse response = gson.fromJson(result, GetRegenGroupPINResponse.class);
                    if (response.getStatus().equals("000")) {
                        mTvVoucherPIN.setText(response.getData().getVoucherPIN());
                        db.updateGroupVoucherPIN(db, response.getData().getVoucherPIN(), mVoucher.getGroupVoucherCode());
                        mBarcode.setImageDrawable(null);
                        mBarcode.setImageBitmap(getBarcodeBitmap(response.getData().getBarCode()));
                        mTvBarcodeValue.setText(response.getData().getBarCode());
                    } else {
                        showToast("" + response.getMessage(), GlobalToastEnum.NOTICE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = cf.getSha1Hex(imei + userid + sessionid);
            try {

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("vouchercode", mVoucher.getGroupVoucherCode());
                jsonObject.accumulate("newgroupname", newgroupname);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
            }

            return cf.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            cf.hideDialog();

            try {
                if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    JSONObject parentObj = new JSONObject(result);
                    String message = parentObj.getString("message");
                    String status = parentObj.getString("status");
                    if (status.equals("000")) {
                        setupToolbarWithTitle(newgroupname);
                        mTvVoucherName.setText(newgroupname);
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        dialog.dismiss();

                    } else {
                        showToast("" + message, GlobalToastEnum.NOTICE);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void start(Context context, Voucher voucher) {
        Intent intent = new Intent(context, GroupedVouchersActivity.class);
        intent.putExtra(VOUCHERS, voucher);
        context.startActivity(intent);
    }

    private void showAskGroupNameDialog() {
        try {
            dialog = new MaterialDialog.Builder(getViewContext())
                    .customView(R.layout.dialog_ask_group_name, false)
                    .cancelable(true)
                    .positiveText("OK")
                    .negativeText("CANCEL")
                    .show();

            View view = dialog.getCustomView();
            final EditText edtGroupName = (EditText) view.findViewById(R.id.dialogGroupName);

            View positive = dialog.getActionButton(DialogAction.POSITIVE);
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String groupname = mVoucher.getGroupName();
                    newgroupname = edtGroupName.getText().toString();
                    if (groupname.equals("") || newgroupname.equals(groupname)) {
                        showToast("Please input new name", GlobalToastEnum.WARNING);
                    } else {
                        verifySession("RENAMEGROUP");
                    }

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

            List<String> editTextDataType = new ArrayList<>();
            editTextDataType.add(String.valueOf(GlobalDialogsEditText.VARCHAR));

            LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
                    LinearLayout.VERTICAL,
                    new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.CENTER,
                            R.drawable.underline, 12, "New Group"));

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
                            if (taggroup.equals("TAG 0")) {
                                newgroupname = editItem.getText().toString();
                            }
                        }
                    }
                    String groupname = mVoucher.getGroupName();
                    if (groupname.equals("") ||
                            newgroupname.equals("") ||
                            newgroupname.equals(groupname)) {
                        showToast("Please input a new name", GlobalToastEnum.WARNING);
                    } else {
                        dialog.dismiss();
                        verifySession("RENAMEGROUP");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     **/

    //RENAME GROUP
    private void renameGroupV2() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("vouchercode", mVoucher.getGroupVoucherCode());
            parameters.put("newgroupname", newgroupname);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(mContext, parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "renameGroupv2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            renameGroupV2Object(renameGroupV2Callback);

        } else {
            cf.hideDialog();
            showNoInternetToast();

        }
    }
    private void renameGroupV2Object(Callback<GenericResponse> renameVoucher) {
        Call<GenericResponse> call = RetrofitBuilder.getVoucherV2API(getViewContext())
                .renameGroupV2(
                        authenticationid, sessionid, param
                );
        call.enqueue(renameVoucher);
    }
    private Callback<GenericResponse> renameGroupV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            cf.hideDialog();
            if (errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                    setupToolbarWithTitle(newgroupname);
                    mTvVoucherName.setText(newgroupname);
                    CommonVariables.VOUCHERISFIRSTLOAD = true;
                } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(mContext.getString(R.string.logoutmessage));
                } else {
                    showErrorGlobalDialogs(message);
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            cf.hideDialog();
            t.printStackTrace();
            showErrorGlobalDialogs();
        }
    };

    //REGENERATE GROUP VOUCHER PIN
    private void regenerateGroupVoucherPINV2() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("vouchercode", mVoucher.getGroupVoucherCode());
            parameters.put("voucherpin", mPIN);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(mContext, parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            regenIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", regenIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            regenAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            regenKeyEncryption = CommonFunctions.getSha1Hex(regenAuthenticationid + sessionid + "regenerateGroupVoucherPINV2");
            regenParam = CommonFunctions.encryptAES256CBC(regenKeyEncryption, String.valueOf(paramJson));

            regenerateGroupVoucherPINV2Object(regenerateGroupVoucherPINV2Callback);

        } else {
            mProgressBar.setVisibility(View.GONE);
            showNoInternetToast();

        }
    }
    private void regenerateGroupVoucherPINV2Object(Callback<GenericResponse> renameVoucher) {
        Call<GenericResponse> call = RetrofitBuilder.getVoucherV2API(getViewContext())
                .regenerateGroupVoucherPINV2(
                        regenAuthenticationid, sessionid, regenParam
                );
        call.enqueue(renameVoucher);
    }
    private Callback<GenericResponse> regenerateGroupVoucherPINV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mProgressBar.setVisibility(View.GONE);
            if (errorBody == null) {
                String message = CommonFunctions.decryptAES256CBC(regenKeyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String data = CommonFunctions.decryptAES256CBC(regenKeyEncryption, response.body().getData());

                    GroupRegenPINData regenData = new Gson().fromJson(data,GroupRegenPINData.class);
                    mTvVoucherPIN.setText(regenData.getVoucherPIN());
                    db.updateGroupVoucherPIN(db, regenData.getVoucherPIN(), mVoucher.getGroupVoucherCode());
                    mBarcode.setImageDrawable(null);
                    mBarcode.setImageBitmap(getBarcodeBitmap(regenData.getBarCode()));
                    mTvBarcodeValue.setText(regenData.getBarCode());

                } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(mContext.getString(R.string.logoutmessage));
                } else {
                    showErrorGlobalDialogs(message);
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mProgressBar.setVisibility(View.GONE);
            t.printStackTrace();
            showErrorGlobalDialogs();
        }
    };


}
