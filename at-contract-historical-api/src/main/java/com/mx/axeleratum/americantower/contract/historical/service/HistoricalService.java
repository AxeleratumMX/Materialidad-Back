package com.mx.axeleratum.americantower.contract.historical.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.historical.model.History;
import com.mx.axeleratum.americantower.contract.historical.repository.HistoricalRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HistoricalService {

    @Autowired
    HistoricalRepository historicalRepository;

    public History findById(String id) {
    	Optional<History> opNotification = historicalRepository.findById(id);
		if (opNotification.isPresent()) {
			return opNotification.get();
		} else {
			throw new BussinessServiceException("history not found");
		}
    }

    public Page<History> findAll(Pageable pageable) {
        return historicalRepository.findAll(pageable);
    }

    public History addHistory(History history) {
    	history = historicalRepository.save(history);
        log.info("Notification created " + history);
        return history;
    }

	public List<History> findAll() {
		 return historicalRepository.findAll();
	}

	public Page<History> addHistories(List<History> histories) {
		histories = historicalRepository.saveAll(histories);
        log.info("histories created " + histories);
        Page<History> page = new PageImpl<>(histories);
        return page;
	}

	public void deleteById(String id) {
		historicalRepository.deleteById(id);
	}
	
	public void deleteAll() {
		historicalRepository.deleteAll();
	}
}
