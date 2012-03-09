package sudoku;

public class Sudoku {

	int sudoku[][] = new int[9][9];
	
	public Sudoku(){
		this.sudoku = beginState();
	}
	
	public int[][] beginState(){
		
		sudoku[0][1] = 2;
		sudoku[0][5] = 3;
		sudoku[0][6] = 5;
		sudoku[1][1] = 5;
		sudoku[1][2] = 7;
		sudoku[1][4] = 8;
		sudoku[1][8] = 9;
		sudoku[2][0] = 9;
		sudoku[2][1] = 8;
		sudoku[2][3] = 4;
		sudoku[2][4] = 1;
		sudoku[3][0] = 7;
		sudoku[3][7] = 5;
		sudoku[4][0] = 1;
		sudoku[4][7] = 3;
		sudoku[4][8] = 6;
		sudoku[5][3] = 5;
		sudoku[5][5] = 6;
		sudoku[5][6] = 4;
		sudoku[5][7] = 9;
		sudoku[6][1] = 7;
		sudoku[6][5] = 4;
		sudoku[6][8] = 3;
		sudoku[7][0] = 3;
		sudoku[7][3] = 2;
		sudoku[7][7] = 8;
		sudoku[8][2] = 2;
		sudoku[8][3] = 1;
		sudoku[8][5] = 8;
		//eerste
		return this.sudoku;
	}
	
	static boolean mogelijkheden(int i, int j, int mogelijkheid, int[][] sudoku){
		
		
		
		for(int k = 0; k <=8; ++k){
			if(mogelijkheid == sudoku[i][k])
				return false;
		}
		
		for(int k = 0; k <=8; ++k){
			if(mogelijkheid == sudoku[k][j])
			return false;
		}
		
		 int rijKwadrant = (i / 3) * 3;
		 int kolomKwadrant = (j / 3) * 3;
		 
		 for(int m = 0; m < 3; ++m){
			 
			 for(int k = 0; k < 3; ++k){
				if(mogelijkheid == sudoku[rijKwadrant + m][kolomKwadrant + k]) 
				return false;
			 }
		 }
		return true;
	}
	
	
	static boolean losOp(int i, int j, int[][] sudoku)
	{
		if (i ==9)
		{
			i = 0;
			if (++j == 9)
				return true;
		}
		
		if (sudoku[i][j] != 0)
			return losOp(i+1,j,sudoku);
		
		for( int k =1 ; k<= 9; ++k)
		{
			if(mogelijkheden(i,j,k,sudoku))
			{
				sudoku[i][j] = k;
				if (losOp(i+1, j, sudoku))
				return true;
			}
		}
		
		sudoku[i][j] = 0;
		
		return false;
	}
	
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
	
	
}
