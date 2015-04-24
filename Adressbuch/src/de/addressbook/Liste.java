package de.addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Liste {
	public static ArrayList<String[]> gebeListe(Map<String,String[]> querys) {
		ArrayList<String[]> liste = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT id,name,christianname FROM address");
		//System.out.println(querys);
		if(querys!=null && !querys.isEmpty()){
			query.append(" WHERE");
			for(Entry<String,String[]> entry : querys.entrySet()){
				for(String value : entry.getValue()){
					query.append(" "+entry.getKey()+" LIKE '"+value+"' AND");
				}
				
			}
			query.append(" TRUE");
			//System.out.println("Query: ["+query.toString()+"]");
		}
		Connection conn;
		try {
			conn = DriverManager.getConnection(Main.CON_INFO);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query.toString());
			while(result.next()){
				liste.add(new String[]{result.getString("id"),result.getString("name"),result.getString("christianname")});
			}
			result.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}
}
