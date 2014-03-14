package com.hsi.madminutemath;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.timelytextview.NumberView;

public class MainActivity extends FragmentActivity {
	private static final int HIDER_FLAGS = 0;// SystemUiHider.FLAG_HIDE_NAVIGATION;
	Vibrator myVib;
    NumberView mNumView;
    MediaPlayer player;
    boolean volume = false;
    ImageView mute;
    BackgroundSound sound;
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
		mute.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(volume == false){
					sound = new BackgroundSound();
					sound.execute();
					volume=true;
					mute.setBackground(getResources().getDrawable(R.drawable.ic_action_volume_on));

				}
				else{
					player.pause();
					mute.setBackground(getResources().getDrawable(R.drawable.ic_action_volume_muted));
					volume = false;
				}
			}
			
		});
		

	}
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(player != null){
		player.pause();
		mute.setBackground(getResources().getDrawable(R.drawable.ic_action_volume_muted));
		volume = false;
		}

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

		finish();
	}
	public class BackgroundSound extends AsyncTask<Void, Void, Void> {

	    @Override
	    protected Void doInBackground(Void... params) {
	         player = MediaPlayer.create(MainActivity.this, R.raw.piano_concerto); 
	        player.setLooping(true); // Set looping 
	        player.setVolume(100,100); 
	        player.start(); 

	        return null;
	    }

	}

}
