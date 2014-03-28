package com.hsi.madminutemath.util;


public class Progress {
	private int completed, difficulty;
	private String mode;
	float accuracy;

	public Progress(int completed, float accuracy, int difficulty, String mode){
		this.completed = completed;
		this.accuracy = accuracy;
		this.difficulty = difficulty;
		this.mode = mode;
	}
	
	public int getCompleted(){
		return completed;
	}
	public float getAccuracy(){
		return accuracy;
	}
	public int getDifficulty(){
		return difficulty;
	}
	public String getMode(){
		return mode;
	}
	
}
