package de.addressbuch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class Liste {
	public ArrayList<String[]> gebeListe(Map<String,String> querys) {
		ArrayList<String[]> liste = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT id,name,christianname FROM address");
		if(querys!=null && !querys.isEmpty()){
			query.append(" WHERE");
			for(Entry<String,String> entry : querys.entrySet()){
				query.append(" "+entry.getKey()+" LIKE '"+entry.getValue()+"' AND");
			}
			query.append(" TRUE");
			System.out.println("Query: ["+query.toString()+"]");
		}
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook?user=root&password=password");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query.toString());
			while(result.next()){
				liste.add(new String[]{result.getString("id"),result.getString("name"),result.getString("christianname")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}
}
