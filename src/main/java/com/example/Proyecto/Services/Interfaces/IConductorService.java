package com.example.Proyecto.Services.Interfaces;

import java.util.List;

import com.example.Proyecto.Enums.EstadoConductorVehiculo;

public interface IConductorService {
    boolean asociarVehiculos(int idPersona, List<Integer> vehiculoIds);

    boolean cambiarEstado(int idPersona, int idVehiculo, EstadoConductorVehiculo estado);
}
