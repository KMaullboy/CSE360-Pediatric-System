package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;

import application.ChangeContactController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PatientViewController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	String firstName, lastName, addressLine1, addressLine2, city, state, 
    username, password, birthMonth, zipCode, birthYear;
	BufferedReader br;
	BufferedWriter bw;
	
	/*
	 * Using @FXML before declaring private fields allows them to be recognized
	 * by SceneBuilder if you choose this controller class as the controller
	 * for your fxml file
	 */
	
    @FXML
    private Label welcome;
    @FXML
    private Label initials;
    @FXML
    private ChoiceBox<String> dropdown;
    @FXML
    private Label date;
    
    private SortedMap<String, ViewInfo> visits = new TreeMap<>();
	
    public void init(String name) throws IOException {
        String filename = name + "_PatientInfo.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            firstName = reader.readLine();
            lastName = reader.readLine();
            addressLine1 = reader.readLine();
            addressLine2 = reader.readLine();
            city = reader.readLine();
            state = reader.readLine();
            zipCode = reader.readLine();
            birthMonth = reader.readLine();
            birthYear = reader.readLine();
            username = reader.readLine();
            password = reader.readLine();
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace(); 
        }
        
        filename = name + "_Visit.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ViewInfo newInfo = new ViewInfo();
                newInfo.firstName = line;
                newInfo.lastName = reader.readLine();
                newInfo.dateOfBirth = reader.readLine();
                newInfo.age = reader.readLine();
                newInfo.date = reader.readLine();
                newInfo.ID = reader.readLine();
                newInfo.insuranceName = reader.readLine();
                newInfo.memberID = reader.readLine();
                newInfo.groupNumber = reader.readLine();
                newInfo.pharmacyName = reader.readLine();
                newInfo.pharmacyAddress = reader.readLine();
                newInfo.pharmacyPhoneNumber = reader.readLine();
                newInfo.height = reader.readLine();
                newInfo.weight = reader.readLine();
                newInfo.bodyTemperature = reader.readLine();
                newInfo.bloodPressure = reader.readLine();
                newInfo.allergies = reader.readLine();
                newInfo.healthConcerns = reader.readLine();
                newInfo.notes = reader.readLine();
                newInfo.previousHealthIssues = reader.readLine();
                newInfo.previousPrescribedMeds = reader.readLine();
                newInfo.history = reader.readLine();
                
                visits.put(newInfo.date, newInfo);
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace(); 
        }
        
        ObservableList<String> visitDates = FXCollections.observableArrayList(visits.keySet());
        dropdown.setItems(visitDates);
        
        if (!visits.isEmpty()) {
            dropdown.setValue(visits.lastKey());
        }
        
        if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()) {
            char firstCharFirstName = firstName.charAt(0);
            char firstCharLastName = lastName.charAt(0);
            initials.setText(String.valueOf(firstCharFirstName) + String.valueOf(firstCharLastName));
        }
        
        welcome.setText("Welcome Back " + firstName + "!");
    }
	/*
	 * switchtoPatientLogin() is called when the user clicks "Logout" button in the 
	 * PatientView page. It switches the displayed page to PatientLogin.fxml
	 */
    		
	
	public void switchtoPatientLogin(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("PatientLogin.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void openSummaryPopup(ActionEvent event) throws IOException {
		String selectedDate = dropdown.getValue();
		if (selectedDate == null) {
			return;
		}
		ViewInfo viewInfo = visits.get(selectedDate);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewSummaryPopup.fxml"));
        Parent root2 = loader.load();
        Scene scene2 = new Scene(root2);
        Stage stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.initModality(Modality.APPLICATION_MODAL);
        
	    ViewSummaryPopupController control = loader.getController();
	    control.init(selectedDate, viewInfo); 
        
        stage2.show();
	}
	@FXML
	public void changeContactInfo(ActionEvent event) throws IOException {
	    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeContact.fxml")); 
	    Parent root = loader.load();
	    ChangeContactController controller = loader.getController();
	    controller.initData(username);
	    stage.getScene().setRoot(root);
	}

	
	@FXML
	public void sendMessageAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientMessages.fxml"));
        Parent root = loader.load();
        // Create a new stage for the pop-up
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Use Modality.WINDOW_MODAL for blocking only the parent window
        popupStage.setTitle("Pop-up Window");
        
        // Set the scene and show the stage
        popupStage.setScene(new Scene(root));
        popupStage.showAndWait(); // Show and wait until the pop-up is closed
	}
	
	@FXML
	public void openReceivedMessagesAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceivedPatientMessages.fxml"));
        Parent root = loader.load();
        // Create a new stage for the pop-up
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Use Modality.WINDOW_MODAL for blocking only the parent window
        popupStage.setTitle("Pop-up Window");
        
        // Set the scene and show the stage
        popupStage.setScene(new Scene(root));
        popupStage.showAndWait(); // Show and wait until the pop-up is closed
	}
	
	
	
}
