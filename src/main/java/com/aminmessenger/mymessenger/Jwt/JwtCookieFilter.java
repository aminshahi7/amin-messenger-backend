package com.aminmessenger.mymessenger.Jwt;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aminmessenger.mymessenger.Cookie.AuthCookieService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import java.io.IOException;
import java.net.http.HttpHeaders;

import jakarta.servlet.http.Cookie;

@Component
public class JwtCookieFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthCookieService cookies;

    public JwtCookieFilter(JwtService jwtService, AuthCookieService cookies) {
        this.jwtService = jwtService;
        this.cookies = cookies;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String token = readToken(request);

        if (token != null) {
            try {
                Claims claims = jwtService.parseToken(token);          // Signatur + Ablauf prüfen
                String role = claims.get("role", String.class);

                var auth = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(), null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (JwtException | IllegalArgumentException e) {
                // abgelaufen, gefälscht, kaputt: Cookie gleich entfernen
                response.addHeader("Set-Cookie", cookies.deleteCookie().toString());
            }
        }
        chain.doFilter(request, response);   // immer weitergeben; Security entscheidet danach
    }

    private String readToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie c : request.getCookies()) {
            if (AuthCookieService.NAME.equals(c.getName())) return c.getValue();
        }
        return null;
    }
}
