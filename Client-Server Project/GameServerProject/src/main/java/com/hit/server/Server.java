package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import com.hit.services.GameServerController;

public class Server implements java.beans.PropertyChangeListener, java.lang.Runnable, java.util.EventListener {

	private ServerSocket server;
	private Socket client;
	private int port;
	private int capacity;
	private Executor executor;
	private HandleRequest request;
	private GameServerController controller;
	private boolean stopServer;
	public Server(int port) {
		this.port=port;
		executor=null;
		stopServer=false;
		server=null;
	}
	@Override
	public void run() {
		try {
			if(server==null)
				server=new ServerSocket(port);
			while(!stopServer) {
				client = server.accept();
				request=new HandleRequest(client, controller);
				executor.execute(request);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		/**
		 * getting the command from CLI by PropertyChangeEvent object
		 */
		//set number of games which can be played simultaneously
		if(evt.getPropertyName().equals("GAME_SERVER_CONFIG")) {
			if(executor!=null)
				return;
			capacity=(int)evt.getNewValue();
			System.out.println("No of games: "+capacity);
			controller=new GameServerController(capacity);
			executor =Executors.newFixedThreadPool (capacity);
		}
		//Enable the server to receive requests from clients 
		else if(evt.getPropertyName().equals("START")) {
			//supplies default configuration in case START command was called before GAME_SERVER_CONFIG command
			if(executor==null) {
				capacity=2;
				controller=new GameServerController(capacity);
				executor =Executors.newFixedThreadPool(capacity);
			}
			new Thread(this).start();
		}
		//Shutdown the server
		else if(evt.getPropertyName().equals("SHUTDOWN")) {
			if(executor==null)
				return;
			stopServer=true;
			try {	
				//Wait for all Threads to finish successfully
				((ExecutorService) executor).awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				server.close();
				if(client!=null)
					client.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
