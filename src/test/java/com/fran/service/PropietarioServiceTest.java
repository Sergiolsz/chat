package com.fran.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.fran.entity.Contacto;
import com.fran.entity.Propietario;
import com.fran.mapper.MapperChat;
import com.fran.modelos.dto.PropietarioDTO;
import com.fran.modelos.response.ContactosPropietarioResponse;
import com.fran.repository.ContactoRepository;
import com.fran.repository.PropietarioRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PropietarioServiceTest {

	@Mock
	private PropietarioRepository propietarioRepository;

	@Mock
	private ContactoRepository contactoRepository;

	@Mock
	private MapperChat mapperChat;

	@InjectMocks
	private PropietarioService propietarioService;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllPropietarios() {

		List<Propietario> propietarios = Arrays.asList(new Propietario(), new Propietario());
		when(propietarioRepository.findAll()).thenReturn(propietarios);
		when(mapperChat.convertirAPropietarioDTO(any())).thenReturn(new PropietarioDTO());

		List<PropietarioDTO> result = propietarioService.getAllPropietarios();

		assertEquals(propietarios.size(), result.size());
	}

	@Test
	public void testGetPropietarioById() {

		Long propietarioId = 1L;
		Propietario propietario = new Propietario();
		propietario.setId(propietarioId);

		PropietarioDTO propietarioDTO = new PropietarioDTO();
		propietarioDTO.setId(propietarioId);

		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));
		when(mapperChat.convertirAPropietarioDTO(propietario)).thenReturn(propietarioDTO);

		PropietarioDTO result = propietarioService.getPropietarioById(propietarioId);

		assertNotNull(result);
		assertEquals(propietarioId, result.getId());
	}

	@Test
	public void testGetContactosDePropietario() {

		Long propietarioId = 1L;
		Propietario propietario = new Propietario();
		Set<Contacto> contactos = new HashSet<>();
		contactos.add(new Contacto("Contacto1", propietario));
		contactos.add(new Contacto("Contacto2", propietario));
		propietario.setContactos(contactos);
		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));

		ContactosPropietarioResponse result = propietarioService.getContactosDePropietario(propietarioId);

		assertNotNull(result);
		assertEquals(propietario.getNombre(), result.getPropietario());
		assertEquals(contactos.size(), result.getContactos().size());
	}

	@Test
	public void testCrearPropietario() {

		PropietarioDTO propietarioDTO = new PropietarioDTO();
		propietarioDTO.setNombre("Propietario1");
		when(propietarioRepository.existsByNombre(anyString())).thenReturn(false);
		when(mapperChat.convertirAPropietario(propietarioDTO)).thenReturn(new Propietario());
		when(propietarioRepository.save(any())).thenReturn(new Propietario());

		String result = propietarioService.crearPropietario(propietarioDTO);

		assertEquals("Propietario Propietario1 creado correctamente", result);
	}

	@Test
	public void testAsignarContacto() {

		Long propietarioId = 1L;
		Long contactoId = 2L;
		Propietario propietario = new Propietario();
		propietario.setId(propietarioId);
		Contacto contacto = new Contacto();
		contacto.setId(contactoId);
		propietario.getContactos().add(contacto);
		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));
		when(contactoRepository.findById(contactoId)).thenReturn(Optional.of(contacto));

		String result = propietarioService.asignarContacto(propietarioId, contactoId);

		assertEquals("Contacto asignado correctamente al propietario", result);
		assertEquals(propietario, contacto.getPropietario());
	}

	@Test
	public void testActualizarPropietario() {

		Long propietarioId = 1L;
		PropietarioDTO propietarioDTO = new PropietarioDTO();
		propietarioDTO.setId(propietarioId);
		propietarioDTO.setNombre("NuevoNombre");

		Propietario propietario = new Propietario();
		propietario.setId(propietarioId);
		propietario.setNombre("AntiguoNombre");

		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));
		when(mapperChat.convertirAPropietarioDTO(propietario)).thenReturn(propietarioDTO);

		PropietarioDTO result = propietarioService.actualizarPropietario(propietarioId, propietarioDTO);

		assertEquals("NuevoNombre", result.getNombre());
	}

	@Test
	public void testEliminarPropietario() {

		Long propietarioId = 1L;
		Propietario propietario = new Propietario();
		propietario.setId(propietarioId);
		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));

		String result = propietarioService.eliminarPropietario(propietarioId);

		assertEquals("El propietario ha sido eliminado exitosamente.", result);
	}

	@Test
	public void testEliminarContactoDePropietario() {

		Long propietarioId = 1L;
		Long contactoId = 2L;
		Propietario propietario = new Propietario();
		propietario.setId(propietarioId);
		Contacto contacto = new Contacto();
		contacto.setId(contactoId);
		propietario.getContactos().add(contacto);
		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));
		when(contactoRepository.findById(contactoId)).thenReturn(Optional.of(contacto));

		String result = propietarioService.eliminarContactoDePropietario(propietarioId, contactoId);

		assertEquals("Eliminado correctamente el Contacto null del listado del Propietario", result);
		assertTrue(propietario.getContactos().isEmpty());
	}

	@Test
	public void testEliminarContactosDePropietario() {

		Long propietarioId = 1L;
		Propietario propietario = new Propietario();
		propietario.setId(propietarioId);
		Set<Contacto> contactos = new HashSet<>();
		Contacto contacto1 = new Contacto("Contacto1", propietario);
		Contacto contacto2 = new Contacto("Contacto2", propietario);
		contactos.add(contacto1);
		contactos.add(contacto2);
		propietario.setContactos(contactos);
		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));
		when(contactoRepository.findByNombreIn(anyList())).thenReturn(Arrays.asList(contacto1, contacto2));

		String result = propietarioService.eliminarContactosDePropietario(propietarioId);

		assertEquals("Eliminados todos los contactos del propietario correctamente", result);
		assertTrue(propietario.getContactos().isEmpty());
	}

}