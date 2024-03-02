package com.fran.service;

import com.fran.entity.Contacto;
import com.fran.entity.Propietario;
import com.fran.exception.ChatException;
import com.fran.mapper.MapperChat;
import com.fran.modelos.dto.ContactoDTO;
import com.fran.modelos.dto.PropietarioDTO;
import com.fran.repository.ContactoRepository;
import com.fran.repository.PropietarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactoService {

	private final ContactoRepository contactoRepository;
	private final PropietarioRepository propietarioRepository;
	private final MapperChat mapperChat;

	public ContactoService(ContactoRepository contactoRepository,
						   PropietarioRepository propietarioRepository, MapperChat mapperChat) {

		this.contactoRepository = contactoRepository;
		this.propietarioRepository = propietarioRepository;
		this.mapperChat = mapperChat;
	}

	/**
	 * Devuelve listado de todos los contactos disponibles en la base de datos.
	 *
	 * @return List de ContactoDTO.
	 */
	@Transactional(readOnly = true)
	public List<ContactoDTO> getAllContactos() {

		return contactoRepository.findAll().stream()
			.map(mapperChat::convertirAContactoDTO)
			.collect(Collectors.toList());
	}

	/**
	 * Recupera un contacto de la base de datos por su identificador.
	 *
	 * @param contactoId El identificador del contacto.
	 *
	 * @return ContactoDTO.
	 *
	 * @throws ChatException Si no se encuentra ningún mensaje con el identificador proporcionado en la base de datos.
	 */
	@Transactional(readOnly = true)
	public ContactoDTO getContactoById(Long contactoId) {

		Contacto contacto = contactoRepository.findById(contactoId)
			.orElseThrow(() -> new ChatException("Contacto no encontrado con ID: " + contactoId));

		return mapperChat.convertirAContactoDTO(contacto);
	}

	/**
	 * Devuelve la información del propietario asociado al contacto.
	 *
	 * @param contactoId El identificador del contacto.
	 * @return PropietarioDTO
	 */
	@Transactional(readOnly = true)
	public PropietarioDTO getPropietarioDelContacto(Long contactoId) {

		Contacto contacto = contactoRepository.findById(contactoId)
			.orElseThrow(() -> new ChatException("Contacto no encontrado con ID: " + contactoId));

		Propietario propietario = contacto.getPropietario();

		if (propietario == null) {
			throw new ChatException("El contacto no tiene un propietario asignado");
		}

		return mapperChat.convertirAPropietarioDTO(propietario);
	}

	/**
	 * Crea y guarda en base de datos un nuevo contacto.
	 *
	 * @param contactoDTO Objeto para crear un nuevo contacto.
	 * @return Mensaje String.
	 */
	@Transactional
	public String crearContacto(ContactoDTO contactoDTO) {

		String nombre = contactoDTO.getNombre();

		boolean existente = contactoRepository.existsByNombre(nombre);

		if (existente) {
			throw new ChatException("Ya existe un contacto con ese nombre: " + nombre);
		}

		Contacto contacto = mapperChat.convertirAContacto(contactoDTO);

		contactoRepository.save(contacto);

		return "Propietario " + nombre + " creado correctamente";
	}

	/**
	 * Asigna a un contacto un propietario.
	 *
	 * @param contactoId El identificador del contacto.
	 * @param propietarioId El identificador del propietario.
	 * @return Mensaje String.
	 */
	@Transactional
	public String asignarPropietario(Long contactoId, Long propietarioId) {

		Contacto contacto = contactoRepository.findById(contactoId)
			.orElseThrow(() -> new ChatException("Contacto no encontrado con ID: " + contactoId));
		Propietario propietario = propietarioRepository.findById(propietarioId)
			.orElseThrow(() -> new ChatException("Propietario no encontrado con ID: " + propietarioId));

		if (contacto.getPropietario() != null) {
			return "El contacto ya tiene asignado un propietario";
		}

		contacto.setPropietario(propietario);
		propietario.getContactos().add(contacto);

		return "Propietario asignado correctamente al contacto";
	}

	/**
	 * Actualiza los datos de un contacto.
	 *
	 * @param contactoId El identificador del contacto.
	 * @param contactoDTO El objeto ContactoDTO con los nuevos detalles del Contacto.
	 * @return ContactoDTO
	 */
	@Transactional
	public ContactoDTO actualizarContacto(Long contactoId, ContactoDTO contactoDTO) {

		Contacto contacto = contactoRepository.findById(contactoId)
			.orElseThrow(() -> new ChatException("Contacto no encontrado con ID: " + contactoId));

		contacto.setNombre(contactoDTO.getNombre());
		contactoRepository.save(contacto);

		return mapperChat.convertirAContactoDTO(contacto);
	}

	/**
	 * Elimina un contacto de la base de datos.
	 *
	 * @param contactoId El identificador del contacto.
	 * @return Mensaje String.
	 */
	@Transactional
	public String eliminarContacto(Long contactoId) {

		Contacto contacto = contactoRepository.findById(contactoId)
			.orElseThrow(() -> new ChatException("Contacto no encontrado con ID: " + contactoId));

		contactoRepository.delete(contacto);

		return "El contacto ha sido eliminado exitosamente.";
	}

	/**
	 * Eliminar el propietario del contacto. A su ver eliminará ese contacto del listado del propietario.
	 *
	 * @param propietarioId El indetificador del propietario
	 * @param contactoId El identificador del contacto.
	 * @return String mensaje
	 */
	@Transactional
	public String eliminarPropietarioDeContacto(Long propietarioId, Long contactoId) {
		Contacto contacto = contactoRepository.findById(contactoId)
			.orElseThrow(() -> new RuntimeException("Contacto no encontrado con ID: " + contactoId));

		Propietario propietario = propietarioRepository.findById(propietarioId)
			.orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + propietarioId));

		contacto.setPropietario(null);
		contactoRepository.save(contacto);

		propietario.getContactos().remove(contacto);
		propietarioRepository.save(propietario);

		return "Se eliminó correctamente el propietario del contacto y el contacto del listado de contactos del propietario.";
	}
}