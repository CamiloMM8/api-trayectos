package com.example.Proyecto.Response;

public class RutaAgrupadaResponse {

    private String codigoRuta;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private String placa;

    public RutaAgrupadaResponse() {
    }

    public RutaAgrupadaResponse(String codigoRuta) {
        this.codigoRuta = codigoRuta;
    }

    public String getCodigoRuta() {
        return codigoRuta;
    }

    public void setCodigoRuta(String codigoRuta) {
        this.codigoRuta = codigoRuta;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
