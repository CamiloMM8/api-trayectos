package com.example.Proyecto.Entities;

import java.io.Serializable;

import com.example.Proyecto.Enums.EstadoCargue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trayecto_tmp", schema = "PROYECTO")
public class TrayectoTmp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "identificacion")
    private String identificacion;

    @Column(name = "placa")
    private String placa;

    @Column(name = "codigo_ruta")
    private String codigoRuta;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "orden_parada")
    private String ordenParada;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "longitud")
    private String longitud;

    @Column(name = "login_registro")
    private String loginRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, columnDefinition = "ENUM('CARGADO','VALIDADO','PROCESADO','ERROR')")
    private EstadoCargue estado;

    @Column(name = "observacion", length = 1000)
    private String observacion;

    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "id_cargue", nullable = false)
    private Double idCargue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCodigoRuta() {
        return codigoRuta;
    }

    public void setCodigoRuta(String codigoRuta) {
        this.codigoRuta = codigoRuta;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getOrdenParada() {
        return ordenParada;
    }

    public void setOrdenParada(String ordenParada) {
        this.ordenParada = ordenParada;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLoginRegistro() {
        return loginRegistro;
    }

    public void setLoginRegistro(String loginRegistro) {
        this.loginRegistro = loginRegistro;
    }

    public EstadoCargue getEstado() {
        return estado;
    }

    public void setEstado(EstadoCargue estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getIdCargue() {
        return idCargue;
    }

    public void setIdCargue(Double idCargue) {
        this.idCargue = idCargue;
    }
}
