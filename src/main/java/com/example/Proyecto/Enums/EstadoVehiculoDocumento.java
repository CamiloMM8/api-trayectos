package com.example.Proyecto.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoVehiculoDocumento {
    Habilitado("Habilitado", "Documento habilitado"),
    Vencido("Vencido", "Documento vencido"),
    EN_VERIFICACION("En Verificacion", "Documento en verificación");

    private final String valorBd;
    private final String descripcion;

    EstadoVehiculoDocumento(String valorBd, String descripcion) {
        this.valorBd = valorBd;
        this.descripcion = descripcion;
    }

    @JsonValue
    public String getValorBd() {
        return valorBd;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @JsonCreator
    public static EstadoVehiculoDocumento fromValor(String valor) {
        if (valor == null) {
            return null;
        }
        String v = valor.trim();
        for (EstadoVehiculoDocumento e : values()) {
            if (e.valorBd.equalsIgnoreCase(v) || e.name().equalsIgnoreCase(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException("EstadoVehiculoDocumento no valido: " + valor);
    }
}
