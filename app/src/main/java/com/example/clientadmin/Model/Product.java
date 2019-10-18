package com.example.clientadmin.model;

public class Product {
    private String id_product;
    private String product_image;
    private String product_name;
    private double price;

    public Product() {
    }

    public Product(String id_product, String product_image, String product_name, double price) {
        this.id_product = id_product;
        this.product_image = product_image;
        this.product_name = product_name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}