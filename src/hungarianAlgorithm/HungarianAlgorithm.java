package hungarianAlgorithm;

public class HungarianAlgorithm {

	public static void main (String[] args){
		//subtractLowest for parts 1 to 2
		//drawLines for part 3
		
		//dummy array to test with
		int[][] arr1 = { { 0, 1, 0, 1, 1 }, { 1, 1, 0, 1, 1 }, { 1, 0, 0, 0, 1 },
                { 1, 1, 0, 1, 1 }, { 1, 0, 0, 1, 0 } };
		drawLines(arr1);
	}
	
	//master-method to draw min-vertex cover lines over the previously-0'd array arr
	public static int[][] drawLines(int[][] arr){
		int n = arr.length;
		int[][] arr1 = new int[n][n];
		int[][] arr2 = new int[n][n];
		
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				if (arr[i][j] == 0)
					arr1[i][j] = hvHigh (arr, i, j);
			}
		}
		printArray(arr1);
		
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
