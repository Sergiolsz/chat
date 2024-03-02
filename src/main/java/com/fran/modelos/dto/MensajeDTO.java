package com.fran.modelos.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MensajeDTO {

	private Long id;
	private Long remitenteId;
	private String remitente;
	private Long destinatarioId;
	private String destinatario;
	private LocalDateTime fechaHora;
	private String texto;

	public MensajeDTO() {

	}

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

	public String getRemitente() {

		return remitente;
	}

	public void setRemitente(String remitente) {

		this.remitente = remitente;
	}

	public Long getDestinatarioId() {

		return destinatarioId;
	}

	public void setDestinatarioId(Long destinatarioId) {

		this.destinatarioId = destinatarioId;
	}

	public String getDestinatario() {

		return destinatario;
	}

	public void setDestinatario(String destinatario) {

		this.destinatario = destinatario;
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

	public String getFechaHoraFormateada() {
		if (fechaHora != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			return fechaHora.format(formatter);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {

		return "Mensaje {" +
			   "id =" + id +
			   ", remitenteId =" + remitenteId +
			   ", remitente ='" + remitente + '\'' +
			   ", destinatarioId =" + destinatarioId +
			   ", destinatario ='" + destinatario + '\'' +
			   ", fecha =" + getFechaHoraFormateada() +
			   ", texto ='" + texto + '\'' +
			   '}';
	}
}