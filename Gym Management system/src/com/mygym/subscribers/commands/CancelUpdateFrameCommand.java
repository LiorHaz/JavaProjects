package com.mygym.subscribers.commands;

import com.mygym.models.Command;
import com.mygym.views.SubscribersView;

public class CancelUpdateFrameCommand implements Command
{
	private SubscribersView view; //Receiver
	
	public CancelUpdateFrameCommand(SubscribersView receiver) {
		view = receiver;
	}

	@Override
	public void execute() {
		view.cancelUpdateFrame();
	}
}