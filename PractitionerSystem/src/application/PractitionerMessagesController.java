package application;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


public class PractitionerMessagesController {
	
	@FXML
	public ListView<String> patientList;
	@FXML
	public ListView<String> patientMessages;
	@FXML
	public Button selectButton;
	@FXML
	public TextArea patientMessage;
	@FXML
	public TextArea doctorReply;
	@FXML
	public Button replyButton;
	@FXML
	public Pane urgentContainer;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    public void initialize() {
    	System.out.println("initialize() method is running.");
    	makePatientList();
    	
        
        
    }
    public void makePatientList() {
    	patientList.setItems(findFiles());
       
    }
    
    
    public ObservableList<String> findFiles() {
        // Specify the directory you want to search in
        File directory = new File(".");
        ObservableList<String> patients =FXCollections.observableArrayList ();
        // Create a FilenameFilter
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("PatientInfo.txt");
            }
        };

        // List the files in the directory with the specified filter
        File[] files = directory.listFiles(filter);

        // Check if any files were found
        if (files != null && files.length > 0) {
            System.out.println("Found files:");
            for (File f : files) {
                System.out.println(f.getAbsolutePath());
                
                
                patients.add(readAndProcessPatientInfoFile(f));
            }
        } else {
            System.out.println("No files ending with 'PatientInfo.txt' were found.");
        }
        System.out.println(patients + " Patients ");
        return patients;
    }
    
    private String readAndProcessPatientInfoFile(File file) {
    	String patientListMember = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = "";
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (count == 0) {
                	patientListMember = patientListMember + line;
                	count++;
                }
                else if (count == 1) {
                	patientListMember = patientListMember + " " + line;
                	count++;
                }
                else if (count == 9) {
                	patientListMember = patientListMember + " ID: " + line;
                	count++;
                }
                else {
                	count++;
                }
                
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(patientListMember + " Patient list member");
        return patientListMember;
        
    }
    
       
    
    
    @FXML
    public void handleSelectButtonClick() {
    	String selectedMessage = patientMessages.getSelectionModel().getSelectedItem();
        String selectedPatient = patientList.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
        	
        	patientMessages.setItems(messagesSearch(selectedPatient));

        } 
        else {
            System.out.println("No item selected.");
        }
        if (selectedMessage != null) {            
            String filePath = "./" + selectedMessage; // "./" represents the current directory
            File file = new File(filePath);
            if (file.exists()) {
                patientMessage.setText(readFileContent(filePath));
            } else {
                System.out.println("File does not exist: " + filePath);
            }
        	
        }
        
    }
    
    
    public ObservableList<String> messagesSearch(String patient) {
        String infoString = patient;
        String directoryPath = "."; // Current directory. Adjust as needed.
        ObservableList<String> patientMes =FXCollections.observableArrayList ();
    	
        
        String id = extractId(infoString);
        System.out.println("Extracted ID " + id);
        if (id != null) {
            File[] matchingFiles = findFilesWithPattern(directoryPath, id);
            for (File file : matchingFiles) {
            	
            	try (BufferedReader reader = new BufferedReader(new FileReader(file.getName()))) {
                    String firstLine = reader.readLine(); // Read the first line of the file
                    String secondLine = reader.readLine();
                    if ("Doctor".equals(firstLine) && PractitionerLoginController.doctor) {
                    	patientMes.add(file.getName());
                    	System.out.println("Found: " + file.getName());
                    } 
                    else if ("Nurse".equals(firstLine) && !PractitionerLoginController.doctor) {
                    	patientMes.add(file.getName());
                    	System.out.println("Found: " + file.getName());
                    }
                    if ("URGENT".equals(secondLine) && urgentContainer.getChildren().size() == 0) {
                    	Label newLabel = new Label("URGENT " + (urgentContainer.getChildren().size() + 1));
                    	newLabel.setFont(new Font(18));
                    	newLabel.setTextFill(Color.RED);
                    	urgentContainer.getChildren().add(newLabel);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            	          
                
                
                
            }
        }
        return patientMes;
    }

    private static String extractId(String infoString) {
    	String[] parts = infoString.split(" ");
    	if (parts.length > 0) {
    	    String id = parts[parts.length - 1];
    	    System.out.println(id); // Prints: bm123456789
    	    return id;
    	} else {
    	    System.out.println("The string does not contain an ID.");
    	    return null;
    	}
   
    }
    
    private static File[] findFilesWithPattern(String directoryPath, String id) {
        File dir = new File(directoryPath);
        
        // Regex pattern for filename: ID followed by a date in the format _DD_MM_YYYY
        final Pattern filenamePattern = Pattern.compile(Pattern.quote(id) + "_\\d{1,2}\\_\\d{1,2}\\_\\d{2,4}");
        
        // Filename filter based on the regex pattern
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return filenamePattern.matcher(name).find();
            }
        };
        
        return dir.listFiles(filter);
    }
    public String readFileContent(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        	
            br.readLine();
        	String urgentCheck = br.readLine();
       
        	if (urgentCheck != null && !urgentCheck.equals("URGENT")) {
                content.append(urgentCheck).append("\n");
            }
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
    
    
    
    @FXML
    public void handleReply() {
    	String selectedPatient = patientList.getSelectionModel().getSelectedItem();
    	String id = extractId(selectedPatient);
    	String text = doctorReply.getText();
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
        String formattedDate = LocalDate.now().format(formatter); // Current date
        
        
    	String filename = id + "_" + formattedDate + "_PractitionerReply.txt";
    	String filePath = "./" + filename;
    	
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    
    public void goBack(ActionEvent event) throws IOException {
    	if (PractitionerLoginController.doctor) {
    		root = FXMLLoader.load(getClass().getResource("DoctorView.fxml"));
    	}
    	else {
    		root = FXMLLoader.load(getClass().getResource("NurseView.fxml"));
    	}
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
    
}




