package com.example.Proyecto.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Proyecto.Entities.Persona;
import com.example.Proyecto.Enums.TipoPersona;

@Repository("IPersonaRepo")
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    Persona findById(int id);

    @Query("SELECT p.tipoPersona, COUNT(p) FROM Persona p GROUP BY p.tipoPersona")
    List<Object[]> totalPorTipoPersona();

    List<Persona> findByTipoPersona(TipoPersona tipoPersona);

    Persona findByIdentificacion(String identificacion);
}
