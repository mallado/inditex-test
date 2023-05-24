package com.inditex.test.prices;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.inditex.test.prices.domain.Price;
import com.inditex.test.prices.domain.businessrules.BusinessRules;
import com.inditex.test.prices.domain.exception.PriceException;

/**
 * Pruebas unitarias sobre la capa de dominio
 * 
 * @author fmallado
 * @since 1.0.0
 */
@DisplayName("Pruebas unitarias sobre la capa de dominio")
class PricesDomainTests {
	
	/**
	 * logger
	 */
    private static final Logger logger = LogManager.getLogger(PricesDomainTests.class);

    /**
     * Listado de precios que simula los datos existentes en BBDD
     */
	private static List<Price> productPriceList = new ArrayList<>();
	
	/**
     * Identificador un producto para el que existen tarifas en la BBDD
     */
	private static final Integer PRODUCT_ID = 35455;
	
	/**
	 * Fecha de aplicación de una tarifa para el producto PRODUCT_ID para la que existen tarifas en la BBDD.
	 */
	private static final OffsetDateTime DATE_APPLICATION_PRODUCT_EXISTENT  = OffsetDateTime.parse("2020-06-15T00:01:00+02:00");
	
	/**
	 * Código de una cadena para la que no existen precios en la BBDD.
	 */
	private static final Integer BRAND_ID_NON_EXISTEN = 99;

	/**
	 * Código de una cadena para la que existen precios en la BBDD.
	 */
	private static final Integer BRAND_ID = 1;
	
	/**
     * Identificador un producto para el no existen tarifas en la BBDD.
     */
	private static final Integer PRODUCT_ID_NON_EXISTENT = 999999;
	
	/**
     * Fecha de aplicación de una tarifa para el producto PRODUCT_ID para la que no existen tarifas en la BBDD.
     */
	private static final OffsetDateTime DATE_APPLICATION_PRODUCT_NON_EXISTENT  = OffsetDateTime.parse("2000-01-01T00:00:00+01:00");
	
	@BeforeAll
	static void ini() {
        logger.info("##### Start PricesTests #####");
        logger.info("Inicializando variables para el test.");
		productPriceList.add(new Price(1, 1, OffsetDateTime.parse("2020-06-14T00:00:00+02:00"), OffsetDateTime.parse("2020-12-31T23:59:59+01:00"), 1, 35455, 0,  new BigDecimal("35.50"), "EUR"));
		productPriceList.add(new Price(2, 1, OffsetDateTime.parse("2020-06-14T15:00:00+02:00"), OffsetDateTime.parse("2020-06-14T18:30:00+02:00"), 2, 35455, 1,  new BigDecimal("25.45"), "EUR"));
		productPriceList.add(new Price(2, 1, OffsetDateTime.parse("2020-06-15T00:00:00+02:00"), OffsetDateTime.parse("2020-06-15T11:00:00+02:00"), 3, 35455, 1,  new BigDecimal("30.50"), "EUR"));
		productPriceList.add(new Price(2, 1, OffsetDateTime.parse("2020-06-15T16:00:00+02:00"), OffsetDateTime.parse("2020-12-31T23:59:59+01:00"), 4, 35455, 1,  new BigDecimal("38.95"), "EUR"));
		logger.info("Variables inicializadas.");
	}
	
	
	@ParameterizedTest(name = "Test{index}: Petición el {1} para el producto {2} y la brand {0} - {argumentsWithNames}")
	@CsvFileSource(resources = {"/prices.csv"}, delimiter = ';', numLinesToSkip = 1)
	@DisplayName("Pruebas con los casos de uso indicados en la definidión del ejercicio de Inditex.")
	void productPriceTest(Integer brandId, OffsetDateTime applicationDate, Integer productId, Integer priceList, BigDecimal price, String curr, OffsetDateTime startDate, OffsetDateTime endDate) {
		logger.info("##### Start productPriceTest #####");
		Price applicationPrice = BusinessRules.getProductPrice(brandId, applicationDate, productId, productPriceList);
		assertNotNull(applicationPrice, () -> "No se ha encontrado el precio del producto para la fecha de aplicación indicada.");
		assertEquals(applicationPrice.getBrandId(), brandId,  () -> "La cadena del grupo del precio devuelto (%s) no es la misma que la cadena solicitada (%s).".formatted(applicationPrice.getBrandId(), brandId)); 
		assertEquals(applicationPrice.getProductId(), productId,  () -> "El identificador del producto devuelto (%s) no es el mismo que el identificador del producto solicitado (%s).".formatted(applicationPrice.getProductId(), productId));
		assertEquals(applicationPrice.getPriceList(), priceList,  () -> "El identificador de tarifa devuelto (%s) no corresponde con el esperado (%s).".formatted(priceList, applicationPrice.getPriceList()));
		assertEquals(applicationPrice.getPvp(), price,  () -> "El precio del producto devuelto (%s) no corresponde con el precio esperado (%s).".formatted(price, applicationPrice.getPvp()));
		assertEquals(applicationPrice.getCurr(), curr,  () -> "La moneda del precio devuelta (%s) no corresponde con la esperada (%s).".formatted(curr, applicationPrice.getCurr()));
		logger.info("##### End productPriceTest #####");
	}

	@Test
	@DisplayName("Prueba con una fecha de aplicación en la que no existe el producto.")
	void productPriceBeforeDateDisponibilityTest() {
		logger.info("##### Start productPriceBeforeDateDisponibilityTest #####");
		
		PriceException textException = catchThrowableOfType(() -> { 
				BusinessRules.getProductPrice(BRAND_ID, DATE_APPLICATION_PRODUCT_NON_EXISTENT, PRODUCT_ID, productPriceList); },
				PriceException.class);

		assertThat(textException).hasMessage("No existen precios definidos para la fecha de aplicación indicada.");
		
		logger.info("##### End productPriceBeforeDateDisponibilityTest #####");
	}
	

	@Test
	@DisplayName("Prueba con un producto que no existe.")
	void productNotExistTest() {
		logger.info("##### Start productNotExistTest #####");
		PriceException textException = catchThrowableOfType(() -> { 
			BusinessRules.getProductPrice(BRAND_ID, DATE_APPLICATION_PRODUCT_EXISTENT, PRODUCT_ID_NON_EXISTENT, productPriceList); },
			PriceException.class);

		assertThat(textException).hasMessage("No existen precios definidos para el brandId y productId indicados.");
		logger.info("##### End productNotExistTest #####");
	}
	
	@Test
	@DisplayName("Prueba con una cadena del grupo que no existe.")
	void brandNotExistTest() {
		logger.info("##### Start brandNotExistTest #####");
		PriceException textException = catchThrowableOfType(() -> { 
			BusinessRules.getProductPrice(BRAND_ID_NON_EXISTEN, DATE_APPLICATION_PRODUCT_EXISTENT, PRODUCT_ID, productPriceList); },
			PriceException.class);

		assertThat(textException).hasMessage("No existen precios definidos para el brandId y productId indicados.");
		logger.info("##### End brandNotExistTest #####");
	}
	
	@Test
	@DisplayName("Prueba con los campos obligatorios a nulos.")
	void requeryParamWhitOutTest() {
			
		logger.info("##### Start requeryParamWhitOutTest #####");
		logger.info("Lanzando prueba sin el BRAND_ID");
		IllegalArgumentException textException = catchThrowableOfType(() -> { 
			BusinessRules.getProductPrice(null, DATE_APPLICATION_PRODUCT_EXISTENT, PRODUCT_ID, productPriceList); },
				IllegalArgumentException.class);
		assertThat(textException).hasMessage("El parámetro brandId es obligatorio.");
		
		logger.info("Lanzando prueba sin el DATE_APPLICATION_PRODUCT_EXISTENT");
		textException = catchThrowableOfType(() -> { 
			BusinessRules.getProductPrice(BRAND_ID, null, PRODUCT_ID, productPriceList); },
				IllegalArgumentException.class);
		assertThat(textException).hasMessage("El parámetro applicationDate es obligatorio.");
		
		logger.info("Lanzando prueba sin el PRODUCT_ID");
		textException = catchThrowableOfType(() -> { 
			BusinessRules.getProductPrice(BRAND_ID, DATE_APPLICATION_PRODUCT_EXISTENT, null, productPriceList); },
				IllegalArgumentException.class);
		assertThat(textException).hasMessage("El parámetro productId es obligatorio.");
		
		logger.info("Lanzando prueba sin la lista de precios");
		textException = catchThrowableOfType(() -> { 
			BusinessRules.getProductPrice(BRAND_ID, DATE_APPLICATION_PRODUCT_EXISTENT, PRODUCT_ID, null); },
				IllegalArgumentException.class);
		assertThat(textException).hasMessage("La lista de precios del producto es obligatoria.");
		
		logger.info("Lanzando prueba con la lista de precios vacía");
		PriceException textPriceException = catchThrowableOfType(() -> { 
				BusinessRules.getProductPrice(BRAND_ID, DATE_APPLICATION_PRODUCT_EXISTENT, PRODUCT_ID, new ArrayList<>()); },
				PriceException.class);
		assertThat(textPriceException).hasMessage("No existen precios definidos para el brandId y productId indicados.");
		
		logger.info("##### End requeryParamWhitOutTest #####");
	}
}
