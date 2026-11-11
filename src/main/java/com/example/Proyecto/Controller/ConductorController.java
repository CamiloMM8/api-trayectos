package com.example.Proyecto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Proyecto.Request.ConductorVehiculosRequest;
import com.example.Proyecto.Request.EstadoConductorRequest;
import com.example.Proyecto.Services.ConductorServiceImpl;

@RestController
@RequestMapping("/ProyectoV1")
public class ConductorController {

    @Autowired
    @Qualifier("ConductorService")
    private ConductorServiceImpl conductorService;

    @PostMapping("/conductor/{idPersona}/vehiculos")
    public boolean asociarVehiculos(@PathVariable("idPersona") int idPersona,
            @RequestBody ConductorVehiculosRequest request) {
        return conductorService.asociarVehiculos(idPersona, request.getVehiculoIds());
    }

    @PutMapping("/conductor/{idPersona}/vehiculo/{idVehiculo}/estado")
    public boolean cambiarEstado(@PathVariable("idPersona") int idPersona,
            @PathVariable("idVehiculo") int idVehiculo, @RequestBody EstadoConductorRequest request) {
        return conductorService.cambiarEstado(idPersona, idVehiculo, request.getEstado());
    }
}
