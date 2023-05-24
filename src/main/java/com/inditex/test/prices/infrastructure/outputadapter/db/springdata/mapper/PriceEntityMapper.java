package com.inditex.test.prices.infrastructure.outputadapter.db.springdata.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.inditex.test.prices.domain.Price;
import com.inditex.test.prices.infrastructure.outputadapter.db.springdata.dbo.PriceEntity;

/**
 * Mapper usado para realizar los mapeos entre los objetos usados por los
 * puertos de salida y los objetos del dominio
 * 
 * @author fmallado
 * @since 1.0.0
 *
 */
@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

	PriceEntityMapper INSTANCE = Mappers.getMapper(PriceEntityMapper.class);

	/**
	 * Método que transforma un PriceEntity a Price
	 * 
	 * @param priceEntity Objeto PriceEntity a transformar
	 * @return Objeto Price transformado
	 */
	Price toDomain(PriceEntity priceEntity);

	/**
	 * Método que transforma un Price a PriceEntity
	 * 
	 * @param price Objeto Price a transformar
	 * @return Objeto PriceEntity transformado
	 */
	PriceEntity toDbo(Price price);

	/**
	 * Método que transforma una lista de PriceEntity a una lista de Price
	 * 
	 * @param priceEntityList Lista de objetos PriceEntity a transformar
	 * @return Lista de objetos de tipo Price
	 */
	default List<Price> toDomainList(List<PriceEntity> priceEntityList) {
		if (priceEntityList == null) {
			return new ArrayList<>();
		}
		return priceEntityList.stream().map(this::toDomain).toList();
	}
}