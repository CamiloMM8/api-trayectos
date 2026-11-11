package com.example.Proyecto.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoVehiculo {
    Automovil("Automóvil — placa: 3 letras + 3 números (ABC123)"),
    Motocicleta("Motocicleta — placa: 3 letras + 2 números + 1 letra (ABC12D)");

    private final String descripcion;

    TipoVehiculo(String descripcion) {
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
    public static TipoVehiculo fromCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (TipoVehiculo t : values()) {
            if (t.name().equalsIgnoreCase(codigo.trim())) {
                return t;
            }
        }
        throw new IllegalArgumentException("TipoVehiculo no valido: " + codigo);
    }
}
