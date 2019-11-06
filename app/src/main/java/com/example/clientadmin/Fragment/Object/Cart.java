package com.example.clientadmin.Fragment.Object;

public class Cart {
    String id_DonHang;
    String id_User;
    String id_Store;
    String id_Product;


    int quantity;
    int price;
    String date;


    String check;
    String delivery;
    String finish;

    public Cart(){}

    public Cart(String id_DonHang, String id_User, String id_Store, String id_Product, int quantity, int price, String date, String check, String delivery, String finish) {
        this.id_DonHang = id_DonHang;
        this.id_User = id_User;
        this.id_Store = id_Store;
        this.id_Product = id_Product;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.check = check;
        this.delivery = delivery;
        this.finish = finish;
    }

    public String getId_DonHang() {
        return id_DonHang;
    }

    public void setId_DonHang(String id_DonHang) {
        this.id_DonHang = id_DonHang;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getId_Store() {
        return id_Store;
    }

    public void setId_Store(String id_Store) {
        this.id_Store = id_Store;
    }

    public String getId_Product() {
        return id_Product;
    }

    public void setId_Product(String id_Product) {
        this.id_Product = id_Product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }
}
