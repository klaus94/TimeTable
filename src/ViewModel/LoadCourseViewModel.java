package ViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import Model.Course;
import Model.ExerciseCourse;
import Model.Lecture;
import Model.Place;
import Model.Time;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import org.jsoup.Jsoup;

import Enumerations.EDay;
import Enumerations.EPeriod;

public class LoadCourseViewModel implements Initializable {
	
	private Map<String, List<Course>> fullCourseMap = new HashMap<String, List<Course>>();
	private Map<String, List<Course>> newCourseMap = new HashMap<String, List<Course>>();
	
	@FXML private javafx.scene.control.Button btnClose;
	@FXML private javafx.scene.control.Button btnLoad;
	@FXML private javafx.scene.control.Button btnSave;
	@FXML private javafx.scene.control.ComboBox<String> cbSemesterType;
	@FXML private javafx.scene.control.ComboBox<String> cbStudyType;
	@FXML private javafx.scene.control.ComboBox<String> cbYear;
	@FXML private javafx.scene.control.ListView<CheckBox> lvModules;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		
	}
	
	public void initData(Set<String> set) {
		ObservableList<String> items1 = cbStudyType.getItems();
		items1.clear();
		items1.add( "Bachelor Informatik" );
		items1.add( "Master Informatik" );
		items1.add( "Diplom Informatik, 2010" );
		items1.add( "Diplom Informatik, 2004" );
		ObservableList<String> items2 = cbSemesterType.getItems();
		items2.clear();
		items2.add( "WS" );
		items2.add( "SS" );
		ObservableList<String> items3 = cbYear.getItems();
		items3.clear();
		items3.add( "2015 (bzw. 2014/15 im WS)" );
		items3.add( "2016 (bzw. 2015/16 im WS)" );
	}
	
	@FXML private void btnCloseClick(ActionEvent event) {
		( (Stage) btnClose.getScene().getWindow() ).close();
	}
	
	@FXML private void btnLoadClick(ActionEvent event) throws MalformedURLException {
		lvModules.getItems().clear();
		
		String semtype = "";
		switch ( cbSemesterType.getValue() ) {
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
		switch ( cbStudyType.getValue() ) {
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
			Reader reader = new InputStreamReader( url.openStream() );
			BufferedReader breader = new BufferedReader(reader);
			for ( String s; (s = breader.readLine()) != null; ) {
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
		
		ArrayList<Course> newCourseList = new ArrayList<Course>();
		String semstr = "";
		String modulenamestr = "";
		String instructorstr = "";
		String kindofcoursestr = "";
		String daystr = "";
		String timestr = "";
		String periodstr = "";
		String placestr = "";
		int column = 0;
		int columnsoftable = 0;
		int coursecounter = 0;
		boolean insubjectcode = false;
		
		for ( String s : content ) {
			if ( s.contains( "<h1>" ) ) {
				if ( !Character.isDigit( Jsoup.parse( s ).text().charAt(0) ) ) {
					semstr = "?";
				}
				semstr = Jsoup.parse( s ).text();
			}
			if ( s.contains( "<table>" ) ) {
				columnsoftable = 0;
				continue;
			}
			if ( s.contains( "<th" ) ) {
				columnsoftable++;
				continue;
			}
			if ( s.contains( "<tr>" ) ) {
				insubjectcode = true;
				column = 0;
				continue;
			}
			if ( insubjectcode && (column+1 <= columnsoftable) ) {
				column++;
				if ( columnsoftable == 11 ) {
					switch ( column ) {
					case 1:
						modulenamestr = Jsoup.parse( s ).text();
						break;
					case 5:
						instructorstr = Jsoup.parse( s ).text();
						break;
					case 7:
						kindofcoursestr = Jsoup.parse( s ).text();
						break;
					case 8:
						daystr = Jsoup.parse( s ).text();
						break;
					case 9:
						timestr = Jsoup.parse( s ).text();
						break;
					case 10:
						placestr = Jsoup.parse( s ).text();
						break;
					case 11:
						periodstr = Jsoup.parse( s ).text();
						break;
					default:
						break;
					}
				}
				if ( columnsoftable == 12 ) {
					switch ( column ) {
					case 1:
						modulenamestr = Jsoup.parse( s ).text();
						break;
					case 4:
						instructorstr = Jsoup.parse( s ).text();
						break;
					case 8:
						kindofcoursestr = Jsoup.parse( s ).text();
						break;
					case 9:
						daystr = Jsoup.parse( s ).text();
						break;
					case 10:
						timestr = Jsoup.parse( s ).text();
						break;
					case 11:
						placestr = Jsoup.parse( s ).text();
						break;
					case 12:
						periodstr = Jsoup.parse( s ).text();
						break;
					default:
						break;
					}
				}
				if ( columnsoftable == 10 ) {
					switch ( column ) {
					case 1:
						modulenamestr = Jsoup.parse( s ).text();
						break;
					case 4:
						instructorstr = Jsoup.parse( s ).text();
						break;
					case 6:
						kindofcoursestr = Jsoup.parse( s ).text();
						break;
					case 7:
						daystr = Jsoup.parse( s ).text();
						break;
					case 8:
						timestr = Jsoup.parse( s ).text();
						break;
					case 9:
						placestr = Jsoup.parse( s ).text();
						break;
					case 10:
						periodstr = Jsoup.parse( s ).text();
						break;
					default:
						break;
					}
				}
				if ( column == columnsoftable ) {
					coursecounter = ( kindofcoursestr.length()+1 ) / 2;
					for ( int i = 1; i <= coursecounter; i++ ) {
						Course newCourse;
						switch ( String.valueOf(kindofcoursestr.charAt(2*(i-1))) ) {
						case "V":
							newCourse = new Lecture(modulenamestr, new Time(EDay.getDay(getDayEntry(daystr, i)), Integer.parseInt(getTimeEntry(timestr, i)), EPeriod.getPeriod(getPeriodEntry(periodstr, i))), new Place(getPlaceEntry(placestr, i), ""), instructorstr);
							newCourseList.add(newCourse);
							System.out.println("added: " + newCourse);
							break;
						case "U":
							newCourse = new ExerciseCourse(modulenamestr, new Time(EDay.getDay(getDayEntry(daystr, i)), Integer.parseInt(getTimeEntry(timestr, i)), EPeriod.getPeriod(getPeriodEntry(periodstr, i))), new Place(getPlaceEntry(placestr, i), ""), "Tutor");
							newCourseList.add(newCourse);
							System.out.println("added: " + newCourse);
							break;
						default:
							newCourse = new ExerciseCourse(modulenamestr, new Time(EDay.getDay(getDayEntry(daystr, i)), Integer.parseInt(getTimeEntry(timestr, i)), EPeriod.getPeriod(getPeriodEntry(periodstr, i))), new Place(getPlaceEntry(placestr, i), ""), "Tutor");
							newCourseList.add(newCourse);
							System.out.println("added: " + newCourse);
							break;
						}
					}
					System.out.println("Added whole Module: " + modulenamestr);
					fullCourseMap.put(modulenamestr, newCourseList);
					modulenamestr = "";
					instructorstr = "";
					kindofcoursestr = "";
					daystr = "";
					timestr = "";
					periodstr = "";
					placestr = "";
					newCourseList = new ArrayList<Course>();
					insubjectcode = false;
				}
				continue;
			}	
		}
		
		for ( String mstr : fullCourseMap.keySet() ) {
			lvModules.getItems().add( new CheckBox(mstr) );
		}
	}
	
	@FXML private void btnSaveClick(ActionEvent event) {		
		for ( CheckBox cb : lvModules.getItems() ) {
			if ( cb.isSelected() ) {
				newCourseMap.put( cb.getText(), fullCourseMap.get( cb.getText() ) );
			}
		}
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Laden erfolgreich");
		alert.setHeaderText("Die Kurse wurden erfolgreich geladen.");
		alert.setContentText("Klicke OK um zum Hauptfenster zurückzukehren.");
		alert.showAndWait();
		
		( (Stage) btnClose.getScene().getWindow() ).close();
	}

	private String getDayEntry(String str, int pos) {
		String[] strfield = str.split( "\\s+" );
		if ( strfield[ pos-1 ].equals("") || strfield[ pos-1 ].equals("ZVZ") ) {
			return "?";
		}
		return strfield[ pos-1 ];
	}
	
	private String getTimeEntry(String str, int pos) {
		//TODO: durch Komma getrennte doppelte Zeileneinträge auseinandernehmen
		String[] strfield = str.split( "\\s+" );
		for ( int k = 0; k <= strfield.length-2; k++ ) {
			if ( strfield[k].endsWith(",") ) {
				String[] tempstrfield = new String[ strfield.length-1 ];
				for ( int i = 0; i <= k-1; i++) {
					tempstrfield[i] = strfield[i];
				}
				tempstrfield[k] = strfield[k] + " " + strfield[k+1];
				for ( int i = k+1; i <= strfield.length-2; i++) {
					tempstrfield[i] = strfield[i+1];
				}
				strfield = tempstrfield;
			}
		}
		if ( strfield[ pos-1 ].equals("") || strfield[ pos-1 ].equals("ZVZ") ) {
			return "0";
		}
		return strfield[ pos-1 ].substring(0, 1);
	}
	
	private String getPeriodEntry(String str, int pos) {
		String[] strfield = str.split( "\\s+" );
		for ( int k = 0; k <= strfield.length-2; k++ ) {
			if ( strfield[k].startsWith("1") || strfield[k].startsWith("2") ) {
				String[] tempstrfield = new String[ strfield.length-1 ];
				for ( int i = 0; i <= k-1; i++) {
					tempstrfield[i] = strfield[i];
				}
				tempstrfield[k] = strfield[k] + " " + strfield[k+1];
				for ( int i = k+1; i <= strfield.length-2; i++) {
					tempstrfield[i] = strfield[i+1];
				}
				strfield = tempstrfield;
			}
		}
		if ( strfield[ pos-1 ].equals("") ) {
			return "?";
		}
		return strfield[ pos-1 ];
	}
	
	private String getPlaceEntry(String str, int pos) {
		String[] strfield = str.split( "\\s+" );
		if (strfield[ pos-1 ].equals("") || strfield[ pos-1 ].equals("AVO")) {
			return "?";
		}
		return strfield[ pos-1 ];
	}
	
	private int countChar(String str, char c) {
		int counter = 0;
		for (int i = 0; i <= str.length(); i++) {
			if ( str.charAt(i) == c ) counter++;
		}
		return counter;
	}
	
	public Map<String, List<Course>> getNewCourseMap() {
		return newCourseMap;
	}

}
