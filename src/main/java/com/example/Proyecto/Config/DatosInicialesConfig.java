package com.example.Proyecto.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.Proyecto.Config.Model.AdminPredeterminado;
import com.example.Proyecto.Entities.Persona;
import com.example.Proyecto.Entities.Usuario;
import com.example.Proyecto.Enums.TipoIdentificacion;
import com.example.Proyecto.Enums.TipoPersona;
import com.example.Proyecto.Repository.PersonaRepository;
import com.example.Proyecto.Repository.UsuarioRepository;
import com.example.Proyecto.Util.UsuarioUtil;

@Component
@Order(1)
public class DatosInicialesConfig implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DatosInicialesConfig.class);

    @Autowired
    @Qualifier("IPersonaRepo")
    private PersonaRepository personaRepository;

    @Autowired
    @Qualifier("IUsuarioRepo")
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (usuarioRepository.count() > 0) {
            return;
        }
        Persona admin = new Persona();
        admin.setIdentificacion(AdminPredeterminado.IDENTIFICACION);
        admin.setTipoIdentificacion(TipoIdentificacion.CC);
        admin.setNombres(AdminPredeterminado.NOMBRES);
        admin.setApellidos(AdminPredeterminado.APELLIDOS);
        admin.setCorreo(AdminPredeterminado.CORREO);
        admin.setTipoPersona(TipoPersona.A);
        personaRepository.save(admin);

        Usuario usuario = new Usuario();
        usuario.getId().setLogin(UsuarioUtil.generarLogin(admin));
        usuario.getId().setIdPersona(admin.getId());
        usuario.setPassword(AdminPredeterminado.PASSWORD);
        usuario.setApikey(AdminPredeterminado.API_KEY);
        usuarioRepository.save(usuario);

        log.info("Admin predeterminado creado — login: {}, password: {}, apikey: {}",
                usuario.getId().getLogin(), AdminPredeterminado.PASSWORD, AdminPredeterminado.API_KEY);
    }
}
