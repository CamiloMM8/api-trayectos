package com.example.Proyecto.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.Proyecto.APIs.GoogleMaps.Geocoder;
import com.example.Proyecto.Entities.Persona;
import com.example.Proyecto.Entities.Trayecto;
import com.example.Proyecto.Entities.VehiculoDocumento;
import com.example.Proyecto.Entities.VehiculoPersona;
import com.example.Proyecto.Enums.EstadoConductorVehiculo;
import com.example.Proyecto.Enums.EstadoVehiculoDocumento;
import com.example.Proyecto.Enums.TipoPersona;
import com.example.Proyecto.Repository.PersonaRepository;
import com.example.Proyecto.Repository.TrayectoRepository;
import com.example.Proyecto.Repository.VehiculoDocumentoRepository;
import com.example.Proyecto.Repository.VehiculoPersonaRepository;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    @Qualifier("IPersonaRepo")
    private PersonaRepository personaRepository;

    @Autowired
    @Qualifier("IVehiculoPersonaRepo")
    private VehiculoPersonaRepository vehiculoPersonaRepository;

    @Autowired
    @Qualifier("IVehiculoDocumentoRepo")
    private VehiculoDocumentoRepository vehiculoDocumentoRepository;

    @Autowired
    @Qualifier("ITrayectoRepo")
    private TrayectoRepository trayectoRepository;

    @Scheduled(cron = "0 */2 * * * ?")
    @Transactional
    public void restringirLicenciasVencidas() {
        logger.info("Cron licencias :: {}", HORA.format(LocalDateTime.now()));
        List<Persona> conductores = personaRepository.findByTipoPersona(TipoPersona.C);
        LocalDate hoy = LocalDate.now();
        for (Persona conductor : conductores) {
            if (conductor.getFechaVigenciaLicencia() == null || !conductor.getFechaVigenciaLicencia().isBefore(hoy)) {
                continue;
            }
            List<VehiculoPersona> relaciones = vehiculoPersonaRepository.findByPersonaId(conductor.getId());
            for (VehiculoPersona relacion : relaciones) {
                if (relacion.getEstado() == EstadoConductorVehiculo.RO) {
                    continue;
                }
                relacion.setEstado(EstadoConductorVehiculo.RO);
                vehiculoPersonaRepository.save(relacion);
                logger.info("Licencia vencida de {} {}. Estado del vehiculo {} cambiado a RO",
                        conductor.getNombres(), conductor.getApellidos(), relacion.getVehiculo().getPlaca());
            }
        }
    }

    @Scheduled(cron = "0 */2 * * * ?")
    @Transactional
    public void marcarDocumentosVencidos() {
        logger.info("Cron documentos :: {}", HORA.format(LocalDateTime.now()));
        LocalDate hoy = LocalDate.now();
        for (VehiculoDocumento documento : vehiculoDocumentoRepository.findAll()) {
            if (documento.getFechaVencimiento() == null || !documento.getFechaVencimiento().isBefore(hoy)) {
                continue;
            }
            if (documento.getEstado() == EstadoVehiculoDocumento.Vencido) {
                continue;
            }
            documento.setEstado(EstadoVehiculoDocumento.Vencido);
            vehiculoDocumentoRepository.save(documento);
            logger.info("Documento {} del vehiculo {} marcado como Vencido",
                    documento.getId(), documento.getVehiculo().getPlaca());
        }
    }

    @Scheduled(fixedRate = 90000, initialDelay = 15000)
    @Transactional
    public void geocodificarTrayectos() {
        logger.info("Geocodificar trayectos :: {}", HORA.format(LocalDateTime.now()));
        List<Trayecto> pendientes = trayectoRepository.findSinCoordenadas();
        if (pendientes.isEmpty()) {
            return;
        }
        Geocoder geocoder = new Geocoder();
        for (Trayecto trayecto : pendientes) {
            try {
                String latLng = geocoder.getLatLng(trayecto.getUbicacion());
                String[] partes = latLng.split(",");
                trayecto.setLatitud(Double.parseDouble(partes[0]));
                trayecto.setLongitud(Double.parseDouble(partes[1]));
                trayectoRepository.save(trayecto);
                logger.info("Trayecto {} {} -> {}", trayecto.getCodigoRuta(), trayecto.getOrdenParada(), latLng);
            } catch (Exception e) {
                logger.error("No se pudo geocodificar el trayecto {}: {}", trayecto.getId(), e.getMessage());
            }
        }
    }
}
