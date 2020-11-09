package com.mx.axeleratum.americantower.contract.dynamicInterfaces;

import com.mx.axeleratum.americantower.contract.core.dto.CamundaProcessInstanceRequestDto;
import com.mx.axeleratum.americantower.contract.core.dto.CamundaProcessInstanceResponseDto;
import com.mx.axeleratum.americantower.contract.core.dto.CamundaVariableDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.CamundaBpmService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.dynamicInterface.DynamicInterfaceApplication.class)
@Slf4j
public class CamundaBpmTest {

	@Autowired
	CamundaBpmService camundaBpmService;

	@Test
	public void testPass() {
		log.info("pass");
	}


	@Test
	public void  createProcessInstance() {
		Map<String, Object> values = new HashMap<String,Object>();
		values.put("asssignCreateContractUser" ,new CamundaVariableDto("userAbogadoCreador"));

		CamundaProcessInstanceRequestDto camundaTaskRequestDto = new CamundaProcessInstanceRequestDto();
		camundaTaskRequestDto.setVariables(values);
		camundaTaskRequestDto.setBusinessKey("123456-gaby");
		camundaTaskRequestDto.setWithVariablesInReturn(true);
		CamundaProcessInstanceResponseDto responseDto = camundaBpmService.startProcessInstance(camundaTaskRequestDto);

		log.info("sali " + responseDto);


	}
}
