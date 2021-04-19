package com.goodkredit.myapplication.fragments.dropoff;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.dropoff.ViewDropOffAdapter;
import com.goodkredit.myapplication.base.BaseFragment;

public class DropOffFragment extends BaseFragment {

    private TabLayout tablayout;
    private ViewPager viewpager;

    private String from;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dropoff, container, false);

        viewpager = (ViewPager) view.findViewById(R.id.viewpager);

        setupViewPager();

        tablayout = (TabLayout) view.findViewById(R.id.tabs);

//        LinearLayout linearLayout = (LinearLayout)tablayout.getChildAt(0);
//        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        GradientDrawable drawable = new GradientDrawable();
//        drawable.setColor(ContextCompat.getColor(getViewContext(), R.color.color_E9E9E9));
//        drawable.setSize(1, 1);
//        linearLayout.setDividerPadding(10);
//        linearLayout.setDividerDrawable(drawable);

        tablayout.setupWithViewPager(viewpager);

        return view;
    }

    private void setupViewPager() {
        viewpager.setOffscreenPageLimit(2);
        viewpager.setAdapter(new ViewDropOffAdapter(getChildFragmentManager(), getViewContext()));
    }


}
