package com.patient.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import com.patient.entity.Patient;

public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Name", "Address", "DOB", "Email", "Number", "Status", "DrugId", "DrugName" };
	static String SHEET = "Patients";


	public static boolean hasExcelFormat(MultipartFile file) {

		System.out.println("incontroller inside try");

		if (!TYPE.equals(file.getContentType())) {
			System.out.println("file format correct inside if"); return false;

		}else { System.out.println("file format incorrect inside else"); }

		return true; }

	public static ByteArrayInputStream patientsToExcel(List<Patient> patients) {

		try (
				
			XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			XSSFSheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}

			int rowIdx = 1;
			for (Patient patient : patients) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(patient.getId());
				row.createCell(1).setCellValue(patient.getName());
				row.createCell(2).setCellValue(patient.getAddress());
				row.createCell(3).setCellValue(patient.getDob());
				row.createCell(4).setCellValue(patient.getEmail());
				row.createCell(5).setCellValue(patient.getNumber());
				row.createCell(6).setCellValue(patient.getStatus());
				row.createCell(7).setCellValue(patient.getDrugId());
				row.createCell(8).setCellValue(patient.getDrugName());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static List<Patient> excelToPatients(InputStream is) {
		try {
			System.out.println("helper 1 ");
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			System.out.println("helper 2 ");
			XSSFSheet sheet = workbook.getSheetAt(0);
			System.out.println("helper 3 ");
			Iterator<Row> rows = sheet.iterator();
			System.out.println("helper 4 ");

			List<Patient> patients = new ArrayList<Patient>();

			int rowNumber = 0;

			System.out.println("helper 5 ");
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				Patient patient = new Patient();

				int cellIdx = 0;
				System.out.println("helper 6 "+cellsInRow);
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						patient.setId((long) currentCell.getNumericCellValue());
						break;

					case 1:
						patient.setName(currentCell.getStringCellValue());
						break;

					case 2:
						patient.setAddress(currentCell.getStringCellValue());
						break;

					case 3:
						patient.setDob(currentCell.getDateCellValue());
						break;
						
					case 4:
						patient.setEmail(currentCell.getStringCellValue());
						break;

					case 5:
						patient.setNumber((long) currentCell.getNumericCellValue());
						break;
						
					case 6:
						patient.setStatus(currentCell.getStringCellValue());
						break;

					case 7:
						patient.setDrugId((long) currentCell.getNumericCellValue());
						break;

					case 8:
						patient.setDrugName(currentCell.getStringCellValue());
						break;
						
					default:
						break;
						
					}

					cellIdx++;
				}

				patients.add(patient);
			}

			workbook.close();

			return patients;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
