package com.mx.axeleratum.americantower.contract.notification.service;

import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.Notification;
import com.mx.axeleratum.americantower.contract.core.repository.ClientRepository;
import com.mx.axeleratum.americantower.contract.core.repository.NotificationRepository;
import com.mx.axeleratum.americantower.contract.core.repository.UserRepository;
import com.mx.axeleratum.americantower.contract.notification.mapper.NotificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NotificationService {


    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    DynamicContractInterfaceFeign dynamicContractInterfaceFeign;

    @Autowired
    NotificationMapper notificationMapper;


    public Notification findById(String id) {
        Optional<Notification> opNotification = notificationRepository.findById(id);
        if(opNotification.isPresent()) {
           return opNotification.get(); 	
        }else{
           throw new BussinessServiceException("Notificacion no encontrada");
        }
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification addNotification(Notification notification) {
        notification = notificationRepository.save(notification);
        log.info("Notification created " + notification);
        return notification;
    }
    

	public List<Notification> findByReceiverUserName(String userName) {
        return  notificationRepository.findByReceiverUsernameAndStatusOrderByCreatedDateDesc(userName,Notification.STATUS_CREATED);
    }



	public Page<Notification> findAll(Pageable pageable) {
		return notificationRepository.findAll(pageable);
	}
	
	public void deleteById(String id) {
		notificationRepository.deleteById(id);
	}
	
	public void deleteAll() {
		notificationRepository.deleteAll();
	}
}
