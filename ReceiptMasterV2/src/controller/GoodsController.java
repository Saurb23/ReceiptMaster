package controller;



import model.Category;
import model.Customer;
import model.Product;
import model.PurchaseEntry;
import model.PurchaseProduct;
import model.StockReportBean;
import model.Supplier;
import model.Unit;

import java.awt.image.Raster;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import com.jfoenix.validation.base.ValidatorBase;

import application.Main;
import dao.GoodsDAO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
//import javafx.scene.control.JFXTextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Saurabh Gupta
 *
 */

public class GoodsController {

	GoodsDAO goodsDAO = new GoodsDAO();
	ObservableList<Category> catData = FXCollections.observableArrayList();
	ObservableList<Product> prodData = FXCollections.observableArrayList();
	static ObservableList<StockReportBean> stockDataList = FXCollections.observableArrayList();
//Product Details
    JFXTextField proNamTxt = new JFXTextField();
    JFXTextField barcodeNum= new JFXTextField();

    JFXTextField hsnTxt = new JFXTextField();
    JFXTextField addInfoTxt = new JFXTextField();
    JFXComboBox<String> gstCombo= new JFXComboBox<>();
    JFXComboBox<String> unitCombo= new JFXComboBox<>();
    JFXTextField sellTxt = new JFXTextField();
    JFXComboBox<String> catCombo = new JFXComboBox<String>();

    public  List<String> unitStrings= new ArrayList<>();
//	Category details
	 JFXTextField categoryTxt = new JFXTextField();
//	 RequiredFieldValidator validator = null;

//Raw material Details
	 JFXTextField rawMatTxt = new JFXTextField();

	    JFXTextField rawInfoTxt = new JFXTextField();

	    Tooltip errorTip= new Tooltip();
	    Label catErrLbl = new Label();

//	Unit Details
	 JFXTextField unitTxt = new JFXTextField();
	 JFXTextField unitAddInfoTxt = new JFXTextField();

//Declaration for search functionality
	 public SortedList<Category> catSortedData;
	 public SortedList<Product> prodSortedData;


	 public FilteredList<Category> catFilteredData;
	 public FilteredList<Product> prodFilteredData;


	 public SortedList<Unit> unitSortedData;
	 public FilteredList<Unit> unitFilteredData;

	public GridPane addProduct(AnchorPane anchorPane){
//		Stage secondary = new Stage();
//		secondary.setTitle("Add New Product");
//		VBox vb = new VBox();
//		vb.setPadding(new Insets(20, 40, 50, 40));
//	    vb.setSpacing(5);


//	    Label productLbl = new Label("Product Name");
//	    Label prodCatLbl = new Label("Product Category");
//	    Label addInfoLbl = new Label("Additional Info");
//		catErrLbl.setText("");

		GridPane gp = new GridPane();
		gp.getStyleClass().add("grid");

		gp.setAlignment(Pos.CENTER);
		gp.setHgap(20);
		gp.setVgap(30);
		gp.setGridLinesVisible(false);
		gp.setPadding(new Insets(0,0,0,10));
//		gp.setMouseTransparent(true);
		Label titleLabel = new Label(" Add New Product");
		GridPane.setMargin(titleLabel, new Insets(-20,-50,0,50));
		gp.add(titleLabel, 0, 0);

		proNamTxt.setText("");
		proNamTxt.setPromptText("Product Name *");
		proNamTxt.setLabelFloat(true);
		validateOnFocus(proNamTxt);
//		validator=new RequiredFieldValidator();
//		proNamTxt.getValidators().add(validator);
//		validator.setMessage("Please specify Product");

//		proNamTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				// TODO Auto-generated method stub
//				if(!newValue) {
//					proNamTxt.validate();
//				}
//			}
//		});

		barcodeNum.clear();
		barcodeNum.setPromptText("Barcode Number");
		barcodeNum.setLabelFloat(true);
		validateOnFocus(barcodeNum);

		unitCombo.setMinWidth(150);
		unitCombo.setPromptText("Unit");
		unitCombo.setLabelFloat(true);

//		unitStrings.clear();
//		List<Unit> unitList =goodsDAO.getUnitList();
//
//		for(Unit u:unitList) {
//			unitStrings.add(u.getUnit());
//		}
		unitCombo.getItems().clear();
		unitCombo.getItems().addAll(fillUnitList());

		unitCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
//				errorTip.hide();
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), unitCombo.getValue(), unitCombo.getItems());
				if(s!=null) {
					unitCombo.requestFocus();
					unitCombo.getSelectionModel().select(s);

				}
			}
		});
		unitCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(!newValue) {
					errorTip.hide();
				}

			}
		});
//		unitCombo.getItems().addAll("BAG",
//				"BAGS",
//				"BAILS",
//				"BOTTLES",
//				"BOU",
//				"BOXES",
//				"BUCKLES",
//				"BULK",
//				"BUNCHES",
//				"BUNDLES",
//				"CANS",
//				"CARTONS",
//				"CASES",
//				"CENTIMETER",
//				"CHEST",
//				"COILS",
//				"COLLIES",
//				"CRATES",
//				"CUBIC CENTIMETER",
//				"CUBIC INCHES",
//				"CUBIC METER",
//				"CUBIC METERS",
//				"CYLINDER",
//				"DECAMETER SQUARE",
//				"DOZEN",
//				"DRUMS",
//				"FEET",
//				"FLASKS",
//				"GRAMS",
//				"GREAT BRITAIN TON",
//				"GREAT GROSS",
//				"GROSS",
//				"GROSS YARDS",
//				"HABBUCK",
//				"HANKS",
//				"INCHES	INC\r\n" +
//				"JOTTA	JTA\r\n" +
//				"KILOGRAMS	KGS\r\n" +
//				"KILOLITER	KLR\r\n" +
//				"KILOMETERS	KME\r\n" +
//				"LITERS	LTR\r\n" +
//				"LOGS	LOG\r\n" +
//				"LOTS	LOT\r\n" +
//				"METER	MTR\r\n" +
//				"METRIC TON	MTS\r\n" +
//				"MILLI GRAMS	MGS\r\n" +
//				"MILLI LITRE	MLT\r\n" +
//				"MILLI METER	MMT\r\n" +
//				"NOT CHOSEN	NONE\r\n" +
//				"NUMBERS	NOS\r\n" +
//				"ODDS	ODD\r\n" +
//				"PACKS	PAC\r\n" +
//				"PAILS	PAI\r\n" +
//				"PAIRS	PRS\r\n" +
//				"PALLETS	PLT\r\n" +
//				"PIECES	PCS\r\n" +
//				"POUNDS	LBS\r\n" +
//				"QUINTAL	QTL\r\n" +
//				"REELS	REL\r\n" +
//				"ROLLS	ROL\r\n" +
//				"SETS	SET\r\n" +
//				"SHEETS	SHT\r\n" +
//				"SLABS	SLB\r\n" +
//				"SQUARE FEET	SQF\r\n" +
//				"SQUARE INCHES	SQI\r\n" +
//				"SQUARE CENTIMETERS	SQC\r\n" +
//				"SQUARE METER	SQM\r\n" +
//				"SQUARE YARDS	SQY\r\n" +
//				"STEEL BLOCKS	BLO\r\n" +
//				"TABLES	TBL\r\n" +
//				"TABLETS	TBS\r\n" +
//				"TEN GROSS	TGM\r\n" +
//				"THOUSANDS	THD\r\n" +
//				"TINS	TIN\r\n" +
//				"TOLA	TOL\r\n" +
//				"TRUNK	TRK\r\n" +
//				"TUBES	TUB\r\n" +
//				"UNITS	UNT\r\n" +
//				"US GALLONS	UGS\r\n" +
//				"Vials	VLS\r\n" +
//				"WOODEN CASES	CSK\r\n" +
//				"YARDS");

		hsnTxt.clear();
		hsnTxt.setPromptText("HSN/SAC");
		hsnTxt.setLabelFloat(true);
		validateOnFocus(hsnTxt);

//		catCombo.getItems().clear();
//		catCombo.setPromptText("Select Category *");
//		catCombo.setLabelFloat(true);
//		HBox hb= new HBox();
//		hb.maxWidthProperty().bind(catCombo.maxWidthProperty());
//		validator=new RequiredFieldValidator();


//		validationFacade.setValidators(validator);
//		hb.getChildren().add(catCombo);

		sellTxt.clear();
		sellTxt.setLabelFloat(true);
		sellTxt.setPromptText("Selling Price");

		validateOnFocus(sellTxt);

		gstCombo.getItems().clear();
		gstCombo.setMinWidth(150);
		gstCombo.getItems().addAll("0.0","5.0","12.0","18.0","28.0");
		gstCombo.setPromptText("Tax Rate(%)");
		gstCombo.setLabelFloat(true);
		gstCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(!newValue) {
					errorTip.hide();
				}
			}
		});

		gstCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
//				errorTip.hide();
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), gstCombo.getValue(), gstCombo.getItems());
				if(s!=null) {
					gstCombo.requestFocus();
					gstCombo.getSelectionModel().select(s);

				}
			}
		});

//		addInfoTxt.clear();
//		addInfoTxt.setLabelFloat(true);
//		addInfoTxt.setPromptText("Additional Info");


//	    List<Category> catList =goodsDAO.showCategory();
//
//	    Iterator<Category> itr = catList.iterator();
//	    while(itr.hasNext()){
//	    	catCombo.getItems().add(itr.next().getCategory());
//	    }
//	    catCombo.getItems().addall(catList);
	   JFXButton addBtn = new JFXButton(" Add ");
	   JFXButton clrBtn = new JFXButton("Clear");

	    addBtn.setOnAction(new EventHandler<ActionEvent>() {
	    	boolean result;
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
//				catErrLbl.setText("");
				createProductOnEnter(anchorPane);
			}
		});
	    clrBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				proNamTxt.clear();
//				addInfoTxt.clear();
				gstCombo.setValue(null);
				unitCombo.setValue(null);
				hsnTxt.clear();
				sellTxt.clear();
				barcodeNum.clear();
			}

		});

	    HBox hbox = new HBox();
//		hb1.setPadding(new Insets(20, 50, 50, 200));
		hbox.setSpacing(20);
		hbox.setAlignment(Pos.CENTER);
		addBtn.setMinWidth(100);
		clrBtn.setMinWidth(100);
		hbox.getChildren().addAll(addBtn,clrBtn);
		gp.add(proNamTxt, 0, 1);
		gp.add(barcodeNum, 1, 1);
		gp.add(hsnTxt, 0, 2);
		gp.add(unitCombo, 1, 2);
//		gp.add(catCombo, 1, 2);
		gp.add(sellTxt, 0, 3);
		gp.add(gstCombo, 1, 3);
//		gp.add(addInfoTxt, 0, 4,2,1);
		gp.add(hbox, 0, 5);


//	    gp.getChildren().addAll(proNamTxt,catCombo,addInfoTxt,hbox);
//	    Scene scene =new Scene(vb,500,400);
//		secondary.setAlwaysOnTop(true);
//		secondary.setScene(scene);
//		secondary.show();

	    return gp;
	}

	public GridPane addCategory(AnchorPane anchorPane){

//		Stage secondary = new Stage();
//		secondary.setAlwaysOnTop(true);
//		secondary.setTitle("Add New Category");
//		VBox vb = new VBox();
//		vb.setPadding(new Insets(20, 40, 50, 40));
//	    vb.setSpacing(5);
//	    Label categoryLbl = new Label("Category Name");
		GridPane gp = new GridPane();
		gp.getStyleClass().add("grid");

		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(30);
		gp.setGridLinesVisible(false);
//		gp.setPadding(new Insets(10,10,10,10));

		Label titleLabel = new Label(" Add New Category");
//		GridPane.setMargin(titleLabel, new Insets(20,-500,-10,400));
		gp.add(titleLabel, 0, 0);

//		Label titleLbl = new Label("Add New Category");
//		titleLbl.setTextFill(Color.WHITE);
//		titleLbl.setStyle("-fx-background-color:green; -fx-font-size:20px;");
//		JFXRippler rippler = new JFXRippler(titleLbl);
//		rippler.setMinSize(200, 20);
//		gp.add(rippler, 0, 0,1,1);

		categoryTxt.setPromptText("Category *");
		categoryTxt.setLabelFloat(true);
		categoryTxt.clear();
//		validator=new RequiredFieldValidator();
//		categoryTxt.getValidators().add(validator);
//		validator.setMessage("Please specify category");
		gp.add(categoryTxt, 0, 2);

		categoryTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(!newValue) {
					categoryTxt.validate();
				}
			}

		});
	    JFXButton addBtn = new JFXButton(" Add ");
	    JFXButton clrBtn = new JFXButton(" Clear ");
	    addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				catErrLbl.setText("");
				if(!categoryTxt.validate()||!categoryTxt.getText().matches("^[a-zA-Z]+$")) {
					catErrLbl.setText("Category must be a string");
					gp.add(catErrLbl, 0, 3);
					return;
				}

				Category category = new Category();
				category.setCategory(categoryTxt.getText());
//				System.out.println(categoryTxt.getText());
				try
				{
					goodsDAO.addCategory(category);
					Alert alert = new Alert(AlertType.INFORMATION, "You have recently added a new Category");
					alert.setTitle("Success Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
					 showCategory(anchorPane);
//					categoryView.setMinSize(750, 500);
//					HBox btnHB = new HBox();
//					JFXButton editBtn = new JFXButton("Update");
//					editBtn.setDisable(false);
//					JFXButton addBtn = new JFXButton("Add New");
//					btnHB.getChildren().addAll(addBtn,editBtn);
//					AnchorPane.setBottomAnchor(btnHB,50d);
					Main.categoryView.setItems(catSortedData);
					Main.categoryView.requestFocus();
					Main.categoryView.getSelectionModel().selectLast();
					Main.categoryView.getFocusModel().focusNext();

					Main.categoryView.setMinSize(600, 500);
					anchorPane.getChildren().set(0,Main.categoryView);

//					TableView<Category> catView = showCategory();
//					catView.setPadding(new Insets(50, 500, 100, 500));
//					mainClass.root.setCenter(catView);
//					mainClass.showStage();
				}
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
	    clrBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				categoryTxt.clear();
			}

		});

	    HBox hbox = new HBox();
//		hb1.setPadding(new Insets(20, 50, 50, 200));
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(addBtn,clrBtn);
//	    vb.getChildren().addAll(categoryTxt,hbox);
		gp.add(hbox, 0, 4);
//	    Scene scene =new Scene(vb,200,200);
//		secondary.setAlwaysOnTop(true);
//		secondary.setScene(scene);
//		secondary.show();
	    return gp;
	}

	public TableView<Product> showProduct(AnchorPane anchorPane){
		TableView<Product> prodView = new TableView<Product>();
		prodData.clear();
		TableColumn<Product,String> srNOCol = new TableColumn<>("Sr. No. ");
		srNOCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));
		srNOCol.setMinWidth(80);
		srNOCol.setStyle("-fx-alignment:center");

		TableColumn<Product,String> prodNameCol = new TableColumn<>("Product ");
		prodNameCol.setCellValueFactory(new PropertyValueFactory<>("product_name"));
		prodNameCol.setMinWidth(200);
		prodNameCol.setStyle("-fx-alignment:center");

		TableColumn<Product, String> unitCol = new TableColumn<Product, String>("Unit");
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		unitCol.setMinWidth(80);
		unitCol.setStyle("-fx-alignment:center");

		TableColumn<Product, Long> hsnCol = new TableColumn<Product, Long>("HSN");
		hsnCol.setCellValueFactory(new PropertyValueFactory<>("hsnNo"));
		hsnCol.setMinWidth(100);
		hsnCol.setStyle("-fx-alignment:center");

//		TableColumn<Product,String> categoryCol = new TableColumn<>("Category ");
//		categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
//		categoryCol.setMinWidth(90);
//		categoryCol.setStyle("-fx-alignment:center");

		TableColumn<Product, Double> sellPriceCol = new TableColumn<Product, Double>("Selling\n"+" Price");
		sellPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
		sellPriceCol.setMinWidth(80);
		sellPriceCol.setStyle("-fx-alignment:center");

		TableColumn<Product, Double> gstCol = new TableColumn<Product, Double>("Tax Rate\n"+"  (%) ");
		gstCol.setCellValueFactory(new PropertyValueFactory<>("gst"));
		gstCol.setMinWidth(80);
		gstCol.setStyle("-fx-alignment:center");

//		TableColumn<Product, Double> cessCol = new TableColumn<Product, Double>("Cess");
//		cessCol.setCellValueFactory(new PropertyValueFactory<>("cess"));
//		cessCol.setMinWidth(70);
//		cessCol.setStyle("-fx-alignment:center");

		TableColumn<Product,String> barcodeCol = new TableColumn<>("Barcode");
		barcodeCol.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		barcodeCol.setMinWidth(200);
		barcodeCol.setStyle("-fx-alignment:center");



//		TableColumn<Product, Product> actionCol = new TableColumn<Product, Product>("Action");
//		actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
//		actionCol.setMaxWidth(100);
//		actionCol.setStyle("-fx-alignment:center");
////		actionCol.;
//		actionCol.setCellFactory(e->new TableCell<Product,Product>(){
//			JFXButton delBtn = new JFXButton("Delete");
//			boolean result1;
//			@Override
//		    protected void updateItem(Product product,boolean empty){
//				delBtn.getStyleClass().add("del-btn");
//				if(product==null||empty) {
//					setGraphic(null);
//					return;
//				}
//				else {
//					setGraphic(delBtn);
//					delBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//						@Override
//						public void handle(ActionEvent event) {
//							// TODO Auto-generated method stub
//							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
//							alert.setTitle("Confirmation");
////							alert.setHeaderText("HI");
//							Optional<ButtonType> result= alert.showAndWait();
//							if(result.get()==ButtonType.OK){
//								getTableView().getSortOrder().remove(product);
////								getTableView().getItems().remove(product);
//								result1= goodsDAO.deleteProduct(product);
//								showProduct(anchorPane);
//								Main.prodView.setItems(prodSortedData);
//								Main.prodView.requestFocus();
//								Main.prodView.getSelectionModel().selectLast();
//								Main.prodView.getFocusModel().focusNext();
//
//								Main.prodView.setMinSize(600, 500);
//								anchorPane.getChildren().set(0,Main.prodView);
//								getTableView().refresh();
////								hb.getChildren().clear();
//						}
//
//
//						}
//					});
//				}
//			}
//		});

		prodView.getColumns().addAll(srNOCol,prodNameCol,barcodeCol,unitCol,hsnCol,sellPriceCol,gstCol);
		prodView.getColumns().addListener(new ListChangeListener() {
			public boolean suspended;

			@Override
			public void onChanged(Change c) {
				// TODO Auto-generated method stub
				c.next();
				if (c.wasReplaced() && !suspended) {
					this.suspended = true;
					prodView.getColumns().setAll(srNOCol,prodNameCol,barcodeCol,unitCol,hsnCol,sellPriceCol,gstCol);
					this.suspended = false;
				}
			}

		});

		List<Product> prodList = goodsDAO.showProduct();
		Iterator<Product> itr = prodList.iterator();
		long srNo=1;
		while(itr.hasNext()){
			Product product = itr.next();
			prodData.add(new Product(product.getId(),product.getProduct_name(),product.getUnit(),product.getHsnNo(),product.getGst(),product.getCess(),product.getSellPrice(),product.getBarcode(),srNo));
			srNo++;
		}

		prodFilteredData= new FilteredList<>(prodData,p->true);

		prodSortedData =new SortedList<>(prodFilteredData);
		prodSortedData.comparatorProperty().bind(prodView.comparatorProperty());

		prodView.setItems(prodSortedData);
//		prodView.setItems(prodData);
		return prodView;
	}


	public GridPane updateProduct(Product product,int index,AnchorPane anchorPane) {

		GridPane gp = new GridPane();
		gp.getStyleClass().add("grid");

		gp.setAlignment(Pos.CENTER);
		gp.setHgap(20);
		gp.setVgap(30);
		gp.setGridLinesVisible(false);
		gp.setPadding(new Insets(0,0,0,10));
		Label titleLabel = new Label("Update Product");
		GridPane.setMargin(titleLabel, new Insets(-20,-50,0,50));
		gp.add(titleLabel, 0, 0);

		proNamTxt.setText(product.getProduct_name());
		proNamTxt.setPromptText("Product Name *");
		proNamTxt.setLabelFloat(true);
		validateOnFocus(proNamTxt);
//		validator=new RequiredFieldValidator();
//		proNamTxt.getValidators().add(validator);
//		validator.setMessage("Please specify Product");

//		proNamTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				// TODO Auto-generated method stub
//				if(!newValue) {
//					proNamTxt.validate();
//				}
//			}
//		});


		barcodeNum.clear();
		barcodeNum.setPromptText("Barcode Number");
		barcodeNum.setLabelFloat(true);
		validateOnFocus(barcodeNum);

		unitCombo.getItems().clear();
		unitCombo.setPromptText("Unit");
		unitCombo.setLabelFloat(true);
		unitCombo.setMinWidth(150);
//		unitStrings.clear();
//		List<Unit> unitList =goodsDAO.getUnitList();
//
//		for(Unit u:unitList) {
//			unitStrings.add(u.getUnit());
//		}

		unitCombo.getItems().addAll(fillUnitList());
		unitCombo.setValue(product.getUnit());

		unitCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(!newValue) {
					errorTip.hide();
				}

			}
		});
		unitCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
//				errorTip.hide();
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), unitCombo.getValue(), unitCombo.getItems());
				if(s!=null) {
					unitCombo.requestFocus();
					unitCombo.getSelectionModel().select(s);

				}
			}
		});

		hsnTxt.clear();
		hsnTxt.setText(String.valueOf(product.getHsnNo()));
		hsnTxt.setPromptText("HSN/SAC");
		hsnTxt.setLabelFloat(true);

//		catCombo.getItems().clear();
//		catCombo.setPromptText("Select Category *");
//		catCombo.setLabelFloat(true);
//		System.out.println(product.getCategory());
//
		sellTxt.clear();
		sellTxt.setPromptText("Selling Price");
		sellTxt.setLabelFloat(true);
		sellTxt.setText(String.valueOf(product.getSellPrice()));

		validateOnFocus(sellTxt);

		gstCombo.getItems().clear();
		gstCombo.setMinWidth(150);
		gstCombo.getItems().addAll("0.0","5.0","12.0","18.0","28.0");
		gstCombo.setValue(String.valueOf(product.getGst()));
		gstCombo.setPromptText("Tax Rate(%)");
		gstCombo.setLabelFloat(true);
//		System.out.println("GST"+product.getGst());
		gstCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(!newValue) {
					errorTip.hide();
				}
			}
		});

		gstCombo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
//				errorTip.hide();
				String s=AutoCompleteComboBoxListener.jumpTo(event.getText(), gstCombo.getValue(), gstCombo.getItems());
				if(s!=null) {
					gstCombo.requestFocus();
					gstCombo.getSelectionModel().select(s);

				}
			}
		});

//		addInfoTxt.setPromptText("Additional Info");
//		addInfoTxt.setLabelFloat(true);
//		addInfoTxt.setText(product.getInfo());

//		List<Category> catList =goodsDAO.showCategory();

//	    Iterator<Category> itr = catList.iterator();
//	    while(itr.hasNext()){
//	    	catCombo.getItems().add(itr.next().getCategory());
//	    }

//	    catCombo.setValue(product.getCategory());

//	    catCombo.getItems().addall(catList);
	    JFXButton editBtn = new JFXButton(" Update ");
	    JFXButton clrBtn = new JFXButton("Clear");

	    editBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				updateProductOnEnter(product, index, anchorPane);

			}
		});

	    clrBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				proNamTxt.clear();
//				addInfoTxt.clear();
				gstCombo.setValue(null);
				unitCombo.setValue(null);
				hsnTxt.clear();
				sellTxt.clear();
				barcodeNum.clear();

			}

		});

	    HBox hbox = new HBox();
//		hb1.setPadding(new Insets(20, 50, 50, 200));
		hbox.setSpacing(20);
		hbox.setAlignment(Pos.CENTER);
		editBtn.setMinWidth(100);
		clrBtn.setMinWidth(100);
		hbox.getChildren().addAll(editBtn,clrBtn);
		gp.add(proNamTxt, 0, 1);
		gp.add(barcodeNum, 1, 1);
		gp.add(hsnTxt, 0, 2);
		gp.add(unitCombo, 1, 2);
//		gp.add(catCombo, 1, 2);
		gp.add(sellTxt, 0, 3);
		gp.add(gstCombo, 1, 3);
//		gp.add(addInfoTxt, 0, 4,2,1);
		gp.add(hbox, 0, 5);

	    return gp;


	}
	public TableView<Category> showCategory(AnchorPane anchorPane){
		TableView<Category> catView = new TableView<Category>();
		catData.clear();
		TableColumn<Category,String> srNOCol = new TableColumn<>("Sr. No. ");
		srNOCol.setMinWidth(250);
		srNOCol.setStyle("-fx-alignment:center");
		srNOCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));
		TableColumn<Category,String> categoryCol = new TableColumn<>("Category ");
		categoryCol.setMinWidth(340);
		categoryCol.setStyle("-fx-alignment:center");
		categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
		TableColumn<Category, Category> actionCol = new TableColumn<Category,Category>("Action");
		actionCol.setMinWidth(300);
		actionCol.setStyle("-fx-alignment:center");
		actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
		actionCol.setCellFactory(e->new TableCell<Category,Category>(){
			JFXButton deleteBtn = new JFXButton("Delete");

			@Override
		    protected void updateItem(Category category,boolean empty){
				deleteBtn.getStyleClass().add("del-btn");
				if(category==null){
					setGraphic(null);
					return;
				}else{
					setGraphic(deleteBtn);
					deleteBtn.setAlignment(Pos.CENTER);
					deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							boolean result1=false;
							// TODO Auto-generated method stub
							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
//							alert.setHeaderText("HI");
							Optional<ButtonType> result= alert.showAndWait();
							if(result.get()==ButtonType.OK){
								getTableView().getSortOrder().remove(category);
								result1=goodsDAO.deleteCategory(category);
								showCategory(anchorPane);
								Main.categoryView.setItems(catSortedData);
								Main.categoryView.requestFocus();
								Main.categoryView.getSelectionModel().selectLast();
								Main.categoryView.getFocusModel().focusNext();

								Main.categoryView.setMinSize(600, 500);
								anchorPane.getChildren().set(0,Main.categoryView);

								getTableView().refresh();
//								getTableView().getItems().remove(category);


							}


						}

				});

				}
			}
			});

		catView.getColumns().addAll(srNOCol,categoryCol,actionCol);
		List<Category> catList = goodsDAO.showCategory();
		Iterator<Category> itr = catList.iterator();
		long srNo=1;
		while (itr.hasNext()){
			Category category = itr.next();
			catData.add(new Category(category.getId(), category.getCategory(),srNo));
			srNo++;
		}

		catFilteredData= new FilteredList<>(catData,p->true);

		catSortedData =new SortedList<>(catFilteredData);
		catSortedData.comparatorProperty().bind(catView.comparatorProperty());

		catView.setItems(catSortedData);
//		catView.setItems(catData);
		return catView;

	}




	public GridPane updateCategory(Category category,AnchorPane anchorPane) {

		GridPane gp = new GridPane();
		gp.getStyleClass().add("grid");

		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(30);
		gp.setGridLinesVisible(false);
//		gp.setPadding(new Insets(10,10,10,10));

		Label titleLabel = new Label(" Update Category");
//		GridPane.setMargin(titleLabel, new Insets(20,-500,-10,400));
		gp.add(titleLabel, 0, 0);

//		Label titleLbl = new Label("Update Category");
//		titleLbl.setTextFill(Color.WHITE);
//		titleLbl.setStyle("-fx-background-color:green; -fx-font-size:20px;");
//		JFXRippler rippler = new JFXRippler(titleLbl);
//		rippler.setMinSize(200, 20);
//		gp.add(rippler, 0, 0,1,1);
		Main.categoryView.refresh();

		categoryTxt.setPromptText("Category *");
		categoryTxt.setText(category.getCategory());

		categoryTxt.setLabelFloat(true);
//		categoryTxt.clear();
//		validator=new RequiredFieldValidator();
//		categoryTxt.getValidators().add(validator);
//		validator.setMessage("Please specify category");
		gp.add(categoryTxt, 0, 2);

		categoryTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(!newValue) {
					categoryTxt.validate();
				}
			}

		});
		JFXButton addBtn = new JFXButton(" Update ");
		JFXButton clrBtn = new JFXButton(" Clear ");
	    addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				catErrLbl.setText("");
				if(!categoryTxt.validate()||!categoryTxt.getText().matches("^[a-zA-Z]+$")) {
					catErrLbl.setText("Category must be a string");
					gp.add(catErrLbl, 0, 3);
					return;
				}

//				Category category = new Category();
				category.setCategory(categoryTxt.getText());
//				category.setId(category.getId());
//				System.out.println(categoryTxt.getText());

					boolean result=goodsDAO.updateCategory(category);
					if(result) {
					Alert alert = new Alert(AlertType.INFORMATION, "You have recently updated a new Category");
					alert.setTitle("Success Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
					showCategory(anchorPane);
//					Main.categoryView.getItems().clear();
					Main.categoryView.setItems(catSortedData);

					Main.categoryView.requestFocus();
					Main.categoryView.getSelectionModel().selectLast();
					Main.categoryView.getFocusModel().focusNext();
					gp.setMinSize(600, 500);
					anchorPane.getChildren().set(0,Main.categoryView);
//					categoryView.setMinSize(750, 500);
//					anchorPane.getChildren().set(0,Main.categoryView);
					}
					else {
						Alert alert = new Alert(AlertType.INFORMATION, "Error occured while updating category..!Please check database connection");
						alert.setTitle("Error Message");
						alert.setHeaderText("HI");
						alert.showAndWait();

					}
//					TableView<Category> catView = showCategory();
//					catView.setPadding(new Insets(50, 500, 100, 500));
//					mainClass.root.setCenter(catView);
//					mainClass.showStage();


			}
		});
	    clrBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				categoryTxt.clear();
			}

		});

	    HBox hbox = new HBox();
//		hb1.setPadding(new Insets(20, 50, 50, 200));
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(addBtn,clrBtn);
//	    vb.getChildren().addAll(categoryTxt,hbox);
		gp.add(hbox, 0, 4);
//	    Scene scene =new Scene(vb,200,200);
//		secondary.setAlwaysOnTop(true);
//		secondary.setScene(scene);
//		secondary.show();
	    return gp;

	}


	public GridPane createUnit(AnchorPane anchorPane) {

		GridPane gp= new GridPane();

		gp.getStyleClass().add("grid");
	    gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(30);
		gp.setGridLinesVisible(false);

		Label titleLabel = new Label("Add New Unit");
//		GridPane.setMargin(titleLabel, new Insets(20,-500,-10,400));
		gp.add(titleLabel, 0, 0);


		unitTxt.setPromptText("Unit Name");
		unitTxt.setLabelFloat(true);
		validateOnFocus(unitTxt);
		gp.add(unitTxt, 0, 2);


		unitAddInfoTxt.setPromptText("Additional Info");
		unitAddInfoTxt.setLabelFloat(true);
		validateOnFocus(unitAddInfoTxt);
		gp.add(unitAddInfoTxt, 0, 4);

		JFXButton submitBtn= new JFXButton("Create");
		gp.add(submitBtn, 0, 6);

		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				createUnitOnSubmit(anchorPane);
			}
		});


		return gp;
	}
	public TableView<Unit> showUnits(AnchorPane anchorPane){
		TableView<Unit> unitView = new TableView<Unit>();
		ObservableList<Unit> unitData = FXCollections.observableArrayList();

		TableColumn<Unit, String> unitCol=new TableColumn<Unit, String>("Unit Name");
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		unitCol.setMinWidth(300);
		unitCol.setStyle("-fx-alignment:center");
		TableColumn<Unit, String> addInfoCol = new TableColumn<Unit, String>("Additional Info");
		addInfoCol.setCellValueFactory(new PropertyValueFactory<>("addinfo"));
		addInfoCol.setMinWidth(300);
		addInfoCol.setStyle("-fx-alignment:center");

		TableColumn<Unit, Unit> actionCol =new TableColumn<Unit, Unit>("Action");
		actionCol.setMinWidth(300);
		actionCol.setStyle("-fx-alignment:center");
		actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
		actionCol.setCellFactory(e->new TableCell<Unit,Unit>(){
			JFXButton delBtn= new JFXButton("Delete");
			@Override
			protected void updateItem(Unit unit,boolean empty) {
				delBtn.getStyleClass().add("del-btn");
				if(unit==null||empty) {
					setGraphic(null);
					return;
				}
				else {
					setGraphic(delBtn);
					delBtn.setOnAction(new EventHandler<ActionEvent>() {
						boolean result1;
						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
//							alert.setHeaderText("HI");
							Optional<ButtonType> result= alert.showAndWait();
							if(result.get()==ButtonType.OK){
								getTableView().getSortOrder().remove(unit);
								result1=goodsDAO.deleteUnit(unit);
								showUnits(anchorPane);
								Main.unitView.setItems(unitSortedData);
								Main.unitView.requestFocus();
								Main.unitView.getSelectionModel().selectLast();
								Main.unitView.getFocusModel().focusNext();

								Main.unitView.setMinSize(600, 500);
								anchorPane.getChildren().set(0,Main.unitView);
							}
						}
					});
				}
			}
		});

		unitView.getColumns().addAll(unitCol,addInfoCol,actionCol);
		unitView.getColumns().addListener(new ListChangeListener() {
			public boolean suspended;

			@Override
			public void onChanged(Change c) {
				// TODO Auto-generated method stub
				c.next();
				if (c.wasReplaced() && !suspended) {
					this.suspended = true;
					unitView.getColumns().setAll(unitCol,addInfoCol,actionCol);
					this.suspended = false;
				}
			}

		});

		List<Unit> unitList = goodsDAO.showUnitList();
		for(Unit u: unitList) {
			unitData.add(new Unit(u.getId(),u.getUnit(),u.getAddinfo()));
		}

		unitFilteredData =new FilteredList<>(unitData, p->true);

		unitSortedData= new SortedList<>(unitFilteredData);
		unitSortedData.comparatorProperty().bind(unitView.comparatorProperty());
		unitView.setItems(unitSortedData);


		return unitView;
	}

	public List<String> fillUnitList(){
		unitStrings.clear();
		List<Unit> unitList =goodsDAO.getUnitList();

		for(Unit u:unitList) {
			unitStrings.add(u.getUnit());
		}

		return unitStrings;
	}


	public boolean validateProductonSubmit() {
		if(proNamTxt.getText().isEmpty()) {
			errorTip.setText("Please enter product name!");
			proNamTxt.setTooltip(errorTip);
			errorTip.show(proNamTxt,400,150);
			proNamTxt.requestFocus();
			return false;
		}

		if(!(proNamTxt.getText().matches(".*[A-Za-z ].*"))) {
			errorTip.setText("Please Enter Correct Product Name");
			proNamTxt.setTooltip(errorTip);
			errorTip.show(proNamTxt,400,150);
			proNamTxt.requestFocus();
			return false;
			}

		List<String> prodNames=goodsDAO.getProductNames();
		for(String p:prodNames) {
			if(p.equalsIgnoreCase(proNamTxt.getText())) {
				errorTip.setText("Product with name \""+p+"\" already exist!");
				proNamTxt.setTooltip(errorTip);
				errorTip.show(proNamTxt,400,150);
				proNamTxt.requestFocus();
				return false;
			}
		}

		if(!barcodeNum.getText().isEmpty()) {
			List<String> barcodeString=goodsDAO.getProductBarCodes();
			for(String b:barcodeString) {
				if(b.equals(barcodeNum.getText())) {
					errorTip.setText("Barcode already assigned to another product!");
					barcodeNum.setTooltip(errorTip);
					errorTip.show(barcodeNum,700,120);
					barcodeNum.requestFocus();
					return false;
				}
			}
		}


		if(unitCombo.getValue()==null) {
			errorTip.setText("Please Select Unit");
	          unitCombo.setTooltip(errorTip);
	          errorTip.show(unitCombo,700,250);
	          unitCombo.requestFocus();
	      	return false;
		}
//		if(hsnTxt.getText().isEmpty()) {
//			errorTip.setText("Please enter HSN!");
//			hsnTxt.setTooltip(errorTip);
//			errorTip.show(hsnTxt,700,260);
//			hsnTxt.requestFocus();
//			return false;
//		}
		if(!(hsnTxt.getText().matches("[0-9]+"))) {
			errorTip.setText("Please enter correct HSN ");
			hsnTxt.setTooltip(errorTip);
			errorTip.show(hsnTxt,700,260);
			hsnTxt.requestFocus();
			return false;
		}

		if(sellTxt.getText().isEmpty()) {
			errorTip.setText("Please enter selling price!");
			sellTxt.setTooltip(errorTip);
			errorTip.show(sellTxt,400,300);
			sellTxt.requestFocus();
			return false;
		}

		if(!sellTxt.getText().matches("^[0-9]*\\.?[0-9]*$")) {
			errorTip.setText("selling price must not be negative!");
			sellTxt.setTooltip(errorTip);
			errorTip.show(sellTxt,400,300);
			sellTxt.requestFocus();
			return false;
		}

		if(gstCombo.getValue()==null) {
			errorTip.setText("Please select Tax Rate!");
			gstCombo.setTooltip(errorTip);
			errorTip.show(gstCombo,700,260);
			gstCombo.requestFocus();
			return false;
		}

		return true;
	}

	public boolean validateProductonUpdate(Product product) {
		if(proNamTxt.getText().isEmpty()) {
			errorTip.setText("Please enter product name!");
			proNamTxt.setTooltip(errorTip);
			errorTip.show(proNamTxt,400,150);
			proNamTxt.requestFocus();
			return false;
		}


		if(!(proNamTxt.getText().matches(".*[A-Za-z ].*"))) {
			errorTip.setText("Please Enter Correct Product Name");
			proNamTxt.setTooltip(errorTip);
			errorTip.show(proNamTxt,400,150);
			proNamTxt.requestFocus();
			return false;
			}

		List<String> prodNames=goodsDAO.getProductNames();
		for(String p:prodNames) {
			if(!p.equalsIgnoreCase(product.getProduct_name())) {
			if(p.equalsIgnoreCase(proNamTxt.getText())) {
				errorTip.setText("Product with name \""+p+"\" already exist!");
				proNamTxt.setTooltip(errorTip);
				errorTip.show(proNamTxt,400,150);
				proNamTxt.requestFocus();
				return false;
			}
			}
		}

		if(!barcodeNum.getText().isEmpty()) {
			List<String> barcodeString=goodsDAO.getProductBarCodes();
			for(String b:barcodeString) {
				if(!product.getBarcode().equalsIgnoreCase(b)) {
				if(b.equals(barcodeNum.getText())) {
					errorTip.setText("Barcode already assigned to another product!");
					barcodeNum.setTooltip(errorTip);
					errorTip.show(barcodeNum,700,120);
					barcodeNum.requestFocus();
					return false;
				}
			}
			}
		}


		if(unitCombo.getValue()==null) {
			errorTip.setText("Please Select Unit");
	          unitCombo.setTooltip(errorTip);
	          errorTip.show(unitCombo,700,250);
	          unitCombo.requestFocus();
	      	return false;
		}
//		if(hsnTxt.getText().isEmpty()) {
//			errorTip.setText("Please enter HSN!");
//			hsnTxt.setTooltip(errorTip);
//			errorTip.show(hsnTxt,700,260);
//			hsnTxt.requestFocus();
//			return false;
//		}
		if(!(hsnTxt.getText().matches("[0-9]+"))) {
			errorTip.setText("Please enter correct HSN ");
			hsnTxt.setTooltip(errorTip);
			errorTip.show(hsnTxt,700,260);
			hsnTxt.requestFocus();
			return false;
		}

		if(sellTxt.getText().isEmpty()) {
			errorTip.setText("Please enter selling price!");
			sellTxt.setTooltip(errorTip);
			errorTip.show(sellTxt,400,300);
			sellTxt.requestFocus();
			return false;
		}

		if(!sellTxt.getText().matches("^[0-9]*\\.?[0-9]*$")) {
			errorTip.setText("selling price must not be negative!");
			sellTxt.setTooltip(errorTip);
			errorTip.show(sellTxt,400,300);
			sellTxt.requestFocus();
			return false;
		}

		if(gstCombo.getValue()==null||gstCombo.getValue().equals("")) {
			errorTip.setText("Please select Tax Rate!");
			gstCombo.setTooltip(errorTip);
			errorTip.show(gstCombo,700,260);
			gstCombo.requestFocus();
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


	public void createUnitOnSubmit(AnchorPane anchorPane) {
		boolean result;


		if(!validateUnit()) {
			return;
		}
		Unit unit= new Unit();
		unit.setUnit(unitTxt.getText());
//		System.out.println("Unit"+unitAddInfoTxt.getText());
		System.out.println(unitAddInfoTxt.getText());
		unit.setAddinfo(unitAddInfoTxt.getText());

		result=goodsDAO.createUnit(unit);
		if(result) {
			Alert alert = new Alert(AlertType.INFORMATION, "You have recently added a new Unit");
			alert.setTitle("Success Message");
			alert.setHeaderText("HI");
			alert.showAndWait();
			Main.dialog.close();
			showUnits(anchorPane);
			Main.unitView.setItems(unitSortedData);
			Main.unitView.requestFocus();
			Main.unitView.getSelectionModel().selectLast();
			Main.unitView.getFocusModel().focusNext();

			Main.unitView.setMinSize(900, 500);
			anchorPane.getChildren().set(0,Main.unitView);

			}
			else {
				Alert alert = new Alert(AlertType.ERROR, "Error occurred while adding unit!Please check database connection");
				alert.setTitle("Error Message");
				alert.setHeaderText("HI");
				alert.showAndWait();
			}
	}

	public void createProductOnEnter(AnchorPane anchorPane) {
		boolean result=false;
		if(!validateProductonSubmit()) {
			return;
		}

		Product product = new Product();
		product.setBarcode(barcodeNum.getText());
		product.setProduct_name(proNamTxt.getText());
		product.setHsnNo(Long.parseLong(hsnTxt.getText()));
		product.setUnit(unitCombo.getValue());
		product.setSellPrice(Double.parseDouble(sellTxt.getText()));
		product.setGst(Double.parseDouble(gstCombo.getValue()));
//		product.setCategory(catCombo.getValue().toString());
//		product.setInfo(addInfoTxt.getText());

		try {
			result=goodsDAO.addProduct(product);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result) {
		Alert alert = new Alert(AlertType.INFORMATION, "You have recently added a new Product");
		alert.setTitle("Success Message");
		alert.setHeaderText("HI");
		alert.showAndWait();
		Main.dialog.close();
		showProduct(anchorPane);
		Main.prodView.setItems(prodSortedData);
		Main.prodView.requestFocus();
		Main.prodView.getSelectionModel().selectLast();
		Main.prodView.getFocusModel().focusNext();

		Main.prodView.setMinSize(900, 500);
		anchorPane.getChildren().set(0,Main.prodView);

		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION, "Error occurred while adding product!Please check database connection");
			alert.setTitle("Error Message");
			alert.setHeaderText("HI");
			alert.showAndWait();
		}

	}
	public void updateProductOnEnter(Product product,int index,AnchorPane anchorPane) {
		boolean result;
		if(!validateProductonUpdate(product)) {
			return;
		}

		product.setProduct_name(proNamTxt.getText());
		product.setBarcode(barcodeNum.getText());
		product.setHsnNo(Long.parseLong(hsnTxt.getText()));
		product.setUnit(unitCombo.getValue());
		product.setSellPrice(Double.parseDouble(sellTxt.getText()));
		product.setGst(Double.parseDouble(gstCombo.getValue()));
//		product.setCategory(catCombo.getValue().toString());
//		product.setInfo(addInfoTxt.getText());
		result=goodsDAO.updateProduct(product);
		if(result) {
			Alert alert = new Alert(AlertType.INFORMATION, "You have recently updated \""+product.getProduct_name()+"\"");
			alert.setTitle("Success Message");
			alert.setHeaderText("HI");
			alert.showAndWait();
			Main.dialog.close();
			showProduct(anchorPane);
			Main.prodView.setItems(prodSortedData);
			Main.prodView.requestFocus();
			Main.prodView.getSelectionModel().select(index);
			Main.prodView.getFocusModel().focus(index);

			Main.prodView.setMinSize(900, 500);
			anchorPane.getChildren().set(0,Main.prodView);

		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION, "Error occurred while updating a product!Please check database connection");
			alert.setTitle("Error Message");
			alert.setHeaderText("HI");
			alert.showAndWait();
		}
	}
	public boolean validateUnit() {
		if(unitTxt.getText().isEmpty()||!unitTxt.getText().matches("^[a-zA-Z ]+$")) {
			errorTip.setText("Unit name is required and must be a string!");
			unitTxt.setTooltip(errorTip);
			errorTip.show(unitTxt,400,200);
			return false;
		}
		List<String> unitList=fillUnitList();
		for(String u:unitList) {
			if(u.equalsIgnoreCase(unitTxt.getText())) {
				errorTip.setText("Unit name with "+u+" already exist..Please enter different unit!");
				unitTxt.setTooltip(errorTip);
				errorTip.show(unitTxt,400,200);
				return false;
			}
		}
		return true;
	}
	public static TableView<StockReportBean> StockReportTable( String From, String To){
		stockDataList.clear();
		TableView<StockReportBean> StockReportDate = new TableView<StockReportBean>();
		List<StockReportBean> StockList=GoodsDAO.StockReportData(From, To);
//		System.out.println(StockList);

		TableColumn<StockReportBean, String> dateCol = new TableColumn<StockReportBean, String>("Product\n"+"Name");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("productname"));
		dateCol.setPrefWidth(170);
		dateCol.setStyle("-fx-alignment:center");

		TableColumn<StockReportBean, BigDecimal> SalesPurchaseCol = new TableColumn<StockReportBean, BigDecimal>("Total Pur-\n"+"chased Qty.");
		SalesPurchaseCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		SalesPurchaseCol.setPrefWidth(100);
		SalesPurchaseCol.setStyle("-fx-alignment:center");
//		SalesPurchaseCol.setPrefWidth(100);
		
		TableColumn<StockReportBean, Long> stockCol= new TableColumn<StockReportBean, Long>("Added\n"+" Stock");
		stockCol.setCellValueFactory(new PropertyValueFactory<>("stockQty"));
		stockCol.setStyle("-fx-alignment:center");
		stockCol.setPrefWidth(100);
		
		TableColumn<StockReportBean, Long> transferCOl= new TableColumn<StockReportBean, Long>("Transferred\n"+"Stock");
		transferCOl.setCellValueFactory(new PropertyValueFactory<>("transferQty"));
		transferCOl.setStyle("-fx-alignment:center");
		transferCOl.setPrefWidth(100);
		
		TableColumn<StockReportBean, Long> saleQtyCol= new TableColumn<StockReportBean, Long>("Sale Qty.");
		saleQtyCol.setCellValueFactory(new PropertyValueFactory<>("saleQty"));
//		saleQtyCol.setMinWidth(100);
		saleQtyCol.setStyle("-fx-alignment:center");
		saleQtyCol.setPrefWidth(100);
		TableColumn<StockReportBean, BigDecimal> CashPayCol = new TableColumn<StockReportBean, BigDecimal>("Sales\n" +"Return");
		CashPayCol.setCellValueFactory(new PropertyValueFactory<>("salesReturn"));
		CashPayCol.setPrefWidth(100);
		CashPayCol.setStyle("-fx-alignment:center");

		TableColumn<StockReportBean, BigDecimal> CashReceivedCol = new TableColumn<StockReportBean, BigDecimal>("Purchase\n"+"Return");
		CashReceivedCol.setCellValueFactory(new PropertyValueFactory<>("PurchaseReturn"));
		CashReceivedCol.setPrefWidth(100);
		CashReceivedCol.setStyle("-fx-alignment:center");

		TableColumn<StockReportBean, BigDecimal> CashInHandCol =new TableColumn<StockReportBean, BigDecimal>("Remaining\n"+"Qty.");
		CashInHandCol.setCellValueFactory(new PropertyValueFactory<>("RemainingQty"));
		CashInHandCol.setPrefWidth(100);
		CashInHandCol.setStyle("-fx-alignment:center");

		Iterator<StockReportBean> itr = StockList.iterator();
		while (itr.hasNext()) {
			StockReportBean s1 = itr.next();
			stockDataList.add(new StockReportBean( s1.getProductname(),s1.getQuantity(),s1.getSaleQty(),
					s1.getSalesReturn(),s1.getPurchaseReturn(),s1.getRemainingQty(),s1.getStockQty(),s1.getTransferQty()));
		}
		StockReportDate.getColumns().addAll(dateCol,SalesPurchaseCol,saleQtyCol,stockCol,transferCOl,CashPayCol,CashReceivedCol,CashInHandCol);
		StockReportDate.setItems(stockDataList);
		return StockReportDate;

	}

}

