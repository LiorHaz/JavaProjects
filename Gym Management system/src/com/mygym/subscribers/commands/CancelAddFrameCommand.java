package com.mygym.subscribers.commands;

import com.mygym.models.Command;
import com.mygym.views.SubscribersView;

public class CancelAddFrameCommand implements Command
{
	private SubscribersView view; //Receiver
	
	public CancelAddFrameCommand(SubscribersView receiver) {
		view = receiver;
	}

	@Override
	public void execute() {
		view.cancelAddFrame();
	}

}