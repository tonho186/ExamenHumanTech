package com.examen.fi.RestHumanTech.controlador;

import com.examen.fi.RestHumanTech.exception.ResourceNotFoundException;
import com.examen.fi.RestHumanTech.modelo.Pelicula;
import com.examen.fi.RestHumanTech.repositorio.EstadoRepositorio;
import com.examen.fi.RestHumanTech.repositorio.PeliculaRepositorio;
import com.examen.fi.RestHumanTech.repositorio.TurnoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    TurnoRepositorio turnoRepositorio;
    
    @GetMapping("/pelicula")
    public Page<Pelicula> getPelicula(Pageable pageable) {
        return peliculaRepositorio.findAll(pageable);
    }

    @GetMapping("/pelicula/{id}")
    public Pelicula getPeliculaById(@PathVariable(value = "id") Integer peliculaId) {
        return peliculaRepositorio.findById(peliculaId)
                .orElseThrow(() -> new ResourceNotFoundException("Peliculas", "id", peliculaId));
    }
    
    @GetMapping("/estado/{codEstado}/pelicula")
    public Page<Pelicula> getPeliculaByEstadoId(@PathVariable (value = "codEstado") Boolean estadoId, Pageable pageable) {
        return peliculaRepositorio.findByEstadoId(estadoId, pageable);
        }
    
    @GetMapping("/turno/{codTurno}/pelicula")
    public Page<Pelicula> getPeliculaByTurnoId(@PathVariable (value = "codTurno") Integer turnoId, Pageable pageable) {
        return peliculaRepositorio.findByTurnoId(turnoId, pageable);
        }
    
    @PostMapping("/estado/{codEstado}/turno/{codTurno}/pelicula")
    public Pelicula createPelicula(@PathVariable (value = "codEstado") Boolean codEstado,@PathVariable (value = "codTurno") Integer codTurno,
                                 @Valid @RequestBody Pelicula pelicula) {
        this.pelicula = pelicula;
        estadoRepositorio.findById(codEstado).map(estado -> {
            this.pelicula.setEstado(estado);
            return this.pelicula;
        }).orElseThrow(() -> new ResourceNotFoundException("Estado ","id",codEstado));
        
        turnoRepositorio.findById(codTurno).map(turno -> {
            this.pelicula.setTurno(turno);
            return this.pelicula;
        }).orElseThrow(() -> new ResourceNotFoundException("Turno ","id",codTurno));
        
        return peliculaRepositorio.save(pelicula);
    }

    @PutMapping("/estado/{codEstado}/turno/{codTurno}/pelicula/{peliculaId}")
    public Pelicula updatePelicula(@PathVariable (value = "codEstado") Boolean codEstado,
                                 @PathVariable (value = "codTurno") Integer codTurno,
                                 @PathVariable (value = "peliculaId") Integer peliculaId,
                                 @Valid @RequestBody Pelicula peliculaRequest) {
        if(!estadoRepositorio.existsById(codEstado)) {
            throw new ResourceNotFoundException("Estado ","id",codEstado);
        }
        
        if(!turnoRepositorio.existsById(codTurno)) {
            throw new ResourceNotFoundException("Turno ","id",codTurno);
        }

        return peliculaRepositorio.findById(peliculaId).map(pelicula -> {
        	pelicula.setNombre(peliculaRequest.getNombre());
        	pelicula.setFec_publicacion(peliculaRequest.getFec_publicacion());
            return peliculaRepositorio.save(pelicula);
        }).orElseThrow(() -> new ResourceNotFoundException("Pelicula ", "id", peliculaId));
    }

    @DeleteMapping("/pelicula/{id}")
    public ResponseEntity<?> deletePelicula(@PathVariable(value = "id") Integer peliculaId) {
        Pelicula pelicula = peliculaRepositorio.findById(peliculaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pelicula", "id", peliculaId));

        peliculaRepositorio.delete(pelicula);

        return ResponseEntity.ok().build();
    }

}
