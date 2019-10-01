package com.mygym.workers.commands;

import com.mygym.models.Command;
import com.mygym.views.WorkersView;

public class ShowAddFrameCommand implements Command
{
	private WorkersView view; //Receiver
	
	public ShowAddFrameCommand(WorkersView receiver) {
		view = receiver;
	}

	@Override
	public void execute() {
		view.showAddFrame();
	}
}