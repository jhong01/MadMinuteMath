package com.hsi.madminutemath;

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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_progress);
        noProgress = (RobotoTextView) findViewById(R.id.noProgress);
        helper = new MySQLiteHelper(this);
        List<Progress> progressList = helper.getAllProgress();
        adapter = new ProgressAdapter(this, R.layout.progress_row, progressList);
        ListView list = (ListView)findViewById(R.id.progressList);
        list.setAdapter(adapter); 
        
     
        
        
        
        
        if(progressList.size()==0){
        	noProgress.setVisibility(View.VISIBLE);
        }
        else{
        	noProgress.setVisibility(View.GONE);
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

		adapter.notifyDataSetChanged();
	}
	public void toMain(View v){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	

}
