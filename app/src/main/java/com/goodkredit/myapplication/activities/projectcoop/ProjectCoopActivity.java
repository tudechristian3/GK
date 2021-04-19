package com.goodkredit.myapplication.activities.projectcoop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.projectcoop.ProjectCoopHomeFragment;
import com.goodkredit.myapplication.fragments.projectcoop.ProjectCoopPlayToSaveConfirmationFragment;
import com.goodkredit.myapplication.fragments.projectcoop.ProjectCoopPlayToSaveFragment;
import com.goodkredit.myapplication.fragments.projectcoop.ProjectCoopPlayToSaveHistoryFragment;
import com.goodkredit.myapplication.fragments.projectcoop.ProjectCoopSupportFAQFragment;
import com.goodkredit.myapplication.fragments.projectcoop.ProjectCoopSupportFragment;
import com.goodkredit.myapplication.fragments.projectcoop.ProjectCoopSupportSendMessageFragment;
import com.goodkredit.myapplication.fragments.projectcoop.PushNotificationPromptFragment;

public class ProjectCoopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_coop);

        int mIndexSelected = getIntent().getIntExtra("index", -1);
        Bundle args = getIntent().getBundleExtra("args");

        if (mIndexSelected != 13) {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        }

        setupToolbar();
        setTitle("CPMPC");

        displayView(mIndexSelected, args);
    }

    public void displayView(int id, Bundle args) {
        Fragment fragment = null;
        String title = "";

        switch (id) {
            case 1: {
                fragment = new ProjectCoopHomeFragment();
                title = "CPMPC";
                break;
            }
            case 2: {
                fragment = new ProjectCoopPlayToSaveFragment();
                title = "CPMPC";
                break;
            }
            case 3: {
                fragment = new ProjectCoopPlayToSaveConfirmationFragment();
                title = "CPMPC";
                break;
            }
            case 4: {
                fragment = new ProjectCoopPlayToSaveHistoryFragment();
                title = "CPMPC";
                break;
            }
            case 5: {
                fragment = new ProjectCoopSupportFragment();
                title = "CPMPC";
                break;
            }
            case 6: {
                fragment = new ProjectCoopSupportSendMessageFragment();
                title = "CPMPC";
                break;
            }
            case 7: {
                fragment = new ProjectCoopSupportFAQFragment();
                title = "CPMPC";
                break;
            }
            case 8: {
                fragment = new PushNotificationPromptFragment();
                title = "Notification";
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
        Intent intent = new Intent(context, ProjectCoopActivity.class);
        intent.putExtra("args", args);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

}
