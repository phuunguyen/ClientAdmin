package com.example.clientadmin.Object;

public class Store {
    String id_Store;
    String store_Name;
    String bossName;
    String address;
    String phone;
    String image;
    String registerDay;
    float rating;
    String userName;
    String password;

    public Store() {
    }

    public Store(String id_Store, String store_Name, String bossName, String address, String phone, String image, String registerDay, float rating, String userName, String password) {
        this.id_Store = id_Store;
        this.store_Name = store_Name;
        this.bossName = bossName;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.registerDay = registerDay;
        this.rating = rating;
        this.userName = userName;
        this.password = password;
    }

    public String getId_Store() {
        return id_Store;
    }

    public void setId_Store(String id_Store) {
        this.id_Store = id_Store;
    }

    public String getStore_Name() {
        return store_Name;
    }

    public void setStore_Name(String store_Name) {
        this.store_Name = store_Name;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRegisterDay() {
        return registerDay;
    }

    public void setRegisterDay(String registerDay) {
        this.registerDay = registerDay;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
