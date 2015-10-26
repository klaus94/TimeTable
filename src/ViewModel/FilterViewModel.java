package ViewModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.FilterObject;
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
	
	@FXML private void btnSaveClick(ActionEvent event){
		txtWert.setText("No");
	}

	@FXML private void btnCloseClick(ActionEvent event) {
		((Stage) btnClose.getScene().getWindow()).close();
	}
	
	public void initData(FilterObject filter) throws IOException{
		
		
			String filePath = ".." + File.separator + "Views" + File.separator + "FilterPage.fxml"; 
			FXMLLoader loader = new FXMLLoader(ViewMain.class.getResource(filePath));
			FlowPane root = (FlowPane) loader.load();
			
			
			ViewFilter filterPage = loader.<ViewFilter>getController();
			System.out.println(filterPage);

			 
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
	
				System.out.println("hallo1");
				//btnClose.setText("hallo");
				System.out.println("hallo2");
			
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
