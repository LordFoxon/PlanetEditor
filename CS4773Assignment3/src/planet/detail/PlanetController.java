package planet.detail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

    @FXML
    void selectImage(ActionEvent event) {
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

    	//set fancy name to be updated
    	fancyPlanetName.setText(planetName.getText());
    	//TODO will have to change all invalid fields to alert or set boolean that says that validating failed
    	//check if planet name is within valid range
    	//TODO he suggested the validator should be delegated
    	//TODO builder to make planet object
    	if (fancyPlanetName.getText().length() < 1 || fancyPlanetName.getText().length() > 255)
    		System.out.println("ERROR: planet name must be between 1 and 255 characters long.");
    	//check for illegal characters
    	if (!fancyPlanetName.getText().matches("[-a-zA-Z0-9. ]+") )
    		System.out.println("ERROR: planet name can only contain alphanumeric and/or punctation characters (\".\", \"-\", or \" \").");
    	//Check for empty string - I think the above two already cover this
    	//TODO initialize planetName to empty i.e ""
    	if (planetName.getText().equals(""))
    		System.out.println("ERROR: planet name field was not set.");
    	
    }
    @FXML
    void setPlanetDiameter(ActionEvent event) {
    	System.out.println(planetDiameterKM.getText());
    }
    
}