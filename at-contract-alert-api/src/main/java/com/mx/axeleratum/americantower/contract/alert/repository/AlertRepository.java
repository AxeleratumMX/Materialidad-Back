package com.mx.axeleratum.americantower.contract.alert.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.axeleratum.americantower.contract.alert.model.Alert;

public interface AlertRepository extends MongoRepository<Alert,String> {
}
