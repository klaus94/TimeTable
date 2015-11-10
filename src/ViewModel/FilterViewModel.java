package ViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Enumerations.EFilter;
import Model.FilterObject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class FilterViewModel implements Initializable {
	
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
		//TODO -oTilo: implementieren
		txtWert.setText("No");
		if (chbMo != null) {
			chbMo.setVisible(false);
		}
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
	
	public void initData(FilterObject filter) throws IOException{
		//TODO -oTilo: implementieren
			
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
