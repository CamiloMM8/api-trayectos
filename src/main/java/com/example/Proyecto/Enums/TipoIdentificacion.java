package com.example.Proyecto.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Valores permitidos en BD (columna tipo_identificacion).
 */
public enum TipoIdentificacion {
    CC("Cédula de ciudadanía");

    private final String descripcion;

    TipoIdentificacion(String descripcion) {
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
    public static TipoIdentificacion fromCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        return valueOf(codigo.trim().toUpperCase());
    }
}
