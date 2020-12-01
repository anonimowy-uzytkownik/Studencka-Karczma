package com.example.projekt;

public class Uzytkownik {

    String email;
    String avatar;

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public Uzytkownik(String email) {
        this.email = email;
    }

    public Uzytkownik(String email, String avatar) {
        this.email = email;
        this.avatar = avatar;
    }
}
