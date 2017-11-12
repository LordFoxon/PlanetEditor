package models;

import app.Error;

public class Validators {

	private static final int MIN_LENGTH = 1;
	private static final int MAX_LENGTH = 255;
	
	private static final double MIN_DIAMETER = 0;
	private static final double MAX_DIAMETER = 200_000;
	
	private static final double MIN_DEGREES = -273.15;
	private static final double MAX_DEGREES = 500.0;
	
	private static final int MIN_MOONS = 0;
	private static final int MAX_MOONS = 1_000;
	
	public static boolean validatePlanetName(String name) {
		if(name == null) {
			return false;
		}
		if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH){
			Error.showError("Planet name must be between 1 and 255 characters long.");
			return false;
		}
		if (!name.matches("[-a-zA-Z0-9. ]+") ){
			Error.showError("Planet name must contain one or more alphanumeric and/or punctation characters (\".\", \"-\", or \" \").");
			return false ;
		}
		return true;
	}
	
	public static boolean validatePlanetDiameter(double diameter) {
		try {
			if (diameter < MIN_DIAMETER || diameter > MAX_DIAMETER) {
				Error.showError("Planet diameter must be between 0 and 200,000 km.");
				return false;
			}
		} catch (NumberFormatException e) {
			Error.conversionError(e.getMessage().toLowerCase());
			return false;
		}

		return true;
	}
	
	public static boolean validatePlanetTemp(double temperature) {
		if (temperature < MIN_DEGREES || temperature > MAX_DEGREES) {
			Error.showError("Planet temperature must be between -273.15\u00b0 and 500.0\u00b0 C.");
			return false;
		}
		return true;
	}
	
	public static boolean validateNumberOfMoons(int numberOfMoons) {
		if (numberOfMoons < MIN_MOONS || numberOfMoons > MAX_MOONS) {
			Error.showError("Number of moons must be between 0 and 1000.");
			return false;
		}
		return true;
	}
	
	public static boolean validatePlanet(Planet planet) {
		if(!validatePlanetName(planet.getPlanetName())) {
			return false;
		}
		if(!validatePlanetDiameter(planet.getPlanetDiameterInKm())) {
			return false;
		}
		if(!validatePlanetTemp(planet.getPlanetTempInC())) {
			return false;
		}
		if(!validateNumberOfMoons(planet.getNumberOfMoons())) {
			return false;
		}

		return true;
	}
}
