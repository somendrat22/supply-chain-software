package com.supply_chain_easy.supply_chain_base_operations.security;

import com.supply_chain_easy.supply_chain_base_operations.models.User;
import com.supply_chain_easy.supply_chain_base_operations.utilities.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtility jwtUtility;

    public JwtFilter(JwtUtility jwtUtility){
        this.jwtUtility = jwtUtility;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // In the request we are going to send the token
        String token = request.getHeader("token");
        // No token -> continue without authentication
        if (token == null || token.trim().isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // We are getting token -> We need to verify that token value is correct or not

        User user = jwtUtility.verifyJwtToken(token);
        if(user == null){
            // We need to reject this request
            // When we are calling filterChain.doFilter without setting any kind of quthentication that means we are rejecting the request.
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // After setting the authentication if we are calling do filter method that means our request is valid and it will go to controller.
        filterChain.doFilter(request, response);
    }
}
