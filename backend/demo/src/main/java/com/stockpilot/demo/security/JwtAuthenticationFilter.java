package com.stockpilot.demo.security;

import com.stockpilot.demo.model.User;
import com.stockpilot.demo.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization != null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            String token = authorization.substring(7);
            String email = jwtService.extractEmail(token);
            User user = userService.getUserByEmail(email);
            if(user != null){
                Boolean valid = jwtService.isTokenValid(token,user);
                if (valid && user.isActive()){
                   var authentication = new UsernamePasswordAuthenticationToken(
                           user,
                           null,
                           List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()))
                   );
                   SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
    }
}
