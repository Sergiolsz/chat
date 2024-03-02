package com.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChatException extends RuntimeException {

	private final String resourceName;
	private final String fieldName;
	private final Object fieldValue;

	/**
	 * Constructor de la excepción sin un campo específico.
	 *
	 * @param resourceName El nombre del recurso que no se pudo encontrar.
	 */
	public ChatException(String resourceName) {
		super(resourceName);
		this.resourceName = resourceName;
		this.fieldName = null;
		this.fieldValue = null;
	}

	/**
	 * Constructor de la excepción con el nombre del recurso y el nombre del campo.
	 *
	 * @param resourceName El nombre del recurso.
	 * @param fieldName    El nombre del campo que no se pudo encontrar.
	 */
	public ChatException(String resourceName, String fieldName) {
		super(String.format("%s : '%s'", resourceName, fieldName));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = null;
	}

	/**
	 * Constructor de la excepción con el nombre del recurso, el nombre del campo y su valor.
	 *
	 * @param resourceName El nombre del recurso.
	 * @param fieldName    El nombre del campo.
	 * @param fieldValue   El valor del campo que no se pudo encontrar.
	 */
	public ChatException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s no encontrado con %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	/**
	 * Obtiene el nombre del recurso.
	 *
	 * @return El nombre del recurso.
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * Obtiene el nombre del campo.
	 *
	 * @return El nombre del campo.
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Obtiene el valor del campo.
	 *
	 * @return El valor del campo.
	 */
	public Object getFieldValue() {
		return fieldValue;
	}
}