package com.examen.fi.RestHumanTech.modelo;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "turno")
public class Turno implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String descripcion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_estado")
	@JsonIgnore
	private Estado estado;
	
	@OneToMany(mappedBy = "turno", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pelicula> pelicula = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Pelicula> getPelicula() {
		return pelicula;
	}

	public void setPelicula(List<Pelicula> pelicula) {
		this.pelicula = pelicula;
	}
	
	

}
