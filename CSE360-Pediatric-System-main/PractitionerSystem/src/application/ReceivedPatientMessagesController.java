package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class ReceivedPatientMessagesController {
	
	@FXML
	public ListView<String> messagesList;
	@FXML
	public TextArea receivedMessage;
	
	
	
	public void initialize() {
	messagesList.setItems(findFiles());
	}
	public ObservableList<String> findFiles() {
        // Specify the directory you want to search in
        File directory = new File(".");
        final String prefix = PatientLoginController.patientUsername;
        final String suffix = "PractitionerReply.txt";
        ObservableList<String> messages =FXCollections.observableArrayList ();
        // Create a FilenameFilter
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
            	return (name.startsWith(prefix) && name.endsWith(suffix));
            }
        };

        // List the files in the directory with the specified filter
        File[] files = directory.listFiles(filter);

        // Check if any files were found
        if (files != null && files.length > 0) { 
            System.out.println("Found files:");
            for (File f : files) {
                System.out.println(f.getName());
                messages.add(f.getName());
            }
        } else {
            System.out.println("No files ending with 'PractitionerReply.txt' were found.");
        }
        System.out.println(messages + " Messages ");
        return messages;
    }
	
	@FXML
    public void handleSelectButtonClick() {
    	String selectedMessage = messagesList.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {            
            String filePath = "./" + selectedMessage; // "./" represents the current directory
            File file = new File(filePath);
            if (file.exists()) {
                receivedMessage.setText(readFileContent(filePath));
            } else {
                System.out.println("File does not exist: " + filePath);
            }
        	
        }
        
    }
    
    
   

    public String readFileContent(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        	
        	String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            } 
            System.out.println(content.toString());
        	
            
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading file: " + e.getMessage();
        }
        return content.toString();
    }
 
	
}


