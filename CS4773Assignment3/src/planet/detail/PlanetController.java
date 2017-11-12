package planet.detail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import app.Error;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Planet;
import models.PlanetGateway;

public class PlanetController implements Initializable {

	private static final double MIN_DIAMETER = 0;
	private static final double MIN_DEGREES = -273.15;
	private static final int MIN_MOONS = 0;
	
	private Planet planet;
	private String name;
	private double diameter;
	private double temperature;
	private int moons;

	private FileChooser fileChooser;
	
	@FXML private ImageView planetImage;
	
	@FXML private Button selectImageButton;
	@FXML private Button loadFile;
	@FXML private Button saveFile;
	
	@FXML private TextField planetName;
	@FXML private TextField planetDiameterKM;
	@FXML private TextField planetDiameterM;
	@FXML private TextField planetMeanSurfaceTempC;
	@FXML private TextField planetMeanSurfaceTempF;
	@FXML private TextField planetNumberOfMoons;
	@FXML private Label fancyPlanetName;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fileChooser = new FileChooser();
		planetImage.setImage(new Image("images/no_image.png"));
		planetImage.setId("images/no_image.png");
		moons = MIN_MOONS - 1;
		name = "";
		diameter = MIN_DIAMETER - 1;
		temperature = MIN_DEGREES - 1;
	}
	
	@FXML void parseUserInput(ActionEvent event) throws Exception{
		try {
			handleUserInput(event);
		} catch(NumberFormatException e) {
			Error.conversionError(e.getMessage().toLowerCase());
		}
	}
	@FXML void handleKeyEvent(KeyEvent event) throws Exception {
		if(event.getSource() == planetName){
			name = planetName.getText();
			fancyPlanetName.setText(name);
		}
	}
	void handleUserInput(ActionEvent event) throws Exception {
		if (event.getSource() == planetDiameterKM) {
			diameter = Double.parseDouble(planetDiameterKM.getText());
			planetDiameterKM.setText(String.format("%,f", diameter));
			planetDiameterM.setText(String.format("%,f", diameter * 0.621371));
		}
		if (event.getSource() == planetMeanSurfaceTempC) {
			temperature = Double.parseDouble(planetMeanSurfaceTempC.getText());
			planetMeanSurfaceTempF.setText("" + temperature * 1.8 + 32);
		}
		if (event.getSource() == planetNumberOfMoons) {
			moons = Integer.parseInt(planetNumberOfMoons.getText());
		}
	}
	
	@FXML
	void selectImage(ActionEvent event) {
		fileChooser.setTitle("Select Planet Image");
		try {
			File file = fileChooser.showOpenDialog(new Stage());
			if (file == null)
				return;
			String path = file.getAbsolutePath();
			Image image = new Image(new FileInputStream(path));
			planetImage.setImage(image);
			planetImage.setId(path);
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: file not found");
		}
	}

	@FXML
	void savePlanet(ActionEvent event) {
		planet = new Planet.PlanetBuilder(name)
				.imagePath(planetImage.getId())
				.diameter(diameter)
				.temperature(temperature)
				.NumberOfMoons(moons).build();
		planet.savePlanet();
	}

	@FXML
	void loadPlanet(ActionEvent event) throws FileNotFoundException {
		setPlanetInformationFromFile(PlanetGateway.loadPlanetFile());
	}
	
	private void setPlanetInformationFromFile(Planet planet) throws FileNotFoundException {
		if(planet == null)
			return;
		try {
			planetImage.setImage(new Image(new FileInputStream(planet.getImagePath())));
		} catch (FileNotFoundException e) {
			planetImage.setImage(new Image("images/no_image.png"));
			Error.showError("Image File not found.");
		}
		planetName.setText(planet.getPlanetName());
		fancyPlanetName.setText(planet.getPlanetName());
		planetDiameterKM.setText(String.format("%,f", planet.getPlanetDiameterInKm()));
		planetDiameterM.setText(String.format("%,f", planet.getPlanetDiameterInM()));
		planetMeanSurfaceTempC.setText("" + planet.getPlanetTempInC());
		planetMeanSurfaceTempF.setText("" + planet.getPlanetTempInF());
		planetNumberOfMoons.setText(String.format("%,d", planet.getNumberOfMoons()));
	}
}