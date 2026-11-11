package com.example.Proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Proyecto.Entities.Usuario;
import com.example.Proyecto.Entities.UsuarioPK;

@Repository("IUsuarioRepo")
public interface UsuarioRepository extends JpaRepository<Usuario, UsuarioPK> {

    @Query("SELECT u FROM UsrProyecto u JOIN FETCH u.personaEntity WHERE u.id.login = :login")
    Usuario findByUsername(@Param("login") String login);

    @Query("SELECT u FROM UsrProyecto u JOIN FETCH u.personaEntity WHERE u.id.login = :login AND u.id.idPersona = :idPersona")
    Usuario findByLoginAndIdPersona(@Param("login") String login, @Param("idPersona") int idPersona);
}
