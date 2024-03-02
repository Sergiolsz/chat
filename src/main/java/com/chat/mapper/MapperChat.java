package com.chat.mapper;

import com.chat.entity.Contacto;
import com.chat.entity.Mensaje;
import com.chat.entity.Propietario;
import com.chat.modelos.dto.ContactoDTO;
import com.chat.modelos.dto.MensajeDTO;
import com.chat.modelos.dto.PropietarioDTO;
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