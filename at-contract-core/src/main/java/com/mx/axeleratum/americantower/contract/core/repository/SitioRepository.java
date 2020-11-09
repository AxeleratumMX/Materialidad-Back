package com.mx.axeleratum.americantower.contract.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.axeleratum.americantower.contract.core.model.Sitio;


public interface SitioRepository extends MongoRepository<Sitio, String> {
	public Optional<Sitio> findByIdActivo(Long idActivo);
	List<Sitio> findByIdActivoIn(List<Long> idActivos);
}
