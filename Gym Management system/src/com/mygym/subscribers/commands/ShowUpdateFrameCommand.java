package com.mygym.subscribers.commands;

import com.mygym.models.Client;
import com.mygym.models.Command;
import com.mygym.models.SubscribersModel;
import com.mygym.views.SubscribersView;

public class ShowUpdateFrameCommand implements Command
{
	private SubscribersView view; 
	private SubscribersModel model;
	
	public ShowUpdateFrameCommand(SubscribersView receiver1, SubscribersModel receiver2) {
		view = receiver1;
		model = receiver2;
	}

	@Override
	public void execute() {
		int r = view.getSelectedRow();
		if(r!=-1) {
			Client c = model.getClientFromRow(r);
			view.showUpdateFrame(c);
		}
	}
}