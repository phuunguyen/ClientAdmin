package com.example.clientadmin.Object;

public class Rating {


    String id_Store;
    String id_User;
    String comment;
    float rating;

    public Rating() {
    }

    public Rating(String id_Store, String id_User, String comment, float rating) {
        this.id_Store = id_Store;
        this.id_User = id_User;
        this.comment = comment;
        this.rating = rating;
    }

    public String getId_Store() {
        return id_Store;
    }

    public void setId_Store(String id_Store) {
        this.id_Store = id_Store;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}