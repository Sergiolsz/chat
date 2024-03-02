package com.fran.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.fran.entity.Contacto;
import com.fran.entity.Propietario;
import com.fran.mapper.MapperChat;
import com.fran.modelos.dto.ContactoDTO;
import com.fran.modelos.dto.PropietarioDTO;
import com.fran.repository.ContactoRepository;
import com.fran.repository.PropietarioRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ContactoServiceTest {

	@Mock
	private ContactoRepository contactoRepository;

	@Mock
	private PropietarioRepository propietarioRepository;

	@Mock
	private MapperChat mapperChat;

	@InjectMocks
	private ContactoService contactoService;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetContactoById() {

		Long contactoId = 1L;
		Contacto contacto = new Contacto();
		contacto.setId(contactoId);

		ContactoDTO contactoDTO = new ContactoDTO();
		contactoDTO.setId(contactoId);

		when(contactoRepository.findById(contactoId)).thenReturn(Optional.of(contacto));
		when(mapperChat.convertirAContactoDTO(contacto)).thenReturn(contactoDTO);

		ContactoDTO result = contactoService.getContactoById(contactoId);

		assertNotNull(result);
		assertEquals(contactoId, result.getId());
	}

	@Test
	public void testGetPropietarioDelContacto() {

		Long contactoId = 1L;
		Contacto contacto = new Contacto();
		Propietario propietario = new Propietario();
		propietario.setId(1L);
		contacto.setPropietario(propietario);

		when(contactoRepository.findById(contactoId)).thenReturn(Optional.of(contacto));
		when(mapperChat.convertirAPropietarioDTO(propietario)).thenReturn(new PropietarioDTO());

		PropietarioDTO result = contactoService.getPropietarioDelContacto(contactoId);

		assertNotNull(result);
	}

	@Test
	public void testCrearContacto() {

		ContactoDTO contactoDTO = new ContactoDTO();
		contactoDTO.setNombre("NuevoContacto");

		when(contactoRepository.existsByNombre(contactoDTO.getNombre())).thenReturn(false);

		String result = contactoService.crearContacto(contactoDTO);

		assertEquals("Propietario NuevoContacto creado correctamente", result);
	}

	@Test
	public void testAsignarPropietario() {

		Long contactoId = 1L;
		Long propietarioId = 1L;
		Contacto contacto = new Contacto();
		Propietario propietario = new Propietario();

		when(contactoRepository.findById(contactoId)).thenReturn(Optional.of(contacto));
		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));

		String result = contactoService.asignarPropietario(contactoId, propietarioId);

		assertEquals("Propietario asignado correctamente al contacto", result);
	}

	@Test
	public void testActualizarContacto() {

		Long contactoId = 1L;
		ContactoDTO contactoDTO = new ContactoDTO();
		contactoDTO.setId(contactoId);
		contactoDTO.setNombre("NuevoNombre");

		Contacto contacto = new Contacto();
		contacto.setId(contactoId);
		contacto.setNombre("AntiguoNombre");

		when(contactoRepository.findById(contactoId)).thenReturn(Optional.of(contacto));
		when(mapperChat.convertirAContactoDTO(contacto)).thenReturn(contactoDTO);

		ContactoDTO result = contactoService.actualizarContacto(contactoId, contactoDTO);

		assertEquals("NuevoNombre", result.getNombre());
	}

	@Test
	public void testEliminarContacto() {

		Long contactoId = 1L;
		Contacto contacto = new Contacto();
		contacto.setId(contactoId);

		when(contactoRepository.findById(contactoId)).thenReturn(Optional.of(contacto));

		String result = contactoService.eliminarContacto(contactoId);

		assertEquals("El contacto ha sido eliminado exitosamente.", result);
	}

	@Test
	public void testEliminarPropietarioDeContacto() {

		Long contactoId = 1L;
		Long propietarioId = 1L;
		Contacto contacto = new Contacto();
		contacto.setId(contactoId);
		Propietario propietario = new Propietario();
		propietario.setId(propietarioId);

		when(contactoRepository.findById(contactoId)).thenReturn(Optional.of(contacto));
		when(propietarioRepository.findById(propietarioId)).thenReturn(Optional.of(propietario));

		String result = contactoService.eliminarPropietarioDeContacto(propietarioId, contactoId);

		assertEquals(
			"Se eliminó correctamente el propietario del contacto y el contacto del listado de contactos del propietario.",
			result);
	}
}