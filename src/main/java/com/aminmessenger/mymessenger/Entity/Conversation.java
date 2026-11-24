package com.aminmessenger.mymessenger.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "conversations")
public class Conversation {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;

    private User user;

    private User contact;

    public void serUser(User user ){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public void setContact(User contact){
        this.contact = contact;
    }

    public User getContact(){
        return this.contact;
    }

}
