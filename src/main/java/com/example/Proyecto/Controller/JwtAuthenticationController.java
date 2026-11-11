package com.example.Proyecto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.Proyecto.Config.JWTAuthenticationConfig;
import com.example.Proyecto.Config.Model.JwtRequest;
import com.example.Proyecto.Config.Model.JwtResponse;
import com.example.Proyecto.Entities.Usuario;
import com.example.Proyecto.Repository.UsuarioRepository;
import com.example.Proyecto.Services.UsuarioServiceImpl;

import static com.example.Proyecto.Config.Model.Constants.HEADER_API_KEY;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("UsuarioService")
    private UsuarioServiceImpl usuarioService;

    @Autowired
    @Qualifier("IUsuarioRepo")
    private UsuarioRepository usuarioRepository;

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
            @RequestHeader(value = HEADER_API_KEY, required = false) String apiKey) throws Exception {
        if (!usuarioService.validarApiKey(authenticationRequest.getUsername(), apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("API Key invalida");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Usuario usuario = usuarioRepository.findByUsername(authenticationRequest.getUsername());
        if (usuario == null || !usuario.getPassword().equals(authenticationRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
        }
        String token = jwtAuthenticationConfig.getJWTToken(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
