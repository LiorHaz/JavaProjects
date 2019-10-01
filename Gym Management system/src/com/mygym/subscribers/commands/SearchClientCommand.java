package com.mygym.subscribers.commands;

import com.mygym.models.Command;
import com.mygym.models.SubscribersModel;
import com.mygym.views.SubscribersView;

public class SearchClientCommand implements Command
{
	//Receivers..
	private SubscribersView view; 
	private SubscribersModel model;
	
	public SearchClientCommand(SubscribersView receiver1, SubscribersModel receiver2) {
		view = receiver1;
		model = receiver2;
	}

	@Override
	public void execute() {
		String[] input = view.getSearchFields();
		model.searchClient(input[0], input[1], input[2]);
	}
}