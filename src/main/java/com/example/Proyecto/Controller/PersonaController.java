package com.example.Proyecto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Proyecto.Entities.Persona;
import com.example.Proyecto.Response.PersonaCreacionResponse;
import com.example.Proyecto.Services.PersonaServiceImpl;

@RestController
@RequestMapping("/ProyectoV1")
public class PersonaController {

    @Autowired
    @Qualifier("PersonaService")
    private PersonaServiceImpl personaService;

    @PostMapping("/persona")
    public PersonaCreacionResponse crearPersona(@RequestBody Persona persona) {
        return personaService.guardar(persona);
    }

    @PutMapping("/persona")
    public boolean actualizarPersona(@RequestBody Persona persona) {
        return personaService.actualizar(persona);
    }

    @GetMapping("/persona/{id}")
    public Persona consultarPersona(@PathVariable("id") int id) {
        return personaService.consultarPorId(id);
    }

    @GetMapping("/personas")
    public List<Persona> listarPersonas() {
        return personaService.consultarTodos();
    }
}
