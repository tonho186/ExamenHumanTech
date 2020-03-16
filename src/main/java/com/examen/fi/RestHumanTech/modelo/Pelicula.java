package com.examen.fi.RestHumanTech.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "peliculas")
public class Pelicula implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nombre;

	private Date fec_publicacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_estado")
	@JsonIgnore
	private Estado estado;
	
	@OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Intermedio> intermedio = new ArrayList<>();	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFec_publicacion() {
		return fec_publicacion;
	}

	public void setFec_publicacion(Date fec_publicacion) {
		this.fec_publicacion = fec_publicacion;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public List<Intermedio> getIntermedio() {
		return intermedio;
	}

	public void setIntermedio(List<Intermedio> intermedio) {
		this.intermedio = intermedio;
	}

}
