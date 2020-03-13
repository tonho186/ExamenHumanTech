package com.examen.fi.RestHumanTech.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examen.fi.RestHumanTech.modelo.Turno;

public interface TurnoRepositorio extends JpaRepository<Turno, Integer> {
	Page<Turno> findByEstadoId(Boolean estadoId, Pageable pageable);
}
