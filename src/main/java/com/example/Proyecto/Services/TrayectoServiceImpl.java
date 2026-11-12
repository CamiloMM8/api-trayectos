package com.example.Proyecto.Services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Proyecto.Entities.Persona;
import com.example.Proyecto.Entities.Trayecto;
import com.example.Proyecto.Entities.Vehiculo;
import com.example.Proyecto.Entities.VehiculoDocumento;
import com.example.Proyecto.Entities.VehiculoPersona;
import com.example.Proyecto.Enums.EstadoConductorVehiculo;
import com.example.Proyecto.Enums.EstadoVehiculoDocumento;
import com.example.Proyecto.Enums.TipoPersona;
import com.example.Proyecto.Repository.PersonaRepository;
import com.example.Proyecto.Repository.TrayectoRepository;
import com.example.Proyecto.Repository.VehiculoDocumentoRepository;
import com.example.Proyecto.Repository.VehiculoPersonaRepository;
import com.example.Proyecto.Repository.VehiculoRepository;
import com.example.Proyecto.Request.TrayectoRequest;
import com.example.Proyecto.Response.RutaAgrupadaResponse;
import com.example.Proyecto.Response.TrayectoDetalleResponse;
import com.example.Proyecto.Services.Interfaces.ITrayectoService;

@Service("TrayectoService")
public class TrayectoServiceImpl implements ITrayectoService {

    private static final int MAX_PARADAS = 7;

    @Autowired
    @Qualifier("ITrayectoRepo")
    private TrayectoRepository trayectoRepository;

    @Autowired
    @Qualifier("IPersonaRepo")
    private PersonaRepository personaRepository;

    @Autowired
    @Qualifier("IVehiculoRepo")
    private VehiculoRepository vehiculoRepository;

    @Autowired
    @Qualifier("IVehiculoPersonaRepo")
    private VehiculoPersonaRepository vehiculoPersonaRepository;

    @Autowired
    @Qualifier("IVehiculoDocumentoRepo")
    private VehiculoDocumentoRepository vehiculoDocumentoRepository;

    @Override
    @Transactional
    public TrayectoDetalleResponse registrar(TrayectoRequest request) {
        String error = validarRegistro(request);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        Persona persona = personaRepository.findById(request.getIdPersona());
        Vehiculo vehiculo = vehiculoRepository.findById(request.getIdVehiculo());
        Trayecto trayecto = new Trayecto();
        trayecto.setPersona(persona);
        trayecto.setVehiculo(vehiculo);
        trayecto.setCodigoRuta(request.getCodigoRuta().trim());
        trayecto.setUbicacion(request.getUbicacion().trim());
        trayecto.setOrdenParada(request.getOrdenParada());
        trayecto.setLoginRegistro(loginActual());
        return aDetalle(trayectoRepository.save(trayecto), null);
    }

    @Override
    public String validarRegistro(TrayectoRequest request) {
        if (request == null || request.getCodigoRuta() == null || request.getCodigoRuta().isBlank()
                || request.getUbicacion() == null || request.getUbicacion().isBlank()) {
            return "Codigo de ruta y ubicacion son obligatorios";
        }
        if (request.getOrdenParada() < 0 || request.getOrdenParada() > MAX_PARADAS - 1) {
            return "El orden de parada debe estar entre 0 y 6. 0 es la parada inicial y el mayor es la final";
        }
        if (loginActual() == null) {
            return "No hay un usuario autenticado para registrar el trayecto";
        }
        Persona persona = personaRepository.findById(request.getIdPersona());
        if (persona == null || persona.getTipoPersona() != TipoPersona.C) {
            return "El trayecto solo puede asociarse a una persona de tipo conductor";
        }
        Vehiculo vehiculo = vehiculoRepository.findById(request.getIdVehiculo());
        if (vehiculo == null) {
            return "El vehiculo no existe";
        }
        if (!vehiculoHabilitado(vehiculo.getId())) {
            return "Los documentos del vehiculo deben estar en estado Habilitado";
        }
        VehiculoPersona relacion = vehiculoPersonaRepository.findByPersonaIdAndVehiculoId(persona.getId(), vehiculo.getId());
        if (relacion == null || relacion.getEstado() != EstadoConductorVehiculo.PO) {
            return "El conductor debe poder operar (PO) el vehiculo del trayecto";
        }
        String codigo = request.getCodigoRuta().trim();
        List<Trayecto> ruta = trayectoRepository.findByCodigoRuta(codigo);
        if (ruta.size() >= MAX_PARADAS) {
            return "La ruta admite como maximo una parada inicial, 5 intermedias y una final";
        }
        if (ruta.isEmpty() && request.getOrdenParada() != 0) {
            return "El primer trayecto de la ruta debe ser la parada inicial con orden 0";
        }
        for (Trayecto existente : ruta) {
            if (existente.getPersona().getId() != persona.getId() || existente.getVehiculo().getId() != vehiculo.getId()) {
                return "El codigo de ruta ya pertenece a otro conductor o vehiculo";
            }
            if (existente.getOrdenParada() == request.getOrdenParada()) {
                return "Ya existe una parada con ese orden en la ruta";
            }
        }
        return null;
    }

    @Override
    public List<TrayectoDetalleResponse> consultarPorCodigoRuta(String codigoRuta) {
        List<TrayectoDetalleResponse> respuesta = new ArrayList<>();
        for (Trayecto trayecto : trayectoRepository.findByCodigoRuta(codigoRuta)) {
            respuesta.add(aDetalle(trayecto, null));
        }
        return respuesta;
    }

    @Override
    public List<RutaAgrupadaResponse> consultarCodigosPorConductor(String identificacion) {
        Map<String, RutaAgrupadaResponse> agrupadas = new LinkedHashMap<>();
        for (Trayecto trayecto : trayectoRepository.findByIdentificacionConductor(identificacion)) {
            agrupadas.putIfAbsent(trayecto.getCodigoRuta(), new RutaAgrupadaResponse(trayecto.getCodigoRuta()));
        }
        return new ArrayList<>(agrupadas.values());
    }

    @Override
    public List<RutaAgrupadaResponse> consultarPorPlaca(String placa) {
        Map<String, RutaAgrupadaResponse> agrupadas = new LinkedHashMap<>();
        for (Trayecto trayecto : trayectoRepository.findByPlaca(placa)) {
            agrupadas.computeIfAbsent(trayecto.getCodigoRuta(), codigo -> {
                RutaAgrupadaResponse item = new RutaAgrupadaResponse(codigo);
                item.setIdentificacion(trayecto.getPersona().getIdentificacion());
                item.setNombres(trayecto.getPersona().getNombres());
                item.setApellidos(trayecto.getPersona().getApellidos());
                item.setPlaca(trayecto.getVehiculo().getPlaca());
                return item;
            });
        }
        return new ArrayList<>(agrupadas.values());
    }

    @Override
    public List<TrayectoDetalleResponse> consultarRestringidos() {
        List<TrayectoDetalleResponse> respuesta = new ArrayList<>();
        for (Trayecto trayecto : trayectoRepository.findAllConRelaciones()) {
            boolean conductorRestringido = conductorRestringido(trayecto);
            boolean vehiculoNoHabilitado = !vehiculoHabilitado(trayecto.getVehiculo().getId());
            if (!conductorRestringido && !vehiculoNoHabilitado) {
                continue;
            }
            String motivo = motivoRestriccion(conductorRestringido, vehiculoNoHabilitado);
            respuesta.add(aDetalle(trayecto, motivo));
        }
        return respuesta;
    }

    private boolean vehiculoHabilitado(int vehiculoId) {
        List<VehiculoDocumento> documentos = vehiculoDocumentoRepository.findByVehiculoId(vehiculoId);
        if (documentos == null || documentos.isEmpty()) {
            return false;
        }
        for (VehiculoDocumento documento : documentos) {
            if (documento.getEstado() != EstadoVehiculoDocumento.Habilitado) {
                return false;
            }
        }
        return true;
    }

    private boolean conductorRestringido(Trayecto trayecto) {
        VehiculoPersona relacion = vehiculoPersonaRepository.findByPersonaIdAndVehiculoId(
                trayecto.getPersona().getId(), trayecto.getVehiculo().getId());
        return relacion != null && relacion.getEstado() == EstadoConductorVehiculo.RO;
    }

    private String motivoRestriccion(boolean conductorRestringido, boolean vehiculoNoHabilitado) {
        if (conductorRestringido && vehiculoNoHabilitado) {
            return "Conductor restringido para operar y vehiculo no habilitado";
        }
        if (conductorRestringido) {
            return "Conductor restringido para operar";
        }
        return "Vehiculo no habilitado";
    }

    private TrayectoDetalleResponse aDetalle(Trayecto trayecto, String motivo) {
        TrayectoDetalleResponse detalle = new TrayectoDetalleResponse();
        detalle.setId(trayecto.getId());
        detalle.setCodigoRuta(trayecto.getCodigoRuta());
        detalle.setOrdenParada(trayecto.getOrdenParada());
        detalle.setUbicacion(trayecto.getUbicacion());
        detalle.setLatitud(trayecto.getLatitud());
        detalle.setLongitud(trayecto.getLongitud());
        detalle.setLoginRegistro(trayecto.getLoginRegistro());
        detalle.setIdPersona(trayecto.getPersona().getId());
        detalle.setIdentificacion(trayecto.getPersona().getIdentificacion());
        detalle.setNombres(trayecto.getPersona().getNombres());
        detalle.setApellidos(trayecto.getPersona().getApellidos());
        detalle.setIdVehiculo(trayecto.getVehiculo().getId());
        detalle.setPlaca(trayecto.getVehiculo().getPlaca());
        detalle.setMarca(trayecto.getVehiculo().getMarca());
        detalle.setVehiculoHabilitado(vehiculoHabilitado(trayecto.getVehiculo().getId()));
        VehiculoPersona relacion = vehiculoPersonaRepository.findByPersonaIdAndVehiculoId(
                trayecto.getPersona().getId(), trayecto.getVehiculo().getId());
        if (relacion != null) {
            detalle.setEstadoConductor(relacion.getEstado().getCodigo());
        }
        detalle.setMotivo(motivo);
        return detalle;
    }

    private String loginActual() {
        Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();
        if (autenticacion == null || autenticacion.getName() == null || autenticacion.getName().isBlank()) {
            return null;
        }
        return autenticacion.getName();
    }
}
