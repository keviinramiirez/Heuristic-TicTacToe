import java.util.Random;

public class Util 
{
	/** creates a 2d array from the string given
	 *  @param s string representation as a string 
	 */
	public static int[][] createMatrix(String s) {
		s = s.replaceAll(" ", "");
		int[][] matrix = new int[3][3];

		int i = 0;
		for (int r = 0; r < matrix.length; r++)
			for (int c = 0; c < matrix[r].length; c++)
				matrix[r][c] = Integer.parseInt(s.substring(i, i++ + 1));

		return matrix;
	}
	
	
	/** creates a shallow copy of the given matrix
	 *  @param matrix 2d array to be copied
	 *  @return shallow copy of given matrix */
	public static char[][] shallowCopyOf(char[][] matrix) {
		char[][] newState = new char[3][3];
		for (int r = 0; r < matrix.length; r++)
			for (int c = 0; c < matrix[r].length; c++)
				newState[r][c] = matrix[r][c];
		return newState;
	}

	
	/** return a string of the values within the given matrix. */
	public static String toString(int[][] matrix) {
		String s = "";
		for (int r = 0; r < matrix.length; r++)
			for (int c = 0; c < matrix[r].length; c++)
				s += matrix[r][c];
		return s;
	}

	
	/** creates a string array in order from 
	 *  upper-left to lower-right of the 2d array.
	 *  @param matrix 2d representation of the puzzle
	 */
	public static String createMatrixString(int[][] matrix) {
		String s = "";
		for (int r = 0; r < matrix.length; r++)
			for (int c = 0; c < matrix[r].length; c++)
				s += matrix[r][c];

		return s;
	}


	/** prints the given matrix
	 *  @param matrix 2d array to be printed */
	public static void printBoard(State state) {
		for (int r = 0; r < state.board.length; r++) {
			for (int c = 0; c < state.board[r].length; c++)
				System.out.print(state.board[r][c] +" ");
			System.out.println();
		}
		System.out.println("current ply is \'"+ ((state.isPlyX) ? 'X' : 'O') +"\', "
				+ "at position ("+ state.currPlyPos.row +", "+ state.currPlyPos.col +")");
		System.out.println("Total Heuristic = "+ state.heuristic +"\n");
//		System.out.println("in position ("+ state.currPlyPos.row +", "+ state.currPlyPos.col +")");
	}
	
	
	/** Validates if the given row or column index is within the 3x3 array range 
	 *  @param i row or column index within the matrix
	 *  @return true if index is between [0, 3) */
	public static boolean validRowOrCol(int i) {
		return i >= 0 && i < 3;
	}
}
