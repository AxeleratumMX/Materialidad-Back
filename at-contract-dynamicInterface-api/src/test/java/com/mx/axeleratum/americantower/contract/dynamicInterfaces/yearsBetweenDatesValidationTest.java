package com.mx.axeleratum.americantower.contract.dynamicInterfaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.dynamicInterface.DynamicInterfaceApplication.class)
@Slf4j
public class yearsBetweenDatesValidationTest extends ValidationTest {

	// (19)aniosVigencia = (22)fechaTerminoVigencia - (18)fechaInicioVigencia = años
	// totales en el intervalo
	// yearsBetweenDates fechaInicioVigencia fechaTerminoVigencia
	@Test
	public void calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia() {
		log.info("Ejecutando Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		master.deleteAllTemplates();

		String firtDateId = "fechaTerminoVigencia";
		String secondDateId = "fechaInicioVigencia";
		String responseId = "aniosVigencia";
		String dependencyId = "fechaFirmaContrato";
		String dependencyValueMenor = "2021-08-01";
		String dependencyValueEqual = "2020-05-01";

		ContractTemplate contract = getAnyTowerContract();
		assertThat(contract).isNotNull();
		ContractTemplateDto contractDto = contractMapper.toContratTemplateDto(contract);

		contractDto = calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_BothNull(contractDto,
				firtDateId, secondDateId, responseId);
		contractDto = calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_BothEnty(contractDto,
				firtDateId, secondDateId, responseId);
		contractDto = calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_OkAndEnty(contractDto,
				firtDateId, secondDateId, responseId);
		contractDto = calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_EntyAndOk(contractDto,
				firtDateId, secondDateId, responseId);
		contractDto = calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_EsMenor(contractDto,
				firtDateId, secondDateId, responseId);
		contractDto = calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_EsEqual(contractDto,
				firtDateId, secondDateId, responseId);
		contractDto = calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_BothOkButErrorDependency(
				contractDto, firtDateId, secondDateId, responseId);

		ContractValueDto.setValueById(contractDto.getTemplateInstance().getValues(), dependencyId,
				dependencyValueMenor);
		ContractValueDto dependency = ContractValueDto.findById(contractDto.getTemplateInstance().getValues(),
				dependencyId);
		check(dependencyId, dependencyValueMenor, dependency);
		contractDto = calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_BothOkDependencyMenor(
				contractDto, firtDateId, secondDateId, responseId);

		ContractValueDto.setValueById(contractDto.getTemplateInstance().getValues(), dependencyId,
				dependencyValueEqual);
		dependency = ContractValueDto.findById(contractDto.getTemplateInstance().getValues(), dependencyId);
		check(dependencyId, dependencyValueEqual, dependency);
		contractDto = calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_BothOk(contractDto,
				firtDateId, secondDateId, responseId);
	}

	private ContractTemplateDto calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_BothOk(
			ContractTemplateDto contractDto, String firtDateId, String secondDateId, String responseId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2023-08-01";
		String secondDateValue = "2020-05-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);
		ContractValueDto.setValueById(dtoValues, responseId, "");

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

		ContractValueDto response = ContractValueDto.findById(dtoValues, responseId);
		check(responseId, "", response);
		assertThat(response.getOperation().getName()).isEqualTo("yearsBetweenDates");

		dtoValues = validation.processOperation(contractDto);
//		contractDto = validation.processOperation(contractDto);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("Test Evaluando:" + response.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(0, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
		infoResult(0, secondDateId, secondDateValue, resultSecondDate);

		response = ContractValueDto.findById(dtoValues, responseId);
		infoResult(0, responseId, "3", response);

		return contractDto;
	}

	private ContractTemplateDto calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_BothOkDependencyMenor(
			ContractTemplateDto contractDto, String firtDateId, String secondDateId, String responseId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2021-08-12";
		String secondDateValue = "2020-08-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);
		ContractValueDto.setValueById(dtoValues, responseId, "");

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

		ContractValueDto response = ContractValueDto.findById(dtoValues, responseId);
		check(responseId, "", response);
		assertThat(response.getOperation().getName()).isEqualTo("yearsBetweenDates");

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("Test Evaluando:" + response.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(0, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
		infoResult(1, secondDateId, secondDateValue, resultSecondDate);

		response = ContractValueDto.findById(dtoValues, responseId);
		infoResult(0, responseId, "1", response);

		return contractDto;
	}

	private ContractTemplateDto calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_BothOkButErrorDependency(
			ContractTemplateDto contractDto, String firtDateId, String secondDateId, String responseId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2021-08-01";
		String secondDateValue = "2020-05-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);
		ContractValueDto.setValueById(dtoValues, responseId, "");

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

		ContractValueDto response = ContractValueDto.findById(dtoValues, responseId);
		check(responseId, "", response);
		assertThat(response.getOperation().getName()).isEqualTo("yearsBetweenDates");

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("Test Evaluando:" + response.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(0, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
		infoResult(1, secondDateId, secondDateValue, resultSecondDate);

		response = ContractValueDto.findById(dtoValues, responseId);
		infoResult(0, responseId, "1", response);

		return contractDto;
	}

	private ContractTemplateDto calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_EsEqual(
			ContractTemplateDto contractDto, String firtDateId, String secondDateId, String responseId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2020-08-01";
		String secondDateValue = "2020-08-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);
		ContractValueDto.setValueById(dtoValues, responseId, "");

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

		ContractValueDto response = ContractValueDto.findById(dtoValues, responseId);
		check(responseId, "", response);
		assertThat(response.getOperation().getName()).isEqualTo("yearsBetweenDates");

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("Test Evaluando:" + response.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(1, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
		infoResult(1, secondDateId, secondDateValue, resultSecondDate);

		response = ContractValueDto.findById(dtoValues, responseId);
		infoResult(0, responseId, "0", response);

		return contractDto;
	}

	private ContractTemplateDto calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_EsMenor(
			ContractTemplateDto contractDto, String firtDateId, String secondDateId, String responseId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2020-02-01";
		String secondDateValue = "2020-08-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);
		ContractValueDto.setValueById(dtoValues, responseId, "");

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

		ContractValueDto response = ContractValueDto.findById(dtoValues, responseId);
		check(responseId, "", response);
		assertThat(response.getOperation().getName()).isEqualTo("yearsBetweenDates");

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("Test Evaluando:" + response.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(1, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
		infoResult(1, secondDateId, secondDateValue, resultSecondDate);

		response = ContractValueDto.findById(dtoValues, responseId);
		infoResult(0, responseId, "0", response);

		return contractDto;
	}

	private ContractTemplateDto calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_EntyAndOk(
			ContractTemplateDto contractDto, String firtDateId, String secondDateId, String responseId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "";
		String secondDateValue = "2020-08-01";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);
		ContractValueDto.setValueById(dtoValues, responseId, "");

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

		ContractValueDto response = ContractValueDto.findById(dtoValues, responseId);
		check(responseId, "", response);
		assertThat(response.getOperation().getName()).isEqualTo("yearsBetweenDates");

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("Test Evaluando:" + response.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(1, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
		infoResult(1, secondDateId, secondDateValue, resultSecondDate);

		response = ContractValueDto.findById(dtoValues, responseId);
		infoResult(1, responseId, "", response);

		return contractDto;
	}

	private ContractTemplateDto calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_OkAndEnty(
			ContractTemplateDto contractDto, String firtDateId, String secondDateId, String responseId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "2020-08-01";
		String secondDateValue = "";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);
		ContractValueDto.setValueById(dtoValues, responseId, "");

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

		ContractValueDto response = ContractValueDto.findById(dtoValues, responseId);
		check(responseId, "", response);
		assertThat(response.getOperation().getName()).isEqualTo("yearsBetweenDates");

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("Test Evaluando:" + response.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(1, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
		infoResult(2, secondDateId, secondDateValue, resultSecondDate);

		response = ContractValueDto.findById(dtoValues, responseId);
		infoResult(1, responseId, "", response);

		return contractDto;
	}

	private ContractTemplateDto calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_BothNull(
			ContractTemplateDto contractDto, String firtDateId, String secondDateId, String responseId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = null;
		String secondDateValue = null;

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);
		ContractValueDto.setValueById(dtoValues, responseId, "");

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

		ContractValueDto response = ContractValueDto.findById(dtoValues, responseId);
		check(responseId, "", response);
		assertThat(response.getOperation().getName()).isEqualTo("yearsBetweenDates");

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("Test Evaluando:" + response.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(2, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
		infoResult(2, secondDateId, secondDateValue, resultSecondDate);

		response = ContractValueDto.findById(dtoValues, responseId);
		infoResult(2, responseId, "", response);

		return contractDto;
	}

	private ContractTemplateDto calAniosVigenciaByYearsBetweenDatesFechaInicioVigenciaFechaTerminoVigencia_BothEnty(
			ContractTemplateDto contractDto, String firtDateId, String secondDateId, String responseId) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());

		String firtDateValue = "";
		String secondDateValue = "";

		List<ContractValueDto> dtoValues = contractDto.getTemplateInstance().getValues();

		ContractValueDto.setValueById(dtoValues, firtDateId, firtDateValue);
		ContractValueDto.setValueById(dtoValues, secondDateId, secondDateValue);
		ContractValueDto.setValueById(dtoValues, responseId, "");

		ContractValueDto firtDate = ContractValueDto.findById(dtoValues, firtDateId);
		check(firtDateId, firtDateValue, firtDate);

		ContractValueDto secondDate = ContractValueDto.findById(dtoValues, secondDateId);
		check(secondDateId, secondDateValue, secondDate);

		ContractValueDto response = ContractValueDto.findById(dtoValues, responseId);
		check(responseId, "", response);
		assertThat(response.getOperation().getName()).isEqualTo("yearsBetweenDates");

//		dtoValues = validation.processOperation(contractDto);
		contractDto = validation.processOperationReturnAllContract(contractDto);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("Test Evaluando:" + response.getOperation().getName());
		assertThat(dtoValues).isNotNull();
		log.info(dtoValues.toString());

		ContractValueDto resultFirtDate = ContractValueDto.findById(dtoValues, firtDateId);
		infoResult(2, firtDateId, firtDateValue, resultFirtDate);

		ContractValueDto resultSecondDate = ContractValueDto.findById(dtoValues, secondDateId);
		infoResult(2, secondDateId, secondDateValue, resultSecondDate);

		response = ContractValueDto.findById(dtoValues, responseId);
		infoResult(2, responseId, "", response);

		return contractDto;
	}
}
