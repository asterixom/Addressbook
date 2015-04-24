package test.java;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.addressbook.Address;
import de.addressbook.Liste;

public class DBTest {

	@Before
	public void setUp() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		Address address = new Address();
		address.setName("Test");
		address.setFirstname("JUnit");
		address.setEmail("test@example.com");
		address.setPhone("0123/4567890");
		assertTrue(address.save());
		assertFalse(address.getId()==-1);
		HashMap<String, String[]> querys = new HashMap<>();
		querys.put("id", new String[]{address.getId()+""});
		ArrayList<String[]> liste = Liste.gebeListe(querys);
		assertTrue(liste.size()==1);
		assertTrue(liste.get(0)[0].equals(address.getId()+""));
		assertTrue(liste.get(0)[1].equals("Test"));
		assertTrue(liste.get(0)[2].equals("JUnit"));
		address.delete();
		liste = Liste.gebeListe(querys);
		assertTrue(liste.isEmpty());
	}
	
	@After
	public void tearDown(){
		Address.saveAll();
	}

}
