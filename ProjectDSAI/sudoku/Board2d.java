import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Board2d
{
	private int[][] sudoku = new int[9][9];
	private int count = 0;
	static final int SUDOKU_LENGTH = 9;
	static final int QUAD_LENGTH = 3;

	/**
	 * The constructor to instantiate a 2d sudoku.
	 * The constructor also checks whether the sudoku is not conflicting. 
	 * Exits the program is the sudoku is conflicting. Reads a sudoku from a file
	 * to load into the field.
	 */
	public Board2d()
	{
		int[][] sudoku = readSudoku();
		if(checkNotConflicting(sudoku))
		{
			this.sudoku = sudoku;
		}
		else
		{
			System.out.printf("The entered sudoku had conflicting cells, please try again.\n");
			System.exit(1);
		}
	} 
	
	/**
	 * The constructor to instantiate a 2d sudoku.
	 * The constructor also checks whether the sudoku is not conflicting. 
	 * Exits the program is the sudoku is conflicting.
	 * 
	 * @param sudoku The sudoku to work with.
	 */
	public Board2d(int[][] sudoku)
	{
		if(checkNotConflicting(sudoku))
		{
			this.sudoku = sudoku;
		}
		else
		{
			System.out.printf("The entered sudoku had conflicting cells, please try again.\n");
			System.exit(1);
		}
	}                                     

	/**
	 * This method reads the sudoku from a file.
	 * This method reads just 81 characters on a single line. So that behind the sudoku,
	 * it is possible to put some comments about the sudoku being read. 
	 * 
	 * @return The read sudoku.
	 */
	public int[][] readSudoku(){
		int[][] sudoku = new int[9][9];
		
		try{
			FileInputStream fstream = new FileInputStream("sudoku.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			for(int i = 0; i < SUDOKU_LENGTH; i++)
			{
				for(int j = 0; j < SUDOKU_LENGTH; j++)
				{
					sudoku[i][j] = br.read() - 48;
				}
			}
			in.close();
		}catch(Exception e){
			System.out.printf("Error: " + e.getMessage());
		}
		return sudoku;
	}

	/**
	 * Returns whether a certain value can be filled in a cell.
	 * This method checks whether it is possible to fill in a cell with 
	 * a certain value.
	 * 
	 * @param i The row number.
	 * @param j The column number.
	 * @param possibleNum The value to be checked.
	 * @param sudoku The 2d sudoku used for checking.
	 * @return <code>True</code> if the poss number can be filled in, 
	 * 		return <code>false</code> otherwise.
	 */
	public boolean possibleNumbers(int i, int j, int possibleNum, int[][] sudoku)
	{
		boolean possible = true;
		
		for(int k = 0; k < SUDOKU_LENGTH; k++)
		{
			if(possibleNum == sudoku[i][k])
			{
				possible = false;
			}
		}
		
		for(int k = 0; k < SUDOKU_LENGTH; k++)
		{
			if(possibleNum == sudoku[k][j])
			{
				possible = false;
			}
		}
		
		int rijKwadrant = (i / 3) * 3;
		int kolomKwadrant = (j / 3) * 3;
		 
		for(int m = 0; m < QUAD_LENGTH; m++)
		{
			for(int k = 0; k < QUAD_LENGTH; k++)
			{
				if(possibleNum == sudoku[rijKwadrant + m][kolomKwadrant + k]) 
				{
					possible = false;
				}
			}
		}
		return possible;
	}
	
	/**
	 * Returns whether the sudoku is completely filled.
	 * This method checks for every cell, whether it contains a zero, 
	 * if so return <code>false</code>, otherwise return <code>true</code>
	 * 
	 * @return Return <code>false</code> if a zero is found,
	 * 		 otherwise return <code>true</code>
	 */
	public boolean sudVol()
	{
		boolean solved = true;
		
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				if(getSudoku()[i][j] == 0)
				{
					solved = false;
				}
			}
		}
		return solved;
	}

	/**
	 * Returns the percentage solved as an int.
	 * Determines for what percentage the sudoku is filled in.
	 * And returns it as an integer.
	 * 
	 * @param sudoku The sudoku to be checked.
	 * @return The percentage as an Integer.
	 */
	public int percentageSolved2d(int[][] sudoku)
	{
		int count = 0;
		
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				if(sudoku[i][j] != 0)
				{
					count++;
				}
			}
		}
		return (int) (100 * count / 81.0);
	}
	
	/**
	 * Returns the updated sudoku.
	 * This method calls for the tactics to be performed on the sudoku.
	 * This is done by transforming the 2d sudoku back to the 3d one,
	 * so that the tactics can be performed, after which, it needs to be converted
	 * back to a 2d sudoku.
	 * 
	 * @param sudoku The 2d sudoku to be updated.
	 * @return The updated 2d sudoku.
	 */
	public int[][] tactics(int[][] sudoku)
	{
		Tactics tactics = new Tactics();
		Board3d board = new Board3d(sudoku);
		board = tactics.useAllTactics(board);
		sudoku = board.updateTo2d();
		
		
		return sudoku;
	}

	/**
	 * Returns whether the sudoku is completely filled.
	 * This method checks for every cell, whether it contains a zero, 
	 * if so return <code>false</code>, otherwise return <code>true</code>
	 * 
	 * @param sudoku The 2d sudoku, which needs to be checked.
	 * @return Return <code>false</code> if a zero is found,
	 * 		 otherwise return <code>true</code>
	 */
	public boolean sudVol(int[][] sudoku)
	{
		boolean vol = true;
		
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				if(sudoku[i][j] == 0)
					vol = false;
			}
		}
		return vol;
	}
	
	/**
	 * Returns the best cell to try to fill using backtracking.
	 * This method determines the best cell to fill, by taking the cell
	 * with the least amount of possibilities for that cell
	 * 
	 * @param sudoku The sudoku of which the best cell needs to be found
	 * @return The row and column index of the best cell.
	 */
	public int[] bestCell(int[][] sudoku)
	{
		int minimum = 100;
		int coord[] = new int[2];
		
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				int pos = 0;
				if(sudoku[i][j] == 0)
				{
					for(int k = 0; k < SUDOKU_LENGTH; k++)
					{
						if(possibleNumbers(i, j, k, sudoku))
						{
							pos++;
						}	
					}
				}
				
				if(pos < minimum && pos > 1)
				{
					minimum = pos;
					coord[0] = i;
					coord[1] = j;
				}
			}
		}
		return coord;
	}

	/**
	 * Returns true if the sudoku is solved.
	 * This method uses backtracking to solve the sudoku."
	 * As long as the sudoku isn't solved it calls itself again.
	 * 
	 * @return Return <code>true</code> if the sudoku is solved, 
	 * 	   otherwise return <code>false</code>.
	 */
	public boolean solveBackTrack(int i, int j, int[][] sudoku)
	{
		if(!checkNotConflicting(sudoku))
		{
			return false;
		}
		
		if(sudVol(sudoku) && checkNotConflicting(sudoku))
		{
			this.sudoku = sudoku;
			return true;
		}
		sudoku  = tactics(sudoku);
		
		if (sudoku[i][j] != 0)
		{
			int coords2[] = bestCell(sudoku);
			return solveBackTrack(coords2[0], coords2[1], sudoku);
		}
		
		for( int k = 1; k <= SUDOKU_LENGTH; k++)
		{
			if(possibleNumbers(i, j, k, sudoku))
			{
				sudoku[i][j] = k;
				///System.out.println(toString());
				count++;
				System.out.println();
				System.out.println(percentageSolved2d(sudoku) + "%, \t" + k + 
						" filled in in cell [" + i + "," + j + "]\t tactic used: Guessed");
				System.out.println();
				
				//check which cell has the least values
				int coords[] = bestCell(sudoku);
				
				//try to solve with best cell as next to fill in
				if (solveBackTrack(coords[0], coords[1], sudoku))
				{	
					return true;
				}
			}
		}
		
		sudoku[i][j] = 0;
		
		return false;
	}

	
	/**
	 * Returns string ready for printing. 
	 * This method creates a string of the sudoku, 
	 * and places zeros at cells of which the value 
	 * is unknown.
	 * 
	 * @return The 2d sudoku ready for printing.
	 */
	public String toString2()
	{
		String output= "";
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				output = output + sudoku[i][j];
				
				if((j + 1) % 3 == 0)
					output = output + " ";
			}
			output = output + "\n";
			if( (i + 1) % 3 == 0)
				output = output + "\n";
		}
		return output;
	}

	/**
	 * Returns String ready for printing.
	 * Creates a string of the 2d sudoku, which can be printed, 
	 * with some extra lines for better readability.
	 * 
	 * @return The 2d sudoku, which can be printed.
	 */ 
	public String toString(){
		
		String output = "";	
			
			for(int i = 0; i < SUDOKU_LENGTH; i++){
				
				for(int j = 0; j < SUDOKU_LENGTH; j++)
				{					
					output = String.format(output + getSudoku()[i][j] + "|");
					if (j == 8)
						output = output + "\n";
				}
				output = String.format(output + "------------------\n");
			}
			
			return output;
		}

	/**
	 * Return amount non-zero entries cell.
	 * Count how many nonzero-values a single cell has
	 * i.e. how many possibilities has this cell at this moment
	 * 
	 * @param array The cell of which the nonzero entries need to be counted
	 * @return The amount of nonzero entries.
	 */
	public int countAmountNotZero(int[] array)
	{
		//init count
		int count = 0;

		//iterate trough cell
		for(int i = 0; i < array.length; i++)
		{
			//if nonzero value found, increment count
			if(array[i] != 0)
			{
				count++;
			}
		}
		return count;
	}

	/**
	 * Checks if array contains only ones.
	 * This method checks if a 1x9 array has all ones.
	 * 
	 * @param array The array to be checked.
	 * @return Returns <code>true</code> if array contains only ones
	 * 		   returns <code>false</code> otherwise.
	 */
	public boolean checkAllOne(int[] array)
	{
		boolean allOne = true;
		//Iterate through array
		for(int i = 0; i < array.length; i++)
		{
			//set returnvalue to false if not 1 found
			if(array[i] != 1)
			{
				allOne = false;
			}
		}
		return allOne;
	}

	/**
	 * Return whether an array contains no double values.
	 * This method returns, whether an array contains no multiple entries of the same value.
	 * 
	 * @param array The array to be checked.
	 * @return Returns <code>true</code>, if no doubles are found, 
	 * 		   <code>false</code> otherwise.
	 */
	public boolean checkNoDoubles(int[] array)
	{
		for(int i = 0; i < array.length; i++)
		{
			//set returnvalue to false if not 1 found
			if(array[i] > 1)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether there are no conflicting values.
	 * This method checks for each row, column and quadrant, whether there are
	 * no multiple occurences of the same value.
	 * 
	 * @param sudoku The 2d sudoku.
	 * @return Returns <code>true</code>, if no conflicting values are found, 
	 * 		   <code>false</code> otherwise.
	 */
	public boolean checkNotConflicting(int[][] sudoku)
	{
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			if(	!checkRowNoDoubles(sudoku, i))
			{
				return false;
			}
			
			if(	!checkColumnNoDoubles(sudoku, i))
			{
				return false;
			}
			
			if(	!checkQuadNoDoubles(sudoku, i))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns if row contains no double values.
	 * This method returns a boolean, which says whether a row contains
	 * same value multiple times.
	 * 
	 * @param sudoku The 2d sudoku.
	 * @param i The row number.
	 * @return Returns <code>true</code>, if no doubles are found, 
	 * 		   <code>false</code> otherwise.
	 */
	public boolean checkRowNoDoubles(int[][] sudoku, int i)
	{
		int countArray[] = new int[9];
		for(int j = 0; j < SUDOKU_LENGTH; j++)
		{
			if(sudoku[i][j] != 0)
			{
				countArray[sudoku[i][j] - 1]++;
			}
		}
		return checkNoDoubles(countArray);
	}

	/**
	 * Returns if column contains no double values.
	 * This method returns a boolean, which says whether a column contains
	 * same value multiple times.
	 * 
	 * @param sudoku The 2d sudoku.
	 * @param j The column number.
	 * @return Returns <code>true</code>, if no doubles are found, 
	 * 		   <code>false</code> otherwise.
	 */
	public boolean checkColumnNoDoubles(int[][] sudoku, int j)
	{
		int countArray[] = new int[9];
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			if(sudoku[i][j] != 0)
			{
				countArray[sudoku[i][j] - 1]++;
			}
		}
		return checkNoDoubles(countArray);

	
	}

	/**
	 * Returns if quadrant contains no double values.
	 * This method returns a boolean, which says whether a quadrant contains
	 * same value multiple times.
	 * 
	 * @param sudoku The 2d sudoku.
	 * @param q The quadrant number.
	 * @return Returns <code>true</code>, if no doubles are found, 
	 * 		   <code>false</code> otherwise.
	 */
	public boolean checkQuadNoDoubles(int[][] sudoku, int q)
	{
		int countArray[] = new int[SUDOKU_LENGTH];

		//iterate through quadrants
		for(int i = q2c(q)[0]; i < q2c(q)[0] + QUAD_LENGTH; i++)
		{
			for(int j = q2c(q)[1]; j < q2c(q)[1] + QUAD_LENGTH; j++)
			{
				//if cell is filled in
				if(sudoku[i][j] != 0)
				{
					//increment countArray on place(value of cell -1)
					countArray[sudoku[i][j] - 1]++;
				}
			}
		}
		//return true if countArray has no value 2 or higher in it
		return checkNoDoubles(countArray);
	}
		
	/**
	 * Converts quadrant to coordinate.
	 * Returns a coord as a 2x1 array,
	 * this array is the the coordinate 
	 * of the upper left corner of quadrant q.
	 *
	 * @param q The quadrant number.
	 * @return A 2x1 array of the coordinates of the upperleft corner.
	 */
	public int[] q2c(int q)
	{
		//q is a number from 0 to 8
		int[] startCoord = {(q / 3) * 3, (q % 3) * 3};
		return startCoord;
	}

	/**
	 * Set the sudoku with a new sudoku.
	 * Replace the old sudoku, with another one.
	 * 
	 * @param sudoku The new sudoku.
	 */ 
	public void setSudoku(int[][] sudoku) {
		this.sudoku = sudoku;
	}

	/**
	 * Returns current sudoku.
	 * This method returns the current sudoku in use.
	 * 
	 * @return The current sudoku.
	 */ 
	public int[][] getSudoku() {
		return sudoku;
	}

	/**
	 * Set the counter.
	 * Set the counter field to a specific value.
	 * 
	 * @param count The new value for the counter.
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Get the counter.
	 * Return the counter field.
	 * 
	 * @return The counter value.
	 */
	public int getCount() {
		return count;
	}	
}
