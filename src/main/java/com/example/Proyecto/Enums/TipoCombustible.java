package com.example.Proyecto.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoCombustible {
    Gasolina("Gasolina"),
    Gas("Gas"),
    Disel("Diésel");

    private final String descripcion;

    TipoCombustible(String descripcion) {
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
    public static TipoCombustible fromCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (TipoCombustible t : values()) {
            if (t.name().equalsIgnoreCase(codigo.trim())) {
                return t;
            }
        }
        throw new IllegalArgumentException("TipoCombustible no valido: " + codigo);
    }
}
