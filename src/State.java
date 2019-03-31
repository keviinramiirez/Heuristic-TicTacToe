import java.util.LinkedList;

public class State 
{
	/** representation of the TicTacToa board */
	char[][] board;
	
	/** list of all its next states */
	LinkedList<State> childStates = new LinkedList<>();
	
	/** accumulated heuristic value */
	int heuristic;
	Pos currPlyPos;
	boolean isPlyX;
	char opponentPly;
	
	
	
	
	public State(char[][] board,  Pos plyPos) {
		this(board, plyPos, 'X', 0);
	}
	public State(char[][] board, Pos currPlyPos, char ply, int accHeuristic) {
		this.board = board;
		this.currPlyPos = currPlyPos;
		this.board[currPlyPos.row][currPlyPos.col] = ply;
		this.heuristic = accHeuristic;
		this.isPlyX = (ply == 'X');
		this.opponentPly = isPlyX ? 'O' : 'X';
		this.evaluateHeuristic();
	}
	
	/** Checks whether the board configuration is a win for the given player. */
	public boolean isSolved(char ply) {
		return ((board[0][0] == ply && board[0][1] == ply && board[0][2] == ply) // row 0
				|| (board[1][0] == ply && board[1][1] == ply && board[1][2] == ply) // row 1
				|| (board[2][0] == ply && board[2][1] == ply && board[2][2] == ply) // row 2
				|| (board[0][0] == ply && board[1][0] == ply && board[2][0] == ply) // column 0
				|| (board[0][1] == ply && board[1][1] == ply && board[2][1] == ply) // column 1
				|| (board[0][2] == ply && board[1][2] == ply && board[2][2] == ply) // column 2
				|| (board[0][0] == ply && board[1][1] == ply && board[2][2] == ply) // diagonal
				|| (board[2][0] == ply && board[1][1] == ply && board[0][2] == ply)); // rev diag
	}
	
	public void evaluateHeuristic() {
		int rCurr = currPlyPos.row;
		int cCurr = currPlyPos.col;
		char currPly = board[rCurr][cCurr];
		
		
		// vertical sum of player's and opponent's ply within board
		for (int r = 0; r < board.length; r++) {
			if (board[r][cCurr] == currPly)
				heuristic++;
			else if (board[r][cCurr] == opponentPly)
				heuristic--;
		}
		
		// horizontal sum of player's and opponent's ply within board
		for (int c = 0; c < board[rCurr].length; c++) {
			if (board[rCurr][c] == currPly)
				heuristic++;
			else if (board[rCurr][c] == opponentPly)
				heuristic--;
		}
		
		// determines if inspected ply is in the middle of the board
		boolean isPlyMiddle = (rCurr == 1 && cCurr == 1);
		
		// diagonal left to right, sum of player's and opponent's ply
		if (isPlyMiddle || rCurr == cCurr) {
			int r = 0, c = 0;
			for (; c < board[rCurr].length; r++, c++)
				if (board[r][c] == currPly)
					heuristic++;
				else if (board[r][c] == opponentPly)
					heuristic--;
					
		}
		
		// diagonal right to left, if inspected ply is in the middle
		if (isPlyMiddle || (rCurr%2 == 0 && cCurr%2 == 0) && rCurr != cCurr) {
			int r = 0, c = board[rCurr].length-1;
			for (; c >= 0 || r < board.length; r++, c--)
				if (board[r][c] == currPly)
					heuristic++;
				else if (board[r][c] == opponentPly)
					heuristic--;
		}
		
//		if (rCurr > 0 && rCurr < board.length 
//				&& cCurr > 0 && cCurr < board[rCurr].length) {
//			for (int r = 0; r < board.length; r++)
//				for (int c = 0; c < board[r].length; c++)
//					if ()
//		}
	}
	
	public void evaluateChildStates() {
		int rCurr = currPlyPos.row;
		int cCurr = currPlyPos.col;
		
		// shall keep track of previous heuristic value along the process
		int prevHeur = (this.isPlyX) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
		
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				if (board[r][c] != board[rCurr][cCurr]) {
					char[][] newBoard = Util.shallowCopyOf(board);
					
					State currState = new State(newBoard, new Pos(r, c), 
							(isPlyX) ? 'O' : 'X', this.heuristic);

					Util.printBoard(currState);
					if (compareCurrAndPrevHeuristic(currState, prevHeur))
							childStates.removeAll(childStates);
					
					if (compareCurrAndPrevHeuristic(currState, prevHeur)
							|| currState.heuristic == prevHeur) {
						prevHeur = currState.heuristic;
						childStates.add(currState);
					}
				}
			}
		}
	}
	
	
	/** true if given state's heuristic value is less than or greater than 
	 *  (depending on whether the state's ply is 'X' or 'O') the given heuristic 
	 *  value. False otherwise.
	 */
	private boolean compareCurrAndPrevHeuristic(State state, int heuristic) {
		return state.isPlyX
				? state.heuristic > heuristic
				: state.heuristic < heuristic;
	}
}
