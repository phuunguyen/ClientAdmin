package com.example.clientadmin.Model;

public class History {
    private String id_product;
    private String product_image;
    private String product_name;
    private String product_address;
    private int product_quantity;
    private double total_product_price;

    public History() {
    }

    public History(String id_product, String product_image, String product_name, String product_address, int product_quantity, double total_product_price) {
        this.id_product = id_product;
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_address = product_address;
        this.product_quantity = product_quantity;
        this.total_product_price = total_product_price;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_address() {
        return product_address;
    }

    public void setProduct_address(String product_address) {
        this.product_address = product_address;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public double getTotal_product_price() {
        return total_product_price;
    }

    public void setTotal_product_price(double total_product_price) {
        this.total_product_price = total_product_price;
    }
}
