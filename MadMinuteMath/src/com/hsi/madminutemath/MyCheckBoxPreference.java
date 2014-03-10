package com.hsi.madminutemath;

import android.content.Context;
import android.graphics.Typeface;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class MyCheckBoxPreference extends CheckBoxPreference{
    public MyCheckBoxPreference(Context context) {
        super(context);
    }

    public MyCheckBoxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCheckBoxPreference(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView titleView = (TextView) view.findViewById(android.R.id.title);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Nexa Bold.otf");
        titleView.setTypeface(tf ,1);
        titleView.setTextColor(0xFFFFFFFF);

    }
}