package com.example.Proyecto.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoServicio {
    Pu("Público"),
    Pr("Privado");

    private final String descripcion;

    TipoServicio(String descripcion) {
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
    public static TipoServicio fromCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        return valueOf(codigo.trim());
    }
}
