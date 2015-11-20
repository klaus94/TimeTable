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
	@FXML private javafx.scene.control.ComboBox<String> cbYear;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		
	}
	
	public void initData(Set<String> set) {
		ObservableList<String> items1 = cbStudyType.getItems();
		items1.clear();
		items1.add("Bachelor Informatik");
		items1.add("Master Informatik");
		items1.add("Diplom Informatik, 2010");
		items1.add("Diplom Informatik, 2004");
		ObservableList<String> items2 = cbSemesterType.getItems();
		items2.clear();
		items2.add("WS");
		items2.add("SS");
		ObservableList<String> items3 = cbYear.getItems();
		items3.clear();
		items3.add("2015 (bzw. 2014/15 im WS)");
		items3.add("2016 (bzw. 2015/16 im WS)");
	}
	
	@FXML private void btnCloseClick(ActionEvent event) {
		((Stage) btnClose.getScene().getWindow()).close();
	}
	
	@FXML private void btnLoadClick(ActionEvent event) throws MalformedURLException {
		String semtype = "";
		switch (cbSemesterType.getValue()) {
			case "WS":
				semtype = "w";
				break;
			case "SS":
				semtype = "ss";
				break;
			default:
				semtype = "null";
				break;
		}
		String studytype = "";
		switch (cbStudyType.getValue()) {
			case "Bachelor Informatik":
				studytype = "inf_bach";
				break;
			case "Master Informatik":
				studytype = "inf_mast";
				break;
			case "Diplom Informatik, 2010":
				studytype = "inf_dipl_2010";
				break;
			case "Diplom Informatik, 2004":
				studytype = "inf_dipl_2004";
				break;
			default:
				studytype = "null";
				break;
		}
		String year = cbYear.getValue().substring(3, 4);
		
		String urlspec = "http://web.inf.tu-dresden.de/Fak/" + semtype + "/" + year + "/studiengang/studiengang_" + studytype + ".html"; 
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
		
		for (String s : content) {
			System.out.println(s);
		}
	}

}
