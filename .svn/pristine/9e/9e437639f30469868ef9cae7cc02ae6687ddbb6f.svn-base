package com.goodkredit.myapplication.adapter.settings;

import android.app.Dialog;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.responses.support.GetSupportConversationResponseData;
import com.goodkredit.myapplication.utilities.Logger;

import java.util.ArrayList;

import me.himanshusoni.chatmessageview.ChatMessageView;

/**
 * Created by Ban_Lenovo on 3/9/2017.
 */

public class SupportSendMessageAdapter extends RecyclerView.Adapter<SupportSendMessageAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<GetSupportConversationResponseData> mArrayList;
    private AQuery aq;
    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1;
    private String mImgURL;

    public SupportSendMessageAdapter(Context context, ArrayList<GetSupportConversationResponseData> arryList) {
        mContext = context;
        mArrayList = arryList;
        aq = new AQuery(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == MY_MESSAGE)
            return new MyViewHolder(inflater.inflate(R.layout.activity_send_message_mine, parent, false));
        else
            return new MyViewHolder(inflater.inflate(R.layout.activity_send_message_other, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetSupportConversationResponseData obj = mArrayList.get(position);
        if (obj.getMessage().contains("https://s3-us-west-1.amazonaws.com")) {
            mImgURL = obj.getMessage();

            Glide.with(mContext)
                    .load(mImgURL)
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.ic_image)
                            .fitCenter())
                    .into(holder.image);
            holder.message.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
            holder.mChatMessageView.setTag(obj);
            holder.mChatMessageView.setOnClickListener(onClickListener);
            holder.dateView.setText(obj.getDateTimeIn());
        } else {
            holder.dateView.setText(obj.getDateTimeIn());
            holder.message.setText(obj.getMessage());
            holder.message.setVisibility(View.VISIBLE);
            holder.image.setVisibility(View.GONE);
        }

    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            GetSupportConversationResponseData obj = (GetSupportConversationResponseData) v.getTag();

            final Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.sendmessages_image);
            ImageView img = (ImageView) dialog.findViewById(R.id.imageZoom);
            Logger.debug("URL", mImgURL);
            Glide
                    .with(mContext)
                    .load(obj.getMessage())
                    .apply(RequestOptions
                            .fitCenterTransform()
                            .placeholder(R.drawable.ic_image))
                    .into(img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
        }
    };

    @Override
    public int getItemCount() {
        return mArrayList == null ? 0 : mArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        GetSupportConversationResponseData obj = mArrayList.get(position);
        if (obj.getThreadType().equals("0"))
            return MY_MESSAGE;
        else
            return OTHER_MESSAGE;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ChatMessageView mChatMessageView;
        TextView message;
        ImageView image;
        TextView dateView;
        RelativeLayout rootView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mChatMessageView = (ChatMessageView) itemView.findViewById(R.id.chatMessageView);
            message = (TextView) itemView.findViewById(R.id.text);
            image = (ImageView) itemView.findViewById(R.id.image);
            dateView = (TextView) itemView.findViewById(R.id.messageDate);
            // rootView = (RelativeLayout) itemView.findViewById(R.id.imageLayout);
        }
    }

    public void update(ArrayList<GetSupportConversationResponseData> arrayList) {
        Logger.debug("SendSupp", "update: ");
        clearData();
        mArrayList = arrayList;
        notifyDataSetChanged();
    }

    public void clearData() {
        mArrayList.clear();
        notifyDataSetChanged();
    }
}
