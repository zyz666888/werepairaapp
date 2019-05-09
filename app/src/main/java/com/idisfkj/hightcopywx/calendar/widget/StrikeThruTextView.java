package com.idisfkj.hightcopywx.calendar.widget;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class StrikeThruTextView extends TextView {

    public StrikeThruTextView(Context context) {
        this(context, null);
    }

    public StrikeThruTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrikeThruTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        TextPaint paint = getPaint();
        paint.setAntiAlias(true);
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

}
