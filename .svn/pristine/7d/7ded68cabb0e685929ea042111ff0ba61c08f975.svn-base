package com.goodkredit.myapplication.fragments.vouchers;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.GroupedVouchersActivity;
import com.goodkredit.myapplication.activities.vouchers.grouping.GroupVoucherActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.entities.GroupRegenPINData;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GetRegenGroupPINResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
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
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 5/19/2017.
 */

public class GroupVoucherDetailsFragment extends BaseFragment implements View.OnClickListener {

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

    private Voucher mVoucher;

    private AQuery aq;
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

    private CommonFunctions cf;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String regenIndex;
    private String regenAuthenticationid;
    private String regenKeyEncryption;
    private String regenParam;

    public static GroupVoucherDetailsFragment newInstance() {
        GroupVoucherDetailsFragment fragment = new GroupVoucherDetailsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grouped_voucher_details, null);

        aq = new AQuery(getContext());
        db = new DatabaseHandler(getContext());
        gson = new Gson();

        borrowerid = PreferenceUtils.getBorrowerId(mContext);
        imei = PreferenceUtils.getImeiId(mContext);
        userid = PreferenceUtils.getUserId(mContext);

        //UNIFIFED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mVoucher = ((GroupedVouchersActivity) getActivity()).getVoucher();

        mVoucherArrayList = db.getVouchersFromGroupedVouchers(db, mVoucher.getGroupVoucherCode());

        mImgVoucherImage = (ImageView) view.findViewById(R.id.gvd_voucherImage);
        mTvVoucherName = (TextView) view.findViewById(R.id.gvd_groupName);
        mTvNofVoucher = (TextView) view.findViewById(R.id.gvd_nofVoucher);
        mTvTotalAmount = (TextView) view.findViewById(R.id.gvd_totalAmount);

        mTvVoucherCode = (TextView) view.findViewById(R.id.gvd_vouchercode);
        mTvVoucherPIN = (TextView) view.findViewById(R.id.gvd_voucherpin);
        mBarcode = (ImageView) view.findViewById(R.id.barcode);
        mTvBarcodeValue = (TextView) view.findViewById(R.id.barcodeValue);

        mProgressBar = (ProgressBar) view.findViewById(R.id.gvd_regen_progress);

        view.findViewById(R.id.gvd_regen_pin).setOnClickListener(this);
        view.findViewById(R.id.gvd_edit_mode).setOnClickListener(this);

        String vouchercodeval = mVoucher.getGroupVoucherCode();
        String vcode = vouchercodeval.substring(0, 2) + "-" + vouchercodeval.substring(2, 6) + "-" + vouchercodeval.substring(6, 11) + "-" + vouchercodeval.substring(11, 12);

        aq.id(mImgVoucherImage).image(CommonVariables.s3link+"group-vouchers.png", false, true);
        mTvVoucherName.setText(mVoucher.getGroupName());

        mTvNofVoucher.setText(String.valueOf(mVoucherArrayList.size()));
        mTvTotalAmount.setText(CommonFunctions.currencyFormatter(getTotal(mVoucherArrayList)));
        mTvVoucherCode.setText(vcode);
        mTvVoucherPIN.setText(mVoucher.getGroupVoucherPIN());
        mBarcode.setImageBitmap(getBarcodeBitmap(mVoucher.getGroupBarCode()));
        mTvBarcodeValue.setText(mVoucher.getGroupBarCode());

        return view;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gvd_regen_pin: {
                mPIN = mTvVoucherPIN.getText().toString().trim();
                verifySession();
                break;
            }
            case R.id.gvd_edit_mode: {
                GroupVoucherActivity.start(getContext(), mVoucher.getGroupVoucherCode(), mVoucher.getGroupName(), true, mVoucher.getGroupVoucherSession());
                break;
            }
        }
    }


    //create session
    private void verifySession() {
        try {
            int status = cf.getConnectivityStatus(getActivity());
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);

                //call AsynTask to perform network operation on separate thread
                //new HttpAsyncTask().execute(CommonVariables.REGEN_GROUPED_VOUCHER_PIN);
                regenerateGroupVoucherPINV2();
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

                }
                else {
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
    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * **/
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

