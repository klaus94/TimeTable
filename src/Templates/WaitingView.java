package Templates;

import java.io.File;
import java.io.IOException;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class WaitingView extends FlowPane{
	@FXML
	ProgressBar pbarWaiting;
	
	private int managedTimeTables = 0;
	private int allTimeTables = 0;
	
	public WaitingView() throws Exception
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
        
        Task task = taskCreator();
        pbarWaiting.progressProperty().unbind();
        pbarWaiting.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
        
	}
	
	public void updateProgress(int managed, int all)
	{
		managedTimeTables = managed;
		allTimeTables = all;
	}
	
	
	//TEST
	//Create a New Task
	private Task taskCreator(){
		return new Task() {

			@Override
			protected Object call() throws Exception {
				//TODO: get controlled by an other class (generate-method)
				
				for(int i=0; i<100;i++){
					Thread.sleep(50);
					System.out.println("updated");
					updateProgress(i+1, 100);
				}
				return true;
			}
		};
	}
	//END TEST

}

