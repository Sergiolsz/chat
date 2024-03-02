package com.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Propietario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@OneToMany(mappedBy = "propietario")
	private Set<Contacto> contactos = new HashSet<>();

	@Column(name = "tipo")
	private String tipo = "propietario";

	public Propietario() {

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

	public Set<Contacto> getContactos() {

		return contactos;
	}

	public void setContactos(Set<Contacto> contactos) {

		this.contactos = contactos;
	}

	public String getTipo() {

		return tipo;
	}

	public void setTipo(String tipo) {

		this.tipo = tipo;
	}

	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Propietario {")
			.append("id = ").append(id)
			.append(", nombre = '").append(nombre).append('\'');

		if (contactos != null && !contactos.isEmpty()) {
			stringBuilder.append(", contactos = [");
			for (Contacto contacto : contactos) {
				stringBuilder.append(contacto.getNombre()).append(", ");
			}
			stringBuilder.setLength(stringBuilder.length() - 2);
			stringBuilder.append("]");
		} else {
			stringBuilder.append(", contactos = []");
		}

		stringBuilder.append("}");

		return stringBuilder.toString();
	}
}