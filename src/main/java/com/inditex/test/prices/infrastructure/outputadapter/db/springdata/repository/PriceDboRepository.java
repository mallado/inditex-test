package com.inditex.test.prices.infrastructure.outputadapter.db.springdata.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.inditex.test.prices.domain.Price;
import com.inditex.test.prices.infrastructure.outputadapter.db.springdata.dbo.PriceEntity;
import com.inditex.test.prices.infrastructure.outputadapter.db.springdata.mapper.PriceEntityMapper;
import com.inditex.test.prices.infrastructure.outputport.PriceRepositoryPort;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del adaptador de salida hacia la BBDD
 * 
 * @author fmallado
 * @since 1.0.0
 *
 */
@RequiredArgsConstructor
@Service
public class PriceDboRepository implements PriceRepositoryPort {

	/**
	 * logger
	 */
    private static final Logger logger = LogManager.getLogger(PriceDboRepository.class);
    
	/**
	 * Respositorio de Spring usado para realizar las consultas con la BBDD.
	 */
	@Autowired
	private SpringDataPriceRepository priceRepository;

	/**
	 * Mapper usado para realizar los mapeos entre los objetos usados por los
	 * puertos de salida y los objetos del dominio
	 */
	@Autowired
	private PriceEntityMapper priceEntityMapper;

	/**
	 * Método que obtiene la lista de precios para un determinado producto en una
	 * determinada cadena del grupo.
	 * 
	 * @param brandId   Identifcador de la cadena del grupo.
	 * @param productId Identificador del producto.
	 * @return Listado de precios del producto indicado.
	 */
	@Override
	public List<Price> getProductPrice(Integer brandId, Integer productId) {
		logger.debug("Realizando consulta de la tabla 'Prices' por los campos: brandId->{}, productId->{}", brandId, productId);
		
		PriceEntity priceProductFilter = new PriceEntity();
		priceProductFilter.setBrandId(brandId);
		priceProductFilter.setProductId(productId);
		Example<PriceEntity> example = Example.of(priceProductFilter);
		return priceEntityMapper.toDomainList(priceRepository.findAll(example));
	}

}