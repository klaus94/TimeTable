import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.File;

import ViewModel.FilterViewModel;
import ViewModel.MainViewModel;

//import Logic.ViewMain;

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

		System.out.println("start");
		String filePath = ".." + File.separator + "Views" + File.separator + "MainPage.fxml"; 
		FXMLLoader loader = new FXMLLoader(FilterViewModel.class.getResource(filePath));
		FlowPane root;
		try {
			root = (FlowPane) loader.load();
			MainViewModel mainModel = loader.getController();
			mainModel.initData();

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		 
	}

}