package de.addressbuch;

public class Main {

	
	public static void main(String[] args) {
		Address address = Address.read(1);
		System.out.println(address.toString());
//		address.setId(0);
		address.setEmail("m@mueller.de");
		address.save();
	}
}
