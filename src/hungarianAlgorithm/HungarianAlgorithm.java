package hungarianAlgorithm;

public class HungarianAlgorithm {
	
	public static int MAXVALUE = 100;

	public static void main (String[] args){
		//subtractLowest for parts 1 to 2
		//drawLines for part 3
		
		//dummy array to test with
		//int[][] arr1 = { { 0, 1, 0, 1, 1 }, { 1, 1, 0, 1, 1 }, { 1, 0, 0, 0, 1 },
          //      { 1, 1, 0, 1, 1 }, { 1, 0, 0, 1, 0 } };
		int[][] arr1 = { { 56, 23, 89, 1, 3 }, { 7, 14, 75, 90, 22 }, { 9, 47, 50, 12, 84 },
                { 32, 51, 94, 29, 16 }, { 6, 7, 30, 85, 99 } };
		arr1 = subMin(arr1);
		minVertCover(arr1);
	}
	
	//covers steps 1 and 2:
	//subtracting the smallest entry in each row from every other value in the row, 
	//and do the same for columns
	public static int[][] subMin(int[][] arr1){
		//rows
		int min;
		for (int row = 0; row < arr1.length; row++){
			min = MAXVALUE + 1;
			//first loop to find the minimum value per row
			for (int col = 0; col < arr1.length; col++){
				if (arr1[row][col] < min)
					min = arr1[row][col];
			}
			//second loop to subtract minimum from each value in the row
			for (int col = 0; col < arr1.length; col++){
				arr1[row][col] = arr1[row][col] - min;
			}
		}
		//columns
		for (int col = 0; col < arr1.length; col++){
			min = MAXVALUE + 1;
			//first loop to find the minimum value per col
			for (int row = 0; row < arr1.length; row++){
				if (arr1[row][col] < min)
					min = arr1[row][col];
			}
			//second loop to subtract minimum from each value in the col
			for (int row = 0; row < arr1.length; row++){
				arr1[row][col] = arr1[row][col] - min;
			}
		}
		
		return arr1;
	}
	
	//step 3
	//Method to draw min-vertex cover lines over the previously-0'd array arr
	//returns an array of 0's and 1's representing the min-vertex-cover lines
	public static int[][] minVertCover(int[][] arr1){
		int n = arr1.length; //arr1 is the master array of values, already containing some 0's after steps 1 and 2
		int[][] arr2 = new int[n][n]; //arr2 holds the number of 0's horizontally or vertically adjacent to the index (whichever is higher)
		int[][] arr3 = new int[n][n]; //arr3 holds the lines drawn over the array.
		
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
		
		printArray(arr3);
		
		return null;
	}
	
	public static int hvHigh (int[][] arr, int x, int y){
		//counter ints to hold the running tally of horizontal and vertical 0's
		int cx = 0;
		int cy = 0;
		//tally 0's in the corresponding row
		for (int i = 0; i < arr.length; i++){
			if (arr[x][i] == 0)
				cx++;
		}
		//tally 0's in the corresponding column
		for (int i = 0; i < arr.length; i++){
			if (arr[i][y] == 0)
				cy++;
		}
		
		//if more 0's on x (horizontal), change to negative and return
		if (cx > cy){
			cx *= -1;
			return cx;
		} else if (cy > cx){
			return cy;
		} else{
			return 0;
		}
	}
	
	public static void drawLines(int[][] arr2, int[][] arr3, int row, int col){
		//positive value = vertical line
		int num = arr2[row][col];
		if (arr2[row][col] > 0){
			for (int i = 0; i < arr2.length; i++){
				if (arr2[i][col] > 0)
					arr2[i][col] = 0; //0 out similar values in the same row/column to avoid unnecessary line-drawing
				arr3[i][col] = 1;
			}
			//negative value = horizontal line
		} else {
			for(int i = 0; i < arr2.length; i++){
				if (arr2[row][i] < 0)
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
