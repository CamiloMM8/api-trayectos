package com.example.Proyecto.Enums;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CatalogoEnums {

    private CatalogoEnums() {
    }

    public static Map<String, List<Map<String, String>>> todos() {
        Map<String, List<Map<String, String>>> catalogo = new LinkedHashMap<>();
        catalogo.put("tipoIdentificacion", items(TipoIdentificacion.class));
        catalogo.put("tipoPersona", items(TipoPersona.class));
        catalogo.put("tipoVehiculo", items(TipoVehiculo.class));
        catalogo.put("tipoServicio", items(TipoServicio.class));
        catalogo.put("tipoCombustible", items(TipoCombustible.class));
        catalogo.put("tiposVehiculosDocumento", items(TiposVehiculosDocumento.class));
        catalogo.put("obligatorio", items(ReglaObligatoriedad.class));
        catalogo.put("estadoVehiculoDocumento", itemsEstadoDocumento());
        catalogo.put("estadoConductorVehiculo", items(EstadoConductorVehiculo.class));
        return catalogo;
    }

    private static <E extends Enum<E>> List<Map<String, String>> items(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants()).map(e -> {
            Map<String, String> item = new LinkedHashMap<>();
            if (e instanceof TipoIdentificacion t) {
                item.put("codigo", t.getCodigo());
                item.put("descripcion", t.getDescripcion());
            } else if (e instanceof TipoPersona t) {
                item.put("codigo", t.getCodigo());
                item.put("descripcion", t.getDescripcion());
            } else if (e instanceof TipoVehiculo t) {
                item.put("codigo", t.getCodigo());
                item.put("descripcion", t.getDescripcion());
            } else if (e instanceof TipoServicio t) {
                item.put("codigo", t.getCodigo());
                item.put("descripcion", t.getDescripcion());
            } else if (e instanceof TipoCombustible t) {
                item.put("codigo", t.getCodigo());
                item.put("descripcion", t.getDescripcion());
            } else if (e instanceof TiposVehiculosDocumento t) {
                item.put("codigo", t.getCodigo());
                item.put("descripcion", t.getDescripcion());
            } else if (e instanceof ReglaObligatoriedad t) {
                item.put("codigo", t.getCodigo());
                item.put("descripcion", t.getDescripcion());
            } else if (e instanceof EstadoConductorVehiculo t) {
                item.put("codigo", t.getCodigo());
                item.put("descripcion", t.getDescripcion());
            }
            return item;
        }).collect(Collectors.toList());
    }

    private static List<Map<String, String>> itemsEstadoDocumento() {
        return Arrays.stream(EstadoVehiculoDocumento.values()).map(e -> {
            Map<String, String> item = new LinkedHashMap<>();
            item.put("codigo", e.getValorBd());
            item.put("descripcion", e.getDescripcion());
            return item;
        }).collect(Collectors.toList());
    }
}
