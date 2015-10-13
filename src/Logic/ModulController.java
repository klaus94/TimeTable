package Logic;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModulController implements Initializable
{
	@FXML
	TextField txtTest;
	
	@FXML
	Label lblModul;
	
	@FXML
	Label lblRoom;
	
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
	}
	
	
	@FXML
	private void btnNextClick(ActionEvent event)
	{
		lblRoom.setText("test");
	}
}
