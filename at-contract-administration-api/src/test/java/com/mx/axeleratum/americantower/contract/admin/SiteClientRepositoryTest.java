package com.mx.axeleratum.americantower.contract.admin;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import com.mx.axeleratum.americantower.contract.core.model.SitioClient;
import com.mx.axeleratum.americantower.contract.core.repository.SitioClientRepository;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdministrationApplication.class)
@Slf4j
public class SiteClientRepositoryTest {

	public static final String XLSX_FILE = "/ATC_TAR_GRP_Passthru_Output_Re_041018.xlsx";
	public static final String METHOD = "POST";
	public static final String strURL = "http://localhost:8080/notifications/batch";
	public static final int rowInitial = 2;
	public static final int idActivo = 1;
	public static final int nameClient = 3;
	public static final int idClient = 4;
	List<Integer> columns = Arrays.asList(idActivo, nameClient, idClient);
	List<String> columsName = Arrays.asList("idActivo", "nameClient", "idClient");

	@Autowired
	SitioClientRepository sitioClientRepository;

	@Test
	public void addDaataFakeToMongo() throws EncryptedDocumentException, IOException {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		sitioClientRepository.deleteAll();
		InputStream testInputStream = this.getClass().getResourceAsStream(XLSX_FILE);
		Workbook workbook = WorkbookFactory.create(testInputStream);
		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

		Iterator<Sheet> sheetIterator = workbook.sheetIterator();
		System.out.println("Retrieving Sheets using Iterator");
		List<Sheet> sheets = new ArrayList<Sheet>();
		while (sheetIterator.hasNext()) {
			Sheet sheet = sheetIterator.next();
			System.out.println("=> " + sheet.getSheetName());
			sheets.add(sheet);
		}

		DataFormatter dataFormatter = new DataFormatter();

		System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
		Sheet sheet = sheets.get(1);
		Iterator<Row> rowIterator = sheet.rowIterator();
		int rowI = 0;

		Set<SitioClient> relaciones = new HashSet<SitioClient>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			SitioClient relacion = new SitioClient();
			Iterator<Cell> cellIterator = row.cellIterator();
			int i = 0;
			rowI++;
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String cellValue = dataFormatter.formatCellValue(cell);
				i++;
				if (columns.contains(i) && rowI > rowInitial) {
					Field field = ReflectionUtils.findField(SitioClient.class, columsName.get(columns.indexOf(i)));
					ReflectionUtils.makeAccessible(field);
					ReflectionUtils.setField(field, relacion, convertValue(field, cellValue));
				}
			}
			if (rowI > rowInitial) {
				System.out.println(relacion);
				if (relacion.getIdClient().equals("1040")) {
					// Si sacamos
					if (relacion.getIdActivo().equals(85649L)) {
						relacion.setIdClient("12345");
						relacion.setNameClient("ATyT COMUNICACIONES DIGITALES S DE RL DE CV");
						relaciones.add(relacion);
					}
				}
				if (relacion.getIdClient().equals("1018")) {
					if (relacion.getIdActivo().equals(85663L)) {
						relacion.setIdClient("11111");
						relacion.setNameClient("Pegaso PCS SA de CV");
						relaciones.add(relacion);
					}
				}
//				if (relacion.getIdClient().equals("1035")) {
//					relacion.setIdClient("22222");
//					relacion.setNameClient("AXTEL SAB DE CV");
//					relaciones.add(relacion);
//				}
			}
		}
		System.out.println(relaciones);
		System.out.println(relaciones.size());
		workbook.close();
		List<SitioClient> list = new ArrayList<>();
		list.addAll(relaciones);
		addAll(list);
	}

	public void addAll(List<SitioClient> relations) {
		relations = sitioClientRepository.saveAll(relations);
		log.info("Relations created " + relations);
	}

	private Object convertValue(Field field, String value) {
		if (Objects.nonNull(value)) {
			if (field.getType() == Long.class) {
				return new Long(value);
			}
			;
			if (field.getType() == BigDecimal.class) {
				return new BigDecimal(value);
			}
			if (field.getType() == Integer.class) {
				return new Integer(value);
			}
			if (field.getType() == String.class) {
				return new String(value);
			}
		}
		return value;
	}

	@Test
	public void findByIdActivo() {
		List<SitioClient> relations = sitioClientRepository.findByIdActivo(85972L);
		log.info("Relations find: ");
		for (int i = 0, n = relations.size(); i < n; i++) {
			System.out.println(relations.get(i));
		}
		relations = sitioClientRepository.findByIdActivo(85625L);
		log.info("Relations find: ");
		for (int i = 0, n = relations.size(); i < n; i++) {
			System.out.println(relations.get(i));
		}
		relations = sitioClientRepository.findByIdActivo(86063L);
		log.info("Relations find: ");
		for (int i = 0, n = relations.size(); i < n; i++) {
			System.out.println(relations.get(i));
		}
	}

	@Test
	public void findfindByNameClient() {
		List<SitioClient> relations = sitioClientRepository
				.findByNameClient("ATyT COMUNICACIONES DIGITALES S DE RL DE CV");
		log.info("Relations find: ");
		for (int i = 0, n = relations.size(); i < n; i++) {
			System.out.println(relations.get(i));
		}
		relations = sitioClientRepository.findByNameClient("Pegaso PCS SA de CV");
		log.info("Relations find: ");
		for (int i = 0, n = relations.size(); i < n; i++) {
			System.out.println(relations.get(i));
		}
		relations = sitioClientRepository.findByNameClient("Total Play Telecomun");
		log.info("Relations find: ");
		for (int i = 0, n = relations.size(); i < n; i++) {
			System.out.println(relations.get(i));
		}
	}

	@Test
	public void findfindByIdClient() {
		List<SitioClient> relations = sitioClientRepository.findByIdClient("12345");
		log.info("Relations find: " + relations.size());
		for (int i = 0, n = relations.size(); i < n; i++) {
			System.out.println(relations.get(i));
		}

		relations = sitioClientRepository.findByIdClient("11111");
		log.info("Relations find: " + relations.size());
		for (int i = 0, n = relations.size(); i < n; i++) {
			System.out.println(relations.get(i));
		}

		relations = sitioClientRepository.findByIdClient("22222");
		log.info("Relations find: " + relations.size());
		for (int i = 0, n = relations.size(); i < n; i++) {
			System.out.println(relations.get(i));
		}
	}

	@Test
	public void findfindByIdActivo() {
		List<SitioClient> relations = sitioClientRepository.findByIdActivo(85649L);
		log.info("Relations find: " + relations.size());
		for (int i = 0, n = relations.size(); i < n; i++) {
			System.out.println(relations.get(i));
		}

		relations = sitioClientRepository.findByIdActivo(85663L);
		log.info("Relations find: " + relations.size());
		for (int i = 0, n = relations.size(); i < n; i++) {
			System.out.println(relations.get(i));
		}
	}
}