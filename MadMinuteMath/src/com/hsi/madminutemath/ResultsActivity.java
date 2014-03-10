package com.hsi.madminutemath;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

public class ResultsActivity extends BaseGameActivity {
	int correct;
	float accuracy;
	MySQLiteHelper helper;
	List<Progress> progressList;
	List<Review> reviewList;
	String correctString, accuracyString;
	ListView reviewListView;
	RobotoTextView correctText, accuracyText;
	Progress recentProgress;
	MathAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_results);
		correctText = (RobotoTextView) findViewById(R.id.numCorrect);
		accuracyText = (RobotoTextView) findViewById(R.id.accuracyPerc);
		reviewListView = (ListView) findViewById(R.id.reviewList);
		helper = new MySQLiteHelper(this);
		generateStrings();
		setRobotoTextViews();
		adapter = new MathAdapter(this, R.layout.row, reviewList);
		reviewListView.setAdapter(adapter);
		if (reviewList.size() == 0) {
			reviewListView.setVisibility(View.GONE);
		}

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

		int max = progressList.size();
		recentProgress = progressList.get(max - 1);
	}

	public void toMain(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public void progress(View v) {
		Intent intent = new Intent(this, ProgressActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.review, menu);
		return true;
	}

	public void submit(View v) {
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		if (status == ConnectionResult.SUCCESS) {
			Intent intent = getIntent();
			String fromPlay = intent.getStringExtra("sendString");
			int adjustedScore = intent.getIntExtra("adjusted", 1);
			long longScore = Long.valueOf(adjustedScore);
			
			/*
			 * GoogleApiClient mGoogleApiClient = new
			 * GoogleApiClient.Builder(this) .addApi(Games.API)
			 * .addScope(Games.SCOPE_GAMES) .build();
			 */
			if (fromPlay.equals("Addition")) {
				Games.Leaderboards.submitScore(getApiClient(), getResources()
						.getString(R.string.leaderboard_addition), longScore);
			}
			else if (fromPlay.equals("Subtraction")) {
				Games.Leaderboards.submitScore(getApiClient(), getResources()
						.getString(R.string.leaderboard_subtraction), longScore);
			}
			else if (fromPlay.equals("Multiplication")) {
				Games.Leaderboards.submitScore(getApiClient(), getResources()
						.getString(R.string.leaderboard_multiplication), longScore);
			}
			else if (fromPlay.equals("Division")) {
				Games.Leaderboards.submitScore(getApiClient(), getResources()
						.getString(R.string.leaderboard_division), longScore);
			}
			else{
				Games.Leaderboards.submitScore(getApiClient(), getResources()
						.getString(R.string.leaderboard_mixed), longScore);
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Play Services Unavailable", Toast.LENGTH_LONG).show();
		}
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
