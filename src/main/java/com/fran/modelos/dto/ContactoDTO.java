package com.fran.modelos.dto;

public class ContactoDTO {

	private Long id;
	private String nombre;

	public ContactoDTO() {

	}

	public ContactoDTO(Long id, String nombre) {

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

		return "Contacto {" +
			   "id =" + id +
			   ", nombre ='" + nombre + '\'' +
			   '}';
	}
}