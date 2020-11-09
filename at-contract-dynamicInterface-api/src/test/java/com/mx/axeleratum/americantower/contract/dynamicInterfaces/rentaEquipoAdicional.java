package com.mx.axeleratum.americantower.contract.dynamicInterfaces;

import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.core.model.FactorRentaEquipoType;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.EquipmentDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.SectionDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.dynamicInterface.DynamicInterfaceApplication.class)
@Slf4j
public class rentaEquipoAdicional extends ValidationTest {

	/**
	 * Agregado una lista de equipos
	 */
	@Test
	public void rentaEquipoAndRentaEquipoAdicional() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		//master.deleteAllTemplates();
		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);
		String currentId = "rentaEquipo";
		String valueIdActivo = "85663";
		
		//Seteo el id de activo
		contractDto.getTemplateInstance().setIdActivo(Integer.valueOf(valueIdActivo));		
		
		SectionDto section = SectionDto.findParamById(contractDto.getTemplateInstance().getSections(),"equipos");
		List<EquipmentDto> list = new ArrayList<EquipmentDto>();
		EquipmentDto equipo1 = new EquipmentDto();
		equipo1.setMarca("Formula MW");
		equipo1.setDiametro(35);
		equipo1.setAltura(598);
		equipo1.setTipoAntena(FactorRentaEquipoType.MW.getValue());
		
		EquipmentDto equipo2 = new EquipmentDto();
		equipo2.setMarca("Formula MW4");
		equipo2.setDiametro(35);
		equipo2.setAltura(598);
		equipo2.setTipoAntena(FactorRentaEquipoType.MW4.getValue());
		
		EquipmentDto equipo3 = new EquipmentDto();
		equipo3.setMarca("Formula RF");
		equipo3.setTipoAntena(FactorRentaEquipoType.RF.getValue());

		EquipmentDto equipo4 = new EquipmentDto();
		equipo4.setMarca("Formula RRU");
		equipo4.setTipoAntena(FactorRentaEquipoType.RRU.getValue());

		list.add(equipo1);
		list.add(equipo2);
		list.add(equipo3);
		list.add(equipo4);
		
		section.setContent(list);
		
		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();
		
		ContractValueDto currenValue = ContractValueDto.findById(dtoValues, currentId);
		check(currentId,currenValue.getContent(), currenValue);
		assertThat(currenValue.getOperation().getName()).isEqualTo("rentaEquipo");

		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(currenValue.getOperation().getName());
		
		ContractValueDto result = ContractValueDto.findById(dtoValues,currentId);
		check(currentId, result.getContent(), result);		
		
		section = SectionDto.findParamById(contractDto.getTemplateInstance().getSections(),"equipos");
		list = section.getContent();
		
		for(EquipmentDto equipo :list) {
			log.info("Equipo: "+equipo.getMarca() + " Renta:"+equipo.getRentaEquipo());
		}
	}

}	
