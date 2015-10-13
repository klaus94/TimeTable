import java.io.File;

import Templates.ModulView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// javaFX - Help:
// Stage = Window  !!!
// Scene = Content !!!

public class Main extends Application
{
	
	public static void main(String[] args)
	{
		launch(args);			// set up javaFX application; call start()
	}


	@Override
	public void start(Stage primaryStage) throws Exception			
	{
//		String filePath = "Views" + File.separator + "MainPage.fxml";		// "/" in Unix and "\" in Windows
//		FlowPane root = (FlowPane)FXMLLoader.load(Main.class.getResource(filePath));
//		String filePath = "Templates" + File.separator + "ModulView.fxml";		// "/" in Unix and "\" in Windows
//		FlowPane root = (FlowPane)FXMLLoader.load(Main.class.getResource(filePath)); 
		
		
		FlowPane root = new ModulView("Ma 1", "WIL/C121");
		
		Scene scene = new Scene(root);
		
		//primaryStage.setScene(scene);
		primaryStage.show();
	}

}