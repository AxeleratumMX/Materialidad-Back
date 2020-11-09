package com.mx.axeleratum.americantower.contract.dynamicInterface.service;

import com.mx.axeleratum.americantower.contract.core.exception.OperationException;
import com.mx.axeleratum.americantower.contract.core.model.Operation;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.TemplateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

@Service
@Slf4j
public class ValidatorService {

	static final String MOSTRAR_NIVEL = "********************************** NIVEL %s*********************************";

	@Autowired
	OperationBetweenDates operationBetweenDates;

	@Autowired
	OperationNumber operationNumber;

	@Autowired
	OperationList operationList;

	@Autowired
	OperationContract operationContract;

	@SuppressWarnings("resource")
	public List<String[]> processFile(MultipartFile file) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<String[]> listValues = new ArrayList<String[]>();
		try {
			InputStream inputStream = file.getInputStream();
			Scanner scan = new Scanner(inputStream);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] values = line.split("\\s+");
				listValues.add(values);
			}

		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error leyendo archivo", e);
		}
		return listValues;
	}

	private Map<Integer, List<Integer>> getLevelsOperations(List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<Integer, List<Integer>> levels = new HashMap<Integer, List<Integer>>();
		for (int j = 0, m = contractValues.size(); j < m; j++) {
			if (contractValues.get(j).getOperation() != null) {
				ContractValueDto value = contractValues.get(j);
				Operation operation = value.getOperation();
				Integer level = (operation.getLevel() == null) ? 0 : operation.getLevel();
				List<Integer> operationsIds = levels.get(level);
				if (operationsIds == null) {
					operationsIds = new ArrayList<Integer>();
				}
				operationsIds.add(j);
				levels.put(level, operationsIds);
			}
		}
		return levels;
	}

	private Map<Integer, List<Integer>> getLevelsOnLoad(List<ContractValueDto> contractValues) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<Integer, List<Integer>> levels = new HashMap<Integer, List<Integer>>();
		for (int j = 0, m = contractValues.size(); j < m; j++) {
			if (contractValues.get(j).getOnLoad() != null) {
				ContractValueDto value = contractValues.get(j);
				Operation operation = value.getOnLoad();
				Integer level = (operation.getLevel() == null) ? 0 : operation.getLevel();
				List<Integer> operationsIds = levels.get(level);
				if (operationsIds == null) {
					operationsIds = new ArrayList<Integer>();
				}
				operationsIds.add(j);
				levels.put(level, operationsIds);
			}
		}
		return levels;
	}

	public List<ContractValueDto> processOperation(ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		TemplateDto template = contract.getTemplateInstance();
		List<ContractValueDto> contractValues = template.getValues();
		Map<Integer, List<Integer>> levels = getLevelsOperations(contractValues);
		Iterator<Entry<Integer, List<Integer>>> itLevels = levels.entrySet().iterator();
		while (itLevels.hasNext()) {
			Entry<Integer, List<Integer>> level = itLevels.next();
			log.info(String.format(MOSTRAR_NIVEL, level.getKey()));
			List<Integer> operationsIds = level.getValue();
			for (int j = 0, n = operationsIds.size(); j < n; j++) {
				Integer indice = operationsIds.get(j);
				ContractValueDto value = contractValues.get(indice);
				Operation operation = value.getOperation();
				String function = operation.getName();
				log.info("+----------------------------------------+");
				log.info("Process method : " + operation.getName());
				log.info("-----------------------------------------");

				Class<?> arg[] = new Class[2];
				arg[0] = ContractValueDto.class;
				arg[1] = ContractTemplateDto.class;

				Class<?> cls = ValidatorService.class;
				Method method = null;
				try {
					method = cls.getDeclaredMethod(function, arg);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				try {
					ReflectionUtils.invokeMethod(method, this, value, contract);
					log.info("Operation (" + operation.getName() + "): id:(" + value.getId() + ") = "
							+ value.getContent());
					log.info("Operation (" + operation.getName() + "): type:(" + operation.getType() + ")");
					log.info("Errors messagess (" + operation.getName() + "," + value.getId() + ") = "
							+ value.getOperationErrors());
				} catch (OperationException e) {
					log.info("Operation (" + operation.getName() + "): id:(" + value.getId() + ")");
					log.info("Errors messagess (" + operation.getName() + "," + value.getId() + ") = "
							+ value.getOperationErrors());
					log.info("Error controlado");
				} catch (java.lang.IllegalArgumentException e) {
					log.error("Error grave validando, algo anda mal en el json del contrato", e);
					e.printStackTrace();
				} catch (Exception e) {
					log.error("Error grave validando, algo anda mal en el json del contrato", e);
					e.printStackTrace();
				}
			}
		}

		return contractValues;
	}


	public List<ContractValueDto> processOnLoad(ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		TemplateDto template = contract.getTemplateInstance();
		List<ContractValueDto> contractValues = template.getValues();
		Map<Integer, List<Integer>> levels = getLevelsOnLoad(contractValues);
		Iterator<Entry<Integer, List<Integer>>> itLevels = levels.entrySet().iterator();
		while (itLevels.hasNext()) {
			Entry<Integer, List<Integer>> level = itLevels.next();
			log.info(String.format(MOSTRAR_NIVEL, level.getKey()));
			List<Integer> operationsIds = level.getValue();
			for (int j = 0, n = operationsIds.size(); j < n; j++) {
				Integer indice = operationsIds.get(j);
				ContractValueDto value = contractValues.get(indice);
				Operation operation = value.getOnLoad();
				String function = operation.getName();
				log.info("+----------------------------------------+");
				log.info("Process method : " + operation.getName());
				log.info("-----------------------------------------");

				Class<?> arg[] = new Class[2];
				arg[0] = ContractValueDto.class;
				arg[1] = ContractTemplateDto.class;

				Class<?> cls = ValidatorService.class;
				Method method = null;
				try {
					method = cls.getDeclaredMethod(function, arg);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				try {
					ReflectionUtils.invokeMethod(method, this, value, contract);
					log.info("Operation (" + operation.getName() + "): id:(" + value.getId() + ") = "
							+ value.getContent());
					log.info("Operation (" + operation.getName() + "): type:(" + operation.getType() + ")");
					log.info("Errors messagess (" + operation.getName() + "," + value.getId() + ") = "
							+ value.getOperationErrors());
				} catch (OperationException e) {
					log.info("Operation (" + operation.getName() + "): id:(" + value.getId() + ")");
					log.info("Errors messagess (" + operation.getName() + "," + value.getId() + ") = "
							+ value.getOperationErrors());
					log.info("Error controlado");
				} catch (java.lang.IllegalArgumentException e) {
					log.error("Error grave validando, algo anda mal en el json del contrato", e);
					e.printStackTrace();
				} catch (Exception e) {
					log.error("Error grave validando, algo anda mal en el json del contrato", e);
					e.printStackTrace();
				}
			}
		}

		return contractValues;
	}

	public ContractTemplateDto processOperationReturnAllContract(ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		processOperation(contract);
		return contract;
	}

	public ContractTemplateDto processOperationOnLoadAllContract(ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		processOnLoad(contract);
		return contract;
	}

	public void isDateGreaterOrEqualThan(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationBetweenDates.isDateGreaterOrEqualThan(currentValue, contract.getTemplateInstance().getValues());
	}

	public void isDateGreaterThan(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationBetweenDates.isDateGreaterThan(currentValue, contract.getTemplateInstance().getValues());
	}

	public void addNumYearsToDate(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationBetweenDates.addNumYearsToDate(currentValue, contract.getTemplateInstance().getValues());
	}

	public void yearsBetweenDates(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationBetweenDates.yearsBetweenDates(currentValue, contract.getTemplateInstance().getValues());
	}

	public void aditionalsMonthsBetweenDates(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationBetweenDates.aditionalsMonthsBetweenDates(currentValue, contract.getTemplateInstance().getValues());
	}

	public void aditionalsDaysBetweenDates(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationBetweenDates.aditionalsDaysBetweenDates(currentValue, contract.getTemplateInstance().getValues());
	}

	public void add(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationNumber.add(currentValue, contract.getTemplateInstance().getValues());
	}

	public void subtraction(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationNumber.subtraction(currentValue, contract.getTemplateInstance().getValues());
	}

	public void findValueInContract(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationContract.findValueInContract(currentValue, contract);
	}

	public void enableEditByListValue(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationList.enableEditByListValue(currentValue, contract.getTemplateInstance().getValues());
	}

	public void addColList(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationContract.addColList(currentValue, contract);
	}

	public void rentaEquipo(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationContract.rentaEquipo(currentValue, contract);
	}

	public void rentaTerreno(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationContract.rentaTerreno(currentValue, contract);
	}
	
	public void findValueInSitio(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationContract.findValueInSitio(currentValue, contract);
	}

	public void findValueInClient(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationContract.findValueInClient(currentValue, contract);
	}


	public void updateDireccionInSitio(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationContract.updateDireccionInSitio(currentValue, contract);
	}
	public void calculoFechaRenovacion(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		operationContract.calculoFechaRenovacion(currentValue, contract);
	}

}
