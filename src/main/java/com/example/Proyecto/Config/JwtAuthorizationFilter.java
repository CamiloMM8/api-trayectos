package com.example.Proyecto.Config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.example.Proyecto.Config.Model.Constants.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getServletPath();
        return LOGIN_URL.equals(path) || path.startsWith("/ProyectoV1/public/");
    }

    private Claims parseClaims(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER_AUTH).replace(TOKEN_BEARER_PREFIX, "");
        return Jwts.parser().setSigningKey(getSigningKey(SUPER_SECRET_KEY)).build().parseClaimsJws(jwtToken).getBody();
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String authenticationHeader = request.getHeader(HEADER_AUTH);
            if (authenticationHeader != null && authenticationHeader.startsWith(TOKEN_BEARER_PREFIX)) {
                Claims claims = parseClaims(request);
                if (claims.get("authorities") != null) {
                    List<String> authorities = (List<String>) claims.get("authorities");
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(),
                            null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }
}
