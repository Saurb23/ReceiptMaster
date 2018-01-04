package controller;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import application.ResourceLoader;
import model.SalesEntry;
import model.SalesReportBean;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.XlsReportConfiguration;
import net.sf.jasperreports.export.XlsxReportConfiguration;
import util.DBUtil;

public class Sales_Report_Generator {

	
	public void SalesReportExcel(SalesReportBean sb) throws JRException, ClassNotFoundException, SQLException, IOException {
		
		JasperReport jasperReport = JasperCompileManager.compileReport(ResourceLoader.load("/Report/SalesReport.jrxml"));
	    Connection conn = DBUtil.dbConnect();

		 // Parameters for report
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("parameter1",sb.getFromDate());
        parameters.put("parameter2",sb.getToDate());

                    JasperPrint print;
                    print = JasperFillManager.fillReport(jasperReport,parameters, conn);

			        File outDir = new File("C:/jasperoutput/SalesReport");
			        outDir.mkdirs();

				    //Excel Exporter
				    JRXlsExporter exporter = new JRXlsExporter();
				    ExporterInput exporterInput = new SimpleExporterInput(print);

				    // ExporterInput
				    exporter.setExporterInput(exporterInput);

				    // ExporterOutput
				    OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
		    		"C:/jasperoutput/SalesReport/"+sb.getFromDate()+"_To_"+sb.getToDate()+"SalesReport.xls");
			        // Output
			        exporter.setExporterOutput(exporterOutput);

			        XlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			        exporter.setConfiguration(configuration);
			        exporter.exportReport();
			        try {
			        	String fileName="C:/jasperoutput/SalesReport/"+sb.getFromDate()+"_To_"+sb.getToDate()+"SalesReport.xls";
			    		if ((new File(fileName)).exists()) {

			    			if(Desktop.isDesktopSupported()) {
				    				Desktop.getDesktop().open(new File(fileName));
				    			}
				    		} else {

				    			System.out.println("File is not exists");

				    		}

				    		System.out.println("Done");

				      	  } catch (Exception ex) {
				    		ex.printStackTrace();
				    	  }
                         System.out.print("Done!");
}

	public void SalesReportPdf(SalesReportBean sb) throws JRException, ClassNotFoundException, SQLException, IOException {

		JasperReport jasperReport = JasperCompileManager
	     .compileReport(ResourceLoader.load("/Report/SalesReport.jrxml"));
		Connection conn = DBUtil.dbConnect();

		// Parameters for report
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("parameter1",sb.getFromDate());
        parameters.put("parameter2",sb.getToDate());


	        JasperPrint print;

		    print = JasperFillManager.fillReport(jasperReport,parameters, conn);

	        File outDir = new File("C:/jasperoutput/SalesReport");
	        outDir.mkdirs();

	        // PDF Exportor.
	        JRPdfExporter exporter = new JRPdfExporter();

	        ExporterInput exporterInput = new SimpleExporterInput(print);
	        // ExporterInput
	        exporter.setExporterInput(exporterInput);

	        // ExporterOutput
	        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
	                "C:/jasperoutput/SalesReport/"+sb.getFromDate()+"_To_"+sb.getToDate()+"SalesReport.pdf");
	        // Output
	        exporter.setExporterOutput(exporterOutput);

	        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
	        exporter.setConfiguration(configuration);
	        exporter.exportReport();
	        try {
	        	String fileName="C:/jasperoutput/SalesReport/"+sb.getFromDate()+"_To_"+sb.getToDate()+"SalesReport.pdf";
	    		if ((new File(fileName)).exists()) {

	    			if(Desktop.isDesktopSupported()) {
	    				Desktop.getDesktop().open(new File(fileName));
	    			}

	    		} else {

	    			System.out.println("File is not exists");

	    		}

	    		System.out.println("Done");

	      	  } catch (Exception ex) {
	    		ex.printStackTrace();
	    	  }



	        System.out.print("Done!");
}
}