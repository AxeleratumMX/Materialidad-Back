package com.mx.axeleratum.americantower.contract.core.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.axeleratum.americantower.contract.core.model.UserPhoto;

public interface PhotoRepository extends MongoRepository<UserPhoto,String> {
    
}
