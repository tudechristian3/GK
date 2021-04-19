package com.goodkredit.myapplication.activities.voting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.fragments.voting.VotesParticipantsFragment;
import com.goodkredit.myapplication.fragments.voting.VotesParticipantsFragmentTwo;
import com.goodkredit.myapplication.fragments.voting.VotesPostEventFragment;

public class VotesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pageantvotes);

            //All Initializations
            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan_promo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        setupToolbar();
    }

    private void initData() {
        int mIndexofFragment = getIntent().getIntExtra("index", -1);
        Bundle args = getIntent().getBundleExtra("args");

        displayView(mIndexofFragment, args);
    }

    public void displayView(int position, Bundle args) {
        Fragment fragment = null;
        String title = "";
        try {
            switch (position) {
                case 0: {
                    fragment = new VotesPostEventFragment();
                    title = "Votes";
                    break;
                }
                case 1: {
                    fragment = new VotesParticipantsFragment();
                    title = "Participants";
                    break;
                }
                case 2: {
                    fragment = new VotesParticipantsFragmentTwo();
                    title = "Event Details";
                    break;
                }
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
                getSupportActionBar().setTitle(title);
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    public static void start(Context context, int index, Bundle args) {
        Intent intent = new Intent(context, VotesActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if (item.getItemId() == R.id.action_view_history) {
            Intent intent = new Intent(getViewContext(),VotesHistoryActivity.class);
            startActivity(intent);
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
