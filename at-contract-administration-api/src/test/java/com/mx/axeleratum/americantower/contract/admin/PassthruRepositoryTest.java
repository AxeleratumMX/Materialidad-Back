package com.mx.axeleratum.americantower.contract.admin;

import com.mx.axeleratum.americantower.contract.core.model.Passthru;
import com.mx.axeleratum.americantower.contract.core.repository.PassthroughRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdministrationApplication.class)
@Slf4j
public class PassthruRepositoryTest {

	public static final String XLSX_FILE = "/ATC_TAR_GRP_Passthru_Output_Re_041018.xlsx";
	public static final String METHOD = "POST";
	public static final String strURL = "http://localhost:8080/notifications/batch";
	public static final int rowInitial = 6;
	public static final int idActivo = 1;
	public static final int grossAmmount = 11;
	public static final int landMonAmmount = 12;
	public static final int percentage = 13;
	List<Integer> columns = Arrays.asList(idActivo, grossAmmount, landMonAmmount, percentage);
	List<String> columsName = Arrays.asList("idActivo", "grossAmmount", "landMonAmmount", "percentage");
//    List<String> statusInit = Arrays.asList("Activado", "Borrador", "Pendiente Firma", "Cancelado");

	@Autowired
	PassthroughRepository passthroughRepository;

    @Test
    public void testAddPassthru() {
//        Passthru
        passthroughRepository.deleteAll();

    }
	@Test
	public void addDaataFakeToMongo() throws EncryptedDocumentException, IOException {
		passthroughRepository.deleteAll();
		DataFormatter dataFormatter = new DataFormatter();
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
		Sheet sheet = sheets.get(1);
		Iterator<Row> rowIterator = sheet.rowIterator();
		int rowI = 0;
		while (rowIterator.hasNext()) {
			Passthru passthru = new Passthru();
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			int i = 0;
			rowI++;
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String cellValue = dataFormatter.formatCellValue(cell);
				i++;
				if (columns.contains(i) && rowI > 1) {
					System.out.print("^" + "set" + columsName.get(columns.indexOf(i)) + "^(" + cell.getRowIndex() + ","
							+ cell.getColumnIndex() + "," + cellValue + ")" + "\t");
					Field field = ReflectionUtils.findField(Passthru.class, columsName.get(columns.indexOf(i)));
					ReflectionUtils.makeAccessible(field);
					ReflectionUtils.setField(field, passthru, convertValue(field, cellValue));
				}
			}
			System.out.println();
			if (rowI > 1) {
				passthru = passthroughRepository.insert(passthru);
				log.info("pass " + passthru);

			}

		}
	}

	private Object convertValue(Field field, String value) {
		try {
			if (Objects.nonNull(value)) {
				if (field.getType() == Long.class) {
					System.out.println("Long------------------------------"+value);
					return new Long(value);
				}				
				if (field.getType() == BigDecimal.class) {
					Boolean error = false;
					BigDecimal num = null;
					try {
						num = new BigDecimal(value);
					} catch (Exception e) {
						error = true;
					}
					if (error) {
						value = value.replace(".", "*");
						value = value.replace(",", ".");
						value = value.replace("*", ",");
						num = new BigDecimal(value);							
					}
					System.out.println("BigDecimal------------------------------"+value);
					return num;
				}
				if (field.getType() == Integer.class) {
					System.out.println("Integer------------------------------"+value);
					return new Integer(value);
				}
				if (field.getType() == String.class) {
					System.out.println("String------------------------------"+value);
					return new String(value);
				}
			}
			return value;
		} catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
	}

}