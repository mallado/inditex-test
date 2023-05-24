package com.inditex.test.prices.infrastructure.outputadapter.db.springdata.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase de configuración de String para el repositorio de salida (BBDD).
 * 
 * @author fmallado
 * @since 1.0.0
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.inditex.test.prices.infrastructure.outputadapter.db.springdata.repository")
@ConfigurationProperties("spring.datasource")
@NoArgsConstructor
@Getter
@Setter
@EnableJpaAuditing
@EntityScan(basePackages = "com.inditex.test.prices.infrastructure.outputadapter.db.springdata.dbo")
public class SpringDataConfig {

}
