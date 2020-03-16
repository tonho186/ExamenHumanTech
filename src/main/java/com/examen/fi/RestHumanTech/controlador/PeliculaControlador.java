package com.examen.fi.RestHumanTech.controlador;

import com.examen.fi.RestHumanTech.exception.ResourceNotFoundException;
import com.examen.fi.RestHumanTech.modelo.Pelicula;
import com.examen.fi.RestHumanTech.modelo.Turno;
import com.examen.fi.RestHumanTech.repositorio.EstadoRepositorio;
import com.examen.fi.RestHumanTech.repositorio.IntermedioRepositorio;
import com.examen.fi.RestHumanTech.repositorio.PeliculaRepositorio;
import com.examen.fi.RestHumanTech.repositorio.TurnoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class PeliculaControlador {
private Pelicula pelicula;
    
    @Autowired
    EstadoRepositorio estadoRepositorio;
    
    @Autowired
    PeliculaRepositorio peliculaRepositorio;
    
    @Autowired
    IntermedioRepositorio intermedioRepositorio;
    
    @GetMapping("/pelicula")
    public List<Pelicula> getPeliculas() {
        return peliculaRepositorio.findAll();
    }

    @PostMapping("/estado/{codEstado}/pelicula")
    public Pelicula createPelicula(@PathVariable (value = "codEstado") Boolean codEstado, @Valid @RequestBody Pelicula pelicula) {
    	this.pelicula = pelicula;
        estadoRepositorio.findById(codEstado).map(estado -> {
            this.pelicula.setEstado(estado);
            return this.pelicula;
        }).orElseThrow(() -> new ResourceNotFoundException("Estado ","id",codEstado));
        return peliculaRepositorio.save(pelicula);
    }

    @GetMapping("/pelicula/{id}")
    public Pelicula getPeliculaById(@PathVariable(value = "id") Integer peliculaId) {
        return peliculaRepositorio.findById(peliculaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pelicula", "id", peliculaId));
    }
    
    @GetMapping("/estado/{codEstado}/pelicula")
    public Page<Pelicula> getPeliculaByEstadoId(@PathVariable (value = "codEstado") Boolean estadoId, Pageable pageable) {
        return peliculaRepositorio.findByEstadoId(estadoId, pageable);
        }

    @PutMapping("/estado/{codEstado}/pelicula/{id}")
    public Pelicula updatePelicula(@PathVariable (value = "codEstado") Boolean codEstado, @PathVariable(value = "id") Integer peliculaId,
                                            @Valid @RequestBody Pelicula nuevaPelicula) {

    	if(!estadoRepositorio.existsById(codEstado)) {
            throw new ResourceNotFoundException("Estado ","id",codEstado);
        }
        Pelicula pelicula = peliculaRepositorio.findById(peliculaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pelicula", "id", peliculaId));

        pelicula.setNombre(nuevaPelicula.getNombre());
        pelicula.setFec_publicacion(nuevaPelicula.getFec_publicacion());

        Pelicula asesorActualizado = peliculaRepositorio.save(pelicula);
        return asesorActualizado;
    }

    @DeleteMapping("/pelicula/{id}")
    public ResponseEntity<?> deletePelicula(@PathVariable(value = "id") Integer peliculaId) {
        Pelicula pelicula = peliculaRepositorio.findById(peliculaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pelicula", "id", peliculaId));

        peliculaRepositorio.delete(pelicula);

        return ResponseEntity.ok().build();
    }

}
