package com.mx.axeleratum.americantower.contract.dynamicInterfaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.core.model.Passthru;
import com.mx.axeleratum.americantower.contract.core.repository.PassthroughRepository;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.dynamicInterface.DynamicInterfaceApplication.class)
@Slf4j
public class PassthrueTest extends ValidationTest {

	@Autowired
	PassthroughRepository passthroughRepository; 
	
	@Test
	public void verDatosDecimales() {
		List<Passthru> list = passthroughRepository.findAll();
		for(Passthru p:list) {
			System.out.println(p);
		}
	}	
}
