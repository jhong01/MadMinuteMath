package com.hsi.madminutemath;

import java.util.List;

import com.hsi.madminutemath.MathAdapter.MathHolder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ScoreAdapter extends ArrayAdapter<String>{
	Context mContext;
	int resourceID;
	List<String> objects;

	public ScoreAdapter(Context context, int resource, List<String> objects) {
		super(context, resource, objects);
		mContext=context;
		resourceID = resource;
		this.objects = objects;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View row = convertView;
		ScoreHolder holder = null;
		if(row == null){
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			row = inflater.inflate(resourceID, parent, false);
			holder = new ScoreHolder();
			holder.text = (RobotoTextView) row.findViewById(R.id.scoreLabel);
			row.setTag(holder);
		}
		else{
			holder = (ScoreHolder)row.getTag();
		}
		holder.text.setText(objects.get(position));
		
		
		return row;
	}
	static class ScoreHolder{
		RobotoTextView text;
	}

}
