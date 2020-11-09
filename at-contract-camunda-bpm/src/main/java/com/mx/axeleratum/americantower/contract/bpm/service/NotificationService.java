package com.mx.axeleratum.americantower.contract.bpm.service;

import com.mx.axeleratum.americantower.contract.core.model.Notification;
import com.mx.axeleratum.americantower.contract.core.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public void sendNotification(String receivedUsername,String comment, DelegateExecution execution) {
        Notification notification = new Notification();
        notification.setContractId(execution.getProcessBusinessKey());
        notification.setCreatedDate(new Date(System.currentTimeMillis()));
        notification.setProcessInstanceId(execution.getProcessInstanceId());
        notification.setReceiverUsername(receivedUsername);
        notification.setStatus(Notification.STATUS_CREATED);
        notification.setComments(comment);
        notificationRepository.save(notification);
    }
}
