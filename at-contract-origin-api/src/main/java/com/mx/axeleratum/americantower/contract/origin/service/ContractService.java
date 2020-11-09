package com.mx.axeleratum.americantower.contract.origin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.ContractLand;
import com.mx.axeleratum.americantower.contract.core.model.ContractTower;
import com.mx.axeleratum.americantower.contract.origin.repository.ContractLandRepository;
import com.mx.axeleratum.americantower.contract.origin.repository.ContractTowerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContractService {

	@Autowired
	ContractLandRepository contratLandRepository;

	@Autowired
	ContractTowerRepository contractTowerRepository;

	public ContractLand findContractLandById(String id) {
		Optional<ContractLand> opContrat = contratLandRepository.findById(id);
		if (opContrat.isPresent()) {
			return opContrat.get();
		} else {
			throw new BussinessServiceException("Contract land no found");
		}
	}

	public Page<ContractLand> findAllContractLands(Pageable pageable) {
		return contratLandRepository.findAll(pageable);
	}
	
	public List<ContractLand> findAllContractLands() {
		return contratLandRepository.findAll();
	}

	public ContractLand addContractLand(ContractLand contratLand) {
		contratLand = contratLandRepository.save(contratLand);
		log.info("Notification created " + contratLand);
		return contratLand;
	}	
	
	public Page<ContractLand> addAllContractLand(List<ContractLand> contratLand) {
		contratLand = contratLandRepository.saveAll(contratLand);
		log.info("Contrat tower created  " + contratLand);
		Page<ContractLand> page = new PageImpl<>(contratLand);
		return page;
	}
	
	public void deleteContractLandByID(String id) {
		contratLandRepository.deleteById(id);
	}
	
	public void deleteAllContractLand() {
		contratLandRepository.deleteAll();
	}
	

	// Contract Tower

	public ContractTower findContractTowerById(String id) {
		Optional<ContractTower> opNotification = contractTowerRepository.findById(id);
		if (opNotification.isPresent()) {
			return opNotification.get();
		} else {
			throw new BussinessServiceException("Contrat Tower o found");
		}
	}

	public Page<ContractTower> findAllContractTowers(Pageable pageable) {
		return contractTowerRepository.findAll(pageable);
	}

	public ContractTower addContractTower(ContractTower contratTower) {
		contratTower = contractTowerRepository.save(contratTower);
		log.info("Contrat tower created " + contratTower);
		return contratTower;
	}

	public Page<ContractTower> addAllContractTower(List<ContractTower> contratsTower) {
		contratsTower = contractTowerRepository.saveAll(contratsTower);
		log.info("Contrat tower created  " + contratsTower);
		Page<ContractTower> page = new PageImpl<>(contratsTower);
		return page;
	}

	public void deleteContratTowerById(String id) {
		contractTowerRepository.deleteById(id);
	}

	public void deleteAllContratTower() {
		contractTowerRepository.deleteAll();
	}

	public List<ContractTower> findAllContratTower() {		
		return contractTowerRepository.findAll();
	}

}
