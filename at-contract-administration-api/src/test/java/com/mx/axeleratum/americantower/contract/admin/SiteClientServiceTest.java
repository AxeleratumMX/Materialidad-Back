package com.mx.axeleratum.americantower.contract.admin;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mx.axeleratum.americantower.contract.admin.service.SitioService;
import com.mx.axeleratum.americantower.contract.core.model.Sitio;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdministrationApplication.class)
@Slf4j
public class SiteClientServiceTest {

	@Autowired
	SitioService sitioService;

	@Test
	public void findfindByNameClient() {
		List<Sitio> sitios = sitioService.findByClientName("ATyT COMUNICACIONES DIGITALES S DE RL DE CV");
		log.info("Sitios find: ");
		for (int i = 0, n = sitios.size(); i < n; i++) {
			System.out.println(sitios.get(i).getIdActivo());
		}
		sitios = sitioService.findByClientName("Pegaso PCS SA de CV");
		log.info("Sitios find: ");
		for (int i = 0, n = sitios.size(); i < n; i++) {
			System.out.println(sitios.get(i).getIdActivo());
		}
	}

	@Test
	public void findfindByIdClient() {
		List<Sitio> sitios = sitioService.findByClientId("12345");
		log.info("Sitios find: ");
		for (int i = 0, n = sitios.size(); i < n; i++) {
			System.out.println(sitios.get(i).getIdActivo());
		}
		sitios = sitioService.findByClientName("11111");
		log.info("Sitios find: ");
		for (int i = 0, n = sitios.size(); i < n; i++) {
			System.out.println(sitios.get(i).getIdActivo());
		}
	}

}