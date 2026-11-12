package com.example.Proyecto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Proyecto.Request.TrayectoRequest;
import com.example.Proyecto.Response.RutaAgrupadaResponse;
import com.example.Proyecto.Response.TrayectoDetalleResponse;
import com.example.Proyecto.Services.TrayectoServiceImpl;

@RestController
@RequestMapping("/ProyectoV1")
@CrossOrigin(origins = "http://localhost")
public class TrayectoController {

    @Autowired
    @Qualifier("TrayectoService")
    private TrayectoServiceImpl trayectoService;

    @PostMapping("/trayecto")
    public ResponseEntity<?> registrar(@RequestBody TrayectoRequest request) {
        try {
            TrayectoDetalleResponse creado = trayectoService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/trayecto/ruta/{codigo}")
    public List<TrayectoDetalleResponse> consultarPorCodigo(@PathVariable("codigo") String codigo) {
        return trayectoService.consultarPorCodigoRuta(codigo);
    }

    @GetMapping("/trayecto/conductor/{identificacion}")
    public List<RutaAgrupadaResponse> consultarPorConductor(@PathVariable("identificacion") String identificacion) {
        return trayectoService.consultarCodigosPorConductor(identificacion);
    }

    @GetMapping("/trayecto/vehiculo/{placa}")
    public List<RutaAgrupadaResponse> consultarPorPlaca(@PathVariable("placa") String placa) {
        return trayectoService.consultarPorPlaca(placa);
    }

    @GetMapping("/trayecto/restringidos")
    public List<TrayectoDetalleResponse> consultarRestringidos() {
        return trayectoService.consultarRestringidos();
    }
}
