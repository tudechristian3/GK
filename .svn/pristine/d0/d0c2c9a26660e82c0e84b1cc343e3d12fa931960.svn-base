package com.goodkredit.myapplication.activities.coopassistant.nonmember;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.coopassistant.nonmember.ScreenSlidePagerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantInformation;
import com.goodkredit.myapplication.utilities.Logger;

import java.util.ArrayList;

import hk.ids.gws.android.sclick.SClick;
import ru.noties.scrollable.ScrollableLayout;

public class CoopAssistantPMESActivity extends BaseActivity implements View.OnClickListener {

    private static final int NUM_PAGES = 2;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    private LinearLayout btn_action_container;
    private TextView btn_action;

    private String termsandconditions = "";

    public static CoopAssistantPMESActivity coopAssistantPMESActivity;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ScrollableLayout scrollableLayout;

    private CardView layout_viewpresentation;
    public String urlLink = "";
    public String videoLinks = "";
    private ArrayList<String> linksList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_coopassistant_pmes);

            coopAssistantPMESActivity = this;
            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        btn_action_container = findViewById(R.id.btn_action_container);
        btn_action_container.setVisibility(View.VISIBLE);
        btn_action = findViewById(R.id.btn_action);
        btn_action.setText("Continue");
        btn_action.setOnClickListener(this);

        if(getIntent() != null){
            Bundle args = new Bundle();
            args = getIntent().getBundleExtra("args");
            urlLink = args.getString(CoopAssistantInformation.KEY_COOPPMES);
            videoLinks = args.getString("links");

            Logger.debug("okhttp","LINK : "+ urlLink);
            Logger.debug("okhttp","VIDEO LINK : "+ videoLinks);

            linksList = getLinks();
        }

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pmes_Viewpager2);
        viewPager.setUserInputEnabled(false);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pagerAdapter = new ScreenSlidePagerAdapter(this,urlLink,linksList);
        pagerAdapter.createFragment(0);
        viewPager.setAdapter(pagerAdapter);

    }

    private void initData() {
        setupToolbarWithTitle("PMES");
    }

    public void viewpresentation(){
        pagerAdapter = new ScreenSlidePagerAdapter(this,urlLink,linksList);
        viewPager.setCurrentItem(1);
        viewPager.setAdapter(pagerAdapter);
    }


    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, CoopAssistantPMESActivity.class);
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
        if(viewPager.getCurrentItem() == 0){
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }else{
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;

                Bundle args = new Bundle();
                args = getIntent().getBundleExtra("args");
                termsandconditions = args.getString(CoopAssistantInformation.KEY_COOPTERMS);

                if (termsandconditions != null) {
                    if (!termsandconditions.trim().equals("") && !termsandconditions.trim().equals(".")) {
                        args.putString(CoopAssistantInformation.KEY_COOPTERMS, termsandconditions);
                        CoopAssistantTermsandConditionsActivity.start(getViewContext(), args);
                    } else {
                        //args.putString("FROM", CoopAssistantInformation.KEY_COOPPMES);
                        CoopAssistantApplyMemberActivity.start(getViewContext(), args);
                    }
                } else {
                    //args.putString("FROM", CoopAssistantInformation.KEY_COOPPMES);
                    CoopAssistantApplyMemberActivity.start(getViewContext(), args);
                }

                break;
            }
            case R.id.layout_viewpresentation: {

            }
        }
    }


    public ArrayList<String> getLinks(){
        ArrayList<String> list = new ArrayList<>();
        if(videoLinks.isEmpty()){
            list = null;
        }else{
            int count = Integer.parseInt(CommonFunctions.parseXML(videoLinks,"count"));
            for(int x=1;x<=count;x++){
                list.add(CommonFunctions.parseXML(videoLinks,"link"+x));
            }

        }
        return list;
    }

}