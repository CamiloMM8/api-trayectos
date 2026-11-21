package com.example.Proyecto.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Proyecto.Entities.Vehiculo;
import com.example.Proyecto.Entities.VehiculoPersona;
import com.example.Proyecto.Enums.EstadoConductorVehiculo;
import com.example.Proyecto.Enums.TipoPersona;
import com.example.Proyecto.Repository.PersonaRepository;
import com.example.Proyecto.Repository.VehiculoPersonaRepository;
import com.example.Proyecto.Repository.VehiculoRepository;
import com.example.Proyecto.Services.Interfaces.IPublicConsultaService;

@Service("PublicConsultaService")
public class PublicConsultaServiceImpl implements IPublicConsultaService {

    @Autowired
    @Qualifier("IVehiculoRepo")
    private VehiculoRepository vehiculoRepository;

    @Autowired
    @Qualifier("IVehiculoPersonaRepo")
    private VehiculoPersonaRepository vehiculoPersonaRepository;

    @Autowired
    @Qualifier("IPersonaRepo")
    private PersonaRepository personaRepository;

    @Override
    public List<Vehiculo> vehiculosConDocumentosVencidos() {
        return vehiculoRepository.findWithDocumentosVencidos();
    }

    @Override
    public List<VehiculoPersona> conductoresPuedenOperar() {
        return vehiculoPersonaRepository.findConductoresByEstado(EstadoConductorVehiculo.PO, TipoPersona.C);
    }

    @Override
    @Transactional(readOnly = true)
    public Vehiculo vehiculoDetallePorPlaca(String placa) {
        Vehiculo vehiculo = vehiculoRepository.findDetalleByPlaca(placa);
        if (vehiculo == null) {
            return null;
        }
        Hibernate.initialize(vehiculo.getConductores());
        for (VehiculoPersona conductor : vehiculo.getConductores()) {
            Hibernate.initialize(conductor.getPersona());
        }
        return vehiculo;
    }

    @Override
    public List<Vehiculo> vehiculosDocumentosPorVencer(int dias) {
        return vehiculoRepository.findWithDocumentosPorVencer(LocalDate.now().plusDays(dias));
    }

    @Override
    public List<Map<String, Object>> totalPersonasPorTipo() {
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Object[] row : personaRepository.totalPorTipoPersona()) {
            Map<String, Object> item = new HashMap<>();
            item.put("tipoPersona", row[0] != null ? row[0].toString() : null);
            item.put("total", row[1]);
            resultado.add(item);
        }
        return resultado;
    }
}
