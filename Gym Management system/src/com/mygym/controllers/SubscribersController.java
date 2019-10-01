package com.mygym.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import com.mygym.models.Command;
import com.mygym.models.SubscribersModel;
import com.mygym.subscribers.commands.AddClientCommand;
import com.mygym.subscribers.commands.CancelAddFrameCommand;
import com.mygym.subscribers.commands.CancelUpdateFrameCommand;
import com.mygym.subscribers.commands.ClearSearchCommand;
import com.mygym.subscribers.commands.SearchClientCommand;
import com.mygym.subscribers.commands.ShowAddFrameCommand;
import com.mygym.subscribers.commands.ShowUpdateFrameCommand;
import com.mygym.subscribers.commands.UpdateClientCommand;
import com.mygym.views.SubscribersView;


public class SubscribersController implements ActionListener
{
	private Map<String, Command> commandInvoker;
	
	public SubscribersController(SubscribersView view, SubscribersModel model) {
		//"Trades" O(n) memory for O(1) time to find the command - Using Command Pattern..
		commandInvoker = new HashMap<String, Command>();
		commandInvoker.put(Actions.ADD_CLIENT.toString(), new AddClientCommand(view, model));
		commandInvoker.put(Actions.SHOW_ADDFRAME.toString(), new ShowAddFrameCommand(view));
		commandInvoker.put(Actions.CANCEL_ADDFRAME.toString(), new CancelAddFrameCommand(view));
		commandInvoker.put(Actions.SEARCH_BUTTON.toString(), new SearchClientCommand(view, model));
		commandInvoker.put(Actions.CLEAR_BUTTON.toString(), new ClearSearchCommand(view, model));
		commandInvoker.put(Actions.SHOW_UPDATEFRAME.toString(), new ShowUpdateFrameCommand(view, model));
		commandInvoker.put(Actions.CANCEL_UPDATEFRAME.toString(), new CancelUpdateFrameCommand(view));
		commandInvoker.put(Actions.UPDATE_CLIENT.toString(), new UpdateClientCommand(view, model));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Command c = commandInvoker.get(e.getActionCommand());
		if(c != null)
			c.execute();
	}
	
	
	public static enum Actions {
		ADD_CLIENT, CANCEL_ADDFRAME, SHOW_ADDFRAME, SEARCH_BUTTON, CLEAR_BUTTON,
		SHOW_UPDATEFRAME, CANCEL_UPDATEFRAME, UPDATE_CLIENT;
	}
}