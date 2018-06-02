package hungarianAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HungarianAlgorithm {
	
    /*The Hungarian Method:
       1. Subtract the smallest entry in each row from all the other entries in the row. This will make the smallest entry in the row now equal to 0.
       2. Subtract the smallest entry in each column from all the other entries in the column. This will make the smallest entry in the column now equal to 0.
       3. Draw lines through the row and columns that have the 0 entries such that the fewest lines possible are drawn.
       4. If there are lines drawn, an optimal assignment of zeros is possible and the algorithm is finished. If the number of lines is less than , then the optimal number of zeroes is not yet reached. Go to the next step.
       5. Find the smallest entry not covered by any line. Subtract this entry from each row that isn’t crossed out, and then add it to each column that is crossed out. Then, go back to Step 3.
*/
	public static int MAXVALUE = 100;
	public static int lineCount = 0;
	public static int n = 0;
	
	public static void main (String[] args){
		
		//Test arrays
		//int[][] arr1 = { { 0, 1, 0, 1, 1 }, { 1, 1, 0, 1, 1 }, { 1, 0, 0, 0, 1 },
          //      { 1, 1, 0, 1, 1 }, { 1, 0, 0, 1, 0 } };
		int[][] arr1 = { { 56, 23, 89, 1, 3 }, { 7, 14, 75, 90, 22 }, { 9, 47, 50, 12, 84 },
                { 32, 51, 94, 29, 16 }, { 6, 7, 30, 85, 99 } };
		
		n = arr1.length;
		
		int[][]arr3 = null;
		//print initial array
		printArray(arr1);
		//STEPS 1 and 2
		subMin(arr1);
		int pass = 1;
		
		do {
			System.out.println("----------------------------------");
			System.out.println("PASS: " + pass);
			System.out.println("----------------------------------");
			lineCount = 0;
			printArray(arr1);
			//STEP 3
			arr3 = minVertCover(arr1);
			System.out.println("linecount: " + lineCount);
			System.out.println("n = " + n);
			//STEP 5
			if (lineCount < n)
				leastNotCovered(arr1, arr3);
			pass++;
		}
		//STEP 4
		while (lineCount < n);
	}
	
	
	
	//step 3
	//Method to draw min-vertex cover lines over the previously-0'd array arr
	//returns an array of 0's and 1's representing the min-vertex-cover lines
	public static int[][] minVertCover(int[][] arr1){
		 //arr1 is the master array of values, already containing some 0's after steps 1 and 2
		int[][] arr2 = new int[n][n]; //arr2 holds the number of 0's horizontally or vertically adjacent to the index (whichever is higher)
		int[][] arr3 = new int[n][n]; //arr3 holds the lines drawn over the array - 1's drawn on an array of 0's
		
		//hvHigh is called on each 0 value in the array in order to generate the adjacency values array arr2.
		for (int row = 0; row < n; row++){
			for (int col = 0; col < n; col++){
					arr2[row][col] = MAXVALUE+1; //originally set all arr2 values to max+1 to distinguish 0 values.
				if (arr1[row][col] == 0)
					arr2[row][col] = hvHigh (arr1, row, col);
			}
		}
		printArray(arr2);
		ArrayList<int[]> zeroList = listZeros(arr2);
		
		//NEW DRAWLINES CODE (draws the 'highest value' lines first): 
		for(int[] curr : zeroList){
			if (arr2[curr[1]][curr[2]] > MAXVALUE) //break if the arr2 value is > MAXVALUE, meaning a line has already been drawn
				continue;
			int rowSum = 0;
			int colSum = 0;
			for (int row = 0; row < n; row++){ //add all arr2 values in curr's column to sum if <= MAXVALUE
				if (arr2[row][curr[2]] <= MAXVALUE)
					colSum++;
			}
			for (int col = 0; col < n; col++){ //add all arr2 values in curr's row to sum
				//if (col != curr[2] && arr2[curr[1]][col] <= MAXVALUE) //so we don't add the current value twice
				if (arr2[curr[1]][col] <= MAXVALUE)	
					rowSum++;
			}
			if (rowSum > colSum){
				arr2[curr[1]][curr[2]] = -1;
				drawLines(arr2, arr3, curr[1], curr[2]);
			} else {
				arr2[curr[1]][curr[2]] = 1;
				drawLines(arr2, arr3, curr[1], curr[2]);
			}	}
		zeroList.clear();
		printArray(arr3);
		
		return arr3;
	}
	
	//step 5
	//only called if number of lines < n - a match hasn't been found
	//find the smallest entry not covered, subtract it from each not-covered row,
	//add it to each covered column, return to minVertCover
	public static int[][] leastNotCovered (int[][] arr1, int[][] arr3){
		
		int min = MAXVALUE + 1;
		//for loops to find minimum not-covered value
		for (int row = 0; row < n; row++){
			for (int col = 0; col < n; col++){
				if (arr1[row][col] < min && arr1[row][col] > 0 && arr3[row][col] == 0){
					min = arr1[row][col];
				}
			}
		}
		//loop through all rows, 
		for (int row = 0; row < n; row++){
			int col = 0;
			boolean covered = true;
			//if the row has any value != 1 in arr3, it's not covered
			while (covered && col < n){
				if (arr3[row][col] != 1)
					covered = false;
				col++;
			}
			//subtract the previously found minimum value from every value in a non-covered row.
			if (!covered)
				for (int c = 0; c < n; c++){
					arr1[row][c] -= min;
				}
			}
		
		//loop through all columns
		for (int col = 0; col < n; col++){
			int row = 0;
			boolean covered = true;
			//if the column has any value != 1 in arr3, it's not covered
			while (covered && col < n && row < n){
				if (arr3[row][col] != 1)
					covered = false;
				row++;
			}
			//if the column is covered, add the minimum value to each value in the column
			if (covered)
				for (int r = 0; r < n; r++){
					arr1[r][col] += min;
				}
		}
		
		return arr1;
	}
	
	//creates an arrayList of every non-max value in arr2, sorts them by value, returns that arrayList
	public static ArrayList<int[]> listZeros (int[][] arr2){
		//list to hold a length 3 integer array for each 0, first index holds the 0's arr2 value, 2nd its row, and 3rd its column.
				ArrayList<int[]> zeroList = new ArrayList<int[]>();
				//loop through arr2, finding values less than max+1 would be quicker to compile zeroList from hvHigh(), when arr2 is completed.
				for (int row = 0; row < n; row++){
					for (int col = 0; col < n; col++){
						if (arr2[row][col] <= MAXVALUE)  
							zeroList.add(new int[]{Math.abs(arr2[row][col]),row,col}); //add a corresponding entry in zeroList.
					}
				}
				Collections.sort(zeroList, new Comparator<int[]>(){ //sort zero list
					//sorts zeroList by the value of index 0 of each member array in descending order
					@Override
					public int compare(int[] a, int[] b) {
						Integer aint = b[0];
						return aint.compareTo(a[0]);
					}
				});
			
			return zeroList;		
	}
	
	//called for each value of 0 in the subtracted array, tallies the number of 0's adjacent to the index horizontally and vertically
	//returns the number of 0's in the column, or the row, whichever is larger, or 0 if they're equal.
	public static int hvHigh (int[][] arr, int row, int col){
		//counter ints to hold the running tally of horizontal and vertical 0's
		int cr = 0;
		int cc = 0;
		//tally 0's in the corresponding row
		for (int i = 0; i < arr.length; i++){
			if (arr[row][i] == 0)
				cr++;
		}
		//tally 0's in the corresponding column
		for (int i = 0; i < arr.length; i++){
			if (arr[i][col] == 0)
				cc++;
		}
		
		//if more 0's on x (horizontal), change to negative and return, if equal # or no adjacent zeros, draw horizontal line

		/* int toReturn = cc - cr;
		if (toReturn == 0)
			toReturn = -1;
		
		return toReturn; */
		
		if (cr > cc){
			cr *= -1;
			return cr;
		} else if (cc > cr){
			return cc;
		} else{
			return 0;
		}
}
	//given a specified index, 'draws a line' (writes 1 values to arr3) along an arr3 row or column based on the specified arr2 value
	//a positive arr2 value indicates a vertical line, a negative value indicates a horizontal line
	public static void drawLines(int[][] arr2, int[][] arr3, int row, int col){
		
		if (arr2[row][col] > MAXVALUE)
			return;
		if (arr2[row][col] > 0 ){//positive value = vertical line
			lineCount++;
			for (int i = 0; i < n; i++){
				arr2[i][col] = MAXVALUE+1; //remove values in the same row/column to avoid unnecessary line-drawing
				arr3[i][col] = 1;
			}
		} else {			//negative value = horizontal line
			lineCount++;
			for(int i = 0; i < n; i++){
				arr2[row][i] = MAXVALUE+1;
				arr3[row][i] = 1;
			}
		}
	}
	
	//covers steps 1 and 2:
		//subtracting the smallest entry in each row from every other value in the row, 
		//and do the same for columns
		public static int[][] subMin(int[][] arr1){
			//rows
			int min;
			for (int row = 0; row < n; row++){
				min = MAXVALUE + 1;
				//first loop to find the minimum value per row
				for (int col = 0; col < n; col++){
					if (arr1[row][col] < min)
						min = arr1[row][col];
				}
				//second loop to subtract minimum from each value in the row
				for (int col = 0; col < n; col++){
					arr1[row][col] = arr1[row][col] - min;
				}
			}
			//columns
			for (int col = 0; col < n; col++){
				min = MAXVALUE + 1;
				//first loop to find the minimum value per col
				for (int row = 0; row < n; row++){
					if (arr1[row][col] < min)
						min = arr1[row][col];
				}
				//second loop to subtract minimum from each value in the col
				for (int row = 0; row < n; row++){
					arr1[row][col] = arr1[row][col] - min;
				}
			}
			return arr1;
		}
	
	//Formats and prints the contents of a 2D array to the console
	//if a value is larger than MAXVALUE (in the case of arr2), prints a hyphen
	public static void printArray(int[][] toPrint){
		for (int row = 0; row < toPrint.length; row++){
			for (int col = 0; col < toPrint[row].length; col++){
				if (toPrint[row][col] > MAXVALUE)
					System.out.print("-" + "\t");
				else
					System.out.print(toPrint[row][col] + "\t");
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
