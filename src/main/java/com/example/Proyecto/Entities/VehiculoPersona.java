package com.example.Proyecto.Entities;

import java.io.Serializable;
import java.time.LocalDate;

import com.example.Proyecto.Enums.EstadoConductorVehiculo;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "VehiculoPersona", schema = "PROYECTO")
public class VehiculoPersona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @JsonBackReference("vehiculo-conductores")
    @ManyToOne
    @JoinColumn(name = "VehiculoID", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "PersonaID", nullable = false)
    private Persona persona;

    @Column(name = "FechaAsociacion", nullable = false)
    private LocalDate fechaAsociacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", nullable = false, length = 2)
    private EstadoConductorVehiculo estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public LocalDate getFechaAsociacion() {
        return fechaAsociacion;
    }

    public void setFechaAsociacion(LocalDate fechaAsociacion) {
        this.fechaAsociacion = fechaAsociacion;
    }

    public EstadoConductorVehiculo getEstado() {
        return estado;
    }

    public void setEstado(EstadoConductorVehiculo estado) {
        this.estado = estado;
    }
}
