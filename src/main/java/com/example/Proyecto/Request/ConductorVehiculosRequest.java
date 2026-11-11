package com.example.Proyecto.Request;

import java.util.List;

public class ConductorVehiculosRequest {
    private List<Integer> vehiculoIds;

    public List<Integer> getVehiculoIds() {
        return vehiculoIds;
    }

    public void setVehiculoIds(List<Integer> vehiculoIds) {
        this.vehiculoIds = vehiculoIds;
    }
}
