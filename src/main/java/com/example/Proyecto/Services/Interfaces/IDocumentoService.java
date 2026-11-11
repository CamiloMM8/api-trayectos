package com.example.Proyecto.Services.Interfaces;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.Proyecto.Entities.Documento;

public interface IDocumentoService {

    boolean guardar(Documento documento);

    boolean actualizar(Documento documento);

    boolean eliminar(int id);

    List<Documento> consultarDocumentos(Pageable pageable);

    Documento findById(int id);

    Documento findByCodigo(String codigo);
}
