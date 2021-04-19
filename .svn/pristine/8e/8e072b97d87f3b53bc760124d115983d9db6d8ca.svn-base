package com.goodkredit.myapplication.fragments.prepaidrequest;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.adapter.prepaidrequest.BarcodeFormatPagerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.CustomTypefaceSpan;

/**
 * Created by User-PC on 10/24/2017.
 */

public class BillingVirtualVoucherFragment extends BaseFragment implements View.OnClickListener {

    private TextView txvShowPaymentChannels;
    private TextView txvBillingNumberLabel;
    private TextView txvBillingNumberValue;
    private TextView txvBilling;
    private TextView txvInstructions;
    private TextView txvTotalAmountLabel;
    private TextView txvTotalAmountValue;

    private String billingid;
    private String totalamount;

    private ViewPager viewpager;
    private ImageView left;
    private ImageView right;

    private int MAX_PAGE = 4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billing_virtual_voucher, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                getActivity().finish();
                //((VoucherPrepaidRequestActivity) getViewContext()).displayView(0, null);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(View view) {
        txvBilling = (TextView) view.findViewById(R.id.txvBilling);
        txvBillingNumberValue = (TextView) view.findViewById(R.id.txvBillingNumberValue);
        txvBillingNumberLabel = (TextView) view.findViewById(R.id.txvBillingNumberLabel);
        txvShowPaymentChannels = (TextView) view.findViewById(R.id.txvShowPaymentChannels);
        txvShowPaymentChannels.setOnClickListener(this);
        txvInstructions = (TextView) view.findViewById(R.id.txvInstructions);
        txvTotalAmountLabel = (TextView) view.findViewById(R.id.txvTotalAmountLabel);
        txvTotalAmountValue = (TextView) view.findViewById(R.id.txvTotalAmountValue);

        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        left = (ImageView) view.findViewById(R.id.left);
        left.setOnClickListener(this);
        right = (ImageView) view.findViewById(R.id.right);
        right.setOnClickListener(this);
    }

    private void initData() {
        billingid = getArguments().getString("billingid");
        totalamount = getArguments().getString("totalamount");

        txvBilling.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "Billing"));

        txvBillingNumberLabel.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "BILLING NUMBER"));
        txvBillingNumberValue.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", billingid));

        txvTotalAmountLabel.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "TOTAL AMOUNT"));
        txvTotalAmountValue.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", CommonFunctions.currencyFormatter(totalamount)));

        txvShowPaymentChannels.setText(CommonFunctions.setTypeFace(getContext(), "fonts/Roboto-Medium.ttf", "SHOW PAYMENT CHANNELS"));
        setUpInstructions();
        setUpBarcodePager();
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(MAX_PAGE);
        viewPager.setAdapter(new BarcodeFormatPagerAdapter(getChildFragmentManager(), getViewContext(), billingid));
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
        Typeface newFont = Typeface.createFromAsset(getContext().getAssets(), font);
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
        switch (v.getId()) {
            case R.id.txvShowPaymentChannels: {
                ((VoucherPrepaidRequestActivity) getViewContext()).displayView(2, billingid, totalamount);
                break;
            }
            case R.id.left: {

                int currentItem = viewpager.getCurrentItem();

                if (currentItem == 0) {

                } else if (currentItem > 0) {

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
}
