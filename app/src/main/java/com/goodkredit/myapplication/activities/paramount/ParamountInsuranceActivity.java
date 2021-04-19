package com.goodkredit.myapplication.activities.paramount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.WindowManager;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.fragments.paramount.ContactInformationFragment;
import com.goodkredit.myapplication.fragments.paramount.PersonalInformationFragment;
import com.goodkredit.myapplication.fragments.paramount.PolicyTypeFragment;
import com.goodkredit.myapplication.fragments.paramount.VehicleDetailsFragment;
import com.goodkredit.myapplication.fragments.paramount.VehicleTypeFragment;

public class ParamountInsuranceActivity extends BaseActivity {

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramount_insurance);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        setupToolbar();
        setTitle("Paramount CTPL");

        int mIndexSelected = getIntent().getIntExtra("index", -1);
        Bundle args = getIntent().getBundleExtra("args");
        displayView(mIndexSelected, args);
    }

    public void displayView(int id, Bundle args) {
        Fragment fragment = null;
        String title = "";
        switch (id) {
            case 1: {
                fragment = new PolicyTypeFragment();
                title = "Policy";
                break;
            }
            case 2: {
                fragment = new VehicleTypeFragment();
                title = "Vehicle Type";
                break;
            }
            case 3: {
                fragment = new VehicleDetailsFragment();
                title = "Vehicle Details";
                break;
            }
            case 4: {
                fragment = new PersonalInformationFragment();
                title = "Personal Information";
                break;
            }
            case 5: {
                fragment = new ContactInformationFragment();
                title = "Contact Information";
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
        Intent intent = new Intent(context, ParamountInsuranceActivity.class);
        intent.putExtra("args", args);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }
}
