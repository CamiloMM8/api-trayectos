package com.example.Proyecto.Repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Proyecto.Entities.Vehiculo;
import com.example.Proyecto.Enums.EstadoVehiculoDocumento;
import com.example.Proyecto.Enums.TipoVehiculo;

@Repository("IVehiculoRepo")
public interface VehiculoRepository extends JpaRepository<Vehiculo, Serializable> {

    Vehiculo findById(int id);

    Vehiculo findByPlaca(String placa);

    List<Vehiculo> findByTipoVehiculo(TipoVehiculo tipoVehiculo);

    Page<Vehiculo> findAll(Pageable pageable);

    @Query("SELECT DISTINCT v FROM Vehiculo v JOIN v.documentos vd JOIN vd.documento d WHERE d.codigoDocumento = :codigo")
    List<Vehiculo> findByCodigoDocumento(@Param("codigo") String codigo);

    @Query("SELECT DISTINCT v FROM Vehiculo v JOIN v.documentos vd WHERE vd.estado = :estado")
    List<Vehiculo> findByEstadoDocumento(@Param("estado") EstadoVehiculoDocumento estado);

    @Query("SELECT DISTINCT v FROM Vehiculo v JOIN v.documentos vd WHERE vd.fechaVencimiento < CURRENT_DATE")
    List<Vehiculo> findWithDocumentosVencidos();

    @Query("SELECT DISTINCT v FROM Vehiculo v JOIN v.documentos vd WHERE vd.fechaVencimiento BETWEEN CURRENT_DATE AND :fechaLimite")
    List<Vehiculo> findWithDocumentosPorVencer(@Param("fechaLimite") LocalDate fechaLimite);

    @Query("SELECT DISTINCT v FROM Vehiculo v LEFT JOIN FETCH v.documentos LEFT JOIN FETCH v.conductores c LEFT JOIN FETCH c.persona WHERE v.placa = :placa")
    Vehiculo findDetalleByPlaca(@Param("placa") String placa);
}
