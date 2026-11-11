package com.example.Proyecto.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.Proyecto.Entities.Documento;
import com.example.Proyecto.Repository.DocumentoRepository;

@Component
@Order(2)
public class DocumentosInicialesConfig implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DocumentosInicialesConfig.class);

    @Autowired
    @Qualifier("IDocumentoRepo")
    private DocumentoRepository documentoRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (documentoRepository.count() > 0) {
            return;
        }
        for (Documento documento : DocumentosCatalogoInicial.ejemplos()) {
            documentoRepository.save(documento);
        }
        log.info("Catálogo de documentos de ejemplo creado ({} registros). Consulta GET /ProyectoV1/documentos con token admin.",
                documentoRepository.count());
    }
}
