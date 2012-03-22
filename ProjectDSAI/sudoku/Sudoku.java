import java.util.Scanner;

public class Sudoku
{
	/**
	 * Initiates the sudoku solving algorithm.
	 * This method, next to initiating the sudoku solving algorithm, allows
	 * the user to specify wether, he/she wants to use only the standard
	 * tactics to solve a sudoku, and also gives the option to use backtracking
	 * in combination with the tactics. This would allow every sudoku to be solved.
	 * 
	 * @param  args The starting arguments are not used.
	 */
	public static void main(String[] args)
	{
		Board2d board2d = new Board2d();
		Board3d board = new Board3d(board2d.getSudoku());
		Tactics tactics = new Tactics();
		board = tactics.useAllTactics(board);
		if(board.checkSolved())
		{
			System.out.println("Sudoku is opgelost!");
			System.out.println(board.toString2());
		} else
		{
			System.out.println("\nHet is niet gelukt om sudoku op te lossen,\nverder proberen dmv backtracken? (j/n)");
			Scanner scanner = new Scanner(System.in);
			String answer = scanner.next();
			
			if(answer.equals("j"))
			{
				board2d.setSudoku(board.updateTo2d());
				
				if(board2d.solveBackTrack(0, 0, board2d.getSudoku()))
				{
					System.out.println("\nSudoku is opgelost, er is " + 
							board2d.getCount() + "x gebacktracked.\nDe oplossing:");
					System.out.print(board2d.toString2());				
				}
			}
		}
	}
}