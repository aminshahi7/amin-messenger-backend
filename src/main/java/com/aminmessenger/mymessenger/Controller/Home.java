package com.aminmessenger.mymessenger.Controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aminmessenger.mymessenger.Cookie.AuthCookieService;
import com.aminmessenger.mymessenger.Entity.User;
import com.aminmessenger.mymessenger.Jwt.JwtService;
import com.aminmessenger.mymessenger.Service.UserService;
import com.aminmessenger.mymessenger.dto.UserRequestDto;
import com.aminmessenger.mymessenger.dto.UserResponseDto;


@RestController 
@RequestMapping("/")
public class Home {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final AuthCookieService authCookieService;
    @Value ("${app.cookie.secure}") private String cookieName;
    @Value ("${jwt.expiration}") private Long expiration;

    public Home(UserService userService, JwtService jwtService, AuthenticationManager authManager, AuthCookieService authCookieService){
        this.userService = userService;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.authCookieService = authCookieService;
    }
    
    @PostMapping("/api/signup")
    public ResponseEntity<UserResponseDto> signup(
        @RequestBody UserRequestDto userDto
    )throws IOException{  
        
        User user = userService.save(userDto);

        UserResponseDto resDto = new UserResponseDto(user.getUsername(), user.getFirstnName(), user.getLastName(), user.getEmail());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(resDto);
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto reqDto){

        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(reqDto.getEmail(), reqDto.getPassword()));

        String token = jwtService.generateToken(reqDto.getEmail());

        ResponseCookie cookie = authCookieService.createCookie(token);

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(Map.of("email", auth.getName()));
    }
    
}
