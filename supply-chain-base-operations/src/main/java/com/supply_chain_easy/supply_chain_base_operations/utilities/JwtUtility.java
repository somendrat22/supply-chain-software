package com.supply_chain_easy.supply_chain_base_operations.utilities;

import com.supply_chain_easy.supply_chain_base_operations.constants.SystemConstant;
import com.supply_chain_easy.supply_chain_base_operations.models.Role;
import com.supply_chain_easy.supply_chain_base_operations.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtUtility {

    private final Key key = Keys.hmacShaKeyFor(SystemConstant.JWT_SECRET_PASSWORD.getBytes());

    public String generateJwtToken(User user){
        String email = user.getEmail();
        List<Role> roles = user.getRoles();
        List<String> roleNames = new ArrayList<>();
        for(Role role : roles){
            roleNames.add(role.getRoleName());
        }
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("roles", roleNames);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SystemConstant.JWT_TOKEN_EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

}
