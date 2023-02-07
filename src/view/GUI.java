package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.IKontrolleriVtoM;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import kayttajanHallinta.KayttajanHallinta;
import model.Kategoria;
import model.Kategoriat;
import model.Kayttaja;
import model.Kulu;
import model.Kulut;
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
	ComboBox<String> kategoriaBox;
	TextField kuvausField;
	Label uusiKategoriaLabel;
	TextField uusiKategoriaField;
	Button lisaaButton;
	Button lisaaKayttajaButton;
	Button kategoriaButton;
	Label kulutLabel;
	Label kayttajaValitsinLabel;
	List<Kulu> kulut = new ArrayList<>();
	ListView<Kulu> kulutlista = new ListView<Kulu>();
	ComboBox<String> userProfileSelector = new ComboBox<>();
	KayttajanHallinta kayttajanhallinta = KayttajanHallinta.getInstance();
	
	public void init() {
		kontrolleri = new Kontrolleri(this);
	}

	@Override
	public void start(Stage primaryStage) {
		  try {
		    if (kontrolleri.getKayttaja(1) == null) {
		      StackPane root = new StackPane();
		      Scene scene = new Scene(root, 400, 400);

		      Label label = new Label("Luo uusi käyttäjätunnus:");
		      TextField textField = new TextField();
		      Label label2 = new Label("Aseta kuukausittainen budjettisi:");
		      TextField textField2 = new TextField();
		      Button button = new Button("Luo käyttäjä");

		      VBox vbox = new VBox();
		      vbox.getChildren().addAll(label, textField, label2, textField2, button);
		      root.getChildren().add(vbox);

		      button.setOnAction(new EventHandler<ActionEvent>() {
		        @Override
		        public void handle(ActionEvent event) {
		          String username = textField.getText();
		          double budjetti = Double.parseDouble(textField2.getText());
		          if (!username.isEmpty()) {
		            kontrolleri.lisaaKayttaja(username, budjetti);
		            kayttajanhallinta.kirjoitaKayttajaID(1);
		            primaryStage.setTitle("Budjettisovellus");
		            HBox hbox = luoHBox();
		            Scene mainScene = new Scene(hbox);
		            primaryStage.setScene(mainScene);
		            primaryStage.show();
		          }
		        }
		      });

		      primaryStage.setScene(scene);
		      primaryStage.show();
		    } else {
		      primaryStage.setTitle("Budjettisovellus");
		      HBox hbox = luoHBox();
		      Scene scene = new Scene(hbox);
		      primaryStage.setScene(scene);
		      primaryStage.show();
		    }
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
		//
		kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(kayttajanhallinta.lueKayttajaID()));
		Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		
		kulut = kontrolleri.getKulut(kayttaja.getKayttajaID());
		setKulut(kulut);
		
		ostosLabel = new Label("Ostos");
		hintaLabel = new Label("Hinta");
		paivamaaraLabel = new Label("Päivämäärä");
		lisaaButton = new Button("Lisää ostos");
		kulutlista.setPrefHeight(300);

		kategoriaLabel = new Label("Kategoria");
		kuvausLabel = new Label("Kuvaus");
		ostosField = new TextField();
		hintaField = new TextField();
		paivamaaraField = new TextField();
		kuvausField = new TextField();
		
		kategoriaBox = new ComboBox<>();
		kategoriaBox.setEditable(true);
		kategoriaBox.getItems().addAll(kontrolleri.getKategorianimet());
		
		lisaaButton = new Button("Lisää ostos");
		lisaaKayttajaButton = new Button("Lisää uusi käyttäjä");
		uusiKategoriaLabel = new Label("Uusi kategoria");
		uusiKategoriaField = new TextField();
		kategoriaButton = new Button("Lisää kategoria");
		
		kayttajaValitsinLabel = new Label("Valitse käyttäjä");
        userProfileSelector.getItems().addAll(kontrolleri.getKayttajat());
        userProfileSelector.getSelectionModel().select(kayttajanhallinta.getKirjautunutKayttaja().getKayttajaID()-1);
        userProfileSelector.setOnAction(event -> {
            int selectedUser = userProfileSelector.getSelectionModel().getSelectedIndex() +1;
            kayttajanhallinta.kirjoitaKayttajaID(selectedUser);
            kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(selectedUser));
            kulut = kontrolleri.getKulut(selectedUser);
    		setKulut(kulut);
            System.out.println("Logging in user: " + selectedUser);
        });
        
		grid.add(ostosLabel, 0, 0);
		grid.add(ostosField, 0, 1);
		grid.add(hintaLabel, 1, 0);
		grid.add(hintaField, 1, 1);
		grid.add(paivamaaraLabel, 2, 0);
		grid.add(paivamaaraField, 2, 1);
		grid.add(kategoriaLabel, 3, 0);
		grid.add(kategoriaBox, 3, 1);
		grid.add(kuvausLabel, 4, 0);
		grid.add(kuvausField, 4, 1);
		grid.add(kayttajaValitsinLabel, 6, 0);
		grid.add(userProfileSelector, 6, 1);
		grid.add(lisaaKayttajaButton, 7, 1);
		grid.add(lisaaButton, 0, 2);
		
		grid.add(uusiKategoriaLabel, 0, 3);
		grid.add(uusiKategoriaField, 0, 4);
		grid.add(kategoriaButton, 0, 5);
		GridPane.setColumnSpan(kulutlista, 5);
		grid.add(kulutlista, 0, 7);
		
		lisaaButton.setOnAction((event) -> {
			String nimi = ostosField.getText();
			double hinta = Double.parseDouble(hintaField.getText());
			String pvm = paivamaaraField.getText();
			LocalDate paivamaara = LocalDate.parse(pvm); //toimii nyt vain formaatissa YYYY/MM/DD
			String kategorianNimi = kategoriaBox.getSelectionModel().getSelectedItem();
			Kategoria kategoria = kontrolleri.getKategoria(kategorianNimi);
			String kuvaus = kuvausField.getText();
			kontrolleri.lisaaKulu(nimi, hinta, paivamaara, kategoria, kayttaja, kuvaus);
			kulut = kontrolleri.getKulut(kayttajanhallinta.getKirjautunutKayttaja().getKayttajaID());
			setKulut(kulut);
		});
		
		
		kategoriaButton.setOnAction((event) -> {
			kontrolleri.lisaaKategoria(uusiKategoriaField.getText());
		});
		lisaaKayttajaButton.setOnAction((event) -> {
			luoKayttajaIkkuna();
		});
		hbox.getChildren().add(grid);
		
		return hbox;
	}
	
	public void setKulut(List<Kulu> kulut) {
		ObservableList<Kulu> observableKulut = FXCollections.observableList(kulut);
		this.kulutlista.setItems(observableKulut);
	}
	
	public void luoKayttajaIkkuna() {
		StackPane root = new StackPane();
	    Scene scene = new Scene(root, 400, 400);
	    Stage stage = new Stage();
	    Label label = new Label("Luo uusi käyttäjätunnus:");
	    TextField textField = new TextField();
	    Label label2 = new Label("Aseta kuukausittainen budjettisi:");
	    TextField textField2 = new TextField();
	    Button button = new Button("Luo käyttäjä");

	    VBox vbox = new VBox();
	    vbox.getChildren().addAll(label, textField, label2, textField2, button);
	    root.getChildren().add(vbox);

	    button.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	      public void handle(ActionEvent event) {
	        String username = textField.getText();
	        double budjetti = Double.parseDouble(textField2.getText());
	        if (!username.isEmpty()) {
	          kontrolleri.lisaaKayttaja(username, budjetti);
	          textField.clear();
	          textField2.clear();
	          userProfileSelector.getItems().add(username);
	        }
	      }
	    });

	      stage.setScene(scene);
	      stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}