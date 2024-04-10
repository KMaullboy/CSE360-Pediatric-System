package application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.collections.ObservableList;
public class PatientMessagesController {

	
	@FXML
	public ChoiceBox<String> recipients;
	@FXML
	public TextArea patientsMessage;
	@FXML
	public Button sendMessage;
	@FXML
	public CheckBox urgent;
	
	public void initialize() {
		ObservableList<String> list = FXCollections.observableArrayList();
		   list.addAll("Doctor", "Nurse");
		  //populate the Choicebox;  
		   recipients.setItems(list);
		   recipients.setValue("Doctor");

    }
	@FXML
	public void sendMessageAction() {
		
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
        String formattedDate = LocalDate.now().format(formatter); // Current date
        System.out.println(formattedDate);
        
        String id = PatientLoginController.patientUsername;
    	String filename = id + "_" + formattedDate + ".txt";
    	String filePath = "./" + filename;
    	System.out.println(filename);
    	System.out.println(recipients.getSelectionModel().getSelectedItem());
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
        	//if doctor selected
        	String firstLine = readFirstLine(file);
        	if (recipients.getSelectionModel().getSelectedItem() == "Doctor" && !"Doctor".equals(firstLine)) {
        		writer.write("Doctor\n");
        	}
        	else if (recipients.getSelectionModel().getSelectedItem() == "Nurse" && !"Nurse".equals(firstLine)) {
        		writer.write("Nurse\n");
        	}
        	if (handleUrgent()) {
        		writer.write("URGENT\n");
        	}
        	writer.write(patientsMessage.getText() + "\n");
        	
        } catch (IOException e) {
            e.printStackTrace();
	}
      
	}
	
	private String readFirstLine(File file) throws IOException {
	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        return reader.readLine(); // Reads the first line of the file
	    } catch (IOException e) {
	        throw new IOException("Error reading from file: " + file.getAbsolutePath(), e);
	    }
	}
	
	@FXML
	public Boolean handleUrgent() {
        if (urgent.isSelected()) {
            return true;
        } else {
            return false;
        }
    }
	}

