package com.example.clientadmin.Model;

public class Store {
    private String idStore;
    private String image;
    private String nameStore;
    private String address;
    private double rating;

    public Store(String idStore, String image, String nameStore, String address, double rating) {
        this.idStore = idStore;
        this.image = image;
        this.nameStore = nameStore;
        this.address = address;
        this.rating = rating;
    }

    public Store(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }
}