package com.goodkredit.myapplication.activities.schoolmini;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantPaymentOptionsActivity;
import com.goodkredit.myapplication.activities.gkearn.GKEarnHomeActivity;
import com.goodkredit.myapplication.activities.gkearn.GKEarnPaymentOptionsActivity;
import com.goodkredit.myapplication.activities.gkearn.stockist.GKEarnStockistPackagesDetailsActivity;
import com.goodkredit.myapplication.activities.vouchers.rfid.RFIDPaymentOptionsActvity;
import com.goodkredit.myapplication.adapter.prepaidrequest.BarcodeFormatPagerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.PaymentChannelsActivity;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPayment;
import com.goodkredit.myapplication.utilities.CustomTypefaceSpan;

public class SchoolMiniBillingReferenceActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_BILLING_ID = "billingid";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_FROM = "from";

    private String mBillingID;
    private String mAmount;

    private TextView txvShowPaymentChannels;
    private TextView txvBillingNumberLabel;
    private TextView txvBillingNumberValue;
    private TextView txvBilling;
    private TextView txvInstructions;
    private LinearLayout linear_amount_container;
    private TextView txvTotalAmountLabel;
    private TextView txvTotalAmountValue;

    private ViewPager viewpager;
    private ImageView left;
    private ImageView right;

    private int MAX_PAGE = 4;

    private String merchantname = "";

    private View view_schoolminibilling;
    private LinearLayout layout_schoolminibilling;

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //PAYMENT CHANNEL NOTE FOR COOP
    private TextView txv_payment_channel_note;
    private View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolmini_billing_reference);
        init();
        initData();
    }

    public static void start(Context context, SchoolMiniPayment schoolminipayment, String amount, String from) {
        Intent intent = new Intent(context, SchoolMiniBillingReferenceActivity.class);
        intent.putExtra(KEY_BILLING_ID, schoolminipayment.getMerchantReferenceNo());
        intent.putExtra(KEY_AMOUNT, amount);
        intent.putExtra(KEY_FROM, from);
        context.startActivity(intent);
    }

    private void init() {
        merchantname = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCMERCHANTNAME);

        setupToolbarWithTitle(CommonFunctions.replaceEscapeData(merchantname));

        mBillingID = getIntent().getStringExtra(KEY_BILLING_ID);
        mAmount = getIntent().getStringExtra(KEY_AMOUNT);

        txvBilling = (TextView) findViewById(R.id.txvBilling);
        txvBillingNumberValue = (TextView) findViewById(R.id.txvBillingNumberValue);
        txvBillingNumberLabel = (TextView) findViewById(R.id.txvBillingNumberLabel);
        txvShowPaymentChannels = (TextView) findViewById(R.id.txvShowPaymentChannels);
        txvShowPaymentChannels.setOnClickListener(this);
        txvInstructions = (TextView) findViewById(R.id.txvInstructions);
        linear_amount_container = findViewById(R.id.linear_amount_container);
        txvTotalAmountLabel = (TextView) findViewById(R.id.txvTotalAmountLabel);
        txvTotalAmountValue = (TextView) findViewById(R.id.txvTotalAmountValue);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        left = (ImageView) findViewById(R.id.left);
        left.setOnClickListener(this);
        right = (ImageView) findViewById(R.id.right);
        right.setOnClickListener(this);

        view_schoolminibilling = (View) findViewById(R.id.view_schoolminibilling);
        layout_schoolminibilling = (LinearLayout) findViewById(R.id.layout_schoolminibilling);
        txv_payment_channel_note = (TextView) findViewById(R.id.txv_payment_channel_note);
        view = (View) findViewById(R.id.view);

        if (getIntent().getStringExtra(KEY_FROM) != null) {
            if (getIntent().getStringExtra(KEY_FROM).equals("dropOffOrderAdapter")) {
                view_schoolminibilling.setVisibility(View.GONE);
                layout_schoolminibilling.setVisibility(View.GONE);

                txv_payment_channel_note.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);

            } else if (getIntent().getStringExtra(KEY_FROM).equals("CoopAssistantPaymentOptionsActivity")
                    || getIntent().getStringExtra(KEY_FROM).equals("CoopAssistantApplicationDetailsFragment")
                    || getIntent().getStringExtra(KEY_FROM).equals(GKEarnPaymentOptionsActivity.GKEARN_PAYMENTOPTION_ACTIVITY)
                    || getIntent().getStringExtra(KEY_FROM).equals(GKEarnStockistPackagesDetailsActivity.GKEARN_STOCKISTPACKAGEDETAILS)
                    || getIntent().getStringExtra(KEY_FROM).equals(GKEarnHomeActivity.GKEARN_VALUE_FROMTOPUPBUTTON)) {

                view_schoolminibilling.setVisibility(View.GONE);
                layout_schoolminibilling.setVisibility(View.GONE);

                txv_payment_channel_note.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);

            } else if (getIntent().getStringExtra(KEY_FROM).equals(RFIDPaymentOptionsActvity.RFID_PAYMENTOPTION_ACTIVITY)) {

                view_schoolminibilling.setVisibility(View.GONE);
                layout_schoolminibilling.setVisibility(View.GONE);
                linear_amount_container.setVisibility(View.GONE);

                txv_payment_channel_note.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
            } else {
                view_schoolminibilling.setVisibility(View.GONE);
                layout_schoolminibilling.setVisibility(View.GONE);

                txv_payment_channel_note.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initData() {
        txvBilling.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "Billing"));
        if (getIntent().getStringExtra(KEY_FROM).equals(RFIDPaymentOptionsActvity.RFID_PAYMENTOPTION_ACTIVITY)) {
            txvBillingNumberLabel.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "RFID CARD NUMBER"));
        } else {
            txvBillingNumberLabel.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "BILLING NUMBER"));
        }
        txvBillingNumberValue.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", mBillingID));
        txvTotalAmountLabel.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "TOTAL AMOUNT TO PAY"));
        txvTotalAmountValue.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", CommonFunctions.currencyFormatter(mAmount)));
        txvShowPaymentChannels.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", "SHOW PAYMENT CHANNELS"));

        setUpInstructions();
        setUpBarcodePager();

        mdb = new DatabaseHandler(getViewContext());

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

                        if (getIntent().getStringExtra(KEY_FROM).equals("CoopAssistantPaymentOptionsActivity")) {

                            CoopAssistantPaymentOptionsActivity coopPaymentOptions =
                                    CoopAssistantPaymentOptionsActivity.coopPaymentOptions;

                            if (coopPaymentOptions != null) {
                                coopPaymentOptions.returntoHomeFragment();
                            }

                        } else if (getIntent().getStringExtra(KEY_FROM).equals(GKEarnPaymentOptionsActivity.GKEARN_PAYMENTOPTION_ACTIVITY)) {
                            GKEarnPaymentOptionsActivity paymentOptions = GKEarnPaymentOptionsActivity.paymentOptions;

                            if (paymentOptions != null) {
                                paymentOptions.returntoHome();
                            }

                        } else if (getIntent().getStringExtra(KEY_FROM).equals(RFIDPaymentOptionsActvity.RFID_PAYMENTOPTION_ACTIVITY)) {
                            RFIDPaymentOptionsActvity paymentOptions = RFIDPaymentOptionsActvity.paymentOptions;

                            if (paymentOptions != null) {
                                paymentOptions.finish();
                            }

                        } else {
                            //SCHOOL PAYMENT REQUEST
                            Boolean schoolpaymentrequest = PreferenceUtils.getBooleanPreference(getViewContext(), "schoolpaymentrequest");
                            if (schoolpaymentrequest) {
                                PreferenceUtils.removePreference(getViewContext(), "schoolpaymentrequest");
                                SchoolMiniActivity.schoolonBackPressed.onBackPressed();
                            }
                        }

                        finish();
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
