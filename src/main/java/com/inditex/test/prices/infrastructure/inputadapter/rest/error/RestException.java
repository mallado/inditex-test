package com.inditex.test.prices.infrastructure.inputadapter.rest.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * Clase de Excepción para los errores en el API Rest.
 * 
 * @author fmallado
 * @since 1.0.0
 */
@Getter
public class RestException extends Exception {

	private static final long serialVersionUID = 2646492212496445879L;

	/**
	 * Codigo del estato de la petición.
	 */
	private final HttpStatus httpStatus;

	/**
	 * Path del método del API donde se ha producido el error.
	 */
	private final String path;

	/**
	 * Constructor
	 * 
	 * @param httpStatus Codigo del estato de la petición.
	 * @param e          Excepción producida
	 * @param path       Path del método del API donde se ha producido el error.
	 */
	public RestException(HttpStatus httpStatus, Exception e, String path) {
		super(e.getMessage(), e, false, false);
		this.httpStatus = httpStatus;
		this.path = path;
	}
}
