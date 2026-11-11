package com.example.Proyecto.Entities;

import java.io.Serializable;

import com.example.Proyecto.Enums.TipoIdentificacion;
import com.example.Proyecto.Enums.TipoPersona;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Persona", schema = "PROYECTO")
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Identificacion", nullable = false, unique = true)
    private String identificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "TipoIdentificacion", nullable = false, length = 2)
    private TipoIdentificacion tipoIdentificacion;

    @Column(name = "Nombres", nullable = false)
    private String nombres;

    @Column(name = "Apellidos", nullable = false)
    private String apellidos;

    @Column(name = "Correo", nullable = false)
    private String correo;

    @Enumerated(EnumType.STRING)
    @Column(name = "TipoPersona", nullable = false, length = 1)
    private TipoPersona tipoPersona;

    @JsonIgnore
    @OneToOne(mappedBy = "personaEntity")
    private Usuario usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
