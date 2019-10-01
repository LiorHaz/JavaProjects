package com.mygym.models;
import java.sql.*;


public final class DBConnection
{
	private static DBConnection dbConnection;
	private Connection connection;
	
	public static DBConnection getInstance() throws ClassNotFoundException {
		if(dbConnection==null) {
			synchronized(DBConnection.class) {
				if(dbConnection == null) //Double check..
					dbConnection = new DBConnection();
			}
		}
		return dbConnection;
	}
	
	private DBConnection() {
		//Singleton private connstructor..
	}
	
	public void openConnection() {	
		try {
			// create a database connection
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:res\\MyGym.db");
		}
		catch(SQLException | ClassNotFoundException ex) {  
			ex.printStackTrace();
		}   
	}
	
	public void closeConnection() {
		try {
			if(connection != null)
				connection.close();
		}
		catch(SQLException e) {            
			System.err.println(e); 
		}
	}

	public ResultSet executeQuery(String query) throws SQLException  { 
		/**
		 * For select queries.
		 */
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query); 
		return resultSet;
	}
	
	public int executeUpdate(String query) throws SQLException  { 
		/**
		 * For insert, update and delete queries.
		 */
		Statement statement = connection.createStatement();
		int r = statement.executeUpdate(query);
		return r;
	}
	
	public int executePreparedStatementUpdate(String query, Object...values) throws SQLException {
		/**
		 *  For insert, update and delete queries with parameters for cleaner code.
		 */
		PreparedStatement ps = connection.prepareStatement(query);
		for(int i=0; i<values.length; i++)
			ps.setObject(i+1, values[i]);
		return ps.executeUpdate();
	}
}