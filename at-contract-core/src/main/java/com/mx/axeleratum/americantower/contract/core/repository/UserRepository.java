package com.mx.axeleratum.americantower.contract.core.repository;


import com.mx.axeleratum.americantower.contract.core.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

    public void deleteById(String id);

    public User findByUsername(String userName);
    
}
