package com.mygym.models;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;



public class SubscribersModel 
{
	private DefaultTableModel tableModel;
	private final Vector<Object> header;
	
	public SubscribersModel()  {
		header = new Vector<Object>();
		header.add("Id");
		header.add("First Name");
		header.add("Last Name");
		header.add("Birth Date");
		header.add("Subscription Type");
		header.add("Subscription Code");
		header.add("Payment");
		tableModel = new MyTableModel(null, header);
		refreshTableModel();
	}
	
	public DefaultTableModel getTableModel() {
		return tableModel;
	}
	
	public boolean refreshTableModel() {		
		
		DBConnection connection = null;
		try {
			connection = DBConnection.getInstance();
			connection.openConnection();
			ResultSet rs = connection.executeQuery("SELECT id,firstname,lastname,birthdate,type,code,paymentform FROM clients");
			DefaultTableModel temp = (DefaultTableModel) DbUtils.resultSetToTableModel(rs);
			tableModel.setDataVector(temp.getDataVector(), header);
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		finally {
			connection.closeConnection();
		}
	}
	
	public boolean addClient(Client c) {
		DBConnection connection = null;
		try {
			connection = DBConnection.getInstance();
			connection.openConnection();
			Calendar calendar = Calendar.getInstance();
			int m = calendar.get(Calendar.MONTH)+1;
			int y = calendar.get(Calendar.YEAR);
			String query = "INSERT INTO clients(id,firstname,lastname,birthdate,type,"
					+ "paymentform,monthlypayment,subscription_month,subscription_year) VALUES (?,?,?,?,?,?,?,?,?)";
			
			connection.executePreparedStatementUpdate(query, c.getId(), c.getFirstName(), c.getLastName(), c.getBirthDate(),
					c.getSubscriptionType(), c.getCreditCard(), c.getMonthlyPayment(), m, y);
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		finally {
			connection.closeConnection();
		}
	}
	
	public Client getClientFromRow(int r) {
		/**
		 * return the client data from the table itself and not
		 * from the database..
		 */
		if(r==-1 || r>=tableModel.getRowCount())
			return null;
		
		String id = tableModel.getValueAt(r, 0).toString();
		String fn = tableModel.getValueAt(r, 1).toString();
		String ln = tableModel.getValueAt(r, 2).toString();
		String bd = tableModel.getValueAt(r, 3).toString();
		int code = (Integer)tableModel.getValueAt(r, 5);
		String card = tableModel.getValueAt(r, 6).toString();
		return new Client(fn, ln, bd, code, null, id, card, -1);
	}
	
	public void searchClient(String id, String fn, String ln) {
		DBConnection connection = null;
		try {
			connection = DBConnection.getInstance();
			connection.openConnection();
			String sid = id.length()==0 ? "id LIKE '%'" : "id = '"+id+"'";
			String sfn = fn.length()==0 ? "firstname LIKE '%'" : "firstname = '"+fn+"'";
			String sln = ln.length()==0 ? "lastname LIKE '%'" : "lastname = '"+ln+"'";
			ResultSet rs = connection.executeQuery("SELECT * FROM clients WHERE "+sid+" AND "+sfn+" AND "+sln);
			DefaultTableModel temp = (DefaultTableModel) DbUtils.resultSetToTableModel(rs);
			tableModel.setDataVector(temp.getDataVector(), header);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			connection.closeConnection();
		}
	}
	
	public boolean updateClient(Client c) {
		DBConnection connection = null;
		try {
			connection = DBConnection.getInstance();
			connection.openConnection();	
			String query = "UPDATE clients SET id='"+c.getId()+"', firstname='"+c.getFirstName()+"', "
					+"lastname='"+c.getLastName()+"', birthdate='"+c.getBirthDate()+"', paymentform='"+c.getCreditCard()+"'"
					+" WHERE code="+c.getSubscriptionCode();
			
			connection.executeUpdate(query);
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		finally {
			connection.closeConnection();
		}
	}

	
	private static class MyTableModel extends DefaultTableModel
	{
		private static final long serialVersionUID = 1L;

		public MyTableModel(Vector<Vector<Object>> data, Vector<Object> header) {
			super(data, header);
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
}
