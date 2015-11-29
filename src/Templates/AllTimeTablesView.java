package Templates;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Model.TimeTable;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class AllTimeTablesView extends ScrollPane{
	private List<TimeTable> allTimeTables;
	
	@FXML
	FlowPane flowPane;
	
	public AllTimeTablesView(List<TimeTable> allTimeTables) throws Exception
	{
		String filePath = ".." + File.separator + "Templates" + File.separator + "AllTimeTablesView.fxml";		// "/" in Unix and "\" in Windows
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
	    fxmlLoader.setRoot(this);
	    fxmlLoader.setController(this);
		
	    try {
	        fxmlLoader.load();
	    } catch (IOException exception) {
	        throw new RuntimeException(exception);
	    }
	    
	    this.allTimeTables = allTimeTables;
	    
	}
	
	public void show()
	{
		Scene scene = new Scene(this);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	public Task<Object> createViews()
	{
		return new Task<Object>() {
            @Override
            protected Object call() throws Exception {
            	int i = 0;
            	int size = allTimeTables.size();
        	    
        	    for (TimeTable timetable: allTimeTables)
        	    {
        	    	i = i+1;
        	    	System.out.println("show TimeTable " + Integer.toString(i) + " von " + Integer.toString(allTimeTables.size()));
        	    	flowPane.getChildren().add(new TimeTableView(timetable));
        	    	updateProgress(i, size);
        	    }
        	    flowPane.setPrefWidth(allTimeTables.size() * 600);		// TODO: change, if size changes (generic)
            	
                return true;
            }
        };
	}
	
}
