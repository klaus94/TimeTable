package ViewModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Enumerations.EDay;
import Enumerations.EPeriod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


	private Map<String, List<Course>> courseMap;
	private EDay day;
	private int ds;
	private EPeriod period;

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
	@FXML private javafx.scene.control.RadioButton rbMo;
	@FXML private javafx.scene.control.RadioButton rbDi;
	@FXML private javafx.scene.control.RadioButton rbMi;
	@FXML private javafx.scene.control.RadioButton rbDo;
	@FXML private javafx.scene.control.RadioButton rbFr;
	@FXML private javafx.scene.control.RadioButton rb1;
	@FXML private javafx.scene.control.RadioButton rb2;
	@FXML private javafx.scene.control.RadioButton rb3;
	@FXML private javafx.scene.control.RadioButton rb4;
	@FXML private javafx.scene.control.RadioButton rb5;
	@FXML private javafx.scene.control.RadioButton rb6;
	@FXML private javafx.scene.control.RadioButton rb7;
	@FXML private javafx.scene.control.RadioButton rbEW;
	@FXML private javafx.scene.control.RadioButton rbEV;
	@FXML private javafx.scene.control.RadioButton rbOD;
	
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

		
		cbTyp.setValue(items.get(0));
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
		

		//TODO: getValue from editable ComboBox.. no idea why this is not possible
		
		try {
			Course newCourse;
			System.out.println(cbModulename.valueProperty());
			if (cbTyp.getValue().equals("Vorlesung")) {
				System.out.println("Vorlesung");
				newCourse = new Lecture(cbModulename.getPromptText(), new Time(day, ds, period), new Place(txtBuilding.getText(), txtRoom.getText()), txtProf.getText());				
			} else {
				System.out.println("Exercise");
				newCourse = new ExerciseCourse(cbModulename.getValue(), new Time(day, ds, period), new Place(txtBuilding.getText(), txtRoom.getText()), txtProf.getText());
			}
			
			
			List<Course> list = courseMap.get(newCourse.getModuleName());
			if(list == null) {
				list = new ArrayList<Course>();
			}
			list.add(newCourse);
			courseMap.put(newCourse.getModuleName(), list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		

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
	
	@FXML
	private void rbMoCheck(ActionEvent event) {
		rbDi.setSelected(false);
		rbMi.setSelected(false);
		rbDo.setSelected(false);
		rbFr.setSelected(false);
		day = EDay.MONTAG;
	}
	
	@FXML
	private void rbDiCheck(ActionEvent event) {
		rbMo.setSelected(false);
		rbMi.setSelected(false);
		rbDo.setSelected(false);
		rbFr.setSelected(false);
		day = EDay.DIENSTAG;
	}
	
	@FXML
	private void rbMiCheck(ActionEvent event) {
		rbDi.setSelected(false);
		rbMo.setSelected(false);
		rbDo.setSelected(false);
		rbFr.setSelected(false);
		day = EDay.MITTWOCH;
	}
	
	@FXML
	private void rbDoCheck(ActionEvent event) {
		rbDi.setSelected(false);
		rbMi.setSelected(false);
		rbMo.setSelected(false);
		rbFr.setSelected(false);
		day = EDay.DONNERSTAG;
	}
	
	@FXML
	private void rbFrCheck(ActionEvent event) {
		rbDi.setSelected(false);
		rbMi.setSelected(false);
		rbDo.setSelected(false);
		rbMo.setSelected(false);
		day = EDay.FREITAG;
	}
	
	@FXML
	private void rb1Check(ActionEvent event) {
		rb2.setSelected(false);
		rb3.setSelected(false);
		rb4.setSelected(false);
		rb5.setSelected(false);
		rb6.setSelected(false);
		rb7.setSelected(false);
		ds = 1;
	}
	
	@FXML
	private void rb2Check(ActionEvent event) {
		rb1.setSelected(false);
		rb3.setSelected(false);
		rb4.setSelected(false);
		rb5.setSelected(false);
		rb6.setSelected(false);
		rb7.setSelected(false);
		ds = 2;
	}
	
	@FXML
	private void rb3Check(ActionEvent event) {
		rb2.setSelected(false);
		rb1.setSelected(false);
		rb4.setSelected(false);
		rb5.setSelected(false);
		rb6.setSelected(false);
		rb7.setSelected(false);
		ds = 3;
	}
	
	@FXML
	private void rb4Check(ActionEvent event) {
		rb2.setSelected(false);
		rb3.setSelected(false);
		rb1.setSelected(false);
		rb5.setSelected(false);
		rb6.setSelected(false);
		rb7.setSelected(false);
		ds = 4;
	}
	
	@FXML
	private void rb5Check(ActionEvent event) {
		rb2.setSelected(false);
		rb3.setSelected(false);
		rb4.setSelected(false);
		rb1.setSelected(false);
		rb6.setSelected(false);
		rb7.setSelected(false);
		ds = 5;
	}
	
	@FXML
	private void rb6Check(ActionEvent event) {
		rb2.setSelected(false);
		rb3.setSelected(false);
		rb4.setSelected(false);
		rb5.setSelected(false);
		rb1.setSelected(false);
		rb7.setSelected(false);
		ds = 6;
	}
	
	@FXML
	private void rb7Check(ActionEvent event) {
		rb2.setSelected(false);
		rb3.setSelected(false);
		rb4.setSelected(false);
		rb5.setSelected(false);
		rb6.setSelected(false);
		rb1.setSelected(false);
		ds = 7;
	}
	
	@FXML
	private void rbEWCheck(ActionEvent event) {
		rbEV.setSelected(false);
		rbOD.setSelected(false);
		period = EPeriod.EACHWEEK;
	}
	
	@FXML
	private void rbEVCheck(ActionEvent event) {
		rbEW.setSelected(false);
		rbOD.setSelected(false);
		period = EPeriod.EVENWEEK;
	}
	
	@FXML
	private void rbODCheck(ActionEvent event) {
		rbEV.setSelected(false);
		rbEW.setSelected(false);
		period = EPeriod.ODDWEEK;
	}

}
