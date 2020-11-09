package com.mx.axeleratum.americantower.contract.core.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.axeleratum.americantower.contract.core.model.Rol;

public interface RolRepository extends MongoRepository<Rol,String> {
      public Rol findByName(String name);
}
