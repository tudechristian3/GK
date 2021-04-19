package com.goodkredit.myapplication.activities.fairchild;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

public class FairChildVoteInstructionsActivity extends BaseActivity implements View.OnClickListener {

    private TextView btn_fairchild_vote;

    public static FairChildVoteInstructionsActivity finishfairInstructionsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fairchild_vote_instructions);

            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    private void init() {
        setupToolbar();

        btn_fairchild_vote = (TextView) findViewById(R.id.btn_fairchild_vote);
        btn_fairchild_vote.setOnClickListener(this);
    }

    private void initData() {
        finishfairInstructionsActivity = this;

        //SETUP TOOLBAR TITLE
        setUpToolBarTitle();
    }

    private void setUpToolBarTitle() {
        String title = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceName");

        if(!title.trim().equals("")) {
            setTitle(CommonFunctions.replaceEscapeData(title));
        } else {
            setTitle("FAIR CHILD");
        }
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
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        super.onBackPressed();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fairchild_vote: {
                Intent intent = new Intent(getViewContext(), FairChildVoteActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
