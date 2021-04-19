package com.goodkredit.myapplication.fragments.soa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.statementofaccount.SOAActivityDetails;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.PaymentSummary;
import com.goodkredit.myapplication.bean.SubscriberBillSummary;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.EnumMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class SettlementDetailsActivity extends BaseActivity implements View.OnClickListener {

    private DatabaseHandler db;
    private CommonVariables cv;
    private CommonFunctions cf;
    private Context mcontext;

    private TextView billingid;
    private TextView amount;
    private ImageView barcode;
    private TextView barcodeval;
    private TextView billnolbl;
    private TextView amountlbl;

    private MaterialEditText met1;
    private MaterialEditText met2;
    private MaterialEditText met3;
    private MaterialEditText met4;
    private MaterialEditText met5;
    private MaterialEditText met8;

    LinearLayout lineardetails;

    private SubscriberBillSummary bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement_details);
        try {

            setupToolbarWithTitle("Bill Details");

            mcontext = this;

            db = new DatabaseHandler(this);

            lineardetails = (LinearLayout) findViewById(R.id.lineardetails);
            billingid = (TextView) findViewById(R.id.billingid);
            amount = (TextView) findViewById(R.id.amount);
            barcode = (ImageView) findViewById(R.id.barcode);
            barcodeval = (TextView) findViewById(R.id.barcodeValueNi);
            billnolbl = (TextView) findViewById(R.id.billnolbl);
            amountlbl = (TextView) findViewById(R.id.amountlbl);

            met1 = (MaterialEditText) findViewById(R.id.met1);
            met2 = (MaterialEditText) findViewById(R.id.met2);
            met3 = (MaterialEditText) findViewById(R.id.met3);
            met4 = (MaterialEditText) findViewById(R.id.met4);
            met5 = (MaterialEditText) findViewById(R.id.met5);
            met8 = (MaterialEditText) findViewById(R.id.met8);

            if (getIntent().hasExtra("OBJ")) {
                setupToolbarWithTitle("Bill Details");
                bill = getIntent().getParcelableExtra("OBJ");
                billingid.setText(bill.getBillingID());
                amount.setText(CommonFunctions.currencyFormatter(String.valueOf(bill.getAmount())));
                billnolbl.setText("BILLING ID");
                amountlbl.setText("DUE AMOUNT");
                // barcode image
                Bitmap bitmap = null;
                try {
                    bitmap = encodeAsBitmap(bill.getBillingID(), BarcodeFormat.CODE_128, 750, 300);
                    barcode.setImageBitmap(bitmap);
                    barcodeval.setText(bill.getBillingID());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                met1.setText(bill.getGuarantorID());
                met2.setText(bill.getGuarantorName());
                met3.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(bill.getStatementDate())));
                met4.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(bill.getDueDate())));
                met5.setText(CommonFunctions.currencyFormatter(String.valueOf(bill.getAmount())));
                met5.setFloatingLabelText("Due Amount");
                met8.setText(CommonFunctions.getDateShortenFromDateTime(CommonFunctions.convertDate(bill.getPeriodFrom())) + " - " + CommonFunctions.getDateShortenFromDateTime(CommonFunctions.convertDate(bill.getPeriodTo())));


            } else {
                setupToolbarWithTitle("Payment Details");
                PaymentSummary payment = getIntent().getParcelableExtra("OBJ2");
                billingid.setText(payment.getPaymentTxnNo());
                amount.setText(CommonFunctions.currencyFormatter(String.valueOf(payment.getAmount())));
                billnolbl.setText("PAYMENT TXN.#");
                amountlbl.setText("AMOUNT PAID");
                // barcode image
                Bitmap bitmap = null;
                try {
                    bitmap = encodeAsBitmap(payment.getPaymentTxnNo(), BarcodeFormat.CODE_128, 750, 300);
                    barcode.setImageBitmap(bitmap);
                    barcodeval.setText(payment.getPaymentTxnNo());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                met1.setText(payment.getCollectedBy());
                met1.setFloatingLabelText("Collected By");
                met2.setText(payment.getCollectionMedium());
                met2.setFloatingLabelText("Collection Medium");
                met3.setText(payment.getPaymentOption());
                met3.setFloatingLabelText("Payment Option");
                met4.setText(CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(payment.getDateTimeIN())));
                met4.setFloatingLabelText("Payment Date");
                met5.setText(CommonFunctions.currencyFormatter(String.valueOf(payment.getAmount())));
                met5.setFloatingLabelText("Amount Paid");
                met8.setVisibility(View.GONE);
            }

            findViewById(R.id.btn_show_payment_channels).setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_soa_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (getIntent().hasExtra("OBJ2")) {
            menu.findItem(R.id.action_view_bills).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public static void start(Context context, SubscriberBillSummary bill) {
        Intent intent = new Intent(context, SettlementDetailsActivity.class);
        intent.putExtra("OBJ", bill);
        context.startActivity(intent);
    }

    public static void start(Context context, PaymentSummary bill, int flag) {
        Intent intent = new Intent(context, SettlementDetailsActivity.class);
        intent.putExtra("OBJ2", bill);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_view_bills) {
            SOAActivityDetails.start(getViewContext(), bill);
        }
        return super.onOptionsItemSelected(item);
    }

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_payment_channels: {

                break;
            }
        }
    }

    private void getPaymentChannels(){

    }
}
