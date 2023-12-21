package com.optum.pdfdemo;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class PdfDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfDemoApplication.class, args);
		// testing App
		testPDF();
	}
	private static String testPDF() {
        System.out.println("Hi... FOP PDF_Testing");
        ArrayList employeeList = new ArrayList();
        String templateFilePath = "D:\\pdfReport\\pdfdemo\\src\\main\\resources\\templates\\";

        Employee e1 = new Employee();
        e1.setName("MaheshP1 Parmar");
        e1.setEmployeeId("5001");
        e1.setAddress("Pune");
        employeeList.add(e1);

        Employee e2 = new Employee();
        e2.setName("MaheshP2 Parmar");
        e2.setEmployeeId("5002");
        e2.setAddress("Hyd");
        employeeList.add(e2);

        Employee e3 = new Employee();
        e3.setName("MaheshP3 Parmar");
        e3.setEmployeeId("5003");
        e3.setAddress("Delhi");
        employeeList.add(e3);

        EmployeeData data = new EmployeeData();
        data.setEemployeeList(employeeList);
        PDFHandler handler = new PDFHandler();


        String response = null;
        try {
            ByteArrayOutputStream streamSource = handler.getXMLSource(data);
            response = handler.createPDFFile(streamSource, templateFilePath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("PDF Response: " + response);
        return response;
    }
}
