package planet.detail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PlanetController {

    @FXML
    private ImageView planetImage;

    @FXML
    private Button selectImageButton;

    @FXML
    private TextField planetName;

    @FXML
    private TextField planetDiameterKM;

    @FXML
    private TextField planetDiameterM;

    @FXML
    private TextField planetMeanSurfaceTempC;

    @FXML
    private TextField planetMeanSurfaceTempF;

    @FXML
    private TextField planetNumberOfMoons;

    @FXML
    private Label fancyPlanetName;
    
    private Stage primaryStage;

    public PlanetController() {
    }
    
    @FXML
    void selectImage(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	try {
    		//TODO need to find way to make it look nicer
    		//I tried to do it without the FileInputStream and it didn't work
    		//Check with Pablo to make sure there is no other way
			Image image = new Image(new FileInputStream(fileChooser.showOpenDialog(new Stage()).getAbsolutePath()));
			planetImage.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("ERROR: file not found");
			e.printStackTrace();
		}
    	
    }

    @FXML
    void loadPlanet(ActionEvent event) {
    }
    
    @FXML
    void savePlanet(ActionEvent event) {
    }
    
    @FXML
    void setPlanetName(ActionEvent event) {
    	//if required to update as typed, have to implement different solution
//    	planetName.getDocument().addDocumentListener(new DocumentListener() { 
//    	@Override
//         public void insertUpdate(DocumentEvent de) {
//             fancyPlanetName.setText(planetName.getText());
//         }
//
//         @Override
//         public void removeUpdate(DocumentEvent de) {
//        	 fancyPlanetName.setText(planetName.getText());
//         }
//
//         @Override
//         public void changedUpdate(DocumentEvent de) {
//         //Plain text components don't fire these events.
//         }
//     });

    	//TODO will have to change all invalid fields to alert or set boolean that says that validating failed
    	//check if planet name is within valid range
    	//TODO he suggested the validator should be delegated
    	//TODO builder to make planet object
    	if (planetName.getText().length() < 1 || planetName.getText().length() > 255){
    		System.err.println("ERROR: planet name must be between 1 and 255 characters long.");
    		return;
    	}
    	//check for illegal characters
    	if (!planetName.getText().matches("[-a-zA-Z0-9. ]+") ){
    		System.err.println("ERROR: planet name can only contain alphanumeric and/or punctation characters (\".\", \"-\", or \" \").");
    		return;
    	}
    	//Check for empty string - I think the above two already cover this
    	//TODO initialize planetName to empty i.e ""
    	if (planetName.getText().equals("")){
    		System.err.println("ERROR: planet name field was not set.");
    		return;
    	}
    	//set fancy name to be updated
    	fancyPlanetName.setText(planetName.getText());
    	
    }
    
    @FXML
    void setPlanetDiameter(ActionEvent event) {
    	//TODO default to invalid value
    	try{
    		double diameterInKM = Double.parseDouble(planetDiameterKM.getText());
    		planetDiameterKM.setText(String.format("%,f", diameterInKM));
    		//check if in valid range
    		if (diameterInKM < 0 || diameterInKM > 200000){
    			System.err.println("ERROR: planet diameter must be between 0 and 200,000 km.");
    			return;
    		}
    		//convert to mi and store
    		planetDiameterM.setText(String.format("%,f", diameterInKM*0.621371));
    	}
    	catch (NumberFormatException e){
    		System.err.println("Cannot do numerical conversion "+ e.getMessage().toLowerCase()+
    				"\nEnter valid number.");
    	}
    }
    
    @FXML
    void setPlanetTemp(ActionEvent event) {
    	//TODO default to invalid value
    	try{
    		double temperatureInC = Double.parseDouble(planetMeanSurfaceTempC.getText());
    		//TODO make constants for values in if
    		//check if in valid range
    		if (temperatureInC < -273.15 || temperatureInC > 500.0){
    			System.err.println("ERROR: planet temperature must be between -273.15\u00b0 and 500.0\u00b0 C.");
    			return;
        	}
    		//convert to mi and store
    		planetMeanSurfaceTempF.setText(String.format("%,f", (temperatureInC*1.8+32)));
    	}
    	catch (NumberFormatException e){
    		System.err.println("Cannot do numerical conversion "+ e.getMessage().toLowerCase()+
    				"\nEnter valid number.");
    	}
    }
    
    @FXML
    void setMoons(ActionEvent event) {
    	//TODO default to invalid value
    	try{
    		int moons = Integer.parseInt(planetNumberOfMoons.getText());
    		planetNumberOfMoons.setText(String.format("%,d", moons));
    		//TODO make constants for values in if
    		//check if in valid range
    		if (moons < 0 || moons > 1000)
    			System.err.println("ERROR: number of moons must be between 0 and 1000.");
    	}
    	catch (NumberFormatException e){
    		System.err.println("Cannot do numerical conversion "+ e.getMessage().toLowerCase()+
    				"\nEnter valid number.");
    	}
    }
}