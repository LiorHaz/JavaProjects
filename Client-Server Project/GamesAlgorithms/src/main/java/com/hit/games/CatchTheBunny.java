package com.hit.games;

import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;

import java.util.Random;
public abstract class CatchTheBunny extends GameBoard{
	//Number of steps for the player
	protected int steps;
	//Last position of the bunny and the player
	protected GameBoard.GameMove player,bunny;
	public CatchTheBunny(int rowLength,int colLength) {
		super(rowLength,colLength);
		this.steps=new Random().nextInt(27)+8;
		setPlayerAndBunnyRandomly();
	}
	@Override
	public abstract void calcComputerMove();
	//Return board current 
	@Override
	public char[][]	getBoardState(){
		return board;
	}

	@Override
	public IGameAlgo.GameState getGameState(GameBoard.GameMove move){
		//Checking if the player lost
		if(steps==0){
			System.out.println("Player lost!");
			return IGameAlgo.GameState.PLAYER_LOST;
			}
		//Checking if the player won
		else if (samePosition()) {
			board[player.getRow()][player.getColumn()]='X';
			System.out.println("Player won!");
			return IGameAlgo.GameState.PLAYER_WON;
		}
		//Keep playing
		else 
			return IGameAlgo.GameState.IN_PROGRESS;
		
	}
	
	@Override
	public boolean	updatePlayerMove(GameBoard.GameMove move) {
		//Check validation of index which is out of bounds
		if(move.getColumn()<0||move.getColumn()>col-1||move.getRow()<0||move.getRow()>row-1) {
			System.out.println(IGameAlgo.GameState.ILLEGAL_PLAYER_MOVE);
			return false;
		}
		//Update the the player position and number of steps
		board[player.getRow()][player.getColumn()]=BoardSigns.BLANK.getSign();
		steps--;
		System.out.println("Remaining steps: "+steps);
		player.setRow(move.getRow());
		player.setColumn(move.getColumn());
		board[player.getRow()][player.getColumn()]=BoardSigns.PLAYER.getSign();
		return true;
	}
	//check if the player and the bunny are in the same position
	public boolean samePosition() {
		return (player.getColumn()==bunny.getColumn())&&(player.getRow()==bunny.getRow());
	}
	public static enum BoardSigns{
		BLANK,
		COMPUTER,
		PLAYER;
		private char sign;
		static {
			BLANK.sign='B';
			COMPUTER.sign='C';
			PLAYER.sign='P';
		}
		public char getSign() {
			return sign;
		}
	}
	//Locate the player and the bunny randomly and set the board at the beginning
	private void setPlayerAndBunnyRandomly() {
		player=new GameBoard.GameMove(new Random().nextInt(9),new Random().nextInt(9));
		bunny=new GameBoard.GameMove(0,0);
		do {
			
			bunny.setColumn(new Random().nextInt(9));
			bunny.setRow(new Random().nextInt(9));
		}//Handle same position at the beginning
		while(samePosition());
		board[bunny.getRow()][bunny.getColumn()]=BoardSigns.COMPUTER.getSign();
		board[player.getRow()][player.getColumn()]=BoardSigns.PLAYER.getSign();
	}
	//Get player position
	public GameBoard.GameMove getPlayer() {
		return player;
	}
	//Get bunny position
	public GameBoard.GameMove getBunny() {
		return bunny;
	}
	//Setters for Testing only
	public void setPlayer(GameBoard.GameMove player) {
		this.player = player;
	}
	public void setBunny(GameBoard.GameMove bunny) {
		this.bunny = bunny;
	}	
}	
