package com.mx.axeleratum.americantower.contract.core.repository;

import com.mx.axeleratum.americantower.contract.core.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
	@Query("{ 'nombre' : { '$regex' : ?0 , $options: 'i'}}")
	List<Client> findAllByNombreLike(String search);

	public Optional<Client> findByIdCliente(String idCliente);
}
