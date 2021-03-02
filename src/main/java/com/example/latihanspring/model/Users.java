package com.example.latihanspring.model;

import javax.validation.constraints.NotEmpty;

public class Users {

    private int id;
    @NotEmpty(message = "Tidak boleh Kosong!")
    private String username, password;

    private String isLogin;
//    private int hasil;


    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsLogin() { return isLogin; }

    public void setIsLogin(String isLogin) { this.isLogin = isLogin; }

    //    public int getHasil() { return hasil; }

//    public void setHasil(int hasil) {
//        this.hasil = hasil;
//    }
}
