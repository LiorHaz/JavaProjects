package com.hit.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.services.GameServerController;

public class HandleRequest implements Runnable {

	private Socket client;
	private GameServerController controller;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public HandleRequest(Socket s, GameServerController controller) throws IOException {
		this.client=s;
		this.controller=controller;
		in=new ObjectInputStream(client.getInputStream());
		out=new ObjectOutputStream(client.getOutputStream());
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public void run() {
		//Handling JSON requests from client,manage the game and send back a JSON object as a response to the client
		JSONObject request=null,response=null;
		JSONArray boardArray=null;
		String type="",game="",opponent="";
		long row,col,state=-1;
		long id;
		char[][] boardGame=null;
		Boolean isGameStoppedByPlayer=new Boolean(false),isGameStopped=new Boolean(false);
		try {

			//gets JSON format request from the client
			try {
				request=(JSONObject)in.readObject();
			}
			//catching the EOFException to prevent bugs while the program is running and closing the sockets and the streams
			catch (EOFException exc) {
				try {
					in.close();
					out.close();
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			response=new JSONObject();
			boardArray=new JSONArray();
			type=(String)request.get("type");
			switch(type) {
			//new game- player starts	
			case "New-Game":{
				game=(String)request.get("game");
				boardGame=game.equals("Catch The Bunny")?new char[9][9]:new char[3][3];
				opponent=(String)request.get("opponent");
				id=(long)controller.newGame(game, opponent);
				//number of games reached to maximum
				if(id==-1) {
					String s="You have reached the maximum number of games the server can handle with";
					response.put("message",s);
					break;
				}
				boardGame=controller.getBoardState((int)id);
				castBoard(boardArray,boardGame);
				response.put("type",type);
				response.put("ID",id);
				response.put("board",boardArray);
				break;
			}
			//new game- computer starts
			case "Start-Game":{
				id=(long)request.get("ID");
				boardGame=controller.computerStartGame((int)id);
				castBoard(boardArray,boardGame);
				response.put("type",type);
				response.put("ID",id);
				response.put("board",boardArray);
				break;
			}
			//update move
			case "Update-Move":{
				id=(long)request.get("ID");
				row=(long)request.get("row");
				col=(long)request.get("col");
				IGameAlgo.GameState gs=controller.updateMove((int)id, new GameBoard.GameMove((int)row,(int)col));
				response.put("type",type);
				response.put("ID",id);
				//game id is not exist
				if(gs==null) {
					boardGame=null;
					response.replace("ID", id);
				}
				//illegal player move case
				else if(gs==IGameAlgo.GameState.ILLEGAL_PLAYER_MOVE) {
					state=0L;
					boardArray=null;
				}
				//game in progress state
				else if(gs==IGameAlgo.GameState.IN_PROGRESS) {
					state=1L;
					boardGame=controller.getBoardState((int)id);
					castBoard(boardArray,boardGame);
				}
				//end game state
				else {//player won\lost\tie states
					if(gs==IGameAlgo.GameState.PLAYER_WON)
						state=2L;
					else if(gs==IGameAlgo.GameState.PLAYER_LOST)
						state=3L;
					else
						state=4L;
					//stops the game at the next iteration
					type="Stop-Game";
					isGameStopped=true;
					synchronized(isGameStopped) {
						isGameStopped=true;
					}
					boardGame=controller.getBoardState((int)id);
					castBoard(boardArray,boardGame);
				}
				response.put("state", state);
				response.put("board",boardArray);
				break;
			}
			//stop game
			case "Stop-Game":{
				id=(long)request.get("ID");
				controller.endGame((int)id);
				synchronized(isGameStoppedByPlayer) {
					isGameStoppedByPlayer=true;
				}
				synchronized(isGameStopped) {
					isGameStopped=true;
				}
				break;
			}
			}
			//sends response in a JSON format back to the client
			if(!isGameStoppedByPlayer) {
				out.writeObject(response);
				out.flush();
			}
			//stops the game by the player
			else {
				synchronized(isGameStoppedByPlayer) {
					isGameStoppedByPlayer=false;
				}
			}
			//stops the current thread is case of win/lose/tie
			if(isGameStopped) {
				synchronized(isGameStopped) {
					isGameStopped=false;
				}
			}
		} 
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				in.close();
				out.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Copying the game board to the JSON array
	 * @param boardArray
	 * @param boardGame
	 * @return JSON array after the copying
	 */
	@SuppressWarnings({ "unchecked" })
	private JSONArray castBoard(JSONArray boardArray,char[][] boardGame) {
		for(int i=0;i<boardGame.length;i++)
			for(int j=0;j<boardGame[0].length;j++)
				boardArray.add(Character.toString(boardGame[i][j]));
		return boardArray;
	}
}
