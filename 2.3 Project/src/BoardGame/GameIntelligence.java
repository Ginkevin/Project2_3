package BoardGame;

import java.util.*;

public class GameIntelligence {
	public static final int HUMAN        = 0; 
	public static final int COMPUTER     = 1; 
	public static final int EMPTY        = 2;

	public static final int HUMAN_WIN    = 0;
	public static final int DRAW         = 1;
	public static final int UNCLEAR      = 2;
	public static final int COMPUTER_WIN = 3;
	
	public static int rows = 8;
	public static int columns = 8;
	public int[][] board = new int[8][8];
	
	public int side;
	
	private int position=UNCLEAR;
	protected boolean calculating = false;
	private String moveDetails;
	private HashMap<String, Integer> playerResults;
	private char computerChar,humanChar;
	
	final int MAX_DEPTH = 3;
	
	public GameIntelligence() {
		clearBoard();
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
	
	private void clearBoard( )
	{
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				board[i][j] = EMPTY;
			}
		}
	}
	
	public int[] getAiMove(int[][] otherBoard)
	{
		board = otherBoard;
		Best tmp = chooseMove(HUMAN, 0);
		int[] moveChosen = new int[2];
		moveChosen[0] = tmp.row;
		moveChosen[1] = tmp.column;
		return moveChosen;
	}
	
	public Best chooseMove(int side, int depth) {
		int opp;
		int bestMove = 0;
		int points = 0;
		int tmp = 0;
		int valid = 0;
		int max = 0;
		int test = 0;
		Stack<Integer> replaced = new Stack<Integer>();
		if (side == COMPUTER) {
			opp = HUMAN;
		} else { 
			opp = COMPUTER;
		}
		if (positionValue() == UNCLEAR && depth < MAX_DEPTH) {
			for (int i = 0; i < rows * columns; i++) {
				if (moveOk(i, side)) {
					valid = i;
					replaced = willBeReplaced(i, side);
					place(i/columns, i%columns, side);
					points = (getMovePoints(i, side, depth) * (MAX_DEPTH - depth)) - chooseMove(opp, depth + 1).val;
					tmp = replaced.pop();
					board[(int)Math.floor(tmp / columns)][tmp % columns] = 2;
					while (replaced.size() > 0) {
						tmp = replaced.pop();
						board[(int)Math.floor(tmp / columns)][tmp % columns] = opp;
					}
					if (points > max) {
						max = points;
						bestMove = i;
					}
					replaced = null;
					tmp = 0;
				}
				test++;
			}
		}
		if (depth == 0) {
			if (moveOk(bestMove, side)) {
				return new Best(0, bestMove/columns, bestMove%columns);
			} else {
				return new Best(0, valid/columns, valid%columns);
			}
		} else {
			return new Best(points, 0, 0);
		}
	}
	
	private boolean in_array(int value, Stack<Integer> array) {
		for (int i = 0; i < array.size(); ++i) {
			if (value == array.get(i)) {
				return true;
			}
		}
		return false;
	}
	
	private void place(int row, int column, int player) {
		int move = row * columns + column;
		Stack<Integer> fields = willBeReplaced(move,player);
		while (fields.size() > 0) {
			int field = fields.pop();
			board[field/columns][field%columns] = player;
		}
	}
	
	private boolean moveOk(int move, int side) {
		if (move==0) return false;
		if (board[(int)Math.floor(move/columns)][move%columns] == EMPTY && willBeReplaced(move, side).size() > 1) 
			return true;
		return false;
	}
	
	private int positionValue() {
		int own = 0;
		int opp = 0;
		for (int[] y : board) {
			for (int x : y) {
				if (x == side) {
					++own;
				} else if (x != EMPTY) {
					++opp;
				}
			}
		}
		if (opp + own < rows * columns) {
			if (opp == 0) {
				if (side == HUMAN) {
					return HUMAN_WIN;
				} else {
					return COMPUTER_WIN;
				}
			} else if (own == 0) {
				if (side == HUMAN) {
					return COMPUTER_WIN;
				} else {
					return HUMAN_WIN;
				}
			}
			return UNCLEAR;
		} else {
			if (opp > own) {
				if (side == HUMAN) {
					return COMPUTER_WIN;
				} else if (side == COMPUTER) {
					return HUMAN_WIN;
				} 
			} else if (opp == own) {
				return DRAW;
			} else if (own > opp) {
				if (side == HUMAN) {
					return HUMAN_WIN;
				} else if (side == COMPUTER) {
					return COMPUTER_WIN;
				} 
			}
		}
		return UNCLEAR;
	}
    
	private int getMovePoints(int move, int side, int depth) {
		Stack<Integer> wbr = willBeReplaced(move, side);
		if (in_array(0, wbr) || in_array(columns - 1, wbr) || in_array((rows - 1) * columns, wbr) || in_array(rows * columns - 1, wbr)) {
			return (10000 * (MAX_DEPTH - depth)) + wbr.size();
		}
		if (in_array(rows * columns - 2, wbr) || in_array((rows - 1) * (columns) - 1, wbr) || in_array((rows - 1) * (columns) - 2, wbr)) {
			if (board[rows-1][columns-1] != side) {
				return wbr.size() - 100;
			} else {
				return 100 + wbr.size();
			}
		}
		if (in_array(columns - 2, wbr) || in_array((columns * 2 - 2), wbr) || in_array((columns * 2 - 1), wbr)) {
			if (board[0][columns-1] != side) {
				return wbr.size() - 100;
			} else {
				return 100 + wbr.size();
			}
		}
		if (in_array(rows * (columns - 1) + 1, wbr) || in_array((rows) * (columns - 2), wbr) || in_array((rows) * (columns - 2) + 1, wbr)) {
			if (board[rows-1][0] != side) {
				return wbr.size() - 100;
			} else {
				return 100 + wbr.size();
			}
		}
		if (in_array(1, wbr) || in_array(columns, wbr) || in_array(columns + 1, wbr)) {
			if (board[0][0] != side) {
				return wbr.size() - 100;
			} else {
				return 100 + wbr.size();
			}
		}
		return wbr.size();
	}
    
	private Stack<Integer> willBeReplaced(int move, int player) {
		Stack<Integer> willBeReplaced = new Stack<Integer>();
		Stack<Integer> temp = new Stack<Integer>();
		int opp;
		if (player == HUMAN)
			opp = COMPUTER;
		else
			opp = HUMAN;
		int moveRow = (int)Math.floor(move / columns);
		int moveColumn = move%columns;
		for (int y = -1; y <= 1; ++y) {
			for (int x = -1; x <= 1; ++x) {
				int trow = moveRow + y;
				int tcolumn = moveColumn + x;
				if (trow >= 0 && tcolumn >= 0 && trow < rows && tcolumn < columns && board[trow][tcolumn] == opp) {
					while (trow < rows && tcolumn < columns && trow >= 0 && tcolumn >= 0) {
						if (trow * columns + tcolumn > 0 && trow < rows && tcolumn < columns && board[trow][tcolumn] == opp) {
							temp.push(trow * columns + tcolumn);
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

    public String toString() {
		String ret = "";
		for (int[] x : board) {
			for (int y : x) {
				if(y == HUMAN) {
					ret += side;
				} else if (y == COMPUTER) {
					ret += side == 0 ? 1 : 0;
				} else {
					ret += ".";
				}
			}
		}
		return ret;
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
