package com.hsi.madminutemath;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

public class ProgressActivity extends Activity{
	MySQLiteHelper helper;
	ProgressAdapter adapter;
	RobotoTextView noProgress;
	ListView list;
	private static final int HIDER_FLAGS = 0;// SystemUiHider.FLAG_HIDE_NAVIGATION;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();      
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_progress);
        noProgress = (RobotoTextView) findViewById(R.id.noProgress);
        helper = new MySQLiteHelper(this);
        List<Progress> progressList = helper.getAllProgress();
        Collections.reverse(progressList);

        adapter = new ProgressAdapter(this, R.layout.progress_row, progressList);
         list = (ListView)findViewById(R.id.progressList);
        list.setAdapter(adapter); 
        
     
        
        
        
        
        if(progressList.size()==0){
        	noProgress.setVisibility(View.VISIBLE);
        	list.setVisibility(View.GONE);
        }
        else{
        	noProgress.setVisibility(View.GONE);
        	list.setVisibility(View.VISIBLE);
        }
       
        
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		 
	}

	public void reset(View v){
		helper.deleteProgress();
    	noProgress.setVisibility(View.VISIBLE);
    	list.setVisibility(View.GONE);
		adapter.notifyDataSetChanged();
	}
	public void toMain(View v){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	

}
