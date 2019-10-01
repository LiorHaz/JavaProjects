package com.mygym.workers.commands;

import com.mygym.models.Command;
import com.mygym.views.WorkersView;

public class CancelAddFrameCommand implements Command
{
	private WorkersView view; //Receiver
	
	public CancelAddFrameCommand(WorkersView receiver) {
		view = receiver;
	}

	@Override
	public void execute() {
		view.cancelAddFrame();
	}
}