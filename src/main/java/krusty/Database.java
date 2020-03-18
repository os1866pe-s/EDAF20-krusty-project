package krusty;

import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import static krusty.Jsonizer.toJson;

public class Database {

	/**
	 * The database connection.
	 */
	private Connection conn;

	/**
	 * Connects to the local sqlite database
	 * */
	public void connect() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlite:src/main/resources/public/krusty_database.db");
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	// TODO: Implement and change output in all methods below!

	public String getCustomers(Request req, Response res) {
		String query;

		String requestedName = req.queryParams("name");

		//Create a sql query builder
		//String query = new Builder().addWhere("name", requestedName).build()

		if (requestedName != null){
			query = "SELECT * FROM Customers WHERE name = ?";
		}else{
			query = "SELECT * FROM Customers";
		}

		try(PreparedStatement statement = conn.prepareStatement(query)){
			if (requestedName != null){
				statement.setString(1, requestedName);
			}
			return Jsonizer.toJson(statement.executeQuery(), "customers");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "{}";
	}

	public String getRawMaterials(Request req, Response res) {
		return "{}";
	}

	public String getCookies(Request req, Response res) {
		return "{\"cookies\":[]}";
	}

	public String getRecipes(Request req, Response res) {
		return "{}";
	}

	public String getPallets(Request req, Response res) {
		return "{\"pallets\":[]}";
	}

	public String reset(Request req, Response res) {
		return "{}";
	}

	//LAST_INSERT_ID() could be used here
	public String createPallet(Request req, Response res) {
		return "{}";
	}
}
