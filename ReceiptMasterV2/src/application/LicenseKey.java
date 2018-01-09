package application;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.itextpdf.awt.geom.misc.RenderingHints.Key;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class LicenseKey {
	
	static TextField firstFourTxt= new TextField();
	static TextField secondFourTxt= new TextField();
	static TextField thirdFourTxt= new TextField();
	static TextField fourthFourTxt= new TextField();
	static HttpWebServices httpWebServices= new HttpWebServices();
	 private static Tooltip errorTip= new Tooltip();
	private static final int LIMIT=4;
	final static KeyCombination keyCombinationCtrlV = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
	
	public static GridPane generateLicense() {
		
		System.out.println("Rahul Shrirao -------============-----------");
		GridPane gp = new GridPane();
		gp.getStyleClass().add("grid");
		gp.setHgap(20);
		gp.setVgap(20);
		gp.setGridLinesVisible(false);
		gp.setAlignment(Pos.CENTER);
		
		Label titleLabel = new Label("Enter License Key ");
		GridPane.setMargin(titleLabel, new Insets(0, -100, 0, 100));
		gp.add(titleLabel, 0, 0);
		
		Label dashLbl1= new Label("-");
		dashLbl1.setStyle("-fx-font-weight:bold");
		Label dashLbl2= new Label("-");
		dashLbl2.setStyle("-fx-font-weight:bold");
		Label dashLbl3= new Label("-");
		dashLbl3.setStyle("-fx-font-weight:bold");
		
//		Label dashLbl4= new Label("-");
//		dashLbl4.setStyle("-fx-font-weight:bold");
		
		
		HBox licenseHB= new HBox();
		
		firstFourTxt.setPrefWidth(80);
		secondFourTxt.setPrefWidth(80);
		thirdFourTxt.setPrefWidth(80);
		fourthFourTxt.setPrefWidth(80);
		validateOnFocus(firstFourTxt);
		validateOnFocus(secondFourTxt);
		validateOnFocus(thirdFourTxt);
		validateOnFocus(fourthFourTxt);
		licenseHB.setSpacing(5);
		licenseHB.getChildren().addAll(firstFourTxt,dashLbl1,secondFourTxt,dashLbl2,thirdFourTxt,dashLbl3,fourthFourTxt);
		gp.add(licenseHB, 0, 2);
		
		JFXButton submitBtn= new JFXButton("Submit");
		GridPane.setMargin(submitBtn, new Insets(0,-100,0,100));
		gp.add(submitBtn, 0, 4);
		
		
		
		firstFourTxt.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				// TODO Auto-generated method stub
				if(newValue.intValue()>oldValue.intValue()) {
					if(firstFourTxt.getText()!=null) {
						if(firstFourTxt.getText().length()>=LIMIT) {
								firstFourTxt.setText(firstFourTxt.getText().substring(0,LIMIT));
								secondFourTxt.requestFocus();
						}
					}
				}
			}
			
		});
		
		secondFourTxt.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				// TODO Auto-generated method stub
				if(newValue.intValue()>oldValue.intValue()) {
					if(secondFourTxt.getText()!=null) {
						if(secondFourTxt.getText().length()>=LIMIT) {
							secondFourTxt.setText(secondFourTxt.getText().substring(0,LIMIT));
								thirdFourTxt.requestFocus();
						}
					}
				}
			}
		});
//		
		thirdFourTxt.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				// TODO Auto-generated method stub
				if(newValue.intValue()>oldValue.intValue()) {
					if(thirdFourTxt.getText()!=null) {
						if(thirdFourTxt.getText().length()>=LIMIT) {
							thirdFourTxt.setText(thirdFourTxt.getText().substring(0,LIMIT));
								fourthFourTxt.requestFocus();
						}
					}
				}
			}
		});
//		
//		
		fourthFourTxt.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				// TODO Auto-generated method stub
				if(newValue.intValue()>oldValue.intValue()) {
					if(fourthFourTxt.getText()!=null) {
						if(fourthFourTxt.getText().length()>=LIMIT) {
							fourthFourTxt.setText(fourthFourTxt.getText().substring(0,LIMIT));
						}
					}
				}
			}
		});
//		
		Label loadingLbl= new Label("Please wait....");
		loadingLbl.setStyle("-fx-text-fill:black;-fx-font-size:15");
		
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				errorTip.hide();
			if(!validateLicense()) {
				return;
			}
			submitBtn.setDisable(true);
			gp.getChildren().remove(submitBtn);
			
			GridPane.setMargin(loadingLbl, new Insets(0,-100,0,100));
			gp.add(loadingLbl, 0, 4);
			
			if(!SmsSender.checkConnection()) {
				Alert alert = new Alert(AlertType.WARNING, "Internet Connection is required for License Verification..!");
				alert.setTitle("No Internet Found");
				alert.showAndWait();
				submitBtn.setDisable(false);
				return;
			}
			
			try {
				String reponseCode_GetMethod=httpWebServices.sendGet(firstFourTxt.getText()+secondFourTxt.getText()+thirdFourTxt.getText()+fourthFourTxt.getText());
				System.out.println(reponseCode_GetMethod);
				if(reponseCode_GetMethod.toString().contains("Hardware Not Avalaible")) {
					String value = "";
//			        value = WinRegistry.readString(
//			            WinRegistry.HKEY_LOCAL_MACHINE,
//			            "SOFTWARE\\Microsoft\\Cryptography",
//			            "MachineGuid");
//			          System.out.println("MachineGuid = " + value);
			          
			          InetAddress address;
			          StringBuilder sb= new StringBuilder();
						try {
							address = InetAddress.getLocalHost();
							 NetworkInterface networkInterface=NetworkInterface.getByInetAddress(address);
							 byte macAddress[]=networkInterface.getHardwareAddress();
							
							 for (int byteIndex = 0; byteIndex < macAddress.length; byteIndex++) {
								   sb.append(String.format("%02X%s", macAddress[byteIndex], (byteIndex < macAddress.length - 1) ? "-" : ""));
								}
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						
						value=sb.toString();
			  	      Calendar cal = new GregorianCalendar();
			  	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d hh:mm:ss");
			  	    
			  	      String Reg_Date=sdf.format(cal.getTime());
			  	      System.out.println(Reg_Date);
			  	      cal.add(Calendar.DAY_OF_MONTH, 365);
			  	     
			  	      String Exp_Date = sdf.format(cal.getTime());
			  	      System.out.println(Exp_Date);
			  	      String status1 = "1";

				String Post_Response=	httpWebServices.sendPost(firstFourTxt.getText()+secondFourTxt.getText()+thirdFourTxt.getText()+fourthFourTxt.getText(),value,Reg_Date,Exp_Date,status1);
//				System.out.println(Post_Response);
//					if(Post_Response.equals("1")) {
//						SplashScreen.popup.close();
//						SplashScreen.mainStage.show();
//					}
				} else if(reponseCode_GetMethod.toString().contains("")) {
					firstFourTxt.clear();
					secondFourTxt.clear();
					thirdFourTxt.clear();
					fourthFourTxt.clear();
					Alert alert = new Alert(AlertType.INFORMATION,"No License Key found!Please register for the new License");
					alert.setTitle("Information");
					alert.showAndWait();
					submitBtn.setDisable(false);
//					System.exit(0);
				
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//SplashScreen.popup.close();
			//SplashScreen.mainStage.show();
			}
		});
		
		ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
        closeImg.setFitHeight(30);
        closeImg.setFitWidth(30);
        closeImg.setStyle("-fx-cursor:hand");
        GridPane.setMargin(closeImg, new Insets(0,0,50,-50));
        gp.add(closeImg, 1, 0);

        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

    		@Override
    		public void handle(MouseEvent arg0) {
    			// TODO Auto-generated method stub
    			SplashScreen.popup.close();
    			
    		}

    	});
        
        gp.addEventFilter(KeyEvent.KEY_PRESSED, e->{
			if(keyCombinationCtrlV.match(e)) {
			Clipboard clipboard=Clipboard.getSystemClipboard();
			String text=clipboard.getString();
			if(text!=null) {
				text=text.replaceAll("-", "");
			
			
			if(text.length()==16) {
				firstFourTxt.setText(text.substring(0, 4));
				secondFourTxt.setText(text.substring(4,8));
				thirdFourTxt.setText(text.substring(8,12));
				fourthFourTxt.setText(text.substring(12,16));
				fourthFourTxt.requestFocus();
			}
			e.consume();
			}
			}else if(e.getCode()==KeyCode.ENTER) {
				submitBtn.fire();
				e.consume();
			}
			
		});
		
        
      
		return gp;	
		
	}
	
	public static boolean validateLicense() {
		if(firstFourTxt.getText().isEmpty()) {
			errorTip.setText("Please enter license key");
			firstFourTxt.setTooltip(errorTip);
			errorTip.show(firstFourTxt,500,200);
			return false;
		}
		if(secondFourTxt.getText().isEmpty()) {
			errorTip.setText("Please enter license key");
			secondFourTxt.setTooltip(errorTip);
			errorTip.show(secondFourTxt,500,200);
			return false;
		}
		if(thirdFourTxt.getText().isEmpty()) {
			errorTip.setText("Please enter license key");
			thirdFourTxt.setTooltip(errorTip);
			errorTip.show(thirdFourTxt,500,200);
			return false;
		}
		if(fourthFourTxt.getText().isEmpty()) {
			errorTip.setText("Please enter license key");
			fourthFourTxt.setTooltip(errorTip);
			errorTip.show(fourthFourTxt,500,200);
			return false;
		}
		
		return true;
		
		
	}
	
	public static boolean validateOnFocus(TextField textField) {
		textField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}

			

		});
	
		return true;
	}

}
