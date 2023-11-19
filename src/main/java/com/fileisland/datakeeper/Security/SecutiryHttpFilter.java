package com.fileisland.datakeeper.Security;

import com.fileisland.datakeeper.Dao.Entity.User;
import com.fileisland.datakeeper.Services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecutiryHttpFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       String token = request.getHeader("token");
       if (token != null){
           User user = jwtService.GetUserFromToken(token);
           Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(authentication);
           }

       response.addHeader("Access-Control-Allow-Origin", "*");
       response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD");
       response.addHeader("Access-Control-Allow-Headers", "*");

        filterChain.doFilter(request, response);
    }
}
