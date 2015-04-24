package de.addressbuch;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
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
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM address WHERE id="+id);
			if(result.next()){
				System.out.println("Update!");
				stmt.executeUpdate("UPDATE address SET name='"+name+"', christianname='"+firstname+"', email='"+email+"', addressform='"+addressform+"', phone='"+phone+"', "
						+ "mobile='"+mobile+"', street='"+street+"', number='"+number+"', city='"+city+"', postcode='"+postcode+"', country='"+country+"', birthday='"+birthday+"' WHERE id="+id);
			}else{
				System.out.println("Insert!");
				stmt.executeUpdate("INSERT INTO address SET name='"+name+"', christianname='"+firstname+"', email='"+email+"', addressform='"+addressform+"', phone='"+phone+"', "
						+ "mobile='"+mobile+"', street='"+street+"', number='"+number+"', city='"+city+"', postcode='"+postcode+"', country='"+country+"', birthday='"+birthday+"'",Statement.RETURN_GENERATED_KEYS);
				ResultSet res = stmt.getGeneratedKeys();
				if(res.next()){
					id=res.getInt(1);
				}
			}
			result.close();
			stmt.close();
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
