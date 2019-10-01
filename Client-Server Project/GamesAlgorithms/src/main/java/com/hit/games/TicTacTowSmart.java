package com.hit.games;
import java.util.Random;
import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;

public class TicTacTowSmart extends TicTacTow {
	private GameMove comMove=new GameMove(0,0);
	private Random rand1 =new Random();
	private Random rand2 =new Random();
	public TicTacTowSmart(int rowLength, int colLength)
	{
		super(rowLength, colLength);
	}
	
	
	public boolean updatePlayerMove(GameBoard.GameMove move) 
	{
		
		{
			boolean validmove=true;
			 if (move.getRow() < 0 || move.getColumn() < 0 || move.getRow() > 2 || move.getColumn() > 2)
		        {
				 System.out.println(IGameAlgo.GameState.ILLEGAL_PLAYER_MOVE);
		            validmove = false;
		        }
		        else if (!(board[move.getRow()][move.getColumn()] == BoardSigns.BLANK.getSign()))
		        {
		        	System.out.println(IGameAlgo.GameState.ILLEGAL_PLAYER_MOVE);
		            validmove = false;
		        }
		        else
		        {
		        	if(getGameState(comMove).name()=="IN_PROGRESS")
			 {board[move.getRow()][move.getColumn()]=BoardSigns.PLAYER.getSign();
			 turnNumber++;
			 }
			 if(getGameState(move).name()!="IN_PROGRESS")
				 System.out.println(getGameState(move));
		        }
		        	
		        	 
		        return validmove;
		}
	}


	@Override
	public void calcComputerMove() {
{
		if(turnNumber<8)	
			{
			int randomNum = rand1.nextInt(3);
			int randomNum2 = rand2.nextInt(3);
			comMove.setRow(randomNum);
			comMove.setColumn(randomNum2);
			while(board[comMove.getRow()][comMove.getColumn()]!=BoardSigns.BLANK.getSign())
			{
				int randomNum3 = rand1.nextInt(3);
				int randomNum4 = rand2.nextInt(3);
				comMove.setRow(randomNum3);
				comMove.setColumn(randomNum4);
			}
				
			if(getGameState(comMove).name()=="IN_PROGRESS")
					{board[comMove.getRow()][comMove.getColumn()]=BoardSigns.COMPUTER.getSign();
					turnNumber++;
					}
					if(getGameState(comMove).name()!="IN_PROGRESS")
						System.out.println(getGameState(comMove));
					return;
					
		
		}
		else
			
		{
		
		if(board[2][2]==board[1][1]&&board[0][0]==BoardSigns.BLANK.getSign())
			{
			if(getGameState(comMove).name()=="IN_PROGRESS")
			{board[0][0]=BoardSigns.COMPUTER.getSign();
			turnNumber++;
			}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
			}
		if(board[2][0]==board[1][0]&&board[0][0]==BoardSigns.BLANK.getSign())
		{
			if(getGameState(comMove).name()=="IN_PROGRESS") {
			board[0][0]=BoardSigns.COMPUTER.getSign();
			turnNumber++;
			}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[0][2]==board[0][1]&&board[0][0]==BoardSigns.BLANK.getSign())
		{
			if(getGameState(comMove).name()=="IN_PROGRESS") {
			board[0][0]=BoardSigns.COMPUTER.getSign();
			turnNumber++;
			}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[0][0]==board[0][2]&&board[0][1]==BoardSigns.BLANK.getSign())
		{
			if(getGameState(comMove).name()=="IN_PROGRESS") {
			board[0][1]=BoardSigns.COMPUTER.getSign();
			turnNumber++;
			}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[2][1]==board[1][1]&&board[0][1]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[0][1]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[2][1]==board[1][1]&&board[0][1]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[0][1]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[0][0]==board[0][1]&&board[0][2]==BoardSigns.BLANK.getSign())
		{
			if(getGameState(comMove).name()=="IN_PROGRESS") {
			board[0][2]=BoardSigns.COMPUTER.getSign();
			turnNumber++;
			}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[2][2]==board[1][2]&&board[0][2]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[0][2]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[2][0]==board[1][1]&&board[0][2]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[0][2]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[0][0]==board[2][0]&&board[1][0]==BoardSigns.BLANK.getSign())
		{
			if(getGameState(comMove).name()=="IN_PROGRESS") {
			board[1][0]=BoardSigns.COMPUTER.getSign();
			turnNumber++;
			}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[1][1]==board[1][2]&&board[1][0]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[1][0]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[0][2]==board[2][0]&&board[1][1]==BoardSigns.BLANK.getSign())
		{
			if(getGameState(comMove).name()=="IN_PROGRESS") {
			board[1][1]=BoardSigns.COMPUTER.getSign();
			turnNumber++;
			}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[0][1]==board[2][1]&&board[1][1]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[1][1]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[0][0]==board[2][2]&&board[1][1]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[1][1]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[1][0]==board[1][2]&&board[1][1]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[1][1]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[1][0]==board[1][1]&&board[1][2]==BoardSigns.BLANK.getSign())
		{
			if(getGameState(comMove).name()=="IN_PROGRESS") {
			board[1][2]=BoardSigns.COMPUTER.getSign();
			turnNumber++;
			}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[0][2]==board[2][2]&&board[1][2]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[1][2]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[2][1]==board[2][2]&&board[2][0]==BoardSigns.BLANK.getSign())
		{
			if(getGameState(comMove).name()=="IN_PROGRESS") {
			board[2][0]=BoardSigns.COMPUTER.getSign();
			turnNumber++;
			}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[1][1]==board[0][2]&&board[2][0]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[2][0]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[2][1]==board[2][2]&&board[2][0]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[2][0]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[2][0]==board[2][2]&&board[2][1]==BoardSigns.BLANK.getSign())
		{
			if(getGameState(comMove).name()=="IN_PROGRESS") {
			board[2][1]=BoardSigns.COMPUTER.getSign();
			turnNumber++;
			}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[1][1]==board[0][1]&&board[2][1]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[2][1]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[1][2]==board[0][2]&&board[2][2]==BoardSigns.BLANK.getSign())
		{
			if(getGameState(comMove).name()=="IN_PROGRESS") {
			board[2][2]=BoardSigns.COMPUTER.getSign();
			turnNumber++;
			}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[2][0]==board[2][1]&&board[2][2]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[2][2]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		if(board[0][0]==board[1][1]&&board[2][2]==BoardSigns.BLANK.getSign())
		{
		
			if(getGameState(comMove).name()=="IN_PROGRESS") {
				board[2][2]=BoardSigns.COMPUTER.getSign();
				turnNumber++;
				}
			if(getGameState(comMove).name()!="IN_PROGRESS")
				System.out.println(getGameState(comMove));
			return;
		}
		}
		
		
		
		
		
		
			
		
	}

}
}

