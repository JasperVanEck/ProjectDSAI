public abstract class SolveSudoku
{
	private Sudoku board;

	public SolveSudoku()
	{
		board = new Sudoku();
	}

	public abstract double percentageSolved();
	
	public abstract void updateSingle();
	
	public abstract void updateHiddenSingle();
	
}