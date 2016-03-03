package Templates;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


// TODO: idea for the next step: show lightly the lectures in the timetable -> user knows when he has lectures, that cannot be displaced

public class ChooseFilterView extends GridPane
{
	private final String COLOR_POSITIVE = "green";
	private final String COLOR_NEGATIVE = "red";
	
	public ChooseFilterView()
	{
		int newRow = 1;
		int newCol = 1;
		
		String filePath = "ChooseFilterView.fxml";		// "/" in Unix and "\" in Windows
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
		
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
		
		for (int i = 0; i < 35; i++)
		{
			Label l = new Label();
			l.setMaxHeight(Double.MAX_VALUE);
			l.setMaxWidth(Double.MAX_VALUE);
			l.setStyle("-fx-background-color: green");
			l.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {
	            	// Toggle the colors
	            	if (l.getStyle().equals("-fx-background-color: " + COLOR_POSITIVE))
	            		l.setStyle("-fx-background-color: " + COLOR_NEGATIVE);
	            	else if (l.getStyle().equals("-fx-background-color: " + COLOR_NEGATIVE))
	            		l.setStyle("-fx-background-color: " + COLOR_POSITIVE);
	            }
	        });
			
			newRow = i / 5 + 1;
			newCol = i % 5 + 1;
			this.add(l, newCol, newRow);		// create these Labels for all 35 inner gridcells
		}
	}
	
	
}
