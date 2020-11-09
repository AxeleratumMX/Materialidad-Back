package com.mx.axeleratum.americantower.contract.origin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.axeleratum.americantower.contract.core.model.ContractLand;

public interface ContractLandRepository extends MongoRepository<ContractLand,String> {
}
