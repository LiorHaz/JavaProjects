package com.mygym.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public final class Shift 
{
	private  String entryHour, quitHour,date;
	private  double totalHour;
	private  static Shift instance=null;
	private static DBConnection dbInstance;
	private LocalDateTime myDateObj;
	private  DateTimeFormatter myFormatObj;
	

	private Shift() {
		 myDateObj = LocalDateTime.now();  
		 myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); 
	}

	public static Shift getInstance() {
		if(instance==null)
			instance=new Shift();
		return instance;
	}

	public boolean startShift(long currID)  {
		//SQL query to add entry hour of the specific worker to the table
		ResultSet rs = null;
		entryHour = myDateObj.format(myFormatObj);
		String[] s=entryHour.split(" ");
		date=s[0];
		entryHour=s[1];
		try {
			dbInstance=DBConnection.getInstance();
			dbInstance.openConnection();
			//Check if the worker exists
			rs=dbInstance.executeQuery("select * from workers where id='"+currID+"'");
			if(!rs.next())
				return false;
			//check if the worker is already in shift
			rs=dbInstance.executeQuery("select end_time from shifts_report where worker_id='"+currID+"' and date='"+date+"'");
			if(rs.next())
				return false;
			//add entry hour to the database for the current worker
			int month = Calendar.getInstance().get(Calendar.MONTH)+1;
			int year = Calendar.getInstance().get(Calendar.YEAR);
			dbInstance.executeUpdate("INSERT INTO shifts_report "
					+ " VALUES('"+currID+"', '"+date+"', '"+entryHour+"', NULL, 0, "+month+","+year+")");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			dbInstance.closeConnection();	
		}
		return true;

	}


	public boolean endShift(long ID) throws ClassNotFoundException, SQLException {
		ResultSet rs = null;
		//calculating quit hour
		LocalDateTime myDateObj = LocalDateTime.now();  
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		quitHour = myDateObj.format(myFormatObj);
		String[] s=quitHour.split(" ");
		quitHour=s[1];
		date=s[0];
		try {
			dbInstance=DBConnection.getInstance();
			dbInstance.openConnection();
			//check if the worker exists
			rs=dbInstance.executeQuery("select * from workers where id='"+ID+"'");
			if(!rs.next())
				return false;
			//check if the worker is already started a shift today
			rs=dbInstance.executeQuery("select * from shifts_report where worker_id='"+ID+"' and date='"+date+"'");
			if(!rs.next())
				return false;
			//check if the worker already finished his shift today
			rs=dbInstance.executeQuery("select end_time from shifts_report where worker_id='"+ID+"' and date='"+date+"'");
			String endTime=rs.getString("end_time");
			if(!(endTime==null))
				return false;
			//calculating total hours per shift for the worker
			rs=dbInstance.executeQuery("select * from shifts_report where worker_id='"+ID+"' and date='"+date+"'");
			entryHour=rs.getString("begin_time");
			String entryDate=rs.getString("date");
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date d1, d2;
			totalHour=0.0;
			try {
				d1 = format.parse(entryDate+" "+entryHour);
				d2 = format.parse(myDateObj.format(myFormatObj)); 
				totalHour = d2.getTime() - d1.getTime();
				totalHour = (totalHour / 1000) / 3600;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			//update quit hour and total hour in database
			dbInstance.executeUpdate("update shifts_report set end_time='"+quitHour+"',total='"+totalHour+"' where worker_id='"+ID+"' and date='"+date+"'");	
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			dbInstance.closeConnection();
		}
		return true;
	}
}