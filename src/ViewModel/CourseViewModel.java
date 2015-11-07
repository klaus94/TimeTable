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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import Model.Course;
import Model.ExerciseCourse;
import Model.Lecture;
import Model.Place;
import Model.Time;

public class CourseViewModel implements Initializable {

	private List<Course> courseList;
	EDay day;
	int ds;
	EPeriod period;
	
	@FXML private javafx.scene.control.ComboBox<String> cbModulename;
	@FXML private javafx.scene.control.ComboBox<String> cbTyp;
	@FXML private javafx.scene.control.TextField txtBuilding;
	@FXML private javafx.scene.control.TextField txtRoom;
	@FXML private javafx.scene.control.TextField txtTime;
	@FXML private javafx.scene.control.TextField txtProf;
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
	}
	
	public void initData(List<Course> courseList) {
		this.courseList = courseList;
		refresh();
	}
	
	private void refresh() {
		ObservableList<String> items = cbModulename.getItems();
		items.clear();
		if(courseList != null) {
			for (Course course : courseList) {
				if (!items.contains(course.getModuleName())) {
					items.add(course.getModuleName());
				}
			}
		}
		items = cbTyp.getItems();
		items.clear();
		items.add("Vorlesung");
		items.add("Übung");
	}
	
	@FXML
	private void btnSaveClick(ActionEvent event) {
		
		try {
			Course newCourse;
			if (cbTyp.getValue().equals("Vorlesung")) {
				newCourse = new Lecture(cbModulename.getValue(), new Time(day, ds, period), new Place(txtBuilding.getText(), txtRoom.getText()), txtProf.getText());				
			} else {
				newCourse = new ExerciseCourse(cbModulename.getValue(), new Time(day, ds, period), new Place(txtBuilding.getText(), txtRoom.getText()), txtProf.getText());
			}
			courseList.add(newCourse);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
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
