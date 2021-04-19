package com.goodkredit.myapplication.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.whatsnew.WhatsNewVPagerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;

import q.rorbin.badgeview.QBadgeView;

public class WhatsNewFragment extends BaseFragment {

    private ViewPager view_pager;
    private TabLayout tabs;

    private QBadgeView badgeeJump;

    private GlobalDialogs globalDialogs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whats_new, container, false);
        view_pager = view.findViewById(R.id.view_pager);
        view_pager.setOffscreenPageLimit(2);
        view_pager.setAdapter(new WhatsNewVPagerAdapter(getChildFragmentManager(), getViewContext()));
        if (!isGPSEnabled(getViewContext())) {
            gpsNotEnabledDialogNew();
        }
        tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(view_pager);


        return view;
    }

    private void gpsNotEnabledDialogNew() {
        globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("Notice", "GPS is disabled in your device." +
                        "\nWould you like to enable it?",
                "Cancel", "Go to Settings", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);
        View closebtn = globalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                showToast("GPS must be enabled to avail the service.", GlobalToastEnum.WARNING);
            }
        });

        View btndoubleone = globalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                showToast("GPS must be enabled to avail the service.", GlobalToastEnum.WARNING);
            }
        });

        View btndoubletwo = globalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_notification_0).setVisible(false);
        menu.findItem(R.id.action_notification_1).setVisible(false);
        menu.findItem(R.id.action_notification_2).setVisible(false);
        menu.findItem(R.id.action_notification_3).setVisible(false);
        menu.findItem(R.id.action_notification_4).setVisible(false);
        menu.findItem(R.id.action_notification_5).setVisible(false);
        menu.findItem(R.id.action_notification_6).setVisible(false);
        menu.findItem(R.id.action_notification_7).setVisible(false);
        menu.findItem(R.id.action_notification_8).setVisible(false);
        menu.findItem(R.id.action_notification_9).setVisible(false);
        menu.findItem(R.id.action_notification_9plus).setVisible(false);
        menu.findItem(R.id.action_process_queue).setVisible(false);
        menu.findItem(R.id.sortitem).setVisible(false);
        menu.findItem(R.id.summary).setVisible(false);
        menu.findItem(R.id.group_voucher).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
    }
}
