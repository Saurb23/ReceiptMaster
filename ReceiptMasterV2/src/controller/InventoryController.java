package controller;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.lowagie.text.Table;

import application.KeyEventHandler;
import application.Main;
import dao.GoodsDAO;
import dao.InventoryDAO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
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
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.AddStock;
import model.Branch;
import model.DeliveryMemo;
import model.DeliveryProduct;
//import model.Account;
import model.Inventory;
import model.Product;
import model.StockTransfer;
import model.StockTransferProduct;
import model.Unit;
import net.sf.jasperreports.engine.JRException;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/***
 * 
 * @author Saurabh Gupta
 * 
 *
 */
public class InventoryController {

	JFXComboBox<String> proCatCombo = new JFXComboBox<String>();
	JFXComboBox<String> selectProdCombo = new JFXComboBox<String>();
	JFXComboBox<String> selectUnit = new JFXComboBox<String>();
	

	ObservableList<Product> stockData = FXCollections.observableArrayList();
	 ObservableList<AddStock> stockQtyData = FXCollections.observableArrayList( );
	 
	 ObservableList<StockTransfer> stockTransferData=FXCollections.observableArrayList();
	 ObservableList<String> prodList = FXCollections.observableArrayList();
	 
	 StockTransferGenerator stockTransferGenerator= new StockTransferGenerator();
	 
	InventoryDAO inventoryDao = new InventoryDAO();
	GoodsDAO goodsDAO= new GoodsDAO();
	JFXButton addbtn, clrbtn;
	JFXDatePicker datep;
	JFXTextField qtyText;
	JFXTextField stockRateTxt;
	JFXTextField totalTxt;
	
	JFXDatePicker transferDatePick;
	JFXDatePicker inwardDatePick;
	JFXComboBox<String> branchCombo;
	JFXTextField address;
	JFXComboBox<String> stateCombo;
	JFXComboBox<String> prodTxt;
	
	JFXTextField quantityTxt;
	JFXTextField rateTxt;
	JFXTextField grTotalTxt;
	double grandTotal=0;
	JFXButton deleteBtn;
	List<Product> productList=new ArrayList<>();

	// Declaration for Search functionality

	public SortedList<Product> inventSortedData;
	public FilteredList<Product> inventFilteredData;
	public SortedList<AddStock> inwardStockSortedData;
	public FilteredList<AddStock> inwardFilteredData;
	public SortedList<StockTransfer> stockSortedData;
	public FilteredList<StockTransfer> stockFilteredData;

	Tooltip errorTip = new Tooltip();

	/************************
	 * initNewStock()
	 * 
	 * @param anchorPane
	 * @return gridPane
	 *********************/
	public GridPane initNewStock(AnchorPane anchorPane) {

		GridPane gp = new GridPane();
		// HBox hbox = new HBox();
//		selectProdCombo.getItems().clear();
		// proCatCombo.getItems().clear();

		// showStockQuantityList();

		gp.getStyleClass().add("grid");

		gp.setAlignment(Pos.CENTER);
//		gp.setPadding(new Insets(0, 10, 0, 10));
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setGridLinesVisible(false);

		Label titleLabel = new Label(" Add New Stock");
		titleLabel.setStyle("-fx-font-size:20");
		GridPane.setMargin(titleLabel, new Insets(0,-230,20,230));
		gp.add(titleLabel, 0, 0);
		

		Label branchNameLbl=new Label("Branch Details");
//		GridPane.setMargin(branchNameLbl, new Insets(0,0,0,-0));
		gp.add(branchNameLbl, 1, 1);
		
		long inwardId=inventoryDao.getInwardStockId()+1;
//		System.out.println(dmId);
		int length=String.valueOf(inwardId).length();
		Label inwardOrderLbl =new Label();
		inwardOrderLbl.setText(String.format("IN"+"%0"+(6-length)+"d", (inwardId)));
		GridPane.setMargin(inwardOrderLbl, new Insets(0,0,20,-100));
		gp.add(inwardOrderLbl, 3, 0);
		
		inwardDatePick= new JFXDatePicker();
		inwardDatePick.setValue(LocalDate.now());
		inwardDatePick.setPromptText("Entry Date");
		inwardDatePick.getStyleClass().add("date-pick");
		
		KeyEventHandler.dateKeyEvent(inwardDatePick);
		GridPane.setMargin(inwardDatePick, new Insets(0,-70,0,0));
//		GridPane.setMargin(invoicDatePick, new Insets(0,0,0,-200));
		gp.add(inwardDatePick, 0, 1);
		
		HBox branchComboHB= new HBox();
		branchCombo= new JFXComboBox<>();
		branchCombo.setValue(null);
		branchCombo.setVisible(true);

//		custCombo.setStyle("-fx-font-size:15");
		branchCombo.getStyleClass().add("jf-combo-box");
		branchCombo.setStyle("-fx-font-size:12");
		branchCombo.setMaxWidth(110);
		branchCombo.setLabelFloat(true);
		branchCombo.setPromptText("Branch Name");
		branchCombo.setEditable(true);
		
		ObservableList<String> branchData=FXCollections.observableArrayList();
		List<Branch> branchList=inventoryDao.getBranchName();
		for(Branch b:branchList) {
			if(b.getBranchName()!=null&&!b.getBranchName().equals("null")) {
			branchData.add(b.getBranchName());
			}
		}
		
		branchCombo.setItems(branchData);
		new AutoCompleteComboBoxListener<>(branchCombo);
		
		branchCombo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				for(Branch b:branchList) {
					if(branchCombo.getValue()!=null) {
						if(branchCombo.getValue().equals(b.getBranchName())) {
							address.setText(b.getAddress());
							stateCombo.setValue(b.getState());
						}
					}
				}
			}
		});
		
		branchCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}
		});
		address= new JFXTextField();
//		address.clear();
		address.setPromptText("Address");
		address.setStyle("-fx-font-size:12");
		address.setLabelFloat(true);
		address.setMaxWidth(80);
		
		
		stateCombo= new JFXComboBox<>();
		stateCombo.setLabelFloat(true);
		stateCombo.setPromptText("State");
		stateCombo.setStyle("-fx-font-size:12");

//		stateCombo.setMaxWidth(10);
		stateCombo.getItems().addAll(SalesController.fillStateCombo());
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
		
		branchComboHB.getChildren().addAll(branchCombo,address,stateCombo);
		branchComboHB.setSpacing(20);
		GridPane.setMargin(branchComboHB, new Insets(0,0,0,-150));
		gp.add(branchComboHB, 2, 2);
		
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
		 inwardDatePick.setDayCellFactory(dayCellFactory);
		
		 HBox prodHB = new HBox();

			Label addProdLbl =  new Label("Add Product");
//			GridPane.setMargin(addProdLbl, new Insets(0,-100,0,30));
			gp.add(addProdLbl, 0, 4);
//			Label prodLbl = new Label("Product");
//			gp.add(prodLbl, 0, 5);

//			BarCodeRead barCodeRead= new BarCodeRead();

			prodTxt= new JFXComboBox<>();
			prodTxt.setLabelFloat(true);
			prodTxt.setPromptText("Select Product");
			prodTxt.setStyle("-fx-font-size:12");
//			new AutoCompleteComboBoxListener<>(prodTxt);

//			prodTxt.setDisable(true);

//			prodTxt.setEditable(true);
			prodTxt.setMaxWidth(100);

			prodTxt.getItems().clear();
			prodList.clear();
			
			
			productList=goodsDAO.showProductList();

			for(Product p:productList) {
				prodList.add(p.getProduct_name());
			}

			if(!prodList.isEmpty()) {
				prodTxt.getItems().addAll(prodList);
			}
			prodTxt.getStyleClass().add("jf-combo-box");
			new AutoCompleteComboBoxListener<>(prodTxt);
			
			
			
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
			
			totalTxt= new JFXTextField();
			totalTxt.setEditable(false);
//			hsnTxt.setStyle("-fx-font-size:12");
			totalTxt.setText("0.00");
			totalTxt.setPromptText("Total");
			totalTxt.setLabelFloat(true);
			totalTxt.setStyle("-fx-font-size: 15px;-fx-font-weight:bold");
			totalTxt.setFocusColor(Color.TRANSPARENT);
			totalTxt.setUnFocusColor(Color.TRANSPARENT);
			totalTxt.setMaxWidth(100);
			HBox.setMargin(totalTxt, new Insets(0, 0, 20, 20));
			
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
				prodHB.getChildren().addAll(prodTxt,quantityTxt,rateTxt,totalTxt,addMoreBtn,vb1);

				prodHB.setSpacing(20);
//				GridPane.setMargin(prodHB, new Insets(0,0,0,0));
				gp.add(prodHB, 0, 6,4,1);


//				Label grTotalLbl = new Label("Grand Total");
//				gp.add(grTotalLbl, 4, 9);

				ObservableList<Inventory> prodData = FXCollections.observableArrayList();
				TableView<Inventory> prodView = new TableView<Inventory>();


				prodView.setMaxSize(700, 200);
				TableColumn<Inventory, String> prodName = new TableColumn<Inventory, String>("Product Name");
				prodName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
				prodName.setMinWidth(150);
//				TableColumn<StockTransferProduct, String> unitCol = new TableColumn<StockTransferProduct, String>("Unit");
//				unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
//				unitCol.setPrefWidth(150);
				TableColumn<Inventory, Integer> quantityCol = new TableColumn<Inventory, Integer>("Quantity");
				quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				quantityCol.setPrefWidth(100);
				TableColumn<Inventory, String> rateCol = new TableColumn<Inventory, String>("Rate");
				rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
				rateCol.setPrefWidth(100);
				
				TableColumn<Inventory, String> totalCol = new TableColumn<Inventory, String>("Total");
				totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
				totalCol.setPrefWidth(100);
				TableColumn<Inventory, Inventory> actionCol = new TableColumn<Inventory, Inventory>("Action");
				actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
				actionCol.setCellFactory(e->new TableCell<Inventory,Inventory>(){
					
					@Override
				    protected void updateItem(Inventory inventory,boolean empty){
						if(inventory==null){
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
										getTableView().getItems().remove(inventory);
										getTableView().refresh();
										grandTotal=0;
										if(prodData.size()==0)
											grTotalTxt.setText("0.0");
										else{
										for(Inventory p:prodData){
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
				prodView.getColumns().addAll(prodName,quantityCol,rateCol,totalCol,actionCol);
				
//				GridPane.setMargin(prodView, new Insets(-20,-100,0,30));

				prodTxt.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(prodTxt.getValue()!=null){
							addMoreBtn.setDisable(false);
							editBtn.setDisable(false);
//							if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
								for(Product p:productList) {
									if(prodTxt.getValue().equals(p.getProduct_name())) {
//										double gst=p.getGst()/2;
//										gstRate=p.getGst();
////										cessRate=p.getCess();
//										cGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
//										gstRsTxt.setText(String.valueOf("0 ("+gst+"%)"));
//										hsnTxt.setText(String.valueOf(p.getHsnNo()));
										rateTxt.setText(String.valueOf(p.getSellPrice()));
									}

								}
						}
					}
				});
				
				JFXButton submitBtn = new JFXButton(" Submit ");
				GridPane.setMargin(submitBtn, new Insets(0,0,0,-100));
				
				
				
				addMoreBtn.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(!validateOnAddEdit()) {
							return;
						}
						boolean productFlag=false;
						
						Inventory stProduct= new Inventory();
						stProduct.setProduct(new Product());
						for(Product p:productList) {
							if(prodTxt.getValue().equals(p.getProduct_name())) {
								stProduct.getProduct().setId(p.getId());
								//stProduct.getProduct().setProduct_name(p.getProduct_name());
								stProduct.setCurrentStock(p.getQuantity());
								stProduct.setProduct_name(p.getProduct_name());
								productFlag=true;
							}
						}
//						if(!productFlag) {
//							errorTip.setText("No Product found with specified name!");
//							prodTxt.setTooltip(errorTip);
//							errorTip.show(prodTxt,100,160);
//							return;
//						}
						stProduct.getProduct().setProduct_name(prodTxt.getValue());
						stProduct.getProduct().setQuantity(Integer.parseInt(quantityTxt.getText()));
//						stProduct.setCurrentStock(Integer.parseInt(quantityTxt.getText()));
						stProduct.setProduct_name(prodTxt.getValue());
						stProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
						String trunRateAmt=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
								.setScale(2,RoundingMode.HALF_UP)
								.toPlainString();
						stProduct.setRateString(trunRateAmt);
						stProduct.setRate(Double.parseDouble(rateTxt.getText()));
						
						String trunTotalAmt=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
								
						stProduct.setTotal(Double.parseDouble(totalTxt.getText()));
						stProduct.setTotalString(trunTotalAmt);
						for(Inventory st:prodData) {
							if(st.getProduct_name().equals(stProduct.getProduct_name())) {
								errorTip.setText("Duplicate entries are not permitted");
								prodTxt.setTooltip(errorTip);
								errorTip.show(prodTxt,100,200);
								return;
							}
						}
						
						prodData.add(stProduct);
						prodView.setItems(prodData);
						prodView.requestFocus();
						prodView.getSelectionModel().selectLast();
						submitBtn.requestFocus();
						grandTotal=grandTotal+stProduct.getTotal();
						grTotalTxt.setText(String.format("%.2f", grandTotal));
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
					Inventory stProduct=prodView.getSelectionModel().getSelectedItem();
					int index=prodView.getSelectionModel().getSelectedIndex();
					prodTxt.setValue(stProduct.getProduct_name());
//					unitCombo.setText(deliveryProduct.getUnit());
					quantityTxt.setText(String.valueOf(stProduct.getQuantity()));

					String trunRateAmt=BigDecimal.valueOf(stProduct.getRate())
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();

					rateTxt.setText(trunRateAmt);
					String trunTotalAmt=BigDecimal.valueOf(stProduct.getTotal())
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();
					totalTxt.setText(trunTotalAmt);

					grandTotal=grandTotal-stProduct.getTotal();
					prodHB.getChildren().remove(addMoreBtn);
					prodHB.getChildren().add(5, editBtn);
					
					editBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
//							clearLabels();
//							gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
							if(!validateOnAddEdit()){
								return;
							}
							stProduct.setProduct_name(prodTxt.getValue());
							stProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
							String trunRateAmt1=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
									.setScale(3, RoundingMode.HALF_UP)
									.toPlainString();
							stProduct.setRateString(trunRateAmt1);

							stProduct.setRate(Double.parseDouble(rateTxt.getText()));

							String trunTotalAmt1=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
									.setScale(3, RoundingMode.HALF_UP)
									.toPlainString();
							stProduct.setTotalString(trunTotalAmt1);
							stProduct.setTotal(Double.parseDouble(totalTxt.getText()));

							prodData.set(index,stProduct);
							prodTxt.requestFocus();
							grandTotal=0;
							for(Inventory p:prodData) {
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
		
		grTotalTxt= new JFXTextField();
		grTotalTxt.setLabelFloat(true);
		grTotalTxt.setEditable(false);
		grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
		grTotalTxt.setFocusColor(Color.TRANSPARENT);
		grTotalTxt.setPromptText("Grand Total");
		grTotalTxt.setStyle("-fx-font-size: 20px; -fx-font-weight:bold;");
		grTotalTxt.setText("00.00");
		
		GridPane.setMargin(grTotalTxt, new Insets(20,-50,0,50));
		gp.add(prodView, 0, 8,5,1);
		gp.add(grTotalTxt, 0, 10);
		gp.add(submitBtn, 2,12);
		
		quantityTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();

				if(!validateStockProductControls()){
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
//					gp.add(quantityErrLbl, 1, 7);
					return;
				}

//				for(Product p:productList) {
//					if(prodTxt.getValue()!=null) {
//					if(prodTxt.getValue().equals(p.getProduct_name())) {
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
//				totErrLbl.setText("");
				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if(!newValue.isEmpty()){
					if(!(rateTxt.getText().equals(""))){
					double total = (Double.parseDouble(newValue)*Double.parseDouble(rateTxt.getText()));
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
				if(!validateStockProductControls()){
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					return;
				}

//				if(!quantityTxt.getText().isEmpty()) {
//				for(Product p:productList) {
//					if(prodTxt.getValue()!=null) {
//					if(prodTxt.getValue().equals(p.getProduct_name())) {
//
//						if(p.getQuantity()<Integer.parseInt(quantityTxt.getText())) {
////							System.out.println(p.getQuantity());
//							errorTip.setText("Quantity must not exceed current stock");
//							quantityTxt.setTooltip(errorTip);
//							errorTip.show(quantityTxt,400,150);
//							addMoreBtn.setDisable(true);
//							editBtn.setDisable(true);
//							return;
//						}
//					}
//				}
//				}
//				}

				addMoreBtn.setDisable(false);
				editBtn.setDisable(false);
				if(!newValue.equals("")){
//					if(newValue.matches(arg0))
					if(!(quantityTxt.getText().equals(""))){
				double total = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(newValue));
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
				if(!validateOnInwardSubmit()) {
					return;
				}
				
				AddStock addStock= new AddStock();
				addStock.setInwardNo(inwardId);
				addStock.setBranch(new Branch());
				
				if(branchCombo.getValue()!=null) {
					for(Branch b:branchList) {
						if(branchCombo.getValue().equals(b.getBranchName())) {
							addStock.getBranch().setId(b.getId());
							addStock.getBranch().setBranchName(b.getBranchName());
						}
					}
				}
				try {
				LocalDate ld=inwardDatePick.getValue();
				SimpleDateFormat sd=new SimpleDateFormat("yyy-MM-dd");
				java.sql.Date sqlDate=new java.sql.Date(sd.parse(ld.toString()).getTime());
				addStock.setEntryDate(sqlDate);
				}catch(ParseException e) {
					e.printStackTrace();
				}
				addStock.getBranch().setBranchName(branchCombo.getValue());
				addStock.getBranch().setAddress(address.getText());
				addStock.getBranch().setState(stateCombo.getValue());
				addStock.setTotal(Double.parseDouble(grTotalTxt.getText()));
				result=inventoryDao.addNewStock(addStock,prodData);
				if(result) {
					Alert alert = new Alert(AlertType.INFORMATION, "Stock has been added successfully");
					alert.setTitle("Success Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
					Main.dialog.close();
//					try {
//						stockTransferGenerator.(addStock);
//					} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					clearInvoiceControls();
					showStockQuantityList(anchorPane);
					Main.stockView.setItems(inwardStockSortedData);
					Main.stockView.requestFocus();
					Main.stockView.getSelectionModel().selectLast();
					Main.stockView.getFocusModel().focusNext();

					Main.stockView.setMinSize(900, 500);
					anchorPane.getChildren().set(0,Main.stockView);

					}
					else {
						Alert alert = new Alert(AlertType.INFORMATION, "Error occurred while adding Stock!Please check database connection");
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
		

		return gp;
	}

	// following work pending

	public TableView<AddStock> showStockQuantityList(AnchorPane anchorPane) {

		TableView<AddStock> table = new TableView<AddStock>();

		// Inventory inventory = new Inventory(null);
		stockQtyData.clear();
		
		List<AddStock> inventoryList= inventoryDao.retriveStockQtyList();
		long srNo=1;
		for(AddStock i:inventoryList) {
			String total=BigDecimal.valueOf(i.getTotal())
					.setScale(2,RoundingMode.HALF_UP).toPlainString();
			stockQtyData.add(new AddStock(i.getId(),srNo,i.getEntryDate(),i.getInwardNo(),i.getBranch().getBranchName().replaceAll("null", ""),i.getTotal(),total));
			srNo++;
		}

//		final Label label = new Label("STOCK LIST (DATEWISE)");
//		label.setFont(new Font("Arial", 20));
//		label.setAlignment(Pos.TOP_CENTER);

		TableColumn srNoCol = new TableColumn<Object, Object>("Sr. No.");
		srNoCol.setPrefWidth(100);
		srNoCol.setStyle("-fx-alignment:center");
		srNoCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));

		TableColumn dateCol = new TableColumn("Date");
		dateCol.setPrefWidth(100);
		dateCol.setStyle("-fx-alignment:center");

		dateCol.setCellValueFactory(new PropertyValueFactory<>("entryDate"));

		// TableColumn categoryCol = new TableColumn("Category");
		//// categoryCol.setMinWidth(120);
		// categoryCol.setMinWidth(200);
		// categoryCol.setStyle("-fx-alignment:center");
		// categoryCol.setCellValueFactory(new
		// PropertyValueFactory<>("product_category"));

		TableColumn branchCol = new TableColumn("Branch Name");
		branchCol.setPrefWidth(300);
		branchCol.setStyle("-fx-alignment:center");
		// productCol.setMinWidth(150);
		branchCol.setCellValueFactory(new PropertyValueFactory<>("branchname"));
		
		TableColumn<AddStock, String> inwardCol= new TableColumn<AddStock, String>("Inward No.");
		inwardCol.setCellValueFactory(new PropertyValueFactory<>("inwardNo"));
		inwardCol.setPrefWidth(125);
		inwardCol.setStyle("-fx-alignment:center");
		
		TableColumn totalCol = new TableColumn("Total");
		totalCol.setPrefWidth(200);
		totalCol.setStyle("-fx-alignment:center");
		// qtyCol.setMinWidth(150);
		totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));

		// TableColumn unitCol = new TableColumn("UNIT");
		// unitCol.setMinWidth(150);
		// unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));

		// table.setPrefSize(500, 600);
		// accTypeCol.setStyle( "-fx-alignment: CENTER;");
		TableColumn<AddStock, AddStock> actionCol = new TableColumn<AddStock, AddStock>("Action");
//		actionCol.setMinWidth(150);
		actionCol.setStyle("-fx-alignment:center");
		actionCol.setCellValueFactory(e -> new ReadOnlyObjectWrapper<>(e.getValue()));
		actionCol.setCellFactory(e -> new TableCell<AddStock, AddStock>() {
			JFXButton delBtn = new JFXButton("Delete");
			boolean result1;
			@Override
			protected void updateItem(AddStock inventory, boolean empty) {
				delBtn.getStyleClass().add("del-btn");
				if (inventory == null || empty) {
					setGraphic(null);
					return;
				} else {
					setGraphic(delBtn);
					delBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
							// alert.setHeaderText("HI");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK) {
								result1=inventoryDao.deleteStock(inventory);
								showStockQuantityList(anchorPane);
								Main.stockView.setItems(inwardStockSortedData);
								Main.stockView.requestFocus();
								Main.stockView.getSelectionModel().selectLast();
								Main.stockView.getFocusModel().focusNext();
								Main.stockView.setMinSize(900, 500);
								anchorPane.getChildren().set(0, Main.stockView);
								
								getTableView().getItems().remove(inventory);
								getTableView().refresh();
							}
						}
					});
				}
			}
		});
		table.getColumns().addAll(srNoCol, dateCol, inwardCol,branchCol,totalCol, actionCol);
		table.getColumns().addListener(new ListChangeListener() {
			public boolean suspended;

			@Override
			public void onChanged(Change c) {
				// TODO Auto-generated method stub
				c.next();
				if (c.wasReplaced() && !suspended) {
					this.suspended = true;
					table.getColumns().setAll(srNoCol, dateCol, inwardCol,branchCol,totalCol, actionCol);
					this.suspended = false;
				}
			}

		});

		// Retrieving account list
//		inventoryDao.retriveStockQtyList();
		
		inwardFilteredData= new FilteredList<>(stockQtyData,p->true);
		inwardStockSortedData= new SortedList<>(inwardFilteredData);
		inwardStockSortedData.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(inwardStockSortedData);
		
		return table;
	}

	private Object buttonClicked(ActionEvent e, AnchorPane anchorPane) {

		if (e.getSource() == addbtn) {
//			storeNewStock(anchorPane);
		} else if (e.getSource() == clrbtn) {
			clearControls();
		}
		return e;
	}

//	private void storeNewStock(AnchorPane anchorPane) {
//		if (!validateNewStock()) {
//			return;
//		}
//		Inventory inventory = new Inventory();
//		// InventoryDAO inventoryDao = new InventoryDAO();
//		inventory.setDate(datep.getValue().toString());
//		// inventory.setProduct_category(proCatCombo.getValue().toString());
//		inventory.setProduct_name(selectProdCombo.getValue().toString());
//		inventory.setQuantity(Long.parseLong(qtyText.getText()));
//		inventory.setRate(Double.parseDouble(rateTxt.getText()));
//		inventory.setTotal(Double.parseDouble(totalTxt.getText()));
//		// inventory.setUnit(selectUnit.getValue().toString());
//
////		boolean result = inventoryDao.addNewStock(inventory);
//		if (result) {
//			Alert alert = new Alert(AlertType.INFORMATION, "You have recently added a new Stock");
//			alert.setTitle("Success Message");
//			alert.setHeaderText("HI");
//			alert.showAndWait();
//			showStockQuantityList();
//			// showCust();
//			Main.dialog.close();
//			Main.stockView.setItems(inventoryDao.stockQtyData);
//			Main.stockView.requestFocus();
//			Main.stockView.getSelectionModel().selectLast();
//			Main.stockView.getFocusModel().focusNext();
//
//			Main.stockView.setMinSize(900, 500);
//			anchorPane.getChildren().set(0, Main.stockView);
//		} else {
//			Alert alert = new Alert(AlertType.INFORMATION,
//					"Error occurred while adding Stock!Please check database connection");
//			alert.setTitle("Error Message");
//			alert.setHeaderText("HI");
//			alert.showAndWait();
//		}
//	}

	private void clearControls() {

		qtyText.clear();
		// proCatCombo.setPromptText("Select Category");
		selectProdCombo.setValue(null);
		datep.setValue(null);
	}

	public void fillComboBox() {

		Inventory inventory = new Inventory(null);
		// InventoryDAO inventoryDAO= new InventoryDAO();

		// ObservableList<String> proCatList = null;
		// try {
		// proCatList = inventoryDao.retriveAccountTypeList(inventory);
		// } catch (Exception e) {
		//
		// Alert alert = new Alert(AlertType.ERROR);
		// alert.setTitle("Error");
		// alert.setHeaderText("Check your database services!");
		//// alert.setContentText("");
		// alert.showAndWait();
		// }
		ObservableList<String> proList = inventoryDao.retriveAccountList(inventory);
		// proCatCombo.setItems(proCatList);
		selectProdCombo.setItems(proList);
	}

	public TableView<Product> showCurrentStock() {

		TableView<Product> currentStockView = new TableView<Product>();
		stockData.clear();

		TableColumn<Product, Long> srNoCol = new TableColumn<Product, Long>("Sr. No.");
		srNoCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));
		srNoCol.setMinWidth(150);
		srNoCol.setStyle("-fx-alignment:center");
		// TableColumn<Product, String> categoryCol = new TableColumn<Product,
		// String>("Category");
		// categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
		// categoryCol.setMinWidth(250);
		// categoryCol.setStyle("-fx-alignment:center");

		TableColumn<Product, String> prodCol = new TableColumn<Product, String>("Product");
		prodCol.setCellValueFactory(new PropertyValueFactory<>("product_name"));
		prodCol.setMinWidth(300);
		prodCol.setStyle("-fx-alignment:center");

		TableColumn<Product, Long> quantityCol = new TableColumn<Product, Long>("Quantity");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		quantityCol.setMinWidth(200);
		quantityCol.setStyle("-fx-alignment:center");
		currentStockView.getColumns().addAll(srNoCol, prodCol, quantityCol);
		currentStockView.getColumns().addListener(new ListChangeListener() {
			public boolean suspended;

			@Override
			public void onChanged(Change c) {
				// TODO Auto-generated method stub
				c.next();
				if (c.wasReplaced() && !suspended) {
					this.suspended = true;
					currentStockView.getColumns().setAll(srNoCol, prodCol, quantityCol);
					this.suspended = false;
				}
			}

		});

		List<Product> stockList = inventoryDao.showCurrentStocks();
		Iterator<Product> itr = stockList.iterator();
		long srNo = 1;
		while (itr.hasNext()) {
			Product product = itr.next();
			stockData.add(new Product(product.getId(), product.getProduct_name(), product.getQuantity(), srNo));
			srNo++;
		}

		inventFilteredData = new FilteredList<>(stockData, p -> true);

		inventSortedData = new SortedList<>(inventFilteredData);
		inventSortedData.comparatorProperty().bind(currentStockView.comparatorProperty());

		currentStockView.setItems(inventSortedData);

		return currentStockView;

	}
	
	
	public GridPane createStockTransfer(AnchorPane anchorPane) {
		
		GridPane gp=new GridPane();
		errorTip.setAutoHide(true);
		gp.getStyleClass().add("grid");
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
//		gp.setPadding(new Insets(10, 10, 10, 10));
		
		Label titleLabel = new Label(" Create New Stock Transfer");
		titleLabel.setStyle("-fx-font-size:20");
		GridPane.setMargin(titleLabel, new Insets(0,-250,20,250));
		gp.add(titleLabel, 0, 0);
		

		Label branchNameLbl=new Label("Branch Details");
//		GridPane.setMargin(branchNameLbl, new Insets(0,0,0,-0));
		gp.add(branchNameLbl, 1, 1);
		
		long transferId=inventoryDao.getTransferId()+1;
//		System.out.println(dmId);
		int length=String.valueOf(transferId).length();
		Label transferOrderLbl =new Label();
		transferOrderLbl.setText(String.format("TR"+"%0"+(6-length)+"d", (transferId)));
		GridPane.setMargin(transferOrderLbl, new Insets(0,0,20,-100));
		gp.add(transferOrderLbl, 3, 0);
		
		transferDatePick= new JFXDatePicker();
		transferDatePick.setValue(LocalDate.now());
		transferDatePick.setPromptText("Entry Date");
		transferDatePick.getStyleClass().add("date-pick");
		
		KeyEventHandler.dateKeyEvent(transferDatePick);
		GridPane.setMargin(transferDatePick, new Insets(0,-70,0,0));
//		GridPane.setMargin(invoicDatePick, new Insets(0,0,0,-200));
		gp.add(transferDatePick, 0, 1);
		
		HBox branchComboHB= new HBox();
		branchCombo= new JFXComboBox<>();
		branchCombo.setValue(null);
		branchCombo.setVisible(true);

//		custCombo.setStyle("-fx-font-size:15");
		branchCombo.getStyleClass().add("jf-combo-box");
		branchCombo.setStyle("-fx-font-size:12");
		branchCombo.setMaxWidth(110);
		branchCombo.setLabelFloat(true);
		branchCombo.setPromptText("Branch Name");
		branchCombo.setEditable(true);
		
		ObservableList<String> branchData=FXCollections.observableArrayList();
		List<Branch> branchList=inventoryDao.getBranchName();
		for(Branch b:branchList) {
			if(b.getBranchName()!=null&&!b.getBranchName().equals("null")) {
			branchData.add(b.getBranchName());
			}
		}
		
		branchCombo.setItems(branchData);
		new AutoCompleteComboBoxListener<>(branchCombo);
		
		branchCombo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				for(Branch b:branchList) {
					if(branchCombo.getValue()!=null) {
						if(branchCombo.getValue().equals(b.getBranchName())) {
							address.setText(b.getAddress());
							stateCombo.setValue(b.getState());
						}
					}
				}
			}
		});
		
		branchCombo.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}
		});
		address= new JFXTextField();
//		address.clear();
		address.setPromptText("Address");
		address.setStyle("-fx-font-size:12");
		address.setLabelFloat(true);
		address.setMaxWidth(80);
		
		
		stateCombo= new JFXComboBox<>();
		stateCombo.setLabelFloat(true);
		stateCombo.setPromptText("State");
		stateCombo.setStyle("-fx-font-size:12");

//		stateCombo.setMaxWidth(10);
		stateCombo.getItems().addAll(SalesController.fillStateCombo());
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
		
		branchComboHB.getChildren().addAll(branchCombo,address,stateCombo);
		branchComboHB.setSpacing(20);
		GridPane.setMargin(branchComboHB, new Insets(0,0,0,-150));
		gp.add(branchComboHB, 2, 2);
		
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
		 transferDatePick.setDayCellFactory(dayCellFactory);
		
		 HBox prodHB = new HBox();

			Label addProdLbl =  new Label("Add Product");
//			GridPane.setMargin(addProdLbl, new Insets(0,-100,0,30));
			gp.add(addProdLbl, 0, 4);
//			Label prodLbl = new Label("Product");
//			gp.add(prodLbl, 0, 5);

//			BarCodeRead barCodeRead= new BarCodeRead();

			prodTxt= new JFXComboBox<>();
			prodTxt.setLabelFloat(true);
			prodTxt.setPromptText("Select Product");
			prodTxt.setStyle("-fx-font-size:12");
//			new AutoCompleteComboBoxListener<>(prodTxt);

//			prodTxt.setDisable(true);

//			prodTxt.setEditable(true);
			prodTxt.setMaxWidth(100);

			prodTxt.getItems().clear();
			prodList.clear();
			
			
			productList=goodsDAO.showProductList();

			for(Product p:productList) {
				prodList.add(p.getProduct_name());
			}

			if(!prodList.isEmpty()) {
				prodTxt.getItems().addAll(prodList);
			}
			prodTxt.getStyleClass().add("jf-combo-box");
			new AutoCompleteComboBoxListener<>(prodTxt);
			
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
			
			totalTxt= new JFXTextField();
			totalTxt.setEditable(false);
//			hsnTxt.setStyle("-fx-font-size:12");
			totalTxt.setText("0.00");
			totalTxt.setPromptText("Total");
			totalTxt.setLabelFloat(true);
			totalTxt.setStyle("-fx-font-size: 15px;-fx-font-weight:bold");
			totalTxt.setFocusColor(Color.TRANSPARENT);
			totalTxt.setUnFocusColor(Color.TRANSPARENT);
			totalTxt.setMaxWidth(100);
			HBox.setMargin(totalTxt, new Insets(0, 0, 20, 20));
			
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
				prodHB.getChildren().addAll(prodTxt,quantityTxt,rateTxt,totalTxt,addMoreBtn,vb1);

				prodHB.setSpacing(20);
//				GridPane.setMargin(prodHB, new Insets(0,0,0,0));
				gp.add(prodHB, 0, 6,4,1);


//				Label grTotalLbl = new Label("Grand Total");
//				gp.add(grTotalLbl, 4, 9);

				ObservableList<StockTransferProduct> prodData = FXCollections.observableArrayList();
				TableView<StockTransferProduct> prodView = new TableView<StockTransferProduct>();


				prodView.setMaxSize(700, 200);
				TableColumn<StockTransferProduct, String> prodName = new TableColumn<StockTransferProduct, String>("Product Name");
				prodName.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
				prodName.setMinWidth(150);
//				TableColumn<StockTransferProduct, String> unitCol = new TableColumn<StockTransferProduct, String>("Unit");
//				unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
//				unitCol.setPrefWidth(150);
				TableColumn<StockTransferProduct, Integer> quantityCol = new TableColumn<StockTransferProduct, Integer>("Quantity");
				quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				quantityCol.setPrefWidth(100);
				TableColumn<StockTransferProduct, String> rateCol = new TableColumn<StockTransferProduct, String>("Rate");
				rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
				rateCol.setPrefWidth(100);
				
				TableColumn<StockTransferProduct, String> totalCol = new TableColumn<StockTransferProduct, String>("Total");
				totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
				totalCol.setPrefWidth(100);
				TableColumn<StockTransferProduct, StockTransferProduct> actionCol = new TableColumn<StockTransferProduct, StockTransferProduct>("Action");
				actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
				actionCol.setCellFactory(e->new TableCell<StockTransferProduct,StockTransferProduct>(){
					
					@Override
				    protected void updateItem(StockTransferProduct stockTransferProduct,boolean empty){
						if(stockTransferProduct==null){
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
										getTableView().getItems().remove(stockTransferProduct);
										getTableView().refresh();
										grandTotal=0;
										if(prodData.size()==0)
											grTotalTxt.setText("0.0");
										else{
										for(StockTransferProduct p:prodData){
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
				prodView.getColumns().addAll(prodName,quantityCol,rateCol,totalCol,actionCol);
				
//				GridPane.setMargin(prodView, new Insets(-20,-100,0,30));

				prodTxt.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(prodTxt.getValue()!=null){
							addMoreBtn.setDisable(false);
							editBtn.setDisable(false);
//							if(prodHB.getChildren().contains(cGstTxt)&&prodHB.getChildren().contains(gstRsTxt)) {
								for(Product p:productList) {
									if(prodTxt.getValue().equals(p.getProduct_name())) {
//										double gst=p.getGst()/2;
//										gstRate=p.getGst();
////										cessRate=p.getCess();
//										cGstTxt.setText(String.valueOf("0 ("+gst+"%)"));
//										gstRsTxt.setText(String.valueOf("0 ("+gst+"%)"));
//										hsnTxt.setText(String.valueOf(p.getHsnNo()));
										rateTxt.setText(String.valueOf(p.getSellPrice()));
									}

								}
						}
					}
				});
				
				JFXButton submitBtn = new JFXButton(" Submit ");
				GridPane.setMargin(submitBtn, new Insets(0,0,0,-100));
				
				
				
				addMoreBtn.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if(!validateOnAddEdit()) {
							return;
						}
						boolean productFlag=false;
						
						StockTransferProduct stProduct= new StockTransferProduct();
						stProduct.setProduct(new Product());
						for(Product p:productList) {
							if(prodTxt.getValue().equals(p.getProduct_name())) {
								stProduct.getProduct().setId(p.getId());
								stProduct.getProduct().setProduct_name(p.getProduct_name());
								stProduct.setCurrentStock(p.getQuantity());
								stProduct.setProd_name(p.getProduct_name());
								productFlag=true;
							}
						}
						if(!productFlag) {
							errorTip.setText("No Product found with specified name!");
							prodTxt.setTooltip(errorTip);
							errorTip.show(prodTxt,100,160);
							return;
						}
						
						stProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
						String trunRateAmt=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
								.setScale(2,RoundingMode.HALF_UP)
								.toPlainString();
						stProduct.setRateString(trunRateAmt);
						stProduct.setRate(Double.parseDouble(rateTxt.getText()));
						
						String trunTotalAmt=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
							    .setScale(2, RoundingMode.HALF_UP)
							    .toPlainString();
								
						stProduct.setTotal(Double.parseDouble(totalTxt.getText()));
						stProduct.setTotalString(trunTotalAmt);
						for(StockTransferProduct st:prodData) {
							if(st.getProd_name().equals(stProduct.getProd_name())) {
								errorTip.setText("Duplicate entries are not permitted");
								prodTxt.setTooltip(errorTip);
								errorTip.show(prodTxt,100,200);
								return;
							}
						}
						
						prodData.add(stProduct);
						prodView.setItems(prodData);
						prodView.requestFocus();
						prodView.getSelectionModel().selectLast();
						submitBtn.requestFocus();
						grandTotal=grandTotal+stProduct.getTotal();
						grTotalTxt.setText(String.format("%.2f", grandTotal));
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
					StockTransferProduct stProduct=prodView.getSelectionModel().getSelectedItem();
					int index=prodView.getSelectionModel().getSelectedIndex();
					prodTxt.setValue(stProduct.getProd_name());
//					unitCombo.setText(deliveryProduct.getUnit());
					quantityTxt.setText(String.valueOf(stProduct.getQuantity()));

					String trunRateAmt=BigDecimal.valueOf(stProduct.getRate())
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();

					rateTxt.setText(trunRateAmt);
					String trunTotalAmt=BigDecimal.valueOf(stProduct.getTotal())
						    .setScale(2, RoundingMode.HALF_UP)
						    .toPlainString();
					totalTxt.setText(trunTotalAmt);

					grandTotal=grandTotal-stProduct.getTotal();
					prodHB.getChildren().remove(addMoreBtn);
					prodHB.getChildren().add(5, editBtn);
					
					editBtn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
//							clearLabels();
//							gp.getChildren().removeAll(prodErrLbl,unitErrorLbl,quantityErrLbl,rateErrLbl,discErrLbl,gstErrLbl,totErrLbl,dupliErrLbl);
							if(!validateOnAddEdit()){
								return;
							}
							stProduct.setProd_name(prodTxt.getValue());
							stProduct.setQuantity(Integer.parseInt(quantityTxt.getText()));
							String trunRateAmt1=BigDecimal.valueOf(Double.parseDouble(rateTxt.getText()))
									.setScale(3, RoundingMode.HALF_UP)
									.toPlainString();
							stProduct.setRateString(trunRateAmt1);

							stProduct.setRate(Double.parseDouble(rateTxt.getText()));

							String trunTotalAmt1=BigDecimal.valueOf(Double.parseDouble(totalTxt.getText()))
									.setScale(3, RoundingMode.HALF_UP)
									.toPlainString();
							stProduct.setTotalString(trunTotalAmt1);
							stProduct.setTotal(Double.parseDouble(totalTxt.getText()));

							prodData.set(index,stProduct);
							prodTxt.requestFocus();
							grandTotal=0;
							for(StockTransferProduct p:prodData) {
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
		
		grTotalTxt= new JFXTextField();
		grTotalTxt.setLabelFloat(true);
		grTotalTxt.setEditable(false);
		grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
		grTotalTxt.setFocusColor(Color.TRANSPARENT);
		grTotalTxt.setPromptText("Grand Total");
		grTotalTxt.setStyle("-fx-font-size: 20px; -fx-font-weight:bold;");
		grTotalTxt.setText("00.00");
		
		GridPane.setMargin(grTotalTxt, new Insets(20,-50,0,50));
		gp.add(prodView, 0, 8,5,1);
		gp.add(grTotalTxt, 0, 10);
		gp.add(submitBtn, 2,12);
		
		quantityTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();

				if(!validateStockProductControls()){
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
//					gp.add(quantityErrLbl, 1, 7);
					return;
				}

				for(Product p:productList) {
					if(prodTxt.getValue()!=null) {
					if(prodTxt.getValue().equals(p.getProduct_name())) {
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
					if(!(rateTxt.getText().equals(""))){
					double total = (Double.parseDouble(newValue)*Double.parseDouble(rateTxt.getText()));
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
				if(!validateStockProductControls()){
					addMoreBtn.setDisable(true);
					editBtn.setDisable(true);
					return;
				}

				if(!quantityTxt.getText().isEmpty()) {
				for(Product p:productList) {
					if(prodTxt.getValue()!=null) {
					if(prodTxt.getValue().equals(p.getProduct_name())) {

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
					if(!(quantityTxt.getText().equals(""))){
				double total = (Double.parseDouble(quantityTxt.getText())*Double.parseDouble(newValue));
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
				if(!validateOnTransferSubmit()) {
					return;
				}
				
				StockTransfer stockTransfer= new StockTransfer();
				stockTransfer.setTransferNo(transferId);
				stockTransfer.setBranch(new Branch());
				
				if(branchCombo.getValue()!=null) {
					for(Branch b:branchList) {
						if(branchCombo.getValue().equals(b.getBranchName())) {
							stockTransfer.getBranch().setId(b.getId());
							stockTransfer.getBranch().setBranchName(b.getBranchName());
						}
					}
				}
				try {
				LocalDate ld=transferDatePick.getValue();
				SimpleDateFormat sd=new SimpleDateFormat("yyy-MM-dd");
				java.sql.Date sqlDate=new java.sql.Date(sd.parse(ld.toString()).getTime());
				stockTransfer.setEntryDate(sqlDate);
				}catch(ParseException e) {
					e.printStackTrace();
				}
				stockTransfer.getBranch().setBranchName(branchCombo.getValue());
				stockTransfer.getBranch().setAddress(address.getText());
				stockTransfer.getBranch().setState(stateCombo.getValue());
				stockTransfer.setTotal(Double.parseDouble(grTotalTxt.getText()));
				result=inventoryDao.createTransferStock(stockTransfer,prodData);
				if(result) {
					Alert alert = new Alert(AlertType.INFORMATION, "Stock has been transferred successfully");
					alert.setTitle("Success Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
					Main.dialog.close();
					try {
						stockTransferGenerator.stockTransferMemo(stockTransfer);
					} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					clearInvoiceControls();
					showTransferView(anchorPane);
					Main.transferView.setItems(stockSortedData);
					Main.transferView.requestFocus();
					Main.transferView.getSelectionModel().selectLast();
					Main.transferView.getFocusModel().focusNext();

					Main.transferView.setMinSize(900, 500);
					anchorPane.getChildren().set(0,Main.transferView);

					}
					else {
						Alert alert = new Alert(AlertType.INFORMATION, "Error occurred while generating Stock Transfer!Please check database connection");
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
		

		return gp;
		
	}
	public TableView<StockTransfer> showTransferView(AnchorPane anchorPane){
		
		TableView<StockTransfer> transferView= new TableView<StockTransfer>();
		stockTransferData.clear();
		TableColumn<StockTransfer, Long> srNoCol= new TableColumn<>("Sr. No.");
		srNoCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));
		srNoCol.setPrefWidth(100);
		srNoCol.setStyle("-fx-alignment:center");
		TableColumn<StockTransfer, Date> dateCol= new TableColumn<>("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
		dateCol.setStyle("-fx-alignment:center");
		dateCol.setPrefWidth(120);
		TableColumn<StockTransfer, String> shopNameCol= new TableColumn<StockTransfer, String>("Branch Name");
		shopNameCol.setCellValueFactory(new PropertyValueFactory<>("branchname"));
		shopNameCol.setStyle("-fx-alignment:center");
		shopNameCol.setPrefWidth(300);
		TableColumn<StockTransfer, Long> transferNoCol= new TableColumn<StockTransfer, Long>("  Stock"+"\nTransfer No");
		transferNoCol.setCellValueFactory(new PropertyValueFactory<>("transferNo"));
		transferNoCol.setStyle("-fx-alignment:center");
		transferNoCol.setPrefWidth(150);
//		TableColumn<StockTransfer, String> productNameCol = new TableColumn<StockTransfer, String>("productname");
//		productNameCol.setCellValueFactory(new PropertyValueFactory<>("productname"));
//		productNameCol.setStyle("-fx-alignment:center");
//		TableColumn<StockTransfer, Long> quantityCol= new TableColumn<StockTransfer, Long>("Quantity");
//		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//		
//		TableColumn<StockTransfer, String> rateCol= new TableColumn<StockTransfer, String>("Rate");
//		rateCol.setCellValueFactory(new PropertyValueFactory<>("rateString"));
//		rateCol.setStyle("-fx-alignment:center");
		
		TableColumn<StockTransfer, String> totalCol= new TableColumn<StockTransfer, String>("Total");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
		totalCol.setStyle("-fx-alignment:center");
		totalCol.setPrefWidth(150);
		TableColumn<StockTransfer, StockTransfer> actionCol= new TableColumn<StockTransfer, StockTransfer>("Action");
		actionCol.setCellValueFactory(e->new ReadOnlyObjectWrapper<>(e.getValue()));
		actionCol.setStyle("-fx-alignment:center");
		actionCol.setCellFactory(e->new TableCell<StockTransfer,StockTransfer>(){
			JFXButton deleteBtn = new JFXButton("Delete");

			@Override
		    protected void updateItem(StockTransfer stockTransfer,boolean empty){
				if(stockTransfer==null){
					setGraphic(null);
					return;
				}
					deleteBtn=new JFXButton("Delete");
					setGraphic(deleteBtn);
					
//					deleteBtn.setDisable(false);
					deleteBtn.getStyleClass().add("del-btn");
					deleteBtn.setAlignment(Pos.CENTER);
					deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
						boolean result1;
						@Override
						public void handle(ActionEvent arg0) {
							// TODO Auto-generated method stub
							Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to delete?");
							alert.setTitle("Confirmation");
//							alert.setHeaderText("HI");
							Optional<ButtonType> result= alert.showAndWait();
							if(result.get()==ButtonType.OK){
							result1=inventoryDao.deleteTransfer(stockTransfer);
							
							showTransferView(anchorPane);
							Main.transferView.setItems(stockSortedData);
							Main.transferView.requestFocus();
							Main.transferView.getSelectionModel().selectLast();
							Main.transferView.getFocusModel().focusNext();
//
							Main.transferView.setMinSize(900, 500);
							anchorPane.getChildren().set(0, Main.transferView);
//								getTableView().getItems().remove(stockTransfer);
							getTableView().getItems().remove(stockTransfer);
							getTableView().refresh();
								
							}

						}
					});

			}
		});
		transferView.getColumns().addAll(srNoCol,dateCol,transferNoCol,shopNameCol,totalCol,actionCol);
		
		List<StockTransfer> transferList= inventoryDao.showTransferList();
		long srNo=1;
		for(StockTransfer st:transferList) {
			String total=BigDecimal.valueOf(st.getTotal())
					.setScale(2,RoundingMode.HALF_UP)
					.toPlainString();
			stockTransferData.add(new StockTransfer(st.getId(),srNo,st.getTransferNo(),st.getBranchname(),st.getEntryDate(),st.getTotal(),total));
			srNo++;
		}
		
		stockFilteredData= new FilteredList<>(stockTransferData,p->true);
		stockSortedData= new SortedList<>(stockFilteredData);
		stockSortedData.comparatorProperty().bind(transferView.comparatorProperty());
		transferView.setItems(stockSortedData);
		
		return transferView;
		
	}
	
	
	public boolean validateNewStock() {
		if (datep.getValue() == null) {
			errorTip.setText("Please select date!");
			datep.setTooltip(errorTip);
			errorTip.show(datep, 500, 180);
			return false;
		}

		if (selectProdCombo.getValue() == null) {
			errorTip.setText("Please select product!");
			selectProdCombo.setTooltip(errorTip);
			errorTip.show(selectProdCombo, 500, 270);
			return false;
		}
		if (qtyText.getText()==null) {
			errorTip.setText("Please select quantity!");
			qtyText.setTooltip(errorTip);
			errorTip.show(qtyText, 500, 360);
			return false;
		}
		if (!qtyText.getText().matches("[0-9]+")) {
			errorTip.setText("quantity must be a number!");
			qtyText.setTooltip(errorTip);
			errorTip.show(qtyText, 500, 360);
			return false;
		}
		if (Double.parseDouble(totalTxt.getText()) == 0) {
			errorTip.setText("Total cannot be zero");
			totalTxt.setTooltip(errorTip);
			errorTip.show(totalTxt, 1000, 150);
			// totErrLbl.setText("Total cannot be zero");
			// totErrLbl.setMinWidth(150);
			return false;
		}
		if(!totalTxt.getText().matches("^[0-9]*\\.?[0-9]*$")) {
			errorTip.setText("Total cannot be negative");
			totalTxt.setTooltip(errorTip);
			errorTip.show(totalTxt, 1000, 150);
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
	
	
	
	public boolean validateStock() {
		
		if(stockRateTxt.getText()==null) {
			errorTip.setText("Please enter cost price");
			stockRateTxt.setTooltip(errorTip);
			errorTip.show(stockRateTxt, 500, 360);
			return false;
		}
		if(!stockRateTxt.getText().matches("^[0-9]*\\.?[0-9]*$")) {
			errorTip.setText("Please enter valid cost Price!");
			stockRateTxt.setTooltip(errorTip);
			errorTip.show(stockRateTxt, 500, 360);
			return false;
		}
		if(qtyText.getText()==null) {
			errorTip.setText("Please enter quantity!");
			qtyText.setTooltip(errorTip);
			errorTip.show(qtyText, 500, 360);
			return false;
		}
		if (!qtyText.getText().matches("[0-9]+")) {
			errorTip.setText("quantity must be a number!");
			qtyText.setTooltip(errorTip);
			errorTip.show(qtyText, 500, 360);
			return false;
		}
		
		return true;
	}
	
	public void clearProductData() {
		prodTxt.setValue(null);
		rateTxt.setText("0.00");
		quantityTxt.setText("0");
		totalTxt.setText("0.00");
		
	}
	
	public boolean validateOnAddEdit() {
		if(prodTxt.getValue()==null) {
			errorTip.setText("Please select Product");
			prodTxt.setTooltip(errorTip);
			errorTip.show(prodTxt,100,160);
			return false;
		}
		if(Double.parseDouble(totalTxt.getText())==0){
//			totErrLbl.setText("Total cannot be zero");
			errorTip.setText("Total cannot be zero");
			errorTip.setWrapText(true);
			totalTxt.setTooltip(errorTip);
			errorTip.show(totalTxt,500,150);

//			totErrLbl.setMinWidth(150);
			return false;
		}
		if(!(totalTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
//			totErrLbl.setText("Total can not be negative");
			errorTip.setText("Total can not be negative");
			errorTip.setWrapText(true);
			totalTxt.setTooltip(errorTip);

			errorTip.show(totalTxt,500,150);
//			totErrLbl.setMinWidth(150);
			return false;
		}
		
		return true;
	}
	
	public boolean validateStockProductControls() {
		if(quantityTxt.getText().isEmpty()||!(quantityTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
//			 quantityErrLbl.setMinWidth(150);
			 errorTip.setText("Quantity must be a positive number!");
			 quantityTxt.setTooltip(errorTip);
			 errorTip.show(quantityTxt,400,150);
//			quantityErrLbl.setText("");
			return false;
		}
		 if(rateTxt.getText().isEmpty()||!(rateTxt.getText().matches("^[0-9]*\\.?[0-9]*$"))){
//			 rateErrLbl.setMinWidth(150);
//			rateErrLbl.setText("Rate must be a positive number!");
			 errorTip.setText("Rate must be a positive number!");
			 rateTxt.setTooltip(errorTip);
			 errorTip.show(rateTxt,500,150);
			 return false;
		}
		 return true;
	}
	
	public boolean validateOnTransferSubmit() {
		if(transferDatePick.getValue()==null) {
			 errorTip.setText("Please enter entry Date!");
			 transferDatePick.setTooltip(errorTip);
			 errorTip.show(transferDatePick,300,80);
			 return false;
		 }
		if(branchCombo.getValue()==null) {
			errorTip.setText("Please select/enter Branch Name");
			branchCombo.setTooltip(errorTip);
			errorTip.show(branchCombo,450,80);
			return false;
		}
		 
		 if(Double.parseDouble(grTotalTxt.getText())==0) {
//				grTotErrLbl.setText("Grand Total cannot be zero");
//				grTotErrLbl.setMinWidth(100);
				errorTip.setText("Grand Total cannot be zero!");
				grTotalTxt.setTooltip(errorTip);
				errorTip.show(grTotalTxt,300,500);
				return false;
			}
		 
		 return true;
	}
	
	public boolean validateOnInwardSubmit() {
		if(inwardDatePick.getValue()==null) {
			 errorTip.setText("Please enter entry Date!");
			 inwardDatePick.setTooltip(errorTip);
			 errorTip.show(inwardDatePick,300,80);
			 return false;
		 }
//		if(branchCombo.getValue()==null) {
//			errorTip.setText("Please select/enter Branch Name");
//			branchCombo.setTooltip(errorTip);
//			errorTip.show(branchCombo,450,50);
//			return false;
//		}
		 
		 if(Double.parseDouble(grTotalTxt.getText())==0) {
//				grTotErrLbl.setText("Grand Total cannot be zero");
//				grTotErrLbl.setMinWidth(100);
				errorTip.setText("Grand Total cannot be zero!");
				grTotalTxt.setTooltip(errorTip);
				errorTip.show(grTotalTxt,300,500);
				return false;
			}
		 
		 return true;
	}
}
