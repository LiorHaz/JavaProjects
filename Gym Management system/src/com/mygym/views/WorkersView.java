package com.mygym.views;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.mygym.controllers.WorkersController;
import com.mygym.models.Manager;
import com.mygym.models.Secretary;
import com.mygym.models.Trainer;
import com.mygym.models.Worker;
import com.mygym.models.WorkersModel;


public class WorkersView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private AddWorkerFrame awf;
	private ReportFrame rf;
	private JTable table;
	
	
	public WorkersView() {
		super("Workers Managment");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationByPlatform(true);
		setSize(900, 350);
		
		WorkersModel model = new WorkersModel();
		WorkersController controller = new WorkersController(this, model);
		
		rf = new ReportFrame(controller, model);
		awf = new AddWorkerFrame(controller);
		
		initTable(model);
		initBottomPanel(controller);
	}
	
	public void showAddFrame() {
		awf.setVisible(true);
	}
	
	public void cancelAddFrame() {
		awf.cancelWindow();
	}
	
	public Worker getAddedWorkerData() {
		return awf.getAddedWorkerData();
	}
	
	public void showReportsFrame(float totalTime) {
		rf.updateTotalHours(totalTime);
		rf.setVisible(true);
	}
	
	public void cancelReportsFrame() {
		rf.closeWindow();
	}
	
	public int getSelectedRow() {
		return table.getSelectedRow();
	}
	
	private void initTable(WorkersModel model) {	
		//Read the table model from the "Model"..
		table = new JTable(model.getTableModel());
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setFont(new Font("Ariel", Font.BOLD, 14));
		JScrollPane scroller = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(scroller, BorderLayout.CENTER);
	}
	
	private void initBottomPanel(WorkersController controller) {
		JPanel p = new JPanel(new FlowLayout());
		JButton addButton = new JButton("Add Worker");
		addButton.setActionCommand(WorkersController.Actions.SHOW_ADDFRAME.toString());
		addButton.addActionListener(controller);
		JButton reportButton = new JButton("Show Selected Worker Reports");
		reportButton.setActionCommand(WorkersController.Actions.SHOW_REPORTFRAME.toString());
		reportButton.addActionListener(controller);
		
		p.add(addButton);
		p.add(reportButton);
		getContentPane().add(p, BorderLayout.SOUTH);
	}
	
	/* --------------------- Nested Classes -----------------------*/
	
	private static class AddWorkerFrame extends JFrame
	{
		private static final long serialVersionUID = 1L;
		private JTextField fn, ln, id, date;
		private JComboBox<String> role;

		public AddWorkerFrame(WorkersController controller) {
			super("Add Worker");
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			setLayout(new BorderLayout());
			setLocationByPlatform(true);
			
			setLayout(new BorderLayout());
			initCenterPanel();
			initBottomPanel(controller); 
			
			pack();
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					cancelWindow();
				}
			});
		}
		
		private void initCenterPanel() {
			JPanel panel = new JPanel(new GridLayout(0, 1));	
			JLabel l1 = new JLabel("Id");
			JLabel l2 = new JLabel("First Name");
			JLabel l3 = new JLabel("Last Name");
			JLabel l4 = new JLabel("Birth Date");
			JLabel l5 = new JLabel("Role");
			fn = new JTextField(10); 
			ln = new JTextField(10);
			id = new JTextField(9);
			date = new JTextField(10);	
			role = new JComboBox<String>();
			date.setToolTipText("dd/mm/yyyy");
			role.addItem("Trainer");
			role.addItem("Secretary");
			role.addItem("Manager");
			role.setSelectedIndex(0);
			JPanel p1 = new JPanel(new FlowLayout());
			JPanel p2 = new JPanel(new FlowLayout());
			JPanel p3 = new JPanel(new FlowLayout());
			JPanel p4 = new JPanel(new FlowLayout());
			JPanel p5 = new JPanel(new FlowLayout());
			p1.add(l1);
			p1.add(id);
			p2.add(l2);
			p2.add(fn);
			p3.add(l3);
			p3.add(ln);
			p4.add(l4);
			p4.add(date);
			p5.add(l5);
			p5.add(role);
			panel.add(p1);
			panel.add(p2);
			panel.add(p3);
			panel.add(p4);
			panel.add(p5);
			getContentPane().add(panel, BorderLayout.CENTER);
		}
		
		private void initBottomPanel(WorkersController controller) {
			JPanel p = new JPanel(new FlowLayout());
			JButton add = new JButton("Add");
			JButton cancel = new JButton("Cancel");
			add.setActionCommand(WorkersController.Actions.ADD_WORKER.toString());
			cancel.setActionCommand(WorkersController.Actions.CANCEL_ADDFRAME.toString());
			add.addActionListener(controller);
			cancel.addActionListener(controller);
			p.add(add);
			p.add(cancel);
			getContentPane().add(p, BorderLayout.SOUTH);
		}

		public Worker getAddedWorkerData() {
			if(role.getSelectedIndex() == 0)
				return new Trainer(id.getText(), fn.getText(), ln.getText(), date.getText());
			else
				if(role.getSelectedIndex() == 1)
					return new Secretary(id.getText(), fn.getText(), ln.getText(), date.getText());
				else
					 return new Manager(id.getText(), fn.getText(), ln.getText(), date.getText());
		}
		
		private void resetFields() {
			fn.setText(""); 
			ln.setText("");
			id.setText("");
			date.setText("");
			role.setSelectedIndex(0);
		}
		
		public void cancelWindow() {
			resetFields();
			dispose();
		}
	}
	
	
	private static class ReportFrame extends JFrame
	{
		private static final long serialVersionUID = 1L;
		private JLabel totalLabel;
		
		public ReportFrame(WorkersController controller, WorkersModel model) {
			super("Report");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setLayout(new BorderLayout());
			setLocationByPlatform(true);
			setSize(500, 300);
			setLayout(new BorderLayout());
			initCenterPanel(model);
			initBottomPanel(controller); 
		}
		
		private void initCenterPanel(WorkersModel model) {	
			//Read the table model from the "Model"..
			JTable reportTable = new JTable(model.getReportsTableModel());
			reportTable.getTableHeader().setReorderingAllowed(false);
			reportTable.setRowSelectionAllowed(false);
			reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			reportTable.getTableHeader().setFont(new Font("Ariel", Font.BOLD, 14));
			JScrollPane scroller = new JScrollPane(reportTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			getContentPane().add(scroller, BorderLayout.CENTER);
		}
		
		private void initBottomPanel(WorkersController controller) {
			JPanel p = new JPanel(new GridLayout(0, 1));
			totalLabel = new JLabel("Total Hours: ");
			JButton okButton = new JButton("Ok");
			okButton.setActionCommand(WorkersController.Actions.CANCEL_REPORTFRAME.toString());
			okButton.addActionListener(controller);
			JPanel wrapperPanel = new JPanel(new FlowLayout());
			wrapperPanel.add(okButton);
			p.add(totalLabel);
			p.add(wrapperPanel);
			getContentPane().add(p, BorderLayout.SOUTH);
		}
		
		private void updateTotalHours(float total) {
			totalLabel.setText("Total Hours: "+total);
		}
		
		public void closeWindow() {
			dispose();
		}
	}
}