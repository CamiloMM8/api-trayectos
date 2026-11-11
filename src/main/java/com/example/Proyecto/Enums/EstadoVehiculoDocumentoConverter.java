package com.example.Proyecto.Enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstadoVehiculoDocumentoConverter implements AttributeConverter<EstadoVehiculoDocumento, String> {

    @Override
    public String convertToDatabaseColumn(EstadoVehiculoDocumento attribute) {
        return attribute == null ? null : attribute.getValorBd();
    }

    @Override
    public EstadoVehiculoDocumento convertToEntityAttribute(String dbData) {
        return dbData == null ? null : EstadoVehiculoDocumento.fromValor(dbData);
    }
}
