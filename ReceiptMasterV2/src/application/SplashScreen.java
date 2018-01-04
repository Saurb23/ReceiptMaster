package application;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Box;

import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.application.LauncherImpl;

import dao.ConnectionDAO;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;
import model.SMSBacklog;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import util.DBUtil;

/**
 *@author Saurabh Gupta
 */
public class SplashScreen extends Application {
	
//    public static final String APPLICATION_ICON =
//            "http://cdn1.iconfinder.com/data/icons/Copenhagen/PNG/32/people.png";
    public static final String SPLASH_IMAGE =
            "/images/Receipt Master splash 21.jpg";

    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    public static  Stage mainStage;
   	public static Stage popup= new  Stage();
    private  static final int SPLASH_WIDTH = 780;
    private static final int SPLASH_HEIGHT = 400;
    
   
    
    JFXTextField userTxt = new JFXTextField();
    JFXPasswordField password= new JFXPasswordField();
    
    public static JFXTextField dbName= new JFXTextField();
	public static JFXTextField userName= new JFXTextField();
	public static JFXPasswordField passTxt= new JFXPasswordField();
    
    private Tooltip errorTip= new Tooltip();
    
    ConnectionDAO connectionDAO= new ConnectionDAO();
    
    Main mainClass;

    public static void main(String[] args) throws Exception {
    	String appId="SplashScreen.MainId";
//    	System.out.println("Started");
    	boolean alreadyRunning;
    	try {
    	JUnique.acquireLock(appId);
    	
    	
        
//    	String value = "";
//        value = WinRegistry.readString(
//            WinRegistry.HKEY_LOCAL_MACHINE,
//            "SOFTWARE\\Microsoft\\Cryptography",
//            "MachineGuid");
//        System.out.println("MachineGuid = " + value);
//        
//        FileWriter fileWriter= new FileWriter(new File("c:/jasperoutput/machin_guid.txt"));
//		PrintWriter printWriter= new PrintWriter(fileWriter);
//		printWriter.println(value);
//		printWriter.close();
    	alreadyRunning=false;
    	}catch (AlreadyLockedException e) {
    		alreadyRunning = true;
    	}
    	if (!alreadyRunning) {
    		Locale.setDefault(Locale.UK);
    		
    		launch(args);
    	}
    	
        
    }
    
   
//    @Override
//    public void stop() {
////    	Main.primaryStage.show();
//    }
    
    @Override
    public void init() {
        ImageView splash = new ImageView(new Image(
                SPLASH_IMAGE
        ));
        loadProgress = new ProgressBar();
        loadProgress.setStyle("-fx-accent: #28CD7B; -fx-background:transparent");
//        loadProgress.setBackground(new Background(fills)));
        loadProgress.setPrefWidth(SPLASH_WIDTH  -20);
//        progressText = new Label("Will find friends for peanuts . . .");
        splashLayout = new VBox();
        VBox.setMargin(loadProgress, new Insets(-50,0,20,0));
        splashLayout.getChildren().addAll(splash, loadProgress);
//        progressText.setAlignment(Pos.CENTER);
//        splashLayout.setStyle(
//                "-fx-padding: 5; " +
//                "-fx-background-color: cornsilk; " +
//                "-fx-border-width:5; " +
//                "-fx-border-color: " +
//                    "linear-gradient(" +
//                        "to bottom, " +
//                        "chocolate, " +
//                        "derive(chocolate, 50%)" +
//                    ");"
//        );
//        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) throws Exception {
    	
        final Task<ObservableList<String>> friendTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {
                ObservableList<String> foundFriends =
                        FXCollections.<String>observableArrayList();
//                ObservableList<String> availableFriends =
//                        FXCollections.observableArrayList(
//                                "Fili", "Kili", "Oin", "Gloin", "Thorin",
//                                "Dwalin", "Balin", "Bifur", "Bofur",
//                                "Bombur", "Dori", "Nori", "Ori"
//                        );
//
//                updateMessage("Finding friends . . .");
//                for (int i = 0; i < availableFriends.size(); i++) {
             
//                "c:\wamp\bin\apache\apache2.4.9\bin\httpd.exe
                    Thread.sleep(8000);
                    
//                    InetAddress address;
//					try {
//						address = InetAddress.getLocalHost();
//						 NetworkInterface networkInterface=NetworkInterface.getByInetAddress(address);
//						 byte macAddress[]=networkInterface.getHardwareAddress();
//						 
//						 for (int byteIndex = 0; byteIndex < macAddress.length; byteIndex++) {
//							    System.out.format("%02X%s", macAddress[byteIndex], (byteIndex < macAddress.length - 1) ? "-" : "");
//							}
//					} catch (UnknownHostException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (SocketException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//                   
                    
//                    updateProgress(i + 1, availableFriends.size());
//                    String nextFriend = availableFriends.get(i);
//                    foundFriends.add(nextFriend);
//                    updateMessage("Finding friends . . . found " + nextFriend);
//                }
//                Thread.sleep(400);
//                updateMessage("All friends found.");

                return foundFriends;
            }
        };

        showSplash(
                initStage,
                friendTask,
                () -> showMainStage()
        );
        new Thread(friendTask).start();
    }

    public  void showMainStage() {

////	Platform.setImplicitExit(true);
    	 mainStage = new Stage(StageStyle.TRANSPARENT);
 
    	 GridPane gp= new GridPane();
    	 gp=LicenseKey.generateLicense();
    //
         Scene scene1= new Scene(gp,500,400);
    // scene1.setFill(Color.TRANSPARENT);
         scene1.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
         scene1.setFill(Color.TRANSPARENT);
    // 
         popup.setScene(scene1);
         popup.initModality(Modality.APPLICATION_MODAL);
         popup.initStyle(StageStyle.TRANSPARENT);
//         popup.setScene(scene1);
         //popup.show();	
	    	
//		file.createNewFile();
    	
       
        mainStage.getIcons().add(new Image(ResourceLoader.load("/images/receiptMasterLOGO.png")));
        VBox vBox= new VBox(10);
        GridPane gridPane = new GridPane();
//      AnchorPane anchorPane= new AnchorPane();
        vBox.setMaxSize(400, 400);
        vBox.setStyle("-fx-background-color:#000d1a; -fx-background-radius: 15;"
				+ "-fx-effect: dropshadow(gaussian, #66ffcc, 50, 0, 0, 0);" + "-fx-background-insets: 50;");
        
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(30); 
		gridPane.setGridLinesVisible(false);
		
		Label titleLbl = new Label("USER LOGIN");
		
		titleLbl.setStyle("-fx-text-fill:#66ffcc;");
		JFXRippler rippler = new JFXRippler(titleLbl);
		rippler.setAlignment(Pos.CENTER);
		rippler.setStyle("-fx-font-size:25;");
		gridPane.add(rippler, 0, 0);
        
        userTxt.setText("admin");
        userTxt.setPromptText("User");
        userTxt.setLabelFloat(true);
        userTxt.setStyle("-fx-text-fill:#66ffcc;-fx-font-size:16;");
        userTxt.setFocusColor(Color.valueOf("#66ffcc"));
        userTxt.setUnFocusColor(Color.valueOf("#C8C8C8"));
        userTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (newValue!=null) {
					errorTip.hide();
				}
			}
		});
        
        
        gridPane.add(userTxt, 0, 2);
       
        password.setText("123");
        password.setPromptText("Password");
        password.setStyle("-fx-text-fill:#66ffcc;-fx-font-size:16;");
        password.setFocusColor(Color.valueOf("#66ffcc"));
        password.setUnFocusColor(Color.valueOf("#C8C8C8"));
        password.setLabelFloat(true);
        
        password.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (newValue!=null) {
					errorTip.hide();
				}
			}
		});
        gridPane.add(password, 0, 4);
        
        JFXButton loginBtn = new JFXButton("Login");
        
//        loginBtn.setStyle("-fx-text-fill:#66ffcc;-fx-font-size:16;-fx-border-color:#66ffcc; -fx-border-radius:10;");
        
        gridPane.add(loginBtn, 0, 6,2,1);
        
        ImageView closeImg = new ImageView(new Image("application/close.png"));
        closeImg.setFitHeight(35);
        closeImg.setFitWidth(35);
//        gridPane.add(closeImg, 1, 0);
        
        VBox.setMargin(closeImg, new Insets(30,-100,-10,400));
        
        
        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

    		@Override
    		public void handle(MouseEvent arg0) {
    			// TODO Auto-generated method stub
    			
    			mainStage.close();
    			
    		}
        	
    	});
        
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				loginApp();
			}
		});
        
//        JFXButton createDBtn= new JFXButton("Create Database Connection");
        ImageView dbIcon= new ImageView(new Image(ResourceLoader.load("/images/dbIcon.png")));
        dbIcon.setFitHeight(30);
        dbIcon.setFitWidth(30);
        Hyperlink dataLink= new Hyperlink("",dbIcon);
//        dataLink.setStyle("-fx-text-fill:#80aaff");
//        gridPane.add(dataLink, 0, 8);
        dataLink.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				dbConn();
			}
		});
        
        VBox.setMargin(dataLink, new Insets(0,-100,0,80));
        vBox.getChildren().addAll(closeImg,gridPane,dataLink);
        
        Scene scene= new Scene(vBox,480,500);
        
        scene.getStylesheets().add(getClass().getResource("file.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e->{
        	if(e.getCode()==KeyCode.ENTER)
        	loginApp();
        });
//        mainStage.setTitle("My Friends");
//        mainStage.getIcons().add(new Image(
//                APPLICATION_ICON
//        ));

//        final ListView<String> peopleView = new ListView<>();
//        peopleView.itemsProperty().bind(friends);

        mainStage.setScene(scene);
//      InputStream is=this.getClass().getClassLoader().getResourceAsStream("/images/licenseFile.properties");
//      File file=new File(this.getClass().getResource("c:/windows/system32/bl-lk/licenseFile.properties").getFile());
       String homeDir=System.getProperty("user.home");
       String filePath=homeDir+File.separator+"bl-lk/lic.prop";
        File file=new File(filePath);
//     InputStreamReader inputStreamReader= new InputStreamReader(is);
//      	File file= new File("c:/jasperoutput/licenseFile.properties");
//        File file= new File("/images/licenseFile.txt");
        String licenseKey=null;
        boolean mainFlag=false;
        if(file.exists()) {
    	try {
    		
			Scanner scanner= new Scanner(file);
			
			while(scanner.hasNextLine()){
				System.out.println("Reacged");
				 licenseKey=scanner.nextLine();
				String hardwareKey=scanner.nextLine();
				String from=scanner.nextLine();
				String to=scanner.nextLine();
				String status=scanner.nextLine();
				DateFormat df = new SimpleDateFormat("yyyy-MM-d hh:mm:ss"); 
				Date fromDate=df.parse(from);
				Date toDate=df.parse(to);
//				System.out.println(new D.compareTo(toDate)==0);
				System.out.println("License"+licenseKey);
				if(new Date().compareTo(toDate)==0) {
					Alert alert = new Alert(AlertType.WARNING, "Your License Expired on"+toDate.toString()+"..!Please renew your license\n"+"Contact:0721-2568190\n"+"GlobularTech Services,Amravati\n");
					alert.setTitle("License Expiry Warning");
					alert.showAndWait();	
				}else if(new Date().compareTo(toDate)>0) {
					Alert alert = new Alert(AlertType.WARNING, "Your License Expired on"+toDate.toString()+"..!Please renew your license\n"+"Contact:0721-2568190\n"+"GlobularTech Services,Amravati\n");
					alert.setTitle("License Expiry Warning");
					alert.showAndWait();	
				}
				else if(licenseKey==null) {
					mainFlag=true;
				}
			}
			scanner.close();
			if(licenseKey==null||licenseKey.equals("")) {
				popup.show();
			}else {
				mainStage.show();
			}
		} 
    	 catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
    	
        }else {
        	popup.show();
        }

       
//       popup.setOnCloseRequest(new EventHandler<WindowEvent>() {
//		
//		@Override
//		public void handle(WindowEvent event) {
//			// TODO Auto-generated method stub
//			mainStage.show();
//		}
//	});
//				
//		
//      
//        popup.show();
//				System.out.println("Reached");
//		    	
    }

    private void showSplash(
            final Stage initStage,
            Task<?> task,
            InitCompletionHandler initCompletionHandler
    ) {
//        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            } // todo add code to gracefully handle other task states.
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
        
        try {
//			Process process=Runtime.getRuntime().exec("C://wamp//bin//apache2.4.9//bin//httpd.exe");
//        	Process p=Runtime.getRuntime().exec("cmd /c C://xampp//mysql_start.bat");
        	  Process process2= Runtime.getRuntime().exec("cmd /c C://xampp//mysql//bin//mysqld.exe");
//			Process process2= Runtime.getRuntime().exec("C://wamp//bin//mysql//mysql5.6.17//bin/mysqld.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    public interface InitCompletionHandler {
        void complete();
    }
    
    
    public void dbConn() {
    	GridPane gp= new GridPane();
    	 Stage secondary= new Stage();
    	
    	dbName.setPromptText("Database Name");
    	dbName.setStyle("-fx-text-fill:#66ffcc;-fx-font-size:16;");
    	dbName.setFocusColor(Color.valueOf("#66ffcc"));
    	dbName.setUnFocusColor(Color.valueOf("#C8C8C8"));
    	dbName.setLabelFloat(true);
    	dbName.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null) {
					errorTip.hide();
				}
			}
		});
    	gp.add(dbName, 1, 2);
    	
    	userName.setPromptText("UserName");
    	userName.setStyle("-fx-text-fill:#66ffcc;-fx-font-size:16;");
    	userName.setFocusColor(Color.valueOf("#66ffcc"));
    	userName.setUnFocusColor(Color.valueOf("#C8C8C8"));
    	userName.setLabelFloat(true);
    	userName.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null) {
					errorTip.hide();
				}
			}
		});
    	gp.add(userName, 1, 4);
    	
    	passTxt.setPromptText("Password");
    	passTxt.setStyle("-fx-text-fill:#66ffcc;-fx-font-size:16;");
    	passTxt.setFocusColor(Color.valueOf("#66ffcc"));
    	passTxt.setUnFocusColor(Color.valueOf("#C8C8C8"));
    	passTxt.setLabelFloat(true);
    	gp.add(passTxt, 1, 6);
    	
    	JFXButton submitBtn= new JFXButton("Submit");
    	gp.add(submitBtn, 1, 8,2,1);
    	submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				dbSubmit(secondary);
			}
		});
    	
    	
//    	ImageView closeImg = new ImageView(new Image("application/close.png"));
//        closeImg.setFitHeight(35);
//        closeImg.setFitWidth(35);
//        gp.add(closeImg, 2, 0);
        
//        VBox.setMargin(closeImg, new Insets(30,-100,-10,400));
        
        
//        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//    		@Override
//    		public void handle(MouseEvent arg0) {
//    			// TODO Auto-generated method stub
//    			
//    			
//    			
//    		}
//        	
//    	});
    	
//    	gp.getStyleClass().add("grid");
    	gp.setStyle("-fx-background-color:#000d1a;");
    	gp.setAlignment(Pos.CENTER);
    	gp.setVgap(20);
    	gp.setHgap(20);
    	 
    	 
    	 Scene scene= new Scene(gp,400,400);
    	 
    	 scene.getStylesheets().add(getClass().getResource("file.css").toExternalForm());
    	
    	 secondary.setTitle("Create Database Connection");
    	 scene.addEventHandler(KeyEvent.KEY_PRESSED, e->{
    		 if(e.getCode()==KeyCode.ENTER)
    		 dbSubmit(secondary);
    	 });
    	 
    	 secondary.setScene(scene);
    	 secondary.initOwner(mainStage);
    	 secondary.initModality(Modality.WINDOW_MODAL);
    	 secondary.initStyle(StageStyle.DECORATED);
    	 secondary.show();
    	 
    }
    
   
    
    public void loginApp() {
    	if(userTxt.getText().equals("admin") && password.getText().equals("123")){
			mainStage.close();
			mainClass= new Main();
		
				mainStage=new Stage(StageStyle.DECORATED);
				
				FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2));
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> {
					try {
						Thread.sleep(500);
						mainClass.start(mainStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
                fadeSplash.play();
				
			
		}
		else {
			
			errorTip.setText("Incorrect Username/Password. Please try again!");
			userTxt.setTooltip(errorTip);
			errorTip.show(userTxt,600,400);
		}
    }
    
    public void dbSubmit(Stage secondary) {
    	if(!ValidateOnSubmitButton()) {
			return;
		}
		String dbname= dbName.getText();
		String user= userName.getText();
		String pass= passTxt.getText();
		
		DBUtil.initDB(dbname, user, pass);
		try {
			DBUtil.dbConnect();
			Alert alert = new Alert(AlertType.INFORMATION, "Connection Established");
			alert.setTitle("Success Message");
			alert.setHeaderText("HI");
			connectionDAO.connectDB(dbname, user, pass);
			File outDir = new File("C:/rconnection");
	        outDir.mkdirs();
			FileWriter fileWriter= new FileWriter(new File("C:/rconnection/connection_file.txt"));
			PrintWriter printWriter= new PrintWriter(fileWriter);
			printWriter.println(dbname);
			printWriter.println(user);
			if(pass==null||pass.equals("")) {
			printWriter.println("null");
			}else {
				printWriter.println(pass);
			}
			printWriter.close();
			Optional<ButtonType> result= alert.showAndWait();
			if(result.get()==ButtonType.OK) {
//				dbName.clear();
//				userName.clear();
//				passTxt.clear();
				secondary.close();
			}
			else {
				secondary.close();
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.ERROR, "Connection Failed! Please try again");
			alert.setTitle("Error Message");
			alert.setHeaderText("HI");
			alert.showAndWait();
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public boolean ValidateOnSubmitButton() {
    	if(dbName.getText().isEmpty()) {
    		errorTip.setText("Please input database name");
    		dbName.setTooltip(errorTip);
    		errorTip.show(dbName,400,200);
    		return false;
    	}
    	
    	if(userName.getText().isEmpty()) {
    		errorTip.setText("Please input user name");
    		userName.setTooltip(errorTip);
    		errorTip.show(userName,400,300);
    		return false;
    	}
    	
//    	if(passTxt.getText().isEmpty()) {
//    		errorTip.setText("Please input password");
//    		passTxt.setTooltip(errorTip);
//    		errorTip.show(passTxt,200,200);
//    		return false;
//    	}
//    	
    	return true;
    	
    }
    
}

