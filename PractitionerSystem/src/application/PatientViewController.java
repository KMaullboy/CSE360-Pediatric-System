package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PatientViewController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	BufferedReader br;
	BufferedWriter bw;
	
	
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

}
