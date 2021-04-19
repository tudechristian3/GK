package com.goodkredit.myapplication.adapter.coopassistant.nonmember;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;

import java.util.ArrayList;

public class CoopAssistantPMESVideoAdapter extends RecyclerView.Adapter<CoopAssistantPMESVideoAdapter.VideoPresentationHolder> {

    private ArrayList<String> linksList = new ArrayList<>();
    private Context mContext;

    public CoopAssistantPMESVideoAdapter(Context context, ArrayList<String> list) {
        mContext = context;
        linksList = list;
    }

    @NonNull
    @Override
    public VideoPresentationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_presentation_item,parent,false);
        return new VideoPresentationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoPresentationHolder holder, int position) {
        if(linksList.size() > 0){
            holder.bind(linksList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return linksList.size();
    }

    public static class VideoPresentationHolder extends RecyclerView.ViewHolder{

        private TextView video_link;

        public VideoPresentationHolder(@NonNull View itemView) {
            super(itemView);
            video_link = itemView.findViewById(R.id.pmes_video_link);
        }

        public void bind(String link){
            video_link.setText("Link : "+link);
        }
    }

}
