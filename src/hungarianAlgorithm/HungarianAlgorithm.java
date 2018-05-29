package hungarianAlgorithm;

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
		
		
		do {
			lineCount = 0;
			printArray(arr1);
			//STEP 3
			arr3 = minVertCover(arr1);
			System.out.println("linecount: " + lineCount);
			System.out.println("n = " + n);
			//STEP 5
			if (lineCount < n)
				leastNotCovered(arr1, arr3);
		}
		//STEP 4
		while (lineCount < n);
			
		
		
		
	
		//else
			//findSolution();
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
	
	//step 3
	//Method to draw min-vertex cover lines over the previously-0'd array arr
	//returns an array of 0's and 1's representing the min-vertex-cover lines
	public static int[][] minVertCover(int[][] arr1){
		 //arr1 is the master array of values, already containing some 0's after steps 1 and 2
		int[][] arr2 = new int[n][n]; //arr2 holds the number of 0's horizontally or vertically adjacent to the index (whichever is higher)
		int[][] arr3 = new int[n][n]; //arr3 holds the lines drawn over the array - 1's drawn on an array of 0's
		
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				if (arr1[i][j] == 0)
					arr2[i][j] = hvHigh (arr1, i, j);
			}
		}
		printArray(arr2);
		
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				if (arr2[i][j] != 0 )
					drawLines(arr2, arr3, i, j);
			}
		}
		if (lineCount == n){
			//find assignment return, (verify with brute force)
		} else{
			//do step 5
		}
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
	
	//called for each value of 0 in the subtracted array, tallies the number of 0's adjacent to the index horizontally and vertically
	public static int hvHigh (int[][] arr, int row, int col){
		//counter ints to hold the running tally of horizontal and vertical 0's
		int cx = 0;
		int cy = 0;
		//tally 0's in the corresponding row
		for (int i = 0; i < arr.length; i++){
			if (arr[row][i] == 0)
				cx++;
		}
		//tally 0's in the corresponding column
		for (int i = 0; i < arr.length; i++){
			if (arr[i][col] == 0)
				cy++;
		}
		
		//if more 0's on x (horizontal), change to negative and return, if equal # or no adjacent zeros, draw horizontal line
		if (cx > cy){
			cx *= -1;
			return cx;
		} else if (cy > cx){
			return cy;
		} else{
			return -1;
		}
	}
	
	public static void drawLines(int[][] arr2, int[][] arr3, int row, int col){
		
		//positive value = vertical line
		// int num = arr2[row][col];
		
		if (arr2[row][col] > 0){
			lineCount++;
			for (int i = 0; i < n; i++){
				if (arr2[i][col] != 0)
					arr2[i][col] = 0; //0 out similar values in the same row/column to avoid unnecessary line-drawing
				arr3[i][col] = 1;
			}
			//negative value = horizontal line
		} else {
			lineCount++;
			for(int i = 0; i < n; i++){
				if (arr2[row][i] != 0)
					arr2[row][i] = 0;
				arr3[row][i] = 1;
			}
		}
	}
	
	//Prints the contents of a 2D array to the console
	public static void printArray(int[][] toPrint){
		for (int i = 0; i < toPrint.length; i++){
			for (int j = 0; j < toPrint[i].length; j++){
				System.out.print(toPrint[i][j] + "\t");
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
