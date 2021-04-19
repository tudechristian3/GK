package com.goodkredit.myapplication.fragments.coopassistant.nonmember;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.adapter.coopassistant.nonmember.CoopAssistantMembershipAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantMembershipType;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hk.ids.gws.android.sclick.SClick;

public class CoopAssistantApplicationRequirementsFragment extends BaseFragment implements View.OnClickListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    private NestedScrollView home_body;

    private TextView txv_header;

    private LinearLayout btn_action_container;
    private TextView btn_action;

    private List<CoopAssistantMembershipType> coopMemberShipTypeList = new ArrayList<>();
    private LinearLayout membership_container;
    private RecyclerView rv_membership;
    private CoopAssistantMembershipAdapter rv_membershipadapter;

    private ArrayList<CoopAssistantMembershipType> passedAssistantMembershipList;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_application_requirements, container, false);
        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        txv_header = (TextView) view.findViewById(R.id.txv_header);

        membership_container = (LinearLayout) view.findViewById(R.id.membership_container);
        rv_membership = (RecyclerView) view.findViewById(R.id.rv_membership);

        btn_action_container = (LinearLayout) view.findViewById(R.id.btn_action_container);
        btn_action_container.setVisibility(View.VISIBLE);
        btn_action = (TextView) view.findViewById(R.id.btn_action);
        btn_action.setText("CONTINUE");
        btn_action.setOnClickListener(this);

        //NO RESULT
        noresult = (LinearLayout) view.findViewById(R.id.noresult);
        txv_noresult = (TextView) view.findViewById(R.id.txv_noresult);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        rv_membership.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_membership.setNestedScrollingEnabled(false);

        rv_membershipadapter = new CoopAssistantMembershipAdapter(getViewContext(), CoopAssistantApplicationRequirementsFragment.this);
        rv_membership.setAdapter(rv_membershipadapter);

        getCoopMemberShipType();
    }

    //MEMBERSHIP TYPE
    private void getCoopMemberShipType() {
        coopMemberShipTypeList = PreferenceUtils.getCoopMemberShipTypeListPreference(getViewContext(),CoopAssistantMembershipType.KEY_COOPMEMBERSHIPTYPE);

        if(coopMemberShipTypeList.size() > 0) {
            txv_header.setVisibility(View.VISIBLE);
            btn_action_container.setVisibility(View.VISIBLE);
            noresult.setVisibility(View.GONE);
            rv_membershipadapter.updateData(coopMemberShipTypeList);
        } else {
            txv_header.setVisibility(View.GONE);
            btn_action_container.setVisibility(View.GONE);
            noresult.setVisibility(View.VISIBLE);
            txv_noresult.setText("No Membership currently available. Please try again later.");
        }
    }

    //CALLS DURING THE SELECTION
    public void selectCoopDetails(ArrayList<CoopAssistantMembershipType> coopdata) {
        Collections.reverse(coopdata);
        passedAssistantMembershipList = coopdata;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action: {
                if (!SClick.check(SClick.BUTTON_CLICK)) return;

                if(passedAssistantMembershipList != null) {
                    if(passedAssistantMembershipList.size() > 0) {
                        Bundle args = new Bundle();
                        args.putParcelableArrayList("MemberShipSelected", passedAssistantMembershipList);
                        CoopAssistantHomeActivity.start(getViewContext(), 2, args);
                    } else {
                        showToast("Please select an option.", GlobalToastEnum.NOTICE);
                    }
                } else {
                    showToast("Please select an option.", GlobalToastEnum.NOTICE);
                }
                break;
            }
        }
    }
}
