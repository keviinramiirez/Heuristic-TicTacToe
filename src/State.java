import java.util.LinkedList;

/** Represents a particular game state within the Tic-Tac-Toe game tree */
public class State 
{
	/** representation of the TicTacToa board */
	int[][] board;
	
	/** list of all its next states */
	LinkedList<State> childStates = new LinkedList<>();
	
	/** accumulated heuristic value */
	int heuristic;
	
	/** position of the current ply that is being considered */
	Pos currPlyPos;
	
	/** is the considered ply in the current state 'X'? */
	boolean isPlyX;
	
	/** Initializes this game state with the given board and the 'X' value ply 
	 *  in the given position with default heuristic value of zero.
	 */
	public State(int[][] board,  Pos plyPos) {
		this(board, plyPos, TicTacToe.X, 0);
	}
	/** Initializes this game state with the given board and the given ply value
	 *  in the given position with the accumulated heuristic value.
	 */
	public State(int[][] board, Pos currPlyPos, int ply, int accHeuristic) {
		this.board = board;
		this.currPlyPos = currPlyPos;
		this.board[currPlyPos.row][currPlyPos.col] = ply;
		this.heuristic = accHeuristic;
		this.isPlyX = (ply == TicTacToe.X);
		this.determineHeuristic();
	}
	
	/** Checks whether the board configuration is a win for the given player. */
	public boolean isSolved(int ply) {
		return ((board[0][0] == ply && board[0][1] == ply && board[0][2] == ply) // row 0
				|| (board[1][0] == ply && board[1][1] == ply && board[1][2] == ply) // row 1
				|| (board[2][0] == ply && board[2][1] == ply && board[2][2] == ply) // row 2
				|| (board[0][0] == ply && board[1][0] == ply && board[2][0] == ply) // column 0
				|| (board[0][1] == ply && board[1][1] == ply && board[2][1] == ply) // column 1
				|| (board[0][2] == ply && board[1][2] == ply && board[2][2] == ply) // column 2
				|| (board[0][0] == ply && board[1][1] == ply && board[2][2] == ply) // diagonal
				|| (board[2][0] == ply && board[1][1] == ply && board[0][2] == ply)); // rev diag
	}
	
	
	/** Evaluates the heuristic value of this board.   */
	public void determineHeuristic() {
		int rCurr = currPlyPos.row;
		int cCurr = currPlyPos.col;
		
		// vertical sum of player's and opponent's ply within board
		for (int r = 0; r < board.length; r++) 
			heuristic += board[r][cCurr];
		
		// horizontal sum of player's and opponent's ply within board
		for (int c = 0; c < board[rCurr].length; c++)
			heuristic += board[rCurr][c];
		
		// determines if inspected ply is in the middle of the board
		boolean isPlyMiddle = (rCurr == 1 && cCurr == 1);
		
		// diagonal left to right, sum of player's and opponent's ply
		if (isPlyMiddle || rCurr == cCurr) {
			int r = 0, c = 0;
			for (; c < board[rCurr].length; r++, c++)
				heuristic += board[r][c];
		}
		
		// diagonal right to left, if inspected ply is in the middle
		if (isPlyMiddle || (rCurr%2 == 0 && cCurr%2 == 0) && rCurr != cCurr) {
			int r = 0, c = board[rCurr].length-1;
			for (; c >= 0 || r < board.length; r++, c--)
				heuristic += board[r][c];
		}
	}
	
	
	/** evaluates each child of this game state. */
	public void evaluateChildStates() {
		int rCurr = currPlyPos.row;
		int cCurr = currPlyPos.col;
		
		// shall keep track of previous heuristic value along the process
		int prevHeur = (this.isPlyX) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
		
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				if (board[r][c] != board[rCurr][cCurr]) {
					int[][] newBoard = Util.shallowCopyOf(board);
					
					State currState = new State(newBoard, new Pos(r, c), 
							((isPlyX) ? TicTacToe.O : TicTacToe.X), this.heuristic);

					Util.printGameState(currState);
					
					// ensures to only store the child game states that 
					// have the maximum or minimum heuristic value.
					// (minimum if this ply is 'O')
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
