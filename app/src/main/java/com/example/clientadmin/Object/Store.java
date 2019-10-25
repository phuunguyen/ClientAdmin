package com.example.clientadmin.Object;

public class Store {
    String id_Store;
    String address;
    String bossName;
    String email;
    String image;
    String password;
    int phone;
    float rating;
    String registerDay;
    String store_Name;
    String username;

    public Store() {
    }

    public Store(String id_Store, String address, String bossName, String email, String image, String password, int phone, float rating, String registerDay, String store_Name, String username) {
        this.id_Store = id_Store;
        this.address = address;
        this.bossName = bossName;
        this.email = email;
        this.image = image;
        this.password = password;
        this.phone = phone;
        this.rating = rating;
        this.registerDay = registerDay;
        this.store_Name = store_Name;
        this.username = username;
    }

    public String getId_Store() {
        return id_Store;
    }

    public void setId_Store(String id_Store) {
        this.id_Store = id_Store;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getRegisterDay() {
        return registerDay;
    }

    public void setRegisterDay(String registerDay) {
        this.registerDay = registerDay;
    }

    public String getStore_Name() {
        return store_Name;
    }

    public void setStore_Name(String store_Name) {
        this.store_Name = store_Name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


