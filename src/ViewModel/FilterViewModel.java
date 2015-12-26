package ViewModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Enumerations.EFilter;
import Enumerations.EDay;
import Model.FilterObject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FilterViewModel implements Initializable {
	
	private FilterObject newFilter;
	
	@FXML private javafx.scene.control.Button btnClose;
	@FXML private javafx.scene.control.Button btnSave;
	@FXML private javafx.scene.control.TextField txtWert;
	@FXML private javafx.scene.control.ComboBox<EFilter> cbFilter;
	
	@FXML private javafx.scene.control.CheckBox chbMo;
	@FXML private javafx.scene.control.CheckBox chbDi;
	@FXML private javafx.scene.control.CheckBox chbMi;
	@FXML private javafx.scene.control.CheckBox chbDo;
	@FXML private javafx.scene.control.CheckBox chbFr;
	
	@FXML private javafx.scene.control.Label lblMo;
	@FXML private javafx.scene.control.Label lblDi;
	@FXML private javafx.scene.control.Label lblMi;
	@FXML private javafx.scene.control.Label lblDo;
	@FXML private javafx.scene.control.Label lblFr;
	
	@FXML private void btnSaveClick(ActionEvent event){
		List<EDay> days = new ArrayList<EDay>();
		
		if (chbMo.isSelected())
			days.add(EDay.MONTAG);
		if (chbDi.isSelected())
			days.add(EDay.DIENSTAG);
		if (chbMi.isSelected())
			days.add(EDay.MITTWOCH);
		if (chbDo.isSelected())
			days.add(EDay.DONNERSTAG);
		if (chbFr.isSelected())
			days.add(EDay.FREITAG);
			
		
		newFilter = new FilterObject(cbFilter.getValue(), Integer.parseInt(txtWert.getText()), days);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Der Eintrag wurde erfolgreich gespeichert");
		alert.setContentText("Klicke OK um zum Hauptfenster zurückzukehren");
		alert.showAndWait();
		
		((Stage) btnClose.getScene().getWindow()).close();
	}

	@FXML private void btnCloseClick(ActionEvent event) {
		((Stage) btnClose.getScene().getWindow()).close();
	}
	
	@FXML private void cbFilterChange(ActionEvent event) {
		System.out.println("hallo");
		if (cbFilter.getValue().needDays()) {
			chbMo.setVisible(true);
			chbDi.setVisible(true);
			chbMi.setVisible(true);
			chbDo.setVisible(true);
			chbFr.setVisible(true);
			lblMo.setVisible(true);
			lblDi.setVisible(true);
			lblMi.setVisible(true);
			lblDo.setVisible(true);
			lblFr.setVisible(true);
			
		} else {
			chbMo.setSelected(false);
			chbDi.setSelected(false);
			chbMi.setSelected(false);
			chbDo.setSelected(false);
			chbFr.setSelected(false);
			chbMo.setVisible(false);
			chbDi.setVisible(false);
			chbMi.setVisible(false);
			chbDo.setVisible(false);
			chbFr.setVisible(false);
			lblMo.setVisible(false);
			lblDi.setVisible(false);
			lblMi.setVisible(false);
			lblDo.setVisible(false);
			lblFr.setVisible(false);
			
		}
	}
	
	public void initData(FilterObject filter){
		if (filter == null) {
			return;
		}
		
		cbFilter.setValue(filter.getType());
		txtWert.setText(Integer.toString(filter.getParameter()));
		for (EDay day : filter.getDays()) {
			if (day == EDay.MONTAG)
				chbMo.setSelected(true);
			if (day == EDay.DIENSTAG)
				chbDi.setSelected(true);
			if (day == EDay.MITTWOCH)
				chbMi.setSelected(true);
			if (day == EDay.DONNERSTAG)
				chbDo.setSelected(true);
			if (day == EDay.FREITAG)
				chbFr.setSelected(true);
		}
	}

	public FilterObject getFilter() {
		System.out.println("filter: " + newFilter + " added");
		return newFilter;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		chbMo.setVisible(false);
		chbDi.setVisible(false);
		chbMi.setVisible(false);
		chbDo.setVisible(false);
		chbFr.setVisible(false);
		lblMo.setVisible(false);
		lblDi.setVisible(false);
		lblMi.setVisible(false);
		lblDo.setVisible(false);
		lblFr.setVisible(false);
		
		ObservableList<EFilter> items = cbFilter.getItems();
		items.clear();
		items.add(EFilter.BYMORNINGTIME);
		items.add(EFilter.BYAFTERNOONTIME);
		items.add(EFilter.BYMINNUMBER);
		items.add(EFilter.BYMAXNUMBER);
		items.add(EFilter.BYMAXINROW);
		
	}
}
