package com.example.Proyecto.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Proyecto.Entities.TrayectoTmp;
import com.example.Proyecto.Services.ExcelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ProyectoV1")
@Tag(name = "Cargue de trayectos", description = "Cargue masivo de trayectos desde Excel y consulta del resultado")
public class CargueFileExcel {

    @Autowired
    @Qualifier("ExcelService")
    private ExcelService excelService;

    @Operation(summary = "Cargar trayectos desde Excel",
            description = "Lee la plantilla, invoca el procedimiento de cargue, luego el de validacion y por ultimo el de procesado. Columnas: identificacion, placa, codigoRuta, ubicacion, ordenParada, latitud, longitud, loginRegistro.")
    @PostMapping(value = "/trayecto/cargue", consumes = "multipart/form-data")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            Double idCargue = excelService.processExcel(file);
            Map<String, Object> respuesta = new LinkedHashMap<>();
            respuesta.put("idCargue", idCargue.longValue());
            respuesta.put("mensaje", "Archivo subido y procesado");
            respuesta.put("registros", excelService.consultarCargue(idCargue));
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Consultar un cargue por su identificador")
    @GetMapping("/trayecto/cargue/{idCargue}")
    public List<TrayectoTmp> consultarCargue(@PathVariable("idCargue") String idCargue) {
        return excelService.consultarCargue(Double.valueOf(idCargue));
    }
}
