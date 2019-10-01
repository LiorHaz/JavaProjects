package com.hit.view;

import java.awt.EventQueue;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GamesView implements View,ActionListener{

	private JFrame frame;
	private PropertyChangeSupport pcs;
	private JButton[][] buttons;
	private GridLayout grid;
	private JLayeredPane layeredPane;
	private JRadioButton rdbtnTicTacToe,rdbtnCatchTheBunny,rdbtnRandom,rdbtnSmart;
	private JButton btnNewGame, btnStopGame,btnStartGame,btnMoveUp,btnMoveRight,btnMoveLeft,btnMoveDown; /*Controls game movement and start/new/stop game*/
	@SuppressWarnings("unused")
	private boolean isStartGameClicked,catchTheBunny,isFirstGame; /*Controls Start Game Button and game type*/
	private int kidRow,kidColumn,size;/*Controls kid's movement*/


	/**
	 * Create the application.
	 */
	public GamesView() {
		isStartGameClicked=false;
		isFirstGame=true;
		pcs=new PropertyChangeSupport(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//Tic Tac Tow Radio Button
		rdbtnTicTacToe = new JRadioButton("Tic Tac Tow");
		rdbtnTicTacToe.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnTicTacToe.setBounds(26, 32, 109, 23);
		frame.getContentPane().add(rdbtnTicTacToe);
		//Catch The bunny Radio Button
		rdbtnCatchTheBunny = new JRadioButton("Catch The Bunny");
		rdbtnCatchTheBunny.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnCatchTheBunny.setBounds(26, 61, 119, 23);
		frame.getContentPane().add(rdbtnCatchTheBunny);
		//Grouping the two radio buttons
		ButtonGroup gameGroup=new ButtonGroup();
		gameGroup.add(rdbtnTicTacToe);
		gameGroup.add(rdbtnCatchTheBunny);
		//Choose game label 
		JLabel lblChooseGame = new JLabel("Choose a game ");
		lblChooseGame.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblChooseGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseGame.setBounds(16, 5, 98, 25);
		frame.getContentPane().add(lblChooseGame);
		//Choose opponent label
		JLabel lblChooseAnOpponent = new JLabel("Choose an opponent ");
		lblChooseAnOpponent.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblChooseAnOpponent.setBounds(140, 10, 128, 14);
		frame.getContentPane().add(lblChooseAnOpponent);
		//Random Radio Button
		rdbtnRandom = new JRadioButton("Random");
		rdbtnRandom.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnRandom.setBounds(173, 32, 89, 23);
		frame.getContentPane().add(rdbtnRandom);
		//Smart Radio Button
		rdbtnSmart = new JRadioButton("Smart");
		rdbtnSmart.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnSmart.setBounds(173, 61, 89, 23);
		frame.getContentPane().add(rdbtnSmart);
		//Grouping the two radio buttons
		ButtonGroup opponentGroup=new ButtonGroup();
		opponentGroup.add(rdbtnRandom);
		opponentGroup.add(rdbtnSmart);
		//Action listener for enabling New Game Button when one radio button from each radio button group is selected
		ActionListener enableNewGameButton = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if(rdbtnSmart.isSelected()||rdbtnRandom.isSelected()) {
					if(rdbtnCatchTheBunny.isSelected()||rdbtnTicTacToe.isSelected())
						btnNewGame.setEnabled(true);  
				}	
			}
		};
		//Adding action listener for each radio button
		rdbtnCatchTheBunny.addActionListener(enableNewGameButton);
		rdbtnTicTacToe.addActionListener(enableNewGameButton);
		rdbtnRandom.addActionListener(enableNewGameButton);
		rdbtnSmart.addActionListener(enableNewGameButton);
		//New Game button
		btnNewGame = new JButton("New Game");
		btnNewGame.setActionCommand("newGame");
		btnNewGame.addActionListener(this);
		btnNewGame.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewGame.setBounds(267, 14, 98, 23);
		frame.getContentPane().add(btnNewGame);
		btnNewGame.setEnabled(false);
		//Stop Game button
		btnStopGame = new JButton("Stop Game");
		btnStopGame.setActionCommand("stopGame");
		btnStopGame.addActionListener(this);
		btnStopGame.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStopGame.setBounds(267, 39, 98, 23);
		frame.getContentPane().add(btnStopGame);
		btnStopGame.setEnabled(false);
		//Start Game Button
		btnStartGame = new JButton("Start Game");
		btnStartGame.setActionCommand("startGame");
		btnStartGame.addActionListener(this);
		btnStartGame.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStartGame.setBounds(267, 64, 99, 23);
		frame.getContentPane().add(btnStartGame);
		btnStartGame.setEnabled(false);
		//Move Up button
		btnMoveUp = new JButton("\u25B2");
		btnMoveUp.setBounds(399, 24, 53, 23);
		frame.getContentPane().add(btnMoveUp);
		btnMoveUp.setActionCommand("moveUp");
		btnMoveUp.addActionListener(this);
		btnMoveUp.setEnabled(false);
		//Move Right button
		btnMoveRight = new JButton("\u25BA");
		btnMoveRight.setBounds(428, 48, 53, 23);
		frame.getContentPane().add(btnMoveRight);
		btnMoveRight.setActionCommand("moveRight");
		btnMoveRight.addActionListener(this);
		btnMoveRight.setEnabled(false);
		//Move Left button
		btnMoveLeft = new JButton("\u25C4");
		btnMoveLeft.setBounds(370, 48, 52, 23);
		frame.getContentPane().add(btnMoveLeft);
		btnMoveLeft.setActionCommand("moveLeft");
		btnMoveLeft.addActionListener(this);
		btnMoveLeft.setEnabled(false);
		//Move Down button
		btnMoveDown = new JButton("\u25BC");
		btnMoveDown.setBounds(399, 72, 53, 23);
		frame.getContentPane().add(btnMoveDown);
		btnMoveDown.setActionCommand("moveDown");
		btnMoveDown.addActionListener(this);
		btnMoveDown.setEnabled(false);
		//Adding Layered Pane to the frame
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 115, 484, 447);
		frame.getContentPane().add(layeredPane);
		//Help Button
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame,"For Catch The Bunny - use the arrow buttons for playing\nFor Tic Tac Tow - click on the button u want on the game board");
			}
		});
		btnHelp.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnHelp.setBounds(267, 88, 99, 23);
		frame.getContentPane().add(btnHelp);
	}

	/**
	 * Define Event Listener for new game/start game/stop game/player's movements buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Player starts first case
		if(e.getActionCommand().equals("moveUp")||e.getActionCommand().equals("moveRight")
				||e.getActionCommand().equals("moveLeft")||e.getActionCommand().equals("moveDown"))
			btnStartGame.setEnabled(false);
		switch(e.getActionCommand()) {
		//Starting a new game
		case "newGame":{
			if(!optionPane("Start a new game?"))
				return;
			//removing the last game which was played from the game list
			if(!isFirstGame)
				pcs.firePropertyChange("stop game", null, null);
			isFirstGame=false;
			catchTheBunny=rdbtnCatchTheBunny.isSelected()? true : false;
			String gameType=rdbtnCatchTheBunny.isSelected()?rdbtnCatchTheBunny.getText() : rdbtnTicTacToe.getText();
			String opponentType=rdbtnRandom.isSelected()? rdbtnRandom.getText() : rdbtnSmart.getText();
			pcs.firePropertyChange("new game", gameType, opponentType);	
			btnStartGame.setEnabled(true);
			btnStopGame.setEnabled(true);
			if(catchTheBunny) {
				btnMoveUp.setEnabled(true);
				btnMoveRight.setEnabled(true);
				btnMoveLeft.setEnabled(true);
				btnMoveDown.setEnabled(true);
			}
			break;
		}
		//Computer plays first
		case "startGame":{
			if(!optionPane("Do you want the computer to make the first move?"))
				return;
			isStartGameClicked=true;
			btnStartGame.setEnabled(false);
			pcs.firePropertyChange("start game",null ,null );	
			break;
		}
		//Stops the game
		case "stopGame":{
			if(!optionPane("Do you want to stop the game?"))
				return;
			btnStartGame.setEnabled(false);
			btnStopGame.setEnabled(false);
			btnMoveUp.setEnabled(false);
			btnMoveRight.setEnabled(false);
			btnMoveLeft.setEnabled(false);
			btnMoveDown.setEnabled(false);
			isStartGameClicked=false;
			pcs.firePropertyChange("stop game",null ,null );
			deleteBoard();
			break;
		}
		//Move up
		case "moveUp":{
			// equal row and column case
			if(kidRow-1==kidColumn) {
				pcs.firePropertyChange("player move",null,(kidRow-1)+" "+kidColumn);
				break;
			}
			pcs.firePropertyChange("player move",kidRow-1 ,kidColumn );
			break;
		}
		//Move right
		case "moveRight":{
			// equal row and column case
			if(kidRow==kidColumn+1) {
				pcs.firePropertyChange("player move",null,(kidRow)+" "+(kidColumn+1));
				break;
			}
			pcs.firePropertyChange("player move",kidRow ,kidColumn+1 );
			break;
		}
		//Move left
		case "moveLeft":{
			// equal row and column case
			if(kidRow==kidColumn-1) {
				pcs.firePropertyChange("player move",null,(kidRow)+" "+(kidColumn-1));
				break;
			}
			pcs.firePropertyChange("player move",kidRow ,kidColumn-1 );
			break;
		}
		//Move down
		case "moveDown":{
			// equal row and column case
			if(kidRow+1==kidColumn) {
				pcs.firePropertyChange("player move",null,(kidRow+1)+" "+(kidColumn));
				break;
			}
			pcs.firePropertyChange("player move",kidRow+1 ,kidColumn);
			break;
		}
		case "TicTacTowMove":
		{
			btnStartGame.setEnabled(false);
			JButton b=(JButton)e.getSource();
			getButtonPosition(b);
			if(kidRow==kidColumn)
				pcs.firePropertyChange("player move",null ,kidRow+" "+kidColumn);
			else
				pcs.firePropertyChange("player move",kidRow ,kidColumn);

			break;
		}
		}
	}

	/**
	 * Launch the application.
	 */
	@Override
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {	
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * add property change listener
	 * @param pcl - property change listener to add
	 */
	public void	addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}


	/**
	 * Updates the game board with the last move
	 * @param gameState - the state of the game after the last round
	 * @param board - the board state after the last round
	 */
	@Override
	public void updateViewGameMove(long gameState, Character[][] board) {
		switch((int)gameState) {
		//Illegal Move
		case 0:{
			JOptionPane.showMessageDialog(frame,"Illegal Player Move!");
			return;
		}
		//Game In Progress
		case 1:{
			if(catchTheBunny)
				updateCatchTheBunnyBoard(board);
			else
				updateTicTacTowBoard(board);
			break;
		}
		//Player Won
		case 2:{
			//delete the last move of the computer to see the winning position only
			if(catchTheBunny) {
				for(int i=0;i<size;i++)
					for(int j=0;j<size;j++)
						if(buttons[i][j].getText().equals("B"))
							buttons[i][j].setText("X");
						else if(buttons[i][j].getText().equals("K"))
							buttons[i][j].setText("");
			}
			else
				buttons[kidRow][kidColumn].setText("X");
			JOptionPane.showMessageDialog(frame,"You Won!");
			resetBoard();
			break;
		}
		//Player Lost
		case 3:{
			if(!catchTheBunny) {
				buttons[kidRow][kidColumn].setText("X");
				updateTicTacTowBoard(board);
			}
			String msg;
			if(catchTheBunny)
				msg="Out of Steps - You Lost!";
			else
				msg="You Lost!";
			JOptionPane.showMessageDialog(frame,msg);
			resetBoard();
			break;
		}
		//Tie
		case 4:{
			if(!catchTheBunny)
				buttons[kidRow][kidColumn].setText("X");
			JOptionPane.showMessageDialog(frame,"Tie!");
			resetBoard();
			break;
		}
		}
	}

	/**
	 * Update the view when a new-game response message arrives
	 *@param board - the initial game board 
	 */
	@Override
	public void updateViewNewGame(Character[][] board) {
		if(!isFirstGame)
			deleteBoard();
		size = rdbtnCatchTheBunny.isSelected()? 9 : 3;
		setGameBoard(board);
	}

	/**
	 * Update the view when a start-game response message arrives
	 *@param board - the initial game board 
	 */
	public void updateViewStartGame(Character[][] board) {
		if(catchTheBunny)
			updateCatchTheBunnyBoard(board);
		else
			updateTicTacTowBoard(board);
	}

	/**
	 * Delete board and its components
	 */
	public void updateViewStopGame() {
		deleteBoard();
	}

	/**
	 * set the Grid Layout and the board for the chosen game 
	 */
	private void setGameBoard(Character[][] board) {
		grid=new GridLayout(size, size);
		buttons= new JButton[size][size];
		layeredPane.setLayout(grid);
		if(catchTheBunny) 
			setCatchTheBunnyBoard(board);
		else
			setTicTacTowBoard(board);

	}

	/**
	 * Sets board for Catch The Bunny game
	 * @param board - game board
	 */
	private void setCatchTheBunnyBoard(Character[][] board) {
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++) {
				buttons[i][j]=new JButton("");
				layeredPane.add(buttons[i][j]);
				buttons[i][j].setFont(new Font("Tahoma", Font.BOLD, 25));
				if(board[i][j]=='P') {
					buttons[i][j].setText("K");
					kidRow=i;
					kidColumn=j;
				}
				else if(board[i][j]=='C')
					buttons[i][j].setText("B");	
			}		
	}

	/**
	 * Sets board for Tic Tac Tow game
	 * @param board - game board
	 */
	private void setTicTacTowBoard(Character[][] board) {
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++) {
				buttons[i][j]=new JButton("");
				layeredPane.add(buttons[i][j]);
				buttons[i][j].setFont(new Font("Tahoma", Font.BOLD, 40));
				buttons[i][j].setText("");
				buttons[i][j].setActionCommand("TicTacTowMove");
				buttons[i][j].addActionListener(this);
			}		
	}

	/**
	 * Updating the game board after game move
	 * @param board
	 */
	private void updateCatchTheBunnyBoard(Character[][] board) {
		buttons[kidRow][kidColumn].setText("");
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++) {
				if(buttons[i][j].getText().equals("B"))
					buttons[i][j].setText("");

				if(board[i][j]=='P') {
					buttons[i][j].setText("K");
					kidRow=i;
					kidColumn=j;
				}
				else if(board[i][j]=='C')
					buttons[i][j].setText("B");	
			}
	}


	/**
	 * Updating the game board after game move
	 * @param board
	 */
	private void updateTicTacTowBoard(Character[][] board) {
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++) {
				if(board[i][j]=='P')
					buttons[i][j].setText("X");
				else if(board[i][j]=='C')
					buttons[i][j].setText("O");
			}

	}

	/**
	 * Delete Board
	 */
	private void deleteBoard() {
		if(buttons==null)return;
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				layeredPane.remove(buttons[i][j]);	
		grid=null;
		buttons=null;
		layeredPane.removeAll();
	}
	/**
	 * Reset board after game ends
	 */
	private void resetBoard() {
		btnStartGame.setEnabled(false);
		btnStopGame.setEnabled(false);
		btnMoveUp.setEnabled(false);
		btnMoveRight.setEnabled(false);
		btnMoveLeft.setEnabled(false);
		btnMoveDown.setEnabled(false);
		isStartGameClicked=false;
		deleteBoard();
	}
	/**
	 * Option Pane window before executing the command
	 *@param msg- The warning message 
	 */
	private boolean optionPane(String msg) {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog (null, msg,"Warning",dialogButton);
		if(dialogResult == JOptionPane.YES_OPTION){
			return true;
		}
		return false;
	}
	/**
	 * Searching for the button which was clicked and getting its position
	 */
	private void getButtonPosition(JButton b) {
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++) {
				if(buttons[i][j].equals(b)) {
					kidRow=i;
					kidColumn=j;
				}
			}	
	}
}
