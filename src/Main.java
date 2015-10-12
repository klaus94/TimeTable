
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
		 GridPane root = (GridPane)FXMLLoader.load(Main.class.getResource("FilterPage.fxml"));
		 
		 
		 Scene scene = new Scene(root);
		 primaryStage.setScene(scene);
		 primaryStage.show();
	}

}