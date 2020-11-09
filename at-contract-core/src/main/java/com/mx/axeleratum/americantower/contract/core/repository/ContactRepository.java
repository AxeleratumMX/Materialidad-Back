package com.mx.axeleratum.americantower.contract.core.repository;

import com.mx.axeleratum.americantower.contract.core.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContactRepository extends MongoRepository<Contact,String> {

    public List<Contact> findByName(String lastName);

    public Contact findByUserId(String userId);
}
