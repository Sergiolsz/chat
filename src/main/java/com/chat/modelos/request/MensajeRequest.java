package com.fran.modelos.request;

import java.time.LocalDateTime;

public class MensajeRequest {
	private Long id;
	private Long remitenteId;
	private Long destinatarioId;
	private LocalDateTime fechaHora;
	private String texto;

	// Constructor, getters y setters

	// Constructor
	public MensajeRequest(Long id, Long remitenteId, Long destinatarioId, LocalDateTime fechaHora, String texto) {
		this.id = id;
		this.remitenteId = remitenteId;
		this.destinatarioId = destinatarioId;
		this.fechaHora = fechaHora;
		this.texto = texto;
	}

	// Getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public String toString() {
		return "MensajeRequest{" +
			   "id=" + id +
			   ", remitenteId=" + remitenteId +
			   ", destinatarioId=" + destinatarioId +
			   ", fechaHora=" + fechaHora +
			   ", texto='" + texto + '\'' +
			   '}';
	}

}
