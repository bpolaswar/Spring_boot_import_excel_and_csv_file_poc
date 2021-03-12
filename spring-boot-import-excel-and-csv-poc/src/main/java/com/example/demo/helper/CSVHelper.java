package com.example.demo.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import com.example.demo.model.Student;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

@Component
public class CSVHelper {
	public List<Student> csvImport() {
		List<Student> studentList = new ArrayList<>();
		Student student = null;
		try {
			long start = System.currentTimeMillis();
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\BHUMESH\\Downloads\\StudentCSV.csv"));
			br.readLine();
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				student = new Student();
				student.setFname(data[1]);
				student.setLname(data[2]);
				studentList.add(student);
			}
			long end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return studentList;
	}

	public List<Student> csvImportOpenCvs() {
		CSVReader csvReader = null;
		try {
			long start = System.currentTimeMillis();
			Map<String, String> mapping = new HashMap<String, String>();
			mapping.put("id", "id");
			mapping.put("fname", "fname");
			mapping.put("lname", "lname");
			HeaderColumnNameTranslateMappingStrategy<Student> strategy = new HeaderColumnNameTranslateMappingStrategy<Student>();
			strategy.setType(Student.class);
			strategy.setColumnMapping(mapping);
			csvReader = new CSVReader(new FileReader("C:\\Users\\BHUMESH\\Downloads\\StudentCSV.csv"));
			CsvToBean<Student> csvToBean = new CsvToBean<Student>();
			// call the parse method of CsvToBean
			// pass strategy, csvReader to parse method
			@SuppressWarnings("deprecation")
			List<Student> studentList = csvToBean.parse(strategy, csvReader);
			long end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));
			return studentList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Student> csvImportApacheCommons() {
		List<Student> studentList = new ArrayList<>();
		Student student = null;
		try {
			long start = System.currentTimeMillis();
			String csvFilePath = "C:\\Users\\BHUMESH\\Downloads\\StudentCSV.csv";
			Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
			CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
			CSVParser csvParser = csvFormat.parse(reader);
			List<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				student = new Student();
//				student.setId(Long.parseLong(csvRecord.get(0)));
				student.setFname(csvRecord.get(1));
				student.setLname(csvRecord.get(2));
				studentList.add(student);
			}
			long end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return studentList;
	}

}
