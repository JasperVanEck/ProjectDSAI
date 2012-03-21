
public class Tactics extends Board3d
{
	/** 
	 * This method checks for single values.
	 * A given cell, look what values are in same column,
	 * row or quadrant and delete these values from this cell
	 * if they are still in cell
	 * 
	 * @param board The 3d board state to be updated.
	 * @return The updated 3d board state.
	 */
	public Board3d singleTactic(Board3d board)
	{
		for(int i = 0 ; i < Board3d.SUDOKU_LENGTH; i++)
		{
			board.setRow(i,  updateSingle(board.getRow(i), i, "row"));
			board.setColumn(i,  updateSingle(board.getColumn(i), i, "column"));
			board.setQuad(i,  updateSingle(board.getQuad(i), i, "quadrant"));
		}
		return board;
	}
	
	/**
	 * Updates the sudoku, if there is just a single possibility for a cell.
	 * Checks a row, column or quadrant for a single value, and then fills it in.
	 * 
	 * @param array A row, column or quadrant in which a single might be found.
	 * @param index The index number of the row, column or quadrant.
	 * @param type The name of what type is put in, a row, column or quadrant.
	 * @return The updated row, column or quadrant.
	 */
	public int[][] updateSingle(int[][] array, int index, String type)
	{
		for(int i = 0; i < Board3d.SUDOKU_LENGTH; i++)
		{
			if(checkCellFilledIn(array[i]))
			{
				int deleteVal = getValue(array[i]);
				for(int j = 0; j < Board3d.SUDOKU_LENGTH; j++)
				{
					if(j != i && countAmountNotZero(array[j]) > 1)
					{
						array[j] = deleteValue(array[j], deleteVal);
						
						if(countAmountNotZero(array[j]) == 1)
						{
							System.out.println("Single, " + type + " " + index + ", " +
									"index " + j + ": filled in " + getValue(array[j]));
						}
					}
				}
			}
		}
		
		return array;
	}
	
	/** 
	 * This method checks for hidden single values.
	 * Calls the updateHiddenSingle, so it can be checked for every row, column or quadrant.
	 * 
	 * @param board The 3d board state to be updated.
	 * @return The updated 3d board state.
	 */
	public Board3d hiddenSingleTactic(Board3d board)
	{
		for(int i = 0; i < Board3d.SUDOKU_LENGTH; i++)
		{
			board.setRow(i,  updateHiddenSingle(board.getRow(i), i, "row"));
			board.setColumn(i,  updateHiddenSingle(board.getColumn(i), i, "column"));
			board.setQuad(i,  updateHiddenSingle(board.getQuad(i), i, "quadrant"));
		}
		return board;
	}
	
	/**
	 * Updates the sudoku, if there is just a single hidden possibility for a cell.
	 * Checks a row, column or quadrant for a hidden single value, and then fills it in.
	 * 
	 * @param array  A row, column or quadrant in which a hidden single might be found.
	 * @param index	   The index number of the row, column or quadrant.
	 * @param type The name of what type is put in, a row, column or quadrant.
	 * @return The updated row, column or quadrant.
	 */
	public int[][] updateHiddenSingle(int[][] array, int index, String type)
	{
		/*
		 * init count array, this array counts how many times 1-9 is found in 
		 * this array, it also saves the index where this value is last found,
		 * so if there are is a hidden single found, you know where this is found
		 */
		int[][] countArray = new int[SUDOKU_LENGTH][2];
		
		//iterate through array
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			//iterate through cell
			for(int k = 0; k < SUDOKU_LENGTH; k++)
			{
				//if nonzero value found
				if(array[i][k] == k + 1)
				{
					//increment count array on right place, and save coords
					countArray[k][0]++;
					countArray[k][1] = i;
				}
			}
		}

		//now count array is filled
		//iterate through it, to find hidden singles
		for(int k = 0; k < countArray.length; k++)
		{
			//if countArray has hidden single && and this cell is not filled in yet
			if(countArray[k][0] == 1 && countAmountNotZero(array[countArray[k][1]]) != 1)
			{
				//fill in the cell
				array[countArray[k][1]] = setValue(array[countArray[k][1]], k + 1);				
				System.out.println("Hidden single, " + type + " "+ index + ", " +
						"index " + countArray[k][1] + ": filled in " + (k + 1) );
			}
		}
		
		return array;
	}
	
	/**
	 * Updates the sudoku, if there are type 1 locked candidates in the sudoku.
	 * This method iterates through all the values and quadrants to check for locked candidates.
	 * 
	 * @param  board The 3d board state to be updated.
	 * 
	 * @return The updated 3d board state.
	 */
	public Board3d lockedCandidates1Tactic(Board3d board)
	{
		//Iterate through all values, and quadrants
		for(int value = 1; value <= SUDOKU_LENGTH; value++)
		{
			for(int q = 0; q < SUDOKU_LENGTH; q++)
			{
				//update the board
				board = updateLockedCandidatesValQ(board, value, q);
			}
		}
		//return the board
		return board;
	}

	/**
	 * Checks in a specific quadrant if there is a locked candidate for the specified value.
	 * If it finds a locked candidate it removes it from the 3d board state.
	 * 
	 * @param   board  The board to be updated.
	 * @param   value The value to be updated
	 * @param   q The quadrant to be checked.
	 * @return  The updated 3d board state.
	 */
	public Board3d updateLockedCandidatesValQ(Board3d board, int value, int q)
	{
		int row = valueOnSingleRowOrColumnOfQuadrant(q, value, "row");
		//System.out.println(value + "," + row);
		//System.out.printf(toString());
		if(row != -1)
		{
			//System.out.println("2q: "+ q + ",val: " + value +" "+ valueOnSingleRowOrColumnOfQuadrant(q, value, "row"));

			board = deleteValueFromRow(board, row, value, q);

		}

		int column = valueOnSingleRowOrColumnOfQuadrant(board, q, value, "column");
		if(column != -1)
		{
			board = deleteValueFromColumn(board, column, value, q);
		}
		return board;
	}

	/**
	 * Checks how many times a value appears in a row or column in a specified quadrant.
	 * 
	 * 
	 * @param board The 3d board state to be updated.
	 * @param q The number of the quadrant.
	 * @param value The value which needs to be searched for.
	 * @param rowOrColumn  Contains the value "row" or "column", so there can be determined in what this method is searching.
	 * @return A row or column number.
	 */
	public int valueOnSingleRowOrColumnOfQuadrant(Board3d board, int q, int value, String rowOrColumn)
	{
		//count how many times the value appears in every row
		int countArray[] = new int[3];
		int returnValue = 0;
		//iterate through quadrant
		for(int i = quadToRow(q); i < quadToRow(q) + QUAD_LENGTH; i++)
		{
			for(int j = quadToCol(q); j < quadToCol(q) + QUAD_LENGTH; j++)
			{
				if(checkCellHasValue(board.getCell(i, j), value) &&
						board.countAmountNotZero(i, j) == 1)
				{
					returnValue = -1;
				}
				if(checkCellHasValue(board.getCell(i, j), value) &&
						countAmountNotZero(board.getCell(i, j)) != 1)
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

		if(returnValue != -1)
		{
			//check if a value appears only in a single row or column
			if(countArray[0] != 0 && countArray[1] == 0 && countArray[2] == 0)
			{
				returnValue = 0;
			} else if(countArray[0] == 0 && countArray[1] != 0 && countArray[2] == 0)
			{
				returnValue = 1;
			} else if(countArray[0] == 0 && countArray[1] == 0 && countArray[2] != 0)
			{
				returnValue = 2;
			} else //not a single value found
			{
				returnValue = -1;
			}

			// add row or column index of upper left corner of quadrant q
			if(returnValue != -1 && rowOrColumn.equals("row"))
			{
				returnValue += quadToRow(q);
			} else if(returnValue != -1 && rowOrColumn.equals("column"))
			{
				returnValue += quadToCol(q);
			}
		}
		return returnValue;
	}

	/**
	 * This method deletes a value in a specified row.
	 * The quadrant number is needed, so that there can be checked that
	 * the quadrant doesn't contain the value.
	 * 
	 * @param  board The 3dboard state which needs to be updated.
	 * @param  row The row the value needs to be deleted from.
	 * @param  value The value that needs to be deleted.
	 * @param  q The quadrant the value needs to be deleted from.
	 * @return The updated 3Dboard state.
	 */
	public Board3d deleteValueFromRow(Board3d board, int row, int value, int q)
	{
		//walk through row
		for(int j = 0; j < SUDOKU_LENGTH; j++)
		{
			//if not in quadrant q
			if( ( j < quadToCol(q) || j > quadToCol(q) + 2 ) && checkCellHasValue(board.getCell(row, j), value) )
			{
				//delete value q from cell
				//System.out.printf(toString());

				board.deleteValue(row, j, value);
				//System.out.printf(printChange("LockedCandindates 1 Row", row, j, value, "deleted  "));
				//System.out.printf(toString());
				System.out.println("locked1 deleted "+ value + " from [" + row + "," + j + "]"); 
			}
		}
		return board;
	}

	/**
	 * This method deletes a value in a specified column.
	 * The quadrant number is needed, so that there can be checked that
	 * the quadrant doesn't contain the value.
	 * 
	 * @param  board The 3dboard state which needs to be updated.
	 * @param  column The column the value needs to be deleted from.
	 * @param  value The value that needs to be deleted.
	 * @param  q The quadrant the value needs to be deleted from.
	 * @return The updated 3Dboard state.
	 */
	public Board3d deleteValueFromColumn(Board3d board, int column, int value, int q)
	{
		//walk through column
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			//if not in quadrant q
			if((i < quadToRow(q) || i > quadToRow(q) + 2) && board.checkCellHasValue(i, column, value))
			{
				//delete value q from cell
				board.deleteValue(i, column, value);
				//System.out.printf(printChange("LockedCandindates 1 Column", i, column, value, "deleted  "));
				System.out.println("locked1 deleted "+ value + " from [" + i + "," + column + "]"); 
				
			}
		}
		return board;
	}
	
	/**
	 * Calls all the tactical methods, to solve the sudoku.
	 * The method also keeps track of the progress, if there is no progress
	 * it returns the update 3d board state.
	 * 
	 * @param  board The 3d board state which needs to be updated.
	 * @return The updated 3d board state.
	 */
	public Board3d useAllTactics(Board3d board)
	{
		double progress1 = 0;
		double progress2 = 1;
		while(progress1 != progress2)
		{
			progress1 = board.percentageSolved();
			board = singleTactic(board);
			
			board = hiddenSingleTactic(board);
			board = lockedCandidates1Tactic(board);
			board = lockedCandidates2Tactic(board);
			
			progress2 = board.percentageSolved();
		}
		
		return board;
	}

	/**
	 * Updates the sudoku, if there are type 2 locked candidates in the sudoku.
	 * This method iterates through all the values and quadrants to check for locked candidates.
	 * 
	 * @param  board The 3d board state to be updated.
	 * @return The updated 3d board state.
	 */
	public Board3d lockedCandidates2Tactic(Board3d board)
	{
		//for all rows 
		for(int i = 0; i < SUDOKU_LENGTH; i++)
		{
			//for all values v
			for(int v = 1; v <= SUDOKU_LENGTH; v++)
			{
				//method who gives the quadrant q on which a value v appears in a
				//certain row r, and doesn't appear in this row r  in the other quadrants (!=q)
				int q = rowValuesOnlyInQuad(board, i, v);

				//delete this value v from the quadrant q, except for row r
				if(q != -1)
				{
					deleteValueFromQuadrant(board, q, i, v, "row");
				}
			}
		}
		//for all columns c
		for(int j = 1; j < SUDOKU_LENGTH; j++)
		{
			//for all values v 
			for(int v = 1; v <= SUDOKU_LENGTH; v++)
			{
				//method who gives the quadrant q on which a value v appears in a
				//certain column c, and doesn't appear in this column c in the other quadrants (!=q)
				int q = columnValuesOnlyInQuad(board, j, v);

				//delete this value from the quadrant, except for column c
				if(q != -1)
				{
					deleteValueFromQuadrant(board, q, j, v, "column");
				}
			}
		}
		return board;
	}

	/**
	 * Deletes a value from a quadrant.
	 * This method deletes a value from a specified quadrant.
	 * 
	 * @param board The board3d to be updated.
	 * @param q     The quadrant the value needs to be deleted from.
	 * @param index	  The index of which the cell, which may not be deleted.
	 * @param value	  The value which, needs to be deleted.
	 * @param rowOrColumn  Specifying whether the value needs to be deleted from a row or column.
	 */
	public void deleteValueFromQuadrant(Board3d board, int q, int index, int value, String rowOrColumn)
	{
		//iterate through quadrant
		//System.out.println("q=" + q);
		for(int i = quadToRow(q); i < quadToRow(q) + QUAD_LENGTH; i++)
		{
			//System.out.println(i);
			for(int j = quadToCol(q); j < (quadToCol(q)) + QUAD_LENGTH; j++)
			{
				//System.out.println(i + "," + j);
				if(j != index && rowOrColumn.equals("column") && board.checkCellHasValue(i, j, value))
				{
					//System.out.printf(printChange("Locked Candidates 2c", i, j, value, "deleted"));
					System.out.println("locked2 deleted " + value + " from [" + i + "," + j + "]"); 
					
					board.deleteValue(i, j, value);
				} else if(i != index && rowOrColumn.equals("row") && board.checkCellHasValue(i, j, value))
				{
					System.out.println("locked2 deleted " + value + " from [" + i + "," + j + "]"); 
					
					//System.out.printf(printChange("Locked Candidates 2r", i, j, value, "deleted"));
					board.deleteValue(i, j, value);
				}
			}
		}
	}

	/**
	 * This method checks in which quadrant the value from a specified column is.
	 * 
	 * @param board The 3d board state.
	 * @param c The column number.
	 * @param value The value to be checked for.
	 * @return The quadrant, in which the value is found.
	 */
	public int columnValuesOnlyInQuad(Board3d board, int c, int value)
	{
		//countArray counts for every cell in column if it how many times it appears in
		//this column,
		int returnValue = 0;
		int countArray[] = new int[QUAD_LENGTH];
		for(int r = 0; r < SUDOKU_LENGTH; r++)
		{
			if(board.checkCellHasValue(r, c, value) && board.countAmountNotZero(r, c) == 1)
			{
				returnValue = -1;
			} else if(board.checkCellHasValue(r, c, value))
			{
				countArray[r / 3]++;
			}
		}

		if(returnValue != -1)
		{
			if(countArray[0] !=0 && countArray[1] == 0 && countArray[2] == 0)
			{
				returnValue = coordToQuad(1, c);
			} else if(countArray[0] == 0 && countArray[1] != 0 && countArray[2] == 0)
			{
				returnValue = coordToQuad(4, c);
			} else if(countArray[0] == 0 && countArray[1] == 0 && countArray[2] != 0)
			{
				returnValue = coordToQuad(7, c);
			} else
			{
				returnValue = -1;
			}
		}
		return returnValue;
	}

	/**
	 * This method checks in which quadrant the value from a specified row is.
	 * 
	 * @param board The 3d board state.
	 * @param r The row number.
	 * @param value The value to be checked for.
	 * @return The quadrant, in which the value is found.
	 */
	public int rowValuesOnlyInQuad(Board3d board, int r, int value)
	{
		//countArray counts for every cell in row if it how many times it appears in
		//this row,
		int returnValue = 0;
		int countArray[] = new int[QUAD_LENGTH];

		//walk through columns of row
		for(int c = 0; c < SUDOKU_LENGTH; c++)
		{
			if(board.checkCellHasValue(r, c, value) && board.countAmountNotZero(r, c) == 1)
			{
				returnValue = -1;
			} else if(board.checkCellHasValue(r, c, value))
			{
				countArray[c / 3]++;
			}
		}

		if(returnValue != -1)
		{
			if(countArray[0] != 0 && countArray[1] == 0 && countArray[2] == 0)
			{
				returnValue = coordToQuad(r, 1);
			} else if(countArray[0] == 0 && countArray[1] != 0 && countArray[2] == 0)
			{
				returnValue = coordToQuad(r, 4);
			} else if(countArray[0] == 0 && countArray[1] == 0 && countArray[2] != 0)
			{
				returnValue = coordToQuad(r, 7);
			} else
			{
				returnValue = -1;
			}
		}
		return returnValue;
	}
	
}