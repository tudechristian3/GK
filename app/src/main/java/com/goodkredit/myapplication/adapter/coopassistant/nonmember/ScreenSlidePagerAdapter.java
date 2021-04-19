package com.goodkredit.myapplication.adapter.coopassistant.nonmember;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.goodkredit.myapplication.fragments.coopassistant.member.CoopAssistantViewPMESPDF;
import com.goodkredit.myapplication.fragments.coopassistant.member.CoopPMESVideoPresentation;
import com.goodkredit.myapplication.utilities.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public  class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 2;
    private String urllink;
    private ArrayList<String> links= new ArrayList<>();

    public ScreenSlidePagerAdapter(FragmentActivity fa, String link, ArrayList<String> linkList) {
        super(fa);
        urllink = link;
        links = linkList;
        Logger.debug("okhttp","LINK333: "+ urllink);
        Logger.debug("okhttp","LINK334: "+ links.toString());
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = CoopAssistantViewPMESPDF.newInstance(urllink);
                break;
            case 1:
                fragment =  CoopPMESVideoPresentation.newInstance(links);
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}