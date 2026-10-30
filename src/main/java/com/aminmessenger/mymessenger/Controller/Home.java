package com.aminmessenger.mymessenger.Controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aminmessenger.mymessenger.Cookie.AuthCookieService;
import com.aminmessenger.mymessenger.Entity.User;
import com.aminmessenger.mymessenger.Jwt.JwtService;
import com.aminmessenger.mymessenger.Service.UserService;
import com.aminmessenger.mymessenger.dto.LoginUserRequestDto;
import com.aminmessenger.mymessenger.dto.SignUpUserRequestDto;
import com.aminmessenger.mymessenger.dto.SignUpUserResponseDto;


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
    public ResponseEntity<SignUpUserResponseDto> signup(
        @RequestBody SignUpUserRequestDto userDto
    )throws IOException{  
        
        User user = userService.save(userDto);

        SignUpUserResponseDto resDto = new SignUpUserResponseDto(user.getUsername(), user.getFirstnName(), user.getLastName(), user.getEmail());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(resDto);
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequestDto reqDto){

        try {
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(reqDto.getEmail(), reqDto.getPassword()));

            String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
            String jwt = jwtService.generateToken(authentication.getName());

            return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, authCookieService.createCookie(jwt).toString())
            .body(Map.of("email", authentication.getName(), "role", role));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "E-Mail oder Passwort falsch"));
        }
        
    }
    
}
