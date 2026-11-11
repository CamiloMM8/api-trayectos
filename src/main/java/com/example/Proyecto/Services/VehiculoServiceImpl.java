package com.example.Proyecto.Services;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import com.example.Proyecto.Request.DocumentoPdfRequest;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Proyecto.Entities.Documento;
import com.example.Proyecto.Entities.Vehiculo;
import com.example.Proyecto.Entities.VehiculoDocumento;
import com.example.Proyecto.Enums.EstadoVehiculoDocumento;
import com.example.Proyecto.Enums.TipoVehiculo;
import com.example.Proyecto.Repository.DocumentoRepository;
import com.example.Proyecto.Repository.VehiculoDocumentoRepository;
import com.example.Proyecto.Repository.VehiculoRepository;
import com.example.Proyecto.Services.Interfaces.IVehiculoService;

@Service("VehiculoService")
public class VehiculoServiceImpl implements IVehiculoService {

    /** Licencia de tránsito del catálogo; si no existe, se usa el primer documento disponible. */
    private static final String CODIGO_DOCUMENTO_DEFECTO = "LIC-TRANS";

    @Autowired
    @Qualifier("IVehiculoRepo")
    private VehiculoRepository vehiculoRepository;

    @Autowired
    @Qualifier("IDocumentoRepo")
    private DocumentoRepository documentoRepository;

    @Autowired
    @Qualifier("IVehiculoDocumentoRepo")
    private VehiculoDocumentoRepository vehiculoDocumentoRepository;

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(VehiculoServiceImpl.class);

    @Override
    public boolean guardar(Vehiculo vehiculo) {
        try {
            if (vehiculo == null || !validarVehiculo(vehiculo)) {
                logger.error("ERROR AGREGAR_VEHICULO: DATOS INVALIDOS");
                return false;
            }
            if (!tieneDocumentos(vehiculo) && !asociarDocumentoCatalogoPorDefecto(vehiculo)) {
                logger.error("ERROR AGREGAR_VEHICULO: CATALOGO DE DOCUMENTOS VACIO");
                return false;
            }
            prepararDocumentos(vehiculo, true);
            vehiculoRepository.save(vehiculo);
            return true;
        } catch (Exception e) {
            logger.error("ERROR AGREGAR_VEHICULO: NO SE HA GUARDADO");
            return false;
        }
    }

    @Override
    public boolean actualizar(Vehiculo vehiculo) {
        try {
            if (vehiculo == null || vehiculo.getId() == 0 || !validarVehiculo(vehiculo)) {
                logger.error("ERROR EDITAR_VEHICULO: DATOS INVALIDOS");
                return false;
            }
            Vehiculo existente = vehiculoRepository.findById(vehiculo.getId());
            if (existente == null) {
                logger.error("ERROR EDITAR_VEHICULO: VEHICULO NO EXISTE");
                return false;
            }
            copiarDatosBasicosVehiculo(existente, vehiculo);
            vehiculoRepository.save(existente);
            return true;
        } catch (Exception e) {
            logger.error("ERROR EDITAR_VEHICULO: NO SE HA EDITADO");
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        try {
            if (id == 0) {
                logger.error("ERROR ELIMINAR_VEHICULO: ID ES 0");
                return false;
            }
            Vehiculo vehiculo = vehiculoRepository.findById(id);
            if (vehiculo == null) {
                return false;
            }
            vehiculoRepository.delete(vehiculo);
            return true;
        } catch (Exception e) {
            logger.error("ERROR ELIMINAR_VEHICULO: NO SE HA ELIMINADO");
            return false;
        }
    }

    @Override
    public List<Vehiculo> consultarVehiculos(Pageable pageable) {
        return vehiculoRepository.findAll(pageable).getContent();
    }

    @Override
    public Vehiculo findById(int id) {
        return vehiculoRepository.findById(id);
    }

    @Override
    public Vehiculo findByPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa);
    }

    @Override
    public List<Vehiculo> findByTipoVehiculo(TipoVehiculo tipoVehiculo) {
        return vehiculoRepository.findByTipoVehiculo(tipoVehiculo);
    }

    @Override
    public List<Vehiculo> findByCodigoDocumento(String codigoDocumento) {
        return vehiculoRepository.findByCodigoDocumento(codigoDocumento);
    }

    @Override
    public List<Vehiculo> findByEstadoDocumento(EstadoVehiculoDocumento estado) {
        return vehiculoRepository.findByEstadoDocumento(estado);
    }

    @Override
    public boolean agregarDocumento(int vehiculoId, VehiculoDocumento vehiculoDocumento) {
        try {
            Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId);
            if (vehiculo == null || vehiculoDocumento == null || !validarVehiculoDocumento(vehiculoDocumento)) {
                logger.error("ERROR AGREGAR_DOCUMENTO_VEHICULO: DATOS INVALIDOS");
                return false;
            }
            Documento documento = resolverDocumento(vehiculoDocumento.getDocumento());
            if (documento == null) {
                logger.error("ERROR AGREGAR_DOCUMENTO_VEHICULO: DOCUMENTO NO EXISTE");
                return false;
            }
            vehiculoDocumento.setDocumento(documento);
            vehiculoDocumento.setVehiculo(vehiculo);
            vehiculoDocumento.setEstado(EstadoVehiculoDocumento.EN_VERIFICACION);
            vehiculoDocumentoRepository.save(vehiculoDocumento);
            return true;
        } catch (Exception e) {
            logger.error("ERROR AGREGAR_DOCUMENTO_VEHICULO: NO SE HA GUARDADO");
            return false;
        }
    }

    private void prepararDocumentos(Vehiculo vehiculo, boolean forzarEstadoInicial) {
        for (VehiculoDocumento vehiculoDocumento : vehiculo.getDocumentos()) {
            Documento documento = resolverDocumento(vehiculoDocumento.getDocumento());
            if (documento == null) {
                throw new IllegalArgumentException("Documento no encontrado");
            }
            vehiculoDocumento.setDocumento(documento);
            vehiculoDocumento.setVehiculo(vehiculo);
            if (forzarEstadoInicial || vehiculoDocumento.getEstado() == null) {
                vehiculoDocumento.setEstado(EstadoVehiculoDocumento.EN_VERIFICACION);
            }
        }
    }

    private Documento resolverDocumento(Documento documento) {
        if (documento == null) {
            return null;
        }
        if (documento.getId() != 0) {
            return documentoRepository.findById(documento.getId());
        }
        if (documento.getCodigoDocumento() != null) {
            return documentoRepository.findByCodigoDocumento(documento.getCodigoDocumento());
        }
        return null;
    }

    private boolean tieneDocumentos(Vehiculo vehiculo) {
        return vehiculo.getDocumentos() != null && !vehiculo.getDocumentos().isEmpty();
    }

    /**
     * Crea al menos un VehiculoDocumento usando el catálogo (LIC-TRANS o el primer registro).
     */
    private boolean asociarDocumentoCatalogoPorDefecto(Vehiculo vehiculo) {
        Documento catalogo = documentoRepository.findByCodigoDocumento(CODIGO_DOCUMENTO_DEFECTO);
        if (catalogo == null) {
            List<Documento> todos = documentoRepository.findAll();
            if (todos.isEmpty()) {
                return false;
            }
            catalogo = todos.get(0);
            logger.info("Documento {} no encontrado; se usa {} como asociacion por defecto",
                    CODIGO_DOCUMENTO_DEFECTO, catalogo.getCodigoDocumento());
        }
        VehiculoDocumento asociacion = new VehiculoDocumento();
        asociacion.setDocumento(catalogo);
        LocalDate hoy = LocalDate.now();
        asociacion.setFechaExpedicion(hoy);
        asociacion.setFechaVencimiento(hoy.plusYears(1));
        asociacion.setEstado(EstadoVehiculoDocumento.EN_VERIFICACION);
        vehiculo.getDocumentos().add(asociacion);
        return true;
    }

    /** Solo datos del vehículo; documentos y conductores se gestionan por otros endpoints. */
    private void copiarDatosBasicosVehiculo(Vehiculo destino, Vehiculo origen) {
        destino.setTipoVehiculo(origen.getTipoVehiculo());
        destino.setPlaca(origen.getPlaca());
        destino.setTipoServicio(origen.getTipoServicio());
        destino.setTipoCombustible(origen.getTipoCombustible());
        destino.setCapacidadPasajeros(origen.getCapacidadPasajeros());
        destino.setColor(origen.getColor());
        destino.setModelo(origen.getModelo());
        destino.setMarca(origen.getMarca());
        destino.setLinea(origen.getLinea());
    }

    private boolean validarVehiculo(Vehiculo vehiculo) {
        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().length() != 6) {
            return false;
        }
        if (!validarPlaca(vehiculo.getTipoVehiculo(), vehiculo.getPlaca())) {
            return false;
        }
        if (vehiculo.getTipoServicio() == null || vehiculo.getTipoCombustible() == null) {
            return false;
        }
        if (vehiculo.getCapacidadPasajeros() <= 0) {
            return false;
        }
        if (vehiculo.getColor() == null || !vehiculo.getColor().matches("#?[0-9A-Fa-f]{6}")) {
            return false;
        }
        if (vehiculo.getModelo() <= 0) {
            return false;
        }
        return vehiculo.getMarca() != null && !vehiculo.getMarca().isBlank()
                && vehiculo.getLinea() != null && !vehiculo.getLinea().isBlank()
                && vehiculo.getTipoVehiculo() != null;
    }

    private boolean validarPlaca(TipoVehiculo tipoVehiculo, String placa) {
        if (tipoVehiculo == TipoVehiculo.Automovil) {
            return placa.matches("[A-Za-z]{3}\\d{3}");
        }
        if (tipoVehiculo == TipoVehiculo.Motocicleta) {
            return placa.matches("[A-Za-z]{3}\\d{2}[A-Za-z]");
        }
        return false;
    }

    @Override
    public boolean cargarDocumentosPdf(List<DocumentoPdfRequest> documentos) {
        try {
            if (documentos == null || documentos.isEmpty()) {
                return false;
            }
            for (DocumentoPdfRequest item : documentos) {
                VehiculoDocumento vd = vehiculoDocumentoRepository.findById(item.getVehiculoDocumentoId());
                if (vd == null || item.getPdfBase64() == null || item.getPdfBase64().isBlank()) {
                    return false;
                }
                vd.setDocumentoPdfBase64(Base64.getDecoder().decode(item.getPdfBase64()));
                vehiculoDocumentoRepository.save(vd);
            }
            return true;
        } catch (Exception e) {
            logger.error("ERROR CARGAR_PDF_DOCUMENTOS");
            return false;
        }
    }

    private boolean validarVehiculoDocumento(VehiculoDocumento vehiculoDocumento) {
        if (vehiculoDocumento.getDocumento() == null) {
            return false;
        }
        if (vehiculoDocumento.getFechaExpedicion() == null || vehiculoDocumento.getFechaVencimiento() == null) {
            return false;
        }
        return vehiculoDocumento.getEstado() != null;
    }
}
