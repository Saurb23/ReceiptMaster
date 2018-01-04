package controller;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import application.ResourceLoader;
import model.CashReportBean;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.XlsReportConfiguration;
import util.DBUtil;
//Rahul Shrirao 25/10/2017
public class Cash_Report_Day_Wise_Generator {
	public void CashReport_Day_Wise_Excel(CashReportBean sb) throws JRException, ClassNotFoundException, SQLException, IOException {
	    JasperReport jasperReport = JasperCompileManager.compileReport(ResourceLoader.load("/Report/CashMemoDayWise.jrxml"));
	    Connection conn = DBUtil.dbConnect();
	    // Parameters for report
		        Map<String, Object> parameters = new HashMap<String, Object>();
		        parameters.put("Fromdate",sb.getFromDate());
		        parameters.put("ToDate",sb.getToDate());

	            JasperPrint print;
	            print = JasperFillManager.fillReport(jasperReport,parameters, conn);

					        File outDir = new File("C:/jasperoutput/CashReport");
					        outDir.mkdirs();

					        // PDF Exportor.
					        //JRPdfExporter exporter = new JRPdfExporter();

					        //Excel Exporter
					        JRXlsExporter exporter = new JRXlsExporter();
					        ExporterInput exporterInput = new SimpleExporterInput(print);
					        // ExporterInput
					        exporter.setExporterInput(exporterInput);

					        // ExporterOutput
					        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					                "C:/jasperoutput/CashReport/"+sb.getFromDate()+"_To_"+sb.getToDate()+"CashMemoDayWise.xls");
					        // Output
					        exporter.setExporterOutput(exporterOutput);

					        XlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
					        exporter.setConfiguration(configuration);
					        exporter.exportReport();
					        try {
					        	String fileName="C:/jasperoutput/CashReport/"+sb.getFromDate()+"_To_"+sb.getToDate()+"CashMemoDayWise.xls";
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
	public void CashReport_Day_Wise_Pdf(CashReportBean sb) throws JRException, ClassNotFoundException, SQLException, IOException {

		JasperReport jasperReport = JasperCompileManager
	    .compileReport(ResourceLoader.load("/Report/CashMemoDayWise.jrxml"));
		Connection conn = DBUtil.dbConnect();

		// Parameters for report
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Fromdate",sb.getFromDate());
        parameters.put("ToDate",sb.getToDate());

        JasperPrint print;
        print = JasperFillManager.fillReport(jasperReport,parameters, conn);

	     File outDir = new File("C:/jasperoutput/CashReport");
	     outDir.mkdirs();

        //PDF Exportor.
        JRPdfExporter exporter = new JRPdfExporter();

        ExporterInput exporterInput = new SimpleExporterInput(print);
        //ExporterInput
        exporter.setExporterInput(exporterInput);

        //ExporterOutput
        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
                "C:/jasperoutput/CashReport/"+sb.getFromDate()+"_To_"+sb.getToDate()+"CashMemoDayWise.pdf");
        //Output
        exporter.setExporterOutput(exporterOutput);

        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        try {
        	String fileName="C:/jasperoutput/CashReport/"+sb.getFromDate()+"_To_"+sb.getToDate()+"CashMemoDayWise.pdf";
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
