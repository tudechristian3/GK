package com.goodkredit.myapplication.activities.dropoff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.fragments.dropoff.DropOffCompletedFragment;
import com.goodkredit.myapplication.fragments.dropoff.DropOffFragment;
import com.goodkredit.myapplication.fragments.dropoff.DropOffPendingFragment;

public class DropOffActivity extends BaseActivity implements View.OnClickListener {

    private int mDisplayedView = 0;
    private Fragment fragment = null;
    private Bundle globalargs;

    private LinearLayout btn_make_new_drop_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropoff);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        try {
            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setupToolbar();

        btn_make_new_drop_off = findViewById(R.id.btn_make_new_drop_off);
        btn_make_new_drop_off.setOnClickListener(this);
    }

    private void initData() {
        int mIndexofFragment = getIntent().getIntExtra("index", -1);
        Bundle args = getIntent().getBundleExtra("args");
        displayView(mIndexofFragment, args);
    }

    public void displayView(int position, Bundle args) {
        String title = "";
        mDisplayedView = position;

        try {
            switch (position) {
                case 1: {
                    fragment = new DropOffFragment();
                    break;
                }
                case 2: {
                    fragment = new DropOffPendingFragment();
                    break;
                }
                case 3: {
                    fragment = new DropOffCompletedFragment();
                }
            }

            if (args != null) {
                fragment.setArguments(args);
                globalargs = args;
            }

            if (fragment != null) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container_body, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(Context context, int index, GKService service, Bundle args) {
        Intent intent = new Intent(context, DropOffActivity.class);
        intent.putExtra("index", index);
        intent.putExtra(GKService.KEY_SERVICE_OBJ, service);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_make_new_drop_off: {
                Intent intent = new Intent(getViewContext(), MakeNewDropOffActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
