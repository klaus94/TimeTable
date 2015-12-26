package ViewModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import Enumerations.EDay;
import Enumerations.EFilter;
import Enumerations.EPeriod;
import Logic.Controller;
import Logic.Filter;
import Model.Course;
import Model.ExerciseCourse;
import Model.FilterObject;
import Model.Lecture;
import Model.Place;
import Model.Time;
import Model.TimeTable;
import Templates.WaitingView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class MainViewModel implements Initializable
{
	private Map<String, List<Course>> courseMap; //maps modulnames to available courses
	private List<FilterObject> filterList;
	
	@FXML private javafx.scene.control.Button btnClose;
	@FXML private javafx.scene.control.Button btnGenerate;
	@FXML private javafx.scene.control.Button btnAddFilter;
	@FXML private javafx.scene.control.Button btnRemoveFilter;
	@FXML private javafx.scene.control.Button btnAddCourse;
	@FXML private javafx.scene.control.Button btnRemoveCourse;
	@FXML private javafx.scene.control.Button btnLoadCourses;
	@FXML private javafx.scene.control.Button btnSaveCourses;
	@FXML private javafx.scene.control.ComboBox<String> cbModuleName;
	@FXML private javafx.scene.control.ListView<FilterObject> listFilter;
	@FXML private javafx.scene.control.ListView<Course> listCourses;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{	
		// set handler for course-list -> click on a course
		listCourses.setOnMouseClicked(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	            // TODO: Test, if clicked mouse-button was PRIMARY (left mouse key)
            	if(event.getClickCount() == 2){
            		System.out.println("Double clicked " + listCourses.getSelectionModel().getSelectedItem());
            		
            		String filePath = ".." + File.separator + "Views" + File.separator + "CoursePage.fxml";
            		FXMLLoader loader = new FXMLLoader(FilterViewModel.class.getResource(filePath));
            		try {
            			GridPane root = (GridPane) loader.load();
	            		CourseViewModel courseModel = loader.getController();
	        			System.out.println("hallo");
	        			courseModel.initData(courseMap.keySet(), listCourses.getSelectionModel().getSelectedItem());
	        			courseModel.setCourse(listCourses.getSelectionModel().getSelectedItem());
	
	        			Scene scene = new Scene(root);
	        			Stage stage = new Stage();
	        			stage.setScene(scene);
	        			stage.showAndWait();
	        			addCourse(courseModel.getNewCourse());		//add new course to map
            		}
            		catch(Exception e)
            		{
            			System.out.println("Kurs konnte nicht geladen werden");
            		}
            	}
	        }
	    });
	}
	
	private void refreshcbModuleName() {
		// set combobox-items
		String value = cbModuleName.getValue();

		ObservableList<String> items =cbModuleName.getItems();
		items.clear();
		
		ArrayList<String> sortedList = new ArrayList<String>( courseMap.keySet() );
		Collections.sort( sortedList );
		for (String module : sortedList) {
			items.add(module);		
		}
		cbModuleName.setItems(items);
		
		if(value == null) {
			cbModuleName.setValue(items.get(0));
		} else {
			cbModuleName.setValue(value);
		}
		
		refreshlbCourses();
	}

	public void refreshlbCourses() {
		
		// set listbox-items
		ObservableList<Course> items = listCourses.getItems();
		items.clear();
		
		List<Course> courseList = courseMap.get(cbModuleName.getValue());
		try {
			for (Course course: courseList)
			{
				items.add(course);
			}
			listCourses.setItems(items);
		} catch (Exception e ){ 
//			System.out.println(e.getMessage());
		}
	}
	
	@FXML
	private void btnCloseClick(ActionEvent event)
	{
		((Stage) btnClose.getScene().getWindow()).close();
	}
	
	@FXML
	private void btnGenerateClick(ActionEvent event) {
		List<TimeTable> allTimeTables = generateTestData();
		WaitingView waitView = null;
		try {
			waitView = new WaitingView(allTimeTables);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(waitView);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	@FXML

	private void btnAddFilterClick(ActionEvent event) throws IOException{
		//TODO throw things in FilterViewModel
		String filePath = ".." + File.separator + "Views" + File.separator + "FilterPage.fxml"; 
		FXMLLoader loader = new FXMLLoader(FilterViewModel.class.getResource(filePath));
		try {
			FlowPane root = (FlowPane) loader.load();
			FilterViewModel filterModel = loader.getController();
			filterModel.initData(null, courseMap);

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.showAndWait();
			if (filterModel.getFilter() != null)
			{
				filterList.add(filterModel.getFilter());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		refreshlbFilter();
	}

	private void refreshlbFilter() {
		ObservableList<FilterObject> items = listFilter.getItems();
		items.clear();
		
		for (FilterObject filter: filterList)
		{
			items.add(filter);
		}
		listFilter.setItems(items);
	}

	@FXML
	private void btnRemoveFilterClick(ActionEvent event) {
		FilterObject remove = listFilter.getSelectionModel().getSelectedItem();
		if (remove != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Remove course: '" + remove + "'");
			alert.setContentText("Are you sure?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				filterList.remove(remove);
				refreshlbFilter();
			} 
		}
	}

	@FXML
	private void btnAddCourseClick(ActionEvent event) throws IOException {

		//TODO sinnlose statements entfernen
		String filePath = ".." + File.separator + "Views" + File.separator + "CoursePage.fxml"; 
		FXMLLoader loader = new FXMLLoader(FilterViewModel.class.getResource(filePath));
		try {
			GridPane root = (GridPane) loader.load();
			
			CourseViewModel courseModel = loader.getController();
			System.out.println("hallo");
			courseModel.initData(courseMap.keySet(), null);

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.showAndWait();
			addCourse(courseModel.getNewCourse());		//add new course to map
		} catch (Exception e) {
			System.out.println("addcourseexception " + e.getMessage());
		}

		System.out.println("btnadd: " + cbModuleName.getValue());
		
		refreshlbCourses();

	}

	@FXML
	private void btnRemoveCourseClick(ActionEvent event) {
		Course remove = listCourses.getSelectionModel().getSelectedItem();
		if (remove != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Remove course: '" + remove + "'");
			alert.setContentText("Are you sure?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				courseMap.get(remove.getModuleName()).remove(remove);
				refreshlbCourses();
			} 
		}
	}

	@FXML
	private void btnLoadCoursesClick(ActionEvent event) {
		String filePath = ".." + File.separator + "Views" + File.separator + "LoadCoursePage.fxml"; 
		FXMLLoader loader = new FXMLLoader(FilterViewModel.class.getResource(filePath));
		try {
			GridPane root = (GridPane) loader.load();
			
			LoadCourseViewModel loadcourseModel = loader.getController();
			loadcourseModel.initData(courseMap.keySet());

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.showAndWait();
			
			for ( String key : loadcourseModel.getNewCourseMap().keySet() ) {
				for ( Course c : loadcourseModel.getNewCourseMap().get(key) ) {
					addCourse(c);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		refreshcbModuleName();
	}

	@FXML
	private void btnSaveCoursesClick(ActionEvent event) {
		//TODO überlegen wie überhaupt
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Attention");
		alert.setHeaderText("Method not implemented yet");
		alert.showAndWait();
	}

	@FXML
	private void cbModuleNameChange(ActionEvent event) {
		refreshlbCourses();
	}
	
	
	private void addCourse(Course course) {
		if (course == null) {
			return;
		} 
		
		List<Course> list = courseMap.get(course.getModuleName());
		if (list == null) {
			list = new ArrayList<Course>();
		} else {
			for (Course c : list) {
				if (c.toString().equals(course.toString())) {
					return;
				}
			}
		}
		
		list.add(course);
		courseMap.put(course.getModuleName(), list);
	}
		
	public void initData(){

		courseMap = new HashMap<String, List<Course>>();
		filterList = new ArrayList<FilterObject>();
		
		//TODO delete this!
		setCourseList();		// fills courseList with hardcoded data
								// later: import list or user input
		refreshcbModuleName();

	}
	

	private void setCourseList()
	{
		List<Course> courseList = new ArrayList<Course>();
		Course courseMath1 = new Lecture("BuS", new Time(EDay.DIENSTAG, 2, EPeriod.EACHWEEK), new Place("HSZ", "0004"), "Härtig");
		Course courseMath2 = new Lecture("BuS", new Time(EDay.FREITAG, 2, EPeriod.EACHWEEK), new Place("HSZ", "0003"), "Härtig");
		Course courseMath3 = new Lecture("FS", new Time(EDay.MONTAG, 3, EPeriod.EACHWEEK), new Place("HSZ", "0002"), "Hölldobler");
		Course courseMath4 = new Lecture("FS", new Time(EDay.DONNERSTAG, 4, EPeriod.EACHWEEK), new Place("HSZ", "0003"), "Hölldobler");
		Course courseMath5 = new Lecture("Mathe", new Time(EDay.DIENSTAG, 3, EPeriod.ODDWEEK), new Place("HSZ", "0002"), "Baumann");
		Course courseMath6 = new Lecture("Mathe", new Time(EDay.DONNERSTAG, 3, EPeriod.EACHWEEK), new Place("HSZ", "0003"), "Baumann");
		Course courseMath10 = new ExerciseCourse("BuS", new Time(EDay.MONTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath11 = new ExerciseCourse("BuS", new Time(EDay.MONTAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath12 = new ExerciseCourse("BuS", new Time(EDay.MONTAG, 3, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
		Course courseMath13 = new ExerciseCourse("BuS", new Time(EDay.DIENSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath14 = new ExerciseCourse("BuS", new Time(EDay.DIENSTAG, 6, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
		Course courseMath15 = new ExerciseCourse("BuS", new Time(EDay.MITTWOCH, 6, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath16 = new ExerciseCourse("BuS", new Time(EDay.DONNERSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath17 = new ExerciseCourse("BuS", new Time(EDay.DONNERSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
		Course courseMath18 = new ExerciseCourse("BuS", new Time(EDay.DONNERSTAG, 3, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath19 = new ExerciseCourse("BuS", new Time(EDay.FREITAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
		Course courseMath20 = new ExerciseCourse("BuS", new Time(EDay.FREITAG, 5, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath21 = new ExerciseCourse("FS", new Time(EDay.MONTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath22 = new ExerciseCourse("FS", new Time(EDay.MONTAG, 6, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
		Course courseMath23 = new ExerciseCourse("FS", new Time(EDay.DIENSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath24 = new ExerciseCourse("FS", new Time(EDay.DIENSTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
		Course courseMath25 = new ExerciseCourse("FS", new Time(EDay.MITTWOCH, 1, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath26 = new ExerciseCourse("FS", new Time(EDay.MITTWOCH, 6, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath27 = new ExerciseCourse("FS", new Time(EDay.DONNERSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
		Course courseMath28 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath29 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
		Course courseMath30 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath31 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath32 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 5, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
		Course courseMath33 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath34 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 4, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
		Course courseMath35 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 4, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath36 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 5, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath37 = new ExerciseCourse("Mathe", new Time(EDay.DIENSTAG, 4, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
		Course courseMath38 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath39 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
		Course courseMath40 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 3, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath41 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 4, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath42 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 4, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
		Course courseMath43 = new ExerciseCourse("Mathe", new Time(EDay.DONNERSTAG, 6, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath44 = new ExerciseCourse("Mathe", new Time(EDay.FREITAG, 5, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
		
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
		
		for (Course course : courseList) {
			addCourse(course);
		}
	}
	
	private List<TimeTable> generateTestData()
	{
		Controller myController = new Controller(courseMap);
		myController.generateTimeTables();
		List<TimeTable> allTimeTables = myController.getAllTimeTables();
		
		List<EDay> days = new ArrayList<EDay>();
		days.add(EDay.MONTAG);
		days.add(EDay.DIENSTAG);
		days.add(EDay.MITTWOCH);
		days.add(EDay.DONNERSTAG);
		days.add(EDay.FREITAG);
		FilterObject filter1 = new FilterObject(EFilter.BYMORNINGTIME, 2, days, null);
		FilterObject filter2 = new FilterObject(EFilter.BYAFTERNOONTIME, 5, days, null);
		FilterObject filter3 = new FilterObject(EFilter.BYMINNUMBER, 2, null, null);
		FilterObject filter4 = new FilterObject(EFilter.BYDOUBLECOURSES, 0, null, null);
		filterList.add(filter1);
		filterList.add(filter2);
		filterList.add(filter3);
		filterList.add(filter4);
		
		//iterate over FilterList and filter allTimeTables
		for (FilterObject filter : filterList) {
			switch (filter.getType()) {
			case BYAFTERNOONTIME:
				allTimeTables = Filter.filterByAfternoontime(allTimeTables, filter.getDays(), filter.getParameter());
				break;
				
			case BYMAXINROW:
				allTimeTables = Filter.filterByMaxInRow(allTimeTables, filter.getParameter());
				break;
			
			case BYMAXNUMBER:
				allTimeTables = Filter.filterByMaxNumber(allTimeTables, filter.getParameter());
				break;
				
			case BYMINNUMBER:
				allTimeTables = Filter.filterByMinNumber(allTimeTables, filter.getParameter());
				break;
				
			case BYMORNINGTIME:
				allTimeTables = Filter.filterByMorningtime(allTimeTables, filter.getDays(), filter.getParameter());
				break;

			case BYDOUBLECOURSES:
				allTimeTables = Filter.filterByDoubleCourses(allTimeTables);
				break;
				
				//TODO aa find mistake
			case BYFIXCOURSE:
				allTimeTables = Filter.filterByFixCourse(allTimeTables, filter.getCourse());
				
			default:
				break;
			}
		}

		myController.showTimeTables(allTimeTables);
		
		return allTimeTables;
	}
	
}
