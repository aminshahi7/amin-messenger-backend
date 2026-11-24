package com.aminmessenger.mymessenger.Entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "messages")
public class Message {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    private Date createdAt;

    @ManyToOne 
    @JoinColumn (name = "conversation_id")
    private Conversation conversation;

    @ManyToOne 
    @JoinColumn (name = "user_id")
    private User sender;

    public Long getId(){
        return this.id;
    }

    public void setContent(String text){
        this.content = text;
    }

    public String getContent(){
        return this.content;
    }

    public void setDate(Date date){
        this.createdAt = date;
    }

    public Date getCreatedAt(){
        return this.createdAt;
    }

    public Long getConversationId(){
        return conversation.getId();
    }
    
    public Long getSenderId(){
        return sender.getId();
    }
}
