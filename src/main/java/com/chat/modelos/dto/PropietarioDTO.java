package com.chat.modelos.dto;

public class PropietarioDTO {

	private Long id;
	private String nombre;

	public PropietarioDTO() {

	}

	public PropietarioDTO(Long id, String nombre) {

		this.id = id;
		this.nombre = nombre;
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

	@Override
	public String toString() {

		return "Propietario {" +
			   "id = " + id +
			   ", Propietario = '" + nombre + '\'' +
			   '}';
	}
}
