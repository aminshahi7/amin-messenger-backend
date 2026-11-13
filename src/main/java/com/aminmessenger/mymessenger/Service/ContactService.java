package com.aminmessenger.mymessenger.Service;

import org.springframework.stereotype.Service;

import com.aminmessenger.mymessenger.Entity.User;
import com.aminmessenger.mymessenger.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service 
public class ContactService {

    private final UserRepository userRepository;
    
    public ContactService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional 
    public void addContact(Long userId, Long contactId){
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("user not found"));

        User contact = userRepository.findById(contactId)
            .orElseThrow(() -> new RuntimeException("user not found"));

        user.addContact(contact);   

        userRepository.save(user);
    }

    @Transactional 
    public void removeContact(Long userId, Long contactId){
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("user not found"));

        User contact = userRepository.findById(contactId)
            .orElseThrow(() -> new RuntimeException("user not found"));

        user.removeContact(contact);

        userRepository.save(user);
    }
    
}
