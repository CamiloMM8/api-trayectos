package com.example.Proyecto.Services.Interfaces;

public interface IUsuarioService {
    boolean validarApiKey(String login, String apiKey);
    boolean cambiarPassword(String login, String nuevaPassword);
    String regenerarApiKey(String login);
}
