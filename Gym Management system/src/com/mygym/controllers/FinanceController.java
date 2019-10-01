package com.mygym.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import com.mygym.models.FinanceModel;
import com.mygym.views.FinanceView;


public class FinanceController implements ActionListener, ItemListener
{
	private FinanceView view;
	private FinanceModel model;
	
	public FinanceController(FinanceView view, FinanceModel model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(Actions.CLOSE_WINDOW.toString())) {
			view.dispose();
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			int year = (int)e.getItem();
			model.setSelectedYear(year);
			model.refreshTableModel();
			view.showTotalIncomesSum(model.getTotalIncomes());
		}
	}
	
	public static enum Actions {
		CLOSE_WINDOW;
	}
}