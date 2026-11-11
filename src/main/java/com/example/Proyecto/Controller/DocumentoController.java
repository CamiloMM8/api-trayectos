package com.example.Proyecto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Proyecto.Entities.Documento;
import com.example.Proyecto.Services.DocumentoServiceImpl;

@RestController
@RequestMapping("/ProyectoV1")
public class DocumentoController {

    @Autowired
    @Qualifier("DocumentoService")
    private DocumentoServiceImpl documentoService;

    @PostMapping("/documento")
    public boolean agregarDocumento(@RequestBody @Validated Documento documento) {
        return documentoService.guardar(documento);
    }

    @PutMapping("/documento")
    public boolean editarDocumento(@RequestBody @Validated Documento documento) {
        return documentoService.actualizar(documento);
    }

    @DeleteMapping("/documento/{id}")
    public boolean eliminarDocumento(@PathVariable("id") int id) {
        return documentoService.eliminar(id);
    }

    @GetMapping("/documentos")
    public List<Documento> listadoDocumentos(Pageable pageable) {
        return documentoService.consultarDocumentos(pageable);
    }

    @GetMapping("/documento/id/{id}")
    public Documento getById(@PathVariable("id") int id) {
        return documentoService.findById(id);
    }

    @GetMapping("/documento/codigo/{codigo}")
    public Documento getByCodigo(@PathVariable("codigo") String codigo) {
        return documentoService.findByCodigo(codigo);
    }
}
