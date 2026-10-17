package com.aminmessenger.mymessenger.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.aminmessenger.mymessenger.Entity.User;
import com.aminmessenger.mymessenger.Service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController 
@RequestMapping("/")
public class Home {

    private UserService userService;

    public Home(UserService userService){
        this.userService = userService;
    }
    
    @GetMapping("/")
    public List<User> getMethodName() {
        return userService.getAll();
    }
    
}
