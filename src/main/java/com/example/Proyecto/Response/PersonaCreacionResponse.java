package com.example.Proyecto.Response;

import com.example.Proyecto.Entities.Persona;

public class PersonaCreacionResponse {
    private Persona persona;
    private String loginGenerado;
    private String passwordGenerado;
    private String apiKeyGenerado;

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getLoginGenerado() {
        return loginGenerado;
    }

    public void setLoginGenerado(String loginGenerado) {
        this.loginGenerado = loginGenerado;
    }

    public String getPasswordGenerado() {
        return passwordGenerado;
    }

    public void setPasswordGenerado(String passwordGenerado) {
        this.passwordGenerado = passwordGenerado;
    }

    public String getApiKeyGenerado() {
        return apiKeyGenerado;
    }

    public void setApiKeyGenerado(String apiKeyGenerado) {
        this.apiKeyGenerado = apiKeyGenerado;
    }
}
