package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

public class UserProfile {
    private String userId;
    private String name;
    private String lastname;
    private String email;
    private String phone;

    public UserProfile(String userId, String name, String lastname, String email, String phone){
        this.userId = userId;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    public UserProfile() {}

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getLastname(){
        return lastname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }
}
