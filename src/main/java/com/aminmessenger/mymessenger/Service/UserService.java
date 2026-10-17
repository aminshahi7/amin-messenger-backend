package com.aminmessenger.mymessenger.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aminmessenger.mymessenger.Entity.User;
import com.aminmessenger.mymessenger.Repository.UserRepository;

@Service 
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    public List<User> getAll(){
        return userRepository.findAll();
    };
    
}
