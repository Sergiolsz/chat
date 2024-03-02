package com.fran.controller;

import com.fran.modelos.dto.ContactoDTO;
import com.fran.modelos.dto.PropietarioDTO;
import com.fran.service.ContactoService;
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
@RequestMapping("api/contacto")
public class ContactoController {

	private final ContactoService contactoService;

	public ContactoController(ContactoService contactoService) {

		this.contactoService = contactoService;
	}

	@GetMapping("/listado")
	public List<ContactoDTO> getAllContactos() {

		return contactoService.getAllContactos();
	}

	@GetMapping("/{id}")
	public ContactoDTO getContactoById(@PathVariable Long id) {

		return contactoService.getContactoById(id);
	}

	@GetMapping("/{contactoId}/propietario")
	public PropietarioDTO getPropietarioDelContacto(@PathVariable Long contactoId) {

		return contactoService.getPropietarioDelContacto(contactoId);
	}

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public String crearContacto(@RequestBody ContactoDTO contactoDTO) {

		return contactoService.crearContacto(contactoDTO);
	}

	@PostMapping("/{contactoId}/asignar-propietario/{propietarioId}")
	public String asignarPropietarioAContacto(@PathVariable Long contactoId,
											  @PathVariable Long propietarioId) {

		return contactoService.asignarPropietario(contactoId, propietarioId);
	}

	@PutMapping("/actualizar/{contactoId}")
	public ContactoDTO updateContacto(@PathVariable Long contactoId,
									  @RequestBody ContactoDTO contactoActualizadoDTO) {

		return contactoService.actualizarContacto(contactoId, contactoActualizadoDTO);
	}

	@DeleteMapping("/eliminar/{contactoId}")
	public String deleteContacto(@PathVariable Long contactoId) {

		return contactoService.eliminarContacto(contactoId);
	}

	@DeleteMapping("/eliminar-propietario/{propietarioId}/contacto/{contactoId}")
	public String eliminarPropietarioDeContacto( @PathVariable Long propietarioId, @PathVariable Long contactoId) {
		return contactoService.eliminarPropietarioDeContacto(propietarioId, contactoId);
	}

}