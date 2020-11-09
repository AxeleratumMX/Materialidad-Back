package com.mx.axeleratum.americantower.contract.dynamicInterface.service;

import com.mx.axeleratum.americantower.contract.core.exception.OperationException;
import com.mx.axeleratum.americantower.contract.core.model.*;
import com.mx.axeleratum.americantower.contract.core.repository.ClientRepository;
import com.mx.axeleratum.americantower.contract.core.repository.PassthroughRepository;
import com.mx.axeleratum.americantower.contract.core.repository.SitioRepository;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.EquipmentDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.SectionDto;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

@Service
@Slf4j
public class OperationContract extends Operations {

	@Autowired
	PassthroughRepository passthroughRepository;

	@Autowired
	SitioRepository sitioRepository;

	@Autowired
	ClientRepository clientRepository;

	final static String PACKAGE_NAME = "com.mx.axeleratum.americantower";

	final static String DATE_FORMAT = "yyyy-MM-dd";
	final static String PERIODO_ANUAL = "Anual";
	final static String PERIODO_MENSUAL = "Mensual";
	final static String PERIODO_DIARIO = "Diario";
	final static String SITIO_NOTFOUND = "El sitio no existe en la base de datos para llenar el valor de %s";
	final static String SITIO_KEY_NUMBER_INVALIDO = "El valor ingresado en %s no en un entero valido";


	private Object findValueByFieldName(Class<?> cls, Object instance, String fieldName) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("Buscando: " + fieldName + " en " + cls.getName());
		Field[] fields = cls.getDeclaredFields();
		List<Object> subsInstances = new ArrayList<Object>();
		Object value = null;
		for (Field field : fields) {
			log.info("field: " + field.getName());
			field.setAccessible(true);
			if (fieldName.equals(field.getName())) {
				try {
					value = field.get(instance);
					if (value == null)
						value = "";
					break;
				} catch (Exception e) {

				}
			} else {
				Object subInstance = null;
				try {
					subInstance = field.get(instance);
					if (field.getType().getName().contains(PACKAGE_NAME)) {
						log.info(field.getType().getName());
						subsInstances.add(subInstance);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		if (value == null) {
			for (Object subInstance : subsInstances) {
				value = findValueByFieldName(subInstance.getClass(), subInstance, fieldName);
				if (value != null)
					return value;
			}
		}
		return value;
	}

	public void findValueInContract(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Param> params = currentValue.getOperation().getParams();
		String fieldName = params.get(0).getId();
		Object result = findValueByFieldName(contract.getClass(), contract, fieldName);
		log.info("Result: (" + result + ")");
		currentValue.setContent(result + "");
	}

	/**
	 * Verifica que la tabla exista en la estructura del contrato
	 * 
	 * @param currentValue
	 * @param contract
	 * @param paramName
	 * @return
	 */
	SectionDto validListParam(ContractValueDto currentValue, ContractTemplateDto contract, String paramName) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Param param = isParamNull(currentValue, paramName);
		SectionDto section = ifExistSection(currentValue, contract, param);
		return section;
	}

	public void addColList(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		SectionDto section = null;
		try {
			section = validListParam(currentValue, contract, "list");
		} catch (Exception e) {
			throw new OperationException();
		}
		/**
		 * Verifica que existan filas en la lista
		 */
		List<EquipmentDto> rows = section.getContent();
		if (rows == null || rows.size() == 0) {
			/**
			 * La sumatoria sería 0 si no existen valores en las filas
			 */
			currentValue.setContent(BigDecimal.ZERO + "");
			return;
		}

		/**
		 * Si existen elementos en las filas Se verifica que el parametro con el id del
		 * header de la columna existe
		 */
		Param param = null;
		try {
			param = isParamNull(currentValue, "col");
		} catch (Exception e) {
			throw new OperationException();
		}

		/**
		 * Se verifica que el header existe entre los valores del contrato
		 */
		List<ContractValueDto> contractValues = contract.getTemplateInstance().getValues();
		ContractValueDto header = ifExistValue(currentValue, contractValues, param);

		/**
		 * Se verifica que la columna indicada sea de tipo númerica
		 */
		if (!DataType.number.equals(header.getDataType())) {
			header.getOperationErrors().add(String.format(ERROR_FORMAT_COL, section.getId(), header.getDescription()));
			throw new OperationException(String.format(ERROR_FORMAT_COL, section.getId(), header.getDescription()));
		}
		header.getOperationErrors().remove(String.format(ERROR_FORMAT_COL, section.getId(), header.getDescription()));

		/**
		 * Se toman los valores de la columna
		 */
		List<String> values = new ArrayList<String>();
		List<Number> numbers = new ArrayList<Number>();
		for (int i = 0, n = rows.size(); i < n; i++) {
			EquipmentDto row = rows.get(i);
			Class<?> cls = EquipmentDto.class;
			Field field = null;
			try {
				field = cls.getDeclaredField(header.getId());
				field.setAccessible(true);
				String value = field.get(row).toString();
				values.add(value);
				Number number = null;
				try {
					number = OperationNumber.fromString(value);
					numbers.add(number);
				} catch (Exception e) {
					// FIXME : creo que esto no deberia ser error si no info podria
					currentValue.getOperationErrors()
							.add(String.format("El valor en la tabla %s en fila y col %s es un valor invalido",
									section.getId(), header.getDescription()));
				}
			} catch (Exception e) {
				currentValue.getOperationErrors()
						.add(String.format("El valor en la tabla %s en fila y col %s es un valor invalido",
								section.getId(), header.getDescription()));
			}
		}

		/**
		 * Se Realizar la sumatoria
		 */
		BigDecimal add = BigDecimal.ZERO;
		for (int i = 0, n = numbers.size(); i < n; i++) {
			add = add.add((BigDecimal) numbers.get(i));
		}
		add.setScale(OperationNumber.DECIMAL_SCALE, BigDecimal.ROUND_UP);
		currentValue.setContent(add + "");
	}

	/**
	 * FIXME: buscar en base de datos crear collecion
	 * 
	 * @param type
	 * @return
	 */
	private BigDecimal buscarFactor(FactorRentaEquipoType type) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		if (type.equals(FactorRentaEquipoType.MW))
			return new BigDecimal("14.55");
		if (type.equals(FactorRentaEquipoType.MW4))
			return new BigDecimal("12.12");
		if (type.equals(FactorRentaEquipoType.RF))
			return new BigDecimal("2061.24");
		if (type.equals(FactorRentaEquipoType.RRU))
			return new BigDecimal("788.12");
		return null;
	}

	static String ERROR_DIAMETRO = "Equipo %s error en el cálculo de renta, el valor de diametro es incorrecto";
	static String ERROR_ALTURA = "Equipo % error en el cálculo de renta, el valor de altura es incorrecto";
	static String ERROR_FACTOR = "Equipo % error en el cálculo de renta, se generó un problema buscando el factor en base de datos";

	/**
	 * Diametro por Altura por Factor
	 * 
	 * @param sectionEquipos
	 * @param headerRenta
	 * @param equipo
	 * @return
	 */
	private EquipmentDto calcularFactorMW(SectionDto sectionEquipos, ContractValueDto headerRenta,
			EquipmentDto equipo) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal factor = BigDecimal.ONE;
		BigDecimal diametro = BigDecimal.ONE;
		BigDecimal altura = BigDecimal.ONE;
		Boolean error = false;
		try {
			diametro = new BigDecimal(equipo.getDiametro());
		} catch (Exception e) {
			headerRenta.getOperationErrors().add(String.format(ERROR_DIAMETRO, sectionEquipos.getId()));
			error = true;
		}
		try {
			altura = new BigDecimal(equipo.getAltura());
		} catch (Exception e) {
			headerRenta.getOperationErrors().add(String.format(ERROR_ALTURA, sectionEquipos.getId()));
			error = true;
		}
		try {
			factor = buscarFactor(FactorRentaEquipoType.MW);
		} catch (Exception e) {
			headerRenta.getOperationErrors().add(String.format(ERROR_FACTOR, sectionEquipos.getId()));
			error = true;
		}
		if(headerRenta.getOperationErrors().size()>0) {
			headerRenta.getOperationErrors().remove(String.format(ERROR_FACTOR, sectionEquipos.getId()));
		}
		if (error) {
			throw new OperationException("Error realizando operacion");
		}
		try {
			headerRenta.getOperationErrors().remove(String.format(ERROR_FACTOR, sectionEquipos.getId()));
			headerRenta.getOperationErrors().remove(String.format(String.format(ERROR_ALTURA, sectionEquipos.getId())));
			headerRenta.getOperationErrors().remove(String.format(String.format(ERROR_DIAMETRO, sectionEquipos.getId())));
		}catch (Exception e) {

		}

		log.info("Diametro="+diametro+" x Altura:"+altura+" x Factor="+factor);
		
		result = diametro.multiply(altura).multiply(factor).multiply(new BigDecimal("3.281"));
		equipo.setRentaEquipo(result);
		
		log.info("Renta="+equipo.getRentaEquipo());
		return equipo;
	}

	public void rentaEquipo(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		/**
		 * Se verifica que el parametro con el id del header de la columna existe
		 */
		Param param = null;
		try {
			param = isParamNull(currentValue, "col");
		} catch (Exception e) {
			throw new OperationException();
		}

		List<ContractValueDto> contractValues = contract.getTemplateInstance().getValues();

		/**
		 * Se busca el header de la columna que determina el factor
		 */
		@SuppressWarnings("unused")
		ContractValueDto tipoAntenaHeader = ifExistValue(currentValue, contractValues, param);

		/**
		 * Se busca el header de la columna que determina el factor
		 */
		ContractValueDto rentaEquipoHeader = ifExistValue(currentValue, contractValues, "rentaEquipo");

		/**
		 * Se busca la seccion de los equipos donde estan los valores ingresados
		 */
		SectionDto sectionEquipos = ifExistSection(currentValue, contract, "equipos");

		/**
		 * Se busca las filas
		 */
		List<EquipmentDto> rows = sectionEquipos.getContent();

		/**
		 * Se calcula la renta segun esta formula
		 * 
		 * 
		 * MW = altura * diametro * factor(14,55) MW a 4 años = altura * diametro *
		 * factor(12,12) RF = factor(2061,24) RRU = factor(788,12)
		 * 
		 */
		for (int i = 0, n = ((rows != null) ? rows.size() : 0); i < n; i++) {
			EquipmentDto row = rows.get(i);
			try {
				if (FactorRentaEquipoType.MW == FactorRentaEquipoType.getByValue(row.getTipoAntena()))
					row = calcularFactorMW(sectionEquipos, rentaEquipoHeader, row);
				if (FactorRentaEquipoType.MW4 == FactorRentaEquipoType.getByValue(row.getTipoAntena()))
					row = calcularFactorMW4(sectionEquipos, rentaEquipoHeader, row);
				if (FactorRentaEquipoType.RF == FactorRentaEquipoType.getByValue(row.getTipoAntena()))
					row = calcularFactorRF(sectionEquipos, rentaEquipoHeader, row);
				if (FactorRentaEquipoType.RRU == FactorRentaEquipoType.getByValue(row.getTipoAntena()))
					row = calcularFactorRRU(sectionEquipos, rentaEquipoHeader, row);
			} catch (Exception e) {
				/**
				 * No pasa nada ya puse el error en la cabecera de la lista
				 */
			}
		}
	}

	private EquipmentDto calcularFactorMW4(SectionDto sectionEquipos, ContractValueDto headerRenta,
			EquipmentDto equipo) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal factor = BigDecimal.ONE;
		BigDecimal diametro = BigDecimal.ONE;
		BigDecimal altura = BigDecimal.ONE;
		Boolean error = false;
		try {
			diametro = new BigDecimal(equipo.getDiametro());
		} catch (Exception e) {
			headerRenta.getOperationErrors().add(String.format(ERROR_DIAMETRO, sectionEquipos.getId()));
			error = true;
		}
		try {
			altura = new BigDecimal(equipo.getAltura());
		} catch (Exception e) {
			headerRenta.getOperationErrors().add(String.format(ERROR_ALTURA, sectionEquipos.getId()));
			error = true;
		}
		try {
			factor = buscarFactor(FactorRentaEquipoType.MW4);
		} catch (Exception e) {
			headerRenta.getOperationErrors().add(String.format(ERROR_FACTOR, sectionEquipos.getId()));
			error = true;
		}
		if (error) {
			throw new OperationException("Error realizando operacion");
		}
		try {
			headerRenta.getOperationErrors().remove(String.format(ERROR_FACTOR, sectionEquipos.getId()));
			headerRenta.getOperationErrors().remove(String.format(String.format(ERROR_ALTURA, sectionEquipos.getId())));
			headerRenta.getOperationErrors().remove(String.format(String.format(ERROR_DIAMETRO, sectionEquipos.getId())));
		}catch (Exception e) {

		}		
		log.info("Diametro="+diametro+" x Altura:"+altura+" x Factor="+factor);
		
		result = diametro.multiply(altura).multiply(factor).multiply(new BigDecimal("3.281"));
		equipo.setRentaEquipo(result);
		
		log.info("Renta="+equipo.getRentaEquipo());
		
		return equipo;
	}

	private EquipmentDto calcularFactorRF(SectionDto sectionEquipos, ContractValueDto headerRenta,
			EquipmentDto equipo) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		BigDecimal factor = BigDecimal.ONE;
		Boolean error = false;
		try {
			factor = buscarFactor(FactorRentaEquipoType.RF);
		} catch (Exception e) {
			headerRenta.getOperationErrors().add(String.format(ERROR_FACTOR, sectionEquipos.getId()));
			error = true;
		}
		if (error) {
			throw new OperationException("Error realizando operacion");
		}
		try {
			headerRenta.getOperationErrors().remove(String.format(ERROR_FACTOR, sectionEquipos.getId()));
		}catch (Exception e) {

		}
		equipo.setRentaEquipo(factor);
		log.info("Factor="+factor);
		log.info("Renta="+equipo.getRentaEquipo());
		return equipo;
	}

	private EquipmentDto calcularFactorRRU(SectionDto sectionEquipos, ContractValueDto headerRenta,
			EquipmentDto equipo) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		BigDecimal factor = BigDecimal.ONE;
		Boolean error = false;
		try {
			factor = buscarFactor(FactorRentaEquipoType.RRU);
		} catch (Exception e) {
			headerRenta.getOperationErrors().add(String.format(ERROR_FACTOR, sectionEquipos.getId()));
			error = true;
		}
		if (error) {
			throw new OperationException("Error realizando operacion");
		}		
		try {
			headerRenta.getOperationErrors().remove(String.format(ERROR_FACTOR, sectionEquipos.getId()));
		}catch (Exception e) {

		}
		equipo.setRentaEquipo(factor);
		log.info("Factor="+factor);
		log.info("Renta="+equipo.getRentaEquipo());
		return equipo;
	}


	public void rentaTerreno(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractValueDto> contractValues = contract.getTemplateInstance().getValues();
		Param paramKey = isParamNull(currentValue, "searchBy");
		ContractValueDto valueKey = ifExistValue(currentValue, contractValues, paramKey);
		if (valueKey.getContent() == null || valueKey.getContent().isEmpty()) {
			// No pasa nada no han ingresado un valor key para realizar la búsqueda
			return;
		}

		// Saco el valor del idActivo del value
		currentValue.getOperationErrors().remove(String.format(SITIO_KEY_NUMBER_INVALIDO, valueKey.getDescription()));
		Long key = null;
		try {
			key = Long.valueOf(valueKey.getContent());
		} catch (Exception e) {
			currentValue.getOperationErrors().add(String.format(SITIO_KEY_NUMBER_INVALIDO, valueKey.getDescription()));
			log.error(String.format(SITIO_KEY_NUMBER_INVALIDO, valueKey.getDescription()));
			throw new OperationException(String.format(SITIO_KEY_NUMBER_INVALIDO, valueKey.getDescription()));
		}

		// Se busca por sitio en base de datos
		List<Passthru> passthrus = passthroughRepository.findByIdActivo(key);
		if (passthrus.size() == 0) {
			currentValue.getOperationErrors().add(String.format(SITIO_NOTFOUND, currentValue.getId()));
			log.error(String.format(SITIO_NOTFOUND, currentValue.getId()));
			throw new OperationException(String.format(SITIO_NOTFOUND, currentValue.getId()));
		}
		log.info("id Activo: " + key);

		// agrupo por porcentaje para validar que estan bien los registros
		Map<BigDecimal, List<Passthru>> gruposParticipantes = new LinkedHashMap<BigDecimal, List<Passthru>>();
		for (int i = 0, n = passthrus.size(); i < n; i++) {
			Passthru passthru = passthrus.get(i);
			List<Passthru> cant = gruposParticipantes.get(passthru.getPercentage());
			if (cant == null) {
				cant = new ArrayList<Passthru>();
			}			
			cant.add(passthru);
			gruposParticipantes.put(passthru.getPercentage(), cant);
			log.info("Porcentaje: " + passthru.getPercentage() + " cantidad: " + cant.size());
		}

		int cantidadParticipantes = 0;
//		BigDecimal porcentajeActual = null;
		BigDecimal rentaActual = null;
		// Si se hizo un solo grupo entonces no hay problema
		// De lo contrario algo anda mal
		if (gruposParticipantes.size() == 1) {
			List<Passthru> participantes = gruposParticipantes.get(gruposParticipantes.keySet().toArray()[0]);
			cantidadParticipantes = participantes.size();
			double porcentajeActual = porcentaje(new Double(cantidadParticipantes+1));


			double landMonAmmount = participantes.get(0).getLandMonAmmount().doubleValue();
			double result = landMonAmmount * porcentajeActual;
			rentaActual = new BigDecimal(result).setScale(OperationNumber.DECIMAL_SCALE,BigDecimal.ROUND_UP);

			log.info("Participantes ="+cantidadParticipantes+" +1");
			log.info("Porcentaje ="+porcentajeActual+" LandMonAmmount ="+participantes.get(0).getLandMonAmmount());
			log.info("Renta Terreno ="+rentaActual);
		} else {
			// Busco el porcentaje que mas se repite considerando que hay errores
			Iterator<Entry<BigDecimal, List<Passthru>>> it = gruposParticipantes.entrySet().iterator();
			while (it.hasNext()) {
				Entry<BigDecimal, List<Passthru>> participacion = it.next();
				if (cantidadParticipantes < participacion.getValue().size()) {
					cantidadParticipantes = participacion.getValue().size();
					double porcentajeActual = porcentaje(new Double(cantidadParticipantes+1));
					double landMonAmmount = participacion.getValue().get(0).getLandMonAmmount().doubleValue();
					double result = landMonAmmount * porcentajeActual;
					rentaActual = new BigDecimal(result).setScale(OperationNumber.DECIMAL_SCALE,BigDecimal.ROUND_UP);

					log.info("Participantes ="+cantidadParticipantes+" +1");
					log.info("Porcentaje ="+porcentajeActual+" LandMonAmmount ="+ participacion.getValue().get(0).getLandMonAmmount());
					log.info("Renta Terreno ="+rentaActual);
				}
			}
		}
		currentValue.setContent(rentaActual + "");
	}

	private double porcentaje( double participantes) {
		Double value1 = new Double(100);

		double result =   (value1 / participantes)/value1;
		return result;

	}

	public void findValueInSitio(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractValueDto> contractValues = contract.getTemplateInstance().getValues();

		Param paramKey = isOnLoadParamNull(currentValue, "key");
		ContractValueDto valueKey = ifExistValue(currentValue, contractValues, paramKey);
		if (valueKey.getContent() == null || valueKey.getContent().isEmpty()) {
			// No pasa nada no han ingresado un valor key para realizar la búsqueda
			return;
		}
		Param paramSet = isOnLoadParamNull(currentValue, "set");

		currentValue.getOperationErrors().remove(String.format(SITIO_KEY_NUMBER_INVALIDO, valueKey.getDescription()));
		Long key = null;
		try {
			key = Long.valueOf(valueKey.getContent());
		} catch (Exception e) {
			currentValue.getOperationErrors().add(String.format(SITIO_KEY_NUMBER_INVALIDO, valueKey.getDescription()));
			log.error("El valor ingresado en Asset Number no en un entero valido");
			throw new OperationException("El valor ingresado en %s no en un entero valido");
		}

		Optional<Sitio> oSitio = sitioRepository.findByIdActivo(key);
		if (oSitio.isPresent()) {
			Sitio sitio = oSitio.get();
			currentValue.getOperationErrors().remove(String.format(SITIO_NOTFOUND, currentValue.getId()));
			Field field;
			try {
				field = sitio.getClass().getDeclaredField(paramSet.getValue());
				field.setAccessible(true);
				Object value = field.get(sitio);
				currentValue.setContent(value + "");
				currentValue.getOperationErrors().clear();
			} catch (Exception e) {
				log.error("El campo escrito en el parametro set no existe en la tabla Sitio");
				e.printStackTrace();
			}
		} else {
			currentValue.getOperationErrors().add(String.format(SITIO_NOTFOUND, currentValue.getId()));
		}
	}

	public void updateDireccionInSitio(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractValueDto> contractValues = contract.getTemplateInstance().getValues();

		Param paramKey = isParamNull(currentValue, "key");
		ContractValueDto valueKey = ifExistValue(currentValue, contractValues, paramKey);
		if (valueKey.getContent() == null || valueKey.getContent().isEmpty()) {
			// No pasa nada no han ingresado un valor key para realizar la búsqueda
			return;
		}
		Param paramSet = isParamNull(currentValue, "set");

		currentValue.getOperationErrors().remove(String.format(SITIO_KEY_NUMBER_INVALIDO, valueKey.getDescription()));
		Long key = null;
		try {
			key = Long.valueOf(valueKey.getContent());
		} catch (Exception e) {
			currentValue.getOperationErrors().add(String.format(SITIO_KEY_NUMBER_INVALIDO, valueKey.getDescription()));
			log.error("El valor ingresado en Asset Number no en un entero valido");
			throw new OperationException("El valor ingresado en %s no en un entero valido");
		}

		Optional<Sitio> oSitio = sitioRepository.findByIdActivo(key);
		if (oSitio.isPresent()) {
			Sitio sitio = oSitio.get();
			currentValue.getOperationErrors().remove(String.format(SITIO_NOTFOUND, currentValue.getId()));
			Field field;
			try {
				field = sitio.getClass().getDeclaredField(paramSet.getValue());
				field.setAccessible(true);
				field.set(sitio,currentValue.getContent());
				sitioRepository.save(sitio);
				currentValue.getOperationErrors().clear();
			} catch (Exception e) {
				log.error("El campo escrito en el parametro set no existe en la tabla Sitio");
				e.printStackTrace();
			}
		} else {
			currentValue.getOperationErrors().add(String.format(SITIO_NOTFOUND, currentValue.getId()));
		}
	}

	public void calculoFechaRenovacion(ContractValueDto currentValue, ContractTemplateDto contract) {

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractValueDto> contractValues = contract.getTemplateInstance().getValues();


		//terminosRenovacion
		Param paramTerminosRenovacion = isParamNull(currentValue, "terminosRenovacion");
		ContractValueDto valueTerminosRenovacion = ifExistValue(currentValue, contractValues, paramTerminosRenovacion);

		//periodo
		Param paramPeriodo = isParamNull(currentValue, "periodo");
		ContractValueDto valuePeriodo = ifExistValue(currentValue, contractValues, paramPeriodo);

		//periodo
		Param paramNumeroRenovaciones = isParamNull(currentValue, "numeroRenovaciones");
		ContractValueDto valueNumeroRenovaciones = ifExistValue(currentValue, contractValues, paramNumeroRenovaciones);

		//fechaTerminoVigencia
		Param paramFechaTerminoVigencia = isParamNull(currentValue, "fechaTerminoVigencia");
		ContractValueDto valueFechaTerminoVigencia = ifExistValue(currentValue, contractValues, paramFechaTerminoVigencia);

		if (
				Objects.nonNull(valueTerminosRenovacion.getContent())
						&& !valueTerminosRenovacion.getContent().isEmpty()
						&& valueTerminosRenovacion.getContent().equals("Auto")) {

			DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_FORMAT);
			DateTime result = null;

			DateTime dateTime = DateTime.parse(valueFechaTerminoVigencia.getContent(), formatter);
			int numeroRenovaciones = isNumber(valueNumeroRenovaciones,"numeroRenovaciones");
			String periodo = valuePeriodo.getContent();

			if (periodo.equals(PERIODO_ANUAL)) {
				result = dateTime.plusYears(numeroRenovaciones);
			}else if (periodo.equals(PERIODO_MENSUAL)) {
				result = dateTime.plusMonths(numeroRenovaciones);
			}else if (periodo.equals(PERIODO_DIARIO)) {
				result = dateTime.plusDays(numeroRenovaciones);
			}
			currentValue.setContent(result.toString(formatter));
		}

		log.info("terminosRenovacion "  + valueTerminosRenovacion.getContent() );

	}

	public void findValueInClient(ContractValueDto currentValue, ContractTemplateDto contract) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractValueDto> contractValues = contract.getTemplateInstance().getValues();

		Param paramKey = isOnLoadParamNull(currentValue, "key");
		ContractValueDto valueKey = ifExistValue(currentValue, contractValues, paramKey);
		if (valueKey.getContent() == null || valueKey.getContent().isEmpty()) {
			// No pasa nada no han ingresado un valor key para realizar la búsqueda
			return;
		}
		Param paramSet = isOnLoadParamNull(currentValue, "set");

		currentValue.getOperationErrors().remove(String.format(SITIO_KEY_NUMBER_INVALIDO, valueKey.getDescription()));

		Optional<Client> oClient = clientRepository.findByIdCliente(valueKey.getContent());
		if (oClient.isPresent()) {
			Client client = oClient.get();
			currentValue.getOperationErrors().remove(String.format(SITIO_NOTFOUND, currentValue.getId()));
			Field field;
			try {
				field = client.getClass().getDeclaredField(paramSet.getValue());
				field.setAccessible(true);
				Object value = field.get(client);
				currentValue.setContent(value + "");
				currentValue.getOperationErrors().clear();
			} catch (Exception e) {
				log.error("El campo escrito en el parametro set no existe en la tabla Sitio");
				e.printStackTrace();
			}
		} else {
			currentValue.getOperationErrors().add(String.format(SITIO_NOTFOUND, currentValue.getId()));
		}
	}

	private int isNumber(ContractValueDto currentValue,  String parameName) {
		try {
			return  new  Integer(currentValue.getContent()).intValue();
		}catch (Exception ex) {
			currentValue.getOperationErrors().add(String.format(SITIO_KEY_NUMBER_INVALIDO, parameName));
			log.error(String.format(SITIO_KEY_NUMBER_INVALIDO, parameName));
			throw new OperationException(String.format(SITIO_KEY_NUMBER_INVALIDO, parameName));

		}

	}
}
