package com.goodkredit.myapplication.adapter.publicsponsor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.PublicSponsorPromos;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

public class PublicSponsorPromoRecyclerAdapter extends RecyclerView.Adapter<PublicSponsorPromoRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<PublicSponsorPromos> mGridData;

    public PublicSponsorPromoRecyclerAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    public void update(List<PublicSponsorPromos> data) {
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

    @Override
    public PublicSponsorPromoRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recyclearview_sponsorpromo_item, parent, false);
        return new PublicSponsorPromoRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PublicSponsorPromoRecyclerAdapter.MyViewHolder holder, int position) {
        PublicSponsorPromos psPromos = mGridData.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(psPromos.getPSPromoImage())
                .apply(new RequestOptions()
                        .fitCenter())
                .into(new BaseTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.promoImage.setImageBitmap(resource);
                    }

                    @Override
                    public void getSize(SizeReadyCallback cb) {
                        cb.onSizeReady(CommonFunctions.getScreenWidthPixel(mContext), 150);
                    }

                    @Override
                    public void removeCallback(SizeReadyCallback cb) {

                    }
                });

        holder.promotitle.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Medium.ttf", CommonFunctions.replaceEscapeData(psPromos.getPSPromoTitle())));
        holder.promodescription.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", CommonFunctions.replaceEscapeData(psPromos.getPSPromoDescription())));

        holder.promodescription.post(new Runnable() {
            @Override
            public void run() {

                int line = holder.promodescription.getLineCount();
                if (line >= 3) {
                    makeTextViewResizable(mContext, holder.promodescription, 3, ".. See More", true);
                }

            }
        });
    }

    public static void makeTextViewResizable(final Context mContext, final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(mContext, CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(mContext, CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(mContext, CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);

//                    tv.setText(CommonFunctions.setTypeFace(mContext, "fonts/robotolight.ttf", tv.getTag().toString()));
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Context mContext, final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {

            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(mContext, tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(mContext, tv, 3, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    public static class MySpannable extends ClickableSpan {

        private boolean isUnderline = true;

        /**
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderline);
            ds.setColor(Color.parseColor("#1b76d3"));
        }

        @Override
        public void onClick(View widget) {


        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView promoImage;
        private TextView promotitle;
        private TextView promodescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            promoImage = (ImageView) itemView.findViewById(R.id.promoImage);
            promotitle = (TextView) itemView.findViewById(R.id.promotitle);
            promodescription = (TextView) itemView.findViewById(R.id.promodescription);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
