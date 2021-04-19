package com.goodkredit.myapplication.activities.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.fragments.gkappintro.FragmentAppIntroItem;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.V2Utils;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created by Ban_Lenovo on 2/27/2017.
 */

public class GKAppIntroActivity extends AppIntro {

    private static final String str_menu = "Menu";
    private static final String str_merchants = "View Merchants";
    private static final String str_functions = "Additional Functions";
    private static final String str_service = "Services";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add slides fragments here
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        addSlide(FragmentAppIntroItem.newInstance(str_menu, R.string.menu_icon, R.drawable.newqt1));
        addSlide(FragmentAppIntroItem.newInstance(str_merchants, R.string.merchants, R.drawable.newqt2));
        addSlide(FragmentAppIntroItem.newInstance(str_functions, R.string.floating_button, R.drawable.newqt3));
        addSlide(FragmentAppIntroItem.newInstance(str_service, R.string.pay_by_qr, R.drawable.newqt4));
        addSlide(FragmentAppIntroItem.newInstance(str_service, R.string.prepaid_load, R.drawable.newqt5));
        addSlide(FragmentAppIntroItem.newInstance(str_service, R.string.pay_bills, R.drawable.newqt6));

        showSkipButton(true);

        if (getIntent().getStringExtra("SOURCE").equals("MAIN")) {
            setSkipText(V2Utils.setTypeFace(this, V2Utils.ROBOTO_REGULAR, "EXIT"));
        } else {
            setSkipText(V2Utils.setTypeFace(this, V2Utils.ROBOTO_REGULAR, "SKIP"));
        }

        showStatusBar(false);
        //setBarColor(getResources().getColor(R.color.color_qt_silver));
        setBarColor(getResources().getColor(R.color.color_transparent));
        setIndicatorColor(getResources().getColor(R.color.color_qt_selected_color),
                getResources().getColor(R.color.color_qt_unselected_color));
        setColorDoneText(getResources().getColor(R.color.colorTextGrey));
        setColorSkipButton(getResources().getColor(R.color.colorTextGrey));
        setNextArrowColor(getResources().getColor(R.color.colorTextGrey));

        setFadeAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        if (getIntent().getStringExtra("SOURCE").equals("MAIN")) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        } else {
            goToMain();
        }
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        goToMain();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    private void goToMain() {
        PreferenceUtils.saveAppIntroStatus(this, true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra("SOURCE").equals("MAIN")) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        } else {
            goToMain();
        }
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
