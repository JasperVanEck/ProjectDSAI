
public class Sudoku
{
	public int[][][] possSudoku = new int[9][9][9];

	public int[][] startSudoku = 
	{
			{4,0,0, 0,5,3, 0,0,0},
			{0,0,0, 2,9,4, 0,7,3},
			{3,0,0, 0,7,0, 5,0,9},
			
			{7,8,0, 0,0,0, 0,1,0},
			{0,0,0, 0,0,0, 0,0,0},
			{0,4,0, 0,0,0, 0,2,5},
			
			{2,0,8, 0,1,0, 0,0,7},
			{9,1,0, 7,6,2, 0,0,0},
			{0,0,0, 8,3,0, 0,0,2}
	};

	
	public Sudoku()
	{
		makeNew();
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if(startSudoku[i][j]!=0)
				{
					setValue(possSudoku[i][j],startSudoku[i][j]);
				}
			}
		}
	}                                     
	
	//create new sudoku (9x9)array, each cell can have value 1-9
	public void makeNew()
	{
		for(int i = 0; i<9;i++)
		{
			for(int j = 0; j<9;j++)
			{
				for(int k = 0; k<9;k++)
				{
					possSudoku[i][j][k]= k+1;
				}	
			}
		}
	}
	
	public static void main(String[] args)
	{
		//create new sudoku, a 9x9 matrix, with in every cell, the number 1-9
		//then fill in the known-in-advance numbers
		Sudoku sudoku = new Sudoku();
		int stop = 0;
		//while sudoku is not solved, use different tactics to search for solutions
		while(!sudoku.checkSolved() && stop < 10)
		{
			System.out.printf(sudoku.toString());
			sudoku.updateSingle();
			sudoku.updateHiddenSingle();
			sudoku.updateLockedCandidates1();
			sudoku.updateLockedCandidates2();
			stop++;
		}
		System.out.println("\nSudoku opgelost!\n");
		System.out.print(sudoku.toString2());
	}
	
	public double percentageSolved()
	{
		int count =0;
		//iterate through 9x9 matrix
		for(int i=0;i<9;i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if( countAmountNotZero(possSudoku[i][j]) == 1)
				{
					count++;
				}
			}
		}
		
		return 100*count/81.0;
	}
	// count how many nonzero-values a single cell has
	// i.e. how many possibilities has this cell at this moment
	public int countAmountNotZero(int[] array)
	{
		//init count
		int count = 0;
		
		//iterate trough cell
		for(int i= 0; i<array.length; i++)
		{
			//if nonzero value found, increment cout
			if(array[i]!=0)
				count++;
		}
		return count;
	}
	
	public boolean checkAllOne(int array[])
	{
		for(int i =0; i<array.length; i++)
			if(array[i] != 1)
				return false;
		return true;
	}
	
	
	 /* check if the sudoku is filled in completely
	 * warning: this doesn't mean it correctly filled in
	 * maybe new method for this has to be written,
	 * which checks if every column row, quadrant has 1-9
	 */
	public boolean checkSolved()
	{
		boolean solved = true;
		//iterate through matrix
		//check that every cell has a single value
		for(int i = 0; i<9; i++)
			for(int j = 0; j<9;j++)
				if(countAmountNotZero(possSudoku[i][j])!=1)
					solved = false;
		
		int countArray[] = new int[9];
		//check for every row that each number appears one time
		for(int i = 0; i<9; i++)
		{
			if(checkColumnSolved(i) == false)
			{
				solved = false;
			}
			
			if(checkRowsSolved(i) == false)
			{
				solved = false;
			}
			
			if(checkQuadsSolved(i) == false)
			{
				solved = false;
			}
		}		
		return solved;
	}
	
	public boolean checkColumnSolved(int i)
	{
		int countArray[] = new int[9];
		for(int j = 0; j<9; j++)
			if(countAmountNotZero(possSudoku[i][j]) == 1)
				countArray[getValue(possSudoku[i][j])-1]++;
		return checkAllOne(countArray);
	}
	
	public boolean checkRowsSolved(int j)
	{
		int countArray[] = new int[9];
		for(int i = 0; i<9; i++)
			if(countAmountNotZero(possSudoku[i][j]) == 1)
				countArray[getValue(possSudoku[i][j])-1]++;
		return checkAllOne(countArray);
	}
	
	public boolean checkQuadsSolved(int q)
	{
		int countArray[] = new int[9];
		for(int i = q2c(q)[0]; i<q2c(q)[0]+3; i++)
		{
			for(int j = q2c(q)[1]; j<q2c(q)[1]+3; j++)
			{
				if(countAmountNotZero(possSudoku[i][j]) == 1)
					countArray[getValue(possSudoku[i][j])-1]++;
			}
		}
		return checkAllOne(countArray);
	}
	
	
	
	
	
	//check if a cell contains value i
	public boolean checkCellHasValue(int[] cell, int value)
	{
		boolean hasValue = false;
		//iterate through cell
		for(int i=0; i<cell.length; i++)
		{
			//if value found in cell,stop, return true
			if(cell[i]==value)
				hasValue = true;
		}
		//value not found in cell, return false
		return hasValue;
	}
	//if cell has only single value remaining return value, else return zero
	public int getValue(int[] cell)
	{
		int value = 0;
		//if cell has only one nonzero value
		if(countAmountNotZero(cell) == 1)
		{
			int i = 0;
			
			//iterate through cell, when value found return this value
			while(i<cell.length)
			{
				if(cell[i] != 0)
				{
					value = cell[i];
				}
				i++;
			}			
		}
		//else return zero
		return value;
	}
	
	//set Cell to certain value
	public void setValue(int[] cell, int value)
	{
		//iterate through cell
		for(int i =0; i<cell.length; i++)
		{
			//set al remaining values other then 'value' to zero
			if(cell[i]!=value)
				cell[i] = 0;
		}
	}
	
	/* the single tactics, looks at the cells which are filled in.
	 * if cell is x, then all the cells in the same column, row, or
	 * quad, can't be this value, so delete this values on the right
	 * places of the possSudoku.
	 */	
	public void updateSingle()
	{
		//iterate through sudoku, (9x9)
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				//first check if cell is not already filled in, to save time
				if(getValue(possSudoku[i][j])==0)
				{
					//update cell(i,j)
					updateSingleCell(i,j);
					
					//if cell is now filled in print it
					if(getValue(possSudoku[i][j]) !=0)
					{
						System.out.printf(printChange("Single            "
								, i, j, getValue(possSudoku[i][j]), "filled in"));
					}
				}
			}
		}
	}
	
	/* a given cell, look what values are in same column,
	 * row or quadrant and delete these values from this cell
	 */
	public void updateSingleCell(int row, int col)
	{
		updateSingleRow(row,col);
		updateSingleColumn(row,col);
		updateSingleQuad(row,col);
	}
	
	// update cell,by looking which values are already filled in,in same column
	public void updateSingleColumn(int row, int col)
	{
		//iterate through rows of a column
		for(int i = 0; i<9; i++)
		{
			//don't compare cell with itself (because this is useless
			//check if cell has only one value/ is filled in
			if(i != row && getValue(possSudoku[i][col])!=0)
			{
				//check if the cell to be updated, still has this value
				if(checkCellHasValue(possSudoku[row][col],getValue(possSudoku[i][col])))
				{	
					//if so, delete this value from this cell
					deleteValue(possSudoku[row][col],getValue(possSudoku[i][col]));
				}
			}
		}
	}
	
	// update cell, by looking which values are already filled in, in same row
	public void updateSingleRow(int row, int col)
	{
		///iterate through columns of a row
		for(int j= 0; j<9; j++)
		{
			//don't compare cell with itself (because this is useless
			//check if cell has only one value/ is filled in
			if(j != col && getValue(possSudoku[row][j])!=0)
			{
				//check if the cell to be updated, still has this value
				if(checkCellHasValue(possSudoku[row][col],getValue(possSudoku[row][j])))
				{
					//if so, delete this value from this cell					
					deleteValue(possSudoku[row][col],getValue(possSudoku[row][j]));
				}
			}
		}
	}
	
	// update cell, by looking which values are already filled in, in same quad
	public void updateSingleQuad(int row, int col)
	{
		//for row's in same quadrant
		for(int i= 3 * (row/3); i< 3* ((row/3)+1);i++)
		{
			//for columns in same quadrant
			for(int j= 3 * (col/3); j< 3* ((col/3)+1);j++)
			{
				// don't compare cell with itself &&
				// check if cell has only one value/is filled in
				if(!(i == row && j == col) && getValue(possSudoku[i][j])!=0)
				{
					//check if cell(row,col), still has this value
					if(checkCellHasValue(possSudoku[row][col],getValue(possSudoku[i][j])))
					{
						//if so delete this value from cell
						deleteValue(possSudoku[row][col],getValue(possSudoku[i][j]));
					}
				}	
			}
		}
	}
	
	//if cell still has value x in it, delete x from cell
	public void deleteValue(int cell[],int value)
	{
			//value will be in cell[value-1]
			//because first place of cell is cell[0]
			if(cell[value-1]==value)
			{
				cell[value-1] = 0;
			}
	}
	
	/* Hidden Single Tactic:
	 * if in a column, row, or quadrant a value is not filled
	 * in yet, but is only found in one particular cell (so
	 * this cell has this certain values AND other values,
	 * then delete the other values
	 */
	public void updateHiddenSingle()
	{
		//loop through rows, columns and quadrant, looking for hidden singles
		for(int i =0; i<9; i++)
		{
			updateHiddenSingleRow(i);
			updateHiddenSingleColumn(i);
			updateHiddenSingleQuad(i);
		}	
	}
		
	/*this method looks for hidden singles in column j
	 *if found, delete other values in cell which has 
	 *hidden single value in it.
	 */ 
	public void updateHiddenSingleColumn(int j)
	{
		/*
		 * init count array, this array counts how many times 1-9
		 * is found in this column, it also saves the coordinate
		 * where this value is last found, so if there are hidden singles
		 * found, you know where this hidden singles were found
		 */
		int[][] countArray = new int[9][3];
	
		//iterate through columns
		for(int i =0; i<9; i++)
		{
			//iterate through cell
			for(int k = 0; k<9; k++)
			{
				//if nonzero value found
				if(possSudoku[i][j][k]== k+1)
				{
					//increment count array on right place
					countArray[k][0]++;
					//save where this value was found
					countArray[k][1] = i;
					countArray[k][2] = j;
				}
			}
		}
		
		//now count array is filled
		//iterate through it, to find hidden singles
		for(int k = 0; k<countArray.length; k++)
		{
			//if countArray has hidden single && and this cell is not filled in yet
			if(countArray[k][0]==1 && countAmountNotZero(possSudoku[countArray[k][1]][countArray[k][2]]) !=1)
			{
				//fill in the cell
				setValue(possSudoku[countArray[k][1]][countArray[k][2]],k+1);//(countArray[j][0])+1);
				System.out.printf(printChange("HiddenSingleColumn   ", countArray[k][1], countArray[k][2], k+1, "filled in"));
			}
		}
	}
	
	/*
	 * this method looks for hidden singles in row i
	 * if found, delete other values in cell which has 
	 * hidden single value in it.
	 */ 
	public void updateHiddenSingleRow(int i)
	{
		/*
		 * init count array, this array counts how many times 1-9
		 * is found in this row it also saves the column coordinate
		 * where this value is last found, so if there are hidden 
		 * singles found, you know where this hidden singles was found
		 */
		int[][] countArray = new int[9][3];
	
		//iterate through rows
		for(int j =0; j<9; j++)
		{
			//iterate through cell
			for(int k = 0; k<9; k++)
			{
				//if nonzero value found
				if(possSudoku[i][j][k]== k+1)
				{
					//increment countArray on right place
					countArray[k][0]++;
					//save the column coordinate where hidden single is found
					countArray[k][1] = i;
					countArray[k][2] = j;
				}
			}
		}
		
		//now count array is filled
		//iterate through it, to find hidden singles	
		for(int k = 0; k<countArray.length; k++)
		{
			//if countArray has hidden single && and this cell is not filled in yet
			if(countArray[k][0]==1 && countAmountNotZero(possSudoku[countArray[k][1]][countArray[k][2]]) !=1)
			{
				//fill in the cell
				setValue(possSudoku[countArray[k][1]][countArray[k][2]],k+1);//(countArray[j][0])+1);
				System.out.printf(printChange("HiddenSingleRow   ", countArray[k][1], countArray[k][2], k+1, "filled in"));
			}
		}
	}

	/* this method looks for hidden singles in row i
	 * if found, delete other values in cell which has 
	 * hidden single value in it.
	 */
	public void updateHiddenSingleQuad(int q)
	{
		int[][] countArray = new int[9][3];
	
		//iterate quadrants
		for(int i = q2c(q)[0]; i< q2c(q)[0]+3; i++)
		{
			for(int j = q2c(q)[1];j<q2c(q)[1]+3 ;j++)
			{
				//iterate through cell
				for(int k=0; k<9; k++)
				{
					//if nonzero value found
					if(possSudoku[i][j][k]== k+1)
					{
						//increment countArray on right place
						countArray[k][0]++;
						//save the coordinate where hidden single is found
						countArray[k][1] = i;
						countArray[k][2] = j;
					}
				}
			}
		}
			
		// count array is filled, iterate through it, to find hidden singles	
		for(int j = 0; j<countArray.length; j++)
		{
			//if countArray has hidden single && and this cell is not filled in yet
			if(countArray[j][0]==1 && countAmountNotZero(possSudoku
					[countArray[j][1]][countArray[j][2]]) !=1)
			{
				//fill in the cell
				setValue(possSudoku[countArray[j][1]][countArray[j][2]],j+1);
				//print which value is filled in on which coordinate
				System.out.printf(printChange("HiddenSingleQuad  ", 
						countArray[j][1], countArray[j][2], j+1, "filled in"));
			}
		}
	}
	
	/* This method prints the progress & which value is filled in at which cell
	 */
	public String printChange(String tactic,int coordI,int coordJ,int changed, String deleteOrFilledIn)
	{
		return (int)percentageSolved() + "%%,\t" + changed + " " + deleteOrFilledIn + " in cell [" + 
			coordI + "," + coordJ + "] \t tactic used: " + tactic + "\n";
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
		int[] startCoord = {(q/3)*3,(q%3)*3};
		return startCoord;
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
		int returnValue = 0;
		//iterate through quadrant
		for(int i = q2c(q)[0]; i < q2c(q)[0] + 3; i++)
		{
			for(int j = q2c(q)[1]; j < q2c(q)[1] + 3; j++)
			{
				if(checkCellHasValue(possSudoku[i][j], value) &&
						countAmountNotZero(possSudoku[i][j]) == 1)
				{
					returnValue = -1;
				}
				
				
				if(checkCellHasValue(possSudoku[i][j], value) &&
						countAmountNotZero(possSudoku[i][j]) != 1)
				{
					if(rowOrColumn.equals("row"))
					{
						countArray[i-q2c(q)[0]]++;
					} else if(rowOrColumn.equals("column"))
					{
						countArray[j-q2c(q)[1]]++;
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
				returnValue += q2c(q)[0];
			} else if(returnValue != -1 && rowOrColumn.equals("column"))
			{
				returnValue += q2c(q)[1];
			}
		}
		
		
		return returnValue;
	}
	
	public void updateLockedCandidates1()
	{
		for(int value = 1; value<=9; value++)
		{
			for(int q = 0; q < 9; q++)
			{
				updateLockedCandidatesValQ(value,q);
			}
		}
	}
	
	
	public void updateLockedCandidatesValQ(int value, int q)
	{
		int row = valueOnSingleRowOrColumnOfQuadrant(q, value, "row");
		//System.out.println(value + "," + row);
		//System.out.printf(toString());
		if(row != -1)
		{
			//System.out.println("2q: "+ q + ",val: " + value +" "+ valueOnSingleRowOrColumnOfQuadrant(q, value, "row"));
			
			deleteValueFromRow(row, value, q);
			
		}
		
		int column = valueOnSingleRowOrColumnOfQuadrant(q, value, "column");
		if(column != -1)
		{
			deleteValueFromColumn(column,value,q);
		}
	}
	
	public void deleteValueFromRow(int row, int value, int q)
	{
		//walk through row
		for(int j = 0; j < 9; j++)
		{
			//if not in quadrant q
			if( ( j<q2c(q)[1] || j > q2c(q)[1] + 2 ) && checkCellHasValue(possSudoku[row][j],value) )
			{
				//delete value q from cell
				//System.out.printf(toString());

				deleteValue(possSudoku[row][j],value);
				System.out.printf(printChange("LockedCandindates 1 Row", row, j, value, "deleted  "));
				//System.out.printf(toString());
				//System.out.println("*******************");
			}
		}
	}
	
	public void deleteValueFromColumn(int column, int value, int q)
	{
		//walk through column
		for(int i = 0; i < 9; i++)
		{
			//if not in quadrant q
			if( ( i<q2c(q)[0] || i > q2c(q)[0] + 2 ) && checkCellHasValue(possSudoku[i][column],value) )
			{
				//delete value q from cell
				deleteValue(possSudoku[i][column],value);
				System.out.printf(printChange("LockedCandindates 1 Column", i, column, value, "deleted  "));                              
			}
		}
	}
	
	
	public void updateLockedCandidates2()
	{
		//for all rows r 
		
		for(int i = 0; i < 9; i++)
		{
			//for all values v
			for(int v = 1; v <= 9; v++)
			{
				//method who gives the quadrant q on which a value v appears in a
				//certain row r, and doesn't appear in this row r  in the other quadrants (!=q)
				int q = rowValuesOnlyInQuad(i,v);
				
				//delete this value v from the quadrant q, except for row r
				if(q!= -1)
				{
					deleteValueFromQuadrantExceptRowOrColumn(q, i, v, "row");
				}
			}
		}	
		
		//for all columns c
		for(int j = 1; j < 9; j++)
		{
			//for all values v 
			for(int v = 1; v <= 9; v++)
			{
				//method who gives the quadrant q on which a value v appears in a
				//certain column c, and doesn't appear in this column c in the other quadrants (!=q)
				int q = columnValuesOnlyInQuad(j,v);
			 
				//delete this value from the quadrant, except for column c
				if(q!=-1)
				{
					deleteValueFromQuadrantExceptRowOrColumn(q, j, v, "column");
				}
			}
		}	
	}
	
	public void deleteValueFromQuadrantExceptRowOrColumn(int q, int index, int value, String rowOrColumn)
	{
		//iterate through quadrant
		//System.out.println("q=" + q);
		for(int i=q2c(q)[0]; i<q2c(q)[0]+3; i++)
		{
			//System.out.println(i);
			for(int j = q2c(q)[1]; j < (q2c(q)[1])+3; j++)
			{
				//System.out.println(i + "," + j);
				if(j != index && rowOrColumn.equals("column") && checkCellHasValue(possSudoku[i][j], value))
				{
					System.out.printf(printChange("Locked Candidates 2c", i, j, value, "deleted"));
					deleteValue(possSudoku[i][j], value);
				} else if(i != index && rowOrColumn.equals("row")&& checkCellHasValue(possSudoku[i][j], value))
				{
					System.out.printf(printChange("Locked Candidates 2r", i, j, value, "deleted"));
					deleteValue(possSudoku[i][j], value);
				}
			}
		}
	}
	
	public int columnValuesOnlyInQuad(int c, int value)
	{
		//countArray counts for every cell in column if it how many times it appears in
		//this column,
		int returnValue = 0;
		int countArray[] = new int[3];
		for(int r = 0; r < 9; r++)
		{
			if(checkCellHasValue(possSudoku[r][c], value) && countAmountNotZero(possSudoku[r][c]) == 1)
			{
				returnValue = -1;
			} else if(checkCellHasValue(possSudoku[r][c], value))
			{
				countArray[r/3]++;
			}
		}
		
		if(returnValue != -1)
		{
			if(countArray[0] !=0 && countArray[1] == 0 && countArray[2] == 0)
			{
				returnValue = c2q(1,c);
			} else if(countArray[0] == 0 && countArray[1] != 0 && countArray[2] == 0)
			{
				returnValue = c2q(4,c);
			} else if(countArray[0] == 0 && countArray[1] == 0 && countArray[2] != 0)
			{
				returnValue = c2q(7,c);
			} else
			{
				returnValue = -1;
			}
		}
		return returnValue;
	}
	
	public int rowValuesOnlyInQuad(int r, int value)
	{
		//countArray counts for every cell in row if it how many times it appears in
		//this row,
		int returnValue = 0;
		int countArray[] = new int[3];
		
		//walk through columns of row
		for(int c = 0; c < 9; c++)
		{
			if(checkCellHasValue(possSudoku[r][c], value) && countAmountNotZero(possSudoku[r][c]) == 1)
			{
				returnValue = -1;
			} else if(checkCellHasValue(possSudoku[r][c], value))
			{
				countArray[c/3]++;
			}
		}
		
		if(returnValue != -1)
		{
			if(countArray[0] !=0 && countArray[1] == 0 && countArray[2] == 0)
			{
				returnValue = c2q(r,1);
			} else if(countArray[0] == 0 && countArray[1] != 0 && countArray[2] == 0)
			{
				returnValue = c2q(r,4);
			} else if(countArray[0] == 0 && countArray[1] == 0 && countArray[2] != 0)
			{
				returnValue = c2q(r,7);
			} else
			{
				returnValue = -1;
			}
		}
		return returnValue;
	}
	
	
	
	public int  c2q(int r, int c)
	{
		return (r/3)*3 + c/3;
	}
	

	/* this method prints sudoku, zeros at cells unknown
	 * else print value
	 */
	public String toString2()
	{
		String output= "";
		for(int i = 0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				if(getValue(possSudoku[i][j])!=0)
					output = output + getValue(possSudoku[i][j]);
				else 
					output = String.format(output + "0");
				if((j+1)%3==0)
					output = output + " ";
			}
			output = output + "\n";
			if((i+1)%3==0)
				output = output + "\n";
		}
		return output;
	}
	
	/*
	 * this method prints sudoku, at each cell, print(non-Javadoc)
	 * @see java.lang.Object#toString()
	 * all the possible values, if one value remaining,
	 * print only this value in middle of cell.
	 */
	public String toString()
	{
		String output= "";
		
		for(int i = 0; i<9; i++)
		{
			if(i%3==0 && i!=0 && i!= 9)
			{
				for(int n = 0; n<2; n++)
				{
					for(int m = 0; m<39; m++)
						output = output + "  ";
					output = output + "\n";	
				}		
			}else
			{
				for(int m = 0; m<39; m++)
					output = output + "  ";
				output = output + "\n";
			}
			
			for(int j = 0; j < 9; j++)
			{
				output = output + "  "; 
				if(j%3==0 && j !=0 && j != 9)
					output = output +"  "; 
				
				if(getValue(possSudoku[i][j])!=0)
					output = output + "   ";
				else 
					for(int k = 0; k<3 ; k++)
						output = String.format(output + possSudoku[i][j][k]);
			}
			output = output + "  \n";

			for(int j = 0; j < 9; j++)
			{
				output = output + "  "; 
				if(j%3==0 && j !=0 && j != 9)
					output = output +"  "; 
				if(getValue(possSudoku[i][j])!=0)
					output = String.format(output + " " + getValue(possSudoku[i][j]) + " ");
				else
					for(int k = 3; k<6 ;k++)
						output = String.format(output+ possSudoku[i][j][k]);				
			}
			output = output + "  \n";

			for(int j = 0 ; j < 9; j++){
				output = output + "  "; 
				if(j%3==0 && j !=0 && j != 9)
					output = output +"  "; 
				
				if(getValue(possSudoku[i][j])!=0)
					output = output + "   ";
				else
					for(int k = 6; k<9 ;k++)
						output = String.format(output+ possSudoku[i][j][k]);
			}
			output = output + "  \n";
		}
		for(int m = 0; m<39; m++)
			output = output + "  ";
		output = output + "\n";
		return output;
	}	
}