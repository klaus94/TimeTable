package Templates;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Model.TimeTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;

public class WaitingView extends FlowPane{
	@FXML
	ProgressBar pbarWaiting;
	
	
	public WaitingView(List<TimeTable> allTimeTables) throws Exception
	{
		String filePath = ".." + File.separator + "Templates" + File.separator + "Waiting.fxml";		// "/" in Unix and "\" in Windows
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
		
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        AllTimeTablesView nextView = new AllTimeTablesView(allTimeTables);
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
			}
        });
        
		Thread t = new Thread(viewBuilder);
		t.start();
        
	}
	

}

