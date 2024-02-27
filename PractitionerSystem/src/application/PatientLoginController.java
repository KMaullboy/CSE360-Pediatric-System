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
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PatientLoginController implements Initializable
{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	BufferedReader br;
	BufferedWriter bw;
	
	/*
	 * Using @FXML before declaring private fields allows them to be recognized
	 * by SceneBuilder if you choose this controller class as the controller
	 * for your fxml file
	 */
	
	@FXML
	private TextField usernameField, passwordField;
	@FXML
	private Button login;
	@FXML
	private CheckBox rememberMe;
	
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
		
		String path = "RememberedUsername.txt"; //The path to RememberedUsername.txt
		
		File f = new File(path); //Create a file 
		
		//Try to open the file 
		try {
			br = new BufferedReader(new FileReader("RememberedUsername.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Read the first line (It should be either the username or blank
		
		try {
			line = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//Once read, set the usernameField to the line that was just read
			usernameField.setText(line);

	}
	
	/*
	 * The login() method is called when the user presses "Login" on the PatientLogin page.
	 * It will attempt to log the user into the PatientView page.
	 */
	
	public void login(ActionEvent event) throws IOException 
    {
		//Get the username and password entered by the user
		username = usernameField.getText();
		password = passwordField.getText();
		
		System.out.println(username);
		System.out.println(password);
		
		/*
		 * Path to the PatientInformation.txt file
		 * 
		 * This file is where we will find the usernames and passwords for users
		 * of the patientView.
		 */
		String path = "PatientInformation.txt"; 
		
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
				 * Format: Username|Password|Addressline1|AddressLine2|City|State|Zip|BirthMonth|BirthYear
				 */
				br = new BufferedReader(new FileReader("PatientInformation.txt"));
	    		
	    		String line;
	    		
	    		while ((line = br.readLine()) != null) 
	    		{
	    			//Parse the line read and split it into sections separated by "|"
	    			String[] info = line.split("\\|");
	    			
	    			if (info.length == 1) 
	    			{
	    				break;
	    			}
	    			
	    			for (int i = 0; i < info.length; i++) {
	    				System.out.println(info[i]);
	    			}
	    			
	    			//If the username and password matches any in the file
	    			if (info[0].equals(username) && info[1].equals(password)) 
	    			{
	    				System.out.println("Login Authenticated");
	    				
	    				if (rememberMe.isSelected()) //Remember the username 
	    				{
	    					System.out.println("Remember Me is selected");
	    					rememberUsername(username);
	    				} else //Don't remember the username
	    				{
	    					System.out.println("Remember Me is not selected");
	    					rememberUsername("");
	    				}
	    				
	    				//Log user into the PatientView page
	    				switchtoPatientView(event);
	    				return;
	    			}
	    		}
	    		throw new Exception("Username or Password is incorrect");
	    		
			}
		}
		catch (Exception e)
		{
			System.out.println("Login Failed");
			return;
		}
    }

	/*
	 * The rememberUsername() is called after the user successfully logs in and has the 
	 * RememberMe checkbox selected.
	 * 
	 * It will write the username used to successfully log into the PatientView
	 * to RememberedUsername.txt if the RememberMe checkbox was selected. 
	 */
	
	public void rememberUsername(String username) throws IOException 
	{
		bw = new BufferedWriter(new FileWriter("RememberedUsername.txt"));
		
    	
		bw.write(username);
		
		bw.close();
	}
	
	/*
	 * switchtoCreateAccount() is called when the user clicks "Create Account" in the 
	 * PatientLogin page. It switches the displayed page to CreateAccount.fxml
	 */
	
	public void switchtoCreateAccount(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * switchtoPractitionerLogin() is called when the user clicks "Practitioner Login" in the 
	 * PatientLogin page. It switches the displayed page to PractitionerLogin.fxml
	 */
	
	public void switchtoPractitionerLogin(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("PractitionerLogin.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * switchtoPatientView() is called when the user successfully logs in from the 
	 * PatientLogin page. It switches the displayed page to PatientView.fxml
	 */
	
	public void switchtoPatientView(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("PatientView.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
