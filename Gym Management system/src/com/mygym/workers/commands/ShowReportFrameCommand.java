package com.mygym.workers.commands;

import com.mygym.models.Command;
import com.mygym.models.WorkersModel;
import com.mygym.views.WorkersView;

public class ShowReportFrameCommand implements Command
{
	private WorkersView view; 
	private WorkersModel model;
	
	public ShowReportFrameCommand(WorkersView receiver1, WorkersModel receiver2) {
		view = receiver1;
		model = receiver2;
	}

	@Override
	public void execute() {
		int r = view.getSelectedRow();
		if(r!=-1) {
			String workerId = model.getWorkerIdOfRow(r);
			model.updateReportTableModel(workerId);
			float total = model.getTotalMonthTime(workerId);
			view.showReportsFrame(total);
		}
	}
}