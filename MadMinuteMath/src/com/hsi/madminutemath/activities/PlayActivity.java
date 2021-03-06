package com.hsi.madminutemath.activities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.hsi.madminutemath.R;
import com.hsi.madminutemath.R.anim;
import com.hsi.madminutemath.R.id;
import com.hsi.madminutemath.R.layout;
import com.hsi.madminutemath.R.raw;
import com.hsi.madminutemath.util.MySQLiteHelper;
import com.hsi.madminutemath.util.Operation;
import com.hsi.madminutemath.util.Progress;
import com.hsi.madminutemath.util.Review;
import com.hsi.madminutemath.util.SystemUiHider;
import com.hsi.madminutemath.views.RobotoTextView;

import de.passsy.holocircularprogressbar.HoloCircularProgressBar;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class PlayActivity extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;
	private static final int HIDER_FLAGS = 0;// SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	
	int iTmp;
	private SystemUiHider mSystemUiHider;
	RobotoTextView countdown, answer, firstNum, secondNum, accuracyText,
			completedText, seconds;
	HoloCircularProgressBar progress;
	RelativeLayout relLayout;
	boolean emergency = false;
	Intent toResults;
	Operation math;
	CountDownTimer cdt;
	int first, second, operation, ans = 0;
	Runnable runnable1, runnable2, runnable3, runnable4;
	private ObjectAnimator mProgressBarAnimator;
	LinearLayout grid;
SoundPool sp;
	int correctCount = 0;
	int totalCount = 0;
	float accuracyPerc = 100;
	Handler handler;
	MySQLiteHelper helper;
	Animation animFadein;
	boolean volume;
	  private InterstitialAd interstitial;

Vibrator myVib;
	RelativeLayout bigdaddy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();      
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_play);
		countdown = (RobotoTextView) findViewById(R.id.countdown);
		answer = (RobotoTextView) findViewById(R.id.answer);
		firstNum = (RobotoTextView) findViewById(R.id.topNumber);
		secondNum = (RobotoTextView) findViewById(R.id.botNumber);
		relLayout = (RelativeLayout) findViewById(R.id.mathRelative);
		accuracyText = (RobotoTextView) findViewById(R.id.accuracyText);
		completedText = (RobotoTextView) findViewById(R.id.completedText);
	    myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

		seconds = (RobotoTextView) findViewById(R.id.seconds);
		grid = (LinearLayout) findViewById(R.id.grid);
		progress = (HoloCircularProgressBar) findViewById(R.id.clock);
		helper = new MySQLiteHelper(this);
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in); 
		countdown.startAnimation(animFadein);
		helper.deleteReview();
		progress.setProgressColor(0xFFFFFFFF);
		progress.setProgressBackgroundColor(0x68A8AD);
		progress.setProgress(1f);
		math = new Operation(this);
		bigdaddy = (RelativeLayout)findViewById(R.id.bigdaddy);
		
		
		 sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

		iTmp = sp.load(this, R.raw.correct, 1); // in 2nd param u have to pass your desire ringtone
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		 volume = prefs.getBoolean("sound", true);
		  interstitial = new InterstitialAd(this);
		    interstitial.setAdUnitId("ca-app-pub-8037510338774840/3330050214");
		   
		    AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("E733C78D485B219EEE030A28AB25E019").build();
		    interstitial.setAdListener(new AdListener(){
		    	
		    	 @Override
		         public void onAdLoaded() {

		           // Change the button text and enable the button.
		         }

		         @Override
		         public void onAdFailedToLoad(int errorCode) {
		        	 emergency = true;
		           // Change the button text and disable the button.
		         }
		         @Override
		         public void onAdClosed(){
		        	 startActivity(toResults);
						finish();
		        	 
		         }
		    });
		    interstitial.loadAd(adRequest);

		

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

    	if(cdt != null){
    	cdt.cancel();
    	}
    	handler.removeCallbacks(runnable1);
    	handler.removeCallbacks(runnable2);
    	handler.removeCallbacks(runnable3);

    	
        finish();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		createThread();
		handler = new Handler();
		handler.postDelayed(runnable1, 1000);

	}

	private void createThread() {
		final Intent intent;

		runnable1 = new Runnable() {
			public void run() {

				countdown.setText("2");
				countdown.startAnimation(animFadein);

				handler.postDelayed(runnable2, 1000);
			}
		};
		runnable2 = new Runnable() {
			public void run() {

				countdown.setText("1");
				countdown.startAnimation(animFadein);

				handler.postDelayed(runnable3, 1000);

			}
		};

		runnable3 = new Runnable() {
			public void run() {
				countdown.clearAnimation();
				countdown.setVisibility(View.GONE);
				relLayout.setVisibility(View.VISIBLE);
				answer.setVisibility(View.VISIBLE);
				setUpNumbers();
				runnable4.run();

			}
		};
		runnable4 = new Runnable() {
			public void run() {
				// animate(progress, null, 1f, 65000);
				// progress.setMarkerProgress(0f);
			/*	Integer colorFrom = getResources().getColor(R.color.White);
				Integer colorTo = getResources().getColor(R.color.IndianRed);
				ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
				colorAnimation.addUpdateListener(new AnimatorUpdateListener() {

				    @Override
				    public void onAnimationUpdate(ValueAnimator animator) {
				        bigdaddy.setBackgroundColor((Integer)animator.getAnimatedValue());
				    }

				});
				colorAnimation.setDuration(60000);
				colorAnimation.start();*/
				 cdt = new CountDownTimer(61000, 1000) {

					public void onTick(long millisUntilFinished) {
						// mTextField.setText("seconds remaining: " +
						// millisUntilFinished / 1000);
						// here you can have your logic to set text to edittext
		

						long time = (millisUntilFinished) / 1000;
						progress.setProgress((float) (time / 61.0));
						seconds.setText(Long.toString(time - 1));
						if (time == 1) {
							relLayout.setVisibility(View.GONE);
							countdown.setText("Done!");
							grid.setVisibility(View.GONE);
							countdown.setVisibility(View.VISIBLE);

							// FIX MODE

							SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(PlayActivity.this);
							boolean add = prefs.getBoolean("addition", true);
							boolean sub = prefs
									.getBoolean("subtraction", false);
							boolean mult = prefs.getBoolean("multiplication",
									false);
							boolean div = prefs.getBoolean("division", false);
							ArrayList<Integer> array = new ArrayList<Integer>();
							if (add == true) {
								array.add(1);
							}
							if (sub == true) {
								array.add(2);
							}
							if (mult == true) {
								array.add(3);
							}
							if (div == true) {
								array.add(4);
							}
							String mode = "";
							String sendString = "";
							for (int i = 0; i < array.size(); i++) {
								if (array.get(i) == 1) {
									mode += " + ";
								} else if (array.get(i) == 2) {
									mode += " - ";
								} else if (array.get(i) == 3) {
									mode += " x ";
								} else if (array.get(i) == 4) {
									mode += " � ";
								}
							}
							if(mode.equals(" + ")){
								sendString = "Addition";
							}
							else if(mode.equals(" - ")){
								sendString = "Subtraction";
							}
							else if(mode.equals(" * ")){
								sendString = "Multiplication";
							}
							else if(mode.equals(" � ")){
								sendString = "Division";
							}
							else{
								sendString = "Mixed";
							}

							Progress tempProgress = new Progress(correctCount,
									accuracyPerc, math.getDifficulty(), mode);
							float adjusted = math.getDifficulty() * correctCount *accuracyPerc *.01f;
							int intAdjusted = Math.round(adjusted);
							helper.addProgress(tempProgress);
							toResults = new Intent(PlayActivity.this,
									ResultsActivity.class);
							toResults.putExtra("adjusted", intAdjusted);
							
							toResults.putExtra("sendString", sendString);
						    displayInterstitial();
						    if(emergency == true){
						    	startActivity(toResults);
								finish();
						    }

							

						}

					}

					public void onFinish() {

					}
				}.start();
			}
		};
	}

	private void setUpNumbers() {
		math.setNumbers();
		first = math.getFirstNum();
		second = math.getSecondNum();
		operation = math.getCurrentOp();
		ans = math.getAnswer();
		firstNum.setText(first + "");

		int firstLength = Integer.toString(first).length();
		int secondLength = Integer.toString(second).length();
		
		String op = "";
		StringBuilder sb = new StringBuilder();
		if (operation == 1) {
			if (firstLength-secondLength == 2){
				op = "+    ";
			}
			else{
			op = "+   ";
			}
		} else if (operation == 2) {
			if (firstLength-secondLength == 2){
				op = "-    ";
			}
			else{
			op = "-   ";
			}
		} else if (operation == 3) {
			if (firstLength-secondLength == 2){
				op = "x    ";
			}
			else{
			op = "x   ";
			}
		} else if (operation == 4) {
			if (firstLength-secondLength == 2){
				op = "�    ";
			}
			else{
			op = "�   ";
			}
		}
		sb.append(op);
		sb.append(Integer.toString(second));
		String toString = sb.toString();
		secondNum.setText(toString);

	}
	 public void displayInterstitial() {
		    if (interstitial.isLoaded()) {
		      interstitial.show();
		    }
		  }
	private boolean checkAnswer() {
		String strAns = answer.getText().toString();
		int intAns = 0;
		try {
			intAns = Integer.parseInt(strAns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (intAns == math.getAnswer()) {
			return true;
		} else {
			return false;
		}
	}

	public void one(View v) {
		String cur = answer.getText().toString();
		if (cur.length() > 6){
		}
		else{
			answer.setText(cur + "1");
		}
	}

	public void two(View v) {
		String cur = answer.getText().toString();
		if (cur.length() > 6){
		}
		else{
			answer.setText(cur + "2");
		}	}

	public void three(View v) {
		String cur = answer.getText().toString();
		if (cur.length() > 6){
		}
		else{
			answer.setText(cur + "3");
		}	}

	public void four(View v) {
		String cur = answer.getText().toString();
		if (cur.length() > 6){
		}
		else{
			answer.setText(cur + "4");
		}	}

	public void five(View v) {
		String cur = answer.getText().toString();
		if (cur.length() > 6){
		}
		else{
			answer.setText(cur + "5");
		}	}

	public void six(View v) {
		String cur = answer.getText().toString();
		if (cur.length() > 6){
		}
		else{
			answer.setText(cur + "6");
		}	}

	public void seven(View v) {
		String cur = answer.getText().toString();
		if (cur.length() > 6){
		}
		else{
			answer.setText(cur + "7");
		}	}

	public void eight(View v) {
		String cur = answer.getText().toString();
		if (cur.length() > 6){
		}
		else{
			answer.setText(cur + "8");
		}	}

	public void nine(View v) {
		String cur = answer.getText().toString();
		if (cur.length() > 6){
		}
		else{
			answer.setText(cur + "9");
		}	}

	public void zero(View v) {
		String cur = answer.getText().toString();
		if (cur.length() > 6){
		}
		else{
			answer.setText(cur + "0");
		}	}

	public void clear(View v) {
		answer.setText("");
	}

	public void enter(View v) {
		boolean correct = checkAnswer();
		totalCount++;
		if (correct) {
			correctCount++;
			

			if(volume==true){
			sp.play(iTmp, .5f, .5f, 0, 0, 1f);
			}

		} else {
			Review review = new Review(first, second, ans, operation);
			helper.addReview(review);
			myVib.vibrate(50);
			
		}
		setUpNumbers();
		float divide = (float) correctCount / totalCount;
		accuracyPerc = divide * 100;

		updateStats();
		answer.setText("");
	}

	private void updateStats() {
		completedText.setText(Integer.toString(correctCount));
		DecimalFormat df = new DecimalFormat("###");
		df.setRoundingMode(RoundingMode.DOWN);
		accuracyText.setText(df.format(accuracyPerc) + "%");
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	    	if(cdt != null){
	    	cdt.cancel();
	    	}
	    	handler.removeCallbacks(runnable1);
	    	handler.removeCallbacks(runnable2);
	    	handler.removeCallbacks(runnable3);
	    	helper.deleteReview();

	    	
	        finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
