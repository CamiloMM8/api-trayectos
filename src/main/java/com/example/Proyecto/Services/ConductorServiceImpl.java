package com.example.Proyecto.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.Proyecto.Entities.Persona;
import com.example.Proyecto.Enums.EstadoConductorVehiculo;
import com.example.Proyecto.Enums.TipoPersona;
import com.example.Proyecto.Entities.Vehiculo;
import com.example.Proyecto.Entities.VehiculoPersona;
import com.example.Proyecto.Repository.PersonaRepository;
import com.example.Proyecto.Repository.VehiculoPersonaRepository;
import com.example.Proyecto.Repository.VehiculoRepository;
import com.example.Proyecto.Services.Interfaces.IConductorService;

@Service("ConductorService")
public class ConductorServiceImpl implements IConductorService {


    @Autowired
    @Qualifier("IPersonaRepo")
    private PersonaRepository personaRepository;

    @Autowired
    @Qualifier("IVehiculoRepo")
    private VehiculoRepository vehiculoRepository;

    @Autowired
    @Qualifier("IVehiculoPersonaRepo")
    private VehiculoPersonaRepository vehiculoPersonaRepository;

    @Override
    public boolean asociarVehiculos(int idPersona, List<Integer> vehiculoIds) {
        Persona persona = personaRepository.findById(idPersona);
        if (persona == null || persona.getTipoPersona() != TipoPersona.C
                || vehiculoIds == null || vehiculoIds.isEmpty()) {
            return false;
        }
        for (Integer vehiculoId : vehiculoIds) {
            Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId.intValue());
            if (vehiculo == null) {
                return false;
            }
            VehiculoPersona existente = vehiculoPersonaRepository.findByPersonaIdAndVehiculoId(idPersona, vehiculoId);
            if (existente != null) {
                continue;
            }
            VehiculoPersona vp = new VehiculoPersona();
            vp.setPersona(persona);
            vp.setVehiculo(vehiculo);
            vp.setFechaAsociacion(LocalDate.now());
            vp.setEstado(EstadoConductorVehiculo.EA);
            vehiculoPersonaRepository.save(vp);
        }
        return true;
    }

    @Override
    public boolean cambiarEstado(int idPersona, int idVehiculo, EstadoConductorVehiculo estado) {
        if (estado == null) {
            return false;
        }
        VehiculoPersona vp = vehiculoPersonaRepository.findByPersonaIdAndVehiculoId(idPersona, idVehiculo);
        if (vp == null) {
            return false;
        }
        vp.setEstado(estado);
        vehiculoPersonaRepository.save(vp);
        return true;
    }
}
