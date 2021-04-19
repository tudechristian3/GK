package com.goodkredit.myapplication.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoRedirectionActivity;
import com.goodkredit.myapplication.activities.transactions.ViewOthersTransactionsActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Merchant;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.OnSwipeTouchListener;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentConfirmation extends BaseActivity implements View.OnClickListener {

    DatabaseHandler db;
    Context mcontext;


    //COMMON
    private String borrowerid = "";
    private String userid = "";
    private String sessionid = "";
    private String imei = "";

    static String amountopayval = "";
    static String mobileval = "";
    static String networkval = "";
    static String producttypeval = "";
    static String productcodeval = "";
    static String amountenderedval = "";
    static String changeval = "";
    static String serialused = "";
    static String vouchercodeused = "";
    static String pinused = "";

    static String vouchersession = "";
    static String source = "";


    static TextView mobile;
    static TextView network;
    static TextView product;
    static TextView productcode;
    static TextView amounttopay;
    static TextView amounttendered;
    static TextView amountchange;

    static TableRow networkRow;
    static TableRow prodRow;
    static TableRow prodCodeRow;

    Dialog dialog;
    Dialog progressdialog;
    TextView popsuccessclose;
    DecimalFormat formatter;

    ImageView successBarcode;
    ImageView successQRCode;
    TextView successMessage;
    TextView barcodeValue;
    TextView dialogNote;
    TextView merchantRefCodeLabel;
    TableRow approvalcodewrap;
    ImageView leftarrow;
    ImageView rightarrow;

    String barcode_data;

    Merchant merchant;
    int currentdelaytime = 0;
    int totaldelaytime = 10000;
    String restransactionno = "";

    private double resellerDiscount = 0;
    private String resellerAmount = "0";

    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;

    private GPSTracker gpsTracker;

    private TextView txvAmount;
    private TextView txvResellerDiscount;

    private boolean checkIfReseller = false;

    //New Variables for SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String processConsummationIndex;
    private String processConsummationAuthenticationid;
    private String processConsummationKeyEncryption;
    private String processConsummationParam;

    private String prepaidStatusIndex;
    private String prepaidStatusAuthenticationid;
    private String prepaidStatusKeyEncryption;
    private String prepaidStatusParam;

    //For Confirm Button
    private Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airtime_payment_confirmation);
        try {
            //set context
            mcontext = this;

            //initialize db
            db = new DatabaseHandler(this);

            //decimal
            formatter = new DecimalFormat("#,###,##0.00");

            //SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            proceed = findViewById(R.id.proceed);

            //set toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            //get passed value from other activity
            Bundle b = getIntent().getExtras();
            if (getIntent().hasExtra("FROMMERCHANTEXPRESS")) {
                Intent intent = getIntent();
                mobileval = intent.getStringExtra("MOBILE");
                source = intent.getStringExtra("FROMMERCHANTEXPRESS");
                vouchersession = intent.getStringExtra("VOUCHERSESSION");
                amountopayval = intent.getStringExtra("AMOUNTTOPAY");
                amountenderedval = intent.getStringExtra("AMOUNTENDERED");
                changeval = intent.getStringExtra("CHANGE");
                merchant = (Merchant) intent.getSerializableExtra("MERCHANT");
                resellerDiscount = Double.valueOf(getIntent().getStringExtra("PAYVIAQRRESELLERDISCOUNT"));
                resellerAmount = getIntent().getStringExtra("GROSSAMOUNT");
            } else {
                source = "PREPAIDLOAD";
                mobileval = b.getString("MOBILE");
                networkval = b.getString("NETWORK");
                producttypeval = b.getString("PRODUCTTYPE");
                productcodeval = b.getString("PRODUCTCODE");
                amountopayval = b.getString("AMOUNTTOPAY");
                amountenderedval = b.getString("AMOUNTENDERED");
                changeval = b.getString("CHANGE");
                serialused = b.getString("SERIALUSED");
                vouchercodeused = b.getString("USEDVOUCHERCODE");
                pinused = b.getString("USEDPIN");
                vouchersession = b.getString("VOUCHERSESSION");
                resellerDiscount = Double.valueOf(getIntent().getStringExtra("PREPAIDLOADINGRESELLERDISCOUNT"));
                resellerAmount = getIntent().getStringExtra("GROSSAMOUNT");
            }

            //initialize elements
            mobile = findViewById(R.id.mobileval);
            network = findViewById(R.id.networkval);
            product = findViewById(R.id.productval);
            productcode = findViewById(R.id.productcodeval);
            amounttopay = findViewById(R.id.amounttopayval);
            amounttendered = findViewById(R.id.amounttenderedval);
            amountchange = findViewById(R.id.amountchangeval);

            networkRow = findViewById(R.id.networkrow);
            prodRow = findViewById(R.id.productrow);
            prodCodeRow = findViewById(R.id.productcoderow);

            txvAmount = findViewById(R.id.txvAmount);
            txvResellerDiscount = findViewById(R.id.txvResellerDiscount);

            txvAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerAmount)));
            txvResellerDiscount.setText(CommonFunctions.currencyFormatter(String.valueOf(resellerDiscount)));

            //setting UI if from merchant express
            if (source.contentEquals("FROMMERCHANTEXPRESS")) {
                networkRow.setVisibility(View.GONE);
                prodRow.setVisibility(View.GONE);
                prodCodeRow.setVisibility(View.GONE);
            }

            //setting value
            if (source.contentEquals("FROMMERCHANTEXPRESS")) {
                mobile.setText("+" + mobileval);
            } else {
                mobile.setText("+63" + mobileval);
            }
            network.setText(networkval);
            product.setText(producttypeval);
            productcode.setText(productcodeval);
            amounttopay.setText(formatter.format(Double.parseDouble(amountopayval)));
            amounttendered.setText(formatter.format(Double.parseDouble(amountenderedval)));
            amountchange.setText(formatter.format(Double.parseDouble(changeval)));

            //get account information
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                } while (cursor.moveToNext());
            }
            cursor.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*---------------*   FUNCTIONS * --------------*/
    private void showDialog(String bcData) {
        //create dialog
        try {
            dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_airtimeconsummation_success);
            popsuccessclose = dialog.findViewById(R.id.popok);
            successBarcode = dialog.findViewById(R.id.successBarcode);
            successMessage = dialog.findViewById(R.id.successMessage);
            barcodeValue = dialog.findViewById(R.id.barcodeValue);
            dialogNote = dialog.findViewById(R.id.dialogNote);
            merchantRefCodeLabel = dialog.findViewById(R.id.merchantRefCodeLabel);
            successQRCode = dialog.findViewById(R.id.successQRCode);
            approvalcodewrap = dialog.findViewById(R.id.approvalcodewrap);
            leftarrow = dialog.findViewById(R.id.leftarrow);
            rightarrow = dialog.findViewById(R.id.rightarrow);
            TextView subject = dialog.findViewById(R.id.subject);

            linearGkNegosyoLayout = dialog.findViewById(R.id.linearGkNegosyoLayout);
            txvGkNegosyoDescription = dialog.findViewById(R.id.txvGkNegosyoDescription);
            txvGkNegosyoDescription.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", getResources().getString(R.string.str_gk_negosyo_prompt)));
            txvGkNegosyoRedirection = dialog.findViewById(R.id.txvGkNegosyoRedirection);
            txvGkNegosyoRedirection.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", "I WANT THIS!"));
            txvGkNegosyoRedirection.setOnClickListener(this);

            if (source.contentEquals("FROMMERCHANTEXPRESS")) {
                // successBarcode.setVisibility(View.VISIBLE);
                successQRCode.setVisibility(View.VISIBLE);
                barcodeValue.setVisibility(View.VISIBLE);
                merchantRefCodeLabel.setVisibility(View.VISIBLE);
                subject.setText("TRANSACTION COMPLETE");
                successMessage.setText("");
                dialogNote.setText("Present this QR code/barcode to the merchant for your payment verification.");
                barcodeValue.setText(bcData);
                approvalcodewrap.setVisibility(View.VISIBLE);
                // barcode image
                Bitmap bitmap = null;
                try {
                    bitmap = encodeAsBitmap(bcData, BarcodeFormat.CODE_128, 600, 300);
                    successBarcode.setImageBitmap(bitmap);

                    bitmap = encodeAsBitmap(bcData, BarcodeFormat.QR_CODE, 600, 300);
                    successQRCode.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (resellerDiscount > 0) {
                    linearGkNegosyoLayout.setVisibility(View.GONE);
                } else {
                    if (PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER).equals("") ||
                            PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID).equals("") ||
                            PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER).equals(".") ||
                            PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID).equals(".")) {
                        linearGkNegosyoLayout.setVisibility(View.VISIBLE);
                    } else {
                        linearGkNegosyoLayout.setVisibility(View.GONE);
                    }
                }
            }
            dialog.show();

            //click close
            popsuccessclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(getViewContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    CommonVariables.VOUCHERISFIRSTLOAD = true;
                    startActivity(intent);
                    CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                }

            });

            //slide left and right to reverse view of the barcode or QR code
            successQRCode.setOnTouchListener(new OnSwipeTouchListener() {
                public void onSwipeRight() {
                    successQRCode.setVisibility(View.GONE);
                    successBarcode.setVisibility(View.VISIBLE);
                }

                public void onSwipeLeft() {
                    successQRCode.setVisibility(View.GONE);
                    successBarcode.setVisibility(View.VISIBLE);
                }
            });

            successBarcode.setOnTouchListener(new OnSwipeTouchListener() {
                public void onSwipeRight() {
                    successQRCode.setVisibility(View.VISIBLE);
                    successBarcode.setVisibility(View.GONE);
                }

                public void onSwipeLeft() {
                    successQRCode.setVisibility(View.VISIBLE);
                    successBarcode.setVisibility(View.GONE);
                }

            });
            leftarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (successQRCode.getVisibility() == View.VISIBLE) {
                        successQRCode.setVisibility(View.GONE);
                        successBarcode.setVisibility(View.VISIBLE);
                    } else {
                        successQRCode.setVisibility(View.VISIBLE);
                        successBarcode.setVisibility(View.GONE);
                    }

                }

            });

            rightarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (successQRCode.getVisibility() == View.VISIBLE) {
                        successQRCode.setVisibility(View.GONE);
                        successBarcode.setVisibility(View.VISIBLE);
                    } else {
                        successQRCode.setVisibility(View.VISIBLE);
                        successBarcode.setVisibility(View.GONE);
                    }
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialogDone(String transactiono, String status, String remarks) {

        dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_airtimeconsummation_success);
        TextView message = dialog.findViewById(R.id.successMessage);
        TextView note = dialog.findViewById(R.id.dialogNote);
        TextView subject = dialog.findViewById(R.id.subject);
        popsuccessclose = dialog.findViewById(R.id.popok);
        TextView retrybtn = dialog.findViewById(R.id.retrybtn);

        linearGkNegosyoLayout = dialog.findViewById(R.id.linearGkNegosyoLayout);
        txvGkNegosyoDescription = dialog.findViewById(R.id.txvGkNegosyoDescription);
        txvGkNegosyoDescription.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", getResources().getString(R.string.str_gk_negosyo_prompt)));
        txvGkNegosyoRedirection = dialog.findViewById(R.id.txvGkNegosyoRedirection);
        txvGkNegosyoRedirection.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/RobotoCondensed-Regular.ttf", "I WANT THIS!"));
        txvGkNegosyoRedirection.setOnClickListener(this);

        if (status.equals("SUCCESS")) {
            subject.setText("SUCCESSFUL LOAD");
            subject.setTextColor(getResources().getColor(R.color.colortoolbar));
            message.setText(remarks);
            note.setText("Thank you for using GoodKredit");
            retrybtn.setVisibility(View.GONE);
            if (resellerDiscount > 0) {
                linearGkNegosyoLayout.setVisibility(View.GONE);
            } else {
                if (PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER).equals("") ||
                        PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID).equals("") ||
                        PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER).equals(".") ||
                        PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID).equals(".")) {
                    linearGkNegosyoLayout.setVisibility(View.VISIBLE);
                } else {
                    linearGkNegosyoLayout.setVisibility(View.GONE);
                }
            }
        } else {
            subject.setText("FAILED LOAD");
            subject.setTextColor(getResources().getColor(R.color.colorred));
            message.setText(remarks);
            note.setText("Please try again.");
            retrybtn.setVisibility(View.VISIBLE);
        }

        dialog.show();

        //click close
        popsuccessclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                dialog.dismiss();
                Intent intent = new Intent(getViewContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                startActivity(intent);
            }

        });

        //click retry
        retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                dialog.dismiss();
                Intent intent = new Intent(getViewContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                startActivity(intent);
                ViewOthersTransactionsActivity.start(getViewContext(), 4);
            }

        });


    }

    private void showDialogNew(String bcData) {
        //create dialog
        try {
            if (source.contentEquals("FROMMERCHANTEXPRESS")) {
                PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_PVSOURCE);
                PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_PVBARCODE);

                PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_PVSOURCE, source);
                PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_PVBARCODE, bcData);

                if (resellerDiscount > 0) {
                    checkIfReseller = true;
                } else {
                    if (PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER).equals("") ||
                            PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID).equals("") ||
                            PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER).equals(".") ||
                            PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID).equals(".")) {
                        checkIfReseller = false;
                    } else {
                        checkIfReseller = true;
                    }
                }

                showGlobalDialogs(bcData, "close", ButtonTypeEnum.SINGLE, checkIfReseller, false, DialogTypeEnum.QRCODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialogNewDone(String status, String remarks) {
        PreferenceUtils.removePreference(getViewContext(), PreferenceUtils.KEY_PVSOURCE);
        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_PVSOURCE, source);
        if (status.equals("SUCCESS")) {
            if (resellerDiscount > 0) {
                checkIfReseller = true;
            } else {
                if (PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER).equals("") ||
                        PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID).equals("") ||
                        PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER).equals(".") ||
                        PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID).equals(".")) {
                    checkIfReseller = false;
                } else {
                    checkIfReseller = true;
                }
            }
            String passedmessage = remarks + "\n\n" + "Thank you for using Goodkredit";

            showGlobalDialogs(passedmessage, "close", ButtonTypeEnum.SINGLE,
                    checkIfReseller, false, DialogTypeEnum.SUCCESS);
        } else {
            String passedmessage = remarks + "\n" + "Please try again.";

            showGlobalDialogs(passedmessage, "retry",
                    ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);
        }
    }

    private void showProgressDialog(Context context, String message) {
        try {

            Logger.debug("PROGRESS", "" + message);

            if (progressdialog == null) {
                progressdialog = new Dialog(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light));
                progressdialog.setCancelable(false);
                progressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressdialog.setContentView(R.layout.pop_processing);
            }


            TextView processingmessage = progressdialog.findViewById(R.id.processingmessage);
            processingmessage.setText(message);
            ImageView iView = progressdialog.findViewById(R.id.imgloader);

            if (iView != null) {
                Glide.with(context)
                        .load(R.drawable.progressloader)
                        .into(iView);
            }
            if (!progressdialog.isShowing()) {
                progressdialog.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hideProgressCustomDialog() {
        try {
            progressdialog.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showOnProcessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("ON PROCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.ONPROCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpProgressLoaderDismissDialog();
                dialog.dismiss();
                dialog.proceedtoMainActivity();
            }
        });
    }

    /********************** ALL FOR BARCODE ******************/
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
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
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

    public void proceedConfirmation(View view) {


        gpsTracker = new GPSTracker(getViewContext());
        if (source.contentEquals("FROMMERCHANTEXPRESS")) {
            verifySession();
        } else {
            if (mobileval.equals("") || networkval.equals("") || producttypeval.equals("") || productcodeval.equals("") || amountopayval.equals("") || amountenderedval.equals("") || changeval.equals("")) {
                showToast("Some data maybe incorrect.", GlobalToastEnum.WARNING);
            } else {
                verifySession();
            }
        }

    }

    /********************** API CALLS ******************/
    private void verifySession() {

        //disable confirmation button
        proceed.setEnabled(false);

        setUpProgressLoader();
        setUpProgressLoaderMessageDialog("Sending request. Please wait...");
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            if (source.contentEquals("FROMMERCHANTEXPRESS")) {
                //new HttpAsyncTask().execute(CommonVariables.PAYNOWMERCHANTEXPRESS);
                processQRConsummationV3();
            } else {
                //new HttpAsyncTask().execute(CommonVariables.PROCESSPAIDLOADING);
                processConsummationV3();
            }
        } else {
            proceed.setEnabled(true);
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    }

//    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            showProgressDialog(mcontext, "Processing request, please wait...");
//
//            setUpProgressLoaderMessageDialog("Processing request, please wait...");
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String json = "";
//            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//            try {
//
//                // build jsonObject
//                JSONObject jsonObject = new JSONObject();
//
//                if (source.contentEquals("FROMMERCHANTEXPRESS")) {
//                    jsonObject.accumulate("sessionid", sessionid);
//                    jsonObject.accumulate("imei", imei);
//                    jsonObject.accumulate("authcode", authcode);
//                    jsonObject.accumulate("merchantid", merchant.getMerchantID());
//                    jsonObject.accumulate("branchid", merchant.getBranchID());
//                    jsonObject.accumulate("branchname", merchant.getBranchName());
//                    jsonObject.accumulate("vouchersession", vouchersession);
//                    jsonObject.accumulate("userid", userid);
//                    jsonObject.accumulate("borrowerid", borrowerid);
//                    jsonObject.accumulate("mobile", userid);
//                    jsonObject.accumulate("hasdiscount", resellerDiscount > 0 ? "1" : "0");
//                    jsonObject.accumulate("servicecode", "PAYVIAQR");
//                    jsonObject.accumulate("grossamount", resellerAmount);
//                    jsonObject.accumulate("longitude", String.valueOf(gpsTracker.getLongitude()));
//                    jsonObject.accumulate("latitude", String.valueOf(gpsTracker.getLatitude()));
//
//                } else {
//                    jsonObject.accumulate("mobile", "0" + mobileval);
//                    jsonObject.accumulate("network", networkval);
//                    jsonObject.accumulate("producttype", producttypeval);
//                    jsonObject.accumulate("productcode", productcodeval);
//                    jsonObject.accumulate("amount", amountopayval);
//                    jsonObject.accumulate("authcode", authcode);
//                    jsonObject.accumulate("userid", userid);
//                    jsonObject.accumulate("borrowerid", borrowerid);
//                    jsonObject.accumulate("imei", imei);
//                    jsonObject.accumulate("sessionid", sessionid);
//                    jsonObject.accumulate("vouchersession", vouchersession);
//                    jsonObject.accumulate("hasdiscount", resellerDiscount > 0 ? "1" : "0");
//                    jsonObject.accumulate("servicecode", PreferenceUtils.getStringPreference(getViewContext(), "prepaidloading_service_code"));
//                    jsonObject.accumulate("grossamount", resellerAmount);
//                    jsonObject.accumulate("longitude", String.valueOf(gpsTracker.getLongitude()));
//                    jsonObject.accumulate("latitude", String.valueOf(gpsTracker.getLatitude()));
//                }
//
//
//                //convert JSONObject to JSON to String
//                json = jsonObject.toString();
//
//            } catch (Exception e) {
//                e.printStackTrace();
////                hideProgressCustomDialog();
//                setUpProgressLoaderDismissDialog();
//            }
//
//            return CommonFunctions.POST(urls[0], json);
//
//        }
//
//        // 2. onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//
//            try {
//
//                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "PREPAIDISPROCESSING";
//
//                JSONObject parentObj = new JSONObject(result);
//                restransactionno = parentObj.getString("data");
//                String message = parentObj.getString("message");
//                String status = parentObj.getString("status");
//
//                if (status.equals("000")) {
//                    if (source.contentEquals("FROMMERCHANTEXPRESS")) {
////                        hideProgressCustomDialog();
//                        setUpProgressLoaderDismissDialog();
////                        showDialog(restransactionno);
//                        showDialogNew(restransactionno);
//                    } else {
//                        //check transaction status here
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                currentdelaytime = currentdelaytime + 1000;
//                                //Do something after 100ms
//                                //new HttpAsyncTask1().execute(CommonVariables.CHECKPREPAIDLOADINGTXNSTAT);
//                                checkPrepaidLoadingTxnStatV2();
//                            }
//                        }, 1000);
//                    }
//
//                } else if (result.contains("<!DOCTYPE html>")) {
////                    hideProgressCustomDialog();
//                    setUpProgressLoaderDismissDialog();
//                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
////                    hideProgressCustomDialog();
//                    setUpProgressLoaderDismissDialog();
//                    showToast(message, GlobalToastEnum.NOTICE);
//                }
//
//            } catch (Exception e) {
////                hideProgressCustomDialog();
//                setUpProgressLoaderDismissDialog();
//                showOnProcessDialog("Your request is still currently being on process." +
//                        "You will receive a notification once it's done." +
//                        "You can monitor your transaction under Usage Menu." +
//                        "Thank you for using GoodKredit.");
//                e.printStackTrace();
//            }
//        }
//
//    }

//    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
//
//            super.onPreExecute();
////            showProgressDialog(mcontext, "Checking status, please wait...");
//            setUpProgressLoaderMessageDialog("Checking status, please wait...");
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String json = "";
//            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//            try {
//                // build jsonObject
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("userid", userid);
//                jsonObject.accumulate("borrowerid", borrowerid);
//                jsonObject.accumulate("authcode", authcode);
//                jsonObject.accumulate("imei", imei);
//                jsonObject.accumulate("sessionid", sessionid);
//                jsonObject.accumulate("transactionno", restransactionno);
//
//                //convert JSONObject to JSON to String
//                json = jsonObject.toString();
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                json = null;
//            }
//
//            return CommonFunctions.POST(urls[0], json);
//
//        }
//
//        // 2. onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//            try {
//
//                JSONObject parentObj = new JSONObject(result);
//                String message = parentObj.getString("message");
//                String status = parentObj.getString("status");
//                String data = parentObj.getString("data");
//                if (status.equals("003")) {
//                    if (currentdelaytime < totaldelaytime) {
//                        //check transaction status here
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                currentdelaytime = currentdelaytime + 1000;
//
//                                new HttpAsyncTask1().execute(CommonVariables.CHECKPREPAIDLOADINGTXNSTAT);
//                            }
//                        }, 1000);
//                    } else {
//                        setUpProgressLoaderDismissDialog();
//                        String passedmessage = " Your prepaid load transaction is still in process." +
//                                "You will receive a notification once it's done." + "\n" + "Thank you for using GoodKredit.";
//
//                        showGlobalDialogs(passedmessage, "close",
//                                ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.ONPROCESS);
//
//                    }
//
//                } else if (status.equals("000")) {
//                    String txnstat = parentObj.getString("data");
//                    String remarks = parentObj.getString("remarks");
//
//                    setUpProgressLoaderDismissDialog();
//                    showDialogNewDone(txnstat, remarks);
//
//
//                } else {
//                    setUpProgressLoaderDismissDialog();
//                    showToast(message, GlobalToastEnum.NOTICE);
//                }
//
//
//            } catch (Exception e) {
//                setUpProgressLoaderDismissDialog();
//                showErrorToast();
//                e.printStackTrace();
//            }
//        }
//    }

    /********************** ONCLICK AND ONSELECTED ******************/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txvGkNegosyoRedirection: {
                GKNegosyoRedirectionActivity.start(getViewContext(), db.getGkServicesData(db, "GKNEGOSYO"));
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /***
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     ***/

    //PROCESS PAYBYQR
    private void processQRConsummationV3() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            if (source.contentEquals("FROMMERCHANTEXPRESS")) {
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("merchantid", merchant.getMerchantID());
                parameters.put("branchid", merchant.getBranchID());
                parameters.put("branchname", merchant.getBranchName());
                parameters.put("mobile", userid);
                parameters.put("vouchersession", vouchersession);
                parameters.put("hasdiscount", resellerDiscount > 0 ? "1" : "0");
                parameters.put("servicecode", "PAYVIAQR");
                parameters.put("grossamount", String.valueOf(resellerAmount));
                parameters.put("longitude", String.valueOf(gpsTracker.getLongitude()));
                parameters.put("latitude", String.valueOf(gpsTracker.getLatitude()));
            }
            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "processQRConsummationV3");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            processQRConsummationV3(processQRConsummationCallback);

        } else {
            proceed.setEnabled(true);
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    }
    private void processQRConsummationV3(Callback<GenericResponse> processQRConsummation) {
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .processQRConsummationV3(
                        authenticationid, sessionid, param
                );
        call.enqueue(processQRConsummation);
    }
    private Callback<GenericResponse> processQRConsummationCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            CommonVariables.PROCESSNOTIFICATIONINDICATOR = "PREPAIDISPROCESSING";
            setUpProgressLoaderDismissDialog();
            proceed.setEnabled(true);
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                String status = response.body().getStatus();
                switch (status) {
                    case "000":
                        restransactionno = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                        if (source.contentEquals("FROMMERCHANTEXPRESS")) {
                            Logger.debug("okhttp", "DATA ::: " + CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData()));
                            showDialogNew(restransactionno);
                        }
                        break;
                    case "003":
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                        break;
                    default:
                        showErrorToast(decryptedMessage);
                        break;
                }
            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            proceed.setEnabled(true);
            t.printStackTrace();
            setUpProgressLoaderDismissDialog();
            showErrorGlobalDialogs();
        }
    };

    //PROCESS PREPAID LOAD
    private void processConsummationV3() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("vouchersession", vouchersession);
            parameters.put("mobile", "0" + mobileval);
            parameters.put("network", networkval.contains("TNT") ? "SMART" : networkval);
            parameters.put("producttype", producttypeval);
            parameters.put("productcode", productcodeval);
            parameters.put("amount", amountopayval);
            parameters.put("hasdiscount", resellerDiscount > 0 ? "1" : "0");
            parameters.put("servicecode", PreferenceUtils.getStringPreference(getViewContext(), "prepaidloading_service_code"));
            parameters.put("grossamount", String.valueOf(resellerAmount));
            parameters.put("longitude", String.valueOf(gpsTracker.getLongitude()));
            parameters.put("latitude", String.valueOf(gpsTracker.getLatitude()));

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            processConsummationIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", processConsummationIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            processConsummationAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            processConsummationKeyEncryption = CommonFunctions.getSha1Hex(processConsummationAuthenticationid + sessionid + "processConsummationV3");
            processConsummationParam = CommonFunctions.encryptAES256CBC(processConsummationKeyEncryption, String.valueOf(paramJson));

            processConsummationV3Object(processConsummationCallback);

        } else {
            proceed.setEnabled(true);
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    }
    private void processConsummationV3Object(Callback<GenericResponse> processQRConsummation) {
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .processConsummationV3(
                        processConsummationAuthenticationid, sessionid, processConsummationParam
                );
        call.enqueue(processQRConsummation);
    }
    private Callback<GenericResponse> processConsummationCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            CommonVariables.PROCESSNOTIFICATIONINDICATOR = "PREPAIDISPROCESSING";
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(processConsummationKeyEncryption, response.body().getMessage());
                String status = response.body().getStatus();
                switch (status) {
                    case "000":
                        restransactionno = CommonFunctions.decryptAES256CBC(processConsummationKeyEncryption, response.body().getData());
                        //check transaction status here
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentdelaytime = currentdelaytime + 1000;
                                //Do something after 100ms
                                //new HttpAsyncTask1().execute(CommonVariables.CHECKPREPAIDLOADINGTXNSTAT);
                                checkPrepaidLoadingTxnStatV2();
                            }
                        }, 1000);
                        break;
                    case "003":
                        proceed.setEnabled(true);
                        setUpProgressLoaderDismissDialog();
                        if (decryptedMessage.contains("Session")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else {
                            showErrorToast(decryptedMessage);
                        }
                        break;
                    default:
                        proceed.setEnabled(true);
                        setUpProgressLoaderDismissDialog();
                        showErrorToast(decryptedMessage);
                        break;
                }
            } else {
                proceed.setEnabled(true);
                setUpProgressLoaderDismissDialog();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
            proceed.setEnabled(true);
            setUpProgressLoaderDismissDialog();
            showErrorGlobalDialogs();
        }
    };


    //CHECK PREPAID LOAD TRANSACTION STAT
    private void checkPrepaidLoadingTxnStatV2() {
        setUpProgressLoaderMessageDialog("Checking status, please wait...");

        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("transactionno", restransactionno);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            prepaidStatusIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", prepaidStatusIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            prepaidStatusAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            prepaidStatusKeyEncryption = CommonFunctions.getSha1Hex(prepaidStatusAuthenticationid + sessionid + "checkPrepaidLoadingTransactionStatusV2");
            prepaidStatusParam = CommonFunctions.encryptAES256CBC(prepaidStatusKeyEncryption, String.valueOf(paramJson));

            checkPrepaidLoadingTxnStatV2Object(checkPrepaidLoadingTxnStatV2Callback);

        } else {
            proceed.setEnabled(true);
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    }
    private void checkPrepaidLoadingTxnStatV2Object(Callback<GenericResponse> checkstatus) {
        Call<GenericResponse> call = RetrofitBuilder.getPrepaidLoadV2API(getViewContext())
                .checkPrepaidLoadingTransactionStatusV2(
                        prepaidStatusAuthenticationid, sessionid, prepaidStatusParam
                );
        call.enqueue(checkstatus);
    }
    private Callback<GenericResponse> checkPrepaidLoadingTxnStatV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(prepaidStatusKeyEncryption, response.body().getMessage());
                String status = response.body().getStatus();
                switch (status) {
                    case "000":
                        setUpProgressLoaderDismissDialog();
                        String data = CommonFunctions.decryptAES256CBC(prepaidStatusKeyEncryption, response.body().getData());
                        String txnstat = CommonFunctions.parseJSON(data, "status");
                        String remarks = CommonFunctions.parseJSON(data, "remarks");

                        showDialogNewDone(txnstat, remarks);

                        break;
                    case "202":
                        if (currentdelaytime < totaldelaytime) {
                            //check transaction status here
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    currentdelaytime = currentdelaytime + 1000;
                                    //Do something after 100ms
                                    checkPrepaidLoadingTxnStatV2();
                                }
                            }, 1000);
                        } else {
                            setUpProgressLoaderDismissDialog();
                            String passedmessage = " Your prepaid load transaction is still in process." +
                                    "You will receive a notification once it's done." + "\n" + "Thank you for using GoodKredit.";

                            showGlobalDialogs(passedmessage, "close",
                                    ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.ONPROCESS);

                        }
                        break;
                    case "003":
                        setUpProgressLoaderDismissDialog();
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                        break;
                    default:
                        proceed.setEnabled(true);
                        setUpProgressLoaderDismissDialog();
                        showErrorToast(decryptedMessage);
                        break;
                }
            } else {
                proceed.setEnabled(true);
                setUpProgressLoaderDismissDialog();
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            proceed.setEnabled(true);
            t.printStackTrace();
            setUpProgressLoaderDismissDialog();
            showErrorGlobalDialogs();
        }
    };

}
