package com.goodkredit.myapplication.activities.gkstore;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;

import java.util.ArrayList;

public class GKStoreFullScreenActivity extends BaseActivity implements View.OnClickListener, ViewPagerEx.OnPageChangeListener {
    private SliderLayout mSlider;
    private PagerIndicator mPagerIndicator;
    private ArrayList<String> checkmultilist = new ArrayList<>();

    private Toolbar toolbar;

    private LinearLayout cancelbtncontainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gkstore_fullscreen);
            overridePendingTransition(R.anim.slide_up, 0);
            //All Initializations
            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        mSlider = (SliderLayout) findViewById(R.id.productmultiimagesslider);
        mPagerIndicator = (PagerIndicator) findViewById(R.id.prodDetail_custom_indicator2);
    }

    private void initData() {

        Bundle b = getIntent().getExtras();
        checkmultilist = b.getStringArrayList("GKSTOREMULTIIMAGE");

        cancelbtncontainer = (LinearLayout) findViewById(R.id.cancelbtncontainer);
        cancelbtncontainer.setOnClickListener(this);
        gkstoremultiimageslider();

    }

    private void gkstoremultiimageslider() {
        mSlider.removeAllSliders();


        if (!checkmultilist.isEmpty()) {
            for (String imgpath : checkmultilist) {
                TextSliderView textSliderView = new TextSliderView(getViewContext());
                // initialize a SliderLayout
                textSliderView
                        .image(imgpath)
                        .setScaleType(BaseSliderView.ScaleType.CenterInside);

                mSlider.addSlider(textSliderView);
            }
            mSlider.stopAutoCycle();

            mSlider.addOnPageChangeListener(this);

        } else {
            TextSliderView textSliderViewDefault = new TextSliderView(getViewContext());

            textSliderViewDefault
                    .image(R.drawable.gkadvert)
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);
            mSlider.addSlider(textSliderViewDefault);

            mSlider.stopAutoCycle();
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    //VIEW PAGER
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelbtncontainer: {
                finish();
                break;
            }
        }
    }
}
