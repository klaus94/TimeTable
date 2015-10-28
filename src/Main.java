import javafx.application.Application;
import javafx.stage.Stage;
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

		MainViewModel mainModel = new MainViewModel();
		mainModel.initData("moin");

	}

}