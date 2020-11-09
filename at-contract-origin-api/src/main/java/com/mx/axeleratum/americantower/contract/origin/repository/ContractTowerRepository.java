package com.mx.axeleratum.americantower.contract.origin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.axeleratum.americantower.contract.core.model.ContractTower;

//import com.mx.axeleratum.americantower.contract.origin.model.ContractTower;

public interface ContractTowerRepository extends MongoRepository<ContractTower, String> {

}
