package com.mygym.subscribers.commands;

import com.mygym.models.Client;
import com.mygym.models.Command;
import com.mygym.models.SubscribersModel;
import com.mygym.views.SubscribersView;

public class AddClientCommand implements Command
{
	//Receivers..
	private SubscribersView view; 
	private SubscribersModel model;
	
	public AddClientCommand(SubscribersView receiver1, SubscribersModel receiver2) {
		view = receiver1;
		model = receiver2;
	}

	@Override
	public void execute() {
		Client c = view.getAddedClientData();
		//check validation..
		boolean r = model.addClient(c);
		if(r)
			model.refreshTableModel();
		
		view.cancelAddFrame();
	}
}