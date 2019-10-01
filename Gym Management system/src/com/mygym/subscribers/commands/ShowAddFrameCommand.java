package com.mygym.subscribers.commands;

import com.mygym.models.Command;
import com.mygym.views.SubscribersView;

public class ShowAddFrameCommand implements Command
{
	private SubscribersView view; //Receiver
	
	public ShowAddFrameCommand(SubscribersView receiver) {
		view = receiver;
	}

	@Override
	public void execute() {
		view.showAddFrame();
	}
}