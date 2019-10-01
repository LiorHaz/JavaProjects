package com.mygym.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import com.mygym.models.Command;
import com.mygym.models.WorkersModel;
import com.mygym.views.WorkersView;
import com.mygym.workers.commands.AddWorkerCommand;
import com.mygym.workers.commands.CancelAddFrameCommand;
import com.mygym.workers.commands.CancelReportFrameCommand;
import com.mygym.workers.commands.ShowAddFrameCommand;
import com.mygym.workers.commands.ShowReportFrameCommand;


public class WorkersController implements ActionListener
{
	private Map<String, Command> commandInvoker;
	
	public WorkersController(WorkersView view, WorkersModel model) {
		//"Trades" O(n) memory for O(1) time to find the command - Using Command Pattern..
		commandInvoker = new HashMap<String, Command>();
		commandInvoker.put(Actions.ADD_WORKER.toString(), new AddWorkerCommand(view, model));
		commandInvoker.put(Actions.CANCEL_ADDFRAME.toString(), new CancelAddFrameCommand(view));
		commandInvoker.put(Actions.SHOW_ADDFRAME.toString(), new ShowAddFrameCommand(view));
		commandInvoker.put(Actions.SHOW_REPORTFRAME.toString(), new ShowReportFrameCommand(view, model));
		commandInvoker.put(Actions.CANCEL_REPORTFRAME.toString(), new CancelReportFrameCommand(view));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Command c = commandInvoker.get(e.getActionCommand());
		if(c != null)
			c.execute();
	}
	
	
	public static enum Actions {
		ADD_WORKER, CANCEL_ADDFRAME, SHOW_ADDFRAME,
		SHOW_REPORTFRAME, CANCEL_REPORTFRAME;
	}
}