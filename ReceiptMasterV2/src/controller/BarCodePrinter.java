package controller;

	import java.awt.Desktop;
	import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
	import java.sql.Connection;
	import java.sql.SQLException;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
	import java.util.List;
	import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import application.ResourceLoader;
import model.Item;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
	import net.sf.jasperreports.engine.JasperCompileManager;
	import net.sf.jasperreports.engine.JasperFillManager;
	import net.sf.jasperreports.engine.JasperPrint;
	import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
	import net.sf.jasperreports.export.ExporterInput;
	import net.sf.jasperreports.export.OutputStreamExporterOutput;
	import net.sf.jasperreports.export.SimpleExporterInput;
	import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
	import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
	import util.DBUtil;

public class BarCodePrinter {
	


		public void generateBarcode(List<Item> itemList) throws JRException, ClassNotFoundException, SQLException, IOException {
			
			SimpleDateFormat sd= new SimpleDateFormat("dd-MM-yyyy");
			
			
			Date date=Calendar.getInstance().getTime();
			String d=sd.format(date);
			
//			
			JasperReport jasperReport = JasperCompileManager
		    .compileReport(ResourceLoader.load("/Report/BARCODE.jrxml"));
			Connection conn = DBUtil.dbConnect();

			// Parameters for report
		    Map<String, Object> parameters = new HashMap<String, Object>();
		    
		    JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(itemList);
//		    for(Item i:itemList) {
//		    	 System.out.println(i.getCompanyName()+" "+i.getBarcodePath()+" "+i.getPrice()+" "+i.getProductName());
//		    	 
//		    	 
//		    }
		  System.out.println(itemList.size()); 
		    parameters.put("ItemDataSource", itemsJRBean);
//		    parameters.put("id",prodList.get(0));
//		    parameters.put("id1",prodList.get(prodList.size()-1));

		    JasperPrint print;
		   // print = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
		    JasperPrint jasperPrint = JasperFillManager.fillReport(ResourceLoader.load("/Report/BARCODE.jasper"), parameters, new JREmptyDataSource());
		    File outDir = new File("C:/jasperoutput/BarcodeGenerator/");
		    outDir.mkdirs(); 

		    // PDF Exportor.
		    JRPdfExporter exporter = new JRPdfExporter();

		    ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		    // ExporterInput
		    exporter.setExporterInput(exporterInput);
		    String fileName="C:/jasperoutput/BarcodeGenerator/Barcode_"+d+Math.random()+".pdf";
		    // ExporterOutput
		    OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
		    		fileName);
		    
		    // Output
		    exporter.setExporterOutput(exporterOutput);
		    
		    SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		    exporter.setConfiguration(configuration);
		    exporter.exportReport();
		try {
		    	//String fileName="C:/jasperoutput/BarcodeGenerator/Barcode_"+d+".pdf";
				if ((new File(fileName)).exists()) {
					if(Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(new File(fileName));
					}
		    		} else {
		    			System.out.println("File not exists");
		    		}
		    		System.out.println("Done");
		      	  } catch (Exception ex) {
		    		ex.printStackTrace();
		    	  }
		        System.out.print("Done!");
			}

	}

