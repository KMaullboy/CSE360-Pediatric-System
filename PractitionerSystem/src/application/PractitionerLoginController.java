package application;

import java.io.BufferedReader;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class PractitionerLoginController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	public static Boolean doctor;
	BufferedReader br;
	BufferedWriter bw;
	
	/*
	 * Using @FXML before declaring private fields allows them to be recognized
	 * by SceneBuilder if you choose this controller class as the controller
	 * for your fxml file
	 */
	
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button login;
	@FXML
	private CheckBox rememberMe;
	@FXML
	private Label error;
	
	String username, password;
	
	/*
	 * The initialize() method runs right before the page that this class controls
	 * is opened.
	 * 
	 * This initialize() method will populate the usernameField with the remembered
	 * username if a username was saved last time a user successfully logged in.
	 */
	
	public void initialize(URL url, ResourceBundle rb) 
	{
		String line = "";
		
		String path = "RememberedUsername.txt";
		
		File f = new File(path);
		
		try {
			br = new BufferedReader(new FileReader("RememberedUsername.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			line = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			usernameField.setText(line);
			
			error.setVisible(false);

	}
	
	/*
	 * The login() method is called when the user presses "Login" on the Practitioner Login page.
	 * It will attempt to log the user into the NurseView or DoctorView page.
	 */
	
	public void login(ActionEvent event) throws IOException 
    {
		//Get the username and password entered by the user
		
		try {
			username = usernameField.getText().replaceAll("\n", "");
			password = passwordField.getText().replaceAll("\n", "");
		} catch (Exception e) 
		{
			error.setVisible(true);
			error.setText("Enter username \n and password");
			return;
		}
		
		//System.out.println(username);
		//System.out.println(password);
		
		/*
		 * Path to the PractitionerInformation.txt file
		 * 
		 * This file is where we will find the usernames and passwords for users
		 * of the NurseView and DoctorView.
		 */
		
		String path = "PractitionerInformation.txt";
		
		File f = new File(path);
		
		try 
		{
			//Creates this file if it does not exist already
			if (!f.exists()) 
			{
	            // Create the directory along with any necessary parent directories
	            boolean created = f.createNewFile();
			}
			else 
			{
				/*
				 * Read every line of the file and check if the username and password
				 * entered by the user matches any of the usernames and passwords
				 * stored in the file.
				 * 
				 * Format: Username|Password|<Nurse or Doctor>
				 */
				
				br = new BufferedReader(new FileReader("PractitionerInformation.txt"));
	    		
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
	    			
	    			//If the username and password matches any in the file
	    			if (info[0].equals(username) && info[1].equals(password)) 
	    			{
	    				//System.out.println("Login Authenticated");
	    				
	    				error.setVisible(false);
	    				
	    				if (rememberMe.isSelected()) //Remember the username 
	    				{
	    					//System.out.println("Remember Me is selected");
	    					rememberUsername(username);
	    				} else //Don't remember the username 
	    				{
	    					//System.out.println("Remember Me is not selected");
	    					rememberUsername("");
	    				}
	    				
	    				
	    				if (info[2].equals("Nurse")) //If the username and password has Nurse Privileges
	    				{
	    					switchtoNurseView(event); //Log user into the NurseView page
	    					doctor = false;
	    				} else //If the username and password has Doctor Privileges
	    				{
	    					switchtoDoctorView(event); //Log user into the DoctorView page
	    					doctor = true;
	    				}
	    				return;
	    			}
	    			
	    			
	    		}
	    		error.setVisible(true);
    			error.setText("Username/Password \n is incorrect");
    			return;
	    		
			}
		}
		catch (Exception e)
		{
			//System.out.println("Login Failed");
			return;
		}
    }
	
	/*
	 * The rememberUsername() is called after the user successfully logs in and has the 
	 * RememberMe checkbox selected.
	 * 
	 * It will write the username used to successfully log into the NurseView or DoctorView
	 * to RememberedUsername.txt if the RememberMe checkbox was selected. 
	 */
	
	public void rememberUsername(String username) throws IOException 
	{
		bw = new BufferedWriter(new FileWriter("RememberedUsername.txt"));
		
		bw.write(username);
		
		bw.close();
	}
	
	/*
	 * switchtoNurseView() is called when the user successfully logs in from the 
	 * PractitionerLogin page with nurse privileges. It switches the displayed page to 
	 * NurseView.fxml
	 */
	
	public void switchtoNurseView(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("NurseView.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * switchtoDoctorView() is called when the user successfully logs in from the 
	 * PractitionerLogin page with doctor privileges. It switches the displayed page to 
	 * DoctorView.fxml
	 */
	
	public void switchtoDoctorView(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("DoctorView.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * switchtoPatientLogin() is called when the user clicks "Patient Login" in the 
	 * PractitionerLogin page. It switches the displayed page to PatientLogin.fxml
	 */
	
	public void switchtoPatientLogin(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("PatientLogin.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	
}
