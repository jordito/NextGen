package model;

import java.util.Date;

public class Order {
    int id;
    String customer;
    int product;
    int quantity;
    Date date;
    
    public Order(int id, String customer, int product, int quantity, Date date) {
    	super();
    	this.id = id;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
