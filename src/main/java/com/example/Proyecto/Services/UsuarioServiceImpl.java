package com.example.Proyecto.Services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Proyecto.Entities.Usuario;
import com.example.Proyecto.Repository.UsuarioRepository;
import com.example.Proyecto.Services.Interfaces.IUsuarioService;
import com.example.Proyecto.Util.UsuarioUtil;

@Service("UsuarioService")
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

    @Autowired
    @Qualifier("IUsuarioRepo")
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new User(usuario.getId().getLogin(), usuario.getPassword(), Collections.emptyList());
    }

    @Override
    public boolean validarApiKey(String login, String apiKey) {
        if (login == null || login.isBlank() || apiKey == null || apiKey.isBlank()) {
            return false;
        }
        Usuario usuario = usuarioRepository.findByUsername(login);
        return usuario != null && apiKey.equals(usuario.getApikey());
    }

    @Override
    public boolean cambiarPassword(String login, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByUsername(login);
        if (usuario == null || nuevaPassword == null || nuevaPassword.isBlank()) {
            return false;
        }
        usuario.setPassword(nuevaPassword);
        usuarioRepository.save(usuario);
        return true;
    }

    @Override
    public String regenerarApiKey(String login) {
        Usuario usuario = usuarioRepository.findByUsername(login);
        if (usuario == null) {
            return null;
        }
        String apiKey = UsuarioUtil.generarApiKey();
        usuario.setApikey(apiKey);
        usuarioRepository.save(usuario);
        return apiKey;
    }
}
