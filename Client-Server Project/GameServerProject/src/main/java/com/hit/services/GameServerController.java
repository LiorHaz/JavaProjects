package com.hit.services;

import java.util.HashMap;

import com.hit.gameHandler.BoardGameHandler;

public class GameServerController {
	private GamesService gs;
	public GameServerController(int capacity) {
		gs=new GamesService(capacity);
	}
	
	public char[][] computerStartGame(java.lang.Integer gameId){
		return gs.computerStartGame(gameId);
	}
	
	public void	endGame(java.lang.Integer gameId) {
		gs.endGame(gameId);
	}

	public char[][]	getBoardState(java.lang.Integer gameId){
		return gs.getBoardState(gameId);
	}
	
	public Integer newGame(java.lang.String gameType, java.lang.String opponent) {
		return gs.newGame(gameType, opponent);
	}
	
	public com.hit.gameAlgo.IGameAlgo.GameState	updateMove(java.lang.Integer gameId, 
			   com.hit.gameAlgo.GameBoard.GameMove playerMove){
		return gs.updateMove(gameId, playerMove);
	}
	
	public HashMap<Integer,BoardGameHandler> getGameList(){
		return gs.getGameList();
	}
	/*
	public void restartGame(Integer gameId) {
		gs.restartGame(gameId);
	}
	*/
}
