package com.inditex.test.prices.infrastructure.inputadapter.rest.error;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author fmallado
 * @since 1.0.0
 *
 */

@Getter
@AllArgsConstructor
public class ErrorMessage {
	
	/**
	 * Fecha y hora en la que ocurre el error.
	 */
	private Date timestamp;
	
	/**
	 * Código de error.
	 */
	private int statusCode;
	
	/**
	 * Error
	 */
	private String error;
	
	/**
	 * Mensaje de error
	 */
	private String message;
	
	/**
	 * Path del método del API donde se ha producido el error.
	 */
	private String path;
	
}

