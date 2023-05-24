package com.inditex.test.prices.infrastructure.outputadapter.db.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inditex.test.prices.infrastructure.outputadapter.db.springdata.dbo.PriceEntity;

/**
 * Respositorio de Spring usado para realizar las consultas con la BBDD.
 * 
 * @author fmallado
 * @since 1.0.0
 */
@Repository
public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Integer> 
{

}
