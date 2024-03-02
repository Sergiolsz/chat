package com.chat.service;

import com.chat.entity.Contacto;
import com.chat.entity.Propietario;
import com.chat.exception.ChatException;
import com.chat.mapper.MapperChat;
import com.chat.modelos.dto.PropietarioDTO;
import com.chat.modelos.response.ContactosPropietarioResponse;
import com.chat.repository.ContactoRepository;
import com.chat.repository.PropietarioRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropietarioService {

	private final PropietarioRepository propietarioRepository;
	private final ContactoRepository contactoRepository;
	private final MapperChat mapperChat;

	public PropietarioService(PropietarioRepository propietarioRepository, ContactoRepository contactoRepository,
							  MapperChat mapperChat) {

		this.propietarioRepository = propietarioRepository;
		this.contactoRepository = contactoRepository;
		this.mapperChat = mapperChat;
	}

	/**
	 * Devuelve listado de todos los propietarios disponibles en la base de datos.
	 *
	 * @return List de PropietarioDTO
	 */
	@Transactional(readOnly = true)
	public List<PropietarioDTO> getAllPropietarios() {

		return propietarioRepository.findAll().stream()
			.map(mapperChat::convertirAPropietarioDTO)
			.collect(Collectors.toList());
	}

	/**
	 * Recupera un propietario de la base de datos por su identificador.
	 *
	 * @param propietarioId El identificador del propietario.
	 *
	 * @return PropietarioDTO
	 */
	@Transactional(readOnly = true)
	public PropietarioDTO getPropietarioById(Long propietarioId) {

		Propietario propietario = propietarioRepository.findById(propietarioId)
			.orElseThrow(() -> new ChatException("Propietario", "id", propietarioId));
		return mapperChat.convertirAPropietarioDTO(propietario);
	}

	/**
	 * Muestra todos los contactos del propietario.
	 *
	 * @param propietarioId El identificador del propietario.
	 *
	 * @return ContactosPropietarioResponse
	 */
	@Transactional(readOnly = true)
	public ContactosPropietarioResponse getContactosDePropietario(Long propietarioId) {

		Propietario propietario = propietarioRepository.findById(propietarioId)
			.orElseThrow(() -> new ChatException("Propietario", "id", propietarioId));

		ContactosPropietarioResponse contactosPropietarioResponse = new ContactosPropietarioResponse();
		contactosPropietarioResponse.setPropietario(propietario.getNombre());
		Set<String> listadoContactos = propietario.getContactos()
			.stream()
			.map(Contacto::getNombre)
			.collect(Collectors.toSet());
		contactosPropietarioResponse.setContactos(listadoContactos);

		return contactosPropietarioResponse;
	}

	/**
	 * Crea y guarda en base de datos un nuevo propietario.
	 *
	 * @param propietarioDTO El objeto PropietarioDTO para crear un nuevo propietario.
	 *
	 * @return Mensaje String.
	 */
	@Transactional
	public String crearPropietario(PropietarioDTO propietarioDTO) {

		String nombre = propietarioDTO.getNombre();

		if (propietarioRepository.existsByNombre(nombre)) {
			throw new ChatException("Ya existe el propietario", nombre);
		}

		Propietario propietario = mapperChat.convertirAPropietario(propietarioDTO);

		propietarioRepository.save(propietario);

		return "Propietario " + nombre + " creado correctamente";
	}

	/**
	 * Asignar un contacto al propietario.
	 *
	 * @param propietarioId El identificador de propietario.
	 * @param contactoId El identificador de contacto.
	 *
	 * @return Mensaje String.
	 */
	@Transactional
	public String asignarContacto(Long propietarioId, Long contactoId) {

		Propietario propietario = propietarioRepository.findById(propietarioId)
			.orElseThrow(() -> new ChatException("Propietario", "id", propietarioId));

		Contacto contacto = contactoRepository.findById(contactoId)
			.orElseThrow(() -> new ChatException("Contacto", "id", contactoId));

		if (contacto.getPropietario() != null && contacto.getPropietario().equals(propietario)) {
			return "El contacto ya está asignado a ese propietario";
		}

		contacto.setPropietario(propietario);
		propietario.getContactos().add(contacto);

		propietarioRepository.save(propietario);
		contactoRepository.save(contacto);

		return "Contacto asignado correctamente al propietario";
	}

	/**
	 * Actualiza los datos de un propietario.
	 *
	 * @param propietarioId El identificador del propietario.
	 * @param propietarioDTO El objeto PropietarioDTO con los nuevos detalles del Contacto.
	 *
	 * @return PropietarioDTO
	 */
	@Transactional
	public PropietarioDTO actualizarPropietario(Long propietarioId, PropietarioDTO propietarioDTO) {

		Propietario propietario = propietarioRepository.findById(propietarioId)
			.orElseThrow(() -> new ChatException("Propietario", "id", propietarioId));

		propietario.setNombre(propietarioDTO.getNombre());
		propietarioRepository.save(propietario);

		return mapperChat.convertirAPropietarioDTO(propietario);
	}

	/**
	 * Eliminar un propietario de la base de datos.
	 *
	 * @param propietarioId El identificador del propietario.
	 *
	 * @return Mensaje String.
	 */
	@Transactional
	public String eliminarPropietario(Long propietarioId) {

		Propietario propietario = propietarioRepository.findById(propietarioId)
			.orElseThrow(() -> new ChatException("Propietario", "id", propietarioId));

		propietarioRepository.delete(propietario);

		return "El propietario ha sido eliminado exitosamente.";
	}

	/**
	 * Elimina un contacto específico del listado de contactos del propietario.
	 *
	 * @param propietarioId El ID del propietario del cual se eliminará el contacto.
	 * @param contactoId El ID del contacto que se eliminará del listado.
	 */
	@Transactional
	public String eliminarContactoDePropietario(Long propietarioId, Long contactoId) {

		Propietario propietario = propietarioRepository.findById(propietarioId)
			.orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + propietarioId));

		Contacto contactoAEliminar = propietario.getContactos().stream()
			.filter(contacto -> contacto.getId().equals(contactoId))
			.findFirst()
			.orElseThrow(() -> new RuntimeException("Contacto no encontrado con ID: " + contactoId));

		propietario.getContactos().remove(contactoAEliminar);
		propietarioRepository.save(propietario);

		return "Eliminado correctamente el Contacto " + contactoAEliminar.getNombre() + " del listado del Propietario";
	}

	/**
	 * Elimina todos los contactos del propietario.
	 *
	 * @param propietarioId El ID del propietario.
	 * @return Un mensaje indicando el resultado de la operación.
	 * @throws ChatException Si no se encuentra el propietario.
	 */
	/**
	 * Elimina todos los contactos del propietario.
	 *
	 * @param propietarioId El ID del propietario del cual se eliminarán los contactos.
	 */
	@Transactional
	public String eliminarContactosDePropietario(Long propietarioId) {

		Propietario propietario = propietarioRepository.findById(propietarioId)
			.orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + propietarioId));

		List<String> nombresContacto = propietario.getContactos().stream()
			.map(Contacto::getNombre)
			.toList();

		List<Contacto> contactos = contactoRepository.findByNombreIn(nombresContacto);

		for (Contacto contacto : contactos) {
			contacto.eliminarPropietario();
		}

		contactoRepository.saveAll(contactos);

		propietario.getContactos().clear();
		propietarioRepository.save(propietario);

		return "Eliminados todos los contactos del propietario correctamente";
	}
}