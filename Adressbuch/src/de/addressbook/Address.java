package de.addressbook;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Address {

	private int id=-1;
	private String name="";
	private String firstname="";
	private String addressform="";
	private String email="";
	private String phone="";
	private String mobile="";
	private String street="";
	private int number=0;
	private String city="";
	private String postcode="";
	private String country="";
	private Date birthday=new Date(0);
	private static HashMap<Integer,Address> addresses = new HashMap<>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getAddressform() {
		return addressform;
	}

	public void setAddressform(String addressform) {
		this.addressform = addressform;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobil) {
		this.mobile = mobil;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getBirthdate() {
		return birthday;
	}

	public void setBirthdate(Date birthdate) {
		this.birthday = birthdate;
	}

	public Address() {
	}
	
	public static Address read(int id){
		if(addresses.containsKey(id)){
			return addresses.get(id);
		}
		Address address = new Address();
		addresses.put(id, address);
		try {
			Connection conn = DriverManager.getConnection(Main.CON_INFO);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM address WHERE id="+id);
			if(result.next()){
				copyToObj(result,address);
			}else{
				address=null;
			}
			result.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return address;
	}
	
	public void reload(){
		try {
			Connection conn = DriverManager.getConnection(Main.CON_INFO);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM address WHERE id="+id);
			if(result.next()){
				copyToObj(result,this);
			}
			result.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void copyToObj(ResultSet result,Address address){
		try {
			address.id = result.getInt("id");
			address.name = result.getString("name");
			address.firstname = result.getString("christianname");
			address.email = result.getString("email");
			address.addressform = result.getString("addressform");
			address.phone = result.getString("phone");
			address.mobile = result.getString("mobile");
			address.street = result.getString("street");
			address.number = result.getInt("number");
			address.city = result.getString("city");
			address.postcode = result.getString("postcode");
			address.country = result.getString("country");
			address.birthday = result.getDate("birthday");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean save(){
		try {
			Connection conn = DriverManager.getConnection(Main.CON_INFO);
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM address WHERE id=?");
			ps1.setInt(0, id);
			ResultSet result = ps1.executeQuery();
			if(result.next()){
				System.out.println("Update!");
				PreparedStatement ps2 = conn.prepareStatement("UPDATE address SET name='?', christianname='?', email='?', addressform='?', phone='?', "
						+ "mobile='?', street='?', number='?', city='?', postcode='?', country='?', birthday='?' WHERE id=?");
				ps2.setString(0, name);
				ps2.setString(1, firstname);
				ps2.setString(2, email);
				ps2.setString(3, addressform);
				ps2.setString(4, phone);
				ps2.setString(5, mobile);
				ps2.setString(6, street);
				ps2.setInt(7, number);
				ps2.setString(8, city);
				ps2.setString(9, postcode);
				ps2.setString(10, country);
				ps2.setDate(11, birthday);
				ps2.setInt(12, id);
				ps2.executeUpdate();
				ps2.close();
			}else{
				System.out.println("Insert!");
				PreparedStatement ps2 = conn.prepareStatement("INSERT INTO address SET name='?', christianname='?', email='?', addressform='?', phone='?', "
						+ "mobile='?', street='?', number='?', city='?', postcode='?', country='?', birthday='?'",Statement.RETURN_GENERATED_KEYS);
				ps2.setString(0, name);
				ps2.setString(1, firstname);
				ps2.setString(2, email);
				ps2.setString(3, addressform);
				ps2.setString(4, phone);
				ps2.setString(5, mobile);
				ps2.setString(6, street);
				ps2.setInt(7, number);
				ps2.setString(8, city);
				ps2.setString(9, postcode);
				ps2.setString(10, country);
				ps2.setDate(11, birthday);
				ps2.setInt(12, id);
				ps2.executeUpdate();
				ResultSet res = ps2.getGeneratedKeys();
				if(res.next()){
					id=res.getInt(1);
				}
				ps2.close();
			}
			result.close();
			ps1.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id!=-1;
	}
	
	public void delete() {
		try {
			Connection conn = DriverManager.getConnection(Main.CON_INFO);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM address WHERE id='"+id+"'");
			addresses.remove(id);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveAll(){
		for(Address address : addresses.values()){
			address.save();
		}
	}
	
	@Override
	public String toString() {
		return id+"\t"+name+"\t"+firstname+"\t"+email+"\t"+birthday;
	}
}
