package com.inditex.test.prices.infrastructure.inputport;

import java.time.OffsetDateTime;

import com.inditex.test.prices.infrastructure.inputadapter.rest.dto.ResponseProductPriceDto;

/**
 * Puerto de entrada
 * 
 * @author fmallado
 * @since 1.0.0
 *
 */
public interface PriceInputPort {

	/**
	 * Método que obtiene el precio de un producto para una fecha determinada.
	 * 
	 * @param brandId         Identifcador de la cadena del grupo.
	 * @param applicationDate Fecha de aplicación del precio al producto.
	 * @param productId       Identificador del producto.
	 * @return Precio del producto en la cadena del grupo para la fecha indicada.
	 */
	public ResponseProductPriceDto getProductPrice(Integer brandId, OffsetDateTime applicationDate, Integer productId);
}
