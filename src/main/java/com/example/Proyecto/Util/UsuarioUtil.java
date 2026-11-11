package com.example.Proyecto.Util;

import java.util.UUID;

import com.example.Proyecto.Entities.Persona;

public final class UsuarioUtil {

    private UsuarioUtil() {
    }

    public static String generarLogin(Persona persona) {
        String n = persona.getNombres().trim();
        String a = persona.getApellidos().trim();
        char ln = Character.toUpperCase(n.charAt(0));
        char la = Character.toUpperCase(a.charAt(0));
        return "" + ln + la + persona.getIdentificacion();
    }

    public static String generarPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    public static String generarApiKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
