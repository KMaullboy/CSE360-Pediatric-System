package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChangeContactController {
	String firstName, lastName, addressLine1, addressLine2, city, state, username, password, birthMonth, zipCode, birthYear;

    @FXML
    private TextField addressLine1Field;

    @FXML
    private TextField addressLine2Field;

    @FXML
    private TextField cityField;

    @FXML
    private TextField stateField;

    @FXML
    private TextField zipCodeField;

    @FXML
    private Button submitButton;
    
    @FXML
    private Button returnButton;
    
    @FXML
    private Label successLabel;
    
    private Parent root;
	private Stage stage;
	private Scene scene;
    // You may include more fields as necessary for your application

    // Method to initialize data in the fields if needed
    public void initData(String username) {
    	this.username = username;
    	String file = username + "_PatientInfo.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        	firstName = reader.readLine();
            lastName = reader.readLine();
            addressLine1 = reader.readLine();
            addressLine2 = reader.readLine();
            city = reader.readLine();
            state = reader.readLine();
            zipCode = reader.readLine();
            birthMonth = reader.readLine();
            birthYear = reader.readLine();
            this.username = reader.readLine();
            password = reader.readLine();
            reader.close();
            
            addressLine1Field.setText(addressLine1);
            addressLine2Field.setText(addressLine2);
            cityField.setText(city);
            stateField.setText(state);
            zipCodeField.setText(zipCode);
        }
        catch (Exception e) {
            e.printStackTrace(); 
        }

    }
    public void saveContactInfo(ActionEvent event) {
        addressLine1 = addressLine1Field.getText();
        addressLine2 = addressLine2Field.getText();
        city = cityField.getText();
        state = stateField.getText();
        zipCode = zipCodeField.getText();
        
        if (addressLine1.isEmpty() || addressLine2.isEmpty() || city.isEmpty() || state.isEmpty() || zipCode.isEmpty()) {
            successLabel.setVisible(true);
            successLabel.setText("Error: Please fill in all fields.");
            return; 
        }
        
        String filename = username + "_PatientInfo.txt";
        try (FileWriter writer = new FileWriter(filename)) {
        	writer.write(firstName + "\n");
            writer.write(lastName + "\n");
            writer.write(addressLine1 + "\n");
            writer.write(addressLine2 + "\n");
            writer.write(city + "\n");
            writer.write(state + "\n");
            writer.write(zipCode + "\n");
            writer.write(birthMonth + "\n");
            writer.write(birthYear + "\n");
            writer.write(username + "\n");
            writer.write(password + "\n");
            
            successLabel.setVisible(true);
            successLabel.setText("Contact information updated!");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
	public void switchtoPatientView(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientView.fxml"));
	    root = loader.load();

	    PatientViewController patientViewController = loader.getController();
	    patientViewController.init(username); 
	    
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
    

}