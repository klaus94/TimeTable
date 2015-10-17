import Enumerations.EDay;
import Enumerations.EPeriod;
import Model.*;
import Templates.TimeTableView;
import javafx.application.Application;
import javafx.scene.Scene;
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
		
		String filePath = "Views\\MainPage.fxml"; 
		System.out.println(filePath);
		FlowPane root = (FlowPane) FXMLLoader.load(Main.class.getResource(filePath));
		
		//ModulView root = new ModulView("Ma 1", "WIL/C121");
		Course course = new ExerciseCourse("MA-1", new Time(EDay.MONTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "C212"), "Dr. Noack");
		TimeTable timetable = new TimeTable(0);
		timetable.addCourse(course);
		
		TimeTableView root = new TimeTableView(timetable);
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}

}