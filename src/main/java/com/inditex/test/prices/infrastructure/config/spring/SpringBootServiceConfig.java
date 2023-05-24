package com.inditex.test.prices.infrastructure.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.inditex.test.prices.application.PriceService;
import com.inditex.test.prices.infrastructure.inputadapter.rest.mapper.PriceRestMapper;
import com.inditex.test.prices.infrastructure.outputport.PriceRepositoryPort;

/**
 * Clase de configuración de SpringBoot.
 * 
 * En esta clase se define de forma manual el Bean priceService de la capa de
 * aplicación en lugar de hacerlo por anotaciones para que la capa de aplicación
 * sea independiente al framework de Spring.
 * 
 * @author fmallado
 * @since 1.0.0
 *
 */
@Configuration
public class SpringBootServiceConfig {

	/**
	 * Inicialización del priceService en el contexto de Spring.
	 * 
	 * @param priceDboRepository Adaptador de salida hacia la BBDD
	 * @param priceRestMapper    Mapper usado para realizar los mapeos entre los
	 *                           objetos usados por los puertos de salida y los
	 *                           objetos del dominio.
	 * 
	 * @return
	 */
	@Bean
	PriceService priceService(PriceRepositoryPort priceDboRepository, PriceRestMapper priceRestMapper) {
		return new PriceService(priceDboRepository, priceRestMapper);
	}
}