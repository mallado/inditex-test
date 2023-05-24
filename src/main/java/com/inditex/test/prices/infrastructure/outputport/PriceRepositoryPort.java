package com.inditex.test.prices.infrastructure.outputport;

import java.util.List;

import com.inditex.test.prices.domain.Price;

/**
 * Puerto de salida
 * 
 * @author fmallado
 * @since 1.0.0
 *
 */
public interface PriceRepositoryPort {

	/**
	 * Método que obtiene la lista de precios para un determinado producto en una
	 * determinada cadena del grupo.
	 * 
	 * @param brandId   Identifcador de la cadena del grupo.
	 * @param productId Identificador del producto.
	 * @return Listado de precios del producto indicado.
	 */
	public List<Price> getProductPrice(Integer brandId, Integer productId);
}
