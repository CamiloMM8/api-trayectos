package com.example.Proyecto.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ReglaObligatoriedad {
    RA("Obligatorio automóvil"),
    RM("Obligatorio motocicleta"),
    RR("Obligatorio ambos tipos");

    private final String descripcion;

    ReglaObligatoriedad(String descripcion) {
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
    public static ReglaObligatoriedad fromCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        return valueOf(codigo.trim().toUpperCase());
    }
}
