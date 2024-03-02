package com.chat.service;


import com.chat.entity.Contacto;
import com.chat.entity.Mensaje;
import com.chat.entity.Propietario;
import com.chat.exception.ChatException;
import com.chat.mapper.MapperChat;
import com.chat.modelos.request.CrearMensajeRequest;
import com.chat.modelos.dto.MensajeDTO;
import com.chat.repository.ContactoRepository;
import com.chat.repository.MensajeRepository;
import com.chat.repository.PropietarioRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MensajeService {

	private final MensajeRepository mensajeRepository;
	private final PropietarioRepository propietarioRepository;
	private final ContactoRepository contactoRepository;
	private final MapperChat mapperChat;

	public MensajeService(MensajeRepository mensajeRepository,
						  PropietarioRepository propietarioRepository,
						  ContactoRepository contactoRepository, MapperChat mapperChat) {

		this.mensajeRepository = mensajeRepository;
		this.propietarioRepository = propietarioRepository;
		this.contactoRepository = contactoRepository;
		this.mapperChat = mapperChat;
	}

	/**
	 * Recupera todos los mensajes almacenados en la base de datos.
	 *
	 * @return List de MensajeDTO
	 */
	@Transactional(readOnly = true)
	public List<MensajeDTO> getAllMensajes() {

		return mensajeRepository.findAll().stream()
			.map(mapperChat::convertirAMensajeDTO)
			.collect(Collectors.toList());
	}

	/**
	 * Recupera un mensaje de la base de datos por su identificador.
	 *
	 * @param mensajeId El identificador del mensaje.
	 * @return MensajeDTO.
	 * @throws ChatException Si no se encuentra ningún mensaje con el identificador proporcionado en la base de datos.
	 */
	@Transactional(readOnly = true)
	public MensajeDTO getMensajeById(Long mensajeId) {

		Mensaje mensaje = mensajeRepository.findById(mensajeId)
			.orElseThrow(() -> new ChatException("Mensaje", "id", mensajeId));

		return mapperChat.convertirAMensajeDTO(mensaje);
	}

	/**
	 * Recupera los mensajes enviados por un propietario específico a un contacto específico.
	 *
	 * @param propietarioId El ID del propietario que envió los mensajes.
	 * @param contactoId El ID del contacto que recibió los mensajes.
	 * @return Una lista de mensajes enviados por el propietario al contacto en forma de DTO.
	 * @throws ChatException Si no se encuentra el propietario o el contacto.
	 */
	@Transactional(readOnly = true)
	public List<MensajeDTO> getMensajesByPropietarioAndContacto(Long propietarioId, Long contactoId) {

		Propietario propietario = propietarioRepository.findById(propietarioId)
			.orElseThrow(() -> new ChatException("Propietario", "id", propietarioId));

		Contacto contacto = contactoRepository.findById(contactoId)
			.orElseThrow(() -> new ChatException("Contacto", "id", contactoId));

		Optional<List<Mensaje>> mensajes =
			mensajeRepository.findByRemitenteIdAndDestinatarioId(propietario.getId(), contacto.getId());

		return mensajes.map(mensajesList ->
								mensajesList.stream()
									.map(mapperChat::convertirAMensajeDTO)
									.toList())
			.orElseThrow(() -> new ChatException("No se encontraron mensajes para el propietario y contacto dados"));
	}

	/**
	 * Recupera una lista de mensajes enviados entre contactos.
	 *
	 * @param contactoId1 El ID del contacto.
	 * @param contactoId2 El ID del contacto.
	 * @return Una lista de {@link MensajeDTO} enviados por el propietario al contacto.
	 * @throws ChatException Si no se encuentra el contacto o el propietario con los IDs proporcionados.
	 */
	@Transactional(readOnly = true)
	public List<MensajeDTO> getMensajesByContactoAndContacto(Long contactoId1, Long contactoId2) {

		Contacto contacto1 = contactoRepository.findById(contactoId1)
			.orElseThrow(() -> new ChatException("Contacto", "id", contactoId1));

		Contacto contacto2 = contactoRepository.findById(contactoId2)
			.orElseThrow(() -> new ChatException("Contacto", "id", contactoId2));

		Optional<List<Mensaje>> mensajes =
			mensajeRepository.findByRemitenteIdAndDestinatarioId(contacto1.getId(), contacto2.getId());

		return mensajes.map(mensajesList ->
								mensajesList.stream()
									.map(mapperChat::convertirAMensajeDTO)
									.toList())
			.orElseThrow(() -> new ChatException("Mensaje no encontrado"));
	}

	/**
	 * Envía y guarda en base de datos mensajes entre un remitente y destinatario.
	 *
	 * @param crearMensajeRequest El objeto para crear un Mensaje.
	 * @return Mensaje String.
	 */
	@Transactional
	public String enviarMensaje(CrearMensajeRequest crearMensajeRequest) {

		try {
			Mensaje mensaje = new Mensaje();
			mensaje.setRemitenteId(crearMensajeRequest.getRemitenteId());
			mensaje.setDestinatarioId(crearMensajeRequest.getDestinatarioId());
			mensaje.setRemitente(crearMensajeRequest.getRemitente());
			mensaje.setDestinatario(crearMensajeRequest.getDestinatario());
			mensaje.setFechaHora(LocalDateTime.now());
			mensaje.setTexto(crearMensajeRequest.getTexto());

			mensajeRepository.save(mensaje);

			return "Mensaje enviado correctamente";
		} catch (Exception e) {
			throw new ChatException("Error al enviar el mensaje: " + e.getMessage());
		}
	}

	/**
	 * Actualiza un mensaje existente en la base de datos.
	 *
	 * @param mensajeId El ID del mensaje que se desea actualizar.
	 * @param mensajeDTO El objeto MensajeDTO con los datos actualizados del mensaje.
	 * @return Un objeto MensajeDTO que representa el mensaje actualizado.
	 * @throws ChatException Si no se encuentra ningún mensaje con el ID proporcionado en la base de datos.
	 */
	@Transactional
	public MensajeDTO updateMensaje(Long mensajeId, MensajeDTO mensajeDTO) {

		Mensaje mensaje = mensajeRepository.findById(mensajeId)
			.orElseThrow(() -> new ChatException("Mensaje", "id", mensajeId));

		mensaje.setTexto(mensajeDTO.getTexto());
		Mensaje mensajeActualizado = mensajeRepository.save(mensaje);

		return mapperChat.convertirAMensajeDTO(mensajeActualizado);
	}

	/**
	 * Elimina un mensaje de la base de datos.
	 *
	 * @param mensajeId El ID del mensaje que se desea eliminar.
	 * @throws ChatException Si no se encuentra ningún mensaje con el ID proporcionado en la base de datos.
	 */
	@Transactional
	public void deleteMensaje(Long mensajeId) {

		Mensaje mensajeTexto = mensajeRepository.findById(mensajeId)
			.orElseThrow(() -> new ChatException("Mensaje", "id", mensajeId));

		mensajeRepository.delete(mensajeTexto);
	}

}