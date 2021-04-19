package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.coopassistant.nonmember.CoopAssistantPMESVideoAdapter;

import java.util.ArrayList;


public class CoopPMESVideoPresentation extends Fragment {

    private ArrayList<String> links = new ArrayList<>();
    private CoopAssistantPMESVideoAdapter videoAdapter = null;
    private RecyclerView recyclerView;
    private LinearLayout novideo_attached_layout;

    public static CoopPMESVideoPresentation newInstance(ArrayList<String> link){
        CoopPMESVideoPresentation fragment = new CoopPMESVideoPresentation();
        Bundle args = new Bundle();
        args.putStringArrayList("links", link);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            links = getArguments().getStringArrayList("links");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_coop_p_m_e_s_video_presentation, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        novideo_attached_layout = view.findViewById(R.id.novideo_attached_layout);
        recyclerView = view.findViewById(R.id.rv_video_presentation);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        assert links != null;
        if(links != null && links.size() > 0){
            novideo_attached_layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            videoAdapter = new CoopAssistantPMESVideoAdapter(requireContext(),links);
            recyclerView.setAdapter(videoAdapter);
        }else{
            novideo_attached_layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }


    }
}