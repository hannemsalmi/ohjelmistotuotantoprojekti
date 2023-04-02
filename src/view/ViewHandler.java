package view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//Täältä löytyvät kaikki metodit, joita kutsutaan eri controllereista
public class ViewHandler {
	private Stage stage;
	private ViewController aktiivinen;
	private BorderPane root;
	private AnchorPane sisalto;
	private boolean suomi = true;
	
	public ViewHandler(Stage stage) {
		this.stage = stage;
	}
	
	public void start() {
		try {
		Scene scene;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/Ulkoasu.fxml"));
		
		root = loader.load();
		System.out.println(root);
		
		ViewController controller = loader.getController();
		controller.init(this);
		
		scene = new Scene(root);
		stage.setTitle("Budjettisovellus");
		stage.setScene(scene);
		stage.show();
		
		} catch (IOException e) {
			System.out.println("Ei onnistunut rootin avaus");
		}
		
		naytaEtusivu();
	}
	
	public void naytaEtusivu() {
		avaaSisalto("../view/Etusivu.fxml");
	}
	
	public void naytaKulut() {
		avaaSisalto("../view/Kulut.fxml");
	}
	
	public void naytaDiagrammi() {
		avaaSisalto("../view/Diagrammi.fxml");
	}
	
	public void naytaEnnuste() {
		avaaSisalto("../view/Ennuste.fxml");
	}

	public void naytaAsetukset() {
		avaaSisalto("../view/Asetukset.fxml");
	}
	
	private void avaaSisalto(String nimi) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(nimi));
			sisalto = (AnchorPane)loader.load();
			root.setCenter(sisalto);
			System.out.println(sisalto);
			ViewController controller = loader.getController();
			System.out.println(controller);
			aktiivinen = controller;
			controller.init(this);
		} catch (IOException e) {
			System.out.println("Ei onnistunut sisällön avaus");
			e.printStackTrace();
		}
	}
	
	public boolean getKieli() {
		return suomi;
	}
		
	public void setKieli(boolean kieli) {
		suomi = kieli;
	}

	
}
