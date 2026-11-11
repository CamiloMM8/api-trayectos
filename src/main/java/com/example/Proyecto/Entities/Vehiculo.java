package com.example.Proyecto.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.Proyecto.Enums.TipoCombustible;
import com.example.Proyecto.Enums.TipoServicio;
import com.example.Proyecto.Enums.TipoVehiculo;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vehiculo", schema = "PROYECTO")
public class Vehiculo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @JsonProperty("tipoVehiculo")
    @JsonAlias("TipoVehiculo")
    @Enumerated(EnumType.STRING)
    @Column(name = "TipoVehiculo", nullable = false)
    private TipoVehiculo tipoVehiculo;

    @JsonProperty("placa")
    @JsonAlias("Placa")
    @Column(name = "Placa", nullable = false, unique = true, length = 6)
    private String placa;

    @JsonProperty("tipoServicio")
    @JsonAlias("TipoServicio")
    @Enumerated(EnumType.STRING)
    @Column(name = "TipoServicio", nullable = false, length = 2)
    private TipoServicio tipoServicio;

    @JsonProperty("tipoCombustible")
    @JsonAlias("TipoCombustible")
    @Enumerated(EnumType.STRING)
    @Column(name = "TipoCombustible", nullable = false)
    private TipoCombustible tipoCombustible;

    @JsonProperty("capacidadPasajeros")
    @JsonAlias("CapacidadPasajeros")
    @Column(name = "CapacidadPasajeros", nullable = false)
    private int capacidadPasajeros;

    @JsonProperty("color")
    @JsonAlias("Color")
    @Column(name = "Color", nullable = false, length = 7)
    private String color;

    @JsonProperty("modelo")
    @JsonAlias("Modelo")
    @Column(name = "Modelo", nullable = false)
    private int modelo;

    @JsonProperty("marca")
    @JsonAlias("Marca")
    @Column(name = "Marca", nullable = false)
    private String marca;

    @JsonProperty("linea")
    @JsonAlias("Linea")
    @Column(name = "Linea", nullable = false)
    private String linea;

    @JsonManagedReference("vehiculo-documentos")
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehiculoDocumento> documentos = new ArrayList<>();

    @JsonManagedReference("vehiculo-conductores")
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehiculoPersona> conductores = new ArrayList<>();

    public Vehiculo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public TipoCombustible getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(TipoCombustible tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public int getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public void setCapacidadPasajeros(int capacidadPasajeros) {
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public List<VehiculoDocumento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<VehiculoDocumento> documentos) {
        this.documentos = documentos;
    }

    public List<VehiculoPersona> getConductores() {
        return conductores;
    }

    public void setConductores(List<VehiculoPersona> conductores) {
        this.conductores = conductores;
    }
}
