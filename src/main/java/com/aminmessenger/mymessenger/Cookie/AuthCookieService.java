package com.aminmessenger.mymessenger.Cookie;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component 
public class AuthCookieService {
    
    public static final String NAME = "token";

    @Value("${app.cookie.secure}")     private boolean secure;
    @Value("${app.cookie.same-site}") private String sameSite;
    @Value("${jwt.expiration}")    private long expiration;

    private ResponseCookie.ResponseCookieBuilder base(String value){
        return ResponseCookie.from(NAME, value)
            .httpOnly(true)
            .secure(secure)
            .sameSite(sameSite)
            .path("/");
    }

    public ResponseCookie createCookie(String jwt){
        return base(jwt).maxAge(Duration.ofMillis(expiration)).build();
    }

    public ResponseCookie deleteCookie(){
        return base("").maxAge(0).build();
    }
}
