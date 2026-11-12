package com.example.Proyecto.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Proyecto.Entities.Trayecto;

@Repository("ITrayectoRepo")
public interface TrayectoRepository extends JpaRepository<Trayecto, Integer> {

    @Query("SELECT t FROM Trayecto t JOIN FETCH t.persona JOIN FETCH t.vehiculo WHERE t.codigoRuta = :codigo ORDER BY t.ordenParada")
    List<Trayecto> findByCodigoRuta(@Param("codigo") String codigo);

    @Query("SELECT t FROM Trayecto t JOIN FETCH t.persona JOIN FETCH t.vehiculo WHERE t.persona.identificacion = :identificacion ORDER BY t.codigoRuta, t.ordenParada")
    List<Trayecto> findByIdentificacionConductor(@Param("identificacion") String identificacion);

    @Query("SELECT t FROM Trayecto t JOIN FETCH t.persona JOIN FETCH t.vehiculo WHERE t.vehiculo.placa = :placa ORDER BY t.codigoRuta, t.ordenParada")
    List<Trayecto> findByPlaca(@Param("placa") String placa);

    @Query("SELECT t FROM Trayecto t JOIN FETCH t.persona JOIN FETCH t.vehiculo WHERE t.latitud IS NULL OR t.longitud IS NULL")
    List<Trayecto> findSinCoordenadas();

    @Query("SELECT t FROM Trayecto t JOIN FETCH t.persona JOIN FETCH t.vehiculo ORDER BY t.codigoRuta, t.ordenParada")
    List<Trayecto> findAllConRelaciones();

    @Query("SELECT COUNT(t) FROM Trayecto t WHERE t.codigoRuta = :codigo AND t.ordenParada = :orden")
    long contarOrden(@Param("codigo") String codigo, @Param("orden") int orden);
}
