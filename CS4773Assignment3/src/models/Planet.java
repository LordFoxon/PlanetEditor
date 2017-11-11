package models;

import javafx.scene.image.Image;

public class Planet {
	
	private String name;
	private String imagePath;
	private double diameterInKm;
	private double diameterInM;
	private double temperatureInCelsius;
	private double temperatureInFahrenheit;
	private int numberOfMoons;
	private Image image;
	
	public Planet(PlanetBuilder planetBuilder) {
		this.imagePath = planetBuilder.imagePath;
		this.name = planetBuilder.name;
		this.diameterInKm = planetBuilder.diameterInKm;
		this.diameterInM = planetBuilder.diameterInM;
		this.temperatureInCelsius = planetBuilder.temperature;
		this.temperatureInFahrenheit = planetBuilder.temperatureInFahrenheit;
		this.numberOfMoons = planetBuilder.moons;
		this.image = planetBuilder.image;
	}
	
	public void setPlanetImage(Image image) {
		this.image = image;
	}
	
	public String getImagePath() {
		return this.imagePath;
	}
	
	public String getPlanetName() {
		return this.name;
	}
	
	public double getPlanetDiameterInKm() {
		return this.diameterInKm;
	}
	
	public double getPlanetDiameterInM() {
		return this.diameterInM;
	}
	
	public double getPlanetTempInC() {
		return this.temperatureInCelsius;
	}
	
	public double getPlanetTempInF() {
		return this.temperatureInFahrenheit;
	}
	
	public int getNumberOfMoons() {
		return this.numberOfMoons;
	}
	
	public Image getPlanetImage() {
		return this.image;
	}
	
	public void savePlanet() {
		PlanetGateway.savePlanet(this);
	}
	
	public static class PlanetBuilder{
		
		private String name;
		private String imagePath;
		private double diameterInKm;
		private double diameterInM;
		private double temperature;
		private double temperatureInFahrenheit;
		private int moons;
		private Image image;
		
		public PlanetBuilder(String name) {
			if (Validators.validatePlanetName(name)) {
				this.name = name;
			}
		}
		
		public PlanetBuilder imagePath(String imagePath) {
			System.out.println(imagePath);
			this.imagePath = imagePath;
			return this;
		}
		
		public PlanetBuilder diameter(double diameterInKm) {
			if(Validators.validatePlanetDiameter(diameterInKm)) {
				this.diameterInKm = diameterInKm;
				this.diameterInM = (diameterInKm * 0.621371);
			}
			return this;
		}

		public PlanetBuilder temperature(double temperature) {
			if (Validators.validatePlanetTemp(temperature)) {
				this.temperature = temperature;
				this.temperatureInFahrenheit = temperature * 1.8 + 32;
			}
			return this;
		}

		public PlanetBuilder NumberOfMoons(int moons) {
			if(Validators.validateNumberOfMoons(moons)) {
				this.moons = moons;
			}
			return this;
		}

		public Planet build() {
			return new Planet(this);
		}
	}
}
