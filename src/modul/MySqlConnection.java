package modul;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;


public class MySqlConnection {
	
	public MySqlConnection() {
	}
	
	private String hostName = "localhost:3306";
	private String dbName = "telephonist_infor";
	private String userName = "root";
	private String password = "";
	
	private String connectionURL = "jdbc:mysql://"+hostName+"/"+dbName;
	
	
	public ResultSet rs = null;
	public ResultSet getInformation ()  {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(connectionURL, userName, password);
			
			String query = "SELECT idTelephonist, timeStart, timeFinish, timeReceive, phoneNumber FROM telephonist ";
			Statement stmt  = connection.createStatement();
            rs    = stmt.executeQuery(query);
            
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}
	
	public void insertTelephonist (int idTelephonist, Time timeStart, Time timeFinish, long timeReceive, String phoneNumber) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(connectionURL, userName, password);
			System.out.println("connect successfully");
			String INSERT_QUERY = "INSERT INTO telephonist (idTelephonist, timeStart, timeFinish, timeReceive, phoneNumber) "
					+ "VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY); 
            stmt.setInt(1, idTelephonist);
            stmt.setTime(2, timeStart);
            stmt.setTime(3, timeFinish);
            stmt.setLong(4, timeReceive);
            stmt.setString(5, phoneNumber);
            
            int i = stmt.executeUpdate();
            if (i > 0) {
            	System.out.println("insert successfully!");
            } else {
            	System.out.println("Not insert database!");
            }			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteInfor() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(connectionURL, userName, password);
			
			Statement stmt = connection.createStatement();
			String DELETE_QUERY = "DELETE FROM telephonist ";
			int deletedRows = stmt.executeUpdate(DELETE_QUERY);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
