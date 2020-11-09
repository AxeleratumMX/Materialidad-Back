package com.mx.axeleratum.americantower.contract.core.repository;

import com.mx.axeleratum.americantower.contract.core.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification,String> {

    public void deleteById(String id);

    @Query(sort = "")
    public List<Notification> findByReceiverUsernameAndStatusOrderByCreatedDateDesc(String username, String status);


}
