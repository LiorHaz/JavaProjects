package com.hit.gameHandler;

import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;

public class BoardGameHandler {
	private com.hit.gameAlgo.IGameAlgo game;
	public BoardGameHandler(IGameAlgo game) {
		this.game=game;
	}
	/**
	 The computer was chosen to play first, calculate its move and update the game board
	 */
	public char[][] computerStartGame(){
		game.calcComputerMove();
		return game.getBoardState();
	}
	/**
	 * the computer will be the first to start its move
	 * @return game board state after computer's move
	 */
	public char[][] getBoardState(){
		return game.getBoardState();
	}
	/**
	 Execute one round of a game steps: 
	 1. update the player's move or
     2.if game is not over: calculate and update the computer's move
     @return game state after the move
	*/
	public IGameAlgo.GameState playOneRound(GameBoard.GameMove playerMove){
		if(!game.updatePlayerMove(playerMove))
			return IGameAlgo.GameState.ILLEGAL_PLAYER_MOVE;
		if(game.getGameState(playerMove)==IGameAlgo.GameState.IN_PROGRESS)
			game.calcComputerMove();
		return game.getGameState(playerMove);
	}
}
