package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

public class ViewSummaryPopupController {
	/*
	 * Using @FXML before declaring private fields allows them to be recognized
	 * by SceneBuilder if you choose this controller class as the controller
	 * for your fxml file
	 */
	
    @FXML
	private Label doctor;
	@FXML
	private Label nurse;
    @FXML
    private Label date;
    @FXML
    private Label height;
    @FXML
    private Label weight;
    @FXML
    private Label temp;
    @FXML
    private Label bloodPressure;
    @FXML
    private TextArea findings;
    @FXML
    private TextArea notes;
    @FXML
    private TextArea meds;
	
    public void init(String dateInfo, ViewInfo viewInfo) throws IOException {
		date.setText(dateInfo);
		
		if(viewInfo.height != null) 
			height.setText(viewInfo.height);
		if(viewInfo.weight != null)
			weight.setText(viewInfo.weight);
		if(viewInfo.bodyTemperature != null)
			temp.setText(viewInfo.bodyTemperature);
		if(viewInfo.bloodPressure != null)
			bloodPressure.setText(viewInfo.bloodPressure);
		
		findings.setFocusTraversable(false);
		notes.setFocusTraversable(false);
		meds.setFocusTraversable(false);
		
		if(viewInfo.previousHealthIssues != null) 
			findings.setText(viewInfo.previousHealthIssues);
		if(viewInfo.notes != null)
			notes.setText(viewInfo.notes);
		if(viewInfo.previousPrescribedMeds != null)
			meds.setText(viewInfo.previousPrescribedMeds);
	}
}
