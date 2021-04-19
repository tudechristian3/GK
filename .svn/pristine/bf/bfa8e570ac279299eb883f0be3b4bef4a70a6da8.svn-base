package com.goodkredit.myapplication.fragments.vouchers;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.viewpager.widget.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.transfer.TransferThruBorrower;
import com.goodkredit.myapplication.activities.transfer.TransferVerifyEmail;
import com.goodkredit.myapplication.activities.transfer.TransferVerifySMS;
import com.goodkredit.myapplication.activities.vouchers.AvailableVoucherDetailsTransaction;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.barcodes.BarcodeVPagerAdapter;
import com.goodkredit.myapplication.fragments.barcodes.FragmentBarcodeFormatCODABAR;
import com.goodkredit.myapplication.fragments.barcodes.FragmentBarcodeFormatCode39;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.EnumMap;
import java.util.Map;

import jp.wasabeef.blurry.Blurry;

//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;

public class AvailableVoucherDetails extends BaseActivity {
    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;

    private TextView vouchercode;
    private TextView voucherpin;
    private TextView amount;
    private TextView remainingbalance;
    private TextView used;
    private TextView barcodeValue;
    private TextView whitebarcodeValue;
    private ImageView barcode;
    private ImageView voucherimage;
    private ImageView voucherTag;
    private LinearLayout linearView;
    private LinearLayout options;
    static Animation rotate_forward, rotate_backward, fab_open, fab_close, fade_in;
    private FloatingActionButton fab;
    TableRow transfersms;
    TableRow transferemail;
    TableRow transferborrower;
    TextView transfervoucher;
    LinearLayout progress;
    TextView generatepin;

    String serialnoval = "";
    String vouchercodeval = "";
    String voucherpinval = "";
    String amountval = "";
    String remainingamount = "";
    String productidval = "";
    String sessionidval = "";
    String borroweridval = "";
    String userid = "";
    String imei = "";
    String barcodeval = "";

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    static boolean isFabOpen = false;

    private boolean isClicked = false;

    Dialog dialog;
    AQuery aq;

    ViewPager vPager;
    RelativeLayout left;
    RelativeLayout right;
    String barcode_data;

    RelativeLayout barcodesLayout;
    ImageView arrowdown;

    RelativeLayout rootView;

    private Voucher voucher;

    //UNIFIED SESSION
    private String sessionid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_voucher_details);

        mcontext = this;

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

//        try {
        //re initialize db
        db = new DatabaseHandler(this);
        rootView = (RelativeLayout) findViewById(R.id.rootView);
        //get the passed value
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            voucher = extras.getParcelable("VOUCHER_OBJECT");
            serialnoval = voucher.getVoucherSerialNo();
        }

        //set toolbar
        setupToolbar();
        //initialize elements
        aq = new AQuery(this);
        vouchercode = (TextView) findViewById(R.id.vouchercode);
        voucherpin = (TextView) findViewById(R.id.voucherpin);
        amount = (TextView) findViewById(R.id.amount);
        remainingbalance = (TextView) findViewById(R.id.remainingbalance);
        used = (TextView) findViewById(R.id.used);
        barcode = (ImageView) findViewById(R.id.barcode);
        options = (LinearLayout) findViewById(R.id.options);
        linearView = (LinearLayout) findViewById(R.id.optionswrap);
        voucherimage = (ImageView) findViewById(R.id.voucherimage);
        transfervoucher = (TextView) findViewById(R.id.transfervoucher);
        progress = (LinearLayout) findViewById(R.id.progress);
        generatepin = (TextView) findViewById(R.id.generatepin);
        barcodeValue = (TextView) findViewById(R.id.barcodeValueNi);
        whitebarcodeValue = (TextView) findViewById(R.id.whitebarcodeValue);

        arrowdown = (ImageView) findViewById(R.id.arrowdown);
        voucherTag = (ImageView) findViewById(R.id.prepaid_tag);

        rotate_forward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        //create a dialog confirmation
        dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_transferoptions);

        transfersms = (TableRow) dialog.findViewById(R.id.transferSMS);
        transferemail = (TableRow) dialog.findViewById(R.id.transferEMAIL);
        transferborrower = (TableRow) dialog.findViewById(R.id.transferBorrower);


        vPager = (ViewPager) findViewById(R.id.viewpager);
        left = (RelativeLayout) findViewById(R.id.left);
        right = (RelativeLayout) findViewById(R.id.right);

        //get account information
        Cursor c = db.getAccountInformation(db);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                borroweridval = c.getString(c.getColumnIndex("borrowerid"));
                userid = c.getString(c.getColumnIndex("mobile"));
                imei = c.getString(c.getColumnIndex("imei"));

            } while (c.moveToNext());
        }


        //get data from local database
        getVoucherInfoLocal();


//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        /************
         * TRIGGERS
         ************/

        //click transfer through sms
        transfersms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getBaseContext(), TransferVerifySMS.class);
                    intent.putExtra("VOUCHERCODE", vouchercodeval);
                    intent.putExtra("AMOUNT", amountval);

                    startActivity(intent);
                } catch (Exception e) {
                }

            }
        });

        //click transfer through email
        transferemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getBaseContext(), TransferVerifyEmail.class);
                    intent.putExtra("VOUCHERCODE", vouchercodeval);
                    intent.putExtra("AMOUNT", amountval);
                    startActivity(intent);
                } catch (Exception e) {
                }

            }
        });

        //click transfer through borrower
        transferborrower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getBaseContext(), TransferThruBorrower.class);
                    intent.putExtra("VOUCHERCODE", vouchercodeval);
                    intent.putExtra("AMOUNT", amountval);
                    startActivity(intent);

                } catch (Exception e) {
                }

            }
        });


    }


    /*
     * FUNCTIONS
     * */

    public void getVoucherInfoLocal() {

//        try {
        Cursor cursor = db.getSpecificVoucher(db, serialnoval);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                vouchercodeval = cursor.getString(cursor.getColumnIndex("vouchercode"));
                voucherpinval = cursor.getString(cursor.getColumnIndex("voucherpin"));
                amountval = cursor.getString(cursor.getColumnIndex("amount"));
                remainingamount = cursor.getString(cursor.getColumnIndex("remainingamount"));
                productidval = cursor.getString(cursor.getColumnIndex("productid"));
                barcodeval = cursor.getString(cursor.getColumnIndex("barcode"));

            } while (cursor.moveToNext());

            //calculate the used
            double usedamount = Double.parseDouble(amountval) - Double.parseDouble(remainingamount);

            //format voucher code
            String vcode = vouchercodeval.substring(0, 2) + "-" + vouchercodeval.substring(2, 6) + "-" + vouchercodeval.substring(6, 11) + "-" + vouchercodeval.substring(11, 12);
            vouchercode.setText(vcode);
            voucherpin.setText(voucherpinval);

            //setting the balances
            DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
            amount.setText(formatter.format(Double.parseDouble(amountval)));
            remainingbalance.setText(formatter.format(Double.parseDouble(remainingamount)));
            used.setText(formatter.format(usedamount));

            //setting the logo
            aq.id(voucherimage).image(cv.s3link + productidval + "-voucher-design.jpg", false, true);

            if (voucher.getExtra3().equals(".")) {
                voucherTag.setVisibility(View.GONE);
            } else {
                voucherTag.setVisibility(View.VISIBLE);
            }


            //hide transfer voucher if its being use already
            if (usedamount > 0 || voucher.getIsTransferable().equals("1")) {
                transfervoucher.setVisibility(View.GONE);
            } else {
                transfervoucher.setVisibility(View.VISIBLE);
            }
        }


        arrowdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcode.performClick();
            }
        });

        // create barcode
        //barcode_data = vouchercodeval + voucherpinval + String.valueOf((int) (Double.parseDouble(remainingamount) * 100));
        barcode_data = barcodeval;
        barcodeValue.setText(barcode_data);
        whitebarcodeValue.setText(barcode_data);

        // barcode image
        Bitmap bitmap = null;
        try {
            bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.CODE_128, 750, 300);
            barcode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        barcodesLayout = (RelativeLayout) findViewById(R.id.barcodeLayout);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodesLayout.setVisibility(View.VISIBLE);
                setupViewPager(vPager);
                if (!isClicked) {
                    isClicked = true;
                    Blurry.with(getApplicationContext())
                            .radius(2)
                            .sampling(8)
                            .color(Color.argb(80, 0, 0, 0))
                            .async()
                            .onto(rootView);
                }
            }
        });


        barcodesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodesLayout.setVisibility(View.GONE);
                Blurry.delete(rootView);
                isClicked = false;
//                    for (int i = 0; i < rootView.getChildCount(); i++) {
//                        View child = rootView.getChildAt(i);
//                        child.setEnabled(true);
//                    }

            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vPager.getCurrentItem() == 0) {
                    vPager.setCurrentItem(0, true);
                } else {
                    vPager.setCurrentItem(0, true);
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vPager.getCurrentItem() == 1) {
                    vPager.setCurrentItem(1, true);
                } else {
                    vPager.setCurrentItem(1, true);
                }
            }
        });

        cursor.close();

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public String getCode() {
        return barcode_data;
    }


    private void setupViewPager(ViewPager viewPager) {
        BarcodeVPagerAdapter adapter = new BarcodeVPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FragmentBarcodeFormatCODABAR.newInstance(barcode_data), barcode_data);
        adapter.addFragment(FragmentBarcodeFormatCode39.newInstance(barcode_data), barcode_data);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
//        try {
        if (item.getItemId() == android.R.id.home) {
            if (barcodesLayout.getVisibility() == View.VISIBLE && barcodesLayout != null) {
                barcodesLayout.setVisibility(View.GONE);
                Blurry.delete(rootView);
                isClicked = false;
            } else {
                finish();
            }
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (barcodesLayout.getVisibility() == View.VISIBLE) {
            barcodesLayout.setVisibility(View.GONE);
            Blurry.delete(rootView);
            isClicked = false;
        } else {
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        try {
        Cursor c = db.getAccountInformation(db);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                borroweridval = c.getString(c.getColumnIndex("borrowerid"));
                userid = c.getString(c.getColumnIndex("mobile"));
                imei = c.getString(c.getColumnIndex("imei"));
            } while (c.moveToNext());
        }
        c.close();

//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }


    }


    public void openTransferOption(View view) {
        // dialog.show();
        Intent intent = new Intent(getBaseContext(), TransferThruBorrower.class);
        intent.putExtra("VOUCHERCODE", vouchercodeval);
        intent.putExtra("AMOUNT", amountval);
        intent.putExtra("from","INDIVIDUAL");
        startActivity(intent);
    }

    public void openVoucherLogs(View view) {
//        try {
        Intent intent = new Intent(mcontext, AvailableVoucherDetailsTransaction.class);
        intent.putExtra("SERIALNO", serialnoval);
        startActivity(intent);
//        } catch (Exception e) {
//        }

    }

    public void generateNewPIN(View view) {
//        try {
        int status = cf.getConnectivityStatus(this);
        if (status == 0) { //no connection
            showToast("No internet connection.", GlobalToastEnum.NOTICE);
        } else { //has connection proceed

            Cursor cursor = db.getSpecificVoucher(db, serialnoval);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    voucherpinval = cursor.getString(cursor.getColumnIndex("voucherpin"));
                } while (cursor.moveToNext());

                //3.
                verifySession();
            }
            cursor.close();

        }
//        } catch (Exception e) {
//        } finally {
//        }

    }

    /*************
     * FUNCTIONS
     ************/

    public void verifySession() {
        //call AsynTask to perform network operation on separate thread
        //4.
        new HttpAsyncTask().execute(cv.GENERATENEWPIN);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            generatepin.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = cf.getSha1Hex(imei + userid + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("vouchercode", vouchercodeval);
                jsonObject.accumulate("voucherpin", voucherpinval);
                jsonObject.accumulate("version", cv.version);


                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                cf.hideDialog();
                e.printStackTrace();
            }

            return cf.POST(urls[0], json);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            cf.hideDialog();
            try {
                progress.setVisibility(View.GONE);
                generatepin.setVisibility(View.VISIBLE);
                if (result.equals("001")) {
                    showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
                } else if (result.equals("003")) {
                    showToast("Invalid Session.", GlobalToastEnum.NOTICE);
                } else if (result.equals("004")) {
                    showToast("Voucher code not found or old PIN did not match.", GlobalToastEnum.WARNING);
                } else if (result.equals("005")) {
                    showToast("Voucher invalid. A pending transaction with this voucher is being processed.", GlobalToastEnum.NOTICE);
                } else if (result.equals("error")) {
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {

                    JSONArray jsonArr = new JSONArray(result);
                    JSONObject jsonObj = jsonArr.getJSONObject(0);
                    String vcode = jsonObj.getString("VoucherCode");
                    String vpin = jsonObj.getString("VoucherPIN");
                    String rbalance = jsonObj.getString("RemainingBalance");
                    String bcode = jsonObj.getString("BarCode");

                    voucherpin.setText(vpin);
                    db.updateParticularVoucher(db, vpin, vcode, rbalance, bcode);

                    //reload voucher
                    getVoucherInfoLocal();
                }


            } catch (Exception e) {
            }

        }
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
//        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
//        } catch (IllegalArgumentException iae) {
//            // Unsupported format
//            return null;
//        }
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

}
