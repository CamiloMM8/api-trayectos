package com.example.Proyecto.Entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UsuarioPK implements Serializable {

    @Column(name = "login")
    private String login;

    @Column(name = "idpersona")
    private int idPersona;

    public UsuarioPK() {
    }

    public UsuarioPK(String login, int idPersona) {
        this.login = login;
        this.idPersona = idPersona;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UsuarioPK that = (UsuarioPK) o;
        return idPersona == that.idPersona && Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, idPersona);
    }
}
