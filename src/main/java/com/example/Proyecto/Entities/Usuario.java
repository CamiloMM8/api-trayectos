package com.example.Proyecto.Entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "UsrProyecto")
@Table(name = "usuario", schema = "PROYECTO")
public class Usuario implements Serializable {

    @EmbeddedId
    private UsuarioPK id = new UsuarioPK();

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @Column(name = "apikey")
    private String apikey;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idpersona", referencedColumnName = "ID", insertable = false, updatable = false)
    private Persona personaEntity;

    public UsuarioPK getId() {
        return id;
    }

    public void setId(UsuarioPK id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public Persona getPersonaEntity() {
        return personaEntity;
    }
}
