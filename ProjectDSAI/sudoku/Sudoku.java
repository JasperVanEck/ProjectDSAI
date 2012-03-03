public class Sudoku
{
	public int[][][] possSudoku = new int[9][9][9];

	public int[][] startSudoku =
	{
			{0,0,5,0,9,0,0,0,1},
			{0,0,0,0,0,2,0,7,3},
			{7,6,0,0,0,8,2,0,0},

			{0,1,2,0,0,9,0,0,4},
			{0,0,0,2,0,3,0,0,0},
			{3,0,0,1,0,0,9,6,0},

			{0,0,1,9,0,0,0,5,8},
			{9,7,0,5,0,0,0,0,0},
			{5,0,0,0,3,0,7,0,0}
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
		Sudoku sudoku = new Sudoku();

		while(!sudoku.checkSolved())
		{
			for(int i=0; i<9; i++)
			{
				for(int j=0; j<9; j++)
				{
					sudoku.updateCell(i,j);
				}
			}
			System.out.printf(sudoku.toString());
			System.out.println("\n ========================= \n");
		}
	}

	// count how many values a single cell can have
	public int countAmountNotZero(int[] array)
	{
		int count = 0;
		for(int i= 0; i<array.length; i++)
		{
			if(array[i]!=0)
				count++;
		}
		return count;
	}

	//check if the sudoku is solved
	public boolean checkSolved()
	{
		for(int i = 0; i<9; i++)
		{
			for(int j = 0; i<9;i++)//niet j <9?
			{
				for(int k = 0; i<9;i++)//niet k < 9?
				{
					if(countAmountNotZero(possSudoku[i][j])!=1)
					{
						return false;//en waarom die derde forloop als je niet k gebruikt
					}
				}
			}
		}

		return true;
	}

	//check if a cell contains i
	public boolean checkValue(int[] cell, int value)
	{
		for(int i=0; i<cell.length; i++)
		{
			if(cell[i]==value)
				return true;
		}
		return false;
	}

	//get value of a cell, if single
	public int getValue(int[] cell)
	{
		int value = 0;
		if(countAmountNotZero(cell) == 1)
		{
			for(int i=0; i<cell.length; i++)
			{
				value += cell[i];
			}
			return value;
		}
		return value;
	}

	//set Cell to certain value
	public void setValue(int[] cell, int value)
	{
		for(int i =0; i<cell.length; i++)
		{
			if(cell[i]!=value)
				cell[i] = 0;
		}
	}

	public void updateColumns(int row, int col)
	{
		for(int i = 0; i<9; i++)
		{
			if(i != row && getValue(possSudoku[i][col])!=0)
			{
				if(checkValue(possSudoku[row][col],getValue(possSudoku[i][col])))
				{
				deleteValue(possSudoku[row][col],getValue(possSudoku[i][col]));
				}
			}
		}
	}
	public void updateRow(int row, int col)
	{
		for(int i= 0; i<9; i++)
		{
			if(i != col && getValue(possSudoku[row][i])!=0)
			{

				if(checkValue(possSudoku[row][col],getValue(possSudoku[row][i])))
				{
					deleteValue(possSudoku[row][col],getValue(possSudoku[row][i]));
				}
			}
		}
	}

	public void updateQuad(int row, int col)
	{
		for(int i= 3 * (row/3); i< 3* ((row/3)+1);i++)
		{
			//System.out.print(i);
			for(int j= 3 * (col/3); j< 3* ((col/3)+1);j++)
			{
				//System.out.print(j);
				if(!(i == row && j == col) && getValue(possSudoku[i][j])!=0)
				{
					if(checkValue(possSudoku[row][col],getValue(possSudoku[i][j])))
					{
						deleteValue(possSudoku[row][col],getValue(possSudoku[i][j]));
					}
				}
			}
		}
	}

	public void updateCell(int row, int col)
	{
		updateRow(row,col);
		updateColumns(row,col);
		updateQuad(row,col);
	}

	public void deleteValue(int cell[],int value)
	{
		for(int i =0; i < cell.length; i++)
		{
			if(cell[i]==value)
			{
				cell[i] = 0;
			}
		}
	}

	public void updateQuadrants(){}
	public void updateSingles(){}
	public void updateHiddenSingles(){}
	public void updateLockedCandidates1(){}
	public void updateLockedCandidates2(){}



	public String toString()
	{
		String output= "";

		for(int i = 0; i<9; i++)
		{

			if(i%3==0 && i!=0 && i!= 9)
			{
				for(int n = 0; n<2; n++){
					for(int m = 0; m<39; m++){
						output = output + "  ";
					}
					output = output + "\n";
				}
			}else{
				for(int m = 0; m<39; m++)
					output = output + "  ";
			output = output + "\n";
			}

			for(int j = 0; j < 9; j++)
			{
				output = output + "  ";
				if(j%3==0 && j !=0 && j != 9)
				{
					output = output +"  ";
				}
				if(getValue(possSudoku[i][j])!=0)
				{
					output = output + "   ";
				} else {
					for(int k = 0; k<3 ; k++)
					{
						output = String.format(output + possSudoku[i][j][k]);
					}
				}
			}
			output = output + "  \n";

			for(int j = 0; j < 9; j++){
				output = output + "  ";
				if(j%3==0 && j !=0 && j != 9)
				{
					output = output +"  ";
				}
				if(getValue(possSudoku[i][j])!=0)
				{
					output = String.format(output + " " + getValue(possSudoku[i][j]) + " ");
				} else {
					for(int k = 3; k<6 ;k++)
					{
						output = String.format(output+ possSudoku[i][j][k]);
					}
				}

			}
			output = output + "  \n";

			for(int j = 0 ; j < 9; j++){
				output = output + "  ";
				if(j%3==0 && j !=0 && j != 9)
				{
					output = output +"  ";
				}
				if(getValue(possSudoku[i][j])!=0)
				{
					output = output + "   ";
				} else {
					for(int k = 6; k<9 ;k++)
					{
						output = String.format(output+ possSudoku[i][j][k]);
					}
				}
				//output = output + "  ";
			}

			output = output + "  \n";

			}
		for(int m = 0; m<39; m++)
			output = output + "  ";
		output = output + "\n";
		return output;
	}

}
