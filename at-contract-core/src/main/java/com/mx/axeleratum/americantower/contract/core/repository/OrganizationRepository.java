package com.mx.axeleratum.americantower.contract.core.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.axeleratum.americantower.contract.core.model.Organization;

public interface OrganizationRepository extends MongoRepository<Organization,String> {

    public void deleteById(String id);

    public Organization findByName(String name);

	public List<Organization> findByActivo(Boolean activo);
    
}
