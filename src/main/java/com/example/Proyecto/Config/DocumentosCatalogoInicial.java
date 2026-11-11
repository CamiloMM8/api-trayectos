package com.example.Proyecto.Config;

import java.util.ArrayList;
import java.util.List;

import com.example.Proyecto.Entities.Documento;
import com.example.Proyecto.Enums.ReglaObligatoriedad;
import com.example.Proyecto.Enums.TiposVehiculosDocumento;

/**
 * Ejemplos de catálogo: cruce tiposVehiculos (a quién aplica) + obligatorio (si es exigible y para quién).
 */
public final class DocumentosCatalogoInicial {

    private DocumentosCatalogoInicial() {
    }

    public static List<Documento> ejemplos() {
        List<Documento> lista = new ArrayList<>();

        lista.add(doc(
                "SOAT",
                "Seguro Obligatorio de Accidentes de Tránsito",
                TiposVehiculosDocumento.AM,
                ReglaObligatoriedad.RR,
                "Aplica a automóvil y moto. Obligatorio en ambos (RR). Todo vehículo debería llevarlo asociado."));

        lista.add(doc(
                "RTM",
                "Revisión técnico-mecánica automóvil",
                TiposVehiculosDocumento.A,
                ReglaObligatoriedad.RA,
                "Aplica a automóvil y moto. Obligatorio en ambos (RR). Todo vehículo debería llevarlo asociado."));

        lista.add(doc(
                "LIC-TRANS",
                "Licencia de tránsito del vehículo",
                TiposVehiculosDocumento.AM,
                ReglaObligatoriedad.RR,
                "Tarjeta de propiedad/licencia de tránsito. Aplica a ambos tipos y es obligatoria en ambos (RR)."));

        lista.add(doc(
                "IMP-AUTO",
                "Impuesto vehicular automóvil",
                TiposVehiculosDocumento.A,
                ReglaObligatoriedad.RA,
                "Documento tributario solo para automóvil: aplica A y obligatorio RA. Ejemplo de regla exclusiva de auto."));

        lista.add(doc(
                "IMP-MOTO",
                "Impuesto vehicular moto",
                TiposVehiculosDocumento.A,
                ReglaObligatoriedad.RA,
                "Documento tributario solo para MOTO: aplica M y obligatorio RM. Ejemplo de regla exclusiva de moto."));

        return lista;
    }

    private static Documento doc(String codigo, String nombre, TiposVehiculosDocumento tipos,
            ReglaObligatoriedad obligatorio, String descripcion) {
        Documento d = new Documento();
        d.setCodigoDocumento(codigo);
        d.setNombreDocumento(nombre);
        d.setTiposVehiculos(tipos);
        d.setObligatorio(obligatorio);
        d.setDescripcion(descripcion);
        return d;
    }
}
