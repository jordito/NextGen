package model;

public class Customer {
	String email;
	String name;
	String address;
	String nif;
	
	public Customer(String email, String name, String address, String nif) {
		super();
		this.email = email;
		this.name = name;
		this.address = address;
		this.nif = nif;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}
}
