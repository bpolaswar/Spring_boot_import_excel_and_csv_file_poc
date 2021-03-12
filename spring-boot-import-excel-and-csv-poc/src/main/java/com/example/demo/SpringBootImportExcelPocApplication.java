package com.example.demo;

import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.helper.CSVHelper;
import com.example.demo.helper.ExcelHelper;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootImportExcelPocApplication {
	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootImportExcelPocApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			// for excel file import
			ExcelHelper excelHelper = new ExcelHelper();
			studentRepository.saveAll(excelHelper.excelImport());
			// for csv file import
			CSVHelper csvHelper = new CSVHelper();
			if (null != csvHelper.csvImportApacheCommons()) {
				studentRepository.saveAll(csvHelper.csvImportApacheCommons());
			} else {
				System.out.println("csvImportApacheCommons() is called with 0 students");
			}
		};
	}
}
