package gui;

/*
 * Class to compute in constant time complexity the
 * sum of elements in submatrix defined by up-left and
 * down-right positions of a given matrix.
 */
public class PartialSumMatrix {
	
	private int[][] matrix;
	
	private PartialSumMatrix() {}
	
	public int getSum(int u, int l, int d, int r) {
		
		if(d < u)
			throw new IllegalArgumentException("Value of up must be lower or equal than down value");
		if(r < l)
			throw new IllegalArgumentException("Value of left must be lower or equal than right value");
		if(l < 0 || r < 0 || u < 0 || d < 0)
			throw new IllegalArgumentException("Values must be non-negative");
		if(r >= matrix[0].length || d >= matrix.length)
			throw new IllegalArgumentException("Illegal values");
		
		int sum = matrix[d][r];
		if(l > 0)
			sum = sum - matrix[d][l-1];
		if(u > 0)
			sum = sum - matrix[u-1][r];
		if(l > 0 && u > 0)
			sum = sum + matrix[u-1][l-1];
		
		return sum;
	}
	
	private static int[][] precompute(int[][] elements) {
		if(elements == null)
			throw new IllegalArgumentException("Null value not accepted");
		
		int[][] result = new int[elements.length][elements[0].length];
		for(int i = 0; i < result.length; i++) {
			for(int j = 0; j < result[i].length; j++) {
				result[i][j] = elements[i][j];
				if(i > 0)
					result[i][j] += result[i-1][j];
				if(j > 0)
					result[i][j] += result[i][j-1];
				if(i > 0 && j > 0)
					result[i][j] -= result[i-1][j-1];
			}
		}
		return result;
	}
	
	public static PartialSumMatrix generateForArray(int[][] elements) {
		PartialSumMatrix psm = new PartialSumMatrix();
		psm.matrix = precompute(elements);
		return psm;
	}

}
