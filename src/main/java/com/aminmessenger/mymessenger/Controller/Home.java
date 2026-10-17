package com.aminmessenger.mymessenger.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.aminmessenger.mymessenger.Service.UserService;
import com.aminmessenger.mymessenger.dto.UserResponseDto;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserResponseDto>> getMethodName() {

        List<UserResponseDto> users = userService.getAll()
        .stream()
        .map(user -> new UserResponseDto(user.getUsername(), user.getFirstnName(), user.getLastName(), user.getEmail()))
        .toList();

        return ResponseEntity.ok(users);
    }   
    
}
