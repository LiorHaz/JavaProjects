package com.hit.services;

import java.util.HashMap;
import java.util.Random;
import com.hit.exception.UnknownIdException;
import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameHandler.BoardGameHandler;
import com.hit.games.CatchTheBunnyRandom;
import com.hit.games.CatchTheBunnySmart;
import com.hit.games.TicTacTowRandom;
import com.hit.games.TicTacTowSmart;

public class GamesService {

	private Integer capacity,capacityTotal;
	private HashMap<Integer,BoardGameHandler> gameList;
	private HashMap<Integer,String> gameTypeList;
	private BoardGameHandler game;
	/**
	capacity - the maximum number of games that could be played simultaneously 
	 */
	public GamesService(int capacity) {
		gameList=new HashMap<Integer,BoardGameHandler>();
		gameTypeList=new HashMap<Integer,String>();
		this.capacity=capacityTotal=capacity;
		game=null;
	}

	//get the game list
	public HashMap<Integer,BoardGameHandler> getGameList(){
		return gameList;
	}
	/**
	 * @param gameId - the id of the game that should be contacted
	 * @return a matrix that represents the game board after the computer's move
	 * @throws UnknownIdException - thrown in case the game id does not exist
	 */
	public char[][] computerStartGame(Integer gameId){
		if(!checkGameId(gameId))
			return null;
		game=gameList.get(gameId);
		return game.computerStartGame();
	}
	/**
	 * End a game even if it is not finished
	 * @param gameId - the id of the game that should be contacted
	 * @throws UnknownIdException - thrown in case the game id does not exist
	 */
	public void	endGame(Integer gameId) {
		if(!checkGameId(gameId)) return;
		gameList.remove(gameId);
		gameTypeList.remove(gameId);
		synchronized(capacity) {
			capacity++;	
		}
	}
	/** 
	 * @param gameId - the id of the game that should be contacted
	 * @return the game state returned by the corresponding game handler
	 * @throws UnknownIdException - thrown in case the game id does not exist
	 */
	public char[][]	getBoardState(Integer gameId) {
		if(!checkGameId(gameId)) return null;
		game=gameList.get(gameId);
		return game.getBoardState();
	}

	/**
	 * starting a new game by the following parameters:
	 @param gameType - game that was chosen (Tic Tac Tow or Catch the Bunny)
	 @param opponent - the desired opponent type (whether the computer should play smart or random)
	 */
	public Integer newGame(String gameType, String opponent) {
		if(capacity==0)
			return -1;
		//choose random number for id
		Integer gameId;
		do {
			gameId=new Integer(new Random().nextInt(capacityTotal+1)+1);				
		}
		while(gameList.containsKey(gameId));
		if(gameType.equals("Tic Tac Tow")) {
			switch(opponent) {
			case "Random":{
				gameList.put(gameId,new BoardGameHandler(new TicTacTowSmart(3, 3)));
				break;
			}
			case "Smart":{
				gameList.put(gameId,new BoardGameHandler(new TicTacTowRandom(3, 3)));
				break;
			}
			}
		}
		else if(gameType.equals("Catch The Bunny")) {
			switch(opponent) {
			case "Random":{
				gameList.put(gameId,new BoardGameHandler(new CatchTheBunnyRandom(9, 9)));
				break;
			}
			case "Smart":{
				gameList.put(gameId,new BoardGameHandler(new CatchTheBunnySmart(9, 9)));
				break;
			}
			}
		}
		gameTypeList.put(gameId,gameType+" "+opponent);
		synchronized(capacity) {
			capacity--;	
		}
		return gameId;
	}
	/**
	 * @param gameId - the game id
	 * @param playerMove - the move the player chose to make
	 * @return the game state: ILLEGAL_PLAYER_MOVE \ PLAYER_WON \ PLAYER_LOST \ TIE \ IN PROGRESS
	 * @throws UnknownIdException thrown in case the game id does not exist
	 */
	public IGameAlgo.GameState updateMove(Integer gameId,GameBoard.GameMove playerMove) {
		if(!checkGameId(gameId))return null;
		game=gameList.get(gameId);
		return game.playOneRound(playerMove);
	}
	/**
	 * Check if Game Id exists
	 * @param gameId - the game's id
	 * @throws UnknownIdException thrown in case the game id does not exist
	 */
	@SuppressWarnings("finally")
	private boolean checkGameId(Integer gameId) {
		boolean isExist=false;
		try {
			if(!gameList.containsKey(gameId))
				throw new UnknownIdException("Game id "+gameId+" does not exist",new Exception());
			else isExist=true;
		}
		catch(UnknownIdException e) {
			e.printStackTrace();
			
		}	
		finally {
			return isExist;
		}
	}
}
