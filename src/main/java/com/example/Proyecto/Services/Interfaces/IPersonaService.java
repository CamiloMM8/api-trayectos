package com.example.Proyecto.Services.Interfaces;

import java.util.List;

import com.example.Proyecto.Entities.Persona;
import com.example.Proyecto.Response.PersonaCreacionResponse;

public interface IPersonaService {
    PersonaCreacionResponse guardar(Persona persona);
    boolean actualizar(Persona persona);
    Persona consultarPorId(int id);
    List<Persona> consultarTodos();
}
