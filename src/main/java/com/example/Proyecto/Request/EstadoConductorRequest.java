package com.example.Proyecto.Request;

import com.example.Proyecto.Enums.EstadoConductorVehiculo;

public class EstadoConductorRequest {
    private EstadoConductorVehiculo estado;

    public EstadoConductorVehiculo getEstado() {
        return estado;
    }

    public void setEstado(EstadoConductorVehiculo estado) {
        this.estado = estado;
    }
}
