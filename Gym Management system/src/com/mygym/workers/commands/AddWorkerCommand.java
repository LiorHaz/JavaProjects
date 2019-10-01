package com.mygym.workers.commands;

import com.mygym.models.Command;
import com.mygym.models.Worker;
import com.mygym.models.WorkersModel;
import com.mygym.views.WorkersView;

public class AddWorkerCommand implements Command
{
	//Receivers..
	private WorkersView view; 
	private WorkersModel model;
	
	public AddWorkerCommand(WorkersView receiver1, WorkersModel receiver2) {
		view = receiver1;
		model = receiver2;
	}

	@Override
	public void execute() {
		Worker w = view.getAddedWorkerData();
		//check validation..
		boolean r = model.addWorker(w);
		if(r)
			model.refreshTableModel();
		
		view.cancelAddFrame();
	}
}