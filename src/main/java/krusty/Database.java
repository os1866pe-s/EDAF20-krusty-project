package krusty;

import org.sqlite.SQLiteConfig;
import spark.Request;
import spark.Response;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Database {

	/**
	 * The database connection.
	 */
	private Connection conn;

	private String connectionString = "jdbc:sqlite:src/main/resources/public/krusty_database_v2.db";

	/**
	 * Connects to the local sqlite database
	 */
	public void connect(boolean foreignKeysOn) {
		try {
			SQLiteConfig config = new SQLiteConfig();
			config.enforceForeignKeys(foreignKeysOn);
			conn = DriverManager.getConnection(connectionString, config.toProperties());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getCustomers(Request req, Response res) {
		String query = "SELECT * FROM Customers";

		try (PreparedStatement statement = conn.prepareStatement(query)) {
			return Jsonizer.toJson(statement.executeQuery(), "customers");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "{}";
	}

	public String getRawMaterials(Request req, Response res) {
		String query = "SELECT * FROM Ingredients";


		try (PreparedStatement statement = conn.prepareStatement(query)) {
			return Jsonizer.toJson(statement.executeQuery(), "raw-materials");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "{}";
	}

	public String getCookies(Request req, Response res) {
		String query = "SELECT * FROM Cookies";


		try (PreparedStatement statement = conn.prepareStatement(query)) {
			return Jsonizer.toJson(statement.executeQuery(), "cookies");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "{\"cookies\":[]}";
	}

	public String getRecipes(Request req, Response res) {
		String query = "SELECT * FROM IngredientsInCookies";

		try (PreparedStatement statement = conn.prepareStatement(query)) {
			return Jsonizer.toJson(statement.executeQuery(), "recipes");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "{}";
	}

	public String getPallets(Request req, Response res) {
		String query = "SELECT Pallets.cookie, Pallets.blocked, Pallets.produced_timestamp as production_date, Customers.name as customer FROM Pallets Join Orders USING(order_number) Join Customers USING(name)";

		List<String> values = new ArrayList<>();

		String from = req.queryParams("from");
		String to = req.queryParams("to");
		String cookie = req.queryParams("cookie");
		String blockedString = req.queryParams("blocked");

		boolean aParameterExists = from != null || to != null ||
				cookie != null || blockedString != null;

		if (aParameterExists) {
			query += " WHERE";
		}
		if (from != null) {
			query += " produced_timestamp >= ? AND";
			values.add(from);
		}
		if (to != null) {
			query += " produced_timestamp <= ? AND";
			values.add(to);
		}
		if (cookie != null) {
			query += " cookie = ? AND";
			values.add(cookie);
		}
		if (blockedString != null) {
			query += " blocked = ? AND";
			values.add(blockedString);
		}

		//Subtract the extra ' AND'
		if (aParameterExists) {
			query = query.substring(0, query.length() - 4);
		}

		query += " ORDER BY produced_timestamp";

		try (PreparedStatement statement = conn.prepareStatement(query)) {

			for (int i = 0; i < values.size(); i++) {
				statement.setString(i + 1, values.get(i));
			}
			ResultSet result = statement.executeQuery();

			return Jsonizer.toJson(result, "pallets");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "{\"pallets\":[]}";
	}

	public String reset(Request req, Response res) {

		connect(false);
		removeAllRowsInTable("Customers");
		removeAllRowsInTable("Ingredients");
		removeAllRowsInTable("Pallets");
		removeAllRowsInTable("Orders");
		removeAllRowsInTable("Cookies");

		insertCookie("Amneris");
		insertCookie("Berliner");
		insertCookie("Nut cookie");
		insertCookie("Nut ring");
		insertCookie("Tango");
		insertCookie("Almond delight");

		insertCustomer("Bjudkakor AB","Ystad");
		insertCustomer("Finkakor AB", "Helsingborg");
		insertCustomer("Gastkakor AB", "Hassleholm");
		insertCustomer("Kaffebrod AB", "Landskrona");
		insertCustomer("Kalaskakor AB", "Trelleborg");
		insertCustomer("Partykakor AB", "Kristianstad");
		insertCustomer("Skanekakor AB", "Perstorp");
		insertCustomer("Smabrod AB", "Malmo");

		insertIngredient("Bread crumbs",500000, "g");
		insertIngredient("Butter",500000, "g");
		insertIngredient("Chocolate",500000, "g");
		insertIngredient("Chopped almonds",500000, "g");
		insertIngredient("Cinnamon",500000, "g");
		insertIngredient("Egg whites",500000, "ml");
		insertIngredient("Eggs",500000, "g");
		insertIngredient("Fine-ground nuts",500000, "g");
		insertIngredient("Flour",500000, "g");
		insertIngredient("Ground, roasted nuts",500000, "g");
		insertIngredient("Icing sugar",500000, "g");
		insertIngredient("Marzipan",500000, "g");
		insertIngredient("Potato starch",500000, "g");
		insertIngredient("Roasted, chopped nuts",500000, "g");
		insertIngredient("Sodium bicarbonate",500000, "g");
		insertIngredient("Sugar",500000, "g");
		insertIngredient("Vanilla",500000, "g");
		insertIngredient("Vanilla sugar",500000, "g");
		insertIngredient("Wheat flour",500000, "g");

		connect(true);
		return "{}";
	}

	private void removeAllRowsInTable(String tableName){
		String query = "DELETE FROM " + tableName;

			try (PreparedStatement statement = conn.prepareStatement(query)) {

				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}


	private void insertCustomer(String name, String address){
		String query = "INSERT INTO Customers(name, address) VALUES (?,?);";

		try (PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, name);
			statement.setString(2, address);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertIngredient(String name, int amount, String unit){
		String query = "INSERT INTO Ingredients(name, amount, unit) VALUES (?,?,?);";

		try (PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, name);
			statement.setInt(2, amount);
			statement.setString(3, unit);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private void insertCookie(String name){
		String query = "INSERT INTO Cookies(name) VALUES (?);";

		try (PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, name);


			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String createPallet(Request req, Response res) {
		String cookie = req.queryParams("cookie");

		if (!cookieExists(cookie)) {
			return "{\"status\":\"unknown cookie\"}";
		}

		int orderId = createOrder();

		String query = "INSERT INTO Pallets(cookie, order_number, blocked) VALUES (?,?,?);";

		try (PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, cookie);
			statement.setInt(2, orderId);
			statement.setString(3, "no");
			statement.executeUpdate();

			int palletId = getLastInsertedId();
			if (palletId > -1) {
				makeCookiePallet_2(cookie);
				return "{\"status\":\"ok\",\"id\":" + palletId + "}";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "{\"status\":\"error\"}";
	}

	/**
	 * Alternativ 2
	 * */
	private void makeCookiePallet_2(String cookie){
		String query = "update Ingredients " +
				"SET amount = amount - ((SELECT quantity FROM IngredientsInCookies " +
				  "WHERE (Ingredients.name = IngredientsInCookies.ingredient_name) " +
				  "AND (IngredientsInCookies.cookie_name = ?)" +
				") * 54) " +
				"WHERE EXISTS (SELECT * FROM IngredientsInCookies " +
				  "WHERE (Ingredients.name = IngredientsInCookies.ingredient_name) " +
				  "AND (IngredientsInCookies.cookie_name = ?)" +
				");";

			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, cookie);
				statement.setString(2, cookie);
				statement.executeUpdate();
			}catch (SQLException e){
				e.printStackTrace();
		}
	}

	/**
	 * Alternativ 1
	 * */
	private void makeCookiePallet(String cookie) {

		Map<String, Integer> ingredientsInCookie = getIngredientsInCookie(cookie);
		String query = "UPDATE Ingredients SET amount = amount - ? WHERE name = ?";
		for (String ingredient : ingredientsInCookie.keySet()) {
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, ingredientsInCookie.get(ingredient));
				statement.setString(2, ingredient);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private Map<String, Integer> getIngredientsInCookie(String cookie) {
		Map<String, Integer> ingredientsInCookie = new HashMap<>();
		String query = "SELECT ingredient_name, quantity FROM IngredientsInCookies WHERE cookie_name = ?";

		try (PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, cookie);
			ResultSet result = statement.executeQuery();

			while (result.next()){
				ingredientsInCookie.put(result.getString("ingredient_name"),
						result.getInt("quantity") * 54);
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ingredientsInCookie;
	}

	private String getRandomCustomer() {
		String query = "SELECT name FROM Customers ORDER BY RANDOM() LIMIT 1;";
		try (PreparedStatement statement = conn.prepareStatement(query)) {
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				return result.getString("name");
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private int createOrder() {

		String query = "INSERT INTO Orders(name) VALUES (?);";
		try (PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, getRandomCustomer());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getLastInsertedId();
	}

	private int getLastInsertedId() {
		String queryID = "SELECT LAST_INSERT_ROWID() AS id;";
		try (PreparedStatement statementID = conn.prepareStatement(queryID)) {
			ResultSet result = statementID.executeQuery();
			if (result.next()) {
				return result.getInt("id");
			}
			result.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private boolean cookieExists(String cookie) {
		String query = "SELECT * FROM Cookies WHERE name = ?";
		try (PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setString(1, cookie);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				result.close();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
