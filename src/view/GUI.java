package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.IKontrolleriVtoM;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Kategoria;
import model.Kayttaja;
import controller.Kontrolleri;

public class GUI extends Application implements IGUI{
	private IKontrolleriVtoM kontrolleri;
	Label ostosLabel;
	Label hintaLabel;
	Label paivamaaraLabel;
	Label kategoriaLabel;
	Label kuvausLabel;
	
	TextField ostosField;
	TextField hintaField;
	TextField paivamaaraField;
	TextField kategoriaField; // muokataan myöhemmin valikoksi, josta saa valita haluamansa kategorian
	TextField kuvausField;
	
	Label uusiKategoriaLabel;
	TextField uusiKategoriaField;
	
	Button lisaaButton;
	Button kategoriaButton;
	
	public void init() {
		kontrolleri = new Kontrolleri(this);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Budjettisovellus");
			HBox root = luoHBox();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HBox luoHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(20, 15, 20, 15));
		hbox.setSpacing(10);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 20, 10, 20));
		
		ostosLabel = new Label("Ostos");
		hintaLabel = new Label("Hinta");
		paivamaaraLabel = new Label("Päivämäärä");
		kategoriaLabel = new Label("Kategoria");
		kuvausLabel = new Label("Kuvaus");
		ostosField = new TextField();
		hintaField = new TextField();
		paivamaaraField = new TextField();
		kategoriaField = new TextField();
		kuvausField = new TextField();
		
		lisaaButton = new Button("Lisää ostos");
		
		uusiKategoriaLabel = new Label("Uusi kategoria");
		uusiKategoriaField = new TextField();
		
		kategoriaButton = new Button("Lisää kategoria");
		
		grid.add(ostosLabel, 0, 0);
		grid.add(ostosField, 0, 1);
		grid.add(hintaLabel, 1, 0);
		grid.add(hintaField, 1, 1);
		grid.add(paivamaaraLabel, 2, 0);
		grid.add(paivamaaraField, 2, 1);
		grid.add(kategoriaLabel, 3, 0);
		grid.add(kategoriaField, 3, 1);
		grid.add(kuvausLabel, 4, 0);
		grid.add(kuvausField, 4, 1);
		grid.add(lisaaButton, 0, 2);
		
		grid.add(uusiKategoriaLabel, 0, 3);
		grid.add(uusiKategoriaField, 0, 4);
		grid.add(kategoriaButton, 0, 5);
		
		lisaaButton.setOnAction((event) -> {
			String nimi = ostosField.getText();
			double hinta = Double.parseDouble(hintaField.getText());
			String pvm = paivamaaraField.getText();
			LocalDate paivamaara = LocalDate.parse(pvm); //toimii nyt vain formaatissa YYYY/MM/DD
			Kategoria kategoria = new Kategoria(kategoriaField.getText()); //muokataan myöhemmin toimimaan valikon kanssa
			Kayttaja kayttaja = new Kayttaja("testi", 500); //kovakoodattu kehitystyötä varten
			String kuvaus = kuvausField.getText();
			kontrolleri.lisaaKulu(nimi, hinta, paivamaara, kategoria, kayttaja, kuvaus);
		});
		
		kategoriaButton.setOnAction((event) -> {
			kontrolleri.lisaaKategoria(uusiKategoriaField.getText());
		});
		
		hbox.getChildren().add(grid);
		
		return hbox;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}