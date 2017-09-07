import java.util.Arrays;

public class TestMain {

	public static void main(String[] args) {
		int[] inputArray = new int[]{3, 6, -2, -5, 7, 3};
		System.out.println(test(inputArray));
	}

	/*
	 * For inputArray = [3, 6, -2, -5, 7, 3], the output should be
		adjacentElementsProduct(inputArray) = 21.
	 * 
	 * 
	 * 
	 */
	
	
	public static int test(int[] inputArray){
		
		int[] sortArray = inputArray.clone();
		
		Arrays.sort(sortArray);
		
		
		int maxVal = sortArray[sortArray.length - 1];
		
		boolean isCalc = false;
		for(int i : inputArray){
			if(isCalc) return maxVal * i;
			
			if(maxVal == i) isCalc = true;
		}
		
		
		
		return 0;
	}

}
