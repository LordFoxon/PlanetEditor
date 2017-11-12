package app;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Error {
	public static void showError(String errorMessage) {
		System.err.println("ERROR: " + errorMessage);
		try {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Value Entered");
			alert.setContentText(errorMessage);
			alert.showAndWait();
		} catch(ExceptionInInitializerError e) {
			System.err.println("ERROR: JavaFX not initialized in test");
		} catch(NoClassDefFoundError e) {
			System.err.println("ERROR: JavaFX not initialized in test");
		}
	}

	public static void conversionError(String errorMessage) {
		errorMessage = "Cannot do numerical conversion " + errorMessage + ".\nEnter valid number.";
		if (errorMessage.contains("empty"))
			errorMessage = errorMessage.replace("empty", "on empty");
		showError(errorMessage);
	}
}
