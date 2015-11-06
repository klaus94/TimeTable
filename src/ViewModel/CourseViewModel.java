package ViewModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

public class CourseViewModel implements Initializable {

	private List<Course> courseList;
	private ObservableList<String> items;
	
	@FXML private javafx.scene.control.ComboBox<String> cbModulename;
	@FXML private javafx.scene.control.ComboBox<String> cbTyp;
	@FXML private javafx.scene.control.TextField txtBuilding;
	@FXML private javafx.scene.control.TextField txtRoom;
	@FXML private javafx.scene.control.TextField txtTime;
	@FXML private javafx.scene.control.TextField txtProf;
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
		
		items = cbModulename.getItems();
		System.out.println("initialize();");
		System.out.println("cbi " + cbModulename);
		System.out.println("cli " + courseList);
		System.out.println("ii " + items);
	}
	
	public void initData(List<Course> courseList) throws IOException {
		System.out.println("iiD1 " + items);
		System.out.println("initData");
		this.courseList = courseList;
		String filePath = ".." + File.separator + "Views" + File.separator + "CoursePage.fxml"; 
		System.out.println(filePath);
		FXMLLoader loader = new FXMLLoader(CourseViewModel.class.getResource(filePath));
		System.out.println(loader);
		System.out.println(this.courseList);
		GridPane root = (GridPane) loader.load();
		System.out.println(root);
		
	
		
		try {
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (Exception E) {
			System.out.println(E.getMessage());
		}
		
		System.out.println("initData();");
		System.out.println("cbiD " + cbModulename);
		System.out.println("cliD " + this.courseList);
		System.out.println("iiD2 " + items);
		
		refresh();
	}
	
	@FXML
	private void refresh() {
		System.out.println("refresh();");
		System.out.println("cbr " + cbModulename);
		System.out.println("clr " + this.courseList);
		System.out.println("ir " + items);
		
	}
	
	@FXML
	private void btnSaveClick(ActionEvent event) {
		
	}
	
	@FXML
	private void btnCloseClick(ActionEvent event) {
		((Stage) btnClose.getScene().getWindow()).close();
	}

}
