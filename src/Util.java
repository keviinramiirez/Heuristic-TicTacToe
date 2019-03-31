public class Util 
{
	/** creates a shallow copy of the given matrix
	 *  @param board 2d array to be copied
	 *  @return shallow copy of given matrix */
	public static int[][] shallowCopyOf(int[][] board) {
		int[][] newBoard = new int[3][3];
		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[r].length; c++)
				newBoard[r][c] = board[r][c];
		return newBoard;
	}

	
	/** return a string of the values within the given matrix. */
	public static String toString(int[][] matrix) {
		String s = "";
		for (int r = 0; r < matrix.length; r++)
			for (int c = 0; c < matrix[r].length; c++)
				s += matrix[r][c];
		return s;
	}


	/** prints the given game state
	 *  @param state game state within the Tic-Tac-Toa game tree */
	public static void printGameState(State state) {
		for (int r = 0; r < state.board.length; r++) {
			for (int c = 0; c < state.board[r].length; c++) {
				char ply = '_';
				if (state.board[r][c] != TicTacToe.EMP)
					ply = state.board[r][c] == TicTacToe.X ? 'X' : 'O';
				System.out.print(ply +" ");
			}
			System.out.println();
		}
		
		System.out.println("current ply is \'"+ ((state.isPlyX) ? 'X' : 'O') +"\', "
				+ "at position ("+ state.currPlyPos.row +", "+ state.currPlyPos.col +")");
		System.out.println("Total Heuristic = "+ state.heuristic +"\n");
	}
}
