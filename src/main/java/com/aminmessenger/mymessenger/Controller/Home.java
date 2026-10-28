package com.aminmessenger.mymessenger.Controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aminmessenger.mymessenger.Entity.User;
import com.aminmessenger.mymessenger.Service.UserService;
import com.aminmessenger.mymessenger.dto.UserRequestDto;
import com.aminmessenger.mymessenger.dto.UserResponseDto;


@RestController 
@RequestMapping("/")
public class Home {

    private final UserService userService;

    public Home(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
    }
    
    @PostMapping("/api/login")
    public ResponseEntity<UserResponseDto> signup(
        @RequestBody UserRequestDto userDto
    )throws IOException{  
        
        User user = userService.save(userDto);

        UserResponseDto resDto = new UserResponseDto(user.getUsername(), user.getFirstnName(), user.getLastName(), user.getEmail());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(resDto);
    }
}
