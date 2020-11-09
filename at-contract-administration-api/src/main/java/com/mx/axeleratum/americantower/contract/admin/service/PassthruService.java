package com.mx.axeleratum.americantower.contract.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.Passthru;
import com.mx.axeleratum.americantower.contract.core.repository.PassthroughRepository;

@Service
public class PassthruService {

	@Autowired
	private PassthroughRepository passthruRepository;

	public List<Passthru> findAll() {
		return passthruRepository.findAll();
	}

	public Passthru findById(String id) {
		Optional<Passthru> oPassthru = passthruRepository.findById(id);
		if (oPassthru.isPresent()) {
			return oPassthru.get();
		} else {
			throw new BussinessServiceException("No se encontro el value " + id);
		}
	}
	
	public List<Passthru> findByIdActivo(Long id) {
		return passthruRepository.findByIdActivo(id);		
	}

	public Passthru save(Passthru passthru) {
		return passthruRepository.save(passthru);
	}

	public Passthru insert(Passthru passthru) {
		return passthruRepository.insert(passthru);
	}

	public void deleteAll() {
		passthruRepository.deleteAll();
	}

}
