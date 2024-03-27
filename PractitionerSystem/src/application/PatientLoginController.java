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

import javax.security.auth.login.AccountException;

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
import javafx.scene.control.PasswordField;
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
	private String uname;
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
			
		error.setVisible(false);

	}
	
	/*
	 * The login() method is called when the user presses "Login" on the PatientLogin page.
	 * It will attempt to log the user into the PatientView page.
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
		
		uname = username;
		//System.out.println(username);
		//System.out.println(password);
		
		/*
		 * Path to the PatientInformation.txt file
		 * 
		 * This file is where we will find the usernames and passwords for users
		 * of the patientView.
		 */
		String path = username + "_PatientInfo.txt";
		
		File f = new File(path);
		
		try 
		{
			//If the username does not correspond to a file
			if (!f.exists()) 
			{
	            //It means username or password is incorrect
				error.setVisible(true);
				error.setText("Username/password \n is incorrect");
	            return;
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
				PatientInfo PI = new PatientInfo(path);
				
				if (PI.password.equals(password))
				{
					if (rememberMe.isSelected()) //Remember the username 
    				{
    					//System.out.println("Remember Me is selected");
    					rememberUsername(username);
    				} else //Don't remember the username
    				{
    					//System.out.println("Remember Me is not selected");
    					rememberUsername("");
    				}
					
					error.setVisible(false);
    				
    				//Log user into the PatientView page
    				switchtoPatientView(event);
    				return;
				}
				
				error.setVisible(true);
				error.setText("Username/password \n is incorrect");
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientView.fxml"));
	    root = loader.load();

	    PatientViewController patientViewController = loader.getController();
	    patientViewController.init(uname); 
	    
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
