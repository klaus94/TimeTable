package Templates;

import java.io.IOException;

import Model.Course;
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
		String filePath = "TimeTableView.fxml";		// "/" in Unix and "\" in Windows
		
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
        	
        	this.add(newCourseView, newCourseDay, newCourseTime);		// add module to grid
        	this.setHalignment(newCourseView, HPos.CENTER);
        	this.setValignment(newCourseView, VPos.CENTER);
        }
        
	}
}