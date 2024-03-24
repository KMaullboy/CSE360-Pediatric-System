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
    		
    		
    		/*
    		 * Path to the PatientInformation.txt file
    		 * 
    		 * This file is where we will find the usernames and passwords for users
    		 * of the patientView.
    		 */
    		
    		if ( !firstNameField.getText().equals("") && !lastNameField.getText().equals("") &&
        			!addressLine1Field.getText().equals("") && !cityField.getText().equals("") &&
        			!stateField.getText().equals("") && !birthYearField.getText().equals("") && 
        			!passwordField.getText().equals(""))
        	{
    			
    			if (birthYear > currentYear) 
	    		{
	    			throw new Exception("Invalid Age");
	    		}
				
        		//If not, save all the information in PatientID_PatientInfo.txt
        		
        		//Start by generating an ID, keep trying until an ID is generated
        		int ID = 0;
        		File f;
        		while (true) 
        		{                	
        			username = String.valueOf(firstName.charAt(0)) + String.valueOf(lastName.charAt(0)) +  month + String.valueOf(birthYear % 100)
					+ String.format("%05d", ID);
        			
            		String path = username + "_PatientInfo.txt";
            		
        			f = new File(path);
        			
        			if (!f.exists())
        			{
        				//Create this file
        				try {
							f.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				
        				System.out.println("File Created");
        				
        				usernameField.setText(username);
        				
        				//Get all patient information
        				String patientInformation = firstNameField.getText() + "\n" + lastNameField.getText() 
        				+ "\n" + addressLine1Field.getText() + "\n" + addressLine2Field.getText() + "\n" + 
        				cityField.getText() + "\n" + stateField.getText() + "\n" + zipCodeField.getText()
        				 + "\n" + monthChoiceBox.getValue() + "\n" + birthYearField.getText()
        				 + "\n" + usernameField.getText() + "\n" + passwordField.getText();
       	
        				/*
        				 * Store information in newly created text file
        				 */
        				try {
							bw = new BufferedWriter(new FileWriter(path));
							bw.write(patientInformation);
            				bw.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

        				return;
        			}
        			else if (ID == 99999) 
        			{
        				//No unique 5-Digit ID's available
        				System.out.println("No ID available");
        				return;
        			}
        			ID++;
        		}
        	}
    	} catch(Exception e) {
			e.printStackTrace();
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
