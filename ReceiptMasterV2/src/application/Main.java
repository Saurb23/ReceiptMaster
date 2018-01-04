package application;

import java.awt.Desktop;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.itextpdf.text.log.SysoCounter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDrawerEvent;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import controller.AutoCompleteComboBoxListener;
import controller.Cash_Report_Day_Wise_Generator;
import controller.GoodsController;
import controller.InventoryController;
import controller.PurchaseController;
import controller.Purchase_Report_Generator;
import controller.ReportController;
import controller.ReturnController;
import controller.SalesController;
import controller.SalesManReportGenerator;
import controller.Sales_Report_Generator;
import dao.ConnectionDAO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.AddStock;
import model.CashReportBean;
import model.Category;
import model.Company;
import model.Customer;
import model.DeliveryMemo;
import model.Inventory;
import model.Product;
import model.PurchaseEntry;
import model.PurchaseReturn;
import model.SMSBacklog;
import model.SalesEntry;
import model.SalesMan;
import model.SalesManReportBean;
import model.SalesReportBean;
import model.SalesReturn;
import model.StockReportBean;
import model.StockTransfer;
import model.Supplier;
import model.Unit;
import net.sf.jasperreports.engine.JRException;
import util.DBUtil;

public class Main extends Application  {
	//Declaring panes
	
	private int minute;
	private int hour;
	private int second;
	private Label time=new Label();
	public static HBox dateHB=new HBox();
	BorderPane root = new BorderPane();
	SplitPane splitPane1 = new SplitPane();
	FlowPane content = new FlowPane();
	public static AnchorPane anchorPane = new AnchorPane();
	Separator separator= new Separator();
	JFXHamburger hamBurger = new JFXHamburger();
	public static GridPane gridPane = new GridPane();
	public static Stage primaryStage = new Stage();
	public static Stage dialog;
	SplashScreen splashScreen= new SplashScreen();
	JFXButton logoutbtn= new JFXButton();
	ImageView logoutImg= new ImageView(new Image(ResourceLoader.load("/images/logoutImg.png")));
	public static JFXTextField searchBtn;
//Declaring tableviews
	public static TableView<Supplier> supplierView =new TableView<Supplier>();
	public static TableView<PurchaseEntry> purchaseView = new TableView<PurchaseEntry>();
	public static TableView<Category> categoryView=new TableView<Category>();
	public static TableView<Product> prodView = new TableView<Product>();
	 public static TableView<SalesReportBean> SalesReportDate= new TableView<SalesReportBean>();
	public static TableView<Customer> custView = new TableView<Customer>();
	public static TableView<SalesEntry> salesView=new TableView<SalesEntry>();
	public static TableView<SalesEntry> recoverView = new TableView<SalesEntry>();
	public static TableView<AddStock> stockView = new TableView<AddStock>();
	public static TableView<StockTransfer> transferView= new TableView<StockTransfer>();
	public static TableView<Product> currentStockView = new TableView<Product>();
	public static TableView<Unit> unitView = new TableView<Unit>();
	public static TableView<PurchaseReturn> purReturnView= new TableView<>();
	public static TableView<SalesReturn> saleReturnView= new TableView<>();
	public static TableView<DeliveryMemo> deliveryView= new TableView<DeliveryMemo>();
	public static TableView<SalesMan> salesManView= new TableView<SalesMan>();

	public static TableView<PurchaseEntry> PurchaseReportDate= new TableView<PurchaseEntry>();
	public static TableView<StockReportBean> StockReportDate= new TableView<StockReportBean>();
	public static TableView<CashReportBean> CashReportDate= new TableView<CashReportBean>();
	public static TableView<SalesManReportBean> salesManReportDate= new TableView<SalesManReportBean>();
	 
		Label adminLbl= new Label(" Admin");
		ImageView logoImg = new ImageView();
		public static ImageView backImg= new ImageView(new Image("/images/rupee.jpg"));
//creating title panes

	public static TitledPane purchTitlePane = new TitledPane();
	public static TitledPane saleTitlePane = new TitledPane();
	public static TitledPane returnTitlePane = new TitledPane();
	public static TitledPane inventTitlePane = new TitledPane();
	public static TitledPane goodTitlePane = new TitledPane();
	public static TitledPane reportTitilePane= new TitledPane();
	
//Profile Menu	
	public static MenuButton profileMenu= new MenuButton();
	MenuItem editDetMenuItem= new MenuItem("Edit Profile");
	MenuItem logoutMenuItem= new MenuItem("Logout");
	
	MenuButton purchaseMenu= new MenuButton();
	MenuItem supplierMenuItem= new MenuItem("Supplier Record");
	MenuItem purchaseEntryMenuItem= new MenuItem("Purchase Record");
	
	MenuButton salesMenu = new MenuButton();
	MenuItem custMenuItem= new MenuItem("Customer Record");
	MenuItem deleiveryMenuItem= new MenuItem("Delivery Memo");
	MenuItem salesRecMenuItem= new MenuItem("Sales Record");
	
	MenuButton reportMenu = new MenuButton();
	MenuItem stockRepMenuItem= new MenuItem("Stock Report");
	MenuItem purcRepMenuItem= new MenuItem("Purchase Report");
	MenuItem saleRepMenuItme= new MenuItem("Sales Report");
	MenuItem cashRepMenuItem= new MenuItem("Cash Report");
	
	MenuButton returnMenu = new MenuButton();
	MenuItem purchRetMenuItem= new MenuItem("Purchase Return");
	MenuItem saleRetMenuItem= new MenuItem("Sales Return");
	
	MenuButton stockMenu= new MenuButton();
	MenuItem addStockMenuItem= new MenuItem("Add Stock");
	MenuItem transMenuItem= new MenuItem("Transfer Stock");
	

	public static JFXTextField companyName=new JFXTextField();
	public static JFXTextField address=new JFXTextField();
	public static JFXTextField officeAddress=new JFXTextField();
	public static JFXTextField emailId=new JFXTextField();
	public static JFXTextField contactNo=new JFXTextField();
	public static JFXTextField altContactNo=new JFXTextField();
	public static JFXComboBox<String> state=new JFXComboBox<>();
	public static JFXTextField logoPath= new JFXTextField();
	public static JFXTextField gstin= new JFXTextField();
	public static JFXButton submitBtn= new JFXButton("Submit");
	
	

	ConnectionDAO connectionDAO= new ConnectionDAO();

	public static HBox hambHB = new HBox();
	ScrollPane scrollPane= new ScrollPane();

	public static HBox saleRetunHB;
	public static HBox purchReturnHB;
	public static HBox cashReportHB;
	public static HBox stockReportHB;
	public static HBox	salesManReportHB;
	
	List<SMSBacklog> smsList=new ArrayList<>();
	public static String companyDetails="";
//creating drawer
			public static JFXDrawer leftDrawer = new JFXDrawer();
			JFXDrawer sideDrawer= new JFXDrawer();
//creating object of class AccountController

//creating object of class Purchase Controller
		PurchaseController purchaseController= new PurchaseController();

//Declaring object of class SalesController
	SalesController salesController=new SalesController();

//Declaring objects for class InventoryController
	InventoryController inventoryController= new InventoryController();
//Declaring object for class ReportController
	ReportController reportController= new ReportController();

//	Declaring object for ReturnController
	ReturnController returnController= new ReturnController();
//Declaring menus for Purchase Management
	public static JFXButton supplierRecBtn = new JFXButton();
	public static  JFXButton prchsEntryBtn = new JFXButton();
//	JFXButton prchseRecBtn = new JFXButton("Purchase Record");
//	public static JFXButton paymentsBtn = new JFXButton("Payments");

//Declaring menus for Sales Management
	public static JFXButton createCustBtn = new JFXButton();
//	JFXButton custRecordBtn = new JFXButton("Customer Record");
//	JFXButton salesInvoicBtn = new JFXButton("Sales Invoice");
	public static JFXButton salesRecBtn = new JFXButton();
	public static JFXButton dmRecBtn= new JFXButton();
	public static JFXButton salesManRecBtn= new JFXButton();
//	public static JFXButton saleReturnBtn= new JFXButton("Sales Return Record");
//	public static JFXButton recoveryBtn = new JFXButton("Recovery");

////Declaring menus for Goods Return Managment
//
		public static JFXButton purReturnBtn= new JFXButton();
		public static JFXButton saleReturnBtn= new JFXButton();
//
//Declaring menus for Inventory Management
	public static JFXButton addStockBtn = new JFXButton();
	public static JFXButton transferStock= new JFXButton();
	public static JFXButton currentStockBtn = new JFXButton("Current Stock");

//Declaring menus for Goods Management
	public static JFXButton addCatBtn = new JFXButton("Add Category");
	public static JFXButton addProdBtn = new JFXButton("Add Product");
	public static JFXButton addRawBtn = new JFXButton("Add Raw Material");
	public static JFXButton addUnitBtn = new JFXButton("Add Measurement Units");


//Purchase Management
	//Add New Supplier
	public static JFXButton addSupBtn = new JFXButton();
	public static JFXButton editSupBtn = new JFXButton();
	//Purchase Entry
	public static JFXButton createPurBtn = new JFXButton();
	public static JFXButton editPurBtn = new JFXButton();
	public static JFXButton viewPurBtn= new JFXButton("View Details");

//Sales Management
	//Create New Customer
	public static JFXButton createNewCustBtn = new JFXButton();
	public static JFXButton editCustBtn = new JFXButton();
	//Delivery Memo
	public static JFXButton createDMBtn= new JFXButton();
	public static JFXButton createDMInvBtn= new JFXButton();
	public static JFXButton viewDMBtn= new JFXButton();
	
	//Sales Entry
	public static JFXButton createSalesBtn = new JFXButton();
	public static JFXButton editSalesBtn = new JFXButton();
	public static  JFXButton printSalesBtn=new JFXButton();

	// SalesMan Record
	public static JFXButton createSalesManBtn= new JFXButton();
	public static JFXButton editSalesManBtn= new JFXButton();

// Goods Return Managment
	public static JFXButton returnPurcBtn= new JFXButton();
	public static JFXButton returnSaleBtn= new JFXButton();


//Report Management
	public static JFXButton salesReport= new JFXButton();
	public static JFXButton CashReport= new JFXButton();
	public static JFXButton PurchaseReport= new JFXButton();
	public static JFXButton StockReport = new JFXButton();
	public static JFXButton salesManReport= new JFXButton();

	public static JFXComboBox<String> Cash_Report_Filter;
	private static final long THRESHOLD = 100;
	private static final int MIN_BARCODE_LENGTH = 8;
	private long lastEventTimeStamp = 0L;
	StringBuffer barcode= new StringBuffer();

//Stock Management
	public static JFXButton createStockBtn = new JFXButton();
	public static JFXButton transferStockBtn= new JFXButton();

//Shortcut menu
	Button shortAcountBtn;
	Button shortCustomerBtn;
	Button shortProdBtn;
	Button shortStockBtn;
	Button shortJournalBtn;
	Button shortSaleInBtn;
	Button shortPurchEntBtn;

//Controls for Sales Report
	public static JFXButton GenerateBtn= new JFXButton();
	public static  JFXButton ViewBtn= new JFXButton("View");
	 Sales_Report_Generator salesReportGen=new Sales_Report_Generator();
	 Purchase_Report_Generator purchaseReportGen= new Purchase_Report_Generator();
	 controller.Stock_Report_Generator stockReportGen=new controller.Stock_Report_Generator();
	 controller.Cash_Report_Generator cashReportGen=new controller.Cash_Report_Generator();
	 Cash_Report_Day_Wise_Generator cashReportDay_wise_gen= new Cash_Report_Day_Wise_Generator();
	 SalesManReportGenerator salesManRepGen= new SalesManReportGenerator();

	 static Tooltip errorTip = new Tooltip();
	 final static ToggleGroup group = new ToggleGroup();
	 public DatePicker FromDate= new DatePicker();
	 public DatePicker ToDate= new DatePicker();

	 String fromDate1,toDate1;

	 public LocalDate date;
	 public LocalDate date1;

	 RadioButton excel;
	 RadioButton pdf;


	public static JFXButton backBtn;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// final ImageView imageView = new ImageView(
		// new
		// Image("https://upload.wikimedia.org/wikipedia/commons/b/b7/Idylls_of_the_King_3.jpg")
		// );
		// imageView.setFitHeight(300);
		// imageView.setFitWidth(228);

		Main.primaryStage=primaryStage;
		AnchorPane hamAnchor = new AnchorPane();
//		BarCodeRead.generateBarCode();
		final Scene scene = new Scene(scrollPane);
//creating flow pane which will act as container


		// content.getChildren().add(imageView);
		// JFXHamburger leftButton = new JFXHamburger();
		// GridPane content = new GridPane();

//creating hamburger

//define transition on hamburger
		HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(hamBurger);
		burgerTask.setRate(1);
		hamBurger.setStyle("-fx-cursor:hand");
		hamBurger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

			burgerTask.setRate(burgerTask.getRate()*0);
			// FlowPane.setMargin(h1, new Insets(0, 1200, 670, -500));
			burgerTask.play();

		});
		// burgerTask.play();

		// content.add(h1);

//		hambHB.getChildren().add(hamBurger);
//		hambHB.setMaxSize(200, 200);
//		HBox.setMargin(hamBurger, new Insets(0, 600, 670, -200));

//	adding hamburger to flowpane
//		content.getChildren().add(hamBurger);
//		content.setMaxSize(200, 200);

		// JFXDrawer leftDrawer = new JFXDrawer();
		// VBox leftDrawerPane = new VBox();

		// StackPane leftDrawerPane = new StackPane();

//creating vertical box which will hold all titles
	
		VBox leftDrawerPane = new VBox();
		leftDrawerPane.setStyle("-fx-background-color:#000d1a");
		  final Separator separator1 = new Separator();
		  separator1.setOrientation(Orientation.HORIZONTAL);
		  separator1.setMaxWidth(30);
		  separator1.setMinHeight(25);
		  separator1.setVisible(false);
//		leftDrawerPane.setStyle("-fx-background-color:black");

//main Image of receipt master
		ImageView mainImage = new ImageView();
		mainImage.setImage(new Image(ResourceLoader.load("/images/rm3.JPG")));
//		System.out.println(javafx.scene.text.Font.getFamilies());

		mainImage.setFitWidth(200);
		mainImage.setFitHeight(40);

		Label reciptMasterLbl= new Label("Billing Master");
		reciptMasterLbl.setFont(Font.font("Helvetica",FontWeight.BOLD,28));
		reciptMasterLbl.setStyle("-fx-text-fill:white;-fx-effect: dropshadow( gaussian , #3399ff , 0,0,0,1 );");

//		JFXRippler rippler= new JFXRippler(reciptMasterLbl);
//		rippler.set

		ImageView receiptLogo= new ImageView(new Image(ResourceLoader.load("/images/receiptMasterLOGO.png")));
		receiptLogo.setFitHeight(70);
		receiptLogo.setFitWidth(70);

		  final Separator separator2 = new Separator();
		  separator2.setMaxWidth(300);
		  separator2.setMinHeight(20);
//		  separator2.setVisible(false);
//adding logo and welcome title
		HBox hbLogo = new HBox();
		hbLogo.setSpacing(20);
		
		logoImg.setImage(new Image(ResourceLoader.load("/images/user.png")));
		logoImg.setFitHeight(60);
		logoImg.setFitWidth(50);
		
		Company company= connectionDAO.getCompanyDetails();
		if(company!=null) {
			if(company.getLogoPath()!=null) {
				if(!company.getLogoPath().equals("")) {
				Image img=new Image("file:///"+company.getLogoPath().replaceAll(" ", "/"));
				logoImg.setImage(img);
				logoImg.setFitHeight(60);
				logoImg.setFitWidth(50);
			}
			}
//		}else {
//			System.out.println("Reached");
//			logoImg.setImage(new Image(ResourceLoader.load("/images/user.png")));
//			logoImg.setFitHeight(50);
//			logoImg.setFitWidth(60);
//		}
			
		}
		
		
//		logoImg.setImage(new Image(ResourceLoader.load("/images/user.png")));
//		logoImg.setFitWidth(50);
//		logoImg.setFitHeight(60);
		Label welcLbl = new Label("Welcome,");
		welcLbl.setStyle("-fx-font-size:16;-fx-font-family:arial; -fx-text-fill:#C0C0C0;");

	
		adminLbl.setStyle("-fx-font-size:17;-fx-font-family:arial; -fx-text-fill:white;");

		HBox.setMargin(logoImg, new Insets(0,0,0,10));
		HBox.setMargin(adminLbl, new Insets(28,50,-50,-70));
		hbLogo.getChildren().addAll(logoImg,welcLbl,adminLbl);

		// TitledPane imgAnchor = new TitledPane();
		// TitledPane imageTitle = new TitledPane("Receipt Master", img);
		HBox hbMainImg = new HBox();

		// vboximg.add(img, 0, 0);
		// imageGB.add(rm, 2, 0);
//		hbMainImg.setSpacing(10);
		hbMainImg.setAlignment(Pos.CENTER);
		HBox.setMargin(reciptMasterLbl, new Insets(0,0,10,0));
		hbMainImg.getChildren().addAll(reciptMasterLbl);

//		hbMainImg.setAlignment(Pos.CENTER);
		 final Separator separator3 = new Separator();
		 separator3.setOrientation(Orientation.HORIZONTAL);
		 separator3.setMaxWidth(300);
		 separator3.setMinHeight(35);
//		 separator3.setVisible(false);

		 Separator separator4= new Separator();
		 separator4.setMaxWidth(30);
		 separator4.setMinHeight(20);
		 separator4.setVisible(false);

//Side Menus		 
		
		 VBox sideDrawerVB= new VBox();
//		 sideDrawerVB.setMinSize(100, 800);
//		 sideDrawerVB.set
		 ImageView logoImg2 = new ImageView();
		 logoImg2.setImage(new Image(ResourceLoader.load("/images/user.png")));
		 logoImg2.setFitWidth(50);
		 logoImg2.setFitHeight(60);
		 
		 Separator sideSeparator= new Separator();
		 sideSeparator.setMaxWidth(30);
		 sideSeparator.setMinHeight(20);
		 sideSeparator.setVisible(false);
		 	supplierMenuItem.setOnAction(e->setOnActionMethod(e));
			
			purchaseEntryMenuItem.setOnAction(e->setOnActionMethod(e));
			purchaseMenu.getItems().addAll(supplierMenuItem,purchaseEntryMenuItem);
			ImageView purchImg= new ImageView(new Image("/images/purchase colored.png"));
			purchImg.setFitHeight(50);
			purchImg.setFitWidth(50);
			purchaseMenu.setGraphic(purchImg);
			purchaseMenu.setPopupSide(Side.RIGHT);
			
			custMenuItem.setOnAction(e->setOnActionMethod(e));
			salesRecMenuItem.setOnAction(e->setOnActionMethod(e));
			deleiveryMenuItem.setOnAction(e->setOnActionMethod(e));
			salesMenu.getItems().addAll(custMenuItem,salesRecMenuItem);
			salesMenu.setPopupSide(Side.RIGHT);
			stockRepMenuItem.setOnAction(e->setOnActionMethod(e));
			purcRepMenuItem.setOnAction(e->setOnActionMethod(e));
			saleRepMenuItme.setOnAction(e->setOnActionMethod(e));
			cashRepMenuItem.setOnAction(e->setOnActionMethod(e));
			reportMenu.getItems().addAll(stockRepMenuItem,purcRepMenuItem,saleRepMenuItme,cashRepMenuItem);
			reportMenu.setPopupSide(Side.RIGHT);
			
			purchRetMenuItem.setOnAction(e->setOnActionMethod(e));
			saleRetMenuItem.setOnAction(e->setOnActionMethod(e));
			returnMenu.getItems().addAll(purchRetMenuItem,saleRetMenuItem);
			returnMenu.setPopupSide(Side.RIGHT);
			addStockMenuItem.setOnAction(e->setOnActionMethod(e));
			transMenuItem.setOnAction(e->setOnActionMethod(e));
			stockMenu.getItems().addAll(addStockMenuItem,transMenuItem);
			stockMenu.setPopupSide(Side.RIGHT);
			sideDrawerVB.setStyle("-fx-background-color:#000d1a");
			sideDrawerVB.getChildren().addAll(sideSeparator,logoImg2,purchaseMenu,salesMenu,reportMenu,returnMenu,stockMenu);
			
//creating list for titles
		Collection<TitledPane> titleList = new ArrayList<TitledPane>();
		// result.add(imageTitle);



		// t1.setText("Account Management");
//		HBox hbox = new HBox();
//		hbox.setSpacing(10);
//		ImageView accImgView = new ImageView(new Image(ResourceLoader.load("/images/account colored.png")));
//		HBox.setMargin(accImgView, new Insets(8,0,0,0));
//		accImgView.setFitHeight(20);
//		accImgView.setFitWidth(20);
//		ImageView arrImgView = new ImageView(new Image(ResourceLoader.load("/images/down-arrow.png")));
//		HBox.setMargin(arrImgView, new Insets(8,2,0,10));
//		arrImgView.setFitHeight(20);
//		arrImgView.setFitWidth(20);
//		Label accTitleLbl = new Label("Account Management");
//		hbox.getChildren().addAll(accImgView, accTitleLbl,arrImgView);
//		hbox.setMinWidth(500);
//		accountTitlePane.setGraphic(hbox);


//		vb.setStyle("-fx-background-color:#B23850; -fx-border-color:#66ffff;");
//		createAccBtn.setAlignment(Pos.CENTER);
////		createAccBtn.setMinHeight(20);
//		createAccBtn.setMaxWidth(200);
//		createAccBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");

		HBox hbox2 = new HBox();
		hbox2.setSpacing(10);
		ImageView imgPrchse = new ImageView(new Image(ResourceLoader.load("/images/purchase colored.png")));
		HBox.setMargin(imgPrchse, new Insets(8,0,0,0));
		imgPrchse.setFitHeight(20);
		imgPrchse.setFitWidth(20);
		Label prchseTitleLbl = new Label("urchase Management");
		Label pLbl= new Label("P");
		pLbl.setStyle("-fx-text-fill:#ff5c33;-fx-font-weight:bold");
//		prchseTitleLbl.setMnemonicParsing(true);
		ImageView arrImgView2 = new ImageView(new Image(ResourceLoader.load("/images/down-arrow.png")));
		HBox.setMargin(arrImgView2, new Insets(8,4,0,-2));
		arrImgView2.setFitHeight(20);
		arrImgView2.setFitWidth(20);
////		HBox.setMargin(arrImgView, new Insets(8,0,0,10));
		hbox2.setMinWidth(500);
		HBox.setMargin(prchseTitleLbl, new Insets(0,0,0,-10));
		hbox2.getChildren().addAll(imgPrchse, pLbl,prchseTitleLbl,arrImgView2);
		purchTitlePane.setGraphic(hbox2);
		// t2.setText("Purchase Management");
		VBox vb2 = new VBox();
		vb2.setStyle("-fx-background-color:aliceblue");
////		JFXRippler supplierRecRippl= new JFXRippler(supplierRecBtn);
		vb2.getChildren().addAll(supplierRecBtn, prchsEntryBtn);
////		supplierRecBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
		supplierRecBtn.getStyleClass().add("jfxBtn");
//		supplierRecBtn.getStyleClass().add("jfxBtn");
//		prchsEntryBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
		prchsEntryBtn.getStyleClass().add("jfxBtn");
//		paymentsBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
//		paymentsBtn.getStyleClass().add("jfxBtn");
//		supplierRecBtn.setMnemonicParsing(true);
		Label suppLbl= new Label("S");
		suppLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label supplTitlLbl= new Label("upplier Record");
		supplTitlLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox supplierHB= new HBox();
		HBox.setMargin(supplTitlLbl, new Insets(0,0,0,-2));
		supplierHB.getChildren().addAll(suppLbl,supplTitlLbl);
		supplierRecBtn.setGraphic(supplierHB);
		supplierRecBtn.setOnAction((ActionEvent e)->{
			setOnActionMethod(e);

		});
//		prchsEntryBtn.setMnemonicParsing(true);
		Label purLbl= new Label("P");
		purLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label purchEntryLbl= new Label("urchase Record");
		purchEntryLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox purchaseHB= new HBox();
		HBox.setMargin(purchEntryLbl, new Insets(0,0,0,-2));
		purchaseHB.getChildren().addAll(purLbl,purchEntryLbl);
		prchsEntryBtn.setGraphic(purchaseHB);

		prchsEntryBtn.setOnAction(e->setOnActionMethod(e));
//		paymentsBtn.setOnAction(e->setOnActionMethod(e));
		purchTitlePane.setContent(vb2);
//		purchTitlePane.setStyle("-fx-background-color:black");
//add purchase management to titlelist
		titleList.add(purchTitlePane);


		HBox hbox3 = new HBox();
		hbox3.setSpacing(10);
		ImageView imgSales = new ImageView(new Image(ResourceLoader.load("/images/sales colored.png")));
		HBox.setMargin(imgSales, new Insets(8,0,0,0));
		imgSales.setFitHeight(20);
		imgSales.setFitWidth(20);
		Label salesTitleLbl = new Label("ales Management");
		Label sLbl= new Label("S");
		sLbl.setStyle("-fx-text-fill:#ff5c33;-fx-font-weight:bold");
//		salesTitleLbl.setMnemonicParsing(true);
		ImageView arrImgView3 = new ImageView(new Image(ResourceLoader.load("/images/down-arrow.png")));
		HBox.setMargin(arrImgView3, new Insets(8,0,0,32));
		arrImgView3.setFitHeight(20);
		arrImgView3.setFitWidth(20);
		hbox3.setMinWidth(500);
		HBox.setMargin(salesTitleLbl, new Insets(0,0,0,-10));
		hbox3.getChildren().addAll(imgSales, sLbl,salesTitleLbl,arrImgView3);
		saleTitlePane.setGraphic(hbox3);
		// t3.setText("Sales Management");
		VBox vb3 = new VBox();
		vb3.setStyle("-fx-background-color:aliceblue");

		vb3.getChildren().addAll(createCustBtn, dmRecBtn,salesRecBtn,salesManRecBtn);
////		createCustBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
		createCustBtn.getStyleClass().add("jfxBtn");
//		createCustBtn.setMnemonicParsing(true);
		Label custLbl= new Label("C");
		custLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label custRecLbl= new Label("ustomer Record");
		custRecLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox custRecHB= new  HBox();
		HBox.setMargin(custRecLbl, new Insets(0,0,0,-2));
		custRecHB.getChildren().addAll(custLbl,custRecLbl);
		createCustBtn.setGraphic(custRecHB);
////		createAccBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
////		salesRecBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");

		Label saleLbl= new Label("S");
		saleLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label saleRecLbl= new Label("ales Record");
		saleRecLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox saleHb= new HBox();
		HBox.setMargin(saleRecLbl, new Insets(0,0,0,-2));

		saleHb.getChildren().addAll(saleLbl,saleRecLbl);
		salesRecBtn.setGraphic(saleHb);

		salesRecBtn.getStyleClass().add("jfxBtn");
		
		Label dmLbl= new Label("D");
		dmLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label dmRecLbl= new Label("elivery Memo");
		dmRecLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox dmHB= new HBox();
		HBox.setMargin(dmRecLbl, new Insets(0,0,0,-2));
		dmHB.getChildren().addAll(dmLbl,dmRecLbl);
		dmRecBtn.setGraphic(dmHB);
		dmRecBtn.getStyleClass().add("jfxBtn");
		
		salesManRecBtn.getStyleClass().add("jfxBtn");
		
		Label mLbl= new Label("M");
		mLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label salesMLbl= new Label("Sales");
		salesMLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		Label salesManRecLbl= new Label("an Record");
		salesManRecLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox salesManHB= new HBox();
		salesManHB.getChildren().addAll(salesMLbl,mLbl,salesManRecLbl);
		HBox.setMargin(salesManRecLbl, new Insets(0,0,0,-2));
		salesManRecBtn.setGraphic(salesManHB);
		
//		salesRecBtn.setMnemonicParsing(true);
//		saleReturnBtn.getStyleClass().add("jfxBtn");
////		recoveryBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
		createCustBtn.setOnAction(e->setOnActionMethod(e));
		salesRecBtn.setOnAction(e->setOnActionMethod(e));
		dmRecBtn.setOnAction(e->setOnActionMethod(e));
		salesManRecBtn.setOnAction(e->setOnActionMethod(e));
		StockReport.setOnAction(e->setOnActionMethod(e));
		
//		saleReturnBtn.setOnAction(e->setOnActionMethod(e));
		CashReport.setOnAction(e->setOnActionMethod(e));

		saleTitlePane.setContent(vb3);
////		saleTitlePane.setStyle("-fx-background-color:black");
////adding sale to titlelist
		titleList.add(saleTitlePane);

		HBox hbox6 = new HBox();
		hbox6.setSpacing(10);
		ImageView reportImgView = new ImageView(new Image(ResourceLoader.load("/images/account colored.png")));
		HBox.setMargin(reportImgView, new Insets(8,0,0,0));
		reportImgView.setFitHeight(20);
		reportImgView.setFitWidth(20);
		ImageView arrImgView1 = new ImageView(new Image(ResourceLoader.load("/images/down-arrow.png")));
		HBox.setMargin(arrImgView1, new Insets(8,2,0,16));
		arrImgView1.setFitHeight(20);
		arrImgView1.setFitWidth(20);
		Label repTitleLbl = new Label("eport Management");
		Label rLbl= new Label("R");
		rLbl.setStyle("-fx-text-fill:#ff5c33;-fx-font-weight:bold");
//		rLbl.setMnemonicParsing(true);
		HBox.setMargin(repTitleLbl, new Insets(0,0,0,-10));
		hbox6.getChildren().addAll(reportImgView, rLbl,repTitleLbl,arrImgView1);
		hbox6.setMinWidth(500);
		reportTitilePane.setGraphic(hbox6);
		titleList.add(reportTitilePane);

		VBox vb6 = new VBox();
		vb6.setStyle("-fx-background-color:aliceblue");


		vb6.getChildren().addAll(StockReport,PurchaseReport,salesReport,CashReport,salesManReport);

		Label stockSLbl= new Label("R");
		stockSLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label stockLbl= new Label("Stock");
		stockLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		Label stockRecLbl= new Label("eport");
		stockRecLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox stockReportHB= new HBox();
		HBox.setMargin(stockSLbl, new Insets(0,-2,0,4));

		stockReportHB.getChildren().addAll(stockLbl,stockSLbl,stockRecLbl);
		StockReport.setGraphic(stockReportHB);
		StockReport.getStyleClass().add("jfxBtn");

		Label purcPLbl= new Label("P");
		purcPLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label purchRepLbl= new Label("urchase Report");
		purchRepLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox purchReportHB= new HBox();
		HBox.setMargin(purchRepLbl, new Insets(0,0,0,-2));
		purchReportHB.getChildren().addAll(purcPLbl,purchRepLbl);
		PurchaseReport.setGraphic(purchReportHB);

		PurchaseReport.getStyleClass().add("jfxBtn");

		Label cashCLabel= new Label("C");
		cashCLabel.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label cashRepLbl= new Label("ash Report");
		cashRepLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox cashHb= new HBox();
		HBox.setMargin(cashRepLbl, new Insets(0,0,0,-2));
		cashHb.getChildren().addAll(cashCLabel,cashRepLbl);
		CashReport.setGraphic(cashHb);
		CashReport.getStyleClass().add("jfxBtn");
		salesReport.getStyleClass().add("jfxBtn");
		Label saleSLbl= new Label("S");
		saleSLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label saleRepLbl= new Label("ales Report");
		saleRepLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox saleReportHB= new HBox();
		HBox.setMargin(saleRepLbl, new Insets(0,0,0,-2));
		saleReportHB.getChildren().addAll(saleSLbl,saleRepLbl);
		salesReport.setGraphic(saleReportHB);

//		StockReport.setOnAction(e->setOnActionMethod(e));
		salesReport.setOnAction(e->setOnActionMethod(e));


		PurchaseReport.setOnAction(e->setOnActionMethod(e));
//		CashReport.setOnAction(e->setOnActionMethod(e));
		
		Label smLbl= new Label("M");
		smLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label salesManLbl= new Label("Sales");
		salesManLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		Label manRepLbl= new Label("an Report");
		manRepLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox salesManReportHB= new HBox();
		HBox.setMargin(manRepLbl, new Insets(0,0,0,-2));
		salesManReportHB.getChildren().addAll(salesManLbl,smLbl,manRepLbl);
		salesManReport.setGraphic(salesManReportHB);
		salesManReport.getStyleClass().add("jfxBtn");
		salesManReport.setOnAction(e->setOnActionMethod(e));
		
		reportTitilePane.setContent(vb6);




		HBox hbox4 = new HBox();
		hbox4.setSpacing(10);
		ImageView imgReturn = new ImageView(new Image(ResourceLoader.load("/images/inventory colored.png")));
		HBox.setMargin(imgReturn, new Insets(8,0,0,0));
		imgReturn.setFitHeight(20);
		imgReturn.setFitWidth(20);
		Label returnTitleLbl = new Label("oods Return");
		Label gLbl= new Label("G");
		gLbl.setStyle("-fx-text-fill:#ff5c33;-fx-font-weight:bold");
//		returnTitleLbl.setMnemonicParsing(true);
		ImageView arrImgView4 = new ImageView(new Image(ResourceLoader.load("/images/down-arrow.png")));
		HBox.setMargin(arrImgView4, new Insets(8,0,0,77));
		arrImgView4.setFitHeight(20);
		arrImgView4.setFitWidth(20);
////		HBox.setMargin(arrImgView, new Insets(8,0,0,10));
		hbox4.setMinWidth(500);
		HBox.setMargin(returnTitleLbl, new Insets(0,0,0,-10));
		hbox4.getChildren().addAll(imgReturn, gLbl,returnTitleLbl,arrImgView4);
		returnTitlePane.setGraphic(hbox4);
		// t4.setText("Inventory Management");

		// t4.setStyle("-fx-background-color:transparent");
		// t4.getStylesheets().add(e)
		VBox vb4 = new VBox();
		vb4.setStyle("-fx-background-color:aliceblue");

		vb4.getChildren().addAll(purReturnBtn,saleReturnBtn);
		purReturnBtn.getStyleClass().add("jfxBtn");

		Label purRetuPLbl= new Label("P");
		purRetuPLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label purReturnLbl= new Label("urchase Return");
		purReturnLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox purReturnHB= new HBox();
		HBox.setMargin(purReturnLbl, new Insets(0,0,0,-2));
		purReturnHB.getChildren().addAll(purRetuPLbl,purReturnLbl);
		purReturnBtn.setGraphic(purReturnHB);

		saleReturnBtn.getStyleClass().add("jfxBtn");
		Label saleRetuSLbl= new Label("S");

		saleRetuSLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label saleReturnLbl = new Label("ales Return");
		saleReturnLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox saleReturnHB= new HBox();
		HBox.setMargin(saleReturnLbl, new Insets(0,0,0,-2));
		saleReturnHB.getChildren().addAll(saleRetuSLbl,saleReturnLbl);
		saleReturnBtn.setGraphic(saleReturnHB);
//		purReturnBtn.setMnemonicParsing(true);
//		saleReturnBtn.setMnemonicParsing(true);
////		addStockBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
////		currentStockBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
		purReturnBtn.setOnAction(e->setOnActionMethod(e));
		saleReturnBtn.setOnAction(e->setOnActionMethod(e));
		returnTitlePane.setContent(vb4);
//		// t4.setStyle("-fx-background-color:black");
////add inventory to title list
		titleList.add(returnTitlePane);
//
//
		HBox hbox5 = new HBox();
		hbox5.setSpacing(10);
		ImageView imgGoods = new ImageView(new Image(ResourceLoader.load("/images/goods colored.png")));
		HBox.setMargin(imgGoods, new Insets(8,0,0,0));
		imgGoods.setFitHeight(20);
		imgGoods.setFitWidth(20);
		
		Label goodTitleLbl = new Label("Stock");
		Label tLbl= new Label("T");
		Label stckTLbl= new Label("ransfer");
		tLbl.setStyle("-fx-text-fill:#ff5c33;-fx-font-weight:bold");
//		returnTitleLbl.setMnemonicParsing(true);
		ImageView arrImgView5 = new ImageView(new Image(ResourceLoader.load("/images/down-arrow.png")));
		
		arrImgView5.setFitHeight(20);
		arrImgView5.setFitWidth(20);
////		HBox.setMargin(arrImgView, new Insets(8,0,0,10));
		hbox5.setMinWidth(500);
		HBox.setMargin(tLbl, new Insets(0,0,0,-5));
		HBox.setMargin(stckTLbl, new Insets(0,0,0,-10));
		HBox.setMargin(arrImgView5, new Insets(8,0,0,73));
//		HBox.setMargin(arrImgView5, new Insets(0,-10,0,0));
		hbox5.getChildren().addAll(imgGoods,goodTitleLbl,tLbl,stckTLbl,arrImgView5);
		goodTitlePane.setGraphic(hbox5);
		
		VBox vb5 = new VBox();
		vb5.setStyle("-fx-background-color:aliceblue");
		
		vb5.getChildren().addAll(addStockBtn,transferStock);
		addStockBtn.getStyleClass().add("jfxBtn");
		Label addALbl= new Label("I");
		addALbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label addStockLbl= new Label("nward Stock");
		addStockLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox stockHB= new HBox();
		HBox.setMargin(addStockLbl, new Insets(0,0,0,-2) );
		stockHB.getChildren().addAll(addALbl,addStockLbl);
		addStockBtn.setGraphic(stockHB);
		
		Label transferTLbl= new Label("O");
		transferTLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
		Label transStockLbl= new Label("utward Stock");
		transStockLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
		HBox transferHB= new HBox();
		HBox.setMargin(transStockLbl, new Insets(0,0,0,-2));
		transferHB.getChildren().addAll(transferTLbl,transStockLbl);
		transferStock.getStyleClass().add("jfxBtn");
		transferStock.setGraphic(transferHB);
		transferStock.setOnAction(e->setOnActionMethod(e));
		addStockBtn.setOnAction(e->setOnActionMethod(e));
		
		goodTitlePane.setContent(vb5);
		
		
		
//		Label goodsLbl= new Label("S");
//		goodsLbl.setStyle("-fx-font-size: 18;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
//		
//		Label goodsTitleLbl = new Label("tock Management");
//		goodsTitleLbl.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black;-fx-background-color:transparent;");
//		
//		HBox goodsHB= new HBox();
//		HBox.setMargin(goodsTitleLbl, new Insets(0,0,0,-2));
//		goodsHB.getChildren().addAll(goodsLbl,goodsTitleLbl);
	
////		HBox.setMargin(arrImgView, new Insets(8,0,0,10));
//		ImageView arrImgView5 = new ImageView(new Image(ResourceLoader.load("/images/down-arrow.png")));
//		HBox.setMargin(arrImgView5, new Insets(8,0,0,23));
//		arrImgView5.setFitHeight(20);
//		arrImgView5.setFitWidth(20);
//		hbox5.setMinWidth(500);
//		hbox5.getChildren().addAll(imgGoods, goodsTitleLbl,arrImgView5);
//		goodTitlePane.setGraphic(hbox5);

//		// t5.setText("Goods Management");
//		VBox vb5 = new VBox();
//		vb5.setStyle("-fx-background-color:aliceblue");
//
//		vb5.getChildren().addAll(addUnitBtn, addProdBtn, addRawBtn);
////		addUnitBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
//////		addCatBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
////		addProdBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
////		addRawBtn.setStyle("-fx-font-size: 16;-fx-border-color:transparent;-fx-text-fill:black; -fx-background-color:transparent");
//
//		addUnitBtn.getStyleClass().add("jfxBtn");
//		addProdBtn.getStyleClass().add("jfxBtn");
//		addRawBtn.getStyleClass().add("jfxBtn");
////		addCatBtn.setOnAction(e->setOnActionMethod(e));
//		addUnitBtn.setOnAction(e->setOnActionMethod(e));
//		addProdBtn.setOnAction(e->setOnActionMethod(e));
//		addRawBtn.setOnAction(e->setOnActionMethod(e));
//		goodTitlePane.setContent(vb5);
////		goodTitlePane.setStyle("-fx-background-color:black");
////adding goods to titlelist
		titleList.add(goodTitlePane);

//creating accordian to hold all titles with effects
		Accordion accordion = new Accordion();
		// ScrollPane scrollpane = new ScrollPane();
		// scrollpane.autosize();
		accordion.getPanes().addAll(titleList);


		// Group g = new Group();
		// g.getChildren().add(accordion);
		// scrollpane.setContent(accordion);

//		leftDrawerPane.getStyleClass().add("red");
		leftDrawerPane.getChildren().addAll(separator1,hbMainImg,separator2,hbLogo, separator3,accordion);
//		HBox finalHB= new HBox();
//		finalHB.getChildren().addAll(leftDrawerPane,hambHB);
		leftDrawer.setSidePane(leftDrawerPane);
		leftDrawer.setDefaultDrawerSize(200);
//		leftDrawer.setContent(hamBurger);
		leftDrawer.setOverLayVisible(false);
		leftDrawer.setOnDrawerClosed(new EventHandler<JFXDrawerEvent>() {

			@Override
			public void handle(JFXDrawerEvent event) {
				// TODO Auto-generated method stub
//				GridPane.setMargin(backImg, new Insets(0,0,0,-200));
				leftDrawer.open();
			}
		});
		
//		leftDrawer.setResizableOnDrag(true);
		
		sideDrawer.setSidePane(sideDrawerVB);
		sideDrawer.setDefaultDrawerSize(100);
		sideDrawer.setOverLayVisible(false);
//		sideDrawer.setResizableOnDrag(true);
		
//		JFXDrawersStack drawersStack = new JFXDrawersStack();
		// leftDrawerPane
//		if (leftDrawer.isShown()) {
//			leftDrawer.close();
//		} else {
			leftDrawer.open();
//		}

//		drawersStack.setContent(leftDrawer);



		hamBurger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			// if(drawersStack.getContent().equals(leftDrawer)){
			// drawersStack.setContent(null);
			// drawersStack.setContent(leftDrawer);
			// drawersStack.toggle(leftDrawer);
			if (leftDrawer.isShown()) {
				leftDrawer.close();
				hambHB.getChildren().clear();
//				sideDrawerVB.setMinHeight(1000);
//				hambHB.getChildren().addAll(,hamAnchor,anchorPane,logoutImg);
				
				sideDrawer.open();
//				HBox.setMargin(hamAnchor, new Insets(0, 500, 670, -100));
//				HBox.setMargin(anchorPane, new Insets(50,0,50,-200));
//				HBox.setMargin(logoutImg, new Insets(0,0,0,700));
//				HBox.setMargin(/anchorPane, new Insets(100, 0, 0, -1000));

			} else {
				leftDrawer.open();
				
				hambHB.getChildren().clear();
//				hambHB.getChildren().addAll(leftDrawer,hamAnchor,anchorPane,logoutImg);
				HBox.setMargin(hamAnchor, new Insets(0, 600, 670, 0));
//				HBox.setMargin(logoutImg, new Insets(0,0,0,385));

			}
			// leftDrawer.close();
		});
		// drawersStack.setContent(leftDrawer);
//		flowPane.getChildren().add(drawersStack);

//		splitPane1.setOrientation(Orientation.HORIZONTAL);
//		splitPane1.setPrefSize(200,200);
//		splitPane1.getItems().add(leftDrawer);
//		gridPane.add(vbMainImg, 0, 0,1,2);
//		gridPane.add(leftDrawer, 0, 0);
//		gridPane.setColumnSpan(leftDrawer, 10);
		leftDrawer.setMinSize(180, 200);
//		gridPane.add(hamBurger, 1, 0);


		hamAnchor.getChildren().add(hamBurger);
//		AnchorPane.setRightAnchor(hamBurger, 10d);
//		hamAnchor.setMaxWidth(50);

//		anchorPane.getChildren().addAll(leftDrawer,hamAnchor);
//		AnchorPane.setLeftAnchor(leftDrawer,10d);
		hamAnchor.setMaxSize(20, 50);
		HBox.setMargin(hamAnchor, new Insets(0, 600, 670, 0));


//		AnchorPane shortCutPane= new AnchorPane();
//		ImageView accountImage = new ImageView(new Image(ResourceLoader.load("/images/account.png")));
//		accountImage.setFitHeight(50);
//		accountImage.setFitWidth(50);
//		createAccBtn.setGraphic(accountImage);
//		shortCutPane.getChildren().addAll(createAccBtn);


		
		ImageView profileImg= new ImageView(new Image("/images/settingImg.png"));
		profileImg.setFitHeight(30);
		profileImg.setFitWidth(30);
		profileImg.setStyle("-fx-cursor:hand");
		profileMenu.setGraphic(profileImg);
		
//		MenuItem editDetMenuItem= new MenuItem("Edit Profile");
		ImageView editImg= new ImageView(new Image("/images/pencil-edit-button.png"));
		editImg.setFitHeight(20);
		editImg.setFitWidth(20);
		
		editDetMenuItem.setGraphic(editImg);
		editDetMenuItem.setStyle("-fx-font: 12 century ;-fx-font-weight:bold;-fx-text-fill:black;-fx-cursor:hand;");
		editDetMenuItem.setOnAction(e->setOnActionMethod(e));
//		editDetMenuItem.setStyle("-fx-cursor:hand;");
//		MenuItem logoutMenuItem= new MenuItem("Logout");
		logoutMenuItem.setGraphic(logoutImg);
		logoutMenuItem.setStyle("-fx-font: 12px century;-fx-font-weight:bold;-fx-text-fill:black;-fx-cursor:hand;");
		profileMenu.getItems().addAll(editDetMenuItem,logoutMenuItem);
		logoutImg.setFitHeight(20);
		logoutImg.setFitWidth(20);
		logoutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Alert alert= new Alert(AlertType.CONFIRMATION,"Are you sure you want to logout?");

				alert.setTitle("Logout Confirmation");
				// alert.setHeaderText("HI");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					primaryStage.close();
					splashScreen.showMainStage();
				}
			}
		});

//		logoutbtn.setGraphic(logoutImg);
//		logoutbtn.setMaxSize(60, 20);
//		logoutbtn.setStyle("-fx-font-size:15;-fx-text-fill:black;-fx-cursor:hand;-fx-background-color:transparent;-fx-border-color:transparent;");
//		logoutHB.getChildren().add(logoutImg);
//		logoutHB.setMaxHeight(60);
//		logoutHB.setMaxWidth(50);
//		logoutHB.setStyle("-fx-border-style: solid inside;"+
//				                 "-fx-border-width: 2;" +
//				                  "-fx-border-insets: 5;" +
//				                    "-fx-border-radius: 5;" +
//				                   "-fx-border-color: red;");

//		logoutImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				// TODO Auto-generated method stub
//				Alert alert= new Alert(AlertType.CONFIRMATION,"Are you sure you want to logout?");
//
//				alert.setTitle("Logout Confirmation");
//				// alert.setHeaderText("HI");
//				Optional<ButtonType> result = alert.showAndWait();
//				if (result.get() == ButtonType.OK) {
//					primaryStage.close();
//					splashScreen.showMainStage();
//				}
//
//				}
//		});




//		AnchorPane createAccPane= new AnchorPane();

//		creatAcountBtn.setStyle("");
//		createAccPane.getChildren().add(shortAcountBtn);
//		AnchorPane.setTopAnchor(creatAcImg, 50d);

//		shortAcountBtn.setOnAction(e->{
//			setOnActionMethod(e);
//			accountTitlePane.setExpanded(true);
//		});
//
////		AnchorPane creatCustPane= new AnchorPane();

		ImageView purchEntImg= new ImageView(new Image(ResourceLoader.load("/images/purchase.png")));
		purchEntImg.setFitHeight(60);
		purchEntImg.setFitWidth(60);
		shortPurchEntBtn= new Button("Create Purchase Entry",purchEntImg);
		shortPurchEntBtn.getStyleClass().add("short-btn");
		shortPurchEntBtn.setMinHeight(100);
		shortPurchEntBtn.setMinWidth(300);
		shortPurchEntBtn.setOnAction(e->{
			setOnActionMethod(e);
			purchTitlePane.setExpanded(true);

		});
		ImageView createCustImg= new ImageView(new Image(ResourceLoader.load("/images/customer.png")));
		createCustImg.setFitHeight(60);
		createCustImg.setFitWidth(60);
		shortCustomerBtn= new Button("Sales Return",createCustImg);
		shortCustomerBtn.getStyleClass().add("short-btn");
		shortCustomerBtn.setMinHeight(100);
		shortCustomerBtn.setMinWidth(300);
		shortCustomerBtn.setOnAction(e->{
		setOnActionMethod(e);
		returnTitlePane.setExpanded(true);
		});
//		creatCustPane.getChildren().add(shortCustomerBtn);

//		AnchorPane saleInvoicePane= new AnchorPane();
		ImageView saleInvoiceImg= new ImageView(new Image(ResourceLoader.load("/images/sales.png")));
		saleInvoiceImg.setFitHeight(60);
		saleInvoiceImg.setFitWidth(60);
		shortSaleInBtn= new Button("Create Sales Invoice",saleInvoiceImg);
		shortSaleInBtn.getStyleClass().add("short-btn");
		shortSaleInBtn.setMinHeight(100);
		shortSaleInBtn.setMinWidth(60);
		shortSaleInBtn.setOnAction(e->{
			setOnActionMethod(e);
			saleTitlePane.setExpanded(true);
		});
//		saleInvoice/Pane.getChildren().add(shortSaleInBtn);


//		AnchorPane addStockPane= new AnchorPane();
		ImageView addStockImg= new ImageView(new Image(ResourceLoader.load("/images/inventory.png")));
		addStockImg.setFitHeight(60);
		addStockImg.setFitWidth(70);
		shortStockBtn= new Button(" Purchase Return  ",addStockImg);
		shortStockBtn.getStyleClass().add("short-btn");
		shortStockBtn.setMinHeight(100);
		shortStockBtn.setMinWidth(60);
		shortStockBtn.setOnAction(e->{
		setOnActionMethod(e);
		returnTitlePane.setExpanded(true);
		});


//		addStockP/ane.getChildren().add(shortStockBtn);

		anchorPane.getChildren().clear();
//		AnchorPane.setTopAnchor(createAccPane, 50d);

//		gridPane.setMaxSize(400, 300);
//		gridPane.setGridLinesVisible(true);
		gridPane.setVgap(30);
		gridPane.setHgap(30);
//		HBox mainHB= new HBox();
//		mainHB.getChildren().add(gridPane);
//		mainHB.setStyle("-fx-background-image: url('/images/backgroundImg.png')");
		
		
//		Label companyDetails= new Label("Company Details");
		
		
		
//		companyDetails.setStyle("-fx-font-size:24;-fx-text-fill:Red;-fx-font-weight:bold");
//		GridPane.setMargin(companyDetails, new Insets(0,-100,0,150));
//		gridPane.add(companyDetails, 0, 0);
//		gridPane.add(companyName, 0, 2);
//		gridPane.add(address, 1, 2);
//		gridPane.add(officeAddress, 0, 4);
//		gridPane.add(emailId, 1, 4); 
//		gridPane.add(contactNo, 0, 6);
//		gridPane.add(altContactNo, 1, 6);
//		gridPane.add(state, 0, 8);
//		gridPane.add(gstin, 1, 8);
//		gridPane.add(logoPath, 0, 9);
//		GridPane.setMargin(openBtn, new Insets(0,0,0,-100));
//		gridPane.add(openBtn, 1, 9);
//		
//		GridPane.setMargin(submitBtn, new Insets(0,-100,0,150));
////		
//		
//		gridPane.add(submitBtn, 0, 10);
		
//		backImg.setFitHeight(700);
//		backImg.setFitWidth(1050);
		
//		dateHB.getChildren().addAll(time,profileMenu);
		
		backImg.fitWidthProperty().bind(scene.widthProperty().subtract(leftDrawer.widthProperty().add(5)));
		backImg.fitHeightProperty().bind(scene.heightProperty().subtract(2));
//		backImg.fitHeightProperty().bind(scrollPane.heightProperty().subtract(hambHB.heightProperty().add(leftDrawer.heightProperty())));
		
		anchorPane.getChildren().add(gridPane);
		gridPane.getChildren().clear();
		gridPane.add(backImg, 0, 0);
		
//		gridPane.setPrefSize(800, 760);
//		GridPane.setMargin(backImg, new Insets(0,0,0,10));
//		GridPane.setMargin(profileMenu, new Insets(10,900,0,900));
//		gridPane.add(profileMenu, 0, 0);
//		anchorPane.setStyle("-fx-background-image:url('/images/rupee.png')");
		
	
//		anchorPane.getChildren().add(backImg);
//		AnchorPane.setLeftAnchor(backImg, 0d);
		reciptMasterLbl.setStyle("-fx-cursor:hand");
		reciptMasterLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
//				hambHB.getChildren().removeAll(leftDrawer,anchorPane);
				
				anchorPane.getChildren().clear();
				hambHB.setMargin(anchorPane, new Insets(0,0,0,0));
				backImg.fitWidthProperty().bind(scene.widthProperty().subtract(leftDrawer.widthProperty().add(5)));
				backImg.fitHeightProperty().bind(scene.heightProperty().subtract(2));
				hambHB.setMargin(profileMenu, new Insets(10,0,0,-80));
				anchorPane.getChildren().add(gridPane);
//				HBox.setMargin(anchorPane, new Insets(10,-990,0,990));
//				HBox.setMargin(anchorPane, new Insets(10,-980,0,980));
//				HBox.setMargin(logoutImg, new Insets(0,-600,0,350));
			}
		});
//		anchorPane.setMaxSize(400, 300);
		hambHB.getChildren().clear();
//		HBox.setMargin(anchorPane, new Insets(100,-50,50,-550));
//		HBox.setMargin(profileMenu, new Insets(10,-990,0,990));
		HBox.setMargin(profileMenu, new Insets(10,0,0,-100));
//		HBox.setMargin(mainHB, new Insets(0,0,0,200));
//		HBox.setMargin(logoutImg, new Insets(0,-600,0,350));
//		AnchorPane.setRightAnchor(gridPane, 900d);
//		anchorPane.getChildren().addAll(gridPane);
		
		hambHB.getChildren().addAll(leftDrawer,anchorPane,profileMenu);

//		scrollPane.setPannable(true);
		scrollPane.setHmin(0);
		scrollPane.setHmax(100);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
//		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		scrollPane.setContent(hambHB);
//		anchorPane.getChildren().add(hambHB);

		 scene.getStylesheets().add(getClass().getResource("/images/file2.css").toExternalForm());
//		 Main.accountView.setOnKeyPressed(e->{e.consume();});
		 KeyEventHandler.singlekeyEvent(scene);
//		 KeyEventHandler.DeleteHandler(scene);
//		 KeyEventHandler.keyComboEvent(scene);
//		 scene.addEventHandler(KeyEvent.KEY_PRESSED, e->{
//			
//		 });
		 
		
		 Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

	        //set Stage boundaries to visible bounds of the main screen
	        primaryStage.setX(primaryScreenBounds.getMinX());
	        primaryStage.setY(primaryScreenBounds.getMinY());
	        primaryStage.setWidth(primaryScreenBounds.getWidth());
	        primaryStage.setHeight(primaryScreenBounds.getHeight());
		 
		primaryStage.setTitle("Billing Master");
		primaryStage.getIcons().add(new Image(ResourceLoader.load("/images/receiptMasterLOGO.png")));
		primaryStage.setScene(scene);


//		primaryStage.setResizable(true);
		 primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(2));
		 primaryStage.minHeightProperty().bind(scene.widthProperty().divide(2));
//		primaryStage.resizableProperty().setValue(Boolean.FALSE);

		primaryStage.setMaximized(true);
		
		primaryStage.show();
	
		
		scene.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event1) {
				// TODO Auto-generated method stub
				Alert alert= new Alert(AlertType.CONFIRMATION,"DO you really want to exit the Billing Master?");
				alert.initOwner(Main.primaryStage);
				alert.setTitle("Exit confirmation");
				alert.setHeaderText(null);
//				ButtonType yesBtn= new ButtonType("Yes",ButtonData.OK_DONE);

//				ButtonType noBtn= new ButtonType("No",ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().clear();
				alert.getButtonTypes().addAll(ButtonType.YES,ButtonType.NO);


				DialogPane dialogPane=alert.getDialogPane();
				dialogPane.getStylesheets().add(KeyEventHandler.class.getResource("/images/popup.css").toExternalForm());

				dialogPane.getStyleClass().add("alert");


				Button yesBtn= (Button)dialogPane.lookupButton(ButtonType.YES);
				yesBtn.setDefaultButton(false);

				dialogPane.addEventHandler(KeyEvent.KEY_PRESSED, e->{
					if(e.getCode()==KeyCode.Y) {
						System.exit(0);
					}
					else if(e.getCode()==KeyCode.N) {
						alert.close();
						e.consume();
					}

				});
//				EventHandler<KeyEvent> fireOnEnter = event -> {
//				    if (KeyCode.Y.equals(event.getCode())
//				            && event.getTarget() instanceof Button) {
//				        ((Button) event.getTarget()).fire();
//
//				    }
//				    else if(KeyCode.N.equals(event.getCode())
//				            && event.getTarget() instanceof Button) {
////				        ((Button) event.getTarget()).fire();
//				    	alert.close();
//				        }
//				};
//
//
//				dialogPane.getButtonTypes().stream()
//				        .map(dialogPane::lookupButton)
//				        .forEach(button ->
//				                button.addEventHandler(
//				                        KeyEvent.KEY_PRESSED,
//				                        fireOnEnter
//				                )
//				        );
				Optional<ButtonType> result= alert.showAndWait();
				if(result.get()==ButtonType.YES) {
					System.exit(0);
				}else if(result.get()==ButtonType.NO) {
				event1.consume();
				}


			}
		});

//		Node arrow1=accountTitlePane.lookup(".arrow");
//		arrow1.setVisible(false);

		Node arrow1=purchTitlePane.lookup(".arrow");
		arrow1.setVisible(false);

		arrow1=saleTitlePane.lookup(".arrow");
		arrow1.setVisible(false);
//		arrow1= inventTitlePane.lookup(".arrow");
//		arrow1.setVisible(false);
//		arrow1=goodTitlePane.lookup(".arrow");
//		arrow1.setVisible(false);
//		Pane arrow= (Pane) accountTitlePane.lookup(".arrow");
//		arrow.translateXProperty().bind(accountTitlePane.widthProperty().subtract(arrow.widthProperty().multiply(2)));
//
//		arrow= (Pane) purchTitlePane.lookup(".arrow");
//		arrow.translateXProperty().bind(purchTitlePane.widthProperty().subtract(arrow.widthProperty().multiply(2)));
//
//		 arrow= (Pane) saleTitlePane.lookup(".arrow");
//		arrow.translateXProperty().bind(saleTitlePane.widthProperty().subtract(arrow.widthProperty().multiply(2)));
//
//		 arrow= (Pane) inventTitlePane.lookup(".arrow");
//		arrow.translateXProperty().bind(inventTitlePane.widthProperty().subtract(arrow.widthProperty().multiply(2)));
//
//		arrow= (Pane) goodTitlePane.lookup(".arrow");
//		arrow.translateXProperty().bind(goodTitlePane.widthProperty().subtract(arrow.widthProperty().multiply(2)));
////		Pane headerTitle = (Pane) accountTitlePane.lookup(".header");
////		headerTitle.translateXProperty().bind(arrow.widthProperty().negate());
		smsList.clear();
		 Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
//					System.out.println("reached");
					// TODO Auto-generated method stub
//					Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {            
//				        Calendar cal = Calendar.getInstance();
//				        second = cal.get(Calendar.SECOND);
//				        minute = cal.get(Calendar.MINUTE);
//				        hour = cal.get(Calendar.HOUR);
//				        //System.out.println(hour + ":" + (minute) + ":" + second);
//				        time.setText(hour + ":" + (minute) + ":" + second);
//				    }),
//				         new KeyFrame(Duration.seconds(1))
//				    );
//				    clock.setCycleCount(Animation.INDEFINITE);
//				    clock.play();
				    
				    
					String stmt="select * from rm_sms_backlog";
					try {
						ResultSet rs= DBUtil.dbExecuteQuery(stmt);
						while(rs.next()) {
							SMSBacklog sender= new SMSBacklog();
							sender.setId(rs.getLong("id"));
							sender.setContactNo(rs.getString("contactNo"));
							sender.setMessage(rs.getString("messageText"));
							smsList.add(sender);
						}
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(!smsList.isEmpty()) {
						SmsSender.initializeSMS(smsList);
					}
				}
			});
		 
	}

	public void setOnActionMethod(ActionEvent e) {
		if(e.getSource()==supplierRecBtn||e.getSource()==supplierMenuItem) {
			supplierView = purchaseController.showSupplier(anchorPane);
			supplierView.getStyleClass().add("edge-to-edge");
			anchorPane.getChildren().clear();
//			supplierView.autosize();
			supplierView.setPrefSize(900, 500);

			supplierView.requestFocus();
			supplierView.getSelectionModel().selectFirst();
			supplierView.getFocusModel().focus(0);
//			supplierView.setBorder(null);
//			supplierView.setMaxSize(1000, 900);
//			AnchorPane anchorPane = new AnchorPane();
			searchBtn=new JFXTextField();

			 HBox btnHB = new HBox();
			 btnHB.setSpacing(20);
			 btnHB.getChildren().addAll(addSupBtn,editSupBtn);

//			 Pagination pagination= new Pagination((purchaseController.sortedData.size()/20),0);
//			 pagination.setPageFactory(new Callback<Integer, Node>() {
//
//				@Override
//				public Node call(Integer pageIndex) {
//					// TODO Auto-generated method stub
//					return createPage(pageIndex);
//				}
//			});
//

			anchorPane.getChildren().addAll(supplierView,searchBtn,btnHB);
//			AnchorPane.setBottomAnchor(pagination, 10d);
//			HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
			HBox.setMargin(profileMenu, new Insets(10,0,0,20));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
//			anchorPane.setMinSize(800, 500);
//			addBtn.setAlignment(Pos.BOTTOM_RIGHT);
			searchBtn.setMinWidth(500);
			AnchorPane.setTopAnchor(searchBtn, -50d);
			AnchorPane.setBottomAnchor(btnHB,50d);

//			hambHB.getChildren().removeAll(anchorPane,logoutImg);
//			HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//			hambHB.getChildren().addAll(logoutImg,anchorPane);
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);
			
			searchBtn.setPromptText("Search");
			searchBtn.setLabelFloat(true);
			searchBtn.textProperty().addListener((observable,oldValue,newValue)->{
				purchaseController.filteredData.setPredicate(supplier->{
					if(newValue==null||newValue.isEmpty()){
						return true;
					}
					String lowerCaseFilter=newValue.toLowerCase();

					if(supplier.getSupplierName().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
					else if(supplier.getOwnerName().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
					else if(supplier.getAddress().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
					else if(supplier.getContactNO().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
					else if(supplier.getGstState().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
					else if(supplier.getVatTinNo().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
					return false;
				});
			});

//			purchaseController.searchBtn.setMinWidth(500);

			Label newLbl= new Label("N");
			newLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label createLbl= new Label("Create ");
			createLbl.setStyle("-fx-text-fill:white");
			Label createNewLbl= new Label("ew");
			createNewLbl.setStyle("-fx-text-fill:white");
			HBox createNewHB= new HBox();
			createNewHB.getChildren().addAll(createLbl,newLbl,createNewLbl);
			addSupBtn.setGraphic(createNewHB);

			Label eLbl= new Label("U");
			eLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label updateLbl= new Label("pdate");
			updateLbl.setStyle("-fx-text-fill:white");
			HBox updateHB= new HBox();
			updateHB.getChildren().addAll(eLbl,updateLbl);
			editSupBtn.setGraphic(updateHB);

			addSupBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					GridPane gridPane = purchaseController.addSupplier(anchorPane);
					editSupBtn.setDisable(false);
					Scene scene = new Scene(gridPane,700,600);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);
					scene.addEventHandler(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ENTER) {
						purchaseController.submitSupplierFunc(anchorPane);
						}else if(e.getCode()==KeyCode.ESCAPE) {
							Main.dialog.close();
						}

					});
					 dialog = new Stage();

					dialog.setScene(scene);
//					dialog.setX(200);
//					dialog.setY(150);
//					dialog.setTitle("Add New Supplier");
					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
//			        GridPane.setMargin(closeImg, new Insets(0,0,0,-50));
			        gridPane.add(closeImg, 2, 0);
			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});


					dialog.show();

//
				}
			});

//			editSupBtn.setMnemonicParsing(true);

			editSupBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					supplierView.refresh();
					if(supplierView.getItems().isEmpty()) {
						editSupBtn.setDisable(true);
						return;
					}
					editSupBtn.setDisable(false);
					Supplier supplier = supplierView.getSelectionModel().getSelectedItem();
					int index=supplierView.getSelectionModel().getSelectedIndex();
					GridPane gridPane2 =purchaseController.updateSupplier(supplier,index,anchorPane);
					Scene scene = new Scene(gridPane2,700,600);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);
					scene.addEventHandler(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ENTER) {
						purchaseController.updateSupplierFunc(supplier, index,anchorPane);
						}else if(e.getCode()==KeyCode.ESCAPE) {
							Main.dialog.close();
						}

					});
					 dialog = new Stage();

					dialog.setScene(scene);
//					dialog.setX(200);
//					dialog.setY(150);
//					dialog.setTitle("Add New Supplier");
					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
//			        GridPane.setMargin(closeImg, new Insets(0,0,0,-50));
			        gridPane2.add(closeImg, 2, 0);
			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});


					dialog.show();

//					getTableRow().commitEdit(supplier);
				}
			});



		}

		else if(e.getSource()==prchsEntryBtn||e.getSource()==shortPurchEntBtn) {
			purchaseView = purchaseController.showPurchaseRecord(anchorPane);
			purchaseView.setMinSize(900, 500);

			anchorPane.getChildren().clear();

			purchaseView.requestFocus();
			purchaseView.getSelectionModel().selectFirst();
			purchaseView.getFocusModel().focus(0);
			searchBtn=new JFXTextField();

			 HBox btnHB = new HBox();
			 btnHB.setSpacing(20);
			 btnHB.getChildren().addAll(createPurBtn);
			 searchBtn.setMinWidth(500);

			anchorPane.getChildren().addAll(purchaseView,searchBtn,btnHB);
			HBox.setMargin(profileMenu, new Insets(10,0,0,20));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
//			HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
//			anchorPane.setMinSize(800, 500);
//			addBtn.setAlignment(Pos.BOTTOM_RIGHT);

//				searchBtn=new JFXTextField();


			AnchorPane.setTopAnchor(searchBtn, -50d);
			AnchorPane.setBottomAnchor(btnHB,50d);

			hambHB.getChildren().removeAll(anchorPane,profileMenu);
//			HBox.setMargin(logoutImg, new Insets(0,0,0,400));
			hambHB.getChildren().addAll(anchorPane,profileMenu);

			searchBtn.setPromptText("Search");
			searchBtn.setLabelFloat(true);
			searchBtn.textProperty().addListener((observable,oldValue,newValue)->{
				purchaseController.purchFilteredData.setPredicate(purchaseEntry->{
					if(newValue==null||newValue.isEmpty()){
						return true;
					}
					String lowerCaseFilter=newValue.toLowerCase();

					if(purchaseEntry.getInvoiceNo().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
					else if(purchaseEntry.getPurchEntryNo().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
					else if(String.valueOf(purchaseEntry.getTotal()).contains(lowerCaseFilter)){
						return true;
					}
					else if(String.valueOf(purchaseEntry.getPurchaseDate()).contains(lowerCaseFilter)){
						return true;
					}
//					else if(purchaseEntry.getGstState().toLowerCase().contains(lowerCaseFilter)){
//						return true;
//					}
//					else if(purchaseEntry.getVatTinNo().toLowerCase().contains(lowerCaseFilter)){
//						return true;
//					}
					return false;
				});
			});

			Label newLbl= new Label("N");
			newLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label createLbl= new Label("Create ");
			createLbl.setStyle("-fx-text-fill:white");
			Label createNewLbl= new Label("ew");
			createNewLbl.setStyle("-fx-text-fill:white");
			HBox createNewHB= new HBox();
			createNewHB.getChildren().addAll(createLbl,newLbl,createNewLbl);
			createPurBtn.setGraphic(createNewHB);

			Label eLbl= new Label("U");
			eLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label updateLbl= new Label("pdate");
			updateLbl.setStyle("-fx-text-fill:white");
			HBox updateHB= new HBox();
			updateHB.getChildren().addAll(eLbl,updateLbl);
			editPurBtn.setGraphic(updateHB);

			createPurBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					GridPane gridPane = purchaseController.createPurchaseEntry(anchorPane);
//					gridPane.setMinSize(1000, 800);
					Scene scene = new Scene(gridPane,1400,770);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);


					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ESCAPE) {
							Main.dialog.close();
							e.consume();
						}
					});
					 dialog = new Stage();

					dialog.setScene(scene);

					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(0,100,0,-50));
			        gridPane.add(closeImg, 7, 0);
			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});

					editPurBtn.setDisable(false);
					
					dialog.show();

				}
			});



			editPurBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					if(purchaseView.getItems().isEmpty()) {
						editPurBtn.setDisable(true);
						return;
					}
					editPurBtn.setDisable(false);
					PurchaseEntry purchaseEntry = purchaseView.getSelectionModel().getSelectedItem();
					int index=purchaseView.getSelectionModel().getSelectedIndex();
					GridPane gridPane = purchaseController.updatePurchaseEntry(purchaseEntry,index,anchorPane);
					Scene scene = new Scene(gridPane,1400,770);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);


					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ESCAPE) {
							Main.dialog.close();
							e.consume();
						}
					});
					 dialog = new Stage();

					dialog.setScene(scene);

					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(0,100,0,-50));
			        gridPane.add(closeImg, 7, 0);
			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});

//					editBtn.setDisable(false);
					dialog.show();
				}
			});



//
//			returnPurcBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//				@Override
//				public void handle(ActionEvent event) {
//					// TODO Auto-generated method stub
//					if(purchaseView.getItems().isEmpty()) {
//						returnPurcBtn.setDisable(true);
//						return;
//					}
//					returnPurcBtn.setDisable(false);
//					PurchaseEntry purchaseEntry=purchaseView.getSelectionModel().getSelectedItem();
//					int index=purchaseView.getSelectionModel().getSelectedIndex();
//
//					GridPane gridPane= purchaseController.returnPurchaseGoods(purchaseEntry,index,anchorPane);
//					Scene scene=new Scene(gridPane,1350,700);
//					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
//					scene.setFill(Color.TRANSPARENT);
//
//					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
//						if(e.getCode()==KeyCode.ESCAPE) {
//						Main.dialog.close();
//						e.consume();
//					}
//					});
//
//					dialog = new Stage();
//					dialog.setScene(scene);
//
//					dialog.initOwner(primaryStage);
//					dialog.initModality(Modality.WINDOW_MODAL);
//					dialog.initStyle(StageStyle.TRANSPARENT);
//					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
//			        closeImg.setFitHeight(30);
//			        closeImg.setFitWidth(30);
//			        closeImg.setStyle("-fx-cursor:hand");
////			        GridPane.setMargin(closeImg, new Insets(-40,0,20,-30));
//			        gridPane.add(closeImg, 3, 0);
//			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//			    		@Override
//			    		public void handle(MouseEvent arg0) {
//			    			// TODO Auto-generated method stub
//			    			dialog.close();
//			    		}
//
//			    	});
//
//			        dialog.show();
//				}
//			});

		}else if(e.getSource()==createCustBtn) {
			custView=salesController.showCust(anchorPane);
			custView.setMinSize(900, 500);
			anchorPane.getChildren().clear();

			custView.requestFocus();
			custView.getSelectionModel().selectFirst();
			custView.getFocusModel().focus(0);

			searchBtn= new JFXTextField();
			searchBtn.setMinWidth(500);

			 HBox btnHB = new HBox();
			 btnHB.setSpacing(20);
			 btnHB.getChildren().addAll(createNewCustBtn,editCustBtn);
			anchorPane.getChildren().addAll(custView,searchBtn,btnHB);
//			HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
			HBox.setMargin(profileMenu, new Insets(10,0,0,20));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
//			anchorPane.setMinSize(800, 500);
//			addBtn.setAlignment(Pos.BOTTOM_RIGHT);
			AnchorPane.setBottomAnchor(btnHB,50d);
			AnchorPane.setTopAnchor(searchBtn, -50d);

//			hambHB.getChildren().removeAll(anchorPane,logoutImg);
//			HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//			hambHB.getChildren().addAll(logoutImg,anchorPane);
			
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);

			searchBtn.setPromptText("Search");
			searchBtn.setLabelFloat(true);
			searchBtn.textProperty().addListener((observable,oldValue,newValue)->{
				salesController.custFilteredData.setPredicate(customer->{
					if(newValue==null||newValue.isEmpty()){
						return true;
					}
					String lowerCaseFilter=newValue.toLowerCase();

					if(customer.getFull_name().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(customer.getContact().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(customer.getAddress().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
//					else if(customer.getArea().toLowerCase().contains(lowerCaseFilter)) {
//						return true;
//					}
//					else if(customer.getCity().toLowerCase().contains(lowerCaseFilter)) {
//						return true;
//					}
					else if(customer.getState().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(customer.getGstin().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(String.valueOf(customer.getSrNo()).contains(lowerCaseFilter)) {
						return true;
					}
					return false;

				});
			});

			Label newLbl= new Label("N");
			newLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label createLbl= new Label("Create ");
			createLbl.setStyle("-fx-text-fill:white");
			Label createNewLbl= new Label("ew");
			createNewLbl.setStyle("-fx-text-fill:white");
			HBox createNewHB= new HBox();
			createNewHB.getChildren().addAll(createLbl,newLbl,createNewLbl);
			createNewCustBtn.setGraphic(createNewHB);

			Label eLbl= new Label("U");
			eLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label updateLbl= new Label("pdate");
			updateLbl.setStyle("-fx-text-fill:white");
			HBox updateHB= new HBox();
			updateHB.getChildren().addAll(eLbl,updateLbl);
			editCustBtn.setGraphic(updateHB);

			createNewCustBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					editCustBtn.setDisable(false);
					GridPane gridPane = salesController.createNewCust(anchorPane);

					Scene scene=new Scene(gridPane,750,700);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);
					scene.addEventHandler(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ENTER)
						{
							salesController.creatCustSubmit(anchorPane);
						}else if(e.getCode()==KeyCode.ESCAPE) {
							Main.dialog.close();
						}
					});
					dialog = new Stage();
					dialog.setScene(scene);

					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(-40,0,20,-30));
			        gridPane.add(closeImg, 3, 0);
			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});

			        dialog.show();

				}
			});

//			edi.setMnemonicParsing(true);
			editCustBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					if(custView.getItems().isEmpty()) {
						editCustBtn.setDisable(true);
					}else {
						editCustBtn.setDisable(false);
					Customer customer=custView.getSelectionModel().getSelectedItem();
					int index=custView.getSelectionModel().getSelectedIndex();
					GridPane gridPane = salesController.updateCustomer(customer, index,anchorPane);
					Scene scene=new Scene(gridPane,750,700);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);
					scene.addEventHandler(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ENTER) {
							salesController.updateCustSubmit(customer, index,anchorPane);
						}else if(e.getCode()==KeyCode.ESCAPE) {
							Main.dialog.close();
						}
					});
					 dialog = new Stage();
					dialog.setScene(scene);

					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(-40,0,20,-30));
			        gridPane.add(closeImg, 3, 0);
			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});

			        dialog.show();
					}
				}
			});
		}
		else if(e.getSource()==dmRecBtn) {
			deliveryView=salesController.showDeliveryMemo(anchorPane);
			
			deliveryView.setMinSize(900, 500);
			anchorPane.getChildren().clear();

			deliveryView.requestFocus();
			deliveryView.getSelectionModel().selectFirst();
			deliveryView.getFocusModel().focus(0);
			searchBtn=new JFXTextField();
			searchBtn.setMinWidth(500);
			searchBtn.setPromptText("Search");
			searchBtn.setLabelFloat(true);
			
			 HBox btnHB = new HBox(); 
			 btnHB.setSpacing(20);
			 btnHB.getChildren().addAll(createDMBtn,createDMInvBtn);
			anchorPane.getChildren().addAll(deliveryView,searchBtn,btnHB);
//			HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
			AnchorPane.setTopAnchor(searchBtn, -50d);
			AnchorPane.setBottomAnchor(btnHB,50d);
			HBox.setMargin(profileMenu, new Insets(10,0,0,20));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
			

//			hambHB.getChildren().removeAll(anchorPane,logoutImg);
//			HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//			hambHB.getChildren().addAll(logoutImg,anchorPane);
//			
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);
			
			searchBtn.textProperty().addListener((observable,oldValue,newValue)->{
				salesController.delFilteredData.setPredicate(deliveryMemo->{
					if(newValue==null||newValue.isEmpty()){
						return true;
					}
					String lowerCaseFilter=newValue.toLowerCase();

					if(String.valueOf(deliveryMemo.getSrNo()).toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(deliveryMemo.getDm_no().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(String.valueOf(deliveryMemo.getEntry_date()).contains(lowerCaseFilter)) {
						return true;
					}
					else if(deliveryMemo.getCustomer().getFull_name().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(String.valueOf(deliveryMemo.getAdvanceAmt()).contains(lowerCaseFilter)) {
						return true;
					}
					return false;
				});
			});
			
			Label newLbl= new Label("N");
			newLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label createLbl= new Label("Create ");
			createLbl.setStyle("-fx-text-fill:white");
			Label createNewLbl= new Label("ew");
			createNewLbl.setStyle("-fx-text-fill:white");
			HBox createNewHB= new HBox();
			createNewHB.getChildren().addAll(createLbl,newLbl,createNewLbl);
			createDMBtn.setGraphic(createNewHB);
			
			createDMBtn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					createDMInvBtn.setDisable(false);
					GridPane gridPane=salesController.createDeliveryMemo(anchorPane);
					
					Scene scene=new Scene(gridPane,1000,750);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			
			scene.addEventFilter(KeyEvent.KEY_TYPED, e->{
				long now= Instant.now().toEpochMilli();
				if(now-lastEventTimeStamp>THRESHOLD) {
					barcode.delete(0, barcode.length());
				}
				lastEventTimeStamp=now;
				if(e.getCharacter().charAt(0)==(char) 0x000d) {
					if(barcode.length()>=MIN_BARCODE_LENGTH) {
						salesController.fireBarcode(barcode.toString());
					}
					barcode.delete(0, barcode.length());
				}else {
					barcode.append(e.getCharacter());
				}
//				e.consume();
//
			});
			
			scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
				if(e.getCode()==KeyCode.ESCAPE) {
				Main.dialog.close();
			}
			});
			
		
			dialog = new Stage();
			dialog.setScene(scene);

			dialog.initOwner(primaryStage);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initStyle(StageStyle.TRANSPARENT);
			ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
	        closeImg.setFitHeight(30);
	        closeImg.setFitWidth(30);
	        closeImg.setStyle("-fx-cursor:hand");
	        GridPane.setMargin(closeImg, new Insets(0,0,0,-50));
	        gridPane.add(closeImg, 4, 0);

	        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    		@Override
	    		public void handle(MouseEvent arg0) {
	    			// TODO Auto-generated method stub
	    			dialog.close();
	    		}

	    	});

	        dialog.show();
		}
	});
			
			Label invLbl= new Label("I");
			invLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label saleInvLbl = new Label("Create Sale ");
			saleInvLbl.setStyle("-fx-text-fill:white");
			Label invLbl2= new Label("nvoice");
			invLbl2.setStyle("-fx-text-fill:white");
			HBox saleInvHB= new HBox();
			saleInvHB.getChildren().addAll(saleInvLbl,invLbl,invLbl2);
			createDMInvBtn.setGraphic(saleInvHB);
			createDMInvBtn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					if(deliveryView.getItems().isEmpty()) {
						createDMInvBtn.setDisable(true);
						return;
					}

					DeliveryMemo deliveryMemo= deliveryView.getSelectionModel().getSelectedItem();
					int index=deliveryView.getSelectionModel().getSelectedIndex();
					if(deliveryMemo.getStatus()==0) {
						Alert alert = new Alert(AlertType.INFORMATION,"Sales Invoice Already Generated");
						alert.setTitle("Information");
//						alert.setHeaderText("HI");
						alert.showAndWait();
						return;
					}
					GridPane gridPane= salesController.createDeliveryInvoice(deliveryMemo,index,anchorPane);
					Scene scene=new Scene(gridPane,1350,790);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);
				
					scene.addEventFilter(KeyEvent.KEY_TYPED, e->{
						long now= Instant.now().toEpochMilli();
						if(now-lastEventTimeStamp>THRESHOLD) {
							barcode.delete(0, barcode.length());
						}
						lastEventTimeStamp=now;
						if(e.getCharacter().charAt(0)==(char) 0x000d) {
							if(barcode.length()>=MIN_BARCODE_LENGTH) {
								salesController.fireBarcode(barcode.toString());
							}
							barcode.delete(0, barcode.length());
						}else {
							barcode.append(e.getCharacter());
						}
//						e.consume();
		//
					});
					
					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ESCAPE) {
							Main.dialog.close();
						}
			});
					dialog = new Stage();
					dialog.setScene(scene);

					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(0,0,0,-50));
			        gridPane.add(closeImg, 4, 0);

			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});

			        dialog.show();
				}
			});
			
			
			deliveryView.setRowFactory(tv ->{
				TableRow<DeliveryMemo> row=new TableRow<>();
				row.setOnMouseClicked(event1->{
					if(event1.getClickCount()==2&&(!row.isEmpty())) {
						DeliveryMemo deliveryMemo=row.getItem();
						GridPane gridPane=salesController.viewDeliveryMemo(deliveryMemo,anchorPane);
						Scene scene=new Scene(gridPane,1000,650);
						scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
						scene.setFill(Color.TRANSPARENT);
						scene.addEventFilter(KeyEvent.KEY_PRESSED, event->{
							if(event.getCode()==KeyCode.ESCAPE) {
							Main.dialog.close();
						}
						});

						 dialog = new Stage();
							dialog.setScene(scene);

							dialog.initOwner(primaryStage);
							dialog.initModality(Modality.WINDOW_MODAL);
							dialog.initStyle(StageStyle.TRANSPARENT);
							ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
					        closeImg.setFitHeight(30);
					        closeImg.setFitWidth(30);
					        closeImg.setStyle("-fx-cursor:hand");
					        GridPane.setMargin(closeImg, new Insets(0,0,0,-50));
					        gridPane.add(closeImg, 4, 0);
					        
					        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

					    		@Override
					    		public void handle(MouseEvent arg0) {
					    			// TODO Auto-generated method stub
					    			dialog.close();
					    		}

					    	});

					        dialog.show();
					}
			});
				return row;
		});
				
			
		}

		else if(e.getSource()==salesRecBtn||e.getSource()==shortSaleInBtn){

			salesView=salesController.showInvoice(anchorPane);
			salesView.setMinSize(900, 500);
			anchorPane.getChildren().clear();

			salesView.requestFocus();
			salesView.getSelectionModel().selectFirst();
			salesView.getFocusModel().focus(0);
			searchBtn=new JFXTextField();
			searchBtn.setMinWidth(500);
			searchBtn.setPromptText("Search");
			searchBtn.setLabelFloat(true);


//			editBtn.setDisable(true);
			 HBox btnHB = new HBox();
			 btnHB.setSpacing(20);
			 btnHB.getChildren().addAll(createSalesBtn,editSalesBtn,printSalesBtn);
			anchorPane.getChildren().addAll(salesView,searchBtn,btnHB);
//			HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
			AnchorPane.setTopAnchor(searchBtn, -50d);
			AnchorPane.setBottomAnchor(btnHB,50d);

//			hambHB.getChildren().removeAll(anchorPane,logoutImg);
//			HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//			hambHB.getChildren().addAll(logoutImg,anchorPane);
			
			
			HBox.setMargin(profileMenu, new Insets(10,0,0,20));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
			
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);

			searchBtn.textProperty().addListener((observable,oldValue,newValue)->{
				salesController.saleFilteredData.setPredicate(salesEntry->{
					if(newValue==null||newValue.isEmpty()){
						return true;
					}
					String lowerCaseFilter=newValue.toLowerCase();

					if(salesEntry.getInvoice().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(salesEntry.getCustName().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(String.valueOf(salesEntry.getEntry_date()).contains(lowerCaseFilter)) {
						return true;
					}
					else if(String.valueOf(salesEntry.getTotal()).contains(lowerCaseFilter)) {
						return true;
					}
					return false;
				});
			});

			Label newLbl= new Label("N");
			newLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label createLbl= new Label("Create ");
			createLbl.setStyle("-fx-text-fill:white");
			Label createNewLbl= new Label("ew");
			createNewLbl.setStyle("-fx-text-fill:white");
			HBox createNewHB= new HBox();
			createNewHB.getChildren().addAll(createLbl,newLbl,createNewLbl);
			createSalesBtn.setGraphic(createNewHB);

			Label eLbl= new Label("U");
			eLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label updateLbl= new Label("pdate");
			updateLbl.setStyle("-fx-text-fill:white");
			HBox updateHB= new HBox();
			updateHB.getChildren().addAll(eLbl,updateLbl);
			editSalesBtn.setGraphic(updateHB);

			createSalesBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					editSalesBtn.setDisable(false);
					GridPane gridPane=salesController.createNewInvoice(anchorPane);
					Scene scene=new Scene(gridPane,1350,790);
							scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);
					
					scene.addEventFilter(KeyEvent.KEY_TYPED, e->{
						long now= Instant.now().toEpochMilli();
						if(now-lastEventTimeStamp>THRESHOLD) {
							barcode.delete(0, barcode.length());
						}
						lastEventTimeStamp=now;
						if(e.getCharacter().charAt(0)==(char) 0x000d) {
							if(barcode.length()>=MIN_BARCODE_LENGTH) {
								salesController.fireBarcode(barcode.toString());
							}
							barcode.delete(0, barcode.length());
						}else {
							barcode.append(e.getCharacter());
						}
//						e.consume();
//
					});

					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ESCAPE) {
						Main.dialog.close();
					}
					});
					dialog = new Stage();
					dialog.setScene(scene);

					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(0,-150,0,0));
			        gridPane.add(closeImg, 7, 0);

			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});

			        dialog.show();
				}
			});

//			editSalesBtn.setMnemonicParsing(true);
			editSalesBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					if(salesView.getItems().isEmpty()) {
						editSalesBtn.setDisable(true);
						return;
					}
					editSalesBtn.setDisable(false);
					SalesEntry salesEntry=salesView.getSelectionModel().getSelectedItem();
					GridPane gridPane = salesController.updateSalesEntry(salesEntry,anchorPane);
					Scene scene=new Scene(gridPane,1350,790);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);

					scene.addEventFilter(KeyEvent.KEY_TYPED, e->{
						long now= Instant.now().toEpochMilli();
						if(now-lastEventTimeStamp>THRESHOLD) {
							barcode.delete(0, barcode.length());
						}
						lastEventTimeStamp=now;
						if(e.getCharacter().charAt(0)==(char) 0x000d) {
							if(barcode.length()>=MIN_BARCODE_LENGTH) {
								salesController.fireBarcode(barcode.toString());
							}
							barcode.delete(0, barcode.length());
						}else {
							barcode.append(e.getCharacter());
						}
//						e.consume();
//
					});

//					scene.addEventFilter(KeyEvent.KEY_TYPED, e->{
//						long now= Instant.now().toEpochMilli();
//						if(now-lastEventTimeStamp>THRESHOLD) {
//							barcode.delete(0, barcode.length());
//						}
//						lastEventTimeStamp=now;
//						if(e.getCharacter().charAt(0)==(char) 0x000d) {
//							if(barcode.length()>=MIN_BARCODE_LENGTH) {
//								salesController.fireBarcode(barcode.toString());
//							}
//							barcode.delete(0, barcode.length());
//						}else {
//							barcode.append(e.getCharacter());
//						}
////						e.consume();
////
//					});
					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ESCAPE) {
						Main.dialog.close();
					}
					});

					 dialog = new Stage();
					dialog.setScene(scene);

					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(0,-150,0,0));
			        gridPane.add(closeImg, 7, 0);
			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});

			        dialog.show();
				}
			});
			

			salesView.setRowFactory(tv ->{
					TableRow<SalesEntry> row=new TableRow<>();
					row.setOnMouseClicked(event1->{
						if(event1.getClickCount()==2&&(!row.isEmpty())) {
							SalesEntry salesEntry=row.getItem();
							GridPane gridPane=salesController.viewSalesEntry(salesEntry, anchorPane);
							Scene scene=new Scene(gridPane,1350,700);
							scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
							scene.setFill(Color.TRANSPARENT);
							scene.addEventFilter(KeyEvent.KEY_PRESSED, event->{
								if(event.getCode()==KeyCode.ESCAPE) {
								Main.dialog.close();
							}
							});

							 dialog = new Stage();
								dialog.setScene(scene);

								dialog.initOwner(primaryStage);
								dialog.initModality(Modality.WINDOW_MODAL);
								dialog.initStyle(StageStyle.TRANSPARENT);
								ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
						        closeImg.setFitHeight(30);
						        closeImg.setFitWidth(30);
						        closeImg.setStyle("-fx-cursor:hand");
						        GridPane.setMargin(closeImg, new Insets(0,-150,0,0));
						        gridPane.add(closeImg, 7, 0);
						        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

						    		@Override
						    		public void handle(MouseEvent arg0) {
						    			// TODO Auto-generated method stub
						    			dialog.close();
						    		}

						    	});

						        dialog.show();
						}
				});
					return row;
			});
//			salesView.setRowFactory(tv->{
//				TableRow<SalesEntry> row=new TableRow<>();
//				row.setOnMouseClicked(event->{
//					if(event.getClickCount()==2&&(!row.isEmpty())) {
//						
//					}
//				});
//			});

//			returnSaleBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//				@Override
//				public void handle(ActionEvent event) {
//					// TODO Auto-generated method stub
//					if(salesView.getItems().isEmpty()) {
//						returnSaleBtn.setDisable(true);
//						return;
//					}
//					returnSaleBtn.setDisable(false);
//					SalesEntry salesEntry=salesView.getSelectionModel().getSelectedItem();
//					int index=salesView.getSelectionModel().getSelectedIndex();
//
//					GridPane gridPane= salesController.returnSalesGoods(salesEntry,index,anchorPane);
//					Scene scene=new Scene(gridPane,1350,700);
//					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
//					scene.setFill(Color.TRANSPARENT);
//
//					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
//						if(e.getCode()==KeyCode.ESCAPE) {
//						Main.dialog.close();
//					}
//					});
//
//					dialog = new Stage();
//					dialog.setScene(scene);
//
//					dialog.initOwner(primaryStage);
//					dialog.initModality(Modality.WINDOW_MODAL);
//					dialog.initStyle(StageStyle.TRANSPARENT);
//					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
//			        closeImg.setFitHeight(30);
//			        closeImg.setFitWidth(30);
//			        closeImg.setStyle("-fx-cursor:hand");
////			        GridPane.setMargin(closeImg, new Insets(-40,0,20,-30));
//			        gridPane.add(closeImg, 3, 0);
//			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//			    		@Override
//			    		public void handle(MouseEvent arg0) {
//			    			// TODO Auto-generated method stub
//			    			dialog.close();
//			    		}
//
//			    	});
//
//			        dialog.show();
//				}
//
//			});

			Label pLbl= new Label("P");
			pLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label printLbl= new Label("rint Invoice");
			printLbl.setStyle("-fx-text-fill:white");
			HBox printHB= new HBox();
			printHB.getChildren().addAll(pLbl,printLbl);

			printSalesBtn.setGraphic(printHB);
			printSalesBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					if(salesView.getItems().isEmpty()) {
						printSalesBtn.setDisable(true);
						return;
					}

					printSalesBtn.setDisable(false);
					SalesEntry salesEntry=salesView.getSelectionModel().getSelectedItem();
					salesController.printInvoice(salesEntry);
					
				}
			});
		}
		else if(e.getSource()==salesManRecBtn) {
			salesManView=salesController.showSalesMan(anchorPane);
			salesManView.setMinSize(900, 500);
			anchorPane.getChildren().clear();

			salesManView.requestFocus();
			salesManView.getSelectionModel().selectFirst();
			salesManView.getFocusModel().focus(0);
			searchBtn=new JFXTextField();
			searchBtn.setMinWidth(500);
			searchBtn.setPromptText("Search");
			searchBtn.setLabelFloat(true);
			
			 HBox btnHB = new HBox();
			 btnHB.setSpacing(20);
			 btnHB.getChildren().addAll(createSalesManBtn,editSalesManBtn);
			 
			 Label newLbl= new Label("N");
			 newLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label createLbl= new Label("Create ");
			createLbl.setStyle("-fx-text-fill:white");
			Label createNewLbl= new Label("ew");
			createNewLbl.setStyle("-fx-text-fill:white");
			HBox createNewHB= new HBox();
			createNewHB.getChildren().addAll(createLbl,newLbl,createNewLbl);
			createSalesManBtn.setGraphic(createNewHB);

			Label eLbl= new Label("U");
			eLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label updateLbl= new Label("pdate");
			updateLbl.setStyle("-fx-text-fill:white");
			HBox updateHB= new HBox();
			updateHB.getChildren().addAll(eLbl,updateLbl);
			editSalesManBtn.setGraphic(updateHB);

				
			anchorPane.getChildren().addAll(salesManView,searchBtn,btnHB);
//			HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
			AnchorPane.setTopAnchor(searchBtn, -50d);
			AnchorPane.setBottomAnchor(btnHB,50d);

//			hambHB.getChildren().removeAll(anchorPane,logoutImg);
//			HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//			hambHB.getChildren().addAll(logoutImg,anchorPane);
			
			
			HBox.setMargin(profileMenu, new Insets(10,0,0,20));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
			
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);

			searchBtn.textProperty().addListener((observable,oldValue,newValue)->{
				salesController.salesManFilteredData.setPredicate(salesMan->{
					if(newValue==null||newValue.isEmpty()){
						return true;
					}
					String lowerCaseFilter=newValue.toLowerCase();

					if(salesMan.getSalesManName().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(salesMan.getContact().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(String.valueOf(salesMan.getSrNo()).contains(lowerCaseFilter)) {
						return true;
					}
					else if(salesMan.getAddress().contains(lowerCaseFilter)) {
						return true;
					}
					return false;
				});
			});
			
			createSalesManBtn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					editSalesManBtn.setDisable(false);
					GridPane gridPane=salesController.createNewSalesMan(anchorPane);
					Scene scene=new Scene(gridPane,450,600);
							scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);
					
					
					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ESCAPE) {
						Main.dialog.close();
					}
					});
					dialog = new Stage();
					dialog.setScene(scene);

					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(0,0,0,10));
			        gridPane.add(closeImg, 1, 0);

			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});

			        dialog.show();
				}
			});
			
			editSalesManBtn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					if(salesManView.getItems().isEmpty()) {
						editSalesManBtn.setDisable(true);
						return;
					}
					editSalesManBtn.setDisable(false);
					SalesMan salesMan=salesManView.getSelectionModel().getSelectedItem();
					int index=salesManView.getSelectionModel().getSelectedIndex();
					GridPane gridPane=salesController.updateSalesMan(anchorPane, index, salesMan);
					Scene scene=new Scene(gridPane,450,600);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);
			
			
					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ESCAPE) {
						Main.dialog.close();
					}
					});
					dialog = new Stage();
					dialog.setScene(scene);
				
					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
				    closeImg.setFitHeight(30);
				    closeImg.setFitWidth(30);
				    closeImg.setStyle("-fx-cursor:hand");
				    GridPane.setMargin(closeImg, new Insets(0,0,0,10));
				    gridPane.add(closeImg, 1, 0);
				
				    closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
						@Override
						public void handle(MouseEvent arg0) {
							// TODO Auto-generated method stub
							dialog.close();
						}
				
					});
				
				    dialog.show();
				}
			});

		}

		else if(e.getSource()==salesReport) {
			saleRetunHB = new HBox();
			saleRetunHB.setSpacing(30);

			HBox HB1 = new HBox();
			HB1.setSpacing(20);

			FromDate=new DatePicker();
			FromDate.setShowWeekNumbers(false);
			FromDate.setEditable(true);

			FromDate.setPromptText("From Date");
			FromDate.setStyle("-fx-font-size:12");
			FromDate.setMinWidth(150);
			
//			FromDate.getStyleClass().add("date-pick");
			FromDate.setValue(LocalDate.now());
			KeyEventHandler.dateKeyEvent(FromDate);

			ToDate=new DatePicker();
			ToDate.setShowWeekNumbers(false);
			ToDate.setPromptText("To Date");
//			ToDate.getStyleClass().add("date-pick");
			 ToDate.setValue(LocalDate.now());
			 KeyEventHandler.dateKeyEvent(ToDate);

			FromDate.getEditor().textProperty().addListener(new ChangeListener<String>() {


				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					errorTip.hide();
				}
			});
			ToDate.getEditor().textProperty().addListener(new ChangeListener<String>() {


				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					errorTip.hide();
				}
			});

			pdf=new RadioButton("PDF");
			pdf.setToggleGroup(group);
			pdf.setSelected(true);

			excel= new RadioButton("Excel");
			excel.setToggleGroup(group);
			excel.setSelected(true);

			 Label eLbl= new Label("E");
			 eLbl.setStyle("-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			 Label exportLbl= new Label("xport");
			 exportLbl.setStyle("-fx-text-fill:white");
			 HBox exportHB= new HBox();
			 exportHB.getChildren().addAll(eLbl,exportLbl);
			 GenerateBtn.setGraphic(exportHB);
//			GenerateBtn.setMnemonicParsing(true);
			GenerateBtn.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent event) {
					boolean result;
					errorTip.hide();
					if (!validateonGenerateButton()) {
			             return;
		            }

					SalesReportBean sb=new SalesReportBean();

				    date = FromDate.getValue();
					java.sql.Date sqlDate=null;
					try {
						SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
						long dateLong= sd.parse(date.toString()).getTime();
						sqlDate=new java.sql.Date(dateLong);
						fromDate1=sqlDate.toString();
//						System.out.println("FromDate1             "+FromDate1);

					} catch (ParseException e1) {
						e1.printStackTrace();
					}

				    date1 = ToDate.getValue();
					java.sql.Date sqlDate1=null;
					try {
						SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
						long dateLong1= sd1.parse(date1.toString()).getTime();
						sqlDate1=new java.sql.Date(dateLong1);
						toDate1=sqlDate1.toString();
//						System.out.println("ToDate1             "+ToDate1);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
		            sb.setFromDate(sqlDate.toString());
		            sb.setToDate(sqlDate1.toString());


					if(pdf.isSelected()){

							try {
								salesReportGen.SalesReportPdf(sb);
							} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

					}else if(excel.isSelected()){
						try {
							salesReportGen.SalesReportExcel(sb);
						} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			});


			ViewBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					if(!validateonViewButton()) {
						return;
					}
					SalesReportBean sb=new SalesReportBean();

				    date = FromDate.getValue();
				    
					java.sql.Date sqlDate=null;
					try {
						SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
						long dateLong= sd.parse(date.toString()).getTime();
						sqlDate=new java.sql.Date(dateLong);
						fromDate1=sqlDate.toString();

					} catch (ParseException e1) {
						e1.printStackTrace();
					}

				    date1 = ToDate.getValue();
					java.sql.Date sqlDate1=null;
					try {
						SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
						long dateLong1= sd1.parse(date1.toString()).getTime();
						sqlDate1=new java.sql.Date(dateLong1);
						toDate1=sqlDate1.toString();

					} catch (ParseException e1) {
						e1.printStackTrace();
					}
		            sb.setFromDate(sqlDate.toString());
		            sb.setToDate(sqlDate1.toString());
					SalesReportDate = salesController.SalesReportTable(fromDate1,toDate1);
					SalesReportDate.setMinSize(900, 500);
				     HBox.setMargin(profileMenu, new Insets(10,0,0,20));
					 anchorPane.getChildren().removeAll(SalesReportDate,HB1);
					anchorPane.getChildren().addAll(SalesReportDate,HB1);
					AnchorPane.setBottomAnchor(HB1,50d);
				}
			});
			saleRetunHB.getChildren().addAll(FromDate,ToDate,ViewBtn);
			HB1.getChildren().addAll(pdf,excel,GenerateBtn);

			
			anchorPane.getChildren().clear();
			anchorPane.getChildren().addAll(saleRetunHB);
			AnchorPane.setTopAnchor(saleRetunHB,-50d);
//			AnchorPane.setBottomAnchor(HB1,50d);
//			HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
//			hambHB.getChildren().removeAll(anchorPane,logoutImg);
//			HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//			hambHB.getChildren().addAll(logoutImg,anchorPane);

			HBox.setMargin(profileMenu, new Insets(10,0,0,480));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
			
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);

		}

		else if(e.getSource()==CashReport) {
            HBox HB = new HBox();
            HB.setSpacing(20);
            HBox HB1 = new HBox();
            HB1.setSpacing(20);

            FromDate=new DatePicker();
			FromDate.setEditable(true);
			FromDate.setShowWeekNumbers(false);
			FromDate.setPromptText("From Date");
			//FromDate.getStyleClass().add("date-pick");
			FromDate.setValue(LocalDate.now());
			KeyEventHandler.dateKeyEvent(FromDate);

			ToDate=new DatePicker();
			ToDate.setShowWeekNumbers(false);
			ToDate.setPromptText("To Date");
			//ToDate.getStyleClass().add("date-pick");
			ToDate.setValue(LocalDate.now());
			KeyEventHandler.dateKeyEvent(ToDate);

			ObservableList<String> options =
				    FXCollections.observableArrayList(
				        "Invoice Wise",
				        "Day Wise"
				    );

			Cash_Report_Filter=new JFXComboBox(options);
			Cash_Report_Filter.setPromptText("Select Filter");
			Cash_Report_Filter.setLabelFloat(true);
			Cash_Report_Filter.setValue("Invoice Wise");
			Cash_Report_Filter.setMaxWidth(150);

			FromDate.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					errorTip.hide();
				}
			});
			ToDate.getEditor().textProperty().addListener(new ChangeListener<String>() {


				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					errorTip.hide();
				}
			});
			pdf= new RadioButton("PDF");
            pdf.setSelected(true);
            pdf.setToggleGroup(group);
            excel= new RadioButton("EXCEL");
            excel.setToggleGroup(group);
            excel.setSelected(true);
            Label eLbl= new Label("E");
			 eLbl.setStyle("-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			 Label exportLbl= new Label("xport");
			 exportLbl.setStyle("-fx-text-fill:white");
			 HBox exportHB= new HBox();
			 exportHB.getChildren().addAll(eLbl,exportLbl);
			 GenerateBtn.setGraphic(exportHB);
            GenerateBtn.setOnAction(new EventHandler<ActionEvent>() {
            	long dateLong1;
                long dateLong;
                public void handle(ActionEvent event) {
                    boolean result;
                    if (!validateonViewButton()) {
                         return;
                    }else {
                        errorTip.hide();
                    }
                    CashReportBean sb=new CashReportBean();
                    date = FromDate.getValue();
                    java.sql.Date sqlDate=null;
                    try {
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                        dateLong= sd.parse(date.toString()).getTime();
                        sqlDate=new java.sql.Date(dateLong);
                        fromDate1=sqlDate.toString();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    date1 = ToDate.getValue();
                    java.sql.Date sqlDate1=null;
                    try {
                        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
                        dateLong1= sd1.parse(date1.toString()).getTime();
                        sqlDate1=new java.sql.Date(dateLong1);
                        toDate1=sqlDate1.toString();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    sb.setFromDate(sqlDate.toString());
                    sb.setToDate(sqlDate1.toString());
                    try {
                    if(pdf.isSelected()){
                    	if(Cash_Report_Filter.getValue().toString()=="Invoice Wise"){
                    		cashReportGen.CashReportPdf(sb);
                    	}else {
                    		cashReportDay_wise_gen.CashReport_Day_Wise_Pdf(sb);
                    	}
                    }else if(excel.isSelected()){
                    	if(Cash_Report_Filter.getValue().toString()=="Invoice Wise"){
                             cashReportGen.CashReportExcel(sb);
                    	}else{
                    		cashReportDay_wise_gen.CashReport_Day_Wise_Excel(sb);
                    	}
                    }
                    } catch (ClassNotFoundException | JRException | SQLException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

});
            ViewBtn.setOnAction(new EventHandler<ActionEvent>() {
            	long dateLong;
                long dateLong1;
                @Override
                public void handle(ActionEvent arg0) {
                	 if (!validateonViewButton()) {
                         return;
                    }else {
                        errorTip.hide();
                    }
               CashReportBean sb=new CashReportBean();
               date = FromDate.getValue();
               java.sql.Date sqlDate=null;
               try {
               SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
               dateLong= sd.parse(date.toString()).getTime();
               sqlDate=new java.sql.Date(dateLong);
               fromDate1=sqlDate.toString();
                } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    date1 = ToDate.getValue();
                    java.sql.Date sqlDate1=null;
                    try {
                        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
                        dateLong1= sd1.parse(date1.toString()).getTime();
                        sqlDate1=new java.sql.Date(dateLong1);
                        toDate1=sqlDate1.toString();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    sb.setFromDate(sqlDate.toString());
                    sb.setToDate(sqlDate1.toString());

                    CashReportDate = SalesController.CashReportTable(fromDate1,toDate1,Cash_Report_Filter.getValue().toString());
                    HBox.setMargin(profileMenu, new Insets(10,10,0,0));
                    anchorPane.getChildren().removeAll(CashReportDate,HB1);
                    anchorPane.getChildren().addAll(CashReportDate,HB1);
                    CashReportDate.setMinSize(600, 500);
                    AnchorPane.setBottomAnchor(HB1,50d);


                }

            });
            HB.getChildren().addAll(FromDate,ToDate,Cash_Report_Filter,ViewBtn);
            HB1.getChildren().addAll(pdf,excel,GenerateBtn);

            anchorPane.getChildren().clear();
            anchorPane.getChildren().addAll(HB);
            AnchorPane.setTopAnchor(HB,-50d);

//            HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
//            hambHB.getChildren().removeAll(anchorPane,logoutImg);
//            HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//            hambHB.getChildren().addAll(logoutImg,anchorPane);
            HBox.setMargin(profileMenu, new Insets(10,0,0,360));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
			
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);
        }


		else if(e.getSource()==PurchaseReport) {
			purchReturnHB = new HBox();
			purchReturnHB.setSpacing(20);

			   HBox HB1 = new HBox();
			   HB1.setSpacing(20);

			   FromDate=new DatePicker();
			   FromDate.setShowWeekNumbers(false);
			   FromDate.setPromptText("From Date");
//			   FromDate.getStyleClass().add("date-pick");
			   FromDate.setValue(LocalDate.now());
			   KeyEventHandler.dateKeyEvent(FromDate);

			   ToDate=new DatePicker();
			   ToDate.setShowWeekNumbers(false);
			   ToDate.setPromptText("From Date");
//			   ToDate.getStyleClass().add("date-pick");
			   ToDate.setValue(LocalDate.now());
			   KeyEventHandler.dateKeyEvent(ToDate);

			   pdf= new RadioButton("PDF");

			   pdf.setToggleGroup(group);
			   pdf.setSelected(true);
			    excel=new RadioButton("EXCEL");
			   excel.setToggleGroup(group);
			   excel.setSelected(true);


			   FromDate.getEditor().textProperty().addListener(new ChangeListener<String>() {


					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						// TODO Auto-generated method stub
						errorTip.hide();
					}
				});
			   ToDate.getEditor().textProperty().addListener(new ChangeListener<String>() {


					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						// TODO Auto-generated method stub
						errorTip.hide();
					}
				});

//			   GenerateBtn.setMnemonicParsing(true);
			   Label eLbl= new Label("E");
				 eLbl.setStyle("-fx-text-fill:#ff5c33;-fx-font-weight:bold");
				 Label exportLbl= new Label("xport");
				 exportLbl.setStyle("-fx-text-fill:white");
				 HBox exportHB= new HBox();
				 exportHB.getChildren().addAll(eLbl,exportLbl);
				 GenerateBtn.setGraphic(exportHB);
			   GenerateBtn.setOnAction(new EventHandler<ActionEvent>() {

			    public void handle(ActionEvent event) {
			     boolean result;
			     if (!validateonGenerateButton()) {
			                return;
			              }else {
			               errorTip.hide();
			              }

			     PurchaseEntry pe=new PurchaseEntry();

			        date = FromDate.getValue();
			     java.sql.Date sqlDate=null;
			     try {
			      SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			      long dateLong= sd.parse(date.toString()).getTime();
			      sqlDate=new java.sql.Date(dateLong);
			      fromDate1=sqlDate.toString();
//			      System.out.println("FromDate1             "+FromDate1);

			     } catch (ParseException e1) {
			      e1.printStackTrace();
			     }

			        date1 = ToDate.getValue();
			     java.sql.Date sqlDate1=null;
			     try {
			      SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
			      long dateLong1= sd1.parse(date1.toString()).getTime();
			      sqlDate1=new java.sql.Date(dateLong1);
			      toDate1=sqlDate1.toString();
//			      System.out.println("ToDate1             "+ToDate1);
			     } catch (ParseException e1) {
			      e1.printStackTrace();
			     }
			              pe.setFromDate(sqlDate.toString());
			              pe.setToDate(sqlDate1.toString());

			                 try {
			     if(pdf.isSelected()){

			      purchaseReportGen.PurchaseReportPdf(pe);
			     }else if(excel.isSelected()){
			      purchaseReportGen.PurchaseReportExcel(pe);
			     }
			     } catch (ClassNotFoundException | JRException | SQLException | IOException e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			     }
			    }


			});
			   ViewBtn.setOnAction(new EventHandler<ActionEvent>() {

			    @Override
			    public void handle(ActionEvent arg0) {
			    	if(!validateonViewButton()) {
			    		return;
			    	}
			     PurchaseEntry pe=new PurchaseEntry();

			        date = FromDate.getValue();
			     java.sql.Date sqlDate=null;
			     try {
			      SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			      long dateLong= sd.parse(date.toString()).getTime();
			      sqlDate=new java.sql.Date(dateLong);
			      fromDate1=sqlDate.toString();
//			      System.out.println("FromDate1             "+FromDate1);

			     } catch (ParseException e1) {
			      e1.printStackTrace();
			     }

			        date1 = ToDate.getValue();
			     java.sql.Date sqlDate1=null;
			     try {
			      SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
			      long dateLong1= sd1.parse(date1.toString()).getTime();
			      sqlDate1=new java.sql.Date(dateLong1);
			      toDate1=sqlDate1.toString();
//			      System.out.println("ToDate1             "+ToDate1);
			     } catch (ParseException e1) {
			      e1.printStackTrace();
			     }
			              pe.setFromDate(sqlDate.toString());
			              pe.setToDate(sqlDate1.toString());
			     PurchaseReportDate = purchaseController.PurchaseReportTable(fromDate1,toDate1);
			     PurchaseReportDate.setMinSize(900, 500);
			     HBox.setMargin(profileMenu, new Insets(10,0,0,20));
			     anchorPane.getChildren().removeAll(PurchaseReportDate,HB1);
			     anchorPane.getChildren().addAll(PurchaseReportDate,HB1);
			     AnchorPane.setBottomAnchor(HB1,50d);
			    }
			   });
			   purchReturnHB.getChildren().addAll(FromDate,ToDate,ViewBtn);
			   HB1.getChildren().addAll(pdf,excel,GenerateBtn);

			   anchorPane.getChildren().clear();
			   anchorPane.getChildren().addAll(purchReturnHB);
			   AnchorPane.setTopAnchor(purchReturnHB,-50d);

//			   HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
//			   hambHB.getChildren().removeAll(anchorPane,logoutImg);
//			   HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//			   hambHB.getChildren().addAll(logoutImg,anchorPane);
			   HBox.setMargin(profileMenu, new Insets(10,0,0,480));
				HBox.setMargin(anchorPane, new Insets(100,0,0,80));
				
				hambHB.getChildren().removeAll(anchorPane,profileMenu);
				hambHB.getChildren().addAll(anchorPane,profileMenu);
			   
			   
			  }
		else if(e.getSource()==StockReport) {
            stockReportHB = new HBox();
            stockReportHB.setSpacing(10);
            HBox HB1 = new HBox();
            HB1.setSpacing(20);

            FromDate=new DatePicker();
            FromDate.setShowWeekNumbers(false);
          	FromDate.setPromptText("From Date");
			//FromDate.getStyleClass().add("date-pick");
         	FromDate.setValue(LocalDate.now());
			KeyEventHandler.dateKeyEvent(FromDate);

			System.out.println("Images1 ");
			ToDate=new DatePicker();
			ToDate.setShowWeekNumbers(false);
			ToDate.setPromptText("To Date");
			//ToDate.getStyleClass().add("date-pick");
			ToDate.setValue(LocalDate.now());
			KeyEventHandler.dateKeyEvent(ToDate);
			System.out.println("Images2 ");
			FromDate.getEditor().textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					errorTip.hide();
				}
			});
			ToDate.getEditor().textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					errorTip.hide();
				}
			});
			System.out.println("Images3 ");
			pdf= new RadioButton("PDF");
            pdf.setSelected(true);
            pdf.setToggleGroup(group);
            excel= new RadioButton("EXCEL");
            excel.setToggleGroup(group);
            excel.setSelected(true);
            Label eLbl= new Label("E");
			 eLbl.setStyle("-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			 Label exportLbl= new Label("xport");
			 exportLbl.setStyle("-fx-text-fill:white");
			 HBox exportHB= new HBox();
			 exportHB.getChildren().addAll(eLbl,exportLbl);
			 GenerateBtn.setGraphic(exportHB);
            ViewBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                	StockReportBean sb=new StockReportBean();
                    date = FromDate.getValue();
                    java.sql.Date sqlDate=null;
                    try {
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                        long dateLong= sd.parse(date.toString()).getTime();
                        sqlDate=new java.sql.Date(dateLong);
                        fromDate1=sqlDate.toString();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    date1 = ToDate.getValue();
                    java.sql.Date sqlDate1=null;
                    try {
                        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
                        long dateLong1= sd1.parse(date1.toString()).getTime();
                        sqlDate1=new java.sql.Date(dateLong1);
                        toDate1=sqlDate1.toString();
                        } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    sb.setFromDate(sqlDate.toString());
                    sb.setToDate(sqlDate1.toString());
                    StockReportDate = GoodsController.StockReportTable(fromDate1,toDate1);
                    StockReportDate.setMinSize(900, 500);
                    HB1.getChildren().removeAll(pdf,excel,GenerateBtn);
                    HB1.getChildren().addAll(pdf,excel,GenerateBtn);
                    HBox.setMargin(profileMenu, new Insets(10,0,0,20));
                    anchorPane.getChildren().removeAll(StockReportDate,HB1);
                    anchorPane.getChildren().addAll(StockReportDate,HB1);
                    AnchorPane.setBottomAnchor(HB1,50d);
                }
            });
            GenerateBtn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    if (!validateonViewButton()) {
                    	System.out.println("Images5 ");
                        return;
                   }else {
                       errorTip.hide();
                   }
                    StockReportBean sb=new StockReportBean();
                    System.out.println("Images6 ");
                    date = FromDate.getValue();
                    java.sql.Date sqlDate=null;
                    try {
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                        long dateLong= sd.parse(date.toString()).getTime();
                        sqlDate=new java.sql.Date(dateLong);
                        fromDate1=sqlDate.toString();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    date1 = ToDate.getValue();
                    System.out.println("Images7 ");
                    java.sql.Date sqlDate1=null;
                    try {
                        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
                        long dateLong1= sd1.parse(date1.toString()).getTime();
                        sqlDate1=new java.sql.Date(dateLong1);
                        toDate1=sqlDate1.toString();
                        } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    sb.setFromDate(sqlDate.toString());
                    sb.setToDate(sqlDate1.toString());
                    try {
          					if(pdf.isSelected()){
							    stockReportGen.StockReportPdf(sb);
                 			    //anchorPane.getChildren().remove(backgroundImage);
                                }else if(excel.isSelected()){
        						stockReportGen.StockReportExcel(sb);
                              }
                    } catch (ClassNotFoundException | JRException | SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
});
            stockReportHB.getChildren().addAll(FromDate,ToDate,ViewBtn);

            anchorPane.getChildren().clear();
            anchorPane.getChildren().addAll(stockReportHB);

            AnchorPane.setTopAnchor(stockReportHB,-50d);

//            HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
//            hambHB.getChildren().removeAll(anchorPane,logoutImg);
//            HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//            hambHB.getChildren().addAll(logoutImg,anchorPane);
            HBox.setMargin(profileMenu, new Insets(10,0,0,490));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
			
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);
        }
		else if(e.getSource()==salesManReport) {
				salesManReportHB = new HBox();
				salesManReportHB.setSpacing(10);
	            HBox HB1 = new HBox();
	            HB1.setSpacing(20);

	            FromDate=new DatePicker();
	            FromDate.setShowWeekNumbers(false);
	          	FromDate.setPromptText("From Date");
				//FromDate.getStyleClass().add("date-pick");
	         	FromDate.setValue(LocalDate.now());
				KeyEventHandler.dateKeyEvent(FromDate);

//				System.out.println("Images1 ");
				ToDate=new DatePicker();
				ToDate.setShowWeekNumbers(false);
				ToDate.setPromptText("To Date");
				//ToDate.getStyleClass().add("date-pick");
				ToDate.setValue(LocalDate.now());
				KeyEventHandler.dateKeyEvent(ToDate);
				System.out.println("Images2 ");
				FromDate.getEditor().textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						// TODO Auto-generated method stub
						errorTip.hide();
					}
				});
				ToDate.getEditor().textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						// TODO Auto-generated method stub
						errorTip.hide();
					}
				});
//				System.out.println("Images3 ");
				pdf= new RadioButton("PDF");
	            pdf.setSelected(true);
	            pdf.setToggleGroup(group);
	            excel= new RadioButton("EXCEL");
	            excel.setToggleGroup(group);
	            excel.setSelected(true);
	            Label eLbl= new Label("E");
				 eLbl.setStyle("-fx-text-fill:#ff5c33;-fx-font-weight:bold");
				 Label exportLbl= new Label("xport");
				 exportLbl.setStyle("-fx-text-fill:white");
				 HBox exportHB= new HBox();
				 exportHB.getChildren().addAll(eLbl,exportLbl);
				 GenerateBtn.setGraphic(exportHB);
	            ViewBtn.setOnAction(new EventHandler<ActionEvent>() {
	                @Override
	                public void handle(ActionEvent arg0) {
	                	SalesManReportBean sb=new SalesManReportBean();
	                    date = FromDate.getValue();
	                    java.sql.Date sqlDate=null;
	                    try {
	                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	                        long dateLong= sd.parse(date.toString()).getTime();
	                        sqlDate=new java.sql.Date(dateLong);
	                        fromDate1=sqlDate.toString();
	                    } catch (ParseException e1) {
	                        e1.printStackTrace();
	                    }
	                    date1 = ToDate.getValue();
	                    java.sql.Date sqlDate1=null;
	                    try {
	                        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
	                        long dateLong1= sd1.parse(date1.toString()).getTime();
	                        sqlDate1=new java.sql.Date(dateLong1);
	                        toDate1=sqlDate1.toString();
	                        } catch (ParseException e1) {
	                        e1.printStackTrace();
	                    }
	                    sb.setFrom_date(sqlDate.toString());
	                    sb.setTo_date(sqlDate1.toString());
	                    salesManReportDate = salesController.SalesManReportTable(fromDate1,toDate1);
	                    salesManReportDate.setPrefSize(900, 500);
	                    HB1.getChildren().removeAll(pdf,excel,GenerateBtn);
	                    HB1.getChildren().addAll(pdf,excel,GenerateBtn);
	                    HBox.setMargin(profileMenu, new Insets(10,0,0,20));
	                    anchorPane.getChildren().removeAll(salesManReportDate,HB1);
	                    anchorPane.getChildren().addAll(salesManReportDate,HB1);
	                    AnchorPane.setBottomAnchor(HB1,50d);
	                }
	            });
	            GenerateBtn.setOnAction(new EventHandler<ActionEvent>() {
	                public void handle(ActionEvent event) {
	                    if (!validateonViewButton()) {
//	                    	System.out.println("Images5 ");
	                        return;
	                   }else {
	                       errorTip.hide();
	                   }
	                    SalesManReportBean sb=new SalesManReportBean();
//	                    System.out.println("Images6 ");
	                    date = FromDate.getValue();
	                    java.sql.Date sqlDate=null;
	                    try {
	                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	                        long dateLong= sd.parse(date.toString()).getTime();
	                        sqlDate=new java.sql.Date(dateLong);
	                        fromDate1=sqlDate.toString();
	                    } catch (ParseException e1) {
	                        e1.printStackTrace();
	                    }
	                    date1 = ToDate.getValue();
	                    System.out.println("Images7 ");
	                    java.sql.Date sqlDate1=null;
	                    try {
	                        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
	                        long dateLong1= sd1.parse(date1.toString()).getTime();
	                        sqlDate1=new java.sql.Date(dateLong1);
	                        toDate1=sqlDate1.toString();
	                        } catch (ParseException e1) {
	                        e1.printStackTrace();
	                    }
	                    sb.setFrom_date(sqlDate.toString());
	                    sb.setTo_date(sqlDate1.toString());
//	                    System.out.println(sqlDate1.toString());
	                    try {
	          					if(pdf.isSelected()){
								    salesManRepGen.SalesManReportPdf(sb);
	                 			    //anchorPane.getChildren().remove(backgroundImage);
	                                }else if(excel.isSelected()){
	                                	salesManRepGen.SalesManReportExcel(sb);
	                              }
	                    } catch (ClassNotFoundException | JRException | SQLException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	});
	            salesManReportHB.getChildren().addAll(FromDate,ToDate,ViewBtn);

	            anchorPane.getChildren().clear();
	            anchorPane.getChildren().addAll(salesManReportHB);

	            AnchorPane.setTopAnchor(salesManReportHB,-50d);

//	            HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
//	            hambHB.getChildren().removeAll(anchorPane,logoutImg);
//	            HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//	            hambHB.getChildren().addAll(logoutImg,anchorPane);
	            HBox.setMargin(profileMenu, new Insets(10,0,0,490));
				HBox.setMargin(anchorPane, new Insets(100,0,0,80));
				
				hambHB.getChildren().removeAll(anchorPane,profileMenu);
				hambHB.getChildren().addAll(anchorPane,profileMenu);
		}

		else if(e.getSource()==saleReturnBtn||e.getSource()==shortCustomerBtn) {
			saleReturnView=returnController.showSalesReturn(anchorPane);
			saleReturnView.setMinSize(900, 500);
			anchorPane.getChildren().clear();

			saleReturnView.requestFocus();
			saleReturnView.getSelectionModel().selectFirst();
			saleReturnView.getFocusModel().focus(0);
			searchBtn=new JFXTextField();
			searchBtn.setMinWidth(500);
			searchBtn.setPromptText("Search");
			searchBtn.setLabelFloat(true);


//			editBtn.setDisable(true);
			 HBox btnHB = new HBox();
			 btnHB.setSpacing(20);
			 btnHB.getChildren().addAll(returnSaleBtn);
			anchorPane.getChildren().addAll(saleReturnView,searchBtn,btnHB);
//			HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
			AnchorPane.setTopAnchor(searchBtn, -50d);
			AnchorPane.setBottomAnchor(btnHB,50d);

//			hambHB.getChildren().removeAll(anchorPane,logoutImg);
//			HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//			hambHB.getChildren().addAll(logoutImg,anchorPane);
			
			HBox.setMargin(profileMenu, new Insets(10,0,0,20));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
			
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);
			

			searchBtn.textProperty().addListener((observable,oldValue,newValue)->{
				returnController.returnFilteredList.setPredicate(salesReturn->{
					if(newValue==null||newValue.isEmpty()){
						return true;
					}
					String lowerCaseFilter=newValue.toLowerCase();

					if(String.valueOf(salesReturn.getSalesEntry().getId()).contains(lowerCaseFilter)) {
						return true;
					}
					else if(salesReturn.getProduct().getProduct_name().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					else if(String.valueOf(salesReturn.getReturnQuantity()).contains(lowerCaseFilter)) {
						return true;
					}

					return false;
				});
			});

			Label rLbl= new Label("R");
			rLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label retunGoodLbl= new Label("eturn Goods");
			retunGoodLbl.setStyle("-fx-text-fill:white");
			HBox returnGoodHB= new HBox();
			returnGoodHB.getChildren().addAll(rLbl,retunGoodLbl);
			returnSaleBtn.setGraphic(returnGoodHB);

			returnSaleBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					GridPane gridPane= returnController.returnSalesGoods(anchorPane);
					Scene scene=new Scene(gridPane,1200,500);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);

					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ESCAPE) {
						Main.dialog.close();
					}
					});

					dialog = new Stage();
					dialog.setScene(scene);

					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(0,0,0,-30));
			        gridPane.add(closeImg, 3, 0);
			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});

			        dialog.show();

				}
			});

		}
		else if(e.getSource()==purReturnBtn||e.getSource()==shortStockBtn) {
			purReturnView=returnController.showPurchaseReturn(anchorPane);
			purReturnView.setMinSize(900, 500);
			anchorPane.getChildren().clear();

			purReturnView.requestFocus();
			purReturnView.getSelectionModel().selectFirst();
			purReturnView.getFocusModel().focus(0);
			searchBtn=new JFXTextField();
			searchBtn.setMinWidth(500);
			searchBtn.setPromptText("Search");
			searchBtn.setLabelFloat(true);


//			editBtn.setDisable(true);
			 HBox btnHB = new HBox();
			 btnHB.setSpacing(20);
			 btnHB.getChildren().addAll(returnPurcBtn);
			anchorPane.getChildren().addAll(purReturnView,searchBtn,btnHB);
//			HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
			AnchorPane.setTopAnchor(searchBtn, -50d);
			AnchorPane.setBottomAnchor(btnHB,50d);

//			hambHB.getChildren().removeAll(anchorPane,logoutImg);
//			HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//			hambHB.getChildren().addAll(logoutImg,anchorPane);
			
			HBox.setMargin(profileMenu, new Insets(10,0,0,20));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
			
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);

			searchBtn.textProperty().addListener((observable,oldValue,newValue)->{
				returnController.purRetFilteredList.setPredicate(purchaseReturn->{
				if(newValue==null||newValue.isEmpty()){
					return true;
				}
				String lowerCaseFilter=newValue.toLowerCase();

				if(String.valueOf(purchaseReturn.getSrNo()).contains(lowerCaseFilter)) {
					return true;
				}
				else if(purchaseReturn.getReturnDate().toString().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				else if(purchaseReturn.getSupplierName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}

				else if(purchaseReturn.getProduct().getProduct_name().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				else if(String.valueOf(purchaseReturn.getReturnQuantity()).contains(lowerCaseFilter)) {
					return true;
				}

				return false;
			});
			});

			Label rLbl= new Label("R");
			rLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label retunGoodLbl= new Label("eturn Goods");
			retunGoodLbl.setStyle("-fx-text-fill:white");
			HBox returnGoodHB= new HBox();
			returnGoodHB.getChildren().addAll(rLbl,retunGoodLbl);
			returnPurcBtn.setGraphic(returnGoodHB);

			returnPurcBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					GridPane gridPane= returnController.returnPurchaseGoods(anchorPane);
					Scene scene=new Scene(gridPane,1150,500);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);

					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ESCAPE) {
						Main.dialog.close();
					}
					});

					dialog = new Stage();
					dialog.setScene(scene);

					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(0,0,0,-30));
			        gridPane.add(closeImg, 3, 0);
			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}

			    	});

			        dialog.show();

				}
			});

		}
		
		else if(e.getSource()==addStockBtn) {
			stockView= inventoryController.showStockQuantityList(anchorPane);
			stockView.setMinSize(900, 500);
			anchorPane.getChildren().clear();
			
			stockView.requestFocus();
			stockView.getSelectionModel().selectFirst();
			stockView.getFocusModel().focus(0);
			
			
//			JFXButton editBtn = new JFXButton(" Update ");
			 HBox btnHB = new HBox();
			 btnHB.setSpacing(20);
			 btnHB.getChildren().add(createStockBtn);
			anchorPane.getChildren().addAll(stockView,btnHB);
//			HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
			AnchorPane.setBottomAnchor(btnHB,50d);
			
//			hambHB.getChildren().removeAll(anchorPane,logoutImg);
//			HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//			hambHB.getChildren().addAll(logoutImg,anchorPane);
			
			HBox.setMargin(profileMenu, new Insets(10,0,0,20));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
			
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);
			
			Label newLbl= new Label("N");
			newLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label createLbl= new Label("Create ");
			createLbl.setStyle("-fx-text-fill:white");
			Label createNewLbl= new Label("ew");
			createNewLbl.setStyle("-fx-text-fill:white");
			HBox createNewHB= new HBox();
			createNewHB.getChildren().addAll(createLbl,newLbl,createNewLbl);
			createStockBtn.setGraphic(createNewHB);
			
			createStockBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					GridPane gridPane =inventoryController.initNewStock(anchorPane);
					Scene scene=new Scene(gridPane,1000,750);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);
					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ESCAPE) {
						Main.dialog.close();
					}else if(e.getCode()==KeyCode.ENTER) {
						
					}
					});
					 dialog = new Stage();
					dialog.setScene(scene);
					
					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);	
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(0,0,20,0));
			        gridPane.add(closeImg, 4, 0);
			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}
			    		
				
			});
			        dialog.show();
				}
			});
			
			
		
		}else if(e.getSource()==transferStock) {
			transferView=inventoryController.showTransferView(anchorPane);
			transferView.setMinSize(900, 500);
			anchorPane.getChildren().clear();
			
			transferView.requestFocus();
			transferView.getSelectionModel().selectFirst();
			transferView.getFocusModel().focus(0);
			
			
//			JFXButton editBtn = new JFXButton(" Update ");
			 HBox btnHB = new HBox();
			 btnHB.setSpacing(20);
			 btnHB.getChildren().add(transferStockBtn);
			anchorPane.getChildren().addAll(transferView,btnHB);
//			HBox.setMargin(anchorPane, new Insets(100, 0, 0, -1000));
			AnchorPane.setBottomAnchor(btnHB,50d);
			
			
//			hambHB.getChildren().removeAll(anchorPane,logoutImg);
//			HBox.setMargin(logoutImg, new Insets(0,0,0,400));
//			hambHB.getChildren().addAll(logoutImg,anchorPane);
			
			HBox.setMargin(profileMenu, new Insets(10,0,0,20));
			HBox.setMargin(anchorPane, new Insets(100,0,0,80));
			
			hambHB.getChildren().removeAll(anchorPane,profileMenu);
			hambHB.getChildren().addAll(anchorPane,profileMenu);
			
			Label newLbl= new Label("N");
			newLbl.setStyle("-fx-font-size:12;-fx-text-fill:#ff5c33;-fx-font-weight:bold");
			Label createLbl= new Label("Create ");
			createLbl.setStyle("-fx-text-fill:white");
			Label createNewLbl= new Label("ew");
			createNewLbl.setStyle("-fx-text-fill:white");
			HBox createNewHB= new HBox();
			createNewHB.getChildren().addAll(createLbl,newLbl,createNewLbl);
			transferStockBtn.setGraphic(createNewHB);
			
			transferStockBtn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					GridPane gridPane= inventoryController.createStockTransfer(anchorPane);
					Scene scene=new Scene(gridPane,1000,750);
					scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);
					scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
						if(e.getCode()==KeyCode.ESCAPE) {
						Main.dialog.close();
					}
					});
					 dialog = new Stage();
					dialog.setScene(scene);
					
					dialog.initOwner(primaryStage);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initStyle(StageStyle.TRANSPARENT);	
					ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
			        closeImg.setFitHeight(30);
			        closeImg.setFitWidth(30);
			        closeImg.setStyle("-fx-cursor:hand");
			        GridPane.setMargin(closeImg, new Insets(0,0,20,0));
			        gridPane.add(closeImg, 4, 0);
			        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    		@Override
			    		public void handle(MouseEvent arg0) {
			    			// TODO Auto-generated method stub
			    			dialog.close();
			    		}
			    		
				
			});
			        dialog.show();
				}
			});
			
			
			
		}
		else if(e.getSource()==editDetMenuItem) {
			GridPane gridPane= new GridPane();
			gridPane.getStyleClass().add("grid");

			gridPane.setAlignment(Pos.CENTER);
//			gp.setPadding(new Insets(0, 10, 0, 10));
			gridPane.setHgap(20);
			gridPane.setVgap(30);
			gridPane.setGridLinesVisible(false);
				
			Label companyLbl= new Label("Company Details");
			
			companyLbl.setStyle("-fx-font-size:24;-fx-text-fill:Red;-fx-font-weight:bold");
			GridPane.setMargin(companyLbl, new Insets(0,-100,0,150));
			
			
			Company company=connectionDAO.getCompanyDetails();
			
			if(company!=null) {
				companyName.setText(company.getCompanyName());
				companyDetails=company.getCompanyName();
				address.setText(company.getAddress());
				officeAddress.setText(company.getOfficeAddr());
				emailId.setText(company.getEmailId());
				contactNo.setText(company.getContactNo());
				altContactNo.setText(company.getAltContact());
				if(company.getState()!=null) {
				state.setValue(company.getState());
				}
				if(company.getLogoPath()!=null) {
				String path=company.getLogoPath().replace(' ', '\\');
				logoPath.setText(path);
//				logoImg.setImage(new Image(path));
				
				}
				gstin.setText(company.getGstin());
				
			}
			companyName.setLabelFloat(true);
			companyName.setPromptText("Company Name");
			companyName.setStyle("-fx-font-size:19");
			companyName.setMinWidth(250);
			validateOnFocus(companyName);
			
			address.setLabelFloat(true);
			address.setPromptText("Address");
			address.setStyle("-fx-font-size:19");
			address.setMinWidth(250);
			validateOnFocus(address);
			
			officeAddress.setLabelFloat(true);
			officeAddress.setStyle("-fx-font-size:19");
			officeAddress.setPromptText("Office Address");
			officeAddress.setMinWidth(250);
			validateOnFocus(officeAddress);
			
			emailId.setLabelFloat(true);
			emailId.setPromptText("Email ID");
			emailId.setMinWidth(250);
			emailId.setStyle("-fx-font-size:19");
			validateOnFocus(emailId);
			
			contactNo.setLabelFloat(true);
			contactNo.setPromptText("Contact No");
			contactNo.setMinWidth(250);
			contactNo.setStyle("-fx-font-size:19");
			validateOnFocus(contactNo);
			
			altContactNo.setLabelFloat(true);
			altContactNo.setPromptText("Alternate Contact No");
			altContactNo.setMinWidth(250);
			altContactNo.setStyle("-fx-font-size:19");
			validateOnFocus(altContactNo);
			
			gstin.setLabelFloat(true);
			gstin.setPromptText("GSTIN");
			gstin.setMinWidth(250);
			gstin.setStyle("-fx-font-size:19");
			validateOnFocus(gstin);
			
			state.setLabelFloat(true);
			state.setStyle("-fx-font-size:19");
			state.setPromptText("State");
			state.setMinWidth(250);
			state.getItems().addAll(purchaseController.fillStateCombo());
			state.setValue("Maharashtra");
			state.setOnKeyReleased(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					// TODO Auto-generated method stub
					String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), state.getValue(), state.getItems());
					if(s!=null) {
						state.requestFocus();
						state.getSelectionModel().select(s);
						
					}
				}
			});
			
			state.focusedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					// TODO Auto-generated method stub
					errorTip.hide();
				}
			});
			
			FileChooser chooser= new FileChooser();
			chooser.setTitle("Choose Logo");
			chooser.setInitialDirectory(new File(System.getProperty("user.home")));
			chooser.getExtensionFilters().addAll(
				    new FileChooser.ExtensionFilter("All Images", "*.*"),
				    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				    new FileChooser.ExtensionFilter("GIF", "*.gif"),
				    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
				    new FileChooser.ExtensionFilter("PNG", "*.png")
				);

			JFXButton openBtn= new JFXButton("Browse");
			openBtn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					File file= chooser.showOpenDialog(primaryStage);
					if(file!=null) {
						openFile(file);
					}
					
				}
			});
			
			logoPath.setPromptText("Logo Path");
			logoPath.setMaxWidth(200);
			validateOnFocus(logoPath);
			
			submitBtn.setOnAction(new EventHandler<ActionEvent>() {
				boolean result;
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					if(!ValidateCompany()) {
						return;
					}
					
//					Company company= new Company();
					company.setCompanyName(companyName.getText());
					company.setAddress(address.getText());
					company.setOfficeAddr(officeAddress.getText());
					company.setContactNo(contactNo.getText());
					company.setAltContact(altContactNo.getText());
					company.setEmailId(emailId.getText());
					company.setState(state.getValue());
					company.setGstin(gstin.getText());
					String path=logoPath.getText().replace('\\', ' ');
//					System.out.println(path);
					company.setLogoPath(path);
					result=connectionDAO.saveCompanyDetails(company);
					if(result) {
						Alert alert = new Alert(AlertType.INFORMATION, "Company Details Captured Successfully");
						alert.setTitle("Success Message");
						alert.setHeaderText("HI");
						alert.showAndWait();
						
						Main.dialog.close();
						Image img=new Image("file:///"+path.replaceAll(" ", "/"));
						logoImg.setImage(img);
						logoImg.setFitHeight(50);
						logoImg.setFitWidth(60);
					}else {
						Alert alert = new Alert(AlertType.ERROR,
								"Error while capturing data..!Please check database connection");
						alert.setTitle("Error Message");
						alert.setHeaderText("HI");
						alert.showAndWait();
					}
				}
			});
			
			gridPane.add(companyLbl, 0, 0);
			gridPane.add(companyName, 0, 2);
			gridPane.add(address, 1, 2);
			gridPane.add(officeAddress, 0, 4);
			gridPane.add(emailId, 1, 4); 
			gridPane.add(contactNo, 0, 6);
			gridPane.add(altContactNo, 1, 6);
			gridPane.add(state, 0, 8);
			gridPane.add(gstin, 1, 8);
			gridPane.add(logoPath, 0, 9);
			GridPane.setMargin(openBtn, new Insets(0,0,0,-100));
			gridPane.add(openBtn, 1, 9);
			
			GridPane.setMargin(submitBtn, new Insets(0,-100,0,150));
//			
			
			gridPane.add(submitBtn, 0, 10);
			
			Scene scene=new Scene(gridPane,900,750);
			scene.getStylesheets().add(getClass().getResource("/images/popup.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			scene.addEventFilter(KeyEvent.KEY_PRESSED, event->{
				
				if(event.getCode()==KeyCode.ESCAPE) {
				Main.dialog.close();
			}
				else if(event.getCode()==KeyCode.ENTER) {
	        		submitBtn.fire();
	        		event.consume();
	        	}
			});
			 dialog = new Stage();
			dialog.setScene(scene);
			
			dialog.initOwner(primaryStage);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initStyle(StageStyle.TRANSPARENT);	
			ImageView closeImg = new ImageView(new Image(ResourceLoader.load("/images/closeIcon.png")));
	        closeImg.setFitHeight(30);
	        closeImg.setFitWidth(30);
	        closeImg.setStyle("-fx-cursor:hand");
//	        GridPane.setMargin(closeImg, new Insets(0,0,0,-50));
	        gridPane.add(closeImg, 4, 0);
	        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    		@Override
	    		public void handle(MouseEvent arg0) {
	    			// TODO Auto-generated method stub
	    			dialog.close();
	    		}
	    		
		
	});
	        dialog.show();
	        
//	        gridPane.addEventFilter(KeyEvent.KEY_PRESSED, event->{
//	        	if(event.getCode()==KeyCode.ENTER) {
//	        		submitBtn.fire();
//	        		e.consume();
//	        	}
//	        });
//		
		}
		
		

	}

		private boolean validateonViewButton() {
			if (FromDate.getValue() == null) {
				errorTip.setText("Please select From date!");
				FromDate.setTooltip(errorTip);
				errorTip.show(FromDate, 500, 150);
				FromDate.requestFocus();
				return false;
			}
       if (ToDate.getValue() == null) {
			errorTip.setText("Please select To date!");
			ToDate.setTooltip(errorTip);
			errorTip.show(ToDate, 500, 150);
			ToDate.requestFocus();
			return false;
		}

       	if(FromDate.getValue().isAfter(ToDate.getValue())) {
       		errorTip.setText("From Date must be smalller than To Date!");
       		FromDate.setTooltip(errorTip);
			errorTip.show(FromDate, 500, 150);
			FromDate.requestFocus();
			return false;
       	}

       	if(ToDate.getValue().isAfter(LocalDate.now())) {
       		errorTip.setText("To Date must not exceed Today's date");
       		ToDate.setTooltip(errorTip);
			errorTip.show(ToDate, 500, 150);
			ToDate.requestFocus();
			return false;
       	}
//       if (!(pdf.isSelected()) && !(excel.isSelected())   ) {
//			errorTip.setText("Please select one Formate Atleast!");
//			ToDate.setTooltip(errorTip);
//			errorTip.show(ToDate, 500, 150);
//			return false;
//		}
			return true;

		}

		public boolean validateonGenerateButton() {
			if(anchorPane.getChildren().contains(purchReturnHB)) {
			if(PurchaseReportDate.getItems().isEmpty()) {
				errorTip.setText("No Data found for selected Dates");
				GenerateBtn.setTooltip(errorTip);
				errorTip.show(GenerateBtn, 550, 600);
				return false;
			}
			}
			else if(anchorPane.getChildren().contains(saleRetunHB)) {
				if(SalesReportDate.getItems().isEmpty()) {
					errorTip.setText("No Data found for selected Dates");
					GenerateBtn.setTooltip(errorTip);
					errorTip.show(GenerateBtn, 550, 600);
					return false;
				}
			}

			return true;

		}
		
		public boolean ValidateCompany() {
			if(companyName.getText().isEmpty()) {
				errorTip.setText("Please enter company Name");
				companyName.setTooltip(errorTip);
				errorTip.show(companyName, 300, 150);
				companyName.requestFocus();
				return false;
			}
			if(address.getText().isEmpty()) {
				errorTip.setText("Please enter address");
				address.setTooltip(errorTip);
				errorTip.show(address, 700, 150);
				address.requestFocus();
				return false;
			}
			
			if(officeAddress.getText().isEmpty()) {
				errorTip.setText("Please enter office address");
				officeAddress.setTooltip(errorTip);
				errorTip.show(officeAddress, 300, 230);
				officeAddress.requestFocus();
				return false;
			}
			if(emailId.getText().isEmpty()) {
				errorTip.setText("Please enter email Id");
				emailId.setTooltip(errorTip);
				errorTip.show(emailId, 700, 230);
				emailId.requestFocus();
				return false;
			}
			
			if(contactNo.getText().isEmpty()) {
				errorTip.setText("Please enter contact Number");
				contactNo.setTooltip(errorTip);
				errorTip.show(contactNo, 300, 330);
				contactNo.requestFocus();
				return false;
			}
			if(altContactNo.getText()==null) {
//			if(altContactNo.getText().isEmpty()) {
				errorTip.setText("Please enter alternate contact Number");
				altContactNo.setTooltip(errorTip);
				errorTip.show(altContactNo, 700, 330);
				altContactNo.requestFocus();
				return false;
//			}
			}
			if(state.getValue()==null) {
				errorTip.setText("Please select state");
				state.setTooltip(errorTip);
				errorTip.show(state, 300, 450);
//				state.requestFocus();
				return false;
			}
			
			if(!gstin.getText().isEmpty()) {
//				if(!gstin.getText().matches("\\d+")) {
//				errorTip.setText("Please enter correct GST containing 15 numbers only");
//				gstin.setTooltip(errorTip);
//				errorTip.show(gstin, 700, 450);
//				gstin.requestFocus();
//				return false;
//				}
				if(gstin.getText().length()!=15) {
					errorTip.setText("Please enter GST number containing 15 digits only");
					gstin.setTooltip(errorTip);
					errorTip.show(gstin, 700, 450);
					gstin.requestFocus();
					return false;
				}
			}
			if(logoPath.getText().isEmpty()) {
				errorTip.setText("Please select logo");
				logoPath.setTooltip(errorTip);
				errorTip.show(logoPath, 700, 600);
				logoPath.requestFocus();
				return false;
			}
			
			return true;
		}
		
		public boolean validateOnFocus(JFXTextField textField) {
			textField.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					errorTip.hide();
				}


			});
		
			return true;
		}
		
		 private void openFile(File file) {
			 Desktop desktop = Desktop.getDesktop();
//			 System.out.println(file.getAbsolutePath().toString());
		        	logoPath.setText(file.getAbsolutePath().toString());
		    }
		 
		 


}
