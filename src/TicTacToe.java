import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class TicTacToe
{
	/** the broad game to begin solving the puzzle */
	char[][] board = new char[3][3];

	/** queue that keeps track of all states (frontier) */
	Queue<State> qTrack = new ArrayDeque<State>();

	/** shall contain the path from initial ___ to goal ___. */
	LinkedList<State> path = new LinkedList<>();
	

	/** initializes the game board with an X in the given position */
	public TicTacToe(Pos pos) {
		for (int r = 0; r < 3; r++)
			for (int c = 0; c < 3; c++)
				board[r][c] = '_';

		State initState = new State(board, pos);
		this.qTrack.add(initState);
	}
	
	
	
	public void startEvaluating() {
		Util.printBoard(qTrack.peek());
		while (!qTrack.isEmpty()) {
			State currState = qTrack.peek();
			if (currState.isSolved('X') || currState.isSolved('O')) {
				return;
			}
			
			
			currState.evaluateChildStates();
			
			for (State puzzleState : currState.childStates) {
				qTrack.add(puzzleState);
			}
			
			// dequeue the first state for it to not be evaluated again
			qTrack.poll();
		}
	}
	
	
	public static void main(String[] args) {
		TicTacToe game = new TicTacToe(new Pos(1, 1));
		
		System.out.println("Let User's ply be 'X' and opponent's ply to be 'O'");
		System.out.println("\nThis is a console printed observation of each game state and their heuristic value.");
		System.out.println("*Note: the algorithm only considers states that lead to the victory");
		System.out.println("       of the user, but we're printing every state leading to the user's");
		System.out.println("       victory board for the purpose of observing each heuristic value.");

		System.out.println();
		game.startEvaluating();
	}
}
