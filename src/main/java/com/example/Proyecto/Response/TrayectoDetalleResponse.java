package com.example.Proyecto.Response;

public class TrayectoDetalleResponse {

    private int id;
    private String codigoRuta;
    private int ordenParada;
    private String ubicacion;
    private Double latitud;
    private Double longitud;
    private String loginRegistro;
    private int idPersona;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private String estadoConductor;
    private int idVehiculo;
    private String placa;
    private String marca;
    private boolean vehiculoHabilitado;
    private String motivo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoRuta() {
        return codigoRuta;
    }

    public void setCodigoRuta(String codigoRuta) {
        this.codigoRuta = codigoRuta;
    }

    public int getOrdenParada() {
        return ordenParada;
    }

    public void setOrdenParada(int ordenParada) {
        this.ordenParada = ordenParada;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
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

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
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

    public String getEstadoConductor() {
        return estadoConductor;
    }

    public void setEstadoConductor(String estadoConductor) {
        this.estadoConductor = estadoConductor;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public boolean isVehiculoHabilitado() {
        return vehiculoHabilitado;
    }

    public void setVehiculoHabilitado(boolean vehiculoHabilitado) {
        this.vehiculoHabilitado = vehiculoHabilitado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
