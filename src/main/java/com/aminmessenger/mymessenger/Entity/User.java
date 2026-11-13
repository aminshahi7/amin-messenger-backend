package com.aminmessenger.mymessenger.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity 
@Table(name = "users")
public class User {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "first_name", nullable = false, length = 100) 
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
        name="user_contacts",
        joinColumns= @JoinColumn (name="user_id"),
        inverseJoinColumns= @JoinColumn (name="contact_id")
    )
    private Set<User> contacts = new HashSet<>();

    public Long getId(){
        return this.Id;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getFirstnName(){
        return this.firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Set<User> getContacts(){
        return contacts;
    }

    public void addContact(User user){
        contacts.add(user);
    }

    public void removeContact(User user){
        contacts.remove(user);
    }
}