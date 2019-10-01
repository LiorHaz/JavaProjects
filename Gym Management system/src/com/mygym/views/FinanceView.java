package com.mygym.views;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Calendar;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.mygym.controllers.FinanceController;
import com.mygym.models.FinanceModel;


public class FinanceView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel sumLabel;
	
	public FinanceView() {
		super("Finance Managment");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationByPlatform(true);
		setSize(1050, 400);
		
		FinanceModel model = new FinanceModel();
		FinanceController controller = new FinanceController(this, model);
		
		initTable(model);
		initBottomPanel(controller, model);
		initUpperPanel(controller);
	}
	
	public void showTotalIncomesSum(int total) {
		sumLabel.setText("Total Incomes: "+total);
	}

	private void initTable(FinanceModel model) {	
		//Read the table model from the "Model"..
		table = new JTable(model.getTableModel());
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setFont(new Font("Ariel", Font.BOLD, 14));
		JScrollPane scroller = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(scroller, BorderLayout.CENTER);
	}
	
	private void initBottomPanel(FinanceController controller, FinanceModel model) {
		JPanel p = new JPanel(new GridLayout(0, 1));
		sumLabel = new JLabel("Total Incomes: "+model.getTotalIncomes());
		JButton okButton = new JButton("OK");
		okButton.setActionCommand(FinanceController.Actions.CLOSE_WINDOW.toString());
		okButton.addActionListener(controller);
		JPanel wrapperPanel = new JPanel(new FlowLayout());
		wrapperPanel.add(okButton);
		p.add(sumLabel);
		p.add(wrapperPanel);
		getContentPane().add(p, BorderLayout.SOUTH);
	}
	
	private void initUpperPanel(FinanceController controller) {
		JPanel p = new JPanel(new FlowLayout());
		JComboBox<Integer> combo = new JComboBox<Integer>();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		for(int i=2000; i<=currentYear; i++)
			combo.addItem(i);
		combo.addItemListener(controller);
		combo.setSelectedIndex(combo.getItemCount()-1);
		
		p.add(combo);
		getContentPane().add(p, BorderLayout.NORTH);
	}
}