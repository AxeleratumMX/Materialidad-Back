package com.mx.axeleratum.americantower.contract.admin.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.axeleratum.americantower.contract.core.model.Sitio;
import com.mx.axeleratum.americantower.contract.core.model.SitioClient;
import com.mx.axeleratum.americantower.contract.core.repository.SitioRepository;
import com.mx.axeleratum.americantower.contract.core.repository.SitioClientRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SitioService {
	
	@Autowired
	SitioRepository sitioRepository;
	
	@Autowired 
	SitioClientRepository sitioClientRepository;
	
	public Sitio findIdActivo(Long idActivo) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		 Optional<Sitio> oSitio =  sitioRepository.findByIdActivo(idActivo);
		 if(oSitio.isPresent()) {
			 return oSitio.get();
		 }
		 return null;
	}
	
	public Sitio save(Sitio sitio) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return sitioRepository.save(sitio);
	}
	
	public List<Sitio> findAll() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Sitio> sitios = sitioRepository.findAll();
		return sitios;
	}

	public Object findById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<Sitio> oSitio = sitioRepository.findById(id);
		 if(oSitio.isPresent()) {
			 return oSitio.get();
		 }
		 return null;
	}

	public Sitio insert(Sitio sitio) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return sitioRepository.insert(sitio);
	}

	public void deleteAll() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		sitioRepository.deleteAll();
	}
	
	private List<Long> extractIdActivoFrom(List<SitioClient> relacion){
		Set<Long> relaciones = new HashSet<Long>();
		for(int i=0,n=relacion.size();i<n;i++) {
			relaciones.add(relacion.get(i).getIdActivo()); 
		}
		List<Long> list = new ArrayList<>();
		list.addAll(relaciones);
		return list;
	}

	public List<Sitio> findByClientName(String name) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<SitioClient> relacion = sitioClientRepository.findByNameClient(name);
		List<Long> list = extractIdActivoFrom(relacion);
		List<Sitio> sitio = sitioRepository.findByIdActivoIn(list);
		return sitio;
	}
	
	public List<Sitio> findByClientId(String idClient) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<SitioClient> relacion = sitioClientRepository.findByIdClient(idClient);
		List<Long> list = extractIdActivoFrom(relacion);
		List<Sitio> sitio = sitioRepository.findByIdActivoIn(list);
		return sitio;
	}
}
