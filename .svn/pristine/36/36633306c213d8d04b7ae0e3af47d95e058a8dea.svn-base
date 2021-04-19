package com.goodkredit.myapplication.activities.schoolmini;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniBulletinFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniDtrFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniFAQFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniGradesFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniMenuFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniMultipleStudentsFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniPaymentLogsDetails;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniPaymentOptionsFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniPaymentsLogsFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniProfileFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniSendMessageFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniSupportFragment;
import com.goodkredit.myapplication.fragments.schoolmini.SchoolMiniTuitionFragment;

public class SchoolMiniActivity extends BaseActivity {

    @SuppressLint("StaticFieldLeak")
    public static SchoolMiniActivity schoolonBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_school_mini);
            //All Initializations
            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setupToolbar();
    }

    private void initData() {
        int mIndexofFragment = getIntent().getIntExtra("index", -1);
        Bundle args = getIntent().getBundleExtra("args");

        displayView(mIndexofFragment, args);

        schoolonBackPressed = this;
    }

    public void displayView(int position, Bundle args) {
        Fragment fragment = null;
        String title;
        try {
            switch (position) {
                case 0: {
                    fragment = new SchoolMiniMenuFragment();
                    title = "";
                    break;
                }

                case 1: {
                    fragment = new SchoolMiniGradesFragment();
                    title = "Grades";
                    break;
                }

                case 2: {
                    fragment = new SchoolMiniMultipleStudentsFragment();
                    title = "Students";
                    break;
                }

                case 3: {
                    fragment = new SchoolMiniTuitionFragment();
                    title = "School Fees";
                    break;
                }
                case 4: {
                    fragment = new SchoolMiniPaymentOptionsFragment();
                    title = "Payment Options";
                    break;
                }

                case 5: {
                    fragment = new SchoolMiniProfileFragment();
                    title = "Profile";
                    break;
                }

                case 6: {
                    fragment = new SchoolMiniBulletinFragment();
                    title = "Bulletin";
                    break;
                }

                case 7: {
                    fragment = new SchoolMiniSupportFragment();
                    title = "Support";
                    break;
                }
                case 8: {
                    fragment = new SchoolMiniFAQFragment();
                    title = "FAQ";
                    break;
                }

                case 9: {
                    fragment = new SchoolMiniSendMessageFragment();
                    title = "Support";
                    break;
                }

                case 10: {
                    fragment = new SchoolMiniPaymentsLogsFragment();
                    title = "Payment History";
                    break;
                }

                case 11: {
                    fragment = new SchoolMiniPaymentLogsDetails();
                    title = "Payment Details";
                    break;
                }

                case 12: {
                    fragment = new SchoolMiniDtrFragment();
                    title = "Student Attendant";
                    break;
                }

                default:
                    fragment = new SchoolMiniMenuFragment();
                    title = "";
                    break;
            }

            if (args != null) {
                fragment.setArguments(args);
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                // set the toolbar title
                getSupportActionBar().setTitle(CommonFunctions.replaceEscapeData(title));
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    public static void start(Context context, int index, Bundle args) {
        Intent intent = new Intent(context, SchoolMiniActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("args", args);
        context.startActivity(intent);
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
}
