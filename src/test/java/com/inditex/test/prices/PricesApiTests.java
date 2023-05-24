package com.inditex.test.prices;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.inditex.test.prices.infrastructure.config.spring.SpringBootService;
import com.inditex.test.prices.infrastructure.inputadapter.rest.dto.ResponseProductPriceDto;

/**
 * Pruebas de integración sobre el API Rest
 * 
 * @author fmallado
 * @since 1.0.0
 */

@DisplayName("Pruebas de integración sobre el API Rest")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes= SpringBootService.class)
class PricesApiTests {
	
	/**
	 * logger
	 */
    private static final Logger logger = LogManager.getLogger(PricesApiTests.class);

    /**
	 * Cliente web test utilizado para realizar las llamadas al API Rest.
	 */
    @Autowired
	private WebTestClient client;
    
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
	
	/**
     * Fecha de aplicación de una tarifa para el producto PRODUCT_ID mal formada.
     */
	private static final String DATE_APPLICATION_PRODUCT_MALFORMED = "20000-01-01T00:00:00+01:00";
	
	/**
	 * Path del servicio del API sobre el que se realiza la prueba.
	 */
	private static final String PRODUCT_PRICE_API_PATH = "/api/price/v0/product-price"; 

    
	@ParameterizedTest(name = "Petición el {1} para el producto {2} y la brand {0}")
	@CsvFileSource(resources = {"/prices.csv"}, delimiter = ';', numLinesToSkip = 1)
	@DisplayName("Pruebas con los casos de uso indicados en la definidión del ejercicio de Inditex.")
	void productPriceTest(Integer brandId, OffsetDateTime applicationDate, Integer productId, Integer priceList, BigDecimal price, String curr, OffsetDateTime startDate, OffsetDateTime endDate) {
		logger.info("##### Start productPriceTest #####");
		logger.info("Lanzando la llamada al método del API %s con los valores: brandId=%s, applicationDate=%s, productId=%s".formatted(PRODUCT_PRICE_API_PATH, brandId, applicationDate, productId));
		client.get().uri(uriBuilder -> uriBuilder
			    .path(PRODUCT_PRICE_API_PATH)
			    .queryParam("brandId", "{brandId}")
			    .queryParam("applicationDate", "{applicationDate}")
			    .queryParam("productId", "{productId}")
			    .build(brandId, applicationDate, productId)).exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody(ResponseProductPriceDto.class)
        .consumeWith(response -> {
        	ResponseProductPriceDto applicationPrice = response.getResponseBody();
        	logger.info("Respuesta -> %s".formatted(response));
        	assertNotNull(applicationPrice, () -> "No se ha encontrado el precio del producto para la fecha de aplicación indicada.");
    		assertEquals(applicationPrice.getBrandId(), brandId,  () -> "La cadena del grupo del precio devuelto (%s) no es la misma que la cadena solicitada (%s).".formatted(applicationPrice.getBrandId(), brandId)); 
    		assertEquals(applicationPrice.getProductId(), productId,  () -> "El identificador del producto devuelto (%s) no es el mismo que el identificador del producto solicitado (%s).".formatted(applicationPrice.getProductId(), productId));
    		assertEquals(applicationPrice.getPriceList(), priceList,  () -> "El identificador de tarifa devuelto (%s) no corresponde con el esperado (%s).".formatted(priceList, applicationPrice.getPriceList()));
    		assertEquals(applicationPrice.getPvp(), price,  () -> "El precio del producto devuelto (%s) no corresponde con el precio esperado (%s).".formatted(price, applicationPrice.getPvp()));
    		assertEquals(applicationPrice.getCurr(), curr,  () -> "La moneda del precio devuelta (%s) no corresponde con la esperada (%s).".formatted(curr, applicationPrice.getCurr()));
        });
		
		logger.info("##### End productPriceTest #####");
	}

	@Test
	@DisplayName("Prueba con una fecha de aplicación en la que no existe el producto.")
	void productPriceBeforeDateDisponibilityTest() {
		logger.info("##### Start productPriceBeforeDateDisponibilityTest #####");
		
		client.get().uri(uriBuilder -> uriBuilder
			    .path(PRODUCT_PRICE_API_PATH)
			    .queryParam("brandId", "{brandId}")
			    .queryParam("applicationDate", "{applicationDate}")
			    .queryParam("productId", "{productId}")
			    .build(BRAND_ID, DATE_APPLICATION_PRODUCT_NON_EXISTENT, PRODUCT_ID)).exchange()
        		.expectStatus().is4xxClientError()
        		.expectBody(String.class).consumeWith(result -> {
        			logger.info("Respuesta -> %s".formatted(result));
        			assertThat(result.getResponseBody()).contains("No existen precios definidos para la fecha de aplicación indicada.");
        		});
		
		logger.info("##### End productPriceBeforeDateDisponibilityTest #####");
	}
	

	@Test
	@DisplayName("Prueba con un producto que no existe.")
	void productNotExistTest() {
		logger.info("##### Start productNotExistTest #####");

		client.get().uri(uriBuilder -> uriBuilder
			    .path(PRODUCT_PRICE_API_PATH)
			    .queryParam("brandId", "{brandId}")
			    .queryParam("applicationDate", "{applicationDate}")
			    .queryParam("productId", "{productId}")
			    .build(BRAND_ID, DATE_APPLICATION_PRODUCT_EXISTENT, PRODUCT_ID_NON_EXISTENT)).exchange()
        		.expectStatus().is4xxClientError()
        		.expectBody(String.class).consumeWith(result -> {
        			logger.info("Respuesta -> %s".formatted(result));
        			assertThat(result.getResponseBody()).contains("No existen precios definidos para el brandId y productId indicados.");
        		});
		logger.info("##### End productNotExistTest #####");
	}
	
	@Test
	@DisplayName("Prueba con una cadena del grupo que no existe.")
	void brandNotExistTest() {
		logger.info("##### Start brandNotExistTest #####");
				
		client.get().uri(uriBuilder -> uriBuilder
			    .path(PRODUCT_PRICE_API_PATH)
			    .queryParam("brandId", "{brandId}")
			    .queryParam("applicationDate", "{applicationDate}")
			    .queryParam("productId", "{productId}")
			    .build(BRAND_ID_NON_EXISTEN, DATE_APPLICATION_PRODUCT_EXISTENT, PRODUCT_ID)).exchange()
        		.expectStatus().is4xxClientError()
        		.expectBody(String.class).consumeWith(result -> {
        			logger.info("Respuesta -> %s".formatted(result));
        			assertThat(result.getResponseBody()).contains("No existen precios definidos para el brandId y productId indicados.");
          });
		
		logger.info("##### End brandNotExistTest #####");
	}

	@Test
	@DisplayName("Prueba con los campos obligatorios a nulos.")
	void requeryParamWhitOutTest() {
		logger.info("##### Start requeryParamWhitOutTest #####");
				
		client.get().uri(uriBuilder -> uriBuilder
			    .path(PRODUCT_PRICE_API_PATH)
			    .queryParam("applicationDate", "{applicationDate}")
			    .queryParam("productId", "{productId}")
			    .build(DATE_APPLICATION_PRODUCT_EXISTENT, PRODUCT_ID)).exchange()
        		.expectStatus().is4xxClientError()
        		.expectBody(String.class).consumeWith(result -> {
        			logger.info("Respuesta -> %s".formatted(result));
        			assertThat(result.getResponseBody()).contains("Bad Request");
          });
		
		client.get().uri(uriBuilder -> uriBuilder
			    .path(PRODUCT_PRICE_API_PATH)
			    .queryParam("brandId", "{brandId}")
			    .queryParam("productId", "{productId}")
			    .build(BRAND_ID, PRODUCT_ID)).exchange()
        		.expectStatus().is4xxClientError()
        		.expectBody(String.class).consumeWith(result -> {
        			logger.info("Respuesta -> %s".formatted(result));
        			assertThat(result.getResponseBody()).contains("Bad Request");
          });
		
		client.get().uri(uriBuilder -> uriBuilder
			    .path(PRODUCT_PRICE_API_PATH)
			    .queryParam("brandId", "{brandId}")
			    .queryParam("applicationDate", "{applicationDate}")
			    .build(BRAND_ID, DATE_APPLICATION_PRODUCT_EXISTENT)).exchange()
        		.expectStatus().is4xxClientError()
        		.expectBody(String.class).consumeWith(result -> {
        			logger.info("Respuesta -> %s".formatted(result));
        			assertThat(result.getResponseBody()).contains("Bad Request");
          });
		
		logger.info("##### End requeryParamWhitOutTest #####");
	}	
	
	@Test
	@DisplayName("Prueba con una fecha que tiene un formato erroneo.")
	void dateTimeFormatErrorTest() {
		logger.info("##### Start dateTimeFormatErrorTest #####");
		client.get().uri(uriBuilder -> uriBuilder
			    .path(PRODUCT_PRICE_API_PATH)
			    .queryParam("brandId", "{brandId}")
			    .queryParam("applicationDate", "{applicationDate}")
			    .queryParam("productId", "{productId}")
			    .build(BRAND_ID, DATE_APPLICATION_PRODUCT_MALFORMED, PRODUCT_ID)).exchange()
        		.expectStatus().is4xxClientError()
        		.expectBody(String.class).consumeWith(result -> {
        			logger.info("Respuesta -> %s".formatted(result));
        			assertThat(result.getResponseBody()).contains("Bad Request");
          });
		logger.info("##### End dateTimeFormatErrorTest #####");
	}
}