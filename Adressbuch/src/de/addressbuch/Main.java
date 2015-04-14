package de.addressbuch;

import java.util.HashMap;

public class Main {

	
	public static void main(String[] args) {
		Address address = Address.read(1);
		System.out.println(address.toString());
		Liste liste = new Liste();
		HashMap<String,String> querys = new HashMap<>();
		querys.put("email", "%@gmx.de");
		querys.put("christianname", "%nn%");
		for(String[] wert : liste.gebeListe(querys)){
			System.out.println(wert[0]+"\t"+wert[1]+"\t"+wert[2]);
		}
//		address.setId(0);
//		address.setEmail("m@mueller.de");
//		address.save();
	}
}
