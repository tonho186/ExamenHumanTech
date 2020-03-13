package com.examen.fi.RestHumanTech.controlador;

import java.util.List;

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
import com.examen.fi.RestHumanTech.modelo.Turno;
import com.examen.fi.RestHumanTech.repositorio.EstadoRepositorio;
import com.examen.fi.RestHumanTech.repositorio.PeliculaRepositorio;
import com.examen.fi.RestHumanTech.repositorio.TurnoRepositorio;

@RestController
@RequestMapping("/api")
public class TurnoControlador {
	private Turno turno;
	@Autowired
    TurnoRepositorio turnoRepositorio;
    
    @Autowired
    PeliculaRepositorio peliculaRepositorio;
    
    @Autowired
    EstadoRepositorio estadoRepositorio;

    @GetMapping("/turno")
    public List<Turno> getTurnos() {
        return turnoRepositorio.findAll();
    }

    @PostMapping("/estado/{codEstado}/turno")
    public Turno createTurno(@PathVariable (value = "codEstado") Boolean codEstado, @Valid @RequestBody Turno turno) {
    	this.turno = turno;
        estadoRepositorio.findById(codEstado).map(estado -> {
            this.turno.setEstado(estado);
            return this.turno;
        }).orElseThrow(() -> new ResourceNotFoundException("Estado ","id",codEstado));
        return turnoRepositorio.save(turno);
    }

    @GetMapping("/turno/{id}")
    public Turno getTurnoById(@PathVariable(value = "id") Integer turnoId) {
        return turnoRepositorio.findById(turnoId)
                .orElseThrow(() -> new ResourceNotFoundException("Turno", "id", turnoId));
    }
    
    @GetMapping("/estado/{codEstado}/turno")
    public Page<Turno> getTurnoByEstadoId(@PathVariable (value = "codEstado") Boolean estadoId, Pageable pageable) {
        return turnoRepositorio.findByEstadoId(estadoId, pageable);
        }

    @PutMapping("/estado/{codEstado}/turno/{turnoId}")
    public Turno updateTurno(@PathVariable (value = "codEstado") Boolean codEstado,@PathVariable(value = "turnoId") Integer turnoId,
                                            @Valid @RequestBody Turno nuevoTurno) {

    	if(!estadoRepositorio.existsById(codEstado)) {
            throw new ResourceNotFoundException("Estado ","id",codEstado);
        }
        Turno turno = turnoRepositorio.findById(turnoId)
                .orElseThrow(() -> new ResourceNotFoundException("Turno", "id", turnoId));

        turno.setDescripcion(nuevoTurno.getDescripcion());

        Turno turnoActualizado = turnoRepositorio.save(turno);
        return turnoActualizado;
    }

    @DeleteMapping("/turno/{id}")
    public ResponseEntity<?> deleteTurno(@PathVariable(value = "id") Integer turnoId) {
        Turno turno = turnoRepositorio.findById(turnoId)
                .orElseThrow(() -> new ResourceNotFoundException("Turno", "id", turnoId));

        turnoRepositorio.delete(turno);

        return ResponseEntity.ok().build();
    }

}
