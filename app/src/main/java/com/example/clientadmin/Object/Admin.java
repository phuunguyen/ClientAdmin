package com.example.clientadmin.Object;

public class Admin {
    String Name;
    String Password;
    String id_User;


    public Admin() {
    }

    public Admin(String name, String password, String id_User) {
        Name = name;
        Password = password;
        this.id_User = id_User;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }
}
