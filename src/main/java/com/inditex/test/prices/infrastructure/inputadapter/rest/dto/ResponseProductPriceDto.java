package com.inditex.test.prices.infrastructure.inputadapter.rest.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto utilizado para devolver los datos requeridos para el metodo "product-price" del API Rest.
 * 
 * @author fmallado
 * @since 1.0.0
 *
 */
@Builder	
@Getter 
@Setter
public class ResponseProductPriceDto {
	
	/**
	 * Identificador del producto.
	 */
	private Integer productId;
	
	/**
	 * Identificador de la cadena del grupo (1 = ZARA)
	 */
	private Integer brandId;
	
	/**
	 * Precio final de venta.
	 */
	private BigDecimal pvp;
	
	/**
	 * Código iso de la moneda.
	 */
	private String curr;
	
	/**
	 * Identificador de la tarifa de precios aplicable.
	 */
	private Integer priceList;
	
	/**
	 * Fecha de inicio de aplicación de la tarifa
	 */
	private OffsetDateTime startDate;
	
	/**
	 * Fecha de fin de aplicación de la tarifa
	 */
	private OffsetDateTime endDate;
}
