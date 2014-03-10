package com.hsi.madminutemath;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ProgressAdapter extends ArrayAdapter<Progress> {
	Context mContext;
	int resourceID;
	List<Progress> objects;

	public ProgressAdapter(Context context, int resource, List<Progress> objects) {
		super(context, resource, objects);
		mContext = context;
		resourceID = resource;
		this.objects = objects;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View row = convertView;
		ProgressHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(resourceID, parent, false);
			holder = new ProgressHolder();
			holder.correct = (RobotoTextView) row.findViewById(R.id.progress_correct);
			holder.accuracy = (RobotoTextView) row
					.findViewById(R.id.progress_accuracy);
			holder.difficulty = (RobotoTextView) row
					.findViewById(R.id.progress_difficulty);
			holder.mode = (RobotoTextView) row.findViewById(R.id.progress_mode);
			holder.score = (RobotoTextView) row.findViewById(R.id.progress_score);
			row.setTag(holder);
		} else {
			holder = (ProgressHolder) row.getTag();
		}
		
			holder.correct.setText(objects.get(position).getCompleted()+ "");
			DecimalFormat df = new DecimalFormat("###");
			df.setRoundingMode(RoundingMode.DOWN);
			String accuracyString = df.format(objects.get(position)
					.getAccuracy()) + "%";
			holder.accuracy.setText(accuracyString);
			holder.difficulty.setText(objects.get(position).getDifficulty()+ "");
			holder.mode.setText(objects.get(position).getMode());
			float adjusted = objects.get(position).getDifficulty()
					* objects.get(position).getCompleted()
					* objects.get(position).getAccuracy() * .01f;
			int intAdjusted = Math.round(adjusted);
			String scoreString = Integer.toString(intAdjusted);
			holder.score.setText(scoreString);

		return row;
	}

	static class ProgressHolder {
		RobotoTextView correct, accuracy, difficulty, mode, score;
	}
}