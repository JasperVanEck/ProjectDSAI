import java.util.Scanner;

public class Sudoku
{
	public static void main(String[] args)
	{
		Board3d board = new Board3d();
		Tactics tactics = new Tactics();
		board = tactics.useAllTactics(board);
		if(board.checkSolved())
		{
			System.out.println("Sudoku is opgelost!");
			System.out.println(board);
		} else
		{
			System.out.println("\nHet is niet gelukt om sudoku op te lossen,\nverder proberen dmv backtracken? (j/n)");
			Scanner scanner = new Scanner(System.in);
			String answer = scanner.next();
			
			if(answer.equals("j"))
			{
				Board2d board2d = new Board2d(board.updateTo2d());
				
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