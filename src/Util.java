public class Util 
{
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
	}
}
