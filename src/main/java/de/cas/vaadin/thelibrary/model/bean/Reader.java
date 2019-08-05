package de.cas.vaadin.thelibrary.model.bean;

/**
 * @author mate.biro
 * Bean class for Reader object(s)
 */
public class Reader implements DatabaseBean {
	
	
	//TODO: maybe address class datatype?
	private String name, address, email;
	private Integer Id;
	//TODO: maybe phone number builder class?
	private Long phoneNumber;
	public static final String DBname = "reader.db";
	
	
	public Reader() {
		
	}


	public Reader(String name, String address, String email, Integer id, Long phoneNumber) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		Id = id;
		this.phoneNumber = phoneNumber;
	}
	
	public Reader(String name, String address, String email, Long phoneNumber) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	public Integer getId() {
		return Id;
	}


	public void setId(Integer id) {
		Id = id;
	}


	public Long getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
}
