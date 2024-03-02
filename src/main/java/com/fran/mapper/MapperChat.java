package com.fran.mapper;

import com.fran.modelos.dto.ContactoDTO;
import com.fran.modelos.dto.MensajeDTO;
import com.fran.modelos.dto.PropietarioDTO;
import com.fran.entity.Contacto;
import com.fran.entity.Mensaje;
import com.fran.entity.Propietario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperChat {

	private final ModelMapper modelMapper;

	public MapperChat(ModelMapper modelMapper) {

		this.modelMapper = modelMapper;
	}

	public Contacto convertirAContacto(ContactoDTO contactoDTO) {
		return modelMapper.map(contactoDTO, Contacto.class);
	}

	public ContactoDTO convertirAContactoDTO(Contacto contacto) {

		return modelMapper.map(contacto, ContactoDTO.class);
	}

	public Propietario convertirAPropietario(PropietarioDTO propietarioDTO) {

		return modelMapper.map(propietarioDTO, Propietario.class);
	}

	public PropietarioDTO convertirAPropietarioDTO(Propietario propietario) {

		return modelMapper.map(propietario, PropietarioDTO.class);
	}

	public MensajeDTO convertirAMensajeDTO(Mensaje mensaje) {
		return modelMapper.map(mensaje, MensajeDTO.class);
	}

}