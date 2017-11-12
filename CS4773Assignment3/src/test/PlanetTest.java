package test;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.*;
import models.Planet;
import models.PlanetGateway;
import models.Validators;

public class PlanetTest{

	@Test
	public void invalidDiameterCheck() {
		assertEquals(false, Validators.validatePlanetDiameter(-10));
	}
	
	@Test
	public void validDiameterCheck() {
		assertEquals(true, Validators.validatePlanetDiameter(10));
	}
	
	@Test
	public void invalidNameCheck(){
		assertEquals(false, Validators.validatePlanetName("!"));
	}

	@Test
	public void validNameCheck(){
		assertEquals(true, Validators.validatePlanetName("Bruce"));
	}
	
	@Test
	public void invalidTemperatureCheck(){
		assertEquals(false, Validators.validatePlanetTemp(10000000));
	}
	
	@Test
	public void validTemperatureCheck(){
		assertEquals(true, Validators.validatePlanetTemp(30));
	}
	
	@Test
	public void invalidMoonsCheck(){
		assertEquals(false, Validators.validateNumberOfMoons(10000000));
	}
	
	@Test
	public void validMoonsCheck(){
		assertEquals(true, Validators.validateNumberOfMoons(200));
	}
	
	@Test
	public void testPlanetLoading() throws FileNotFoundException {
		File file = new File("tests/LoadTest.txt");
		Planet planetLoaded = PlanetGateway.returnPlanetForTest(file);
		Planet testPlanet = PlanetGateway.loadedPlanet = new Planet.PlanetBuilder("Vegeta")
				.imagePath("images/no_image.png")
				.diameter(2000)
				.temperature(10)
				.NumberOfMoons(15)
				.build();
		assertEquals(testPlanet.getPlanetName(), planetLoaded.getPlanetName());
		assertEquals(testPlanet.getPlanetDiameterInKm(), planetLoaded.getPlanetDiameterInKm(), .1);
		assertEquals(testPlanet.getPlanetTempInC(), planetLoaded.getPlanetTempInC(),.1);
		assertEquals(testPlanet.getImagePath(), planetLoaded.getImagePath());
		assertEquals(testPlanet.getNumberOfMoons(), planetLoaded.getNumberOfMoons());
	}
	
	@Test
	public void testPlanetSaving() throws IOException {
			File newFile = new File("tests/SavedFile");
			File oldFile = new File("tests/SaveTest");
			Planet planetToSave = PlanetGateway.loadedPlanet = new Planet.PlanetBuilder("Namek")
					.imagePath("images/no_image.png")
					.diameter(3000)
					.temperature(20)
					.NumberOfMoons(30)
					.build();
			PlanetGateway.saveFile(PlanetGateway.addInputToBuffer(planetToSave), newFile);
			String oldFileString = new String(Files.readAllBytes(Paths.get(oldFile.getPath()))
					, StandardCharsets.UTF_8);
			String newFileString = new String(Files.readAllBytes(Paths.get(newFile.getPath()))
					, StandardCharsets.UTF_8);
			assertEquals(oldFileString, newFileString);		
	}
}
