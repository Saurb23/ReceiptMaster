package controller;

import java.util.List;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/***
 * 
 * @author Saurabh Gupta
 *
 * @param <T>
 */
public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

    private JFXComboBox comboBox;
    private StringBuilder sb;
    private ObservableList<T> data;
    private boolean moveCaretToPos = false;
    private int caretPos;

    public AutoCompleteComboBoxListener(final JFXComboBox comboBox) {
        this.comboBox = comboBox;
        sb = new StringBuilder();
        data = comboBox.getItems();
//        this.comboBox.setLabelFloat(true);
        
        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                comboBox.hide();
            }
        });
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }

    @Override
    public void handle(KeyEvent event) {
    
    	if(event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.DOWN) {
            if(!comboBox.isShowing()) {
                comboBox.show();
            }
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        }
        else if(event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        }
//        else if(event.getCode() == KeyCode.DELETE) {
//            moveCaretToPos = true;
//            caretPos = comboBox.getEditor().getCaretPosition();
//        }
//        
        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
        	
            return;
        }
        
        ObservableList list = FXCollections.observableArrayList();
        for (int i=0; i<data.size(); i++) {
            if(data.get(i).toString().toLowerCase().contains(
                AutoCompleteComboBoxListener.this.comboBox
                .getEditor().getText().toLowerCase())) {
                list.add(data.get(i));
            }
        }
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
//        comboBox.getEditor().setText(t);
        comboBox.setValue(t);
       	
       
        if(!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if(!list.isEmpty()) {
            comboBox.show();
        }
        if(event.getCode()==KeyCode.ENTER) {
        	if(list.size()!=0) {
        	comboBox.setValue(list.get(0).toString());
//        	caretPos=-1;
            moveCaret(comboBox.getEditor().getText().length());
       
        	}
            
        }
    }

    private void moveCaret(int textLength) {
        if(caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }
    
    
    public static String jumpTo(String keyPressed, String currentlySelected, List<String> items) {
	    String key = keyPressed.toUpperCase();
	    if (key.matches("^[A-Za-z0-9]$")) {
	        // Only act on letters so that navigating with cursor keys does not
	        // try to jump somewhere.
	        boolean letterFound = false;
	        boolean foundCurrent = currentlySelected == null;
	        for (String s : items) {
	            if (s.toUpperCase().startsWith(key)) {
	                letterFound = true;
	                if (foundCurrent) {
	                	
	                    return s;
	                }
	                foundCurrent = s.equals(currentlySelected);
	            }
	        }
	        if (letterFound) {
	            return jumpTo(keyPressed, null, items);
	        }
	    }
	    return null;
	}

}
