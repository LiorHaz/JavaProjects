package com.hit.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GamesModel implements Model {

	private PropertyChangeSupport pcs;
	private GamesClient gc;
	private JSONObject request,response;
	private JSONParser parser;
	private JSONArray board;
	private long gameID;
	boolean catchTheBunny;
 	public GamesModel() {
		pcs=new PropertyChangeSupport(this);
		gc=new GamesClient(34567);
		parser=new JSONParser();
		request=response=null;
		board=null;
	}

	/**
	 * adding PropertyChangeListener
	 * @param pcl - the property change listener to add
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}

	/**
	 * Sends the server a "new-game" request
	 * @param gameType - the chosen game
	 * @param opponentType - computer type (random \ smart)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void newGame(String gameType, String opponentType) {
		catchTheBunny=gameType.equals("Catch The Bunny")?true:false;
		request=new JSONObject();
		request.put("type","New-Game");
		request.put("game",gameType);
		request.put("opponent",opponentType);
		sendAndGetJSONMessage("new game response");
	}

	/**
	 * Sends the server an "update-move" message
	 * @param row - the row of the move the player chose
	 * @param col - the column of the move the player chose
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updatePlayerMove(int row, int col) {
		request=new JSONObject();
		request.put("type","Update-Move");
		request.put("ID",gameID);
		request.put("row",row);
		request.put("col",col);
		sendAndGetJSONMessage("player move response");
	}

	/**
	 *starting the game by letting the computer the first move 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void startGame() {
		request=new JSONObject();
		request.put("type","Start-Game");
		request.put("ID",gameID);
		sendAndGetJSONMessage("start game response");
	}

	/**
	 * stopping particular game by game id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void stopGame() {
		request=new JSONObject();
		request.put("type","Stop-Game");
		request.put("ID",gameID);
		gc.connectToServer();
		gc.sendMessage(request.toJSONString(), false);
		gc.closeConnection();
	}

	/**
	 * sends JSON request to the server, gets JSON response from the server and sends the response back to the controller
	 * @param msg -message type to the controller 
	 */
	private void sendAndGetJSONMessage(String msg) {
		gc.connectToServer();
		try {
			//sends the JSON request and get the JSON response
			String jsonString=gc.sendMessage(request.toJSONString(), true);
			response=(JSONObject)parser.parse(jsonString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//check number of games played simultaneously validation when applying for new game
		if(response.containsKey("message")) {
			System.out.println((String)response.get("message"));
			return;
		}
		//sets game id for the new game
		if(response.containsValue("New-Game"))
			gameID=(Long)response.get("ID");
		board=(JSONArray)response.get("board");
		//sends the response to the controller
		if(((String)response.get("type")).equals("Update-Move"))
			pcs.firePropertyChange(msg, (long)response.get("state"),readBoard());
		else
			pcs.firePropertyChange(msg, null,readBoard());
		gc.closeConnection();
	}

	/**
	 * casting the board game from JSONArray to Character matrix
	 * @return board game
	 */
	private Character[][] readBoard() {
		if(board==null||board.size()==0) return null;
		int k=0;
		String s;
		Character[][] c=catchTheBunny?new Character[9][9]:new Character[3][3];
		for(int i=0;i<c.length;i++)
			for(int j=0;j<c[0].length;j++) {
				 s=(String)board.get(k);
				c[i][j]=s.charAt(0);
				k++;
			}
		return c;
	}
}
