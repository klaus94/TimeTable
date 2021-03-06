package Controller;

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
import Logic.Controller;
import Logic.Filter;
import Model.Course;
import Model.CourseList;
import Model.FilterObject;
import Model.TimeTable;
import Persistance.CourseListJAXB;
import Templates.WaitingView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class MainController implements Initializable
{
	private Map<String, List<Course>> courseMap; //maps modulnames to available courses
	private List<FilterObject> filterList;
	
	@FXML private javafx.scene.control.Button btnClose;
	@FXML private javafx.scene.control.Button btnGenerate;
	@FXML private javafx.scene.control.Button btnAddFilter;
	@FXML private javafx.scene.control.Button btnRemoveFilter;
	@FXML private javafx.scene.control.Button btnAddCourse;
	@FXML private javafx.scene.control.Button btnRemoveCourse;
	@FXML private javafx.scene.control.Button btnRemoveModule;
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
            		Course oldCourse = listCourses.getSelectionModel().getSelectedItem();
            		if (oldCourse == null)
            		{
            			throw new NullPointerException();
            		}
            		System.out.println("Double clicked " + oldCourse);
            		
            		String filePath = ".." + File.separator + "Views" + File.separator + "CoursePage.fxml";
            		FXMLLoader loader = new FXMLLoader(FilterController.class.getResource(filePath));
            		try {
            			GridPane root = (GridPane) loader.load();
	            		CourseController courseModel = loader.getController();
	        			System.out.println("hallo");
	        			courseModel.initData(courseMap.keySet(), oldCourse);
	        			courseModel.setCourse(oldCourse);
	
	        			Scene scene = new Scene(root);
	        			Stage stage = new Stage();
	        			stage.setScene(scene);
	        			stage.showAndWait();
	        			Course newCourse = courseModel.getNewCourse();
	        			if (newCourse != null){
	        				addCourse(newCourse);		//add new course to map
	        				courseMap.get(oldCourse.getModuleName()).remove(oldCourse);		// delete old Course
		        			refreshlbCourses();
	        			}
	        			
            		}
            		catch(Exception e)
            		{
            			System.out.println("Course could not be loaded");
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
		FXMLLoader loader = new FXMLLoader(FilterController.class.getResource(filePath));
		try {
			FlowPane root = (FlowPane) loader.load();
			FilterController filterModel = loader.getController();
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
			alert.setHeaderText("Remove filter: '" + remove + "'");
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
		FXMLLoader loader = new FXMLLoader(FilterController.class.getResource(filePath));
		try {
			GridPane root = (GridPane) loader.load();
			
			CourseController courseModel = loader.getController();
			System.out.println("hallo");
			courseModel.initData(courseMap.keySet(), null);

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.showAndWait();
			addCourse(courseModel.getNewCourse());		
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
	private void btnRemoveModuleClick(ActionEvent event) {
		String remove = cbModuleName.getSelectionModel().getSelectedItem(); //listCourses.getSelectionModel().getSelectedItem();
		if (remove != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Remove all courses of the module: '" + remove + "'");
			alert.setContentText("Are you sure?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				courseMap.remove(remove);
				refreshcbModuleName();
			} 
		}
	}

	@FXML
	private void btnSaveCoursesClick(ActionEvent event){
		CourseListJAXB c = new CourseListJAXB();
		List<Course> myList = getAllCourses();
		CourseList cList = new CourseList(myList);
		c.save(cList);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Your courses were saved succesfully!");
		alert.showAndWait();
	}
	
	@FXML
	private void btnLoadCoursesClick(ActionEvent event) {
		ButtonType bt1 = new ButtonType("File");
		ButtonType bt2 = new ButtonType("Website");
		ButtonType bt3 = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.getButtonTypes().setAll(bt1, bt2, bt3);
		alert.setTitle("Load courses");
		alert.setContentText("Do you want to load courses from a file or from the internet?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == bt1){
			// LOAD courses from file
			CourseListJAXB c = new CourseListJAXB();
			CourseList courseList = (CourseList)c.load();
			List<Course> courses = courseList.getCourse();
			if (courses == null)
			{
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setHeaderText("Your courses could not be loaded!");
				alert2.setContentText("Maybe the file is empty.");
				alert2.showAndWait();
				
				return;
			}
				
			for (Course course: courses)
			{
				addCourse(course);
			}
			refreshcbModuleName();
		} 
		else if (result.get() == bt2)
		{
			// LOAD courses from web
			String filePath = ".." + File.separator + "Views" + File.separator + "LoadCoursePage.fxml"; 
			FXMLLoader loader = new FXMLLoader(FilterController.class.getResource(filePath));
			try {
				GridPane root = (GridPane) loader.load();
				LoadCourseController loadcourseModel = loader.getController();
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
				System.out.println(e.getMessage());
			}
			refreshcbModuleName();
		}
		else
		{
			//canceled
		}
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
	
	private List<Course> getAllCourses()
	{
		List<Course> resultList = new ArrayList<Course>();
		for (String s: courseMap.keySet())
		{
			resultList.addAll(courseMap.get(s));
		}
		
		return resultList;
	}
		
	public void initData(){

		courseMap = new HashMap<String, List<Course>>();
		filterList = new ArrayList<FilterObject>();
		
		//TODO delete this!
//		setCourseList();		// fills courseList with hardcoded data
								// later: import list or user input
//		refreshcbModuleName();

	}
	

//	private void setCourseList()
//	{
//		List<Course> courseList = new ArrayList<Course>();
//		Course courseMath1 = new Course(ECourseType.LECTURE, "BuS", new Time(EDay.DIENSTAG, 2, EPeriod.EACHWEEK), new Place("HSZ", "0004"), "Härtig");
//		Course courseMath2 = new Course(ECourseType.LECTURE, "BuS", new Time(EDay.FREITAG, 2, EPeriod.EACHWEEK), new Place("HSZ", "0003"), "Härtig");
//		Course courseMath3 = new Course(ECourseType.LECTURE, "FS", new Time(EDay.MONTAG, 3, EPeriod.EACHWEEK), new Place("HSZ", "0002"), "Hölldobler");
//		Course courseMath4 = new Course(ECourseType.LECTURE, "FS", new Time(EDay.DONNERSTAG, 4, EPeriod.EACHWEEK), new Place("HSZ", "0003"), "Hölldobler");
//		Course courseMath5 = new Course(ECourseType.LECTURE, "Mathe", new Time(EDay.DIENSTAG, 3, EPeriod.ODDWEEK), new Place("HSZ", "0002"), "Baumann");
//		Course courseMath6 = new Course(ECourseType.LECTURE, "Mathe", new Time(EDay.DONNERSTAG, 3, EPeriod.EACHWEEK), new Place("HSZ", "0003"), "Baumann");
//		Course courseMath10 = new Course(ECourseType.EXERCISE, "BuS", new Time(EDay.MONTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
//		Course courseMath11 = new Course(ECourseType.EXERCISE, "BuS", new Time(EDay.MONTAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
//		Course courseMath12 = new Course(ECourseType.EXERCISE, "BuS", new Time(EDay.MONTAG, 3, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
//		Course courseMath13 = new Course(ECourseType.EXERCISE, "BuS", new Time(EDay.DIENSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
//		Course courseMath14 = new Course(ECourseType.EXERCISE, "BuS", new Time(EDay.DIENSTAG, 6, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
//		Course courseMath15 = new Course(ECourseType.EXERCISE, "BuS", new Time(EDay.MITTWOCH, 6, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
//		Course courseMath16 = new Course(ECourseType.EXERCISE, "BuS", new Time(EDay.DONNERSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
//		Course courseMath17 = new Course(ECourseType.EXERCISE, "BuS", new Time(EDay.DONNERSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
//		Course courseMath18 = new Course(ECourseType.EXERCISE, "BuS", new Time(EDay.DONNERSTAG, 3, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
//		Course courseMath19 = new Course(ECourseType.EXERCISE, "BuS", new Time(EDay.FREITAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
//		Course courseMath20 = new Course(ECourseType.EXERCISE, "BuS", new Time(EDay.FREITAG, 5, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
//		Course courseMath21 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.MONTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
//		Course courseMath22 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.MONTAG, 6, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
//		Course courseMath23 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.DIENSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
//		Course courseMath24 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.DIENSTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
//		Course courseMath25 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.MITTWOCH, 1, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
//		Course courseMath26 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.MITTWOCH, 6, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
//		Course courseMath27 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.DONNERSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
//		Course courseMath28 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.FREITAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
//		Course courseMath29 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.FREITAG, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
//		Course courseMath30 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.FREITAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
//		Course courseMath31 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.FREITAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
//		Course courseMath32 = new Course(ECourseType.EXERCISE, "FS", new Time(EDay.FREITAG, 5, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
//		Course courseMath33 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.MONTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
//		Course courseMath34 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.MONTAG, 4, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
//		Course courseMath35 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.MONTAG, 4, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
//		Course courseMath36 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.MONTAG, 5, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
//		Course courseMath37 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.DIENSTAG, 4, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
//		Course courseMath38 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.MITTWOCH, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
//		Course courseMath39 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.MITTWOCH, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
//		Course courseMath40 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.MITTWOCH, 3, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
//		Course courseMath41 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.MITTWOCH, 4, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
//		Course courseMath42 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.MITTWOCH, 4, EPeriod.EACHWEEK), new Place("WIL", "A923"), "3");
//		Course courseMath43 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.DONNERSTAG, 6, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
//		Course courseMath44 = new Course(ECourseType.EXERCISE, "Mathe", new Time(EDay.FREITAG, 5, EPeriod.EACHWEEK), new Place("WIL", "B234"), "5");
//		
//		courseList.add(courseMath1);
//		courseList.add(courseMath2);
//		courseList.add(courseMath3);
//		courseList.add(courseMath4);
//		courseList.add(courseMath5);
//		courseList.add(courseMath6);
//
//		courseList.add(courseMath10);
//		courseList.add(courseMath11);
//		courseList.add(courseMath12);
//		courseList.add(courseMath13);
//		courseList.add(courseMath14);
//		courseList.add(courseMath15);
//		courseList.add(courseMath16);
//		courseList.add(courseMath17);
//		courseList.add(courseMath18);
//		courseList.add(courseMath19);
//		courseList.add(courseMath20);
//		courseList.add(courseMath21);
//		courseList.add(courseMath22);
//		courseList.add(courseMath23);
//		courseList.add(courseMath24);
//		courseList.add(courseMath25);
//		courseList.add(courseMath26);
//		courseList.add(courseMath27);
//		courseList.add(courseMath28);
//		courseList.add(courseMath29);
//		courseList.add(courseMath30);
//		courseList.add(courseMath31);
//		courseList.add(courseMath32);
//		courseList.add(courseMath33);
//		courseList.add(courseMath34);
//		courseList.add(courseMath35);
//		courseList.add(courseMath36);
//		courseList.add(courseMath37);
//		courseList.add(courseMath38);
//		courseList.add(courseMath39);
//		courseList.add(courseMath40);
//		courseList.add(courseMath41);
//		courseList.add(courseMath42);
//		courseList.add(courseMath43);
//		courseList.add(courseMath44);
//		
//		for (Course course : courseList) {
//			addCourse(course);
//		}
//	}
	
	private List<TimeTable> generateTestData()
	{
		Controller myController = new Controller(courseMap);
		myController.generateTimeTables();
		List<TimeTable> allTimeTables = myController.getAllTimeTables();
		
//		List<EDay> days = new ArrayList<EDay>();
//		days.add(EDay.MONTAG);
//		days.add(EDay.DIENSTAG);
//		days.add(EDay.MITTWOCH);
//		days.add(EDay.DONNERSTAG);
//		days.add(EDay.FREITAG);
//		FilterObject filter1 = new FilterObject(EFilter.BYMORNINGTIME, 2, days, null);
//		FilterObject filter2 = new FilterObject(EFilter.BYAFTERNOONTIME, 5, days, null);
//		FilterObject filter3 = new FilterObject(EFilter.BYMINNUMBER, 2, null, null);
//		FilterObject filter4 = new FilterObject(EFilter.BYDOUBLECOURSES, 0, null, null);
//		filterList.add(filter1);
//		filterList.add(filter2);
//		filterList.add(filter3);
//		filterList.add(filter4);
		
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
