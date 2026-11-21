package com.example.Proyecto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Proyecto.Entities.Vehiculo;
import com.example.Proyecto.Entities.VehiculoDocumento;
import com.example.Proyecto.Enums.EstadoVehiculoDocumento;
import com.example.Proyecto.Enums.TipoVehiculo;
import com.example.Proyecto.Services.VehiculoServiceImpl;

@RestController
@RequestMapping("/ProyectoV1")
public class VehiculoController {

    @Autowired
    @Qualifier("VehiculoService")
    private VehiculoServiceImpl vehiculoService;

    @PostMapping("/vehiculo")
    public boolean agregarVehiculo(@RequestBody @Validated Vehiculo vehiculo) {
        return vehiculoService.guardar(vehiculo);
    }

    @PutMapping("/vehiculo")
    public boolean editarVehiculo(@RequestBody @Validated Vehiculo vehiculo) {
        return vehiculoService.actualizar(vehiculo);
    }

    @DeleteMapping("/vehiculo/{id}")
    public boolean eliminarVehiculo(@PathVariable("id") int id) {
        return vehiculoService.eliminar(id);
    }

    @GetMapping("/vehiculos")
    public List<Vehiculo> listadoVehiculos(Pageable pageable) {
        return vehiculoService.consultarVehiculos(pageable);
    }

    @GetMapping("/vehiculo/id/{id}")
    public Vehiculo getById(@PathVariable("id") int id) {
        return vehiculoService.findById(id);
    }

    @GetMapping("/vehiculo/placa/{placa}")
    public Vehiculo getByPlaca(@PathVariable("placa") String placa) {
        return vehiculoService.findByPlaca(placa);
    }

    @GetMapping("/vehiculo/tipo/{tipoVehiculo}")
    public List<Vehiculo> getByTipoVehiculo(@PathVariable("tipoVehiculo") String tipoVehiculo) {
        return vehiculoService.findByTipoVehiculo(TipoVehiculo.fromCodigo(tipoVehiculo));
    }

    @GetMapping("/vehiculo/documento/{codigoDocumento}")
    public List<Vehiculo> getByCodigoDocumento(@PathVariable("codigoDocumento") String codigoDocumento) {
        return vehiculoService.findByCodigoDocumento(codigoDocumento);
    }

    @GetMapping("/vehiculo/estado/{estado}")
    public List<Vehiculo> getByEstadoDocumento(@PathVariable("estado") String estado) {
        return vehiculoService.findByEstadoDocumento(EstadoVehiculoDocumento.fromValor(estado));
    }

    @PostMapping("/vehiculo/{id}/documento")
    public boolean agregarDocumentoVehiculo(@PathVariable("id") int id,
            @RequestBody @Validated VehiculoDocumento vehiculoDocumento) {
        return vehiculoService.agregarDocumento(id, vehiculoDocumento);
    }

    @PostMapping(value = "/vehiculo/documentos/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean cargarDocumentoPdf(@RequestParam("vehiculoDocumentoId") int vehiculoDocumentoId,
            @RequestParam("archivo") MultipartFile archivo) {
        return vehiculoService.cargarDocumentoPdf(vehiculoDocumentoId, archivo);
    }
}
