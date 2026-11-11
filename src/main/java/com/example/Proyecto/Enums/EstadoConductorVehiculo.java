package com.example.Proyecto.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoConductorVehiculo {
    PO("Puede operar"),
    EA("En espera de aprobación"),
    RO("Restringido / no puede operar");

    private final String descripcion;

    EstadoConductorVehiculo(String descripcion) {
        this.descripcion = descripcion;
    }

    @JsonValue
    public String getCodigo() {
        return name();
    }

    public String getDescripcion() {
        return descripcion;
    }

    @JsonCreator
    public static EstadoConductorVehiculo fromCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        return valueOf(codigo.trim().toUpperCase());
    }
}
