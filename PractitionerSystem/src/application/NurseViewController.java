package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NurseViewController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	BufferedReader br;
	BufferedWriter bw;
	
	
	public TextField firstNameField;
	public TextField lastNameField;
	public TextField dateOfBirthField;
	public TextField ageField;
	public TextField dateField;
	public TextField IDField;
	public TextField insuranceNameField;
	public TextField memberIDField;
	public TextField groupNumberField;
	public TextField pharmacyNameField;
	public TextField pharmacyAddressField;
	public TextField pharmacyPhoneNumberField;
	public TextField heightField;
	public TextField weightField;
	public TextField bodyTemperatureField;
	public TextField bloodPressureField;
	public TextArea  allergiesField;
	public TextArea healthConcernsField;
	public TextArea notesField;
	public TextArea previousHealthIssuesField;
	public TextArea previousPrescribedMedsField;
	public TextArea historyField;
	
	@FXML 
	private Label error;
	
	
	String firstName, lastName, dateOfBirth, age, date, ID, insuranceName, memberID, groupNumber, 
		   pharmacyName, pharmacyAddress, pharmacyPhoneNumber, height, weight, bodyTemperature,
		   bloodPressure, allergies, healthConcerns, notes, previousHealthIssues, previousPrescribedMeds,
		   history;
	
	public void save(ActionEvent event) throws IOException 
    {
    	try 
    	{
    		
    		//Get all the information in the text fields
    		firstName = firstNameField.getText();
    		lastName = lastNameField.getText();
    		dateOfBirth = dateOfBirthField.getText();
    		age = ageField.getText();
    		date = dateField.getText();
    		ID = IDField.getText();
    		insuranceName = insuranceNameField.getText();
    		memberID = memberIDField.getText();
    		groupNumber = groupNumberField.getText();
    		pharmacyName = pharmacyNameField.getText();
    		pharmacyAddress = pharmacyAddressField.getText();
    		pharmacyPhoneNumber = pharmacyPhoneNumberField.getText();
    		height = heightField.getText();
    		weight = weightField.getText();
    		bodyTemperature = bodyTemperatureField.getText();
    		bloodPressure = bloodPressureField.getText();
    		allergies = allergiesField.getText();
    		healthConcerns = healthConcernsField.getText();
    		notes = notesField.getText();
    		previousHealthIssues= previousHealthIssuesField.getText();
    		previousPrescribedMeds = previousPrescribedMedsField.getText();
    		history = historyField.getText();
    		
    		
    		
    		if ( !firstName.equals("") && !lastName.equals("") && !dateOfBirth.equals("") && !age.equals("")
    				&& !date.equals("") && !ID.equals("") && !insuranceName.equals("") && !memberID.equals("")
    				&& !groupNumber.equals("") && !pharmacyName.equals("") && !pharmacyAddress.equals("")
    				&& !pharmacyPhoneNumber.equals("") && !height.equals("") && !weight.equals("")
    				&& !bodyTemperature.equals("") && !bloodPressure.equals("") && !allergies.equals("")
    				&& !healthConcerns.equals("") && !notes.equals("") && !previousHealthIssues.equals("")
    				&& !previousPrescribedMeds.equals("") && !history.equals(""))
        	{
    			try {
        			Integer.parseInt(ageField.getText());
        			Integer.parseInt(groupNumberField.getText());
        			Integer.parseInt(pharmacyPhoneNumberField.getText());
        			Integer.parseInt(heightField.getText());
        			Integer.parseInt(weightField.getText());
        			Integer.parseInt(bodyTemperatureField.getText());
        			Integer.parseInt(bloodPressureField.getText());
        		} catch (NumberFormatException e)
        		{
        			error.setVisible(true);
        			error.setText("Only numbers for Age,\nGroup Number, Height,\nWeight, "
        					+ "Body\nTemperature, Phone \nNumber and Blood \nPressure");
        			return;
        		}
    			
    			if (Integer.parseInt(age) < 0)
    			{
    				error.setVisible(true);
        			error.setText("Invalid Age");
        			return;
    			}
    			
            		String path1 = ID + "_Visit.txt";
            		String path2 = ID + "_PatientInfo.txt";
            		
        			File f = new File(path2);
        			
        			if (f.exists())
        			{
        				f = new File(path1);
        				
        				if (f.exists())
        				{
        					error.setVisible(true);
        					error.setText("Patient info has already \nbeen recorded");
        					return;
        				}
        				//Create this file
        				try {
							f.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				
        				System.out.println("File Created");
        				        				
        				//Get all patient information
        				String patientInformation = firstName + "\n" + lastName + "\n" + dateOfBirth
        						 + "\n" + age + "\n" + date + "\n" + ID + "\n" + insuranceName + "\n" + memberID
        						 + "\n" + groupNumber + "\n" + pharmacyName + "\n" + pharmacyAddress + "\n" + 
        						 pharmacyPhoneNumber + "\n" + height + "\n" + weight + "\n" + bodyTemperature
        						 + "\n" + bloodPressure + "\n" + allergies + "\n" + healthConcerns + "\n" + notes
        						 + "\n" + previousHealthIssues + "\n" + previousPrescribedMeds + "\n" + history;
       	
        				/*
        				 * Store information in newly created text file
        				 */
        				try {
							bw = new BufferedWriter(new FileWriter(path1));
							bw.write(patientInformation);
            				bw.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				
        				error.setVisible(false);
        				return;
        				
        			}
        			
        			//Means patient is not in the system
        			error.setVisible(true);
					error.setText("Invalid Patient \nID");
        			return;
        		
        	}
    		
    		//If program goes here, user has not filled in all boxes
    		error.setVisible(true);
    		error.setText("Please Fill in \nall fields");
    		
    	} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	public void getPreviousInfo(ActionEvent event) throws IOException
	{
		//Get the user ID that was entered
		ID = IDField.getText();
		
		String path2 = ID + "_Visit.txt";
		

		File f = new File(path2);
		
		//If the user has previous health information from a previous visit
		if (f.exists())
		{
			//Read that file
			br = new BufferedReader(new FileReader(path2));
			
			try {
			
			//Read in the previous visit information
			firstNameField.setText(br.readLine());
    		lastNameField.setText(br.readLine());
    		dateOfBirthField.setText(br.readLine());
    		ageField.setText(br.readLine());
    		dateField.setText(br.readLine());
    		br.readLine();
    		insuranceNameField.setText(br.readLine());
    		memberIDField.setText(br.readLine());
    		groupNumberField.setText(br.readLine());
    		pharmacyNameField.setText(br.readLine());
    		pharmacyAddressField.setText(br.readLine());
    		pharmacyPhoneNumberField.setText(br.readLine());
    		
    		//Read over the lines that we do not need
    		for (int i = 0; i < 7; i++)
    		{
    			br.readLine();
    		}
    		
			previousHealthIssuesField.setText(br.readLine());
    		previousPrescribedMedsField.setText(br.readLine());
    		historyField.setText(br.readLine());
    		
			} catch(IOException e) {
				//Error occurred
			}
		}
		
	}
	
	/*
	 * switchtoPractitionerLogin() is called when the user clicks "Logout" in the 
	 * DoctorView page. It switches the displayed page to PractitionerLogin.fxml
	 */
	
	public void switchtoPractitionerLogin(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("PractitionerLogin.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
