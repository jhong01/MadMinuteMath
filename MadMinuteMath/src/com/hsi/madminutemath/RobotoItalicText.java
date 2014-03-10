package com.hsi.madminutemath;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class RobotoItalicText extends TextView{

    public RobotoItalicText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RobotoItalicText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoItalicText(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Nexa Bold.otf");
        setTypeface(tf ,1);
        setTextColor(0xFFFFFFFF);

    }

}

