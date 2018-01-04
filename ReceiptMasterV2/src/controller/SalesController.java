package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.eclipse.jdt.internal.compiler.ast.ClassLiteralAccess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lowagie.text.Table;

//import application.BarCodeRead;
//import application.GenerateReceipt;
import application.KeyEventHandler;
import application.Main;
import application.Receipt_Master_Invoice;
import application.SmsSender;
//import application.Main.BarcodeListener;
//import dao.AccountDAO;
import dao.GoodsDAO;
import dao.SalesDAO;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
//import javafx.scene.control.JFXTextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.CashReportBean;
import model.Customer;
import model.DeliveryMemo;
import model.DeliveryProduct;
import model.PaymentMode;
import model.Product;
//import model.RecoveryDetails;
import model.SalesEntry;
import model.SalesMan;
import model.SalesManReportBean;
import model.SalesProduct;
import model.SalesReportBean;
import model.SalesReturn;
import net.sf.jasperreports.engine.JRException;
//import np.com.ngopal.control.AutoFillTextBox;
/***
 *
 * @author Saurabh Gupta
 *
 */
public class SalesController {

//	AccountDAO accountDAO= new AccountDAO();
	Customer customer = null;
	Product product = new Product();
	SalesProduct saleProd = new SalesProduct();

//	GenerateReceipt generateReceipt= new GenerateReceipt();
	
	ObservableList<Customer> custData = FXCollections.observableArrayList();
	ObservableList<String> prodList = FXCollections.observableArrayList();
	ObservableList<SalesEntry> salesData= FXCollections.observableArrayList();
	ObservableList<DeliveryMemo> deliveryData=FXCollections.observableArrayList();
	ObservableList<SalesReturn> saleReturnData= FXCollections.observableArrayList();
	ObservableList<SalesMan> salesManListData= FXCollections.observableArrayList();
    Receipt_Master_Invoice generateInvoice= new Receipt_Master_Invoice();
	List<Product> productList = new ArrayList<Product>();
	ObservableList<SalesReportBean> saleDataList = FXCollections.observableArrayList();
	ObservableList<SalesManReportBean> salesManReportDataList= FXCollections.observableArrayList();
	static ObservableList<CashReportBean> cashDataList = FXCollections.observableArrayList();
	Delivery_Memo_Generator delivery_Memo_Generator= new Delivery_Memo_Generator();
	ObservableList<String> salesManData= FXCollections.observableArrayList();
	  

//	Declaring validators
	RequiredFieldValidator validator=null;
	NumberValidator numberValidator=null;

	 GoodsController goodsController= new GoodsController();
	 double total=0;
	 double unPaidAmt=0;
	 double grandTotal=0;
	 double gstRate=0;
	 double cessRate=0;
	 double cessAmt=0;
	 double taxableAmt=0;
	 double gstAmt=0;

//Declaring fields for customer
	JFXTextField shopNameTxt = new JFXTextField();
	JFXTextField agencyTxt = new JFXTextField();
	JFXTextField prmConTxt = new JFXTextField();
	JFXComboBox<String> genderCombo = new JFXComboBox<String>();

	JFXTextArea addressTxt = new JFXTextArea();
	JFXTextField cityTxt = new JFXTextField();

	JFXComboBox<String> stateCombo = new JFXComboBox<>();
	JFXTextField fullNameTxt = new JFXTextField();
	JFXTextField emailTxt = new JFXTextField();
	JFXTextField altConTxt = new JFXTextField();


	JFXComboBox<String> areaCombo = new JFXComboBox<String>();
	JFXComboBox<String> salesManCombo= new JFXComboBox<>();
	

//Declaring fields for sales Invoice
	JFXComboBox<String> custCombo = new JFXComboBox<>();

	JFXDatePicker invoicDatePick=new JFXDatePicker();
	JFXComboBox<String> prodTxt = new JFXComboBox<>();
	JFXTextField gstTxt = new JFXTextField();
	JFXTextField address= new JFXTextField();
	JFXTextField hsnTxt = new JFXTextField();

	JFXTextField unitCombo = new JFXTextField();
//	JFXTextField dmTxt = new JFXTextField();
	JFXTextField quantityTxt = new JFXTextField();
	JFXTextField rateTxt = new JFXTextField();
	JFXTextField discTxt = new JFXTextField();
	JFXTextField taxableTxt =new JFXTextField();
	JFXTextField gstRsTxt = new JFXTextField();
	JFXTextField cGstTxt= new JFXTextField();
	JFXTextField iGstTxt = new JFXTextField();
	JFXTextField cessTxt = new JFXTextField();
	Label payModeLbl= new Label("Payment Modes :-");
	public JFXButton deleteBtn;

	JFXTextField totalTxt = new JFXTextField();
	JFXTextField grTotalTxt = new JFXTextField();
	JFXComboBox<String> payCombo = new JFXComboBox<String>();
	JFXTextField partPayTxt = new JFXTextField();
	JFXComboBox<String> payModeCombo = new JFXComboBox<String>();
//	Payment related controls
	JFXCheckBox cashCheck= new JFXCheckBox();
	JFXTextField cashAmtTxt= new JFXTextField();

	JFXCheckBox bankCheck= new JFXCheckBox();
	JFXTextField bankAmtTxt= new JFXTextField();
	JFXTextField bankTxt = new JFXTextField();
	JFXTextField cheqTxt = new JFXTextField();

	JFXCheckBox cardCheck= new JFXCheckBox();
	JFXTextField transId=new JFXTextField();
	JFXTextField cardAmtTxt= new JFXTextField();

	JFXCheckBox voucherCheck= new JFXCheckBox();
	JFXTextField vouchAmtTxt= new JFXTextField();
	JFXTextField voucherTypeTxt= new JFXTextField();
	
//Declaring fields for Sales Man
	JFXTextField salesManName;
	JFXTextField salesManContactNo;
	JFXTextField salesManAddress;
	
	
//Declaration for search functionality
	public SortedList<Customer> custSortedData;
	public SortedList<SalesEntry> saleSortedData;
	public SortedList<SalesEntry> recovSortedData;
	public FilteredList<Customer> custFilteredData;
	public FilteredList<SalesEntry> saleFilteredData;
	public FilteredList<SalesEntry> recoFilteredData;
	public SortedList<SalesEntry> paySortedData;
	public SortedList<SalesReturn> returnSortedData;
	public FilteredList<SalesReturn> returnFilteredData;
	
	public SortedList<DeliveryMemo> delSortedData;
	public FilteredList<DeliveryMemo> delFilteredData;
	
	public SortedList<SalesMan> salesManSortedData;
	public FilteredList<SalesMan> salesManFilteredData;
//Declaring database access object
	SalesDAO salesDAO = new SalesDAO();
	GoodsDAO goodsDAO = new GoodsDAO();

//Declaration for Payment Functionality
	JFXDatePicker datePayTxt = new JFXDatePicker();
	JFXTextField newPayTxt = new JFXTextField();
//Validation
	Tooltip errorTip=new Tooltip();
	ImageView errorIcon= new ImageView();
	ImageView errorIcon1= new ImageView();
	ImageView errorIcon2= new ImageView();
	ImageView errorIcon3= new ImageView();

	List<PaymentMode> paymentList= new ArrayList<>();
	
	
//Declaration for Delivery Memo
	
	JFXTextField advanceAmt= new JFXTextField();
	
//
//	public static JFXButton addMoreBtn;
//	public static RadioButton addItem;
//	public static	RadioButton editItem;
//
//	public static JFXButton submitBtn;


/**************	Start of label validation
 */
//	Label dateErrorLbl = new Label();
//	Label prodErrLbl = new Label();
//	Label unitErrorLbl =new Label();
//	Label quantityErrLbl = new Label();
//	Label rateErrLbl=new Label();
//	Label discErrLbl=new Label();
//	Label gstErrLbl= new Label();
//	Label totErrLbl = new Label();
//	Label dupliErrLbl = new Label();
//	Label payErrLbl = new Label();
//	Label payModeErrLbl = new Label();
//	Label emptyErrLbl = new Label();
//	Label grTotErrLbl = new Label();
//	Label partPayErrLbl =new Label();
/******End of label for validation
	 * */
//	String gender;
	// Main mainClass = new Main();

	public GridPane createNewCust(AnchorPane anchorPane) {
//		Stage secondary = new Stage();
//		secondary.setTitle("Add New Customer");
//		VBox vb = new VBox();
//		vb.setPadding(new Insets(10, 50, 50, 50));
//		vb.setSpacing(5);
		// vb.setMaxHeight(50.0);
		// vb.setMaxSize(100.0, 100.0);
		GridPane gp= new GridPane();

//		gp.setMinSize(800, 600);
		gp.getStyleClass().add("grid");
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(30);
		gp.setVgap(30);
		gp.setGridLinesVisible(false);

		Label titleLabel = new Label(" Add New Customer");
		GridPane.setMargin(titleLabel, new Insets(-40,-200,10,100));
		gp.add(titleLabel, 0, 0);

//		errorIcon.setImage(new Image(ResourceLoader.load("/images/errorIcon.png")));
//		errorIcon.setFitHeight(22);
//		errorIcon.setFitWidth(22);

		clearControls();
		fullNameTxt=new JFXTextField();
		fullNameTxt.setPromptText("Full Name");
		fullNameTxt.setLabelFloat(true);
		gp.add(fullNameTxt, 0, 1);
		
		genderCombo= new JFXComboBox<>();
		genderCombo.getItems().addAll("Male","Female");
		genderCombo.setLabelFloat(true);
		genderCombo.setPromptText("Gender");
		genderCombo.getSelectionModel().selectFirst();
		genderCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}
		});
		gp.add(genderCombo, 1, 1);
//		shopNameTxt.setPromptText("Shop Name *");
//		shopNameTxt.setLabelFloat(true);
//		validateOnFocus(shopNameTxt);
//		validator=new RequiredFieldValidator();
//		shopNameTxt.getValidators().add(validator);
//		validator.setMessage("Please enter Shop Name");
//		validator.setIcon(errorIcon);
//		gp.add(shopNameTxt, 0, 1);

//		shopNameTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				// TODO Auto-generated method stub
//				if(!newValue) {
//					shopNameTxt.validate();
//				}
//			}
//		});

//		agencyTxt.setLabelFloat(true);
//		agencyTxt.setPromptText("Agency *");
//		validateOnFocus(agencyTxt);

//		validator=new RequiredFieldValidator();
//		agencyTxt.getValidators()/.add(validator);
//		validator.s//etMessage("Please enter Agency");
//		errorIcon1.setImage(new Image(ResourceLoader.load("/images/errorIcon.png")));
//		errorIcon1.setFitHeight(22);
//		errorIcon1.setFitW/idth(22);

//		validator.setIcon(er/rorIcon1);
//		validator.getStyleClass().add("error-label");
//		gp.add(agencyTxt, 1, 1);

//		agencyTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				// TODO Auto-generated method stub
//			if(!newValue) {
//				agencyTxt.validate();
//			}
//			}
//		});
		
		prmConTxt= new JFXTextField();
		prmConTxt.setPromptText("Primary Contact Number *");
		prmConTxt.setLabelFloat(true);
		validateOnFocus(prmConTxt);
//		validator=new RequiredFieldValidator();
//		prmConTxt.getValidators().add(validator);
//		validator.setMessage("Please enter contact No");
//		errorIcon2.setImage(new Image(ResourceLoader.load("/images/errorIcon.png")));
//		errorIcon2.setFitHeight(22);
//		errorIcon2.setFitWidth(22);
//		validator.setIcon(errorIcon2);
//		numberValidator=new NumberValidator();
//		prmConTxt.getValidators().add(numberValidator);
//		numberValidator.setMessage("Only numbers are supported");
//		errorIcon3.se/tImage/(new Image(ResourceLoader.load("/images/errorIcon.png")));
//		errorIcon3.se/tFitHeight(22);
//		errorIcon3.set/FitWidth(22);
//		numberValidator.setIcon(errorIcon3);
		gp.add(prmConTxt, 0, 3);
		
		altConTxt=new JFXTextField();
		altConTxt.setPromptText("Alternate Contact Number");
		altConTxt.setLabelFloat(true);
		gp.add(altConTxt, 1, 3);

//		prmConTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				// TODO Auto-generated method stub
//				if(!newValue) {
//					prmConTxt.validate();
////					errorTip.hide();
//				}
//			}
//		});



//		Label genderLbl = new Label("Gender");
//		gp.add(genderLbl, 0, 6);
//		HBox hb = new HBox();

//		ToggleGroup tg = new ToggleGroup();
//		maleBtn.setToggleGroup(tg);
//		maleBtn.setSelected(true);
//		femaleBtn.setToggleGroup(tg);
//		hb.getChildren().addAll(maleBtn, femaleBtn);

//		areaCombo.setLabelFloat(true);
//		areaCombo.setPromptText("Area");
//		areaCombo.getItems().addAll("Amravati", "Badnera", "Cotton Market", "Rajpeth", "Gadge Nagar");
//		areaCombo.setEditable(true);
//		areaCombo.getSelectionModel().selectFirst();

//		gp.add(areaCombo, 1, 4);
		
		addressTxt= new JFXTextArea();
		addressTxt.setPromptText("Address");
		addressTxt.setLabelFloat(true);
		addressTxt.setMaxSize(150, 70);

		gp.add(addressTxt, 0, 4,1,2);

//		address.addEventFilter(KeyEvent.KEY_PRESSED, e->{
//			if(e.getCode()==KeyCode.TAB) {
//				JFXTextAreaSkin skin=(JFXTextAreaSkin) addressTxt.getSkin();
//				if(skin.getBehavior() instanceof TextAreaBehavior) {
//					TextAreaBehavior behavior=(TextAreaBehavior)skin.getBehavior();
//					if(e.isControlDown()) {
//						behavior.callAction("Insert Tab");
//					}
//					else if(e.isShiftDown()) {
//						behavior.callAction("TraversePrevious");
//					}
//					else {
//						behavior.callAction("TraverseNext");
//					}
//					e.consume();
//				}
//			}
//		});
		
		cityTxt.setPromptText("City *");
		cityTxt.setLabelFloat(true);
		validateOnFocus(cityTxt);
		validateOnFocus(cityTxt);
		gp.add(cityTxt, 1, 5);

		emailTxt.clear();
		emailTxt.setPromptText("Email");
		emailTxt.setLabelFloat(true);
		gp.add(emailTxt, 0, 6);
		
		stateCombo= new JFXComboBox<>();
		stateCombo.getItems().clear();
		stateCombo.setPromptText("State *");
		stateCombo.setLabelFloat(true);
		stateCombo.setItems(fillStateCombo());

		gp.add(stateCombo, 1, 6);

		stateCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(!newValue) {
				errorTip.hide();
				}
			}
		});
		stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
//				errorTip.hide();
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
				if(s!=null) {
					stateCombo.requestFocus();
					stateCombo.getSelectionModel().select(s);

				}
			}
		});



//		areaCombo.setValue("");



		JFXButton addBtn = new JFXButton(" Add ");
		JFXButton clrButton = new JFXButton(" Clear ");
		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				// ArrayList<String> arrList = new ArrayList<String>();

					// Main.stage.show();
//					System.out.println("Reached");
				errorTip.hide();
				creatCustSubmit(anchorPane);
			}
		});
		clrButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				errorTip.hide();
				emailTxt.clear();
				prmConTxt.clear();
				altConTxt.clear();
				addressTxt.clear();
				cityTxt.clear();
				altConTxt.clear();
				fullNameTxt.clear();
//				emailTxt.clear();
				genderCombo.getSelectionModel().selectFirst();
//				if (tg.getSelectedToggle().isSelected() == true) {
//					maleBtn.setSelected(true);
//				}
			}
		});

		HBox hb1 = new HBox();
        hb1.setSpacing(50);
//        hb1.setMinSize(100, 30);
        addBtn.setMinSize(100, 30);
//        addBtn.setStyle("-fx-font-size: 10pt;");
        clrButton.setMinSize(100,30);
//        clrButton.setStyle("-fx-font-size: 10pt;");
        hb1.getChildren().addAll(addBtn, clrButton);

        GridPane.setMargin(hb1, new Insets(0,-50,0,100));
		gp.add(hb1, 0, 9,2,1);

		gp.setAlignment(Pos.CENTER);

		return gp;
//		vb.getChildren().addAll(shopNameLbl, shopNameTxt, agencyLbl, agencyTxt, prmConLbl, prmConTxt, genderLbl, hb,
//				addressLbl, addressTxt, areaLbl, areaCombo, cityLbl, cityTxt, fulNameLbl, fullNameTxt, emailLbl,
//				emailTxt, altConLbl, altConTxt, hb1);
//		Scene scene = new Scene(vb, 500, 700);
		// secondary.setAlwaysOnTop(true);
//		secondary.setScene(scene);
//		secondary.show();

	}

	public TableView<Customer> showCust(AnchorPane anchorPane) {

		TableView<Customer> custView = new TableView<Customer>();
		custData.clear();
		// custView.setEditable(true);
		TableColumn<Customer, String> srNOCol = new TableColumn<>("Sr. No. ");
		srNOCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));
		srNOCol.setPrefWidth(100);
		srNOCol.setStyle("-fx-alignment:center");
		TableColumn<Customer, String> fullNameCol = new TableColumn<Customer, String>("Full Name");
		fullNameCol.setCellValueFactory(new PropertyValueFactory<>("full_name"));
		fullNameCol.setPrefWidth(200);
		fullNameCol.setStyle("-fx-alignment:center");
//		TableColumn<Customer, String> agencyCol = new TableColumn<Customer, String>("Agency");
//		agencyCol.setCellValueFactory(new PropertyValueFactory<>("agency"));
//		agencyCol.setMinWidth(150);
//		agencyCol.setStyle("-fx-alignment:center");
//		TableColumn<Customer, String> genderCol = new TableColumn<Customer, String>("Gender");
//		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
//		TableColumn<Customer, String> fullNameCol = new TableColumn<Customer, String>("Full Name");
//		fullNameCol.setCellValueFactory(new PropertyValueFactory<>("full_name"));
//		TableColumn<Customer, String> altcontCol = new TableColumn<Customer, String>("Alternate Contact Number");
//		altcontCol.setCellValueFactory(new PropertyValueFactory<>("altcontno"));
		TableColumn<Customer, String> contacCol = new TableColumn<Customer, String>("Contact No");
		contacCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
		contacCol.setPrefWidth(150);
		contacCol.setStyle("-fx-alignment:center");
		TableColumn<Customer, String> areaCol = new TableColumn<Customer, String>("State");
		areaCol.setCellValueFactory(new PropertyValueFactory<>("state"));
		areaCol.setPrefWidth(150);
		areaCol.setStyle("-fx-alignment:center");
		TableColumn<Customer, String> gstCol = new TableColumn<Customer, String>("GSTIN");
		gstCol.setCellValueFactory(new PropertyValueFactory<>("gstin"));
		gstCol.setPrefWidth(150);
		gstCol.setStyle("-fx-alignment:center");
		TableColumn<Customer, String> addressCol = new TableColumn<Customer, String>("Address");
		addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
		addressCol.setMinWidth(120);
		addressCol.setStyle("-fx-alignment:center");
		TableColumn<Customer, Customer> actionCol = new TableColumn<Customer, Customer>("Action");
		actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
		actionCol.setCellFactory(e->new TableCell<Customer,Customer>(){
//			JFXButton delBtn = new JFXButton("Delete");
			boolean result1;
			@Override
		    protected void updateItem(Customer customer,boolean empty){
				deleteBtn= new JFXButton("Delete");
				deleteBtn.getStyleClass().add("del-btn");


				custView.setOnKeyPressed( new EventHandler<KeyEvent>()
				{
				  @Override
				  public void handle( final KeyEvent keyEvent )
				  {
					 Customer customer=getTableView().getSelectionModel().getSelectedItem();

				    if ( customer != null )
				    {
				      if ( keyEvent.getCode()==KeyCode.DELETE )
				      {
				    	  Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
							Optional<ButtonType> result= alert.showAndWait();
							if(result.get()==ButtonType.OK){

								getTableView().getSortOrder().remove(customer);
								result1= salesDAO.deleteCustomer(customer);
								showCust(anchorPane);
								Main.custView.setItems(custSortedData);
								Main.custView.requestFocus();
								Main.custView.getSelectionModel().selectLast();
								Main.custView.getFocusModel().focusNext();

								Main.custView.setMinSize(800, 500);
								anchorPane.getChildren().set(0,Main.custView);
								getTableView().refresh();

				    	  }


				      }}}
				  });

					deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
//							Main.custView.setItems(custSortedData);
//							int index=getTableView().getFocusModel().getFocusedIndex();
//							System.out.println("index"+index);
							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
//							alert.setHeaderText("HI");
							Optional<ButtonType> result= alert.showAndWait();
							if(result.get()==ButtonType.OK){

								getTableView().getSortOrder().remove(customer);
								result1= salesDAO.deleteCustomer(customer);
								showCust(anchorPane);
								Main.custView.setItems(custSortedData);
								Main.custView.requestFocus();
								Main.custView.getSelectionModel().selectLast();
								Main.custView.getFocusModel().focusNext();

								Main.custView.setMinSize(800, 500);
								anchorPane.getChildren().set(0,Main.custView);
								getTableView().refresh();
							}
						}
					});
					if(customer==null||empty) {
						setGraphic(null);
						return;
					}
					else {
						setGraphic(deleteBtn);
				}
			}
		});
//		TableColumn<Customer, String> emailCol = new TableColumn<Customer, String>("Email");
//		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		custView.getColumns().addAll(srNOCol, fullNameCol, contacCol,addressCol, areaCol,
				gstCol);

		custView.getColumns().addListener(new ListChangeListener() {
			public boolean suspended;

			@Override
			public void onChanged(Change c) {
				// TODO Auto-generated method stub
				c.next();
				if (c.wasReplaced() && !suspended) {
					this.suspended = true;
					custView.getColumns().setAll(srNOCol, fullNameCol, contacCol,addressCol, areaCol,
							gstCol);
					this.suspended = false;
				}
			}

		});

		List<Customer> custList = salesDAO.showCust();
		Iterator<Customer> itr = custList.iterator();
		long srNo=1;
		while (itr.hasNext()) {
			Customer cust = itr.next();
			if(!cust.getFull_name().equals("null")) {
			custData.add(new Customer(cust.getCust_id(), cust.getShopName(), cust.getAgency(), cust.getGender(),
					cust.getFull_name(), cust.getAddress(), cust.getState(), cust.getCity(), cust.getContact(),
					cust.getAltcontno(), cust.getEmail(),cust.getGstin(),srNo));
			srNo++;
			}

		}

		custFilteredData = new FilteredList<>(custData,p->true);



		custSortedData =new SortedList<>(custFilteredData);
		custSortedData.comparatorProperty().bind(custView.comparatorProperty());
		custView.setItems(custSortedData);
		return custView;

	}

	public GridPane updateCustomer(Customer customer,int index,AnchorPane anchorPane) {
		GridPane gp= new GridPane();
		gp.getStyleClass().add("grid");
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(30);
		gp.setVgap(30);
		gp.setGridLinesVisible(false);

		Label titleLabel = new Label("Update Customer");
		GridPane.setMargin(titleLabel, new Insets(-40,-200,10,100));
		gp.add(titleLabel, 0, 0);

//		shopNameTxt.setPromptText("Shop Name *");
//		shopNameTxt.setText(customer.getShopName());
//		shopNameTxt.setLabelFloat(true);
//		validateOnFocus(shopNameTxt);
//		gp.add(shopNameTxt, 0, 1);


		clearControls();
//		agencyTxt.setPromptText("Agency *");
//		agencyTxt.setText(customer.getAgency());
//		agencyTxt.setLabelFloat(true);
//		validateOnFocus(agencyTxt);
//		gp.add(agencyTxt, 1, 1);

		fullNameTxt.setPromptText("Full Name");
		fullNameTxt.setLabelFloat(true);
		fullNameTxt.setText(customer.getFull_name());
		gp.add(fullNameTxt, 0, 1);


		genderCombo.getItems().addAll("Male","Female");
		genderCombo.setLabelFloat(true);
		genderCombo.setPromptText("Gender");
		genderCombo.setValue(customer.getGender());
		genderCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}
		});
		gp.add(genderCombo, 1, 1);
//

		prmConTxt.setPromptText("Primary Contact Number *");
		validateOnFocus(prmConTxt);

		prmConTxt.setLabelFloat(true);
		prmConTxt.setText(customer.getContact());
		gp.add(prmConTxt, 0, 3);

		altConTxt.setPromptText("Alternate Contact Number");
		altConTxt.setLabelFloat(true);
		altConTxt.setText(customer.getAltcontno());
		gp.add(altConTxt, 1, 3);


		addressTxt.setPromptText("Address");
		addressTxt.setText(customer.getAddress());
		addressTxt.setLabelFloat(true);
		addressTxt.setMaxSize(150, 70);
		gp.add(addressTxt, 0, 4,1,2);

		cityTxt.setPromptText("City *");
		cityTxt.setText(customer.getCity());
		cityTxt.setLabelFloat(true);
		gp.add(cityTxt, 1, 5);
		validateOnFocus(cityTxt);


		emailTxt.clear();
		emailTxt.setPromptText("Email");
		emailTxt.setText(customer.getEmail());
		emailTxt.setLabelFloat(true);
		gp.add(emailTxt, 0, 6);

		stateCombo.getItems().clear();
		stateCombo.setPromptText("State *");
		stateCombo.setLabelFloat(true);

		stateCombo.setItems(fillStateCombo());
		stateCombo.setValue(customer.getState());

		stateCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}
		});

		stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				errorTip.hide();
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
				if(s!=null) {
					stateCombo.requestFocus();
					stateCombo.setValue(s);

				}
			}
		});

		gp.add(stateCombo, 1, 6);


		JFXButton editBtn = new JFXButton(" Update ");
		JFXButton clrButton = new JFXButton(" Clear ");
		editBtn.setOnAction(new EventHandler<ActionEvent>() {
//			boolean result;
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				// ArrayList<String> arrList = new ArrayList<String>();

					// Main.stage.show();
//					System.out.println("Reached");
				updateCustSubmit(customer, index,anchorPane);
			}
		});
		clrButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				errorTip.hide();
				emailTxt.clear();
				prmConTxt.clear();
				altConTxt.clear();
				addressTxt.clear();
				cityTxt.clear();
				altConTxt.clear();
				fullNameTxt.clear();
//				emailTxt.clear();
				genderCombo.getSelectionModel().selectFirst();
//				if (tg.getSelectedToggle().isSelected() == true) {
//					maleBtn.setSelected(true);
//				}
			}
		});

		HBox hb1 = new HBox();
        hb1.setSpacing(50);
//        hb1.setMinSize(100, 30);
        editBtn.setMinSize(100, 30);
//        editBtn.setStyle("-fx-font-size: 10pt;");
        clrButton.setMinSize(100,30);
//        clrButton.setStyle("-fx-font-size: 10pt;");
        hb1.getChildren().addAll(editBtn, clrButton);
        GridPane.setMargin(hb1, new Insets(0,-50,0,100));
		gp.add(hb1, 0, 9,2,1);

		gp.setAlignment(Pos.CENTER);

		return gp;
	}

	public GridPane createNewInvoice(AnchorPane anchorPane) {
		clearInvoiceControls();
		clearProductData();
		grandTotal=0;
		cessAmt=0;
		taxableAmt=0;
		gstAmt=0;

		GridPane gp=new GridPane();
		errorTip.setAutoHide(true);
		gp.getStyleClass().add("grid");
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(10, 10, 10, 10));
//		gp.setGridLinesVisible(true);

		Label titleLabel = new Label(" Create New Sales Invoice");
		GridPane.setMargin(titleLabel, new Insets(0,-400,0,400));
		gp.add(titleLabel, 0, 0);

		Label custCreatLbl=new Label("Customer Details");
//		GridPane.setMargin(custCreatLbl, new Insets(0,-100,0,100));
		gp.add(custCreatLbl, 1, 1);

//		long entry_id=accountDAO.getAccountEntryId()+1;

		long invoiceId=salesDAO.getSalesInvoiceId()+1;
		int length=String.valueOf(invoiceId).length();
		Label saleInvoiceLbl =new Label();
		saleInvoiceLbl.setText(String.format("SI"+"%0"+(6-length)+"d", (invoiceId)));
		GridPane.setMargin(saleInvoiceLbl, new Insets(0,0,0,-200));
		gp.add(saleInvoiceLbl, 6, 0);
		
		invoicDatePick= new JFXDatePicker();
		invoicDatePick.setValue(LocalDate.now());
		invoicDatePick.setPromptText("Entry Date");
		invoicDatePick.setMaxWidth(150);
		invoicDatePick.setEditable(true);
		invoicDatePick.setShowWeekNumbers(false);
		invoicDatePick.getStyleClass().add("date-pick");

		KeyEventHandler.dateKeyEvent(invoicDatePick);
		GridPane.setMargin(invoicDatePick, new Insets(0,-50,0,0));
//		GridPane.setMargin(invoicDatePick, new Insets(0,0,0,-200));
		gp.add(invoicDatePick, 0, 1);

		HBox custComboHB= new HBox();
		
		custCombo= new JFXComboBox<>();
//		custCombo.setValue(null);
		custCombo.setVisible(true);

//		custCombo.setStyle("-fx-font-size:15");
		custCombo.getStyleClass().add("jf-combo-box");
		custCombo.setStyle("-fx-font-size:12");
		custCombo.setMaxWidth(110);
		custCombo.setLabelFloat(true);
		custCombo.setPromptText("Customer Name");
		custCombo.setEditable(true);




		ObservableList<String> custObsData = FXCollections.observableArrayList();
		List<Customer> custList= salesDAO.getCustomerNames();
		Iterator<Customer> itr= custList.iterator();
		while(itr.hasNext()) {
			Customer customer=itr.next();
			if(!customer.getFull_name().equals("null")) {
			custObsData.add(customer.getFull_name());
			}
		}
		custCombo.setItems(custObsData);

		new AutoCompleteComboBoxListener<>(custCombo);

		custCombo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				for(Customer c:custList) {
					if(custCombo.getValue()!=null) {
					if(custCombo.getValue().equals(c.getFull_name())) {
						address.setText(c.getAddress());
						stateCombo.setValue(c.getState());
						prmConTxt.setText(c.getContact());
						gstTxt.setText(c.getGstin());
					}
					}
				}
			}
		});


//		address.clear();
		address= new JFXTextField();
		address.setPromptText("Address");
		address.setStyle("-fx-font-size:12");
		address.setLabelFloat(true);
		address.setMaxWidth(80);

//		prmConTxt.clear();
		prmConTxt= new JFXTextField();
		prmConTxt.setPromptText("Contact Number");
		prmConTxt.setStyle("-fx-font-size:12");
		prmConTxt.setLabelFloat(true);
		prmConTxt.setMaxWidth(80);
		
		stateCombo= new JFXComboBox<>();
		stateCombo.setLabelFloat(true);
		stateCombo.setPromptText("State");
		stateCombo.setStyle("-fx-font-size:12");

//		stateCombo.setMaxWidth(10);
		stateCombo.getItems().addAll(fillStateCombo());
		stateCombo.setValue("Maharashtra");
		stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
//				errorTip.hide();
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
				if(s!=null) {
					stateCombo.requestFocus();
					stateCombo.getSelectionModel().select(s);

				}
			}
		});

//		gstTxt.clear();
		gstTxt= new JFXTextField();
		gstTxt.setPromptText("GSTIN");
		gstTxt.setLabelFloat(true);
		gstTxt.setMaxWidth(100);
		gstTxt.setStyle("-fx-font-size:12");


		custComboHB.getChildren().addAll(custCombo,address,stateCombo,prmConTxt,gstTxt);
		custComboHB.setSpacing(20);
		GridPane.setMargin(custComboHB, new Insets(0,0,0,-200));
		gp.add(custComboHB, 2, 2);




		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
		     public DateCell call(final DatePicker datePicker) {
		         return new DateCell() {
		             @Override public void updateItem(LocalDate item, boolean empty) {
//		                 super.updateItem(item, empty);


		                 if (item.isAfter(LocalDate.now())) {
		                     // Tomorrow is too soon.
		                     setDisable(true);
		                 }
		             }
		         };
		     }
		 };
		 invoicDatePick.setDayCellFactory(dayCellFactory);


//		GridPane.setMargin(custCombo, new Insets(0,0,0,20));
//		gp.add(custCombo, 0, 2);



//		invoicDatePick.setMinWidth(100);
//		GridPane.setMargin(custCombo, new Insets(0,0,0,20));
//		gp.add(invoicDatePick, 1, 2);



//		custCombo.setConverter(new StringConverter<String>() {
//
//			@Override
//			public String toString(String object) {
//				// TODO Auto-generated method stub
//				if(object==null)return null;
//
//				return object.toString();
//			}
//
//			@Override
//			public String fromString(String string) {
//				// TODO Auto-generated method stub
////				Customer customer=new Customer();
////				for(String cust:custCombo.getItems()) {
////					cust
////				}
//				return string;
//			}
//		});



		HBox prodHB = new HBox();

		Label addProdLbl =  new Label("Add Product");
		GridPane.setMargin(addProdLbl, new Insets(0,-50,0,0));
		gp.add(addProdLbl, 0, 4);
//		Label prodLbl = new Label("Product");
//		gp.add(prodLbl, 0, 5);

//		BarCodeRead barCodeRead= new BarCodeRead();

		prodTxt= new JFXComboBox<>();
		prodTxt.setLabelFloat(true);
		prodTxt.setPromptText("Select Product");
		prodTxt.setStyle("-fx-font-size:12");
//		new AutoCompleteComboBoxListener<>(prodTxt);

//		prodTxt.setDisable(true);

//		prodTxt.setEditable(true);
		prodTxt.setMaxWidth(100);

		prodTxt.getItems().clear();
		prodList.clear();

		productList=goodsDAO.showProductList();

		for(Product p:productList) {
			if(p.getCategory()!=null) {
			prodList.add(p.getProduct_name()+"        "+p.getCategory().replaceAll("null", "")+"("+p.getSubGroup().replaceAll("null", "")+")");
			}
		}

		if(!prodList.isEmpty()) {
			prodTxt.getItems().addAll(prodList);
		}
		prodTxt.getStyleClass().add("jf-combo-box");
		new AutoCompleteComboBoxListener<>(prodTxt);
//

//		hsnTxt.clear();
		hsnTxt= new JFXTextField();
		hsnTxt.setPromptText("HSN/SAC");
		hsnTxt.setLabelFloat(true);
		hsnTxt.setEditable(false);
		hsnTxt.setStyle("-fx-font-size:12");



		//unitCombo.getItems().clear();
		unitCombo= new JFXTextField();
		unitCombo.setLabelFloat(true);
		unitCombo.setPromptText("Select Unit");
		unitCombo.setStyle("-fx-font-size:12");
		unitCombo.setMaxWidth(80);
		unitCombo.setEditable(false);
//		unitCombo.getStyleClass().add("jf-combo-box");

//		unitCombo.add(goodsController.fillUnitList());
//		new AutoCompleteComboBoxListener<>(unitCombo);
//		unitCombo.setConverter(new StringConverter<String>() {
//
//			@Override
//			public String toString(String object) {
//				// TODO Auto-generated method stub
//				if(object==null)return null;
//
//				return object.toString();
//			}
//
//			@Override
//			public String fromString(String string) {
//				// TODO Auto-generated method stub
//				Customer customer=new Customer();
//				for(String cust:custCombo.getItems()) {
//					cust
//				}
//				return string;
//			}
//		});

//		unitCombo.getItems().addAll("KG","PCS","LIT","ML","GRAM");
		quantityTxt= new JFXTextField();
		quantityTxt.setPromptText("Quantity");
		quantityTxt.setText("0");
		quantityTxt.setStyle("-fx-font-size:12");
		quantityTxt.setLabelFloat(true);
		quantityTxt.setMaxWidth(50);
		
		rateTxt= new JFXTextField();
		rateTxt.setLabelFloat(true);
		rateTxt.setStyle("-fx-font-size:12");
		rateTxt.setMaxWidth(50);
		rateTxt.setPromptText("Rate");
		rateTxt.setText("0.00");
		
		discTxt= new JFXTextField();
		discTxt.setMaxWidth(50);
		discTxt.setStyle("-fx-font-size:12");
		discTxt.setLabelFloat(true);
		discTxt.setPromptText("Discount");
		discTxt.setText("0.0");

//		taxableTxt.setPromptText("Taxable"+"\n Amt");
//		taxableTxt.setText("0");
//		taxableTxt.setLabelFloat(true);
//		taxableTxt.setMaxWidth(50);
//		taxableTxt.setEditable(false);
//		taxableTxt.setStyle("-fx-font-size:12");
		
//		cGstTxt.clear();
		cGstTxt= new JFXTextField();
		cGstTxt.setPromptText("CGST");
		cGstTxt.setMaxWidth(50);
		cGstTxt.setEditable(false);
		cGstTxt.setStyle("-fx-font-size:12");
		cGstTxt.setLabelFloat(true);

//		gstRsTxt.clear();
		gstRsTxt= new JFXTextField();
		gstRsTxt.setPromptText("SGST");
		gstRsTxt.setMaxWidth(50);
		gstRsTxt.setEditable(false);
		gstRsTxt.setStyle("-fx-font-size:12");
		gstRsTxt.setLabelFloat(true);

//		iGstTxt.clear();
		iGstTxt= new JFXTextField();
		iGstTxt.setPromptText("IGST");
		iGstTxt.setMaxWidth(50);
		iGstTxt.setEditable(false);
		iGstTxt.setStyle("-fx-font-size:12");
		iGstTxt.setLabelFloat(true);

//		cessTxt.clear();
		cessTxt= new JFXTextField();
		cessTxt.setPromptText("Cess");
		cessTxt.setMaxWidth(50);
		cessTxt.setEditable(false);
		cessTxt.setStyle("-fx-font-size:12");
		cessTxt.setLabelFloat(true);

//		gstRsTxt.setPromptText("GST In %");

		totalTxt= new JFXTextField();
		totalTxt.setEditable(false);
//		hsnTxt.setStyle("-fx-font-size:12");
		totalTxt.setText("0.00");
		totalTxt.setPromptText("Total");
		totalTxt.setLabelFloat(true);
		totalTxt.setStyle("-fx-font-size: 15px;-fx-font-weight:bold");
		totalTxt.setFocusColor(Color.TRANSPARENT);
		totalTxt.setUnFocusColor(Color.TRANSPARENT);
		totalTxt.setMaxWidth(100);
		HBox.setMargin(totalTxt, new Insets(0, 0, 20, 20));



		stateCombo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				String state=stateCombo.getSelectionModel().getSelectedItem();
				if(state!=null) {
					prodTxt.setDisable(false);
				if(state.contains("Maharashtra")) {
					prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
					prodHB.getChildren().add(6, cGstTxt);
					prodHB.getChildren().add(7,gstRsTxt);
					HBox.setMargin(gstRsTxt, new Insets(0,-20,0,0));
//					GridPane.setMargin(prodHB, new Insets(0,0,0,50));
//					gp.setPadding(new Insets(10,10,10,10));
				}
				else {
					prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
					prodHB.getChildren().add(6,iGstTxt);
					prodHB.getChildren().add(7,cessTxt);
					HBox.setMargin(cessTxt, new Insets(0,-20,0,0));
//					GridPane.setMargin(prodHB, new Insets(0,0,0,50));
//					HBox.setMargin(totalTxt, new Insets(0,5,0,-50));
				}
			}
			}
		});

	    JFXButton addMoreBtn = new JFXButton(" Add ");
		addMoreBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");
		addMoreBtn.setMaxWidth(80);
		addMoreBtn.setDisable(true);

		JFXButton editBtn=new JFXButton("Edit");
		editBtn.setMaxWidth(80);
		editBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");

		RadioButton addItem = new RadioButton("Add Item");
		addItem.setSelected(true);
		addItem.setUserData("Add Item");

		RadioButton editItem=new RadioButton("Edit Item");
		editItem.setDisable(true);
		editItem.setUserData("Edit Item");
		ToggleGroup tg = new ToggleGroup();
		addItem.setToggleGroup(tg);
		editItem.setToggleGroup(tg);
		VBox vb1 = new VBox();
		vb1.setStyle("-fx-font-size:15px; -fx-font-weight:bold;");
		vb1.getChildren().addAll(addItem,editItem);
		vb1.setMaxHeight(30);
//		HBox.setMargin(addMoreBtn, new Insets(0,-50,0,20));
		HBox.setMargin(vb1, new Insets(0,0,0,20));
		prodHB.getChildren().addAll(prodTxt,hsnTxt,unitCombo,quantityTxt,rateTxt,discTxt,cGstTxt,gstRsTxt,totalTxt,addMoreBtn,vb1);

		prodHB.setSpacing(20);
		GridPane.setMargin(prodHB, new Insets(0,-50,0,0));
		gp.add(prodHB, 0, 6,8,1);


//		Label grTotalLbl = new Label("Grand Total");
//		gp.add(grTotalLbl, 4, 9);

		ObservableList<SalesProduct> prodData = FXCollections.observableArrayList();
		TableView<SalesProduct> prodView = new TableView<SalesProduct>();
		prodView.setFocusTraversable(true);

		prodView.setMaxSize(1100, 200);
		TableColumn<SalesProduct, String> prodName = new TableColumn<SalesProduct, String>("Product Name");
		prodName.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
		prodName.setPrefWidth(150);
		TableColumn<SalesProduct, Long> hsnCol = new TableColumn<SalesProduct, Long>("HSN/SAC");
		hsnCol.setCellValueFactory(new PropertyValueFactory<>("hsnNo"));
		TableColumn<SalesProduct, String> unitCol = new TableColumn<SalesProduct, String>("Unit");
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		TableColumn<SalesProduct, Integer> quantityCol = new TableColumn<SalesProduct, Integer>("Quantity");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		TableColumn<SalesProduct, String> rateCol = new TableColumn<SalesProduct, String>("Rate");
		rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
		TableColumn<SalesProduct, String> discCol = new TableColumn<SalesProduct, String>("Discount\n"+" Rs.");
		discCol.setCellValueFactory(new PropertyValueFactory<>("discountString"));
		TableColumn<SalesProduct, SalesProduct> taxableCol = new TableColumn<SalesProduct, SalesProduct>("Taxable\n"+"Amount");
		taxableCol.setCellValueFactory(new PropertyValueFactory<>("subTotalString"));
//		taxableCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>(){
//			@Override
//			protected void updateItem(SalesProduct salesProduct,boolean empty) {
//				if(salesProduct==null||empty) {
//					setText(null);
//					return;
//				}
//				setText(taxableTxt.getText());
//			}
//		});

		TableColumn<SalesProduct, SalesProduct> cgstCol = new TableColumn<SalesProduct, SalesProduct>("CGST\n"+"  (%)");
		cgstCol.setCellValueFactory(new PropertyValueFactory<>("cgst"));
//		cgstCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>() {
//			@Override
//			protected void updateItem(SalesProduct salesProduct,boolean empty) {
//				if(salesProduct==null||empty) {
//					setGraphic(null);
//					return;
//				}
//
//					System.out.println(cGstTxt.getText());
//					String cgst=cGstTxt.getText()+"("+salesProduct.getGst()/2+"%)";
//					setText(cgst);
//
//			}
//		});
		TableColumn<SalesProduct, SalesProduct> sgstCol = new TableColumn<SalesProduct, SalesProduct>("SGST\n"+"  (%)");
		sgstCol.setCellValueFactory(new PropertyValueFactory<>("sgst"));
//		sgstCol.setCellFactory(e-> new TableCell<SalesProduct,SalesProduct>(){
//			@Override
//			protected void updateItem(SalesProduct salesProduct,boolean empty) {
//				if(salesProduct==null||empty) {
//					setText(null);
//					return;
//				}
//				else {
//					System.out.println(gstRsTxt.getText());
//
//					String sgst=gstRsTxt.getText()+"("+salesProduct.getGst()/2+"%)";
//					setText(sgst);
//				}
//			}
//		});

		TableColumn<SalesProduct, SalesProduct> igstCol = new TableColumn<SalesProduct, SalesProduct>("IGST\n"+"  (%)");
		igstCol.setCellValueFactory(new PropertyValueFactory<>("igst"));
//		igstCol.setCellFactory(e-> new TableCell<SalesProduct,SalesProduct>(){
//			@Override
//			protected void updateItem(SalesProduct salesProduct,boolean empty) {
//				if(salesProduct==null||empty) {
//					setText(null);
//					return;
//				}
////				else {
//					String igst=iGstTxt.getText()+"("+salesProduct.getGst()+"%)";
//					setText(igst);
////				}
//			}
//		});
		TableColumn<SalesProduct, SalesProduct> cessCol = new TableColumn<SalesProduct, SalesProduct>("Cess\n"+" (%)");
//		cessCol.setCellValueFactory(new PropertyValueFactory<>("gst"));
		cessCol.setCellValueFactory(new PropertyValueFactory<>("cess"));
//		cessCol.setCellFactory(e-> new TableCell<SalesProduct,SalesProduct>(){
//			@Override
//			protected void updateItem(SalesProduct salesProduct,boolean empty) {
//				if(salesProduct==null||empty) {
//					setText(null);
//					return;
//				}
////				else {
//					String cess=cessTxt.getText();
//					setText(cess);
////				}
//			}
//		});

		TableColumn<SalesProduct, String> totalCol = new TableColumn<SalesProduct, String>("Total");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
		TableColumn<SalesProduct, SalesProduct> actionCol = new TableColumn<SalesProduct, SalesProduct>("Action");
		actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
		actionCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>(){

			@Override
		    protected void updateItem(SalesProduct salesProduct,boolean empty){
				if(salesProduct==null){
					setGraphic(null);
					return;
				}else{
					deleteBtn=new JFXButton("Delete");
					deleteBtn= new JFXButton("Delete");
					setGraphic(deleteBtn);
//					deleteBtn.setDisable(false);
					deleteBtn.getStyleClass().add("del-btn");
					deleteBtn.setAlignment(Pos.CENTER);
					deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							// TODO Auto-generated method stub
							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
//							alert.setHeaderText("HI");
							Optional<ButtonType> result= alert.showAndWait();
							if(result.get()==ButtonType.OK){
								getTableView().getItems().remove(salesProduct);
								getTableView().refresh();
								grandTotal=0;
								if(prodData.size()==0)
									grTotalTxt.setText("0.0");
								else{
								for(SalesProduct p:prodData){
									grandTotal=grandTotal+p.getTotal();
									grTotalTxt.setText(String.format("%.2f",grandTotal));

								}
								}
							}

						}
					});


				}
			}
		});
		prodView.getColumns().addAll(prodName,hsnCol,unitCol,quantityCol,rateCol,discCol,taxableCol,cgstCol,sgstCol,igstCol,cessCol,totalCol,actionCol);
		
		GridPane.setMargin(prodView, new Insets(-20,-50,0,0));


		gp.add(prodView, 0, 8,12,1);

//		prodView.prefHeightProperty().bind(prodView.prefHeightProperty().multiply(prodView.getItems().size()/2));


		prodTxt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(prodTxt.getValue()!=null){
					addMoreBtn.setDisable(false);
					editBtn.setDisable(false);
					if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
						for(Product p:productList) {
							if(prodTxt.getValue().contains(p.getProduct_name())) {
								double gst=p.getGst()/2;
								gstRate=p.getGst();
//								cessRate=p.getCess();
								cGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
								gstRsTxt.setText(String.valueOf("0 ("+gst+"%)"));
								hsnTxt.setText(String.valueOf(p.getHsnNo()));
								rateTxt.setText(String.valueOf(p.getSellPrice()));
								unitCombo.setText(String.valueOf(p.getUnit()));
							}


						}

					}

					else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
						for(Product p:productList) {
							if(prodTxt.getValue().contains(p.getProduct_name())) {
								double gst=p.getGst();
								double cess=p.getCess();
								gstRate=p.getGst();
								cessRate=p.getCess();
								iGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
								cessTxt.setText(String.valueOf("0 ("+cess+"%)"));
								hsnTxt.setText(String.valueOf(p.getHsnNo()));
								rateTxt.setText(String.valueOf(p.getSellPrice()));
								unitCombo.setText(String.valueOf(p.getUnit()));
							}

						}
					}
//					return;
				}
			}
		});

		unitCombo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(unitCombo.getText()!=null) {
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				}
			}
		});


		addMoreBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
//				clearLabels();
//				gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
//					System.out.println(validateControls());
				boolean productFlag=false;
				boolean unitFlag=false;
					if(!validateOnAddEdit()){
					return;
				}
//					deleteBtn.setDisable(false);
					SalesProduct salesProduct = new SalesProduct();
					Product product = new Product();
					salesProduct.setProduct(product);
					for(Product p:productList) {
					if(prodTxt.getValue().contains(p.getProduct_name())&& Long.parseLong(hsnTxt.getText())==p.getHsnNo()) {
					salesProduct.getProduct().setId(p.getId());
					salesProduct.getProduct().setProduct_name(p.getProduct_name());
					salesProduct.setProd_name(p.getProduct_name());
					salesProduct.setGst(p.getGst());
					salesProduct.setHsnNo(Long.parseLong(hsnTxt.getText()));
					salesProduct.setCurrentStock(p.getQuantity());
					productFlag=true;
							}
						}
					if(!productFlag) {
						errorTip.setText("No Product found with specified name!");
						prodTxt.setTooltip(errorTip);
						errorTip.show(prodTxt,100,160);
						return;
					}
//					salesProduct.setProd_name(prodTxt.getValue());
//					if(!unitCombo.getId().contains(unitCombo.getText())) {
//						errorTip.setText("Unit Name not found!");
//						unitCombo.setTooltip(errorTip);
//						errorTip.show(unitCombo,300,160);
//						return;
//					}

					salesProduct.setUnit(unitCombo.getText());
					salesProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));

					String trunRateAmt=BigDecimal.valueOf((taxableAmt+(Double.parseDouble(discTxt.getText())))/salesProduct.getQuantity())
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();
//					System.out.println("Subtotal"+taxableAmt);

//					System.out.println(trunRateAmt);
					salesProduct.setRateString(trunRateAmt);
					salesProduct.setRate(Double.parseDouble(rateTxt.getText()));

					String trunDiscAmt=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();

					salesProduct.setDiscount(Double.parseDouble(discTxt.getText()));
					salesProduct.setDiscountString(trunDiscAmt);


						String cgstTxt=cGstTxt.getText().replace("(", "\n(");
						salesProduct.setCgst(cgstTxt);

//					if(iGstTxt.getText().contains("(")) {/
						String igstTxt=iGstTxt.getText().replace("(", "\n(");
						salesProduct.setIgst(igstTxt);
//					}
//					salesProduct.setSgst(gstRsTxt.getText());
//					if(gstRsTxt.getText().contains("(")) {
						String sgstTxt=gstRsTxt.getText().replace("(", "\n(");
						salesProduct.setSgst(sgstTxt);
//					}

//					salesProduct.setCess(cessTxt.getText());
					if(cessTxt.getText().contains("(")) {
						String cesssTxt=cessTxt.getText().replace("(", "\n(");
						salesProduct.setCess(cesssTxt);
					}
//					salesProduct.setGst(Double.parseDouble());
//					NumberFormat formatter = new DecimalFormat("#0.00");

					String trunTaxablAmt=BigDecimal.valueOf(taxableAmt)
				    .setScale(2, RoundingMode.HALF_UP)
				    .toPlainString();

					double taxAmt=BigDecimal.valueOf(taxableAmt)
							.setScale(2,RoundingMode.HALF_UP)
							.doubleValue();

					salesProduct.setSubTotal(taxAmt);
					salesProduct.setSubTotalString(trunTaxablAmt);

					salesProduct.setTotal(Double.parseDouble(totalTxt.getText()));
					String trunTotalAmt=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();
					salesProduct.setTotalString(trunTotalAmt);

					for(SalesProduct sp:prodData) {
						if(sp.getProd_name().equals(salesProduct.getProd_name())) {
//							dupliErrLbl.setText("Duplicate entries are not permitted");
//							gp.add(dupliErrLbl, 0, 7);
							errorTip.setText("Duplicate entries are not permitted");
							prodTxt.setTooltip(errorTip);
							errorTip.show(prodTxt,100,200);
							return;
						}
					}

					prodData.add(salesProduct);
					prodView.setItems(prodData);
					prodView.requestFocus();
					prodView.getSelectionModel().selectLast();
					
					cashCheck.requestFocus();
					grandTotal=grandTotal+salesProduct.getTotal();
					grTotalTxt.setText(String.format("%.2f",grandTotal));

					clearProductData();
					editItem.setDisable(false);
			}
		});

		
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				if(tg.getSelectedToggle()!=null) {
					String item=tg.getSelectedToggle().getUserData().toString();
					if(item.equals("Add Item")) {
						prodHB.getChildren().removeAll(editBtn,addMoreBtn);
						prodHB.getChildren().add(9,addMoreBtn);
						deleteBtn.setDisable(false);
						clearProductData();
					}else if(item.equals("Edit Item")) {
						deleteBtn.setDisable(true);
						SalesProduct salesProduct = prodView.getSelectionModel().getSelectedItem();
						int index = prodView.getSelectionModel().getSelectedIndex();
						prodTxt.setValue(salesProduct.getProd_name());
						unitCombo.setText(salesProduct.getUnit());
						hsnTxt.setText(String.valueOf(salesProduct.getHsnNo()));
						quantityTxt.setText(String.valueOf(salesProduct.getQuantity()));

						String trunRateAmt=BigDecimal.valueOf(salesProduct.getRate())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();

						rateTxt.setText(trunRateAmt);

						String trunDiscAmt=BigDecimal.valueOf(salesProduct.getDiscount())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
						discTxt.setText(trunDiscAmt);

						gstRsTxt.setText(String.valueOf(salesProduct.getSgst()));
						cGstTxt.setText(String.valueOf(salesProduct.getCgst()));
						iGstTxt.setText(String.valueOf(salesProduct.getIgst()));
						cessTxt.setText(String.valueOf(salesProduct.getCess()));

//						String trunTaxableAmt=BigDecimal.valueOf(salesProduct.getSubTotal())
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						taxableTxt.setText(trunTaxableAmt);

						String trunTotalAmt=BigDecimal.valueOf(salesProduct.getTotal())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
						totalTxt.setText(trunTotalAmt);

						grandTotal=grandTotal-salesProduct.getTotal();
						prodHB.getChildren().remove(addMoreBtn);
						prodHB.getChildren().add(9, editBtn);

						editBtn.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								// TODO Auto-generated method stub
//								clearLabels();
//								gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
								if(!validateOnAddEdit()){
									return;
								}

//								PurchaseProduct purchaseProduct1 = new PurchaseProduct();
								salesProduct.setProd_name(prodTxt.getValue());
								salesProduct.setUnit(unitCombo.getText());
								salesProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
								String trunRateAmt1=BigDecimal.valueOf((taxableAmt+(Double.parseDouble(discTxt.getText())))/salesProduct.getQuantity())
									    .setScale(2, RoundingMode.HALF_UP)
									    .toPlainString();
								salesProduct.setRateString(trunRateAmt1);

								salesProduct.setRate(Double.parseDouble(rateTxt.getText()));
								String trunDiscAmt1=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
										.setScale(3, RoundingMode.HALF_UP)
										.toPlainString();
								salesProduct.setDiscountString(trunDiscAmt1);

								salesProduct.setDiscount(Double.parseDouble(discTxt.getText()));
//								salesProduct.setGst(Double.parseDouble());

								String cgstTxt=cGstTxt.getText().replace("(", "\n(");
								salesProduct.setCgst(cgstTxt);

//							if(iGstTxt.getText().contains("(")) {/

								String igstTxt=iGstTxt.getText().replace("(", "\n(");
								salesProduct.setIgst(igstTxt);
//							}
//							salesProduct.setSgst(gstRsTxt.getText());
//							if(gstRsTxt.getText().contains("(")) {
								String sgstTxt=gstRsTxt.getText().replace("(", "\n(");
								salesProduct.setSgst(sgstTxt);
//							}

//							salesProduct.setCess(cessTxt.getText());
							if(cessTxt.getText().contains("(")) {
								String cesssTxt=cessTxt.getText().replace("(", "\n(");
								salesProduct.setCess(cesssTxt);
							}

								String trunTaxablAmt=BigDecimal.valueOf(taxableAmt)
								    .setScale(2, RoundingMode.HALF_UP)
								    .toPlainString();

									double taxAmt=BigDecimal.valueOf(taxableAmt)
											.setScale(2,RoundingMode.HALF_UP)
											.doubleValue();
									
								salesProduct.setSubTotal(taxAmt);
						salesProduct.setSubTotalString(trunTaxablAmt);

								String trunTotalAmt1=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
										.setScale(3, RoundingMode.HALF_UP)
										.toPlainString();
								salesProduct.setTotalString(trunTotalAmt1);
								salesProduct.setTotal(Double.parseDouble(totalTxt.getText()));

								prodData.set(index,salesProduct);
								cashCheck.requestFocus();
								grandTotal=0;
								for(SalesProduct p:prodData) {
									grandTotal=grandTotal+p.getTotal();
								}
								grTotalTxt.setText(String.format("%.2f",grandTotal));
								prodHB.getChildren().remove(editBtn);
								prodHB.getChildren().add(10, addMoreBtn);
								addItem.setSelected(true);
								clearProductData();
							}
						});

					}
				}
			}
		});





//		Label payTypeLbl =  new Label("Payment Type");
//		gp.add(payTypeLbl, 4, 10);

		HBox paymentBox= new HBox();
		grTotalTxt= new JFXTextField();
		grTotalTxt.setFocusTraversable(true);
		grTotalTxt.setLabelFloat(true);
		grTotalTxt.setEditable(false);
		grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
		grTotalTxt.setFocusColor(Color.TRANSPARENT);
		grTotalTxt.setPromptText("Grand Total");
		grTotalTxt.setStyle("-fx-font-size: 20px; -fx-font-weight:bold;");
		grTotalTxt.setText("00.00");
//		grTotalTxt.setMaxWidth(100);
		GridPane.setMargin(grTotalTxt, new Insets(20,-50,0,50));
		gp.add(grTotalTxt, 0, 10);
		
		salesManCombo= new JFXComboBox<>();
		salesManCombo.setLabelFloat(true);
		salesManCombo.setPromptText("Sales Man");
		salesManCombo.setStyle("-fx-font-size:14");
		salesManData.clear();
		List<SalesMan> saleManList= salesDAO.showSalesManList();
		for(SalesMan s:saleManList) {
			if(s.getStatus()!=0) {
				if(s.getSalesManName()!=null||!s.getSalesManName().equals("")) {
					salesManData.add(s.getSalesManName());	
				}
			}
		}
		salesManCombo.getStyleClass().add("jf-combo-box");
		salesManCombo.setItems(salesManData);
		salesManCombo.setValue(null);
		new AutoCompleteComboBoxListener<>(salesManCombo);
		
		GridPane.setMargin(salesManCombo, new Insets(0,-50,0,50));
		gp.add(salesManCombo, 0, 12);
//		payCombo.setLabelFloat(true);
//		payCombo.getItems().clear();
//		payCombo.getItems().addAll("Full Payment");
//		payCombo.setValue("Full Payment");
		//payCombo.getItems().addAll("Full Payment","Part Payment","UnPaid");
//		payCombo.getStyleClass().add("jf-combo-box");

//		payCombo.setPromptText("Payment Type");
//		GridPane.setMargin(payCombo, new Insets(20,-50,0,50));
//		payCombo.requestFocus();
//		payCombo= new JFXComboBox<>();
//		payCombo.setVisible(false);
//		gp.add(payCombo, 0, 11);
//		partPayTxt.setPromptText("Part Pay Amount");
//		partPayTxt.setStyle("-fx-font-size:14");
//		partPayTxt.setLabelFloat(true);
//		partPayTxt.setText("0");
//
//		partPayTxt.setVisible(false);
//		GridPane.setMargin(partPayTxt, new Insets(20,-50,0,50));
//		gp.add(partPayTxt, 0, 12);
//		HBox hb = new HBox();

//		payCombo.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
//				if(payCombo.getValue()!=null){
//				if(payCombo.getValue().equals("Full Payment")) {
//					partPayTxt.setVisible(false);
//					paymentBox.setVisible(true);
//					payModeLbl.setVisible(true);
//
//				}
//				else if(payCombo.getValue().equals("UnPaid")){
//					partPayTxt.setVisible(false);
//					paymentBox.setVisible(false);
//					payModeLbl.setVisible(false);
////					hb.setVisible(false);
//				}
//				else if(payCombo.getValue().equals("Part Payment")) {
////				gp.addRow(9, hb);
//				partPayTxt.setVisible(true);
//				paymentBox.setVisible(true);
//				payModeLbl.setVisible(true);
//				}
//			}}
//		});
//		Label payModeLbl = new Label("Payment Mode");
//		gp.add(payModeLbl, 4, 11);
//		VBox payModeHB= new VBox();

		
		payModeLbl.setStyle("-fx-font-size:14");
		GridPane.setMargin(payModeLbl, new Insets(-70,0,0,-50));
		gp.add(payModeLbl, 2, 10);
		
		cashCheck= new JFXCheckBox();
		cashCheck.setFocusTraversable(true);
		cashCheck.setText("Pay by Cash");
		cashAmtTxt= new JFXTextField();
		cashAmtTxt.setPromptText("Amount");

//		cashAmtTxt.setVisible(false);
//		cashAmtTxt.setMaxWidth(50);
//		cashAmtTxt.setLabelFloat(true);
		cashAmtTxt.setStyle("-fx-font-size:14");

		VBox cashBox= new VBox();
		cashBox.setSpacing(10);
//		cashBox.setMaxHeight(100);
		cashBox.setSpacing(10);
		cashBox.getChildren().add(cashCheck);
		
		bankCheck= new JFXCheckBox();
		bankCheck.setText("Pay by Cheque/DD");
		bankTxt= new JFXTextField();
		bankTxt.setPromptText("Bank Name");
//		bankTxt.setVisible(false);
		bankTxt.setStyle("-fx-font-size:14");

//		bankTxt.setMaxWidth(50);
//		bankTxt.setLabelFloat(true);/
		cheqTxt= new JFXTextField();
		cheqTxt.setPromptText("Cheque No/DD No");
		cheqTxt.setStyle("-fx-font-size:14");
//		cheqTxt.setVisible(false);
//		cheqTxt.setMaxWidth(50);
//		cheqTxt.setLabelFloat(true);
		bankAmtTxt= new JFXTextField();
		bankAmtTxt.setPromptText("Amount");
//		bankAmtTxt.setVisible(false);
//		bankAmtTxt.setLabelFloat(true);
		bankAmtTxt.setStyle("-fx-font-size:14");

		VBox bankBox= new VBox();
		bankBox.getChildren().addAll(bankCheck);
		bankBox.setSpacing(10);
		cardCheck= new JFXCheckBox();
		cardCheck.setText("Pay by Credit/Debit Card/NetBanking");
		transId= new JFXTextField();
		transId.setPromptText("Transaction ID");
		transId.setStyle("-fx-font-size:14");
//		transId.setVisible(false);
//		transId.setMaxWidth(50);
//		transId.setLabelFloat(true);
		 cardAmtTxt= new JFXTextField();
		cardAmtTxt.setPromptText("Amount");
		cardAmtTxt.setStyle("-fx-font-size:14");
//		cardAmtTxt.setVisible(false);
//		cardAmtTxt.setMaxWidth(50);
//		cardAmtTxt.setLabelFloat(true);

		VBox cardBox= new VBox();
		cardBox.getChildren().add(cardCheck);
		cardBox.setSpacing(10);
		voucherCheck= new JFXCheckBox();
		voucherCheck.setText("Pay by Voucher/Wallet");
		voucherTypeTxt= new JFXTextField();
		voucherTypeTxt.setPromptText("Voucher Code/Wallet Name");
//		voucherTypeTxt.setVisible(false);
		voucherTypeTxt.setStyle("-fx-font-size:14");
//		voucherTypeTxt.setMaxWidth(50);
//		voucherTypeTxt.setLabelFloat(true);
		vouchAmtTxt= new JFXTextField();
		vouchAmtTxt.setPromptText("Amount");
//		vouchAmtTxt.setVisible(false);
		vouchAmtTxt.setStyle("-fx-font-size:14");
//		vouchAmtTxt.setMaxWidth(50);
//		vouchAmtTxt.setLabelFloat(true);

		VBox vouchBox= new VBox();
		vouchBox.getChildren().addAll(voucherCheck);
		vouchBox.setSpacing(10);


		paymentBox.setSpacing(20);
		paymentBox.setMaxSize(180, 50);
		paymentBox.setStyle("-fx-border-style: solid;-fx-padding:15;-fx-border-width: 2;-fx-border-insets:5; -fx-border-radius: 5;");
		paymentBox.getChildren().addAll(cashBox,bankBox,cardBox,vouchBox);


//		payModeCombo.getItems().clear();
//		payModeCombo.getItems().addAll("Cash","Cheque","NEFT/RTGS","Card");
//		new AutoCompleteComboBoxListener<>(payModeCombo);
//		payModeCombo.setLabelFloat(true);
//		payModeCombo.setPromptText("Select Payment Mode");

		GridPane.setMargin(paymentBox, new Insets(-80,0,0,-50));
		gp.add(paymentBox, 2, 12);


		cashCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub

//				cashBox.getChildren().remove(cashAmtTxt);
				if(newValue) {
					cardBox.getChildren().remove(cardAmtTxt);
					vouchBox.getChildren().remove(vouchAmtTxt);
					bankBox.getChildren().remove(bankAmtTxt);

					if(bankCheck.isSelected()||cardCheck.isSelected()||voucherCheck.isSelected()) {
						cashBox.getChildren().add(cashAmtTxt);
					}

					boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);
					boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
					boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);

				}
				else {
					cashAmtTxt.clear();
					cashBox.getChildren().remove(cashAmtTxt);
					if(!(bankCheck.isSelected()&&cardCheck.isSelected()&&voucherCheck.isSelected())) {
						if(bankCheck.isSelected()&&!(cardCheck.isSelected()||voucherCheck.isSelected())) {
							bankAmtTxt.clear();
							bankBox.getChildren().remove(bankAmtTxt);
						}
						else if(cardCheck.isSelected()&&!(bankCheck.isSelected()||voucherCheck.isSelected())) {
							cardAmtTxt.clear();
							cardBox.getChildren().remove(cardAmtTxt);
						}

						else if(voucherCheck.isSelected()&&!(bankCheck.isSelected()||cardCheck.isSelected())) {
							vouchAmtTxt.clear();
							vouchBox.getChildren().remove(vouchAmtTxt);
						}
					}

//					cashAmtTxt.setVisible(false);
				}

			}
		});


//
		bankCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(newValue) {

				bankBox.getChildren().addAll(bankTxt,cheqTxt);
				if(cashCheck.isSelected()||cardCheck.isSelected()||voucherCheck.isSelected()) {
					bankBox.getChildren().add(bankAmtTxt);
//					cashBox.getChildren().add(cashAmtTxt);
//					cardBox.getChildren().add(cardAmtTxt);
//					vouchBox.getChildren().add(vouchAmtTxt);
				}

				cashBox.getChildren().remove(cashAmtTxt);
				cardBox.getChildren().remove(cardAmtTxt);
				vouchBox.getChildren().remove(vouchAmtTxt);

				boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
				boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);
				boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);


			}
			else{
					bankBox.getChildren().removeAll(bankTxt,cheqTxt,bankAmtTxt);
					bankTxt.clear();
					cheqTxt.clear();
					bankAmtTxt.clear();
					if(!(cashCheck.isSelected()&&cardCheck.isSelected()&&voucherCheck.isSelected())) {
						if(cashCheck.isSelected()&&!(cardCheck.isSelected()||voucherCheck.isSelected())) {
							cashAmtTxt.clear();
							cashBox.getChildren().remove(cashAmtTxt);
						}
						else if(cardCheck.isSelected()&&!(cashCheck.isSelected()||voucherCheck.isSelected())) {
							cardAmtTxt.clear();
							cardBox.getChildren().remove(cardAmtTxt);
						}

						else if(voucherCheck.isSelected()&&!(cashCheck.isSelected()||cardCheck.isSelected())) {
							vouchAmtTxt.clear();
							vouchBox.getChildren().remove(vouchAmtTxt);
						}
					}
				}
			}
		});

		cardCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub

			if(newValue) {
				cardBox.getChildren().add(transId);


					if(cashCheck.isSelected()||bankCheck.isSelected()||voucherCheck.isSelected()) {
//						bankBox.getChildren().add(bankAmtTxt);
//						cashBox.getChildren().add(cashAmtTxt);
						cardBox.getChildren().add(cardAmtTxt);
//						vouchBox.getChildren().add(vouchAmtTxt);
					}

					cashBox.getChildren().remove(cashAmtTxt);
					vouchBox.getChildren().remove(vouchAmtTxt);
					bankBox.getChildren().remove(bankAmtTxt);

					boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
					boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
					boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);



			}
			else {
					cardBox.getChildren().removeAll(transId,cardAmtTxt);
					transId.clear();
					cardAmtTxt.clear();

					if(!(cashCheck.isSelected()&&bankCheck.isSelected()&&voucherCheck.isSelected())) {
						if(cashCheck.isSelected()&&!(bankCheck.isSelected()||voucherCheck.isSelected())) {
							cashAmtTxt.clear();
							cashBox.getChildren().remove(cashAmtTxt);
						}
						else if(bankCheck.isSelected()&&!(cashCheck.isSelected()||voucherCheck.isSelected())) {
							bankAmtTxt.clear();
							bankBox.getChildren().remove(bankAmtTxt);
						}

						else if(voucherCheck.isSelected()&&!(cashCheck.isSelected()||bankCheck.isSelected())) {
							vouchBox.getChildren().remove(vouchAmtTxt);
							vouchAmtTxt.clear();
						}
					}

				}
			}
		});

		voucherCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub


				if(newValue) {
					vouchBox.getChildren().add(voucherTypeTxt);

					if(cashCheck.isSelected()||bankCheck.isSelected()||cardCheck.isSelected()) {
						vouchBox.getChildren().add(vouchAmtTxt);
					}

					cardBox.getChildren().remove(cardAmtTxt);
					bankBox.getChildren().remove(bankAmtTxt);
					cashBox.getChildren().remove(cashAmtTxt);

					boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
					boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
					boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);

//						bankBox.getChildren().add(bankAmtTxt);
//						cashBox.getChildren().add(cashAmtTxt);
//						cardBox.getChildren().add(cardAmtTxt);
//						vouchBox.getChildren().add(vouchAmtTxt);
//					}
				}
			else {
				vouchBox.getChildren().removeAll(voucherTypeTxt,vouchAmtTxt);
				voucherTypeTxt.clear();
				vouchAmtTxt.clear();
				if(!(cashCheck.isSelected()&&bankCheck.isSelected()&&cardCheck.isSelected())) {
					if(cashCheck.isSelected()&&!(bankCheck.isSelected()||cardCheck.isSelected())) {
						cashAmtTxt.clear();
						cashBox.getChildren().remove(cashAmtTxt);
					}
					else if(bankCheck.isSelected()&&!(cashCheck.isSelected()||cardCheck.isSelected())) {
						bankAmtTxt.clear();
						bankBox.getChildren().remove(bankAmtTxt);
					}

					else if(cardCheck.isSelected()&&!(cashCheck.isSelected()||bankCheck.isSelected())) {
						cardAmtTxt.clear();
						cardBox.getChildren().remove(cardAmtTxt);
					}
				}
				}
			}
		});
//		Label cheqLbl = new Label("Cheque No/Transaction Id/Status");

//		hb.getChildren().addAll(bankTxt,cheqTxt);
//		hb.setMaxWidth(200);
//		hb.setSpacing(20);
//		hb.setVisible(false);
//		gp.add(vb, 5, 11);
		JFXButton submitBtn = new JFXButton(" Submit ");
		gp.add(submitBtn, 2,13);
//

		quantityTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();

				if(!validateProductControls()){
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
//					gp.add(quantityErrLbl, 1, 7);
					return;
				}

				for(Product p:productList) {
					if(prodTxt.getValue()!=null) {
					if(prodTxt.getValue().contains(p.getProduct_name())) {
						if(p.getQuantity()<Integer.parseInt(newValue)) {
//							System.out.println(p.getQuantity());
							errorTip.setText("Quantity must not exceed current stock");
							quantityTxt.setTooltip(errorTip);
							errorTip.show(quantityTxt,400,150);
							addMoreBtn.setDisable(true);
							editBtn.setDisable(true);
							return;
						}
					}
					}
				}
//				totErrLbl.setText("");
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if(!newValue.isEmpty()){
					if(!(rateTxt.getText().equals("")||discTxt.getText().equals(""))){
					double total = (Double.parseDouble(newValue)*Double.parseDouble(rateTxt.getText()))-Double.parseDouble(discTxt.getText());
					
//					double subTotal = total*(100/(100+gstRate));
					double subTotal=total/(100+gstRate)*100;
					gstAmt=total-subTotal;
					double halfGst=gstAmt/2;

					if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {

						cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
						gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
					}
					else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
						iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
//						double subTotal1=subTotal*(100/(100+cessRate));
//						cessAmt=subTotal-subTotal1;
////						subTotalsub=subTotal+subTotal1;
//						cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
////						subTotal=subTotal+cess;
						cessTxt.setText("0.0");
					}
					taxableAmt=subTotal;
					totalTxt.setText(String.format("%.2f",total));}
					else{
						totalTxt.setText("NaN");
					}
			}
				else{
					quantityTxt.setText(quantityTxt.getPromptText());
				}
				}

		});

		rateTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
//
				errorTip.hide();
				if(!validateProductControls()){
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					return;
				}

				if(!quantityTxt.getText().isEmpty()) {
				for(Product p:productList) {
					if(prodTxt.getValue()!=null) {
					if(prodTxt.getValue().contains(p.getProduct_name())) {

						if(p.getQuantity()<Integer.parseInt(quantityTxt.getText())) {
//							System.out.println(p.getQuantity());
							errorTip.setText("Quantity must not exceed current stock");
							quantityTxt.setTooltip(errorTip);
							errorTip.show(quantityTxt,400,150);
							addMoreBtn.setDisable(true);
							editBtn.setDisable(true);
							return;
						}
					}
				}
				}
				}

				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if(!newValue.equals("")){
//					if(newValue.matches(arg0))
					if(!(quantityTxt.getText().equals("")||discTxt.getText().equals(""))){
						double total = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(newValue))-Double.parseDouble(discTxt.getText());

						double subTotal =  total/(100+gstRate)*100;
						gstAmt=total-subTotal;

						double halfGst=gstAmt/2;
						if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
							cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
							gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
						}
						else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
							iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
//							double subTotal1=subTotal*(100/(100+cessRate));
//							cessAmt=subTotal-subTotal1;
////							subTotal=subTotal+subTotal1;
//							cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
//							subTotal=subTotal+cess;
							cessTxt.setText("0.0");
						}
						taxableAmt=subTotal;
				totalTxt.setText(String.format("%.2f",total));}
					else{
						totalTxt.setText("NaN");
					}
			}else
				rateTxt.setText(rateTxt.getPromptText());
				}
		});

		discTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//				 TODO Auto-generated method stub
				errorTip.hide();
				if(!validateProductControls()){
//					System.out.println("Reched inside validation");
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					return;
				}
				if(!quantityTxt.getText().isEmpty()) {
				for(Product p:productList) {
					if(prodTxt.getValue()!=null) {
						if(prodTxt.getValue().contains(p.getProduct_name())) {
							if(p.getQuantity()<Integer.parseInt(quantityTxt.getText())) {
//							System.out.println(p.getQuantity());
							errorTip.setText("Quantity must not exceed current stock");
							quantityTxt.setTooltip(errorTip);
							errorTip.show(quantityTxt,400,150);
							addMoreBtn.setDisable(true);
							editBtn.setDisable(true);

							return;
							}
						
					}
					}
				}
				}
//				totErrLbl.setText("");
//				System.out.println("Oustdie validation");
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if(!newValue.equals("")){
					if(!(quantityTxt.getText().equals("") || rateTxt.getText().equals(""))){
					double total = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(rateTxt.getText()))-Double.parseDouble(newValue);
					double subTotal = total/(100+gstRate)*100;

//					double subTotal = total*(100/(100+gstRate));
					gstAmt=total-subTotal;
					double halfGst=gstAmt/2;

					if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
						cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
						gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
					}
					else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
						iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
//						double subTotal1=subTotal*(100/(100+cessRate));
//						cessAmt=subTotal-subTotal1;
////						subTotal=subTotal+subTotal1;
//						cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
////						subTotal=subTotal+cess;
						cessTxt.setText("0.0");
					}
					taxableAmt=subTotal;
					totalTxt.setText(String.format("%.2f",total));}
					else{
						totalTxt.setText("NaN");
					}
			}else{
						discTxt.setText(discTxt.getPromptText());	}
				}

		});
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;
			@Override
			public void handle(ActionEvent arg0) {
				if(!validateonSaleSubmitButton()){
					return;
				}
				SalesEntry salesEntry = new SalesEntry();
				salesEntry.setDeliveryMemo(new DeliveryMemo());
//				salesEntry.setEntryId(entry_id);
				salesEntry.setInvoice_no(invoiceId);
				Customer customer=new Customer();
				salesEntry.setCustomer(customer);
				salesEntry.setSalesMan(new SalesMan());
			
				if(salesManCombo.getValue()!=null) {
					for(SalesMan s:saleManList) {
						if(s.getSalesManName().equals(salesManCombo.getValue())) {
							salesEntry.getSalesMan().setId(s.getId());
						}
					}
				}else {
					salesEntry.getSalesMan().setSalesManName(null);
				}

				if(custCombo.getValue()!=null) {
				for(Customer c:custList){
					if(custCombo.getValue().equals(c.getFull_name())&& prmConTxt.getText().equals(c.getContact())) {
						salesEntry.getCustomer().setCust_id(c.getCust_id());
					}
					}
				}
				try {
					LocalDate localDate = invoicDatePick.getValue();
					SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
					Date utilDate = new Date(sd.parse(localDate.toString()).getTime());
					salesEntry.setEntry_date(utilDate);
//					System.out.println(utilDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
//				salesEntry.setCustName(fullNameTxt.getText());
				salesEntry.getCustomer().setFull_name(custCombo.getValue());
				salesEntry.getCustomer().setAddress(address.getText());
				salesEntry.getCustomer().setState(stateCombo.getValue());
				salesEntry.getCustomer().setGstin(gstTxt.getText());
				salesEntry.getCustomer().setContact(prmConTxt.getText());

//				salesEntry.setPaid_amount(Double.parseDouble(partPayTxt.getText()));
				salesEntry.setSub_total(Double.parseDouble(grTotalTxt.getText()));
				salesEntry.setTotal(Double.parseDouble(grTotalTxt.getText()));
//				salesEntry.setPayment_type(payCombo.getValue());


                //work from here fetch value for payment modes
				PaymentMode paymentMode= new PaymentMode();
				salesEntry.setPaymentMode(paymentMode);
//				if(salesEntry.getPayment_type().equals("Full Payment")) {
					if(cashCheck.isSelected()) {
						salesEntry.getPaymentMode().setCashMode(cashCheck.getText());
						if(!cashAmtTxt.getText().isEmpty()) {
						salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(cashAmtTxt.getText()));
						}
						else {
						salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(grTotalTxt.getText()));
						}
					}
					if(bankCheck.isSelected()) {
						salesEntry.getPaymentMode().setBankMode(bankCheck.getText());
						salesEntry.getPaymentMode().setBankName(bankTxt.getText());
						salesEntry.getPaymentMode().setChequeNo(cheqTxt.getText());

						if(!bankAmtTxt.getText().isEmpty()) {
						salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(bankAmtTxt.getText()));
						}
						else {
							salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(grTotalTxt.getText()));
						}
					}
					if(cardCheck.isSelected()) {
						salesEntry.getPaymentMode().setCardMode(cardCheck.getText());
						salesEntry.getPaymentMode().setTransId(transId.getText());
						if(!cardAmtTxt.getText().isEmpty()) {
							salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(cardAmtTxt.getText()));
						}
						else {
							salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(grTotalTxt.getText()));
						}

					}
					if(voucherCheck.isSelected()) {
						salesEntry.getPaymentMode().setVoucherMode(voucherCheck.getText());
						salesEntry.getPaymentMode().setVoucherWalletCode(voucherTypeTxt.getText());
						if(!vouchAmtTxt.getText().isEmpty()) {
							salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(vouchAmtTxt.getText()));
						}
						else {
							salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(grTotalTxt.getText()));
						}

					}
//				}else if(salesEntry.getPayment_type().equals("Part Payment")) {
					if(cashCheck.isSelected()) {
						salesEntry.getPaymentMode().setCashMode(cashCheck.getText());
						if(!cashAmtTxt.getText().isEmpty()) {
						salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(cashAmtTxt.getText()));
						}
//						else {
////						salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(partPayTxt.getText()));
//						}
					}
					if(bankCheck.isSelected()) {
						salesEntry.getPaymentMode().setBankMode(bankCheck.getText());
						salesEntry.getPaymentMode().setBankName(bankTxt.getText());
						salesEntry.getPaymentMode().setChequeNo(cheqTxt.getText());

						if(!bankAmtTxt.getText().isEmpty()) {
						salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(bankAmtTxt.getText()));
						}
//						else {
//							salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(partPayTxt.getText()));
//						}
					}
					if(cardCheck.isSelected()) {
						salesEntry.getPaymentMode().setCardMode(cardCheck.getText());
						salesEntry.getPaymentMode().setTransId(transId.getText());
						if(!cardAmtTxt.getText().isEmpty()) {
							salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(cardAmtTxt.getText()));
						}
//						else {
//							salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(partPayTxt.getText()));
//						}

					}
					if(voucherCheck.isSelected()) {
						salesEntry.getPaymentMode().setVoucherMode(voucherCheck.getText());
						salesEntry.getPaymentMode().setVoucherWalletCode(voucherTypeTxt.getText());
						if(!vouchAmtTxt.getText().isEmpty()) {
							salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(vouchAmtTxt.getText()));
						}
//						else {
//							salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(partPayTxt.getText()));
//						}

					}
//				}

				result=salesDAO.createNewInvoice(salesEntry, prodData);
				if(result) {
					Alert alert = new Alert(AlertType.INFORMATION, "Sales Invoice Generated");
					alert.setTitle("Success Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
					Main.dialog.close();
					try {
						generateInvoice.showReport(salesEntry);
					} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
//					clearInvoiceControls();
					showInvoice(anchorPane);
					Main.salesView.setItems(saleSortedData);
					Main.salesView.requestFocus();
					Main.salesView.getSelectionModel().selectLast();
					Main.salesView.getFocusModel().focusNext();

					Main.salesView.setMinSize(900, 500);
					anchorPane.getChildren().set(0,Main.salesView);
					try {
						
						SmsSender.sendSms(salesEntry);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


					}
					else {
						Alert alert = new Alert(AlertType.INFORMATION, "Error occurred while generating sales invoice!Please check database connection");
						alert.setTitle("Error Message");
						alert.setHeaderText("HI");
						alert.showAndWait();
					}

				clearProductData();
//				clearLabels();

			}
		});

		gp.addEventFilter(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode()==KeyCode.INSERT) {
				if(prodHB.getChildren().contains(addMoreBtn)) {
					addMoreBtn.fire();
					e.consume();
				}else if(prodHB.getChildren().contains(editBtn)) {
					editBtn.fire();
					e.consume();
				}

			}else if(e.getCode()==KeyCode.ENTER) {
				submitBtn.fire();
				e.consume();
			}
		});

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				stateCombo.requestFocus();
			}
		});

		return gp;

		}

	public TableView<SalesEntry> showInvoice(AnchorPane anchorPane){
		TableView<SalesEntry> salesView = new TableView<SalesEntry>();
		salesData.clear();
		TableColumn<SalesEntry, SalesEntry> invoiceCol = new TableColumn<SalesEntry, SalesEntry>("Invoice No");
		invoiceCol.setCellValueFactory(new PropertyValueFactory<>("invoice"));
		invoiceCol.setPrefWidth(200);
		invoiceCol.setStyle("-fx-alignment:center");
//		invoiceCol.setCellFactory(e->new TableCell<SalesEntry,SalesEntry>(){
//			@Override
//		    protected void updateItem(SalesEntry salesEntry,boolean empty) {
//				if(salesEntry==null) {
//					setGraphic(null);
//					return;
//				}else {
//
//					long length= String.valueOf(salesEntry.getInvoice_no()).length();
//
//					String invoiceNo=String.format("SI"+"%0"+(6-length)+"d",salesEntry.getInvoice_no());
//					setText(invoiceNo);
//				}
//			}
//		});

		TableColumn<SalesEntry, SalesEntry> custmerCol=new TableColumn<SalesEntry, SalesEntry>("Customer Name");
		custmerCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
		custmerCol.setPrefWidth(400);
		custmerCol.setStyle("-fx-alignment:center");
//		custmerCol.setCellFactory(e->new TableCell<SalesEntry,SalesEntry>(){
//			@Override
//		    protected void updateItem(SalesEntry salesEntry,boolean empty) {
//				if(salesEntry==null) {
//					setGraphic(null);
//					return;
//				}
//				else {
//					String custName=salesEntry.getCustName();
//				}
//			}
//		});

		TableColumn<SalesEntry, Date> dateCol = new TableColumn<SalesEntry, Date>("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("entry_date"));
		dateCol.setPrefWidth(150);
		dateCol.setStyle("-fx-alignment:center");
		TableColumn<SalesEntry, Double> amountCol = new TableColumn<SalesEntry, Double>("Amount");
		amountCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
		amountCol.setPrefWidth(150);
		amountCol.setStyle("-fx-alignment:center");
		TableColumn<SalesEntry, SalesEntry> actionCol = new TableColumn<SalesEntry, SalesEntry>("Action");
		actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
		actionCol.setMinWidth(140);
		actionCol.setStyle("-fx-alignment:center");
		actionCol.setCellFactory(e-> new TableCell<SalesEntry,SalesEntry>(){
			JFXButton delBtn = new JFXButton("Delete");
			boolean result1;
			@Override
		    protected void updateItem(SalesEntry salesEntry,boolean empty) {
				delBtn.getStyleClass().add("del-btn");
				if(salesEntry==null) {
					setGraphic(null);
					return;
				}else {
					setGraphic(delBtn);

					salesView.setOnKeyPressed( new EventHandler<KeyEvent>()
					{
					  @Override
					  public void handle( final KeyEvent keyEvent )
					  {
						 SalesEntry salesEntry=getTableView().getSelectionModel().getSelectedItem();

					    if ( salesEntry != null )
					    {
					      if ( keyEvent.getCode()==KeyCode.DELETE )
					      {
					    	  Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
								alert.setTitle("Confirmation");
								Optional<ButtonType> result= alert.showAndWait();
								if(result.get()==ButtonType.OK){
									getTableView().getSortOrder().remove(salesEntry);
									result1= salesDAO.deleteSalesEntry(salesEntry);
									showInvoice(anchorPane);
									Main.salesView.setItems(saleSortedData);
									Main.salesView.requestFocus();
									Main.salesView.getSelectionModel().selectLast();
									Main.salesView.getFocusModel().focusNext();

									Main.salesView.setMinSize(800, 500);
									anchorPane.getChildren().set(0,Main.salesView);
									getTableView().refresh();
									}


					      }}}
					  });
					delBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
//							alert.setHeaderText("HI");
							Optional<ButtonType> result= alert.showAndWait();
							if(result.get()==ButtonType.OK){
//								getTableView().getItems().remove(salesEntry);
								getTableView().getSortOrder().remove(salesEntry);
								result1= salesDAO.deleteSalesEntry(salesEntry);
								showInvoice(anchorPane);
								Main.salesView.setItems(saleSortedData);
								Main.salesView.requestFocus();
								Main.salesView.getSelectionModel().selectLast();
								Main.salesView.getFocusModel().focusNext();

								Main.salesView.setMinSize(800, 500);
								anchorPane.getChildren().set(0,Main.salesView);
								getTableView().refresh();
//								hb.getChildren().clear();s
								}
						}

					});
				}
			}
		});

		salesView.getColumns().addAll(invoiceCol,custmerCol,dateCol,amountCol);
		salesView.getColumns().addListener(new ListChangeListener() {
			public boolean suspended;

			@Override
			public void onChanged(Change c) {
				// TODO Auto-generated method stub
				c.next();
				if (c.wasReplaced() && !suspended) {
					this.suspended = true;
					salesView.getColumns().setAll(invoiceCol,custmerCol,dateCol,amountCol);
					this.suspended = false;
				}
			}

		});

		List<SalesEntry> salesList=salesDAO.showInvoice();
		Iterator<SalesEntry> itr= salesList.iterator();
		while(itr.hasNext()) {
			SalesEntry salesEntry=itr.next();
			String custName=salesEntry.getCustomer().getFull_name();
			custName=custName.replace("null", "");
//			custName=custName.replace("null", "");
			salesEntry.setCustName(custName);
			long length= String.valueOf(salesEntry.getInvoice_no()).length();
			String invoiceNo=String.format("SI"+"%0"+(6-length)+"d",salesEntry.getInvoice_no());
//			System.out.println(salesEntry.getPayment_mode());
			String total=BigDecimal.valueOf(salesEntry.getTotal())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();
			salesData.add(new SalesEntry(salesEntry.getId(),salesEntry.getInvoice_no(),salesEntry.getCustName(),salesEntry.getEntry_date(),salesEntry.getTotal(),invoiceNo,salesEntry.getPayment_type(),salesEntry.getPaymentMode(),salesEntry.getBank_name(),salesEntry.getCheque_no(),salesEntry.getPaid_amount(),salesEntry.getCustomer(),total,salesEntry.getSalesMan().getSalesManName()));
		}

		saleFilteredData = new FilteredList<>(salesData,p->true);



		saleSortedData =new SortedList<>(saleFilteredData);
		saleSortedData.comparatorProperty().bind(salesView.comparatorProperty());

//		salesView.setItems(sortedData);
		salesView.setItems(saleSortedData);
		return salesView;

	}

	public GridPane updateSalesEntry(SalesEntry salesEntry,AnchorPane anchorPane) {
		
//		clearInvoiceControls();
		errorTip.setAutoHide(true);
		GridPane gp=new GridPane();
		grandTotal=0;
		cessAmt=0;
		taxableAmt=0;
		gstAmt=0;
		List<SalesProduct> deletedSalesList= new ArrayList<>();
		gp.getStyleClass().add("grid");
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setGridLinesVisible(false);

		Label titleLabel = new Label(" Update Sales Invoice");
		GridPane.setMargin(titleLabel, new Insets(0,-400,0,400));
		gp.add(titleLabel, 0, 0);

		Label custCreatLbl=new Label("Customer Details");
//		GridPane.setMargin(custCreatLbl, new Insets(0,-100,0,100));
		gp.add(custCreatLbl, 1, 1);

//		long entry_id=

		long invoiceId=salesEntry.getInvoice_no();
		int length=String.valueOf(invoiceId).length();
		Label saleInvoiceLbl =new Label();
		saleInvoiceLbl.setText(String.format("SI"+"%0"+(6-length)+"d", (invoiceId)));
		GridPane.setMargin(saleInvoiceLbl, new Insets(0,0,0,-200));
		gp.add(saleInvoiceLbl, 6, 0);

//		invoicDatePick.setValue(LocalDate.now());
		invoicDatePick= new JFXDatePicker();
//		invoicDatePick.setStyle("-fx-background-color:red");
		invoicDatePick.setPromptText("Entry Date");
		invoicDatePick.setMaxWidth(150);
		invoicDatePick.setEditable(true);
		LocalDate ld = LocalDate.parse(salesEntry.getEntry_date().toString());
//		invoicDatePick.setStyle("-fx-font-size:13");
		invoicDatePick.getStyleClass().add("date-pick");
		invoicDatePick.setShowWeekNumbers(false);
		invoicDatePick.setValue(ld);
//		GridPane.setMargin(custCombo, new Insets(0,0,0,20));
		GridPane.setMargin(invoicDatePick, new Insets(0,-50,0,0));
//		invoicDatePick.setMinWidth(100);
		KeyEventHandler.dateKeyEvent(invoicDatePick);
		gp.add(invoicDatePick, 0, 1);

		HBox custComboHB= new HBox();

		custCombo= new JFXComboBox<>();
		custCombo.setLabelFloat(true);
		custCombo.setStyle("-fx-font-size:12");
//		System.out.println("State"+salesEntry.getCustomer().getState());
		custCombo.setPromptText("Customer Name");
		custCombo.getStyleClass().add("jf-combo-box");
//		GridPane.setMargin(custCombo, new Insets(0,0,0,20));
//		custCombo.setEditable(true);
//		gp.add(custCombo, 0, 2);

		ObservableList<String> custObsData = FXCollections.observableArrayList();
		List<Customer> custList= salesDAO.getCustomerNames();
		Iterator<Customer> itr= custList.iterator();
		while(itr.hasNext()) {
			Customer customer=itr.next();
			custObsData.add(customer.getFull_name().replaceAll("null", ""));
		
		}
		
		
		custCombo.setItems(custObsData);
		new AutoCompleteComboBoxListener<>(custCombo);
		

		
//		custCombo.setConverter(new StringConverter<String>() {
//
//			@Override
//			public String toString(String object) {
//				// TODO Auto-generated method stub
//				if(object==null)return null;
//
//				return object.toString();
//			}
//
//			@Override
//			public String fromString(String string) {
//				// TODO Auto-generated method stub
////				Customer customer=new Customer();
////				for(String cust:custCombo.getItems()) {
////					cust
////				}
//				return string;
//			}
//		});

//		address.clear();
		address= new JFXTextField();
		address.setPromptText("Address");
		address.setText(salesEntry.getCustomer().getAddress().replaceAll("null", ""));
		address.setStyle("-fx-font-size:12");
		address.setLabelFloat(true);
		address.setMaxWidth(80);

//		prmConTxt.clear();
		prmConTxt= new JFXTextField();
		prmConTxt.setPromptText("Contact Number");
		prmConTxt.setText(salesEntry.getCustomer().getContact().replaceAll("null", ""));
		prmConTxt.setStyle("-fx-font-size:12");
		prmConTxt.setLabelFloat(true);
		prmConTxt.setMaxWidth(80);

//		stateCombo.getItems().clear();
		stateCombo= new JFXComboBox<>();
		stateCombo.setPromptText("State");
		stateCombo.setStyle("-fx-font-size:12");
		stateCombo.setLabelFloat(true);
//		stateCombo.setMaxWidth(10);
		stateCombo.setItems(fillStateCombo());
		stateCombo.setValue(salesEntry.getCustomer().getState());
		stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
//				errorTip.hide();
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
				if(s!=null) {
					stateCombo.requestFocus();
					stateCombo.getSelectionModel().select(s);

				}
			}
		});

//		gstTxt.clear();
		gstTxt= new JFXTextField();
		gstTxt.setPromptText("GSTIN");
		gstTxt.setText(salesEntry.getCustomer().getGstin().replaceAll("null", ""));
		gstTxt.setLabelFloat(true);
		gstTxt.setMaxWidth(100);
		gstTxt.setStyle("-fx-font-size:12");
		
		


		custComboHB.getChildren().addAll(custCombo,address,stateCombo,prmConTxt,gstTxt);
		custComboHB.setSpacing(20);
		GridPane.setMargin(custComboHB, new Insets(0,0,0,-200));
		gp.add(custComboHB, 2, 2);
		
		custCombo.setValue(salesEntry.getCustomer().getFull_name().replaceAll("null", ""));
		
		custCombo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
//				System.out.println("Reached outside");
				for(Customer c:custList) {
//					System.out.println("Reached");
					if(custCombo.getValue()!=null) {
					if(custCombo.getValue().equals(c.getFull_name())) {
						address.setText(c.getAddress());
						stateCombo.setValue(c.getState());
						prmConTxt.setText(c.getContact());
						gstTxt.setText(c.getGstin());
					}
					}
				}
			}
		});
		
		
		
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
		     public DateCell call(final DatePicker datePicker) {
		         return new DateCell() {
		             @Override public void updateItem(LocalDate item, boolean empty) {
//		                 super.updateItem(item, empty);


		                 if (item.isAfter(LocalDate.now())) {
		                     // Tomorrow is too soon.
		                     setDisable(true);
		                 }
		             }
		         };
		     }
		 };
		 invoicDatePick.setDayCellFactory(dayCellFactory);

		HBox prodHB = new HBox();

		Label addProdLbl =  new Label("Add Product");
		GridPane.setMargin(addProdLbl, new Insets(0,-50,0,0));
		gp.add(addProdLbl, 0, 4);
//		Label prodLbl = new Label("Product");
//		gp.add(prodLbl, 0, 5);
		prodTxt= new JFXComboBox<>();
		prodTxt.setPromptText("Select Product");
		prodTxt.setStyle("-fx-font-size:12");
		prodTxt.getStyleClass().add("jf-combo-box");
//		new AutoCompleteComboBoxListener<>(prodTxt);
//		prodTxt.setValue(salesEntry);
		prodTxt.setLabelFloat(true);
		prodTxt.setEditable(true);
		prodTxt.setMaxWidth(100);

		prodTxt.getItems().clear();
		prodList.clear();

		productList=goodsDAO.showProductList();

		for(Product p:productList) {
			if(p.getCategory()!=null) {
				prodList.add(p.getProduct_name()+"        "+p.getCategory().replaceAll("null", "")+"("+p.getSubGroup().replaceAll("null", "")+")");
				}
		}

		if(!prodList.isEmpty()) {
			prodTxt.getItems().addAll(prodList);
		}

//		prodTxt.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent event) {
//				// TODO Auto-generated method stub
////				errorTip.hide();
//				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), prodTxt.getValue(), prodTxt.getItems());
//				if(s!=null) {
//					prodTxt.requestFocus();
//					prodTxt.getSelectionModel().select(s);
//
//				}
//			}
//		});

		new AutoCompleteComboBoxListener<>(prodTxt);

//		prodTxt.setConverter(new StringConverter<String>() {
//
//			@Override
//			public String toString(String object) {
//				// TODO Auto-generated method stub
//				if(object==null)return null;
//
//				return object.toString();
//			}
//
//			@Override
//			public String fromString(String string) {
//				// TODO Auto-generated method stub
////				Customer customer=new Customer();
////				for(String cust:custCombo.getItems()) {
////					cust
////				}
//				return string;
//			}
//		});

//		hsnTxt.clear();
		hsnTxt= new JFXTextField();
		hsnTxt.setPromptText("HSN/SAC");
		hsnTxt.setLabelFloat(true);
		hsnTxt.setEditable(false);
		hsnTxt.setStyle("-fx-font-size:12");

//		unitCombo.setLabelFloat(true);
		unitCombo= new JFXTextField();
		unitCombo.setPromptText("Select Unit");
		unitCombo.setStyle("-fx-font-size:12");
		unitCombo.setMaxWidth(80);
		unitCombo.setEditable(false);
//		unitCombo.getId().addAll(goodsController.fillUnitList());
//		new AutoCompleteComboBoxListener<>(unitCombo);
//		unitCombo.setConverter(new StringConverter<String>() {
//
//			@Override
//			public String toString(String object) {
//				// TODO Auto-generated method stub
//				if(object==null)return null;
//
//				return object.toString();
//			}
//
//			@Override
//			public String fromString(String string) {
//				// TODO Auto-generated method stub
////				Customer customer=new Customer();
////				for(String cust:custCombo.getItems()) {
////					cust
////				}
//				return string;
//			}
//		});
		
		quantityTxt= new JFXTextField();
		quantityTxt.setPromptText("Quantity");
		quantityTxt.setStyle("-fx-font-size:12");
		quantityTxt.setText("0");
		quantityTxt.setLabelFloat(true);
		quantityTxt.setMaxWidth(50);
		
		rateTxt= new JFXTextField();
		rateTxt.setLabelFloat(true);
		rateTxt.setMaxWidth(50);
		rateTxt.setStyle("-fx-font-size:12");
		rateTxt.setPromptText("Rate");
		rateTxt.setText("0.00");
		
		discTxt= new JFXTextField();
		discTxt.setMaxWidth(50);
		discTxt.setLabelFloat(true);
		discTxt.setStyle("-fx-font-size:12");
		discTxt.setPromptText("Discount");
		discTxt.setText("0.0");

//		taxableTxt.setPromptText("Taxable"+"\n Amt");
//		taxableTxt.setText("0");
//		taxableTxt.setLabelFloat(true);
//		taxableTxt.setMaxWidth(50);
//		taxableTxt.setEditable(false);
//		taxableTxt.setStyle("-fx-font-size:12");
		
		cGstTxt= new JFXTextField();
		cGstTxt.clear();
		cGstTxt.setPromptText("CGST");
		cGstTxt.setMaxWidth(50);
		cGstTxt.setEditable(false);
		cGstTxt.setStyle("-fx-font-size:12");
		cGstTxt.setLabelFloat(true);

//		gstRsTxt.clear();
		gstRsTxt= new JFXTextField();
		gstRsTxt.setPromptText("SGST");
		gstRsTxt.setMaxWidth(50);
		gstRsTxt.setEditable(false);
		gstRsTxt.setStyle("-fx-font-size:12");
		gstRsTxt.setLabelFloat(true);

//		iGstTxt.clear();
		iGstTxt= new JFXTextField();
		iGstTxt.setPromptText("IGST");
		iGstTxt.setMaxWidth(50);
		iGstTxt.setEditable(false);
		iGstTxt.setStyle("-fx-font-size:12");
		iGstTxt.setLabelFloat(true);

//		cessTxt.clear();
		cessTxt= new JFXTextField();
		cessTxt.setPromptText("Cess");
		cessTxt.setMaxWidth(50);
		cessTxt.setEditable(false);
		cessTxt.setStyle("-fx-font-size:12");
		cessTxt.setLabelFloat(true);

		totalTxt= new JFXTextField();
		totalTxt.setEditable(false);
		totalTxt.setPromptText("Total");
		totalTxt.setText("0.00");
		totalTxt.setLabelFloat(true);
		totalTxt.setStyle("-fx-font-size: 15px;-fx-font-weight:bold");
		totalTxt.setUnFocusColor(Color.TRANSPARENT);
		totalTxt.setMaxWidth(100);
		HBox.setMargin(totalTxt, new Insets(0, 0, 20, 20));


		stateCombo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				String state=stateCombo.getSelectionModel().getSelectedItem();
				if(state!=null) {
					prodTxt.setDisable(false);
				if(state.contains("Maharashtra")) {
					prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
					prodHB.getChildren().add(6, cGstTxt);
					prodHB.getChildren().add(7,gstRsTxt);
					HBox.setMargin(gstRsTxt, new Insets(0,-20,0,0));
//					GridPane.setMargin(prodHB, new Insets(0,0,0,50));
//					gp.setPadding(new Insets(10,10,10,10));
				}
				else {
					prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
					prodHB.getChildren().add(6,iGstTxt);
					prodHB.getChildren().add(7,cessTxt);
					HBox.setMargin(cessTxt, new Insets(0,-20,0,0));
//					GridPane.setMargin(prodHB, new Insets(0,0,0,50));
//					HBox.setMargin(totalTxt, new Insets(0,5,0,-50));
				}
			}
			}
		});

		JFXButton addMoreBtn = new JFXButton(" Add ");
		addMoreBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");
		addMoreBtn.setMaxWidth(80);
		addMoreBtn.setDisable(true);

		JFXButton editBtn=new JFXButton("Edit");
		editBtn.setMaxWidth(80);
		editBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");

		RadioButton addItem = new RadioButton("Add Item");
		addItem.setUserData("Add Item");
		addItem.setSelected(true);
		RadioButton editItem =new RadioButton("Edit Item");
		editItem.setUserData("Edit Item");
//		editItem.setDisable(true);
		ToggleGroup tg = new ToggleGroup();
		addItem.setToggleGroup(tg);
		editItem.setToggleGroup(tg);
		VBox vb1 = new VBox();
		vb1.setStyle("-fx-font-size:15px; -fx-font-weight:bold;");
		vb1.getChildren().addAll(addItem,editItem);
		HBox.setMargin(vb1, new Insets(0,0,0,20));
		prodHB.getChildren().addAll(prodTxt,hsnTxt,unitCombo,quantityTxt,rateTxt,discTxt,totalTxt,addMoreBtn,vb1);

		prodHB.setSpacing(20);
		GridPane.setMargin(prodHB, new Insets(0,-50,0,0));
		gp.add(prodHB, 0, 6,8,1);

		String state=stateCombo.getValue();
		if(state!=null) {
			if(state.contains("Maharashtra")){
				prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
				prodHB.getChildren().add(6, cGstTxt);
				prodHB.getChildren().add(7,gstRsTxt);
				HBox.setMargin(gstRsTxt, new Insets(0,-20,0,0));
			}else {
				prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
				prodHB.getChildren().add(6,iGstTxt);
				prodHB.getChildren().add(7,cessTxt);
				HBox.setMargin(cessTxt, new Insets(0,-20,0,0));

		}
		}

//		Label grTotalLbl = new Label("Grand Total");
//		gp.add(grTotalLbl, 4, 9);
//		grTotalTxt.setText("00.00");
//		grTotalTxt.clear();

		ObservableList<SalesProduct> prodData = FXCollections.observableArrayList();
		List<SalesProduct> salesList=salesDAO.getProductDetails(salesEntry);
		for(SalesProduct sp:salesList) {
//			System.out.println("ST"+sp.getSubTotal());
			double gst=sp.getSubTotal()*(sp.getProduct().getGst()/100);
			String rateString=BigDecimal.valueOf(sp.getRate())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();
			String discString=BigDecimal.valueOf(sp.getDiscount())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();

			String subTotalString=BigDecimal.valueOf(sp.getSubTotal())
					.setScale(2,RoundingMode.HALF_UP)
					.toPlainString();

			String totalString=BigDecimal.valueOf(sp.getTotal())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();
			

			if(stateCombo.getValue().contains("Maharashtra")) {
//				System.out.println("GST"+sp.getProduct().getGst());
			String cgst=String.format("%.2f", (gst/2))+"\n("+(sp.getProduct().getGst()/2)+"%)";
			String sgst=String.format("%.2f", (gst/2))+"\n("+(sp.getProduct().getGst()/2)+"%)";
//			System.out.println("rate"+sp.getRate());
//			System.out.println("Quantity"+sp.getProduct().getQuantity());

			prodData.add(new SalesProduct(sp.getProd_name(),sp.getId(),sp.getProduct().getHsnNo(),sp.getQuantity(),sp.getPrevQty(),sp.getUnit(),sp.getRate(),sp.getDiscount(),sp.getTotal(),sp.getSubTotal(),cgst,sgst,"","",sp.getProduct(),rateString,discString,subTotalString,totalString,sp.getProduct().getQuantity()));
			}
			else {
				String igst=String.format("%.2f", gst)+"\n("+(sp.getProduct().getGst())+"%)";
				String cess="0";

//				System.out.println("quantit"+sp.getQuantity());
				prodData.add(new SalesProduct(sp.getProd_name(),sp.getId(),sp.getProduct().getHsnNo(),sp.getQuantity(),sp.getPrevQty(),sp.getUnit(),sp.getRate(),sp.getDiscount(),sp.getTotal(),sp.getSubTotal(),"","",igst,cess,sp.getProduct(),rateString,discString,subTotalString,totalString,sp.getProduct().getQuantity()));
				}

			}

//		prodData.addAll(salesList);


		TableView<SalesProduct> prodView = new TableView<SalesProduct>();
		prodView.setMaxSize(1100, 200);
		TableColumn<SalesProduct, String> prodName = new TableColumn<SalesProduct, String>("Product Name");
		prodName.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
		prodName.setPrefWidth(150);
		TableColumn<SalesProduct, Long> hsnCol = new TableColumn<SalesProduct, Long>("HSN/"+"\nSAC");
		hsnCol.setCellValueFactory(new PropertyValueFactory<>("hsnNo"));
		hsnCol.setPrefWidth(90);
		TableColumn<SalesProduct, String> unitCol = new TableColumn<SalesProduct, String>("Unit");
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		unitCol.setPrefWidth(100);
		TableColumn<SalesProduct, Integer> quantityCol = new TableColumn<SalesProduct, Integer>("Qty.");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		quantityCol.setPrefWidth(90);
		TableColumn<SalesProduct, SalesProduct> rateCol = new TableColumn<SalesProduct, SalesProduct>("Rate");
		rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
		rateCol.setPrefWidth(100);
		TableColumn<SalesProduct, SalesProduct> discCol = new TableColumn<SalesProduct, SalesProduct>("Disc.\n"+"Rs.");
		discCol.setCellValueFactory(new PropertyValueFactory<>("discountString"));
		discCol.setPrefWidth(90);
		TableColumn<SalesProduct, SalesProduct> taxableCol = new TableColumn<SalesProduct, SalesProduct>("Taxable\n"+" Amount");
		taxableCol.setCellValueFactory(new PropertyValueFactory<>("subTotalString"));
		taxableCol.setPrefWidth(100);
		TableColumn<SalesProduct, SalesProduct> cgstCol = new TableColumn<SalesProduct, SalesProduct>("CGST\n"+"  (%)");
		cgstCol.setCellValueFactory(new PropertyValueFactory<>("cgst"));
		cgstCol.setPrefWidth(80);
		TableColumn<SalesProduct, SalesProduct> sgstCol = new TableColumn<SalesProduct, SalesProduct>("SGST\n"+"  (%)");
		sgstCol.setCellValueFactory(new PropertyValueFactory<>("sgst"));
		sgstCol.setPrefWidth(80);
		TableColumn<SalesProduct, SalesProduct> igstCol = new TableColumn<SalesProduct, SalesProduct>("IGST\n"+" (%)");
		igstCol.setCellValueFactory(new PropertyValueFactory<>("igst"));
		igstCol.setPrefWidth(80);
		TableColumn<SalesProduct, SalesProduct> cessCol = new TableColumn<SalesProduct, SalesProduct>("Cess");
		cessCol.setCellValueFactory(new PropertyValueFactory<>("cess"));
		cessCol.setPrefWidth(80);
		TableColumn<SalesProduct, SalesProduct> totalCol = new TableColumn<SalesProduct, SalesProduct>("Total");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
		totalCol.setPrefWidth(100);
		TableColumn<SalesProduct, SalesProduct> actionCol = new TableColumn<SalesProduct, SalesProduct>("Action");
		actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
		actionCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>(){
//			Button deleteBtn = new Button("Delete");

			@Override
		    protected void updateItem(SalesProduct salesProduct,boolean empty){
				if(salesProduct==null){
					setGraphic(null);
					return;
				}else{
					deleteBtn=new JFXButton("Delete");
					setGraphic(deleteBtn);

//					deleteBtn.setDisable(false);
					deleteBtn.getStyleClass().add("del-btn");
					deleteBtn.setAlignment(Pos.CENTER);
					deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							// TODO Auto-generated method stub
							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
//							alert.setHeaderText("HI");
							Optional<ButtonType> result= alert.showAndWait();
							if(result.get()==ButtonType.OK){
								deletedSalesList.add(salesProduct);
								getTableView().getItems().remove(salesProduct);
								getTableView().refresh();
								
								grandTotal=0;
								if(prodData.size()==0)
									grTotalTxt.setText("0.0");
								else{
								for(SalesProduct p:prodData){
									grandTotal=grandTotal+p.getTotal();
									grTotalTxt.setText(String.format("%.2f",grandTotal));

								}
								}
							}

						}
					});


				}
			}
		});

		prodView.getColumns().addAll(prodName,hsnCol,unitCol,quantityCol,rateCol,discCol,taxableCol,cgstCol,sgstCol,igstCol,cessCol,totalCol,actionCol);
		prodView.setItems(prodData);
		prodView.requestFocus();
		prodView.getSelectionModel().selectFirst();
		stateCombo.requestFocus();

		prodTxt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(prodTxt.getValue()!=null){
					addMoreBtn.setDisable(false);
					editBtn.setDisable(false);
					if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
						for(Product p:productList) {
							if(prodTxt.getValue().contains(p.getProduct_name())) {
								double gst=p.getGst()/2;
								gstRate=p.getGst();
//								cessRate=p.getCess();
								cGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
								gstRsTxt.setText(String.valueOf("0 ("+gst+"%)"));
								hsnTxt.setText(String.valueOf(p.getHsnNo()));
								rateTxt.setText(String.valueOf(p.getSellPrice()));
								unitCombo.setText(String.valueOf(p.getUnit()));
							}


						}

					}

					else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
						for(Product p:productList) {
							if(prodTxt.getValue().contains(p.getProduct_name())) {
								double gst=p.getGst();
								double cess=p.getCess();
								gstRate=p.getGst();
								cessRate=p.getCess();
								iGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
								cessTxt.setText(String.valueOf("0 ("+cess+"%)"));
								hsnTxt.setText(String.valueOf(p.getHsnNo()));
								rateTxt.setText(String.valueOf(p.getSellPrice()));
								unitCombo.setText(String.valueOf(p.getUnit()));
							}


						}
					}
//					return;
				}
			}
		});

		unitCombo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(unitCombo.getText()!=null) {
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);

				}
			}
		});

		addMoreBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
//				clearLabels();
//				gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
//					System.out.println(validateControls());
				boolean productFlag=false;
				boolean unitFlag=false;
					if(!validateOnAddEdit()){
//
					return;
				}
//					deleteBtn.setDisable(false);
					SalesProduct salesProduct = new SalesProduct();
					Product product = new Product();
					salesProduct.setProduct(product);
					for(Product p:productList) {
						if(prodTxt.getValue().contains(p.getProduct_name())&& Long.parseLong(hsnTxt.getText())==p.getHsnNo()) {
							salesProduct.getProduct().setId(p.getId());
							salesProduct.getProduct().setProduct_name(p.getProduct_name());
							salesProduct.setProd_name(p.getProduct_name());
							salesProduct.setGst(p.getGst());
							salesProduct.setHsnNo(Long.parseLong(hsnTxt.getText()));
							salesProduct.setCurrentStock(p.getQuantity());
							productFlag=true;
						}
					}
					if(!productFlag) {
						errorTip.setText("No Product found with specified name!");
						prodTxt.setTooltip(errorTip);
						errorTip.show(prodTxt,100,160);
						return;
					}

//					salesProduct.setProd_name(prodTxt.getValue());

//					System.out.println(unitCombo.getValue());
//					if(!unitCombo.getId().contains(unitCombo.getText())) {
//						errorTip.setText("Unit Name not found!");
//						unitCombo.setTooltip(errorTip);
//						errorTip.show(unitCombo,300,160);
//						return;
//					}

					salesProduct.setUnit(unitCombo.getText());
					salesProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));

					String trunRateAmt=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();
					salesProduct.setRate(Double.parseDouble(rateTxt.getText()));
					salesProduct.setRateString(trunRateAmt);

					String trunDiscAmt=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();
					salesProduct.setDiscount(Double.parseDouble(discTxt.getText()));
					salesProduct.setDiscountString(trunDiscAmt);

//					salesProduct.setGst(Double.parseDouble(gstRsTxt.getText()));
					String cgstTxt=cGstTxt.getText().replace("(", "\n(");
					salesProduct.setCgst(cgstTxt);

					String igstTxt=iGstTxt.getText().replace("(", "\n(");
					salesProduct.setIgst(igstTxt);

					String sgstTxt=gstRsTxt.getText().replace("(", "\n(");
					salesProduct.setSgst(sgstTxt);

					String cesssTxt=cessTxt.getText().replace("(", "\n(");
					salesProduct.setCess(cesssTxt);

					String trunTaxablAmt=BigDecimal.valueOf(taxableAmt)
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();
					salesProduct.setSubTotalString(trunTaxablAmt);
					double taxAmt=BigDecimal.valueOf(taxableAmt)
							.setScale(2,RoundingMode.HALF_UP)
							.doubleValue();
					
					salesProduct.setSubTotal(taxAmt);

					String trunTotalAmt=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();
					salesProduct.setTotalString(trunTotalAmt);
					salesProduct.setTotal(Double.parseDouble(totalTxt.getText()));
					for(SalesProduct sp:prodData) {
						if(sp.getProd_name().equals(salesProduct.getProd_name())) {
//							dupliErrLbl.setText("Duplicate entries are not permitted");
//							gp.add(dupliErrLbl, 0, 7);
							errorTip.setText("Duplicate entries are not permitted");
							prodTxt.setTooltip(errorTip);
							errorTip.show(prodTxt,100,200);
							return;
						}
					}

					prodData.add(salesProduct);
					prodView.setItems(prodData);
					prodView.requestFocus();
					prodView.getSelectionModel().selectLast();
					cashCheck.requestFocus();
					grandTotal=grandTotal+salesProduct.getTotal();
					grTotalTxt.setText(String.format("%.2f",grandTotal));

					clearProductData();
					editItem.setDisable(false);
			}
		});

		GridPane.setMargin(prodView, new Insets(-20,-50,0,0));
		gp.add(prodView, 0, 8,12,1);


		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				if(tg.getSelectedToggle()!=null) {
					String item=tg.getSelectedToggle().getUserData().toString();
					if(item.equals("Add Item")) {
						prodHB.getChildren().removeAll(editBtn,addMoreBtn);
						prodHB.getChildren().add(9,addMoreBtn);
						deleteBtn.setDisable(false);
						clearProductData();
					}else if(item.equals("Edit Item")) {
						deleteBtn.setDisable(true);
						SalesProduct salesProduct = prodView.getSelectionModel().getSelectedItem();
						int index = prodView.getSelectionModel().getSelectedIndex();
						prodTxt.setValue(salesProduct.getProd_name());
						unitCombo.setText(salesProduct.getUnit());
						hsnTxt.setText(String.valueOf(salesProduct.getHsnNo()));
						salesProduct.setPrevQty(salesProduct.getQuantity());
						quantityTxt.setText(String.valueOf(salesProduct.getQuantity()));

						String trunRateAmt=BigDecimal.valueOf(salesProduct.getRate())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();

						rateTxt.setText(trunRateAmt);

						String trunDiscAmt=BigDecimal.valueOf(salesProduct.getDiscount())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
						discTxt.setText(trunDiscAmt);

						gstRsTxt.setText(String.valueOf(salesProduct.getSgst()));
						cGstTxt.setText(String.valueOf(salesProduct.getCgst()));
						iGstTxt.setText(String.valueOf(salesProduct.getIgst()));
						cessTxt.setText(String.valueOf(salesProduct.getCess()));

//						String trunTaxableAmt=BigDecimal.valueOf(salesProduct.getSubTotal())
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						taxableTxt.setText(trunTaxableAmt);

						String trunTotalAmt=BigDecimal.valueOf(salesProduct.getTotal())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
						totalTxt.setText(trunTotalAmt);

						grandTotal=grandTotal-salesProduct.getTotal();
						prodHB.getChildren().remove(addMoreBtn);
						prodHB.getChildren().add(9, editBtn);

						editBtn.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								// TODO Auto-generated method stub
//								clearLabels();
//								gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
								if(!validateOnAddEdit()){
									return;
								}

//								PurchaseProduct purchaseProduct1 = new PurchaseProduct();
								salesProduct.setProd_name(prodTxt.getValue());
								salesProduct.setUnit(unitCombo.getText());
								salesProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
								String trunRateAmt1=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
										.setScale(3, RoundingMode.HALF_UP)
										.toPlainString();
								salesProduct.setRateString(trunRateAmt1);

								salesProduct.setRate(Double.parseDouble(rateTxt.getText()));
								String trunDiscAmt1=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
										.setScale(3, RoundingMode.HALF_UP)
										.toPlainString();
								salesProduct.setDiscountString(trunDiscAmt1);

								salesProduct.setDiscount(Double.parseDouble(discTxt.getText()));
//								salesProduct.setGst(Double.parseDouble());

								String cgstTxt=cGstTxt.getText().replace("(", "\n(");
								salesProduct.setCgst(cgstTxt);

//							if(iGstTxt.getText().contains("(")) {/

								String igstTxt=iGstTxt.getText().replace("(", "\n(");
								salesProduct.setIgst(igstTxt);
//							}
//							salesProduct.setSgst(gstRsTxt.getText());
//							if(gstRsTxt.getText().contains("(")) {
								String sgstTxt=gstRsTxt.getText().replace("(", "\n(");
								salesProduct.setSgst(sgstTxt);
//							}

//							salesProduct.setCess(cessTxt.getText());
							if(cessTxt.getText().contains("(")) {
								String cesssTxt=cessTxt.getText().replace("(", "\n(");
								salesProduct.setCess(cesssTxt);
							}

								String trunTaxablAmt=BigDecimal.valueOf(taxableAmt)
								    .setScale(2, RoundingMode.HALF_UP)
								    .toPlainString();

									double taxAmt=BigDecimal.valueOf(taxableAmt)
											.setScale(2,RoundingMode.HALF_UP)
											.doubleValue();
									
								salesProduct.setSubTotal(taxAmt);
						salesProduct.setSubTotalString(trunTaxablAmt);

								String trunTotalAmt1=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
										.setScale(3, RoundingMode.HALF_UP)
										.toPlainString();
								salesProduct.setTotalString(trunTotalAmt1);
								salesProduct.setTotal(Double.parseDouble(totalTxt.getText()));

								prodData.set(index,salesProduct);
								cashCheck.requestFocus();
								grandTotal=0;
								for(SalesProduct p:prodData) {
									grandTotal=grandTotal+p.getTotal();
								}
								grTotalTxt.setText(String.format("%.2f",grandTotal));
								prodHB.getChildren().remove(editBtn);
								prodHB.getChildren().add(10, addMoreBtn);
								addItem.setSelected(true);
								clearProductData();
							}
						});

					}
				}
			}
		});

		HBox paymentBox= new HBox();
		
		grTotalTxt= new JFXTextField();
		grTotalTxt.setFocusTraversable(true);
		grTotalTxt.setLabelFloat(true);
		grTotalTxt.setFocusTraversable(true);
		grTotalTxt.setText(String.format("%.2f", salesEntry.getTotal()));

		grTotalTxt.setEditable(false);
		grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
		grTotalTxt.setFocusColor(Color.TRANSPARENT);
//		System.out.println("GRand Total"+grandTotal);

		grandTotal=salesEntry.getTotal();
		grTotalTxt.setPromptText("Grand Total");
		grTotalTxt.setStyle("-fx-font-size: 20px; -fx-font-weight:bold");
		GridPane.setMargin(grTotalTxt, new Insets(20,-50,0,50));

		gp.add(grTotalTxt, 0, 10);

		
		salesManCombo= new JFXComboBox<>();
		salesManCombo.setLabelFloat(true);
		salesManCombo.setPromptText("Sales Man");
		salesManCombo.setStyle("-fx-font-size:14");
		salesManData.clear();
		List<SalesMan> saleManList= salesDAO.showSalesManList();
		for(SalesMan s:saleManList) {
			
				if(s.getSalesManName()!=null||!s.getSalesManName().equals("")) {
					salesManData.add(s.getSalesManName());	
				
			}
		}
		salesManCombo.getStyleClass().add("jf-combo-box");
		salesManCombo.setItems(salesManData);
		salesManCombo.setValue(salesEntry.getSalesManName());
		new AutoCompleteComboBoxListener<>(salesManCombo);
		
		GridPane.setMargin(salesManCombo, new Insets(0,-50,0,50));
		gp.add(salesManCombo, 0, 12);
		
//		payCombo.getItems().clear();
//
//		payCombo.getItems().addAll("Full Payment","Part Payment","UnPaid");
//		payCombo.setValue(salesEntry.getPayment_type());
//		payCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent event) {
//				// TODO Auto-generated method stub
////				errorTip.hide();
//				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), payCombo.getValue(), payCombo.getItems());
//				if(s!=null) {
//					payCombo.requestFocus();
//					payCombo.getSelectionModel().select(s);
//
//				}
//			}
//		});
//		payCombo.setLabelFloat(true);
//		payCombo.setPromptText("Select Payment Type");
//		GridPane.setMargin(payCombo, new Insets(20,-50,0,50));
////		payCombo.requestFocus();
//		gp.add(payCombo, 0, 11);
//		partPayTxt.setPromptText("Part Pay Amount");
//		partPayTxt.setStyle("-fx-font-size:14");
//		partPayTxt.setLabelFloat(true);
//		partPayTxt.setText("0");
//
//		partPayTxt.setVisible(false);
//		GridPane.setMargin(partPayTxt, new Insets(20,-50,0,50));
//		gp.add(partPayTxt, 0, 12);
////		HBox hb = new HBox();
//
//		if(payCombo.getValue().equals("Part Payment")) {
//			partPayTxt.setVisible(true);
//			partPayTxt.setText(String.valueOf(salesEntry.getPaid_amount()));
//		}
//		payCombo.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
//				if(payCombo.getValue()!=null){
//				if(payCombo.getValue().equals("Full Payment")) {
//					partPayTxt.setVisible(false);
//					paymentBox.setVisible(true);
//					payModeLbl.setVisible(true);
//
//				}
//				else if(payCombo.getValue().equals("UnPaid")){
//					partPayTxt.setVisible(false);
//					paymentBox.setVisible(false);
//					payModeLbl.setVisible(false);
////					hb.setVisible(false);
//				}
//				else if(payCombo.getValue().equals("Part Payment")) {
////				gp.addRow(9, hb);
//				partPayTxt.setVisible(true);
//				paymentBox.setVisible(true);
//				payModeLbl.setVisible(true);
//				}
//			}}
//		});
//		Label payModeLbl = new Label("Payment Mode");
//		gp.add(payModeLbl, 4, 11);
//		VBox payModeHB= new VBox();

		PaymentMode paymentMode=salesDAO.getPaymentModes(salesEntry);

		
		payModeLbl.setStyle("-fx-font-size:14");
		GridPane.setMargin(payModeLbl, new Insets(-70,0,0,-50));
		gp.add(payModeLbl, 2, 10);
		
		cashCheck= new JFXCheckBox();
		cashCheck.setSelected(false);
		cashCheck.setText("Pay by Cash");
		cashAmtTxt= new JFXTextField();
		cashAmtTxt.setPromptText("Amount");

//		cashAmtTxt.setVisible(false);
//		cashAmtTxt.setMaxWidth(50);
//		cashAmtTxt.setLabelFloat(true);
		cashAmtTxt.clear();
		cashAmtTxt.setStyle("-fx-font-size:14");

		VBox cashBox= new VBox();
		cashBox.setSpacing(10);
//		cashBox.setMaxHeight(100);
		cashBox.setSpacing(10);
		cashBox.getChildren().add(cashCheck);
		
		bankCheck= new JFXCheckBox();
		bankCheck.setSelected(false);
		bankCheck.setText("Pay by Cheque/DD");
		
//		bankTxt.clear();
		bankTxt= new JFXTextField();
		bankTxt.setPromptText("Bank Name");
//		bankTxt.setVisible(false);
		bankTxt.setStyle("-fx-font-size:14");


//		bankTxt.setMaxWidth(50);
//		bankTxt.setLabelFloat(true);/
//		cheqTxt.clear();
		cheqTxt= new JFXTextField();
		cheqTxt.setPromptText("Cheque No/DD No");
		cheqTxt.setStyle("-fx-font-size:14");
//		cheqTxt.setVisible(false);
//		cheqTxt.setMaxWidth(50);
//		cheqTxt.setLabelFloat(true);
//		bankAmtTxt.clear();
		bankAmtTxt= new JFXTextField();
		bankAmtTxt.setPromptText("Amount");
//		bankAmtTxt.setVisible(false);
//		bankAmtTxt.setLabelFloat(true);
		bankAmtTxt.setStyle("-fx-font-size:14");

		VBox bankBox= new VBox();
		bankBox.getChildren().addAll(bankCheck);
		bankBox.setSpacing(10);
		cardCheck= new JFXCheckBox();
		cardCheck.setSelected(false);
		cardCheck.setText("Pay by Credit/Debit Card/NetBanking");
//		transId.clear();
		transId= new JFXTextField();
		transId.setPromptText("Transaction ID");
		transId.setStyle("-fx-font-size:14");
//		transId.setVisible(false);
//		transId.setMaxWidth(50);
//		transId.setLabelFloat(true);
//		cardAmtTxt.clear();
		cardAmtTxt= new JFXTextField();
		cardAmtTxt.setPromptText("Amount");
		cardAmtTxt.setStyle("-fx-font-size:14");
//		cardAmtTxt.setVisible(false);
//		cardAmtTxt.setMaxWidth(50);
//		cardAmtTxt.setLabelFloat(true);

		VBox cardBox= new VBox();
		cardBox.getChildren().add(cardCheck);
		cardBox.setSpacing(10);
		voucherCheck= new JFXCheckBox();
		voucherCheck.setSelected(false);
		voucherCheck.setText("Pay by Voucher/Wallet");
//		voucherTypeTxt.clear();
		voucherTypeTxt= new JFXTextField();
		voucherTypeTxt.setPromptText("Voucher Code/Wallet Name");
//		voucherTypeTxt.setVisible(false);
		voucherTypeTxt.setStyle("-fx-font-size:14");
//		voucherTypeTxt.setMaxWidth(50);
//		voucherTypeTxt.setLabelFloat(true);
//		vouchAmtTxt.clear();
		vouchAmtTxt= new JFXTextField();
		vouchAmtTxt.setPromptText("Amount");
//		vouchAmtTxt.setVisible(false);
		vouchAmtTxt.setStyle("-fx-font-size:14");
//		vouchAmtTxt.setMaxWidth(50);
//		vouchAmtTxt.setLabelFloat(true);

		VBox vouchBox= new VBox();
		vouchBox.getChildren().addAll(voucherCheck);
		vouchBox.setSpacing(10);

		System.out.println(paymentList.size());
//		if(!salesEntry.getPayment_type().equals("UnPaid")) {
//		for(PaymentMode p:paymentList) {
				if(paymentMode.getCashMode()!=null) {
					if(!paymentMode.getCashMode().equals("null")) {
						cashCheck.setSelected(true);
						if(paymentMode.getCashAmount()!=0&&(!paymentMode.getBankMode().equals("null")&&!paymentMode.getCardMode().equals("null")&&!paymentMode.getVoucherMode().equals("null"))) {
							cashBox.getChildren().add(cashAmtTxt);
							cashAmtTxt.setText(String.valueOf(paymentMode.getCashAmount()));
						}
					}
				}
				if(paymentMode.getBankMode()!=null) {
					if(!paymentMode.getBankMode().equals("null")) {
						bankCheck.setSelected(true);
						bankTxt.setText(paymentMode.getBankName());
						cheqTxt.setText(paymentMode.getChequeNo());
						bankBox.getChildren().addAll(bankTxt,cheqTxt);
						if(paymentMode.getBankAmount()!=0) {
							bankBox.getChildren().add(bankAmtTxt);
							bankAmtTxt.setText(String.valueOf(paymentMode.getBankAmount()));
						}
					}
				}
				if(paymentMode.getCardMode()!=null) {
					if(!paymentMode.getCardMode().equals("null")) {
						cardCheck.setSelected(true);
						transId.setText(paymentMode.getTransId());
						cardBox.getChildren().add(transId);
						if(paymentMode.getCardAmount()!=0) {
							cardBox.getChildren().add(cardAmtTxt);
							cardAmtTxt.setText(String.valueOf(paymentMode.getCardAmount()));
						}
					}
				}

				if(paymentMode.getVoucherMode()!=null) {
					if(!paymentMode.getVoucherMode().equals("null")) {
						voucherCheck.setSelected(true);
						voucherTypeTxt.setText(paymentMode.getVoucherWalletCode());
						vouchBox.getChildren().add(voucherTypeTxt);
						if(paymentMode.getVoucherAmt()!=0) {
							vouchBox.getChildren().add(vouchAmtTxt);
							vouchAmtTxt.setText(String.valueOf(paymentMode.getVoucherAmt()));
						}
					}
				}
				
			
//			}
//		}
//
		paymentBox.setSpacing(20);
		paymentBox.setMaxSize(180, 50);
		paymentBox.setStyle("-fx-border-style: solid;-fx-padding:15;-fx-border-width: 2;-fx-border-insets:5; -fx-border-radius: 5;");
		paymentBox.getChildren().addAll(cashBox,bankBox,cardBox,vouchBox);


//		payModeCombo.getItems().clear();
//		payModeCombo.getItems().addAll("Cash","Cheque","NEFT/RTGS","Card");
//		new AutoCompleteComboBoxListener<>(payModeCombo);
//		payModeCombo.setLabelFloat(true);
//		payModeCombo.setPromptText("Select Payment Mode");

//		if(salesEntry.getPayment_type().equals("UnPaid")) {
//			paymentBox.setVisible(false);
//		}
		GridPane.setMargin(paymentBox, new Insets(-80,0,0,-50));
		gp.add(paymentBox, 2, 12);


		cashCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub

//				cashBox.getChildren().remove(cashAmtTxt);
				if(newValue) {
					cardBox.getChildren().remove(cardAmtTxt);
					vouchBox.getChildren().remove(vouchAmtTxt);
					bankBox.getChildren().remove(bankAmtTxt);

					if(bankCheck.isSelected()||cardCheck.isSelected()||voucherCheck.isSelected()) {
						cashBox.getChildren().add(cashAmtTxt);
					}

					boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);
					boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
					boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);

				}
				else {
					cashAmtTxt.clear();
					cashBox.getChildren().remove(cashAmtTxt);
					if(!(bankCheck.isSelected()&&cardCheck.isSelected()&&voucherCheck.isSelected())) {
						if(bankCheck.isSelected()&&!(cardCheck.isSelected()||voucherCheck.isSelected())) {
							bankAmtTxt.clear();
							bankBox.getChildren().remove(bankAmtTxt);
						}
						else if(cardCheck.isSelected()&&!(bankCheck.isSelected()||voucherCheck.isSelected())) {
							cardAmtTxt.clear();
							cardBox.getChildren().remove(cardAmtTxt);
						}

						else if(voucherCheck.isSelected()&&!(bankCheck.isSelected()||cardCheck.isSelected())) {
							vouchAmtTxt.clear();
							vouchBox.getChildren().remove(vouchAmtTxt);
						}
					}

//					cashAmtTxt.setVisible(false);
				}

			}
		});


//
		bankCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(newValue) {

				bankBox.getChildren().addAll(bankTxt,cheqTxt);
				if(cashCheck.isSelected()||cardCheck.isSelected()||voucherCheck.isSelected()) {
					bankBox.getChildren().add(bankAmtTxt);
//					cashBox.getChildren().add(cashAmtTxt);
//					cardBox.getChildren().add(cardAmtTxt);
//					vouchBox.getChildren().add(vouchAmtTxt);
				}

				cashBox.getChildren().remove(cashAmtTxt);
				cardBox.getChildren().remove(cardAmtTxt);
				vouchBox.getChildren().remove(vouchAmtTxt);

				boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
				boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);
				boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);


			}
			else{
					bankBox.getChildren().removeAll(bankTxt,cheqTxt,bankAmtTxt);
					bankTxt.clear();
					cheqTxt.clear();
					bankAmtTxt.clear();
					if(!(cashCheck.isSelected()&&cardCheck.isSelected()&&voucherCheck.isSelected())) {
						if(cashCheck.isSelected()&&!(cardCheck.isSelected()||voucherCheck.isSelected())) {
							cashAmtTxt.clear();
							cashBox.getChildren().remove(cashAmtTxt);
						}
						else if(cardCheck.isSelected()&&!(cashCheck.isSelected()||voucherCheck.isSelected())) {
							cardAmtTxt.clear();
							cardBox.getChildren().remove(cardAmtTxt);
						}

						else if(voucherCheck.isSelected()&&!(cashCheck.isSelected()||cardCheck.isSelected())) {
							vouchAmtTxt.clear();
							vouchBox.getChildren().remove(vouchAmtTxt);
						}
					}
				}
			}
		});

		cardCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub

			if(newValue) {
				cardBox.getChildren().add(transId);


					if(cashCheck.isSelected()||bankCheck.isSelected()||voucherCheck.isSelected()) {
//						bankBox.getChildren().add(bankAmtTxt);
//						cashBox.getChildren().add(cashAmtTxt);
						cardBox.getChildren().add(cardAmtTxt);
//						vouchBox.getChildren().add(vouchAmtTxt);
					}

					cashBox.getChildren().remove(cashAmtTxt);
					vouchBox.getChildren().remove(vouchAmtTxt);
					bankBox.getChildren().remove(bankAmtTxt);

					boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
					boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
					boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);



			}
			else {
					cardBox.getChildren().removeAll(transId,cardAmtTxt);
					transId.clear();
					cardAmtTxt.clear();

					if(!(cashCheck.isSelected()&&bankCheck.isSelected()&&voucherCheck.isSelected())) {
						if(cashCheck.isSelected()&&!(bankCheck.isSelected()||voucherCheck.isSelected())) {
							cashAmtTxt.clear();
							cashBox.getChildren().remove(cashAmtTxt);
						}
						else if(bankCheck.isSelected()&&!(cashCheck.isSelected()||voucherCheck.isSelected())) {
							bankAmtTxt.clear();
							bankBox.getChildren().remove(bankAmtTxt);
						}

						else if(voucherCheck.isSelected()&&!(cashCheck.isSelected()||bankCheck.isSelected())) {
							vouchBox.getChildren().remove(vouchAmtTxt);
							vouchAmtTxt.clear();
						}
					}

				}
			}
		});

		voucherCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub


				if(newValue) {
					vouchBox.getChildren().add(voucherTypeTxt);

					if(cashCheck.isSelected()||bankCheck.isSelected()||cardCheck.isSelected()) {
						vouchBox.getChildren().add(vouchAmtTxt);
					}

					cardBox.getChildren().remove(cardAmtTxt);
					bankBox.getChildren().remove(bankAmtTxt);
					cashBox.getChildren().remove(cashAmtTxt);

					boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
					boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
					boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);

//						bankBox.getChildren().add(bankAmtTxt);
//						cashBox.getChildren().add(cashAmtTxt);
//						cardBox.getChildren().add(cardAmtTxt);
//						vouchBox.getChildren().add(vouchAmtTxt);
//					}
				}
			else {
				vouchBox.getChildren().removeAll(voucherTypeTxt,vouchAmtTxt);
				voucherTypeTxt.clear();
				vouchAmtTxt.clear();
				if(!(cashCheck.isSelected()&&bankCheck.isSelected()&&cardCheck.isSelected())) {
					if(cashCheck.isSelected()&&!(bankCheck.isSelected()||cardCheck.isSelected())) {
						cashAmtTxt.clear();
						cashBox.getChildren().remove(cashAmtTxt);
					}
					else if(bankCheck.isSelected()&&!(cashCheck.isSelected()||cardCheck.isSelected())) {
						bankAmtTxt.clear();
						bankBox.getChildren().remove(bankAmtTxt);
					}

					else if(cardCheck.isSelected()&&!(cashCheck.isSelected()||bankCheck.isSelected())) {
						cardAmtTxt.clear();
						cardBox.getChildren().remove(cardAmtTxt);
					}
				}
				}
			}
		});



		JFXButton updateBtn = new JFXButton(" Update ");
		gp.add(updateBtn, 2,13);
//		payModeCombo.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
//				if(payModeCombo.getValue()!=null){
//				if(!payModeCombo.getValue().equals("Cash")){
//					if(gp.getChildren().contains(hb)){
//						gp.getChildren().remove(hb);}
//					hb.setVisible(true);
//					gp.add(hb, 5, 14);
//				}
//				else{
//					if(gp.getChildren().contains(hb)){
//						gp.getChildren().remove(hb);
//					}
//			}
//				}
//			}
//		});

		quantityTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
//				quantityErrLbl.setText("");
//				totErrLbl.setText("");
//				totalTxt.setText("");
//				if(gp.getChildren().contains(quantityErrLbl))
//					gp.getChildren().remove(quantityErrLbl);
				errorTip.hide();

				if(!validateProductControls()){
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
//					gp.add(quantityErrLbl, 1, 7);

					return;
				}

				for(Product p:productList) {
					if(prodTxt.getValue()!=null) {
					if(prodTxt.getValue().contains(p.getProduct_name())) {
						if(p.getQuantity()<Integer.parseInt(newValue)) {
//							System.out.println(p.getQuantity());
							errorTip.setText("Quantity must not exceed current stock");
							quantityTxt.setTooltip(errorTip);
							errorTip.show(quantityTxt,400,150);
							addMoreBtn.setDisable(true);
							editBtn.setDisable(true);
							return;
						}
					}
					}
				}
				
				if(!deletedSalesList.isEmpty()) {
					for(SalesProduct sp:deletedSalesList) {
						salesDAO.addStock(sp);
					}
				}
//				totErrLbl.setText("");
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if(!newValue.isEmpty()){
					if(!(rateTxt.getText().equals("")||discTxt.getText().equals(""))){
						double total = (Double.parseDouble(newValue)*Double.parseDouble(rateTxt.getText()))-Double.parseDouble(discTxt.getText());


						double subTotal = total*(100/(100+gstRate));
						gstAmt=total-subTotal;
						double halfGst=gstAmt/2;

						if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {

							cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
							gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
						}
						else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
							iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
							double subTotal1=subTotal*(100/(100+cessRate));
							cessAmt=subTotal-subTotal1;
//							subTotalsub=subTotal+subTotal1;
							cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
//							subTotal=subTotal+cess;
						}
						taxableAmt=subTotal;
					totalTxt.setText(String.format("%.2f",total));}
					else{
						totalTxt.setText("NaN");
					}
			}
				else{
					quantityTxt.setText(quantityTxt.getPromptText());
				}
				}

		});

		rateTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
//				rateErrLbl.setText("");
//				totErrLbl.setText("");
//				totalTxt.setText("");
//				if(gp.getChildren().contains(rateErrLbl))
//					gp.getChildren().remove(rateErrLbl);
				errorTip.hide();
				if(!validateProductControls()){
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
//					gp.add(rateErrLbl, 2, 7);
					return;
				}
				if(!quantityTxt.getText().isEmpty()) {
					for(Product p:productList) {
						if(prodTxt.getValue()!=null) {
						if(prodTxt.getValue().contains(p.getProduct_name())) {

							if(p.getQuantity()<Integer.parseInt(quantityTxt.getText())) {
//								System.out.println(p.getQuantity());
								errorTip.setText("Quantity must not exceed current stock");
								quantityTxt.setTooltip(errorTip);
								errorTip.show(quantityTxt,400,150);
								addMoreBtn.setDisable(true);
								editBtn.setDisable(true);
								return;
							}
						}
						}
					}
					}

//				totErrLbl.setText("");
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if(!newValue.equals("")){
//					if(newValue.matches(arg0))
					if(!(quantityTxt.getText().equals("")||discTxt.getText().equals(""))){
						double total = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(newValue))-Double.parseDouble(discTxt.getText());

						double subTotal = total*(100/(100+gstRate));
						gstAmt=total-subTotal;

						double halfGst=gstAmt/2;
						if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
							cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
							gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
						}
						else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
							iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
							double subTotal1=subTotal*(100/(100+cessRate));
							cessAmt=subTotal-subTotal1;
//							subTotal=subTotal+subTotal1;
							cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
//							subTotal=subTotal+cess;
						}
						taxableAmt=subTotal;
				totalTxt.setText(String.format("%.2f",total));}
					else{
						totalTxt.setText("NaN");
					}
			}else
				rateTxt.setText(rateTxt.getPromptText());
				}
		});

		discTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
//				discErrLbl.setText("");
//				totErrLbl.setText("");
//				totalTxt.setText("");
//				if(gp.getChildren().contains(discErrLbl))
//					gp.getChildren().remove(discErrLbl);

				if(!validateProductControls()){
//					System.out.println("Reched inside validation");
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
//					gp.add(discErrLbl, 3, 7);
					return;
				}
				if(!quantityTxt.getText().isEmpty()) {
					for(Product p:productList) {
						if(prodTxt.getValue()!=null) {
						if(prodTxt.getValue().contains(p.getProduct_name())) {
							if(p.getQuantity()<Integer.parseInt(quantityTxt.getText())) {
//								System.out.println(p.getQuantity());
								errorTip.setText("Quantity must not exceed current stock");
								quantityTxt.setTooltip(errorTip);
								errorTip.show(quantityTxt,400,150);
								addMoreBtn.setDisable(true);
								editBtn.setDisable(true);

								return;
							}
						}
					}
					}
					}

//				totErrLbl.setText("");
//				System.out.println("Oustdie validation");
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if(!newValue.equals("")){
					if(!(quantityTxt.getText().equals("") || rateTxt.getText().equals(""))){
						double total = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(rateTxt.getText()))-Double.parseDouble(newValue);


						double subTotal = total*(100/(100+gstRate));
						gstAmt=total-subTotal;
						double halfGst=gstAmt/2;

						if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
							cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
							gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
						}
						else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
							iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
							double subTotal1=subTotal*(100/(100+cessRate));
							cessAmt=subTotal-subTotal1;
//							subTotal=subTotal+subTotal1;
							cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
//							subTotal=subTotal+cess;
						}
						taxableAmt=subTotal;

					totalTxt.setText(String.format("%.2f",total));}
					else{
						totalTxt.setText("NaN");
					}
			}else{
						discTxt.setText(discTxt.getPromptText());	}
				}

		});
//		gstRsTxt.textProperty().addListener(new ChangeListener<String>() {
//
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//				// TODO Auto-generated method stub
//				gstErrLbl.setText("");
//				totErrLbl.setText("");
////				totalTxt.setText("");
//				if(gp.getChildren().contains(gstErrLbl))
//					gp.getChildren().remove(gstErrLbl);
//
//				if(!validateProductControls()){
//					addMoreBtn.setDisable(true);
//					editBtn.setDisable(true);
//					gp.add(gstErrLbl, 4, 7);
//					return;
//				}
//
//					addMoreBtn.setDisable(false);
//					editBtn.setDisable(false);
//				if(!newValue.equals("")){
//					if(!(quantityTxt.getText().equals("")||rateTxt.getText().equals("")||discTxt.getText().equals(""))){
//					double subTotal = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(rateTxt.getText()))-Double.parseDouble(discTxt.getText());
//					double gst = subTotal*(Double.parseDouble(newValue)/100);
//					double total=subTotal+gst;
//					totalTxt.setText(String.format("%.2f",total));}
//					else{
//						totalTxt.setText("NaN");
//					}
//			}
//				else
//					gstRsTxt.setText(gstRsTxt.getPromptText());
//			}
//
//		});


		updateBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
//				clearLabels();

//				if(gp.getChildren().contains(suppErrLbl)) {
//					gp.getChildren().removeAll(dateErrorLbl,grTotErrLbl,payErrLbl,payModeErrLbl,partPayErrLbl);
//				}
				if(!validateonSaleSubmitButton()){
//
					return;
				}

					for(SalesProduct sp:prodData) {
						Product product=sp.getProduct();
//						System.out.println("quantiy"+product.getQuantity());
						if((sp.getCurrentStock()+sp.getPrevQty())<sp.getQuantity()) {
//								System.out.println(product.getQuantity());
								errorTip.setText("Quantity for the product \'"+sp.getProd_name()+"\' must not exceed current stock");
								updateBtn.setTooltip(errorTip);
								errorTip.show(updateBtn,400,150);
//								addMoreBtn.setDisable(true);
//								editBtn.setDisable(true);

								return;
							}

					}
//


//				SalesEntry salesEntry = new SalesEntry();
//				salesEntry.setId(id);
				salesEntry.setInvoice_no(invoiceId);
				salesEntry.setDeliveryMemo(new DeliveryMemo());
				salesEntry.setSalesMan(new SalesMan());
				
//				salesEntry.setInvoiceNo(purchInvoicTxt.getText());
//				Supplier supplier = new Supplier();
				Customer customer=new Customer();
//				purchaseEntry.setSupplier(supplier);
				salesEntry.setCustomer(customer);
				if(custCombo.getValue()!=null) {
				for(Customer c:custList){
					if(custCombo.getValue().equals(c.getFull_name())&& prmConTxt.getText().equals(c.getContact())) {
						salesEntry.getCustomer().setCust_id(c.getCust_id());
					}
//					salesEntry.getCustomer().setShopName(c.getShopName());
//					salesEntry.getCustomer().setAgency(c.getAgency());
//					salesEntry.getCustomer().setAddress(c.getAddress());
//					salesEntry.getCustomer().setState(c.getState());
//					salesEntry.getCustomer().setCity(c.getCity());
//					salesEntry.getCustomer().setContact(c.getContact());
					
				}
				}
				
				if(salesManCombo.getValue()!=null) {
					for(SalesMan s:saleManList) {
					
						if(s.getSalesManName().equals(salesManCombo.getValue())) {
								if(s.getStatus()==0) {
									errorTip.setText("Sales Man deleted from the system");
									salesManCombo.setTooltip(errorTip);
									errorTip.show(salesManCombo,200,600);
									return;
								}else {
									salesEntry.getSalesMan().setId(s.getId());
								}
							}
						}
					}
				
				try {
					LocalDate localDate = invoicDatePick.getValue();
					SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
					Date utilDate = new Date(sd.parse(localDate.toString()).getTime());
					salesEntry.setEntry_date(utilDate);
//					System.out.println(utilDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				salesEntry.getCustomer().setFull_name(custCombo.getValue());
				salesEntry.getCustomer().setAddress(address.getText());
				salesEntry.getCustomer().setState(stateCombo.getValue());
				salesEntry.getCustomer().setGstin(gstTxt.getText());
				salesEntry.getCustomer().setContact(prmConTxt.getText());

//				salesEntry.setPaid_amount(Double.parseDouble(partPayTxt.getText()));

//				System.out.println("Grand total"+grTotalTxt.getText());
				salesEntry.setSub_total(Double.parseDouble(grTotalTxt.getText()));
				salesEntry.setTotal(Double.parseDouble(grTotalTxt.getText()));
				salesEntry.setPayment_type(payCombo.getValue());

				PaymentMode paymentMode= new PaymentMode();
				salesEntry.setPaymentMode(paymentMode);
//				if(salesEntry.getPayment_type().equals("Full Payment")) {
					if(cashCheck.isSelected()) {
						salesEntry.getPaymentMode().setCashMode(cashCheck.getText());
						if(!cashAmtTxt.getText().isEmpty()) {
						salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(cashAmtTxt.getText()));
						}
						else {
						salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(grTotalTxt.getText()));
						}
					}
					if(bankCheck.isSelected()) {
						salesEntry.getPaymentMode().setBankMode(bankCheck.getText());
						salesEntry.getPaymentMode().setBankName(bankTxt.getText());
						salesEntry.getPaymentMode().setChequeNo(cheqTxt.getText());

						if(!bankAmtTxt.getText().isEmpty()) {
						salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(bankAmtTxt.getText()));
						}
						else {
							salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(grTotalTxt.getText()));
						}
					}
					if(cardCheck.isSelected()) {
						salesEntry.getPaymentMode().setCardMode(cardCheck.getText());
						salesEntry.getPaymentMode().setTransId(transId.getText());
						if(!cardAmtTxt.getText().isEmpty()) {
							salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(cardAmtTxt.getText()));
						}
						else {
							salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(grTotalTxt.getText()));
						}

					}
					if(voucherCheck.isSelected()) {
						salesEntry.getPaymentMode().setVoucherMode(voucherCheck.getText());
						salesEntry.getPaymentMode().setVoucherWalletCode(voucherTypeTxt.getText());
						if(!vouchAmtTxt.getText().isEmpty()) {
							salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(vouchAmtTxt.getText()));
						}
						else {
							salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(grTotalTxt.getText()));
						}

					}
//				}else if(salesEntry.getPayment_type().equals("Part Payment")) {
					if(cashCheck.isSelected()) {
						salesEntry.getPaymentMode().setCashMode(cashCheck.getText());
						if(!cashAmtTxt.getText().isEmpty()) {
						salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(cashAmtTxt.getText()));
						}
//						else {
//						salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(partPayTxt.getText()));
//						}
					}
					if(bankCheck.isSelected()) {
						salesEntry.getPaymentMode().setBankMode(bankCheck.getText());
						salesEntry.getPaymentMode().setBankName(bankTxt.getText());
						salesEntry.getPaymentMode().setChequeNo(cheqTxt.getText());

						if(!bankAmtTxt.getText().isEmpty()) {
						salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(bankAmtTxt.getText()));
						}
//						else {
//							salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(partPayTxt.getText()));
//						}
					}
					if(cardCheck.isSelected()) {
						salesEntry.getPaymentMode().setCardMode(cardCheck.getText());
						salesEntry.getPaymentMode().setTransId(transId.getText());
						if(!cardAmtTxt.getText().isEmpty()) {
							salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(cardAmtTxt.getText()));
						}
//						else {
//							salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(partPayTxt.getText()));
//						}

					}
					if(voucherCheck.isSelected()) {
						salesEntry.getPaymentMode().setVoucherMode(voucherCheck.getText());
						salesEntry.getPaymentMode().setVoucherWalletCode(voucherTypeTxt.getText());
						if(!vouchAmtTxt.getText().isEmpty()) {
							salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(vouchAmtTxt.getText()));
						}
//						else {
//							salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(partPayTxt.getText()));
//						}

					}
//				}

//				salesEntry.setPayment_mode(payModeCombo.getValue());
//				salesEntry.setBank_name(bankTxt.getText());
//				salesEntry.setCheque_no(cheqTxt.getText());
//			

				result=salesDAO.updateSalesEntry(salesEntry, prodData);
				if(result) {
					Alert alert = new Alert(AlertType.INFORMATION, "Sales Entry updated");
					alert.setTitle("Success Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
					Main.dialog.close();
					Main.salesView.setItems(saleSortedData);
					Main.salesView.requestFocus();
					Main.salesView.getSelectionModel().selectLast();
					Main.salesView.getFocusModel().focusNext();
					try {
						generateInvoice.showReport(salesEntry);
					} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					showInvoice(anchorPane);


					Main.salesView.setMinSize(900, 500);
					anchorPane.getChildren().set(0,Main.salesView);
					try {
						
						SmsSender.sendSms(salesEntry);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					else {
						Alert alert = new Alert(AlertType.INFORMATION, "Error occurred while updating sales invoice!Please check database connection");
						alert.setTitle("Error Message");
						alert.setHeaderText("HI");
						alert.showAndWait();
					}

				clearProductData();
//				clearLabels();

			}
		});
		
		gp.addEventFilter(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode()==KeyCode.INSERT) {
				if(prodHB.getChildren().contains(addMoreBtn)) {
					addMoreBtn.fire();
					e.consume();
				}else if(prodHB.getChildren().contains(editBtn)) {
					editBtn.fire();
					e.consume();
				}

			}else if(e.getCode()==KeyCode.ENTER) {
				updateBtn.fire();
				e.consume();
			}
		});


		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				stateCombo.requestFocus();
			}
		});




		return gp;

	}
	
	public GridPane viewSalesEntry(SalesEntry salesEntry,AnchorPane anchorPane) {
		GridPane gp=new GridPane();
		gp.getStyleClass().add("grid");
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setGridLinesVisible(false);

		Label titleLabel = new Label(" View Sales Invoice");
		GridPane.setMargin(titleLabel, new Insets(0,-400,0,400));
		gp.add(titleLabel, 0, 0);

		Label custCreatLbl=new Label("Customer Details");
//		GridPane.setMargin(custCreatLbl, new Insets(0,-100,0,100));
		gp.add(custCreatLbl, 1, 1);

//		long entry_id=

		long invoiceId=salesEntry.getInvoice_no();
		int length=String.valueOf(invoiceId).length();
		Label saleInvoiceLbl =new Label();
		saleInvoiceLbl.setText(String.format("SI"+"%0"+(6-length)+"d", (invoiceId)));
		GridPane.setMargin(saleInvoiceLbl, new Insets(0,0,0,-200));
		gp.add(saleInvoiceLbl, 6, 0);

//		invoicDatePick.setValue(LocalDate.now());
		invoicDatePick= new JFXDatePicker();
//		invoicDatePick.setStyle("-fx-background-color:red");
		invoicDatePick.setPromptText("Entry Date");
		invoicDatePick.setMaxWidth(150);
		invoicDatePick.setEditable(false);
		invoicDatePick.setDisable(true);
		LocalDate ld = LocalDate.parse(salesEntry.getEntry_date().toString());
//		invoicDatePick.setStyle("-fx-font-size:13");
		invoicDatePick.getStyleClass().add("date-pick");
		invoicDatePick.setStyle("-fx-opacity:1");
		invoicDatePick.setShowWeekNumbers(false);
		invoicDatePick.setValue(ld);
//		GridPane.setMargin(custCombo, new Insets(0,0,0,20));
		GridPane.setMargin(invoicDatePick, new Insets(0,-50,0,0));
//		invoicDatePick.setMinWidth(100);
//		KeyEventHandler.dateKeyEvent(invoicDatePick);
		gp.add(invoicDatePick, 0, 1);

		HBox custComboHB= new HBox();

		custCombo= new JFXComboBox<>();
		custCombo.setLabelFloat(true);
		custCombo.setEditable(false);
		custCombo.setDisable(true);
		custCombo.setStyle("-fx-font-size:12;-fx-opacity:1");
//		System.out.println("State"+salesEntry.getCustomer().getState());
		custCombo.setPromptText("Customer Name");
		custCombo.getStyleClass().add("jf-combo-box");
//		GridPane.setMargin(custCombo, new Insets(0,0,0,20));
//		custCombo.setEditable(true);
//		gp.add(custCombo, 0, 2);

		ObservableList<String> custObsData = FXCollections.observableArrayList();
		List<Customer> custList= salesDAO.getCustomerNames();
		Iterator<Customer> itr= custList.iterator();
		while(itr.hasNext()) {
			Customer customer=itr.next();
			custObsData.add(customer.getFull_name().replaceAll("null", ""));
		
		}
		
		
		custCombo.setItems(custObsData);
//		new AutoCompleteComboBoxListener<>(custCombo);
		

		
//		custCombo.setConverter(new StringConverter<String>() {
//
//			@Override
//			public String toString(String object) {
//				// TODO Auto-generated method stub
//				if(object==null)return null;
//
//				return object.toString();
//			}
//
//			@Override
//			public String fromString(String string) {
//				// TODO Auto-generated method stub
////				Customer customer=new Customer();
////				for(String cust:custCombo.getItems()) {
////					cust
////				}
//				return string;
//			}
//		});

//		address.clear();
		address= new JFXTextField();
		address.setEditable(false);
		address.setPromptText("Address");
		address.setText(salesEntry.getCustomer().getAddress().replaceAll("null", ""));
		address.setStyle("-fx-font-size:12");
		address.setLabelFloat(true);
		address.setMaxWidth(80);

//		prmConTxt.clear();
		prmConTxt= new JFXTextField();
		prmConTxt.setEditable(false);
		prmConTxt.setPromptText("Contact Number");
		prmConTxt.setText(salesEntry.getCustomer().getContact().replaceAll("null", ""));
		prmConTxt.setStyle("-fx-font-size:12");
		prmConTxt.setLabelFloat(true);
		prmConTxt.setMaxWidth(80);

//		stateCombo.getItems().clear();
		stateCombo= new JFXComboBox<>();
		stateCombo.setEditable(false);
		stateCombo.setDisable(true);
		stateCombo.setPromptText("State");
		stateCombo.setStyle("-fx-font-size:12;-fx-opacity:1");
		stateCombo.setLabelFloat(true);
//		stateCombo.setMaxWidth(10);
		stateCombo.setItems(fillStateCombo());
		stateCombo.setValue(salesEntry.getCustomer().getState());
		
		salesManCombo= new JFXComboBox<>();
		salesManCombo.setEditable(false);
		salesManCombo.setDisable(true);
		salesManCombo.setLabelFloat(true);
		salesManCombo.setPromptText("Sales Man");
		salesManCombo.setStyle("-fx-font-size:14;-fx-opacity:1");
		salesManData.clear();
		List<SalesMan> saleManList= salesDAO.showSalesManList();
		for(SalesMan s:saleManList) {
			if(s.getSalesManName()!=null||!s.getSalesManName().equals("")) {
				salesManData.add(s.getSalesManName());	
			}
		}
		salesManCombo.getStyleClass().add("jf-combo-box");
		salesManCombo.setItems(salesManData);
//		System.out.println(salman);
		
//		new AutoCompleteComboBoxListener<>(salesManCombo);
		
		GridPane.setMargin(salesManCombo, new Insets(0,-50,0,50));
		gp.add(salesManCombo, 0, 12);
		
//		stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent event) {
//				// TODO Auto-generated method stub
////				errorTip.hide();
//				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
//				if(s!=null) {
//					stateCombo.requestFocus();
//					stateCombo.getSelectionModel().select(s);
//
//				}
//			}
//		});

//		gstTxt.clear();
		gstTxt= new JFXTextField();
		gstTxt.setPromptText("GSTIN");
		gstTxt.setEditable(false);
		gstTxt.setText(salesEntry.getCustomer().getGstin().replaceAll("null", ""));
		gstTxt.setLabelFloat(true);
		gstTxt.setMaxWidth(100);
		gstTxt.setStyle("-fx-font-size:12");
		
		


		custComboHB.getChildren().addAll(custCombo,address,stateCombo,prmConTxt,gstTxt);
		custComboHB.setSpacing(20);
		GridPane.setMargin(custComboHB, new Insets(0,0,0,-200));
		gp.add(custComboHB, 2, 2);
		
		custCombo.setValue(salesEntry.getCustomer().getFull_name().replaceAll("null", ""));
		salesManCombo.setValue(salesEntry.getSalesManName());
//		custCombo.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
////				System.out.println("Reached outside");
//				for(Customer c:custList) {
////					System.out.println("Reached");
//					if(custCombo.getValue()!=null) {
//					if(custCombo.getValue().equals(c.getFull_name())) {
//						address.setText(c.getAddress());
//						stateCombo.setValue(c.getState());
//						prmConTxt.setText(c.getContact());
//						gstTxt.setText(c.getGstin());
//					}
//					}
//				}
//			}
//		});
		
		
		
//		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
//		     public DateCell call(final DatePicker datePicker) {
//		         return new DateCell() {
//		             @Override public void updateItem(LocalDate item, boolean empty) {
////		                 super.updateItem(item, empty);
//
//
//		                 if (item.isAfter(LocalDate.now())) {
//		                     // Tomorrow is too soon.
//		                     setDisable(true);
//		                 }
//		             }
//		         };
//		     }
//		 };
//		 invoicDatePick.setDayCellFactory(dayCellFactory);

		HBox prodHB = new HBox();

		Label addProdLbl =  new Label("Add Product");
		GridPane.setMargin(addProdLbl, new Insets(0,-50,0,0));
		gp.add(addProdLbl, 0, 4);
//		Label prodLbl = new Label("Product");
//		gp.add(prodLbl, 0, 5);
		prodTxt= new JFXComboBox<>();
		prodTxt.setPromptText("Select Product");
		prodTxt.setStyle("-fx-font-size:12");
		prodTxt.getStyleClass().add("jf-combo-box");
//		new AutoCompleteComboBoxListener<>(prodTxt);
//		prodTxt.setValue(salesEntry);
		prodTxt.setLabelFloat(true);
		prodTxt.setEditable(true);
		prodTxt.setMaxWidth(100);

		prodTxt.getItems().clear();
		prodList.clear();

		productList=goodsDAO.showProductList();

		for(Product p:productList) {
			if(p.getCategory()!=null) {
				prodList.add(p.getProduct_name()+"        "+p.getCategory().replaceAll("null", "")+"("+p.getSubGroup().replaceAll("null", "")+")");
				}
		}

		if(!prodList.isEmpty()) {
			prodTxt.getItems().addAll(prodList);
		}

//		prodTxt.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent event) {
//				// TODO Auto-generated method stub
////				errorTip.hide();
//				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), prodTxt.getValue(), prodTxt.getItems());
//				if(s!=null) {
//					prodTxt.requestFocus();
//					prodTxt.getSelectionModel().select(s);
//
//				}
//			}
//		});

//		new AutoCompleteComboBoxListener<>(prodTxt);

//		prodTxt.setConverter(new StringConverter<String>() {
//
//			@Override
//			public String toString(String object) {
//				// TODO Auto-generated method stub
//				if(object==null)return null;
//
//				return object.toString();
//			}
//
//			@Override
//			public String fromString(String string) {
//				// TODO Auto-generated method stub
////				Customer customer=new Customer();
////				for(String cust:custCombo.getItems()) {
////					cust
////				}
//				return string;
//			}
//		});

//		hsnTxt.clear();
		hsnTxt= new JFXTextField();
		hsnTxt.setPromptText("HSN/SAC");
		hsnTxt.setLabelFloat(true);
		hsnTxt.setEditable(false);
		hsnTxt.setStyle("-fx-font-size:12");

//		unitCombo.setLabelFloat(true);
		unitCombo= new JFXTextField();
		unitCombo.setPromptText("Select Unit");
		unitCombo.setStyle("-fx-font-size:12");
		unitCombo.setMaxWidth(80);
		unitCombo.setEditable(false);
//		unitCombo.getId().addAll(goodsController.fillUnitList());
//		new AutoCompleteComboBoxListener<>(unitCombo);
//		unitCombo.setConverter(new StringConverter<String>() {
//
//			@Override
//			public String toString(String object) {
//				// TODO Auto-generated method stub
//				if(object==null)return null;
//
//				return object.toString();
//			}
//
//			@Override
//			public String fromString(String string) {
//				// TODO Auto-generated method stub
////				Customer customer=new Customer();
////				for(String cust:custCombo.getItems()) {
////					cust
////				}
//				return string;
//			}
//		});
		
		quantityTxt= new JFXTextField();
		quantityTxt.setPromptText("Quantity");
		quantityTxt.setStyle("-fx-font-size:12");
		quantityTxt.setText("0");
		quantityTxt.setLabelFloat(true);
		quantityTxt.setMaxWidth(50);
		
		rateTxt= new JFXTextField();
		rateTxt.setLabelFloat(true);
		rateTxt.setMaxWidth(50);
		rateTxt.setStyle("-fx-font-size:12");
		rateTxt.setPromptText("Rate");
		rateTxt.setText("0.00");
		
		discTxt= new JFXTextField();
		discTxt.setMaxWidth(50);
		discTxt.setLabelFloat(true);
		discTxt.setStyle("-fx-font-size:12");
		discTxt.setPromptText("Discount");
		discTxt.setText("0.0");

//		taxableTxt.setPromptText("Taxable"+"\n Amt");
//		taxableTxt.setText("0");
//		taxableTxt.setLabelFloat(true);
//		taxableTxt.setMaxWidth(50);
//		taxableTxt.setEditable(false);
//		taxableTxt.setStyle("-fx-font-size:12");
		
		cGstTxt= new JFXTextField();
		cGstTxt.clear();
		cGstTxt.setPromptText("CGST");
		cGstTxt.setMaxWidth(50);
		cGstTxt.setEditable(false);
		cGstTxt.setStyle("-fx-font-size:12");
		cGstTxt.setLabelFloat(true);

//		gstRsTxt.clear();
		gstRsTxt= new JFXTextField();
		gstRsTxt.setPromptText("SGST");
		gstRsTxt.setMaxWidth(50);
		gstRsTxt.setEditable(false);
		gstRsTxt.setStyle("-fx-font-size:12");
		gstRsTxt.setLabelFloat(true);

//		iGstTxt.clear();
		iGstTxt= new JFXTextField();
		iGstTxt.setPromptText("IGST");
		iGstTxt.setMaxWidth(50);
		iGstTxt.setEditable(false);
		iGstTxt.setStyle("-fx-font-size:12");
		iGstTxt.setLabelFloat(true);

//		cessTxt.clear();
		cessTxt= new JFXTextField();
		cessTxt.setPromptText("Cess");
		cessTxt.setMaxWidth(50);
		cessTxt.setEditable(false);
		cessTxt.setStyle("-fx-font-size:12");
		cessTxt.setLabelFloat(true);

		totalTxt= new JFXTextField();
		totalTxt.setEditable(false);
		totalTxt.setPromptText("Total");
		totalTxt.setText("0.00");
		totalTxt.setLabelFloat(true);
		totalTxt.setStyle("-fx-font-size: 15px;-fx-font-weight:bold");
		totalTxt.setUnFocusColor(Color.TRANSPARENT);
		totalTxt.setMaxWidth(100);
		HBox.setMargin(totalTxt, new Insets(0, 0, 20, 20));


		stateCombo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				String state=stateCombo.getSelectionModel().getSelectedItem();
				if(state!=null) {
					prodTxt.setDisable(false);
				if(state.contains("Maharashtra")) {
					prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
					prodHB.getChildren().add(6, cGstTxt);
					prodHB.getChildren().add(7,gstRsTxt);
					HBox.setMargin(gstRsTxt, new Insets(0,-20,0,0));
//					GridPane.setMargin(prodHB, new Insets(0,0,0,50));
//					gp.setPadding(new Insets(10,10,10,10));
				}
				else {
					prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
					prodHB.getChildren().add(6,iGstTxt);
					prodHB.getChildren().add(7,cessTxt);
					HBox.setMargin(cessTxt, new Insets(0,-20,0,0));
//					GridPane.setMargin(prodHB, new Insets(0,0,0,50));
//					HBox.setMargin(totalTxt, new Insets(0,5,0,-50));
				}
			}
			}
		});

		JFXButton addMoreBtn = new JFXButton(" Add ");
		addMoreBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");
		addMoreBtn.setMaxWidth(80);
		addMoreBtn.setDisable(true);

		JFXButton editBtn=new JFXButton("Edit");
		editBtn.setMaxWidth(80);
		editBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");

		RadioButton addItem = new RadioButton("Add Item");
		addItem.setUserData("Add Item");
		addItem.setSelected(true);
		RadioButton editItem =new RadioButton("Edit Item");
		editItem.setUserData("Edit Item");
//		editItem.setDisable(true);
		ToggleGroup tg = new ToggleGroup();
		addItem.setToggleGroup(tg);
		editItem.setToggleGroup(tg);
		VBox vb1 = new VBox();
		vb1.setStyle("-fx-font-size:15px; -fx-font-weight:bold;");
		vb1.getChildren().addAll(addItem,editItem);
		HBox.setMargin(vb1, new Insets(0,0,0,20));
		prodHB.getChildren().addAll(prodTxt,hsnTxt,unitCombo,quantityTxt,rateTxt,discTxt,totalTxt,addMoreBtn,vb1);

		prodHB.setSpacing(20);
//		GridPane.setMargin(prodHB, new Insets(0,-50,0,0));
//		gp.add(prodHB, 0, 6,8,1);

//		String state=stateCombo.getValue();
//		if(state!=null) {
//			if(state.contains("Maharashtra")){
//				prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
//				prodHB.getChildren().add(6, cGstTxt);
//				prodHB.getChildren().add(7,gstRsTxt);
//				HBox.setMargin(gstRsTxt, new Insets(0,-20,0,0));
//			}else {
//				prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
//				prodHB.getChildren().add(6,iGstTxt);
//				prodHB.getChildren().add(7,cessTxt);
//				HBox.setMargin(cessTxt, new Insets(0,-20,0,0));
//
//		}
//		}

//		Label grTotalLbl = new Label("Grand Total");
//		gp.add(grTotalLbl, 4, 9);
//		grTotalTxt.setText("00.00");
//		grTotalTxt.clear();

		ObservableList<SalesProduct> prodData = FXCollections.observableArrayList();
		List<SalesProduct> salesList=salesDAO.getProductDetails(salesEntry);
		for(SalesProduct sp:salesList) {
//			System.out.println("ST"+sp.getSubTotal());
			double gst=sp.getSubTotal()*(sp.getProduct().getGst()/100);
			String rateString=BigDecimal.valueOf(sp.getRate())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();
			String discString=BigDecimal.valueOf(sp.getDiscount())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();

			String subTotalString=BigDecimal.valueOf(sp.getSubTotal())
					.setScale(2,RoundingMode.HALF_UP)
					.toPlainString();

			String totalString=BigDecimal.valueOf(sp.getTotal())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();
			

			if(stateCombo.getValue().contains("Maharashtra")) {
//				System.out.println("GST"+sp.getProduct().getGst());
			String cgst=String.format("%.2f", (gst/2))+"\n("+(sp.getProduct().getGst()/2)+"%)";
			String sgst=String.format("%.2f", (gst/2))+"\n("+(sp.getProduct().getGst()/2)+"%)";
//			System.out.println("rate"+sp.getRate());
//			System.out.println("Quantity"+sp.getProduct().getQuantity());

			prodData.add(new SalesProduct(sp.getProd_name(),sp.getId(),sp.getProduct().getHsnNo(),sp.getQuantity(),sp.getPrevQty(),sp.getUnit(),sp.getRate(),sp.getDiscount(),sp.getTotal(),sp.getSubTotal(),cgst,sgst,"","",sp.getProduct(),rateString,discString,subTotalString,totalString,sp.getProduct().getQuantity()));
			}
			else {
				String igst=String.format("%.2f", gst)+"\n("+(sp.getProduct().getGst())+"%)";
				String cess="0";

//				System.out.println("quantit"+sp.getQuantity());
				prodData.add(new SalesProduct(sp.getProd_name(),sp.getId(),sp.getProduct().getHsnNo(),sp.getQuantity(),sp.getPrevQty(),sp.getUnit(),sp.getRate(),sp.getDiscount(),sp.getTotal(),sp.getSubTotal(),"","",igst,cess,sp.getProduct(),rateString,discString,subTotalString,totalString,sp.getProduct().getQuantity()));
				}

			}

//		prodData.addAll(salesList);


		TableView<SalesProduct> prodView = new TableView<SalesProduct>();
		prodView.setMaxSize(1100, 200);
		TableColumn<SalesProduct, String> prodName = new TableColumn<SalesProduct, String>("Product Name");
		prodName.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
		prodName.setPrefWidth(150);
		TableColumn<SalesProduct, Long> hsnCol = new TableColumn<SalesProduct, Long>("HSN/"+"\nSAC");
		hsnCol.setCellValueFactory(new PropertyValueFactory<>("hsnNo"));
		hsnCol.setPrefWidth(90);
		TableColumn<SalesProduct, String> unitCol = new TableColumn<SalesProduct, String>("Unit");
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		unitCol.setPrefWidth(100);
		TableColumn<SalesProduct, Integer> quantityCol = new TableColumn<SalesProduct, Integer>("Qty.");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		quantityCol.setPrefWidth(90);
		TableColumn<SalesProduct, SalesProduct> rateCol = new TableColumn<SalesProduct, SalesProduct>("Rate");
		rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
		rateCol.setPrefWidth(100);
		TableColumn<SalesProduct, SalesProduct> discCol = new TableColumn<SalesProduct, SalesProduct>("Disc.\n"+"Rs.");
		discCol.setCellValueFactory(new PropertyValueFactory<>("discountString"));
		discCol.setPrefWidth(80);
		TableColumn<SalesProduct, SalesProduct> taxableCol = new TableColumn<SalesProduct, SalesProduct>("Taxable\n"+" Amount");
		taxableCol.setCellValueFactory(new PropertyValueFactory<>("subTotalString"));
		taxableCol.setPrefWidth(100);
		TableColumn<SalesProduct, SalesProduct> cgstCol = new TableColumn<SalesProduct, SalesProduct>("CGST\n"+"  (%)");
		cgstCol.setCellValueFactory(new PropertyValueFactory<>("cgst"));
		cgstCol.setPrefWidth(90);
		TableColumn<SalesProduct, SalesProduct> sgstCol = new TableColumn<SalesProduct, SalesProduct>("SGST\n"+"  (%)");
		sgstCol.setCellValueFactory(new PropertyValueFactory<>("sgst"));
		sgstCol.setPrefWidth(90);
		TableColumn<SalesProduct, SalesProduct> igstCol = new TableColumn<SalesProduct, SalesProduct>("IGST\n"+" (%)");
		igstCol.setCellValueFactory(new PropertyValueFactory<>("igst"));
		igstCol.setPrefWidth(90);
		TableColumn<SalesProduct, SalesProduct> cessCol = new TableColumn<SalesProduct, SalesProduct>("Cess");
		cessCol.setCellValueFactory(new PropertyValueFactory<>("cess"));
		cessCol.setPrefWidth(80);
		TableColumn<SalesProduct, SalesProduct> totalCol = new TableColumn<SalesProduct, SalesProduct>("Total");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
		totalCol.setPrefWidth(100);
		TableColumn<SalesProduct, SalesProduct> actionCol = new TableColumn<SalesProduct, SalesProduct>("Action");
		actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
		actionCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>(){
//			Button deleteBtn = new Button("Delete");

			@Override
		    protected void updateItem(SalesProduct salesProduct,boolean empty){
				if(salesProduct==null){
					setGraphic(null);
					return;
				}else{
					deleteBtn=new JFXButton("Delete");
					setGraphic(deleteBtn);

//					deleteBtn.setDisable(false);
					deleteBtn.getStyleClass().add("del-btn");
					deleteBtn.setAlignment(Pos.CENTER);
					deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							// TODO Auto-generated method stub
							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
//							alert.setHeaderText("HI");
							Optional<ButtonType> result= alert.showAndWait();
							if(result.get()==ButtonType.OK){
								getTableView().getItems().remove(salesProduct);
								getTableView().refresh();
								grandTotal=0;
								if(prodData.size()==0)
									grTotalTxt.setText("0.0");
								else{
								for(SalesProduct p:prodData){
									grandTotal=grandTotal+p.getTotal();
									grTotalTxt.setText(String.format("%.2f",grandTotal));

								}
								}
							}

						}
					});


				}
			}
		});

		prodView.getColumns().addAll(prodName,hsnCol,unitCol,quantityCol,rateCol,discCol,taxableCol,cgstCol,sgstCol,igstCol,cessCol,totalCol);
		prodView.setItems(prodData);
		prodView.requestFocus();
		prodView.getSelectionModel().selectFirst();
		prodView.getFocusModel().focus(0);
//		stateCombo.requestFocus();

//		prodTxt.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
//				if(prodTxt.getValue()!=null){
//					addMoreBtn.setDisable(false);
//					editBtn.setDisable(false);
//					if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
//						for(Product p:productList) {
//							if(prodTxt.getValue().contains(p.getProduct_name())) {
//								double gst=p.getGst()/2;
//								gstRate=p.getGst();
////								cessRate=p.getCess();
//								cGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
//								gstRsTxt.setText(String.valueOf("0 ("+gst+"%)"));
//								hsnTxt.setText(String.valueOf(p.getHsnNo()));
//								rateTxt.setText(String.valueOf(p.getSellPrice()));
//								unitCombo.setText(String.valueOf(p.getUnit()));
//							}
//
//
//						}
//
//					}
//
//					else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
//						for(Product p:productList) {
//							if(prodTxt.getValue().contains(p.getProduct_name())) {
//								double gst=p.getGst();
//								double cess=p.getCess();
//								gstRate=p.getGst();
//								cessRate=p.getCess();
//								iGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
//								cessTxt.setText(String.valueOf("0 ("+cess+"%)"));
//								hsnTxt.setText(String.valueOf(p.getHsnNo()));
//								rateTxt.setText(String.valueOf(p.getSellPrice()));
//								unitCombo.setText(String.valueOf(p.getUnit()));
//							}
//
//
//						}
//					}
////					return;
//				}
//			}
//		});
//
//		unitCombo.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
//				if(unitCombo.getText()!=null) {
//				addMoreBtn.setDisable(false);
//				editBtn.setDisable(false);
//
//				}
//			}
//		});
//
//		addMoreBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
////				clearLabels();
////				gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
////					System.out.println(validateControls());
//				boolean productFlag=false;
//				boolean unitFlag=false;
//					if(!validateOnAddEdit()){
////
//					return;
//				}
////					deleteBtn.setDisable(false);
//					SalesProduct salesProduct = new SalesProduct();
//					Product product = new Product();
//					salesProduct.setProduct(product);
//					for(Product p:productList) {
//						if(prodTxt.getValue().contains(p.getProduct_name())&& Long.parseLong(hsnTxt.getText())==p.getHsnNo()) {
//							salesProduct.getProduct().setId(p.getId());
//							salesProduct.getProduct().setProduct_name(p.getProduct_name());
//							salesProduct.setProd_name(p.getProduct_name());
//							salesProduct.setGst(p.getGst());
//							salesProduct.setHsnNo(Long.parseLong(hsnTxt.getText()));
//							salesProduct.setCurrentStock(p.getQuantity());
//							productFlag=true;
//						}
//					}
//					if(!productFlag) {
//						errorTip.setText("No Product found with specified name!");
//						prodTxt.setTooltip(errorTip);
//						errorTip.show(prodTxt,100,160);
//						return;
//					}
//
////					salesProduct.setProd_name(prodTxt.getValue());
//
////					System.out.println(unitCombo.getValue());
////					if(!unitCombo.getId().contains(unitCombo.getText())) {
////						errorTip.setText("Unit Name not found!");
////						unitCombo.setTooltip(errorTip);
////						errorTip.show(unitCombo,300,160);
////						return;
////					}
//
//					salesProduct.setUnit(unitCombo.getText());
//					salesProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
//
//					String trunRateAmt=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
//						    .setScale(2, RoundingMode.HALF_UP)
//						    .toPlainString();
//					salesProduct.setRate(Double.parseDouble(rateTxt.getText()));
//					salesProduct.setRateString(trunRateAmt);
//
//					String trunDiscAmt=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
//						    .setScale(2, RoundingMode.HALF_UP)
//						    .toPlainString();
//					salesProduct.setDiscount(Double.parseDouble(discTxt.getText()));
//					salesProduct.setDiscountString(trunDiscAmt);
//
////					salesProduct.setGst(Double.parseDouble(gstRsTxt.getText()));
//					String cgstTxt=cGstTxt.getText().replace("(", "\n(");
//					salesProduct.setCgst(cgstTxt);
//
//					String igstTxt=iGstTxt.getText().replace("(", "\n(");
//					salesProduct.setIgst(igstTxt);
//
//					String sgstTxt=gstRsTxt.getText().replace("(", "\n(");
//					salesProduct.setSgst(sgstTxt);
//
//					String cesssTxt=cessTxt.getText().replace("(", "\n(");
//					salesProduct.setCess(cesssTxt);
//
//					String trunTaxablAmt=BigDecimal.valueOf(taxableAmt)
//						    .setScale(2, RoundingMode.HALF_UP)
//						    .toPlainString();
//					salesProduct.setSubTotalString(trunTaxablAmt);
//					double taxAmt=BigDecimal.valueOf(taxableAmt)
//							.setScale(2,RoundingMode.HALF_UP)
//							.doubleValue();
//					
//					salesProduct.setSubTotal(taxAmt);
//
//					String trunTotalAmt=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
//						    .setScale(2, RoundingMode.HALF_UP)
//						    .toPlainString();
//					salesProduct.setTotalString(trunTotalAmt);
//					salesProduct.setTotal(Double.parseDouble(totalTxt.getText()));
//					for(SalesProduct sp:prodData) {
//						if(sp.getProd_name().equals(salesProduct.getProd_name())) {
////							dupliErrLbl.setText("Duplicate entries are not permitted");
////							gp.add(dupliErrLbl, 0, 7);
//							errorTip.setText("Duplicate entries are not permitted");
//							prodTxt.setTooltip(errorTip);
//							errorTip.show(prodTxt,100,200);
//							return;
//						}
//					}
//
//					prodData.add(salesProduct);
//					prodView.setItems(prodData);
//					prodView.requestFocus();
//					prodView.getSelectionModel().selectLast();
//					cashCheck.requestFocus();
//					grandTotal=grandTotal+salesProduct.getTotal();
//					grTotalTxt.setText(String.format("%.2f",grandTotal));
//
//					clearProductData();
//					editItem.setDisable(false);
//			}
//		});

		GridPane.setMargin(prodView, new Insets(-20,-50,0,0));
		gp.add(prodView, 0, 8,12,1);


//		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
//				// TODO Auto-generated method stub
//				if(tg.getSelectedToggle()!=null) {
//					String item=tg.getSelectedToggle().getUserData().toString();
//					if(item.equals("Add Item")) {
//						prodHB.getChildren().removeAll(editBtn,addMoreBtn);
//						prodHB.getChildren().add(9,addMoreBtn);
//						deleteBtn.setDisable(false);
//						clearProductData();
//					}else if(item.equals("Edit Item")) {
//						deleteBtn.setDisable(true);
//						SalesProduct salesProduct = prodView.getSelectionModel().getSelectedItem();
//						int index = prodView.getSelectionModel().getSelectedIndex();
//						prodTxt.setValue(salesProduct.getProd_name());
//						unitCombo.setText(salesProduct.getUnit());
//						hsnTxt.setText(String.valueOf(salesProduct.getHsnNo()));
//						salesProduct.setPrevQty(salesProduct.getQuantity());
//						quantityTxt.setText(String.valueOf(salesProduct.getQuantity()));
//
//						String trunRateAmt=BigDecimal.valueOf(salesProduct.getRate())
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//
//						rateTxt.setText(trunRateAmt);
//
//						String trunDiscAmt=BigDecimal.valueOf(salesProduct.getDiscount())
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						discTxt.setText(trunDiscAmt);
//
//						gstRsTxt.setText(String.valueOf(salesProduct.getSgst()));
//						cGstTxt.setText(String.valueOf(salesProduct.getCgst()));
//						iGstTxt.setText(String.valueOf(salesProduct.getIgst()));
//						cessTxt.setText(String.valueOf(salesProduct.getCess()));
//
////						String trunTaxableAmt=BigDecimal.valueOf(salesProduct.getSubTotal())
////							    .setScale(2, RoundingMode.HALF_UP)
////							    .toPlainString();
////						taxableTxt.setText(trunTaxableAmt);
//
//						String trunTotalAmt=BigDecimal.valueOf(salesProduct.getTotal())
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						totalTxt.setText(trunTotalAmt);
//
//						grandTotal=grandTotal-salesProduct.getTotal();
//						prodHB.getChildren().remove(addMoreBtn);
//						prodHB.getChildren().add(9, editBtn);
//
//						editBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//							@Override
//							public void handle(ActionEvent event) {
//								// TODO Auto-generated method stub
////								clearLabels();
////								gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
//								if(!validateOnAddEdit()){
//									return;
//								}
//
////								PurchaseProduct purchaseProduct1 = new PurchaseProduct();
//								salesProduct.setProd_name(prodTxt.getValue());
//								salesProduct.setUnit(unitCombo.getText());
//								salesProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
//								String trunRateAmt1=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
//										.setScale(3, RoundingMode.HALF_UP)
//										.toPlainString();
//								salesProduct.setRateString(trunRateAmt1);
//
//								salesProduct.setRate(Double.parseDouble(rateTxt.getText()));
//								String trunDiscAmt1=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
//										.setScale(3, RoundingMode.HALF_UP)
//										.toPlainString();
//								salesProduct.setDiscountString(trunDiscAmt1);
//
//								salesProduct.setDiscount(Double.parseDouble(discTxt.getText()));
////								salesProduct.setGst(Double.parseDouble());
//
//								String cgstTxt=cGstTxt.getText().replace("(", "\n(");
//								salesProduct.setCgst(cgstTxt);
//
////							if(iGstTxt.getText().contains("(")) {/
//
//								String igstTxt=iGstTxt.getText().replace("(", "\n(");
//								salesProduct.setIgst(igstTxt);
////							}
////							salesProduct.setSgst(gstRsTxt.getText());
////							if(gstRsTxt.getText().contains("(")) {
//								String sgstTxt=gstRsTxt.getText().replace("(", "\n(");
//								salesProduct.setSgst(sgstTxt);
////							}
//
////							salesProduct.setCess(cessTxt.getText());
//							if(cessTxt.getText().contains("(")) {
//								String cesssTxt=cessTxt.getText().replace("(", "\n(");
//								salesProduct.setCess(cesssTxt);
//							}
//
//								String trunTaxablAmt=BigDecimal.valueOf(taxableAmt)
//								    .setScale(2, RoundingMode.HALF_UP)
//								    .toPlainString();
//
//									double taxAmt=BigDecimal.valueOf(taxableAmt)
//											.setScale(2,RoundingMode.HALF_UP)
//											.doubleValue();
//									
//								salesProduct.setSubTotal(taxAmt);
//						salesProduct.setSubTotalString(trunTaxablAmt);
//
//								String trunTotalAmt1=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
//										.setScale(3, RoundingMode.HALF_UP)
//										.toPlainString();
//								salesProduct.setTotalString(trunTotalAmt1);
//								salesProduct.setTotal(Double.parseDouble(totalTxt.getText()));
//
//								prodData.set(index,salesProduct);
//								cashCheck.requestFocus();
//								grandTotal=0;
//								for(SalesProduct p:prodData) {
//									grandTotal=grandTotal+p.getTotal();
//								}
//								grTotalTxt.setText(String.format("%.2f",grandTotal));
//								prodHB.getChildren().remove(editBtn);
//								prodHB.getChildren().add(10, addMoreBtn);
//								addItem.setSelected(true);
//								clearProductData();
//							}
//						});
//
//					}
//				}
//			}
//		});

		HBox paymentBox= new HBox();
		
		grTotalTxt= new JFXTextField();
		grTotalTxt.setFocusTraversable(true);
		grTotalTxt.setLabelFloat(true);
		grTotalTxt.setFocusTraversable(true);
		grTotalTxt.setText(String.valueOf(salesEntry.getTotal()));

		grTotalTxt.setEditable(false);
		grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
		grTotalTxt.setFocusColor(Color.TRANSPARENT);
//		System.out.println("GRand Total"+grandTotal);

		grandTotal=salesEntry.getTotal();
		grTotalTxt.setPromptText("Grand Total");
		grTotalTxt.setStyle("-fx-font-size: 20px; -fx-font-weight:bold");
		GridPane.setMargin(grTotalTxt, new Insets(20,-50,0,50));

		gp.add(grTotalTxt, 0, 10);

//		payCombo.getItems().clear();
//
//		payCombo.getItems().addAll("Full Payment","Part Payment","UnPaid");
//		payCombo.setValue(salesEntry.getPayment_type());
//		payCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent event) {
//				// TODO Auto-generated method stub
////				errorTip.hide();
//				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), payCombo.getValue(), payCombo.getItems());
//				if(s!=null) {
//					payCombo.requestFocus();
//					payCombo.getSelectionModel().select(s);
//
//				}
//			}
//		});
//		payCombo.setLabelFloat(true);
//		payCombo.setPromptText("Select Payment Type");
//		GridPane.setMargin(payCombo, new Insets(20,-50,0,50));
////		payCombo.requestFocus();
//		gp.add(payCombo, 0, 11);
//		partPayTxt.setPromptText("Part Pay Amount");
//		partPayTxt.setStyle("-fx-font-size:14");
//		partPayTxt.setLabelFloat(true);
//		partPayTxt.setText("0");
//
//		partPayTxt.setVisible(false);
//		GridPane.setMargin(partPayTxt, new Insets(20,-50,0,50));
//		gp.add(partPayTxt, 0, 12);
////		HBox hb = new HBox();
//
//		if(payCombo.getValue().equals("Part Payment")) {
//			partPayTxt.setVisible(true);
//			partPayTxt.setText(String.valueOf(salesEntry.getPaid_amount()));
//		}
//		payCombo.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
//				if(payCombo.getValue()!=null){
//				if(payCombo.getValue().equals("Full Payment")) {
//					partPayTxt.setVisible(false);
//					paymentBox.setVisible(true);
//					payModeLbl.setVisible(true);
//
//				}
//				else if(payCombo.getValue().equals("UnPaid")){
//					partPayTxt.setVisible(false);
//					paymentBox.setVisible(false);
//					payModeLbl.setVisible(false);
////					hb.setVisible(false);
//				}
//				else if(payCombo.getValue().equals("Part Payment")) {
////				gp.addRow(9, hb);
//				partPayTxt.setVisible(true);
//				paymentBox.setVisible(true);
//				payModeLbl.setVisible(true);
//				}
//			}}
//		});
//		Label payModeLbl = new Label("Payment Mode");
//		gp.add(payModeLbl, 4, 11);
//		VBox payModeHB= new VBox();

		PaymentMode paymentMode=salesDAO.getPaymentModes(salesEntry);

		
		payModeLbl.setStyle("-fx-font-size:14");
		GridPane.setMargin(payModeLbl, new Insets(-70,0,0,-50));
		gp.add(payModeLbl, 2, 10);
		
		cashCheck= new JFXCheckBox();
		cashCheck.setDisable(true);
		cashCheck.setSelected(false);
		cashCheck.setText("Pay by Cash");
		
		cashAmtTxt= new JFXTextField();
		cashAmtTxt.setEditable(false);
		cashAmtTxt.setPromptText("Amount");

//		cashAmtTxt.setVisible(false);
//		cashAmtTxt.setMaxWidth(50);
//		cashAmtTxt.setLabelFloat(true);
		cashAmtTxt.clear();
		cashAmtTxt.setStyle("-fx-font-size:14");

		VBox cashBox= new VBox();
		cashBox.setSpacing(10);
//		cashBox.setMaxHeight(100);
		cashBox.setSpacing(10);
		cashBox.getChildren().add(cashCheck);
		
		bankCheck= new JFXCheckBox();
		bankCheck.setDisable(true);
		bankCheck.setSelected(false);
		bankCheck.setText("Pay by Cheque/DD");
		
//		bankTxt.clear();
		bankTxt= new JFXTextField();
		bankTxt.setEditable(false);
		bankTxt.setPromptText("Bank Name");
//		bankTxt.setVisible(false);
		bankTxt.setStyle("-fx-font-size:14");


//		bankTxt.setMaxWidth(50);
//		bankTxt.setLabelFloat(true);/
//		cheqTxt.clear();
		cheqTxt= new JFXTextField();
		cheqTxt.setEditable(false);
		cheqTxt.setPromptText("Cheque No/DD No");
		cheqTxt.setStyle("-fx-font-size:14");
//		cheqTxt.setVisible(false);
//		cheqTxt.setMaxWidth(50);
//		cheqTxt.setLabelFloat(true);
//		bankAmtTxt.clear();
		bankAmtTxt= new JFXTextField();
		bankAmtTxt.setEditable(false);
		bankAmtTxt.setPromptText("Amount");
//		bankAmtTxt.setVisible(false);
//		bankAmtTxt.setLabelFloat(true);
		bankAmtTxt.setStyle("-fx-font-size:14");

		VBox bankBox= new VBox();
		bankBox.getChildren().addAll(bankCheck);
		bankBox.setSpacing(10);
		cardCheck= new JFXCheckBox();
		cardCheck.setDisable(true);
		cardCheck.setSelected(false);
		cardCheck.setText("Pay by Credit/Debit Card/NetBanking");
//		transId.clear();
		transId= new JFXTextField();
		transId.setEditable(false);
		transId.setPromptText("Transaction ID");
		transId.setStyle("-fx-font-size:14");
//		transId.setVisible(false);
//		transId.setMaxWidth(50);
//		transId.setLabelFloat(true);
//		cardAmtTxt.clear();
		cardAmtTxt= new JFXTextField();
		cardAmtTxt.setEditable(false);
		cardAmtTxt.setPromptText("Amount");
		cardAmtTxt.setStyle("-fx-font-size:14");
//		cardAmtTxt.setVisible(false);
//		cardAmtTxt.setMaxWidth(50);
//		cardAmtTxt.setLabelFloat(true);

		VBox cardBox= new VBox();
		cardBox.getChildren().add(cardCheck);
		cardBox.setSpacing(10);
		voucherCheck= new JFXCheckBox();
		voucherCheck.setDisable(true);
		voucherCheck.setSelected(false);
		voucherCheck.setText("Pay by Voucher/Wallet");
//		voucherTypeTxt.clear();
		voucherTypeTxt= new JFXTextField();
		voucherTypeTxt.setEditable(false);
		voucherTypeTxt.setPromptText("Voucher Code/Wallet Name");
//		voucherTypeTxt.setVisible(false);
		voucherTypeTxt.setStyle("-fx-font-size:14");
//		voucherTypeTxt.setMaxWidth(50);
//		voucherTypeTxt.setLabelFloat(true);
//		vouchAmtTxt.clear();
		vouchAmtTxt= new JFXTextField();
		vouchAmtTxt.setEditable(false);
		vouchAmtTxt.setPromptText("Amount");
//		vouchAmtTxt.setVisible(false);
		vouchAmtTxt.setStyle("-fx-font-size:14");
//		vouchAmtTxt.setMaxWidth(50);
//		vouchAmtTxt.setLabelFloat(true);

		VBox vouchBox= new VBox();
		vouchBox.getChildren().addAll(voucherCheck);
		vouchBox.setSpacing(10);

//		System.out.println(paymentList.size());
//		if(!salesEntry.getPayment_type().equals("UnPaid")) {
//		for(PaymentMode p:paymentList) {
				if(paymentMode.getCashMode()!=null) {
					if(!paymentMode.getCashMode().equals("null")) {
						cashCheck.setSelected(true);
						if(paymentMode.getCashAmount()!=0&&(!paymentMode.getBankMode().equals("null")&&!paymentMode.getCardMode().equals("null")&&!paymentMode.getVoucherMode().equals("null"))) {
							cashBox.getChildren().add(cashAmtTxt);
							cashAmtTxt.setText(String.valueOf(paymentMode.getCashAmount()));
						}
					}
				}
				if(paymentMode.getBankMode()!=null) {
					if(!paymentMode.getBankMode().equals("null")) {
						bankCheck.setSelected(true);
						bankTxt.setText(paymentMode.getBankName());
						cheqTxt.setText(paymentMode.getChequeNo());
						bankBox.getChildren().addAll(bankTxt,cheqTxt);
						if(paymentMode.getBankAmount()!=0) {
							bankBox.getChildren().add(bankAmtTxt);
							bankAmtTxt.setText(String.valueOf(paymentMode.getBankAmount()));
						}
					}
				}
				if(paymentMode.getCardMode()!=null) {
					if(!paymentMode.getCardMode().equals("null")) {
						cardCheck.setSelected(true);
						transId.setText(paymentMode.getTransId());
						cardBox.getChildren().add(transId);
						if(paymentMode.getCardAmount()!=0) {
							cardBox.getChildren().add(cardAmtTxt);
							cardAmtTxt.setText(String.valueOf(paymentMode.getCardAmount()));
						}
					}
				}

				if(paymentMode.getVoucherMode()!=null) {
					if(!paymentMode.getVoucherMode().equals("null")) {
						voucherCheck.setSelected(true);
						voucherTypeTxt.setText(paymentMode.getVoucherWalletCode());
						vouchBox.getChildren().add(voucherTypeTxt);
						if(paymentMode.getVoucherAmt()!=0) {
							vouchBox.getChildren().add(vouchAmtTxt);
							vouchAmtTxt.setText(String.valueOf(paymentMode.getVoucherAmt()));
						}
					}
				}
				
			
//			}
//		}
//
		paymentBox.setSpacing(20);
		paymentBox.setMaxSize(180, 50);
		paymentBox.setStyle("-fx-border-style: solid;-fx-padding:15;-fx-border-width: 2;-fx-border-insets:5; -fx-border-radius: 5;");
		paymentBox.getChildren().addAll(cashBox,bankBox,cardBox,vouchBox);


//		payModeCombo.getItems().clear();
//		payModeCombo.getItems().addAll("Cash","Cheque","NEFT/RTGS","Card");
//		new AutoCompleteComboBoxListener<>(payModeCombo);
//		payModeCombo.setLabelFloat(true);
//		payModeCombo.setPromptText("Select Payment Mode");

//		if(salesEntry.getPayment_type().equals("UnPaid")) {
//			paymentBox.setVisible(false);
//		}
		GridPane.setMargin(paymentBox, new Insets(-80,0,0,-50));
		gp.add(paymentBox, 2, 12);


//		cashCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				// TODO Auto-generated method stub
//
////				cashBox.getChildren().remove(cashAmtTxt);
//				if(newValue) {
//					cardBox.getChildren().remove(cardAmtTxt);
//					vouchBox.getChildren().remove(vouchAmtTxt);
//					bankBox.getChildren().remove(bankAmtTxt);
//
//					if(bankCheck.isSelected()||cardCheck.isSelected()||voucherCheck.isSelected()) {
//						cashBox.getChildren().add(cashAmtTxt);
//					}
//
//					boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);
//					boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
//					boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);
//
//				}
//				else {
//					cashAmtTxt.clear();
//					cashBox.getChildren().remove(cashAmtTxt);
//					if(!(bankCheck.isSelected()&&cardCheck.isSelected()&&voucherCheck.isSelected())) {
//						if(bankCheck.isSelected()&&!(cardCheck.isSelected()||voucherCheck.isSelected())) {
//							bankAmtTxt.clear();
//							bankBox.getChildren().remove(bankAmtTxt);
//						}
//						else if(cardCheck.isSelected()&&!(bankCheck.isSelected()||voucherCheck.isSelected())) {
//							cardAmtTxt.clear();
//							cardBox.getChildren().remove(cardAmtTxt);
//						}
//
//						else if(voucherCheck.isSelected()&&!(bankCheck.isSelected()||cardCheck.isSelected())) {
//							vouchAmtTxt.clear();
//							vouchBox.getChildren().remove(vouchAmtTxt);
//						}
//					}
//
////					cashAmtTxt.setVisible(false);
//				}
//
//			}
//		});
//
//
////
//		bankCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				// TODO Auto-generated method stub
//				if(newValue) {
//
//				bankBox.getChildren().addAll(bankTxt,cheqTxt);
//				if(cashCheck.isSelected()||cardCheck.isSelected()||voucherCheck.isSelected()) {
//					bankBox.getChildren().add(bankAmtTxt);
////					cashBox.getChildren().add(cashAmtTxt);
////					cardBox.getChildren().add(cardAmtTxt);
////					vouchBox.getChildren().add(vouchAmtTxt);
//				}
//
//				cashBox.getChildren().remove(cashAmtTxt);
//				cardBox.getChildren().remove(cardAmtTxt);
//				vouchBox.getChildren().remove(vouchAmtTxt);
//
//				boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
//				boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);
//				boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);
//
//
//			}
//			else{
//					bankBox.getChildren().removeAll(bankTxt,cheqTxt,bankAmtTxt);
//					bankTxt.clear();
//					cheqTxt.clear();
//					bankAmtTxt.clear();
//					if(!(cashCheck.isSelected()&&cardCheck.isSelected()&&voucherCheck.isSelected())) {
//						if(cashCheck.isSelected()&&!(cardCheck.isSelected()||voucherCheck.isSelected())) {
//							cashAmtTxt.clear();
//							cashBox.getChildren().remove(cashAmtTxt);
//						}
//						else if(cardCheck.isSelected()&&!(cashCheck.isSelected()||voucherCheck.isSelected())) {
//							cardAmtTxt.clear();
//							cardBox.getChildren().remove(cardAmtTxt);
//						}
//
//						else if(voucherCheck.isSelected()&&!(cashCheck.isSelected()||cardCheck.isSelected())) {
//							vouchAmtTxt.clear();
//							vouchBox.getChildren().remove(vouchAmtTxt);
//						}
//					}
//				}
//			}
//		});
//
//		cardCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				// TODO Auto-generated method stub
//
//			if(newValue) {
//				cardBox.getChildren().add(transId);
//
//
//					if(cashCheck.isSelected()||bankCheck.isSelected()||voucherCheck.isSelected()) {
////						bankBox.getChildren().add(bankAmtTxt);
////						cashBox.getChildren().add(cashAmtTxt);
//						cardBox.getChildren().add(cardAmtTxt);
////						vouchBox.getChildren().add(vouchAmtTxt);
//					}
//
//					cashBox.getChildren().remove(cashAmtTxt);
//					vouchBox.getChildren().remove(vouchAmtTxt);
//					bankBox.getChildren().remove(bankAmtTxt);
//
//					boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
//					boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
//					boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);
//
//
//
//			}
//			else {
//					cardBox.getChildren().removeAll(transId,cardAmtTxt);
//					transId.clear();
//					cardAmtTxt.clear();
//
//					if(!(cashCheck.isSelected()&&bankCheck.isSelected()&&voucherCheck.isSelected())) {
//						if(cashCheck.isSelected()&&!(bankCheck.isSelected()||voucherCheck.isSelected())) {
//							cashAmtTxt.clear();
//							cashBox.getChildren().remove(cashAmtTxt);
//						}
//						else if(bankCheck.isSelected()&&!(cashCheck.isSelected()||voucherCheck.isSelected())) {
//							bankAmtTxt.clear();
//							bankBox.getChildren().remove(bankAmtTxt);
//						}
//
//						else if(voucherCheck.isSelected()&&!(cashCheck.isSelected()||bankCheck.isSelected())) {
//							vouchBox.getChildren().remove(vouchAmtTxt);
//							vouchAmtTxt.clear();
//						}
//					}
//
//				}
//			}
//		});
//
//		voucherCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				// TODO Auto-generated method stub
//
//
//				if(newValue) {
//					vouchBox.getChildren().add(voucherTypeTxt);
//
//					if(cashCheck.isSelected()||bankCheck.isSelected()||cardCheck.isSelected()) {
//						vouchBox.getChildren().add(vouchAmtTxt);
//					}
//
//					cardBox.getChildren().remove(cardAmtTxt);
//					bankBox.getChildren().remove(bankAmtTxt);
//					cashBox.getChildren().remove(cashAmtTxt);
//
//					boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
//					boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
//					boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);
//
////						bankBox.getChildren().add(bankAmtTxt);
////						cashBox.getChildren().add(cashAmtTxt);
////						cardBox.getChildren().add(cardAmtTxt);
////						vouchBox.getChildren().add(vouchAmtTxt);
////					}
//				}
//			else {
//				vouchBox.getChildren().removeAll(voucherTypeTxt,vouchAmtTxt);
//				voucherTypeTxt.clear();
//				vouchAmtTxt.clear();
//				if(!(cashCheck.isSelected()&&bankCheck.isSelected()&&cardCheck.isSelected())) {
//					if(cashCheck.isSelected()&&!(bankCheck.isSelected()||cardCheck.isSelected())) {
//						cashAmtTxt.clear();
//						cashBox.getChildren().remove(cashAmtTxt);
//					}
//					else if(bankCheck.isSelected()&&!(cashCheck.isSelected()||cardCheck.isSelected())) {
//						bankAmtTxt.clear();
//						bankBox.getChildren().remove(bankAmtTxt);
//					}
//
//					else if(cardCheck.isSelected()&&!(cashCheck.isSelected()||bankCheck.isSelected())) {
//						cardAmtTxt.clear();
//						cardBox.getChildren().remove(cardAmtTxt);
//					}
//				}
//				}
//			}
//		});



//		JFXButton updateBtn = new JFXButton(" Update ");
//		gp.add(updateBtn, 2,13);
//		payModeCombo.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
//				if(payModeCombo.getValue()!=null){
//				if(!payModeCombo.getValue().equals("Cash")){
//					if(gp.getChildren().contains(hb)){
//						gp.getChildren().remove(hb);}
//					hb.setVisible(true);
//					gp.add(hb, 5, 14);
//				}
//				else{
//					if(gp.getChildren().contains(hb)){
//						gp.getChildren().remove(hb);
//					}
//			}
//				}
//			}
//		});

//		quantityTxt.textProperty().addListener(new ChangeListener<String>() {
//
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//				// TODO Auto-generated method stub
////				quantityErrLbl.setText("");
////				totErrLbl.setText("");
////				totalTxt.setText("");
////				if(gp.getChildren().contains(quantityErrLbl))
////					gp.getChildren().remove(quantityErrLbl);
//				errorTip.hide();
//
//				if(!validateProductControls()){
//					addMoreBtn.setDisable(true);
//					editBtn.setDisable(true);
////					gp.add(quantityErrLbl, 1, 7);
//
//					return;
//				}
//
//				for(Product p:productList) {
//					if(prodTxt.getValue()!=null) {
//					if(prodTxt.getValue().contains(p.getProduct_name())) {
//						if(p.getQuantity()<Integer.parseInt(newValue)) {
////							System.out.println(p.getQuantity());
//							errorTip.setText("Quantity must not exceed current stock");
//							quantityTxt.setTooltip(errorTip);
//							errorTip.show(quantityTxt,400,150);
//							addMoreBtn.setDisable(true);
//							editBtn.setDisable(true);
//							return;
//						}
//					}
//					}
//				}
//
////				totErrLbl.setText("");
//				addMoreBtn.setDisable(false);
//				editBtn.setDisable(false);
//				if(!newValue.isEmpty()){
//					if(!(rateTxt.getText().equals("")||discTxt.getText().equals(""))){
//						double total = (Double.parseDouble(newValue)*Double.parseDouble(rateTxt.getText()))-Double.parseDouble(discTxt.getText());
//
//
//						double subTotal = total*(100/(100+gstRate));
//						gstAmt=total-subTotal;
//						double halfGst=gstAmt/2;
//
//						if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
//
//							cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
//							gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
//						}
//						else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
//							iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
//							double subTotal1=subTotal*(100/(100+cessRate));
//							cessAmt=subTotal-subTotal1;
////							subTotalsub=subTotal+subTotal1;
//							cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
////							subTotal=subTotal+cess;
//						}
//						taxableAmt=subTotal;
//					totalTxt.setText(String.format("%.2f",total));}
//					else{
//						totalTxt.setText("NaN");
//					}
//			}
//				else{
//					quantityTxt.setText(quantityTxt.getPromptText());
//				}
//				}
//
//		});
//
//		rateTxt.textProperty().addListener(new ChangeListener<String>() {
//
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//				// TODO Auto-generated method stub
////				rateErrLbl.setText("");
////				totErrLbl.setText("");
////				totalTxt.setText("");
////				if(gp.getChildren().contains(rateErrLbl))
////					gp.getChildren().remove(rateErrLbl);
//				errorTip.hide();
//				if(!validateProductControls()){
//					addMoreBtn.setDisable(true);
//					editBtn.setDisable(true);
////					gp.add(rateErrLbl, 2, 7);
//					return;
//				}
//				if(!quantityTxt.getText().isEmpty()) {
//					for(Product p:productList) {
//						if(prodTxt.getValue()!=null) {
//						if(prodTxt.getValue().contains(p.getProduct_name())) {
//
//							if(p.getQuantity()<Integer.parseInt(quantityTxt.getText())) {
////								System.out.println(p.getQuantity());
//								errorTip.setText("Quantity must not exceed current stock");
//								quantityTxt.setTooltip(errorTip);
//								errorTip.show(quantityTxt,400,150);
//								addMoreBtn.setDisable(true);
//								editBtn.setDisable(true);
//								return;
//							}
//						}
//						}
//					}
//					}
//
////				totErrLbl.setText("");
//				addMoreBtn.setDisable(false);
//				editBtn.setDisable(false);
//				if(!newValue.equals("")){
////					if(newValue.matches(arg0))
//					if(!(quantityTxt.getText().equals("")||discTxt.getText().equals(""))){
//						double total = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(newValue))-Double.parseDouble(discTxt.getText());
//
//						double subTotal = total*(100/(100+gstRate));
//						gstAmt=total-subTotal;
//
//						double halfGst=gstAmt/2;
//						if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
//							cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
//							gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
//						}
//						else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
//							iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
//							double subTotal1=subTotal*(100/(100+cessRate));
//							cessAmt=subTotal-subTotal1;
////							subTotal=subTotal+subTotal1;
//							cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
////							subTotal=subTotal+cess;
//						}
//						taxableAmt=subTotal;
//				totalTxt.setText(String.format("%.2f",total));}
//					else{
//						totalTxt.setText("NaN");
//					}
//			}else
//				rateTxt.setText(rateTxt.getPromptText());
//				}
//		});
//
//		discTxt.textProperty().addListener(new ChangeListener<String>() {
//
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//				// TODO Auto-generated method stub
////				discErrLbl.setText("");
////				totErrLbl.setText("");
////				totalTxt.setText("");
////				if(gp.getChildren().contains(discErrLbl))
////					gp.getChildren().remove(discErrLbl);
//
//				if(!validateProductControls()){
////					System.out.println("Reched inside validation");
//					addMoreBtn.setDisable(true);
//					editBtn.setDisable(true);
////					gp.add(discErrLbl, 3, 7);
//					return;
//				}
//				if(!quantityTxt.getText().isEmpty()) {
//					for(Product p:productList) {
//						if(prodTxt.getValue()!=null) {
//						if(prodTxt.getValue().contains(p.getProduct_name())) {
//							if(p.getQuantity()<Integer.parseInt(quantityTxt.getText())) {
////								System.out.println(p.getQuantity());
//								errorTip.setText("Quantity must not exceed current stock");
//								quantityTxt.setTooltip(errorTip);
//								errorTip.show(quantityTxt,400,150);
//								addMoreBtn.setDisable(true);
//								editBtn.setDisable(true);
//
//								return;
//							}
//						}
//					}
//					}
//					}
//
////				totErrLbl.setText("");
////				System.out.println("Oustdie validation");
//				addMoreBtn.setDisable(false);
//				editBtn.setDisable(false);
//				if(!newValue.equals("")){
//					if(!(quantityTxt.getText().equals("") || rateTxt.getText().equals(""))){
//						double total = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(rateTxt.getText()))-Double.parseDouble(newValue);
//
//
//						double subTotal = total*(100/(100+gstRate));
//						gstAmt=total-subTotal;
//						double halfGst=gstAmt/2;
//
//						if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
//							cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
//							gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
//						}
//						else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
//							iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
//							double subTotal1=subTotal*(100/(100+cessRate));
//							cessAmt=subTotal-subTotal1;
////							subTotal=subTotal+subTotal1;
//							cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
////							subTotal=subTotal+cess;
//						}
//						taxableAmt=subTotal;
//
//					totalTxt.setText(String.format("%.2f",total));}
//					else{
//						totalTxt.setText("NaN");
//					}
//			}else{
//						discTxt.setText(discTxt.getPromptText());	}
//				}
//
//		});
//		
		return gp;
	}
	public GridPane returnSalesGoods(SalesEntry salesEntry,int index,AnchorPane anchorPane) {
		GridPane gp = new GridPane();
		// gp.setMinSize(1200, 800);
		gp.setAlignment(Pos.CENTER);
		gp.getStyleClass().add("grid");
//		gp.setGridLinesVisible(true);
		// gp.autosize();
//		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(10, 10, 10, 10));
		// gp.setPadding(new Insets(10, 10, 10, 10));
//		gp.setGridLinesVisible(true);
		Label titleLabel = new Label(" Return Goods");
		GridPane.setMargin(titleLabel, new Insets(0, -400, 0, 400));

		gp.add(titleLabel, 0, 0);

		JFXTextField invoiceDate= new JFXTextField();
		invoiceDate.setLabelFloat(true);
		invoiceDate.setPromptText("Invoice Date");
		invoiceDate.setText(salesEntry.getEntry_date().toString());
		invoiceDate.setEditable(false);
		invoiceDate.setStyle("-fx-font-size:14");
//		invoiceDate.setPrefWidth(100);

		Label custLbl= new Label("Customer Details");
		GridPane.setMargin(custLbl, new Insets(0,0,0,50));
		gp.add(custLbl, 0, 1);

		HBox custComboHB= new HBox();

		JFXTextField custCombo= new JFXTextField();
		custCombo.setLabelFloat(true);
		custCombo.setPromptText("Customer Name");
		custCombo.setEditable(false);
		String custName=salesEntry.getCustomer().getFull_name().replace("null", "");
		custCombo.setText(custName);
		custCombo.setStyle("-fx-font-size:14");
//		custCombo.setPrefWidth(100);

		JFXTextField addressTxt= new JFXTextField();
		addressTxt.setLabelFloat(true);
		addressTxt.setPromptText("Address");
		addressTxt.setText(salesEntry.getCustomer().getAddress());
		addressTxt.setEditable(false);
		addressTxt.setStyle("-fx-font-size:14");
//		addressTxt.setPrefWidth(100);

		JFXTextField stateTxt= new JFXTextField();
		stateTxt.setLabelFloat(true);
		stateTxt.setPromptText("State");
		stateTxt.setText(salesEntry.getCustomer().getState());
		stateTxt.setEditable(false);
		stateTxt.setStyle("-fx-font-size:14");
//		stateTxt.setPrefWidth(100);

		JFXTextField contactTxt=new JFXTextField();
		contactTxt.setPromptText("Contact Number");
		contactTxt.setLabelFloat(true);
		contactTxt.setEditable(false);
		contactTxt.setText(salesEntry.getCustomer().getContact());
		contactTxt.setStyle("-fx-font-size:14");
//		contactTxt.setPrefWidth(100);

		JFXTextField gstTxt= new JFXTextField();
		gstTxt.setLabelFloat(true);
		gstTxt.setPromptText("GSTIN");
		gstTxt.setEditable(false);
		gstTxt.setText(salesEntry.getCustomer().getGstin());
		gstTxt.setStyle("-fx-font-size:14");
//		gstTxt.setPrefWidth(100);


		custComboHB.getChildren().addAll(invoiceDate,custCombo,addressTxt,stateTxt,contactTxt,gstTxt);
		custComboHB.setSpacing(20);
//		GridPane.setMargin(custComboHB, new Insets(0,0,0,0));
		GridPane.setMargin(custComboHB, new Insets(0,0,0,50));
		gp.add(custComboHB, 0, 2);

		ObservableList<SalesProduct> prodData = FXCollections.observableArrayList();

		List<SalesProduct> salesList=salesDAO.getProductDetails(salesEntry);
		List<SalesReturn> returnList= salesDAO.getReturnDetails(salesEntry);

		for(SalesProduct sp:salesList) {
//			System.out.println("ST"+sp.getSubTotal());
			double gst=sp.getSubTotal()*(sp.getProduct().getGst()/100);
			double cess=sp.getSubTotal()*(sp.getProduct().getCess()/100);
			String rateString=BigDecimal.valueOf(sp.getRate())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();
			String discString=BigDecimal.valueOf(sp.getDiscount())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();

			String subTotalString=BigDecimal.valueOf(sp.getSubTotal())
					.setScale(2,RoundingMode.HALF_UP)
					.toPlainString();

			String totalString=BigDecimal.valueOf(sp.getTotal())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();

			if(stateTxt.getText().contains("Maharashtra")) {
//				System.out.println("GST"+sp.getProduct().getGst());
			String cgst=String.format("%.2f", (gst/2))+"\n("+(sp.getProduct().getGst()/2)+"%)";
			String sgst=String.format("%.2f", (gst/2))+"\n("+(sp.getProduct().getGst()/2)+"%)";
//			System.out.println("rate"+sp.getRate());
//			System.out.println("Quantity"+sp.getProduct().getQuantity());

//			prodData.add(new SalesProduct(sp.getProd_name(),sp.getId(),sp.getProduct().getHsnNo(),sp.getQuantity(),sp.getUnit(),sp.getRate(),sp.getDiscount(),sp.getTotal(),sp.getSubTotal(),cgst,sgst,"","",sp.getProduct(),rateString,discString,subTotalString,totalString));
			}
			else {
				String igst=String.format("%.2f", gst)+"\n("+(sp.getProduct().getGst())+"%)";
				String cessTxt=String.format("%.2f", cess)+"\n("+sp.getProduct().getCess()+"%)";

//				System.out.println("quantit"+sp.getQuantity());
//				prodData.add(new SalesProduct(sp.getProd_name(),sp.getId(),sp.getProduct().getHsnNo(),sp.getQuantity(),sp.getUnit(),sp.getRate(),sp.getDiscount(),sp.getTotal(),sp.getSubTotal(),"","",igst,cessTxt,sp.getProduct(),rateString,discString,subTotalString,totalString));
				}

			}

		ObservableList<SalesReturn> returnData= FXCollections.observableArrayList();
		TableView<SalesProduct> prodView = new TableView<SalesProduct>();


		prodView.setMaxSize(1100, 200);
		TableColumn<SalesProduct, String> prodName = new TableColumn<SalesProduct, String>("Product Name");
		prodName.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
		prodName.setMinWidth(150);
		TableColumn<SalesProduct, Long> hsnCol = new TableColumn<SalesProduct, Long>("HSN/\n"+"SAC");
		hsnCol.setCellValueFactory(new PropertyValueFactory<>("hsnNo"));
		hsnCol.setPrefWidth(100);
		TableColumn<SalesProduct, String> unitCol = new TableColumn<SalesProduct, String>("Unit");
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		unitCol.setPrefWidth(80);
		TableColumn<SalesProduct, Integer> quantityCol = new TableColumn<SalesProduct, Integer>("Qty.");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		quantityCol.setPrefWidth(80);
		TableColumn<SalesProduct, String> rateCol = new TableColumn<SalesProduct, String>("Rate");
		rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
		rateCol.setPrefWidth(100);
		TableColumn<SalesProduct, String> discCol = new TableColumn<SalesProduct, String>("Disc.\n"+" Rs.");
		discCol.setCellValueFactory(new PropertyValueFactory<>("discountString"));
		discCol.setPrefWidth(70);
		TableColumn<SalesProduct, SalesProduct> taxableCol = new TableColumn<SalesProduct, SalesProduct>("Taxable\n"+"Amt");
		taxableCol.setCellValueFactory(new PropertyValueFactory<>("subTotalString"));
		taxableCol.setPrefWidth(100);

		TableColumn<SalesProduct, SalesProduct> cgstCol = new TableColumn<SalesProduct, SalesProduct>("CGST\n"+"  (%)");
		cgstCol.setCellValueFactory(new PropertyValueFactory<>("cgst"));
		cgstCol.setPrefWidth(60);
		TableColumn<SalesProduct, SalesProduct> sgstCol = new TableColumn<SalesProduct, SalesProduct>("SGST\n"+"  (%)");
		sgstCol.setCellValueFactory(new PropertyValueFactory<>("sgst"));
		sgstCol.setPrefWidth(60);

		TableColumn<SalesProduct, SalesProduct> igstCol = new TableColumn<SalesProduct, SalesProduct>("IGST\n"+"  (%)");
		igstCol.setCellValueFactory(new PropertyValueFactory<>("igst"));
		igstCol.setPrefWidth(60);
		TableColumn<SalesProduct, SalesProduct> cessCol = new TableColumn<SalesProduct, SalesProduct>("Cess\n"+" (%)");

		cessCol.setCellValueFactory(new PropertyValueFactory<>("cess"));
		cessCol.setPrefWidth(60);

		TableColumn<SalesProduct, String> totalCol = new TableColumn<SalesProduct, String>("Total");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
		totalCol.setPrefWidth(100);

		TableColumn<SalesProduct, SalesProduct> actionCol = new TableColumn<SalesProduct, SalesProduct>("Action");
		actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
		actionCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>(){

			@Override
		    protected void updateItem(SalesProduct salesProduct,boolean empty){
				if(salesProduct==null){
					setGraphic(null);
					return;
				}
				for(SalesReturn sr:returnList) {
					if(salesProduct.getId()==sr.getSalesProduct().getId()) {
						if(salesProduct.getQuantity()==sr.getReturnQuantity()) {
						setGraphic(null);
						return;
						}
					}
				}

				JFXCheckBox returnCheck= new JFXCheckBox();
				setGraphic(returnCheck);
				returnCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						// TODO Auto-generated method stub
						if(newValue) {
							returnData.add(new SalesReturn(salesProduct,salesProduct.getQuantity(),salesProduct.getProduct()));
							grandTotal=0;
							if(prodData.size()==0) {
								grTotalTxt.setText("0.0");
							}
							else {
								for(SalesProduct sp:prodData) {
									grandTotal=grandTotal+sp.getTotal();
								}
								grandTotal=grandTotal-salesProduct.getTotal();
								grTotalTxt.setText(String.format("%.2f", grandTotal));
							}
						}else {
							returnData.remove(new SalesReturn(salesProduct,salesProduct.getQuantity(),salesProduct.getProduct()));
							grandTotal=0;
							for(SalesProduct p:prodData) {
								grandTotal=grandTotal+p.getTotal();
							}
							grTotalTxt.setText(String.format("%.2f", grandTotal));
						}
					}
				});
			}
		});

		TableColumn<SalesProduct,SalesProduct> statusCol= new TableColumn<SalesProduct, SalesProduct>("Status");
		statusCol.setCellValueFactory(e-> new ReadOnlyObjectWrapper<>(e.getValue()));
		statusCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>(){

			@Override
			protected void updateItem(SalesProduct salesProduct, boolean empty) {
			if(salesProduct==null) {
				setGraphic(null);
				setText(null);
				return;
			}

			for(SalesReturn sr:returnList) {
				if(salesProduct.getId()==sr.getSalesProduct().getId()) {
					setText("Returned ("+sr.getReturnQuantity()+")");
				}

			}



			}
		});


		prodView.getColumns().addAll(actionCol,prodName,hsnCol,unitCol,quantityCol,rateCol,discCol,taxableCol,cgstCol,sgstCol,igstCol,cessCol,totalCol,statusCol);
		prodView.setItems(prodData);

		GridPane.setMargin(prodView, new Insets(0,0,0,50));
		gp.add(prodView, 0, 8,12,1);

		grTotalTxt.setLabelFloat(true);
		grTotalTxt.setPromptText("Grand Total");
		grTotalTxt.setStyle("-fx-font-size: 20px;-fx-font-weight:bold");
		grTotalTxt.setEditable(false);
		grTotalTxt.setFocusColor(Color.TRANSPARENT);
		grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
		grTotalTxt.setText(String.valueOf(salesEntry.getTotal()));
		GridPane.setMargin(grTotalTxt, new Insets(20,0,0,50));
		gp.add(grTotalTxt, 0, 10);

		JFXButton submitBtn = new JFXButton(" Submit ");
		GridPane.setMargin(submitBtn,new Insets(20,0,0,50));
		gp.add(submitBtn, 0,11);

		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(returnData.size()==0) {
					errorTip.setText("Porduct already returned");
					submitBtn.setTooltip(errorTip);
					errorTip.show(submitBtn,200,100);
					return;
				}

				result=salesDAO.returnSales(salesEntry,returnData);
				if(result) {
					Alert alert = new Alert(AlertType.INFORMATION, "Sales Return accepted");
					alert.setTitle("Success Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
					Main.dialog.close();
					showInvoice(anchorPane);

					Main.salesView.setItems(saleSortedData);
					Main.salesView.requestFocus();
					Main.salesView.getSelectionModel().selectLast();
					Main.salesView.getFocusModel().focusNext();

					Main.salesView.setMinSize(900, 500);
					anchorPane.getChildren().set(0, Main.salesView);
				} else {
					Alert alert = new Alert(AlertType.ERROR,
							"Error while updating data..!Please check database connection");
					alert.setTitle("Error Message");
					alert.setHeaderText("HI");
					alert.showAndWait();

				}
			}
		});

		return gp;
	}



		public void printInvoice(SalesEntry salesEntry) {
//			boolean result= false;
			File file=new File("C:/jasperoutput/"+salesEntry.getInvoice_no()+"_"+(salesEntry.getCustomer().getFull_name())+"_Invoice.pdf");
			if(file.exists()) {
				if(Desktop.isDesktopSupported()) {
    				try {
						Desktop.getDesktop().open(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}else {
    				System.out.println("File does not exist");
    			}
			}

		}
		public void clearControls() {
			shopNameTxt.clear();
			agencyTxt.clear();
			areaCombo.setValue("Amravati");
			prmConTxt.clear();
			altConTxt.clear();
			addressTxt.clear();
			cityTxt.clear();
			altConTxt.clear();
			fullNameTxt.clear();
//			emailTxt.clear();
		}


		public void clearProductData(){
			unitCombo.setText(null);;
			prodTxt.setValue(null);
//			hsnTxt.clear();
			quantityTxt.setText("0");
			rateTxt.setText("0");
			discTxt.setText("0");
			gstRsTxt.setText("0");
//			cGstTxt.clear();
//			iGstTxt.clear();
//			cessTxt.clear();
			totalTxt.setText("0.0");
//			gstRsTxt.clear();
			cGstTxt.setText("0");
			iGstTxt.setText("0");
			cessTxt.clear();
			hsnTxt.clear();
//			taxableTxt.clear();
			taxableAmt=0;
			gstAmt=0;
			gstRate=0;
			cessAmt=0;
			cessRate=0;

		}


		public boolean validateOnAddEdit(){
			boolean valid = true;
			if(prodTxt.getValue()==null){
//				prodErrLbl.setMinWidth(35);
//				prodErrLbl.setText("Please select Product");
				errorTip.setText("Please select Product");
				prodTxt.setTooltip(errorTip);
				errorTip.show(prodTxt,100,160);
				valid=false;
			}
//
			if(unitCombo.getText()==null){
//				unitErrorLbl.setMinWidth(35);
//					unitErrorLbl.setText("Please select Unit");
				errorTip.setText("Please select Unit");
				unitCombo.setTooltip(errorTip);
				errorTip.show(unitCombo,300,160);
				valid=false;

			}

			if(totalTxt.getText().isEmpty()||Double.parseDouble(totalTxt.getText())==0){
//				totErrLbl.setText("Total cannot be zero");
				errorTip.setText("Total cannot be zero");
				errorTip.setWrapText(true);
				totalTxt.setTooltip(errorTip);
				errorTip.show(totalTxt,850,150);

//				totErrLbl.setMinWidth(150);
				valid=false;
			}
			if(!(totalTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
//				totErrLbl.setText("Total can not be negative");
				errorTip.setText("Total can not be negative");
				errorTip.setWrapText(true);
				totalTxt.setTooltip(errorTip);

				errorTip.show(totalTxt,850,150);
//				totErrLbl.setMinWidth(150);
				valid=false;
			}
			return valid;
		}


		public boolean validateCustomer() {
			if(fullNameTxt.getText().isEmpty()) {
				errorTip.setText("Please enter customer's full name!");
				fullNameTxt.setTooltip(errorTip);
				errorTip.show(fullNameTxt,370,100);

				return false;
			}
//			if(!shopNameTxt.getText().matches("^[a-zA-Z ]+$")) {
//				errorTip.setText("Please enter correct shop name!");
//				shopNameTxt.setTooltip(errorTip);
//				errorTip.show(shopNameTxt,370,100);
//				shopNameTxt.requestFocus();
//				return false;
//			}
//			if(agencyTxt.getText().isEmpty()) {
//				errorTip.setText("Please enter agency name!");
//				shopNameTxt.setTooltip(errorTip);
//				errorTip.show(shopNameTxt,700,100);
//
//				return false;
//			}
//			if (!agencyTxt.getText().matches("^[a-zA-Z ]+$")) {
//				errorTip.setText("Please enter correct agency name!");
//				agencyTxt.setTooltip(errorTip);
//				errorTip.show(agencyTxt,700,100);
//
//				return false;
//			}
			if(prmConTxt.getText().isEmpty()) {
				errorTip.setText("Please enter contact number!");
				errorTip.setWrapText(true);
				prmConTxt.setTooltip(errorTip);
				errorTip.show(prmConTxt,370,200);

				return false;
			}
			if(!prmConTxt.getText().matches("(?:(?:\\+|0{0,2})91(\\s*[\\- ]\\s*)?|[0 ]?)?[0-9]\\d{9}|(\\d[ -]?){10}\\d")) {
				errorTip.setText("Please enter correct contact number!");
				prmConTxt.setTooltip(errorTip);
				errorTip.show(prmConTxt,370,200);

				return false;
			}

			if(cityTxt.getText()==null||cityTxt.getText().isEmpty()) {
				errorTip.setText("Please enter city!");
				cityTxt.setTooltip(errorTip);
				errorTip.show(cityTxt,700,300);

				return false;
			}
			if(stateCombo.getValue()==null) {
				errorTip.setText("Please select state!");
				stateCombo.setTooltip(errorTip);
				errorTip.show(stateCombo,500,400);

				return false;
			}
			 return true;
		}
		public boolean validateonSaleSubmitButton() {
			double payTotal=0;
			if(invoicDatePick.getValue()==null){
				errorTip.setText("Please select purchase date!");
				invoicDatePick.setTooltip(errorTip);
				errorTip.show(invoicDatePick,100,30);
				return false;
			}

			if(Double.parseDouble(grTotalTxt.getText())==0) {
//				grTotErrLbl.setText("Grand Total cannot be zero");
//				grTotErrLbl.setMinWidth(100);
				errorTip.setText("Grand Total cannot be zero!");
				grTotalTxt.setTooltip(errorTip);
				errorTip.show(grTotalTxt,250,470);
				return false;
			}

			


//			 if(!payCombo.getValue().equals("UnPaid")) {
			 if(!cashCheck.isSelected()&& !bankCheck.isSelected()&& !cardCheck.isSelected()&& !voucherCheck.isSelected()) {
				 	errorTip.setText("Please select payment mode!");
					payModeLbl.setTooltip(errorTip);
					errorTip.show(payModeLbl,600,480);
					return false;
			 }
//			 }

//			 if(!payCombo.getValue().equals("Unpaid")) {
				 if(cashCheck.isSelected()&&(bankCheck.isSelected()||cardCheck.isSelected()||voucherCheck.isSelected())) {
					 if(cashAmtTxt.getText().isEmpty()||!cashAmtTxt.getText().matches("^[0-9]*\\.?[0-9]*$")) {
					 errorTip.setText("Please enter valid cash amount!");
						cashAmtTxt.setTooltip(errorTip);
						errorTip.show(cashAmtTxt,500,480);
						cashAmtTxt.requestFocus();
						return false;
					 }
//					if(payCombo.getValue().equals("Full Payment")) {
						if(Double.parseDouble(cashAmtTxt.getText())>Double.parseDouble(grTotalTxt.getText())) {
								errorTip.setText("Cash Amount must be smaller than Grand Total!");
								cashAmtTxt.setTooltip(errorTip);
								errorTip.show(cashAmtTxt,500,480);
								cashAmtTxt.requestFocus();
//								cashFlag=true;
								return false;
						}



////					}else if(payCombo.getValue().equals("Part Payment")) {
//						if(Double.parseDouble(cashAmtTxt.getText())>Double.parseDouble(partPayTxt.getText())) {
//						errorTip.setText("Cash Amount must be smaller than Part Payment!");
//						cashAmtTxt.setTooltip(errorTip);
//						errorTip.show(cashAmtTxt,500,480);
//						cashAmtTxt.requestFocus();
////						cashFlag=true;
//						return false;
//						}
//					}
					payTotal=payTotal+Double.parseDouble(cashAmtTxt.getText());
				 }

				 if(bankCheck.isSelected()) {
					 if(bankTxt.getText().isEmpty()||!bankTxt.getText().matches("^[a-zA-Z ]+$")) {
						 errorTip.setText("Please enter correct bank name!");
						bankTxt.setTooltip(errorTip);
						errorTip.show(bankTxt,600,480);
						return false;
					 }
					 if(cheqTxt.getText().isEmpty()) {
						 	errorTip.setText("Please enter cheque No!");
							cheqTxt.setTooltip(errorTip);
							errorTip.show(cheqTxt,600,480);
							return false;
					 }
				 }
				 if(bankCheck.isSelected()&&(cashCheck.isSelected()||cardCheck.isSelected()||voucherCheck.isSelected())) {
					 if(bankAmtTxt.getText().isEmpty()||!bankAmtTxt.getText().matches("^[0-9]*\\.?[0-9]*$")) {
					 	errorTip.setText("Please enter valid cheque/DD amount!");
						bankAmtTxt.setTooltip(errorTip);
						errorTip.show(bankAmtTxt,600,480);
						return false;
					 }
//					 if(payCombo.getValue().equals("Full Payment")) {
						 if(Double.parseDouble(bankAmtTxt.getText())>Double.parseDouble(grTotalTxt.getText())) {

							errorTip.setText("Cheque/DD amount must be smaller than Grand Total!");
							bankAmtTxt.setTooltip(errorTip);
							errorTip.show(bankAmtTxt,600,480);
//							bankFlag=true;
							return false;
						 }

//					 }
//					 else if(payCombo.getValue().equals("Part Payment")) {
//						 if(Double.parseDouble(bankAmtTxt.getText())>Double.parseDouble(partPayTxt.getText())) {
//						 errorTip.setText("Cheque/DD amount must be smaller than Part Payment!");
//							bankAmtTxt.setTooltip(errorTip);
//							errorTip.show(bankAmtTxt,600,480);
////							bankFlag=true;
//							return false;
//						 }
//					 }

					 payTotal=payTotal+Double.parseDouble(bankAmtTxt.getText());
				 }

				 if(cardCheck.isSelected()) {
					 if(transId.getText().isEmpty()) {
						 	errorTip.setText("Please enter Transaction Id!");
						 	transId.setTooltip(errorTip);
							errorTip.show(transId,650,480);
							return false;
					 }
				 }

				 if(cardCheck.isSelected()&&(cashCheck.isSelected()||bankCheck.isSelected()||voucherCheck.isSelected())) {
					 if(cardAmtTxt.getText().isEmpty()||!cardAmtTxt.getText().matches("^[0-9]*\\.?[0-9]*$")) {
					 	errorTip.setText("Please enter valid card amount!");
						cardAmtTxt.setTooltip(errorTip);
						errorTip.show(cardAmtTxt,650,480);
						return false;
					 }
//					if(payCombo.getValue().equals("Full Payment")) {
						if(Double.parseDouble(cardAmtTxt.getText())>Double.parseDouble(grTotalTxt.getText())) {
							errorTip.setText("Card amount must be smaller than Grand Total!");
							cardAmtTxt.setTooltip(errorTip);
							errorTip.show(cardAmtTxt,650,480);
							return false;
						}

//					}
//					else if(payCombo.getValue().equals("Part Payment")) {
//						if(Double.parseDouble(cardAmtTxt.getText())>Double.parseDouble(partPayTxt.getText())) {
//						errorTip.setText("Card amount must be smaller than Part Payment!");
//						cardAmtTxt.setTooltip(errorTip);
//						errorTip.show(cardAmtTxt,650,480);
//						return false;
//						}
//					}

					payTotal=payTotal+Double.parseDouble(cardAmtTxt.getText());
				 }
				 if(voucherCheck.isSelected()) {
					 if(voucherTypeTxt.getText().isEmpty()) {
					 errorTip.setText("Please enter voucher Code/Wallet Name!");
						voucherTypeTxt.setTooltip(errorTip);
						errorTip.show(voucherTypeTxt,800,480);
						return false;
					 }
				 }

				 if(voucherCheck.isSelected()&&(cashCheck.isSelected()||bankCheck.isSelected()||cardCheck.isSelected())) {

					 if(vouchAmtTxt.getText().isEmpty()||!vouchAmtTxt.getText().matches("^[0-9]*\\.?[0-9]*$")) {
					 errorTip.setText("Please enter voucher amount!");
						vouchAmtTxt.setTooltip(errorTip);
						errorTip.show(vouchAmtTxt,800,480);
						return false;
					 }
//					if(payCombo.getValue().equals("Full Payment")) {
						if(Double.parseDouble(vouchAmtTxt.getText())>Double.parseDouble(grTotalTxt.getText())) {
						 	errorTip.setText("voucher amount must be smaller than Grand Total!");
							vouchAmtTxt.setTooltip(errorTip);
							errorTip.show(vouchAmtTxt,800,480);
							return false;
						}

//						}else if(payCombo.getValue().equals("Part Payment")) {
//								if(Double.parseDouble(vouchAmtTxt.getText())>Double.parseDouble(partPayTxt.getText())) {
//								 	errorTip.setText("voucher amount must be smaller than Part Payment!");
//									vouchAmtTxt.setTooltip(errorTip);
//									errorTip.show(vouchAmtTxt,800,480);
//									return false;
//								}
//
//					}
					 payTotal=payTotal+Double.parseDouble(vouchAmtTxt.getText());
				 }
				 if(!cashAmtTxt.getText().isEmpty()||!bankAmtTxt.getText().isEmpty()||!cardAmtTxt.getText().isEmpty()||!vouchAmtTxt.getText().isEmpty()) {
//					 if(payCombo.getValue().equals("Full Payment")) {
						 if(payTotal!=Double.parseDouble(grTotalTxt.getText())) {
						 	errorTip.setText("Payment amounts entered must be equal to Grand Total");
							grTotalTxt.setTooltip(errorTip);
							errorTip.show(grTotalTxt,600,600);

							return false;
						 }
//					 }
//					 else {
//						 if(payTotal!=Double.parseDouble(partPayTxt.getText())) {
//							 errorTip.setText("Payment amounts entered must be equal to Part Payment");
//								grTotalTxt.setTooltip(errorTip);
//								errorTip.show(grTotalTxt,600,500);
//						 }
//					 }
				 }
//			 }
//			  if(!payCombo.getValue().equals("UnPaid")) {
//					if(payModeCombo.getValue()==null) {
////						payModeErrLbl.setText("Please select payment mode!");
////						payModeErrLbl.setMinWidth(150);
//						errorTip.setText("Please select payment mode!");
//						payModeCombo.setTooltip(errorTip);
//						errorTip.show(payModeCombo,450,600);
//						return false;
//					}
//					}

//			  if(!payCombo.getValue().equals("UnPaid")) {
//					if(!payModeCombo.getValue().equals("Cash")) {
//						if(bankTxt.getText().isEmpty()) {
//							errorTip.setText("Please enter bank name!");
//							bankTxt.setTooltip(errorTip);
//							errorTip.show(bankTxt, 850, 650);
//							return false;
//						}
//						if(!bankTxt.getText().matches("^[a-zA-Z ]+$")) {
//							errorTip.setText("Please enter valid bank name!");
//							bankTxt.setTooltip(errorTip);
//							errorTip.show(bankTxt, 850, 650);
//							return false;
//						}
//						 if(cheqTxt.getText().isEmpty()) {
//							errorTip.setText("Please enter Cheque No/TransactionId/Status!");
//							cheqTxt.setTooltip(errorTip);
//							errorTip.show(cheqTxt, 900, 650);
//							return false;
//						}
//					}
//				}


//			if(!payModeCombo.getValue().equals("Cash")) {
//
//			}
			return true;
		}


		public boolean validateSaleUpdateButton() {

			if(invoicDatePick.getValue()==null){
//				dateErrorLbl.setText("Please select purchase date");
//				dateErrorLbl.setMinWidth(150);
				errorTip.setText("Please select purchase date!");
				invoicDatePick.setTooltip(errorTip);
				errorTip.show(invoicDatePick,400,80);
				return false;
			}

			if(Double.parseDouble(grTotalTxt.getText())==0) {
//				grTotErrLbl.setText("Grand Total cannot be zero");
//				grTotErrLbl.setMinWidth(100);
				errorTip.setText("Grand Total cannot be zero!");
				grTotalTxt.setTooltip(errorTip);
				errorTip.show(grTotalTxt,450,500);
				return false;
			}
			return true;
		}


		public boolean validateProductControls(){
//			boolean valid=true;
			 if(quantityTxt.getText().isEmpty()||!(quantityTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
//				 quantityErrLbl.setMinWidth(150);
				 errorTip.setText("Quantity must be a positive number!");
				 quantityTxt.setTooltip(errorTip);
				 errorTip.show(quantityTxt,400,150);
//				quantityErrLbl.setText("");
				return false;
			}
			 if(rateTxt.getText().isEmpty()||!(rateTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
//				 rateErrLbl.setMinWidth(150);
//				rateErrLbl.setText("Rate must be a positive number!");
				 errorTip.setText("Rate must be a positive number!");
				 rateTxt.setTooltip(errorTip);
				 errorTip.show(rateTxt,500,150);
				 return false;
			}
			 if(discTxt.getText().isEmpty()||!(discTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
//				 discErrLbl.setMinWidth(150);
//				discErrLbl.setText("Discount must be 0 or greater than 0!");
				 errorTip.setText("Discount must be 0 or greater than 0!");
				 discTxt.setTooltip(errorTip);
				 errorTip.show(discTxt,600,150);

				 return false;
			}
//			if(gstRsTxt.getText().isEmpty()||!(gstRsTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
////				gstErrLbl.setMinWidth(150);
////				gstErrLbl.setText("GST must be 0 or greater than 0!");
//				errorTip.setText("Discount must be 0 or greater than 0!");
//				 discTxt.setTooltip(errorTip);
//				 errorTip.show(discTxt,400,400);
//				valid=false;
////				return;
//			}

//			if(!(totalTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
//				totErrLbl.setText("Total can not be negative");
//				totErrLbl.setMinWidth(150);
//				valid=false;
//			}

//			 if(gstRsTxt.getText().equals("0")){
//				 gstErrLbl.setMinWidth(100);
//				gstErrLbl.setText("GST must be greater than or equal to 0!");
//				valid=false;
//			}
				return true;
		}

		public void clearInvoiceControls() {

			custCombo.getItems().clear();
			invoicDatePick.setValue(null);
			address.clear();
			prmConTxt.clear();
			gstTxt.clear();
			prodTxt.getItems().clear();
			//unitCombo.getId().clear();
			cGstTxt.clear();
			gstRsTxt.clear();
			iGstTxt.clear();
			cessTxt.clear();
			grandTotal=0;
//			grTotalTxt.clear();
			payCombo.setValue(null);
//			payModeCombo.setValue(null);
			partPayTxt.clear();
			bankCheck.setSelected(false);
			bankAmtTxt.clear();
			cheqTxt.clear();
			bankTxt.clear();
			cashCheck.setSelected(false);
			cashAmtTxt.clear();
			cardCheck.setSelected(false);
			cashAmtTxt.clear();
			transId.clear();
			voucherCheck.setSelected(false);
			vouchAmtTxt.clear();
			voucherTypeTxt.clear();
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

		public void creatCustSubmit(AnchorPane anchorPane) {
			boolean result;
			if(!validateCustomer()) {
				return;
			}

			Customer customer = new Customer();
//			System.out.println(shopNameTxt.getText());
			customer.setContact(prmConTxt.getText());
			customer.setAddress(addressTxt.getText());
			customer.setCity(cityTxt.getText());
			customer.setFull_name(fullNameTxt.getText());
			customer.setAltcontno(altConTxt.getText());
			customer.setEmail(emailTxt.getText());
//			RadioButton gender = (RadioButton) tg.getSelectedToggle();
			customer.setGender(genderCombo.getValue());
//			customer.setArea(areaCombo.getValue().toString());
			customer.setState(stateCombo.getValue());

				result=salesDAO.createCustomer(customer);
				if(result) {
				Alert alert = new Alert(AlertType.INFORMATION, "You have recently added a new Customer");
				alert.setTitle("Success Message");
				alert.setHeaderText("HI");
				alert.showAndWait();
				Main.dialog.close();
				showCust(anchorPane);
				Main.custView.setItems(custSortedData);
				Main.custView.requestFocus();
				Main.custView.getSelectionModel().selectLast();
				Main.custView.getFocusModel().focusNext();

				Main.custView.setMinSize(900, 500);
				anchorPane.getChildren().set(0,Main.custView);
				}
				else {
					Alert alert = new Alert(AlertType.INFORMATION, "Error occurred while adding customer!Please check database connection");
					alert.setTitle("Error Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
				}
		}

		public void updateCustSubmit(Customer customer,int index,AnchorPane anchorPane) {
			boolean result;

			if(!validateCustomer()) {
				return;
			}
//			Customer customer = new Customer();
//			System.out.println(shopNameTxt.getText());
			customer.setContact(prmConTxt.getText());
			customer.setAddress(addressTxt.getText());
			customer.setCity(cityTxt.getText());
			customer.setFull_name(fullNameTxt.getText());
			customer.setAltcontno(altConTxt.getText());
			customer.setEmail(emailTxt.getText());
//			customer.setEmail(emailTxt.getText());
//			RadioButton gender = (RadioButton) tg.getSelectedToggle();
			customer.setGender(genderCombo.getValue());
//			customer.setArea(areaCombo.getValue().toString());
			customer.setState(stateCombo.getValue());

				result=salesDAO.updateCustomer(customer);
				if(result) {
				Alert alert = new Alert(AlertType.INFORMATION, "You have recently updated a Customer");
				alert.setTitle("Success Message");
				alert.setHeaderText("HI");
				alert.showAndWait();
				Main.dialog.close();
				showCust(anchorPane);
				Main.custView.setItems(custSortedData);
				Main.custView.requestFocus();
				Main.custView.getSelectionModel().select(index);
				Main.custView.getFocusModel().focus(index);

				Main.custView.setMinSize(900, 500);
				anchorPane.getChildren().set(0,Main.custView);
				}
				else {
					Alert alert = new Alert(AlertType.INFORMATION, "Error occurred while updating customer!Please check database connection");
					alert.setTitle("Error Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
				}
		}

		public boolean validateOnPayAmount() {
			System.out.println("reahced inside validation");
			if(datePayTxt.getValue()==null) {
				errorTip.setText("Please select date of Payment!");
				datePayTxt.setTooltip(errorTip);
				errorTip.show(datePayTxt,300,400);
				return false;
			}

			if(newPayTxt.getText().isEmpty()) {
				errorTip.setText("Please enter payment amount!");
				newPayTxt.setTooltip(errorTip);
				errorTip.show(newPayTxt,500,400);
				return false;
			}
			if(!newPayTxt.getText().matches("[0-9]+([,.][0-9]{1,2})?")) {
				errorTip.setText("Payment Amount must be a positive number!");
				newPayTxt.setTooltip(errorTip);
				errorTip.show(newPayTxt,500,400);
				return false;
			}

			return true;
		}
		public static ObservableList<String> fillStateCombo(){
			ObservableList<String> stateList=FXCollections.observableArrayList();
			stateList.addAll("Andaman and Nicobar Islands","Andhra Pradesh","Arunachal Pradesh","Assam",
				"Bihar",
				"Chhattisgarh",
				"Chandigarh",
				"Dadra and Nagar Haveli",
				"Daman and Diu",
				"Delhi-NCR",
				"Goa",
				"Gujarat",
				"Haryana",
				"Himachal Pradesh",
				"Jammu & Kashmir",
				"Jharkhand",
				"Karnataka",
				"Kerala",
				"Lakshadweep",
				"Madhya Pradesh",
				"Maharashtra",
				"Manipur",
				"Meghalaya",
				"Mizoram",
				"Nagaland",
				"Odisha",
				"Puducherry",
				"Punjab",
				"Rajasthan",
				"Sikkim",
				"Tamil Nadu",
				"Tripura",
				"Uttarkhand",
				"Uttar Pradesh",
				"West Bengal");

		return stateList;
		}

		public void fireBarcode(String barcode) {
//			System.out.println("hI"+barcode);
//			for (BarcodeListener listener : listeners) {
//				listener.onBarcodeRead(barcode);
//				System.out.println("jhgfk"+barcode);
//			}
//			System.out.println(barcode);
			for(Product p:productList) {
				if(p.getBarcode().equals(barcode.trim())) {
					prodTxt.setValue(p.getProduct_name());
					quantityTxt.setText("1");
				}
			}
		}

		 public  TableView<SalesReportBean> SalesReportTable( String From, String To){
			  saleDataList.clear();
			  TableView<SalesReportBean> SalesReportDate = new TableView<SalesReportBean>();
			  List<SalesReportBean> saleList=salesDAO.SalesReportData(From,To);

			  TableColumn<SalesReportBean, Long> titleCol = new TableColumn<SalesReportBean, Long>("Invoice No");
			  titleCol.setCellValueFactory(new PropertyValueFactory<>("invoice_no"));
			  titleCol.setPrefWidth(200);
			  titleCol.setStyle("-fx-alignment:center");
			  
			  TableColumn<SalesReportBean, Date> debitCol = new TableColumn<SalesReportBean, Date>("Invoice Date");
			  debitCol.setCellValueFactory(new PropertyValueFactory<>("invoice_date"));
			  debitCol.setPrefWidth(200);
			  debitCol.setStyle("-fx-alignment:center");

			  TableColumn<SalesReportBean, String> creditCol = new TableColumn<SalesReportBean, String>("Customer Name");
			  creditCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));
			  creditCol.setPrefWidth(300);
			  creditCol.setStyle("-fx-alignment:center");
			  
			  TableColumn<SalesReportBean, Double> totalCol= new TableColumn<SalesReportBean, Double>("Total");
			  totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
			  totalCol.setPrefWidth(200);
			  totalCol.setStyle("-fx-alignment:center");

			  Iterator<SalesReportBean> itr = saleList.iterator();
			  //long srNo = 1;
			  while (itr.hasNext()) {
			   SalesReportBean s1 = itr.next();
			   double total=BigDecimal.valueOf(s1.getTotal())
					   .setScale(2,RoundingMode.HALF_UP).doubleValue();
			   
			   String fullname=s1.getFullname().replaceAll("null", "");
			   saleDataList.add(new SalesReportBean(s1.getInvoice_no(), s1.getInvoice_date(),
			     fullname,total));

			  }



			  SalesReportDate.getColumns().addAll(titleCol,debitCol,creditCol,totalCol);


			//  SalesReportDate.getColumns().addListener(new ListChangeListener() {
			//   public boolean suspended;
			//
			//   @Override
			//   public void onChanged(Change c) {
//			    // TODO Auto-generated method stub
//			    c.next();
//			    if (c.wasReplaced() && !suspended) {
//			     this.suspended = true;
//			     SalesReportDate.getColumns().setAll(titleCol,debitCol,creditCol);
//			     this.suspended = false;
//			    }
			//   }
			//
			//  });
			//  Long srNo1=null;
			  SalesReportDate.setItems(saleDataList);


			  return SalesReportDate;
		 }
		 public static TableView<CashReportBean> CashReportTable( String From, String To,String Filter){
			    cashDataList.clear();
				TableView<CashReportBean> CashReportDate = new TableView<CashReportBean>();
				List<CashReportBean> cashList=SalesDAO.CashReportData(From,To);
				List<CashReportBean> cashListDayWise=SalesDAO.CashReportDataDayWise(From, To);

				TableColumn<CashReportBean, Date> dateCol = new TableColumn<CashReportBean, Date>("Date");
				dateCol.setCellValueFactory(new PropertyValueFactory<>("CashDate"));
				dateCol.setMinWidth(250);
				dateCol.setStyle("-fx-alignment:center");

				TableColumn<CashReportBean, String> SalesPurchaseCol = new TableColumn<CashReportBean, String>("Sales / Purchase");
				SalesPurchaseCol.setCellValueFactory(new PropertyValueFactory<>("salesPurchase"));
				SalesPurchaseCol.setMinWidth(170);
				SalesPurchaseCol.setStyle("-fx-alignment:center");

				TableColumn<CashReportBean, Double> CashPayCol = new TableColumn<CashReportBean, Double>("Cash Payment");
				CashPayCol.setCellValueFactory(new PropertyValueFactory<>("CashPayment"));
				CashPayCol.setMinWidth(170);
				CashPayCol.setStyle("-fx-alignment:center");

				TableColumn<CashReportBean, Double> CashReceivedCol = new TableColumn<CashReportBean, Double>("Cash Received");
				CashReceivedCol.setCellValueFactory(new PropertyValueFactory<>("CashReceived"));
				CashReceivedCol.setMinWidth(170);
				CashReceivedCol.setStyle("-fx-alignment:center");

				TableColumn<CashReportBean, Double> CashInHandCol =new TableColumn<CashReportBean, Double>("Cash In Hand");
				CashInHandCol.setCellValueFactory(new PropertyValueFactory<>("CashReceived"));
				CashInHandCol.setMinWidth(170);
				CashInHandCol.setStyle("-fx-alignment:center");

				 if(Filter=="Invoice Wise"){
				     Iterator<CashReportBean> itr = cashList.iterator();
				     //long srNo = 1;
				     while (itr.hasNext()) {
				      CashReportBean s1 = itr.next();
				      double cashPayment= BigDecimal.valueOf(s1.getCashPayment()).setScale(2,RoundingMode.HALF_UP)
				    		  .doubleValue();
				      double cashReceived=BigDecimal.valueOf(s1.getCashReceived())
				    		  .setScale(2,RoundingMode.HALF_UP).doubleValue();
				      double cashinHand=BigDecimal.valueOf(s1.getCashInHAnd())
				    		  .setScale(2,RoundingMode.HALF_UP).doubleValue();
				      if(s1.getCashDate()!=null) {
				      cashDataList.add(new CashReportBean(s1.getCashDate(), s1.getSalesPurchase(),
				       cashPayment,cashReceived,cashinHand));
				     }
				     }
				    }
				     else{
				      Iterator<CashReportBean> itr = cashListDayWise.iterator();
				      //long srNo = 1;
				      while (itr.hasNext()) {
				       CashReportBean s1 = itr.next();
				       double cashPayment= BigDecimal.valueOf(s1.getCashPayment()).setScale(2,RoundingMode.HALF_UP)
					    		  .doubleValue();
					      double cashReceived=BigDecimal.valueOf(s1.getCashReceived())
					    		  .setScale(2,RoundingMode.HALF_UP).doubleValue();
					      double cashinHand=BigDecimal.valueOf(s1.getCashInHAnd())
					    		  .setScale(2,RoundingMode.HALF_UP).doubleValue();
					      if(s1.getCashDate()!=null) {
					      cashDataList.add(new CashReportBean(s1.getCashDate(), s1.getSalesPurchase(),
					       cashPayment,cashReceived,cashinHand));
					     }
				      }

				     }
				CashReportDate.getColumns().addAll(dateCol,SalesPurchaseCol,CashPayCol,CashReceivedCol,CashInHandCol);
				CashReportDate.setItems(cashDataList);


				return CashReportDate;

			}
		 
		 
		 public GridPane createDeliveryMemo(AnchorPane anchorPane) {
			 
			 	clearInvoiceControls();
				GridPane gp=new GridPane();
				errorTip.setAutoHide(true);
				gp.getStyleClass().add("grid");
				gp.setHgap(10);
				gp.setVgap(10);
				gp.setAlignment(Pos.CENTER);
				gp.setPadding(new Insets(10, 10, 10, 10));
//				gp.setGridLinesVisible(true);

				Label titleLabel = new Label(" Create New Delivery Memo");
				GridPane.setMargin(titleLabel, new Insets(0,-300,0,300));
				gp.add(titleLabel, 0, 0);

				Label custCreatLbl=new Label("Customer Details");
				GridPane.setMargin(custCreatLbl, new Insets(0,0,0,-70));
				gp.add(custCreatLbl, 1, 1);

//				long entry_id=accountDAO.getAccountEntryId()+1;
				
				long dmId=salesDAO.getDeliveryId()+1;
//				System.out.println(dmId);
				int length=String.valueOf(dmId).length();
				Label deliveryMemoLbl =new Label();
				deliveryMemoLbl.setText(String.format("DM"+"%0"+(6-length)+"d", (dmId)));
				GridPane.setMargin(deliveryMemoLbl, new Insets(0,0,0,-200));
				gp.add(deliveryMemoLbl, 3, 0);
				
				invoicDatePick= new JFXDatePicker();
				invoicDatePick.setValue(LocalDate.now());
				invoicDatePick.setPromptText("Entry Date");
				invoicDatePick.setMaxWidth(150);
				invoicDatePick.setEditable(true);
				invoicDatePick.setShowWeekNumbers(false);
				invoicDatePick.getStyleClass().add("date-pick");

				KeyEventHandler.dateKeyEvent(invoicDatePick);
				GridPane.setMargin(invoicDatePick, new Insets(0,-70,0,0));
//				GridPane.setMargin(invoicDatePick, new Insets(0,0,0,-200));
				gp.add(invoicDatePick, 0, 1);

				HBox custComboHB= new HBox();
				custCombo= new JFXComboBox<>();
				custCombo.setValue(null);
				custCombo.setVisible(true);

//				custCombo.setStyle("-fx-font-size:15");
				custCombo.getStyleClass().add("jf-combo-box");
				custCombo.setStyle("-fx-font-size:12");
				custCombo.setMaxWidth(110);
				custCombo.setLabelFloat(true);
				custCombo.setPromptText("Customer Name");
				custCombo.setEditable(true);




				ObservableList<String> custObsData = FXCollections.observableArrayList();
				List<Customer> custList= salesDAO.getCustomerNames();
				Iterator<Customer> itr= custList.iterator();
				while(itr.hasNext()) {
					Customer customer=itr.next();
					if(!customer.getFull_name().equals("null")) {
					custObsData.add(customer.getFull_name());
					}
				}
				custCombo.setItems(custObsData);

				new AutoCompleteComboBoxListener<>(custCombo);

				custCombo.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub

						for(Customer c:custList) {
							if(custCombo.getValue()!=null) {
							if(custCombo.getValue().equals(c.getFull_name())) {
								address.setText(c.getAddress());
								stateCombo.setValue(c.getState());
								prmConTxt.setText(c.getContact());
								gstTxt.setText(c.getGstin());
							}
							}
						}
					}
				});

				address= new JFXTextField();
//				address.clear();
				address.setPromptText("Address");
				address.setStyle("-fx-font-size:12");
				address.setLabelFloat(true);
				address.setMaxWidth(80);
				
				prmConTxt= new JFXTextField();
//				prmConTxt.clear();
				prmConTxt.setPromptText("Contact Number");
				prmConTxt.setStyle("-fx-font-size:12");
				prmConTxt.setLabelFloat(true);
				prmConTxt.setMaxWidth(80);
				
				stateCombo= new JFXComboBox<>();
				stateCombo.setLabelFloat(true);
				stateCombo.setPromptText("State");
				stateCombo.setStyle("-fx-font-size:12");

//				stateCombo.setMaxWidth(10);
				stateCombo.getItems().addAll(fillStateCombo());
				stateCombo.setValue("Maharashtra");
				stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent event) {
						// TODO Auto-generated method stub
//						errorTip.hide();
						String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
						if(s!=null) {
							stateCombo.requestFocus();
							stateCombo.getSelectionModel().select(s);

						}
					}
				});
				
				gstTxt= new JFXTextField();
				gstTxt.clear();
				gstTxt.setPromptText("GSTIN");
				gstTxt.setLabelFloat(true);
				gstTxt.setMaxWidth(100);
				gstTxt.setStyle("-fx-font-size:12");


				custComboHB.getChildren().addAll(custCombo,address,stateCombo,prmConTxt,gstTxt);
				custComboHB.setSpacing(20);
				GridPane.setMargin(custComboHB, new Insets(0,0,0,-300));
				gp.add(custComboHB, 2, 2);




				final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
				     public DateCell call(final DatePicker datePicker) {
				         return new DateCell() {
				             @Override public void updateItem(LocalDate item, boolean empty) {
//				                 super.updateItem(item, empty);


				                 if (item.isAfter(LocalDate.now())) {
				                     // Tomorrow is too soon.
				                     setDisable(true);
				                 }
				             }
				         };
				     }
				 };
				 invoicDatePick.setDayCellFactory(dayCellFactory);


//				GridPane.setMargin(custCombo, new Insets(0,0,0,20));
//				gp.add(custCombo, 0, 2);



//				invoicDatePick.setMinWidth(100);
//				GridPane.setMargin(custCombo, new Insets(0,0,0,20));
//				gp.add(invoicDatePick, 1, 2);



//				custCombo.setConverter(new StringConverter<String>() {
		//
//					@Override
//					public String toString(String object) {
//						// TODO Auto-generated method stub
//						if(object==null)return null;
		//
//						return object.toString();
//					}
		//
//					@Override
//					public String fromString(String string) {
//						// TODO Auto-generated method stub
////						Customer customer=new Customer();
////						for(String cust:custCombo.getItems()) {
////							cust
////						}
//						return string;
//					}
//				});



				HBox prodHB = new HBox();

				Label addProdLbl =  new Label("Add Product");
				GridPane.setMargin(addProdLbl, new Insets(0,-100,0,30));
				gp.add(addProdLbl, 0, 4);
//				Label prodLbl = new Label("Product");
//				gp.add(prodLbl, 0, 5);

//				BarCodeRead barCodeRead= new BarCodeRead();

				prodTxt= new JFXComboBox<>();
				prodTxt.setLabelFloat(true);
				prodTxt.setPromptText("Select Product");
				prodTxt.setStyle("-fx-font-size:12");
//				new AutoCompleteComboBoxListener<>(prodTxt);

//				prodTxt.setDisable(true);

//				prodTxt.setEditable(true);
				prodTxt.setMaxWidth(100);

				prodTxt.getItems().clear();
				prodList.clear();

				productList=goodsDAO.showProductList();

				for(Product p:productList) {
					if(p.getCategory()!=null) {
					prodList.add(p.getProduct_name()+"        "+p.getCategory()+"("+p.getSubGroup()+")");
					}
				}

				if(!prodList.isEmpty()) {
					prodTxt.getItems().addAll(prodList);
				}
				prodTxt.getStyleClass().add("jf-combo-box");
				new AutoCompleteComboBoxListener<>(prodTxt);
		//
				
//				hsnTxt.clear();
//				hsnTxt.setPromptText("HSN/SAC");
//				hsnTxt.setLabelFloat(true);
//				hsnTxt.setEditable(false);
//				hsnTxt.setStyle("-fx-font-size:12");



				//unitCombo.getItems().clear();
				unitCombo= new JFXTextField();
				unitCombo.setLabelFloat(true);
				unitCombo.setPromptText("Select Unit");
				unitCombo.setStyle("-fx-font-size:12");
				unitCombo.setMaxWidth(80);
				unitCombo.setEditable(false);
//				unitCombo.getStyleClass().add("jf-combo-box");

//				unitCombo.add(goodsController.fillUnitList());
//				new AutoCompleteComboBoxListener<>(unitCombo);
//				unitCombo.setConverter(new StringConverter<String>() {
		//
//					@Override
//					public String toString(String object) {
//						// TODO Auto-generated method stub
//						if(object==null)return null;
		//
//						return object.toString();
//					}
		//
//					@Override
//					public String fromString(String string) {
//						// TODO Auto-generated method stub
//						Customer customer=new Customer();
//						for(String cust:custCombo.getItems()) {
//							cust
//						}
//						return string;
//					}
//				});

//				unitCombo.getItems().addAll("KG","PCS","LIT","ML","GRAM");
				quantityTxt= new JFXTextField();
				quantityTxt.setPromptText("Quantity");
				quantityTxt.setText("0");
				quantityTxt.setStyle("-fx-font-size:12");
				quantityTxt.setLabelFloat(true);
				quantityTxt.setMaxWidth(50);
				
				rateTxt= new JFXTextField();
				rateTxt.setLabelFloat(true);
				rateTxt.setStyle("-fx-font-size:12");
				rateTxt.setMaxWidth(50);
				rateTxt.setPromptText("Rate");
				rateTxt.setText("0.00");

//				discTxt.setMaxWidth(50);
//				discTxt.setStyle("-fx-font-size:12");
//				discTxt.setLabelFloat(true);
//				discTxt.setPromptText("Discount");
//				discTxt.setText("0.0");
//
////				taxableTxt.setPromptText("Taxable"+"\n Amt");
////				taxableTxt.setText("0");
////				taxableTxt.setLabelFloat(true);
////				taxableTxt.setMaxWidth(50);
////				taxableTxt.setEditable(false);
////				taxableTxt.setStyle("-fx-font-size:12");
//
//				cGstTxt.clear();
//				cGstTxt.setPromptText("CGST");
//				cGstTxt.setMaxWidth(50);
//				cGstTxt.setEditable(false);
//				cGstTxt.setStyle("-fx-font-size:12");
//				cGstTxt.setLabelFloat(true);
//
//				gstRsTxt.clear();
//				gstRsTxt.setPromptText("SGST");
//				gstRsTxt.setMaxWidth(50);
//				gstRsTxt.setEditable(false);
//				gstRsTxt.setStyle("-fx-font-size:12");
//				gstRsTxt.setLabelFloat(true);
//
//				iGstTxt.clear();
//				iGstTxt.setPromptText("IGST");
//				iGstTxt.setMaxWidth(50);
//				iGstTxt.setEditable(false);
//				iGstTxt.setStyle("-fx-font-size:12");
//				iGstTxt.setLabelFloat(true);
//
//				cessTxt.clear();
//				cessTxt.setPromptText("Cess");
//				cessTxt.setMaxWidth(50);
//				cessTxt.setEditable(false);
//				cessTxt.setStyle("-fx-font-size:12");
//				cessTxt.setLabelFloat(true);

//				gstRsTxt.setPromptText("GST In %");

				totalTxt= new JFXTextField();
				totalTxt.setEditable(false);
//				hsnTxt.setStyle("-fx-font-size:12");
				totalTxt.setText("0.00");
				totalTxt.setPromptText("Total");
				totalTxt.setLabelFloat(true);
				totalTxt.setStyle("-fx-font-size: 15px;-fx-font-weight:bold");
				totalTxt.setFocusColor(Color.TRANSPARENT);
				totalTxt.setUnFocusColor(Color.TRANSPARENT);
				totalTxt.setMaxWidth(100);
				HBox.setMargin(totalTxt, new Insets(0, 0, 20, 20));



//				stateCombo.setOnAction(new EventHandler<ActionEvent>() {
//
//					@Override
//					public void handle(ActionEvent event) {
//						// TODO Auto-generated method stub
//
//						String state=stateCombo.getSelectionModel().getSelectedItem();
//						if(state!=null) {
//							prodTxt.setDisable(false);
//						if(state.contains("Maharashtra")) {
//							prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
//							prodHB.getChildren().add(6, cGstTxt);
//							prodHB.getChildren().add(7,gstRsTxt);
//							HBox.setMargin(gstRsTxt, new Insets(0,-20,0,0));
////							GridPane.setMargin(prodHB, new Insets(0,0,0,50));
////							gp.setPadding(new Insets(10,10,10,10));
//						}
//						else {
//							prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
//							prodHB.getChildren().add(6,iGstTxt);
//							prodHB.getChildren().add(7,cessTxt);
//							HBox.setMargin(cessTxt, new Insets(0,-20,0,0));
////							GridPane.setMargin(prodHB, new Insets(0,0,0,50));
////							HBox.setMargin(totalTxt, new Insets(0,5,0,-50));
//						}
//					}
//					}
//				});

			    JFXButton addMoreBtn = new JFXButton(" Add ");
				addMoreBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");
				addMoreBtn.setMaxWidth(80);
				addMoreBtn.setDisable(true);

				JFXButton editBtn=new JFXButton("Edit");
				editBtn.setMaxWidth(80);
				editBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");

				RadioButton addItem = new RadioButton("Add Item");
				addItem.setSelected(true);
				addItem.setUserData("Add Item");

				RadioButton editItem=new RadioButton("Edit Item");
				editItem.setDisable(true);
				editItem.setUserData("Edit Item");
				ToggleGroup tg = new ToggleGroup();
				addItem.setToggleGroup(tg);
				editItem.setToggleGroup(tg);
				VBox vb1 = new VBox();
				vb1.setStyle("-fx-font-size:15px; -fx-font-weight:bold;");
				vb1.getChildren().addAll(addItem,editItem);
				vb1.setMaxHeight(30);
//				HBox.setMargin(addMoreBtn, new Insets(0,-50,0,20));
				HBox.setMargin(vb1, new Insets(0,0,0,20));
				prodHB.getChildren().addAll(prodTxt,unitCombo,quantityTxt,rateTxt,totalTxt,addMoreBtn,vb1);

				prodHB.setSpacing(20);
				GridPane.setMargin(prodHB, new Insets(0,-100,0,30));
				gp.add(prodHB, 0, 6,4,1);


//				Label grTotalLbl = new Label("Grand Total");
//				gp.add(grTotalLbl, 4, 9);

				ObservableList<DeliveryProduct> prodData = FXCollections.observableArrayList();
				TableView<DeliveryProduct> prodView = new TableView<DeliveryProduct>();


				prodView.setMaxSize(700, 200);
				TableColumn<DeliveryProduct, String> prodName = new TableColumn<DeliveryProduct, String>("Product Name");
				prodName.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
				prodName.setMinWidth(150);
				TableColumn<DeliveryProduct, String> unitCol = new TableColumn<DeliveryProduct, String>("Unit");
				unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
				unitCol.setPrefWidth(150);
				TableColumn<DeliveryProduct, Integer> quantityCol = new TableColumn<DeliveryProduct, Integer>("Quantity");
				quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				quantityCol.setPrefWidth(100);
				TableColumn<DeliveryProduct, String> rateCol = new TableColumn<DeliveryProduct, String>("Rate");
				rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
				rateCol.setPrefWidth(100);
				
//				TableColumn<SalesProduct, String> discCol = new TableColumn<SalesProduct, String>("Discount\n"+" Rs.");
//				discCol.setCellValueFactory(new PropertyValueFactory<>("discountString"));
//				TableColumn<SalesProduct, SalesProduct> taxableCol = new TableColumn<SalesProduct, SalesProduct>("Taxable\n"+"Amount");
//				taxableCol.setCellValueFactory(new PropertyValueFactory<>("subTotalString"));
//				taxableCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>(){
//					@Override
//					protected void updateItem(SalesProduct salesProduct,boolean empty) {
//						if(salesProduct==null||empty) {
//							setText(null);
//							return;
//						}
//						setText(taxableTxt.getText());
//					}
//				});

//				TableColumn<SalesProduct, SalesProduct> cgstCol = new TableColumn<SalesProduct, SalesProduct>("CGST\n"+"  (%)");
//				cgstCol.setCellValueFactory(new PropertyValueFactory<>("cgst"));
//				cgstCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>() {
//					@Override
//					protected void updateItem(SalesProduct salesProduct,boolean empty) {
//						if(salesProduct==null||empty) {
//							setGraphic(null);
//							return;
//						}
		//
//							System.out.println(cGstTxt.getText());
//							String cgst=cGstTxt.getText()+"("+salesProduct.getGst()/2+"%)";
//							setText(cgst);
		//
//					}
//				});
//				TableColumn<SalesProduct, SalesProduct> sgstCol = new TableColumn<SalesProduct, SalesProduct>("SGST\n"+"  (%)");
//				sgstCol.setCellValueFactory(new PropertyValueFactory<>("sgst"));
//				sgstCol.setCellFactory(e-> new TableCell<SalesProduct,SalesProduct>(){
//					@Override
//					protected void updateItem(SalesProduct salesProduct,boolean empty) {
//						if(salesProduct==null||empty) {
//							setText(null);
//							return;
//						}
//						else {
//							System.out.println(gstRsTxt.getText());
		//
//							String sgst=gstRsTxt.getText()+"("+salesProduct.getGst()/2+"%)";
//							setText(sgst);
//						}
//					}
//				});

//				TableColumn<SalesProduct, SalesProduct> igstCol = new TableColumn<SalesProduct, SalesProduct>("IGST\n"+"  (%)");
//				igstCol.setCellValueFactory(new PropertyValueFactory<>("igst"));
//				igstCol.setCellFactory(e-> new TableCell<SalesProduct,SalesProduct>(){
//					@Override
//					protected void updateItem(SalesProduct salesProduct,boolean empty) {
//						if(salesProduct==null||empty) {
//							setText(null);
//							return;
//						}
////						else {
//							String igst=iGstTxt.getText()+"("+salesProduct.getGst()+"%)";
//							setText(igst);
////						}
//					}
//				});
//				TableColumn<SalesProduct, SalesProduct> cessCol = new TableColumn<SalesProduct, SalesProduct>("Cess\n"+" (%)");
////				cessCol.setCellValueFactory(new PropertyValueFactory<>("gst"));
//				cessCol.setCellValueFactory(new PropertyValueFactory<>("cess"));
//				cessCol.setCellFactory(e-> new TableCell<SalesProduct,SalesProduct>(){
//					@Override
//					protected void updateItem(SalesProduct salesProduct,boolean empty) {
//						if(salesProduct==null||empty) {
//							setText(null);
//							return;
//						}
////						else {
//							String cess=cessTxt.getText();
//							setText(cess);
////						}
//					}
//				});

				TableColumn<DeliveryProduct, String> totalCol = new TableColumn<DeliveryProduct, String>("Total");
				totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
				totalCol.setPrefWidth(100);
				TableColumn<DeliveryProduct, DeliveryProduct> actionCol = new TableColumn<DeliveryProduct, DeliveryProduct>("Action");
				actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
				actionCol.setCellFactory(e->new TableCell<DeliveryProduct,DeliveryProduct>(){

					@Override
				    protected void updateItem(DeliveryProduct deliveryProduct,boolean empty){
						if(deliveryProduct==null){
							setGraphic(null);
							return;
						}else{
							deleteBtn=new JFXButton("Delete");
							setGraphic(deleteBtn);
//							deleteBtn.setDisable(false);
							deleteBtn.getStyleClass().add("del-btn");
							deleteBtn.setAlignment(Pos.CENTER);
							deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									// TODO Auto-generated method stub
									Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
									alert.setTitle("Confirmation");
//									alert.setHeaderText("HI");
									Optional<ButtonType> result= alert.showAndWait();
									if(result.get()==ButtonType.OK){
										getTableView().getItems().remove(deliveryProduct);
										getTableView().refresh();
										grandTotal=0;
										if(prodData.size()==0)
											grTotalTxt.setText("0.0");
										else{
										for(DeliveryProduct p:prodData){
											grandTotal=grandTotal+p.getTotal();
											grTotalTxt.setText(String.format("%.2f",grandTotal));

										}
										}
									}

								}
							});


						}
					}
				});
				prodView.getColumns().addAll(prodName,unitCol,quantityCol,rateCol,totalCol,actionCol);
				
				GridPane.setMargin(prodView, new Insets(-20,-100,0,30));


			

//				prodView.prefHeightProperty().bind(prodView.prefHeightProperty().multiply(prodView.getItems().size()/2));


				prodTxt.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(prodTxt.getValue()!=null){
							addMoreBtn.setDisable(false);
							editBtn.setDisable(false);
//							if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
								for(Product p:productList) {
									if(prodTxt.getValue().contains(p.getProduct_name())) {
//										double gst=p.getGst()/2;
//										gstRate=p.getGst();
////										cessRate=p.getCess();
//										cGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
//										gstRsTxt.setText(String.valueOf("0 ("+gst+"%)"));
//										hsnTxt.setText(String.valueOf(p.getHsnNo()));
										rateTxt.setText(String.valueOf(p.getSellPrice()));
										unitCombo.setText(String.valueOf(p.getUnit()));
										
									}


								}

//							}

////							else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
//								for(Product p:productList) {
//									if(prodTxt.getValue().contains(p.getProduct_name())) {
//										double gst=p.getGst();
//										double cess=p.getCess();
//										gstRate=p.getGst();
//										cessRate=p.getCess();
//										iGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
//										cessTxt.setText(String.valueOf("0 ("+cess+"%)"));
//										hsnTxt.setText(String.valueOf(p.getHsnNo()));
//										rateTxt.setText(String.valueOf(p.getSellPrice()));
//										unitCombo.setText(String.valueOf(p.getUnit()));
//									}
//
//								}
//							}
//							return;
						}
					}
				});

				unitCombo.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(unitCombo.getText()!=null) {
						addMoreBtn.setDisable(false);
						editBtn.setDisable(false);
						}
					}
				});


				addMoreBtn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
//						clearLabels();
//						gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
//							System.out.println(validateControls());
						boolean productFlag=false;
//						boolean unitFlag=false;
							if(!validateOnAddEdit()){
							return;
						}
//							deleteBtn.setDisable(false);
							DeliveryProduct deliveryProduct = new DeliveryProduct();
							Product product = new Product();
							deliveryProduct.setProduct(product);
							for(Product p:productList) {
							if(prodTxt.getValue().contains(p.getProduct_name())) {
								deliveryProduct.getProduct().setId(p.getId());
								deliveryProduct.getProduct().setProduct_name(p.getProduct_name());
								deliveryProduct.setProd_name(p.getProduct_name());
//							salesProduct.setGst(p.getGst());
							deliveryProduct.setCurrentStock(p.getQuantity());
							productFlag=true;
									}
								}
							if(!productFlag) {
								errorTip.setText("No Product found with specified name!");
								prodTxt.setTooltip(errorTip);
								errorTip.show(prodTxt,100,160);
								return;
							}
//							salesProduct.setProd_name(prodTxt.getValue());
//							if(!unitCombo.getId().contains(unitCombo.getText())) {
//								errorTip.setText("Unit Name not found!");
//								unitCombo.setTooltip(errorTip);
//								errorTip.show(unitCombo,300,160);
//								return;
//							}

							deliveryProduct.setUnit(unitCombo.getText());
							deliveryProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));

							String trunRateAmt=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
								    .setScale(2, RoundingMode.HALF_UP)
								    .toPlainString();


//							System.out.println(trunRateAmt);
							deliveryProduct.setRateString(trunRateAmt);
							deliveryProduct.setRate(Double.parseDouble(rateTxt.getText()));

//							String trunDiscAmt=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
//								    .setScale(2, RoundingMode.HALF_UP)
//								    .toPlainString();
//
//							salesProduct.setDiscount(Double.parseDouble(discTxt.getText()));
//							salesProduct.setDiscountString(trunDiscAmt);
//
//
//								String cgstTxt=cGstTxt.getText().replace("(", "\n(");
//								salesProduct.setCgst(cgstTxt);
//
////							if(iGstTxt.getText().contains("(")) {/
//								String igstTxt=iGstTxt.getText().replace("(", "\n(");
//								salesProduct.setIgst(igstTxt);
////							}
////							salesProduct.setSgst(gstRsTxt.getText());
////							if(gstRsTxt.getText().contains("(")) {
//								String sgstTxt=gstRsTxt.getText().replace("(", "\n(");
//								salesProduct.setSgst(sgstTxt);
////							}
//
////							salesProduct.setCess(cessTxt.getText());
//							if(cessTxt.getText().contains("(")) {
//								String cesssTxt=cessTxt.getText().replace("(", "\n(");
//								salesProduct.setCess(cesssTxt);
//							}
//							salesProduct.setGst(Double.parseDouble());
//							NumberFormat formatter = new DecimalFormat("#0.00");

//							String trunTaxablAmt=BigDecimal.valueOf(taxableAmt)
//						    .setScale(2, RoundingMode.HALF_UP)
//						    .toPlainString();
//
//							double taxAmt=BigDecimal.valueOf(taxableAmt)
//									.setScale(2,RoundingMode.HALF_UP)
//									.doubleValue();

//							salesProduct.setSubTotal(taxAmt);
//							salesProduct.setSubTotalString(trunTaxablAmt);

							deliveryProduct.setTotal(Double.parseDouble(totalTxt.getText()));
							String trunTotalAmt=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
								    .setScale(2, RoundingMode.HALF_UP)
								    .toPlainString();
							deliveryProduct.setTotalString(trunTotalAmt);

							for(DeliveryProduct sp:prodData) {
								if(sp.getProd_name().equals(deliveryProduct.getProd_name())) {
//									dupliErrLbl.setText("Duplicate entries are not permitted");
//									gp.add(dupliErrLbl, 0, 7);
									errorTip.setText("Duplicate entries are not permitted");
									prodTxt.setTooltip(errorTip);
									errorTip.show(prodTxt,100,200);
									return;
								}
							}

							prodData.add(deliveryProduct);
							prodView.setItems(prodData);
							prodView.requestFocus();
							prodView.getSelectionModel().selectLast();
							stateCombo.requestFocus();
//							cashCheck.requestFocus();
							
							grandTotal=grandTotal+deliveryProduct.getTotal();
							grTotalTxt.setText(String.format("%.2f",grandTotal));

							clearProductData();
							editItem.setDisable(false);
					}
				});

				
				tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

					@Override
					public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
						// TODO Auto-generated method stub
						if(tg.getSelectedToggle()!=null) {
							String item=tg.getSelectedToggle().getUserData().toString();
							if(item.equals("Add Item")) {
								prodHB.getChildren().removeAll(editBtn,addMoreBtn);
								prodHB.getChildren().add(5,addMoreBtn);
								deleteBtn.setDisable(false);
								clearProductData();
							}else if(item.equals("Edit Item")) {
								deleteBtn.setDisable(true);
								DeliveryProduct deliveryProduct = prodView.getSelectionModel().getSelectedItem();
								int index = prodView.getSelectionModel().getSelectedIndex();
								prodTxt.setValue(deliveryProduct.getProd_name());
								unitCombo.setText(deliveryProduct.getUnit());
								quantityTxt.setText(String.valueOf(deliveryProduct.getQuantity()));

								String trunRateAmt=BigDecimal.valueOf(deliveryProduct.getRate())
									    .setScale(2, RoundingMode.HALF_UP)
									    .toPlainString();

								rateTxt.setText(trunRateAmt);

//								String trunTaxableAmt=BigDecimal.valueOf(salesProduct.getSubTotal())
//									    .setScale(2, RoundingMode.HALF_UP)
//									    .toPlainString();
//								taxableTxt.setText(trunTaxableAmt);

								String trunTotalAmt=BigDecimal.valueOf(deliveryProduct.getTotal())
									    .setScale(2, RoundingMode.HALF_UP)
									    .toPlainString();
								totalTxt.setText(trunTotalAmt);

								grandTotal=grandTotal-deliveryProduct.getTotal();
								prodHB.getChildren().remove(addMoreBtn);
								prodHB.getChildren().add(5, editBtn);

								editBtn.setOnAction(new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent event) {
										// TODO Auto-generated method stub
//										clearLabels();
//										gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
										if(!validateOnAddEdit()){
											return;
										}

//										PurchaseProduct purchaseProduct1 = new PurchaseProduct();
										deliveryProduct.setProd_name(prodTxt.getValue());
										deliveryProduct.setUnit(unitCombo.getText());
										deliveryProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
										String trunRateAmt1=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
												.setScale(3, RoundingMode.HALF_UP)
												.toPlainString();
										deliveryProduct.setRateString(trunRateAmt1);

										deliveryProduct.setRate(Double.parseDouble(rateTxt.getText()));

										String trunTotalAmt1=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
												.setScale(3, RoundingMode.HALF_UP)
												.toPlainString();
										deliveryProduct.setTotalString(trunTotalAmt1);
										deliveryProduct.setTotal(Double.parseDouble(totalTxt.getText()));

										prodData.set(index,deliveryProduct);
										advanceAmt.requestFocus();
//										cashCheck.requestFocus();
										grandTotal=0;
										for(DeliveryProduct p:prodData) {
											grandTotal=grandTotal+p.getTotal();
										}
										grTotalTxt.setText(String.format("%.2f",grandTotal));
										prodHB.getChildren().remove(editBtn);
										prodHB.getChildren().add(5, addMoreBtn);
										addItem.setSelected(true);
										clearProductData();
									}
								});

							}
						}
					}
				});





//				Label payTypeLbl =  new Label("Payment Type");
//				gp.add(payTypeLbl, 4, 10);

				HBox paymentBox= new HBox();

				grTotalTxt= new JFXTextField();
				grTotalTxt.setLabelFloat(true);
				grTotalTxt.setEditable(false);
				grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
				grTotalTxt.setFocusColor(Color.TRANSPARENT);
				grTotalTxt.setPromptText("Grand Total");
				grTotalTxt.setStyle("-fx-font-size: 20px; -fx-font-weight:bold;");
				grTotalTxt.setText("00.00");
//				grTotalTxt.setMaxWidth(100);
				GridPane.setMargin(grTotalTxt, new Insets(20,-50,0,50));
				gp.add(prodView, 0, 8,5,1);
				gp.add(grTotalTxt, 0, 10);
				
				advanceAmt= new JFXTextField();
				advanceAmt.setLabelFloat(true);
				advanceAmt.setPromptText("Advance Amount");
				advanceAmt.setMinWidth(100);
				advanceAmt.setText("0.00");
				gp.add(advanceAmt, 1, 10);
				
				JFXButton submitBtn = new JFXButton(" Submit ");
				gp.add(submitBtn, 2,12);
		//

				quantityTxt.textProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						// TODO Auto-generated method stub
						errorTip.hide();

						if(!validateDeliveryProductControls()){
							addMoreBtn.setDisable(true);
							editBtn.setDisable(true);
//							gp.add(quantityErrLbl, 1, 7);
							return;
						}

						for(Product p:productList) {
							if(prodTxt.getValue()!=null) {
							if(prodTxt.getValue().contains(p.getProduct_name())) {
								if(p.getQuantity()<Integer.parseInt(newValue)) {
//									System.out.println(p.getQuantity());
									errorTip.setText("Quantity must not exceed current stock");
									quantityTxt.setTooltip(errorTip);
									errorTip.show(quantityTxt,400,150);
									addMoreBtn.setDisable(true);
									editBtn.setDisable(true);
									return;
								}
							}
							}
						}
//						totErrLbl.setText("");
						addMoreBtn.setDisable(false);
						editBtn.setDisable(false);
						if(!newValue.isEmpty()){
							if(!(rateTxt.getText().equals(""))){
							double total = (Double.parseDouble(newValue)*Double.parseDouble(rateTxt.getText()));


//							double subTotal = total*(100/(100+gstRate));
//							gstAmt=total-subTotal;
//							double halfGst=gstAmt/2;
//
//							if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
//
//								cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
//								gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
//							}
//							else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
//								iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
//								double subTotal1=subTotal*(100/(100+cessRate));
//								cessAmt=subTotal-subTotal1;
////								subTotalsub=subTotal+subTotal1;
//								cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
////								subTotal=subTotal+cess;
//							}
//							taxableAmt=subTotal;
							totalTxt.setText(String.format("%.2f",total));}
							else{
								totalTxt.setText("NaN");
							}
					}
						else{
							quantityTxt.setText(quantityTxt.getPromptText());
						}
						}

				});

				rateTxt.textProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						// TODO Auto-generated method stub
		//
						errorTip.hide();
						if(!validateDeliveryProductControls()){
							addMoreBtn.setDisable(true);
							editBtn.setDisable(true);
							return;
						}

						if(!quantityTxt.getText().isEmpty()) {
						for(Product p:productList) {
							if(prodTxt.getValue()!=null) {
							if(prodTxt.getValue().contains(p.getProduct_name())) {

								if(p.getQuantity()<Integer.parseInt(quantityTxt.getText())) {
//									System.out.println(p.getQuantity());
									errorTip.setText("Quantity must not exceed current stock");
									quantityTxt.setTooltip(errorTip);
									errorTip.show(quantityTxt,400,150);
									addMoreBtn.setDisable(true);
									editBtn.setDisable(true);
									return;
								}
							}
						}
						}
						}

						addMoreBtn.setDisable(false);
						editBtn.setDisable(false);
						if(!newValue.equals("")){
//							if(newValue.matches(arg0))
							if(!(quantityTxt.getText().equals(""))){
						double total = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(newValue));

//						double subTotal = total*(100/(100+gstRate));
//						gstAmt=total-subTotal;
//
//						double halfGst=gstAmt/2;
//						if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
//							cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
//							gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
//						}
//						else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
//							iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
//							double subTotal1=subTotal*(100/(100+cessRate));
//							cessAmt=subTotal-subTotal1;
////							subTotal=subTotal+subTotal1;
//							cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
////							subTotal=subTotal+cess;
//						}
//						taxableAmt=subTotal;
						totalTxt.setText(String.format("%.2f",total));}
							else{
								totalTxt.setText("NaN");
							}
					}else
						rateTxt.setText(rateTxt.getPromptText());
						}
				});
				
				submitBtn.setOnAction(new EventHandler<ActionEvent>() {
					boolean result;
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(!validateOnDeliverySubmit()) {
							return;
						}
						
						DeliveryMemo deliveryMemo= new DeliveryMemo();
						deliveryMemo.setDm_no(String.valueOf(dmId));
						Customer customer= new Customer();
						deliveryMemo.setCustomer(customer);
						
						if(custCombo.getValue()!=null) {
							for(Customer c:custList) {
							if(custCombo.getValue().equals(c.getFull_name())&&prmConTxt.getText().equals(c.getContact())) {
								deliveryMemo.getCustomer().setCust_id(c.getCust_id());
							}
						}
						}
						try {
					LocalDate localDate=invoicDatePick.getValue();
					SimpleDateFormat sd=new SimpleDateFormat("yyy-MM-dd");
					java.sql.Date sqlDate=new java.sql.Date(sd.parse(localDate.toString()).getTime());
					deliveryMemo.setEntry_date(sqlDate);
					}
					catch(ParseException e) {
						e.printStackTrace();
					}
						
//					deliveryMemo.setCustName(fullNameTxt.getText());
					deliveryMemo.getCustomer().setFull_name(custCombo.getValue());
					deliveryMemo.getCustomer().setAddress(address.getText());
					deliveryMemo.getCustomer().setGstin(gstTxt.getText());
					deliveryMemo.getCustomer().setState(stateCombo.getValue());
					deliveryMemo.getCustomer().setContact(prmConTxt.getText());
					deliveryMemo.setAdvanceAmt(Double.parseDouble(advanceAmt.getText()));
					deliveryMemo.setTotal(Double.parseDouble(grTotalTxt.getText()));
					result= salesDAO.createDeliveryMemo(deliveryMemo,prodData);
					if(result) {
						Alert alert = new Alert(AlertType.INFORMATION, "Delivery Memo Generated");
						alert.setTitle("Success Message");
						alert.setHeaderText("HI");
						alert.showAndWait();
						Main.dialog.close();
						try {
							delivery_Memo_Generator.DeliveryMemoPdf(deliveryMemo);
						} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						try {
//							generateInvoice.showReport(deliveryMemo);
//						} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace(); 
//						}
//						clearInvoiceControls();
						showDeliveryMemo(anchorPane);
						Main.deliveryView.setItems(delSortedData);
						Main.deliveryView.requestFocus();
						Main.deliveryView.getSelectionModel().selectLast();
						Main.deliveryView.getFocusModel().focusNext();

						Main.deliveryView.setMinSize(900, 500);
						anchorPane.getChildren().set(0,Main.deliveryView);

						}
						else {
							Alert alert = new Alert(AlertType.INFORMATION, "Error occurred while generating Delievry Memo!Please check database connection");
							alert.setTitle("Error Message");
							alert.setHeaderText("HI");
							alert.showAndWait();
							Main.dialog.close();
						}
					}
				});
				
				gp.addEventFilter(KeyEvent.KEY_PRESSED, e->{
					if(e.getCode()==KeyCode.INSERT) {
						if(prodHB.getChildren().contains(addMoreBtn)) {
							addMoreBtn.fire();
							e.consume();
						}else if(prodHB.getChildren().contains(editBtn)) {
							editBtn.fire();
							e.consume();
						}

					}else if(e.getCode()==KeyCode.ENTER) {
						submitBtn.fire();
						e.consume();
					}
				});
				
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						stateCombo.requestFocus();
					}
				});
				

				return gp;
				
			 
		 }
		 public TableView<DeliveryMemo> showDeliveryMemo(AnchorPane anchorPane){
			 TableView<DeliveryMemo> deliveryView=new TableView<DeliveryMemo>();
			deliveryData.clear();
			TableColumn<DeliveryMemo, Long> srNoCol= new TableColumn<>("Sr. No");
			srNoCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));
			srNoCol.setPrefWidth(80);
			srNoCol.setStyle("-fx-alignment:center");
			TableColumn<DeliveryMemo, Date> dateCol= new TableColumn<>("Date");
			dateCol.setCellValueFactory(new PropertyValueFactory<>("entry_date"));
			dateCol.setStyle("-fx-alignment:center");
			TableColumn<DeliveryMemo, String> deliverNoCol= new TableColumn<DeliveryMemo, String>("Delivery Memo\n"+"    Number");
			deliverNoCol.setCellValueFactory(new PropertyValueFactory<>("dm_no"));
			deliverNoCol.setPrefWidth(150);
			deliverNoCol.setStyle("-fx-alignment:center");
			TableColumn<DeliveryMemo, String> custNameCol= new TableColumn<DeliveryMemo, String>("Customer Name");
			custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
			custNameCol.setPrefWidth(250);
			custNameCol.setStyle("-fx-alignment:center");
			TableColumn<DeliveryMemo, Double> advancePayCol= new TableColumn<DeliveryMemo, Double>("Advance\n"+"Amount");
			advancePayCol.setCellValueFactory(new PropertyValueFactory<>("advanceAmt"));
			advancePayCol.setPrefWidth(150);
			advancePayCol.setStyle("-fx-alignment:center");
			TableColumn<DeliveryMemo, DeliveryMemo> actionCol= new TableColumn<DeliveryMemo, DeliveryMemo>("Action");
			actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
			actionCol.setStyle("-fx-alignment:center");
			actionCol.setCellFactory(e->new TableCell<DeliveryMemo,DeliveryMemo>(){

				@Override
			    protected void updateItem(DeliveryMemo deliveryMemo,boolean empty){
					if(deliveryMemo==null){
						setGraphic(null);
						setText(null);
						return;
					}else{
						deleteBtn=new JFXButton("Delete");
					if(deliveryMemo.getStatus()==1) {
						setGraphic(deleteBtn);
						setText(null);
						
						}
					else if(deliveryMemo.getStatus()==0) {
						setGraphic(null);
						setText("Sales invoice Generated\n\t\t("+deliveryMemo.getInvoiceNo()+")");
						setStyle("-fx-font-weight:bold");
					}
					
//						deleteBtn.setDisable(false);
						deleteBtn.getStyleClass().add("del-btn");
						deleteBtn.setAlignment(Pos.CENTER);
						deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
							boolean result1;
							@Override
							public void handle(ActionEvent arg0) {
								// TODO Auto-generated method stub
								Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
								alert.setTitle("Confirmation");
//								alert.setHeaderText("HI");
								Optional<ButtonType> result= alert.showAndWait();
								if(result.get()==ButtonType.OK){
								result1=salesDAO.deletedeliveryMemo(deliveryMemo);
								showDeliveryMemo(anchorPane);
								Main.deliveryView.setItems(delSortedData);
								Main.deliveryView.requestFocus();
								Main.deliveryView.getSelectionModel().selectLast();
								Main.deliveryView.getFocusModel().focusNext();
//
								Main.deliveryView.setMinSize(900, 500);
								anchorPane.getChildren().set(0, Main.deliveryView);
									getTableView().getSortOrder().remove(deliveryMemo);
									getTableView().refresh();
									
								}

							}
						});


					}
				}
			});
			
			deliveryView.getColumns().addAll(srNoCol,deliverNoCol,dateCol,custNameCol,advancePayCol,actionCol);
			List<DeliveryMemo> deliveryList=salesDAO.showDeliveryMemo();
			
			Iterator<DeliveryMemo> itr=deliveryList.iterator();
			long srNo=1;
			while(itr.hasNext()) {
				DeliveryMemo deliveryMemo=itr.next();
				String advance=BigDecimal.valueOf(deliveryMemo.getAdvanceAmt())
						.setScale(2,RoundingMode.HALF_UP)
						.toPlainString();
				
				deliveryData.add(new DeliveryMemo(srNo,deliveryMemo.getId(),deliveryMemo.getDm_no(),deliveryMemo.getCustName().replaceAll("null", ""),deliveryMemo.getCustomer(),deliveryMemo.getEntry_date(),deliveryMemo.getTotal(),deliveryMemo.getAdvanceAmt(),deliveryMemo.getStatus(),advance,deliveryMemo.getInvoiceNo()));
				srNo++;
			}
			 
			deliveryView.getColumns().addListener(new ListChangeListener() {
				public boolean suspended;

				@Override
				public void onChanged(Change c) {
					// TODO Auto-generated method stub
					c.next();
					if (c.wasReplaced() && !suspended) {
						this.suspended = true;
						deliveryView.getColumns().setAll(srNoCol,deliverNoCol,dateCol,custNameCol,advancePayCol,actionCol);
						this.suspended = false;
					}
				}

			});
			
			delFilteredData = new FilteredList<>(deliveryData, p -> true);

			delSortedData = new SortedList<>(delFilteredData);
			delSortedData.comparatorProperty().bind(deliveryView.comparatorProperty());

			deliveryView.setItems(delSortedData);
			return deliveryView;

		 }
		 
		 public boolean validateOnDeliverySubmit() {
			 if(invoicDatePick.getValue()==null) {
				 errorTip.setText("Please enter entry Date!");
				 invoicDatePick.setTooltip(errorTip);
				 errorTip.show(invoicDatePick,100,30);
				 return false;
			 }
			 
			 if(Double.parseDouble(grTotalTxt.getText())==0) {
//					grTotErrLbl.setText("Grand Total cannot be zero");
//					grTotErrLbl.setMinWidth(100);
					errorTip.setText("Grand Total cannot be zero!");
					grTotalTxt.setTooltip(errorTip);
					errorTip.show(grTotalTxt,450,500);
					return false;
				}
			 if(advanceAmt.getText()!=null) {
			 if(!advanceAmt.getText().matches("^[0-9]*\\.?[0-9]*$")){
				 errorTip.setText("Advance amount must be a positive number!");
				 advanceAmt.setTooltip(errorTip);
					errorTip.show(advanceAmt,600,500);
					return false;
			 }
			 }
				return true;
		 }
		 
		 public boolean validateDeliveryProductControls(){
//				boolean valid=true;
				 if(quantityTxt.getText().isEmpty()||!(quantityTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
//					 quantityErrLbl.setMinWidth(150);
					 errorTip.setText("Quantity must be a positive number!");
					 quantityTxt.setTooltip(errorTip);
					 errorTip.show(quantityTxt,400,150);
//					quantityErrLbl.setText("");
					return false;
				}
				 if(rateTxt.getText().isEmpty()||!(rateTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
//					 rateErrLbl.setMinWidth(150);
//					rateErrLbl.setText("Rate must be a positive number!");
					 errorTip.setText("Rate must be a positive number!");
					 rateTxt.setTooltip(errorTip);
					 errorTip.show(rateTxt,500,150);
					 return false;
				}
				 return true;
		 }
		 
		 public GridPane createDeliveryInvoice(DeliveryMemo deliveryMemo,int index,AnchorPane anchorPane) {
			 
			 clearInvoiceControls();
			 GridPane gp=new GridPane();
				errorTip.setAutoHide(true);
				gp.getStyleClass().add("grid");
				gp.setHgap(10);
				gp.setVgap(10);
				gp.setAlignment(Pos.CENTER);
				gp.setPadding(new Insets(10, 10, 10, 10));
//				gp.setGridLinesVisible(true);

				Label titleLabel = new Label(" Create New Sales Invoice");
				GridPane.setMargin(titleLabel, new Insets(0,-400,0,400));
				gp.add(titleLabel, 0, 0);

				Label custCreatLbl=new Label("Customer Details");
//				GridPane.setMargin(custCreatLbl, new Insets(0,-100,0,100));
				gp.add(custCreatLbl, 1, 1);

//				long entry_id=accountDAO.getAccountEntryId()+1;

				long invoiceId=salesDAO.getSalesInvoiceId()+1;
				int length=String.valueOf(invoiceId).length();
				Label saleInvoiceLbl =new Label();
				saleInvoiceLbl.setText(String.format("SI"+"%0"+(6-length)+"d", (invoiceId)));
				GridPane.setMargin(saleInvoiceLbl, new Insets(0,0,0,-200));
				gp.add(saleInvoiceLbl, 6, 0);
				
				invoicDatePick= new JFXDatePicker();
				invoicDatePick.setValue(LocalDate.now());
				invoicDatePick.setPromptText("Entry Date");
				invoicDatePick.setMaxWidth(150);
				invoicDatePick.setEditable(true);
				invoicDatePick.setShowWeekNumbers(false);
				invoicDatePick.getStyleClass().add("date-pick");

				KeyEventHandler.dateKeyEvent(invoicDatePick);
				GridPane.setMargin(invoicDatePick, new Insets(0,-50,0,0));
//				GridPane.setMargin(invoicDatePick, new Insets(0,0,0,-200));
				gp.add(invoicDatePick, 0, 1);

				HBox custComboHB= new HBox();
				
				custCombo= new JFXComboBox<>();
				custCombo.setValue(deliveryMemo.getCustomer().getFull_name());
				custCombo.setVisible(true);

//				custCombo.setStyle("-fx-font-size:15");
				custCombo.getStyleClass().add("jf-combo-box");
				custCombo.setStyle("-fx-font-size:12");
				custCombo.setMaxWidth(110);
				custCombo.setLabelFloat(true);
				custCombo.setPromptText("Customer Name");
				custCombo.setEditable(true);




				ObservableList<String> custObsData = FXCollections.observableArrayList();
				List<Customer> custList= salesDAO.getCustomerNames();
				Iterator<Customer> itr= custList.iterator();
				while(itr.hasNext()) {
					Customer customer=itr.next();
					if(!customer.getFull_name().equals("null")) {
					custObsData.add(customer.getFull_name());
					}
				}
				custCombo.setItems(custObsData);

				new AutoCompleteComboBoxListener<>(custCombo);

				custCombo.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub

						for(Customer c:custList) {
							if(custCombo.getValue()!=null) {
							if(custCombo.getValue().equals(c.getFull_name())) {
								address.setText(c.getAddress());
								stateCombo.setValue(c.getState());
								prmConTxt.setText(c.getContact());
								gstTxt.setText(c.getGstin());
							}
							}
						}
					}
				});

				address= new JFXTextField();
				address.setText(deliveryMemo.getCustomer().getAddress());
				address.setPromptText("Address");
				address.setStyle("-fx-font-size:12");
				address.setLabelFloat(true);
				address.setMaxWidth(80);
				
				prmConTxt=new JFXTextField();
				prmConTxt.setText(deliveryMemo.getCustomer().getContact());
				prmConTxt.setPromptText("Contact Number");
				prmConTxt.setStyle("-fx-font-size:12");
				prmConTxt.setLabelFloat(true);
				prmConTxt.setMaxWidth(80);
				
				stateCombo= new JFXComboBox<>();
				stateCombo.setLabelFloat(true);
				stateCombo.setPromptText("State");
				stateCombo.setStyle("-fx-font-size:12");

//				stateCombo.setMaxWidth(10);
				stateCombo.getItems().addAll(fillStateCombo());
				stateCombo.setValue(deliveryMemo.getCustomer().getState());
				stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent event) {
						// TODO Auto-generated method stub
//						errorTip.hide();
						String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
						if(s!=null) {
							stateCombo.requestFocus();
							stateCombo.getSelectionModel().select(s);

						}
					}
				});

				gstTxt= new JFXTextField();
				gstTxt.setText(deliveryMemo.getCustomer().getGstin());
				gstTxt.setPromptText("GSTIN");
				gstTxt.setLabelFloat(true);
				gstTxt.setMaxWidth(100);
				gstTxt.setStyle("-fx-font-size:12");


				custComboHB.getChildren().addAll(custCombo,address,stateCombo,prmConTxt,gstTxt);
				custComboHB.setSpacing(20);
				GridPane.setMargin(custComboHB, new Insets(0,0,0,-200));
				gp.add(custComboHB, 2, 2);




				final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
				     public DateCell call(final DatePicker datePicker) {
				         return new DateCell() {
				             @Override public void updateItem(LocalDate item, boolean empty) {
//				                 super.updateItem(item, empty);


				                 if (item.isAfter(LocalDate.now())) {
				                     // Tomorrow is too soon.
				                     setDisable(true);
				                 }
				             }
				         };
				     }
				 };
				 invoicDatePick.setDayCellFactory(dayCellFactory);


//				GridPane.setMargin(custCombo, new Insets(0,0,0,20));
//				gp.add(custCombo, 0, 2);



//				invoicDatePick.setMinWidth(100);
//				GridPane.setMargin(custCombo, new Insets(0,0,0,20));
//				gp.add(invoicDatePick, 1, 2);



//				custCombo.setConverter(new StringConverter<String>() {
		//
//					@Override
//					public String toString(String object) {
//						// TODO Auto-generated method stub
//						if(object==null)return null;
		//
//						return object.toString();
//					}
		//
//					@Override
//					public String fromString(String string) {
//						// TODO Auto-generated method stub
////						Customer customer=new Customer();
////						for(String cust:custCombo.getItems()) {
////							cust
////						}
//						return string;
//					}
//				});



				HBox prodHB = new HBox();

				Label addProdLbl =  new Label("Add Product");
				GridPane.setMargin(addProdLbl, new Insets(0,-50,0,0));
				gp.add(addProdLbl, 0, 4);
//				Label prodLbl = new Label("Product");
//				gp.add(prodLbl, 0, 5);

//				BarCodeRead barCodeRead= new BarCodeRead();

				prodTxt= new JFXComboBox<>();
				prodTxt.setLabelFloat(true);
				prodTxt.setPromptText("Select Product");
				prodTxt.setStyle("-fx-font-size:12");
//				new AutoCompleteComboBoxListener<>(prodTxt);

//				prodTxt.setDisable(true);

//				prodTxt.setEditable(true);
				prodTxt.setMaxWidth(100);

				prodTxt.getItems().clear();
				prodList.clear();

				productList=goodsDAO.showProductList();

				for(Product p:productList) {
					if(p.getCategory()!=null) {
					prodList.add(p.getProduct_name()+"        "+p.getCategory()+"("+p.getSubGroup()+")");
					}
				}

				if(!prodList.isEmpty()) {
					prodTxt.getItems().addAll(prodList);
				}
				prodTxt.getStyleClass().add("jf-combo-box");
				new AutoCompleteComboBoxListener<>(prodTxt);
		//
				
				
//				hsnTxt.clear();
				hsnTxt=new JFXTextField();
				hsnTxt.setPromptText("HSN/SAC");
				hsnTxt.setLabelFloat(true);
				hsnTxt.setEditable(false);
				hsnTxt.setStyle("-fx-font-size:12");



				//unitCombo.getItems().clear();
				unitCombo= new JFXTextField();
				unitCombo.setLabelFloat(true);
				unitCombo.setPromptText("Select Unit");
				unitCombo.setStyle("-fx-font-size:12");
				unitCombo.setMaxWidth(80);
				unitCombo.setEditable(false);
//				unitCombo.getStyleClass().add("jf-combo-box");

//				unitCombo.add(goodsController.fillUnitList());
//				new AutoCompleteComboBoxListener<>(unitCombo);
//				unitCombo.setConverter(new StringConverter<String>() {
		//
//					@Override
//					public String toString(String object) {
//						// TODO Auto-generated method stub
//						if(object==null)return null;
		//
//						return object.toString();
//					}
		//
//					@Override
//					public String fromString(String string) {
//						// TODO Auto-generated method stub
//						Customer customer=new Customer();
//						for(String cust:custCombo.getItems()) {
//							cust
//						}
//						return string;
//					}
//				});

//				unitCombo.getItems().addAll("KG","PCS","LIT","ML","GRAM");
				
				quantityTxt= new JFXTextField();
				quantityTxt.setPromptText("Quantity");
				quantityTxt.setText("0");
				quantityTxt.setStyle("-fx-font-size:12");
				quantityTxt.setLabelFloat(true);
				quantityTxt.setMaxWidth(50);
				
				rateTxt= new JFXTextField();
				rateTxt.setLabelFloat(true);
				rateTxt.setStyle("-fx-font-size:12");
				rateTxt.setMaxWidth(50);
				rateTxt.setPromptText("Rate");
				rateTxt.setText("0.00");
				
				discTxt= new JFXTextField();
				discTxt.setMaxWidth(50);
				discTxt.setStyle("-fx-font-size:12");
				discTxt.setLabelFloat(true);
				discTxt.setPromptText("Discount");
				discTxt.setText("0.0");

//				taxableTxt.setPromptText("Taxable"+"\n Amt");
//				taxableTxt.setText("0");
//				taxableTxt.setLabelFloat(true);
//				taxableTxt.setMaxWidth(50);
//				taxableTxt.setEditable(false);
//				taxableTxt.setStyle("-fx-font-size:12");
				
				cGstTxt= new JFXTextField();
//				cGstTxt.clear();
				cGstTxt.setPromptText("CGST");
				cGstTxt.setMaxWidth(50);
				cGstTxt.setEditable(false);
				cGstTxt.setStyle("-fx-font-size:12");
				cGstTxt.setLabelFloat(true);
				
				
//				gstRsTxt.clear();
				gstRsTxt=new JFXTextField();
				gstRsTxt.setPromptText("SGST");
				gstRsTxt.setMaxWidth(50);
				gstRsTxt.setEditable(false);
				gstRsTxt.setStyle("-fx-font-size:12");
				gstRsTxt.setLabelFloat(true);
				
				
//				iGstTxt.clear();
				iGstTxt= new JFXTextField();
				iGstTxt.setPromptText("IGST");
				iGstTxt.setMaxWidth(50);
				iGstTxt.setEditable(false);
				iGstTxt.setStyle("-fx-font-size:12");
				iGstTxt.setLabelFloat(true);

//				cessTxt.clear();
				cessTxt= new JFXTextField();
				cessTxt.setPromptText("Cess");
				cessTxt.setMaxWidth(50);
				cessTxt.setEditable(false);
				cessTxt.setStyle("-fx-font-size:12");
				cessTxt.setLabelFloat(true);

//				gstRsTxt.setPromptText("GST In %");

				totalTxt= new JFXTextField();
				totalTxt.setEditable(false);
//				hsnTxt.setStyle("-fx-font-size:12");
				totalTxt.setText("0.00");
				totalTxt.setPromptText("Total");
				totalTxt.setLabelFloat(true);
				totalTxt.setStyle("-fx-font-size: 15px;-fx-font-weight:bold");
				totalTxt.setFocusColor(Color.TRANSPARENT);
				totalTxt.setUnFocusColor(Color.TRANSPARENT);
				totalTxt.setMaxWidth(100);
				HBox.setMargin(totalTxt, new Insets(0, 0, 20, 20));



				stateCombo.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub

						String state=stateCombo.getSelectionModel().getSelectedItem();
						if(state!=null) {
							prodTxt.setDisable(false);
						if(state.contains("Maharashtra")) {
							prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
							prodHB.getChildren().add(6, cGstTxt);
							prodHB.getChildren().add(7,gstRsTxt);
							HBox.setMargin(gstRsTxt, new Insets(0,-20,0,0));
//							GridPane.setMargin(prodHB, new Insets(0,0,0,50));
//							gp.setPadding(new Insets(10,10,10,10));
						}
						else {
							prodHB.getChildren().removeAll(iGstTxt,cessTxt,cGstTxt,gstRsTxt);
							prodHB.getChildren().add(6,iGstTxt);
							prodHB.getChildren().add(7,cessTxt);
							HBox.setMargin(cessTxt, new Insets(0,-20,0,0));
//							GridPane.setMargin(prodHB, new Insets(0,0,0,50));
//							HBox.setMargin(totalTxt, new Insets(0,5,0,-50));
						}
					}
					}
				});

			    JFXButton addMoreBtn = new JFXButton(" Add ");
				addMoreBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");
				addMoreBtn.setMaxWidth(80);
				addMoreBtn.setDisable(true);

				JFXButton editBtn=new JFXButton("Edit");
				editBtn.setMaxWidth(80);
				editBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");

				RadioButton addItem = new RadioButton("Add Item");
				addItem.setSelected(true);
				addItem.setUserData("Add Item");

				RadioButton editItem=new RadioButton("Edit Item");
//				editItem.setDisable(true);
				editItem.setUserData("Edit Item");
				ToggleGroup tg = new ToggleGroup();
				addItem.setToggleGroup(tg);
				editItem.setToggleGroup(tg);
				VBox vb1 = new VBox();
				vb1.setStyle("-fx-font-size:15px; -fx-font-weight:bold;");
				vb1.getChildren().addAll(addItem,editItem);
				vb1.setMaxHeight(30);
//				HBox.setMargin(addMoreBtn, new Insets(0,-50,0,20));
				HBox.setMargin(vb1, new Insets(0,0,0,20));
				prodHB.getChildren().addAll(prodTxt,hsnTxt,unitCombo,quantityTxt,rateTxt,discTxt,cGstTxt,gstRsTxt,totalTxt,addMoreBtn,vb1);

				prodHB.setSpacing(20);
				GridPane.setMargin(prodHB, new Insets(0,-50,0,0));
				gp.add(prodHB, 0, 6,8,1);


//				Label grTotalLbl = new Label("Grand Total");
//				gp.add(grTotalLbl, 4, 9);

				ObservableList<SalesProduct> prodData = FXCollections.observableArrayList();
				TableView<SalesProduct> prodView = new TableView<SalesProduct>();

				List<DeliveryProduct> prodList=salesDAO.getDeliveryProduct(deliveryMemo);
				for(DeliveryProduct dp:prodList) {
					double subTotal=dp.getTotal()*(100/(100+dp.getProduct().getGst()));
					double gst=dp.getTotal()-subTotal;
					String rate=BigDecimal.valueOf(dp.getRate())
							.setScale(2,RoundingMode.HALF_UP)
							.toPlainString();
					String cgst="";
					String igst="";
					String subTotalString=BigDecimal.valueOf(subTotal)
							.setScale(2,RoundingMode.HALF_UP)
							.toPlainString();
					String totalString=BigDecimal.valueOf(dp.getTotal())
							.setScale(2, RoundingMode.HALF_UP)
							.toPlainString();
					if(deliveryMemo.getCustomer().getState().equals("Maharashtra")) {
						double gstRate=dp.getProduct().getGst()/2;
						cgst=String.format("%.2f", (gst/2))+"\n("+(gstRate)+"%)";
						prodData.add(new SalesProduct(dp.getProduct().getProduct_name(),dp.getId(),dp.getProduct().getHsnNo(),dp.getQuantity(),dp.getUnit(),dp.getRate(),0,dp.getTotal(),subTotal,cgst,cgst,"","",dp.getProduct(),rate,"",subTotalString,totalString,dp,dp.getProduct().getQuantity()));
					}else {
						igst=(gst)+"\n("+(dp.getProduct().getGst())+"%)";
						prodData.add(new SalesProduct(dp.getProduct().getProduct_name(),dp.getId(),dp.getProduct().getHsnNo(),dp.getQuantity(),dp.getUnit(),dp.getRate(),0,dp.getTotal(),subTotal,"","",igst,"0.0",dp.getProduct(),rate,"",subTotalString,totalString,dp,dp.getProduct().getQuantity()));
						
					}
					
				}
				prodView.setItems(prodData);
				prodView.requestFocus();
				prodView.getSelectionModel().selectFirst();
				prodView.getFocusModel().focus(0);
				
				prodView.setMaxSize(1100, 200);
				TableColumn<SalesProduct, String> prodName = new TableColumn<SalesProduct, String>("Product Name");
				prodName.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
				prodName.setMinWidth(140);
				TableColumn<SalesProduct, Long> hsnCol = new TableColumn<SalesProduct, Long>("HSN"+"\n/SAC");
				hsnCol.setCellValueFactory(new PropertyValueFactory<>("hsnNo"));
				hsnCol.setPrefWidth(90);
				TableColumn<SalesProduct, String> unitCol = new TableColumn<SalesProduct, String>("Unit");
				unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
				unitCol.setPrefWidth(90);
				TableColumn<SalesProduct, Integer> quantityCol = new TableColumn<SalesProduct, Integer>("Qty.");
				quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				quantityCol.setPrefWidth(80);
				TableColumn<SalesProduct, String> rateCol = new TableColumn<SalesProduct, String>("Rate");
				rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
				rateCol.setPrefWidth(80);
				TableColumn<SalesProduct, String> discCol = new TableColumn<SalesProduct, String>("Disc.\n"+" Rs.");
				discCol.setCellValueFactory(new PropertyValueFactory<>("discountString"));
				discCol.setPrefWidth(80);
				TableColumn<SalesProduct, SalesProduct> taxableCol = new TableColumn<SalesProduct, SalesProduct>("Taxable\n"+"Amount");
				taxableCol.setCellValueFactory(new PropertyValueFactory<>("subTotalString"));
				taxableCol.setPrefWidth(100);
//				taxableCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>(){
//					@Override
//					protected void updateItem(SalesProduct salesProduct,boolean empty) {
//						if(salesProduct==null||empty) {
//							setText(null);
//							return;
//						}
//						setText(taxableTxt.getText());
//					}
//				});

				TableColumn<SalesProduct, SalesProduct> cgstCol = new TableColumn<SalesProduct, SalesProduct>("CGST\n"+"  (%)");
				cgstCol.setCellValueFactory(new PropertyValueFactory<>("cgst"));
				cgstCol.setPrefWidth(90);
//				cgstCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>() {
//					@Override
//					protected void updateItem(SalesProduct salesProduct,boolean empty) {
//						if(salesProduct==null||empty) {
//							setGraphic(null);
//							return;
//						}
		//
//							System.out.println(cGstTxt.getText());
//							String cgst=cGstTxt.getText()+"("+salesProduct.getGst()/2+"%)";
//							setText(cgst);
		//
//					}
//				});
				TableColumn<SalesProduct, SalesProduct> sgstCol = new TableColumn<SalesProduct, SalesProduct>("SGST\n"+"  (%)");
				sgstCol.setCellValueFactory(new PropertyValueFactory<>("sgst"));
				sgstCol.setPrefWidth(90);
//				sgstCol.setCellFactory(e-> new TableCell<SalesProduct,SalesProduct>(){
//					@Override
//					protected void updateItem(SalesProduct salesProduct,boolean empty) {
//						if(salesProduct==null||empty) {
//							setText(null);
//							return;
//						}
//						else {
//							System.out.println(gstRsTxt.getText());
		//
//							String sgst=gstRsTxt.getText()+"("+salesProduct.getGst()/2+"%)";
//							setText(sgst);
//						}
//					}
//				});

				TableColumn<SalesProduct, SalesProduct> igstCol = new TableColumn<SalesProduct, SalesProduct>("IGST\n"+"  (%)");
				igstCol.setCellValueFactory(new PropertyValueFactory<>("igst"));
				igstCol.setPrefWidth(90);
//				igstCol.setCellFactory(e-> new TableCell<SalesProduct,SalesProduct>(){
//					@Override
//					protected void updateItem(SalesProduct salesProduct,boolean empty) {
//						if(salesProduct==null||empty) {
//							setText(null);
//							return;
//						}
////						else {
//							String igst=iGstTxt.getText()+"("+salesProduct.getGst()+"%)";
//							setText(igst);
////						}
//					}
//				});
				TableColumn<SalesProduct, SalesProduct> cessCol = new TableColumn<SalesProduct, SalesProduct>("Cess\n"+" (%)");
//				cessCol.setCellValueFactory(new PropertyValueFactory<>("gst"));
				cessCol.setCellValueFactory(new PropertyValueFactory<>("cess"));
				cessCol.setPrefWidth(90);
//				cessCol.setCellFactory(e-> new TableCell<SalesProduct,SalesProduct>(){
//					@Override
//					protected void updateItem(SalesProduct salesProduct,boolean empty) {
//						if(salesProduct==null||empty) {
//							setText(null);
//							return;
//						}
////						else {
//							String cess=cessTxt.getText();
//							setText(cess);
////						}
//					}
//				});

				TableColumn<SalesProduct, String> totalCol = new TableColumn<SalesProduct, String>("Total");
				totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
				totalCol.setPrefWidth(90);
				
				TableColumn<SalesProduct, SalesProduct> actionCol = new TableColumn<SalesProduct, SalesProduct>("Action");
				actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
				actionCol.setCellFactory(e->new TableCell<SalesProduct,SalesProduct>(){

					@Override
				    protected void updateItem(SalesProduct salesProduct,boolean empty){
						if(salesProduct==null){
							setGraphic(null);
							return;
						}else{
							deleteBtn=new JFXButton("Delete");
							setGraphic(deleteBtn);
//							deleteBtn.setDisable(false);
							deleteBtn.getStyleClass().add("del-btn");
							deleteBtn.setAlignment(Pos.CENTER);
							deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									// TODO Auto-generated method stub
									Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
									alert.setTitle("Confirmation");
//									alert.setHeaderText("HI");
									Optional<ButtonType> result= alert.showAndWait();
									if(result.get()==ButtonType.OK){
										getTableView().getItems().remove(salesProduct);
										getTableView().refresh();
										
										grandTotal=0;
										if(prodData.size()==0)
											grTotalTxt.setText("0.0");
										else{
										for(SalesProduct p:prodData){
											grandTotal=grandTotal+p.getTotal();
											grTotalTxt.setText(String.format("%.2f",grandTotal));

										}
										}
									}

								}
							});


						}
					}
				});
				prodView.getColumns().addAll(prodName,hsnCol,unitCol,quantityCol,rateCol,discCol,taxableCol,cgstCol,sgstCol,igstCol,cessCol,totalCol,actionCol);
				
				GridPane.setMargin(prodView, new Insets(-20,-50,0,0));


				gp.add(prodView, 0, 8,12,1);

//				prodView.prefHeightProperty().bind(prodView.prefHeightProperty().multiply(prodView.getItems().size()/2));


				prodTxt.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(prodTxt.getValue()!=null){
							addMoreBtn.setDisable(false);
							editBtn.setDisable(false);
							if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
								for(Product p:productList) {
									if(prodTxt.getValue().contains(p.getProduct_name())) {
										double gst=p.getGst()/2;
										gstRate=p.getGst();
//										cessRate=p.getCess();
										cGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
										gstRsTxt.setText(String.valueOf("0 ("+gst+"%)"));
										hsnTxt.setText(String.valueOf(p.getHsnNo()));
										rateTxt.setText(String.valueOf(p.getSellPrice()));
										unitCombo.setText(String.valueOf(p.getUnit()));
									}


								}

							}

							else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
								for(Product p:productList) {
									if(prodTxt.getValue().contains(p.getProduct_name())) {
										double gst=p.getGst();
										double cess=p.getCess();
										gstRate=p.getGst();
										cessRate=p.getCess();
										iGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
										cessTxt.setText(String.valueOf("0 ("+cess+"%)"));
										hsnTxt.setText(String.valueOf(p.getHsnNo()));
										rateTxt.setText(String.valueOf(p.getSellPrice()));
										unitCombo.setText(String.valueOf(p.getUnit()));
									}

								}
							}
//							return;
						}
					}
				});

				unitCombo.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(unitCombo.getText()!=null) {
						addMoreBtn.setDisable(false);
						editBtn.setDisable(false);
						}
					}
				});


				addMoreBtn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
//						clearLabels();
//						gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
//							System.out.println(validateControls());
						boolean productFlag=false;
						boolean unitFlag=false;
							if(!validateOnAddEdit()){
							return;
						}
//							deleteBtn.setDisable(false);
							SalesProduct salesProduct = new SalesProduct();
							Product product = new Product();
							salesProduct.setProduct(product);
							for(Product p:productList) {
							if(prodTxt.getValue().contains(p.getProduct_name())) {
							salesProduct.getProduct().setId(p.getId());
							salesProduct.getProduct().setProduct_name(p.getProduct_name());
							salesProduct.setProd_name(p.getProduct_name());
							salesProduct.setGst(p.getGst());
							salesProduct.setHsnNo(Long.parseLong(hsnTxt.getText()));
							salesProduct.setCurrentStock(p.getQuantity());
							productFlag=true;
									}
								}
							if(!productFlag) {
								errorTip.setText("No Product found with specified name!");
								prodTxt.setTooltip(errorTip);
								errorTip.show(prodTxt,100,160);
								return;
							}
//							salesProduct.setProd_name(prodTxt.getValue());
//							if(!unitCombo.getId().contains(unitCombo.getText())) {
//								errorTip.setText("Unit Name not found!");
//								unitCombo.setTooltip(errorTip);
//								errorTip.show(unitCombo,300,160);
//								return;
//							}

							salesProduct.setUnit(unitCombo.getText());
							salesProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));

							String trunRateAmt=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
								    .setScale(2, RoundingMode.HALF_UP)
								    .toPlainString();


//							System.out.println(trunRateAmt);
							salesProduct.setRateString(trunRateAmt);
							salesProduct.setRate(Double.parseDouble(rateTxt.getText()));

							String trunDiscAmt=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
								    .setScale(2, RoundingMode.HALF_UP)
								    .toPlainString();

							salesProduct.setDiscount(Double.parseDouble(discTxt.getText()));
							salesProduct.setDiscountString(trunDiscAmt);


								String cgstTxt=cGstTxt.getText().replace("(", "\n(");
								salesProduct.setCgst(cgstTxt);

//							if(iGstTxt.getText().contains("(")) {/
								String igstTxt=iGstTxt.getText().replace("(", "\n(");
								salesProduct.setIgst(igstTxt);
//							}
//							salesProduct.setSgst(gstRsTxt.getText());
//							if(gstRsTxt.getText().contains("(")) {
								String sgstTxt=gstRsTxt.getText().replace("(", "\n(");
								salesProduct.setSgst(sgstTxt);
//							}

//							salesProduct.setCess(cessTxt.getText());
							if(cessTxt.getText().contains("(")) {
								String cesssTxt=cessTxt.getText().replace("(", "\n(");
								salesProduct.setCess(cesssTxt);
							}
//							salesProduct.setGst(Double.parseDouble());
//							NumberFormat formatter = new DecimalFormat("#0.00");

							String trunTaxablAmt=BigDecimal.valueOf(taxableAmt)
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();

							double taxAmt=BigDecimal.valueOf(taxableAmt)
									.setScale(2,RoundingMode.HALF_UP)
									.doubleValue();

							salesProduct.setSubTotal(taxAmt);
							salesProduct.setSubTotalString(trunTaxablAmt);

							salesProduct.setTotal(Double.parseDouble(totalTxt.getText()));
							String trunTotalAmt=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
								    .setScale(2, RoundingMode.HALF_UP)
								    .toPlainString();
							salesProduct.setTotalString(trunTotalAmt);

							for(SalesProduct sp:prodData) {
								if(sp.getProd_name().equals(salesProduct.getProd_name())) {
//									dupliErrLbl.setText("Duplicate entries are not permitted");
//									gp.add(dupliErrLbl, 0, 7);
									errorTip.setText("Duplicate entries are not permitted");
									prodTxt.setTooltip(errorTip);
									errorTip.show(prodTxt,100,200);
									return;
								}
							}

							prodData.add(salesProduct);
//							prodVie
							prodView.requestFocus();
							prodView.getSelectionModel().selectLast();
							
							cashCheck.requestFocus();
							grandTotal=grandTotal+salesProduct.getTotal();
							grTotalTxt.setText(String.format("%.2f",grandTotal));

							clearProductData();
							editItem.setDisable(false);
					}
				});

				
				tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

					@Override
					public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
						// TODO Auto-generated method stub
						if(tg.getSelectedToggle()!=null) {
							String item=tg.getSelectedToggle().getUserData().toString();
							if(item.equals("Add Item")) {
								prodHB.getChildren().removeAll(editBtn,addMoreBtn);
								prodHB.getChildren().add(9,addMoreBtn);
								deleteBtn.setDisable(false);
								clearProductData();
							}else if(item.equals("Edit Item")) {
								deleteBtn.setDisable(true);
								SalesProduct salesProduct = prodView.getSelectionModel().getSelectedItem();
								int index = prodView.getSelectionModel().getSelectedIndex();
								prodTxt.setValue(salesProduct.getProd_name());
								unitCombo.setText(salesProduct.getUnit());
								hsnTxt.setText(String.valueOf(salesProduct.getHsnNo()));
								quantityTxt.setText(String.valueOf(salesProduct.getQuantity()));

								String trunRateAmt=BigDecimal.valueOf(salesProduct.getRate())
									    .setScale(2, RoundingMode.HALF_UP)
									    .toPlainString();

								rateTxt.setText(trunRateAmt);

								String trunDiscAmt=BigDecimal.valueOf(salesProduct.getDiscount())
									    .setScale(2, RoundingMode.HALF_UP)
									    .toPlainString();
								discTxt.setText(trunDiscAmt);

								gstRsTxt.setText(String.valueOf(salesProduct.getSgst()));
								cGstTxt.setText(String.valueOf(salesProduct.getCgst()));
								iGstTxt.setText(String.valueOf(salesProduct.getIgst()));
								cessTxt.setText(String.valueOf(salesProduct.getCess()));

//								String trunTaxableAmt=BigDecimal.valueOf(salesProduct.getSubTotal())
//									    .setScale(2, RoundingMode.HALF_UP)
//									    .toPlainString();
//								taxableTxt.setText(trunTaxableAmt);

								String trunTotalAmt=BigDecimal.valueOf(salesProduct.getTotal())
									    .setScale(2, RoundingMode.HALF_UP)
									    .toPlainString();
								totalTxt.setText(trunTotalAmt);

								grandTotal=grandTotal-salesProduct.getTotal();
								prodHB.getChildren().remove(addMoreBtn);
								prodHB.getChildren().add(9, editBtn);

								editBtn.setOnAction(new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent event) {
										// TODO Auto-generated method stub
//										clearLabels();
//										gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
										if(!validateOnAddEdit()){
											return;
										}

//										PurchaseProduct purchaseProduct1 = new PurchaseProduct();
										salesProduct.setProd_name(prodTxt.getValue());
										salesProduct.setUnit(unitCombo.getText());
										salesProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
										String trunRateAmt1=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
												.setScale(3, RoundingMode.HALF_UP)
												.toPlainString();
										salesProduct.setRateString(trunRateAmt1);

										salesProduct.setRate(Double.parseDouble(rateTxt.getText()));
										String trunDiscAmt1=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
												.setScale(3, RoundingMode.HALF_UP)
												.toPlainString();
										salesProduct.setDiscountString(trunDiscAmt1);

										salesProduct.setDiscount(Double.parseDouble(discTxt.getText()));
//										salesProduct.setGst(Double.parseDouble());

										String cgstTxt=cGstTxt.getText().replace("(", "\n(");
										salesProduct.setCgst(cgstTxt);

//									if(iGstTxt.getText().contains("(")) {/

										String igstTxt=iGstTxt.getText().replace("(", "\n(");
										salesProduct.setIgst(igstTxt);
//									}
//									salesProduct.setSgst(gstRsTxt.getText());
//									if(gstRsTxt.getText().contains("(")) {
										String sgstTxt=gstRsTxt.getText().replace("(", "\n(");
										salesProduct.setSgst(sgstTxt);
//									}

//									salesProduct.setCess(cessTxt.getText());
									if(cessTxt.getText().contains("(")) {
										String cesssTxt=cessTxt.getText().replace("(", "\n(");
										salesProduct.setCess(cesssTxt);
									}

										String trunTaxablAmt=BigDecimal.valueOf(taxableAmt)
										    .setScale(2, RoundingMode.HALF_UP)
										    .toPlainString();

											double taxAmt=BigDecimal.valueOf(taxableAmt)
													.setScale(2,RoundingMode.HALF_UP)
													.doubleValue();
											
										salesProduct.setSubTotal(taxAmt);
								salesProduct.setSubTotalString(trunTaxablAmt);

										String trunTotalAmt1=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
												.setScale(3, RoundingMode.HALF_UP)
												.toPlainString();
										salesProduct.setTotalString(trunTotalAmt1);
										salesProduct.setTotal(Double.parseDouble(totalTxt.getText()));

										prodData.set(index,salesProduct);
										cashCheck.requestFocus();
										grandTotal=0;
										for(SalesProduct p:prodData) {
											grandTotal=grandTotal+p.getTotal();
										}
										grTotalTxt.setText(String.format("%.2f",grandTotal));
										prodHB.getChildren().remove(editBtn);
										prodHB.getChildren().add(10, addMoreBtn);
										addItem.setSelected(true);
										clearProductData();
									}
								});

							}
						}
					}
				});





//				Label payTypeLbl =  new Label("Payment Type");
//				gp.add(payTypeLbl, 4, 10);

				HBox paymentBox= new HBox();

				grTotalTxt= new JFXTextField();
				grTotalTxt.setLabelFloat(true);
				grTotalTxt.setEditable(false);
				grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
				grTotalTxt.setFocusColor(Color.TRANSPARENT);
				grTotalTxt.setPromptText("Grand Total");
				grTotalTxt.setStyle("-fx-font-size: 20px; -fx-font-weight:bold;");
				grTotalTxt.setText(String.valueOf(deliveryMemo.getTotal()));
				grandTotal=deliveryMemo.getTotal();
//				grTotalTxt.setMaxWidth(100);
				GridPane.setMargin(grTotalTxt, new Insets(20,-50,0,50));
				gp.add(grTotalTxt, 0, 10);
				
				JFXTextField advancAmt= new JFXTextField();
				advancAmt.setLabelFloat(true);
				advancAmt.setPromptText("Advance Amount");
				advancAmt.setEditable(false);
				advancAmt.setMinWidth(100);
				advancAmt.setText(String.valueOf(deliveryMemo.getAdvanceAmt()));
				GridPane.setMargin(advancAmt, new Insets(20,-50,0,50));
				gp.add(advancAmt, 0, 11);
				

//				payCombo.setLabelFloat(true);
//				payCombo.getItems().clear();
//				payCombo.getItems().addAll("Full Payment");
//				payCombo.setValue("Full Payment");
				//payCombo.getItems().addAll("Full Payment","Part Payment","UnPaid");
//				payCombo.getStyleClass().add("jf-combo-box");

//				payCombo.setPromptText("Payment Type");
//				GridPane.setMargin(payCombo, new Insets(20,-50,0,50));
//				payCombo.requestFocus();
//				payCombo.setVisible(false);/
//				gp.add(payCombo, 0, 11);
//				partPayTxt.setPromptText("Part Pay Amount");
//				partPayTxt.setStyle("-fx-font-size:14");
//				partPayTxt.setLabelFloat(true);
//				partPayTxt.setText("0");
		//
//				partPayTxt.setVisible(false);
//				GridPane.setMargin(partPayTxt, new Insets(20,-50,0,50));
//				gp.add(partPayTxt, 0, 12);
//				HBox hb = new HBox();

//				payCombo.setOnAction(new EventHandler<ActionEvent>() {
		//
//					@Override
//					public void handle(ActionEvent event) {
//						// TODO Auto-generated method stub
//						if(payCombo.getValue()!=null){
//						if(payCombo.getValue().equals("Full Payment")) {
//							partPayTxt.setVisible(false);
//							paymentBox.setVisible(true);
//							payModeLbl.setVisible(true);
		//
//						}
//						else if(payCombo.getValue().equals("UnPaid")){
//							partPayTxt.setVisible(false);
//							paymentBox.setVisible(false);
//							payModeLbl.setVisible(false);
////							hb.setVisible(false);
//						}
//						else if(payCombo.getValue().equals("Part Payment")) {
////						gp.addRow(9, hb);
//						partPayTxt.setVisible(true);
//						paymentBox.setVisible(true);
//						payModeLbl.setVisible(true);
//						}
//					}}
//				});
//				Label payModeLbl = new Label("Payment Mode");
//				gp.add(payModeLbl, 4, 11);
//				VBox payModeHB= new VBox();

				
				payModeLbl.setStyle("-fx-font-size:14");
				GridPane.setMargin(payModeLbl, new Insets(-70,0,0,-50));
				gp.add(payModeLbl, 2, 10);
				
				cashCheck= new JFXCheckBox();
				cashCheck.setFocusTraversable(true);
				cashCheck.setText("Pay by Cash");
				cashAmtTxt= new JFXTextField();
				cashAmtTxt.setPromptText("Amount");

//				cashAmtTxt.setVisible(false);
//				cashAmtTxt.setMaxWidth(50);
//				cashAmtTxt.setLabelFloat(true);
				cashAmtTxt.setStyle("-fx-font-size:14");

				VBox cashBox= new VBox();
				cashBox.setSpacing(10);
//				cashBox.setMaxHeight(100);
				cashBox.setSpacing(10);
				cashBox.getChildren().add(cashCheck);
				
				bankCheck= new JFXCheckBox();
				bankCheck.setText("Pay by Cheque/DD");
				bankTxt= new JFXTextField();
				bankTxt.setPromptText("Bank Name");
//				bankTxt.setVisible(false);
				bankTxt.setStyle("-fx-font-size:14");

//				bankTxt.setMaxWidth(50);
//				bankTxt.setLabelFloat(true);/
				cheqTxt= new JFXTextField();
				cheqTxt.setPromptText("Cheque No/DD No");
				cheqTxt.setStyle("-fx-font-size:14");
//				cheqTxt.setVisible(false);
//				cheqTxt.setMaxWidth(50);
//				cheqTxt.setLabelFloat(true);
				bankAmtTxt= new JFXTextField();
				bankAmtTxt.setPromptText("Amount");
//				bankAmtTxt.setVisible(false);
//				bankAmtTxt.setLabelFloat(true);
				bankAmtTxt.setStyle("-fx-font-size:14");

				VBox bankBox= new VBox();
				bankBox.getChildren().addAll(bankCheck);
				bankBox.setSpacing(10);
				
				cardCheck= new JFXCheckBox();
				cardCheck.setText("Pay by Credit/Debit Card/NetBanking");
				transId= new JFXTextField();
				transId.setPromptText("Transaction ID");
				transId.setStyle("-fx-font-size:14");
//				transId.setVisible(false);
//				transId.setMaxWidth(50);
//				transId.setLabelFloat(true);
				cardAmtTxt= new  JFXTextField();
				cardAmtTxt.setPromptText("Amount");
				cardAmtTxt.setStyle("-fx-font-size:14");
//				cardAmtTxt.setVisible(false);
//				cardAmtTxt.setMaxWidth(50);
//				cardAmtTxt.setLabelFloat(true);

				VBox cardBox= new VBox();
				cardBox.getChildren().add(cardCheck);
				cardBox.setSpacing(10);
				voucherCheck= new JFXCheckBox();
				voucherCheck.setText("Pay by Voucher/Wallet");
				voucherTypeTxt=new JFXTextField();
				voucherTypeTxt.setPromptText("Voucher Code/Wallet Name");
//				voucherTypeTxt.setVisible(false);
				voucherTypeTxt.setStyle("-fx-font-size:14");
//				voucherTypeTxt.setMaxWidth(50);
//				voucherTypeTxt.setLabelFloat(true);
				vouchAmtTxt= new JFXTextField();
				vouchAmtTxt.setPromptText("Amount");
//				vouchAmtTxt.setVisible(false);
				vouchAmtTxt.setStyle("-fx-font-size:14");
//				vouchAmtTxt.setMaxWidth(50);
//				vouchAmtTxt.setLabelFloat(true);

				VBox vouchBox= new VBox();
				vouchBox.getChildren().addAll(voucherCheck);
				vouchBox.setSpacing(10);


				paymentBox.setSpacing(20);
				paymentBox.setMaxSize(180, 50);
				paymentBox.setStyle("-fx-border-style: solid;-fx-padding:15;-fx-border-width: 2;-fx-border-insets:5; -fx-border-radius: 5;");
				paymentBox.getChildren().addAll(cashBox,bankBox,cardBox,vouchBox);


//				payModeCombo.getItems().clear();
//				payModeCombo.getItems().addAll("Cash","Cheque","NEFT/RTGS","Card");
//				new AutoCompleteComboBoxListener<>(payModeCombo);
//				payModeCombo.setLabelFloat(true);
//				payModeCombo.setPromptText("Select Payment Mode");

				GridPane.setMargin(paymentBox, new Insets(-130,0,0,-50));
				gp.add(paymentBox, 2, 12);


				cashCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						// TODO Auto-generated method stub

//						cashBox.getChildren().remove(cashAmtTxt);
						if(newValue) {
							cardBox.getChildren().remove(cardAmtTxt);
							vouchBox.getChildren().remove(vouchAmtTxt);
							bankBox.getChildren().remove(bankAmtTxt);

							if(bankCheck.isSelected()||cardCheck.isSelected()||voucherCheck.isSelected()) {
								cashBox.getChildren().add(cashAmtTxt);
							}

							boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);
							boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
							boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);

						}
						else {
							cashAmtTxt.clear();
							cashBox.getChildren().remove(cashAmtTxt);
							if(!(bankCheck.isSelected()&&cardCheck.isSelected()&&voucherCheck.isSelected())) {
								if(bankCheck.isSelected()&&!(cardCheck.isSelected()||voucherCheck.isSelected())) {
									bankAmtTxt.clear();
									bankBox.getChildren().remove(bankAmtTxt);
								}
								else if(cardCheck.isSelected()&&!(bankCheck.isSelected()||voucherCheck.isSelected())) {
									cardAmtTxt.clear();
									cardBox.getChildren().remove(cardAmtTxt);
								}

								else if(voucherCheck.isSelected()&&!(bankCheck.isSelected()||cardCheck.isSelected())) {
									vouchAmtTxt.clear();
									vouchBox.getChildren().remove(vouchAmtTxt);
								}
							}

//							cashAmtTxt.setVisible(false);
						}

					}
				});


		//
				bankCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						// TODO Auto-generated method stub
						if(newValue) {

						bankBox.getChildren().addAll(bankTxt,cheqTxt);
						if(cashCheck.isSelected()||cardCheck.isSelected()||voucherCheck.isSelected()) {
							bankBox.getChildren().add(bankAmtTxt);
//							cashBox.getChildren().add(cashAmtTxt);
//							cardBox.getChildren().add(cardAmtTxt);
//							vouchBox.getChildren().add(vouchAmtTxt);
						}

						cashBox.getChildren().remove(cashAmtTxt);
						cardBox.getChildren().remove(cardAmtTxt);
						vouchBox.getChildren().remove(vouchAmtTxt);

						boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
						boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);
						boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);


					}
					else{
							bankBox.getChildren().removeAll(bankTxt,cheqTxt,bankAmtTxt);
							bankTxt.clear();
							cheqTxt.clear();
							bankAmtTxt.clear();
							if(!(cashCheck.isSelected()&&cardCheck.isSelected()&&voucherCheck.isSelected())) {
								if(cashCheck.isSelected()&&!(cardCheck.isSelected()||voucherCheck.isSelected())) {
									cashAmtTxt.clear();
									cashBox.getChildren().remove(cashAmtTxt);
								}
								else if(cardCheck.isSelected()&&!(cashCheck.isSelected()||voucherCheck.isSelected())) {
									cardAmtTxt.clear();
									cardBox.getChildren().remove(cardAmtTxt);
								}

								else if(voucherCheck.isSelected()&&!(cashCheck.isSelected()||cardCheck.isSelected())) {
									vouchAmtTxt.clear();
									vouchBox.getChildren().remove(vouchAmtTxt);
								}
							}
						}
					}
				});

				cardCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						// TODO Auto-generated method stub

					if(newValue) {
						cardBox.getChildren().add(transId);


							if(cashCheck.isSelected()||bankCheck.isSelected()||voucherCheck.isSelected()) {
//								bankBox.getChildren().add(bankAmtTxt);
//								cashBox.getChildren().add(cashAmtTxt);
								cardBox.getChildren().add(cardAmtTxt);
//								vouchBox.getChildren().add(vouchAmtTxt);
							}

							cashBox.getChildren().remove(cashAmtTxt);
							vouchBox.getChildren().remove(vouchAmtTxt);
							bankBox.getChildren().remove(bankAmtTxt);

							boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
							boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
							boolean vouchFlag=(voucherCheck.isSelected())?vouchBox.getChildren().add(vouchAmtTxt):vouchBox.getChildren().remove(vouchAmtTxt);



					}
					else {
							cardBox.getChildren().removeAll(transId,cardAmtTxt);
							transId.clear();
							cardAmtTxt.clear();

							if(!(cashCheck.isSelected()&&bankCheck.isSelected()&&voucherCheck.isSelected())) {
								if(cashCheck.isSelected()&&!(bankCheck.isSelected()||voucherCheck.isSelected())) {
									cashAmtTxt.clear();
									cashBox.getChildren().remove(cashAmtTxt);
								}
								else if(bankCheck.isSelected()&&!(cashCheck.isSelected()||voucherCheck.isSelected())) {
									bankAmtTxt.clear();
									bankBox.getChildren().remove(bankAmtTxt);
								}

								else if(voucherCheck.isSelected()&&!(cashCheck.isSelected()||bankCheck.isSelected())) {
									vouchBox.getChildren().remove(vouchAmtTxt);
									vouchAmtTxt.clear();
								}
							}

						}
					}
				});

				voucherCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						// TODO Auto-generated method stub


						if(newValue) {
							vouchBox.getChildren().add(voucherTypeTxt);

							if(cashCheck.isSelected()||bankCheck.isSelected()||cardCheck.isSelected()) {
								vouchBox.getChildren().add(vouchAmtTxt);
							}

							cardBox.getChildren().remove(cardAmtTxt);
							bankBox.getChildren().remove(bankAmtTxt);
							cashBox.getChildren().remove(cashAmtTxt);

							boolean cashFlag=(cashCheck.isSelected())?cashBox.getChildren().add(cashAmtTxt):cashBox.getChildren().remove(cashAmtTxt);
							boolean bankFlag=(bankCheck.isSelected())?bankBox.getChildren().add(bankAmtTxt):bankBox.getChildren().remove(bankAmtTxt);
							boolean cardFlag=(cardCheck.isSelected())?cardBox.getChildren().add(cardAmtTxt):cardBox.getChildren().remove(cardAmtTxt);

//								bankBox.getChildren().add(bankAmtTxt);
//								cashBox.getChildren().add(cashAmtTxt);
//								cardBox.getChildren().add(cardAmtTxt);
//								vouchBox.getChildren().add(vouchAmtTxt);
//							}
						}
					else {
						vouchBox.getChildren().removeAll(voucherTypeTxt,vouchAmtTxt);
						voucherTypeTxt.clear();
						vouchAmtTxt.clear();
						if(!(cashCheck.isSelected()&&bankCheck.isSelected()&&cardCheck.isSelected())) {
							if(cashCheck.isSelected()&&!(bankCheck.isSelected()||cardCheck.isSelected())) {
								cashAmtTxt.clear();
								cashBox.getChildren().remove(cashAmtTxt);
							}
							else if(bankCheck.isSelected()&&!(cashCheck.isSelected()||cardCheck.isSelected())) {
								bankAmtTxt.clear();
								bankBox.getChildren().remove(bankAmtTxt);
							}

							else if(cardCheck.isSelected()&&!(cashCheck.isSelected()||bankCheck.isSelected())) {
								cardAmtTxt.clear();
								cardBox.getChildren().remove(cardAmtTxt);
							}
						}
						}
					}
				});
//				Label cheqLbl = new Label("Cheque No/Transaction Id/Status");

//				hb.getChildren().addAll(bankTxt,cheqTxt);
//				hb.setMaxWidth(200);
//				hb.setSpacing(20);
//				hb.setVisible(false);
//				gp.add(vb, 5, 11);
				JFXButton submitBtn = new JFXButton(" Submit ");
				gp.add(submitBtn, 2,13);
		//

				quantityTxt.textProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						// TODO Auto-generated method stub
						errorTip.hide();

						if(!validateProductControls()){
							addMoreBtn.setDisable(true);
							editBtn.setDisable(true);
//							gp.add(quantityErrLbl, 1, 7);
							return;
						}

						for(Product p:productList) {
							if(prodTxt.getValue()!=null) {
							if(prodTxt.getValue().contains(p.getProduct_name())) {
								if(p.getQuantity()<Integer.parseInt(newValue)) {
//									System.out.println(p.getQuantity());
									errorTip.setText("Quantity must not exceed current stock");
									quantityTxt.setTooltip(errorTip);
									errorTip.show(quantityTxt,400,150);
									addMoreBtn.setDisable(true);
									editBtn.setDisable(true);
									return;
								}
							}
							}
						}
//						totErrLbl.setText("");
						addMoreBtn.setDisable(false);
						editBtn.setDisable(false);
						if(!newValue.isEmpty()){
							if(!(rateTxt.getText().equals("")||discTxt.getText().equals(""))){
							double total = (Double.parseDouble(newValue)*Double.parseDouble(rateTxt.getText()))-Double.parseDouble(discTxt.getText());


							double subTotal = total*(100/(100+gstRate));
							gstAmt=total-subTotal;
							double halfGst=gstAmt/2;

							if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {

								cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
								gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
							}
							else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
								iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
								double subTotal1=subTotal*(100/(100+cessRate));
								cessAmt=subTotal-subTotal1;
//								subTotalsub=subTotal+subTotal1;
								cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
//								subTotal=subTotal+cess;
							}
							taxableAmt=subTotal;
							totalTxt.setText(String.format("%.2f",total));}
							else{
								totalTxt.setText("NaN");
							}
					}
						else{
							quantityTxt.setText(quantityTxt.getPromptText());
						}
						}

				});

				rateTxt.textProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						// TODO Auto-generated method stub
		//
						errorTip.hide();
						if(!validateProductControls()){
							addMoreBtn.setDisable(true);
							editBtn.setDisable(true);
							return;
						}

						if(!quantityTxt.getText().isEmpty()) {
						for(Product p:productList) {
							if(prodTxt.getValue()!=null) {
							if(prodTxt.getValue().contains(p.getProduct_name())) {

								if(p.getQuantity()<Integer.parseInt(quantityTxt.getText())) {
//									System.out.println(p.getQuantity());
									errorTip.setText("Quantity must not exceed current stock");
									quantityTxt.setTooltip(errorTip);
									errorTip.show(quantityTxt,400,150);
									addMoreBtn.setDisable(true);
									editBtn.setDisable(true);
									return;
								}
							}
						}
						}
						}

						addMoreBtn.setDisable(false);
						editBtn.setDisable(false);
						if(!newValue.equals("")){
//							if(newValue.matches(arg0))
							if(!(quantityTxt.getText().equals("")||discTxt.getText().equals(""))){
						double total = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(newValue))-Double.parseDouble(discTxt.getText());

						double subTotal = total*(100/(100+gstRate));
						gstAmt=total-subTotal;

						double halfGst=gstAmt/2;
						if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
							cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
							gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
						}
						else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
							iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
							double subTotal1=subTotal*(100/(100+cessRate));
							cessAmt=subTotal-subTotal1;
//							subTotal=subTotal+subTotal1;
							cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
//							subTotal=subTotal+cess;
						}
						taxableAmt=subTotal;
						totalTxt.setText(String.format("%.2f",total));}
							else{
								totalTxt.setText("NaN");
							}
					}else
						rateTxt.setText(rateTxt.getPromptText());
						}
				});

				discTxt.textProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//						 TODO Auto-generated method stub
						errorTip.hide();
						if(!validateProductControls()){
//							System.out.println("Reched inside validation");
							addMoreBtn.setDisable(true);
							editBtn.setDisable(true);
							return;
						}
						if(!quantityTxt.getText().isEmpty()) {
						for(Product p:productList) {
							if(prodTxt.getValue()!=null) {
							if(p.getProduct_name().equals(prodTxt.getValue())) {
								if(prodTxt.getValue().contains(p.getProduct_name())) {
									if(p.getQuantity()<Integer.parseInt(quantityTxt.getText())) {
//									System.out.println(p.getQuantity());
									errorTip.setText("Quantity must not exceed current stock");
									quantityTxt.setTooltip(errorTip);
									errorTip.show(quantityTxt,400,150);
									addMoreBtn.setDisable(true);
									editBtn.setDisable(true);

									return;
									}
								}
							}
							}
						}
						}
//						totErrLbl.setText("");
//						System.out.println("Oustdie validation");
						addMoreBtn.setDisable(false);
						editBtn.setDisable(false);
						if(!newValue.equals("")){
							if(!(quantityTxt.getText().equals("") || rateTxt.getText().equals(""))){
							double total = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(rateTxt.getText()))-Double.parseDouble(newValue);


							double subTotal = total*(100/(100+gstRate));
							gstAmt=total-subTotal;
							double halfGst=gstAmt/2;

							if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
								cGstTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
								gstRsTxt.setText(String.format("%.2f",halfGst)+"("+gstRate/2+"%)");
							}
							else if(prodHB.getChildren().contains(iGstTxt)&&prodHB.getChildren().contains(cessTxt)) {
								iGstTxt.setText(String.format("%.2f",gstAmt)+"("+gstRate+"%)");
								double subTotal1=subTotal*(100/(100+cessRate));
								cessAmt=subTotal-subTotal1;
//								subTotal=subTotal+subTotal1;
								cessTxt.setText(String.format("%.2f", cessAmt)+"("+cessRate+"%)");
//								subTotal=subTotal+cess;
							}
							taxableAmt=subTotal;
							totalTxt.setText(String.format("%.2f",total));}
							else{
								totalTxt.setText("NaN");
							}
					}else{
								discTxt.setText(discTxt.getPromptText());	}
						}

				});
				submitBtn.setOnAction(new EventHandler<ActionEvent>() {
					boolean result;
					@Override
					public void handle(ActionEvent arg0) {
						if(!validateonSaleSubmitButton()){
							return;
						}
						SalesEntry salesEntry = new SalesEntry();
//						salesEntry.setEntryId(entry_id);
						salesEntry.setInvoice_no(invoiceId);
						Customer customer=new Customer();
						salesEntry.setCustomer(customer);

						if(custCombo.getValue()!=null) {
						for(Customer c:custList){
							if(custCombo.getValue().equals(c.getFull_name())&& prmConTxt.getText().equals(c.getContact())) {
								salesEntry.getCustomer().setCust_id(c.getCust_id());
							}
							}
						}
						try {
							LocalDate localDate = invoicDatePick.getValue();
							SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
							Date utilDate = new Date(sd.parse(localDate.toString()).getTime());
							salesEntry.setEntry_date(utilDate);
//							System.out.println(utilDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
//						salesEntry.setCustName(fullNameTxt.getText());
						salesEntry.getCustomer().setFull_name(custCombo.getValue());
						salesEntry.getCustomer().setAddress(address.getText());
						salesEntry.getCustomer().setState(stateCombo.getValue());
						salesEntry.getCustomer().setGstin(gstTxt.getText());
						salesEntry.getCustomer().setContact(prmConTxt.getText());

//						salesEntry.setPaid_amount(Double.parseDouble(partPayTxt.getText()));
						salesEntry.setSub_total(Double.parseDouble(grTotalTxt.getText()));
						salesEntry.setTotal(Double.parseDouble(grTotalTxt.getText()));
//						salesEntry.setPayment_type(payCombo.getValue());

		                //work from here fetch value for payment modes
						PaymentMode paymentMode= new PaymentMode();
						salesEntry.setPaymentMode(paymentMode);
//						if(salesEntry.getPayment_type().equals("Full Payment")) {
							if(cashCheck.isSelected()) {
								salesEntry.getPaymentMode().setCashMode(cashCheck.getText());
								if(!cashAmtTxt.getText().isEmpty()) {
								salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(cashAmtTxt.getText()));
								}
								else {
								salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(grTotalTxt.getText()));
								}
							}
							if(bankCheck.isSelected()) {
								salesEntry.getPaymentMode().setBankMode(bankCheck.getText());
								salesEntry.getPaymentMode().setBankName(bankTxt.getText());
								salesEntry.getPaymentMode().setChequeNo(cheqTxt.getText());

								if(!bankAmtTxt.getText().isEmpty()) {
								salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(bankAmtTxt.getText()));
								}
								else {
									salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(grTotalTxt.getText()));
								}
							}
							if(cardCheck.isSelected()) {
								salesEntry.getPaymentMode().setCardMode(cardCheck.getText());
								salesEntry.getPaymentMode().setTransId(transId.getText());
								if(!cardAmtTxt.getText().isEmpty()) {
									salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(cardAmtTxt.getText()));
								}
								else {
									salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(grTotalTxt.getText()));
								}

							}
							if(voucherCheck.isSelected()) {
								salesEntry.getPaymentMode().setVoucherMode(voucherCheck.getText());
								salesEntry.getPaymentMode().setVoucherWalletCode(voucherTypeTxt.getText());
								if(!vouchAmtTxt.getText().isEmpty()) {
									salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(vouchAmtTxt.getText()));
								}
								else {
									salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(grTotalTxt.getText()));
								}

							}
//						}else if(salesEntry.getPayment_type().equals("Part Payment")) {
							if(cashCheck.isSelected()) {
								salesEntry.getPaymentMode().setCashMode(cashCheck.getText());
								if(!cashAmtTxt.getText().isEmpty()) {
								salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(cashAmtTxt.getText()));
								}
//								else {
////								salesEntry.getPaymentMode().setCashAmount(Double.parseDouble(partPayTxt.getText()));
//								}
							}
							if(bankCheck.isSelected()) {
								salesEntry.getPaymentMode().setBankMode(bankCheck.getText());
								salesEntry.getPaymentMode().setBankName(bankTxt.getText());
								salesEntry.getPaymentMode().setChequeNo(cheqTxt.getText());

								if(!bankAmtTxt.getText().isEmpty()) {
								salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(bankAmtTxt.getText()));
								}
//								else {
//									salesEntry.getPaymentMode().setBankAmount(Double.parseDouble(partPayTxt.getText()));
//								}
							}
							if(cardCheck.isSelected()) {
								salesEntry.getPaymentMode().setCardMode(cardCheck.getText());
								salesEntry.getPaymentMode().setTransId(transId.getText());
								if(!cardAmtTxt.getText().isEmpty()) {
									salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(cardAmtTxt.getText()));
								}
//								else {
//									salesEntry.getPaymentMode().setCardAmount(Double.parseDouble(partPayTxt.getText()));
//								}

							}
							if(voucherCheck.isSelected()) {
								salesEntry.getPaymentMode().setVoucherMode(voucherCheck.getText());
								salesEntry.getPaymentMode().setVoucherWalletCode(voucherTypeTxt.getText());
								if(!vouchAmtTxt.getText().isEmpty()) {
									salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(vouchAmtTxt.getText()));
								}
//								else {
//									salesEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(partPayTxt.getText()));
//								}

							}
//						}
							salesEntry.setDeliveryMemo(deliveryMemo);
						result=salesDAO.createNewInvoice(salesEntry, prodData);
						if(result) {
							Alert alert = new Alert(AlertType.INFORMATION, "Sales Invoice Generated");
							alert.setTitle("Success Message");
							alert.setHeaderText("HI");
							alert.showAndWait();
							Main.dialog.close();
							try {
								generateInvoice.showReport(salesEntry);
							} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
//							clearInvoiceControls();
							showDeliveryMemo(anchorPane);
							Main.deliveryView.setItems(delSortedData);
//							Main.deliveryView.setItems(saleSortedData);
							Main.deliveryView.requestFocus();
							Main.deliveryView.getSelectionModel().selectLast();
							Main.deliveryView.getFocusModel().focusNext();
//							showInvoice(anchorPane);
//							Main.salesView.setItems(saleSortedData);
//							Main.salesView.requestFocus();
//							Main.salesView.getSelectionModel().selectLast();
//							Main.salesView.getFocusModel().focusNext();

							Main.deliveryView.setMinSize(900, 500);
							anchorPane.getChildren().set(0,Main.deliveryView);


							}
							else {
								Alert alert = new Alert(AlertType.INFORMATION, "Error occurred while generating sales invoice!Please check database connection");
								alert.setTitle("Error Message");
								alert.setHeaderText("HI");
								alert.showAndWait();
							}

						clearProductData();
//						clearLabels();

					}
				});

				gp.addEventFilter(KeyEvent.KEY_PRESSED, e->{
					if(e.getCode()==KeyCode.INSERT) {
						if(prodHB.getChildren().contains(addMoreBtn)) {
							addMoreBtn.fire();
							e.consume();
						}else if(prodHB.getChildren().contains(editBtn)) {
							editBtn.fire();
							e.consume();
						}

					}else if(e.getCode()==KeyCode.ENTER) {
						submitBtn.fire();
						e.consume();
					}
				});

				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						stateCombo.requestFocus();
					}
				});

				return gp;
		 }
		 
		 
		 public GridPane viewDeliveryMemo(DeliveryMemo deliveryMemo,AnchorPane anchorPane) {
			 
			 	GridPane gp=new GridPane();
//				errorTip.setAutoHide(true);
				gp.getStyleClass().add("grid");
				gp.setHgap(10);
				gp.setVgap(10);
				gp.setAlignment(Pos.CENTER);
				gp.setPadding(new Insets(10, 10, 10, 10));
//				gp.setGridLinesVisible(true);

				Label titleLabel = new Label(" View Delivery Memo");
				GridPane.setMargin(titleLabel, new Insets(0,-300,0,300));
				gp.add(titleLabel, 0, 0);

				Label custCreatLbl=new Label("Customer Details");
				GridPane.setMargin(custCreatLbl, new Insets(0,0,0,-70));
				gp.add(custCreatLbl, 1, 1);

//				long entry_id=accountDAO.getAccountEntryId()+1;
				
				long dmId=Long.parseLong(deliveryMemo.getDm_no());
//				System.out.println(dmId);
				int length=deliveryMemo.getDm_no().length();
				Label deliveryMemoLbl =new Label();
				deliveryMemoLbl.setText(String.format("DM"+"%0"+(6-length)+"d", (dmId)));
				GridPane.setMargin(deliveryMemoLbl, new Insets(0,0,0,-200));
				gp.add(deliveryMemoLbl, 3, 0);
				
				invoicDatePick= new JFXDatePicker();
				LocalDate ld=LocalDate.parse(deliveryMemo.getEntry_date().toString());
				invoicDatePick.setValue(ld);
				invoicDatePick.setPromptText("Entry Date");
				invoicDatePick.setMaxWidth(150);
				invoicDatePick.setEditable(true);
				invoicDatePick.setShowWeekNumbers(false);
				invoicDatePick.setDisable(true);
				invoicDatePick.setStyle("-fx-opacity:1");
				invoicDatePick.getStyleClass().add("date-pick");

//				KeyEventHandler.dateKeyEvent(invoicDatePick);
				GridPane.setMargin(invoicDatePick, new Insets(0,-70,0,0));
//				GridPane.setMargin(invoicDatePick, new Insets(0,0,0,-200));
				gp.add(invoicDatePick, 0, 1);

				HBox custComboHB= new HBox();
				custCombo= new JFXComboBox<>();
				custCombo.setValue(deliveryMemo.getCustomer().getFull_name());
				custCombo.setDisable(true);
				custCombo.setStyle("-fx-opacity:1");

//				custCombo.setStyle("-fx-font-size:15");
				custCombo.getStyleClass().add("jf-combo-box");
				custCombo.setStyle("-fx-font-size:12");
				custCombo.setMaxWidth(110);
				custCombo.setLabelFloat(true);
				custCombo.setPromptText("Customer Name");
//				custCombo.setEditable(true);

				ObservableList<String> custObsData = FXCollections.observableArrayList();
				List<Customer> custList= salesDAO.getCustomerNames();
				Iterator<Customer> itr= custList.iterator();
				while(itr.hasNext()) {
					Customer customer=itr.next();
					if(!customer.getFull_name().equals("null")) {
					custObsData.add(customer.getFull_name());
					}
				}
				custCombo.setItems(custObsData);

//				new AutoCompleteComboBoxListener<>(custCombo);


				address= new JFXTextField();
//				address.clear();
				address.setPromptText("Address");
				address.setEditable(false);
				address.setText(deliveryMemo.getCustomer().getAddress());
				address.setStyle("-fx-font-size:12");
				address.setLabelFloat(true);
				address.setMaxWidth(80);
				
				prmConTxt= new JFXTextField();
//				prmConTxt.clear();
				prmConTxt.setEditable(false);
				prmConTxt.setText(deliveryMemo.getCustomer().getContact());
				prmConTxt.setPromptText("Contact Number");
				prmConTxt.setStyle("-fx-font-size:12");
				prmConTxt.setLabelFloat(true);
				prmConTxt.setMaxWidth(80);
				
				stateCombo= new JFXComboBox<>();
				stateCombo.setDisable(true);
				stateCombo.setStyle("-fx-opacity:1");
				stateCombo.setLabelFloat(true);
				stateCombo.setPromptText("State");
				stateCombo.setStyle("-fx-font-size:12");

//				stateCombo.setMaxWidth(10);
				stateCombo.getItems().addAll(fillStateCombo());
				stateCombo.setValue(deliveryMemo.getCustomer().getState());
//				stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//					@Override
//					public void handle(KeyEvent event) {
//						// TODO Auto-generated method stub
////						errorTip.hide();
//						String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
//						if(s!=null) {
//							stateCombo.requestFocus();
//							stateCombo.getSelectionModel().select(s);
//
//						}
//					}
//				});
//				
				gstTxt= new JFXTextField();
//				gstTxt.clear();
				gstTxt.setEditable(false);
				gstTxt.setText(deliveryMemo.getCustomer().getGstin());
				gstTxt.setPromptText("GSTIN");
				gstTxt.setLabelFloat(true);
				gstTxt.setMaxWidth(100);
				gstTxt.setStyle("-fx-font-size:12");


				custComboHB.getChildren().addAll(custCombo,address,stateCombo,prmConTxt,gstTxt);
				custComboHB.setSpacing(20);
				GridPane.setMargin(custComboHB, new Insets(0,0,0,-300));
				gp.add(custComboHB, 2, 2);



//				Label grTotalLbl = new Label("Grand Total");
//				gp.add(grTotalLbl, 4, 9);

				ObservableList<DeliveryProduct> prodData = FXCollections.observableArrayList();
				
				List<DeliveryProduct> deliveryList=salesDAO.getDeliveryProduct(deliveryMemo);
				for(DeliveryProduct dp:deliveryList) {
					String rate=BigDecimal.valueOf(dp.getRate())
							.setScale(2,RoundingMode.HALF_UP)
							.toPlainString();
					String total=BigDecimal.valueOf(dp.getTotal())
							.setScale(2, RoundingMode.HALF_UP)
							.toPlainString();
					prodData.add(new DeliveryProduct(dp.getId(),dp.getProduct().getProduct_name(),dp.getProduct().getUnit(),dp.getRate(),dp.getQuantity(),dp.getTotal(),rate,total));
					
				}
				
				TableView<DeliveryProduct> prodView = new TableView<DeliveryProduct>();
				

				prodView.setMaxSize(700, 200);
				TableColumn<DeliveryProduct, String> prodName = new TableColumn<DeliveryProduct, String>("Product Name");
				prodName.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
				prodName.setMinWidth(150);
				TableColumn<DeliveryProduct, String> unitCol = new TableColumn<DeliveryProduct, String>("Unit");
				unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
				unitCol.setPrefWidth(150);
				TableColumn<DeliveryProduct, Integer> quantityCol = new TableColumn<DeliveryProduct, Integer>("Quantity");
				quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				quantityCol.setPrefWidth(100);
				TableColumn<DeliveryProduct, String> rateCol = new TableColumn<DeliveryProduct, String>("Rate");
				rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
				rateCol.setPrefWidth(100);
				
//				

				TableColumn<DeliveryProduct, String> totalCol = new TableColumn<DeliveryProduct, String>("Total");
				totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
				totalCol.setPrefWidth(100);
//				TableColumn<DeliveryProduct, DeliveryProduct> actionCol = new TableColumn<DeliveryProduct, DeliveryProduct>("Action");
//				actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
//				actionCol.setCellFactory(e->new TableCell<DeliveryProduct,DeliveryProduct>(){
//
//					@Override
//				    protected void updateItem(DeliveryProduct deliveryProduct,boolean empty){
//						if(deliveryProduct==null){
//							setGraphic(null);
//							return;
//						}else{
//							deleteBtn=new JFXButton("Delete");
//							setGraphic(deleteBtn);
////							deleteBtn.setDisable(false);
//							deleteBtn.getStyleClass().add("del-btn");
//							deleteBtn.setAlignment(Pos.CENTER);
//							deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//								@Override
//								public void handle(ActionEvent arg0) {
//									// TODO Auto-generated method stub
//									Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
//									alert.setTitle("Confirmation");
////									alert.setHeaderText("HI");
//									Optional<ButtonType> result= alert.showAndWait();
//									if(result.get()==ButtonType.OK){
//										getTableView().getItems().remove(deliveryProduct);
//										getTableView().refresh();
//										grandTotal=0;
//										if(prodData.size()==0)
//											grTotalTxt.setText("0.0");
//										else{
//										for(DeliveryProduct p:prodData){
//											grandTotal=grandTotal+p.getTotal();
//											grTotalTxt.setText(String.format("%.2f",grandTotal));
//
//										}
//										}
//									}
//
//								}
//							});
//
//
//						}
//					}
//				});
				prodView.getColumns().addAll(prodName,unitCol,quantityCol,rateCol,totalCol);
				
				GridPane.setMargin(prodView, new Insets(-20,-100,0,30));

				prodView.setItems(prodData);
				prodView.requestFocus();
				prodView.getSelectionModel().selectFirst();
				prodView.getFocusModel().focus(0);


//				Label payTypeLbl =  new Label("Payment Type");
//				gp.add(payTypeLbl, 4, 10);

				HBox paymentBox= new HBox();

				grTotalTxt= new JFXTextField();
				grTotalTxt.setLabelFloat(true);
				grTotalTxt.setEditable(false);
				grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
				grTotalTxt.setFocusColor(Color.TRANSPARENT);
				grTotalTxt.setPromptText("Grand Total");
				grTotalTxt.setStyle("-fx-font-size: 20px; -fx-font-weight:bold;");
				grTotalTxt.setText(String.valueOf(deliveryMemo.getTotal()));
//				grTotalTxt.setMaxWidth(100);
				GridPane.setMargin(grTotalTxt, new Insets(20,-50,0,50));
				gp.add(prodView, 0, 8,5,1);
				gp.add(grTotalTxt, 0, 10);
				
				advanceAmt= new JFXTextField();
				advanceAmt.setEditable(false);
				advanceAmt.setText(String.valueOf(deliveryMemo.getAdvanceAmt()));
				advanceAmt.setLabelFloat(true);
				advanceAmt.setPromptText("Advance Amount");
				advanceAmt.setMinWidth(100);
//				advanceAmt.setText("0.00");
				gp.add(advanceAmt, 1, 10);
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						prodView.requestFocus();
					}
				});
		
				return gp;
				
			 
		 }
		 
		 public TableView<SalesMan> showSalesMan(AnchorPane anchorPane){
			 TableView<SalesMan> salesManView= new TableView<SalesMan>();
			 salesManListData.clear();
			 TableColumn<SalesMan, Long> srNoCol= new TableColumn<>("Sr.No.");
			 srNoCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));
			 srNoCol.setPrefWidth(100);
			 srNoCol.setStyle("-fx-alignment:center");
			 TableColumn<SalesMan, String> salesManCol= new TableColumn<SalesMan, String>("Sales Man");
			 salesManCol.setCellValueFactory(new PropertyValueFactory<>("salesManName"));
			 salesManCol.setPrefWidth(250);
			 salesManCol.setStyle("-fx-alignment:center");
			 TableColumn<SalesMan, String> contactCol= new TableColumn<SalesMan, String>("Contact No");
			 contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
			 contactCol.setPrefWidth(150);
			 contactCol.setStyle("-fx-alignment:center");
			 TableColumn<SalesMan, String> addressCol= new TableColumn<SalesMan, String>("Address");
			 addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
			 addressCol.setPrefWidth(200);
			 addressCol.setStyle("-fx-alignment:center");
			 TableColumn<SalesMan, SalesMan> actionCol= new TableColumn<SalesMan, SalesMan>("Action");
			 actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
			 actionCol.setStyle("-fx-alignment:center");
			 actionCol.setCellFactory(e->new TableCell<SalesMan,SalesMan>() {
				 @Override
				    protected void updateItem(SalesMan salesMan,boolean empty){
						if(salesMan==null){
							setGraphic(null);
							return;
						}else{
							deleteBtn= new JFXButton("Delete");
							setGraphic(deleteBtn);
////						deleteBtn.setDisable(false);
						deleteBtn.getStyleClass().add("del-btn");
						deleteBtn.setAlignment(Pos.CENTER);
						deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
							
							@Override
							public void handle(ActionEvent event) {
								// TODO Auto-generated method stub
								Alert alert= new Alert(AlertType.CONFIRMATION,"Do you really want to delete?");
								alert.setTitle("Confirmation");
								alert.setHeaderText("HI");
								Optional<ButtonType> result= alert.showAndWait();
								if(result.get()==ButtonType.OK){
									getTableView().getSortOrder().remove(salesMan);
									boolean result1= salesDAO.deleteSalesMan(salesMan);
									showSalesMan(anchorPane);
									Main.salesManView.setItems(salesManSortedData);
									Main.salesManView.requestFocus();
									Main.salesManView.getSelectionModel().selectLast();
									Main.salesManView.getFocusModel().focusNext();

									Main.salesManView.setMinSize(900, 500);
									anchorPane.getChildren().set(0,Main.salesManView);
									getTableView().refresh();
								}
							}
						});
						}
				 }
			 });
			 List<SalesMan> salesManList= salesDAO.getSalesManList();
			 long srNo=1;
			 for(SalesMan s:salesManList) {
				 if(!s.getSalesManName().equals("null")) {
				 String salesManName=s.getSalesManName().replaceAll("null", "");
				 String contactNO=s.getContact().replaceAll("null", "");
				 String address=s.getAddress().replaceAll("null", "");
				 salesManListData.add(new SalesMan(s.getId(),salesManName,contactNO,address,srNo));
				 srNo++;
				 }
			 }
			 
			 salesManView.getColumns().addAll(srNoCol,salesManCol,contactCol,addressCol,actionCol);
			 
			 
			 salesManView.getColumns().addListener(new ListChangeListener() {
					public boolean suspended;

					@Override
					public void onChanged(Change c) {
						// TODO Auto-generated method stub
						c.next();
						if (c.wasReplaced() && !suspended) {
							this.suspended = true;
							salesManView.getColumns().setAll(srNoCol,salesManCol,contactCol,addressCol,actionCol);
							this.suspended = false;
						}
					}

				});
			 
			 salesManFilteredData= new FilteredList<>(salesManListData,p->true);
			 
			 salesManSortedData= new SortedList<>(salesManFilteredData);
			 salesManSortedData.comparatorProperty().bind(salesManView.comparatorProperty());
			 salesManView.setItems(salesManSortedData);
			 
			 return salesManView;
		 }
		 
		 public GridPane createNewSalesMan(AnchorPane anchorPane) {
			 	GridPane gp= new GridPane();

//				gp.setMinSize(800, 600);
				gp.getStyleClass().add("grid");
				gp.setAlignment(Pos.CENTER);
				gp.setHgap(20);
				gp.setVgap(30);
//				gp.setGridLinesVisible(true);

				Label titleLabel = new Label(" Add New SalesMan");
				GridPane.setMargin(titleLabel, new Insets(0,0,0,30));
				gp.add(titleLabel, 0, 0);
				
				salesManName= new JFXTextField();
				salesManName.setLabelFloat(true);
				salesManName.setPromptText("SalesMan Name");
				validateOnFocus(salesManName);
				
				gp.add(salesManName, 0, 2);
				
				salesManContactNo= new JFXTextField();
				salesManContactNo.setLabelFloat(true);
				salesManContactNo.setPromptText("Contact No");
				validateOnFocus(salesManContactNo);
				gp.add(salesManContactNo, 0, 4);
				
				salesManAddress= new JFXTextField();
				salesManAddress.setLabelFloat(true);
				salesManAddress.setPromptText("Address");
				validateOnFocus(salesManAddress);
				gp.add(salesManAddress, 0, 6);
				
				JFXButton submitBtn= new JFXButton("Submit");
				
				submitBtn.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(!validateSalesMan()) {
							return;
						}
						
						SalesMan salesMan= new SalesMan();
						salesMan.setSalesManName(salesManName.getText());
						salesMan.setContact(salesManContactNo.getText());
						salesMan.setAddress(salesManAddress.getText());
						
						boolean result=salesDAO.createSalesMan(salesMan);
						if(result) {
							Alert alert= new Alert(AlertType.INFORMATION,"SalesMan has been added Successfully");
							alert.setTitle("Success");
							alert.showAndWait();
							Main.dialog.close();
							showSalesMan(anchorPane);
							Main.salesManView.setItems(salesManSortedData);
							Main.salesManView.requestFocus();
							Main.salesManView.getSelectionModel().selectLast();
							Main.salesManView.getFocusModel().focusNext();

							Main.salesManView.setMinSize(900, 500);
							anchorPane.getChildren().set(0,Main.salesManView);
							}
							else {
								Alert alert = new Alert(AlertType.ERROR, "Error occurred while adding salesMan!Please check database connection");
								alert.setTitle("Error Message");
								alert.showAndWait();
							}
						
					}
				});
				
				gp.add(submitBtn, 0, 8);
				
				gp.addEventFilter(KeyEvent.KEY_PRESSED, e->{
					if(e.getCode()==KeyCode.ENTER) {
						submitBtn.fire();
						e.consume();
					}
				});
				
				
				return gp;
				
		 }
		 
		 public GridPane updateSalesMan(AnchorPane anchorPane,int index,SalesMan salesMan) {
			 	GridPane gp= new GridPane();

//				gp.setMinSize(800, 600);
				gp.getStyleClass().add("grid");
				gp.setAlignment(Pos.CENTER);
				gp.setHgap(20);
				gp.setVgap(30);
//				gp.setGridLinesVisible(true);

				Label titleLabel = new Label(" Update SalesMan");
				GridPane.setMargin(titleLabel, new Insets(0,0,0,30));
				gp.add(titleLabel, 0, 0);
				
				salesManName= new JFXTextField();
				salesManName.setLabelFloat(true);
				salesManName.setPromptText("SalesMan Name");
				salesManName.setText(salesMan.getSalesManName());
				validateOnFocus(salesManName);
				
				gp.add(salesManName, 0, 2);
				
				salesManContactNo= new JFXTextField();
				salesManContactNo.setLabelFloat(true);
				salesManContactNo.setPromptText("Contact No");
				validateOnFocus(salesManContactNo);
				salesManContactNo.setText(salesMan.getContact());
				gp.add(salesManContactNo, 0, 4);
				
				salesManAddress= new JFXTextField();
				salesManAddress.setLabelFloat(true);
				salesManAddress.setPromptText("Address");
				validateOnFocus(salesManAddress);
				salesManAddress.setText(salesMan.getAddress());
				gp.add(salesManAddress, 0, 6);
				
				JFXButton updateBtn= new JFXButton("Submit");
				
				updateBtn.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(!validateSalesMan()) {
							return;
						}
						
//						SalesMan salesMan= new SalesMan();
						salesMan.setSalesManName(salesManName.getText());
						salesMan.setContact(salesManContactNo.getText());
						salesMan.setAddress(salesManAddress.getText());
						
						boolean result=salesDAO.updateSalesMan(salesMan);
						if(result) {
							Alert alert= new Alert(AlertType.INFORMATION,"SalesMan has been updated Successfully");
							alert.setTitle("Success");
							alert.showAndWait();
							Main.dialog.close();
							showSalesMan(anchorPane);
							Main.salesManView.setItems(salesManSortedData);
							Main.salesManView.requestFocus();
							Main.salesManView.getSelectionModel().select(salesMan);
							Main.salesManView.getFocusModel().focus(index);

							Main.salesManView.setMinSize(900, 500);
							anchorPane.getChildren().set(0,Main.salesManView);
							}
							else {
								Alert alert = new Alert(AlertType.ERROR, "Error occurred while updating salesMan!Please check database connection");
								alert.setTitle("Error Message");
								alert.showAndWait();
							}
						
					}
				});
				
				gp.add(updateBtn, 0, 8);
				
				gp.addEventFilter(KeyEvent.KEY_PRESSED, e->{
					if(e.getCode()==KeyCode.ENTER) {
						updateBtn.fire();
						e.consume();
					}
				});
				
				return gp;
				
		 }
		 
		 public boolean validateSalesMan() {
			 if(salesManName.getText()==null||salesManName.getText().isEmpty()) {
				 errorTip.setText("SalesMan Name must not be empty!");
				 salesManName.setTooltip(errorTip);
				 errorTip.show(salesManName,400,150);
				 salesManName.requestFocus();
				 return false;
			 }
			 if(salesManContactNo.getText()==null||salesManContactNo.getText().isEmpty()) {
				 errorTip.setText("Contact Number must not be empty!");
				 salesManContactNo.setTooltip(errorTip);
				 errorTip.show(salesManContactNo,400,250);
				 salesManContactNo.requestFocus();
				 return false;
			 }
			 
			 if(salesManAddress.getText()==null||salesManAddress.getText().isEmpty()) {
				 errorTip.setText("Address must not be empty!");
				 salesManAddress.setTooltip(errorTip);
				 errorTip.show(salesManAddress,400,350);
				 salesManAddress.requestFocus();
				 return false;
			 }
			 
			 return true;
		 }
		 
		 public  TableView<SalesManReportBean> SalesManReportTable( String From, String To){
			 salesManReportDataList.clear();
			  TableView<SalesManReportBean> SalesReportDate = new TableView<SalesManReportBean>();
			  List<SalesManReportBean> saleList=salesDAO.SalesManReportData(From,To);

			  TableColumn<SalesManReportBean, Long> srNoCol = new TableColumn<SalesManReportBean, Long>("Sr.No");
			  srNoCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));
			  srNoCol.setPrefWidth(80);
			  srNoCol.setStyle("-fx-alignment:center");
			  
			  TableColumn<SalesManReportBean, Date> dateCol = new TableColumn<SalesManReportBean, Date>("Invoice Date");
			  dateCol.setCellValueFactory(new PropertyValueFactory<>("invoice_date"));
			  dateCol.setPrefWidth(120);
			  dateCol.setStyle("-fx-alignment:center");

			  TableColumn<SalesManReportBean, String> salesManCol = new TableColumn<SalesManReportBean, String>("SalesMan Name");
			  salesManCol.setCellValueFactory(new PropertyValueFactory<>("salesManName"));
			  salesManCol.setPrefWidth(200);
			  salesManCol.setStyle("-fx-alignment:center");
			  
			  TableColumn<SalesManReportBean, Long> invoiceCol= new TableColumn<SalesManReportBean, Long>("Bill No");
			  invoiceCol.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
			  invoiceCol.setPrefWidth(100);
			  invoiceCol.setStyle("-fx-alignment:center");
			  
			  TableColumn<SalesManReportBean, Double> totalCol= new TableColumn<SalesManReportBean, Double>("Amount");
			  totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
			  totalCol.setPrefWidth(150);
			  totalCol.setStyle("-fx-alignment:center");
			  
			  

			  Iterator<SalesManReportBean> itr = saleList.iterator();
			  long srNo = 1;
			  while (itr.hasNext()) {
			   SalesManReportBean s1 = itr.next();
			   double total=BigDecimal.valueOf(s1.getTotal())
					   .setScale(2,RoundingMode.HALF_UP).doubleValue();
			   
			   String fullname=s1.getSalesManName().replaceAll("null", "OWNER");
			   salesManReportDataList.add(new SalesManReportBean(s1.getInvoiceNo(), srNo,s1.getInvoice_date(),
			     fullname,total));
			   srNo++;

			  }


			  SalesReportDate.getColumns().addAll(srNoCol,dateCol,salesManCol,invoiceCol,totalCol);


			//  SalesReportDate.getColumns().addListener(new ListChangeListener() {
			//   public boolean suspended;
			//
			//   @Override
			//   public void onChanged(Change c) {
//			    // TODO Auto-generated method stub
//			    c.next();
//			    if (c.wasReplaced() && !suspended) {
//			     this.suspended = true;
//			     SalesReportDate.getColumns().setAll(titleCol,debitCol,creditCol);
//			     this.suspended = false;
//			    }
			//   }
			//
			//  });
			//  Long srNo1=null;
			  SalesReportDate.setItems(salesManReportDataList);


			  return SalesReportDate;
		 }
}
