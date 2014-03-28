package com.hsi.madminutemath.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Operation {

	Context mContext;
	int firstNum, secondNum = 0;
	int currentOp = 0;
	int answer = 0;

	public Operation(Context context) {
		mContext = context;
	}

	public int getFirstNum() {
		return firstNum;
	}

	public int getSecondNum() {
		return secondNum;
	}

	public int getDifficulty() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		int difficulty = prefs.getInt("difficulty", 2);
		return difficulty;
	}

	public int getCurrentOp() {
		return currentOp;
	}

	private void genCurrentOp() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		boolean add = prefs.getBoolean("addition", true);
		boolean sub = prefs.getBoolean("subtraction", false);
		boolean mult = prefs.getBoolean("multiplication", false);
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
		Random r = new Random();
		int opPick = r.nextInt(array.size());
		currentOp = array.get(opPick);

	}

	private void genAnswer(int operation) {
		if (operation == 1) {
			answer = firstNum + secondNum;
		} else if (operation == 2) {
			answer = firstNum - secondNum;
		} else if (operation == 3) {
			answer = firstNum * secondNum;
		} else if (operation == 4) {
			answer = firstNum / secondNum;
		}
	}

	private boolean checkLogic(int operation) {
		switch (operation) {
		case 1:
			return true;
		case 2:
			if (firstNum < secondNum) {
				int temp = firstNum;
				firstNum = secondNum;
				secondNum = temp;
				return true;
			} else {
				return true;
			}
		case 3:
			if (firstNum < secondNum) {
				int temp = firstNum;
				firstNum = secondNum;
				secondNum = temp;
				return true;
			} else {
				return true;
			}
		case 4:
			List<Integer> list = Operation.findFactors(firstNum);
			Random rand = new Random();
			
			if(list.size()==2){
				return false;
			}
			else{
				int randInt = rand.nextInt(list.size());
				secondNum = list.get(randInt);
				if(secondNum == 0 || secondNum == 1 || secondNum == firstNum){
					return false;
				}
				return true;
			}
			/*if (secondNum == 0) {
				return false;
			} else if (firstNum % secondNum != 0) {
				return false;
			} else {
				return true;
			}*/
		default:
			return false;
		}

	}

	public int getAnswer() {
		return answer;
	}

	public void setNumbers() {
		genCurrentOp();
		firstNum = generateNum(firstNum, currentOp);
		secondNum = generateNum(secondNum, currentOp);
		;
		boolean logic = checkLogic(currentOp);
		while (logic == false) {
			firstNum = generateNum(firstNum, currentOp);
			secondNum = generateNum(secondNum, currentOp);
			logic = checkLogic(currentOp);
		}
		genAnswer(currentOp);
	}

	// addition = 0, subtraction = 1, multiplication = 2, division = 3
	private int generateNum(int number, int operation) {
		int difficulty = getDifficulty();
		Random r = new Random();
		int range = 0;
		switch (difficulty) {
		case 1:
			switch (operation) {
			case 1:
				range = 20;
				break;
			case 2:
				range = 20;
				break;
			case 3:
				range = 5;
				break;
			case 4:
				range = 25;
				break;
			}
			break;

		case 2:
			switch (operation) {
			case 1:
				range = 50;
				break;
			case 2:
				range = 50;
				break;
			case 3:
				range = 9;
				break;
			case 4:
				range = 81;
				break;
			}
			break;
		case 3:
			switch (operation) {
			case 1:
				range = 100;
				break;
			case 2:
				range = 100;
				break;
			case 3:
				range = 12;
				break;
			case 4:
				range = 144;
				break;
			}
			break;
		case 4:
			switch (operation) {
			case 1:
				range = 1000;
				break;
			case 2:
				range = 1000;
				break;
			case 3:
				range = 20;
				break;
			case 4:
				range = 400;
				break;
			}
			break;
		}
		Random rand = new Random();
		return rand.nextInt(range);
	}
	private static List<Integer> findFactors(int num)
    {
        int incrementer = 1;
        if (num % 2 != 0)
        {
            incrementer = 2; //only test the odd ones
        }
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= num / 2; i=i+incrementer)
        {
            if (num % i == 0)
            {
                list.add(i);
            }
        }
        list.add(num);
        return list;
    }
}
