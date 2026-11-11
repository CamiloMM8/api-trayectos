package com.example.Proyecto.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Aplica a automóvil, moto o ambos. */
public enum TiposVehiculosDocumento {
    A("Automóvil"),
    M("Motocicleta"),
    AM("Automóvil y motocicleta");

    private final String descripcion;

    TiposVehiculosDocumento(String descripcion) {
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
    public static TiposVehiculosDocumento fromCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        return valueOf(codigo.trim().toUpperCase());
    }
}
