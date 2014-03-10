package com.hsi.madminutemath;

import java.util.List;

import com.hsi.madminutemath.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MathAdapter extends ArrayAdapter<Review>{
	Context mContext;
	int resourceID;
	List<Review> objects;

	public MathAdapter(Context context, int layoutResourceID,
			List<Review> objects) {
		super(context, layoutResourceID, objects);
		mContext=context;
		resourceID = layoutResourceID;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MathHolder holder = null;
		if(row == null){
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			row = inflater.inflate(resourceID, parent, false);
			holder = new MathHolder();
			holder.firstNum = (RobotoTextView) row.findViewById(R.id.topNumber2);
			holder.secondNum = (RobotoTextView) row.findViewById(R.id.botNumber2);
			holder.answer = (RobotoTextView)row.findViewById(R.id.answer2);
			row.setTag(holder);
		}
		else{
			holder = (MathHolder)row.getTag();
		}
		Review review = objects.get(position);
		holder.firstNum.setText(Integer.toString(review.getFirstNum()));
		String op = "";
		StringBuilder sb = new StringBuilder();
		int firstLength = Integer.toString(review.getFirstNum()).length();
		int secondLength = Integer.toString(review.getSecondNum()).length();
		int operation = review.getOperation();
		if (operation == 1) {
			if (firstLength-secondLength == 2){
				op = "+   ";
			}
			else{
			op = "+ ";
			}
		} else if (operation == 2) {
			if (firstLength-secondLength == 2){
				op = "-   ";
			}
			else{
			op = "- ";
			}
		} else if (operation == 3) {
			if (firstLength-secondLength == 2){
				op = "x   ";
			}
			else{
			op = "x ";
			}
		} else if (operation == 4) {
			if (firstLength-secondLength == 2){
				op = "÷   ";
			}
			else{
			op = "÷ ";
			}
		}
		sb.append(op);
		sb.append(Integer.toString(review.getSecondNum()));
		String toString = sb.toString();	
		holder.secondNum.setText(toString);
		holder.answer.setText(Integer.toString(review.getAnswer()));
		return row;
	}
	
	static class MathHolder{
		RobotoTextView firstNum, secondNum, answer;
	}
	

}
