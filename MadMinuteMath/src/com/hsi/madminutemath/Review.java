package com.hsi.madminutemath;

public class Review {
	int firstNum, secondNum, answer, operation;
	public Review(int firstNum, int secondNum, int answer, int operation){
		this.firstNum = firstNum;
		this.secondNum = secondNum;
		this.answer = answer;
		this.operation = operation;
	}
	
	public int getFirstNum(){
		return firstNum;
	}
	public int getSecondNum(){
		return secondNum;	
	}
	public int getAnswer(){
		return answer;
	}
	public int getOperation(){
		return operation;
	}
}
