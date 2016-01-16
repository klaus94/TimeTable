package ViewModel;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import Enumerations.ECourseType;
import Enumerations.EDay;
import Enumerations.EPeriod;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import Model.Course;
import Model.Place;
import Model.Time;

public class CourseViewModel implements Initializable {

	private EDay day;
	private int ds;
	private EPeriod period;

	private Course newCourse;
	private ObservableList<String> modulItems;

	
	@FXML private javafx.scene.control.ComboBox<String> cbModulename;
	@FXML private javafx.scene.control.ComboBox<String> cbTyp;
	@FXML private javafx.scene.control.TextField txtBuilding;
	@FXML private javafx.scene.control.TextField txtRoom;
	//@FXML private javafx.scene.control.TextField txtTime;		// not used yet
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
		System.out.println("old constructor");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public void initData(Set<String> set, Course oldCourse) {
		//TODO sinnlose statements entfernen
		
		// fill in Combobox - Items for cbTyp:
		ObservableList<String> typList = cbTyp.getItems();
		typList.clear();
		typList.add("Übung");
		typList.add("Vorlesung");
		cbTyp.setItems(typList);
		cbTyp.setValue("Übung");
		
		// fill in Combobox - Items for cbModuleName
		modulItems = cbModulename.getItems();
		modulItems.clear();
		modulItems.addAll(set);
		cbModulename.setItems(modulItems);
		if (set != null)
			cbModulename.setValue(modulItems.get(0));
		
		// fill in other fields with course, the user wants to edit
		if (oldCourse != null)
		{
			// SET DATA
			day = oldCourse.getTime().getDay();
			period = oldCourse.getTime().getPeriod();
			ds = oldCourse.getTime().getTime();
			
			// SET GUI
			// comboboxes
			cbModulename.setValue(oldCourse.getModuleName());
			if (oldCourse.getCourseType() == ECourseType.LECTURE)
				cbTyp.setValue("Vorlesung");
			else
				cbTyp.setValue("Übung");
			// textboxes
			txtBuilding.setText(oldCourse.getPlace().getBuilding());
			txtRoom.setText(oldCourse.getPlace().getRoom());
			txtInstructor.setText(oldCourse.getInstructor());
			// checkboxes
			switch (oldCourse.getTime().getDay())
			{
				case MONTAG: rbMo.setSelected(true); break;
				case DIENSTAG: rbDi.setSelected(true); break;
				case MITTWOCH: rbMi.setSelected(true); break;
				case DONNERSTAG: rbDo.setSelected(true); break;
				case FREITAG: rbFr.setSelected(true); break;
				default: break;
			}
			switch (oldCourse.getTime().getPeriod())
			{
				case EACHWEEK: rbEW.setSelected(true); break;
				case EVENWEEK: rbEV.setSelected(true); break;
				case ODDWEEK: rbOD.setSelected(true); break;
				default: break;
			}
			switch (oldCourse.getTime().getTime())
			{
				case 1: rb1.setSelected(true); break;
				case 2: rb2.setSelected(true); break;
				case 3: rb3.setSelected(true); break;
				case 4: rb4.setSelected(true); break;
				case 5: rb5.setSelected(true); break;
				case 6: rb6.setSelected(true); break;
				default: break;
			}
		}

	}
	
	// for a course to be edited
	public void setCourse(Course oldCourse)
	{
		System.out.println(oldCourse + " wurde geladen");
	}
	
	public Course getNewCourse()
	{
		return newCourse;
	}
		
	@FXML
	private void btnSaveClick(ActionEvent event) {
		try {
			String newModulName = cbModulename.getEditor().getText();
			String newInstructor = txtInstructor.getText();

			EDay newDay = day;					
			EPeriod newPeriod = period;		
			
			int newLesson = ds;	
			System.out.println("time: " + newDay + newLesson + newPeriod);
			Time newTime = new Time(newDay, newLesson, newPeriod);

			String newBuilding = txtBuilding.getText();
			String newRoom = txtRoom.getText();
			Place newPlace = new Place(newBuilding, newRoom);

			

			if (cbTyp.getValue().equals("Vorlesung"))
			{
				newCourse = new Course(ECourseType.LECTURE, newModulName, newTime, newPlace, newInstructor);
			}
			else if (cbTyp.getValue().equals("Übung"))
			{
				newCourse = new Course(ECourseType.EXERCISE, newModulName, newTime, newPlace, newInstructor);
			}
		} catch (Exception e) {
			System.out.println("btnSave " + e.getMessage());
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Information");
			alert.setHeaderText("Der Eintrag konnte nicht gespeichert werden!!!");
 			alert.setContentText("Es fehlen bestimmte notwendige Informationen!!!");
			alert.showAndWait();
			return;
		}

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
