package com.mx.axeleratum.americantower.contract.historical.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.axeleratum.americantower.contract.historical.model.History;

public interface HistoricalRepository extends MongoRepository<History,String> {

}
