package com.blurspace.doov.Models;

public class Userauth {

    private String Username;

    private String Email;
    private String Avatar;

    public Userauth() {

    }

    public Userauth(String Username,String Email,String Avatar) {

        this.Username=Username;
        this.Email=Email;
        this.Avatar=Avatar;
    }
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }



}
