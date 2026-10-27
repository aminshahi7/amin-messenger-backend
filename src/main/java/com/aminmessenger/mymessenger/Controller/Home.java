package com.aminmessenger.mymessenger.Controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aminmessenger.mymessenger.Entity.User;
import com.aminmessenger.mymessenger.Service.UserService;
import com.aminmessenger.mymessenger.dto.UserRequestDto;


@RestController 
@RequestMapping("/")
public class Home {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public Home(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping(
        value="/api/login",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> regloginister(
            @RequestPart("user") UserRequestDto dto,
            @RequestPart(value = "profilePicture", required = false)
            MultipartFile profilePicture
    ) throws IOException {

        User user = userService.registerUser(dto, profilePicture);

        return ResponseEntity.ok(
                "User successfully registered with ID: " + user.getId()
        );
    }  
    
}
