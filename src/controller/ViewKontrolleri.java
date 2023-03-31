package controller;

import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ViewKontrolleri {
	
	@FXML
	private Button buttonLangFI;
	@FXML
	private Button buttonLangEN;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	public void kieliSuomeksi(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Ulkoasu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root, 800, 500);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void kieliEnglanniksi(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getClassLoader().getResource("view/UlkoasuEN.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root, 800, 500);
		stage.setScene(scene);
		stage.show();
	}

}
