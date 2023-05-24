package com.inditex.test.prices.application;

import java.time.OffsetDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.inditex.test.prices.domain.businessrules.BusinessRules;
import com.inditex.test.prices.infrastructure.inputadapter.rest.dto.ResponseProductPriceDto;
import com.inditex.test.prices.infrastructure.inputadapter.rest.mapper.PriceRestMapper;
import com.inditex.test.prices.infrastructure.inputport.PriceInputPort;
import com.inditex.test.prices.infrastructure.outputport.PriceRepositoryPort;

/**
 * Servicio de la capa de aplicación encargado de implementar el puerto de
 * entrada del API Rest.
 * 
 * @author fmallado
 * @since 1.0.0
 */
public class PriceService implements PriceInputPort {

	/**
	 * logger
	 */
    private static final Logger logger = LogManager.getLogger(PriceService.class);
    
	/**
	 * Repositorio utilizado como conector con la BBDD
	 */
	private final PriceRepositoryPort entityRepository;

	/**
	 * Mapper usado para realizar los mapeos entre los objetos usados por los
	 * puertos de entrada y los objetos del dominio.
	 */
	private final PriceRestMapper priceRestMapper;

	/**
	 * Constructor del servicio
	 * 
	 * @param entityRepository Repositorio utilizado como conector con la BBDD
	 * @param priceRestMapper  Mapper usado para realizar los mapeos entre los
	 *                         objetos usados por los puertos de entrada y los
	 *                         objetos del dominio.
	 */
	public PriceService(PriceRepositoryPort entityRepository, PriceRestMapper priceRestMapper) {
		logger.debug("Inicializando PriceService");
		this.entityRepository = entityRepository;
		this.priceRestMapper = priceRestMapper;
	}

	@Override
	public ResponseProductPriceDto getProductPrice(Integer brandId, OffsetDateTime applicationDate, Integer productId) {
		logger.debug("Llamada al método getProductPrice con los atributos: brandId->{}, applicationDate->{}, productId->{}", brandId, applicationDate, productId);
		return priceRestMapper.toResponseProductPriceDto(BusinessRules.getProductPrice(brandId, applicationDate,
				productId, entityRepository.getProductPrice(brandId, productId)));
	}
}
