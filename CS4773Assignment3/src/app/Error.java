package app;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Error {
	public static void showError(String errorMessage){
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Value Entered");
		System.err.println("ERROR: "+errorMessage);
		alert.setContentText(errorMessage);
		alert.showAndWait();
    }
	
	public static void conversionError(String errorMessage) {
		errorMessage = "Cannot do numerical conversion " + errorMessage + ".\nEnter valid number.";
		if (errorMessage.contains("empty"))
			errorMessage = errorMessage.replace("empty", "on empty");
		showError(errorMessage);
	}
}
