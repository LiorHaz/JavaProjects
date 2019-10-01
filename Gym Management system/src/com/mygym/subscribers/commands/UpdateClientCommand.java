package com.mygym.subscribers.commands;

import com.mygym.models.Client;
import com.mygym.models.Command;
import com.mygym.models.SubscribersModel;
import com.mygym.views.SubscribersView;

public class UpdateClientCommand implements Command
{
	//Receivers..
	private SubscribersView view; 
	private SubscribersModel model;
	
	public UpdateClientCommand(SubscribersView receiver1, SubscribersModel receiver2) {
		view = receiver1;
		model = receiver2;
	}

	@Override
	public void execute() {
		Client c = view.getUpdatedClient();
		//check validation..
		boolean r = model.updateClient(c);
		if(r)
			model.refreshTableModel();
		
		view.cancelUpdateFrame();
	}
}