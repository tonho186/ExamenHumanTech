package com.examen.fi.RestHumanTech.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examen.fi.RestHumanTech.modelo.Estado;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Boolean> {

}
