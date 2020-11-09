package com.mx.axeleratum.americantower.contract.dynamicInterface.repository;

import com.mx.axeleratum.americantower.contract.core.model.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository extends MongoRepository<Template,String> {
	public List<Template> findByClientId(String id);

	@Query("{ 'nombre' : { '$regex' : ?0 , $options: 'i'}}")
	public List<Template> findAllByNombreLike(String search);

	Optional<Template> findByName(String name);
}
