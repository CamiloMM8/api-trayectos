package com.example.Proyecto.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Proyecto.Entities.TrayectoTmp;

@Repository("ITrayectoTmpRepo")
public interface TrayectoTmpRepository extends JpaRepository<TrayectoTmp, Long> {

    List<TrayectoTmp> findByIdCargueOrderByIdAsc(Double idCargue);

    @Procedure(procedureName = "CARGAR_TRAYECTO_EXCEL")
    void CARGAR_TRAYECTO_EXCEL(
            @Param("p_identificacion") String pIdentificacion,
            @Param("p_placa") String pPlaca,
            @Param("p_codigo_ruta") String pCodigoRuta,
            @Param("p_ubicacion") String pUbicacion,
            @Param("p_orden_parada") String pOrdenParada,
            @Param("p_latitud") String pLatitud,
            @Param("p_longitud") String pLongitud,
            @Param("p_login_registro") String pLoginRegistro,
            @Param("p_id_cargue") Double pIdCargue);

    @Procedure(procedureName = "VALIDAR_TRAYECTO_CARGUE")
    void VALIDAR_TRAYECTO_CARGUE(@Param("p_id_cargue") Double pIdCargue);

    @Procedure(procedureName = "PROCESAR_TRAYECTO_EXCEL")
    void PROCESAR_TRAYECTO_EXCEL(@Param("p_id_cargue") Double pIdCargue);
}
