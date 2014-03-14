package com.hsi.madminutemath;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class SettingsActivity extends Activity {
	private static final int HIDER_FLAGS = 0;// SystemUiHider.FLAG_HIDE_NAVIGATION;


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();      
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
       getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
        
 
    }
	   /**
     * Populate the activity with the top-level headers.
     */
    
	
	 public static class PrefsFragment extends PreferenceFragment {

	        @Override
	        public void onCreate(Bundle savedInstanceState) {
	            super.onCreate(savedInstanceState);
	            addPreferencesFromResource(R.xml.mode);


	            // Load the preferences from an XML resource
	            
	        }
	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	            View view = super.onCreateView(inflater, container, savedInstanceState);
	            view.setBackground(getResources().getDrawable(R.drawable.gradient));
	            return view;
	        }
	    }
	
}
