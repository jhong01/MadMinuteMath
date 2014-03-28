package com.hsi.madminutemath.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "data";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static final String CREATE_TABLE_REVIEW = "CREATE TABLE review ( "
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "answer INTEGER, "
			+ "firstNum INTEGER, secondNum INTEGER, operation INTEGER )";
	private static final String CREATE_TABLE_PROGRESS = "CREATE TABLE progress ( "
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "correct INTEGER, "
			+ "accuracy REAL, difficulty INTEGER, mode TEXT)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_REVIEW);
		db.execSQL(CREATE_TABLE_PROGRESS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older books table if existed
		db.execSQL("DROP TABLE IF EXISTS review");
		db.execSQL("DROP TABLE IF EXISTS progress");

		// create fresh books table
		this.onCreate(db);
	}

	public void addReview(Review review) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("firstNum", review.getFirstNum());
		values.put("secondNum", review.getSecondNum());
		values.put("answer", review.getAnswer());

		values.put("operation", review.getOperation());

		db.insert("review", null, values);
		db.close();

	}

	public void addProgress(Progress progress) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("correct", progress.getCompleted());
		values.put("accuracy", progress.getAccuracy());

		values.put("difficulty", progress.getDifficulty());
		values.put("mode", progress.getMode());

		db.insert("progress", null, values);
		db.close();
	}

	public Review getReview(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM review WHERE id = " + id;
		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null) {
			c.moveToFirst();
		}
		Review review = new Review(c.getInt(c.getColumnIndex("firstNum")),
				c.getInt(c.getColumnIndex("secondNum")), c.getInt(c
						.getColumnIndex("answer")), c.getInt(c
						.getColumnIndex("operation")));
		db.close();
		return review;

	}

	public Progress getProgress(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM progress WHERE id = " + id;
		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null) {
			c.moveToFirst();
		}
		Progress progress = new Progress(c.getInt(c.getColumnIndex("correct")),
				c.getInt(c.getColumnIndex("accuracy")), c.getInt(c
						.getColumnIndex("difficulty")), c.getString(c
						.getColumnIndex("mode")));
		db.close();
		return progress;

	}

	public List<Review> getAllReview() {
		List<Review> reviews = new LinkedList<Review>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM review";
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
				Review review = new Review(c.getInt(c
						.getColumnIndex("firstNum")), c.getInt(c
						.getColumnIndex("secondNum")), c.getInt(c
						.getColumnIndex("answer")), c.getInt(c
						.getColumnIndex("operation")));
				reviews.add(review);
			} while (c.moveToNext());
		}
		db.close();
		return reviews;

	}
	public List<Progress> getAllProgress() {
		List<Progress> progresses = new ArrayList<Progress>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM progress";
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
				Progress progress = new Progress(c.getInt(c
						.getColumnIndex("correct")), c.getInt(c
						.getColumnIndex("accuracy")), c.getInt(c
						.getColumnIndex("difficulty")), c.getString(c
						.getColumnIndex("mode")));
				progresses.add(progress);
			} while (c.moveToNext());
		}
		db.close();
		return progresses;

	}
	public void deleteReview(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("review", null, null);
	    db.close();

	}
	public void deleteProgress(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("progress", null, null);
	    db.close();

	}

}
