package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import application.ResourceLoader;
import model.DeliveryMemo;
import model.StockTransfer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import util.DBUtil;

public class StockTransferGenerator {
	public void stockTransferMemo(StockTransfer stockTransfer)throws JRException, ClassNotFoundException, SQLException, IOException {

		JasperReport jasperReport = JasperCompileManager
	     .compileReport(ResourceLoader.load("/Report/StockTransferMemo.jrxml"));
		Connection conn = DBUtil.dbConnect();

		// Parameters for report
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("TransferNo",stockTransfer.getTransferNo());
       

	        JasperPrint print;

		    print = JasperFillManager.fillReport(jasperReport,parameters, conn);

	        File outDir = new File("C:/jasperoutput/StockTransfer/");
	        outDir.mkdirs();

	        // PDF Exportor.
	        JRPdfExporter exporter = new JRPdfExporter();

	        ExporterInput exporterInput = new SimpleExporterInput(print);
	        // ExporterInput
	        exporter.setExporterInput(exporterInput);

	        // ExporterOutput
	        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
	                "C:/jasperoutput/StockTransfer/"+stockTransfer.getTransferNo()+"_To_"+stockTransfer.getBranchname()+"_Transfer.pdf");
	        // Output
	        exporter.setExporterOutput(exporterOutput);

	        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
	        exporter.setConfiguration(configuration);
	        exporter.exportReport();
	        try {
	        	String fileName="C:/jasperoutput/StockTransfer/"+stockTransfer.getTransferNo()+"_To_"+stockTransfer.getBranchname()+"_Transfer.pdf";
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
