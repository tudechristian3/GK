package com.goodkredit.myapplication.activities.skycable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.skycable.SkycableHomeFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableLinkAccountFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableNewApplicationFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableNewApplicationPlansFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableNewApplicationRegistrationsDetailsFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableNewApplicationRegistrationsFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycablePayPerViewFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycablePayPerViewHistoryFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycablePushNotificationFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableSOADetailsFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableSOAWebviewFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableSubscribePayPerViewConfirmationFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableSubscribePayPerViewFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableSupportFAQFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableSupportFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableSupportSendMessageFragment;

public class SkyCableActivity extends BaseActivity {

    public static SkyCableActivity skycablefinishActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sky_cable);
        try {
            skycablefinishActivity = this;

            int mIndexSelected = getIntent().getIntExtra("index", -1);
            Bundle args = getIntent().getBundleExtra("args");

            if (mIndexSelected != 13) {
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }

            setupToolbar();
            setTitle("Skycable");

            displayView(mIndexSelected, args);
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    public void displayView(int id, Bundle args) {
        Fragment fragment = null;
        String title = "";
        switch (id) {
            case 1: {
                fragment = new SkycableHomeFragment();
//                title = "SKYCABLE";
                title = "SKYCABLE";
                break;
            }
            case 2: {
                fragment = new SkycableNewApplicationFragment();
//                title = "SKYCABLE";
                title = "SKYCABLE";
                break;
            }
            case 3: {
//                fragment = new SkycableSOAFragment();
                fragment = new SkycableSOAWebviewFragment();
//                title = "SKYCABLE";
                title = "SKYCABLE";
                break;
            }
            case 4: {
                fragment = new SkycablePayPerViewFragment();
//                title = "SKYCABLE";
                title = "SKYCABLE";
                break;
            }
            case 5: {
                fragment = new SkycableSubscribePayPerViewFragment();
//                title = "SUBSCRIBE";
                title = "SKYCABLE";
                break;
            }
            case 6: {
                fragment = new SkycableSubscribePayPerViewConfirmationFragment();
//                title = "SUBSCRIBE";
                title = "SKYCABLE";
                break;
            }
            case 7: {
                fragment = new SkycableLinkAccountFragment();
//                title = "SKYCABLE";
                title = "SKYCABLE";
                break;
            }
            case 8: {
                fragment = new SkycablePayPerViewHistoryFragment();
                title = "SUBSCRIPTION HISTORY";
                break;
            }
            case 9: {
                fragment = new SkycableSOADetailsFragment();
//                title = "SKYCABLE";
                title = "SKYCABLE";
                break;
            }
            case 10: {
                fragment = new SkycableNewApplicationRegistrationsFragment();
//                title = "SKYCABLE REGISTRATIONS";
                title = "SKYCABLE REGISTRATIONS";
                break;
            }
            case 11: {
                fragment = new SkycableNewApplicationRegistrationsDetailsFragment();
//                title = "SKYCABLE REGISTRATIONS";
                title = "SKYCABLE REGISTRATIONS";
                break;
            }
            case 12: {
                fragment = new SkycableSupportFragment();
//                title = "SKYCABLE SUPPORT";
                title = "SKYCABLE SUPPORT";
                break;
            }
            case 13: {
                fragment = new SkycablePushNotificationFragment();
//                title = "SKYCABLE";
                title = "SKYCABLE";
                break;
            }
            case 14: {
                fragment = new SkycableSupportFAQFragment();
                title = "FAQ";
                break;
            }
            case 15: {
                fragment = new SkycableSupportSendMessageFragment();
                title = "SUPPORT";
                break;
            }
            case 16: {
                fragment = new SkycableNewApplicationPlansFragment();
//                title = "SKYCABLE";
                title = "SKYCABLE";
                break;
            }
        }

        if (args != null) {
            fragment.setArguments(args);
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (fragment != null) {
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.addToBackStack(null);
            } else {
                fragmentTransaction.add(R.id.container_body, fragment);
            }
            fragmentTransaction.commit();

            // set the toolbar title
            setActionBarTitle(title);
        }
    }

    public void setActionBarTitle(String title) {
        setTitle(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", title));
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }

    }

    public static void start(Context context, int index, Bundle args) {
        Intent intent = new Intent(context, SkyCableActivity.class);
        intent.putExtra("args", args);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Logger.debug("antonhttp", "ACTIVITY requestCode = " + requestCode);
//    }
}
