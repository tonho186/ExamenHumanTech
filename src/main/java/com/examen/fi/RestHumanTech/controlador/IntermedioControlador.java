package com.examen.fi.RestHumanTech.controlador;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.fi.RestHumanTech.exception.ResourceNotFoundException;
import com.examen.fi.RestHumanTech.modelo.Intermedio;
import com.examen.fi.RestHumanTech.repositorio.IntermedioRepositorio;
import com.examen.fi.RestHumanTech.repositorio.PeliculaRepositorio;
import com.examen.fi.RestHumanTech.repositorio.TurnoRepositorio;

@RestController
@RequestMapping("/api")
public class IntermedioControlador {
	private Intermedio intermedio;
	
	@Autowired
    IntermedioRepositorio intermedioRepositorio;
    
    @Autowired
    PeliculaRepositorio peliculaRepositorio;
    
    @Autowired
    TurnoRepositorio turnoRepositorio;
    
    @GetMapping("/intermedio")
    public Page<Intermedio> getIntermedio(Pageable pageable) {
        return intermedioRepositorio.findAll(pageable);
    }

    @GetMapping("/intermedio/{id}")
    public Intermedio getIntermedioById(@PathVariable(value = "id") Integer intermedioId) {
        return intermedioRepositorio.findById(intermedioId)
                .orElseThrow(() -> new ResourceNotFoundException("Intermedio", "id", intermedioId));
    }
    
    @GetMapping("/pelicula/{peliculaId}/intermedio")
    public Page<Intermedio> getIntermedioByPeliculaId(@PathVariable (value = "peliculaId") Integer peliculaId, Pageable pageable) {
        return intermedioRepositorio.findByPeliculaId(peliculaId, pageable);
        }
    
    @GetMapping("/turno/{turnoId}/intermedio")
    public Page<Intermedio> getIntermedioByTurnoId(@PathVariable (value = "turnoId") Integer turnoId, Pageable pageable) {
        return intermedioRepositorio.findByTurnoId(turnoId, pageable);
        }
    
    @PostMapping("/pelicula/{peliculaId}/turno/{turnoId}/intermedio")
    public Intermedio createIntermedio(@PathVariable (value = "peliculaId") Integer peliculaId, @PathVariable (value = "turnoId") Integer turnoId,
                                 @Valid @RequestBody Intermedio intermedio) {
        this.intermedio = intermedio;
        peliculaRepositorio.findById(peliculaId).map(pelicula -> {
            this.intermedio.setPelicula(pelicula);
            return this.intermedio;
        }).orElseThrow(() -> new ResourceNotFoundException("Pelicula ","id",peliculaId));
        
        turnoRepositorio.findById(turnoId).map(turno -> {
            this.intermedio.setTurno(turno);
            return this.intermedio;
        }).orElseThrow(() -> new ResourceNotFoundException("Turno ","id",turnoId));
        
        return intermedioRepositorio.save(intermedio);
    }

    @PutMapping("/pelicula/{peliculaId}/turno/{turnoId}/intermedio/{intermedioId}")
    public Intermedio updateIntermedio(@PathVariable (value = "peliculaId") Integer peliculaId,
                                 @PathVariable (value = "turnoId") Integer turnoId,
                                 @PathVariable (value = "intermedioId") Integer intermedioId,
                                 @Valid @RequestBody Intermedio intermedioRequest) {
        if(!peliculaRepositorio.existsById(peliculaId)) {
            throw new ResourceNotFoundException("Pelicula ","id",peliculaId);
        }
        
        if(!turnoRepositorio.existsById(turnoId)) {
            throw new ResourceNotFoundException("Turno ","id",turnoId);
        }

        return intermedioRepositorio.findById(intermedioId).map(intermedio -> {
            return intermedioRepositorio.save(intermedio);
        }).orElseThrow(() -> new ResourceNotFoundException("Intermedio ", "id", intermedioId));
    }

    @DeleteMapping("/tesis/{id}")
    public ResponseEntity<?> deleteIntermedio(@PathVariable(value = "id") Integer intermedioId) {
        Intermedio intermedio = intermedioRepositorio.findById(intermedioId)
                .orElseThrow(() -> new ResourceNotFoundException("Intermedio", "id", intermedioId));

        intermedioRepositorio.delete(intermedio);

        return ResponseEntity.ok().build();
    }

}
