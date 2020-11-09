package com.mx.axeleratum.americantower.contract.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.axeleratum.americantower.contract.core.model.FactorRentaEquipo;
import com.mx.axeleratum.americantower.contract.core.model.FactorRentaEquipoType;

public interface FactorRentaEquipoRepository extends MongoRepository<FactorRentaEquipo, String> {
	
	public FactorRentaEquipo findTopByTypeOrderByCreatedDateDesc(FactorRentaEquipoType type);
}
