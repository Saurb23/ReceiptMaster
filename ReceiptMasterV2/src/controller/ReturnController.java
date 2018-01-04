package controller;


import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import dao.PurchaseDAO;
import dao.ReturnDAO;
import dao.SalesDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.Product;
import model.PurchaseEntry;
import model.PurchaseProduct;
import model.PurchaseReturn;
import model.SalesEntry;
import model.SalesProduct;
import model.SalesReturn;
import model.Supplier;
import net.sf.jasperreports.engine.JRException;

public class ReturnController {
	
	ObservableList<SalesReturn> returnData= FXCollections.observableArrayList();
	public SortedList<SalesReturn> returnSortedList;
	public FilteredList<SalesReturn> returnFilteredList;
	
	ObservableList<PurchaseReturn> purReturnData=FXCollections.observableArrayList();
	public SortedList<PurchaseReturn> purRetSortedList;
	public FilteredList<PurchaseReturn> purRetFilteredList;
	
	PurchaseDAO purchaseDAO= new PurchaseDAO();
	
	SalesDAO salesDAO=new SalesDAO();
	ReturnDAO returnDAO= new ReturnDAO();
	Tooltip errorTip = new Tooltip();
	List<SalesProduct> productList=new ArrayList<>();
	List<PurchaseProduct> purProdList= new ArrayList<>();
	
	JFXComboBox<String> invoiceTxt;
	JFXComboBox<String> productCombo;
	JFXTextField returnQtyTxt;
	JFXTextField remainQtyTxt;
	JFXTextField qtyTxt;
	JFXTextField sellPriceTxt;
	JFXTextField actualReturnTxt;
	JFXTextField grTotalTxt;
	
	JFXComboBox<String> supplierCombo;
	JFXTextField buyPriceTxt;
	ObservableSet<String> purProdData=FXCollections.observableSet();
	
	Sales_Return_Generator sales_Return_Generator= new Sales_Return_Generator();
	Purchase_Return_Generaotr purchase_Return_Generaotr= new Purchase_Return_Generaotr();
	
	long salesProductId=0;
	long purchaseProductId=0;
	long purchaseEntryId=0;
	long supplierId=0;
	long productId=0;
	long purchaseQty=0;
	long returnQty=0;
	
	
	public GridPane returnSalesGoods(AnchorPane anchorPane) {
		GridPane gp=new GridPane();
		errorTip.setAutoHide(true);
		gp.getStyleClass().add("grid");
		gp.setHgap(10);
		gp.setVgap(30);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(0, 10, 0, 10));
		
		Label titleLabel = new Label("Sales Product Return");
		GridPane.setMargin(titleLabel, new Insets(0,-200,0,200));
		gp.add(titleLabel, 0, 0);

		invoiceTxt= new JFXComboBox<>();
		invoiceTxt.setLabelFloat(true);
		invoiceTxt.setPromptText("Select Invoice Number");
		invoiceTxt.setPrefWidth(150);
		invoiceTxt.setStyle("-fx-font-size:14");
		invoiceTxt.getStyleClass().add("jf-combo-box");
		
		
		List<SalesEntry> saleInvoiceList=salesDAO.getSalesInvoiceList();
		System.out.println(saleInvoiceList.size());
		ObservableList<String> invoiceData=FXCollections.observableArrayList();
		for(SalesEntry se:saleInvoiceList) {
			invoiceData.add(String.valueOf(se.getInvoice_no()));
		}
		 
		invoiceTxt.setItems(invoiceData);
		new AutoCompleteComboBoxListener<>(invoiceTxt);
		
		invoiceTxt.getEditor().textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}
		});
		
		JFXTextField custmerTxt=new JFXTextField();
		custmerTxt.setLabelFloat(true);
		custmerTxt.setPromptText("Customer Name");
		custmerTxt.setEditable(false);
		custmerTxt.setStyle("-fx-font-size:14");
		
		JFXTextField invoiceDate= new JFXTextField();
		invoiceDate.setLabelFloat(true);
		invoiceDate.setPromptText("Invoice Date");
		invoiceDate.setEditable(false);
		invoiceDate.setStyle("-fx-font-size:14");
		
		
		productCombo= new JFXComboBox<>();
		productCombo.setLabelFloat(true);
		productCombo.setPromptText("Select Product");
		productCombo.setPrefWidth(150);
		productCombo.setStyle("-fx-font-size:14");
		
		productCombo.getEditor().textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}
		});
		
		 sellPriceTxt= new JFXTextField();
		sellPriceTxt.setLabelFloat(true);
		sellPriceTxt.setPromptText("Selling Price");
		sellPriceTxt.setStyle("-fx-font-size:14");
		
		qtyTxt= new JFXTextField();
		qtyTxt.setLabelFloat(true);
		qtyTxt.setPromptText("Sales Quantity");
		qtyTxt.setEditable(false);
		qtyTxt.setStyle("-fx-font-size:14");
		
		returnQtyTxt= new JFXTextField();
		returnQtyTxt.setLabelFloat(true);
		returnQtyTxt.setPromptText("Return Quantity");
		returnQtyTxt.setStyle("-fx-font-size:14");
		
		validateOnFocus(returnQtyTxt);
		
		 grTotalTxt= new JFXTextField();
		grTotalTxt.setLabelFloat(true);
		grTotalTxt.setEditable(false);
		grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
		grTotalTxt.setFocusColor(Color.TRANSPARENT);
		grTotalTxt.setPromptText("Grand Total");
		grTotalTxt.setStyle("-fx-font-size: 15px; -fx-font-weight:bold;");
		grTotalTxt.setText("00.00");
		grTotalTxt.setPrefWidth(100);
//		grTotalTxt.setMaxWidth(100);
		
		
		invoiceTxt.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
			if(invoiceTxt.getValue()!=null) {
				
				for(SalesEntry s:saleInvoiceList) {
				
					if(invoiceTxt.getValue().equals(String.valueOf(s.getInvoice_no()))) {
						invoiceDate.setText(s.getEntry_date().toString());
						custmerTxt.setText(s.getCustomer().getFull_name());
						productList=salesDAO.getProductDetails(s);
						ObservableList<String> prodData=FXCollections.observableArrayList();
						for(SalesProduct sp:productList) {
							prodData.add(sp.getProd_name());
							
						}
						productCombo.setItems(prodData);
						new AutoCompleteComboBoxListener<>(productCombo);
					}
					
				}
				
			}
			}
		});
		
		remainQtyTxt= new JFXTextField();
		remainQtyTxt.setLabelFloat(true);
		remainQtyTxt.setEditable(false);
		remainQtyTxt.setPromptText("Returned Quantity");
		remainQtyTxt.setStyle("-fx-font-size:14");
		
	
		
		productCombo.setOnAction(new EventHandler<ActionEvent>() {
			long remainQty=0;
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(productCombo.getValue()!=null) {
					for(SalesProduct sp:productList) {
						if(sp.getProd_name().equals(productCombo.getValue())) {
							 sellPriceTxt.setText(String.valueOf(sp.getProduct().getSellPrice()));
							 qtyTxt.setText(String.valueOf(sp.getQuantity()));
							 salesProductId=sp.getId();
						}
						
					List<SalesReturn> returnList=returnDAO.getReturnDetails(Long.parseLong(invoiceTxt.getValue()));
					for(SalesReturn sr:returnList) {
						if(sr.getSalesProduct().getId()==salesProductId) {
							remainQty=remainQty+sr.getReturnQuantity();
						}
					}
					remainQtyTxt.setText(String.valueOf(remainQty));
//					System.out.println(remainQty);
					remainQty=0;
					}
					if(!returnQtyTxt.getText().isEmpty()&&!sellPriceTxt.getText().isEmpty()) {
						double grTotal=(Double.parseDouble(sellPriceTxt.getText()))*(Double.parseDouble(returnQtyTxt.getText()));
						grTotalTxt.setText(String.format("%.2f", grTotal));
					}
					
				}
			}
		});
		
		
		
		
		returnQtyTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(!returnQtyTxt.getText().isEmpty()&&!sellPriceTxt.getText().isEmpty()) {
					if(!returnQtyTxt.getText().matches("\\d+")) {
						errorTip.setText("Return quantity must be a number");
						returnQtyTxt.setTooltip(errorTip);
						errorTip.show(returnQtyTxt,800,300);
						returnQtyTxt.requestFocus();
						return;
					}
					double grTotal=(Double.parseDouble(sellPriceTxt.getText()))*(Double.parseDouble(returnQtyTxt.getText()));
					grTotalTxt.setText(String.format("%.2f", grTotal));
				}
			}
		});
		
		
	
		
		HBox invoiceDetailHB= new HBox();
		invoiceDetailHB.setSpacing(20);
		invoiceDetailHB.getChildren().addAll(invoiceTxt,invoiceDate,custmerTxt);
		
		HBox prodDetailHB= new HBox();
		prodDetailHB.setSpacing(20);
		prodDetailHB.getChildren().addAll(productCombo,sellPriceTxt,qtyTxt,remainQtyTxt,returnQtyTxt,grTotalTxt);
		
		gp.add(invoiceDetailHB, 0, 2);
		
		gp.add(prodDetailHB, 0, 4);
		
		JFXButton submitBtn= new JFXButton("Submit");
		GridPane.setMargin(submitBtn, new Insets(0,-50,0,50));
		gp.add(submitBtn, 0, 6);
		
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;
			@Override
			public void handle(ActionEvent event) {
				if(!validateOnSalesReturn()) {
					return;
				}
//				SalesEntry salesEntry= new SalesEntry();
				
				
				SalesReturn salesReturn= new SalesReturn();
				salesReturn.setSalesEntry(new SalesEntry());
				salesReturn.setSalesProduct(new SalesProduct());
				salesReturn.setProduct(new Product());
				salesReturn.getSalesEntry().setId(Long.parseLong(invoiceTxt.getValue()));
				for(SalesProduct sp:productList) {
					if(productCombo.getValue().equals(sp.getProd_name())) {
						salesReturn.getSalesProduct().setId(sp.getId());
						salesReturn.getProduct().setId(sp.getProduct().getId());
						salesReturn.getProduct().setQuantity(sp.getProduct().getQuantity());
						salesReturn.setReturnQuantity(Long.parseLong(returnQtyTxt.getText()));
					}
				}
				
				result=returnDAO.returnSalesGoods(salesReturn);
				if(result) {
					Alert alert = new Alert(AlertType.INFORMATION, "Sales Return accepted");
					alert.setTitle("Success Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
					Main.dialog.close();
					showSalesReturn(anchorPane);
					
					Main.saleReturnView.setItems(returnSortedList);
					Main.saleReturnView.requestFocus();
					Main.saleReturnView.getSelectionModel().selectLast();
					Main.saleReturnView.getFocusModel().focusNext();

					Main.saleReturnView.setMinSize(900, 500);
					anchorPane.getChildren().set(0, Main.saleReturnView);
					try {
						sales_Return_Generator.Sales_ReturnReportPdf(salesReturn);
					} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR,
							"Error while generating data..!Please check database connection");
					alert.setTitle("Error Message");
					alert.setHeaderText("HI");
					alert.showAndWait();

				}
				
				
			}
		});
		
		
		gp.addEventFilter(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode()==KeyCode.ENTER) {
				submitBtn.fire();
				e.consume();
			}
		});
//		gp.add(grTotalTxt, 0, 6);
		
		return gp;
		
	}
	
	public TableView<SalesReturn> showSalesReturn(AnchorPane anchorPane) {
		TableView<SalesReturn> returnView= new TableView<SalesReturn>();
		returnData.clear();
		TableColumn<SalesReturn, Long> srNoCol= new TableColumn<SalesReturn, Long>("Sr.No.");
		srNoCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));
		srNoCol.setPrefWidth(100);
		srNoCol.setStyle("-fx-alignment:center");
		TableColumn<SalesReturn, Date> dateCol= new TableColumn<SalesReturn, Date>("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
		dateCol.setPrefWidth(200);
		dateCol.setStyle("-fx-alignment:center");
		TableColumn<SalesReturn, String> productCol= new TableColumn<SalesReturn, String>("Product");
		productCol.setCellValueFactory(new PropertyValueFactory<>("prodName"));
		productCol.setPrefWidth(200);
		productCol.setStyle("-fx-alignment:center");
		TableColumn<SalesReturn, String> invoiceCol= new TableColumn<SalesReturn, String>("Invoice No");
		invoiceCol.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
		invoiceCol.setPrefWidth(200);
		invoiceCol.setStyle("-fx-alignment:center");
		TableColumn<SalesReturn, Long> quantityCol= new TableColumn<SalesReturn, Long>("Return"+"\nQuantity");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("returnQuantity"));
		quantityCol.setPrefWidth(200);
		quantityCol.setStyle("-fx-alignment:center");
		List<SalesReturn> returnList=returnDAO.showSalesReturn();
//		System.out.println(returnList.size());
		long srNo=1;
		double returnQty=0;
		for(int i=0;i<returnList.size();i++) {
			if(i>0) {
			if(returnList.get(i).getSalesEntry().getId()==returnList.get(i-1).getSalesEntry().getId()) {
				if(returnList.get(i).getProduct().getId()==returnList.get(i-1).getProduct().getId()) {
					returnQty=returnQty+returnList.get(i).getReturnQuantity();
				}
				
			}
			}
			
			String invoiceNo=String.valueOf(returnList.get(i).getSalesEntry().getId());
			returnData.add(new SalesReturn(srNo,returnList.get(i).getId(),returnList.get(i).getProduct().getProduct_name(),invoiceNo,returnList.get(i).getReturnDate(),returnList.get(i).getReturnQuantity(),
					returnList.get(i).getProduct()));
			
			
			srNo++;
			returnQty=0;
		}
		
		returnView.getColumns().addAll(srNoCol,dateCol,invoiceCol,productCol,quantityCol);
		returnFilteredList= new FilteredList<>(returnData,p->true);
		
		returnSortedList=new SortedList<>(returnFilteredList);
		returnSortedList.comparatorProperty().bind(returnView.comparatorProperty());
		returnView.setItems(returnSortedList);
		
		return returnView; 
				
	}
	
	public GridPane returnPurchaseGoods(AnchorPane anchorPane) {
		
		purProdData.clear();
		GridPane gp=new GridPane();
		errorTip.setAutoHide(true);
		gp.getStyleClass().add("grid");
		gp.setHgap(10);
		gp.setVgap(30);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(0, 10, 0, 10));
		
		Label titleLabel = new Label("Purchase Product Return");
		GridPane.setMargin(titleLabel, new Insets(0,-200,0,200));
		gp.add(titleLabel, 0, 0);
		
		supplierCombo=new JFXComboBox<>();
		supplierCombo.setLabelFloat(true);
		supplierCombo.setPromptText("Select Supplier Name");
		supplierCombo.setPrefWidth(300);
		supplierCombo.setStyle("-fx-font-size:14");
		supplierCombo.getStyleClass().add("jf-combo-box");
		
		List<PurchaseEntry> purchaseList=purchaseDAO.getSupplierList();
//		System.out.println(purchaseList.size());
		
//		List<Supplier> supplierList=purchaseDAO.getSupplierNames();
		
		ObservableList<String> supplierData=FXCollections.observableArrayList();
//		for(PurchaseEntry p:purchaseList) {
		for(int i=0;i<purchaseList.size();i++) {
			if(i>0) {
				if(purchaseList.get(i).getSupplier().getSupplier_id()==purchaseList.get(i-1).getSupplier().getSupplier_id()) {
					continue;
				}
			}
			
			supplierData.add(purchaseList.get(i).getSupplier().getSupplierName());
		}
		
		supplierCombo.setItems(supplierData);
		new AutoCompleteComboBoxListener<>(supplierCombo);
		
		productCombo=new JFXComboBox<>();
		productCombo.setLabelFloat(true);
		productCombo.setPromptText("Select Product");
		productCombo.setStyle("-fx-font-size:14");
		productCombo.setPrefWidth(150);
		
		productCombo.getEditor().textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}
		});
		
		supplierCombo.getEditor().textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				errorTip.hide();
			}
		});
		
		
		supplierCombo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(supplierCombo.getValue()!=null) {
					purProdData.clear();
					for(PurchaseEntry pe:purchaseList) {
//						System.out.println("Reached");
						if(pe.getSupplier().getSupplierName().equals(supplierCombo.getValue())) {
						List<PurchaseProduct> productList=purchaseDAO.getProductDetails(pe);
						supplierId=pe.getSupplier().getSupplier_id();
//						System.out.println("size   "+purProdList.size());
						purProdList.clear();
						for(PurchaseProduct p:productList) {
							purProdData.add(p.getProd_name());
							purProdList.add(p);
							
						}
						ObservableList<String> prodData=FXCollections.observableArrayList(purProdData);
						productCombo.setItems(prodData);
						new AutoCompleteComboBoxListener<>(productCombo);
						}
					}
				}
				
			}
		});
		
		buyPriceTxt= new JFXTextField();
		buyPriceTxt.setLabelFloat(true);
		buyPriceTxt.setPromptText("Cost Price");
		buyPriceTxt.setEditable(false);
		buyPriceTxt.setStyle("-fx-font-size:14");
		buyPriceTxt.setPrefWidth(100);
//		buyPriceTxt.setPrefWidth(100);
		
		qtyTxt= new JFXTextField();
		qtyTxt.setLabelFloat(true);
		qtyTxt.setPromptText("Purchase Quantity");
		qtyTxt.setEditable(false);
		qtyTxt.setStyle("-fx-font-size:14");
		qtyTxt.setPrefWidth(100);
		
		actualReturnTxt= new JFXTextField();
		actualReturnTxt.setLabelFloat(true);
		actualReturnTxt.setPromptText("Returned Quantity");
		actualReturnTxt.setEditable(false);
		actualReturnTxt.setStyle("-fx-font-size:14");
		actualReturnTxt.setPrefWidth(100);
		
		remainQtyTxt= new JFXTextField();
		remainQtyTxt.setPromptText("Stock Quantity");
		remainQtyTxt.setLabelFloat(true);
		remainQtyTxt.setEditable(false);
		remainQtyTxt.setStyle("-fx-font-size:14");
		remainQtyTxt.setPrefWidth(100);
		
		returnQtyTxt= new JFXTextField();
		returnQtyTxt.setLabelFloat(true);
		returnQtyTxt.setPromptText("Return Quantity");
		returnQtyTxt.setStyle("-fx-font-size:14");
		returnQtyTxt.setPrefWidth(100);
		validateOnFocus(returnQtyTxt);
		
		grTotalTxt= new JFXTextField();
		grTotalTxt.setLabelFloat(true);
		grTotalTxt.setEditable(false);
		grTotalTxt.setUnFocusColor(Color.TRANSPARENT);
		grTotalTxt.setFocusColor(Color.TRANSPARENT);
		grTotalTxt.setPromptText("Grand Total");
		grTotalTxt.setStyle("-fx-font-size: 15px; -fx-font-weight:bold;");
		grTotalTxt.setText("00.00");
		grTotalTxt.setPrefWidth(100);
		
		GridPane.setMargin(supplierCombo, new Insets(0,-20,0,20));
		gp.add(supplierCombo, 0, 2);

		
		HBox prodDetailHB= new HBox();
		prodDetailHB.setSpacing(20);
		prodDetailHB.getChildren().addAll(productCombo,buyPriceTxt,qtyTxt,actualReturnTxt,remainQtyTxt,returnQtyTxt,grTotalTxt);
		GridPane.setMargin(prodDetailHB, new Insets(0,-20,0,20));
		gp.add(prodDetailHB, 0, 4);
		
		productCombo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(productCombo.getValue()!=null) {
					 purchaseQty=0;
					 returnQty=0;
					PurchaseReturn purchaseReturn= new PurchaseReturn();
					purchaseReturn.setProduct(new Product());
					purchaseReturn.setSupplier(new Supplier());
					purchaseReturn.getSupplier().setSupplier_id(supplierId);
					for(PurchaseProduct p:purProdList) {
						if(productCombo.getValue().equals(p.getProd_name())) {
							buyPriceTxt.setText(String.valueOf(p.getProduct().getBuyPrice()));
							purchaseQty=purchaseQty+p.getQuantity();
							remainQtyTxt.setText(String.valueOf(p.getProduct().getQuantity()));
							purchaseProductId=p.getId();
							purchaseReturn.getProduct().setId(p.getProduct().getId());
						}
					}
					
					List<PurchaseReturn> returnList=returnDAO.getPurchaseReturn(purchaseReturn);
//					System.out.println(returnList.size());
					for(PurchaseReturn pr:returnList) {
						returnQty=returnQty+pr.getReturnQuantity();
						System.out.println(pr.getReturnQuantity());
					}
					
					actualReturnTxt.setText(String.valueOf(returnQty));
					
					qtyTxt.setText(String.valueOf(purchaseQty));
					
					if(!returnQtyTxt.getText().isEmpty()&&!buyPriceTxt.getText().isEmpty()) {
						double grTotal=(Double.parseDouble(buyPriceTxt.getText()))*(Double.parseDouble(returnQtyTxt.getText()));
						grTotalTxt.setText(String.format("%.2f", grTotal));
					}
					
					 purchaseQty=0;
					 returnQty=0;
				}
			}
		});
		
		returnQtyTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(!returnQtyTxt.getText().isEmpty()&&!buyPriceTxt.getText().isEmpty()) {
					double grTotal=(Double.parseDouble(buyPriceTxt.getText()))*(Double.parseDouble(returnQtyTxt.getText()));
					grTotalTxt.setText(String.format("%.2f", grTotal));
				}
			}
		});
		
		JFXButton submitBtn= new JFXButton("Submit");
		GridPane.setMargin(submitBtn, new Insets(0,-50,0,50));
		gp.add(submitBtn, 0, 6);
		
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(!validateOnPurchaseReturn()) {
					return;
				}
				
				PurchaseReturn purchaseReturn= new PurchaseReturn();
				purchaseReturn.setSupplier(new Supplier());
				purchaseReturn.getSupplier().setSupplier_id(supplierId);
				purchaseReturn.setPurchaseEntry(new PurchaseEntry());
				purchaseReturn.getPurchaseEntry().setId(purchaseEntryId);
				purchaseReturn.setPurchaseProduct(new PurchaseProduct());
//				purchaseReturn.getPurchaseProduct().setId(purchaseProductId);
				purchaseReturn.setProduct(new Product());
				
				for(PurchaseProduct pp:purProdList) {
					if(productCombo.getValue().equals(pp.getProd_name())) {
						purchaseReturn.getPurchaseProduct().setId(pp.getId());
						purchaseReturn.getProduct().setId(pp.getProduct().getId());
						purchaseReturn.getProduct().setQuantity(pp.getProduct().getQuantity());
						purchaseReturn.setReturnQuantity(Long.parseLong(returnQtyTxt.getText()));
						break;
					}
				}
				
				result=returnDAO.returnPurchaseGoods(purchaseReturn);
				if(result) {
					Alert alert = new Alert(AlertType.INFORMATION, "Purchase Return Generated");
					alert.setTitle("Success Message");
					alert.setHeaderText("HI");
					alert.showAndWait();
					Main.dialog.close();
					showPurchaseReturn(anchorPane);
					
					Main.purReturnView.setItems(purRetSortedList);
					Main.purReturnView.requestFocus();
					Main.purReturnView.getSelectionModel().selectLast();
					Main.purReturnView.getFocusModel().focusNext();

					Main.purReturnView.setMinSize(900, 500);
					anchorPane.getChildren().set(0, Main.purReturnView);
					try {
						purchase_Return_Generaotr.Purchase_ReturnReportPdf(purchaseReturn);
					} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR,
							"Error while updating data..!Please check database connection");
					alert.setTitle("Error Message");
					alert.setHeaderText("HI");
					alert.showAndWait();

				
				}
				
			}
		});
		
		gp.addEventFilter(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode()==KeyCode.ENTER) {
				submitBtn.fire();
				e.consume();
			}
		});
		
		return gp;
	}
	
	
	public TableView<PurchaseReturn> showPurchaseReturn(AnchorPane anchorPane){
		TableView<PurchaseReturn> returnView= new TableView<PurchaseReturn>();
		purReturnData.clear();
		
		TableColumn<PurchaseReturn, Long> srNoCol= new TableColumn<>("Sr. No");
		srNoCol.setCellValueFactory(new PropertyValueFactory<>("srNo"));
		srNoCol.setStyle("-fx-alignment:center");
		srNoCol.setPrefWidth(100);
		TableColumn<PurchaseReturn, Date> dateCol= new TableColumn<PurchaseReturn, Date>("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
		dateCol.setStyle("-fx-alignment:center");
		dateCol.setPrefWidth(200);
		TableColumn<PurchaseReturn, String> supplierCol= new TableColumn<PurchaseReturn, String>("Supplier");
		supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
		supplierCol.setStyle("-fx-alignment:center");
		supplierCol.setPrefWidth(200);
		TableColumn<PurchaseReturn, String> productCol=new TableColumn<PurchaseReturn, String>("Product");
		productCol.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
		productCol.setStyle("-fx-alignment:center");
		productCol.setPrefWidth(200);
		TableColumn<PurchaseReturn, Long> quantityCol= new TableColumn<PurchaseReturn, Long>("Return\n"+"Quantity.");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("returnQuantity"));
		quantityCol.setStyle("-fx-alignment:center");
		quantityCol.setPrefWidth(200);
		List<PurchaseReturn> returnList=returnDAO.showPurchaseReturn();
		long srNo=1;
		long returnQty=0;
		for(int i=0;i<returnList.size();i++) {
			if(i>0) {
				if(returnList.get(i).getPurchaseEntry().getSupplier().getSupplier_id()==returnList.get(i-1).getPurchaseEntry().getSupplier().getSupplier_id()) {
					if(returnList.get(i).getProduct().getId()==returnList.get(i-1).getProduct().getId()) {
						returnQty=returnQty+returnList.get(i).getReturnQuantity();
					}
				}
			}
			
			purReturnData.add(new PurchaseReturn(srNo,returnList.get(i).getId(),returnList.get(i).getReturnDate(),returnList.get(i).getSupplierName(),returnList.get(i).getProd_name(),returnList.get(i).getProduct(),returnList.get(i).getReturnQuantity()));
			srNo++;
			returnQty=0;
			
		}
		
		returnView.getColumns().addAll(srNoCol,dateCol,supplierCol,productCol,quantityCol);
		purRetFilteredList=new FilteredList<>(purReturnData,p->true);
		purRetSortedList= new SortedList<>(purRetFilteredList);
		purRetSortedList.comparatorProperty().bind(returnView.comparatorProperty());
		returnView.setItems(purRetSortedList);
		
		return returnView;
	}
	
	public boolean validateOnSalesReturn() {
		if(invoiceTxt.getValue()==null) {
			errorTip.setText("Please select invoice number");
			invoiceTxt.setTooltip(errorTip);
			errorTip.show(invoiceTxt,250,200);
			invoiceTxt.requestFocus();
			return false;
		}
		
		if(productCombo.getValue()==null) {
			errorTip.setText("Please select product");
			productCombo.setTooltip(errorTip);
			errorTip.show(productCombo,250,350);
			productCombo.requestFocus();
			return false;
		}
		if(returnQtyTxt.getText().isEmpty()) {
			errorTip.setText("Please enter return quantity");
			returnQtyTxt.setTooltip(errorTip);
			errorTip.show(returnQtyTxt,800,300);
			returnQtyTxt.requestFocus();
			return false;
		}
		if(!returnQtyTxt.getText().matches("\\d+")) {
			errorTip.setText("Return quantity must be a number");
			returnQtyTxt.setTooltip(errorTip);
			errorTip.show(returnQtyTxt,800,300);
			returnQtyTxt.requestFocus();
			return false;
		}
		
		if(!qtyTxt.getText().isEmpty()&&!remainQtyTxt.getText().isEmpty()) {
			
		if(Double.parseDouble(qtyTxt.getText())==Double.parseDouble(remainQtyTxt.getText())) {
			errorTip.setText("All quantities are already returned");
			qtyTxt.setTooltip(errorTip);
			errorTip.show(qtyTxt,500,300);
			returnQtyTxt.requestFocus();
			return false;
		}
		
		
		double remainQty=Double.parseDouble(qtyTxt.getText())-Double.parseDouble(remainQtyTxt.getText());
		
		if(Double.parseDouble(returnQtyTxt.getText())>remainQty){
			errorTip.setText("Return quantity must not exceed already returned quantity");
			returnQtyTxt.setTooltip(errorTip);
			errorTip.show(returnQtyTxt,600,300);
			returnQtyTxt.requestFocus();
			return false;
		}
		}
		
		if(grTotalTxt.getText().equals("00.00")) {
			errorTip.setText("Grand Total can not be zero");
			grTotalTxt.setTooltip(errorTip);
			errorTip.show(grTotalTxt,950,300);
//			grTotalTxt.requestFocus();
			return false;
		}
		
		return true;
	}
	
	public boolean validateOnPurchaseReturn() {
		if(supplierCombo.getValue()==null) {
			errorTip.setText("Please enter supplier name");
			supplierCombo.setTooltip(errorTip);
			errorTip.show(supplierCombo,250,200);
			supplierCombo.requestFocus();
			return false;
		}
		
		if(productCombo.getValue()==null) {
			errorTip.setText("Please select product");
			productCombo.setTooltip(errorTip);
			errorTip.show(productCombo,250,400);
			productCombo.requestFocus();
			return false;
		}
		if(returnQtyTxt.getText().isEmpty()) {
			errorTip.setText("Please enter return quantity ");
			returnQtyTxt.setTooltip(errorTip);
			errorTip.show(returnQtyTxt,800,300);
			returnQtyTxt.requestFocus();
			return false;
		}
		
		if(!returnQtyTxt.getText().matches("\\d+")) {
			errorTip.setText("Return quantity must be a number");
			returnQtyTxt.setTooltip(errorTip);
			errorTip.show(returnQtyTxt,800,300);
			returnQtyTxt.requestFocus();
			return false;
		}
		if(!qtyTxt.getText().isEmpty()&&!remainQtyTxt.getText().isEmpty()) {
			
			if(Double.parseDouble(returnQtyTxt.getText())>Double.parseDouble(remainQtyTxt.getText())){
				errorTip.setText("Return quantity must not exceed stock quantity");
				returnQtyTxt.setTooltip(errorTip);
				errorTip.show(returnQtyTxt,600,300);
				returnQtyTxt.requestFocus();
				return false;
			}
			if(Double.parseDouble(remainQtyTxt.getText())==0) {
				errorTip.setText("Stock is already Nil for specified product");
				returnQtyTxt.setTooltip(errorTip);
				errorTip.show(returnQtyTxt,600,300);
				returnQtyTxt.requestFocus();
				return false;
			}
			}
			
			if(grTotalTxt.getText().equals("00.00")) {
				errorTip.setText("Grand Total can not be zero");
				grTotalTxt.setTooltip(errorTip);
				errorTip.show(grTotalTxt,950,300);
//				grTotalTxt.requestFocus();
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
}
