package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;

public class CoopAssistantSOAFragment extends BaseFragment {

    private ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_soa, container, false);
        try {
            viewPager = (ViewPager) view.findViewById(R.id.viewpager);

            setUpViewPager();

            TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void setUpViewPager(){
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new ViewCoopAssistantSOAPagerAdapter(getChildFragmentManager(), getContext()));
    }
}
