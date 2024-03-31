package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

public class DoctorViewController {

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
	private Label error;
	
	public void save(ActionEvent event) throws IOException {
		
		try {
			
			String patientInformation = firstNameField.getText() + "\n" 
				    + lastNameField.getText() + "\n" 
				    + dateOfBirthField.getText() + "\n" 
				    + ageField.getText() + "\n" 
				    + dateField.getText() + "\n" 
				    + IDField.getText() + "\n" 
				    + heightField.getText() + "\n" 
				    + weightField.getText() + "\n" 
				    + bodyTemperatureField.getText() + "\n" 
				    + bloodPressureField.getText() + "\n" 
				    + allergiesField.getText() + "\n" 
				    + healthConcernsField.getText() + "\n" 
				    + notesField.getText() + "\n" 
				    + previousHealthIssuesField.getText() + "\n" 
				    + previousPrescribedMedsField.getText() + "\n" 
				    + historyOfImmunizationsField.getText() + "\n"
				    + physicalTestFindingsField.getText() + "\n" 
				    + recommendationsField.getText() + "\n"
				    + medicationField.getText() + "\n"
				    + prescribedByField.getText() + "\n"
				    + qtyDurationField.getText() + "\n"
				    + refillsField.getText() + "\n"
				    + prescriptionCommentsField.getText();

		}
		catch (Exception e) {}
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
	
	 @FXML
	    public void initialize() {
	        // Initially hide the prescription fields if they should not be visible from the start
	        medicationField.setVisible(false);
	        prescribedByField.setVisible(false);
	        qtyDurationField.setVisible(false);
	        refillsField.setVisible(false);
	        prescriptionCommentsField.setVisible(false);

	        // Initialize any other UI components as needed
	    }
}
