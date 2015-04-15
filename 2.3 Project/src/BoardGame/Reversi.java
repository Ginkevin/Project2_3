package BoardGame;

public class Reversi {
	private static Reversi reversi;
	private int [ ] [ ] board = new int[ 8 ][ 8 ];
	private static final int HUMAN        = 0; 
	private static final int COMPUTER     = 1; 
	public  static final int EMPTY        = 2;
	
	public static Reversi getReversi(){
		if (reversi == null){
			reversi = new Reversi();
		}
		return reversi;
	}
	
	public void clearBoard(){
		for (int i=0; i < 8; i++){
			for (int j=0; j<8; j++){
				board[i][j] = 2;
			}
		}
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public void setMoveHuman(int move){
		int rowtmp = move / 8;
		int columntmp = move % 8;
		board[rowtmp][ columntmp] = 0;
	}
	public void setMoveComputer(int move){
		int rowtmp = move / 8;
		int columntmp = move % 8;
		board[rowtmp][ columntmp] = 1;
	}
	
	public int getBoardValue(int location){
		return board[location/8][location%8];
	}
	
	public boolean getLegalMove(int row,int col,int currentplayer, int nextplayer, boolean flip){
        
        int i,j;
        boolean legal;
        int stepcount;;
         
        legal = false;
        if(board[row][col]== EMPTY){
            for(int r = -1;r<=2;r++){
                for(int c = -1;c<=2;c++){
                    stepcount= 0;
                    do{
                        stepcount++;
                        i = row + stepcount*r;
                        j = col + stepcount*c; 
                    }
                    while((i>0)&&(i<=7)&&(j>0)&&(j<=7)&&board[i][j] == nextplayer);
                        if((i>0)&&(i<=7)&&(j>0)&&(j<=7)&&(stepcount >1)&&(board[i][j] == currentplayer)){
                            legal = true;
                            if (flip){
                                for(int k = 1; k < stepcount; k++)
                                    board[row+r*k][col+c*k] = currentplayer;
                            }
                        }
                }
            }   
        }
         
        return legal;
    }
	
	public String toString()
	{
	    //TODO:
		//return "...\n...\n...\n";
		//return board[0][0]+board[0][1]+board[0][2]+"\n" + board[1][0]+board[1][1]+board[1][2]+"\n" + board[2][0]+board[2][1]+board[2][2]+"\n";   
	
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (board[row][col] == HUMAN) {
					sb.append("O");
				} else if (board[row][col] == COMPUTER) {
					sb.append("X");
				} else {
					sb.append('-');
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
