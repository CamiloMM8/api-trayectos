package com.example.Proyecto.Services.Interfaces;

import java.util.List;
import java.util.Map;

import com.example.Proyecto.Entities.Vehiculo;
import com.example.Proyecto.Entities.VehiculoPersona;

public interface IPublicConsultaService {
    List<Vehiculo> vehiculosConDocumentosVencidos();
    List<VehiculoPersona> conductoresPuedenOperar();
    Vehiculo vehiculoDetallePorPlaca(String placa);
    List<Vehiculo> vehiculosDocumentosPorVencer(int dias);
    List<Map<String, Object>> totalPersonasPorTipo();
}
