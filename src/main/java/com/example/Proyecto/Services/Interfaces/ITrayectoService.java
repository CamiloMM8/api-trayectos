package com.example.Proyecto.Services.Interfaces;

import java.util.List;

import com.example.Proyecto.Request.TrayectoRequest;
import com.example.Proyecto.Response.RutaAgrupadaResponse;
import com.example.Proyecto.Response.TrayectoDetalleResponse;

public interface ITrayectoService {

    TrayectoDetalleResponse registrar(TrayectoRequest request);

    String validarRegistro(TrayectoRequest request);

    List<TrayectoDetalleResponse> consultarPorCodigoRuta(String codigoRuta);

    List<RutaAgrupadaResponse> consultarCodigosPorConductor(String identificacion);

    List<RutaAgrupadaResponse> consultarPorPlaca(String placa);

    List<TrayectoDetalleResponse> consultarRestringidos();
}
