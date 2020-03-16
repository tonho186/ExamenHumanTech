package com.examen.fi.RestHumanTech.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examen.fi.RestHumanTech.modelo.Intermedio;

@Repository
public interface IntermedioRepositorio extends JpaRepository<Intermedio, Integer> {
	Page<Intermedio> findByPeliculaId(Integer peliculaId, Pageable pageable);
    Page<Intermedio> findByTurnoId(Integer turnoId, Pageable pageable);
}
