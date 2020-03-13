package com.examen.fi.RestHumanTech.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.fi.RestHumanTech.modelo.Estado;
import com.examen.fi.RestHumanTech.repositorio.EstadoRepositorio;

@RestController
@RequestMapping("/api")
public class EstadoControlador {
	
	@Autowired
    EstadoRepositorio estadoRepositorio;
	
	@GetMapping("/estado")
    public List<Estado> getAsesores() {
        return estadoRepositorio.findAll();
    }
}
