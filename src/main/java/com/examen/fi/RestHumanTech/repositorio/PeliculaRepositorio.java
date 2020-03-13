package com.examen.fi.RestHumanTech.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examen.fi.RestHumanTech.modelo.Pelicula;

@Repository
public interface PeliculaRepositorio extends JpaRepository<Pelicula, Integer> {
	Page<Pelicula> findByEstadoId(Boolean estadoId, Pageable pageable);
    Page<Pelicula> findByTurnoId(Integer turnoId, Pageable pageable);
}
