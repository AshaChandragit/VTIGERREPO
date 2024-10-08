package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class ToReadDataFromDatabase {

	public static void main(String[] args) throws SQLException {
		//Step1: Load/ register the database driver
		Driver driverRef=new Driver();
		DriverManager.registerDriver(driverRef);
		
		//Step2: Connect to Database
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/advancedselenium","root","Pratham@06");
		//Step3: Create SQL statement
		Statement stat = conn.createStatement();
					
		//Step4: Execute the Select query & get the result
		ResultSet result = stat.executeQuery("select * from StudentsOfAdvSel");
		while(result.next()) {
			System.out.println(result.getInt(1)+"\t"+result.getString(2)+"\t"+result.getString(3)+"\t"+result.getString(4));;
		}
		//Step5: Close the connection
		conn.close();
		System.out.println("Disconnected the Database");

	}

}
