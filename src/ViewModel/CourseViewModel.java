package ViewModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Enumerations.EDay;
import Enumerations.EPeriod;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import Model.Course;
import Model.ExerciseCourse;
import Model.Lecture;
import Model.Place;
import Model.Time;

public class CourseViewModel implements Initializable {
	private static Course newCourse;
	private static List<String> modulList;
	private ObservableList<String> modulItems;
	
	@FXML private javafx.scene.control.ComboBox<String> cbModulename;
	@FXML private javafx.scene.control.ComboBox<String> cbTyp;
	@FXML private javafx.scene.control.TextField txtBuilding;
	@FXML private javafx.scene.control.TextField txtRoom;
	@FXML private javafx.scene.control.TextField txtTime;
	@FXML private javafx.scene.control.TextField txtInstructor;
	@FXML private javafx.scene.control.Button btnSave;
	@FXML private javafx.scene.control.Button btnClose;
	
	public CourseViewModel() {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
//		ObservableList<String> items = cbModulename.getItems();
//		for (Course course : courseList) {
//			items.add(course.getModuleName());
//		}
		
		modulItems = cbModulename.getItems();
		System.out.println("initialize();");
		System.out.println("cbi " + cbModulename);
		System.out.println("cli " + modulList);
		System.out.println("ii " + modulItems);
		
		// fill in Combobox - Items for cbTyp:
		ObservableList<String> typList = cbTyp.getItems();
		typList.clear();
		typList.add("Übung");
		typList.add("Vorlesung");
		cbTyp.setItems(typList);
		cbTyp.setValue("Übung");
		
		// fill in Combobox - Items for cbModuleName
		System.out.println("my modullist:2 " + modulList);
		modulItems.clear();
		modulItems.addAll(modulList);
		cbModulename.setItems(modulItems);
		if (modulList != null)
			cbModulename.setValue(modulList.get(0));
		
		
	}
	
	public void initData(List<String> modulList) throws IOException {
		System.out.println("iiD1 " + modulItems);
		System.out.println("initData");
		CourseViewModel.modulList = modulList;
		System.out.println("my modullist:1 " + modulList);
		String filePath = ".." + File.separator + "Views" + File.separator + "CoursePage.fxml"; 
		System.out.println(filePath);
		FXMLLoader loader = new FXMLLoader(CourseViewModel.class.getResource(filePath));
		System.out.println(loader);
		System.out.println(CourseViewModel.modulList);
		GridPane root = (GridPane) loader.load();		// call initialize
		System.out.println(root);
		
		
		try {
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.showAndWait();
		} catch (Exception E) {
			System.out.println(E.getMessage());
		}
		
		System.out.println("initData();");
		System.out.println("cbiD " + cbModulename);
		System.out.println("cliD " + CourseViewModel.modulList);
		System.out.println("iiD2 " + modulItems);
		
		refresh();
	}
	
	//TEST
	public Course getNewCourse()
	{
		return CourseViewModel.newCourse;
	}
	
	@FXML
	private void refresh() {
		System.out.println("refresh();");
		System.out.println("cbr " + cbModulename);
		System.out.println("clr " + CourseViewModel.modulList);
		System.out.println("ir " + modulItems);
		
	}
	
	@FXML
	private void btnSaveClick(ActionEvent event) {
		// TODO: validate user-input
		//		- try/catch block
		//		- wrong input -> cancel save-method, print warning
		String newModulName = cbModulename.getValue();
		String newInstructor = txtInstructor.getText();
		
		EDay newDay = EDay.MONTAG;					// TODO: read from GUI
		EPeriod newPeriod = EPeriod.EACHWEEK;		// TODO: read from GUI
		int newLesson = 2;							// TODO: read from GUI
		Time newTime = new Time(newDay, newLesson, newPeriod);
		
		String newBuilding = txtBuilding.getText();
		String newRoom = txtRoom.getText();
		Place newPlace = new Place(newBuilding, newRoom);
		
		if (cbTyp.getValue().equals("Vorlesung"))
		{
			newCourse = new Lecture(newModulName, newTime, newPlace, newInstructor);
		}
		else if (cbTyp.getValue().equals("Übung"))
		{
			newCourse = new ExerciseCourse(newModulName, newTime, newPlace, newInstructor);
		}

		System.out.println("new entry saved");
		System.out.println("new course instructor:" + getNewCourse().getInstructor());
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Der Eintrag wurde erfolgreich gespeichert");
		alert.setContentText("Klicke OK um zum Hauptfenster zurückzukehren");
		alert.showAndWait();
		
		((Stage) btnClose.getScene().getWindow()).close();
	}
	
	@FXML
	private void btnCloseClick(ActionEvent event) {
		((Stage) btnClose.getScene().getWindow()).close();
	}

}
