package de.addressbuch;

import java.util.HashMap;

public class Main {

	
	public static void main(String[] args) {
		Address address = Address.read(1);
		System.out.println(address.toString());
		HashMap<String,String[]> querys = new HashMap<>();
		querys.put("email", new String[]{"%@gmx.de"});
		querys.put("christianname", new String[]{"%nn%"});
		for(String[] wert : Liste.gebeListe(querys)){
			System.out.println(wert[0]+"\t"+wert[1]+"\t"+wert[2]);
		}
//		address.setId(0);
//		address.setEmail("m@mueller.de");
//		address.save();
	}
}
