package com.hsi.madminutemath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
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

}
