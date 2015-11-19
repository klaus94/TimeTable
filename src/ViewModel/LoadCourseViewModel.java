package ViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import Model.Course;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class LoadCourseViewModel implements Initializable {
	
	private ObservableList<String> modulItems;
	private List<Course> courseList;
	
	@FXML private javafx.scene.control.Button btnClose;
	@FXML private javafx.scene.control.Button btnLoad;
	@FXML private javafx.scene.control.ComboBox<String> cbSemesterType;
	@FXML private javafx.scene.control.ComboBox<String> cbStudyType;
	@FXML private javafx.scene.control.TextField txtYear;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		
	}
	
	public void initData(Set<String> set) {
		ObservableList<String> items1 = cbSemesterType.getItems();
		items1.clear();
		items1.add("WS");
		items1.add("SS");
		ObservableList<String> items2 = cbStudyType.getItems();
		items2.clear();
		items2.add("Bachelor Informatik");
		items2.add("Master Informatik");
		items2.add("Diplom Informatik, 2010");
		items2.add("Diplom Informatik, 2004");
	}
	
	@FXML private void btnCloseClick(ActionEvent event) {
		((Stage) btnClose.getScene().getWindow()).close();
	}
	
	@FXML private void btnLoadClick(ActionEvent event) throws MalformedURLException {
		String semtype = "";
		switch (cbSemesterType.getValue()) {
			case "WS": semtype = "w";
			case "SS": semtype = "ss";
		}
		String studytype = "";
		switch (cbStudyType.getValue()) {
			case "Bachelor Informatik": studytype = "inf_bach";
			case "Master Informatik": studytype = "inf_mast";
			case "Diplom Informatik, 2010": studytype = "inf_dipl_2010";
			case "Diplom Informatik, 2004": studytype = "inf_dipl_2004";
		}
		
		String urlspec = "http://web.inf.tu-dresden.de/Fak/" + semtype + "/16/studiengang/studiengang_" + studytype + ".html"; 
		ArrayList<String> content = new ArrayList<String>( );
		try {
			URL url = new URL( urlspec );
			Reader reader = new InputStreamReader( url.openStream( ) );
			BufferedReader breader = new BufferedReader( reader );
			for ( String s; ( s = breader.readLine( ) ) != null; ) {
				content.add( s );
			}
			breader.close();
		}
		catch ( MalformedURLException e ) {
			System.out.println( "MalformedURLException: " + e );
		}
		catch ( IOException e ) {
			System.out.println( "IOException: " + e );
		}
	}

}
