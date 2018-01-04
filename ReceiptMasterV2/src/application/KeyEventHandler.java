package application;


import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;

import controller.SalesController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;


/****
 * 
 * @author Saurabh Gupta
 *
 */
public class KeyEventHandler {
	
//		Main mainClass=new Main();
	private final static long MILLISECONDS_PER_DAY = 1000L * 60 * 60 * 24;

	List<String> keyCodeCombo= new ArrayList<String>();
	
	public static void singlekeyEvent(Scene scene) {
		
		
		scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
		
			if(e.getCode()==KeyCode.ESCAPE) {
				if(Main.anchorPane.getChildren().contains(Main.gridPane)) {
					if(Main.purchTitlePane.isExpanded()||Main.saleTitlePane.isExpanded()||Main.reportTitilePane.isExpanded()||Main.returnTitlePane.isExpanded()||Main.goodTitlePane.isExpanded()) {
						Main.purchTitlePane.setExpanded(false);
						Main.saleTitlePane.setExpanded(false);
						Main.returnTitlePane.setExpanded(false);
						Main.reportTitilePane.setExpanded(false);
						Main.goodTitlePane.setExpanded(false);
					}else {
					exitSystem();}
				}
				else {
//					System.out.println("Reahced");
						for(Node n:Main.anchorPane.getChildren()) {
							if(n instanceof TableView) {
//							Main.accountTitlePane.setExpanded(false);
							Main.purchTitlePane.setExpanded(false);
							Main.saleTitlePane.setExpanded(false);
							Main.returnTitlePane.setExpanded(false);
							Main.reportTitilePane.setExpanded(false);
							Main.goodTitlePane.setExpanded(false);
							}
						}	
//						Main.hambHB.getChildren().remove(Main.profileMenu);
						Main.anchorPane.getChildren().clear();
						Main.hambHB.setMargin(Main.anchorPane, new Insets(0,0,0,0));
						Main.backImg.fitWidthProperty().bind(scene.widthProperty().subtract(Main.leftDrawer.widthProperty().add(5)));
						Main.backImg.fitHeightProperty().bind(scene.heightProperty().subtract(2));
						Main.hambHB.setMargin(Main.profileMenu, new Insets(10,0,0,-80));
						Main.anchorPane.getChildren().add(Main.gridPane);
//						Main.hambHB.setMargin(Main.anchorPane,new Insets(10,-500,0,600));
//						Main.anchorPane.setRightAnchor(Main.gridPane, 900d);
//						Main.gridPane.getChildren().add(Main.profileMenu);
//						Main.anchorPane.setRightAnchor(Main.gridPane, 900d);
						
//					}
				
				e.consume();
				}
			}
			
			
			
			else if(e.getCode()==KeyCode.P) {
//				System.out.println("PPP");
				if(Main.reportTitilePane.isExpanded()) {
					Main.PurchaseReport.fire();
					e.consume();
				}
				else if(Main.purchTitlePane.isExpanded()) {
					if(Main.anchorPane.getChildren().contains(Main.searchBtn)) {
						if(!Main.searchBtn.isFocused()) {
					Main.prchsEntryBtn.fire();
					e.consume();
					}
					}else {
						Main.prchsEntryBtn.fire();
						e.consume();
					}
				}
				else if(Main.returnTitlePane.isExpanded()) {
					if(Main.anchorPane.getChildren().contains(Main.searchBtn)) {
						if(!Main.searchBtn.isFocused()) {
							Main.purReturnBtn.fire();
							e.consume();
						}
					}else {
					Main.purReturnBtn.fire();
					e.consume();
					}
				}else if(Main.saleTitlePane.isExpanded()) {
					if(Main.anchorPane.getChildren().contains(Main.salesView)) {
						if(!Main.searchBtn.isFocused()) {
							Main.printSalesBtn.fire();
							e.consume();
						}
					
					}
				}
				else if(Main.anchorPane.getChildren().contains(Main.gridPane)) {
					
					Main.purchTitlePane.setExpanded(true);
					e.consume();
					
				}
			}
			else if(e.getCode()==KeyCode.S) {
//				System.out.println("SSS");
				if(Main.purchTitlePane.isExpanded()) {
				if(Main.anchorPane.getChildren().contains(Main.searchBtn)) {
					if(!Main.searchBtn.isFocused()) {
						Main.supplierRecBtn.fire();
						e.consume();
					}
				}else {
					Main.supplierRecBtn.fire();
					e.consume();
				}
				}
				else if(Main.saleTitlePane.isExpanded()) {
					if(Main.anchorPane.getChildren().contains(Main.searchBtn)) {
						if(!Main.searchBtn.isFocused()) {
							Main.salesRecBtn.fire();
							e.consume();
						}
						
					}else {
						Main.salesRecBtn.fire();
						e.consume();
					}
				}
				else if(Main.reportTitilePane.isExpanded()) {
					Main.salesReport.fire();
					e.consume();
				}
				else if(Main.returnTitlePane.isExpanded()) {
					if(Main.anchorPane.getChildren().contains(Main.searchBtn)) {
						if(!Main.searchBtn.isFocused()) {
							Main.saleReturnBtn.fire();
							e.consume();
						}
					}else {
					Main.saleReturnBtn.fire();
					e.consume();
					}
				}
				
				else if(Main.anchorPane.getChildren().contains(Main.gridPane)){
					
					Main.saleTitlePane.setExpanded(true);
					e.consume();
					
				}
			}
			else if(e.getCode()==KeyCode.C) {
//				System.out.println("CCC");
				if(Main.saleTitlePane.isExpanded()) {
					if(Main.anchorPane.getChildren().contains(Main.searchBtn)) {
						if(!Main.searchBtn.isFocused()) {
							Main.createCustBtn.fire();
							e.consume();
						}
					}else {
						Main.createCustBtn.fire();
						e.consume();
					}
				}
				else if(Main.reportTitilePane.isExpanded()) {
					Main.CashReport.fire();
					e.consume();
				}
			}
				else if(e.getCode()==KeyCode.N) {
//					System.out.println("NNN");
					if(Main.purchTitlePane.isExpanded()) {
						if(Main.anchorPane.getChildren().contains(Main.supplierView)) {
							if(!Main.searchBtn.isFocused()) {
							Main.addSupBtn.fire();
							e.consume();
							}
						}
						else if(Main.anchorPane.getChildren().contains(Main.purchaseView)) {
							if(!Main.searchBtn.isFocused()) {
							Main.createPurBtn.fire();
							e.consume();
							}
						}
					}
					else if(Main.saleTitlePane.isExpanded()) {
						if(Main.anchorPane.getChildren().contains(Main.custView)) {
							if(!Main.searchBtn.isFocused()) {
								Main.createNewCustBtn.fire();
								e.consume();
							}
						}
						else if(Main.anchorPane.getChildren().contains(Main.salesView)) {
							if(!Main.searchBtn.isFocused()) {
								Main.createSalesBtn.fire();
								e.consume();
							}
						}
						else if(Main.anchorPane.getChildren().contains(Main.deliveryView)) {
							if(!Main.searchBtn.isFocused()) {
								Main.createDMBtn.fire();
								e.consume();
							}
						}
						else if(Main.anchorPane.getChildren().contains(Main.salesManView)) {
							if(!Main.searchBtn.isFocused()) {
								Main.createSalesManBtn.fire();
								e.consume();
							}
						}
					}
					else if(Main.goodTitlePane.isExpanded()) {
						if(Main.anchorPane.getChildren().contains(Main.stockView)) {
							Main.createStockBtn.fire();
							e.consume();
						}
						else if(Main.anchorPane.getChildren().contains(Main.transferView)) {
							Main.transferStockBtn.fire();
							e.consume();
						}
					}
				
				}
				else if(e.getCode()==KeyCode.U) {
//					System.out.println("UUU");
					if(Main.purchTitlePane.isExpanded()) {
						if(Main.anchorPane.getChildren().contains(Main.supplierView)) {
							if(!Main.searchBtn.isFocused()) {
							Main.editSupBtn.fire();
							e.consume();
							}
						}
					}
					else if(Main.saleTitlePane.isExpanded()) {
						if(Main.anchorPane.getChildren().contains(Main.custView)) {
							if(!Main.searchBtn.isFocused()) {
							Main.editCustBtn.fire();
							e.consume();
							}
						}
						else if(Main.anchorPane.getChildren().contains(Main.salesView)) {
							if(!Main.searchBtn.isFocused()) {
								Main.editSalesBtn.fire();
								e.consume();
							}
						}
						else if(Main.anchorPane.getChildren().contains(Main.salesManView)) {
							if(!Main.searchBtn.isFocused()) {
								Main.editSalesManBtn.fire();
								e.consume();
							}
						}
					}
					
				}
			
				else if(e.getCode()==KeyCode.R) {
//					System.out.println("RRR");
					if(Main.reportTitilePane.isExpanded()) {
						if(Main.anchorPane.getChildren().contains(Main.gridPane)) {
						Main.StockReport.fire();
						e.consume();
						}
					}
					else if(Main.returnTitlePane.isExpanded()) {
						if(Main.anchorPane.getChildren().contains(Main.purReturnView)) {
							if(!Main.searchBtn.isFocused()) {
								Main.returnPurcBtn.fire();
								e.consume();
							}
						}
						else if(Main.anchorPane.getChildren().contains(Main.saleReturnView)) {
							if(!Main.searchBtn.isFocused()) {
								Main.returnSaleBtn.fire();
								e.consume();
							}
						}
					}
					else if(Main.anchorPane.getChildren().contains(Main.gridPane)) {
						
						Main.reportTitilePane.setExpanded(true);
						e.consume();
						
					}
					
				}
				else if(e.getCode()==KeyCode.G) {
//					System.out.println("GG");
					if(Main.anchorPane.getChildren().contains(Main.gridPane)) {
						
						Main.returnTitlePane.setExpanded(true);
						e.consume();
						
					}
				}
				else if(e.getCode()==KeyCode.E) {
//					System.out.println("EEE");
					if(Main.reportTitilePane.isExpanded()) {
						if(Main.anchorPane.getChildren().contains(Main.SalesReportDate)) {
							Main.GenerateBtn.fire();
							e.consume();
						}
						else if(Main.anchorPane.getChildren().contains(Main.PurchaseReportDate)) {
							Main.GenerateBtn.fire();
							e.consume();
						}
					}
				}else if(e.getCode()==KeyCode.D) {
//					System.out.println("DD");
					if(Main.saleTitlePane.isExpanded()) {
					if(Main.anchorPane.getChildren().contains(Main.searchBtn)) {
						if(!Main.searchBtn.isFocused()) {
							Main.dmRecBtn.fire();
							e.consume();
						}
					}else {
						Main.dmRecBtn.fire();
						e.consume();
					}
					}
				}
				else if(e.getCode()==KeyCode.I) {
//					System.out.println("IIII");
					if(Main.saleTitlePane.isExpanded()) {
					if(Main.anchorPane.getChildren().contains(Main.deliveryView)) {
						if(!Main.searchBtn.isFocused()) {
							Main.createDMInvBtn.fire();
							e.consume();
						}
					}
					}
					else if(Main.goodTitlePane.isExpanded()) {
							Main.addStockBtn.fire();
							e.consume();
						
					}
					
				}
				else if(e.getCode()==KeyCode.T) {
//					System.out.println("TTTT");
					 if(Main.anchorPane.getChildren().contains(Main.gridPane)) {
						Main.goodTitlePane.setExpanded(true);
						e.consume();
					}
					}
				else if(e.getCode()==KeyCode.O) {
					if(Main.goodTitlePane.isExpanded()) {
						Main.transferStock.fire();
						e.consume();
					}
				}
				else if(e.getCode()==KeyCode.M) {
					if(Main.saleTitlePane.isExpanded()) {
						Main.salesManRecBtn.fire();
						e.consume();
					}
				}
				
			
				else if(e.getCode()==KeyCode.ENTER) {
//					System.out.println("Reahced");
					if(Main.reportTitilePane.isExpanded()) {
					if(Main.anchorPane.getChildren().contains(Main.saleRetunHB)) {
						if(Main.SalesReportDate.getItems().isEmpty()) {
						Main.ViewBtn.fire();
						e.consume();
						}
					}
					else if(Main.anchorPane.getChildren().contains(Main.purchReturnHB)) {
						if(Main.PurchaseReportDate.getItems().isEmpty()) {
							Main.ViewBtn.fire();
							e.consume();
						}
					}
					else if (Main.anchorPane.getChildren().contains(Main.cashReportHB)) {
						if(Main.CashReportDate.getItems().isEmpty()) {
							Main.ViewBtn.fire();
							e.consume();
						}
					}
					else if(Main.anchorPane.getChildren().contains(Main.stockReportHB)) {
						if(Main.StockReportDate.getItems().isEmpty()) {
							Main.ViewBtn.fire();
							e.consume();
						}
					}
				}
					
				}
				
				
//			System.out.println("Laststs");
			
			
//			else if(createKeyCombo.match(e)) {
////				System.out.println("HI");
//				 if(Main.anchorPane.getChildren().contains(Main.supplierView)) {
//					Main.addSupBtn.fire();
//					e.consume();
//				}
//				else if(Main.anchorPane.getChildren().contains(Main.purchaseView)){
//					Main.createPurBtn.fire();
//					e.consume();
//				}
//				else if(Main.anchorPane.getChildren().contains(Main.custView)) {
//					Main.createNewCustBtn.fire();
//					e.consume();
//				}
//				else if(Main.anchorPane.getChildren().contains(Main.salesView)) {
//					Main.createSalesBtn.fire();
//					e.consume();
//				}
//			
////				event.consume();
//				
//			}else if(updateKeyCombo.match(e)) {
//				 if(Main.anchorPane.getChildren().contains(Main.supplierView)) {
//					Main.editSupBtn.fire();
//					e.consume();
//					
//				}
//				else if(Main.anchorPane.getChildren().contains(Main.purchaseView)) {
//					Main.editPurBtn.fire();
//					e.consume();
//				}
//				else if(Main.anchorPane.getChildren().contains(Main.custView)) {
//					Main.editCustBtn.fire();
//					e.consume();
//				}
//				else if(Main.anchorPane.getChildren().contains(Main.salesView)) {
//					Main.editSalesBtn.fire();
//					e.consume();
//				}
				
//				event.consume();
//			}
			
			
//			else if(e.getCode()==KeyCode.DELETE){
//			if(Main.anchorPane.getChildren().contains(Main.accountView)){
////				Main.accountView.requestFocus();
//				
////			Account account=Main.accountView.getSelectionModel().getSelectedItem();
//            AccountController.delBtn.fire();
//            System.out.println("Reached");
//			e.consume();
//			}
//	}
			
			
			
		});
		
		
	}
	
	public static  void DeleteHandler(Scene scene) {
//		System.out.println("HI");
		final KeyCombination deleteKey= new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
		
		scene.addEventFilter(KeyEvent.KEY_RELEASED, e->{
			if(deleteKey.match(e)){
				if(Main.anchorPane.getChildren().contains(Main.custView)) {
//					SalesController.deleteBtn.fire();
					e.consume();
				}
		}
	
		});


	}
	
	
//	public static void keyComboEvent(Scene scene) {
//		
//		scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
//			if(e.getCode()==KeyCode.C) {
//				Main.accountTitlePane.setExpanded(true);
//				Main.createAccBtn.fire();
//				e.consume();
//			}
//			else if(e.getCode()==KeyCode.J) {
//					Main.accountTitlePane.setExpanded(true);
//					Main.journalBtn.fire();
//					e.consume();
//			}
//			else if(e.getCode()==KeyCode.E) {
//				Main.accountTitlePane.setExpanded(true);
//				Main.journalEntryBtn.fire();
//				e.consume();
//				
//			}
//			
//		});
//		
//		 
//		
//	}
//	
	public static void enterKeyEvent(JFXButton button) {
		button.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if(event.getCode().equals(KeyCode.ENTER)) {
					
				}
			}
		});
	}
	
	public static void dateKeyEvent(DatePicker datePicker) {
		
		final KeyCombination monthUpCombo= new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN);
		final KeyCombination monthDownCOmbo= new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN);
		final KeyCombination yearUpCombo= new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);
		final KeyCombination yearDownCombo= new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);
		
		
		datePicker.getEditor().addEventFilter(KeyEvent.KEY_PRESSED, event-> {
			
			   LocalDate value = datePicker.getValue();
			   if (value == null) {
			      //give default value if there is no one
			      value = LocalDate.now();
//			      event.consume();
			   }
			   Date utilDate =null;
			   
				
			   if (yearUpCombo.match(event)) {
			      //UP pressed, the control and shift are hold down, increase the year
				   if(value.isAfter(LocalDate.now())) {
					   datePicker.setValue(LocalDate.now());
				   }else {
				  
			      datePicker.setValue(value.plusYears(1));
				   }
				   event.consume();
			   } 
			   else if (monthUpCombo.match(event)) {
			      //UP pressed, the control key is hold down, increase the month
				   if(value.isAfter(LocalDate.now())) {
					   datePicker.setValue(LocalDate.now());
				   }else {
			      datePicker.setValue(value.plusMonths(1));
				   }
				   event.consume();
			   }
			   else if (event.getCode() == KeyCode.UP) {
			      //UP pressed, no modifier keys hold down, increase the day
				   if(value.isAfter(LocalDate.now())) {
					   datePicker.setValue(LocalDate.now());
				   }else {
			      datePicker.setValue(value.plusDays(1));
			
				   }
				    event.consume();
			   } 
			   
			   else if (yearDownCombo.match(event)) {
			      //DOWN pressed, the control and shift are hold down, decrease the year
			      datePicker.setValue(value.minusYears(1));
			      event.consume();
			   } 
			   else if (monthDownCOmbo.match(event)) {
			      //DOWN pressed, the control key is hold down, decrease the month
			      datePicker.setValue(value.minusMonths(1));
			      event.consume();
			   } 
			   else if(event.getCode() == KeyCode.DOWN) {
			      // DOWN pressed, no modifier keys hold, decrease the day
//				   SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
				   value=value.minusDays(1);
				  datePicker.setValue(value);
//				  System.out.println("value"+value);
				  event.consume();
			   }
			   
//			   System.out.println("Reached");
			});
	}
	
	
	public static void salesInvoiceHandler(Scene scene) {

		final KeyCombination salekey= new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHIFT_DOWN);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, e->{
			if(salekey.match(e)) {
				
			}
		});
	}
	
	
	public static void exitSystem() {
		Alert alert= new Alert(AlertType.CONFIRMATION,"DO you really want to exit the Billing Master?");
		alert.initOwner(Main.primaryStage);
		alert.setTitle("Exit confirmation");
		alert.setHeaderText(null);
		
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(ButtonType.YES,ButtonType.NO);
		
		DialogPane dialogPane=alert.getDialogPane();

		dialogPane.getStylesheets().add(KeyEventHandler.class.getResource("/images/popup.css").toExternalForm());
		
		dialogPane.getStyleClass().add("alert");
		
//		EventHandler<KeyEvent> fireOnEnter = event -> {
//		    if (KeyCode.ENTER.equals(event.getCode()) 
//		            && event.getTarget() instanceof Button) {
//		        ((Button) event.getTarget()).fire();
//		    }
//		};
//
//		dialogPane.getButtonTypes().stream()
//		        .map(dialogPane::lookupButton)
//		        .forEach(button ->
//		                button.addEventHandler(
//		                        KeyEvent.KEY_PRESSED,
//		                        fireOnEnter
//		                )
//		        );
		Button yesBtn= (Button)dialogPane.lookupButton(ButtonType.YES);
		yesBtn.setDefaultButton(false);
		
		Button noBtn= (Button) dialogPane.lookupButton(ButtonType.NO);
		noBtn.setDefaultButton(true);
		
		dialogPane.addEventHandler(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode()==KeyCode.Y) {
				System.exit(0);
			}
			else if(e.getCode()==KeyCode.N) {
				alert.hide();
				e.consume();
			}
		
		});
		
		Optional<ButtonType> result= alert.showAndWait();
		if(result.get()==ButtonType.YES) {
			
			System.exit(0);
		}
		
//		dialogPane.addEventHandler(KeyEvent.KEY_PRESSED, e->{
//			if(e.getCode()==KeyCode.Y) {
//				System.exit(0);
//			}else if(e.getCode()==KeyCode.N) {
//				
//			}
//		});
	}
}
