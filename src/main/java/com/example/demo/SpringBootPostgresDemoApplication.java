package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootPostgresDemoApplication {

	@Value("${PG_HOST:localhost}")
	private String dbHost;
	
	@Value("${PG_PORT:5432}")
	private String dbPort;
	
	@Value("${PG_DATABASE:info_db}")
	private String dbName;
	
	@Value("${PG_USER:admin}")
	private String dbUser;
	
	@Value("${PG_PASSWORD:admin123}")
	private String dbPassword;
	
	@RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }
	
	public static JSONArray convert( ResultSet rs ) throws SQLException, JSONException {
		JSONArray json = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();

		while(rs.next()) {
			int numColumns = rsmd.getColumnCount();
			JSONObject obj = new JSONObject();

			for (int i=1; i<numColumns+1; i++) {
				String column_name = rsmd.getColumnName(i);

				if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
					obj.put(column_name, rs.getArray(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
					obj.put(column_name, rs.getInt(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
					obj.put(column_name, rs.getBoolean(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
					obj.put(column_name, rs.getBlob(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
					obj.put(column_name, rs.getDouble(column_name)); 
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
					obj.put(column_name, rs.getFloat(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
					obj.put(column_name, rs.getInt(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
					obj.put(column_name, rs.getNString(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
					obj.put(column_name, rs.getString(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
					obj.put(column_name, rs.getInt(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
					obj.put(column_name, rs.getInt(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
					obj.put(column_name, rs.getDate(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
					obj.put(column_name, rs.getTimestamp(column_name));   
				}
				else{
					obj.put(column_name, rs.getObject(column_name));
				}
			}

			json.put(obj);
		}

		return json;
	}
	
	@RequestMapping("/accounts")
	public String accounts(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		return sql("select a.*, r.* from account a, role r where a.user_id = r.user_id");
	}

	private String sql(String query) throws ClassNotFoundException, SQLException {
		Connection c = null;
		Statement s= null;
		System.out.println("PG DETAILS:");
		System.out.println("HOST: " + dbHost);
		System.out.println("PORT: " + dbPort);
		System.out.println("USER: " + dbUser);
		System.out.println("DBNAME: " + dbName);
		System.out.println("PASSWORD: " + dbPassword);
		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName, dbUser, dbPassword);
		c.setAutoCommit(false);
		s = c.createStatement();
		ResultSet rs = s.executeQuery(query);
		JSONArray json = convert(rs);
		s.close();
		c.close();
		return json.toString(2);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootPostgresDemoApplication.class, args);
	}

}
