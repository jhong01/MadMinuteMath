package com.hsi.madminutemath.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class RobotoButton extends Button{


	    public RobotoButton(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	        init();
	    }

	    public RobotoButton(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        init();
	    }

	    public RobotoButton(Context context) {
	        super(context);
	        init();
	    }

	    public void init() {
	        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Nexa Bold.otf");
	        setTypeface(tf ,1);
	        setTextColor(0xFFFFFFFF);

	    }

	}

