package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import util.*;
import javafx.collections.ObservableList;
import model.SalesEntry;
import model.SalesProduct;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
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
public class Receipt_Master_Invoice {

/* Author Rahul Shrirao Completed on 13/09/2017 */
	public Receipt_Master_Invoice(){
		DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
		JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.xpath.executer.factory",
		    "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
	}
	public void showReport(SalesEntry salesEntry) throws JRException, ClassNotFoundException, SQLException, IOException {

                       JasperReport jasperReport = JasperCompileManager
				      .compileReport(ResourceLoader.load("/Report/Receipt_master_Invoice.jrxml"));
//                    		   .compileReport(ResourceLoader.load("/Report/Receipt_Simplified.jrxml"));

				       Connection conn = DBUtil.dbConnect();

//				       DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//						Date date = new Date();
//						System.out.println(dateFormat.format(date));
//
				       // Parameters for report
				        Map<String, Object> parameters = new HashMap<String, Object>();
				        parameters.put("invoice_no",salesEntry.getInvoice_no());

//						Date date2 = new Date();
//						System.out.println(dateFormat.format(date2));
				        JasperPrint print;

					    print = JasperFillManager.fillReport(jasperReport,parameters, conn);

//					    Date date3 = new Date();
//						System.out.println(dateFormat.format(date3));
				        // Make sure the output directory exists.
				        File outDir = new File("C:/jasperoutput/SalesInvoice/");
				        outDir.mkdirs();

				        // PDF Exportor.
				        JRPdfExporter exporter = new JRPdfExporter();

				        ExporterInput exporterInput = new SimpleExporterInput(print);
				        // ExporterInput
				        exporter.setExporterInput(exporterInput);

				        // ExporterOutput
				        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				                "C:/jasperoutput/SalesInvoice/"+salesEntry.getInvoice_no()+"_"+salesEntry.getCustomer().getFull_name()+"_Invoice.pdf");
				        // Output
				        exporter.setExporterOutput(exporterOutput);

				        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
				        exporter.setConfiguration(configuration);
				        exporter.exportReport();
				        try {
				        	String fileName="C:/jasperoutput/SalesInvoice/"+salesEntry.getInvoice_no()+"_"+salesEntry.getCustomer().getFull_name()+"_Invoice.pdf";
				    		if ((new File(fileName)).exists()) {

				    			if(Desktop.isDesktopSupported()) {
				    				Desktop.getDesktop().open(new File(fileName));
				    			}
//				    			Process p = Runtime
//				    			   .getRuntime()
//				    			   .exec("rundll32 url.dll,FileProtocolHandler C:\\jasperoutput/Receipt_master_Invoice.pdf");
//				    			p.waitFor();

				    		} else {

				    			System.out.println("File is not exists");

				    		}

				    		System.out.println("Done");

				      	  } catch (Exception ex) {
				    		ex.printStackTrace();
				    	  }



				        System.out.print("Done!");
}}