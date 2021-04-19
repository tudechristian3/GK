package com.goodkredit.myapplication.activities.gkearn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.gkearn.GKEarnRegisterFragment;
import com.goodkredit.myapplication.fragments.gkearn.GKEarnRegisteredMemberFragment;
import com.goodkredit.myapplication.fragments.gkearn.GKEarnConversionAndTopupHistoryDetailsFragment;
import com.goodkredit.myapplication.fragments.gkearn.GKEarnUnRegisteredMemberFragment;
import com.goodkredit.myapplication.fragments.gkearn.GKEarnInviteAFriendFragment;

public class GKEarnHomeActivity extends BaseActivity {

    public static final String GKEARN_FRAGMENT_REGISTERED_MEMBER = "REGISTEREARNMEMBER";
    public static final String GKEARN_FRAGMENT_UNREGISTERED_MEMBER = "UNREGISTEREDEARNMEMBER";
    public static final String GKEARN_FRAGMENT_REGISTER = "REGISTER";
    public static final String GKEARN_FRAGMENT_INVITE_A_FRIEND = "INVITEAFRIEND";
    public static final String GKEARN_FRAGMENT_CONVERSION_AND_TOPUP_HISTORY_DETAILS = "CONVERSIONANDTOPUPHISTORYDETAILS";

    public static final String GKEARN_KEY_FROM = "HOME";
    public static final String GKEARN_VALUE_FROMHOME = "FROMHOME";
    public static final String GKEARN_VALUE_FROMREGISTRATION = "FROMREGISTRATION";
    public static final String GKEARN_VALUE_FROMTOPUPBUTTON = "FROMTOPUPBUTTON";

    //Conversion & Top Up Points
    public static final String GKEARN_KEY_PARCELABLE_TOPUP = "KEY_PARCELABLE_TOPUP";
    public static final String GKEARN_KEY_PARCELABLE_CONVERSION = "KEY_PARCELABLE_CONVERSION";
    public static final String GKEARN_KEY_TOPUP_OR_CONVERSION_TO_DETAILS = "KEY_TOPUP_TO_DETAILS";
    public static final String GKEARN_VALUE_TOPUP_TO_DETAILS = "VALUE_TOPUP_TO_DETAILS";
    public static final String GKEARN_VALUE_CONVERSION_TO_DETAILS = "VALUE_CONVERSION_TO_DETAILS";

    //GKEARN TYPE
    public static final String GKEARN_VALUE_TYPE_TOPUP = "TOP UP";
    public static final String GKEARN_VALUE_TYPE_APPLY_AS_STOCKIST = "APPLY AS STOCKIST";


    public String fragmentView = "";
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_gkearn_home);

            String mOptionSelected = getIntent().getStringExtra("fragment");
            Bundle args = getIntent().getBundleExtra("args");

            setupToolbar();

            displayView(mOptionSelected, args);

        } catch (Exception e) {
            e.getLocalizedMessage();
            e.printStackTrace();
        }
    }

    public void displayView(String selectOption, Bundle args) {
        Fragment fragment = null;
        String title = "";

        switch (selectOption) {
            case GKEARN_FRAGMENT_REGISTERED_MEMBER: {
                fragment = new GKEarnRegisteredMemberFragment();
                title = "GK EARN";
                break;
            }

            case GKEARN_FRAGMENT_UNREGISTERED_MEMBER : {
                fragment = new GKEarnUnRegisteredMemberFragment();
                title = "GK EARN";
                break;
            }

            case GKEARN_FRAGMENT_REGISTER : {
                fragment = new GKEarnRegisterFragment();
                title = "GK EARN";
                break;
            }

            case GKEARN_FRAGMENT_INVITE_A_FRIEND: {
                fragment = new GKEarnInviteAFriendFragment();
                title = "GK EARN";
                break;
            }

            case GKEARN_FRAGMENT_CONVERSION_AND_TOPUP_HISTORY_DETAILS: {
                fragment = new GKEarnConversionAndTopupHistoryDetailsFragment();
                title = "GK EARN";
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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        } else {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }
    }

    public static void start(Context context, String fragment, Bundle args) {
        Intent intent = new Intent(context, GKEarnHomeActivity.class);
        intent.putExtra("args", args);
        intent.putExtra("fragment", fragment);
        context.startActivity(intent);

    }

}
