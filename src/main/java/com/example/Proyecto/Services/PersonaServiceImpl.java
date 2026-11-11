package com.example.Proyecto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Proyecto.Entities.Persona;
import com.example.Proyecto.Enums.TipoIdentificacion;
import com.example.Proyecto.Enums.TipoPersona;
import com.example.Proyecto.Entities.Usuario;
import com.example.Proyecto.Entities.UsuarioPK;
import com.example.Proyecto.Repository.PersonaRepository;
import com.example.Proyecto.Repository.UsuarioRepository;
import com.example.Proyecto.Response.PersonaCreacionResponse;
import com.example.Proyecto.Services.Interfaces.IPersonaService;
import com.example.Proyecto.Util.UsuarioUtil;

@Service("PersonaService")
public class PersonaServiceImpl implements IPersonaService {

    @Autowired
    @Qualifier("IPersonaRepo")
    private PersonaRepository personaRepository;

    @Autowired
    @Qualifier("IUsuarioRepo")
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public PersonaCreacionResponse guardar(Persona persona) {
        PersonaCreacionResponse response = new PersonaCreacionResponse();
        if (!validarPersona(persona)) {
            return response;
        }
        if (TipoPersona.A.equals(persona.getTipoPersona())) {
            personaRepository.save(persona);
            Usuario usuario = new Usuario();
            usuario.getId().setLogin(UsuarioUtil.generarLogin(persona));
            usuario.getId().setIdPersona(persona.getId());
            usuario.setPassword(UsuarioUtil.generarPassword());
            usuario.setApikey(UsuarioUtil.generarApiKey());
            usuarioRepository.save(usuario);
            response.setPersona(persona);
            response.setLoginGenerado(usuario.getId().getLogin());
            response.setPasswordGenerado(usuario.getPassword());
            response.setApiKeyGenerado(usuario.getApikey());
            return response;
        }
        personaRepository.save(persona);
        response.setPersona(persona);
        return response;
    }

    @Override
    public boolean actualizar(Persona persona) {
        if (persona == null || persona.getId() == 0 || !validarPersona(persona)) {
            return false;
        }
        personaRepository.save(persona);
        return true;
    }

    @Override
    public Persona consultarPorId(int id) {
        return personaRepository.findById(id);
    }

    @Override
    public List<Persona> consultarTodos() {
        return personaRepository.findAll();
    }

    private boolean validarPersona(Persona persona) {
        if (persona == null) {
            return false;
        }
        if (persona.getTipoIdentificacion() != TipoIdentificacion.CC) {
            return false;
        }
        return persona.getTipoPersona() == TipoPersona.A || persona.getTipoPersona() == TipoPersona.C;
    }
}
