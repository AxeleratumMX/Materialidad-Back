package com.mx.axeleratum.americantower.contract.core.repository;

import com.mx.axeleratum.americantower.contract.core.model.Domain;
import com.mx.axeleratum.americantower.contract.core.model.Domains;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DomainRepository extends MongoRepository<Domain,String> {

    public Domain findByKey(Domains domain);

}
