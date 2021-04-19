package com.goodkredit.myapplication.utilities;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class SpannableDescription extends ClickableSpan {

    private boolean isUnderline = true;

    /**
     * Constructor
     */
    public SpannableDescription(boolean isUnderline) {
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
