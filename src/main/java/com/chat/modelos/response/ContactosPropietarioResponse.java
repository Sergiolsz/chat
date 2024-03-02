package com.chat.modelos.response;

import java.io.Serializable;
import java.util.Set;

public class ContactosPropietarioResponse implements Serializable {

	private String propietario;
	private Set<String> contactos;

	public ContactosPropietarioResponse() {

	}

	public ContactosPropietarioResponse(String propietario, Set<String> contactos) {

		this.propietario = propietario;
		this.contactos = contactos;
	}

	public String getPropietario() {

		return propietario;
	}

	public void setPropietario(String propietario) {

		this.propietario = propietario;
	}

	public Set<String> getContactos() {

		return contactos;
	}

	public void setContactos(Set<String> contactos) {

		this.contactos = contactos;
	}

	@Override
	public String toString() {

		return "Propietario {" +
			   "nombre ='" + propietario + '\'' +
			   ", contactos =" + contactos +
			   '}';
	}
}
