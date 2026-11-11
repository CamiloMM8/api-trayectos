package com.example.Proyecto.Repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Proyecto.Entities.Documento;

@Repository("IDocumentoRepo")
public interface DocumentoRepository extends JpaRepository<Documento, Serializable> {

    Documento findById(int id);

    Documento findByCodigoDocumento(String codigoDocumento);

    Page<Documento> findAll(Pageable pageable);

    List<Documento> findAll();
}
