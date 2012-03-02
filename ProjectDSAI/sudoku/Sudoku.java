
public class Sudoku
{
	public int[][][] possSudoku = new int[9][9][9];

	public int[][] startSudoku = 
	{
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1}
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
					giveValue(possSudoku[i][j],startSudoku[i][j]);
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
		
		
		//for(int i = 0; i<41; i++)
		//{
		//	System.out.print("*");
		//}
		Sudoku sudoku = new Sudoku();
		System.out.printf(sudoku.toString());
		//ma/keNew();
		//System.out.print("*");
	}
	
	
	public String toString()
	{
		String output= "";
		
		//for(int i = 0; i<41; i++)
		//	output = output + "*";
		//output = output + "\n";
		
		for(int i = 0; i<9; i++)
		{
		
			if(i%3==0 && i!=0 && i!= 9)
			{
				for(int n = 0; n<2; n++){
					for(int m = 0; m<39; m++){
						output = output + "*";
					}
					output = output + "\n";	
				}		
			}else{
				for(int m = 0; m<39; m++)
					output = output + "*";
			output = output + "\n";
			}
			
			for(int j = 0; j < 9; j++)
			{
				output = output + "*"; 
				if(j%3==0 && j !=0 && j != 9)
				{
					output = output +"*"; 
				}
				for(int k = 0; k<3 ; k++)
				{
					output = String.format(output + possSudoku[i][j][k]);
				}
				//output = output + "* "; 
			}
			output = output + "*\n";

			for(int j = 0; j < 9; j++){
				output = output + "*"; 
				if(j%3==0 && j !=0 && j != 9)
				{
					output = output +"*"; 
				}
				for(int k = 3; k<6 ;k++){
					output = String.format(output+ possSudoku[i][j][k]);
				}
				
			}
			output = output + "*\n";

			for(int j = 0 ; j < 9; j++){
				output = output + "*"; 
				if(j%3==0 && j !=0 && j != 9)
				{
					output = output +"*"; 
				}
				for(int k = 6; k<9 ;k++){
					output = String.format(output+ possSudoku[i][j][k]);
				}
				//output = output + "*"; 
			}
			
			output = output + "*\n";

			}
		for(int m = 0; m<39; m++)
			output = output + "*";
		output = output + "\n";
	
		
		return output;
	}
	
	/*
	public String toString(){
		
		String output = "";	
			
			for(int i = 0; i <=8; i++){
				
				for(int j = 0; j <= 8; j++)
				{					
					output = String.format(output + sudoku[i][j] + "|");
					if (j==8)
						output = output + "\n";
				}
				output = String.format(output + "------------------\n");
			}
			
			return output;
		}
	*/
	
	
	
	// count how many values a single cell can have
	public int countAmountNotZero(int[] array)
	{
		int count = 0;
		for(int i= 0; i<array.length; i++)
		{
			if(array[i]==0)
				count++;
		}
		return count;
	}
	
	//check if the sudoku is solved
	public boolean checkSolved()
	{
		for(int i = 0; i<9; i++)
		{
			for(int j = 0; i<9;i++)
			{
				for(int k = 0; i<9;i++)
				{
					if(countAmountNotZero(possSudoku[i][j])!=1)
					{
						return false;
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
	
	public void giveValue(int[] cell, int value)
	{
		for(int i =0; i<cell.length; i++)
		{
			if(cell[i]!=value)
				cell[i] = 0;
		}
	}
	
	public void updateColumns(){}
	public void updateRows(){}
	public void updateQuadrants(){}
	public void updateSingles(){}
	public void updateHiddenSingles(){}
	public void updateLockedCandidates1(){}
	public void updateLockedCandidates2(){}
	
	
	
	
	
	
	
}

