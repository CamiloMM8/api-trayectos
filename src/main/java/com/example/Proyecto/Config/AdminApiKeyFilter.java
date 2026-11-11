package com.example.Proyecto.Config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Proyecto.Entities.Persona;
import com.example.Proyecto.Entities.Usuario;
import com.example.Proyecto.Enums.TipoPersona;
import com.example.Proyecto.Repository.UsuarioRepository;

import static com.example.Proyecto.Config.Model.Constants.HEADER_API_KEY;
import static com.example.Proyecto.Config.Model.Constants.LOGIN_URL;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminApiKeyFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("IUsuarioRepo")
    private UsuarioRepository usuarioRepository;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getServletPath();
        return LOGIN_URL.equals(path) || path.startsWith("/ProyectoV1/public/");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        String apiKey = request.getHeader(HEADER_API_KEY);
        Usuario usuario = usuarioRepository.findByUsername(login);
        if (usuario == null || usuario.getApikey() == null || !usuario.getApikey().equals(apiKey)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API Key invalida");
            return;
        }
        Persona persona = usuario.getPersonaEntity();
        if (persona == null || persona.getTipoPersona() != TipoPersona.A) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuario no administrativo");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
