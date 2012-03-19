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
		//readSudoku();
	}                                     

	public int[][] readSudoku(){
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
		return getSudoku();
	}

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
	
	public int[][] tactics(int[][] sudoku)
	{
		Tactics tactics = new Tactics();
		Board3d board = new Board3d(sudoku);
		board = tactics.useAllTactics(board);
		sudoku = board.updateTo2d();
		
		
		return sudoku;
	}

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
	
	public int[] bestCell(int[][] sudoku)
	{
		int minimum = 100;
		int coord[] = new int[3];
		
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
					coord[2] = minimum;
				}
			}
		}
		return coord;
	}
	
	
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

	
	/* this method prints sudoku, zeros at cells unknown
	 * else print value
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

	/*
	 * count how many nonzero-values a single cell has
	 * i.e. how many possibilities has this cell at this moment
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

	/*
	 * This method checks if a 1x9 array has all ones
	 */
	public boolean checkAllOne(int array[])
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
	
	public boolean checkNoDoubles(int array[])
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

	/* 
	 * *************************************************************************
	 * *************************************************************************
	 * This method checks if the sudoku is filled in completely and correct
	 * *************************************************************************
	 * *************************************************************************
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
	
	public boolean checkQuadNoDoubles(int [][] sudoku,int q)
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
		
	/*Method quadrant to coordinate
	 *returns a coord as a 2x1 array
	 *this array is the the coordinate of the upper left corner of quadrant q
	 *
	 * 012345678
	 *0 
	 *1 0  1  2 
	 *2 
	 *3
	 *4 3  4  5
	 *5
	 *6
	 *7 6  7  8
	 *8
	 */
	public int[] q2c(int q)
	{
		//q is a number from 0 to 8
		int[] startCoord = {(q / 3) * 3, (q % 3) * 3};
		return startCoord;
	}

	public void setSudoku(int[][] sudoku) {
		this.sudoku = sudoku;
	}

	public int[][] getSudoku() {
		return sudoku;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}	
}
