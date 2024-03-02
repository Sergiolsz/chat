package com.fran.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.chat.entity.Contacto;
import com.chat.entity.Mensaje;
import com.chat.entity.Propietario;
import com.chat.exception.ChatException;
import com.chat.mapper.MapperChat;
import com.chat.modelos.dto.MensajeDTO;
import com.chat.modelos.request.CrearMensajeRequest;
import com.chat.repository.ContactoRepository;
import com.chat.repository.MensajeRepository;
import com.chat.repository.PropietarioRepository;
import com.chat.service.MensajeService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MensajeServiceTest {

	@Mock
	private MensajeRepository mensajeRepository;

	@Mock
	private PropietarioRepository propietarioRepository;

	@Mock
	private ContactoRepository contactoRepository;

	@Mock
	private MapperChat mapperChat;

	@InjectMocks
	private MensajeService mensajeService;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllMensajes() {

		Long mensajeId = 1L;
		Mensaje mensaje = new Mensaje();
		mensaje.setId(mensajeId);

		MensajeDTO mensajeDTO = new MensajeDTO();
		mensajeDTO.setId(mensajeId);

		List<Mensaje> mensajes = new ArrayList<>();
		mensajes.add(mensaje);

		when(mensajeRepository.findAll()).thenReturn(mensajes);
		when(mapperChat.convertirAMensajeDTO(mensajes.get(0))).thenReturn(mensajeDTO);

		List<MensajeDTO> result = mensajeService.getAllMensajes();

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	public void testGetAllMensajesEmpty() {

		when(mensajeRepository.findAll()).thenReturn(Collections.emptyList());

		List<MensajeDTO> result = mensajeService.getAllMensajes();

		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetMensajeById() {

		Long mensajeId = 1L;
		Mensaje mensaje = new Mensaje();
		mensaje.setId(mensajeId);

		MensajeDTO mensajeDTO = new MensajeDTO();
		mensajeDTO.setId(mensajeId);

		when(mensajeRepository.findById(mensajeId)).thenReturn(Optional.of(mensaje));
		when(mapperChat.convertirAMensajeDTO(mensaje)).thenReturn(mensajeDTO);

		MensajeDTO result = mensajeService.getMensajeById(mensajeId);

		assertNotNull(result);
		assertEquals(mensajeId, result.getId());
	}

	@Test
	public void testGetMensajeById_MensajeNoEncontrado() {

		Long mensajeId = 1L;

		when(mensajeRepository.findById(mensajeId)).thenReturn(Optional.empty());

		assertThrows(ChatException.class, () -> mensajeService.getMensajeById(mensajeId));
	}

	@Test
	public void testGetMensajesByPropietarioAndContacto() {

		Long propietarioId = 1L;
		Long contactoId = 2L;
		Long mensajeId = 1L;

		Propietario propietario = new Propietario();
		propietario.setId(propietarioId);

		Contacto contacto = new Contacto();
		contacto.setId(contactoId);

		Mensaje mensaje = new Mensaje();
		mensaje.setId(mensajeId);

		MensajeDTO mensajeDTO = new MensajeDTO();
		mensajeDTO.setId(mensajeId);

		List<Mensaje> mensajes = new ArrayList<>();
		mensajes.add(mensaje);

		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));
		when(contactoRepository.findById(contactoId)).thenReturn(Optional.of(contacto));
		when(mensajeRepository.findByRemitenteIdAndDestinatarioId(propietarioId, contactoId)).thenReturn(Optional.of(mensajes));
		when(mapperChat.convertirAMensajeDTO(mensajes.get(0))).thenReturn(mensajeDTO);

		List<MensajeDTO> result = mensajeService.getMensajesByPropietarioAndContacto(propietarioId, contactoId);

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	public void testGetMensajesByPropietarioAndContacto_PropietarioNoEncontrado() {

		Long propietarioId = 1L;
		Long contactoId = 2L;

		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.empty());

		assertThrows(ChatException.class, () -> mensajeService.getMensajesByPropietarioAndContacto(propietarioId, contactoId));
	}

	@Test
	public void testGetMensajesByPropietarioAndContacto_ContactoNoEncontrado() {

		Long propietarioId = 1L;
		Long contactoId = 2L;
		Propietario propietario = new Propietario();
		propietario.setId(propietarioId);

		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));
		when(contactoRepository.findById(contactoId)).thenReturn(Optional.empty());

		assertThrows(ChatException.class, () -> mensajeService.getMensajesByPropietarioAndContacto(propietarioId, contactoId));
	}

	@Test
	public void testGetMensajesByPropietarioAndContacto_MensajesNoEncontrados() {

		Long propietarioId = 1L;
		Long contactoId = 2L;
		Propietario propietario = new Propietario();
		propietario.setId(propietarioId);
		Contacto contacto = new Contacto();
		contacto.setId(contactoId);

		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));
		when(contactoRepository.findById(contactoId)).thenReturn(Optional.of(contacto));
		when(mensajeRepository.findByRemitenteIdAndDestinatarioId(propietarioId, contactoId)).thenReturn(Optional.empty());

		assertThrows(ChatException.class, () -> mensajeService.getMensajesByPropietarioAndContacto(propietarioId, contactoId));
	}

	@Test
	public void testGetMensajesByContactoAndContacto() {

		Long contactoId1 = 1L;
		Long contactoId2 = 2L;
		Long mensajeId = 2L;

		Contacto contacto1 = new Contacto();
		contacto1.setId(contactoId1);

		Contacto contacto2 = new Contacto();
		contacto2.setId(contactoId2);

		Mensaje mensaje = new Mensaje();
		mensaje.setId(mensajeId);

		MensajeDTO mensajeDTO = new MensajeDTO();
		mensajeDTO.setId(mensajeId);

		List<Mensaje> mensajes = new ArrayList<>();
		mensajes.add(mensaje);

		when(contactoRepository.findById(contactoId1)).thenReturn(Optional.of(contacto1));
		when(contactoRepository.findById(contactoId2)).thenReturn(Optional.of(contacto2));
		when(mensajeRepository.findByRemitenteIdAndDestinatarioId(contactoId1, contactoId2)).thenReturn(Optional.of(mensajes));
		when(mapperChat.convertirAMensajeDTO(mensajes.get(0))).thenReturn(mensajeDTO);

		List<MensajeDTO> result = mensajeService.getMensajesByContactoAndContacto(contactoId1, contactoId2);

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	public void testGetMensajesByContactoAndContacto_Contacto1NoEncontrado() {

		Long contactoId1 = 1L;
		Long contactoId2 = 2L;

		when(contactoRepository.findById(contactoId1)).thenReturn(Optional.empty());

		assertThrows(ChatException.class, () -> mensajeService.getMensajesByContactoAndContacto(contactoId1, contactoId2));
	}

	@Test
	public void testGetMensajesByContactoAndContacto_Contacto2NoEncontrado() {

		Long contactoId1 = 1L;
		Long contactoId2 = 2L;
		Contacto contacto1 = new Contacto();
		contacto1.setId(contactoId1);

		when(contactoRepository.findById(contactoId1)).thenReturn(Optional.of(contacto1));
		when(contactoRepository.findById(contactoId2)).thenReturn(Optional.empty());

		assertThrows(ChatException.class, () -> mensajeService.getMensajesByContactoAndContacto(contactoId1, contactoId2));
	}

	@Test
	public void testGetMensajesByContactoAndContacto_MensajesNoEncontrados() {

		Long contactoId1 = 1L;
		Long contactoId2 = 2L;
		Contacto contacto1 = new Contacto();
		contacto1.setId(contactoId1);
		Contacto contacto2 = new Contacto();
		contacto2.setId(contactoId2);

		when(contactoRepository.findById(contactoId1)).thenReturn(Optional.of(contacto1));
		when(contactoRepository.findById(contactoId2)).thenReturn(Optional.of(contacto2));
		when(mensajeRepository.findByRemitenteIdAndDestinatarioId(contactoId1, contactoId2)).thenReturn(Optional.empty());

		assertThrows(ChatException.class, () -> mensajeService.getMensajesByContactoAndContacto(contactoId1, contactoId2));
	}

	@Test
	public void testEnviarMensaje() {

		CrearMensajeRequest crearMensajeRequest = new CrearMensajeRequest();
		crearMensajeRequest.setRemitenteId(1L);
		crearMensajeRequest.setDestinatarioId(2L);
		crearMensajeRequest.setRemitente("Remitente");
		crearMensajeRequest.setDestinatario("Destinatario");
		crearMensajeRequest.setTexto("Texto");

		Mensaje mensaje = new Mensaje();
		mensaje.setRemitenteId(crearMensajeRequest.getRemitenteId());
		mensaje.setDestinatarioId(crearMensajeRequest.getDestinatarioId());
		mensaje.setRemitente(crearMensajeRequest.getRemitente());
		mensaje.setDestinatario(crearMensajeRequest.getDestinatario());
		mensaje.setFechaHora(LocalDateTime.now());
		mensaje.setTexto(crearMensajeRequest.getTexto());

		when(mensajeRepository.save(mensaje)).thenReturn(mensaje);

		String result = mensajeService.enviarMensaje(crearMensajeRequest);

		assertNotNull(result);
		assertEquals(result, "Mensaje enviado correctamente");
	}

	@Test
	public void testUpdateMensaje() {

		Long mensajeId = 1L;

		MensajeDTO mensajeDTO = new MensajeDTO();
		mensajeDTO.setTexto("NuevoTexto");

		Mensaje mensaje = new Mensaje();
		mensaje.setId(mensajeId);
		mensaje.setTexto("AntiguoTexto");

		when(mensajeRepository.findById(mensajeId)).thenReturn(Optional.of(mensaje));
		when(mensajeRepository.save(mensaje)).thenReturn(mensaje);
		when(mapperChat.convertirAMensajeDTO(mensaje)).thenReturn(mensajeDTO);

		MensajeDTO result = mensajeService.updateMensaje(mensajeId, mensajeDTO);

		assertNotNull(result);
		assertEquals(mensajeDTO.getTexto(), result.getTexto());
	}

	@Test
	public void testUpdateMensaje_MensajeNoEncontrado() {

		Long mensajeId = 1L;
		MensajeDTO mensajeDTO = new MensajeDTO();

		when(mensajeRepository.findById(mensajeId)).thenReturn(Optional.empty());

		assertThrows(ChatException.class, () -> mensajeService.updateMensaje(mensajeId, mensajeDTO));
	}

	@Test
	public void testDeleteMensaje() {

		Long mensajeId = 1L;
		Mensaje mensaje = new Mensaje();
		mensaje.setId(mensajeId);
		when(mensajeRepository.findById(mensajeId)).thenReturn(Optional.of(mensaje));

		mensajeService.deleteMensaje(mensajeId);
	}

	@Test
	public void testDeleteMensaje_MensajeNoEncontrado() {

		Long mensajeId = 1L;

		when(mensajeRepository.findById(mensajeId)).thenReturn(Optional.empty());

		assertThrows(ChatException.class, () -> mensajeService.deleteMensaje(mensajeId));
	}
}