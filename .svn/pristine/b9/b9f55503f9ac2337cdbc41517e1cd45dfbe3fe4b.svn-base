package com.goodkredit.myapplication.activities.promo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.PromoQRDetails;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;

/**
 * Created by Ban_Lenovo on 12/15/2017.
 */

public class SuccessPromoActivity extends BaseActivity implements View.OnClickListener {

    public static String PROMO_DETAILS = "promoqrdetails";

    private PromoQRDetails mPromoQRDetails;

    private ImageView mPromoImage;
    private TextView mPromoDenom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_promo);
        mPromoQRDetails = getIntent().getParcelableExtra(PROMO_DETAILS);
        init();
    }

    private void init() {
        try {
            mPromoQRDetails = getIntent().getParcelableExtra(PROMO_DETAILS);

            mPromoImage = (ImageView) findViewById(R.id.promo_image);
            mPromoDenom = (TextView) findViewById(R.id.promo_denom);
            findViewById(R.id.promo_done).setOnClickListener(this);

            Glide.with(getViewContext())
                    .load(mPromoQRDetails.getPromoImageURL())
                    .apply(RequestOptions.centerCropTransform()
                            .error(R.drawable.promo_default)
                            .placeholder(R.drawable.promo_default))
                    .into(mPromoImage);

            mPromoDenom.setText("You've been awarded ₱" +
                    CommonFunctions.currencyFormatter(
                            String.valueOf(
                                    computeAmount(mPromoQRDetails.getVoucherDenom(), mPromoQRDetails.getVoucherQuantity())
                            )) + " voucher");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double computeAmount(String voucherDenom, int voucherQuantity) {
        double amount = 0;
        amount = Double.parseDouble(voucherDenom) * (double) voucherQuantity;
        return amount;
    }

    public static void start(Context context, PromoQRDetails promoqrdetails) {
        Intent intent = new Intent(context, SuccessPromoActivity.class);
        intent.putExtra(PROMO_DETAILS, promoqrdetails);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.promo_done: {
                onBackPressed();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getViewContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        startActivity(intent);
        super.onBackPressed();
    }
}
