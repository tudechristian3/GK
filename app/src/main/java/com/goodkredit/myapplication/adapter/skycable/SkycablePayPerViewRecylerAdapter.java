package com.goodkredit.myapplication.adapter.skycable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
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
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.bean.SkycablePPV;
import com.goodkredit.myapplication.common.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

public class SkycablePayPerViewRecylerAdapter extends RecyclerView.Adapter<SkycablePayPerViewRecylerAdapter.MyViewHolder> {
    private Context mContext;
    private List<SkycablePPV> mGridData;

    public SkycablePayPerViewRecylerAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
    }

    private Context getContext() {
        return mContext;
    }

    public void update(List<SkycablePPV> data) {
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
    public SkycablePayPerViewRecylerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recyclerview_ppv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SkycablePayPerViewRecylerAdapter.MyViewHolder holder, int position) {
        SkycablePPV skycablePPV = mGridData.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(skycablePPV.getImageURL())
                .apply(new RequestOptions()
                        .fitCenter())
                .into(new BaseTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.ppvImage.setImageBitmap(resource);
                    }

                    @Override
                    public void getSize(SizeReadyCallback cb) {
                        cb.onSizeReady(CommonFunctions.getScreenWidth(mContext), CommonFunctions.getScreenHeight(mContext));
                    }

                    @Override
                    public void removeCallback(SizeReadyCallback cb) {

                    }
                });
        holder.ppvTitle.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Medium.ttf", skycablePPV.getPPVName()));

        final String desc = skycablePPV.getPPVDescription();
        holder.ppvDescription.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", skycablePPV.getPPVDescription()));

        holder.ppvDescription.post(new Runnable() {
            @Override
            public void run() {

                int line = holder.ppvDescription.getLineCount();
                if (line >= 3) {
                    makeTextViewResizable(mContext, holder.ppvDescription, 3, ".. See More", true);
                }

            }
        });

        String showingDate = "Valid from " + CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(skycablePPV.getShowingDateFrom())) + " to " + CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(skycablePPV.getShowingDateTo())) + ".";
        holder.ppvShowingDate.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", showingDate));

        if (skycablePPV.getDiscountPercentage() > 0) {

            String grossRate = ("₱").concat(CommonFunctions.currencyFormatter(String.valueOf(skycablePPV.getGrossRate())));
            String discountedRate = CommonFunctions.currencyFormatter(String.valueOf(skycablePPV.getDiscountedRate()));
            String ppvamount = grossRate.concat("  ").concat(discountedRate);

            SpannableStringBuilder ssb = new SpannableStringBuilder(ppvamount);
            applyColor(ssb, 0, grossRate.length(), ContextCompat.getColor(mContext, R.color.color_F83832));
            ssb.setSpan(new StrikethroughSpan(), 0, grossRate.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.ppvAmount.setText(ssb, TextView.BufferType.EDITABLE);

        } else {

            holder.ppvAmount.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Medium.ttf", "₱".concat(CommonFunctions.currencyFormatter(String.valueOf(skycablePPV.getDiscountedRate())))));

        }

        SpannableString spannableString = new SpannableString(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Medium.ttf", mContext.getString(R.string.string_subscribe)));
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        holder.ppvSubscribe.setText(spannableString);

    }

    private void applyColor(SpannableStringBuilder ssb, int start, int end, int color) {
        // Create a span that will make the text red
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(
                color);
        // Apply the color span
        ssb.setSpan(
                colorSpan,            // the span to add
                start,                // the start of the span (inclusive)
                end,                  // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder
        // SPAN_EXCLUSIVE_EXCLUSIVE means to not extend the span when additional
        // text is added in later
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ppvImage;
        private TextView ppvTitle;
        private TextView ppvDescription;
        private TextView ppvAmount;
        private TextView ppvSubscribe;
        private TextView ppvShowingDate;
        private TextView ppvDescriptionMore;

        public MyViewHolder(View itemView) {
            super(itemView);
            ppvImage = (ImageView) itemView.findViewById(R.id.ppvImage);
            ppvTitle = (TextView) itemView.findViewById(R.id.ppvTitle);
            ppvDescription = (TextView) itemView.findViewById(R.id.ppvDescription);
            ppvAmount = (TextView) itemView.findViewById(R.id.ppvAmount);
            ppvSubscribe = (TextView) itemView.findViewById(R.id.ppvSubscribe);
            ppvSubscribe.setOnClickListener(this);
            ppvShowingDate = (TextView) itemView.findViewById(R.id.txvShowingDate);
            ppvDescriptionMore = (TextView) itemView.findViewById(R.id.ppvDescriptionMore);
            ppvDescriptionMore.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position > -1) {

                SkycablePPV skycablePPV = mGridData.get(position);

                switch (v.getId()) {
                    case R.id.ppvSubscribe: {

                        Bundle args = new Bundle();
                        args.putParcelable("skycablePPV", skycablePPV);

                        ((SkyCableActivity) mContext).displayView(5, args);

                        break;
                    }
                    case R.id.ppvDescriptionMore: {

                        ppvDescriptionMore.setVisibility(View.GONE);
                        ppvDescription.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", skycablePPV.getPPVDescription()));

                        break;
                    }
                }
            }
        }
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
}
