package in.co.khuranasales.khuranasales;

import java.util.ArrayList;

public class Promoter {
    String name;
    String contact;
    String id;
    String email;
    String total_products;
    String total_amount_items_sold;
    ArrayList<Product> products = new ArrayList<>();
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal_products() {
        return total_products;
    }

    public void setTotal_products(String total_products) {
        this.total_products = total_products;
    }

    public String getTotal_amount_items_sold() {
        return total_amount_items_sold;
    }

    public void setTotal_amount_items_sold(String total_amount_items_sold) {
        this.total_amount_items_sold = total_amount_items_sold;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

}
