package com.hsi.madminutemath.views;

import com.hsi.madminutemath.R;
import com.hsi.madminutemath.R.id;
import com.hsi.madminutemath.R.layout;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

public class AboutDialog extends DialogFragment {


    public AboutDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.dialog_about, container);
        
        RobotoTextView textView = (RobotoTextView) view.findViewById(R.id.aboutText);
        textView.setText("This application was made thanks to icons by SuperAtic LABS, Edward Boatman,Jamison Wieser.\n" +
        		"Design and implementation by HongShenIndustries.");

        return view;
    }
}