package com.example.Proyecto.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Trayecto", schema = "PROYECTO")
public class Trayecto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "PersonaID", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "VehiculoID", nullable = false)
    private Vehiculo vehiculo;

    @Column(name = "CodigoRuta", nullable = false, length = 30)
    private String codigoRuta;

    @Column(name = "Ubicacion", nullable = false)
    private String ubicacion;

    @Column(name = "OrdenParada", nullable = false)
    private int ordenParada;

    @Column(name = "Latitud")
    private Double latitud;

    @Column(name = "Longitud")
    private Double longitud;

    @Column(name = "LoginRegistro", nullable = false)
    private String loginRegistro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
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

    public int getOrdenParada() {
        return ordenParada;
    }

    public void setOrdenParada(int ordenParada) {
        this.ordenParada = ordenParada;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getLoginRegistro() {
        return loginRegistro;
    }

    public void setLoginRegistro(String loginRegistro) {
        this.loginRegistro = loginRegistro;
    }
}
