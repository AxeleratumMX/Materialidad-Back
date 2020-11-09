package com.mx.axeleratum.americantower.contract.core.repository;

import com.mx.axeleratum.americantower.contract.core.model.Passthru;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface PassthroughRepository extends MongoRepository<Passthru, String> {
	
	public List<Passthru> findByIdActivo(Long idActivo);
	
}
