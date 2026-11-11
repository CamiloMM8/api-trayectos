package com.example.Proyecto.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Proyecto.Entities.Vehiculo;
import com.example.Proyecto.Entities.VehiculoPersona;
import com.example.Proyecto.Enums.CatalogoEnums;
import com.example.Proyecto.Services.PublicConsultaServiceImpl;

@RestController
@RequestMapping("/ProyectoV1/public")
public class PublicConsultaController {

    @Autowired
    @Qualifier("PublicConsultaService")
    private PublicConsultaServiceImpl publicConsultaService;

    @GetMapping("/vehiculos/documentos-vencidos")
    public List<Vehiculo> vehiculosDocumentosVencidos() {
        return publicConsultaService.vehiculosConDocumentosVencidos();
    }

    @GetMapping("/conductores/pueden-operar")
    public List<VehiculoPersona> conductoresPuedenOperar() {
        return publicConsultaService.conductoresPuedenOperar();
    }

    @GetMapping("/vehiculo/placa/{placa}")
    public Vehiculo vehiculoDetallePorPlaca(@PathVariable("placa") String placa) {
        return publicConsultaService.vehiculoDetallePorPlaca(placa);
    }

    @GetMapping("/vehiculos/documentos-por-vencer/{dias}")
    public List<Vehiculo> vehiculosDocumentosPorVencer(@PathVariable("dias") int dias) {
        return publicConsultaService.vehiculosDocumentosPorVencer(dias);
    }

    @GetMapping("/personas/total-por-tipo")
    public List<Map<String, Object>> totalPersonasPorTipo() {
        return publicConsultaService.totalPersonasPorTipo();
    }

    /** Valores permitidos (enums) para campos seleccionables del PDF — sin autenticación. */
    @GetMapping("/catalogos")
    public Map<String, List<Map<String, String>>> catalogos() {
        return CatalogoEnums.todos();
    }
}
