package ViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import Enumerations.EDay;
import Enumerations.EPeriod;
import Logic.Controller;
import Logic.Filter;
import Model.Course;
import Model.ExerciseCourse;
import Model.Lecture;
import Model.Place;
import Model.Time;
import Model.TimeTable;
import Templates.AllTimeTablesView;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class MainViewModel implements Initializable
{
	private List<Course> courseList;
	
	@FXML private javafx.scene.control.Button btnClose;
	@FXML private javafx.scene.control.Button btnGenerate;
	@FXML private javafx.scene.control.Button btnAddFilter;
	@FXML private javafx.scene.control.Button btnRemoveFilter;
	@FXML private javafx.scene.control.Button btnAddCourse;
	@FXML private javafx.scene.control.Button btnRemoveCourse;
	@FXML private javafx.scene.control.Button btnLoadCourses;
	@FXML private javafx.scene.control.Button btnSaveCourses;
	@FXML private javafx.scene.control.ComboBox<String> cbModuleName;
	@FXML private javafx.scene.control.ListView<String> listFilter;
	@FXML private javafx.scene.control.ListView<String> listCourses;
	private String str;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		
		
	}
	
	private void refreshComboBox() {
		// set combobox-items
		String value = cbModuleName.getValue();

		ObservableList<String> items =cbModuleName.getItems();
		items.clear();
		
		for (Course course : courseList) {
			if (!items.contains(course.getModuleName())) {
				items.add(course.getModuleName());
			}
		}
		cbModuleName.setItems(items);
		
		if(value == null) {
			cbModuleName.setValue(items.get(0));
		} else {
			cbModuleName.setValue(value);
		}
		
		refreshListBox();
	}

	public void refreshListBox() {
		
		
		// set listbox-items
		ObservableList<String> items = listCourses.getItems();
		items.clear();
		
		if(str != null) {
			items.add(str);
		}
		
		for (Course course: courseList)
		{
			if (course.getModuleName().equals(cbModuleName.getValue())) {
				items.add(course.toString());
			}
		}
		listCourses.setItems(items);
	}
	
	@FXML
	private void btnCloseClick(ActionEvent event)
	{
		((Stage) btnClose.getScene().getWindow()).close();
	}
	
	@FXML
	private void btnGenerateClick(ActionEvent event) {
		List<TimeTable> allTimeTables = generateTestData();
		AllTimeTablesView nextView = null;
		try
		{
			nextView = new AllTimeTablesView(allTimeTables);
		} catch (Exception exception) {
	        throw new RuntimeException(exception);
	    }
		
		Scene scene = new Scene(nextView);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	@FXML

	private void btnAddFilterClick(ActionEvent event) throws IOException{
		
		FilterViewModel filterPage = new FilterViewModel();//loader.<FilterViewModel>getController();
		filterPage.initData(null);

	}

	@FXML
	private void btnRemoveFilterClick(ActionEvent event) {
		
	}

	@FXML
	private void btnAddCourseClick(ActionEvent event) throws IOException {
		System.out.println("davor");
		CourseViewModel cvm = new CourseViewModel();
		System.out.println("darin");
		cvm.initData(courseList);
		System.out.println("danach");
	}

	@FXML
	private void btnRemoveCourseClick(ActionEvent event) {
		String remove =listCourses.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Remove course: '" + remove + "'");
		alert.setContentText("Are you sure?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			// remove course
			ObservableList<String> items = listCourses.getItems();
			items.remove(remove);
			listCourses.setItems(items);
		} 
	}

	@FXML
	private void btnLoadCoursesClick(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Attention");
		alert.setHeaderText("Method not implemented yet");
		alert.showAndWait();
	}

	@FXML
	private void btnSaveCoursesClick(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Attention");
		alert.setHeaderText("Method not implemented yet");
		alert.showAndWait();
	}

	@FXML
	private void cbModuleNameChange(ActionEvent event) {
		refreshListBox();
	}
	
	
	
	public void initData(String str){
		this.str = str;
		courseList = new ArrayList<Course>();
		
		setCourseList();		// fills courseList with hardcoded data
								// later: import list or user input
		refreshComboBox();

	}
	
	private void setCourseList()
	{
		Course courseMath1 = new Lecture("BuS", new Time(EDay.DIENSTAG, 2, EPeriod.ODDWEEK), new Place("HSZ", "0004"), "Härtig");
		Course courseMath2 = new Lecture("BuS", new Time(EDay.FREITAG, 2, EPeriod.EACHWEEK), new Place("HSZ", "0003"), "Härtig");
		Course courseMath3 = new Lecture("FS", new Time(EDay.MONTAG, 3, EPeriod.EACHWEEK), new Place("HSZ", "0002"), "Hölldobler");
		Course courseMath4 = new Lecture("FS", new Time(EDay.DONNERSTAG, 4, EPeriod.ODDWEEK), new Place("HSZ", "0003"), "Hölldobler");
		Course courseMath5 = new Lecture("Mathe", new Time(EDay.DIENSTAG, 3, EPeriod.ODDWEEK), new Place("HSZ", "0002"), "Baumann");
		Course courseMath6 = new Lecture("Mathe", new Time(EDay.DONNERSTAG, 3, EPeriod.EACHWEEK), new Place("HSZ", "0003"), "Baumann");
		Course courseMath10 = new ExerciseCourse("BuS", new Time(EDay.MONTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath11 = new ExerciseCourse("BuS", new Time(EDay.MONTAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath12 = new ExerciseCourse("BuS", new Time(EDay.MONTAG, 3, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath13 = new ExerciseCourse("BuS", new Time(EDay.DIENSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath14 = new ExerciseCourse("BuS", new Time(EDay.DIENSTAG, 6, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath15 = new ExerciseCourse("BuS", new Time(EDay.MITTWOCH, 6, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath16 = new ExerciseCourse("BuS", new Time(EDay.DONNERSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath17 = new ExerciseCourse("BuS", new Time(EDay.DONNERSTAG, 1, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath18 = new ExerciseCourse("BuS", new Time(EDay.DONNERSTAG, 3, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath19 = new ExerciseCourse("BuS", new Time(EDay.FREITAG, 1, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath20 = new ExerciseCourse("BuS", new Time(EDay.FREITAG, 5, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath21 = new ExerciseCourse("FS", new Time(EDay.MONTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath22 = new ExerciseCourse("FS", new Time(EDay.MONTAG, 6, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath23 = new ExerciseCourse("FS", new Time(EDay.DIENSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath24 = new ExerciseCourse("FS", new Time(EDay.DIENSTAG, 2, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath25 = new ExerciseCourse("FS", new Time(EDay.MITTWOCH, 1, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath26 = new ExerciseCourse("FS", new Time(EDay.MITTWOCH, 6, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath27 = new ExerciseCourse("FS", new Time(EDay.DONNERSTAG, 1, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath28 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath29 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 2, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath30 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath31 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath32 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 5, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath33 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath34 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 4, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath35 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 4, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath36 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 5, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath37 = new ExerciseCourse("Mathe", new Time(EDay.DIENSTAG, 4, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath38 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath39 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 2, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath40 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 3, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath41 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 4, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath42 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 4, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath43 = new ExerciseCourse("Mathe", new Time(EDay.DONNERSTAG, 6, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath44 = new ExerciseCourse("Mathe", new Time(EDay.FREITAG, 5, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		
		courseList.add(courseMath1);
		courseList.add(courseMath2);
		courseList.add(courseMath3);
		courseList.add(courseMath4);
		courseList.add(courseMath5);
		courseList.add(courseMath6);

		courseList.add(courseMath10);
		courseList.add(courseMath11);
		courseList.add(courseMath12);
		courseList.add(courseMath13);
		courseList.add(courseMath14);
		courseList.add(courseMath15);
		courseList.add(courseMath16);
		courseList.add(courseMath17);
		courseList.add(courseMath18);
		courseList.add(courseMath19);
		courseList.add(courseMath20);
		courseList.add(courseMath21);
		courseList.add(courseMath22);
		courseList.add(courseMath23);
		courseList.add(courseMath24);
		courseList.add(courseMath25);
		courseList.add(courseMath26);
		courseList.add(courseMath27);
		courseList.add(courseMath28);
		courseList.add(courseMath29);
		courseList.add(courseMath30);
		courseList.add(courseMath31);
		courseList.add(courseMath32);
		courseList.add(courseMath33);
		courseList.add(courseMath34);
		courseList.add(courseMath35);
		courseList.add(courseMath36);
		courseList.add(courseMath37);
		courseList.add(courseMath38);
		courseList.add(courseMath39);
		courseList.add(courseMath40);
		courseList.add(courseMath41);
		courseList.add(courseMath42);
		courseList.add(courseMath43);
		courseList.add(courseMath44);
	}
	
	private List<TimeTable> generateTestData()
	{
		Controller myController = new Controller(courseList);
		myController.generateTimeTables();
		List<TimeTable> allTimeTables = myController.getAllTimeTables();

		//myController.showTimeTables(allTimeTables);
		List<EDay> days = new ArrayList<EDay>();
		days.add(EDay.MONTAG);
		days.add(EDay.DIENSTAG);
		days.add(EDay.MITTWOCH);
		days.add(EDay.DONNERSTAG);
		days.add(EDay.FREITAG);

		allTimeTables = Filter.filterByMinNumber(allTimeTables, 2);
		allTimeTables = Filter.filterByMorningtime(allTimeTables, days, 1);
		allTimeTables = Filter.filterByAfternoontime(allTimeTables, days, 5);
		allTimeTables = Filter.filterByMaxInRow(allTimeTables, 4);
		
		return allTimeTables;
	}
	
}
