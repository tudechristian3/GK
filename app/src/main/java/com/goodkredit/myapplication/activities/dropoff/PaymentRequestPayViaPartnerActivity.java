package com.goodkredit.myapplication.activities.dropoff;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.adapter.prepaidrequest.BarcodeFormatPagerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.PaymentChannelsActivity;
import com.goodkredit.myapplication.model.dropoff.PaymentRequest;
import com.goodkredit.myapplication.utilities.CustomTypefaceSpan;

public class PaymentRequestPayViaPartnerActivity extends BaseActivity implements View.OnClickListener {

    private PaymentRequest paymentrequest = null;

    private TextView txvshowpaymentchannel;
    private TextView txvInstructions;
    private TextView txvTotalAmountValue;
    private TextView txvBillingNumberValue;

    private String billingid;
    private double amount;

    private ViewPager viewpager;
    private ImageView left;
    private ImageView right;

    private int MAX_PAGE = 4;

    Bundle args = new Bundle();
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropoff_paymentrequestviapartner);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        try {
            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void init() {
        setupToolbar();

        txvshowpaymentchannel = (TextView) findViewById(R.id.txvShowPaymentChannels);
        txvshowpaymentchannel.setOnClickListener(this);

        txvInstructions = (TextView) findViewById(R.id.txvInstructions);
        txvTotalAmountValue = (TextView) findViewById(R.id.txvTotalAmountValue);
        txvBillingNumberValue = (TextView) findViewById(R.id.txvBillingNumberValue);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        left = (ImageView) findViewById(R.id.left);
        left.setOnClickListener(this);
        right = (ImageView) findViewById(R.id.right);
        right.setOnClickListener(this);
    }

    private void initData() {
        args = getIntent().getExtras();
        from = args.getString("from");
        paymentrequest = args.getParcelable(PaymentRequest.KEY_PAYMENTREQUEST);

        amount = paymentrequest.getTotalAmount();
        txvTotalAmountValue.setText(CommonFunctions.currencyFormatter(String.valueOf(amount)));

        if(paymentrequest.getPaymentType().isEmpty() || paymentrequest.getPaymentType().equals(".")){
            billingid = args.getString("billingreferenceno");
            txvBillingNumberValue.setText(billingid);
        } else{
            if(paymentrequest.getPaymentType().equalsIgnoreCase("PAY VIA PARTNER")){
                billingid = paymentrequest.getBillingID();
                txvBillingNumberValue.setText(billingid);
            } else{
                //payment via gkvoucher goes here
            }
        }

        setUpInstructions();
        setUpBarcodePager();
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(MAX_PAGE);
        viewPager.setAdapter(new BarcodeFormatPagerAdapter(((FragmentActivity)getViewContext()).getSupportFragmentManager(), getViewContext(), billingid));
    }

    private void setUpBarcodePager() {
        setupViewPager(viewpager);
    }

    private void setUpInstructions() {

        String str_0 = "Show the";
        SpannableStringBuilder ssb = new SpannableStringBuilder(str_0);
        applyFontStyle(ssb, 0, ssb.length(), "fonts/RobotoCondensed-Regular.ttf");
        applyColor(ssb, 0, ssb.length(), ContextCompat.getColor(getViewContext(), R.color.color_8A8A8A));

        ssb.append(" ");

        String str_1 = "BILLING \nREFERENCE #, BARCODE";
        ssb.append(str_1);
        applyFontStyle(ssb, ssb.length() - str_1.length(), ssb.length(), "fonts/Roboto-Bold.ttf");
        applyColor(ssb, ssb.length() - str_1.length(), ssb.length(), ContextCompat.getColor(getViewContext(), R.color.color_8A8A8A));
//
        ssb.append(" ");

        String str_2 = "or\n";
        ssb.append(str_2);
        applyFontStyle(ssb, ssb.length() - str_2.length(), ssb.length(), "fonts/RobotoCondensed-Regular.ttf");
        applyColor(ssb, ssb.length() - str_2.length(), ssb.length(), ContextCompat.getColor(getViewContext(), R.color.color_8A8A8A));

        //ssb.append(" ");

        String str_3 = "QR CODE";
        ssb.append(str_3);
        applyFontStyle(ssb, ssb.length() - str_3.length(), ssb.length(), "fonts/Roboto-Bold.ttf");
        applyColor(ssb, ssb.length() - str_3.length(), ssb.length(), ContextCompat.getColor(getViewContext(), R.color.color_8A8A8A));

        ssb.append(" ");

        String str_4 = "below to any GoodKredit \nPayment Channels";
        ssb.append(str_4);
        applyFontStyle(ssb, ssb.length() - str_4.length(), ssb.length(), "fonts/RobotoCondensed-Regular.ttf");
        applyColor(ssb, ssb.length() - str_4.length(), ssb.length(), ContextCompat.getColor(getViewContext(), R.color.color_8A8A8A));

        txvInstructions.setText(ssb, TextView.BufferType.EDITABLE);
    }

    private void applyFontStyle(SpannableStringBuilder ssb, int start, int end, String font) {
        Typeface newFont = Typeface.createFromAsset(getViewContext().getAssets(), font);
        ssb.setSpan(new CustomTypefaceSpan("", newFont), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void applyColor(SpannableStringBuilder ssb, int start, int end, int color) {
        // Create a span that will make the text red
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(
                color);
        // Apply the color span
        ssb.setSpan(
                colorSpan,            // the span to add
                start,                // the start of the span (inclusive)
                end,                  // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder
        // SPAN_EXCLUSIVE_EXCLUSIVE means to not extend the span when additional
        // text is added in later
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txvShowPaymentChannels: {
                PaymentChannelsActivity.start(getViewContext());
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if(from.equals("fromPaymentOptions")){
                showWarningDialog();
            } else{
//                Intent intent = new Intent(getViewContext(), PaymentRequestActivity.class);
//                startActivity(intent);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showWarningDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("Are you sure you want to close the page?")
                .positiveText("Close")
                .cancelable(false)
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        PaymentRequestActivity.start(getViewContext(), 1, null, args);
//                        proceedtoMainActivity();
                        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        Intent intent = new Intent(getViewContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        getViewContext().startActivity(intent);
                }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        if(from.equals("fromPaymentOptions")){
            showWarningDialog();
        } else{
            finish();
        }
    }


}
