package com.example.Proyecto.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A = Administrador (tiene usuario). C = Conductor (sin usuario).
 */
public enum TipoPersona {
    A("Administrador"),
    C("Conductor");

    private final String descripcion;

    TipoPersona(String descripcion) {
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
    public static TipoPersona fromCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        return valueOf(codigo.trim().toUpperCase());
    }
}
