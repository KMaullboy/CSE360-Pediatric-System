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

public class NurseViewController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	BufferedReader br;
	BufferedWriter bw;
	
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
