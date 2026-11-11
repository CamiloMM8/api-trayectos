package com.example.Proyecto.Repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Proyecto.Entities.VehiculoDocumento;

@Repository("IVehiculoDocumentoRepo")
public interface VehiculoDocumentoRepository extends JpaRepository<VehiculoDocumento, Serializable> {

    VehiculoDocumento findById(int id);

    List<VehiculoDocumento> findByVehiculoId(int vehiculoId);
}
