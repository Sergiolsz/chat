package com.fran.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "mensaje")
public class Mensaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "remitente_id")
	private Long remitenteId;

	@Column(name = "remitente_nombre")
	private String remitente;

	@Column(name = "destinatario_id")
	private Long destinatarioId;

	@Column(name = "destinatario_nombre")
	private String destinatario;

	@Column(name = "fecha_hora")
	private LocalDateTime fechaHora;

	@Column(name = "texto")
	private String texto;

	public Mensaje() {

	}

	public Mensaje(Long remitenteId,
				   Long destinatarioId,
				   String remitente,
				   String destinatario,
				   LocalDateTime fechaHora,
				   String texto) {

		this.remitenteId = remitenteId;
		this.destinatarioId = destinatarioId;
		this.remitente = remitente;
		this.destinatario = destinatario;
		this.fechaHora = fechaHora;
		this.texto = texto;
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

	public Long getDestinatarioId() {

		return destinatarioId;
	}

	public void setDestinatarioId(Long destinatarioId) {

		this.destinatarioId = destinatarioId;
	}

	public String getRemitente() {

		return remitente;
	}

	public void setRemitente(String remitenteNombre) {

		this.remitente = remitenteNombre;
	}

	public String getDestinatario() {

		return destinatario;
	}

	public void setDestinatario(String destinatarioTipo) {

		this.destinatario = destinatarioTipo;
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
			   "id=" + id +
			   ", remitenteId=" + remitenteId +
			   ", remitente='" + remitente + '\'' +
			   ", destinatarioId=" + destinatarioId +
			   ", destinatario='" + destinatario + '\'' +
			   ", fechaHora=" + getFechaHoraFormateada() +
			   ", texto='" + texto + '\'' +
			   '}';
	}


}