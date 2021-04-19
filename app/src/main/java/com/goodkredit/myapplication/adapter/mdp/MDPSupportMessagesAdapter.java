package com.goodkredit.myapplication.adapter.mdp;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.SupportConversation;

import java.util.ArrayList;
import java.util.List;

import me.himanshusoni.chatmessageview.ChatMessageView;

public class MDPSupportMessagesAdapter extends RecyclerView.Adapter<MDPSupportMessagesAdapter.MyViewHolder> {
    private Context mContext;
    private List<SupportConversation> mGridData;
    private static final int MY_MESSAGE = 0;
    private static final int OTHER_MESSAGE = 1;

    public MDPSupportMessagesAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void update(List<SupportConversation> data) {
        int startPos = mGridData.size() + 1;
        mGridData.clear();
        mGridData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public void clear() {
        int startPos = mGridData.size();
        mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == MY_MESSAGE) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_send_message_mine, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_send_message_other, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if (position > -1) {
            SupportConversation mdpconversation = mGridData.get(position);

            holder.dateView.setText(CommonFunctions.convertDate(mdpconversation.getDateTimeIN()));

            if (CommonFunctions.replaceEscapeData(mdpconversation.getMessage()).contains("https://s3-us-west-1.amazonaws.com")) {
                holder.message.setVisibility(View.GONE);
                holder.image.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .asBitmap()
                        .load(mdpconversation.getMessage())
                        .apply(new RequestOptions()
                                .fitCenter())
                        .into(new BaseTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                holder.image.setImageBitmap(resource);
                            }

                            @Override
                            public void getSize(SizeReadyCallback cb) {
                                cb.onSizeReady(CommonFunctions.getScreenWidthPixel(mContext), 200);
                            }

                            @Override
                            public void removeCallback(SizeReadyCallback cb) {

                            }
                        });
            } else {
                holder.message.setText(CommonFunctions.replaceEscapeData(mdpconversation.getMessage()));
                holder.message.setVisibility(View.VISIBLE);
                holder.image.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    @Override
    public int getItemViewType(int position) {
        SupportConversation mdpconversation = mGridData.get(position);
        if (mdpconversation.getThreadType().equals("0")) {
            return MY_MESSAGE;
        } else {
            return OTHER_MESSAGE;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ChatMessageView mChatMessageView;
        private TextView message;
        private ImageView image;
        private TextView dateView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mChatMessageView = (ChatMessageView) itemView.findViewById(R.id.chatMessageView);
            message = (TextView) itemView.findViewById(R.id.text);
            image = (ImageView) itemView.findViewById(R.id.image);
            dateView = (TextView) itemView.findViewById(R.id.messageDate);
        }
    }
}
