package com.aminmessenger.mymessenger.Jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


import com.aminmessenger.mymessenger.Cookie.AuthCookieService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class JwtFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final AuthCookieService cookie;

    public JwtFilter(JwtService jwtService, AuthCookieService cookie){
        this.jwtService = jwtService;
        this.cookie = cookie;
    }

    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = readToken(request);

        if(token != null){
            try{
            Claims claims = jwtService.parseToken(token);
            String role = claims.get("role", String.class);

            var auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), role);
            SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException | IllegalArgumentException e){
            response.setHeader("Set-Cookie", cookie.deletCookie().toString());
            }
        }

        chain.doFilter(request, response);
    }

    private String readToken(HttpServletRequest req){
        
        if(req.getCookies() == null) return null;
        for(Cookie c : req.getCookies()){
            if(AuthCookieService.NAME.equals(c.getName())){
                return c.getValue();
            }
        }
        return null;
    }
    
}
