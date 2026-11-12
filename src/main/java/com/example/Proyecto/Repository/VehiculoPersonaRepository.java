package com.example.Proyecto.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Proyecto.Entities.VehiculoPersona;
import com.example.Proyecto.Enums.EstadoConductorVehiculo;
import com.example.Proyecto.Enums.TipoPersona;

@Repository("IVehiculoPersonaRepo")
public interface VehiculoPersonaRepository extends JpaRepository<VehiculoPersona, Integer> {

    @Query("SELECT vp FROM VehiculoPersona vp JOIN FETCH vp.persona p JOIN FETCH vp.vehiculo v WHERE vp.estado = :estado AND p.tipoPersona = :tipoConductor")
    List<VehiculoPersona> findConductoresByEstado(@Param("estado") EstadoConductorVehiculo estado,
            @Param("tipoConductor") TipoPersona tipoConductor);

    @Query("SELECT vp FROM VehiculoPersona vp WHERE vp.persona.id = :personaId AND vp.vehiculo.id = :vehiculoId")
    VehiculoPersona findByPersonaIdAndVehiculoId(@Param("personaId") int personaId, @Param("vehiculoId") int vehiculoId);

    List<VehiculoPersona> findByPersonaId(int personaId);
}
