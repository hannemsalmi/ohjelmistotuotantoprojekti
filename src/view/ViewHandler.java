package view;

import java.io.IOException;
import java.util.List;

import controller.IKontrolleri;
import controller.Kontrolleri;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kayttajanHallinta.KayttajanHallinta;
import model.Kulu;

//Täältä löytyvät kaikki metodit, joita kutsutaan eri controllereista
public class ViewHandler implements IGUI{
	private IKontrolleri kontrolleri;
	private Stage stage;
	private ViewController aktiivinen;
	private BorderPane root;
	private AnchorPane sisalto;
	private boolean suomi = true;
	
	private KayttajanHallinta kayttajanhallinta = KayttajanHallinta.getInstance();

	public ViewHandler(Stage stage) {
		this.stage = stage;
		kontrolleri = new Kontrolleri(this);
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
		onkoKayttajaa();
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

	public void onkoKayttajaa() {
		if (kontrolleri.getKayttaja(1) == null) {
			StackPane root = new StackPane();
            Scene scene = new Scene(root, 400, 400);
            Stage kayttajaStage = new Stage();

            root.setStyle("-fx-background-color: #DAE3E5;");

            Label label = new Label("Luo uusi käyttäjätunnus:");
            TextField textField = new TextField();
            Label label2 = new Label("Aseta kuukausittainen budjettisi:");
            TextField textField2 = new TextField();
            Button button = new Button("Luo käyttäjä");
            button.setStyle("-fx-background-color: #507DBC; -fx-text-fill: white; -fx-font-weight: bold;");
            label.setStyle("-fx-font-family: Arial;");
            textField.setStyle("-fx-font-family: Arial;");
            label2.setStyle("-fx-font-family: Arial;");
            textField2.setStyle("-fx-font-family: Arial;");

            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(10));
            vbox.getChildren().addAll(label, textField, label2, textField2, button);
            root.getChildren().add(vbox);
            
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String username = textField.getText();
                    double budjetti = Double.parseDouble(textField2.getText());
                    if (!username.isEmpty()) {
                        kontrolleri.lisaaKayttaja(username, budjetti);
                        kontrolleri.lisaaKategoria("Yleinen", username);
                        kayttajanhallinta.kirjoitaKayttajaID(1);
                        kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(kayttajanhallinta.lueKayttajaID()));
                        kayttajaStage.close();
                    }
                }
            });
            kayttajaStage.setScene(scene);
            kayttajaStage.setTitle("Luo käyttäjä");
    	    kayttajaStage.show();
		}
		
		kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(kayttajanhallinta.lueKayttajaID()));
	}

	
	public KayttajanHallinta getKayttajanhallinta() {
		return kayttajanhallinta;
	}

	public IKontrolleri getKontrolleri() {
		return kontrolleri;
	}
}
