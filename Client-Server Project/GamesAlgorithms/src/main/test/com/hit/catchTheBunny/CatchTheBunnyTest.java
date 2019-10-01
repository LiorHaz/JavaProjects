package com.hit.catchTheBunny;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.games.CatchTheBunny;
import com.hit.games.CatchTheBunnySmart;

class CatchTheBunnyTest {
	
	private GameBoard.GameMove player,computer;
	private CatchTheBunny game;
	public CatchTheBunnyTest() {
		player=new GameBoard.GameMove(1,1);
		computer=new GameBoard.GameMove(0,0);
		game=new CatchTheBunnySmart(9,9);
		game.setBunny(computer);
		game.setPlayer(player);
	}
	//Every move Test has two checks:
	//1.Makes sure player moves successfully
	//2.Makes sure player can't move due to index out of board bounds 
	@Test
	void testPlayerLeftMove() {
		player.setColumn(0);
		assertTrue(game.updatePlayerMove(player));
		player.setColumn(-1);
		assertFalse(game.updatePlayerMove(player));
	}
	@Test
	void testPlayerRightMove() {
		player.setColumn(8);
		assertTrue(game.updatePlayerMove(player));
		player.setColumn(9);
		assertFalse(game.updatePlayerMove(player));
	}
	@Test
	void testPlayerUpMove() {
		player.setRow(0);
		assertTrue(game.updatePlayerMove(player));
		player.setRow(-1);
		assertFalse(game.updatePlayerMove(player));
	}
	@Test
	void testPlayerDownMove() {
		player.setRow(8);
		assertTrue(game.updatePlayerMove(player));
		player.setRow(9);
		assertFalse(game.updatePlayerMove(player));
	}
	//Makes sure player is winning
	@Test
	void testWinning() {
		player.setRow(0);
		player.setColumn(0);
		computer.setRow(0);
		computer.setColumn(0);
		game.setBunny(computer);
		game.setPlayer(player);
		assertEquals(IGameAlgo.GameState.PLAYER_WON,game.getGameState(player));
		player.setRow(5);
		assertNotEquals(IGameAlgo.GameState.PLAYER_WON,game.getGameState(player));
	}
	//Makes sure player is losing
	@Test
	void testLosing() {
		player.setRow(0);
		player.setColumn(0);
		computer.setRow(1);
		computer.setColumn(7);
		game.setBunny(computer);
		//Steps No:2
		game.updatePlayerMove(player);//Steps No:1
		assertNotEquals(IGameAlgo.GameState.PLAYER_LOST,game.getGameState(player));
		player.setColumn(3);
		game.updatePlayerMove(player);//Steps No:0
		assertEquals(IGameAlgo.GameState.PLAYER_LOST,game.getGameState(player));
	}

}
