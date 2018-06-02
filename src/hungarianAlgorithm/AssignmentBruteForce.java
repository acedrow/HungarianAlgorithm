package hungarianAlgorithm;

import java.util.ArrayList;

public class AssignmentBruteForce {
	
	public static int min = Integer.MAX_VALUE;
	public static int n = 0;
	public static ArrayList<Integer> solution = new ArrayList<Integer>();
	
	//main method for testing purposes
	public static void main (String[] args){
		 int[][] arr1 = { { 56, 23, 89, 1, 3 }, { 7, 14, 75, 90, 22 }, { 9, 47, 50, 12, 84 },
                { 32, 51, 94, 29, 16 }, { 6, 7, 30, 85, 99 } }; 
		
		int[][] arr11 = {{1,2,3},{6,4,1},{0,9,3}};
		
		bruteForceSolution(arr1);
		System.out.println(min);
		System.out.println(solution);
	}

	//given the original array of assignment problem costs, computes a lowest-value solution via brute-force (iteration)
	public static int bruteForceSolution(int[][] arr1){
		n = arr1.length;
		ArrayList<Integer> usedCol = new ArrayList<Integer>();
		rowIterator (0, 0, usedCol, arr1);
		
		return 0;
	}
	
	public static int rowIterator(int currRow, int runningSum, ArrayList<Integer> usedCol, int[][] arr1){
		ArrayList<Integer> myUsedCol = new ArrayList<Integer>(); //copy previously used column values into iteration-specific instance
		for (int col : usedCol){
			myUsedCol.add(col);
		}
		if (currRow >= n){ //our end condition. Each recursive call increments the currRow, 
			//when we get past the bottom row (currRow == n), we've summed one complete 
			if (runningSum < min){
				min = runningSum;
				solution.clear();
				for (int i : myUsedCol){
					solution.add(i);
				}	
			}
		return 1;
		}
		for (int col = 0; col < n; col++){ 
			while ((myUsedCol.size()) > currRow){ //make sure our previously used column list is the correct length
				myUsedCol.remove((myUsedCol.size()-1));
			}
			boolean used = false;
			int i = 0;
			//need to add code to remove the second-most-recent usedCol entry once the recursive call is made.
			do {
				
				if (myUsedCol.size() == 0){
					used = false;
					break;
				}
				if (myUsedCol.get(i) == col){
					used = true;
				}
				
				i++;
			}
			while (i < myUsedCol.size());
				
				if (!used){
					int myRunningSum = runningSum + arr1[currRow][col]; //add the value to running sum
					myUsedCol.add(col); //add the column to our used columns list
					int nextRow = currRow+1;
					rowIterator(nextRow, myRunningSum, myUsedCol, arr1);
				}
		}
		return 0;
	}
}
