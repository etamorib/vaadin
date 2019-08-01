package de.cas.vaadin.thelibrary.bean;

/**
 * @author mate.biro
 * Bean class for Reader object(s)
 */
public class Reader {
	
	
	//TODO: maybe address class datatype?
	private String firstName, lastName, address, email;
	private int Id;
	//TODO: maybe phone number builder class?
	private long phoneNumber;
	
	public Reader(String firstName, String lastName, String address, 
			String email, int id, long phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		Id = id;
		this.phoneNumber = phoneNumber;
	}
	
	public Reader() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
	

}
