package com.inditex.test.prices.infrastructure.config.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Clase de arranque de SpringBoot.
 * 
 * @author fmallado
 * @since 1.0.0
 *
 */
@SpringBootApplication(scanBasePackages = "com.inditex.test.prices")
@EntityScan(basePackages = "com.inditex.test.prices")
public class SpringBootService {

	/**
	 * Método principal mediante el cual se arranca Spring Boot.
	 * 
	 * @param args Argumentos de entrada.
	 */
  public static void main(String[] args) {
    SpringApplication.run(SpringBootService.class, args);
  }

}