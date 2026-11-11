package com.example.Proyecto.Config.Model;

/**
 * Credenciales del administrador inicial (se crea al arrancar si no hay usuarios).
 */
public final class AdminPredeterminado {

    public static final String IDENTIFICACION = "1000000001";
    public static final String NOMBRES = "Ana";
    public static final String APELLIDOS = "Admin";
    public static final String CORREO = "admin.proyecto@ut.edu.co";
    public static final String PASSWORD = "Admin12345";
    public static final String API_KEY = "apikey-proyecto-admin-001";

    private AdminPredeterminado() {
    }
}
