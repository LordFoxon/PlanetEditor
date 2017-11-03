package planet.detail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class PlanetController {

	private static final int MIN_LENGTH = 1;
	private static final int MAX_LENGTH = 255;
	
	private static final double MIN_DIAMETER = 0;
	private static final double MAX_DIAMETER = 200_000;
	
	private static final double MIN_DEGREES = -273.15;
	private static final double MAX_DEGREES = 500.0;
	
	private static final int MIN_MOONS = 0;
	private static final int MAX_MOONS = 1_000;
    @FXML
    private ImageView planetImage;

    @FXML
    private Button selectImageButton;
    
    @FXML
    private Button loadFile;
    
    @FXML
    private Button saveFile;

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
    
    public PlanetController() {
    }
    
    @FXML
    void selectImage(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Select Planet Image");
    	try {
    		//TODO need to find way to make it look nicer
    		//I tried to do it without the FileInputStream and it didn't work
    		//Check with Pablo to make sure there is no other way
    		String path = fileChooser.showOpenDialog(new Stage()).getAbsolutePath();
			Image image = new Image(new FileInputStream(path));
			planetImage.setImage(image);
			planetImage.setId(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("ERROR: file not found");
			e.printStackTrace();
		}
    }

    @FXML
    void loadPlanet(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Choose Planet File To Load");
    	try {
    		Scanner scanner = new Scanner(fileChooser.showOpenDialog(new Stage()));

    		planetImage.setImage(new Image(new FileInputStream(scanner.nextLine())));

    		planetName.setText(scanner.nextLine());
    		
    		fancyPlanetName.setText(scanner.nextLine());

    		planetDiameterKM.setText(scanner.nextLine());

    		planetDiameterM.setText(scanner.nextLine());

    		planetMeanSurfaceTempC.setText(scanner.nextLine());

    		planetMeanSurfaceTempF.setText(scanner.nextLine());

    		planetNumberOfMoons.setText(scanner.nextLine());
            scanner.close();
		} catch (FileNotFoundException e) {
			showError(e.getMessage());
		}
    }
    
    @FXML
    void savePlanet(ActionEvent event) {
    	//TODO ask Robinson how to save file
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Save Planet As File");
    	//Set extension filter
        ExtensionFilter extensionFilter = new ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        
        File file = fileChooser.showSaveDialog(new Stage());
        
        String planetContents = "";
        planetContents += planetImage.getId()+"\n";

        planetContents += planetName.getText()+"\n";
        
        planetContents += fancyPlanetName.getText()+"\n";

        planetContents += planetDiameterKM.getText()+"\n";

        planetContents += planetDiameterM.getText()+"\n"; ;

        planetContents += planetMeanSurfaceTempC.getText()+"\n";
        
        planetContents += planetMeanSurfaceTempF.getText()+"\n";
        
        planetContents += planetNumberOfMoons.getText();
        
        if(file != null){
            saveFile(planetContents, file);
        }
    }
    
    @FXML
    void setPlanetName(ActionEvent event) {
    	//if required to update fancyPlanetName as typed, have to implement different solution
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
    	//TODO he suggested the validator should be delegated
    	//TODO builder to make planet object
    	if (planetName.getText().length() < MIN_LENGTH || planetName.getText().length() > MAX_LENGTH){
    		showError("Planet name must be between 1 and 255 characters long.");
    		return;
    	}
    	//check for illegal characters
    	if (!planetName.getText().matches("[-a-zA-Z0-9. ]+") ){
    		showError("Planet name must contain one or more alphanumeric and/or punctation characters (\".\", \"-\", or \" \").");
    		return;
    	}
    	//Check for empty string - I think the above two already cover this
    	//TODO initialize planetName to empty i.e ""
    	if (planetName.getText().equals("")){
    		showError("Planet name field was not set.");
    		return;
    	}
    	
    	fancyPlanetName.setText(planetName.getText());
    }
    
    @FXML
    void setPlanetDiameter(ActionEvent event) {
    	//TODO default to invalid value
    	try{
    		double diameterInKM = Double.parseDouble(planetDiameterKM.getText());
    		planetDiameterKM.setText(String.format("%,f", diameterInKM));
    		if (diameterInKM < MIN_DIAMETER || diameterInKM > MAX_DIAMETER){
    			showError("Planet diameter must be between 0 and 200,000 km.");
    			return;
    		}
    		//convert to mi and store
    		planetDiameterM.setText(String.format("%,f", diameterInKM*0.621371));
    	}
    	catch (NumberFormatException e){
    		conversionError(e.getMessage().toLowerCase());
    	}
    }
    
    @FXML
    void setPlanetTemp(ActionEvent event) {
    	//TODO default to invalid value
    	try{
    		double temperatureInC = Double.parseDouble(planetMeanSurfaceTempC.getText());
    		if (temperatureInC < MIN_DEGREES || temperatureInC > MAX_DEGREES){
    			showError("Planet temperature must be between -273.15\u00b0 and 500.0\u00b0 C.");
    			return;
        	}
    		//convert to f and store
    		planetMeanSurfaceTempF.setText(""+temperatureInC*1.8+32);
    	}
    	catch (NumberFormatException e){
    		conversionError(e.getMessage().toLowerCase());
    	}
    }
    
    @FXML
    void setMoons(ActionEvent event) {
    	//TODO default to invalid value
    	try{
    		int moons = Integer.parseInt(planetNumberOfMoons.getText());
    		planetNumberOfMoons.setText(String.format("%,d", moons));
    		if (moons < MIN_MOONS || moons > MAX_MOONS)
    			showError("Number of moons must be between 0 and 1,000.");
    	}
    	catch (NumberFormatException e){
    		conversionError(e.getMessage().toLowerCase());
    	}
    }
    
    void showError(String errorMessage){
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Value Entered");
		System.err.println("ERROR: "+errorMessage);
		alert.setContentText(errorMessage);
		alert.showAndWait();
    }
    
    void conversionError(String errorMessage){
    	errorMessage = "Cannot do numerical conversion "+ errorMessage +".\nEnter valid number.";
    	if (errorMessage.contains("empty"))
    		errorMessage = errorMessage.replace("empty","on empty");
    	showError(errorMessage);
    }
    
    public void saveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
        	showError(e.getMessage());
        }
         
    }
}