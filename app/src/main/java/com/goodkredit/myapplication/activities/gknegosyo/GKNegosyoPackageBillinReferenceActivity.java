package com.goodkredit.myapplication.activities.gknegosyo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.goodkredit.myapplication.model.GKNegosyoApplicationStatus;
import com.goodkredit.myapplication.utilities.CustomTypefaceSpan;

public class GKNegosyoPackageBillinReferenceActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_BILLING_ID = "billingid";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_APPLICATION_STATUS = "status";

    private String mBillingID;
    private String mAmount;
    private GKNegosyoApplicationStatus mGKNegosyoApplicationStatus;

    private TextView txvShowPaymentChannels;
    private TextView txvBillingNumberLabel;
    private TextView txvBillingNumberValue;
    private TextView txvBilling;
    private TextView txvInstructions;
    private TextView txvTotalAmountLabel;
    private TextView txvTotalAmountValue;

    private ViewPager viewpager;
    private ImageView left;
    private ImageView right;

    private int MAX_PAGE = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_negosyo_package_billing_reference);
        init();
        initData();
    }

    public static void start(Context context, GKNegosyoApplicationStatus status, String amount) {
        Intent intent = new Intent(context, GKNegosyoPackageBillinReferenceActivity.class);
        intent.putExtra(KEY_BILLING_ID, status.getDataResID());
        intent.putExtra(KEY_AMOUNT, amount);
        intent.putExtra(KEY_APPLICATION_STATUS, status);
        context.startActivity(intent);
    }

    public static void start(Context context, String bilingID, String amount) {
        Intent intent = new Intent(context, GKNegosyoPackageBillinReferenceActivity.class);
        intent.putExtra(KEY_BILLING_ID, bilingID);
        intent.putExtra(KEY_AMOUNT, amount);
        context.startActivity(intent);
    }

    private void init() {
        setupToolbar();

        mBillingID = getIntent().getStringExtra(KEY_BILLING_ID);
        mAmount = getIntent().getStringExtra(KEY_AMOUNT);
        mGKNegosyoApplicationStatus = getIntent().getParcelableExtra(KEY_APPLICATION_STATUS);

        txvBilling = (TextView) findViewById(R.id.txvBilling);
        txvBillingNumberValue = (TextView) findViewById(R.id.txvBillingNumberValue);
        txvBillingNumberLabel = (TextView) findViewById(R.id.txvBillingNumberLabel);
        txvShowPaymentChannels = (TextView) findViewById(R.id.txvShowPaymentChannels);
        txvShowPaymentChannels.setOnClickListener(this);
        txvInstructions = (TextView) findViewById(R.id.txvInstructions);
        txvTotalAmountLabel = (TextView) findViewById(R.id.txvTotalAmountLabel);
        txvTotalAmountValue = (TextView) findViewById(R.id.txvTotalAmountValue);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        left = (ImageView) findViewById(R.id.left);
        left.setOnClickListener(this);
        right = (ImageView) findViewById(R.id.right);
        right.setOnClickListener(this);
    }

    private void initData() {
        txvBilling.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "Billing"));
        txvBillingNumberLabel.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "BILLING NUMBER"));
        txvBillingNumberValue.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", mBillingID));
        txvTotalAmountLabel.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "TOTAL AMOUNT"));
        txvTotalAmountValue.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", CommonFunctions.currencyFormatter(mAmount)));
        txvShowPaymentChannels.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "SHOW PAYMENT CHANNELS"));
        setUpInstructions();
        setUpBarcodePager();
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
        showWarningDialog();
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
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        CommonVariables.VOUCHERISFIRSTLOAD = true;
                        startActivity(intent);
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txvShowPaymentChannels: {
                PaymentChannelsActivity.start(getViewContext());
                break;
            }
            case R.id.left: {
                int currentItem = viewpager.getCurrentItem();
                if (currentItem > 0) {
                    viewpager.setCurrentItem(currentItem - 1, true);
                }
                break;
            }
            case R.id.right: {
                int currentItem = viewpager.getCurrentItem();
                if (currentItem == (MAX_PAGE - 1)) {
                } else {
                    viewpager.setCurrentItem(currentItem + 1, true);
                }
                break;
            }
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(MAX_PAGE);
        viewPager.setAdapter(new BarcodeFormatPagerAdapter(getSupportFragmentManager(), getViewContext(), mBillingID));
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
}
