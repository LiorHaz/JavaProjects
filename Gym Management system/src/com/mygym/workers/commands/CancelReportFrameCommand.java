package com.mygym.workers.commands;

import com.mygym.models.Command;
import com.mygym.views.WorkersView;

public class CancelReportFrameCommand implements Command
{
	private WorkersView view; //Receiver
	
	public CancelReportFrameCommand(WorkersView receiver) {
		view = receiver;
	}

	@Override
	public void execute() {
		view.cancelReportsFrame();
	}
}