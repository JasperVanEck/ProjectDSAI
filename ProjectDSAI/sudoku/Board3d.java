import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Board3d
{
	private int [][][] possSudoku = new int[9][9][9];
	static final int SUDOKU_LENGTH = 9;
	static final int QUAD_LENGTH = 3;
	static final int LENGTH_PRINT_LINE = 39;
	
	/* default constructor
	 * with no arguments, read the sudoku from file
	 * create board object
	 */
	public Board3d()
	{
		makeNew();
		readSudoku();
	}
	
	/*
	 * Sudoku with 2d-sudoku as input, creates board object
	 * with a given state
	 */
	public Board3d(int[][] sudoku)
	{
		makeNew();
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			for(int j = 0; j < SUDOKU_LENGTH;j++)
			{
				for(int k = 0; k < SUDOKU_LENGTH;k++)
				{
					if(sudoku[i][j] !=0)
					{
						setValue(i,j, sudoku[i][j]);
					}
				}	
			}
		}	
	}
	
	/*
	 * This method fills in every cell of the board with 1-9 on cells 0-8 
	 */
	public void makeNew()
	{
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			for(int j = 0; j < SUDOKU_LENGTH;j++)
			{
				for(int k = 0; k < SUDOKU_LENGTH;k++)
				{
					possSudoku[i][j][k] = k + 1;
				}	
			}
		}
	}
		
	/*
	 * This method read the sudoku from file
	 */
	public void readSudoku()
	{
		try
		{
			FileInputStream fstream = new FileInputStream("sudoku.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int b;
			for(int i = 0; i < SUDOKU_LENGTH; i++)
			{
				for(int j = 0; j < SUDOKU_LENGTH; j++)
				{
					b = br.read() - 48;
					if(b != 0)
					{
						setValue(i, j, b);
					}
				}
			}
			in.close();
			//return sudoku;
		}catch(Exception e)
		{
			System.out.printf("Error: " + e.getMessage());
		}
	}
	
	public int[][][] getSudoku()
	{
		return possSudoku;
	}
	
	
	/*
	 * This method deletes all the values of a cell, except for the given
	 * parameter value
	 */
	public void setValue(int i, int j, int value)
	{
		for(int k = 0; k < SUDOKU_LENGTH; k++)
		{
			//set al remaining values other then 'value' to zero
			if(possSudoku[i][j][k] != value )
			{
				possSudoku[i][j][k] = 0;
			}
		}
	}
	
	/*
	 * count how many nonzero-values cell possSudoku(i,j) has
	 * i.e. how many possibilities has this cell at this moment
	 */
	public int countAmountNotZero(int i, int j)
	{
		//init count
		int count = 0;

		//iterate trough cell
		for(int k = 0; k < SUDOKU_LENGTH; k++)
		{
			//if nonzero value found, increment count
			if(possSudoku[i][j][k] != 0)
			{
				count++;
			}
		}
		return count;
	}
	
	/*
	 * This method counts how many non-zero values a single cell has
	 * i.e. how many possiblities has this cell at this moment
	 */
	public int countAmountNotZero(int[] cell)
	{
		//init count
		int count = 0;

		//iterate trough cell
		for(int k = 0; k < SUDOKU_LENGTH; k++)
		{
			//if nonzero value found, increment count
			if(cell[k] != 0)
			{
				count++;
			}
		}
		return count;
	}
	
	/*
	 * This method deletes a value from a possSudoku[i][j]
	 */
	public void deleteValue(int i, int j, int value)
	{
		if(possSudoku[i][j][value - 1] == value && countAmountNotZero(i, j) != 1)
		{
			possSudoku[i][j][value - 1] = 0;
		}	
	}
	
	/*
	 * This method checks if a cell is filled in, in other words, is there
	 * only one value remaining in this cell
	 */
	public boolean checkCellFilledIn(int i, int j)
	{
		boolean filled = false;
		
		if(countAmountNotZero(i, j) == 1)
		{
			filled = true;
		}
		return filled;
	}
	
	/*
	 * This method checks if a cell is filled in, in other words, is there
	 * only one value remaining in this cell
	 */
	public boolean checkCellFilledIn(int cell[])
	{
		boolean filled = false;
		
		if(countAmountNotZero(cell) == 1)
		{
			filled = true;
		}
		return filled;
	}
	
	/*
	 * Inserts a certain value into a cell.
	 */
	public int[] setValue(int[] cell, int value)
	{
		
		//iterate through cell
		if(value != 0)
		{
			for(int k = 0; k < SUDOKU_LENGTH; k++)
			{
				//set al remaining values other then 'value' to zero
				if(cell[k] != value )
				{
					cell[k] = 0;
				}
			}
		}
		return cell;
	}
	
	/*
	 * This method deletes a certain value from a certain cell
	 */
	public int[] deleteValue(int cell[], int value)
	{
		if(cell[value - 1] == value && countAmountNotZero(cell) != 1)
		{
			cell[value - 1] = 0;
		}	
		return cell;
	}
	
	/*
	 * This method if a cell still has a certain value
	 */
	public boolean checkCellHasValue(int i, int j, int value)
	{
		boolean hasValue = false;
		
		//iterate through cell
		for(int k = 0; k < SUDOKU_LENGTH; k++)
		{
			//if value found in cell, set hasValue to true
			if(possSudoku[i][j][k] == value)
			{
				hasValue = true;
			}
		}
		//return hasValue, if not found, hasValue remained false
		return hasValue;
	}
	
	/*
	 * This method checks if a value has a certain value
	 */
	public boolean checkCellHasValue(int[] cell, int value)
	{
		boolean hasValue = false;
		
		//iterate through cell
		for(int i = 0; i < cell.length; i++)
		{
			//if value found in cell, set hasValue to true
			if(cell[i] == value)
			{
				hasValue = true;
			}
		}
		//return hasValue, if not found, hasValue remained false
		return hasValue;
	}
	
	public int[][] updateTo2d()
	{
		int sudoku[][] = new int[9][9];

		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				if(countAmountNotZero(i, j) == 1)
				{
					sudoku[i][j] = getValue(i, j);
				}
			}
		}
		return sudoku;
	}
	
	
	/*
	 *  This method returns a cell (i,j)
	 */
	public int[] getCell(int i, int j)
	{
		return possSudoku[i][j];
	}
	
	/*
	 * This method sets a Cell
	 */
	public void setCell(int i, int j, int[] cell)
	{
		possSudoku[i][j] = cell;
	}
	
	/*
	 * This method return a whole row;
	 * This is a 9x9 array[j][k]
	 * j = the column number
	 * k = the index of the cell 
	 */
	public int[][] getRow(int i)
	{
		return possSudoku[i];
	}
	
	/*
	 * This methods sets a row;
	 */
	public void setRow(int i, int[][] row)
	{
		possSudoku[i] = row;
	}
	
	/*
	 * This method return a whole column;
	 * This is a 9x9 array[i][k]
	 * i = the column number
	 * k = the index of the cell 
	 */
	public int[][] getColumn(int j)
	{
		int[][] column = new int[9][9];
		
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			column[i] = getCell(i, j);
		}
		
		return column;
	}
	
	/*
	 * This method sets a column 
	 */
	public void setColumn(int j, int[][] column)
	{
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			possSudoku[i][j] = column[i];
		}
	}
	
	/*
	 * This method return a whole quadrant;
	 * This is a 9x9 array[i][k]
	 * i = index of the quadrant like this:	0 1 2
	 * 										3 4 5
	 * 										6 7 8
	 * k = the index of the cell 
	 */
	public int[][] getQuad(int q)
	{
		int[][] quad = new int[9][9];
		int count = 0;
		
		for(int i = quadToRow(q); i < quadToRow(q) + QUAD_LENGTH; i++)
		{
			for(int j = quadToCol(q); j < quadToCol(q) + QUAD_LENGTH; j++)
			{
				quad[count]  = getCell(i, j);
				count++;
			}
		}
		return quad;
	}
	
	/*
	 * This method sets a quad
	 */
	public void setQuad(int q, int[][] quad)
	{
		int count = 0;
		for(int i = quadToRow(q); i < quadToRow(q) + QUAD_LENGTH; i++)
		{
			for(int j = quadToCol(q); j < quadToCol(q) + QUAD_LENGTH; j++)
			{
				possSudoku[i][j] = quad[count];
				count++;
			}
		}
	}
	
	
	/*
	 * This method returns the quadrant the coord (i,j is in
	 * 
	 */
	public int  coordToQuad(int i, int j)
	{
		return (i / 3) * 3 + j / 3;
	}
	
	/*
	 * This method returns the row-index of the upper left corner
	 * of quadrant q
	 */
	public int quadToRow(int q)
	{
		//q is a number from 0 to 8
		return (q / 3) * 3;
	}
	
	/*
	 * This method returns the column-index of the upper left corner
	 * of quadrant q
	 */
	public int quadToCol(int q)
	{
		//q is a number from 0 to 8
		return (q % 3) * 3;
	}
	
	/*
	 * This method returns the value of cell (i,j) of pussSudoku,
	 * if the cell is filled, in other words, if 
	 * the cell has one non-zero value. If there are more then
	 * one value remaining return zero.
	 */
	public int getValue(int i, int j)
	{
		int value = 0;
		//if cell has only one nonzero value
		if(countAmountNotZero(i, j) == 1)
		{
			//iterate through cell, when value found return this value
			for(int k = 0; k < SUDOKU_LENGTH; k++)
			{
				if(getCell(i, j)[k] != 0)
				{
					value = getCell(i, j)[k];
				}
			}				
		}
		return value;
	}
	
	
	
	
	/*
	 *  This method checks if a value only appears in one row or column.
	 *  It returns the row or column number on which this value appears
	 *  If the value doesn't appear in a single row or column or is already
	 *  filled in
	 */
	public int valueOnSingleRowOrColumnOfQuadrant(int q, int value, String rowOrColumn)
	{
		//count how many times the value appears in every row
		int countArray[] = new int[3];
		
		//iterate through quadrant
		for(int i = quadToRow(q); i < quadToRow(q) + QUAD_LENGTH; i++)
		{
			for(int j = quadToCol(q); j < quadToCol(q) + QUAD_LENGTH; j++)
			{
				if(checkCellHasValue(i , j, value) &&
						countAmountNotZero(i, j) == 1)
				{
					return -1;
				}


				if(checkCellHasValue(i,j, value) &&
						countAmountNotZero(i,j) != 1)
				{
					if(rowOrColumn.equals("row"))
					{
						countArray[i-quadToRow(q)]++;
					} else if(rowOrColumn.equals("column"))
					{
						countArray[j-quadToCol(q)]++;
					}
				}
			}
		}
			int additionRowOrColumn = 0;
			
			if(rowOrColumn.equals("row"))
			{
				additionRowOrColumn = quadToRow(q);
			} else if(rowOrColumn.equals("column"))
			{
				additionRowOrColumn = quadToCol(q);
			}
			
			//check if a value appears only in a single row or column
			if(countArray[0] != 0 && countArray[1] == 0 && countArray[2] == 0)
			{
				return 0 + additionRowOrColumn;
			} else if(countArray[0] == 0 && countArray[1] != 0 && countArray[2] == 0)
			{
				return 1 + additionRowOrColumn;
			} else if(countArray[0] == 0 && countArray[1] == 0 && countArray[2] != 0)
			{
				return 2 + additionRowOrColumn;
			} else //not a single value found
			{
				return -1;
			}	
	}
	
	public double percentageSolved()
	{
		int count = 0;
			
		//iterate through 9x9 matrix
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				if(countAmountNotZero(i, j) == 1)
				{
					count++;
				}
				
			}
		}
		//double totalAmount = 9 * 9 * 9 - 9 * 9;
		//return 100* (1 -   (count-81) / totalAmount);
		return 100 * count / 81;
	}
	
	/*
	 * This method checks if the sudoku is solved. Sudoku is solved if on
	 * every row, column and quadrant every value 1-9 appears one time.
	 */
	public boolean checkSolved()
	{
		boolean solved = true;
		//iterate through rows,columns and quadrant
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			//if a row column or quadrant is not solved, return false
			if(!checkArraySolved(getColumn(i)) || !checkArraySolved(getRow(i)) || !checkArraySolved(getQuad(i)))
			{
				solved = false;
			}
		}
		return solved;
	}

	/*
	 * This method checks if 9x9 array-representation of a row column of quadrant
	 * is filled in, and ever value appears exactly once
	 */
	public boolean checkArraySolved(int[][] array)
	{
		//init count array
		int countArray[] = new int[SUDOKU_LENGTH];

		//iterate through array
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			//if cell is filled in
			if(countAmountNotZero(array[i]) == 1)
			{
				//increment countArray on place(value of cell -1)
				countArray[getValue(array[i]) - 1]++;
			}else
			{
				return false;
			}
		}

		//return true if countArray has only ones
		return checkAllOne(countArray);
	}
		
	/*
	 * This method checks if a array contains only ones
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

	public int getValue(int[] cell)
	{
		int value = 0;
		
		if(countAmountNotZero(cell) == 1)
		{
			//iterate through cell, when value found return this value
			for(int k = 0; k < SUDOKU_LENGTH; k++)
			{
				if(cell[k] != 0)
				{
					value = cell[k];
				}
			}
		}
		return value;
	}
	
	/*
	 ********************************************
	 * PRINT METHODS
	 ********************************************
	 */
	
	
	/*
	 * This method only prints the cells which are filled in,
	 * else print a zero
	 */
	public String toString2()
	{
		String output= "";
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				if(getValue(i, j) != 0)
					output = output + getValue(i, j);
				else 
					output = String.format(output + "0");
				if((j + 1) % 3 == 0)
					output = output + " ";
			}
			output = output + "\n";
			if( (i + 1) % 3 == 0)
				output = output + "\n";
		}
		return output;
	}

	/*
	 * this method prints the sudoku, a 9 x 9 sudoku
	 * at each cell, all the possible values, 
	 * if one value remaining,
	 * print only this value in middle of cell.
	 */
	public String printRowColumnOrQuad(int[][] matrix )
	{
		String output = "";
			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				output = output + "  "; 
				if(j % 3 == 0 && j != 0 && j != SUDOKU_LENGTH)
				{
					output = output +"  "; 
				}
				
				for(int k = 0; k < 3 ; k++)
				{
					output = String.format(output + matrix[j][k]);
				}
			}
			output = output + "  \n";

			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				output = output + "  "; 
				if(j % 3 == 0 && j != 0 && j != SUDOKU_LENGTH)
				{
					output = output + "  "; 
				}
				
				for(int k = 3; k < 6 ;k++)
				{
					output = String.format(output+ matrix[j][k]);
				}
			}
			output = output + "  \n";

			for(int j = 0 ; j < SUDOKU_LENGTH; j++)
			{
				output = output + "  "; 
				if(j % 3 == 0 && j != 0 && j != SUDOKU_LENGTH)
				{
					output = output + "  "; 
				}

				for(int k = 6; k < SUDOKU_LENGTH; k++)
				{
					output = String.format(output + matrix[j][k]);
				}
			}
			output = output + "  \n";
		
		for(int m = 0; m < LENGTH_PRINT_LINE; m++)
		{
			output = output + "  ";
		}
		output = output + "\n";
		return output;
	}	

	public String toString()
	{
		String output = "";

		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			if(i % 3 == 0 && i != 0 && i != SUDOKU_LENGTH)
			{
				for(int n = 0; n < 2; n++)
				{
					for(int m = 0; m < LENGTH_PRINT_LINE; m++)
					{
						output = output + "  ";
					}
					output = output + "\n";	
				}		
			}else
			{
				for(int m = 0; m < LENGTH_PRINT_LINE; m++)
				{
					output = output + "  ";
				}
				output = output + "\n";
			}

			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				output = output + "  "; 
				if(j % 3 == 0 && j != 0 && j != SUDOKU_LENGTH)
				{
					output = output +"  "; 
				}
				
				if(getValue(i,j) != 0)
				{
					output = output + "   ";
				}
				else 
				{
					for(int k = 0; k < 3 ; k++)
					{
						output = String.format(output + possSudoku[i][j][k]);
					}
				}
			}
			output = output + "  \n";

			for(int j = 0; j < SUDOKU_LENGTH; j++)
			{
				output = output + "  "; 
				if(j % 3 == 0 && j != 0 && j != SUDOKU_LENGTH)
				{
					output = output +"  "; 
				}
				
				if(getValue(i, j) != 0)
				{
					output = String.format(output + " " + getValue(i, j) + " ");
				}
				else
				{
					for(int k = 3; k < 6; k++)
					{
						output = String.format(output + getCell(i, j)[k]);				
					}
				}
			}
			output = output + "  \n";

			for(int j = 0 ; j < SUDOKU_LENGTH; j++)
			{
				output = output + "  "; 
				if(j % 3 == 0 && j != 0 && j != SUDOKU_LENGTH)
				{
					output = output + "  "; 
				}
				
				if(getValue(i, j) != 0)
				{
					output = output + "   ";
				}
				else
				{
					for(int k = 6; k < SUDOKU_LENGTH; k++)
					{
						output = String.format(output + getCell(i, j)[k]);
					}
				}
			}
			output = output + "  \n";
		
			for(int m = 0; m < LENGTH_PRINT_LINE; m++)
			{
				output = output + "  ";
			}
			output = output + "\n";
		}
		return output;
	}
}
