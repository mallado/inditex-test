package com.inditex.test.prices.infrastructure.inputadapter.rest.error;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Hanler encargado de transformar las excepciones de tipo RestException a un tipo de respuesta JSON.
 * 
 * @author fmallado
 * @since 1.0.0
 *
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
	
	/**
	 * Método encargado de manejar la excepción.
	 * @param ex Excepción producida.
	 * @return Respuesta de la excepción en formato JSON.
	 */
	@ExceptionHandler(RestException.class)
	public ResponseEntity<ErrorMessage> restException(RestException ex) {
	    
		ErrorMessage message = new ErrorMessage(new Date(),
	    	ex.getHttpStatus().value(),
	        ex.getMessage(),
	        ex.getHttpStatus().name(),
	        ex.getPath());
	    return new ResponseEntity<>(message, ex.getHttpStatus());
	}
}
