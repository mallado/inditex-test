package com.inditex.test.prices.domain.businessrules;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.inditex.test.prices.domain.Price;
import com.inditex.test.prices.domain.exception.PriceException;

/**
 * Case que contiene las reglas de negocio para las operaciones de venta de los productos
 * 
 * @author fmallado
 * @since 1.0.0
 */
public class BusinessRules {

	/**
	 * logger
	 */
    private static final Logger logger = LogManager.getLogger(BusinessRules.class);
    
    /**
     * Constructor privado
     */
	private BusinessRules() {
		throw new IllegalStateException("Clase de utilidades.");
	}

	/**
	 * Método que calcula el precio de venta que hay que aplicar a un producto.
	 * 
	 * @param brandId          Identifcador de la cadena del grupo.
	 * @param applicationDate  Fecha de aplicación del precio al producto.
	 * @param productId        Identificador del producto.
	 * @param productPriceList Lista de precios disponibles para el producto.
	 * @return Precio de venta.
	 * @throws PriceException Excepción de dominio.
	 */
	public static Price getProductPrice(Integer brandId, OffsetDateTime applicationDate, Integer productId,
			List<Price> productPriceList) throws PriceException {
		logger.debug("Llamada al método getProductPrice con los atributos: brandId->{}, applicationDate->{}, productId->{}, productPriceList->{}", brandId, applicationDate, productId, productPriceList);
		validations(brandId, applicationDate, productId, productPriceList);

		logger.debug("Filtrando la lista de precios de producto por brandId y productId.");
		// Filtramos la lista por brandId y productos
		List<Price> productPricesByBrandId = productPriceList.stream()
				.filter(price -> price.getBrandId().equals(brandId) && price.getProductId().equals(productId)).toList();
		if (productPricesByBrandId.isEmpty())
			throw new PriceException("No existen precios definidos para el brandId y productId indicados.");

		logger.debug("Filtrando la lista de precios de producto por la fecha de aplicación.");
		// Verificamos si existen precios para la fecha de aplicación indicada.
		List<Price> pricesByApplicationDate = productPricesByBrandId.stream().filter(
				price -> price.getStartDate().isBefore(applicationDate) && price.getEndDate().isAfter(applicationDate))
				.sorted(Comparator.comparingInt(Price::getPriority).reversed()).toList();
		if (pricesByApplicationDate.isEmpty())
			throw new PriceException("No existen precios definidos para la fecha de aplicación indicada.");

		logger.debug("Obteniendo el precio de aplicación.");
		// Ordenamos los precios vigentes por prioridad y nos quedamos con el más
		// prioritario.
		List<Price> pricesByApplicationDateOrderByPriority = pricesByApplicationDate.stream()
				.sorted(Comparator.comparingInt(Price::getPriority).reversed()).toList();
		return pricesByApplicationDateOrderByPriority.get(0);
	}

	/**
	 * Méodo de validación de los parámetros de entrada al método getProductPrice
	 * 
	 * @param brandId          Identifcador de la cadena del grupo.
	 * @param applicationDate  Fecha de aplicación del precio al producto.
	 * @param productId        Identificador del producto.
	 * @param productPriceList Lista de precios disponibles para el producto.
	 * 
	 * @throws IllegalArgumentException
	 * @throws PriceException
	 */
	private static void validations(Integer brandId, OffsetDateTime applicationDate, Integer productId,
			List<Price> productPriceList) throws IllegalArgumentException, PriceException {
		logger.debug("Realizando las validaciones de los parámetrod de entrada sobre getProductPrice.");
		if (brandId == null)
			throw new IllegalArgumentException("El parámetro brandId es obligatorio.");
		if (applicationDate == null)
			throw new IllegalArgumentException("El parámetro applicationDate es obligatorio.");
		if (productId == null)
			throw new IllegalArgumentException("El parámetro productId es obligatorio.");
		if (productPriceList == null)
			throw new IllegalArgumentException("La lista de precios del producto es obligatoria.");
		if (productPriceList.isEmpty())
			throw new PriceException("No existen precios definidos para el brandId y productId indicados.");
		
		logger.debug("Validación realizada correctamente.");
	}
}
