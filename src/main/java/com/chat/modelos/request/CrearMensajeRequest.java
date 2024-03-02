package com.fran.modelos.request;

import java.io.Serializable;

public class CrearMensajeRequest implements Serializable {

	private Long remitenteId;
	private Long destinatarioId;
	private String remitente;
	private String destinatario;
	private String texto;


	public CrearMensajeRequest() {

	}

	public CrearMensajeRequest(Long remitenteId,
							   Long destinatarioId,
							   String remitente,
							   String destinatario,
							   String texto) {

		this.remitenteId = remitenteId;
		this.destinatarioId = destinatarioId;
		this.remitente = remitente;
		this.destinatario = destinatario;
		this.texto = texto;
	}

	public Long getRemitenteId() {

		return remitenteId;
	}

	public void setRemitenteId(Long remitenteId) {

		this.remitenteId = remitenteId;
	}

	public Long getDestinatarioId() {

		return destinatarioId;
	}

	public void setDestinatarioId(Long destinatarioId) {

		this.destinatarioId = destinatarioId;
	}

	public String getRemitente() {

		return remitente;
	}

	public void setRemitente(String remitente) {

		this.remitente = remitente;
	}

	public String getDestinatario() {

		return destinatario;
	}

	public void setDestinatario(String destinatario) {

		this.destinatario = destinatario;
	}

	public String getTexto() {

		return texto;
	}

	public void setTexto(String texto) {

		this.texto = texto;
	}

	@Override
	public String toString() {

		return "CrearMensajeRequest{" +
			   "remitenteId=" + remitenteId +
			   ", remitente='" + remitente + '\'' +
			   ", destinatarioId=" + destinatarioId +
			   ", destinatario='" + destinatario + '\'' +
			   ", texto='" + texto + '\'' +
			   '}';
	}
}