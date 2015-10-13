package Logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ViewController {
	
	@FXML private javafx.scene.control.Button btnNext;
	
	@FXML private void btnNextClick(ActionEvent event) {
		((Stage) btnNext.getScene().getWindow()).close();
	}
}
