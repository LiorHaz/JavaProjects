package com.hit.controller;

import java.beans.PropertyChangeEvent;
import com.hit.model.Model;
import com.hit.view.View;

public class GamesController implements Controller {
	private Model model;
	private View view;
	public GamesController(Model model,View view) {
		this.model=model;
		this.view=view;
	}

	/**
	 * connects between the view and the model
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
			case "new game":{
				model.newGame((String)evt.getOldValue(), (String)evt.getNewValue());
				break;
			}
			case "new game response":{
				view.updateViewNewGame((Character[][])evt.getNewValue());
				break;
			}
			case "player move":{
				// equal row and column case
				if(evt.getNewValue() instanceof String)
				{
					String str=(String)evt.getNewValue();
					String[] s=str.split(" ");
					int row=Integer.parseInt(s[0]);
					int col=Integer.parseInt(s[1]);
					model.updatePlayerMove(row, col);
					break;
				}
				model.updatePlayerMove((int)evt.getOldValue(), (int)evt.getNewValue());
				break;
			}
			case "player move response":{
				view.updateViewGameMove((long)evt.getOldValue(), (Character[][])evt.getNewValue());
				break;
			}
			case "start game":{
				model.startGame();
				break;
			}
			case "start game response":{
				view.updateViewStartGame((Character[][])evt.getNewValue());
				break;
			}
			case "stop game":{
				model.stopGame();
				break;
			}
			default:{
				break;
			}
		}
	}

}