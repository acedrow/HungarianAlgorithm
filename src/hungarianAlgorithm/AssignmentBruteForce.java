package hungarianAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AssignmentBruteForce {
	
	public static int min = Integer.MAX_VALUE;
	public static int n = 0;
	public static ArrayList<Integer> solution = new ArrayList<Integer>();
	
	//main method for testing purposes
	public static void main (String[] args){
		int[][] arr1 = { { 56, 23, 89, 1, 3 }, { 7, 14, 75, 90, 22 }, { 9, 47, 50, 12, 84 },
                { 32, 51, 94, 29, 16 }, { 6, 7, 30, 85, 99 } };
		
		bruteForceSolution(arr1);
		System.out.println(min);
		System.out.println(solution);
	}

	//given the original array of assignment problem costs, computes a lowest-value solution via brute-force (iteration)
	public static int bruteForceSolution(int[][] arr1){
		n = arr1.length;
		ArrayList<Integer> usedCol = new ArrayList<Integer>();
		usedCol.add(-1);
		rowIterator (0, 0, usedCol, arr1);
		
		return 0;
	}
	
	public static int rowIterator(int currRow, int runningSum, ArrayList<Integer> usedCol, int[][] arr1){
		if (currRow >= n) //our end condition. Each recursive call increments the currRow, 
			//when we get past the bottom row (currRow == n), we've summed one complete 
			if (runningSum < min){
				min = runningSum;
				solution.clear();
				for (int i : usedCol){
					solution.add(i);
				}
			}
		
		for (int col = 0; col < n; col++){ 
			boolean used = true;
			int i = 0;
			//need to add code to remove the second-most-recent usedCol entry once the recursive call is made.
			do {
				if (usedCol.size() == 0 || usedCol.get(i) == col){
					used = false;
				}
				i++;
			}
			while (i <= usedCol.size() && used);
				
				
			
				if (!used){
					runningSum += arr1[currRow][col]; //add the value to running sum
					usedCol.add(col); //add the column to our used columns list
					rowIterator(currRow++, runningSum, usedCol, arr1);
				}
		}
		return 0;
	}
}
