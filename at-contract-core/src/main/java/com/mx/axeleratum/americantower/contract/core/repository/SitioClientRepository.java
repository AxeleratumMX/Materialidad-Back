package com.mx.axeleratum.americantower.contract.core.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.axeleratum.americantower.contract.core.model.SitioClient;

public interface SitioClientRepository extends MongoRepository<SitioClient, String> {
	List<SitioClient> findByIdActivo(Long idActivo);
	List<SitioClient> findByNameClient(String name);
	List<SitioClient> findByIdClient(String id);
}
