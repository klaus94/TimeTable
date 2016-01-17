package Templates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.TimeTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class WaitingView extends FlowPane{
	@FXML
	ProgressBar pbarWaiting;
	
	private int timeTableCount;
	private boolean tooMuchTimeTables = false;
	
	
	public WaitingView(List<TimeTable> allTimeTables) throws Exception
	{
		List<TimeTable> timeTableShowList = new ArrayList<TimeTable>();
		String filePath = ".." + File.separator + "Templates" + File.separator + "Waiting.fxml";		// "/" in Unix and "\" in Windows
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
		
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        // show just the first 20 time-tables
        timeTableCount = allTimeTables.size();
        if (timeTableCount > 20)
        {
        	tooMuchTimeTables = true;
        	for (int i = 0; i < 20; i++)
        	{
        		timeTableShowList.add(allTimeTables.get(i));
        	}
        }
        else
        {
        	timeTableShowList = allTimeTables;
        }
        
        
        final AllTimeTablesView nextView = new AllTimeTablesView(timeTableShowList);
        Task<Object> viewBuilder = nextView.createViews();
        pbarWaiting.progressProperty().unbind();
        pbarWaiting.progressProperty().bind(viewBuilder.progressProperty());
        viewBuilder.messageProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
            }
        });
        
        // after creating the view, show it to the user
        viewBuilder.setOnSucceeded(new EventHandler() {
			@Override
			public void handle(Event event) {
				nextView.show();
				if (tooMuchTimeTables)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Es werden nur 20/" + timeTableCount + " Stundenpläne angezeigt.");					
					alert.setContentText("Tipp: Füg doch noch ein paar Filter hinzu");
					alert.showAndWait();
				}
				((Stage) pbarWaiting.getScene().getWindow()).close();
			}
        });
        
		Thread t = new Thread(viewBuilder);
		t.start();
        
	}
	

}

