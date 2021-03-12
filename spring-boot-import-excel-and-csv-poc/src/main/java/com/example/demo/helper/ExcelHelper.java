package com.example.demo.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.example.demo.model.Student;
import org.springframework.stereotype.Component;

@Component
public class ExcelHelper {
	public List<Student> excelImport() {
		String excelFilePath = "C:\\Users\\BHUMESH\\Downloads\\Student2.xlsx";
		List<Student> stuList = new ArrayList<Student>();
		try {
			long start = System.currentTimeMillis();
			FileInputStream inputStream = new FileInputStream(excelFilePath);
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();
			rowIterator.next(); // skip the header row
			Student student = null;
			while (rowIterator.hasNext()) {
				student = new Student();
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();
					switch (columnIndex) {
//                        case 0:
//                            long id = (long) nextCell.getNumericCellValue();
//                            student.setId(id);
//                            break;
					case 1:
						String fname = nextCell.getStringCellValue();
						student.setFname(fname);
						break;
					case 2:
						String lname = nextCell.getStringCellValue();
						student.setLname(lname);
						break;
					}
				}
				stuList.add(student);
			}
			workbook.close();
			long end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));
		} catch (Exception ex) {
			System.out.println("Error reading file");
			ex.printStackTrace();
		}
		return stuList;
	}
}