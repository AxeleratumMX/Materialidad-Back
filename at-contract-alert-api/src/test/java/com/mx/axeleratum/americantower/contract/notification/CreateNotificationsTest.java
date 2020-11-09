package com.mx.axeleratum.americantower.contract.notification;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class CreateNotificationsTest {
	public static final String XLSX_FILE = "/ReporteDataLoaderTenant.xlsx";
	public static final String METHOD = "POST";
	public static final String strURL = "http://localhost:8080/alerts/batch";
	public static final int rowInitial = 6;
	public static final int assetId = 2;
	public static final int folio = 5;
	public static final int client = 6;
	public static final int tipoContrato = 8;
	public static final int subTipoContrato = 9;
	List<Integer> columns = Arrays.asList(assetId, folio, client, tipoContrato, subTipoContrato);
	List<String> columsName = Arrays.asList("assetId", "folio", "client", "tipoContrato", "subTipoContrato");
	List<String> statusInit = Arrays.asList("Control de sitios", "Evento por tiempo", "Control de equipamento");

	public void addNotifications(JSONArray notifications) throws IOException {
		URL url = new URL(strURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Content-Type", "application/json");
		
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(notifications.toString());
		wr.flush();
		
		
		int responseCode = conn.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());
		} else {
			System.out.println("POST request not worked");
		}
		conn.disconnect();
	}

	@Test
	void fileTest() throws EncryptedDocumentException, IOException, JSONException {

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
		Sheet sheet = sheets.get(0);
		Iterator<Row> rowIterator = sheet.rowIterator();
		int rowI = 0;
		JSONArray notifications = new JSONArray();
	
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Random random = new Random();
			boolean reminder = random.nextBoolean();
			boolean notify = random.nextBoolean();
			int indice = random.nextInt(statusInit.size()-1);
			JSONObject notification = new JSONObject();
			notification.put("reminder",reminder);
			notification.put("notify",notify);
			notification.put("status",statusInit.get(indice));
			Iterator<Cell> cellIterator = row.cellIterator();
			int i = 0;
			rowI++;
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String cellValue = dataFormatter.formatCellValue(cell);
				i++;
				if (columns.contains(i) && rowI > rowInitial) {
					notification.put(columsName.get(columns.indexOf(i)),cellValue);
				}
			}
			if (rowI > rowInitial) {
				System.out.println(notification);
				notifications.put(notification);
			}
		}		
		System.out.println(notifications);
		System.out.println(notifications.length());
		workbook.close();
		addNotifications(notifications);
	}

}
