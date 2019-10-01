package com.hit.ticTacTow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.games.TicTacTow;
import com.hit.games.TicTacTowRandom;


class TicTacTowTest {

	private GameBoard.GameMove player,computer;
	private TicTacTow game;


	public TicTacTowTest() {
		player=new GameBoard.GameMove(0,0);
		computer= new GameBoard.GameMove(0, 0);
		game=new TicTacTowRandom(3,3);		
	}
	//Every move Test has two checks:
	//1.Makes sure player moves successfully
	//2.Makes sure player can't move due to index out of board bounds 
	@Test
	void testPlayerMove() {
		player.setColumn(0);
		player.setRow(0);
		assertTrue(game.updatePlayerMove(player));
		player.setColumn(-1);
		assertFalse(game.updatePlayerMove(player));
	}
	@Test
	void testPlayerMove1() {
		player.setColumn(2);
		assertTrue(game.updatePlayerMove(player));
		player.setColumn(3);
		assertFalse(game.updatePlayerMove(player));
	}
	@Test
	void testPlayerMove2() {
		player.setRow(0);
		assertTrue(game.updatePlayerMove(player));
		player.setRow(-1);
		assertFalse(game.updatePlayerMove(player));
	}
	@Test
	void testPlayerMove3() {
		player.setRow(2);
		assertTrue(game.updatePlayerMove(player));
		player.setRow(3);
		assertFalse(game.updatePlayerMove(player));
	}
	//Makes sure player is winning
	@Test
	void testWinning() {
		computer.setRow(0);
		computer.setColumn(0);
		game.updatePlayerMove(computer);
		player.setRow(1);
		player.setColumn(0);
		game.updatePlayerMove(player);
		computer.setRow(0);
		computer.setColumn(1);
		game.updatePlayerMove(computer);
		player.setRow(1);
		player.setColumn(1);
		game.updatePlayerMove(player);
		computer.setRow(2);
		computer.setColumn(2);
		game.updatePlayerMove(computer);
		player.setRow(1);
		player.setColumn(2);
		game.updatePlayerMove(player);

		assertEquals(IGameAlgo.GameState.PLAYER_WON,game.getGameState(player));

	}
	//Makes sure player is losing
	@Test
	void testLosing() {	
		game.calcComputerMove();
		game.calcComputerMove();
		game.calcComputerMove();
		game.calcComputerMove();
		game.calcComputerMove();
		game.calcComputerMove();
		assertEquals(IGameAlgo.GameState.PLAYER_LOST,game.getGameState(player));
	}

}
