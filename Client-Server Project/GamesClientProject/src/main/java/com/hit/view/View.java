package com.hit.view;

public interface View {
	public void	start();
	public void updateViewGameMove(long gameState, Character[][] board);
	public void updateViewNewGame(Character[][] board);
	public void updateViewStartGame(Character[][] board);
}
