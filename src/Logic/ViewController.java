package Logic;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Label;
	Label lblModul;
	
	@FXML
	Label lblRoom;
	
	
	}
	
	@FXML
	private void onMouseClicked(ActionEvent event)
	{
		lblRoom.setText("Wil/C212");
		System.out.println("event ausgelößt");
	}
	
	@FXML
	private void btnNextClick(ActionEvent event)
	{
		performAction();
		
		lblRoom.setText("test");

public class ViewController {
	
	@FXML private javafx.scene.control.Button btnNext;
	
	@FXML private void btnNextClick(ActionEvent event) {
		((Stage) btnNext.getScene().getWindow()).close();
	}
}
