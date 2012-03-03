public class SudokuTest {


	public static void main(String[] args) {
		
		Sudoku test = new Sudoku();
		
		Sudoku.losOp(0,0,test.beginState());
		System.out.print(test);
		
	}

}
