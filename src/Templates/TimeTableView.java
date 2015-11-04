package Templates;

import java.io.File;
import java.io.IOException;

import Model.Course;
import Model.ExerciseCourse;
import Model.TimeTable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class TimeTableView extends GridPane
{
	
	@FXML
	Label lblRoom;
	
	public TimeTableView(TimeTable timeTable) throws Exception
	{
		String filePath = ".." + File.separator + "Views" + File.separator + "TimeTableView.fxml";		// "/" in Unix and "\" in Windows
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
		
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        int newCourseDay = -1;
        int newCourseTime = -1;
        
        
        for (Course course: timeTable.getCourseList())
        {
        	FlowPane newCourseView = new ModulView(course.getModuleName(), course.getPlace().getRoom());
        	newCourseTime = course.getTime().getTime();					// get row-index
        	newCourseDay = course.getTime().getDay().toInt();			// get column-index
        	
        	
        	if (course instanceof ExerciseCourse)
        	{
        		newCourseView.getStyleClass().add("exercise");
        	}
        	else
        	{
        		newCourseView.getStyleClass().add("lecture");
        	}
        	
        	this.add(newCourseView, newCourseDay, newCourseTime);		// add module to grid
        	GridPane.setHalignment(newCourseView, HPos.CENTER);
        	GridPane.setValignment(newCourseView, VPos.CENTER);
        }
        
        this.setStyle("-fx-border-color: orange; -fx-border-radius: 12pt; -fx-border-width: 3pt;");
        
	}
}
