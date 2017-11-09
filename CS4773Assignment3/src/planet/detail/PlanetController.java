package planet.detail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import models.Planet;

public class PlanetController implements Initializable{

	private static final int MIN_LENGTH = 1;
	private static final int MAX_LENGTH = 255;
	
	private static final double MIN_DIAMETER = 0;
	private static final double MAX_DIAMETER = 200_000;
	
	private static final double MIN_DEGREES = -273.15;
	private static final double MAX_DEGREES = 500.0;
	
	private static final int MIN_MOONS = 0;
	private static final int MAX_MOONS = 1_000;
	
	private Planet planet;
	
	private String name;
	private double diameter;
	private double temperature;
	private int moons;
	
	private FileChooser fileChooser;
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
    		fileChooser = new FileChooser();
    		this.planet = new Planet();
    }
    
    @FXML
    void selectImage(ActionEvent event) {
    	fileChooser.setTitle("Select Planet Image");
    	try {
    		String path = fileChooser.showOpenDialog(new Stage()).getAbsolutePath();
			Image image = new Image(new FileInputStream(path));
			planetImage.setImage(image);
			planetImage.setId(path);
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: file not found");
		}
    }

    @FXML
    void loadPlanet(ActionEvent event) {
    	fileChooser.setTitle("Choose Planet File To Load");
        ExtensionFilter extensionFilter = new ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
    	setPlanetInformationFromFile();
    	fileChooser.getExtensionFilters().remove(extensionFilter);
    }
    
    @FXML
    void savePlanet(ActionEvent event) {
    	if (validatePlanet() == false)
    		return;
    	fileChooser.setTitle("Save Planet As File");
    	//Set extension filter
        ExtensionFilter extensionFilter = new ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        
        File file = fileChooser.showSaveDialog(new Stage());
        
        if(file != null)
            saveFile(addInputToBuffer(), file);
        
        fileChooser.getExtensionFilters().remove(extensionFilter);
        
    }
    
    @FXML
    void setPlanetName(ActionEvent event) {
    	//TODO he suggested the validator should be delegated
    	//TODO builder to make planet object
    		planet.setPlanetName(planetName.getText());
    		fancyPlanetName.setText(planet.getPlanetName());
    }
    
    @FXML
    void setPlanetDiameter(ActionEvent event) {
    	try{
    		diameter = Double.parseDouble(planetDiameterKM.getText());
    		if (diameter < MIN_DIAMETER || diameter > MAX_DIAMETER){
    			showError("Planet diameter must be between 0 and 200,000 km.");
    			return;
    		}
    		planetDiameterKM.setText(String.format("%,f", diameter));
    		planetDiameterM.setText(String.format("%,f", diameter*0.621371));
    	}
    	catch (NumberFormatException e){
    		conversionError(e.getMessage().toLowerCase());
    	}
    }
    
    @FXML
    void setPlanetTemp(ActionEvent event) {
    	try{
    		temperature = Double.parseDouble(planetMeanSurfaceTempC.getText());
    		if (temperature < MIN_DEGREES || temperature > MAX_DEGREES){
    			showError("Planet temperature must be between -273.15\u00b0 and 500.0\u00b0 C.");
    			return;
        	}
    		planetMeanSurfaceTempF.setText(""+(temperature*1.8+32));
    	}
    	catch (NumberFormatException e){
    		conversionError(e.getMessage().toLowerCase());
    	}
    }
    
    @FXML
    void setMoons(ActionEvent event) {
    	try{
    		moons = Integer.parseInt(planetNumberOfMoons.getText());
    		if (moons < MIN_MOONS || moons > MAX_MOONS){
    			showError("Number of moons must be between 0 and 1,000.");
    			return;
    		}
    		planetNumberOfMoons.setText(String.format("%,d", moons));
    	}
    	catch (NumberFormatException e){
    		conversionError(e.getMessage().toLowerCase());
    	}
    }
    
    public void showError(String errorMessage){
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Value Entered");
		System.err.println("ERROR: "+errorMessage);
		alert.setContentText(errorMessage);
		alert.showAndWait();
    }
    
    public void conversionError(String errorMessage){
    	errorMessage = "Cannot do numerical conversion "+ errorMessage +".\nEnter valid number.";
    	if (errorMessage.contains("empty"))
    		errorMessage = errorMessage.replace("empty","on empty");
    	showError(errorMessage);
    }
    
    private void saveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
        	showError(e.getMessage());
        }
    }
    
    public boolean validatePlanet(){
		if (moons < MIN_MOONS || moons > MAX_MOONS){
			showError("Number of moons must be between 0 and 1,000.");
			return false;
		}
		
		if (temperature < MIN_DEGREES || temperature > MAX_DEGREES){
			showError("Planet temperature must be between -273.15\u00b0 and 500.0\u00b0 C.");
			return false;
		}
		
		if (diameter < MIN_DIAMETER || diameter > MAX_DIAMETER){
			showError("Planet diameter must be between 0 and 200,000 km.");
			return false;
		}
		
		if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH){
    		showError("Planet name must be between 1 and 255 characters long.");
    		return false;
    	}
    	if (!name.matches("[-a-zA-Z0-9. ]+") ){
    		showError("Planet name must contain one or more alphanumeric and/or punctation characters (\".\", \"-\", or \" \").");
    		return false;
    	}
		
		return true;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		planetImage.setImage(new Image("images/no_image.png"));
		planetImage.setId("images/no_image.png");
		moons = MIN_MOONS-1;
		name = "";
		diameter = MIN_DIAMETER-1;
		temperature = MIN_DEGREES-1;
	}
	
	private String addInputToBuffer(){
        return  (planetImage.getId()+"\n"+planetName.getText()+"\n"+diameter+"\n"+(diameter*0.621371)+
        		"\n"+temperature+"\n"+(temperature*1.8+32)+"\n"+moons);
	}
	
	private void setPlanetInformationFromFile(){
		try {
    		Scanner scanner = new Scanner(fileChooser.showOpenDialog(new Stage()));
    		planetImage.setId(scanner.nextLine());
    		planetImage.setImage(new Image(new FileInputStream(planetImage.getId())));
    		planetName.setText((name = scanner.nextLine()));
    		fancyPlanetName.setText(name);
    		planetDiameterKM.setText(String.format("%,f",(diameter = scanner.nextDouble())));
    		planetDiameterM.setText(String.format("%,f",scanner.nextDouble()));
    		planetMeanSurfaceTempC.setText(""+(temperature = scanner.nextDouble()));
    		planetMeanSurfaceTempF.setText(""+scanner.nextDouble());
    		planetNumberOfMoons.setText(String.format("%,d", (moons = scanner.nextInt())));
            scanner.close();
		} catch (FileNotFoundException e) {
			showError(e.getMessage());
		} catch (NoSuchElementException e){
			showError(e.getMessage());
		}
	}
}