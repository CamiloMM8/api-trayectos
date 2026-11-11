package com.example.Proyecto.Entities;

import java.io.Serializable;

import com.example.Proyecto.Enums.ReglaObligatoriedad;
import com.example.Proyecto.Enums.TiposVehiculosDocumento;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Documento", schema = "PROYECTO")
public class Documento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @JsonProperty("codigoDocumento")
    @JsonAlias("CodigoDocumento")
    @Column(name = "CodigoDocumento", nullable = false, unique = true)
    private String codigoDocumento;

    @JsonProperty("nombreDocumento")
    @JsonAlias("NombreDocumento")
    @Column(name = "NombreDocumento", nullable = false)
    private String nombreDocumento;

    @JsonProperty("tiposVehiculos")
    @JsonAlias("TiposVehiculos")
    @Enumerated(EnumType.STRING)
    @Column(name = "TiposVehiculos", nullable = false, length = 2)
    private TiposVehiculosDocumento tiposVehiculos;

    @JsonProperty("obligatorio")
    @JsonAlias("Obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "Obligatorio", nullable = false, length = 2)
    private ReglaObligatoriedad obligatorio;

    @JsonProperty("descripcion")
    @JsonAlias("Descripcion")
    @Column(name = "Descripcion")
    private String descripcion;

    public Documento() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public TiposVehiculosDocumento getTiposVehiculos() {
        return tiposVehiculos;
    }

    public void setTiposVehiculos(TiposVehiculosDocumento tiposVehiculos) {
        this.tiposVehiculos = tiposVehiculos;
    }

    public ReglaObligatoriedad getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(ReglaObligatoriedad obligatorio) {
        this.obligatorio = obligatorio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
