package com.hsi.madminutemath;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

public class ScoresActivity extends BaseGameActivity{
	ArrayList<String> list;
	ListView scoreChooser;
	private static final int HIDER_FLAGS = 0;// SystemUiHider.FLAG_HIDE_NAVIGATION;

	@Override
	protected void onCreate(Bundle b) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();      
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(b);
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		if(status == ConnectionResult.SUCCESS) {
		    //Success! Do what you want
		beginUserInitiatedSignIn();
		
		setContentView(R.layout.activity_score);
		scoreChooser = (ListView)findViewById(R.id.scoreChooser);
		list = new ArrayList<String>();
		list.add("Addition");
		list.add("Subtraction");
		list.add("Multiplication");
		list.add("Division");
		list.add("Mixed");
		ScoreAdapter adapter = new ScoreAdapter(this, R.layout.score_row, list);
		scoreChooser.setAdapter(adapter);
		scoreChooser.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				String curString = list.get(position);
				if(curString.equals("Addition")){
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.leaderboard_addition)), 1);
				}
				else if(curString.equals("Subtraction")){
					startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.leaderboard_subtraction)), 1);
				}
				else if(curString.equals("Multiplication")){
					startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.leaderboard_multiplication)), 1);
				}
				else if(curString.equals("Division")){
					startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.leaderboard_division)), 1);
				}
				else{
					startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.leaderboard_mixed)), 1);
				}
			}
			
		});
		}
		else{
			setContentView(R.layout.unavailable);
		}
		
		
		
	}
	
	public void signin(View v){
        beginUserInitiatedSignIn();

	}
	
	public void signout(View v){
        signOut();

	}
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

}
