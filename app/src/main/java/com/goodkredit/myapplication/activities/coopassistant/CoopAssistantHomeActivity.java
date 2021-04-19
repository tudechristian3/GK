package com.goodkredit.myapplication.activities.coopassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.coopassistant.help.CoopGuideFragment;
import com.goodkredit.myapplication.fragments.coopassistant.member.CoopAssistantBillDetailsFragment;
import com.goodkredit.myapplication.fragments.coopassistant.member.CoopAssistantLoanFormFragment;
import com.goodkredit.myapplication.fragments.coopassistant.member.CoopAssistantLoanTransactionDetailsFragment;
import com.goodkredit.myapplication.fragments.coopassistant.member.CoopAssistantLoanTransactionsFragment;
import com.goodkredit.myapplication.fragments.coopassistant.member.CoopAssistantPaymentDetailsFragment;
import com.goodkredit.myapplication.fragments.coopassistant.member.CoopAssistantReferAFriendFragment;
import com.goodkredit.myapplication.fragments.coopassistant.member.CoopAssistantSOAFragment;
import com.goodkredit.myapplication.fragments.coopassistant.nonmember.CoopAssistantApplicationDetailsFragment;
import com.goodkredit.myapplication.fragments.coopassistant.support.CoopAssistantFAQFragment;
import com.goodkredit.myapplication.fragments.coopassistant.support.CoopAssistantSendMessageFragment;
import com.goodkredit.myapplication.fragments.coopassistant.support.CoopAssistantSupportFragment;
import com.goodkredit.myapplication.fragments.coopassistant.member.CoopAssistantMemberProfileFragment;
import com.goodkredit.myapplication.fragments.coopassistant.nonmember.CoopAssistantApplicationFormFragment;
import com.goodkredit.myapplication.fragments.coopassistant.CoopAssistantEBulletinFragment;
import com.goodkredit.myapplication.fragments.coopassistant.nonmember.CoopAssistantApplicationRequirementsFragment;
import com.goodkredit.myapplication.fragments.coopassistant.CoopAssistantHomeFragment;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.fragments.coopassistant.CoopAssistantLoanFragment;

public class CoopAssistantHomeActivity extends BaseActivity {
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_coopassistant_home);

            int mIndexSelected = getIntent().getIntExtra("index", -1);
            Bundle args = getIntent().getBundleExtra("args");

            setupToolbar();

            displayView(mIndexSelected, args);
        } catch (Exception e) {

            e.getLocalizedMessage();
            e.printStackTrace();
        }
    }

    public void displayView(int id, Bundle args) {
        fragment = null;
        String title = "";

        switch (id) {
            case 0: {
                fragment = new CoopAssistantHomeFragment();
                break;
            }
            case 1: {
                fragment = new CoopAssistantApplicationRequirementsFragment();
                title = "Membership";
                break;
            }

            case 2: {
                fragment = new CoopAssistantApplicationFormFragment();
                title = "New Application";
                break;
            }

            case 3: {
                fragment = new CoopAssistantLoanFragment();
                title = "Loan Services";
                break;
            }

            case 4: {
                fragment = new CoopAssistantEBulletinFragment();
                title = "Bulletin";
                break;
            }

            case 5: {
                fragment = new CoopAssistantMemberProfileFragment();
                title = "Member Profile";
                break;
            }

            case 6: {
                fragment = new CoopAssistantSupportFragment();
                title = "Support";
                break;
            }

            case 7: {
                fragment = new CoopAssistantFAQFragment();
                title = "FAQ";
                break;
            }

            case 8: {
                fragment = new CoopAssistantSendMessageFragment();
                title = "Support";
                break;
            }

            case 9: {
                fragment = new CoopAssistantSOAFragment();
                title = "Statement of Account";
                break;
            }

            case 10: {
                fragment = new CoopAssistantBillDetailsFragment();
                title = "Bill Details";
                break;
            }

            case 11: {
                fragment = new CoopAssistantReferAFriendFragment();
                title = "Refer A Friend";
                break;
            }

            case 12: {
                fragment = new CoopAssistantPaymentDetailsFragment();
                title = "Payment Details";
                break;
            }

            case 13: {
                fragment = new CoopAssistantLoanFormFragment();
                title = "";
                break;
            }

            case 14: {
                fragment = new CoopAssistantLoanTransactionsFragment();
                title = "Loan Transactions";
                break;
            }

            case 15: {
                fragment = new CoopAssistantLoanTransactionDetailsFragment();
                title = "Loan Transaction Details";
                break;
            }

            case 16: {
                fragment = new CoopAssistantApplicationDetailsFragment();
                title = "Application Details";
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

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        if(fragment instanceof CoopAssistantLoanFragment){
            getMenuInflater().inflate(R.menu.generic_menu, paramMenu);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.generic_help) {
            showHelpGuide();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showHelpGuide() {
        (new CoopGuideFragment()).show(getSupportFragmentManager(), "Help");
    }

    @Override
    public void onBackPressed() {
        boolean isPreviousPage = false;

        if(fragment instanceof CoopAssistantApplicationFormFragment) {
            isPreviousPage = ((CoopAssistantApplicationFormFragment)fragment).onBackPressed();
        } else {
            isPreviousPage = true;
        }

        if(isPreviousPage) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            } else {
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        }
    }

    public static void start(Context context, int index, Bundle args) {
        Intent intent = new Intent(context, CoopAssistantHomeActivity.class);
        intent.putExtra("args", args);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }
}
