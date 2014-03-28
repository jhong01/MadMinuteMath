package com.hsi.madminutemath.activities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.hsi.madminutemath.R;
import com.hsi.madminutemath.R.drawable;
import com.hsi.madminutemath.R.id;
import com.hsi.madminutemath.R.layout;
import com.hsi.madminutemath.R.string;
import com.hsi.madminutemath.adapters.MathAdapter;
import com.hsi.madminutemath.util.MySQLiteHelper;
import com.hsi.madminutemath.util.Progress;
import com.hsi.madminutemath.util.Review;
import com.hsi.madminutemath.views.RobotoTextView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

public class ResultsActivity extends BaseGameActivity {
	int correct;
	float accuracy;
	MySQLiteHelper helper;
	ImageView up1, up2;
	List<Progress> progressList;
	List<Review> reviewList;
	String correctString, accuracyString;
	private static final int HIDER_FLAGS = 0;// SystemUiHider.FLAG_HIDE_NAVIGATION;

	ListView reviewListView;
	RobotoTextView correctText, accuracyText;
	Progress recentProgress;

	Vibrator myVib;
	int adjustedScore;
	MathAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();      
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
	    myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		setContentView(R.layout.activity_results);
		correctText = (RobotoTextView) findViewById(R.id.numCorrect);
		accuracyText = (RobotoTextView) findViewById(R.id.accuracyPerc);
		reviewListView = (ListView) findViewById(R.id.reviewList);
		up1= (ImageView)findViewById(R.id.up1);	
		up2= (ImageView)findViewById(R.id.up2);	
		
	helper = new MySQLiteHelper(this);
		generateStrings();
		setRobotoTextViews();
		 SpeedScrollListener listener = new SpeedScrollListener();
		    reviewListView.setOnScrollListener(listener);
		adapter = new MathAdapter(this, listener, reviewList);
		adapter.setResource(R.layout.row);
		reviewListView.setAdapter(adapter);
		if (reviewList.size() == 0) {
			reviewListView.setVisibility(View.GONE);
		}
		final LinearLayout topLayout = (LinearLayout)findViewById(R.id.dragView);
		final RelativeLayout relTopLayout = (RelativeLayout)findViewById(R.id.relToplayout);
		final SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
		final TextView reviewTextView = (TextView)findViewById(R.id.reviewText);
       // layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
		if ((getResources().getConfiguration().screenLayout & 
			    Configuration.SCREENLAYOUT_SIZE_MASK) == 
			        Configuration.SCREENLAYOUT_SIZE_NORMAL) {
			    // on a large screen device ...
	        layout.setPanelHeight(140);

		}
		
		else if((getResources().getConfiguration().screenLayout & 
			    Configuration.SCREENLAYOUT_SIZE_MASK) == 
			        Configuration.SCREENLAYOUT_SIZE_LARGE){
	        layout.setPanelHeight(140);

		}
		else if((getResources().getConfiguration().screenLayout & 
			    Configuration.SCREENLAYOUT_SIZE_MASK) == 
		        Configuration.SCREENLAYOUT_SIZE_XLARGE){
	        layout.setPanelHeight(320);

		}
		else{
			layout.setPanelHeight(120);
		}
        layout.setDragView(topLayout);
        layout.setAnchorPoint(0.3f);
        
        layout.setPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
               
            }

            @Override
            public void onPanelExpanded(View panel) {
            	up1.setImageResource(R.drawable.whitedown);
            	up2.setImageResource(R.drawable.whitedown);
            }

            @Override
            public void onPanelCollapsed(View panel) {
            	up1.setImageResource(R.drawable.whiteup);
            	up2.setImageResource(R.drawable.whiteup);
            }

            @Override
            public void onPanelAnchored(View panel) {
            	up1.setImageResource(R.drawable.whitedown);
            	up2.setImageResource(R.drawable.whitedown);

            }
        });
        Intent intent = getIntent();
		adjustedScore = intent.getIntExtra("adjusted", 1);
		TextView adjusted = (TextView)findViewById(R.id.adjustedScore);
		adjusted.setText(""+adjustedScore);
		Animation fadeIn = new AlphaAnimation(0, 1);
		//fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
		fadeIn.setDuration(2000);
		adjusted.setAnimation(fadeIn);
		

		


	}
	

	private void setRobotoTextViews() {
		correct = recentProgress.getCompleted();
		accuracy = recentProgress.getAccuracy();
		DecimalFormat df = new DecimalFormat("###");
		df.setRoundingMode(RoundingMode.DOWN);

		correctString = Integer.toString(correct) + " correct";
		accuracyString = df.format(accuracy) + "% accuracy";
		correctText.setText(correctString);
		accuracyText.setText(accuracyString);

	}

	private void generateStrings() {
		progressList = helper.getAllProgress();
		reviewList = helper.getAllReview();
		Collections.reverse(reviewList);
		int max = progressList.size();
		recentProgress = progressList.get(max - 1);
	}

	public void toMain(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	
	

	public void submit(View v) {
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		if (status == ConnectionResult.SUCCESS) {
			Intent intent = getIntent();
			String fromPlay = intent.getStringExtra("sendString");
			long longScore = Long.valueOf(adjustedScore);
			
			/*
			 * GoogleApiClient mGoogleApiClient = new
			 * GoogleApiClient.Builder(this) .addApi(Games.API)
			 * .addScope(Games.SCOPE_GAMES) .build();
			 */
			if (fromPlay.equals("Addition")) {
				Games.Leaderboards.submitScore(getApiClient(), getResources()
						.getString(R.string.leaderboard_addition), longScore);
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.leaderboard_addition)), 1);

			}
			else if (fromPlay.equals("Subtraction")) {
				Games.Leaderboards.submitScore(getApiClient(), getResources()
						.getString(R.string.leaderboard_subtraction), longScore);
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.leaderboard_subtraction)), 1);

			}
			else if (fromPlay.equals("Multiplication")) {
				Games.Leaderboards.submitScore(getApiClient(), getResources()
						.getString(R.string.leaderboard_multiplication), longScore);
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.leaderboard_multiplication)), 1);

			}
			else if (fromPlay.equals("Division")) {
				Games.Leaderboards.submitScore(getApiClient(), getResources()
						.getString(R.string.leaderboard_division), longScore);
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.leaderboard_division)), 1);

			}
			else{
				Games.Leaderboards.submitScore(getApiClient(), getResources()
						.getString(R.string.leaderboard_mixed), longScore);
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.leaderboard_mixed)), 1);

			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Play Services Unavailable", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(),
				"Sign in failed.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(),
				"Sign in succeeded.", Toast.LENGTH_SHORT).show();

	}

}
