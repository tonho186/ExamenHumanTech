package com.examen.fi.RestHumanTech.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estado")
public class Estado implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Boolean id;

	private String descripcion;

	@OneToMany(mappedBy = "estado", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pelicula> pelicula = new ArrayList<>();
	
	@OneToMany(mappedBy = "estado", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Turno> turno = new ArrayList<>();
	
	public Boolean getId() {
		return id;
	}

	public void setId(Boolean id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Pelicula> getPelicula() {
		return pelicula;
	}

	public void setPelicula(List<Pelicula> pelicula) {
		this.pelicula = pelicula;
	}

	public List<Turno> getTurno() {
		return turno;
	}

	public void setTurno(List<Turno> turno) {
		this.turno = turno;
	}

}
