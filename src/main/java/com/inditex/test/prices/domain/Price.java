package com.inditex.test.prices.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad del dominio que almacena el precio de un producto para una cadena del grupo en un rango de fechas determinado.
 * 
 * @author fmallado
 * @since 1.0.0
 *
 */
@AllArgsConstructor	
@Getter 
@Setter
public class Price {

	/**
	 * Identificador
	 */
	private Integer id;
	
	/**
	 * Identificador de la cadena del grupo (1 = ZARA)
	 */
	private Integer brandId;
	
	/**
	 * Fecha de inicio de aplicación de la tarifa
	 */
	private OffsetDateTime startDate;
	
	/**
	 * Fecha de fin de aplicación de la tarifa
	 */
	private OffsetDateTime endDate;
	
	/**
	 * Identificador de la tarifa de precios aplicable.
	 */
	private Integer priceList;
	
	/**
	 * Identificador del producto.
	 */
	private Integer productId;
	
	/**
	 * Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rago de fechas se aplica la de mayor prioridad (mayor valor numérico).
	 */
	private Integer priority;
	
	/**
	 * Precio final de venta.
	 */
	private BigDecimal pvp;
	
	/**
	 * Código iso de la moneda.
	 */
	private String curr;
	
}
