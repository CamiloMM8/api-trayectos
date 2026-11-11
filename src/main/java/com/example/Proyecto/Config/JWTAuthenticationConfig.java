package com.example.Proyecto.Config;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.example.Proyecto.Config.Model.Constants.*;

@Configuration
public class JWTAuthenticationConfig {

    public String getJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
        String token = Jwts.builder()
                .setId("PROYECTO_JWT")
                .setSubject(username)
                .claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey(SUPER_SECRET_KEY), SignatureAlgorithm.HS512)
                .compact();
        return TOKEN_BEARER_PREFIX + token;
    }
}
