package com.fran.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Contacto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@ManyToOne
	@JoinColumn(name = "propietario_id")
	private Propietario propietario;

	@Column(name = "tipo")
	private String tipo = "contacto";

	public Contacto() {

	}

	public Contacto(String nombre, Propietario propietario) {

		this.nombre = nombre;
		this.propietario = propietario;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getNombre() {

		return nombre;
	}

	public void setNombre(String nombre) {

		this.nombre = nombre;
	}

	public Propietario getPropietario() {

		return propietario;
	}

	public void setPropietario(Propietario propietario) {

		this.propietario = propietario;
	}

	public String getTipo() {

		return tipo;
	}

	public void setTipo(String tipo) {

		this.tipo = tipo;
	}

	public void eliminarPropietario() {
		this.propietario = null;
	}

	@Override
	public String toString() {

		return "Contacto {" +
			   "id = " + id +
			   ", nombre = '" + nombre + '\'' +
			   ", propietario = " + (propietario != null ? propietario.getNombre() : null) +
			   '}';
	}
}