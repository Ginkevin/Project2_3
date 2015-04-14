package BoardGame;

import java.util.Random;
public class TicTacToe
{
	private static final int HUMAN        = 0; 
	private static final int COMPUTER     = 1; 
	public  static final int EMPTY        = 2;
	public static int TRIES = 0;

	public  static final int HUMAN_WIN    = 0;
	public  static final int DRAW         = 1;
	public  static final int UNCLEAR      = 2;
	public  static final int COMPUTER_WIN = 3;

	private int [ ] [ ] board = new int[ 3 ][ 3 ];
    //private Random random=new Random();  
	private int side;  
	private int position=UNCLEAR;
	private char computerChar,humanChar;

	// Constructor
	public TicTacToe( )
	{
		clearBoard( );
		initSide();
	}
	
	public void setSide(String sideframwork){
		if (sideframwork.contains("ai")){
			this.side = COMPUTER;
		}
		else if (sideframwork.contains("speler")){
			this.side = HUMAN;
		}
		
		
	}
	
	private void initSide()
	{
	    if (this.side==COMPUTER) { 
	    	computerChar='X'; 
	    	humanChar='0'; 
	    }
		else { 
			computerChar='0';
			humanChar='X';
		}
    }
    
    public void setComputerPlays()
    {
        this.side=COMPUTER;
        initSide();
    }
    
    public void setHumanPlays()
    {
        this.side=HUMAN;
        initSide();
    }

	public boolean computerPlays()
	{
	    return side==COMPUTER;
	}

	public int chooseMove()
	{
		
	    Best best=chooseMove(COMPUTER);
	    return best.row*3+best.column;
	    //return 0;
    }
    
    // Find optimal move
	private Best chooseMove( int side )
	{
		int opp = 0;              // The other side
		Best reply = null;           // Opponent's best reply
		int simpleEval;       // Result of an immediate evaluation
		int bestRow = 0;
		int bestColumn = 0;
		int value = 0;

		if( ( simpleEval = positionValue( ) ) != UNCLEAR )
			return new Best( simpleEval );

		// TODO: implementeren m.b.v. recursie/backtracking
	    if (side == HUMAN){
	    	opp = COMPUTER;
	    }
	    if (side == COMPUTER){
	    	opp = HUMAN;
	    }
	    
	    for (int row =0; row < board.length; row++){
	    	for(int column =0; column < board[row].length; column++){
	    		if (squareIsEmpty(row, column)) {
					boolean firstMove = (reply == null);

					place(row, column, side);
					reply = chooseMove(opp);
					place(row, column, EMPTY);

					if ((side == COMPUTER && reply.val > value)	|| (side == HUMAN && reply.val < value)	|| firstMove) {
						value = reply.val;
						bestRow = row;
						bestColumn = column;
					}
				}
			}
		}

		return new Best(value, bestRow, bestColumn);
    }

   
    //check if move ok
    public boolean moveOk(int move)
    {
 	return ( move>=0 && move <=8 && board[move/3 ][ move%3 ] == EMPTY );
 	//return true;
    }
    
    // play move
    public void playMove(int move)
    {
		board[move/3][ move%3] = this.side;
		if (side==COMPUTER) this.side=HUMAN;  else this.side=COMPUTER;
		
		
		//if (move == 0){
		//	board[0][0] = 0;
		//}
		
	}


	// Simple supporting routines
	void clearBoard( )
	{
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				board[row][col] = EMPTY;
			}
		}
	}


	private boolean boardIsFull( )
	{
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if(board[row][col] == EMPTY){
					return false;
				}
			}
		}
		return true;
	}

	// Returns whether 'side' has won in this position
	private boolean isAWin( int side )
	{
	    //TODO:
		TRIES++;
		for (int i = 0; i < board.length; i++){
			if(board[i][0] == side && board[i][1] == side && board[i][2] == side){
				return true;
			}
			if(board[0][i] == side && board[1][i] == side && board[2][i] == side){
				return true;
			}
			if(board[0][0] == side && board[1][1] == side && board[2][2] == side){
				return true;
			}
			if(board[0][2] == side && board[1][1] == side && board[2][0] == side){
				return true;
			}
			
		}
		return false;
	    
    }

	// Play a move, possibly clearing a square
	private void place( int row, int column, int piece )
	{
		board[ row ][ column ] = piece;
	}

	private boolean squareIsEmpty( int row, int column )
	{
		return board[ row ][ column ] == EMPTY;
	}

	// Compute static value of current position (win, draw, etc.)
	private int positionValue( )
	{
		if (isAWin(HUMAN)){
			return HUMAN_WIN;
		}
		if(isAWin(COMPUTER)){
			return COMPUTER_WIN;
		}
		if(boardIsFull()){
			return DRAW;
		}
		return UNCLEAR;
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
					sb.append(humanChar);
				} else if (board[row][col] == COMPUTER) {
					sb.append(computerChar);
				} else {
					sb.append('-');
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}  
	
	public boolean gameOver()
	{
		
		int result = positionValue();
		if(result != 2){
			this.position = (result);
			return true;
		}
	    //this.position=positionValue();
	    //return this.position!=UNCLEAR;
		if (boardIsFull()){
			return true;
		}
		else {
			return false;
		}
		
    }
    
    public String winner()
    {
        if      (this.position==COMPUTER_WIN) return "computer";
        else if (this.position==HUMAN_WIN   ) return "human";
        else                                  return "nobody";
    }
    
	
	private class Best
    {
       int row;
       int column;
       int val;

       public Best( int v )
         { this( v, 0, 0 ); }
      
       public Best( int v, int r, int c )
        { val = v; row = r; column = c; }
    } 
	
	
}

