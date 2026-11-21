package com.example.Proyecto.Services.Interfaces;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.Proyecto.Entities.Vehiculo;
import com.example.Proyecto.Entities.VehiculoDocumento;
import com.example.Proyecto.Enums.EstadoVehiculoDocumento;
import com.example.Proyecto.Enums.TipoVehiculo;

public interface IVehiculoService {

    boolean guardar(Vehiculo vehiculo);

    boolean actualizar(Vehiculo vehiculo);

    boolean eliminar(int id);

    List<Vehiculo> consultarVehiculos(Pageable pageable);

    Vehiculo findById(int id);

    Vehiculo findByPlaca(String placa);

    List<Vehiculo> findByTipoVehiculo(TipoVehiculo tipoVehiculo);

    List<Vehiculo> findByCodigoDocumento(String codigoDocumento);

    List<Vehiculo> findByEstadoDocumento(EstadoVehiculoDocumento estado);

    boolean agregarDocumento(int vehiculoId, VehiculoDocumento vehiculoDocumento);

    boolean cargarDocumentoPdf(int vehiculoDocumentoId, MultipartFile archivo);
}
