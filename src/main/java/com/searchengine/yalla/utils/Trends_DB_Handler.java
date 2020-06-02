package com.searchengine.yalla.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Trends_DB_Handler {

	final static String Db_name = "yalla-prod";
	final static String url1 = "jdbc:mysql://localhost:3306/" + Db_name;
	final static String user = "yalladev";
	final static String password = "yalladev";
	private Connection c = null;

	static void print(Object s) {
		System.out.println(s); // helper fumction to print
	}

	public void connect() {
		try {

			setC(DriverManager.getConnection(url1, user, password));
			if (getC() != null) {
				//print("Thread " + Thread.currentThread().getName() + " Connected to the database " + Db_name);
			}

		} catch (SQLException ex) {
			print("An error occurred. Maybe user/password is invalid");
			ex.printStackTrace();
		}
	}

	public void close() {
		if (getC() != null) {
			try {
				getC().close();
				print("Thread " + Thread.currentThread().getName() + " Closed database connection " + Db_name);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				print(e.getMessage());
			}
		}
	}

	public Connection getC() {
		return this.c;
	}

	public void setC(Connection c) {
		this.c = c;
	}

	public void Insert_Row(String Name, String Country) {
		try {
			print(Name);
			String sql = null;
			sql = "Select ID,Count from Trends Where Name = ? and Country = ? ;";

			PreparedStatement pst = getC().prepareStatement(sql); // prepare insert query

			pst.setString(1, Name);
			pst.setString(2, Country);
			ResultSet rs = pst.executeQuery();
			boolean check = rs.next();
			// print(check + " check");
			if (check) {
				int count = rs.getInt(2);
				int ID = rs.getInt(1);
				// update number
				sql = "Update Trends SET Count = ? Where ID = ?;";
				pst = getC().prepareStatement(sql); // prepare insert query
				count += 1;
				pst.setInt(1, count);
				pst.setInt(2, ID);
				print(pst);
				int done = pst.executeUpdate();
				if (done > 0) {
					print("Update Row Successfully");
				}

			} else {

				pst.close();

				sql = "INSERT INTO Trends (Name , Country,Count) VALUES (?,?,?);";
				pst = getC().prepareStatement(sql); // prepare insert query
				pst.setString(1, Name);
				pst.setString(2, Country);
				pst.setInt(3, 1);
				int done = pst.executeUpdate();
				if (done > 0) {
					print("Insert Row Successfully");
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Object> GetTrends(String Country) {

		String sql = null;
		sql = "SELECT Name from Trends where Country = ? order by count Desc limit 10;";

		List<Object> response = new ArrayList<>();
		PreparedStatement pst;
		try {
			pst = getC().prepareStatement(sql);
			pst.setString(1, Country);
			ResultSet rs = pst.executeQuery();
			

			List<Object> results = new ArrayList<>();
			Dictionary searchResult = new Hashtable();
			

			while (rs.next()) {
				Dictionary<String, String> result = new Hashtable<String, String>();
				result.put("trend", rs.getString(1));
				results.add(result);
				
			}

			searchResult.put("trendResults", results);
			

			response.add(searchResult);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // prepare insert query

		return response;

	}

	public void Delete_Trends() {
		try {

			String sql = " DELETE FROM Trends; ";

			PreparedStatement pst = getC().prepareStatement(sql); // prepare select * query

			int done = pst.executeUpdate();

			if (done > 0) {
				// print("Delete " + done + " Records successfully");
			}
			sql = "ALTER TABLE Trends AUTO_INCREMENT = 1;";
			pst = getC().prepareStatement(sql);
			pst.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		Trends_DB_Handler db = new Trends_DB_Handler();
		db.connect();
		// db.Delete_Trends();
		// db.Insert_Row("sosan", "en");
		print(db.GetTrends("dd"));
		db.close();

	}
}
