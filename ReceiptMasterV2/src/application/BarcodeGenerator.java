package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import model.Product;
//import net.sourceforge.barbecue.Barcode;
//import net.sourceforge.barbecue.BarcodeException;
//import net.sourceforge.barbecue.BarcodeFactory;
//import net.sourceforge.barbecue.BarcodeImageHandler;
//import net.sourceforge.barbecue.output.OutputException;

public class BarcodeGenerator {
	
	public static String generateBarcode(Product product) {
//	String str = "DEFGHI";
	String barEncrypt="";   
System.out.println("Rahul Change here ");
//    Barcode barcode=null;
//	try {
//		String productData=product.getProduct_name()+""+product.getUnit()+""+product.getHsnNo()+""+product.getSellPrice()+""+product.getCategory()+""+product.getSubGroup();
		String prodName=product.getProduct_name().substring(0, 1);
		
		String unit=product.getUnit().substring(0,1);
//		Double d=product.getBuyPrice();
		
		String productData=prodName+unit;
		
		char[] ch=productData.toLowerCase().toCharArray();
		for(char c:ch) {
			int temp=(int) c;
			int temp_integer=96;
			if(temp<=122&temp>=97) {
				barEncrypt=barEncrypt+(temp-temp_integer);
			}
		}
		barEncrypt=barEncrypt+product.getHsnNo();
		
//		barcode = BarcodeFactory.createCode128B(barEncrypt);
//		  barcode.setBarHeight(60);
//		    barcode.setBarWidth(2);
//		    System.out.println(barcode);
//	} catch ( Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
  

		final int dpi = 300;
	Code128Bean code128 = new Code128Bean();
	code128.setHeight(15f);
	code128.setModuleWidth(0.3);
//	code128.setFontSize(8);
//	code128.setQuietZone(5);
	code128.doQuietZone(false);

//	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
	//write to png file
//	FileOutputStream fos;
//	try {
//		fos = new FileOutputStream("c:/jasperoutput/".png");
//		fos.write(baos.toByteArray());
//    	fos.flush();
//    	fos.close();
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	File dir=new File("C:/jasperoutput/BarcodeImg/");
	dir.mkdirs();
	
	File outputFile = new File("C:/jasperoutput/BarcodeImg/"+product.getProduct_name().replaceAll(" ", "")+".png");
	OutputStream out=null;
    try {
		out = new FileOutputStream(outputFile);
		 BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
			code128.generateBarcode(canvas, barEncrypt);
			
			canvas.finish();
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
   
	

  
//    try {
//		BarcodeImageHandler.savePNG(barcode, file);
//	} catch (OutputException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
    
    return barEncrypt;

}
	
	

}
