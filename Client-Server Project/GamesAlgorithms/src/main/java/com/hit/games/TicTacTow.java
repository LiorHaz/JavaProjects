package com.hit.games;
import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;

public abstract class TicTacTow extends GameBoard {
	int rowLength;
	int colLength;
	protected int turnNumber;
	public static enum BoardSigns {
		PLAYER('P'),
		COMPUTER('C'),
		BLANK('B');
		
		private char sign;
		private BoardSigns(char sign)
		{
			this.sign=sign;
		}
		
		public char getSign()
		{
			return sign;
		}
	}
	
	TicTacTow(int rowLength, int colLength)
	{
		super(rowLength,colLength);
		turnNumber=0;
		
	}
	
	public IGameAlgo.GameState getGameState(GameMove move)
	{
		
	        if (turnNumber>=5&&turnNumber<=9)
	        {
	            for (int i = 0; i < board.length; i++)
	            {
	                if (board[0][i] == BoardSigns.PLAYER.getSign()&& board[0][i] == board[1][i] && board[1][i] == board[2][i])
	                {
	                	return GameState.PLAYER_WON;
	                }
	                if (board[i][0] == BoardSigns.PLAYER.getSign() && board[i][0] == board[i][1] && board[i][1] == board[i][2])
	                {
	                	return GameState.PLAYER_WON;
	                }
	                if (board[0][i] == BoardSigns.COMPUTER.getSign() && board[0][i] == board[1][i] && board[1][i] == board[2][i])
	                {
	                	return GameState.PLAYER_LOST;
	                }
	                if (board[i][0] == BoardSigns.COMPUTER.getSign() && board[i][0] == board[i][1] && board[i][1] == board[i][2])
	                {
	                	return GameState.PLAYER_LOST;
	                }
	            }
	            if (board[1][1] == BoardSigns.PLAYER.getSign() && ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
	                                      (board[0][2] == board[1][1] && board[1][1] == board[2][0])))
	            {
	            	return GameState.PLAYER_WON;
	            }
	            if (board[1][1] == BoardSigns.COMPUTER.getSign() && ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
                        (board[0][2] == board[1][1] && board[1][1] == board[2][0])))
	            {
	            	return GameState.PLAYER_LOST;
	            }
	        }
	        
	        if(turnNumber<9)
	        	return GameState.IN_PROGRESS;
	       
	        			
	        return GameState.TIE;
	        	
	        		
	       
	       
	}
	       
	
	
	public char[][]	getBoardState(){
		return board;
	}
}


	
	 

	        
	

	

	
	        	
	

	            
	            
	     


