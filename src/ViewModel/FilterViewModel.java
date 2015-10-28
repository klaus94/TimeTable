package ViewModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Enumerations.EFilter;
import Model.FilterObject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class FilterViewModel implements Initializable {
	
	@FXML private javafx.scene.control.Button btnClose;
	@FXML private javafx.scene.control.Button btnSave;
	@FXML private javafx.scene.control.TextField txtWert;
	@FXML private javafx.scene.control.ComboBox<String> cbFilter;
	
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
		if (EFilter.toEnum(cbFilter.getValue()).needDays()) {
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
		
		
			String filePath = ".." + File.separator + "Views" + File.separator + "FilterPage.fxml"; 
			FXMLLoader loader = new FXMLLoader(FilterViewModel.class.getResource(filePath));
			FlowPane root = (FlowPane) loader.load();
			
			
			FilterViewModel filterPage = loader.<FilterViewModel>getController();
			System.out.println(filterPage);

			 
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
				
			
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
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
		
		ObservableList<String> items = cbFilter.getItems();
		items.clear();
		items.add(EFilter.BYMORNINGTIME.toString());
		items.add(EFilter.BYAFTERNOONTIME.toString());
		items.add(EFilter.BYMINNUMBER.toString());
		items.add(EFilter.BYMAXNUMBER.toString());
		items.add(EFilter.BYMAXINROW.toString());
		
	}
}
