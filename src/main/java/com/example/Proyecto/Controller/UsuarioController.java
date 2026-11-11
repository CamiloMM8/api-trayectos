package com.example.Proyecto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Proyecto.Request.PasswordChangeRequest;
import com.example.Proyecto.Services.UsuarioServiceImpl;

@RestController
@RequestMapping("/ProyectoV1")
public class UsuarioController {

    @Autowired
    @Qualifier("UsuarioService")
    private UsuarioServiceImpl usuarioService;

    @PutMapping("/usuario/{login}/password")
    public boolean cambiarPassword(@PathVariable("login") String login,
            @RequestBody PasswordChangeRequest request) {
        return usuarioService.cambiarPassword(login, request.getPassword());
    }

    @GetMapping("/usuario/{login}/apikey")
    public String regenerarApiKey(@PathVariable("login") String login) {
        return usuarioService.regenerarApiKey(login);
    }
}
