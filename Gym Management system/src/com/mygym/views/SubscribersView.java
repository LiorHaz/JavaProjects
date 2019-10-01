package com.mygym.views;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.mygym.controllers.SubscribersController;
import com.mygym.models.Client;
import com.mygym.models.SubscribersModel;


public class SubscribersView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private AddSubscriberFrame asf;
	private UpdateSubscriberFrame usf;
	private JTextField idSearchTF, fnSearchTF, lnSearchTF;
	private JTable table;
	
	public SubscribersView() {
		super("Subscribers Managment");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationByPlatform(true);
		setSize(1050, 400);
		
		SubscribersModel model = new SubscribersModel();
		SubscribersController controller = new SubscribersController(this, model);
		
		asf = new AddSubscriberFrame(controller);
		usf = new UpdateSubscriberFrame(controller);
		initTable(model);
		initSearchPanel(controller);
		initBottomPanel(controller);
	}
	
	public void showAddFrame() {
		asf.setVisible(true);
	}
	
	public void cancelAddFrame() {
		asf.cancelWindow();
	}
	
	public void cancelUpdateFrame() {
		usf.cancelWindow();
	}
	
	public void showUpdateFrame(Client client) {
		usf.showUpdateFor(client);
	}
	
	public Client getUpdatedClient() {
		return usf.getClient();
	}
	
	public Client getAddedClientData() {
		return asf.getAddedClientData();
	}
	
	public int getSelectedRow() {
		return table.getSelectedRow();
	}
	
	public void clearSearchFields() {
		idSearchTF.setText(null);
		fnSearchTF.setText(null);
		lnSearchTF.setText(null);
	}
	
	public String[] getSearchFields() {
		/**
		 * return the search textfields text.
		 * the order of the returned array is: id, firstname, lastname.
		 */
		return new String[] {idSearchTF.getText(), fnSearchTF.getText(), lnSearchTF.getText()};
	}
	
	private void initTable(SubscribersModel model) {	
		//Read the table model from the "Model"..
		table = new JTable(model.getTableModel());
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setFont(new Font("Ariel", Font.BOLD, 14));
		JScrollPane scroller = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(scroller, BorderLayout.CENTER);
	}
	
	private void initSearchPanel(SubscribersController controller) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Search"));
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JLabel label1 = new JLabel("Id");
		idSearchTF = new JTextField(9);
		JLabel label2 = new JLabel("First Name");
		fnSearchTF = new JTextField(9);
		JLabel label3 = new JLabel("Last Name");
		lnSearchTF = new JTextField(9);
		
		JButton searchButton = new JButton("Search");
		JButton clearButton = new JButton("Clear Search");
		searchButton.setActionCommand(SubscribersController.Actions.SEARCH_BUTTON.toString());
		searchButton.addActionListener(controller);
		clearButton.setActionCommand(SubscribersController.Actions.CLEAR_BUTTON.toString());
		clearButton.addActionListener(controller);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(label1)
					.addComponent(idSearchTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(label2)
					.addComponent(fnSearchTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(label3)
					.addComponent(lnSearchTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(searchButton)
						.addComponent(clearButton)));

		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(label1)
						.addComponent(idSearchTF))
				.addGroup(layout.createSequentialGroup()
						.addComponent(label2)
						.addComponent(fnSearchTF))
				.addGroup(layout.createSequentialGroup()
						.addComponent(label3)
						.addComponent(lnSearchTF))
				.addGroup(layout.createSequentialGroup()
						.addComponent(searchButton)
						.addComponent(clearButton)));
		
		
		getContentPane().add(panel, BorderLayout.WEST);
	}
	
	private void initBottomPanel(SubscribersController controller) {
		JPanel p = new JPanel(new FlowLayout());
		JButton addButton = new JButton("Add Subscriber");
		addButton.setActionCommand(SubscribersController.Actions.SHOW_ADDFRAME.toString());
		addButton.addActionListener(controller);
		JButton updateButton = new JButton("Update Subscriber");
		updateButton.setActionCommand(SubscribersController.Actions.SHOW_UPDATEFRAME.toString());
		updateButton.addActionListener(controller);
		
		p.add(addButton);
		p.add(updateButton);
		getContentPane().add(p, BorderLayout.SOUTH);
	}
	
	/* --------------------- Nested Classes -----------------------*/
	
	private static class AddSubscriberFrame extends JFrame
	{
		private static final long serialVersionUID = 1L;
		private JTextField fn, ln, id, date, card, exp;
		private JComboBox<String> type;

		public AddSubscriberFrame(SubscribersController controller) {
			super("Add Subscriber");
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
			JLabel l5 = new JLabel("Subscription Type");
			JLabel l6 = new JLabel("Card number");
			JLabel l7 = new JLabel("MM/YY");
			fn = new JTextField(10); 
			ln = new JTextField(10);
			id = new JTextField(9);
			date = new JTextField(10);	
			type = new JComboBox<String>();
			card = new JTextField(16);
			exp = new JTextField(5);
			date.setToolTipText("dd/mm/yyyy");
			exp.setToolTipText("mm/yy");
			type.addItem("Full Branches Access - 199 NIS/month");
			type.addItem("This Branche Access Only - 99 NIS/month");
			JPanel p1 = new JPanel(new FlowLayout());
			JPanel p2 = new JPanel(new FlowLayout());
			JPanel p3 = new JPanel(new FlowLayout());
			JPanel p4 = new JPanel(new FlowLayout());
			JPanel p5 = new JPanel(new FlowLayout());
			JPanel p6 = new JPanel(new FlowLayout());
			p1.add(l1);
			p1.add(id);
			p2.add(l2);
			p2.add(fn);
			p3.add(l3);
			p3.add(ln);
			p4.add(l4);
			p4.add(date);
			p5.add(l5);
			p5.add(type);
			p6.add(l6);
			p6.add(card);
			p6.add(l7);
			p6.add(exp);
			panel.add(p1);
			panel.add(p2);
			panel.add(p3);
			panel.add(p4);
			panel.add(p5);
			panel.add(p6);
			getContentPane().add(panel, BorderLayout.CENTER);
		}
		
		private void initBottomPanel(SubscribersController controller) {
			JPanel p = new JPanel(new FlowLayout());
			JButton add = new JButton("Add");
			JButton cancel = new JButton("Cancel");
			add.setActionCommand(SubscribersController.Actions.ADD_CLIENT.toString());
			cancel.setActionCommand(SubscribersController.Actions.CANCEL_ADDFRAME.toString());
			add.addActionListener(controller);
			cancel.addActionListener(controller);
			p.add(add);
			p.add(cancel);
			getContentPane().add(p, BorderLayout.SOUTH);
		}
		
		public Client getAddedClientData() {
			int payment = type.getSelectedIndex() == 0 ? 199 : 99;
			return new Client(fn.getText(), ln.getText(), date.getText(), 
					(String)type.getSelectedItem(), id.getText(), card.getText()+"|"+exp.getText(), payment);
		}
		
		private void resetFields() {
			fn.setText(""); 
			ln.setText("");
			id.setText("");
			date.setText("");
			card.setText("");
			exp.setText("");
			type.setSelectedIndex(0);
		}
		
		public void cancelWindow() {
			resetFields();
			dispose();
		}
	}
	
	
	private static class UpdateSubscriberFrame extends JFrame
	{
		private static final long serialVersionUID = 1L;
		private JTextField id, fn, ln, date, card, exp; //everything but subscription type and code..
		private int clientCode;
		
		public UpdateSubscriberFrame(SubscribersController controller) {
			super("Update Subscriber");
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
		
		private void showUpdateFor(Client c) {
			String card = c.getCreditCard();
			String exp = "";
			if(card.indexOf('|') != -1) {
				//should always reach here.. because this is how the card kept at the DB..
				exp = card.substring(card.indexOf('|')+1, card.length());
				card = card.substring(0, card.indexOf('|'));
			}
			id.setText(c.getId());
			fn.setText(c.getFirstName());
			ln.setText(c.getLastName());
			date.setText(c.getBirthDate());
			clientCode = c.getSubscriptionCode();
			this.card.setText(card);
			this.exp.setText(exp);
			setVisible(true);
		}
		
		private void initCenterPanel() {
			JPanel panel = new JPanel(new GridLayout(0, 1));	
			JLabel l1 = new JLabel("Id");
			JLabel l2 = new JLabel("First Name");
			JLabel l3 = new JLabel("Last Name");
			JLabel l4 = new JLabel("Birth Date");
			JLabel l6 = new JLabel("Card number");
			JLabel l7 = new JLabel("MM/YY");
			fn = new JTextField(10); 
			ln = new JTextField(10);
			id = new JTextField(9);
			date = new JTextField(10);	
			card = new JTextField(16);
			exp = new JTextField(5);
			date.setToolTipText("dd/mm/yyyy");
			exp.setToolTipText("mm/yy");
			JPanel p1 = new JPanel(new FlowLayout());
			JPanel p2 = new JPanel(new FlowLayout());
			JPanel p3 = new JPanel(new FlowLayout());
			JPanel p4 = new JPanel(new FlowLayout());
			JPanel p6 = new JPanel(new FlowLayout());
			p1.add(l1);
			p1.add(id);
			p2.add(l2);
			p2.add(fn);
			p3.add(l3);
			p3.add(ln);
			p4.add(l4);
			p4.add(date);
			p6.add(l6);
			p6.add(card);
			p6.add(l7);
			p6.add(exp);
			panel.add(p1);
			panel.add(p2);
			panel.add(p3);
			panel.add(p4);
			panel.add(p6);
			getContentPane().add(panel, BorderLayout.CENTER);
		}
		
		private void initBottomPanel(SubscribersController controller) {
			JPanel p = new JPanel(new FlowLayout());
			JButton add = new JButton("Update");
			JButton cancel = new JButton("Cancel");
			add.setActionCommand(SubscribersController.Actions.UPDATE_CLIENT.toString());
			cancel.setActionCommand(SubscribersController.Actions.CANCEL_UPDATEFRAME.toString());
			add.addActionListener(controller);
			cancel.addActionListener(controller);
			p.add(add);
			p.add(cancel);
			getContentPane().add(p, BorderLayout.SOUTH);
		}
		
		private void resetFields() {
			fn.setText(""); 
			ln.setText("");
			id.setText("");
			date.setText("");
			card.setText("");
			exp.setText("");
			clientCode = -1;
		}
		
		public void cancelWindow() {
			resetFields();
			dispose();
		}
		
		private Client getClient() {
			return new Client(fn.getText(), ln.getText(), date.getText(), clientCode, 
					null, id.getText(), card.getText()+"|"+exp.getText(), -1);
		}
	}
}