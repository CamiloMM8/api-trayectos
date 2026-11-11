package com.example.Proyecto.Services;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Proyecto.Entities.Documento;
import com.example.Proyecto.Repository.DocumentoRepository;
import com.example.Proyecto.Services.Interfaces.IDocumentoService;

@Service("DocumentoService")
public class DocumentoServiceImpl implements IDocumentoService {

    @Autowired
    @Qualifier("IDocumentoRepo")
    private DocumentoRepository documentoRepository;

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(DocumentoServiceImpl.class);

    @Override
    public boolean guardar(Documento documento) {
        try {
            if (documento == null || !validarDocumento(documento)) {
                logger.error("ERROR AGREGAR_DOCUMENTO: DATOS INVALIDOS");
                return false;
            }
            documentoRepository.save(documento);
            return true;
        } catch (Exception e) {
            logger.error("ERROR AGREGAR_DOCUMENTO: NO SE HA GUARDADO");
            return false;
        }
    }

    @Override
    public boolean actualizar(Documento documento) {
        try {
            if (documento == null || documento.getId() == 0 || !validarDocumento(documento)) {
                logger.error("ERROR EDITAR_DOCUMENTO: DATOS INVALIDOS");
                return false;
            }
            documentoRepository.save(documento);
            return true;
        } catch (Exception e) {
            logger.error("ERROR EDITAR_DOCUMENTO: NO SE HA EDITADO");
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        try {
            if (id == 0) {
                logger.error("ERROR ELIMINAR_DOCUMENTO: ID ES 0");
                return false;
            }
            Documento documento = documentoRepository.findById(id);
            if (documento == null) {
                return false;
            }
            documentoRepository.delete(documento);
            return true;
        } catch (Exception e) {
            logger.error("ERROR ELIMINAR_DOCUMENTO: NO SE HA ELIMINADO");
            return false;
        }
    }

    @Override
    public List<Documento> consultarDocumentos(Pageable pageable) {
        return documentoRepository.findAll(pageable).getContent();
    }

    @Override
    public Documento findById(int id) {
        return documentoRepository.findById(id);
    }

    @Override
    public Documento findByCodigo(String codigo) {
        return documentoRepository.findByCodigoDocumento(codigo);
    }

    private boolean validarDocumento(Documento documento) {
        if (documento.getCodigoDocumento() == null || documento.getCodigoDocumento().isBlank()) {
            return false;
        }
        if (documento.getNombreDocumento() == null || documento.getNombreDocumento().isBlank()) {
            return false;
        }
        return documento.getTiposVehiculos() != null && documento.getObligatorio() != null;
    }
}
