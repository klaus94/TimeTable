package Templates;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class ModulView extends FlowPane
{
	@FXML
	Label lblModul;
	
	@FXML
	Label lblRoom;
	
	public ModulView(String modulName, String room) throws Exception
	{
		String filePath = "Views" + File.separator + "ModulViewTemplate.fxml";		// "/" in Unix and "\" in Windows
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
		
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
		lblModul.setText(modulName);
		lblRoom.setText(room);
	}
}
