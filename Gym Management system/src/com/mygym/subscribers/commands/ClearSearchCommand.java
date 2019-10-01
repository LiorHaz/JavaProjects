package com.mygym.subscribers.commands;

import com.mygym.models.Command;
import com.mygym.models.SubscribersModel;
import com.mygym.views.SubscribersView;

public class ClearSearchCommand implements Command
{
	//Receivers..
	private SubscribersView view; 
	private SubscribersModel model;
	
	public ClearSearchCommand(SubscribersView receiver1, SubscribersModel receiver2) {
		view = receiver1;
		model = receiver2;
	}

	@Override
	public void execute() {
		view.clearSearchFields();
		model.refreshTableModel();
	}
}