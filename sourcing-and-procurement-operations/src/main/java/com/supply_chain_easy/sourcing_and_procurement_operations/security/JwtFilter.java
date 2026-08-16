package com.supply_chain_easy.sourcing_and_procurement_operations.security;

import com.supply_chain_easy.supply_chain_base_operations.utilities.JwtUtility;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {



    private final JwtUtility jwtUtility;

    public JwtFilter(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        log.info("JWT Filter started | {} {}", method, path);

        try {
            String token = request.getHeader("token");

            if (token == null || token.trim().isEmpty()) {
                log.info("No JWT token found in request header.");
                filterChain.doFilter(request, response);
                return;
            }

            log.info("JWT token found. Verifying token...");

            Claims claims = jwtUtility.verifyJwtToken(token);

            if (claims == null) {
                log.warn("JWT verification failed. Claims are null.");
                filterChain.doFilter(request, response);
                return;
            }

            String email = claims.get("email", String.class);
            List<String> roles = claims.get("roles", List.class);

            log.info("JWT verified successfully. Email: {}, Roles: {}", email, roles);

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("Authentication set in SecurityContext for user: {}", email);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            log.info("Before chain - Auth: {}, Authenticated: {}, Authorities: {}",
                    auth,
                    auth != null && auth.isAuthenticated(),
                    auth != null ? auth.getAuthorities() : null);


            filterChain.doFilter(request, response);

            Authentication after = SecurityContextHolder.getContext().getAuthentication();

            log.info("After chain - Auth: {}", after);

            log.info("Request completed successfully for user: {}", email);

        } catch (Exception e) {
            log.error("Exception occurred in JwtFilter for {} {}: {}", method, path, e.getMessage(), e);

            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }
}


