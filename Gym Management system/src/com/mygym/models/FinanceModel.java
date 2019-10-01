package com.mygym.models;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;



public class FinanceModel 
{
	private DefaultTableModel tableModel;
	private final Vector<Object> header;
	private int selectedYear = 2019; //combo-box selected year..
	private int totalIncomes;
	
	public FinanceModel()  {
		header = new Vector<Object>();
		header.add("Date");
		header.add("Amount");
		header.add("Description");
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
			ResultSet incomesRS = connection.executeQuery("SELECT id, subscription_month, subscription_year, monthlypayment"
					+ " FROM clients "
					+ "WHERE subscription_year<='"+selectedYear+"'");
			
			ResultSet outcomesRS = connection.executeQuery("SELECT worker_id, month, role, sum(total) "
					+ "FROM shifts_report AS sr JOIN workers AS w ON(sr.worker_id = w.id)"
					+ " WHERE year='"+selectedYear+"'"
					+ " GROUP BY worker_id, month");
			
			
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			data.addAll(incomesIntoVector(incomesRS));
			data.addAll(outcomesIntoVector(outcomesRS));
			data.sort(new DateComperator());
			
			tableModel.setDataVector(data, header);
			calculateTotalIncomes(data);
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
	
	private Vector<Vector<Object>> outcomesIntoVector(ResultSet rs) {
		/**
		 * Parse the ResultSet into Vector with the required data.
		 */
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		try {
			if(rs.isBeforeFirst())
				rs.next();
			
			Vector<Object> row;
			String id, role;
			int totalHours, month;
			int cpa; //cost per hour
			while(!rs.isAfterLast()) {
				row = new Vector<Object>();
				id = rs.getString(1);
				month = rs.getInt(2);
				role = rs.getString(3);
				totalHours = rs.getInt(4);
				cpa = role.equalsIgnoreCase("manager") ? Manager.getSalary() : 
					role.equalsIgnoreCase("trainer") ? Trainer.getSalary() : Secretary.getSalary();
				
				row.add("1/"+month+"/"+selectedYear);
				row.add(-1*(totalHours*cpa));
				row.add("Salary playment for "+role+" with id "+id);
				data.add(row);
				rs.next();
			}
			return data;
		} 
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	private Vector<Vector<Object>> incomesIntoVector(ResultSet rs) {
		/**
		 * Parse the ResultSet into Vector with the required data.
		 */
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		try {
			if(rs.isBeforeFirst())
				rs.next();
			
			Vector<Object> row;
			int income, month, monthsEnd, year;
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			String id;
			while(!rs.isAfterLast()) {
				id = rs.getString(1);
				month = rs.getInt(2);
				year = rs.getInt(3);
				income = rs.getInt(4);
				if(year < selectedYear) 
					month = 1; //if the subsription year is smaller then the client paid from Januar..
				if(selectedYear == currentYear)
					monthsEnd = Calendar.getInstance().get(Calendar.MONTH)+1; //the maximum month is the current month
				else
					monthsEnd = 12;
				
				for(int i=month; i<=monthsEnd; i++) {
					row = new Vector<Object>();
					row.add("1/"+i+"/"+selectedYear);
					row.add(income);
					row.add("Monthly payment from subscriber "+id);
					data.add(row);
				}
				
				rs.next();
			}
			return data;
		} 
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public int getTotalIncomes() {
		return totalIncomes;
	}
	
	private void calculateTotalIncomes(Vector<Vector<Object>> data) {
		/**
		 * refresh and re-calculate the total incomes after the end of
		 * the table model refresh.
		 */
		int sum = 0;
		Vector<Object> row;
		for(int i=0; i<data.size(); i++) {
			row = data.get(i);
			sum += (int)row.get(1);
		}
		totalIncomes = sum;
	}
	
	public void setSelectedYear(int year) {
		selectedYear = year;
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
	
	
	private static class DateComperator implements Comparator<Vector<Object>> 
	{

		@Override
		public int compare(Vector<Object> o1, Vector<Object> o2) {
			/**
			 * Assumption: Day and Year are equals at all items!
			 * The only difference might be the month, so considering only for that case..
			 */
			String date1 = (String)o1.get(0);
			String date2 = (String)o2.get(0);
			int m1 = Integer.valueOf(date1.substring(date1.indexOf('/')+1, date1.lastIndexOf('/')));
			int m2 = Integer.valueOf(date2.substring(date2.indexOf('/')+1, date2.lastIndexOf('/')));
			return m1 > m2 ? 1 : m1 < m2 ? -1 : 0;
		}
	}
}