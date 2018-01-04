package controller;

import java.util.Date;
import java.util.HashSet;

import javafx.scene.input.MouseEvent;

import java.awt.Checkbox;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.PasswordAuthentication;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import dao.GoodsDAO;
//import dao.AccountDAO;
//import dao.GoodsDAO;
import dao.PurchaseDAO;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
//import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
//import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
//import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.*;
import com.jfoenix.validation.RequiredFieldValidator;

import application.ComboBoxAutoComplete;
import application.KeyEventHandler;
import application.Main;
import application.ResourceLoader;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.Light.Distant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Category;
import model.Item;
import model.PaymentMode;
import model.Product;
import model.PurchaseEntry;
import model.PurchaseProduct;
import model.PurchaseReturn;
//import model.RawMaterial;
import model.SalesProduct;
import model.SubCategory;
//import model.Customer;
import model.Supplier;
import model.Unit;
import net.sf.jasperreports.engine.JRException;
//import org.controlsfx.
/***
 * 
 * @author Saurabh Gupta
 *
 */

public class PurchaseController {
	/***
	 * Start of data Declaration
	 *************/
	boolean result1 = false;
	double grandTotal;
	double gstRate = 0;
	double gstAmt=0;
	double cessAmt=0;
	double taxableAmt=0;

//	Set<String> unitHashSet= new HashSet<>();
	// Creating object for printing invoice
	// GenerateInvoice generateInvoice= new GenerateInvoice();
	// Label supplierLbl = new Label("Supplier Name *");
	JFXTextField supplierTxt = new JFXTextField();
	public static List<Item> itemList=new ArrayList<>(); 

	ObservableList<Supplier> supplierData = FXCollections.observableArrayList();
	ObservableList<PurchaseEntry> purchaseData = FXCollections.observableArrayList();
	ObservableList<PurchaseReturn> returnData= FXCollections.observableArrayList();

	// Declaration for search functionality
	// public JFXTextField searchBtn;
	public SortedList<Supplier> sortedData;
	public SortedList<PurchaseEntry> purchSortedData;
	public SortedList<PurchaseReturn> returnSortedData;
	
	public FilteredList<Supplier> filteredData;
	public FilteredList<PurchaseEntry> purchFilteredData;
	public FilteredList<PurchaseReturn> returnFilteredData;
	
	
	JFXTextField ownerTxt = new JFXTextField();
	JFXTextField contactTxt = new JFXTextField();
	JFXTextField gstTxt = new JFXTextField();
	ToggleGroup tg;
	JFXComboBox<String> stateCombo = new JFXComboBox<>();
	JFXTextArea addressTxt = new JFXTextArea();
	Label gstStateLbl = new Label("GST State *");
	JFXRadioButton mahaBtn = new JFXRadioButton("Maharashtra");
	JFXRadioButton outBtn = new JFXRadioButton("Out of Maharashtra");

	ObservableList<String> prodList = FXCollections.observableArrayList();
	List<Product> productList = new ArrayList<Product>();
	
	FilteredList<String> filteredList = null;
	

	JFXTextField purchInvoicTxt = new JFXTextField();
	JFXComboBox<String> supplierCombo = new JFXComboBox<String>();
	JFXDatePicker purchDatePick = new JFXDatePicker();
	JFXComboBox<String> materialCombo = new JFXComboBox<String>();
	JFXComboBox<String> prodTxt = new JFXComboBox<String>();
	JFXComboBox<String> groupTxt= new JFXComboBox<>();
	JFXComboBox<String> subGroupTxt= new JFXComboBox<>();
//	JFXTextField prodTxt= new JFXTextField();
//	AutoCompleteTextField<String> prodTxt= new AutoCompleteTextField<String>();
	JFXTextField hsnTxt = new JFXTextField();
	JFXComboBox<String> unitCombo = new JFXComboBox<>();
	JFXTextField quantityTxt = new JFXTextField();
	JFXTextField sellPriceTxt= new JFXTextField();
	JFXTextField rateTxt = new JFXTextField();
	JFXTextField discTxt = new JFXTextField();
	JFXTextField taxableTxt = new JFXTextField();
	JFXComboBox<String> gstRsTxt= new JFXComboBox<>();
//	JFXTextField gstRsTxt = new JFXTextField();
//	String cgst,sgst,igst,cess;
	JFXTextField cGstTxt = new JFXTextField();
	JFXTextField iGstTxt = new JFXTextField();
	JFXTextField cessTxt = new JFXTextField();
	Label payModeLbl= new Label("Payment Modes :-");
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
	JFXButton deleteBtn;

	JFXTextField totalTxt = new JFXTextField();

	JFXTextField grTotalTxt = new JFXTextField();
	JFXComboBox<String> payCombo = new JFXComboBox<String>();
	JFXTextField partPayTxt = new JFXTextField();
	
//DAO and controllers declaration
	GoodsDAO goodsDAO= new GoodsDAO();
	PurchaseDAO purchaseDAO = new PurchaseDAO();
	GoodsController goodsController= new GoodsController();

	Tooltip errorTip = new Tooltip();
	
	public static List<Long> productIdList= new ArrayList<>();
	BarCodePrinter barcodePrinter= new BarCodePrinter();

	/******************************
	 * addSupplier()
	 * 
	 * @return void
	 ****************************/
	public GridPane addSupplier(AnchorPane anchorPane) {
		
		GridPane gp = new GridPane();
		gp.getStyleClass().add("grid");
		gp.setHgap(30);
		gp.setVgap(30);
		gp.setGridLinesVisible(false);
		
		Label titleLabel = new Label(" Add New Supplier");
		GridPane.setMargin(titleLabel, new Insets(0, -100, 0, 100));
		gp.add(titleLabel, 0, 0);

		// gp.add(supplierLbl, 0, 0);
		supplierTxt.setPromptText("Supplier Name *");
		supplierTxt.setLabelFloat(true);

		// supplierTxt.setEditable(false);
		supplierTxt.clear();
		gp.add(supplierTxt, 0, 1);

		
		ownerTxt.setPromptText("Owner Name *");
		ownerTxt.setLabelFloat(true);
		// ownValidator= new RequiredFieldValidator();
		// ownerTxt.getValidators().add(ownValidator);
		// ownValidator.setMessage("Please input owner name");
		ownerTxt.clear();
		gp.add(ownerTxt, 1, 1);

		// gp.add(contactLbl, 0, 2);
		contactTxt= new JFXTextField();
		contactTxt.setPromptText("Contact Number *");
		// contValidator= new RequiredFieldValidator();
		// numberValidator=new NumberValidator();
		// contactTxt.getValidators().add(contValidator);
		// contactTxt.getValidators().add(numberV/alidator);
		// numberValidator.setMessage("Only numbers are supported");
		// contValidator.setMessage("Please input contact number");
		contactTxt.clear();
		contactTxt.setLabelFloat(true);
		gp.add(contactTxt, 0, 3);

		// gp.add(gstLbl, 1, 2);
		gstTxt=new JFXTextField();
		gstTxt.setPromptText("GSTIN");
		gstTxt.clear();
		gstTxt.setLabelFloat(true);
		gp.add(gstTxt, 1, 3);

		// gp.add(addressLbl, 0, 4);
		addressTxt=new JFXTextArea();
//		addressTxt.clear();
		addressTxt.setPromptText("Address");
		// addrValidator= new RequiredFieldValidator();
		// addressTxt.getValidators().add(addrValidator);
		// addrValidator.setMessage("Please enter address");
		addressTxt.setLabelFloat(true);
		// addressTxt.setMaxSize(300, 100);
		addressTxt.setMaxHeight(50);
		gp.add(addressTxt, 0, 5, 2, 1);

//		gstStateLbl.setStyle("-fx-font-size:14");
//		gp.add(gstStateLbl, 0, 7);
		stateCombo= new JFXComboBox<>();
		stateCombo.setLabelFloat(true);
		stateCombo.setPromptText("State");
		stateCombo.getItems().addAll(fillStateCombo());
		stateCombo.setValue("Maharashtra");
		stateCombo.setMinWidth(200);
//		stateCombo.setStyle("-fx-font-size:12");
		stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
				if(s!=null) {
					stateCombo.requestFocus();
					stateCombo.getSelectionModel().select(s);
					
				}
			}
		});
		
		gp.add(stateCombo, 0, 7,2,1);
		stateCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}
		});
//		mahaBtn.setSelected(true);
//
//		HBox hb = new HBox();
//		hb.setSpacing(5);
//		// hb.setMinSize());
//		hb.setMaxSize(30, 20);
//		hb.getChildren().addAll(mahaBtn, outBtn);
//		GridPane.setMargin(hb, new Insets(0, 50, 0, -100));
//		gp.add(hb, 1, 7);
//		tg=new ToggleGroup();
//		mahaBtn.setToggleGroup(tg);
//		outBtn.setToggleGroup(tg);

		// Label emailLbl = new Label("Email");
		// gp.add(emailLbl, 0, 6);
		// JFXTextField emailTxt = new JFXTextField();
		// gp.add(emailTxt, 0, 7);

		JFXButton createBtn = new JFXButton(" Create ");
		createBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				// gp.getChildren().removeAll(suppErrLbl,ownErrLbl,contErrLbl,addErrLbl);
				// clearValidators();
				submitSupplierFunc(anchorPane);

			}
		});
		JFXButton clrBtn = new JFXButton(" Clear ");
		clrBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				supplierTxt.clear();
				ownerTxt.clear();
				contactTxt.clear();
				addressTxt.clear();
				gstTxt.clear();
				stateCombo.setValue("Maharashtra");
			}
		});

		validateOnFocus(supplierTxt);

		validateOnFocus(ownerTxt);
		validateOnFocus(contactTxt);
		validateOnFocus(gstTxt);
		addressTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();

			}
		});

		HBox hb1 = new HBox();
		hb1.setSpacing(30);
		// hb1.setMinSize(100, 30);
		createBtn.setMinWidth(100);
		// createBtn.setStyle("-fx-font-size: 10pt;");
		clrBtn.setMinWidth(100);
		// createBtn.setStyle("-fx-font-size: 10pt;");
		hb1.getChildren().addAll(createBtn, clrBtn);
//		hb.setAlignment(Pos.CENTER);
		hb1.setAlignment(Pos.CENTER);
		gp.add(hb1, 0, 8, 2, 1);
		// stackPane.getChildren().add(gp);

		// gp.add(clrBtn,1,/9);

		// Stage secondary = new Stage();
		// secondary.setScene(scene);
		//// secondary.setAlwaysOnTop(true);
		// secondary.setMaxHeight(600);
		// secondary.setMaxWidth(800);
		// secondary.setTitle("Add New Supplier");
		//
		// secondary.setMinHeight(600);
		// secondary.setMinWidth(800);
		// secondary.show();

		gp.setAlignment(Pos.CENTER);

		return gp;
	}

	/************
	 * showSupplier()
	 * 
	 * @return TableView<Supplier>
	 **************************************/


	public TableView<Supplier> showSupplier(AnchorPane anchorPane) {
		TableView<Supplier> supplierView = new TableView<Supplier>();
		// supplierView.setEditable(false);
		supplierData.clear();
		TableColumn<Supplier, String> srNOCol = new TableColumn<>("Sr.No.");
		srNOCol.setPrefWidth(80); 
		srNOCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));
		TableColumn<Supplier, String> supplierNameCol = new TableColumn<Supplier, String>("Supplier Name");
		supplierNameCol.setPrefWidth(200);
		supplierNameCol.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
//		TableColumn<Supplier, String> ownerNameCol = new TableColumn<Supplier, String>("Owner Name");
//		ownerNameCol.setPrefWidth(100);
//		ownerNameCol.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
		TableColumn<Supplier, String> contactCol = new TableColumn<Supplier, String>("Contact No.");
		contactCol.setPrefWidth(150);
		contactCol.setCellValueFactory(new PropertyValueFactory<>("contactNO"));
		TableColumn<Supplier, String> gstCol = new TableColumn<Supplier, String>("GSTIN");
		gstCol.setPrefWidth(150);
		gstCol.setCellValueFactory(new PropertyValueFactory<>("vatTinNo"));
		TableColumn<Supplier, String> addressCol = new TableColumn<Supplier, String>("Address");
		addressCol.setPrefWidth(180);
		addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
		TableColumn<Supplier, String> gstStateCol = new TableColumn<Supplier, String>("GST State");
		gstStateCol.setPrefWidth(120);
		gstStateCol.setCellValueFactory(new PropertyValueFactory<>("gstState"));
//		TableColumn<Supplier, Supplier> actionCol = new TableColumn<Supplier, Supplier>("Action");
////		actionCol.setMinWidth(80);
//		actionCol.setCellValueFactory(e -> new ReadOnlyObjectWrapper<>(e.getValue()));
//		actionCol.setCellFactory(e -> new TableCell<Supplier, Supplier>() {
//			// private HBox hb = new HBox();
//			JFXButton deleteBtn = new JFXButton("Delete");
//
//			@Override
//			protected void updateItem(Supplier supplier, boolean empty) {
//
//				deleteBtn.getStyleClass().add("del-btn");
//
//				// super.updateItem(supplier, empty);
//
//				if (supplier == null || empty) {
//					setGraphic(null);
//					return;
//				} else {
//					// hb.setMaxWidth(100);
//					// hb.setAlignment(Pos.CENTER);
//					// hb.setSpac/ing(5);
//
//					// hb.getChildren().add(deleteBtn);
//					setGraphic(deleteBtn);
//
//					deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//						@Override
//						public void handle(ActionEvent event) {
//							// TODO Auto-generated method stub
//							// supplierData.remove(supplier);
//							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
//							alert.setTitle("Confirmation");
//							// alert.setHeaderText("HI");
//							Optional<ButtonType> result = alert.showAndWait();
//							if (result.get() == ButtonType.OK) {
//								// getTableView().getSortOrder().remove(supplier);
//								result1 = purchaseDAO.deleteSupplier(supplier);
//								showSupplier(anchorPane);
//								Main.supplierView.setItems(sortedData);
//								Main.supplierView.requestFocus();
//								Main.supplierView.getSelectionModel().selectLast();
//								Main.supplierView.getFocusModel().focusNext();
//
//								Main.supplierView.setMinSize(600, 500);
//								anchorPane.getChildren().set(0, Main.supplierView);
//
//								// hb.getChildren().clear();
//
//							}
//
//						}
//					});
//
//				}
//
//			}
//
//		});

		supplierView.getColumns().addAll(srNOCol, supplierNameCol, contactCol, gstCol, addressCol,
				gstStateCol);
		List<Supplier> supplierList = purchaseDAO.showSupplier();
		Iterator<Supplier> itr = supplierList.iterator();
		long srNo = 1;
		while (itr.hasNext()) {
			Supplier supplier = itr.next();
			supplierData.add(new Supplier(supplier.getSupplier_id(), supplier.getSupplierName(),
					supplier.getOwnerName(), supplier.getVatTinNo(), supplier.getAddress(), supplier.getContactNO(),
					supplier.getGstState(), srNo));
			srNo++;
		}

		supplierView.getColumns().addListener(new ListChangeListener() {
			public boolean suspended;

			@Override
			public void onChanged(Change c) {
				// TODO Auto-generated method stub
				c.next();
				if (c.wasReplaced() && !suspended) {
					this.suspended = true;
					supplierView.getColumns().setAll(srNOCol, supplierNameCol, contactCol, gstCol,
							addressCol, gstStateCol);
					this.suspended = false;
				}
			}

		});

		filteredData = new FilteredList<>(supplierData, p -> true);

		sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(supplierView.comparatorProperty());

		supplierView.setItems(sortedData);
		return supplierView;

	}

	/***********************
	 * updateSupplier()
	 * 
	 * @param supplier
	 **********************/
	public GridPane updateSupplier(Supplier supplier,int index, AnchorPane anchorPane) {
		// clearValidators();
		GridPane gp = new GridPane();
		gp.getStyleClass().add("grid");
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(30);
		gp.setVgap(30);
		gp.setGridLinesVisible(false);
//		gp.setPadding(new Insets(0, -50, 0, 50));
		// Scene scene = new Scene(gp, 700, 700);

		// gp.add(supplierLbl, 0, 0);
		Label titleLabel = new Label(" Update Supplier ");
		GridPane.setMargin(titleLabel, new Insets(0, -100, 0, 100));

		gp.add(titleLabel, 0, 0);

		// supplierTxt.setEditable(false);
		supplierTxt.setPromptText("Supplier Name *");
		supplierTxt.setLabelFloat(true);
		supplierTxt.setText(supplier.getSupplierName());
//		supplierTxt.getValidators().add(suppValidator);
//		suppValidator.setMessage("Please input supplier name");
		gp.add(supplierTxt, 0, 1);

		// gp.add(ownerLbl, 1, 0);
		ownerTxt.setPromptText("Owner Name *");
		ownerTxt.setLabelFloat(true);
//		ownerTxt.getValidators().add(ownValidator);
//		ownValidator.setMessage("Please input owner name");

		ownerTxt.setText(supplier.getOwnerName());
		gp.add(ownerTxt, 1, 1);

		// gp.add(contactLbl, 0, 2);
		contactTxt.setPromptText("Contact Number *");
//		contactTxt.getValidators().add(contValidator);
//		contactTxt.getValidators().add(numberValidator);
//		numberValidator.setMessage("Only numbers are supported");
//		contValidator.setMessage("Please input contact number");
		contactTxt.setLabelFloat(true);
		contactTxt.setText(supplier.getContactNO());
		gp.add(contactTxt, 0, 3);

		// gp.add(gstLbl, 1, 2);
		gstTxt.setPromptText("GSTIN");
		gstTxt.setLabelFloat(true);
		gstTxt.setText(supplier.getVatTinNo());
		gp.add(gstTxt, 1, 3);

		// gp.add(addressLbl, 0, 4);
		addressTxt.setPromptText("Address");
//		addressTxt.getValidators().add(addrValidator);
//		addrValidator.setMessage("Please enter address");
		addressTxt.setLabelFloat(true);
		addressTxt.setText(supplier.getAddress());
		addressTxt.setMaxHeight(50);
		gp.add(addressTxt, 0, 5, 2, 1);

		String gst = supplier.getGstState();
		
		
		// mahaBtn.setSelected(true);

//		HBox hb = new HBox();
//		hb.setSpacing(5);
//		// hb.setMinSize(100, 30);
//		hb.setMaxSize(30, 20);
//		hb.getChildren().addAll(mahaBtn, outBtn);
//		GridPane.setMargin(hb, new Insets(0, 50, 0, -100));
//		gp.add(hb, 1, 7);
//		tg = new ToggleGroup();
//		mahaBtn.setToggleGroup(tg);
//		outBtn.setToggleGroup(tg);
//		if ("Maharashtra".equals(gst))
//			mahaBtn.setSelected(true);
//		else
//			outBtn.setSelected(true);
		
		// tg.selectToggle(mahaBtn.setSelected(gst.equals(anObject)););
		
		stateCombo.setLabelFloat(true);
		stateCombo.setPromptText("State");
		stateCombo.getItems().addAll(fillStateCombo());
		stateCombo.setValue(supplier.getGstState());
		stateCombo.setMinWidth(200);
//		stateCombo.setStyle("-fx-font-size:12");
		stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
				if(s!=null) {
					stateCombo.requestFocus();
					stateCombo.getSelectionModel().select(s);
					
				}
			}
		});
		
		stateCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}
		});
		gp.add(stateCombo, 0, 7,2,1);

		HBox hb1 = new HBox();
		hb1.setSpacing(30);
		// hb1.setMinSize(100, 30);

		JFXButton updateBtn = new JFXButton(" Update ");
		updateBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				// gp.getChildren().removeAll(suppErrLbl,ownErrLbl,contErrLbl,addErrLbl);
				// clearValidators();
				
				updateSupplierFunc(supplier,index,anchorPane);

			}
		});
		JFXButton clrBtn = new JFXButton("Clear");
		clrBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				supplierTxt.clear();
				ownerTxt.clear();
				contactTxt.clear();
				addressTxt.clear();
				gstTxt.clear();
				stateCombo.setValue("Maharashtra");
			}
		});

		validateOnFocus(supplierTxt);
		validateOnFocus(ownerTxt);
		validateOnFocus(contactTxt);
		validateOnFocus(gstTxt);

		addressTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();

			}
		});
		updateBtn.setMinWidth(100);
//		updateBtn.setStyle("-fx-font-size: 10pt;");
		clrBtn.setMinWidth(100);
//		updateBtn.setStyle("-fx-font-size: 10pt;");
		hb1.getChildren().addAll(updateBtn, clrBtn);
		
		hb1.setAlignment(Pos.CENTER);
		gp.add(hb1, 0, 8, 2, 1);

		// gp.add(clrBtn,1,/9);

		// Stage secondary = new Stage();
		// secondary.setScene(scene);
		//// secondary.setAlwaysOnTop(true);
		// secondary.setMaxHeight(600);
		// secondary.setMaxWidth(800);
		// secondary.setTitle("Update Supplier Details");
		//
		// secondary.setMinHeight(600);
		// secondary.setMinWidth(800);
		// secondary.show();
		gp.setAlignment(Pos.CENTER);
		return gp;
	}

	/**********
	 * create PurchaseEntry()
	 * 
	 * @return
	 */
	public GridPane createPurchaseEntry(AnchorPane anchorPane) {

//		enableAllControls();
		clearControls();
		clearProductData();
		grandTotal = 0;
		gstAmt=0;
		cessAmt=0;
		taxableAmt=0;
//		errorTip.setText("");
		errorTip.setAutoHide(true);
		
		GridPane gp = new GridPane();
		// gp.setMinSize(1200, 800);
		gp.getStyleClass().add("grid");

		// gp.autosize();
//		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(10, 10, 10, 10));
		// gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setGridLinesVisible(false);
		Label titleLabel = new Label(" Create New Purchase Entry");
		GridPane.setMargin(titleLabel, new Insets(0, -400, 0, 400));

		gp.add(titleLabel, 0, 0);
		// long purchase_count = 0;
		// Scene scene = new Scene(gp,1100,600);

		long purchaseId = purchaseDAO.getPurchaseEntryId() + 1;
		int length = String.valueOf(purchaseId).length();
		// Label generLbl = new Label("General Details");
		// gp.add(generLbl, 0, 1);
		Label purchEntryLbl = new Label();
		purchEntryLbl.setText(String.format("PE" + "%0" + (7 - length) + "d", (purchaseId)));
		GridPane.setMargin(purchEntryLbl, new Insets(0,0,0,-200));
		gp.add(purchEntryLbl, 6, 0);
		// Label purchInvoiceLbl = new Label("Purchase Invoice Number");
		// gp.add(purchInvoiceLbl, 0, 2);
		
		HBox supplierHB= new HBox();
		supplierHB.setMinWidth(500);
		
		
//		Label suppDetaiLbl= new Label("Supplier Details");
//		suppDetaiLbl.setStyle("-fx-font-size:12");
////		GridPane.setMargin(suppDetaiLbl, new Insets(0,-500));
//		gp.add(suppDetaiLbl, 1, 1);
		
		purchDatePick= new JFXDatePicker();
		purchDatePick.setValue(LocalDate.now());
		purchDatePick.setPromptText("Purchase Date");
		purchDatePick.setMinWidth(150);
		KeyEventHandler.dateKeyEvent(purchDatePick);
//		GridPane.setMargin(purchDatePick, new Insets(0, -200, 0, 100));
//		gp.add(purchDatePick, 2, 2);
		purchDatePick.getStyleClass().add("date-pick");
		purchDatePick.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
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
		 purchDatePick.setDayCellFactory(dayCellFactory);
		 
		 
//	 	purchInvoicTxt.clear();
		 purchInvoicTxt= new JFXTextField();
		purchInvoicTxt.setLabelFloat(true);
		purchInvoicTxt.setPromptText("Invoice Number");
		purchInvoicTxt.setMinWidth(150);
		purchInvoicTxt.setStyle("-fx-font-size:12");
		
		supplierCombo= new JFXComboBox<>();
		supplierCombo.setLabelFloat(true);
		supplierCombo.getStyleClass().add("jf-combo-box");
//		supplierCombo.getItems().clear();
		supplierCombo.setPromptText("Supplier Name");
		supplierCombo.setMinWidth(150);
		supplierCombo.setStyle("-fx-font-size:12");
		
		ObservableList<String> supplierData = FXCollections.observableArrayList();
		List<Supplier> supplierList = purchaseDAO.getSupplierNames();
		Iterator<Supplier> itr = supplierList.iterator();
		while (itr.hasNext()) {
			Supplier supplier = itr.next();

			supplierData.add(supplier.getSupplierName());
		}
		supplierCombo.setItems(supplierData);
		new AutoCompleteComboBoxListener<>(supplierCombo);
		
//		supplierTxt.clear();
//		supplierTxt.setLabelFloat(true);
//		supplierTxt.setPromptText("Supplier Name");
//		supplierTxt.setMinWidth(150);
//		supplierTxt.setStyle("-fx-font-size:12");
		
		stateCombo= new JFXComboBox<>();
		stateCombo.setLabelFloat(true);
		stateCombo.setPromptText("State");
		stateCombo.getItems().addAll(fillStateCombo());
		stateCombo.setValue("Maharashtra");
		stateCombo.setMinWidth(150);
		stateCombo.setStyle("-fx-font-size:12");
		stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
				if(s!=null) {
					stateCombo.requestFocus();
					stateCombo.getSelectionModel().select(s);
					
				}
			}
		});
		
	
//		contactTxt.clear();
		contactTxt= new JFXTextField();
		contactTxt.setPromptText("Contact No");
		contactTxt.setLabelFloat(true);
		contactTxt.setMinWidth(150);
		contactTxt.setStyle("-fx-font-size:12");
		
		
//		gstTxt.clear();
		gstTxt= new JFXTextField();
		gstTxt.setPromptText("GSTIN");
		gstTxt.setLabelFloat(true);
		gstTxt.setMinWidth(150);
		gstTxt.setStyle("-fx-font-size:12");
		
		supplierHB.setSpacing(30);
		supplierHB.getChildren().addAll(purchDatePick,purchInvoicTxt,supplierCombo,stateCombo,contactTxt,gstTxt);
		GridPane.setMargin(supplierHB, new Insets(0,-200,0,100));
		gp.add(supplierHB, 0, 2);
		

		HBox prodHB = new HBox();

		Label addProdLbl = new Label("Add Product");
		GridPane.setMargin(addProdLbl, new Insets(0, -200, 0, 100));
		gp.add(addProdLbl, 0, 4);
//		;
		// Label prodLbl = new Label("Product");
		// gp.add(prodLbl, 0, 5);
		
		supplierCombo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(supplierCombo.getValue()!=null) {
					for(Supplier s:supplierList) {
						if(s.getSupplierName().equals(supplierCombo.getValue())) {
							stateCombo.setValue(s.getGstState());
							contactTxt.setText(s.getContactNO());
							gstTxt.setText(s.getVatTinNo());
						}
					}
				}
			}
		});

		stateCombo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(stateCombo.getValue()!=null) {
					
					if(stateCombo.getValue().equals("Maharashtra")) {
						prodHB.getChildren().remove(cessTxt);
					}else {
//						prodHB.getChildren().remove(cessTxt);
//						prodHB.getChildren().add(10,cessTxt);
					}
				}
			}
		});
		
//		prodTxt.getItems().clear();
		prodTxt= new JFXComboBox<>();
		prodTxt.setEditable(true);
//		prodTxt.setTooltip(new Tooltip());
		prodTxt.setLabelFloat(true);
		prodTxt.setPromptText("Select Product");
		prodTxt.getStyleClass().add("jf-combo-box");
		
//		prodTxt.getItems().addAll("Hi","Hello");
		prodTxt.setStyle("-fx-font-size:12");
		prodTxt.setMaxWidth(150);
//		prodTxt.getItems().clear();
		prodList.clear();
		productList = goodsDAO.showProductList();
		for (Product s : productList) {

			prodList.add(s.getProduct_name());
		}
		prodTxt.setItems(prodList);
		new AutoCompleteComboBoxListener<>(prodTxt);
//		new ComboBoxAutoComplete<>(prodTxt);
//		prodTxt.setEditable(false);
		// GridPane.setMargin(prodTxt, new Insets(0, -100, 0, 0));
	
		
		
//		validateOnFocus(prodTxt);
		// gp.add(prodTxt, 0, 6);
		groupTxt= new JFXComboBox<>();
		groupTxt.setLabelFloat(true);
		groupTxt.getItems().clear();
		groupTxt.setPromptText("Product Group");
		groupTxt.setPrefWidth(70);
		groupTxt.getEditor().setPrefWidth(70);
		groupTxt.setStyle("-fx-font-size:12");
		groupTxt.getStyleClass().add("jf-combo-box");
		
		subGroupTxt= new JFXComboBox<>();
		subGroupTxt.setLabelFloat(true);
		subGroupTxt.getItems().clear();
		subGroupTxt.setPromptText("SubGroup");
		subGroupTxt.setPrefWidth(60);
		subGroupTxt.getStyleClass().add("jf-combo-box");
		subGroupTxt.setStyle("-fx-font-size:12");
		
		
		ObservableList<String> categoryList= FXCollections.observableArrayList();
		ObservableList<String> subGroupList= FXCollections.observableArrayList();
		List<Category> categoryData= goodsDAO.showCategory();
		List<SubCategory> subCategoryData= goodsDAO.showSubCategory();
		for(Category c: categoryData) {
			categoryList.add(c.getCategory());
		}
		
		for(SubCategory s:subCategoryData) {
			subGroupList.add(s.getSubCategory());
		}
		groupTxt.setItems(categoryList);
		new AutoCompleteComboBoxListener<>(groupTxt);
		
		subGroupTxt.setItems(subGroupList);
		new AutoCompleteComboBoxListener<>(subGroupTxt);
		
//		hsnTxt.clear();
		hsnTxt= new JFXTextField();
		hsnTxt.setPromptText("HSN/SAC");
		hsnTxt.setLabelFloat(true);
//		hsnTxt.setEditable(false);
		hsnTxt.setStyle("-fx-font-size:12");
		hsnTxt.setMaxWidth(60);
		
		unitCombo= new JFXComboBox<>();
		unitCombo.setLabelFloat(true);
		unitCombo.getItems().clear();
		unitCombo.setPromptText("Select Unit");
		unitCombo.setStyle("-fx-font-size:12");
		unitCombo.getStyleClass().add("jf-combo-box");
		
		ObservableList<String> unitList=FXCollections.observableArrayList();
		
		List<Unit> unitData=goodsDAO.getUnitList();
		for(Unit u:unitData) {
			unitList.add(u.getUnit());
		}
		unitCombo.setItems(unitList);
		
		// GridPane.setMargin(unitCombo, new Insets(0, -100, 0, -20));
		
		unitCombo.setMaxWidth(80);
		
		// unitCombo.getItems().addAll("KG","PCS","LIT","ML","GRAM");
		new AutoCompleteComboBoxListener<>(unitCombo);

		quantityTxt= new JFXTextField();
		quantityTxt.setPromptText("Quantity");
		quantityTxt.setText("0");
		quantityTxt.setStyle("-fx-font-size:12");
		quantityTxt.setLabelFloat(true);
		quantityTxt.setMaxWidth(50);
		
		
		rateTxt= new JFXTextField();
		rateTxt.setLabelFloat(true);
		rateTxt.setStyle("-fx-font-size:12");
		rateTxt.setMaxWidth(80);
		rateTxt.setPromptText("Cost Price");
		rateTxt.setText("0.00");
		
		sellPriceTxt= new JFXTextField();
		sellPriceTxt.setLabelFloat(true);
		sellPriceTxt.setStyle("-fx-font-size:12");
		sellPriceTxt.setMaxWidth(80);
		sellPriceTxt.setPromptText("Selling Price");
		sellPriceTxt.setText("0.00");

		discTxt= new JFXTextField();
		discTxt.setMaxWidth(50);
		discTxt.setStyle("-fx-font-size:12");
		discTxt.setLabelFloat(true);
		discTxt.setPromptText("Discount(Rs.)");
		discTxt.setText("0.0");

//		taxableTxt.setLabelFloat(true);
//		taxableTxt.setPromptText("Taxable" + " Amt");
////		taxableTxt.setText("0");
//		taxableTxt.setMaxWidth(80);
//		taxableTxt.setEditable(false);
//		taxableTxt.setStyle("-fx-font-size:12");
		
//		cGstTxt.clear();
//		cGstTxt.setPromptText("CGST");
//		cGstTxt.setMaxWidth(50);
//		cGstTxt.setEditable(false);
//		cGstTxt.setStyle("-fx-font-size:12");
//		cGstTxt.setLabelFloat(true);
		
		gstRsTxt= new JFXComboBox<>();
		gstRsTxt.setLabelFloat(true);
		gstRsTxt.getItems().clear();
		gstRsTxt.setPromptText("Tax Rate(%)");
		gstRsTxt.setMaxWidth(80);
		gstRsTxt.setEditable(false);
		gstRsTxt.setStyle("-fx-font-size:12");
		
		gstRsTxt.getItems().addAll("0.0","5.0","12.0","18.0","28.0");
//		gstRsTxt.getSelectionModel().select(0);
		gstRsTxt.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
//				errorTip.hide();
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), gstRsTxt.getValue(), gstRsTxt.getItems());
				if(s!=null) {
					gstRsTxt.requestFocus();
					gstRsTxt.getSelectionModel().select(s);
					
				}
			}
		});
//		iGstTxt.clear();
//		iGstTxt.setPromptText("IGST");
//		iGstTxt.setMaxWidth(50);
//		iGstTxt.setEditable(false);
//		iGstTxt.setStyle("-fx-font-size:12");
//		iGstTxt.setLabelFloat(true);
//
		cessTxt= new JFXTextField();
		cessTxt.setLabelFloat(true);
		cessTxt.clear();
		cessTxt.setPromptText("Cess(%)");
		cessTxt.setText("0.00");
		cessTxt.setMaxWidth(50);
//		cessTxt.setEditable(false);
		cessTxt.setStyle("-fx-font-size:12");
		
		totalTxt= new JFXTextField();
		totalTxt.setEditable(false);
		totalTxt.setPromptText("Total");

		totalTxt.setLabelFloat(true);
		totalTxt.setText("0.00");
		totalTxt.setStyle("-fx-font-size: 15px;-fx-font-weight:bold");
		// GridPane.setMargin(, new Insets(0, -50, 0, -50));
		totalTxt.setFocusColor(Color.TRANSPARENT);
		totalTxt.setUnFocusColor(Color.TRANSPARENT);
		totalTxt.setMaxWidth(100);
//		HBox.setMargin(totalTxt, new Insets(0, 0, 20, 20));
		// totalTxt.setPadding(new Insets(0, 100, 0, 0));
		// GridPane.setMargin(totalTxt, new Insets(0, -100, 0, -100));
		// gp.add(totalTxt, 6, 6);


		JFXButton addMoreBtn = new JFXButton(" Add ");
		addMoreBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");
//		HBox.setMargin(addMoreBtn, new Insets(0, -20, 0, 20));
		addMoreBtn.setMaxWidth(80);
		addMoreBtn.setDisable(true);
		// addMoreBtn.setPadding(new Insets(0, 100, 0, 0));
		// GridPane.setMargin(addMoreBtn, new Insets(0, 0, 0, -100));

		// gp.add(addMoreBtn, 7, 6);

		JFXButton editBtn = new JFXButton("Edit");
//		HBox.setMargin(editBtn, new Insets(0, -20, 0, 20));
		editBtn.setMaxWidth(80);

		RadioButton addItem = new RadioButton("Add Item");
		addItem.setUserData("Add Item");
		addItem.setSelected(true);
		RadioButton editItem = new RadioButton("Edit Item");
		editItem.setDisable(true);
		editItem.setUserData("Edit Item");
		
		ToggleGroup tg = new ToggleGroup();
		addItem.setToggleGroup(tg);
		editItem.setToggleGroup(tg);
		VBox vb1 = new VBox();
		vb1.setStyle("-fx-font-size:16px; -fx-font-weight:bold;");
		vb1.getChildren().addAll(addItem, editItem);
//		vb1.setMaxHeight(250);
		// GridPane.setMargin(vb1, new Insets(0, 0, 0, -100));
		// gp.add(vb1, 8,6);
//		HBox.setMargin(vb1, new Insets(0,0,0,20));
		prodHB.getChildren().addAll(prodTxt, groupTxt,subGroupTxt,hsnTxt, unitCombo, quantityTxt, rateTxt, sellPriceTxt,discTxt,gstRsTxt, totalTxt,
				addMoreBtn);
		
		// prodHB.setPadding(new Insets(0, 50, 0, 50));
		prodHB.setSpacing(20);
		GridPane.setMargin(prodHB, new Insets(0, 0, 0, 100));
		gp.add(prodHB, 0, 6, 8, 1);

		// Label grTotalLbl = new Label("Grand Total");
		// gp.add(grTotalLbl, 4, 9);
		

		ObservableList<PurchaseProduct> prodData = FXCollections.observableArrayList();
		TableView<PurchaseProduct> prodView = new TableView<PurchaseProduct>();
		prodView.setMaxSize(1100, 200);
		TableColumn<PurchaseProduct, String> prodName = new TableColumn<PurchaseProduct, String>("Product\n"+" Name");
		prodName.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
		prodName.setMinWidth(100);
		TableColumn<PurchaseProduct, String> groupCol= new TableColumn<>("Product\n"+"Group");
		groupCol.setCellValueFactory(new PropertyValueFactory<>("group"));
//		groupCol.setPrefWidth(60);
		TableColumn<PurchaseProduct, String> subGroupCol= new TableColumn<PurchaseProduct, String>("Sub\n"+"Group");
		subGroupCol.setCellValueFactory(new PropertyValueFactory<>("subGroup"));
//		subGroupCol.setPrefWidth(60);
		TableColumn<PurchaseProduct, Long> hsnCol = new TableColumn<PurchaseProduct, Long>("HSN/\n"+"SAC");
		hsnCol.setCellValueFactory(new PropertyValueFactory<>("hsnNo"));
		hsnCol.setPrefWidth(60);
		TableColumn<PurchaseProduct, String> unitCol = new TableColumn<PurchaseProduct, String>("Unit");
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		unitCol.setPrefWidth(50);
		TableColumn<PurchaseProduct, Integer> quantityCol = new TableColumn<PurchaseProduct, Integer>("Qty.");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		quantityCol.setPrefWidth(50);
		TableColumn<PurchaseProduct, Double> rateCol = new TableColumn<PurchaseProduct, Double>("Cost"+"\nPrice");
		rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
//		rateCol.setPrefWidth(50);
		
		TableColumn<PurchaseProduct, Double> sellPriceCol= new TableColumn<PurchaseProduct, Double>("Sell\n"+"Price");
		sellPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
		sellPriceCol.setPrefWidth(80);
		TableColumn<PurchaseProduct, Double> discCol = new TableColumn<PurchaseProduct, Double>("Disc.\n"+" Rs.");
		discCol.setCellValueFactory(new PropertyValueFactory<>("discString"));
		discCol.setPrefWidth(60);
//		TableColumn<PurchaseProduct, SalesProduct> taxableCol = new TableColumn<PurchaseProduct, SalesProduct>(
//				"Taxable\n"+"Amount");
//		taxableCol.setPrefWidth(100);
//		taxableCol.setCellValueFactory(new PropertyValueFactory<>("subTotalString"));
		TableColumn<PurchaseProduct, PurchaseProduct> cgstCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"CGST");
		cgstCol.setPrefWidth(70);
		cgstCol.setCellValueFactory(new PropertyValueFactory<>("cgst"));
		TableColumn<PurchaseProduct, PurchaseProduct> sgstCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"SGST");
		sgstCol.setCellValueFactory(new PropertyValueFactory<>("sgst"));
		sgstCol.setPrefWidth(70);
		TableColumn<PurchaseProduct, PurchaseProduct> igstCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"IGST");
		igstCol.setPrefWidth(70);
		igstCol.setCellValueFactory(new PropertyValueFactory<>("igst"));
		TableColumn<PurchaseProduct, PurchaseProduct> cessCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"Cess");
		cessCol.setCellValueFactory(new PropertyValueFactory<>("cess"));
		cessCol.setPrefWidth(70);
		TableColumn<PurchaseProduct, Double> totalCol = new TableColumn<PurchaseProduct, Double>("Total");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
		totalCol.setPrefWidth(100);
		TableColumn<PurchaseProduct, PurchaseProduct> actionCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"Action");
		actionCol.setCellValueFactory(e -> new ReadOnlyObjectWrapper<>(e.getValue()));
//		actionCol.setPrefWidth(50);
		actionCol.setCellFactory(e -> new TableCell<PurchaseProduct, PurchaseProduct>() {
			// Button deleteBtn = new Button("Delete");

			@Override
			protected void updateItem(PurchaseProduct purchaseProduct, boolean empty) {
				if (purchaseProduct == null) {
					setGraphic(null);
					return;
				} else {
					deleteBtn = new JFXButton("Delete");
					deleteBtn.getStyleClass().add("del-btn");
					setGraphic(deleteBtn);
					deleteBtn.setAlignment(Pos.CENTER);
					deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							// TODO Auto-generated method stub
							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
							// alert.setHeaderText("HI");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK) {
								getTableView().getItems().remove(purchaseProduct);
								getTableView().refresh();
								grandTotal = 0;
								if (prodData.size() == 0)
									grTotalTxt.setText("0.0");
								else {
									for (PurchaseProduct p : prodData) {
										grandTotal = grandTotal + p.getAmount();
										grTotalTxt.setText(String.format("%.2f", grandTotal));

									}
								}
							}

						}
					});

				}
			}
		});

		prodView.getColumns().addAll(prodName, groupCol,subGroupCol,hsnCol,unitCol, quantityCol, rateCol,sellPriceCol, discCol, cgstCol, sgstCol,
				igstCol, cessCol, totalCol, actionCol);
		

		prodTxt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if (prodTxt.getValue() != null) {
					addMoreBtn.setDisable(false);
					editBtn.setDisable(false);
							for (Product p : productList) {
								if (p.getProduct_name().equals(prodTxt.getValue())) {
									groupTxt.setValue(p.getCategory());
									subGroupTxt.setValue(p.getSubGroup());
									gstRate = p.getGst();
//									cessAmt=p.getCess();
									gstRsTxt.setValue(String.valueOf(p.getGst()));
									hsnTxt.setText(String.valueOf(p.getHsnNo()));
									rateTxt.setText(String.valueOf(p.getBuyPrice()));
									sellPriceTxt.setText(String.valueOf(p.getSellPrice()));
									unitCombo.setValue(p.getUnit());
//									cessTxt.setText(String.valueOf(p.getCess()));
								}

							}
						} 

					// return;
			}
		});

		unitCombo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if (unitCombo.getValue() != null) {
					addMoreBtn.setDisable(false);
					editBtn.setDisable(false);
				}
			}
		});
		
		addMoreBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				// clearLabels();
				// gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
				// System.out.println(validateControls());
				boolean productFlag=false;
				boolean unitFlag=false;
				if (!validateOnAddEdit()) {
					
					return;
				}
				// totErrLbl.setText("");
				// addMoreBtn.setDisable(false);
				PurchaseProduct purchaseProduct = new PurchaseProduct();
				Product product = new Product();
				purchaseProduct.setProduct(product);
				Category category= new Category();
				Unit unitObj= new Unit();
				
				purchaseProduct.setCategory(category);
				SubCategory subCategory= new SubCategory();
				purchaseProduct.setSubCategory(subCategory);
				for(Product p:productList) {
					if(p.getProduct_name().equals(prodTxt.getValue())) {
						product.setId(p.getId());
						purchaseProduct.setCurrentStock(p.getQuantity());
					}
				}
				for(Category c:categoryData) {
					if(c.getCategory().equals(groupTxt.getValue())){
						category.setId(c.getId());
					}
				}
				
				for(SubCategory s:subCategoryData) {
					if(s.getSubCategory().equals(subGroupTxt.getValue())){
						subCategory.setId(s.getId());
					}
				}
				
			
				for(Unit u:unitData) {
					if(u.getUnit().equals(unitCombo.getValue())) {
						unitObj.setId(u.getId());
						unitObj.setUnit(u.getUnit());
					}
				}
				purchaseProduct.setUnitObj(unitObj);
				purchaseProduct.getProduct().setProduct_name(prodTxt.getValue());
				purchaseProduct.getProduct().setHsnNo(Long.parseLong(hsnTxt.getText()));
				purchaseProduct.getProduct().setUnit(unitCombo.getValue());
				purchaseProduct.getProduct().setQuantity(Integer.parseInt(quantityTxt.getText()));
				purchaseProduct.getProduct().setSellPrice(Double.parseDouble(sellPriceTxt.getText()));
				purchaseProduct.getProduct().setBuyPrice(Double.parseDouble(rateTxt.getText()));
				purchaseProduct.getProduct().setGst(Double.parseDouble(gstRsTxt.getValue()));
				purchaseProduct.getProduct().setCategory(groupTxt.getValue());
				purchaseProduct.getProduct().setSubGroup(subGroupTxt.getValue());
				if(!cessTxt.getText().isEmpty()) {
				purchaseProduct.getProduct().setCess(Double.parseDouble(cessTxt.getText()));
				}
				purchaseProduct.setProd_name(prodTxt.getValue());
				purchaseProduct.setUnit(unitCombo.getValue());
				purchaseProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
				purchaseProduct.setHsnNo(Long.parseLong(hsnTxt.getText()));
				purchaseProduct.setGroup(groupTxt.getValue());
				purchaseProduct.setSubGroup(subGroupTxt.getValue());
				
				category.setCategory(groupTxt.getValue());
				category.setSubGroup(subGroupTxt.getValue());
				
				String trunRateAmt=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
					    .setScale(2, RoundingMode.HALF_UP)
					    .toPlainString(); 
				purchaseProduct.setRateString(trunRateAmt);
				purchaseProduct.setRate(Double.parseDouble(rateTxt.getText()));
				
				String trunSaleRate=BigDecimal.valueOf(Double.parseDouble(sellPriceTxt.getText()))
						.setScale(2,RoundingMode.HALF_UP)
						.toPlainString();
				purchaseProduct.setSellPrice(Double.parseDouble(sellPriceTxt.getText()));
				purchaseProduct.setSellPriceString(trunSaleRate);
				
				String trunDiscAmt=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
					    .setScale(2, RoundingMode.HALF_UP)
					    .toPlainString();
				purchaseProduct.setDiscString(trunDiscAmt);
				purchaseProduct.setDiscount(Double.parseDouble(discTxt.getText()));
				
				// purchaseProduct.setGst(Double.parseDouble(gstRsTxt.getText()));
				if(stateCombo.getValue().equals("Maharashtra")) {
				double halfGst=gstRate/2;
				
				String gst=BigDecimal.valueOf(gstAmt/2)
						.setScale(2,RoundingMode.HALF_UP)
						.toPlainString();
				
				String cgstTxt=(gst)+"\n("+halfGst+"%)";
				purchaseProduct.setCgst(cgstTxt);
				
				String sgstTxt=(gst)+"\n("+halfGst+"%)";
				purchaseProduct.setSgst(sgstTxt);
				}else {
					String gst=BigDecimal.valueOf(gstAmt)
							.setScale(2,RoundingMode.HALF_UP)
							.toPlainString();
					
					String igstTxt=(gst)+"\n("+gstRate+"%)";
					purchaseProduct.setIgst(igstTxt);
					
					String cesssTxt=(cessAmt)+"\n("+cessTxt.getText()+"%)";
					purchaseProduct.setCess(cesssTxt);
					purchaseProduct.setCessAmt(Double.parseDouble(cessTxt.getText()));
				}
				
				
				String trunTaxablAmt=BigDecimal.valueOf(taxableAmt)
					    .setScale(2, RoundingMode.HALF_UP)
					    .toPlainString();
				purchaseProduct.setSubTotalString(trunTaxablAmt);
				double taxAmt=BigDecimal.valueOf(taxableAmt)
						.setScale(2,RoundingMode.HALF_UP)
						.doubleValue();
				purchaseProduct.setSubTotal(taxAmt);
				
				String trunTotalAmt=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
					    .setScale(2, RoundingMode.HALF_UP)
					    .toPlainString();
				purchaseProduct.setTotalString(trunTotalAmt);
				purchaseProduct.setAmount(Double.parseDouble(totalTxt.getText()));
				// System.out.println(prodData.contains(purchaseProduct));
				for (PurchaseProduct p : prodData) {
					if (p.equals(purchaseProduct)) {
						errorTip.setText("Duplicate entries are not permitted");
						prodTxt.setTooltip(errorTip);
						errorTip.show(prodTxt, 100, 200);
						return;
						// return;
					}
					if(p.getGroup().equals(purchaseProduct.getGroup())) {
						category.setId(1);
					}
					if(p.getSubGroup().equals(purchaseProduct.getSubGroup())) {
						subCategory.setId(1);
					}
					if(p.getUnit().equals(purchaseProduct.getUnit())) {
						unitObj.setId(1);
					}
					
				}
				
				

				// dupliErrLbl.setText("");
				prodData.add(purchaseProduct);

				prodView.setItems(prodData);
				prodView.requestFocus();
				prodView.getSelectionModel().selectLast();
				cashCheck.requestFocus();
				grandTotal = grandTotal + purchaseProduct.getAmount();
				grTotalTxt.setText(String.format("%.2f", grandTotal));

				clearProductData();
				editItem.setDisable(false);
			}
		});
		// prodView.setMaxHeight(200);
		// prodView.setMaxWidth(700);
		// prodView.setPadding(new Insets(0, 0, 0, 100));
		vb1.setMinWidth(100);
		GridPane.setMargin(vb1, new Insets(0,-50,0,50));
		gp.add(vb1, 6, 8);
		GridPane.setMargin(prodView, new Insets(0, -200, 0, 100));
		gp.add(prodView, 0, 8, 11, 1);

		
//		
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				if(tg.getSelectedToggle()!=null) {
					 String item=tg.getSelectedToggle().getUserData().toString();
					if(item.equals("Add Item")) {
						prodHB.getChildren().removeAll(editBtn,addMoreBtn);
						prodHB.getChildren().add(10,addMoreBtn);
						deleteBtn.setDisable(false);
						clearProductData();
					}else if(item.equals("Edit Item")) {
						deleteBtn.setDisable(true);
						PurchaseProduct purchaseProduct = prodView.getSelectionModel().getSelectedItem();
						int index = prodView.getSelectionModel().getSelectedIndex();
						prodTxt.setValue(purchaseProduct.getProd_name());
						unitCombo.setValue(purchaseProduct.getUnit());
						hsnTxt.setText(String.valueOf(purchaseProduct.getHsnNo()));
						groupTxt.setValue(purchaseProduct.getGroup());
						subGroupTxt.setValue(purchaseProduct.getSubGroup());
						
						quantityTxt.setText(String.valueOf(purchaseProduct.getQuantity()));
						
						String trunRateAmt=BigDecimal.valueOf(purchaseProduct.getRate())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
						
						rateTxt.setText(trunRateAmt);
						
						String trunSellRate=BigDecimal.valueOf(purchaseProduct.getSellPrice())
								.setScale(2, RoundingMode.HALF_UP)
								.toPlainString();
						sellPriceTxt.setText(trunSellRate);
						
						String trunDiscAmt=BigDecimal.valueOf(purchaseProduct.getDiscount())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
						discTxt.setText(trunDiscAmt);
						gstRsTxt.setValue(String.valueOf(purchaseProduct.getGst()));
//						cGstTxt.setText(String.valueOf(purchaseProduct.getCgst()));
//						iGstTxt.setText(String.valueOf(purchaseProduct.getIgst()));
						cessTxt.setText(String.valueOf(purchaseProduct.getCess()));
//						String trunTaxableAmt=BigDecimal.valueOf(purchaseProduct.getSubTotal())
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						taxableTxt.setText(trunTaxableAmt);
						
						totalTxt.setText(String.valueOf(purchaseProduct.getAmount()));
						String trunTotalAmt=BigDecimal.valueOf(purchaseProduct.getAmount())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
						totalTxt.setText(trunTotalAmt);
						grandTotal = grandTotal - purchaseProduct.getAmount();
						prodHB.getChildren().remove(addMoreBtn);
						prodHB.getChildren().add(10, editBtn);
						// gp.add(editBtn, 7, 6);

						editBtn.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								if (!validateOnAddEdit()) {
									return;
								}
								// PurchaseProduct purchaseProduct1 = new PurchaseProduct();
								
								for(Product p:productList) {
									if(p.getProduct_name().equals(prodTxt.getValue())) {
										purchaseProduct.getProduct().setId(p.getId());
										purchaseProduct.getCategory().setProductId(p.getId());
									}
								}
								
								for(Category c:categoryData) {
									if(c.getCategory().equals(groupTxt.getValue())) {
										purchaseProduct.getCategory().setId(c.getId());
									}
								}
								purchaseProduct.getProduct().setProduct_name(prodTxt.getValue());
								purchaseProduct.getProduct().setHsnNo(Long.parseLong(hsnTxt.getText()));
								purchaseProduct.getProduct().setCategory(groupTxt.getValue());
								purchaseProduct.getProduct().setSubGroup(subGroupTxt.getValue());
								purchaseProduct.getProduct().setUnit(unitCombo.getValue());
								purchaseProduct.getProduct().setQuantity(Integer.parseInt(quantityTxt.getText()));
								purchaseProduct.getProduct().setSellPrice(Double.parseDouble(rateTxt.getText()));
								purchaseProduct.getProduct().setGst(Double.parseDouble(gstRsTxt.getValue()));
								
								purchaseProduct.setGroup(groupTxt.getValue());
								purchaseProduct.setSubGroup(subGroupTxt.getValue());
								
								purchaseProduct.getCategory().setSubGroup(subGroupTxt.getValue());
								purchaseProduct.getCategory().setCategory(groupTxt.getValue());
								
								purchaseProduct.setProd_name(prodTxt.getValue());
								purchaseProduct.setUnit(unitCombo.getValue());
								purchaseProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
								purchaseProduct.setRate(Double.parseDouble(rateTxt.getText()));
								String trunRateAmt1=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
										.setScale(2, RoundingMode.HALF_UP)
										.toPlainString();
								purchaseProduct.setRateString(trunRateAmt1);
								
								String trunDiscAmt1=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
										.setScale(2, RoundingMode.HALF_UP)
										.toPlainString();
								purchaseProduct.setDiscString(trunDiscAmt1);
								purchaseProduct.setDiscount(Double.parseDouble(discTxt.getText()));
								// purchaseProduct.setGst(Double.parseDouble(gstRsTxt.getText()));
								if(stateCombo.getValue().equals("Maharashtra")) {
								double halfGst=gstRate/2;
								
								
								String cgstTxt=(gstAmt/2)+"\n("+halfGst+"%)";
								purchaseProduct.setCgst(cgstTxt);
								
								String sgstTxt=(gstAmt/2)+"\n("+halfGst+"%)";
								purchaseProduct.setSgst(sgstTxt);
								}else {
									String igstTxt=(gstAmt)+"\n("+gstRate+"%)";
									purchaseProduct.setIgst(igstTxt);
									
									String cesssTxt=(cessAmt)+"\n("+cessTxt.getText()+"%)";
									purchaseProduct.setCess(cesssTxt);
									purchaseProduct.setCessAmt(Double.parseDouble(cessTxt.getText()));
								}
								
							String trunTaxableAmt1=BigDecimal.valueOf(taxableAmt)
									.setScale(2, RoundingMode.HALF_UP)
									.toPlainString();
							
							purchaseProduct.setSubTotalString(trunTaxableAmt1);
							
							double taxAmt=BigDecimal.valueOf(taxableAmt)
									.setScale(2, RoundingMode.HALF_UP)
									.doubleValue();
							
							purchaseProduct.setSubTotal(taxAmt);
								
							String trunTotalAmt1=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
										.setScale(2, RoundingMode.HALF_UP)
										.toPlainString();
							purchaseProduct.setTotalString(trunTotalAmt1);
							purchaseProduct.setAmount(Double.parseDouble(totalTxt.getText()));

								// for(PurchaseProduct p:prodData){
								// if(p.getProd_name().equals(purchaseProduct.getProd_name())){
								// dupliErrLbl.setText("Duplicate entries are not permitted");
								// gp.add(dupliErrLbl, 0, 7);
								// return;
								// }
								// }
								// prodData.remove(purchaseProduct);
								prodData.set(index, purchaseProduct);
								prodView.requestFocus();
								prodView.getSelectionModel().select(purchaseProduct);
								cashCheck.requestFocus();
								
								grandTotal = 0;
								for (PurchaseProduct p : prodData) {
									grandTotal = grandTotal + p.getAmount();
								}
								grTotalTxt.setText(String.format("%.2f", grandTotal));
								editBtn.setDisable(true);
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
		// Label payTypeLbl = new Label("Payment Type");
		// gp.add(payTypeLbl, 4, 1);
		HBox paymentBox= new HBox();
		
		grTotalTxt= new JFXTextField();
		grTotalTxt.setLabelFloat(true);
		grTotalTxt.setPromptText("Grand Total");
		grTotalTxt.setStyle("-fx-font-size: 20px;-fx-font-weight:bold");
		grTotalTxt.setEditable(false);
		grTotalTxt.setFocusColor(Color.TRANSPARENT);
		grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
		grTotalTxt.setText("00.00");
		GridPane.setMargin(grTotalTxt, new Insets(20,-200,0,100));
		gp.add(grTotalTxt, 0, 10);
		
		payModeLbl.setStyle("-fx-font-size:14");
		GridPane.setMargin(payModeLbl, new Insets(-70,100,0,-200));
		gp.add(payModeLbl, 2, 10);
		
		cashCheck= new JFXCheckBox();
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
		paymentBox.setMaxSize(160, 50);
		paymentBox.setStyle("-fx-border-style: solid;-fx-padding:15;-fx-border-width: 2;-fx-border-insets:5; -fx-border-radius: 5;");
		paymentBox.getChildren().addAll(cashBox,bankBox,cardBox,vouchBox);
		
		
		
		GridPane.setMargin(paymentBox, new Insets(-50,100,0,-200));
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
		// rateTxt.setonk

		quantityTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub	
				errorTip.hide();

				if (!validateProductControls()) {
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					// gp.add(quantityErrLbl, 1, 7);
					return;
				}

				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);

				if (!newValue.isEmpty()) {
					if (!(rateTxt.getText().equals("") || discTxt.getText().equals("")||gstRsTxt.getValue()==null)) {
						double total = (Double.parseDouble(newValue) * Double.parseDouble(rateTxt.getText()))
								- Double.parseDouble(discTxt.getText());
						
//						String trunTaxablAmt=BigDecimal.valueOf(subTotal)
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						taxableTxt.setText(trunTaxablAmt);
//						
//						if(gstRate==0) {
							gstRate=Double.parseDouble(gstRsTxt.getValue());
//						}
						double subTotal = total * (100/(100+gstRate));
						gstAmt=total-subTotal;
						if(prodHB.getChildren().contains(cessTxt)) {
//							double subTotal1=total*(100/(100+Double.parseDouble(cessTxt.getText())));
//							cessAmt=total-subTotal1;
//							subTotal=subTotal+subTotal1;
//							subTotal=subTotal+cess;
//							gst=gst+cess;
							cessAmt=0;
						}
						taxableAmt=subTotal;
//						double total = subTotal;
//						double subTotal=gst;
						totalTxt.setText(String.format("%.2f", total));
					} else {
						totalTxt.setText("NaN");
					}
				} else {
					quantityTxt.setText(quantityTxt.getPromptText());
				}
			}

		});

		rateTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				errorTip.hide();
				if (!validateProductControls()) {
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					return;
				}
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if (!newValue.equals("")) {
					// if(newValue.matches(arg0))
					if (!(quantityTxt.getText().equals("") || discTxt.getText().equals("")||gstRsTxt.getValue()==null)) {
						double total = (Double.parseDouble(quantityTxt.getText()) * Double.parseDouble(newValue))
								- Double.parseDouble(discTxt.getText());
//						String trunTaxablAmt=BigDecimal.valueOf(subTotal)
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						
//						taxableTxt.setText(trunTaxablAmt);
//						if(gstRate==0) {
							gstRate=Double.parseDouble(gstRsTxt.getValue());
//						}
						double subTotal = total * (100/(100+gstRate));
						gstAmt=total-subTotal;
						if(prodHB.getChildren().contains(cessTxt)) {
//							double subTotal1=total*(100/(100+Double.parseDouble(cessTxt.getText())));
//							cessAmt=total-subTotal1;
//							subTotal=subTotal+subTotal1;
//							subTotal=subTotal+cess;
//							gst=gst+cess;
							cessAmt=0;
						}
						taxableAmt=subTotal;
//						double total = subTotal;
//						double subTotal=gst;
						totalTxt.setText(String.format("%.2f", total));
					} else {
						totalTxt.setText("NaN");
					}
				} else
					rateTxt.setText(rateTxt.getPromptText());
			}
		});

		discTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				errorTip.hide();
				if (!validateProductControls()) {
					// System.out.println("Reched inside validation");
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					// gp.add(discErrLbl, 3, 7);
					return;
				}
				
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if (!newValue.equals("")) {
					if (!(quantityTxt.getText().equals("") || rateTxt.getText().equals("")||gstRsTxt.getValue()==null)) {
						double total = (Double.parseDouble(quantityTxt.getText())
								* Double.parseDouble(rateTxt.getText())) - Double.parseDouble(newValue);
//						String trunTaxablAmt=BigDecimal.valueOf(subTotal)
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						taxableTxt.setText(trunTaxablAmt);
//						if(gstRate==0) {
							gstRate=Double.parseDouble(gstRsTxt.getValue());
//						}
						double subTotal = total * (100/(100+gstRate));
						gstAmt=total-subTotal;
						if(prodHB.getChildren().contains(cessTxt)) {
							double subTotal1=total*(100/(100+Double.parseDouble(cessTxt.getText())));
//							cessAmt=total-subTotal1;
//							subTotal=subTotal+subTotal1;
//							subTotal=subTotal+cess;
//							gst=gst+cess;
							cessAmt=0;
						}
						taxableAmt=subTotal;
//						double total = subTotal;
//						double subTotal=gst;
						totalTxt.setText(String.format("%.2f", total));
					} else {
						totalTxt.setText("NaN");
					}
				} else {
					discTxt.setText(discTxt.getPromptText());
				}
			}

		});
		
		gstRsTxt.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(gstRsTxt.getValue()!=null) {
				if(!(quantityTxt.getText().isEmpty()||rateTxt.getText().isEmpty()||discTxt.getText().isEmpty())) {
					double total = (Double.parseDouble(quantityTxt.getText())
							* Double.parseDouble(rateTxt.getText())) - Double.parseDouble(discTxt.getText());
					
					gstRate=Double.parseDouble(gstRsTxt.getValue());
					double subTotal = total * (100/(100+gstRate));
					gstAmt=total-subTotal;
					if(prodHB.getChildren().contains(cessTxt)) {
//						double subTotal1=total*(100/(100+Double.parseDouble(cessTxt.getText())));
//						cessAmt=total-subTotal1;
//						subTotal=subTotal+subTotal1;
//						subTotal=subTotal+cess;
//						gst=gst+cess;
						cessAmt=0;
					}
					taxableAmt=subTotal;
//					double total = subTotal;
//					double subTotal=gst;
					totalTxt.setText(String.format("%.2f", total));
				}
				}
			}
		});
		
		cessTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
				if (!validateProductControls()) {
					// System.out.println("Reched inside validation");
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					// gp.add(discErrLbl, 3, 7);
					return;
				}
				// totErrLbl.setText("");
				// System.out.println("Oustdie validation");
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if (!newValue.equals("")) {
					if(!(quantityTxt.getText().isEmpty()||rateTxt.getText().isEmpty()||discTxt.getText().isEmpty()||gstRsTxt.getValue()==null)) {
						double total = (Double.parseDouble(quantityTxt.getText())
								* Double.parseDouble(rateTxt.getText())) - Double.parseDouble(discTxt.getText());
						
						double subTotal = total * (100/(100+gstRate));
						gstAmt=total-subTotal;
						if(prodHB.getChildren().contains(cessTxt)) {
//							double subTotal1=total*(100/(100+Double.parseDouble(cessTxt.getText())));
//							cessAmt=total-subTotal1;
//							subTotal=subTotal+subTotal1;
//							subTotal=subTotal+cess;
//							gst=gst+cess;
							cessAmt=0;
						}
						taxableAmt=subTotal;
//						double total = subTotal;
//						double subTotal=gst;
						totalTxt.setText(String.format("%.2f", total));
					}
				}
			}
		});
		JFXButton submitBtn = new JFXButton(" Submit ");
		gp.add(submitBtn, 2,13);
		
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;

			@Override
			public void handle(ActionEvent arg0) {
				
				if (!validateonSubmitButton()) {
					
					return;
				}
				
				PurchaseEntry purchaseEntry = new PurchaseEntry();
				purchaseEntry.setPurchaseId(purchaseId);
				
				purchaseEntry.setInvoiceNo(purchInvoicTxt.getText());
				Supplier supplier = new Supplier();
				supplier.setSupplierName(supplierCombo.getValue());
				
				supplier.setGstState(stateCombo.getValue());
				supplier.setContactNO(contactTxt.getText());
				supplier.setVatTinNo(gstTxt.getText());
				for(Supplier s:supplierList) {
					if(s.getSupplierName().equalsIgnoreCase(supplier.getSupplierName())) {
						supplier.setSupplier_id(s.getSupplier_id());
					}
				}
				purchaseEntry.setSupplier(supplier);
//				System.out.println(purchaseEntry.);
				
				try {
					LocalDate localDate = purchDatePick.getValue();
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
					Date utilDate = new Date(sd.parse(localDate.toString()).getTime());
					purchaseEntry.setPurchaseDate(utilDate);
//					System.out.println(utilDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				purchaseEntry.setSubTotal(Double.parseDouble(grTotalTxt.getText()));
				purchaseEntry.setTotal(Double.parseDouble(grTotalTxt.getText()));
				purchaseEntry.setPaymentType(payCombo.getValue());
//				
				PaymentMode paymentMode= new PaymentMode();
				purchaseEntry.setPaymentMode(paymentMode);
					if(cashCheck.isSelected()) {
						purchaseEntry.getPaymentMode().setCashMode(cashCheck.getText());
						if(!cashAmtTxt.getText().isEmpty()) {
							purchaseEntry.getPaymentMode().setCashAmount(Double.parseDouble(cashAmtTxt.getText()));
						}
						else {
							purchaseEntry.getPaymentMode().setCashAmount(Double.parseDouble(grTotalTxt.getText()));
						}
					}
					if(bankCheck.isSelected()) {
						purchaseEntry.getPaymentMode().setBankMode(bankCheck.getText());
						purchaseEntry.getPaymentMode().setBankName(bankTxt.getText());
						purchaseEntry.getPaymentMode().setChequeNo(cheqTxt.getText());
						
						if(!bankAmtTxt.getText().isEmpty()) {
							purchaseEntry.getPaymentMode().setBankAmount(Double.parseDouble(bankAmtTxt.getText()));
						}
						else {
							purchaseEntry.getPaymentMode().setBankAmount(Double.parseDouble(grTotalTxt.getText()));
						}
					}
					if(cardCheck.isSelected()) {
						purchaseEntry.getPaymentMode().setCardMode(cardCheck.getText());
						purchaseEntry.getPaymentMode().setTransId(transId.getText());
						if(!cardAmtTxt.getText().isEmpty()) {
							purchaseEntry.getPaymentMode().setCardAmount(Double.parseDouble(cardAmtTxt.getText()));
						}
						else {
							purchaseEntry.getPaymentMode().setCardAmount(Double.parseDouble(grTotalTxt.getText()));
						}
						
					}
					if(voucherCheck.isSelected()) {
						purchaseEntry.getPaymentMode().setVoucherMode(voucherCheck.getText());
						purchaseEntry.getPaymentMode().setVoucherWalletCode(voucherTypeTxt.getText());
						if(!vouchAmtTxt.getText().isEmpty()) {
							purchaseEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(vouchAmtTxt.getText()));
						}
						else {
							purchaseEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(grTotalTxt.getText()));
						}
						
					}
				result = purchaseDAO.createPurchaseEntry(purchaseEntry, prodData);
				if (result) {
					// generateInvoice.createPDF("/docs/purchaseInvoice.pdf",purchaseEntry,prodData);
					Alert alert = new Alert(AlertType.INFORMATION, "You have recently created a purchase entry");
					alert.setTitle("Success Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
					Main.dialog.close();
					showPurchaseRecord(anchorPane);
					
					Main.purchaseView.setItems(purchSortedData);
					Main.purchaseView.requestFocus();
					Main.purchaseView.getSelectionModel().selectLast();
					Main.purchaseView.getFocusModel().focusNext();

					Main.purchaseView.setMinSize(900, 500);
					anchorPane.getChildren().set(0, Main.purchaseView);
					if(!itemList.isEmpty()) {
						try {
//							Thread.sleep(10000);
							barcodePrinter.generateBarcode(itemList);
						} catch (ClassNotFoundException | JRException | SQLException | IOException  e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR,
							"Error while updating data..!Please check database connection");
					alert.setTitle("Error Message");
					alert.setHeaderText("HI");
					alert.showAndWait();

				}
				
				
				clearControls();
				clearProductData();
				// partPayTxt.clear();
				// clearLabels();

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
							supplierCombo.requestFocus();
					}
				});
				
		gp.setAlignment(Pos.CENTER);
		return gp;

	}

	/***********************
	 * showPurchaseRecord()
	 * 
	 * @return TableView<PurchaseRecord>
	 ******************/

	public TableView<PurchaseEntry> showPurchaseRecord(AnchorPane anchorPane) {
		TableView<PurchaseEntry> purchaseView = new TableView<PurchaseEntry>();
		purchaseData.clear();
		TableColumn<PurchaseEntry, Date> dateCol = new TableColumn<PurchaseEntry, Date>("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
		dateCol.setMinWidth(180);
		dateCol.setStyle("-fx-alignment:center");
		TableColumn<PurchaseEntry, PurchaseEntry> purchaseNoCol = new TableColumn<PurchaseEntry, PurchaseEntry>(
				"Purchase Entry No.");
		purchaseNoCol.setCellValueFactory(new PropertyValueFactory<>("purchEntryNo"));
		purchaseNoCol.setMinWidth(250);
		purchaseNoCol.setStyle("-fx-alignment:center");

		TableColumn<PurchaseEntry, String> invoiceCol = new TableColumn<PurchaseEntry, String>("Invoice No.");
		invoiceCol.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
		invoiceCol.setMinWidth(220);
		invoiceCol.setStyle("-fx-alignment:center");
		TableColumn<PurchaseEntry, Double> amountCol = new TableColumn<PurchaseEntry, Double>("Amount");
		amountCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
		amountCol.setMinWidth(220);
		amountCol.setStyle("-fx-alignment:center");

//		TableColumn<PurchaseEntry, PurchaseEntry> actionCol = new TableColumn<PurchaseEntry, PurchaseEntry>("Action");
//		actionCol.setCellValueFactory(e -> new ReadOnlyObjectWrapper<>(e.getValue()));
//		// actionCol.;
////		actionCol.setMin);
//		actionCol.setCellFactory(e -> new TableCell<PurchaseEntry, PurchaseEntry>() {
//			private HBox hb = new HBox();
//			private JFXButton deleteBtn = new JFXButton("Delete");
//
////			private JFXButton viewDetailBtn = new JFXButton("View Details");
//			// private JFXButton printBtn= new JFXButton("Print Invoice");
//
//			@Override
//			protected void updateItem(PurchaseEntry purchaseEntry, boolean empty) {
//				// Button editBtn = new Button("Edit");
//
//				// super.updateItem(supplier, empty);
//				deleteBtn.getStyleClass().add("del-btn");
//				if (purchaseEntry == null || empty) {
//					setGraphic(null);
//					return;
//				} else {
////					hb.setMaxWidth(200);
////					hb.setAlignment(Pos.CENTER);
////					hb.setSpacing(20);
////					hb.getChildren().remove(deleteBtn);
////					hb.getChildren().add(deleteBtn);
//					setGraphic(deleteBtn);
////					viewDetailBtn.setOnAction(new EventHandler<ActionEvent>() {
////
////						@Override
////						public void handle(ActionEvent event) {
////							// TODO Auto-generated method stub
////							// create viewDetails view
////							viewPurchaseEntry(purchaseEntry);
////						}
////					});
//					// editBtn.setOnAction(new EventHandler<ActionEvent>() {
//					//
//					// @Override
//					// public void handle(ActionEvent event) {
//					// // TODO Auto-generated method stub
//					// updatePurchaseEntry(purchaseEntry);
//					// }
//					//
//					// });
//					deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//						@Override
//						public void handle(ActionEvent event) {
//							// TODO Auto-generated method stub
//							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
//							alert.setTitle("Confirmation");
//							// alert.setHeaderText("HI");
//							Optional<ButtonType> result = alert.showAndWait();
//							if (result.get() == ButtonType.OK) {
//								// getTableView().getItems().remove(purchaseEntry);
//								// Main.purchaseView.setItems(purchSortedData);
//								getTableView().getSortOrder().remove(purchaseEntry);
//								result1 = purchaseDAO.deletePurchaseEntry(purchaseEntry);
//								showPurchaseRecord(anchorPane);
//								Main.purchaseView.setItems(purchSortedData);
//								Main.purchaseView.requestFocus();
//								Main.purchaseView.getSelectionModel().selectLast();
//								Main.purchaseView.getFocusModel().focusNext();
//
//								Main.purchaseView.setMinSize(750, 500);
//								anchorPane.getChildren().set(0, Main.purchaseView);
//
//								getTableView().refresh();
//								hb.getChildren().clear();
//							}
//
//						}
//					});

					// printBtn.setOnAction(new EventHandler<ActionEvent>() {
					//
					// @Override
					// public void handle(ActionEvent event) {
					// // TODO Auto-generated method stub
					//
					//// work from here create pdf file
					//// GenerateInvoice.printPDF(purchaseEntry);
					// }
					// });
//				}
//			}
//		});

		purchaseView.getColumns().addAll(dateCol, purchaseNoCol, invoiceCol, amountCol);
		List<PurchaseEntry> purchaseList = purchaseDAO.showPurchaseEntry();
		Iterator<PurchaseEntry> itr = purchaseList.iterator();
		while (itr.hasNext()) {
			PurchaseEntry purchaseEntry = itr.next();
			long length = String.valueOf(purchaseEntry.getPurchaseId()).length();
			// System.out.println(purchaseEntry.getPurchaseId());
			String purchaseString = String.format("PE" + "%0" + (7 - length) + "d", purchaseEntry.getPurchaseId());
			String total=BigDecimal.valueOf(purchaseEntry.getTotal())
					.setScale(2,RoundingMode.HALF_UP)
					.toPlainString();
//			System.out.println(total);
//			double total1=Double.parseDouble(total);
//			System.out.println(total1);
			// purchaseData.add(new
			// PurchaseEntry(purchaseEntry.getPurchaseId(),purchaseEntry.getInvoiceNo(),purchaseEntry.getPurchaseDate(),purchaseEntry.getTotal(),purchaseEntry.getId(),purchaseEntry.getSupplier(),purchaseEntry.getMaterial()));
			purchaseData.add(new PurchaseEntry(purchaseEntry.getId(), purchaseEntry.getInvoiceNo(),
					purchaseEntry.getPurchaseId(), purchaseEntry.getSupplier(), purchaseEntry.getSupplierName(),
					purchaseEntry.getPurchaseDate(), purchaseEntry.getMaterial(), purchaseEntry.getSubTotal(),
					purchaseEntry.getTotal(), purchaseEntry.getPaymentType(), purchaseEntry.getPaymentMode(),
					purchaseEntry.getBankName(), purchaseEntry.getChequeNo(), purchaseEntry.getPaidAmount(),
					purchaseString,total));
		}

		purchaseView.getColumns().addListener(new ListChangeListener() {
			public boolean suspended;

			@Override
			public void onChanged(Change c) {
				// TODO Auto-generated method stub
				c.next();
				if (c.wasReplaced() && !suspended) {
					this.suspended = true;
					purchaseView.getColumns().setAll(dateCol, purchaseNoCol, invoiceCol, amountCol);
					this.suspended = false;
				}
			}

		});

		purchFilteredData = new FilteredList<>(purchaseData, p -> true);

		purchSortedData = new SortedList<>(purchFilteredData);
		purchSortedData.comparatorProperty().bind(purchaseView.comparatorProperty());

		purchaseView.setItems(purchSortedData);

		// searchBtn.setMinWidth(500);

		// purchaseView.setItems(purchaseData);
		return purchaseView;
	}
	
	
//	public GridPane viewPurchaseEntry(PurchaseEntry purchaseEntry) {
//		
//		GridPane gp = new GridPane();
//		// gp.setMinSize(1200, 800);
//		gp.getStyleClass().add("grid");
//
//		// gp.autosize();
////		gp.setAlignment(Pos.CENTER);
//		gp.setHgap(10);
//		gp.setVgap(10);
//		gp.setPadding(new Insets(10, 10, 10, 10));
//		// gp.setPadding(new Insets(10, 10, 10, 10));
//		gp.setGridLinesVisible(false);
//		Label titleLabel = new Label(" Create New Purchase Entry");
//		GridPane.setMargin(titleLabel, new Insets(0, -400, 0, 400));
//
//		gp.add(titleLabel, 0, 0);
//		// long purchase_count = 0;
//		// Scene scene = new Scene(gp,1100,600);
//		
//		long purchaseId = purchaseEntry.getEntryId();
//		int length = String.valueOf(purchaseId).length();
//		// Label generLbl = new Label("General Details");
//		// gp.add(generLbl, 0, 1);
//		Label purchEntryLbl = new Label();
//		purchEntryLbl.setText(String.format("PE" + "%0" + (7 - length) + "d", (purchaseId)));
//		GridPane.setMargin(purchEntryLbl, new Insets(0,0,0,-200));
//		gp.add(purchEntryLbl, 6, 0);
//		// Label purchInvoiceLbl = new Label("Purchase Invoice Number");
//		// gp.add(purchInvoiceLbl, 0, 2);
//		
//		HBox supplierHB= new HBox();
//		supplierHB.setMinWidth(500);
//		
//		purchDatePick.setValue(LocalDate.now());
//		purchDatePick.setPromptText("Purchase Date");
//		purchDatePick.setMinWidth(150);
//		
//		
//	}
//	
	
	public GridPane updatePurchaseEntry(PurchaseEntry purchaseEntry,int index,AnchorPane anchorPane) {

//		enableAllControls();
//		clearControls();
//		clearProductData();
		grandTotal = 0;
		gstAmt=0;
		cessAmt=0;
		taxableAmt=0;
//		errorTip.setText("");
		errorTip.setAutoHide(true);
		List<PurchaseProduct> deletedPurchaseList= new ArrayList<>();
		
		GridPane gp = new GridPane();
		// gp.setMinSize(1200, 800);
		gp.getStyleClass().add("grid");

		// gp.autosize();
//		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(10, 10, 10, 10));
		// gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setGridLinesVisible(false);
		Label titleLabel = new Label(" Update Purchase Entry");
		GridPane.setMargin(titleLabel, new Insets(0, -400, 0, 400));

		gp.add(titleLabel, 0, 0);
		// long purchase_count = 0;
		// Scene scene = new Scene(gp,1100,600);

		long purchaseId = purchaseEntry.getPurchaseId();
		int length = String.valueOf(purchaseId).length();
		// Label generLbl = new Label("General Details");
		// gp.add(generLbl, 0, 1);
		Label purchEntryLbl = new Label();
		purchEntryLbl.setText(String.format("PE" + "%0" + (7 - length) + "d", (purchaseId)));
		GridPane.setMargin(purchEntryLbl, new Insets(0,0,0,-200));
		gp.add(purchEntryLbl, 6, 0);
		// Label purchInvoiceLbl = new Label("Purchase Invoice Number");
		// gp.add(purchInvoiceLbl, 0, 2);
		
		HBox supplierHB= new HBox();
		supplierHB.setMinWidth(500);
		
		
//		Label suppDetaiLbl= new Label("Supplier Details");
//		suppDetaiLbl.setStyle("-fx-font-size:12");
////		GridPane.setMargin(suppDetaiLbl, new Insets(0,-500));
//		gp.add(suppDetaiLbl, 1, 1);
		
		purchDatePick= new JFXDatePicker();
		LocalDate ld=LocalDate.parse(purchaseEntry.getPurchaseDate().toString());
		purchDatePick.setValue(ld);
		purchDatePick.setPromptText("Purchase Date");
		purchDatePick.setMinWidth(150);
		KeyEventHandler.dateKeyEvent(purchDatePick);
//		GridPane.setMargin(purchDatePick, new Insets(0, -200, 0, 100));
//		gp.add(purchDatePick, 2, 2);
		purchDatePick.getStyleClass().add("date-pick");
		purchDatePick.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
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
		 purchDatePick.setDayCellFactory(dayCellFactory);
		 
		 
//	 	purchInvoicTxt.clear();
		 purchInvoicTxt= new JFXTextField();
		 purchInvoicTxt.setText(purchaseEntry.getInvoiceNo());
		purchInvoicTxt.setLabelFloat(true);
		purchInvoicTxt.setPromptText("Invoice Number");
		purchInvoicTxt.setMinWidth(150);
		purchInvoicTxt.setStyle("-fx-font-size:12");
		
		supplierCombo= new JFXComboBox<>();
		
		supplierCombo.setLabelFloat(true);
		supplierCombo.getStyleClass().add("jf-combo-box");
//		supplierCombo.getItems().clear();
		supplierCombo.setPromptText("Supplier Name");
		supplierCombo.setMinWidth(150);
		supplierCombo.setStyle("-fx-font-size:12");
		
		ObservableList<String> supplierData = FXCollections.observableArrayList();
		List<Supplier> supplierList = purchaseDAO.getSupplierNames();
		Iterator<Supplier> itr = supplierList.iterator();
		while (itr.hasNext()) {
			Supplier supplier = itr.next();

			supplierData.add(supplier.getSupplierName());
		}
		supplierCombo.setItems(supplierData);
	
		new AutoCompleteComboBoxListener<>(supplierCombo);
		
//		supplierTxt.clear();
//		supplierTxt.setLabelFloat(true);
//		supplierTxt.setPromptText("Supplier Name");
//		supplierTxt.setMinWidth(150);
//		supplierTxt.setStyle("-fx-font-size:12");
		
		stateCombo= new JFXComboBox<>();
		
		stateCombo.setLabelFloat(true);
		stateCombo.setPromptText("State");
		stateCombo.getItems().addAll(fillStateCombo());
		stateCombo.setValue(purchaseEntry.getSupplier().getGstState());
		stateCombo.setMinWidth(150);
		stateCombo.setStyle("-fx-font-size:12");
		stateCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), stateCombo.getValue(), stateCombo.getItems());
				if(s!=null) {
					stateCombo.requestFocus();
					stateCombo.getSelectionModel().select(s);
					
				}
			}
		});
		
	
//		contactTxt.clear();
		contactTxt= new JFXTextField();
		contactTxt.setText(purchaseEntry.getSupplier().getContactNO());
		contactTxt.setPromptText("Contact No");
		contactTxt.setLabelFloat(true);
		contactTxt.setMinWidth(150);
		contactTxt.setStyle("-fx-font-size:12");
		
		
//		gstTxt.clear();
		gstTxt= new JFXTextField();
		gstTxt.setText(purchaseEntry.getSupplier().getVatTinNo());
		gstTxt.setPromptText("GSTIN");
		gstTxt.setLabelFloat(true);
		gstTxt.setMinWidth(150);
		gstTxt.setStyle("-fx-font-size:12");
		
		supplierHB.setSpacing(30);
		supplierHB.getChildren().addAll(purchDatePick,purchInvoicTxt,supplierCombo,stateCombo,contactTxt,gstTxt);
		GridPane.setMargin(supplierHB, new Insets(0,-200,0,100));
		gp.add(supplierHB, 0, 2);
		supplierCombo.setValue(purchaseEntry.getSupplierName());

		HBox prodHB = new HBox();

		Label addProdLbl = new Label("Add Product");
		GridPane.setMargin(addProdLbl, new Insets(0, -200, 0, 100));
		gp.add(addProdLbl, 0, 4);
//		;
		// Label prodLbl = new Label("Product");
		// gp.add(prodLbl, 0, 5);
		
		supplierCombo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(supplierCombo.getValue()!=null) {
					for(Supplier s:supplierList) {
						if(s.getSupplierName().equals(supplierCombo.getValue())) {
							stateCombo.setValue(s.getGstState());
							contactTxt.setText(s.getContactNO());
							gstTxt.setText(s.getVatTinNo());
						}
					}
				}
			}
		});

		stateCombo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(stateCombo.getValue()!=null) {
					
					if(stateCombo.getValue().equals("Maharashtra")) {
						prodHB.getChildren().remove(cessTxt);
					}else {
//						prodHB.getChildren().remove(cessTxt);
//						prodHB.getChildren().add(10,cessTxt);
					}
				}
			}
		});
		
//		prodTxt.getItems().clear();
		prodTxt= new JFXComboBox<>();
		prodTxt.setEditable(true);
//		prodTxt.setTooltip(new Tooltip());
		prodTxt.setLabelFloat(true);
		prodTxt.setPromptText("Select Product");
		prodTxt.getStyleClass().add("jf-combo-box");
		
//		prodTxt.getItems().addAll("Hi","Hello");
		prodTxt.setStyle("-fx-font-size:12");
		prodTxt.setMaxWidth(150);
//		prodTxt.getItems().clear();
		prodList.clear();
		productList = goodsDAO.showProductList();
		for (Product s : productList) {

			prodList.add(s.getProduct_name());
		}
		prodTxt.setItems(prodList);
		new AutoCompleteComboBoxListener<>(prodTxt);
//		new ComboBoxAutoComplete<>(prodTxt);
//		prodTxt.setEditable(false);
		// GridPane.setMargin(prodTxt, new Insets(0, -100, 0, 0));
	
		
		
//		validateOnFocus(prodTxt);
		// gp.add(prodTxt, 0, 6);
		groupTxt= new JFXComboBox<>();
		groupTxt.setLabelFloat(true);
		groupTxt.getItems().clear();
		groupTxt.setPromptText("Product Group");
		groupTxt.setPrefWidth(70);
		groupTxt.getEditor().setPrefWidth(70);
		groupTxt.setStyle("-fx-font-size:12");
		groupTxt.getStyleClass().add("jf-combo-box");
		
		subGroupTxt= new JFXComboBox<>();
		subGroupTxt.setLabelFloat(true);
		subGroupTxt.getItems().clear();
		subGroupTxt.setPromptText("SubGroup");
		subGroupTxt.setPrefWidth(60);
		subGroupTxt.getStyleClass().add("jf-combo-box");
		subGroupTxt.setStyle("-fx-font-size:12");
		
		
		ObservableList<String> categoryList= FXCollections.observableArrayList();
		ObservableList<String> subGroupList= FXCollections.observableArrayList();
		List<Category> categoryData= goodsDAO.showCategory();
		List<SubCategory> subCategoryData= goodsDAO.showSubCategory();
		for(Category c: categoryData) {
			categoryList.add(c.getCategory());
		}
		
		for(SubCategory s:subCategoryData) {
			subGroupList.add(s.getSubCategory());
		}
		groupTxt.setItems(categoryList);
		new AutoCompleteComboBoxListener<>(groupTxt);
		
		subGroupTxt.setItems(subGroupList);
		new AutoCompleteComboBoxListener<>(subGroupTxt);
		
//		hsnTxt.clear();
		hsnTxt= new JFXTextField();
		hsnTxt.setPromptText("HSN/SAC");
		hsnTxt.setLabelFloat(true);
//		hsnTxt.setEditable(false);
		hsnTxt.setStyle("-fx-font-size:12");
		hsnTxt.setMaxWidth(60);
		
		unitCombo= new JFXComboBox<>();
		unitCombo.setLabelFloat(true);
		unitCombo.getItems().clear();
		unitCombo.setPromptText("Select Unit");
		unitCombo.setStyle("-fx-font-size:12");
		unitCombo.getStyleClass().add("jf-combo-box");
		
		ObservableList<String> unitList=FXCollections.observableArrayList();
		
		List<Unit> unitData=goodsDAO.getUnitList();
		for(Unit u:unitData) {
			unitList.add(u.getUnit());
		}
		unitCombo.setItems(unitList);
		
		// GridPane.setMargin(unitCombo, new Insets(0, -100, 0, -20));
		
		unitCombo.setMaxWidth(80);
		
		// unitCombo.getItems().addAll("KG","PCS","LIT","ML","GRAM");
		new AutoCompleteComboBoxListener<>(unitCombo);

		quantityTxt= new JFXTextField();
		quantityTxt.setPromptText("Quantity");
		quantityTxt.setText("0");
		quantityTxt.setStyle("-fx-font-size:12");
		quantityTxt.setLabelFloat(true);
		quantityTxt.setMaxWidth(50);
		
		
		rateTxt= new JFXTextField();
		rateTxt.setLabelFloat(true);
		rateTxt.setStyle("-fx-font-size:12");
		rateTxt.setMaxWidth(80);
		rateTxt.setPromptText("Cost Price");
		rateTxt.setText("0.00");
		
		sellPriceTxt= new JFXTextField();
		sellPriceTxt.setLabelFloat(true);
		sellPriceTxt.setStyle("-fx-font-size:12");
		sellPriceTxt.setMaxWidth(80);
		sellPriceTxt.setPromptText("Selling Price");
		sellPriceTxt.setText("0.00");

		discTxt= new JFXTextField();
		discTxt.setMaxWidth(50);
		discTxt.setStyle("-fx-font-size:12");
		discTxt.setLabelFloat(true);
		discTxt.setPromptText("Discount(Rs.)");
		discTxt.setText("0.0");

//		taxableTxt.setLabelFloat(true);
//		taxableTxt.setPromptText("Taxable" + " Amt");
////		taxableTxt.setText("0");
//		taxableTxt.setMaxWidth(80);
//		taxableTxt.setEditable(false);
//		taxableTxt.setStyle("-fx-font-size:12");
		
//		cGstTxt.clear();
//		cGstTxt.setPromptText("CGST");
//		cGstTxt.setMaxWidth(50);
//		cGstTxt.setEditable(false);
//		cGstTxt.setStyle("-fx-font-size:12");
//		cGstTxt.setLabelFloat(true);
		
		gstRsTxt= new JFXComboBox<>();
		gstRsTxt.setLabelFloat(true);
		gstRsTxt.getItems().clear();
		gstRsTxt.setPromptText("Tax Rate(%)");
		gstRsTxt.setMaxWidth(80);
		gstRsTxt.setEditable(false);
		gstRsTxt.setStyle("-fx-font-size:12");
		
		gstRsTxt.getItems().addAll("0.0","5.0","12.0","18.0","28.0");
//		gstRsTxt.getSelectionModel().select(0);
		gstRsTxt.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
//				errorTip.hide();
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), gstRsTxt.getValue(), gstRsTxt.getItems());
				if(s!=null) {
					gstRsTxt.requestFocus();
					gstRsTxt.getSelectionModel().select(s);
					
				}
			}
		});
//		iGstTxt.clear();
//		iGstTxt.setPromptText("IGST");
//		iGstTxt.setMaxWidth(50);
//		iGstTxt.setEditable(false);
//		iGstTxt.setStyle("-fx-font-size:12");
//		iGstTxt.setLabelFloat(true);
//
		cessTxt= new JFXTextField();
		cessTxt.setLabelFloat(true);
		cessTxt.clear();
		cessTxt.setPromptText("Cess(%)");
		cessTxt.setText("0.00");
		cessTxt.setMaxWidth(50);
//		cessTxt.setEditable(false);
		cessTxt.setStyle("-fx-font-size:12");
		
		totalTxt= new JFXTextField();
		totalTxt.setEditable(false);
		totalTxt.setPromptText("Total");

		totalTxt.setLabelFloat(true);
		totalTxt.setText("0.00");
		totalTxt.setStyle("-fx-font-size: 15px;-fx-font-weight:bold");
		// GridPane.setMargin(, new Insets(0, -50, 0, -50));
		totalTxt.setFocusColor(Color.TRANSPARENT);
		totalTxt.setUnFocusColor(Color.TRANSPARENT);
		totalTxt.setMaxWidth(100);
//		HBox.setMargin(totalTxt, new Insets(0, 0, 20, 20));
		// totalTxt.setPadding(new Insets(0, 100, 0, 0));
		// GridPane.setMargin(totalTxt, new Insets(0, -100, 0, -100));
		// gp.add(totalTxt, 6, 6);


		JFXButton addMoreBtn = new JFXButton(" Add ");
		addMoreBtn.setStyle("-fx-font-size:12px; -fx-font-weight:bold;");
//		HBox.setMargin(addMoreBtn, new Insets(0, -20, 0, 20));
		addMoreBtn.setMaxWidth(80);
		addMoreBtn.setDisable(true);
		// addMoreBtn.setPadding(new Insets(0, 100, 0, 0));
		// GridPane.setMargin(addMoreBtn, new Insets(0, 0, 0, -100));

		// gp.add(addMoreBtn, 7, 6);

		JFXButton editBtn = new JFXButton("Edit");
//		HBox.setMargin(editBtn, new Insets(0, -20, 0, 20));
		editBtn.setMaxWidth(80);

		RadioButton addItem = new RadioButton("Add Item");
		addItem.setUserData("Add Item");
		addItem.setSelected(true);
		RadioButton editItem = new RadioButton("Edit Item");
//		editItem.setDisable(true);
		editItem.setUserData("Edit Item");
		
		ToggleGroup tg = new ToggleGroup();
		addItem.setToggleGroup(tg);
		editItem.setToggleGroup(tg);
		VBox vb1 = new VBox();
		vb1.setStyle("-fx-font-size:16px; -fx-font-weight:bold;");
		vb1.getChildren().addAll(addItem, editItem);
//		vb1.setMaxHeight(250);
		// GridPane.setMargin(vb1, new Insets(0, 0, 0, -100));
		// gp.add(vb1, 8,6);
//		HBox.setMargin(vb1, new Insets(0,0,0,20));
		prodHB.getChildren().addAll(prodTxt, groupTxt,subGroupTxt,hsnTxt, unitCombo, quantityTxt, rateTxt, sellPriceTxt,discTxt,gstRsTxt, totalTxt,
				addMoreBtn);
		
		// prodHB.setPadding(new Insets(0, 50, 0, 50));
		prodHB.setSpacing(20);
		GridPane.setMargin(prodHB, new Insets(0, 0, 0, 100));
		gp.add(prodHB, 0, 6, 8, 1);

		// Label grTotalLbl = new Label("Grand Total");
		// gp.add(grTotalLbl, 4, 9);
		

		ObservableList<PurchaseProduct> prodData = FXCollections.observableArrayList();
		List<PurchaseProduct> prodList= purchaseDAO.getProductDetails(purchaseEntry);
		for(PurchaseProduct pp:prodList) {
			double gst=pp.getSubTotal()*(pp.getProduct().getGst()/100);
			String rate=BigDecimal.valueOf(pp.getRate())
					.setScale(2,RoundingMode.HALF_UP)
					.toPlainString();
			String total=BigDecimal.valueOf(pp.getAmount())
					.setScale(2,RoundingMode.HALF_UP)
					.toPlainString();
			String discString=BigDecimal.valueOf(pp.getDiscount())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();
			String subTotalString=BigDecimal.valueOf(pp.getSubTotal())
					.setScale(2,RoundingMode.HALF_UP)
					.toPlainString();
			if(stateCombo.getValue().contains("Maharashtra")) {
				String cgst=String.format("%.2f", (gst/2))+"\n("+(pp.getProduct().getGst()/2)+"%)";
				String sgst=String.format("%.2f", (gst/2))+"\n("+(pp.getProduct().getGst()/2)+"%)";
//				System.out.println(pp.getCategory());
				prodData.add(new PurchaseProduct(pp.getProduct().getProduct_name(),pp.getId(), pp.getUnitObj(),pp.getCategory(),pp.getSubCategory(),pp.getProduct().getCategory(),pp.getProduct().getSubGroup(),pp.getProduct().getHsnNo(),pp.getProduct().getSellPrice(), pp.getQuantity(), pp.getPrevQty(),pp.getProduct().getUnit(), pp.getRate(),pp.getDiscount(),
						pp.getAmount(), pp.getSubTotal(),  cgst,  sgst, "",  "",pp.getProduct(), rate, discString, subTotalString, total));
			}else {
				String igst=String.format("%.2f", gst)+"\n("+(pp.getProduct().getGst())+"%)";
				String cess="0";
				prodData.add(new PurchaseProduct(pp.getProduct().getProduct_name(),pp.getId(),pp.getUnitObj(), pp.getCategory(),pp.getSubCategory(),pp.getProduct().getCategory(),pp.getProduct().getSubGroup(),pp.getProduct().getHsnNo(),pp.getProduct().getSellPrice(), pp.getQuantity(), pp.getPrevQty(),pp.getProduct().getUnit(), pp.getRate(),pp.getDiscount(),
						pp.getAmount(), pp.getSubTotal(),  "",  "", igst,  cess,pp.getProduct(), rate, discString, subTotalString, total));
			}
			
			
		}
		TableView<PurchaseProduct> prodView = new TableView<PurchaseProduct>();
		prodView.setMaxSize(1100, 200);
		
		TableColumn<PurchaseProduct, String> prodName = new TableColumn<PurchaseProduct, String>("Product\n"+" Name");
		prodName.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
		prodName.setMinWidth(100);
		TableColumn<PurchaseProduct, String> groupCol= new TableColumn<>("Product\n"+"Group");
		groupCol.setCellValueFactory(new PropertyValueFactory<>("group"));
//		groupCol.setPrefWidth(60);
		TableColumn<PurchaseProduct, String> subGroupCol= new TableColumn<PurchaseProduct, String>("Sub\n"+"Group");
		subGroupCol.setCellValueFactory(new PropertyValueFactory<>("subGroup"));
//		subGroupCol.setPrefWidth(60);
		TableColumn<PurchaseProduct, Long> hsnCol = new TableColumn<PurchaseProduct, Long>("HSN/\n"+"SAC");
		hsnCol.setCellValueFactory(new PropertyValueFactory<>("hsnNo"));
		hsnCol.setPrefWidth(60);
		TableColumn<PurchaseProduct, String> unitCol = new TableColumn<PurchaseProduct, String>("Unit");
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		unitCol.setPrefWidth(50);
		TableColumn<PurchaseProduct, Integer> quantityCol = new TableColumn<PurchaseProduct, Integer>("Qty.");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		quantityCol.setPrefWidth(50);
		TableColumn<PurchaseProduct, Double> rateCol = new TableColumn<PurchaseProduct, Double>("Cost"+"\nPrice");
		rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
//		rateCol.setPrefWidth(50);
		
		TableColumn<PurchaseProduct, Double> sellPriceCol= new TableColumn<PurchaseProduct, Double>("Sell\n"+"Price");
		sellPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
		sellPriceCol.setPrefWidth(80);
		TableColumn<PurchaseProduct, Double> discCol = new TableColumn<PurchaseProduct, Double>("Disc.\n"+" Rs.");
		discCol.setCellValueFactory(new PropertyValueFactory<>("discString"));
		discCol.setPrefWidth(60);
//		TableColumn<PurchaseProduct, SalesProduct> taxableCol = new TableColumn<PurchaseProduct, SalesProduct>(
//				"Taxable\n"+"Amount");
//		taxableCol.setPrefWidth(100);
//		taxableCol.setCellValueFactory(new PropertyValueFactory<>("subTotalString"));
		TableColumn<PurchaseProduct, PurchaseProduct> cgstCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"CGST");
		cgstCol.setPrefWidth(70);
		cgstCol.setCellValueFactory(new PropertyValueFactory<>("cgst"));
		TableColumn<PurchaseProduct, PurchaseProduct> sgstCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"SGST");
		sgstCol.setCellValueFactory(new PropertyValueFactory<>("sgst"));
		sgstCol.setPrefWidth(70);
		TableColumn<PurchaseProduct, PurchaseProduct> igstCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"IGST");
		igstCol.setPrefWidth(70);
		igstCol.setCellValueFactory(new PropertyValueFactory<>("igst"));
		TableColumn<PurchaseProduct, PurchaseProduct> cessCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"Cess");
		cessCol.setCellValueFactory(new PropertyValueFactory<>("cess"));
		cessCol.setPrefWidth(70);
		TableColumn<PurchaseProduct, Double> totalCol = new TableColumn<PurchaseProduct, Double>("Total");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
		totalCol.setPrefWidth(100);
		TableColumn<PurchaseProduct, PurchaseProduct> actionCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"Action");
		actionCol.setCellValueFactory(e -> new ReadOnlyObjectWrapper<>(e.getValue()));
//		actionCol.setPrefWidth(50);
		actionCol.setCellFactory(e -> new TableCell<PurchaseProduct, PurchaseProduct>() {
			// Button deleteBtn = new Button("Delete");

			@Override
			protected void updateItem(PurchaseProduct purchaseProduct, boolean empty) {
				if (purchaseProduct == null) {
					setGraphic(null);
					return;
				} else {
					deleteBtn = new JFXButton("Delete");
					deleteBtn.getStyleClass().add("del-btn");
					setGraphic(deleteBtn);
					deleteBtn.setAlignment(Pos.CENTER);
					deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							// TODO Auto-generated method stub
							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
							// alert.setHeaderText("HI");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK) {
								deletedPurchaseList.add(purchaseProduct);
								getTableView().getItems().remove(purchaseProduct);
								getTableView().refresh();
								grandTotal = 0;
								
								if (prodData.size() == 0)
									grTotalTxt.setText("0.0");
								else {
									for (PurchaseProduct p : prodData) {
										grandTotal = grandTotal + p.getAmount();
										grTotalTxt.setText(String.format("%.2f", grandTotal));

									}
								}
							}

						}
					});

				}
			}
		});

		prodView.getColumns().addAll(prodName, groupCol,subGroupCol,hsnCol,unitCol, quantityCol, rateCol,sellPriceCol, discCol, cgstCol, sgstCol,
				igstCol, cessCol, totalCol, actionCol);
		prodView.setItems(prodData);
		prodView.requestFocus();
		prodView.getSelectionModel().selectFirst();
		stateCombo.requestFocus();

		prodTxt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if (prodTxt.getValue() != null) {
					addMoreBtn.setDisable(false);
					editBtn.setDisable(false);
							for (Product p : productList) {
								if (p.getProduct_name().equals(prodTxt.getValue())) {
									groupTxt.setValue(p.getCategory());
									subGroupTxt.setValue(p.getSubGroup());
									gstRate = p.getGst();
//									cessAmt=p.getCess();
									gstRsTxt.setValue(String.valueOf(p.getGst()));
									hsnTxt.setText(String.valueOf(p.getHsnNo()));
									rateTxt.setText(String.valueOf(p.getBuyPrice()));
									sellPriceTxt.setText(String.valueOf(p.getSellPrice()));
									unitCombo.setValue(p.getUnit());
//									cessTxt.setText(String.valueOf(p.getCess()));
								}

							}
						} 

					// return;
			}
		});

		unitCombo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if (unitCombo.getValue() != null) {
					addMoreBtn.setDisable(false);
					editBtn.setDisable(false);
				}
			}
		});
		
		addMoreBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				// clearLabels();
				// gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
				// System.out.println(validateControls());
				boolean productFlag=false;
				boolean unitFlag=false;
				if (!validateOnAddEdit()) {
					
					return;
				}
				// totErrLbl.setText("");
				// addMoreBtn.setDisable(false);
				PurchaseProduct purchaseProduct = new PurchaseProduct();
				Product product = new Product();
				purchaseProduct.setProduct(product);
				Category category= new Category();
				Unit unitObj= new Unit();
				
				purchaseProduct.setCategory(category);
				SubCategory subCategory= new SubCategory();
				purchaseProduct.setSubCategory(subCategory);
				for(Product p:productList) {
					if(p.getProduct_name().equals(prodTxt.getValue())) {
						product.setId(p.getId());
						purchaseProduct.setCurrentStock(p.getQuantity());
					}
				}
				for(Category c:categoryData) {
					if(c.getCategory().equals(groupTxt.getValue())){
						category.setId(c.getId());
					}
				}
				
				for(SubCategory s:subCategoryData) {
					if(s.getSubCategory().equals(subGroupTxt.getValue())){
						subCategory.setId(s.getId());
					}
				}
				
			
				for(Unit u:unitData) {
					if(u.getUnit().equals(unitCombo.getValue())) {
						unitObj.setId(u.getId());
						unitObj.setUnit(u.getUnit());
					}
				}
				purchaseProduct.setUnitObj(unitObj);
				purchaseProduct.getProduct().setProduct_name(prodTxt.getValue());
				purchaseProduct.getProduct().setHsnNo(Long.parseLong(hsnTxt.getText()));
				purchaseProduct.getProduct().setUnit(unitCombo.getValue());
				purchaseProduct.getProduct().setQuantity(Integer.parseInt(quantityTxt.getText()));
				purchaseProduct.getProduct().setSellPrice(Double.parseDouble(sellPriceTxt.getText()));
				purchaseProduct.getProduct().setBuyPrice(Double.parseDouble(rateTxt.getText()));
				purchaseProduct.getProduct().setGst(Double.parseDouble(gstRsTxt.getValue()));
				purchaseProduct.getProduct().setCategory(groupTxt.getValue());
				purchaseProduct.getProduct().setSubGroup(subGroupTxt.getValue());
				if(!cessTxt.getText().isEmpty()) {
				purchaseProduct.getProduct().setCess(Double.parseDouble(cessTxt.getText()));
				}
				purchaseProduct.setProd_name(prodTxt.getValue());
				purchaseProduct.setUnit(unitCombo.getValue());
				purchaseProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
				purchaseProduct.setHsnNo(Long.parseLong(hsnTxt.getText()));
				purchaseProduct.setGroup(groupTxt.getValue());
				purchaseProduct.setSubGroup(subGroupTxt.getValue());
				
				category.setCategory(groupTxt.getValue());
				category.setSubGroup(subGroupTxt.getValue());
				
				String trunRateAmt=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
					    .setScale(2, RoundingMode.HALF_UP)
					    .toPlainString(); 
				purchaseProduct.setRateString(trunRateAmt);
				purchaseProduct.setRate(Double.parseDouble(rateTxt.getText()));
				
				String trunSaleRate=BigDecimal.valueOf(Double.parseDouble(sellPriceTxt.getText()))
						.setScale(2,RoundingMode.HALF_UP)
						.toPlainString();
				purchaseProduct.setSellPrice(Double.parseDouble(sellPriceTxt.getText()));
				purchaseProduct.setSellPriceString(trunSaleRate);
				
				String trunDiscAmt=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
					    .setScale(2, RoundingMode.HALF_UP)
					    .toPlainString();
				purchaseProduct.setDiscString(trunDiscAmt);
				purchaseProduct.setDiscount(Double.parseDouble(discTxt.getText()));
				
				// purchaseProduct.setGst(Double.parseDouble(gstRsTxt.getText()));
				if(stateCombo.getValue().equals("Maharashtra")) {
				double halfGst=gstRate/2;
				
				String gst=BigDecimal.valueOf(gstAmt/2)
						.setScale(2,RoundingMode.HALF_UP)
						.toPlainString();
				
				String cgstTxt=(gst)+"\n("+halfGst+"%)";
				purchaseProduct.setCgst(cgstTxt);
				
				String sgstTxt=(gst)+"\n("+halfGst+"%)";
				purchaseProduct.setSgst(sgstTxt);
				}else {
					String gst=BigDecimal.valueOf(gstAmt)
							.setScale(2,RoundingMode.HALF_UP)
							.toPlainString();
					
					String igstTxt=(gst)+"\n("+gstRate+"%)";
					purchaseProduct.setIgst(igstTxt);
					
					String cesssTxt=(cessAmt)+"\n("+cessTxt.getText()+"%)";
					purchaseProduct.setCess(cesssTxt);
					purchaseProduct.setCessAmt(Double.parseDouble(cessTxt.getText()));
				}
				
				
				String trunTaxablAmt=BigDecimal.valueOf(taxableAmt)
					    .setScale(2, RoundingMode.HALF_UP)
					    .toPlainString();
				purchaseProduct.setSubTotalString(trunTaxablAmt);
				double taxAmt=BigDecimal.valueOf(taxableAmt)
						.setScale(2,RoundingMode.HALF_UP)
						.doubleValue();
				purchaseProduct.setSubTotal(taxAmt);
				
				String trunTotalAmt=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
					    .setScale(2, RoundingMode.HALF_UP)
					    .toPlainString();
				purchaseProduct.setTotalString(trunTotalAmt);
				purchaseProduct.setAmount(Double.parseDouble(totalTxt.getText()));
				// System.out.println(prodData.contains(purchaseProduct));
				for (PurchaseProduct p : prodData) {
					if (p.equals(purchaseProduct)) {
						errorTip.setText("Duplicate entries are not permitted");
						prodTxt.setTooltip(errorTip);
						errorTip.show(prodTxt, 100, 200);
						return;
						// return;
					}
					if(p.getGroup().equals(purchaseProduct.getGroup())) {
						category.setId(1);
					}
					if(p.getSubGroup().equals(purchaseProduct.getSubGroup())) {
						subCategory.setId(1);
					}
					if(p.getUnit().equals(purchaseProduct.getUnit())) {
						unitObj.setId(1);
					}
					
				}
				
				

				// dupliErrLbl.setText("");
				prodData.add(purchaseProduct);

				prodView.setItems(prodData);
				prodView.requestFocus();
				prodView.getSelectionModel().selectLast();
				cashCheck.requestFocus();
				grandTotal = grandTotal + purchaseProduct.getAmount();
				grTotalTxt.setText(String.format("%.2f", grandTotal));

				clearProductData();
				editItem.setDisable(false);
			}
		});
		// prodView.setMaxHeight(200);
		// prodView.setMaxWidth(700);
		// prodView.setPadding(new Insets(0, 0, 0, 100));
		vb1.setMinWidth(100);
		GridPane.setMargin(vb1, new Insets(0,-50,0,50));
		gp.add(vb1, 6, 8);
		GridPane.setMargin(prodView, new Insets(0, -200, 0, 100));
		gp.add(prodView, 0, 8, 11, 1);

		
//		
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				if(tg.getSelectedToggle()!=null) {
					 String item=tg.getSelectedToggle().getUserData().toString();
					if(item.equals("Add Item")) {
						prodHB.getChildren().removeAll(editBtn,addMoreBtn);
						prodHB.getChildren().add(10,addMoreBtn);
						deleteBtn.setDisable(false);
						clearProductData();
					}else if(item.equals("Edit Item")) {
						deleteBtn.setDisable(true);
						PurchaseProduct purchaseProduct = prodView.getSelectionModel().getSelectedItem();
						int index = prodView.getSelectionModel().getSelectedIndex();
						prodTxt.setValue(purchaseProduct.getProd_name());
						unitCombo.setValue(purchaseProduct.getUnit());
						hsnTxt.setText(String.valueOf(purchaseProduct.getHsnNo()));
						groupTxt.setValue(purchaseProduct.getGroup());
						subGroupTxt.setValue(purchaseProduct.getSubGroup());
						purchaseProduct.setPrevQty(purchaseProduct.getQuantity());
						quantityTxt.setText(String.valueOf(purchaseProduct.getQuantity()));
						
						String trunRateAmt=BigDecimal.valueOf(purchaseProduct.getRate())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
						
						rateTxt.setText(trunRateAmt);
						
						String trunSellRate=BigDecimal.valueOf(purchaseProduct.getSellPrice())
								.setScale(2, RoundingMode.HALF_UP)
								.toPlainString();
						sellPriceTxt.setText(trunSellRate);
						
						String trunDiscAmt=BigDecimal.valueOf(purchaseProduct.getDiscount())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
						discTxt.setText(trunDiscAmt);
						gstRsTxt.setValue(String.valueOf(purchaseProduct.getGst()));
//						cGstTxt.setText(String.valueOf(purchaseProduct.getCgst()));
//						iGstTxt.setText(String.valueOf(purchaseProduct.getIgst()));
						cessTxt.setText(String.valueOf(purchaseProduct.getCess()));
//						String trunTaxableAmt=BigDecimal.valueOf(purchaseProduct.getSubTotal())
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						taxableTxt.setText(trunTaxableAmt);
						
						totalTxt.setText(String.valueOf(purchaseProduct.getAmount()));
						String trunTotalAmt=BigDecimal.valueOf(purchaseProduct.getAmount())
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
						totalTxt.setText(trunTotalAmt);
						grandTotal = grandTotal - purchaseProduct.getAmount();
						prodHB.getChildren().remove(addMoreBtn);
						prodHB.getChildren().add(10, editBtn);
						// gp.add(editBtn, 7, 6);

						editBtn.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								if (!validateOnAddEdit()) {
									return;
								}
								// PurchaseProduct purchaseProduct1 = new PurchaseProduct();
								
								for(Product p:productList) {
									if(p.getProduct_name().equals(prodTxt.getValue())) {
										purchaseProduct.getProduct().setId(p.getId());
										purchaseProduct.getCategory().setProductId(p.getId());
										purchaseProduct.setCurrentStock(p.getQuantity());
									}
								}
								
								for(Category c:categoryData) {
									if(c.getCategory().equals(groupTxt.getValue())) {
										purchaseProduct.getCategory().setId(c.getId());
									}
								}
								purchaseProduct.getProduct().setProduct_name(prodTxt.getValue());
								purchaseProduct.getProduct().setHsnNo(Long.parseLong(hsnTxt.getText()));
								purchaseProduct.getProduct().setCategory(groupTxt.getValue());
								purchaseProduct.getProduct().setSubGroup(subGroupTxt.getValue());
								purchaseProduct.getProduct().setUnit(unitCombo.getValue());
								purchaseProduct.getProduct().setQuantity(Integer.parseInt(quantityTxt.getText()));
								purchaseProduct.getProduct().setSellPrice(Double.parseDouble(rateTxt.getText()));
								purchaseProduct.getProduct().setGst(Double.parseDouble(gstRsTxt.getValue()));
								
								purchaseProduct.setGroup(groupTxt.getValue());
								purchaseProduct.setSubGroup(subGroupTxt.getValue());
								
								purchaseProduct.getCategory().setSubGroup(subGroupTxt.getValue());
								purchaseProduct.getCategory().setCategory(groupTxt.getValue());
								
								purchaseProduct.setProd_name(prodTxt.getValue());
								purchaseProduct.setUnit(unitCombo.getValue());
								
								purchaseProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
								purchaseProduct.setRate(Double.parseDouble(rateTxt.getText()));
								String trunRateAmt1=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
										.setScale(2, RoundingMode.HALF_UP)
										.toPlainString();
								purchaseProduct.setRateString(trunRateAmt1);
								
								String trunDiscAmt1=BigDecimal.valueOf(Double.parseDouble(discTxt.getText()))
										.setScale(2, RoundingMode.HALF_UP)
										.toPlainString();
								purchaseProduct.setDiscString(trunDiscAmt1);
								purchaseProduct.setDiscount(Double.parseDouble(discTxt.getText()));
								// purchaseProduct.setGst(Double.parseDouble(gstRsTxt.getText()));
								if(stateCombo.getValue().equals("Maharashtra")) {
								double halfGst=gstRate/2;
								
								
								String cgstTxt=(gstAmt/2)+"\n("+halfGst+"%)";
								purchaseProduct.setCgst(cgstTxt);
								
								String sgstTxt=(gstAmt/2)+"\n("+halfGst+"%)";
								purchaseProduct.setSgst(sgstTxt);
								}else {
									String igstTxt=(gstAmt)+"\n("+gstRate+"%)";
									purchaseProduct.setIgst(igstTxt);
									
									String cesssTxt=(cessAmt)+"\n("+cessTxt.getText()+"%)";
									purchaseProduct.setCess(cesssTxt);
									purchaseProduct.setCessAmt(Double.parseDouble(cessTxt.getText()));
								}
								
							String trunTaxableAmt1=BigDecimal.valueOf(taxableAmt)
									.setScale(2, RoundingMode.HALF_UP)
									.toPlainString();
							
							purchaseProduct.setSubTotalString(trunTaxableAmt1);
							
							double taxAmt=BigDecimal.valueOf(taxableAmt)
									.setScale(2, RoundingMode.HALF_UP)
									.doubleValue();
							
							purchaseProduct.setSubTotal(taxAmt);
								
							String trunTotalAmt1=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
										.setScale(2, RoundingMode.HALF_UP)
										.toPlainString();
							purchaseProduct.setTotalString(trunTotalAmt1);
							purchaseProduct.setAmount(Double.parseDouble(totalTxt.getText()));

								// for(PurchaseProduct p:prodData){
								// if(p.getProd_name().equals(purchaseProduct.getProd_name())){
								// dupliErrLbl.setText("Duplicate entries are not permitted");
								// gp.add(dupliErrLbl, 0, 7);
								// return;
								// }
								// }
								// prodData.remove(purchaseProduct);
								prodData.set(index, purchaseProduct);
								prodView.requestFocus();
								prodView.getSelectionModel().select(purchaseProduct);
								cashCheck.requestFocus();
								
								grandTotal = 0;
								for (PurchaseProduct p : prodData) {
									grandTotal = grandTotal + p.getAmount();
								}
								grTotalTxt.setText(String.format("%.2f", grandTotal));
								editBtn.setDisable(true);
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
		// Label payTypeLbl = new Label("Payment Type");
		// gp.add(payTypeLbl, 4, 1);
		HBox paymentBox= new HBox();
		
		grTotalTxt= new JFXTextField();
		grTotalTxt.setLabelFloat(true);
		grTotalTxt.setPromptText("Grand Total");
		grTotalTxt.setStyle("-fx-font-size: 20px;-fx-font-weight:bold");
		grTotalTxt.setEditable(false);
		grTotalTxt.setFocusColor(Color.TRANSPARENT);
		grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
		grTotalTxt.setText(String.format("%.2f", purchaseEntry.getTotal()));
		GridPane.setMargin(grTotalTxt, new Insets(20,-200,0,100));
		gp.add(grTotalTxt, 0, 10);
		
		
		PaymentMode paymentMode=purchaseDAO.getPaymentModes(purchaseEntry);
		payModeLbl.setStyle("-fx-font-size:14");
		GridPane.setMargin(payModeLbl, new Insets(-70,100,0,-200));
		gp.add(payModeLbl, 2, 10);
		
		cashCheck= new JFXCheckBox();
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
		
		paymentBox.setSpacing(20);
		paymentBox.setMaxSize(160, 50);
		paymentBox.setStyle("-fx-border-style: solid;-fx-padding:15;-fx-border-width: 2;-fx-border-insets:5; -fx-border-radius: 5;");
		paymentBox.getChildren().addAll(cashBox,bankBox,cardBox,vouchBox);
		
		
		
		GridPane.setMargin(paymentBox, new Insets(-50,100,0,-200));
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
		// rateTxt.setonk

		quantityTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub	
				errorTip.hide();

				if (!validateProductControls()) {
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					// gp.add(quantityErrLbl, 1, 7);
					return;
				}

				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);

				if (!newValue.isEmpty()) {
					if (!(rateTxt.getText().equals("") || discTxt.getText().equals("")||gstRsTxt.getValue()==null)) {
						double total = (Double.parseDouble(newValue) * Double.parseDouble(rateTxt.getText()))
								- Double.parseDouble(discTxt.getText());
						
//						String trunTaxablAmt=BigDecimal.valueOf(subTotal)
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						taxableTxt.setText(trunTaxablAmt);
//						
//						if(gstRate==0) {
							gstRate=Double.parseDouble(gstRsTxt.getValue());
//						}
						double subTotal = total * (100/(100+gstRate));
						gstAmt=total-subTotal;
						if(prodHB.getChildren().contains(cessTxt)) {
//							double subTotal1=total*(100/(100+Double.parseDouble(cessTxt.getText())));
//							cessAmt=total-subTotal1;
//							subTotal=subTotal+subTotal1;
//							subTotal=subTotal+cess;
//							gst=gst+cess;
							cessAmt=0;
						}
						taxableAmt=subTotal;
//						double total = subTotal;
//						double subTotal=gst;
						totalTxt.setText(String.format("%.2f", total));
					} else {
						totalTxt.setText("NaN");
					}
				} else {
					quantityTxt.setText(quantityTxt.getPromptText());
				}
			}

		});

		rateTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				errorTip.hide();
				if (!validateProductControls()) {
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					return;
				}
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if (!newValue.equals("")) {
					// if(newValue.matches(arg0))
					if (!(quantityTxt.getText().equals("") || discTxt.getText().equals("")||gstRsTxt.getValue()==null)) {
						double total = (Double.parseDouble(quantityTxt.getText()) * Double.parseDouble(newValue))
								- Double.parseDouble(discTxt.getText());
//						String trunTaxablAmt=BigDecimal.valueOf(subTotal)
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						
//						taxableTxt.setText(trunTaxablAmt);
//						if(gstRate==0) {
							gstRate=Double.parseDouble(gstRsTxt.getValue());
//						}
						double subTotal = total * (100/(100+gstRate));
						gstAmt=total-subTotal;
						if(prodHB.getChildren().contains(cessTxt)) {
//							double subTotal1=total*(100/(100+Double.parseDouble(cessTxt.getText())));
//							cessAmt=total-subTotal1;
//							subTotal=subTotal+subTotal1;
//							subTotal=subTotal+cess;
//							gst=gst+cess;
							cessAmt=0;
						}
						taxableAmt=subTotal;
//						double total = subTotal;
//						double subTotal=gst;
						totalTxt.setText(String.format("%.2f", total));
					} else {
						totalTxt.setText("NaN");
					}
				} else
					rateTxt.setText(rateTxt.getPromptText());
			}
		});

		discTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				errorTip.hide();
				if (!validateProductControls()) {
					// System.out.println("Reched inside validation");
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					// gp.add(discErrLbl, 3, 7);
					return;
				}
				
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if (!newValue.equals("")) {
					if (!(quantityTxt.getText().equals("") || rateTxt.getText().equals("")||gstRsTxt.getValue()==null)) {
						double total = (Double.parseDouble(quantityTxt.getText())
								* Double.parseDouble(rateTxt.getText())) - Double.parseDouble(newValue);
//						String trunTaxablAmt=BigDecimal.valueOf(subTotal)
//							    .setScale(2, RoundingMode.HALF_UP)
//							    .toPlainString();
//						taxableTxt.setText(trunTaxablAmt);
//						if(gstRate==0) {
							gstRate=Double.parseDouble(gstRsTxt.getValue());
//						}
						double subTotal = total * (100/(100+gstRate));
						gstAmt=total-subTotal;
						if(prodHB.getChildren().contains(cessTxt)) {
							double subTotal1=total*(100/(100+Double.parseDouble(cessTxt.getText())));
//							cessAmt=total-subTotal1;
//							subTotal=subTotal+subTotal1;
//							subTotal=subTotal+cess;
//							gst=gst+cess;
							cessAmt=0;
						}
						taxableAmt=subTotal;
//						double total = subTotal;
//						double subTotal=gst;
						totalTxt.setText(String.format("%.2f", total));
					} else {
						totalTxt.setText("NaN");
					}
				} else {
					discTxt.setText(discTxt.getPromptText());
				}
			}

		});
		
		gstRsTxt.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(gstRsTxt.getValue()!=null) {
				if(!(quantityTxt.getText().isEmpty()||rateTxt.getText().isEmpty()||discTxt.getText().isEmpty())) {
					double total = (Double.parseDouble(quantityTxt.getText())
							* Double.parseDouble(rateTxt.getText())) - Double.parseDouble(discTxt.getText());
					
					gstRate=Double.parseDouble(gstRsTxt.getValue());
					double subTotal = total * (100/(100+gstRate));
					gstAmt=total-subTotal;
					if(prodHB.getChildren().contains(cessTxt)) {
//						double subTotal1=total*(100/(100+Double.parseDouble(cessTxt.getText())));
//						cessAmt=total-subTotal1;
//						subTotal=subTotal+subTotal1;
//						subTotal=subTotal+cess;
//						gst=gst+cess;
						cessAmt=0;
					}
					taxableAmt=subTotal;
//					double total = subTotal;
//					double subTotal=gst;
					totalTxt.setText(String.format("%.2f", total));
				}
				}
			}
		});
		
		cessTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
				if (!validateProductControls()) {
					// System.out.println("Reched inside validation");
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					// gp.add(discErrLbl, 3, 7);
					return;
				}
				// totErrLbl.setText("");
				// System.out.println("Oustdie validation");
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if (!newValue.equals("")) {
					if(!(quantityTxt.getText().isEmpty()||rateTxt.getText().isEmpty()||discTxt.getText().isEmpty()||gstRsTxt.getValue()==null)) {
						double total = (Double.parseDouble(quantityTxt.getText())
								* Double.parseDouble(rateTxt.getText())) - Double.parseDouble(discTxt.getText());
						
						double subTotal = total * (100/(100+gstRate));
						gstAmt=total-subTotal;
						if(prodHB.getChildren().contains(cessTxt)) {
//							double subTotal1=total*(100/(100+Double.parseDouble(cessTxt.getText())));
//							cessAmt=total-subTotal1;
//							subTotal=subTotal+subTotal1;
//							subTotal=subTotal+cess;
//							gst=gst+cess;
							cessAmt=0;
						}
						taxableAmt=subTotal;
//						double total = subTotal;
//						double subTotal=gst;
						totalTxt.setText(String.format("%.2f", total));
					}
				}
			}
		});
		JFXButton updateBtn = new JFXButton(" Update ");
		gp.add(updateBtn, 2,13);
		
		updateBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;

			@Override
			public void handle(ActionEvent arg0) {
				
				if (!validateonSubmitButton()) {
					
					return;
				}
				if(!deletedPurchaseList.isEmpty()) {
				for(PurchaseProduct p:deletedPurchaseList) {
				purchaseDAO.removeStock(p);	
				}
				}
//				PurchaseEntry purchaseEntry = new PurchaseEntry();
				purchaseEntry.setPurchaseId(purchaseId);
				
				purchaseEntry.setInvoiceNo(purchInvoicTxt.getText());
				Supplier supplier = new Supplier();
				supplier.setSupplierName(supplierCombo.getValue());
				
				supplier.setGstState(stateCombo.getValue());
				supplier.setContactNO(contactTxt.getText());
				supplier.setVatTinNo(gstTxt.getText());
				for(Supplier s:supplierList) {
					if(s.getSupplierName().equalsIgnoreCase(supplier.getSupplierName())) {
						supplier.setSupplier_id(s.getSupplier_id());
					}
				}
				purchaseEntry.setSupplier(supplier);
//				System.out.println(purchaseEntry.);
				
				try {
					LocalDate localDate = purchDatePick.getValue();
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
					Date utilDate = new Date(sd.parse(localDate.toString()).getTime());
					purchaseEntry.setPurchaseDate(utilDate);
//					System.out.println(utilDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				purchaseEntry.setSubTotal(Double.parseDouble(grTotalTxt.getText()));
				purchaseEntry.setTotal(Double.parseDouble(grTotalTxt.getText()));
				purchaseEntry.setPaymentType(payCombo.getValue());
//				
				PaymentMode paymentMode= new PaymentMode();
				purchaseEntry.setPaymentMode(paymentMode);
					if(cashCheck.isSelected()) {
						purchaseEntry.getPaymentMode().setCashMode(cashCheck.getText());
						if(!cashAmtTxt.getText().isEmpty()) {
							purchaseEntry.getPaymentMode().setCashAmount(Double.parseDouble(cashAmtTxt.getText()));
						}
						else {
							purchaseEntry.getPaymentMode().setCashAmount(Double.parseDouble(grTotalTxt.getText()));
						}
					}
					if(bankCheck.isSelected()) {
						purchaseEntry.getPaymentMode().setBankMode(bankCheck.getText());
						purchaseEntry.getPaymentMode().setBankName(bankTxt.getText());
						purchaseEntry.getPaymentMode().setChequeNo(cheqTxt.getText());
						
						if(!bankAmtTxt.getText().isEmpty()) {
							purchaseEntry.getPaymentMode().setBankAmount(Double.parseDouble(bankAmtTxt.getText()));
						}
						else {
							purchaseEntry.getPaymentMode().setBankAmount(Double.parseDouble(grTotalTxt.getText()));
						}
					}
					if(cardCheck.isSelected()) {
						purchaseEntry.getPaymentMode().setCardMode(cardCheck.getText());
						purchaseEntry.getPaymentMode().setTransId(transId.getText());
						if(!cardAmtTxt.getText().isEmpty()) {
							purchaseEntry.getPaymentMode().setCardAmount(Double.parseDouble(cardAmtTxt.getText()));
						}
						else {
							purchaseEntry.getPaymentMode().setCardAmount(Double.parseDouble(grTotalTxt.getText()));
						}
						
					}
					if(voucherCheck.isSelected()) {
						purchaseEntry.getPaymentMode().setVoucherMode(voucherCheck.getText());
						purchaseEntry.getPaymentMode().setVoucherWalletCode(voucherTypeTxt.getText());
						if(!vouchAmtTxt.getText().isEmpty()) {
							purchaseEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(vouchAmtTxt.getText()));
						}
						else {
							purchaseEntry.getPaymentMode().setVoucherAmt(Double.parseDouble(grTotalTxt.getText()));
						}
						
					}
				result = purchaseDAO.updatePurchaseEntry(purchaseEntry, prodData);
				if (result) {
					// generateInvoice.createPDF("/docs/purchaseInvoice.pdf",purchaseEntry,prodData);
					Alert alert = new Alert(AlertType.INFORMATION, "You have recently updated a purchase entry");
					alert.setTitle("Success Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
					Main.dialog.close();
					showPurchaseRecord(anchorPane);
					
					Main.purchaseView.setItems(purchSortedData);
					Main.purchaseView.requestFocus();
					Main.purchaseView.getSelectionModel().selectLast();
					Main.purchaseView.getFocusModel().focusNext();

					Main.purchaseView.setMinSize(900, 500);
					anchorPane.getChildren().set(0, Main.purchaseView);
					if(!itemList.isEmpty()) {
						try {
//							Thread.sleep(10000);
							barcodePrinter.generateBarcode(itemList);
						} catch (ClassNotFoundException | JRException | SQLException | IOException  e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR,
							"Error while updating data..!Please check database connection");
					alert.setTitle("Error Message");
					alert.setHeaderText("HI");
					alert.showAndWait();

				}
				
				
				clearControls();
				clearProductData();
				// partPayTxt.clear();
				// clearLabels();

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
							supplierCombo.requestFocus();
					}
				});
				
		gp.setAlignment(Pos.CENTER);
		return gp;

	}
	public GridPane returnPurchaseGoods(PurchaseEntry purchaseEntry,int index,AnchorPane anchorPane){
		
		
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
		
		HBox supplierHB= new HBox();
		supplierHB.setMinWidth(500);
//		LocalDate ld=LocalDate.parse(purchaseEntry.getPurchaseDate().toString());
		JFXTextField purchDatePick= new JFXTextField();
		purchDatePick.setLabelFloat(true);
		purchDatePick.setPromptText("Purchase Date");
		purchDatePick.setMinWidth(100);
		purchDatePick.setText(purchaseEntry.getPurchaseDate().toString());
		purchDatePick.setEditable(false);
		purchDatePick.setStyle("-fx-font-size:14");
		
		JFXTextField purchInvoiceTxt= new JFXTextField();
		purchInvoiceTxt.setLabelFloat(true);
		purchInvoiceTxt.setPromptText("Invoice Number");
		purchInvoiceTxt.setMinWidth(100);
		purchInvoiceTxt.setEditable(false);
		purchInvoiceTxt.setText(purchaseEntry.getInvoiceNo());
		purchInvoiceTxt.setStyle("-fx-font-size:14");
		
		JFXTextField supplierCombo= new JFXTextField();
		supplierCombo.setLabelFloat(true);
		supplierCombo.setPromptText("Supplier Name");
		supplierCombo.setMinWidth(150);
		supplierCombo.setEditable(false);
		supplierCombo.setText(purchaseEntry.getSupplierName());
		supplierCombo.setStyle("-fx-font-size:14");
		
		JFXTextField stateCombo= new JFXTextField();
		stateCombo.setLabelFloat(true);
		stateCombo.setEditable(false);
		stateCombo.setPromptText("State");
		stateCombo.setText(purchaseEntry.getSupplier().getGstState());
		stateCombo.setStyle("-fx-font-size:14");
		
		JFXTextField contactTxt= new JFXTextField();
		contactTxt.setLabelFloat(true);
		contactTxt.setPromptText("Contact No");
		contactTxt.setText(purchaseEntry.getSupplier().getContactNO());
		contactTxt.setEditable(false);
		contactTxt.setStyle("-fx-font-size:14");
		
		JFXTextField gstTxt= new JFXTextField();
		gstTxt.setLabelFloat(true);
		gstTxt.setPromptText("GSTIN");
		gstTxt.setText(purchaseEntry.getSupplier().getVatTinNo());
		gstTxt.setEditable(false);
		gstTxt.setStyle("-fx-font-size:14");
		
		supplierHB.setSpacing(30);
		supplierHB.getChildren().addAll(purchDatePick,purchInvoiceTxt,supplierCombo,stateCombo,contactTxt,gstTxt);
		GridPane.setMargin(supplierHB, new Insets(0,-100,0,50));
		gp.add(supplierHB, 0, 2);
		
		
		ObservableList<PurchaseProduct> prodData = FXCollections.observableArrayList();
//		System.out.println("HI");
		List<PurchaseProduct> purchaseList = purchaseDAO.getProductDetails(purchaseEntry);
		List<PurchaseReturn> returnList= purchaseDAO.getReturnDetails(purchaseEntry);
		
//		System.out.println("size"+purchaseList.size());
		for(PurchaseProduct pp:purchaseList) {
			String rateString=BigDecimal.valueOf(pp.getRate())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();
			String discString=BigDecimal.valueOf(pp.getDiscount())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();
			
			String subTotalString=BigDecimal.valueOf(pp.getSubTotal())
					.setScale(2,RoundingMode.HALF_UP)
					.toPlainString();
			
			String totalString=BigDecimal.valueOf(pp.getAmount())
					.setScale(2, RoundingMode.HALF_UP)
					.toPlainString();
			String quanString=String.valueOf(pp.getQuantity());
			
			double gst=pp.getSubTotal()*(pp.getProduct().getGst()/100);
			double cess=pp.getSubTotal()*(pp.getProduct().getCess()/100);
			
			if(stateCombo.getText().equals("Maharashtra")) {
				String cgst=String.format("%.2f", (gst/2))+"\n("+(pp.getProduct().getGst()/2)+"%)";
				String sgst=String.format("%.2f", (gst/2))+"\n("+(pp.getProduct().getGst()/2)+"%)";
//				System.out.println(pp.getProduct().getId());
				prodData.add(new PurchaseProduct(pp.getProd_name(),pp.getId(),pp.getProduct().getHsnNo(),pp.getQuantity(),pp.getUnit(),pp.getRate(),pp.getDiscount(),pp.getAmount(),pp.getSubTotal(),cgst,sgst,"","",pp.getProduct(),quanString,rateString,discString,subTotalString,totalString));
			}else {
					String igst=String.format("%.2f", gst)+"\n("+(pp.getProduct().getGst())+"%)";
					String cessTxt=String.format("%.2f", cess)+"\n("+pp.getProduct().getCess()+"%)";
					prodData.add(new PurchaseProduct(pp.getProd_name(),pp.getId(),pp.getProduct().getHsnNo(),pp.getQuantity(),pp.getUnit(),pp.getRate(),pp.getDiscount(),pp.getAmount(),pp.getSubTotal(),"","",igst,cessTxt,pp.getProduct(),quanString,rateString,discString,subTotalString,totalString));
				}
		}
		
		
		ObservableList<PurchaseReturn> returnData=FXCollections.observableArrayList();
		TableView<PurchaseProduct> prodView = new TableView<PurchaseProduct>();
		prodView.setMaxSize(1100, 200);
		prodView.setEditable(true);
		TableColumn<PurchaseProduct, String> prodName = new TableColumn<PurchaseProduct, String>("Product\n"+" Name");
		prodName.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
		prodName.setMinWidth(100);
		TableColumn<PurchaseProduct, String> groupCol= new TableColumn<>("Product\n"+"Group");
		groupCol.setCellValueFactory(new PropertyValueFactory<>("group"));
//		groupCol.setPrefWidth(60);
		TableColumn<PurchaseProduct, String> subGroupCol= new TableColumn<PurchaseProduct, String>("Sub\n"+"Group");
		subGroupCol.setCellValueFactory(new PropertyValueFactory<>("subGroup"));
//		subGroupCol.setPrefWidth(60);
		TableColumn<PurchaseProduct, Long> hsnCol = new TableColumn<PurchaseProduct, Long>("HSN/\n"+"SAC");
		hsnCol.setCellValueFactory(new PropertyValueFactory<>("hsnNo"));
		hsnCol.setPrefWidth(60);
		TableColumn<PurchaseProduct, String> unitCol = new TableColumn<PurchaseProduct, String>("Unit");
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
//		unitCol.setPrefWidth(50);
		TableColumn<PurchaseProduct, String> quantityCol = new TableColumn<PurchaseProduct, String>("Qty.");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityString"));
//		quantityCol.setCellFactory(new Callback<TableColumn<PurchaseProduct,String>, TableCell<PurchaseProduct,String>>() {
//
//			@Override
//			public TableCell<PurchaseProduct, String> call(TableColumn<PurchaseProduct, String> param) {
//				// TODO Auto-generated method stub
//				return new TextFieldTableCell<PurchaseProduct,String>() {
//					@Override
//					public void updateItem(String item,boolean empty) {
//						super.updateItem(item, empty);
//						TableRow row= getTableRow();
//						if(row!=null) {
//							PurchaseProduct pp=(PurchaseProduct)row.getItem();
//							if(pp!=null) {
//								
//							for(PurchaseReturn pr:returnList) {
//								
//								if(pp.getId()==pr.getId()) {
//									
//								if(pp.getQuantity()==pr.getReturnQuantity()) {
//									System.out.println("ID"+pp.getId());
//									setEditable(false);
//								}
//								else {
//									setEditable(true);
//								}
//								}
//							
//						}
//						}
//					}
//				}
//				};
//			}
//			
//		});
//		
//		quantityCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<PurchaseProduct,String>>() {
//			
//			@Override
//			public void handle(CellEditEvent<PurchaseProduct, String> event) {
//				// TODO Auto-generated method stub
////				System.out.println(((PurchaseProduct)event.getTableView().getItems().get(event.getTablePosition().getRow())));
//				((PurchaseProduct)event.getTableView().getItems().get(event.getTablePosition().getRow())).setQuantity(Integer.parseInt(event.getNewValue()));
//				
//				
//			}
//		});
		quantityCol.setPrefWidth(50);
		TableColumn<PurchaseProduct, Double> rateCol = new TableColumn<PurchaseProduct, Double>("Rate");
		rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
//		rateCol.setPrefWidth(50);
		TableColumn<PurchaseProduct, Double> discCol = new TableColumn<PurchaseProduct, Double>("Disc.\n"+" Rs.");
		discCol.setCellValueFactory(new PropertyValueFactory<>("discString"));
		discCol.setPrefWidth(50);
		TableColumn<PurchaseProduct, SalesProduct> taxableCol = new TableColumn<PurchaseProduct, SalesProduct>(
				"Taxable\n"+"Amount");
		taxableCol.setPrefWidth(100);
		taxableCol.setCellValueFactory(new PropertyValueFactory<>("subTotalString"));
		TableColumn<PurchaseProduct, PurchaseProduct> cgstCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"CGST");
		cgstCol.setPrefWidth(60);
		cgstCol.setCellValueFactory(new PropertyValueFactory<>("cgst"));
		TableColumn<PurchaseProduct, PurchaseProduct> sgstCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"SGST");
		sgstCol.setCellValueFactory(new PropertyValueFactory<>("sgst"));
		sgstCol.setPrefWidth(60);
		TableColumn<PurchaseProduct, PurchaseProduct> igstCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"IGST");
		igstCol.setPrefWidth(60);
		igstCol.setCellValueFactory(new PropertyValueFactory<>("igst"));
		TableColumn<PurchaseProduct, PurchaseProduct> cessCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"Cess");
		cessCol.setCellValueFactory(new PropertyValueFactory<>("cess"));
		cessCol.setPrefWidth(60);
		TableColumn<PurchaseProduct, Double> totalCol = new TableColumn<PurchaseProduct, Double>("Total");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
		totalCol.setPrefWidth(100);
		TableColumn<PurchaseProduct, PurchaseProduct> actionCol = new TableColumn<PurchaseProduct, PurchaseProduct>(
				"Action");
		actionCol.setCellValueFactory(e -> new ReadOnlyObjectWrapper<>(e.getValue()));
		actionCol.setCellFactory(e -> new TableCell<PurchaseProduct, PurchaseProduct>() {
			// Button deleteBtn = new Button("Delete");

			@Override
			protected void updateItem(PurchaseProduct purchaseProduct, boolean empty) {
			if(purchaseProduct==null) {
				setGraphic(null);
				return;
			}
			for(PurchaseReturn pr:returnList) {
				if(purchaseProduct.getId()==pr.getPurchaseProduct().getId()) {
					if(purchaseProduct.getQuantity()==pr.getReturnQuantity()) {
						setGraphic(null);
						return;
					}
				}
			}
			JFXCheckBox returnCheck= new JFXCheckBox();
			setGraphic(returnCheck);
			returnCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					// TODO Auto-generated method stub
					if(newValue) {
						returnData.add(new PurchaseReturn(purchaseProduct,purchaseProduct.getQuantity(),purchaseProduct.getProduct()));
						grandTotal = 0;
						if (prodData.size() == 0)
							grTotalTxt.setText("0.0");
						else {
							for (PurchaseProduct p : prodData) {
								grandTotal = grandTotal + p.getAmount();
							}
							grandTotal=grandTotal-purchaseProduct.getAmount();
							grTotalTxt.setText(String.format("%.2f", grandTotal));
						}
					}else {
						returnData.remove(new PurchaseReturn(purchaseProduct,purchaseProduct.getQuantity(),purchaseProduct.getProduct()));
						grandTotal=0;
						for(PurchaseProduct p:prodData) {
							grandTotal=grandTotal+p.getAmount();
						}
						grTotalTxt.setText(String.format("%.2f", grandTotal));
					}
				}
			});
			}
		});
		
		TableColumn<PurchaseProduct,PurchaseProduct> statusCol= new TableColumn<PurchaseProduct, PurchaseProduct>("Status");
		statusCol.setCellValueFactory(e-> new ReadOnlyObjectWrapper<>(e.getValue()));
		statusCol.setCellFactory(e->new TableCell<PurchaseProduct,PurchaseProduct>(){

			@Override
			protected void updateItem(PurchaseProduct purchaseProduct, boolean empty) {
			if(purchaseProduct==null) {
				setGraphic(null);
				setText(null);
				return;
			}
			
			for(PurchaseReturn pr:returnList) {
				if(purchaseProduct.getId()==pr.getPurchaseProduct().getId()) {
					setText("Returned ("+pr.getReturnQuantity()+")");
				}
				
			}
			
			
			
			}
		});

		prodView.getColumns().addAll(actionCol,prodName, groupCol,subGroupCol,hsnCol,unitCol, quantityCol, rateCol, discCol, taxableCol, cgstCol, sgstCol,
				igstCol, cessCol, totalCol,statusCol);
		prodView.setItems(prodData);
		
		GridPane.setMargin(prodView, new Insets(0, -100, 0, 50));
		gp.add(prodView, 0, 8, 11, 1);
		
		grTotalTxt.setLabelFloat(true);
		grTotalTxt.setPromptText("Grand Total");
		grTotalTxt.setStyle("-fx-font-size: 20px;-fx-font-weight:bold");
		grTotalTxt.setEditable(false);
		grTotalTxt.setFocusColor(Color.TRANSPARENT);
		grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
		grTotalTxt.setText(String.valueOf(purchaseEntry.getTotal()));
		GridPane.setMargin(grTotalTxt, new Insets(20,-200,0,100));
		gp.add(grTotalTxt, 0, 10);
		
		
		JFXButton submitBtn = new JFXButton(" Submit ");
		GridPane.setMargin(submitBtn,new Insets(20,-200,0,100));
		gp.add(submitBtn, 0,11);
		
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
//				PurchaseReturn purchaseReturn= new PurchaseReturn();
//				purchaseReturn.setPurchaseEntry(purchaseEntry);
			if(returnData.size()==0) {
				errorTip.setText("Porduct already returned");
				submitBtn.setTooltip(errorTip);
				errorTip.show(submitBtn,200,100);
				return;
			}
				
				result=purchaseDAO.returnPurchase(purchaseEntry,returnData);
				if(result) {
				Alert alert = new Alert(AlertType.INFORMATION, "Purchase Return Generated");
				alert.setTitle("Success Message");
				alert.setHeaderText("HI");
				alert.showAndWait();
				Main.dialog.close();
				showPurchaseRecord(anchorPane);
				
				Main.purchaseView.setItems(purchSortedData);
				Main.purchaseView.requestFocus();
				Main.purchaseView.getSelectionModel().selectLast();
				Main.purchaseView.getFocusModel().focusNext();

				Main.purchaseView.setMinSize(900, 500);
				anchorPane.getChildren().set(0, Main.purchaseView);
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

	
	
	public boolean validateProductControls() {
		// boolean valid=true;

		//
		if (quantityTxt.getText().isEmpty() || !(quantityTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))) {
			// quantityErrLbl.setMinWidth(150);
			// quantityErrLbl.setText("Quantity must be a positive number!");
			errorTip.setText("Quantity must be a positive number!");
			quantityTxt.setTooltip(errorTip);
			errorTip.show(quantityTxt, 400, 150);
			return false;
		}
		if (rateTxt.getText().isEmpty() || !(rateTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))) {
			errorTip.setText("Rate must be a positive number!");
			rateTxt.setTooltip(errorTip);
			errorTip.show(rateTxt, 450, 150);
			// rateErrLbl.setMinWidth(150);
			// rateErrLbl.setText("Rate must be a positive number!");
			return false;
		}
		if (discTxt.getText().isEmpty() || !(discTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))) {
			errorTip.setText("Discount must be 0 or greater than 0!");
			discTxt.setTooltip(errorTip);
			errorTip.show(discTxt, 500, 150);
			// discErrLbl.setMinWidth(150);
			// discErrLbl.setText("Discount must be 0 or greater than 0!");
			return false;
		}
		// if(gstRsTxt.getText().isEmpty()||!(gstRsTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
		//// gstErrLbl.setMinWidth(150);
		//// gstErrLbl.setText("GST must be 0 or greater than 0!");
		// return false;
		//// return;
		// }

		// if(!(totalTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
		// totErrLbl.setText("Total can not be negative");
		// totErrLbl.setMinWidth(150);
		// valid=false;
		// }

		// if(gstRsTxt.getText().equals("0")){
		// gstErrLbl.setMinWidth(100);
		// gstErrLbl.setText("GST must be greater than or equal to 0!");
		// valid=false;
		// }

		return true;
	}

	public void clearControls() {
		// prodTxt.getItems().clear();
		// quantityTxt.setText("0");
		// rateTxt.setText("0");
		// discTxt.setText("0");
		// gstRsTxt.setText("0");
		// totalTxt.setText("0.0");
		purchInvoicTxt.clear();
		purchDatePick.setValue(null);
		supplierCombo.getItems().clear();
		materialCombo.getItems().clear();
		unitCombo.getItems().clear();
		payCombo.getItems().clear();
//		payModeCombo.getItems().clear();
		partPayTxt.clear();
		bankTxt.clear();
		cheqTxt.clear();
		cardCheck.setSelected(false);
		cashCheck.setSelected(false);
		bankCheck.setSelected(false);
		voucherCheck.setSelected(false);
		transId.clear();
		cashAmtTxt.clear();
		bankAmtTxt.clear();
		cardAmtTxt.clear();
		vouchAmtTxt.clear();
		voucherTypeTxt.clear();
	}

	
	public boolean validateOnAddEdit() {
		// boolean valid = true;
		if (prodTxt.getValue() == null) {
			// prodErrLbl.setMinWidth(35);
			// prodErrLbl.setText("Please select Product");
			errorTip.setText("Please enter/select Product");
			prodTxt.setTooltip(errorTip);
			errorTip.show(prodTxt, 100, 150);
			prodTxt.requestFocus();
			return false;
		}
		//
		if (unitCombo.getValue() == null||unitCombo.getValue().equals("")) {
			// unitErrorLbl.setMinWidth(35);
			// unitErrorLbl.s/etText("Please select Unit");
			errorTip.setText("Please enter/select Unit");
			unitCombo.setTooltip(errorTip);
			errorTip.show(unitCombo, 500, 150);
			unitCombo.requestFocus();
			return false;

		}
		
		if(!hsnTxt.getText().isEmpty()) {
			if(!hsnTxt.getText().matches("^[0-9]*\\.?[0-9]*$")) {
				errorTip.setText("Please enter valid HSN/SAC");
				hsnTxt.setTooltip(errorTip);
				errorTip.show(hsnTxt, 400, 150);
				hsnTxt.requestFocus();
			}
		}
		if (totalTxt.getText().isEmpty() || Double.parseDouble(totalTxt.getText()) == 0) {
			errorTip.setText("Total cannot be zero");
			totalTxt.setTooltip(errorTip);
			errorTip.show(totalTxt, 1000, 150);
			// totErrLbl.setText("Total cannot be zero");
			// totErrLbl.setMinWidth(150);
			return false;
		}
		if (!(totalTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))) {
			errorTip.setText("Total cannot be negative");
			totalTxt.setTooltip(errorTip);
			errorTip.show(totalTxt, 1000, 150);
			// totErrLbl.setText("Total can not be negative");
			// totErrLbl.setMinWidth(150);
			return false;
		}
		return true;
	}

	public void clearProductData() {
		
		unitCombo.setValue(null);
		hsnTxt.clear();
		quantityTxt.setText("0");
		rateTxt.setText("0");
		sellPriceTxt.clear();
		discTxt.setText("0");
		gstRsTxt.setValue("0.0");
		totalTxt.setText("0.0");
		groupTxt.setValue(null);
		subGroupTxt.setValue(null);
		prodTxt.setValue(null);
		taxableAmt=0;
		gstAmt=0;
		cessAmt=0;
		gstRate=0;
		
	}

	public boolean validateonSubmitButton() {
		// boolean valid = true;
		double payTotal=0;
		if (supplierCombo.getValue() == null) {
			errorTip.setText("Please Select Supplier");
			supplierCombo.setTooltip(errorTip);
			errorTip.show(supplierCombo,400,100);
			return false;
		}
		if(!supplierCombo.getValue().matches("^[a-zA-Z0-9 ]+$")) {
			errorTip.setText("Supplier Name must be a string");
			supplierCombo.setTooltip(errorTip);
			errorTip.show(supplierCombo,400,100);
			return false;
		}

		else if (purchDatePick.getValue() == null) {
			errorTip.setText("Please select purchase date!");
			purchDatePick.setTooltip(errorTip);
			errorTip.show(purchDatePick, 300, 100);
			// dateErrorLbl.setText("Please select purchase date");
			// dateErrorLbl.setMinWidth(150);
			return false;
		}

		if (Double.parseDouble(grTotalTxt.getText()) == 0) {
			// grTotErrLbl.setText("Grand Total cannot be zero");
			// grTotErrLbl.setMinWidth(100);
			errorTip.setText("Grand Total cannot be zero!");
			grTotalTxt.setTooltip(errorTip);
			errorTip.show(grTotalTxt, 700, 500);
			return false;
		}
		
//		if (payCombo.getValue() == null) {
//			// payErrLbl.setText("Please select payment type!");
//			// payErrLbl.setMinWidth(150);
//			errorTip.setText("Please select payment type!");
//			payCombo.setTooltip(errorTip);
//			errorTip.show(payCombo, 750, 600);
//			return false;
//		}
//
//		if (payCombo.getValue() != null) {
//			if (payCombo.getValue().equals("Part Payment")) {
//				if (partPayTxt.getText().isEmpty() || Double.parseDouble(partPayTxt.getText()) == 0) {
//					// partPayErrLbl.setText("Part payment can not be blank");
//					// partPayErrLbl.setMinWidth(100);
//					errorTip.setText("Part payment can not be blank!");
//					partPayTxt.setTooltip(errorTip);
//					errorTip.show(partPayTxt, 850, 500);
//					return false;
//				}
//
//			}
//		}
//		if (payCombo.getValue() != null) {
//			if (payCombo.getValue().equals("Part Payment")) {
//				if (!partPayTxt.getText().matches("^[0-9]*\\.?[0-9]*$")) {
//					// partPayErrLbl.setText("Part payment can not be negative");
//					// partPayErrLbl.setMinWidth(100);
//					errorTip.setText("Part payment can not be negative!");
//					partPayTxt.setTooltip(errorTip);
//					errorTip.show(partPayTxt, 850, 500);
//					return false;
//				}
//			}
//		}
//
//		if (payCombo.getValue().equals("Part Payment")) {
//			// System.out.println("Reached inside part payment validations");
//			if ((Double.parseDouble(partPayTxt.getText()) >= Double.parseDouble(grTotalTxt.getText()))) {
//				errorTip.setText("Part payment must be smaller than grand total!");
//				partPayTxt.setTooltip(errorTip);
//				errorTip.show(partPayTxt, 850, 500);
//				return false;
//			}
//		}
//		
//		if(!payCombo.getValue().equals("UnPaid")) {
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
						
						
						
//					}else if(payCombo.getValue().equals("Part Payment")) {
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
////						 System.out.println("Part Payment");
//						 if(payTotal!=Double.parseDouble(partPayTxt.getText())) {
//							 errorTip.setText("Payment amounts entered must be equal to Part Payment");
//								grTotalTxt.setTooltip(errorTip);
//								errorTip.show(grTotalTxt,600,500);
//								return false;
//						 }
//					 }
				 }
//			 }
//		if (!payCombo.getValue().equals("UnPaid")) {
//			if (payModeCombo.getValue() == null) {
//				// payModeErrLbl.setText("Please select payment mode!");
//				// payModeErrLbl.setMinWidth(150);
//				errorTip.setText("Please select payment mode!");
//				payModeCombo.setTooltip(errorTip);
//				errorTip.show(payModeCombo, 750, 650);
//				return false;
//			}
//		}
//		
//		if(!payCombo.getValue().equals("UnPaid")) {
//			if(!payModeCombo.getValue().equals("Cash")) {
//				if(bankTxt.getText().isEmpty()) {
//					errorTip.setText("Please enter bank name!");
//					bankTxt.setTooltip(errorTip);
//					errorTip.show(bankTxt, 850, 680);
//					return false;
//				}
//				if(!bankTxt.getText().matches("^[a-zA-Z ]+$")) {
//					errorTip.setText("Please enter valid bank name!");
//					bankTxt.setTooltip(errorTip);
//					errorTip.show(bankTxt, 850, 680);
//					return false;
//				}
//				 if(cheqTxt.getText().isEmpty()) {
//					errorTip.setText("Please enter Cheque No/TransactionId/Status!");
//					cheqTxt.setTooltip(errorTip);
//					errorTip.show(cheqTxt, 900, 680);
//					return false;
//				}
//			}
//		}

		// if(!payModeCombo.getValue().equals("Cash")) {
		//
		// }
		return true;
	}

	public boolean validateonUpdateButton() {
//		boolean valid = true;
		if (supplierCombo.getValue() == null) {
			errorTip.setText("Please Select Supplier");
			supplierCombo.setTooltip(errorTip);
			errorTip.show(supplierCombo,300,150);
			return false;
		}

		else if (purchDatePick.getValue() == null) {
			errorTip.setText("Please select purchase date!");
			purchDatePick.setTooltip(errorTip);
			errorTip.show(purchDatePick, 500, 150);
			// dateErrorLbl.setText("Please select purchase date");
			// dateErrorLbl.setMinWidth(150);
			return false;
		}

		if (Double.parseDouble(grTotalTxt.getText()) == 0) {
			// grTotErrLbl.setText("Grand Total cannot be zero");
			// grTotErrLbl.setMinWidth(100);
			errorTip.setText("Grand Total cannot be zero!");
			grTotalTxt.setTooltip(errorTip);
			errorTip.show(grTotalTxt, 700, 500);
			return false;
		}

		return true;
	}

	public boolean validateSupplier() {
		
		if(supplierTxt.getText().isEmpty()) {
			errorTip.setText("Please enter supplier name!");
			supplierTxt.setTooltip(errorTip);
			errorTip.show(supplierTxt, 320, 120);
			// supplierTxt.requestFocus();
			return false;
		}
		if (!(supplierTxt.getText().matches("^[a-zA-Z ]+$"))) {
				// suppErrLbl.setText("Please enter correct supplier name!");
				// suppValidator.setMessage("Please enter correct supplier name");
				errorTip.setText("Please enter correct supplier name!");
				supplierTxt.setTooltip(errorTip);
				errorTip.show(supplierTxt, 320, 120);
				// supplierTxt.requestFocus();
				return false;
			}
		

		if (ownerTxt.getText().isEmpty()) {
			errorTip.setText("Please enter correct owner name!");
			ownerTxt.setTooltip(errorTip);
			errorTip.show(ownerTxt, 600,120);
			return false;
		}
		
			if (!(ownerTxt.getText().matches("^[a-zA-Z ]+$"))) {
				errorTip.setText("Please enter correct owner name!");
				ownerTxt.setTooltip(errorTip);
				errorTip.show(ownerTxt, 600, 120);
				// ownErrLbl.setText("Please input correct owner name!");
				// ownValidator.setMessage("Please input correct owner name");
				// ownerTxt.requestFocus();
				return false;
			}
		

		String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
		String regex1 = "^((\\+){0,1}91(\\s){0,1}(\\-){0,1}(\\s){0,1}){0,1}[789][0-9](\\s){0,1}(\\-){0,1}(\\s){0,1}[1-9]{1}[0-9]{7}$";
		
		if(contactTxt.getText().isEmpty()) {
			errorTip.setText("Please input contact number!");
			contactTxt.setTooltip(errorTip);
			contactTxt.requestFocus();
			errorTip.show(contactTxt,320,200);
			return false;
		}
		if ((!(contactTxt.getText().matches(regex)) || (!(contactTxt.getText().matches(regex1))))) {

			errorTip.setText("Please input valid contact number!");
			contactTxt.setTooltip(errorTip);
			contactTxt.requestFocus();
			errorTip.show(contactTxt,320,200);
			return false;
		}

	
		if (!(gstTxt.getText().isEmpty())) {
			if(gstTxt.getText().length()<15) {
			if (!(gstTxt.getText().matches("/^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/"))) {

				errorTip.setText("Please enter correct GSTIN");
				gstTxt.setTooltip(errorTip);
				errorTip.show(gstTxt,600,200);
				gstTxt.requestFocus();

				return false;

			}
		}
		}

//		if (!(addressTxt.getText().isEmpty())) {
//			if ((addressTxt.getText().matches("\\d+\\s+(([a-zA-Z])+|([a-zA-Z]+\\s+[a-zA-Z]+))\\s+[a-zA-Z]*"))) {
//				errorTip.setText("Please enter correct address!");
//				addressTxt.setTooltip(errorTip);
//				errorTip.show(addressTxt, 320, 350);
//				;
//				return false;
//
//			}
//		}

		return true;
	}

	public void enableAllControls() {
		prodTxt.setDisable(false);
		supplierCombo.setDisable(false);
		// supplierCombo.getStyleClass().remove("opacity");
		supplierCombo.setStyle("");
		materialCombo.setDisable(false);
		purchDatePick.setDisable(false);
		purchInvoicTxt.setEditable(true);
		// purchInvoicTxt.setFocusColor(color);
		hsnTxt.setDisable(false);
		quantityTxt.setDisable(false);
		unitCombo.setDisable(false);
		rateTxt.setDisable(false);
		payCombo.setDisable(false);
//		payModeCombo.setDisable(false);

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
	
	public void submitSupplierFunc(AnchorPane anchorPane) {
		boolean result;
		if (!validateSupplier()) {

			// System.out.println(validateSupplier());

			// gp.add(suppErrLbl, 0, 2);
			// gp.add(ownErrLbl, 1, 2);
			// gp.add(contErrLbl, 0, 4);
			// gp.add(addErrLbl, 0, 6);
			return;
		}

		Supplier supplier = new Supplier();
		supplier.setSupplierName(supplierTxt.getText());
		supplier.setOwnerName(ownerTxt.getText());
		supplier.setContactNO(contactTxt.getText());
		supplier.setVatTinNo(gstTxt.getText());
		supplier.setAddress(addressTxt.getText());
		supplier.setGstState(stateCombo.getValue());
		// supplier.setEmail(emailTxt.getText());
		result = purchaseDAO.createSupplier(supplier);
		if (result) {
			Alert alert = new Alert(AlertType.INFORMATION, "You have recently added a new Supplier");
			alert.setTitle("Success Message");
			alert.setHeaderText("HI");
			alert.showAndWait();
			showSupplier(anchorPane);
			Main.dialog.close();
			Main.supplierView.setItems(sortedData);
			Main.supplierView.requestFocus();
			Main.supplierView.getSelectionModel().selectLast();
			Main.supplierView.getFocusModel().focusNext();

			Main.supplierView.setMinSize(900, 500);
			anchorPane.getChildren().set(0, Main.supplierView);
		}

		else {
			Alert alert = new Alert(AlertType.INFORMATION,
					"Error while inserting data..!Please check database connection");
			alert.setTitle("Error Message");
			alert.setHeaderText("HI");
			alert.showAndWait();
		}
	}
	
	public void updateSupplierFunc(Supplier supplier,int index,AnchorPane anchorPane) {
		boolean result;
		
		if (!validateSupplier()) {

			// System.out.println(validateSupplier());

			// gp.add(suppErrLbl, 0, 2);
			// gp.add(ownErrLbl, 1, 2);
			// gp.add(contErrLbl, 0, 4);
			// gp.add(addErrLbl, 0, 6);
			return;
		}

		Supplier suppl = new Supplier();
		suppl.setSupplier_id(supplier.getSupplier_id());
		suppl.setSupplierName(supplierTxt.getText());
		suppl.setOwnerName(ownerTxt.getText());
		suppl.setContactNO(contactTxt.getText());
		suppl.setVatTinNo(gstTxt.getText());
		suppl.setAddress(addressTxt.getText());
		suppl.setGstState(stateCombo.getValue());
		result = purchaseDAO.updateSupplier(suppl);
		if (result) {

			Alert alert = new Alert(AlertType.INFORMATION, "You have recently updated a Supplier");
			alert.setTitle("Success Message");
			alert.setHeaderText("HI");
			alert.showAndWait();
			showSupplier(anchorPane);
			Main.dialog.close();
			Main.supplierView.setItems(sortedData);
			Main.supplierView.requestFocus();
			Main.supplierView.getSelectionModel().select(supplier);
			Main.supplierView.getFocusModel().focus(index);

			Main.supplierView.setMinSize(900, 500);
			anchorPane.getChildren().set(0, Main.supplierView);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION,
					"Error while updating data..!Please check database connection");
			alert.setTitle("Error Message");
			alert.setHeaderText("HI");
			alert.showAndWait();
		}
	}
	
	public ObservableList<String> fillStateCombo(){
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
			"Telangana",
			"Tripura",
			"Uttarkhand", 
			"Uttar Pradesh",
			"West Bengal");
		
	return stateList;
	}
	
	public TableView<PurchaseEntry> PurchaseReportTable( String From, String To){
		purchaseData.clear();

		  TableView<PurchaseEntry> PurchaseReportDate = new TableView<PurchaseEntry>();
		  List<PurchaseEntry> purchaseList=PurchaseDAO.PurchaseReportData(From,To);

		  TableColumn<PurchaseEntry, Date> invDateCol = new TableColumn<PurchaseEntry, Date>("Invoice Date");
		  invDateCol.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));
		  invDateCol.setPrefWidth(200);
		  invDateCol.setStyle("-fx-alignment:center");

		  TableColumn<PurchaseEntry, String> PurInvNoCol = new TableColumn<PurchaseEntry, String>("Purchase Invoice No");
		  PurInvNoCol.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
		  PurInvNoCol.setPrefWidth(200);
		  PurInvNoCol.setStyle("-fx-alignment:center");

		  TableColumn<PurchaseEntry, String> ProductNameCol = new TableColumn<PurchaseEntry, String>("Product Name");
		  ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
		  ProductNameCol.setPrefWidth(300);
		  ProductNameCol.setStyle("-fx-alignment:center");

		  TableColumn<PurchaseEntry, Double> TotalCol = new TableColumn<PurchaseEntry, Double>("Total");
		  TotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
		  TotalCol.setPrefWidth(200);
		  TotalCol.setStyle("-fx-alignment:center");

		  Iterator<PurchaseEntry> itr1 = purchaseList.iterator();
		  //long srNo = 1;
		  while (itr1.hasNext()) {
		   PurchaseEntry p1 = itr1.next();
		   double total=BigDecimal.valueOf(p1.getTotal())
				   .setScale(2,RoundingMode.HALF_UP).doubleValue();
		   purchaseData.add(new PurchaseEntry(p1.getInvoiceDate(), p1.getInvoiceNo(),
		     p1.getProductName(),total));
		  }
		  PurchaseReportDate.getColumns().addAll(invDateCol,PurInvNoCol,ProductNameCol,TotalCol);

		  PurchaseReportDate.setItems(purchaseData);


		  return PurchaseReportDate;

		 }
	/***********
	 * End of********** ********method********* ********Definition
	 **********/
}

/*********
 * End of Class***
 */
