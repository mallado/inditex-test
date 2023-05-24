package com.inditex.test.prices.infrastructure.inputadapter.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.inditex.test.prices.domain.Price;
import com.inditex.test.prices.infrastructure.inputadapter.rest.dto.ResponseProductPriceDto;

/**
 * Mapper usado para realizar los mapeos entre los objetos usados por los
 * puertos de entrada y los objetos del dominio
 * 
 * @author fmallado
 * @since 1.0.0
 *
 */
@Mapper(componentModel = "spring")
public interface PriceRestMapper {

	PriceRestMapper INSTANCE = Mappers.getMapper(PriceRestMapper.class);

	/**
	 * Método que transforma un Price a ResponseProductPriceDto
	 * 
	 * @param price Objeto Price a transformar
	 * @return Objeto ResponseProductPriceDto transformado
	 */
	ResponseProductPriceDto toResponseProductPriceDto(Price price);

}