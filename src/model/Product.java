package model;

public class Product {
	String id;
	String description;
	double price;
	double shipping;
	int status;
	
	public Product(String id, String description, double price, double shipping, int status) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.shipping = shipping;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}	
}
