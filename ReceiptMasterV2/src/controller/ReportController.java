package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.SalesReportBean;
import net.sf.jasperreports.engine.JRException;
/***
*
* @author Rahul Shrirao  07/10/2017
*
*/
public class ReportController {
static 	JFXDatePicker FromDate = new JFXDatePicker();
static	JFXDatePicker ToDate = new JFXDatePicker();

static JFXRadioButton pdf = new JFXRadioButton("Pdf");
static JFXRadioButton excel = new JFXRadioButton("Excel");
public GridPane CreateSalesReport;

Tooltip errorTip = new Tooltip();
static Sales_Report_Generator salesReportGen=new Sales_Report_Generator();
	 public static GridPane CreateSalesReport(AnchorPane anchorPane) {

		GridPane gp= new GridPane();
        gp.getStyleClass().add("grid");
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(0, 25, 25, 25));
//		gp.setGridLinesVisible(true);
		Label titleLabel = new Label("Sales Report");
		gp.add(titleLabel, 0, 0);
        FromDate.setPromptText("From Date");
		gp.add(FromDate, 0, 1);

		ToDate.setPromptText("To Date");
		//ToDate.setLabelFloat(true);
		//validateOnFocus(ToDate);
		gp.add(ToDate, 1, 1);

		GridPane.setHalignment(pdf, HPos.LEFT);
		GridPane.setValignment(pdf, VPos.TOP);
		gp.add(pdf, 0, 2);
		GridPane.setHalignment(excel, HPos.RIGHT);
		GridPane.setValignment(excel, VPos.BOTTOM);
		gp.add(excel, 0, 2);

		JFXButton GenerateBtn= new JFXButton("Generate");
		gp.add(GenerateBtn, 0, 6);

		GenerateBtn.setOnAction(new EventHandler<ActionEvent>() {
			boolean result;
			@Override
			public void handle(ActionEvent event) {
				SalesReportBean sb=new SalesReportBean();

				LocalDate date = FromDate.getValue();
				java.sql.Date sqlDate=null;
				try {
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
					long dateLong= sd.parse(date.toString()).getTime();
					sqlDate=new java.sql.Date(dateLong);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				LocalDate date1 = ToDate.getValue();
				java.sql.Date sqlDate1=null;
				try {
					SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
					long dateLong1= sd1.parse(date1.toString()).getTime();
					sqlDate1=new java.sql.Date(dateLong1);

					System.out.println("                     "+sqlDate1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
sb.setFromDate(sqlDate.toString());
sb.setToDate(sqlDate1.toString());




                try {
							salesReportGen.SalesReportPdf(sb);
					} catch (ClassNotFoundException | JRException | SQLException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}

			}
		});


		return gp;
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

