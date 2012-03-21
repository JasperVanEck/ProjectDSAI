import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Board3d
{
	/**
	 * The sudoku with all the possibilities for a cell.
	 */
	private int [][][] possSudoku = new int[9][9][9];
	static final int SUDOKU_LENGTH = 9;
	static final int QUAD_LENGTH = 3;
	static final int LENGTH_PRINT_LINE = 39;
	
	/** 
	 * Default constructor.
	 * Takes no arguments, reads the sudoku from file and
	 * creates the possSudoku.
	 */
	public Board3d()
	{
		makeNew();
		readSudoku();
	}
	
	/**
	 * Constructor, which takes a 2d sudoku as input.
	 * Sets the possSudoku, according to the 2d input sudoku.
	 * 
	 * @param sudoku A 2d sudoku.
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
	
	/**
	 * Fill the possSudoku field.
	 * This method fills in every cell of the board with 1-9 on cells 0-8.
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
		
	/**
	 * This method reads the sudoku from a file.
	 * This method reads just 81 characters on a single line. So that behind the sudoku,
	 * it is possible to put some comments about the sudoku being read.
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
	
	/** 
	 * Returns the 3d sudoku.
	 * 
	 * @return The possibility sudoku.
	 */
	public int[][][] getSudoku()
	{
		return possSudoku;
	}
	
	
	/**
	 * This method deletes all the values of a cell, except for the given
	 * parameter value.
	 * Iterates through a cell to delete all the values except, for the given parameter value.
	 * 
	 * @param i The row number of the cell.
	 * @param j The column number of the cell.
	 * @param value The value which needs to be set.
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

	/**
	 * This method deletes all the values of a cell, except for the given
	 * parameter value.
	 * Iterates through a cell to delete all the values except, for the given parameter value.
	 * 
	 * @param cell The cell to be set.
	 * @param value The value which needs to be set.
	 * @return The cell the value was set in.
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
	/**
	 * Counts how many non-zero values a cell in the possibility contains.
	 * Count how many nonzero-values cell possSudoku(i,j) has
	 * i.e. how many possibilities has this cell at this moment.
	 * 
	 * @param i The row number.
	 * @param j The column number.
	 * @return The amount of non-zero entries in the specified cell.
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
	
	/**
	 * Counts how many non-zero values a cell in the possibility contains.
	 * Count how many nonzero-values cell possSudoku(i,j) has
	 * i.e. how many possibilities has this cell at this moment.
	 * 
	 * @param cell The cell with all the possible values.
	 * @return The amount of non-zero entries in the specified cell.
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
	
	/**
	 * This method deletes a value from a cell.
	 * This method deletes the value given, in the cell specified by 
	 * the row and column nubmer.
	 * 
	 * @param i The row number.
	 * @param j The column number.
	 * @param value The value to be deleted.
	 */
	public void deleteValue(int i, int j, int value)
	{
		if(possSudoku[i][j][value - 1] == value && countAmountNotZero(i, j) != 1)
		{
			possSudoku[i][j][value - 1] = 0;
		}
	}

	/**
	 * This method deletes a value from a cell.
	 * This method deletes the value given, in the cell specified by 
	 * the row and column nubmer.
	 * 
	 * @param cell The cell from the value needs to be deleted.
	 * @param value The value to be deleted.
	 * @return The cell the value was deleted from.
	 */
	public int[] deleteValue(int cell[], int value)
	{
		if(cell[value - 1] == value && countAmountNotZero(cell) != 1)
		{
			cell[value - 1] = 0;
		}	
		return cell;
	}

	/**
	 * This method checks if a cell is filled in.
	 * This method checks if a cell is filled in, in other words, is there
	 * only one value remaining in this cell.
	 * 
	 * @param i The row number.
	 * @param j The column number.
	 * @return <code>True</code> if it is filled in, <code>false</code> otherwise.
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
	
	/**
	 * This method checks if a cell is filled in.
	 * This method checks if a cell is filled in, in other words, is there
	 * only one value remaining in this cell.
	 * 
	 * @param cell The cell to be checked.
	 * @return <code>True</code> if it is filled in, <code>false</code> otherwise.
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
	
	/**
	 * This method if a cell still has a certain value.
	 * Iterating through the cell to check whether a cell still has a value present.
	 * 
	 * @param i The row number.
	 * @param j The column number.
	 * @param value The value to be checked for.
	 * @return <code>True</code> if it contains the value, <code>false</code> otherwise.
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
	
	/**
	 * This method if a cell still has a certain value.
	 * Iterating through the cell to check whether a cell still has a value present.
	 * 
	 * @param cell The cell to be checked.
	 * @param value The value to be checked for.
	 * @return <code>True</code> if it contains the value, <code>false</code> otherwise.
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
	
	/**
	 * Creates a 2d board from the 3d board.
	 * A 2d board is generated from the 3d board, so it can be used for backtracking.
	 * As backtracking with a 3d board would be inefficient and very difficult.
	 * 
	 * @return The 2d sudoku.
	 */
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
	
	
	/**
	 *  This method returns a cell.
	 *  This method returns a cell, specified by the row and column numbers.
	 *  
	 *  @param i The row number.
	 *  @param j The column number.
	 *  @return The wanted cell.
	 */
	public int[] getCell(int i, int j)
	{
		return possSudoku[i][j];
	}
	
	/**
	 * This method sets a Cell.
	 * With the given row and column number a cell can be inserted into to possibility sudoku.
	 * 
	 * @param i The row number.
	 * @param j The column number.
	 * @param cell The cell to be inserted.
	 */
	public void setCell(int i, int j, int[] cell)
	{
		possSudoku[i][j] = cell;
	}
	
	/**
	 * Returns a row.
	 * This method returns the row specified.
	 * 
	 * @param i The row number
	 * @return The row, with the possibilities. 
	 */
	public int[][] getRow(int i)
	{
		return possSudoku[i];
	}
	
	/**
	 * Sets a row.
	 * Fill in a complete row.
	 * 
	 * @param i The row number.
	 * @param row The row with the possibilities.
	 */
	public void setRow(int i, int[][] row)
	{
		possSudoku[i] = row;
	}
	
	/**
	 * Returns a column.
	 * This method returns the column specified.
	 * 
	 * @param j The column number
	 * @return The column, with the possibilities. 
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
	
	/**
	 * Sets a column.
	 * Fill in a complete column.
	 * 
	 * @param j The column number.
	 * @param column The column with the possibilities.
	 */
	public void setColumn(int j, int[][] column)
	{
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			possSudoku[i][j] = column[i];
		}
	}
	
	/**
	 * Returns a quadrant.
	 * This method returns the quadrant specified.
	 * 
	 * @param q The quadrant number
	 * @return The quadrant with the possibilities. 
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
	
	/**
	 * Sets a quadrant.
	 * Fill in a complete quadrant.
	 * 
	 * @param q The quadrant number.
	 * @param quad The quadrant with the possibilities.
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
	
	
	/**
	 * Converts row and column to quadrant.
	 * This method calculates in which quadrant a cell is located.
	 * 
	 * @param i The row number.
	 * @param j The column number.
	 * @return The quadrant number.
	 */
	public int coordToQuad(int i, int j)
	{
		return (i / 3) * 3 + j / 3;
	}
	
	/**
	 * Returns row-index upper left corner quadrant.
	 * This method returns the row-index of the upper left corner
	 * of quadrant q.
	 * 
	 * @param q The quadrant number.
	 * @return The row number.
	 */
	public int quadToRow(int q)
	{
		//q is a number from 0 to 8
		return (q / 3) * 3;
	}
	
	/**
	 * Returns column-index upper left corner quadrant.
	 * This method returns the column-index of the upper left corner
	 * of quadrant q.
	 * 
	 * @param q The quadrant number.
	 * @return The column number.
	 */
	public int quadToCol(int q)
	{
		//q is a number from 0 to 8
		return (q % 3) * 3;
	}
	
	/**
	 * Returns the value of a cell.
	 * This method returns the value of cell (i,j) of pussSudoku,
	 * if the cell is filled, in other words, if 
	 * the cell has one non-zero value. If there are more then
	 * one value remaining return zero.
	 * 
	 * @param i The row number.
	 * @param j The column number.
	 * @return The value of the cell.
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
	/**
	 *  Returns row or column of quadrant in which the number is.
	 *  This method checks if a value only appears in one row or column.
	 *  It returns the row or column number on which this value appears
	 *  If the value doesn't appear in a single row or column or is already
	 *  filled in.
	 *  
	 *  @param q The quadrant number.
	 *  @param value The value.
	 *  @param rowOrColumn Specifying whether the value needs to be found from a row or column.
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
	
	/**
	 * Returns percentage solved.
	 * Returns the percentage of how much the sudoku is filled in.
	 * 
	 * @return The percentage solved.
	 */
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
		return 100 * count / 81;
	}
	
	/**
	 * Returns whether the sudoku is solved.
	 * This method checks if the sudoku is solved. Sudoku is solved if on
	 * every row, column and quadrant every value 1-9 appears one time.
	 * 
	 * @return If solved returns <code>true</code>, <code>false</code> if not.
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

	/**
	 * Returns whether an array is filled in properly.
	 * This method checks if 9x9 array-representation of a row column of quadrant
	 * is filled in, and ever value appears exactly once.
	 * 
	 * @param array The row, column or quadrant to be checked.
	 * @return If filled in return <code>true</code>, <code>false</code> if not.
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

	/**
	 * Returns whether cell contains only one.
	 * This method checks if a cell contains only ones
	 * 
	 * @param array The cell to be checked.
	 * @return If only ones return <code>true</code>, <code>false</code> if not.
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
	 * Returns the value of a cell.
	 * This method returns the value of cell of pussSudoku,
	 * if the cell is filled, in other words, if 
	 * the cell has one non-zero value. If there are more then
	 * one value remaining return zero.
	 * 
	 * @param cell The cell to be retrieved from.
	 * @return The value of the cell.
	 */
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
	
	/**
	 * Returns a 2d sudoku.
	 * This method only prints the cells which are filled in,
	 * else print a zero
	 * 
	 * @return The 2d sudoku string.
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

	/**
	 * Returns a string with a row, column or quadrant reday for printing.
	 * This method prints the sudoku, a 9 x 9 sudoku
	 * at each cell, all the possible values, 
	 * if one value remaining,
	 * print only this value in middle of cell.
	 * 
	 * @param matrix A row, column or quadrant.
	 * @return The row, column or quadrant printed.
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

	/**
	 * Prints the board in standard format.
	 * Prints all the possible values of a cell, if there is just one value left, just print that value.
	 * 
	 * @return The 3d sudoku in standard format.
	 */
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
