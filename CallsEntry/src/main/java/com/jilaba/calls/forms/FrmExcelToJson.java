package com.jilaba.calls.forms;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.sql.*;
import java.util.*;

public class FrmExcelToJson {

	public static void main(String[] args) {
		String excelPath = "C:/excels/sample.xlsx";
		String fileName = new File(excelPath).getName();

		try {
			List<ObjectNode> jsonData = readExcelAsJson(excelPath);
			String jsonString = new ObjectMapper().writeValueAsString(jsonData);
			insertJsonToSQL(fileName, jsonString);
			System.out.println("✅ Uploaded successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Read Excel File and convert rows to List of JSON objects
	private static List<ObjectNode> readExcelAsJson(String excelPath) throws Exception {
		List<ObjectNode> dataList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();

		try (Workbook workbook = new XSSFWorkbook(new File(excelPath))) {
			Sheet sheet = workbook.getSheetAt(0); // First sheet
			Row headerRow = sheet.getRow(0);

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				ObjectNode jsonObject = mapper.createObjectNode();

				for (int j = 0; j < headerRow.getLastCellNum(); j++) {
					String columnName = headerRow.getCell(j).toString();
					Cell cell = row.getCell(j);
					String value = (cell != null) ? cell.toString() : "";
					jsonObject.put(columnName, value);
				}
				dataList.add(jsonObject);
			}
		}

		return dataList;
	}

	// Insert JSON into SQL Server table
	private static void insertJsonToSQL(String fileName, String json) throws Exception {
		String url = "jdbc:sqlserver://localhost:1433;databaseName=your_db;encrypt=true;trustServerCertificate=true;";
		String user = "your_username";
		String password = "your_password";

		String sql = "INSERT INTO ExcelJsonStore (FileName, JsonContent) VALUES (?, ?)";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, fileName);
			ps.setString(2, json);
			ps.executeUpdate();
		}
	}
}
