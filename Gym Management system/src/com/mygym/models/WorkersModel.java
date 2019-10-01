package com.mygym.models;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;



public class WorkersModel 
{
	private DefaultTableModel tableModel;
	private DefaultTableModel reportsTableModel;
	private final Vector<Object> header;
	private final Vector<Object> reportsTableHeader;
	
	public WorkersModel()  {
		header = new Vector<Object>();
		header.add("Id");
		header.add("First Name");
		header.add("Last Name");
		header.add("Role");
		header.add("Birth Date");
		
		reportsTableHeader = new Vector<Object>();
		reportsTableHeader.add("Date");
		reportsTableHeader.add("Entrance Time");
		reportsTableHeader.add("Exit Time");
		reportsTableHeader.add("Hours");
		
		tableModel = new MyTableModel(null, header);
		reportsTableModel = new MyTableModel(null, reportsTableHeader);
		
		refreshTableModel();
	}
	
	public DefaultTableModel getTableModel() {
		return tableModel;
	}
	
	public DefaultTableModel getReportsTableModel() {
		return reportsTableModel;
	}
	
	public String getWorkerIdOfRow(int r) {
		return tableModel.getValueAt(r, 0).toString();
	}
	
	public boolean refreshTableModel() {			
		DBConnection connection = null;
		try {
			connection = DBConnection.getInstance();
			connection.openConnection();
			ResultSet rs = connection.executeQuery("SELECT id,first_name,last_name,role,date FROM workers");
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
	
	public boolean updateReportTableModel(String workerId) {			
		DBConnection connection = null;
		try {
			int month = Calendar.getInstance().get(Calendar.MONTH)+1;
			connection = DBConnection.getInstance();
			connection.openConnection();
			ResultSet rs = connection.executeQuery("SELECT date,begin_time,"
					+ "end_time,total FROM shifts_report WHERE worker_id = '"+workerId+"'"
							+ " AND month = '"+month+"'");
			DefaultTableModel temp = (DefaultTableModel) DbUtils.resultSetToTableModel(rs);
			reportsTableModel.setDataVector(temp.getDataVector(), reportsTableHeader);
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
	
	public boolean addWorker(Worker w) {
		DBConnection connection = null;
		try {
			connection = DBConnection.getInstance();
			//ResultSet rs=connection.executeQuery("select * from workers where id='"+w.getId()+"'");
			//if(rs.next())
			//	return false;
			connection.openConnection();	
			String query = "INSERT INTO workers(id,first_name,last_name,date,role)"
					+ " VALUES (?,?,?,?,?)";
			
			connection.executePreparedStatementUpdate(query, w.getId(), w.getFirstName(), w.getLastName(), w.getBirthDate(),
					w.getRole());
			return true;
		}
		catch(Exception ex) {
			//ex.printStackTrace();
			return false;
		}
		finally {
			connection.closeConnection();
		}
	}
	
	public float getTotalMonthTime(String workerId) {
		/**
		 * return the worker total work time for the current month
		 */
		DBConnection connection = null;
		float sum = 0f;
		try {
			int month = Calendar.getInstance().get(Calendar.MONTH)+1;
			connection = DBConnection.getInstance();
			connection.openConnection();
			ResultSet rs = connection.executeQuery("SELECT total FROM shifts_report WHERE worker_id = '"+workerId+"'"
							+ " AND month = '"+month+"'");
			
			if(rs.isBeforeFirst())
				rs.next(); //move to the first row
			
			while(!rs.isAfterLast()) {
				sum += rs.getFloat(1);
				rs.next();
			}
			
			return sum;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return 0;
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