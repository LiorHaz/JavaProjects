package com.hit.gameAlgo;

import com.hit.games.CatchTheBunny;

public abstract class GameBoard implements IGameAlgo {
	protected char[][] board;
	protected int row,col;
	//Creates the board and initialize it to BLANK
	public GameBoard(int rowLength,int colLength) {
		this.row=rowLength;
		this.col=colLength;
		board=new char[row][col];
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
				board[i][j]=CatchTheBunny.BoardSigns.BLANK.getSign();
	}
	@Override
	public abstract void calcComputerMove();
	@Override
	public abstract char[][] getBoardState();
	@Override 
	public abstract IGameAlgo.GameState	getGameState(GameBoard.GameMove move);
	@Override
	public abstract boolean	updatePlayerMove(GameBoard.GameMove move);
	//Position of the Player and the Bunny
	public static class GameMove{
		private int row;
		private int column;
		public GameMove(int row,int column) {
			this.row=row;
			this.column=column;
		}
		public int getRow() {
			return this.row;
		}
		public int getColumn() {
			return this.column;
		}
		public void setRow(int r) {
			this.row=r;
		}
		public void setColumn(int c) {
			this.column=c;
		}
	}
	//Display the board in the console	
	public void display() {
		for(int i=0;i<row;i++) {
			for(int j=0;j<col;j++)
				System.out.print(board[i][j]+" ");
			System.out.print('\n');
		}
	}
}
