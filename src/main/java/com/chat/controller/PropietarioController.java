package com.chat.controller;

import com.chat.modelos.dto.PropietarioDTO;
import com.chat.modelos.response.ContactosPropietarioResponse;
import com.chat.service.PropietarioService;
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
@RequestMapping("api/propietario")
public class PropietarioController {

	private final PropietarioService propietarioService;

	public PropietarioController(PropietarioService propietarioService) {

		this.propietarioService = propietarioService;
	}

	@GetMapping("/listado")
	public List<PropietarioDTO> getAllPropietarios() {

		return propietarioService.getAllPropietarios();
	}

	@GetMapping("/{id}")
	public PropietarioDTO getPropietarioById(@PathVariable Long id) {

		return propietarioService.getPropietarioById(id);
	}

	@GetMapping("/{id}/contactos")
	public ContactosPropietarioResponse getContactosDePropietario(@PathVariable Long id) {

		return propietarioService.getContactosDePropietario(id);
	}

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public String createPropietario(@RequestBody PropietarioDTO propietarioDTO) {

		return propietarioService.crearPropietario(propietarioDTO);
	}

	@PostMapping("/{propietarioId}/asignar-contacto/{contactoId}")
	public String asignarContactoAPropietario(@PathVariable Long propietarioId, @PathVariable Long contactoId) {

		return propietarioService.asignarContacto(propietarioId, contactoId);
	}

	@PutMapping("/actualizar/{id}")
	public PropietarioDTO putPropietario(@PathVariable Long id, @RequestBody PropietarioDTO propietarioDTO) {

		return propietarioService.actualizarPropietario(id, propietarioDTO);
	}

	@DeleteMapping("/eliminar/{id}")
	public String deletePropietario(@PathVariable Long id) {

		return propietarioService.eliminarPropietario(id);
	}

	@DeleteMapping("/eliminar/{propietarioId}/contacto/{contactoId}")
	public String deleteContactoPropietario(@PathVariable Long propietarioId, @PathVariable Long contactoId) {

		return propietarioService.eliminarContactoDePropietario(propietarioId, contactoId);
	}

	@DeleteMapping("/eliminar/contactos/{propietarioId}")
	public String deleteAllContactosPropietario(@PathVariable Long propietarioId) {

		return propietarioService.eliminarContactosDePropietario(propietarioId);
	}
}