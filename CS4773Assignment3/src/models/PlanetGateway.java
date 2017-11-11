package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import app.Error;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class PlanetGateway {
	static Planet planet;
	static Planet loadedPlanet;
	public static void savePlanet(Planet planet) {
		PlanetGateway.planet = planet;
		FileChooser fileChooser = new FileChooser();

		if (Validators.validatePlanet(planet) == false) {
			System.out.println("Something is wrong with planet");
			return;
		}
		fileChooser.setTitle("Save Planet As File");
		ExtensionFilter extensionFilter = new ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extensionFilter);

		File file = fileChooser.showSaveDialog(new Stage());

		if (file != null)
			saveFile(addInputToBuffer(), file);

		fileChooser.getExtensionFilters().remove(extensionFilter);
	}
	
	private static String addInputToBuffer() {
		return (planet.getImagePath() + "\n" 
				+ planet.getPlanetName() + "\n" 
				+ planet.getPlanetDiameterInKm() + "\n"  
				+ planet.getPlanetTempInC() + "\n" 
				+ planet.getNumberOfMoons());
	}
	
	private static void saveFile(String content, File file) {
		try {
			FileWriter fileWriter = null;
			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException e) {
			Error.showError(e.getMessage());
		}
	}
	
	public static Planet loadPlanetFile() throws FileNotFoundException{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Planet File To Load");
		ExtensionFilter extensionFilter = new ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extensionFilter);
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Overwriting Input");
		alert.setContentText("Are you sure you want to load a new file and overwrite current fields?");
		alert.showAndWait();
		
		if (alert.getResult() == ButtonType.OK) {
			File file = fileChooser.showOpenDialog(new Stage());
			if (file == null)
				return null;
			loadPlanetFromFile(file);
		}
		fileChooser.getExtensionFilters().remove(extensionFilter);
		return PlanetGateway.loadedPlanet;
	}
	
	private static void loadPlanetFromFile(File file) throws FileNotFoundException {
		String planetImagePath;
		String name;
		double diameter;
		double temperature;
		int moons;
		
		try {
			Scanner scanner = new Scanner(file);
			planetImagePath = scanner.nextLine();
			name = scanner.nextLine();
			diameter = scanner.nextDouble();
			temperature = scanner.nextDouble();
			moons = scanner.nextInt();
			scanner.close();
		} catch (InputMismatchException e) {
			Error.showError("Could not convert value to number");
			return;
		} catch (NoSuchElementException e) {
			Error.showError("Planet file is not formatted correctly.");
			return;
		}
		PlanetGateway.loadedPlanet = new Planet.PlanetBuilder(name)
				.imagePath(planetImagePath)
				.diameter(diameter)
				.temperature(temperature)
				.NumberOfMoons(moons)
				.build();
	}
}
