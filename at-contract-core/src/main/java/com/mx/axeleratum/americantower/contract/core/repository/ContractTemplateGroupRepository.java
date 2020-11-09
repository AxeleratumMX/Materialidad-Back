package com.mx.axeleratum.americantower.contract.core.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.axeleratum.americantower.contract.core.model.ContractTemplateGroup;

public interface ContractTemplateGroupRepository extends MongoRepository<ContractTemplateGroup,String> {
    Optional<ContractTemplateGroup> findByName(String name);
}
