package com.mx.axeleratum.americantower.contract.core.repository;

import com.mx.axeleratum.americantower.contract.core.model.DomainValue;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface DomainValueRepository extends MongoRepository<DomainValue,String> {
    public Optional<DomainValue> findByDomainIdAndKey(String domainId, String key);

    public List<DomainValue> findByDomainIdAndSubDomainValue(String domainId, String subDomainkey);

    public Optional<DomainValue> findByDomainIdAndSubDomainValueAndKey(String domainId, String subDomainkey, String key);


}
