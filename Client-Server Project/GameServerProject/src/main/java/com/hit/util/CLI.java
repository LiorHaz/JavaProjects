package com.hit.util;

import java.beans.PropertyChangeSupport;
import java.io.PrintStream;
//import java.io.PrintWriter;
import java.util.Scanner;

public class CLI implements Runnable {

	private java.io.InputStream in;
	private  java.io.OutputStream out;
	private java.beans.PropertyChangeSupport pcs;
	private Scanner s;
	//private PrintWriter writer;
	private PrintStream ps;

	public CLI(java.io.InputStream in, java.io.OutputStream out) {
		pcs=new PropertyChangeSupport(this);
		this.in=in;
		this.out=out;	
		s=new Scanner(this.in);
		//writer = new PrintWriter(this.out);
		ps=new PrintStream(this.out);
	}
	//add PropertyChangeListener
	public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}
	//remove PropertyChangeListener
	public void	removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
	//Writing a response to the client
	public void	writeResponse(java.lang.String response) {
		//writer.write(response);
		ps.println(response);
	}

	@Override
	public void run() {
		/**
		 * input from the user, waiting for a command
		 * check which command is it from the three that available, and send it to the server by the firePropertyChange
		 */
		String cmd="";
		boolean flag=false,flag2=true;
		while(!cmd.equals("SHUTDOWN")) {
			ps.println("Please enter your command:");
			//writer.write("Please enter your command:");
			while(!flag) {
				cmd=s.nextLine();
				/**
					 Handles 'GAME_SERVER_CONFIG' command:
					 1.Capacity insertion
					 2.default configuration, when no capacity was inserted
				 */
				//1.Capacity insertion
				String[] str=cmd.split(" ");
				if(str[0].equals("GAME_SERVER_CONFIG")) {
					if(str.length>1) {
						for(int i=0;i<str[1].length();i++)
							if(str[1].charAt(i)<'0'||str[1].charAt(i)>'9') {
								flag2=false;
								break;
							}
								
					}
					if(flag2) {
						if(str.length>1)
							pcs.firePropertyChange(str[0],0,Integer.parseInt(str[1]));
						//2.default configuration
						else
							pcs.firePropertyChange(str[0],0,2);
						break;
					}
				}	
				//Handles rest of the commands
				switch(cmd) {
				//starting server listening for clients
				case "START":{
					writeResponse("Starting server...\n");
					flag=true;
					break;
				}
				//shutdown server
				case "SHUTDOWN":{
					writeResponse("Shutdown server...\n");
					flag=true;
					s.close();
					break;
				}
				//handle invalid commands
				default:{
					writeResponse("Not a valid command\n");
					break;
				}
				}
				//send command to the server
				if(flag)
					pcs.firePropertyChange(cmd,null ,null);
			}
			flag=false;
			cmd="";
		}
		}
}
