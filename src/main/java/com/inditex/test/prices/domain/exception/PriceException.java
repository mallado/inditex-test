package com.inditex.test.prices.domain.exception;

/**
 * Clase de Excepción para las excepciones del dominio.
 * 
 * @author fmallado
 * @since 1.0.0
 */
public class PriceException extends RuntimeException {

	private static final long serialVersionUID = 3330852598411086850L;

	/**
	 * Constructor
	 * 
	 * @param message Mensaje de la excepción.
	 */
	public PriceException(String message) {
		super(message);
	}
}
