package com.hsi.madminutemath.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.timelytextview.NumberView;
import com.hsi.madminutemath.R;
import com.hsi.madminutemath.R.drawable;
import com.hsi.madminutemath.R.id;
import com.hsi.madminutemath.R.layout;
import com.hsi.madminutemath.R.menu;
import com.hsi.madminutemath.views.AboutDialog;

public class MainActivity extends FragmentActivity {
	private static final int HIDER_FLAGS = 0;// SystemUiHider.FLAG_HIDE_NAVIGATION;
	Vibrator myVib;
    NumberView mNumView;
    boolean volume = false;
    ImageView mute;
    Editor prefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();                                   //new
	    myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		mute = (ImageView)findViewById(R.id.mute);
		 prefs = PreferenceManager.getDefaultSharedPreferences(this).edit();
		prefs.putBoolean("sound", false);
		prefs.apply();

		mute.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(volume == false){
					
					volume=true;
					mute.setBackgroundResource(R.drawable.ic_action_volume_on);
					prefs.putBoolean("sound", true);
					prefs.apply();

				}
				else{
					mute.setBackgroundResource(R.drawable.ic_action_volume_muted);
					volume = false;
					prefs.putBoolean("sound", false);
					prefs.apply();

				}
			}
			
		});
		

	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		

	}
	public void about(View v){
		 FragmentManager fm = getSupportFragmentManager();
	        AboutDialog aboutDialog = new AboutDialog();
	        aboutDialog.show(fm, "tag");
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void play(View v){
		Intent intent = new Intent(this, PlayActivity.class);
		startActivity(intent);
	}
	public void settings(View v){
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	public void scores(View v){

		Intent intent = new Intent(this, ScoresActivity.class);
		startActivity(intent);
	}
	public void progress(View v){

		Intent intent = new Intent(this, ProgressActivity.class);
		startActivity(intent);
	}
	public void quit(View v){
		
	    Vibrator myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
	    myVib.vibrate(50);			
		finish();
	}
	

}
