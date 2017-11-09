package models;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class Planet {
	private static final int MIN_LENGTH = 1;
	private static final int MAX_LENGTH = 255;
	
	private static final double MIN_DIAMETER = 0;
	private static final double MAX_DIAMETER = 200_000;
	
	private static final double MIN_DEGREES = -273.15;
	private static final double MAX_DEGREES = 500.0;
	
	private static final int MIN_MOONS = 0;
	private static final int MAX_MOONS = 1_000;
	
	private String name;
	private double diameter;
	private double temp;
	private int numberOfMoons;
	private Image image;
	
	void Planet(){
		String name;
		double diameter;
		double temp;
		int numberOfMoons;
		Image image;
	}
	
	public void setPlanetName(String name){
		if (validatePlanetName(name)) {
			this.name = name;
		}
	}
	
	public String getPlanetName() {
		return this.name;
	}
	
	public void setPlanetDiameter(double diameter){
		this.diameter = diameter;
	}
	
	public double getPlanetDiameter() {
		return this.diameter;
	}
	
	public void setPlanetTemp(double temp){
		this.temp = temp;
	}
	
	public double getPlanetTemp() {
		return this.temp;
	}
	
	public void setNumberOfMoons(int numberOfMoons){
		this.numberOfMoons = numberOfMoons;
	}
	
	public int getNumberOfMoons() {
		return this.numberOfMoons;
	}
	
	public void setPlanetImage(Image image) {
		this.image = image;
	}
	
	public Image getPlanetImage() {
		return this.image;
	}
	
	boolean validatePlanetName(String name) {
		if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH){
			showError("Planet name must be between 1 and 255 characters long.");
			return false;
		}
		if (!name.matches("[-a-zA-Z0-9. ]+") ){
			showError("Planet name must contain one or more alphanumeric and/or punctation characters (\".\", \"-\", or \" \").");
			return false ;
		}
		return true;
		
	}
	
	boolean validatePlanetDiameter() {
		return false;
	}
	
	boolean validatePlanetTemp() {
		return false;
	}
	
	boolean validateNumberOfMoons() {
		return false;
	}

	public void showError(String errorMessage){
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Value Entered");
		System.err.println("ERROR: "+errorMessage);
		alert.setContentText(errorMessage);
		alert.showAndWait();
    }
}
