package com.mx.axeleratum.americantower.contract.alert.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mx.axeleratum.americantower.contract.alert.model.Alert;
import com.mx.axeleratum.americantower.contract.alert.repository.AlertRepository;
import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlertService {

	@Autowired
	AlertRepository alertRepository;

	public Alert findById(String id) {
		Optional<Alert> opNotification = alertRepository.findById(id);
		if (opNotification.isPresent()) {
			return opNotification.get();
		} else {
			throw new BussinessServiceException("Alert no encontrada");
		}
	}

	public List<Alert> findAll() {
		return alertRepository.findAll();
	}

	public Alert addAlert(Alert notification) {
		notification = alertRepository.save(notification);
		log.info("Notification created " + notification);
		return notification;
	}

	public Page<Alert> findAll(Pageable pageable) {
		return alertRepository.findAll(pageable);
	}

	public void deleteById(String id) {
		alertRepository.deleteById(id);
	}

	public void deleteAll() {
		alertRepository.deleteAll();
	}

	public Page<Alert> addAllAlerts(List<Alert> alerts) {
		alerts = alertRepository.saveAll(alerts);
		log.info("Notification created " + alerts);
		Page<Alert> page = new PageImpl<>(alerts);
		return page;
	}
}
