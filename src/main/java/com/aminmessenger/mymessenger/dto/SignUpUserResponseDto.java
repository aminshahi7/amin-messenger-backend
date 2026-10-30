package com.aminmessenger.mymessenger.dto;

public class SignUpUserResponseDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    
    public SignUpUserResponseDto(String username, String firstName, String lastName, String email){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }
}
