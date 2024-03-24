package application;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CreateAccountController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	BufferedReader br;
	BufferedWriter bw;
	
	String patientInformation;
	
	/*
	 * Using @FXML before declaring private fields allows them to be recognized
	 * by SceneBuilder if you choose this controller class as the controller
	 * for your fxml file
	 */
	
	@FXML
	private TextField firstNameField, lastNameField, addressLine1Field, addressLine2Field,
					  cityField, stateField, zipCodeField, birthYearField, usernameField;
	@FXML
	private PasswordField passwordField;
    @FXML
    private Button submit, returnToLogin;
    @FXML
    ChoiceBox<String> monthChoiceBox;
    
    String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    
    String firstName, lastName, addressLine1, addressLine2, city, state,
           password, month, username;
    
    int birthYear, zipCode, currentYear = 2024;
    
    /*
	 * The initialize() method runs right before the page that this class controls
	 * is opened.
	 * 
	 * This initialize() method will populate the BirthMonth dropdown button with numbers from 01-12
	 */
    
    public void initialize(URL url, ResourceBundle rb) 
    {
        	monthChoiceBox.getItems().addAll(months);
        	monthChoiceBox.setValue("01");
    }
    
    /*
     * The submit() method is called when the user clicks the Submit button in the CreateAccount page.
     * It will check the information typed into the boxes and either create a new account or not
     */
    
    public void submit(ActionEvent event) throws IOException 
    {
    	try 
    	{
    		//Get all the information in the text fields
    		firstName = firstNameField.getText();
    		lastName = lastNameField.getText();
    		addressLine1 = addressLine1Field.getText();
    		addressLine2 = addressLine2Field.getText();
    		city = cityField.getText();
    		state = stateField.getText();
    		zipCode = Integer.parseInt(zipCodeField.getText());
    		birthYear = Integer.parseInt(birthYearField.getText());
    		password = passwordField.getText();
    		month = monthChoiceBox.getValue();
    	
    		//Validates the age of the user
    		if (birthYear > currentYear) 
    		{
    			throw new Exception("Invalid Age");
    		}

    		//Generates the user's username
    		username = firstName + lastName + month + String.valueOf(birthYear % 100);
    		
    		/*
    		 * Path to the PatientInformation.txt file
    		 * 
    		 * This file is where we will find the usernames and passwords for users
    		 * of the patientView.
    		 */
    		
    		String path = "PatientInformation.txt";
    		
    		File f = new File(path);
    		
    		if (!f.exists()) //Create the file if it does not exist already
    		{
                // Create the directory along with any necessary parent directories
                boolean created = f.createNewFile();
    		}
    		else 
    		{
    			/*
    			 * Read every line of the file and check if the username generated and password
    			 * entered by the user matches the username and password of any other user
    			 * stored in the file.
    			 * 
    			 * Format: Username|Password|Addressline1|AddressLine2|City|State|Zip|BirthMonth|BirthYear
    			 */
    			
    			br = new BufferedReader(new FileReader("PatientInformation.txt"));
        		
        		String line;
        		
        		//Parse the line read and split it into sections separated by "|"
        		while ((line = br.readLine()) != null) 
        		{
        			String[] info = line.split("\\|");
        			
        			if (info.length == 1) 
        			{
        				break;
        			}
        			
        			for (int i = 0; i < info.length; i++) {
        				//System.out.println(info[i]);
        			}
        			
        			/*
        			 * If the username and password combination matches any other user, 
        			 * do not create the account
        			 */
        			if (info[0].equals(username) && info[1].equals(password)) 
        			{
        				//System.out.println("Account already exists");
        				throw new Exception("Account Already Exists");
        			}
        		}
    		}
    		
    		 //If the account is to be created, display the generated username to the user
    		usernameField.setText(username);
    	
    		//Format all the user information entered so that it can be stored in PatientInformation.txt
    		patientInformation = username + "|" + password + "|" + firstName + "|" + lastName + "|" + 
    	                     addressLine1 + "|" + addressLine2 + "|" + city + "|" + state + "|" + 
    	                     zipCode + "|" + month + "|" + birthYear + "\n";
    	
    		/*
    		 * Store the new patient information in PatientInformation.txt. 
    		 * The true parameter means that the file will be appended to, not overwritten
    		 */
    		bw = new BufferedWriter(new FileWriter("PatientInformation.txt", true));
    		
    	
    		bw.write(patientInformation);
    		
    		bw.close();
    	}
    	catch (NumberFormatException e)
    	{
    		//System.out.println("Only numbers inBirth Year and Zip Code Fields!");
    	}
    	catch (Exception e) 
    	{
    		System.out.println(e);
    	}
    	
    	
    	
    	return;
    }
    
    /*
	 * switchtoPatientLogin() is called when the user clicks "Return to Login" button in the 
	 * CreateAccount page. It switches the displayed page to PatientLogin.fxml
	 */
    
    public void switchtoPatientLogin(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("PatientLogin.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
    
    
    
}
