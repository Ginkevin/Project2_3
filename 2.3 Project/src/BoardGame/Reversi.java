package BoardGame;

import java.util.Stack;

public class Reversi {
	private static Reversi reversi;
	private int [ ] [ ] board = new int[ 8 ][ 8 ];
	private static final int HUMAN        = 0; 
	private static final int COMPUTER     = 1; 
	public  static final int EMPTY        = 2;
	
	public final int ROWS = 8;
	public final int COLUMNS = 8;
	
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
	public void setMoveAi(int col, int row) {
		board[row][col] = 0;
		
	}
	
	public void setMoveForRepositioning(int row, int col, int player)
	{
		board[row][col] = player;
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
	
	public Stack<Integer> getLegalMove2(int move, int player) {
		Stack<Integer> willBeReplaced = new Stack<Integer>();
		Stack<Integer> temp = new Stack<Integer>();
		int opp;
		if (player == HUMAN)
			opp = COMPUTER;
		else
			opp = HUMAN;
		int moveRow = (int)Math.floor(move / COLUMNS);
		int moveColumn = move%COLUMNS;
		for (int y = -1; y <= 1; ++y) {
			for (int x = -1; x <= 1; ++x) {
				int trow = moveRow + y;
				int tcolumn = moveColumn + x;
				if (trow >= 0 && tcolumn >= 0 && trow < ROWS && tcolumn < COLUMNS && board[trow][tcolumn] == opp) {
					while (trow < ROWS && tcolumn < COLUMNS && trow >= 0 && tcolumn >= 0) {
						if (trow * COLUMNS + tcolumn > 0 && trow < ROWS && tcolumn < COLUMNS && board[trow][tcolumn] == opp) {
							temp.push(trow * COLUMNS + tcolumn);
						} else if (board[trow][tcolumn] == player) {
							while (temp.size() > 0) {
								willBeReplaced.push(temp.pop());
							}
							temp.clear();
							break;
						} else if(board[trow][tcolumn] == EMPTY) {
							temp.clear();
							break;
						}
						if (y > 0) {
							++trow;
						} else if (y < 0) {
							--trow;
						}
						if (x > 0) {
							++tcolumn;
						} else if (x < 0) {
							--tcolumn;
						}
					}
					temp.clear();
				}
			}
		}
		willBeReplaced.push(move);
		return willBeReplaced;
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
