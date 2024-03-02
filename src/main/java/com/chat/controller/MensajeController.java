package com.chat.controller;

import com.chat.modelos.dto.MensajeDTO;
import com.chat.service.MensajeService;
import com.chat.modelos.request.CrearMensajeRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

	private final MensajeService mensajeService;

	public MensajeController(MensajeService mensajeService) {

		this.mensajeService = mensajeService;
	}

	@GetMapping("/listado")
	public List<MensajeDTO> getAllMensajes() {

		return mensajeService.getAllMensajes();
	}

	@GetMapping("/{id}")
	public MensajeDTO getMensajeById(@PathVariable Long id) {

		return mensajeService.getMensajeById(id);
	}

	@GetMapping("/propietario/{propietarioId}/contacto/{contactoId}")
	public List<MensajeDTO> getMensajesByPropietarioAndContacto(@PathVariable Long propietarioId,
																   @PathVariable Long contactoId) {

		return mensajeService.getMensajesByPropietarioAndContacto(propietarioId, contactoId);
	}

	@GetMapping("/contacto/{contactoId1}/contacto/{contactoId2}")
	public List<MensajeDTO> getMensajesContactoByContacto(@PathVariable Long contactoId1, @PathVariable Long contactoId2) {

		return mensajeService.getMensajesByContactoAndContacto(contactoId1, contactoId2);
	}

	@PostMapping("/enviar")
	@ResponseStatus(HttpStatus.CREATED)
	public String sendMensaje(@RequestBody CrearMensajeRequest crearMensajeRequest) {

		return mensajeService.enviarMensaje(crearMensajeRequest);
	}

	@PutMapping("/actualizar/{id}")
	public MensajeDTO updateMensaje(@PathVariable Long id, @RequestBody MensajeDTO mensajeDTO) {

		return mensajeService.updateMensaje(id, mensajeDTO);
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMensaje(@PathVariable Long id) {

		mensajeService.deleteMensaje(id);
	}

}