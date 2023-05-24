package com.inditex.test.prices.infrastructure.outputadapter.db.springdata.dbo;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de BBDD donde se almacenan los precios de los productos.
 * 
 * @author fmallado
 * @since 1.0.0
 */
@Entity
@Data
@NoArgsConstructor
@Table(name="prices")
public class PriceEntity {

	/**
	 * Identificador
	 */
	@Id
	private Integer id;
	
	/**
	 * Identificador de la cadena del grupo (1 = ZARA)
	 */
	@Column(nullable = false)
	private Integer brandId;
	
	/**
	 * Fecha de inicio de aplicación de la tarifa
	 */
	@Column(nullable = false)
	private OffsetDateTime startDate;
	
	/**
	 * Fecha de fin de aplicación de la tarifa
	 */
	@Column(nullable = false)
	private OffsetDateTime endDate;
	
	/**
	 * Identificador de la tarifa de precios aplicable.
	 */
	@Column(nullable = false)
	private Integer priceList;
	
	/**
	 * Identificador del producto.
	 */
	@Column(nullable = false)
	private Integer productId;
	
	/**
	 * Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rago de fechas se aplica la de mayor prioridad (mayor valor numérico).
	 */
	@Column(nullable = false)
	private Integer priority;
	
	/**
	 * Precio final de venta.
	 */
	@Column(nullable = false, name = "price")
	private BigDecimal pvp;
	
	/**
	 * Código iso de la moneda.
	 */
	@Column(nullable = false)
	private String curr;
}
