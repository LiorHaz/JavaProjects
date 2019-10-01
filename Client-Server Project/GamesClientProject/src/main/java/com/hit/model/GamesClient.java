package com.hit.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GamesClient {
	private int port;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket server;
	private InetAddress address;
	private JSONObject request,response;
	private JSONParser parser;

	public GamesClient(int serverPort) {
		port=serverPort;
		in=null;
		out=null;
		server=null;
		address=null;
		request=null;
		response=null;
		parser = new JSONParser();
	}

	/**
	 * connect to the server
	 */
	public void connectToServer() {
		if(server!=null)
			return;
		try {
			address=InetAddress.getByName("127.0.0.1");
			server=new Socket(address,port);
			out= new ObjectOutputStream(server.getOutputStream());
			in=new ObjectInputStream(server.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * close the connection with the server
	 */
	public void	closeConnection() {
		if(server==null)
			return;
		try {
			server.close();
			server=null;
			in.close();
			in=null;
			out.close();
			out=null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send a message to the server. The messages are in JSON format
	 * @param message -  the JSON message as a string
	 * @param hasResponse - a flag that indicates whether to wait for a response from the server
	 * @return the server's response to the message
	 */
	public String sendMessage(String message, boolean hasResponse){
		if(server==null)
			connectToServer();
		try {
			request=(JSONObject)parser.parse(message);
			out.writeObject(request);
			out.flush();
			if(hasResponse) {
				try {
					response=(JSONObject)in.readObject();
					return response.toJSONString();
				}
				catch(EOFException e) {
					return null;
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
