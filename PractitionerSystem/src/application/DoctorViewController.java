package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class DoctorViewController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public TextField firstNameField;
	public TextField lastNameField;
	public TextField dateOfBirthField;
	public TextField ageField;
	public TextField dateField;
	public TextField IDField;
	
	public TextArea physicalTestFindingsField;
	public TextArea recommendationsField;
	
	public TextField medicationField;
	public TextField prescribedByField;
	public TextField qtyDurationField;
	public TextField refillsField;
	public TextArea prescriptionCommentsField;
	
	public TextField heightField;
	public TextField weightField;
	public TextField bodyTemperatureField;
	public TextField bloodPressureField;
	
	public TextArea allergiesField;
	public TextArea healthConcernsField;
	public TextArea notesField;
	
	public TextArea previousHealthIssuesField;
	public TextArea previousPrescribedMedsField;
	public TextArea historyOfImmunizationsField;
	
	@FXML
	private Button loadPatientInfoButton;
	
	@FXML
	private Button savePatientInfoButton;
	
	@FXML
	private Button sendPrescriptionButton; 
	
	@FXML 
	private Label error;
	
	public String insuranceName, memberID, groupNumber, pharmacyName, pharmacyAddress, pharmacyPhoneNumber;
	
	public String fileName = "";
	
	// Identifies Error & Success messages
	 private void showError(String message) {
		 
	        error.setTextFill(Color.RED);
	        error.setText(message);
	        error.setVisible(true);
	    }

	    private void showSuccess(String message) {
	    	
	        error.setTextFill(Color.GREEN);
	        error.setText(message);
	        error.setVisible(true);
	    }
	
	// Loads patient info using patient ID
	@FXML
	private void loadPatientInfo(ActionEvent event) {
		
	    String patientID = IDField.getText();
	    
	    //I CHANGED THIS TO _Visit.txt so it would work
	    File file = new File(patientID + "_CurrentVisit.txt");
	    
	    if (file.exists()) {
	    	
	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        	
	            firstNameField.setText(reader.readLine());
	            lastNameField.setText(reader.readLine());
	            dateOfBirthField.setText(reader.readLine());
	            ageField.setText(reader.readLine());
	            dateField.setText(reader.readLine());
	            reader.readLine();// IDField is already set
	            insuranceName = reader.readLine();//Insurance Name
	            memberID = reader.readLine();//Insurance ID
	            groupNumber = reader.readLine();//Group Number
	            pharmacyName = reader.readLine();//PharmacyName
	            pharmacyAddress = reader.readLine();//PharmacyAddress
	            pharmacyPhoneNumber = reader.readLine();//Pharmacy Number
	            heightField.setText(reader.readLine());
	            weightField.setText(reader.readLine());
	            bodyTemperatureField.setText(reader.readLine());
	            bloodPressureField.setText(reader.readLine());
	            allergiesField.setText(reader.readLine());
	            healthConcernsField.setText(reader.readLine());
	            notesField.setText(reader.readLine());
	            
	            previousHealthIssuesField.setText(reader.readLine());
	            previousPrescribedMedsField.setText(reader.readLine());
	            historyOfImmunizationsField.setText(reader.readLine());
	            //physicalTestFindingsField.setText(reader.readLine());
	            //recommendationsField.setText(reader.readLine());
	        } catch (IOException e) {
	        	
	            showError("Failed to load patient information.");
	        }
	    } else {
	    	
	        showError("Patient information not found for ID: " + patientID);
	    }
	}
	
	// Saves changes to the patient's text file record
	@FXML
	public void save(ActionEvent event) {
		
		String patientID = IDField.getText();
	    if (patientID.isEmpty()) {
	        showError("Patient ID is required.");
	        return;
	    }
	    
	    //I CHANGED THIS TO OVERWRITE THE VISIT FILE INSTEAD SINCE THE PATIENT USERNAME/PASSWORD IS IN PATIENTINFO
	    int visitNumber = 1;
	    
	    //fileName is the name of the most recent visit# (Has same info as CurrentVisit)
	    File file = new File(fileName);
	    
	    //If the new filename has not been created already
	    if (fileName.equals(""))
	    {
	    	fileName = patientID + "_Visit" + String.format("%02d", visitNumber) + ".txt";
	    	file = new File(fileName);
	    	
	    	//Increment the number at the end of the file name until we get a filename that does not exist
	    	while (file.exists())
		    {
	    		visitNumber++;
	    		fileName = patientID + "_Visit" + String.format("%02d", visitNumber) + ".txt";
		    	file = new File(fileName);
		    }
	    }
	    
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	    	
	    	System.out.println("area1");
	    	
	        writer.write(firstNameField.getText() + "\n");
	        writer.write(lastNameField.getText() + "\n");
	        writer.write(dateOfBirthField.getText() + "\n");
	        writer.write(ageField.getText() + "\n");
	        writer.write(dateField.getText() + "\n");
	        writer.write(IDField.getText() + "\n");
	        
	        System.out.println("area1");
	        
	        //I Added these - Kaleb
	        writer.write(insuranceName + "\n");
	        writer.write(memberID + "\n");
	        writer.write(groupNumber + "\n");
	        writer.write(pharmacyName + "\n");
	        writer.write(pharmacyAddress + "\n");
	        writer.write(pharmacyPhoneNumber + "\n");
	        /////////////////////////////////////////
	        
	        System.out.println("area2");
	        
	        writer.write(heightField.getText() + "\n");
	        writer.write(weightField.getText() + "\n");
	        writer.write(bodyTemperatureField.getText() + "\n");
	        writer.write(bloodPressureField.getText() + "\n");
	        writer.write(allergiesField.getText() + "\n");
	        writer.write(healthConcernsField.getText() + "\n");
	        writer.write(notesField.getText() + "\n");
	        writer.write(previousHealthIssuesField.getText() + "\n");
	        writer.write(previousPrescribedMedsField.getText() + "\n");
	        writer.write(historyOfImmunizationsField.getText() + "\n");
	        writer.write(physicalTestFindingsField.getText() + "\n");
	        writer.write(recommendationsField.getText() + "\n");
	        // Append prescription information only if new or updated
	        writer.write(medicationField.getText() + "\n");
	        writer.write(prescribedByField.getText() + "\n");
	        writer.write(qtyDurationField.getText() + "\n");
	        writer.write(refillsField.getText() + "\n");
	        writer.write(prescriptionCommentsField.getText());
	        
	        System.out.println("area3");
	        
	        showSuccess("Information saved successfully.");    
	        
	        //This saves the info as in a Current Visit file as well
	        System.out.println("Saved visit#");
	        saveCurrentVisit();
	        
	    } catch (IOException e) {
	    	
	        showError("Failed to save patient information.");
	    }
	}
	
	// Initially hides prescription fields until "Add New Prescription" button is pressed
		 @FXML
		    public void initialize() {
			 
		        medicationField.setVisible(false);
		        prescribedByField.setVisible(false);
		        qtyDurationField.setVisible(false);
		        refillsField.setVisible(false);
		        prescriptionCommentsField.setVisible(false);
		    }
		 
		 public void saveCurrentVisit()
		 {
			 String patientID = IDField.getText();
			    if (patientID.isEmpty()) {
			        showError("Patient ID is required.");
			        return;
			    }
			    
			    //I CHANGED THIS TO OVERWRITE THE VISIT FILE INSTEAD SINCE THE PATIENT USERNAME/PASSWORD IS IN PATIENTINFO
			    File file = new File(patientID + "_CurrentVisit.txt");
				
			    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			    	
			        writer.write(firstNameField.getText() + "\n");
			        writer.write(lastNameField.getText() + "\n");
			        writer.write(dateOfBirthField.getText() + "\n");
			        writer.write(ageField.getText() + "\n");
			        writer.write(dateField.getText() + "\n");
			        writer.write(IDField.getText() + "\n");
			        
			        //I Added these - Kaleb
			        writer.write(insuranceName + "\n");
			        writer.write(memberID + "\n");
			        writer.write(groupNumber + "\n");
			        writer.write(pharmacyName + "\n");
			        writer.write(pharmacyAddress + "\n");
			        writer.write(pharmacyPhoneNumber + "\n");
			        /////////////////////////////////////////
			        
			        writer.write(heightField.getText() + "\n");
			        writer.write(weightField.getText() + "\n");
			        writer.write(bodyTemperatureField.getText() + "\n");
			        writer.write(bloodPressureField.getText() + "\n");
			        writer.write(allergiesField.getText() + "\n");
			        writer.write(healthConcernsField.getText() + "\n");
			        writer.write(notesField.getText() + "\n");
			        writer.write(previousHealthIssuesField.getText() + "\n");
			        writer.write(previousPrescribedMedsField.getText() + "\n");
			        writer.write(historyOfImmunizationsField.getText() + "\n");
			        writer.write(physicalTestFindingsField.getText() + "\n");
			        writer.write(recommendationsField.getText() + "\n");
			        // Append prescription information only if new or updated
			        writer.write(medicationField.getText() + "\n");
			        writer.write(prescribedByField.getText() + "\n");
			        writer.write(qtyDurationField.getText() + "\n");
			        writer.write(refillsField.getText() + "\n");
			        writer.write(prescriptionCommentsField.getText());
			        
			        showSuccess("Information saved successfully.");        
			    } catch (IOException e) {
			    	
			        showError("Failed to save patient information.");
			    }
		 }
	
	
	// Shows prescription fields when "Add New Prescription" button is pressed
	 @FXML
	    private void showPrescriptionFields(ActionEvent event) {
		 
	        medicationField.setVisible(true);
	        prescribedByField.setVisible(true);
	        qtyDurationField.setVisible(true);
	        refillsField.setVisible(true);
	        prescriptionCommentsField.setVisible(true);
	    }
	 
	 /* When the "Send" button is pressed in the prescription section,
	  * the changes to the prescription fields are saved in the patient's
	  * text file record
	  */
	 @FXML
	 private void sendPrescription(ActionEvent event) {
		 
	     String patientID = IDField.getText();
	     if (patientID.isEmpty()) {
	         showError("Patient ID is required for sending a prescription.");
	         return;
	     }
	     
	     int visitNumber = 1;
	     File file = new File(fileName);
	     
	     if (fileName.equals(""))
	     {
	    	 fileName = patientID + "_Visit" + String.format("%02d", visitNumber) + ".txt";
		    	
		     //Increment the number at the end of the file name until we get a filename that does not exist
		     while (file.exists())
			 {
		    	 visitNumber++;
		    	 fileName = patientID + "_Visit" + String.format("%02d", visitNumber) + ".txt";
			     file = new File(fileName);
			 }
	     }
	     
	     // Append the prescription information to the file
	     try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) { // true for appending
	        
	         if (!file.exists() || file.length() == 0) {
	             writer.write("\nPrescriptions:\n");
	         }
	         writer.write("\nMedication: " + medicationField.getText());
	         writer.write("\nPrescribed By: " + prescribedByField.getText());
	         writer.write("\nQuantity/Duration: " + qtyDurationField.getText());
	         writer.write("\nRefills: " + refillsField.getText());
	         writer.write("\nComments: " + prescriptionCommentsField.getText() + "\n");
	         
	         showSuccess("Prescription sent successfully.");
	     } catch (IOException e) {
	    	 
	         showError("Failed to send prescription.");
	     }
	 }

	
	// When user clicks "logout" in DoctorView, they are redirected to the Practitioner Login page
	public void switchtoPractitionerLogin(ActionEvent event) throws IOException {
		
		root = FXMLLoader.load(getClass().getResource("PractitionerLogin.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void viewMessages(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("PractitionerMessages.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
}
	