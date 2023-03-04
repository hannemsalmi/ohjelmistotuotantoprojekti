package view;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import controller.IKontrolleri;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kayttajanHallinta.KayttajanHallinta;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;
import controller.Kontrolleri;

public class GUI extends Application implements IGUI{
	private static final int List = 0;
	private IKontrolleri kontrolleri;
	Label ostosLabel;
	Label hintaLabel;
	Label paivamaaraLabel;
	Label kategoriaLabel;
	Label kuvausLabel;
	Label budjettiLabel;
	Label kategoriasuodatin;
	Label kuukausisuodatin;
	Label vuosiSuodatin;
	TextField ostosField;
	TextField hintaField;
	TextField paivamaaraField;
	ComboBox<String> kategoriaBox;
	ComboBox<String> kategoriaBoxSuodatus;
	TextField kuvausField;
	Label uusiKategoriaLabel;
	TextField uusiKategoriaField;
	Button lisaaButton;
	Button lisaaKayttajaButton;
	Button kayttajaAsetusButton;
	Button kategoriaButton;
	Button kulutusTrendiButton;
	Button kuluDiagrammiButton;
	Button muokkaaOstostaButton;
	Button muokkaaKategoriaaButton;
	Label kulutLabel;
	Label kayttajaValitsinLabel;
	List<Kulu> kulut = new ArrayList<>();
	ListView<Kulu> kulutlista = new ListView<Kulu>();
	ComboBox<String> userProfileSelector = new ComboBox<>();
	KayttajanHallinta kayttajanhallinta = KayttajanHallinta.getInstance();
	List<Kulu> suodatetutKulut = new ArrayList<>();
	DatePicker pvmValitsin = new DatePicker();
	LocalDate paivamaara;
	ComboBox<String> kuukausiValitsin;
	ComboBox<String> vuosiValitsin;
	Double budjettiaJaljella;
	public void init() {
		kontrolleri = new Kontrolleri(this);
	}

	@Override
	public void start(Stage primaryStage) {
	    try {
	        if (kontrolleri.getKayttaja(1) == null) {
	            StackPane root = new StackPane();
	            Scene scene = new Scene(root, 400, 400);

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
	                        primaryStage.setTitle("Budjettisovellus");
	                        HBox hbox = luoHBox();
	                        Scene mainScene = new Scene(hbox);
	                        primaryStage.setScene(mainScene);
	                        primaryStage.show();
	                    }
	                }
	            });

	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Luo käyttäjä");
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
		hbox.setStyle("-fx-background-color: " + "#DAE3E5" + ";");
		hbox.setSpacing(20);
		hbox.setMinWidth(1000);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 20, 10, 20));
		grid.setStyle("-fx-background-color: " + "#DAE3E5" + ";");
		grid.setHgap(10);
		grid.setVgap(10);
		
		ColumnConstraints col1 = new ColumnConstraints(120);
		ColumnConstraints col2 = new ColumnConstraints(120);
		ColumnConstraints col3 = new ColumnConstraints(120);
		ColumnConstraints col4 = new ColumnConstraints(120);
		ColumnConstraints col5 = new ColumnConstraints(120);
		ColumnConstraints col6 = new ColumnConstraints(120);
		ColumnConstraints col7 = new ColumnConstraints(120);
		ColumnConstraints col8 = new ColumnConstraints(120);
		
		
		grid.getColumnConstraints().add(col1);
		grid.getColumnConstraints().add(col2);
		grid.getColumnConstraints().add(col3);
		grid.getColumnConstraints().add(col4);
		grid.getColumnConstraints().add(col5);
		grid.getColumnConstraints().add(col6);
		grid.getColumnConstraints().add(col7);
		grid.getColumnConstraints().add(col8);
		
		Color mainColor = Color.rgb(218, 227, 229);
		Color accentColor = Color.rgb(80, 125, 188);
		String mainColorHex = "#DAE3E5";
		String accentColorHex = "#507DBC";
			
		Font labelFont = Font.font("Arial", FontWeight.BOLD, 14);
		Font inputFont = Font.font("Arial", FontWeight.NORMAL, 14);
		Font budjettiFont = Font.font("Arial", FontWeight.BOLD, 20);
		
		kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(kayttajanhallinta.lueKayttajaID()));
		Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		
		kulut = kontrolleri.getKulut(kayttaja.getKayttajaID());
		budjettiaJaljella = kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti();
		budjettiLabel = new Label("Budjettia jäljellä:\n" + String.format("%.2f",budjettiaJaljella) + " €");
		budjettiaJaljella = budjettiaJaljellaLaskuri();
		setKulut(kulut);
		
		ostosLabel = new Label("Ostos");
		hintaLabel = new Label("Hinta");
		paivamaaraLabel = new Label("Päivämäärä");
		lisaaButton = new Button("Lisää ostos");
		kulutlista.setPrefHeight(300);
		kulutlista.setPrefWidth(500);

		kategoriaLabel = new Label("Kategoria");
		kuvausLabel = new Label("Kuvaus");
		ostosField = new TextField();
		hintaField = new TextField();
		paivamaaraField = new TextField();
		kuvausField = new TextField();
		
		
		kategoriasuodatin = new Label("Kategoria");
		kuukausisuodatin = new Label("Kuukausi");
		vuosiSuodatin = new Label("Vuosi");
		kategoriaBox = new ComboBox<>();
		kategoriaBox.setEditable(false);
		kategoriaBox.setMaxWidth(110);
		kategoriaBox.getItems().addAll(kontrolleri.getKategorianimet(kayttaja.getNimimerkki()));
		kategoriaBox.getSelectionModel().select("Yleinen");
		
	    
		kategoriaBoxSuodatus = new ComboBox<>();
		kategoriaBoxSuodatus.setEditable(false);

		kategoriaBoxSuodatus.getItems().addAll(kontrolleri.getKategorianimet(kayttaja.getNimimerkki()));
		kategoriaBoxSuodatus.getItems().add("Kaikki");
		kategoriaBoxSuodatus.getSelectionModel().select("Kaikki");
		
		kategoriaBoxSuodatus.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		        suodata();
		    }
		});
		
		kuukausiValitsin = new ComboBox<>();
		//kuukausiValitsin.setEditable(false);

		kuukausiValitsin.getItems().add("Kaikki");
		kuukausiValitsin.getItems().add("Tammi");
		kuukausiValitsin.getItems().add("Helmi");
		kuukausiValitsin.getItems().add("Maalis");
		kuukausiValitsin.getItems().add("Huhti");
		kuukausiValitsin.getItems().add("Touko");
		kuukausiValitsin.getItems().add("Kesä");
		kuukausiValitsin.getItems().add("Heinä");
		kuukausiValitsin.getItems().add("Elo");
		kuukausiValitsin.getItems().add("Syys");
		kuukausiValitsin.getItems().add("Loka");
		kuukausiValitsin.getItems().add("Marras");
		kuukausiValitsin.getItems().add("Joulu");

		kuukausiValitsin.getSelectionModel().select("Kaikki");

		kuukausiValitsin.setOnAction(new EventHandler<ActionEvent>() {
		  @Override
		  public void handle(ActionEvent event) {
		    suodataAika();
		  }
		});
		
		vuosiValitsin = new ComboBox<>();
		//vuosiValitsin.setEditable(false);
		vuosiValitsin.getItems().add("Kaikki");
		for (int i = LocalDate.now().getYear(); i >= LocalDate.now().getYear() - 5; i--) {
	        vuosiValitsin.getItems().add(Integer.toString(i));
	    }
	    vuosiValitsin.getSelectionModel().select("Kaikki");

	    vuosiValitsin.setOnAction(new EventHandler<ActionEvent>() {
		  @Override
		  public void handle(ActionEvent event) {
		    suodataAika();
		  }
		});
	    
	    kategoriaBoxSuodatus.setMinWidth(110);
	    kuukausiValitsin.setMinWidth(110);
	    vuosiValitsin.setMinWidth(110);
	    
		lisaaButton = new Button("Lisää ostos");
		muokkaaOstostaButton = new Button("Muokkaa ostosta");
		muokkaaKategoriaaButton = new Button("Muokkaa");
		lisaaKayttajaButton = new Button("Uusi käyttäjä");
		kayttajaAsetusButton = new Button("Käyttäjäasetukset");
		uusiKategoriaLabel = new Label("Uusi kategoria");
		uusiKategoriaField = new TextField();
		kategoriaButton = new Button("Lisää kategoria");
		kulutusTrendiButton = new Button("Näytä ennuste");
		kuluDiagrammiButton = new Button("Näytä diagrammi");
		
		lisaaButton.setMinWidth(110);
		muokkaaOstostaButton.setMinWidth(110);
		muokkaaKategoriaaButton.setMinWidth(110);
		lisaaKayttajaButton.setMinWidth(110);
		kayttajaAsetusButton.setMinWidth(110);
		kategoriaButton.setMinWidth(110);
		kulutusTrendiButton.setMinWidth(110);
		kuluDiagrammiButton.setMinWidth(110);
		
		Font buttonFont = Font.font("Arial", FontWeight.BOLD, 12);
		lisaaButton.setFont(buttonFont);
		muokkaaOstostaButton.setFont(buttonFont);
		muokkaaKategoriaaButton.setFont(buttonFont);
		lisaaKayttajaButton.setFont(buttonFont);
		kayttajaAsetusButton.setFont(buttonFont);
		kategoriaButton.setFont(buttonFont);
		kulutusTrendiButton.setFont(buttonFont);
		kuluDiagrammiButton.setFont(buttonFont);
		
		pvmValitsin.setMaxWidth(110);
		
		lisaaButton.setStyle("-fx-background-color: " + accentColorHex+ "; -fx-text-fill: white;");
		muokkaaOstostaButton.setStyle("-fx-background-color: " + accentColorHex + "; -fx-text-fill: white;");
		muokkaaKategoriaaButton.setStyle("-fx-background-color: " + accentColorHex + "; -fx-text-fill: white;");
		lisaaKayttajaButton.setStyle("-fx-background-color: " + accentColorHex + "; -fx-text-fill: white;");
		kayttajaAsetusButton.setStyle("-fx-background-color: " + accentColorHex + "; -fx-text-fill: white;");
		kategoriaButton.setStyle("-fx-background-color: " + accentColorHex + "; -fx-text-fill: white;");
		kulutusTrendiButton.setStyle("-fx-background-color: " + accentColorHex + "; -fx-text-fill: white;");
		kuluDiagrammiButton.setStyle("-fx-background-color: " + accentColorHex + "; -fx-text-fill: white;");
		
		kategoriaBox.setStyle("-fx-background-color: white; -fx-border-color: " + accentColorHex + "; -fx-font: 12px \"Arial\";");
		kategoriaBoxSuodatus.setStyle("-fx-background-color: white; -fx-border-color: " + accentColorHex + "; -fx-font: 12px \"Arial\";");
		kuukausiValitsin.setStyle("-fx-background-color: white; -fx-border-color: " + accentColorHex + "; -fx-font: 12px \"Arial\";");
		vuosiValitsin.setStyle("-fx-background-color: white; -fx-border-color: " + accentColorHex + "; -fx-font: 12px \"Arial\";");
		userProfileSelector.setStyle("-fx-background-color: white; -fx-border-color: " + accentColorHex + "; -fx-font: 12px \"Arial\";");
		
		userProfileSelector.setMaxWidth(110);
		kategoriaBox.setMinWidth(110);
		
		kayttajaValitsinLabel = new Label("Valitse käyttäjä");
        userProfileSelector.getItems().addAll(kontrolleri.getKayttajat());
        userProfileSelector.getSelectionModel().select(kayttajanhallinta.getKirjautunutKayttaja().getKayttajaID()-1);
        userProfileSelector.setOnAction(event -> {
            valitseKayttaja();
        });
        
        
		ostosLabel.setFont(labelFont);
        ostosLabel.setTextFill(accentColor);
        hintaLabel.setFont(labelFont);
        hintaLabel.setTextFill(accentColor);
        paivamaaraLabel.setFont(labelFont);
        paivamaaraLabel.setTextFill(accentColor);
        kategoriaLabel.setFont(labelFont);
        kategoriaLabel.setTextFill(accentColor);
        kuvausLabel.setFont(labelFont);
        kuvausLabel.setTextFill(accentColor);
        kategoriasuodatin.setFont(labelFont);
        kategoriasuodatin.setTextFill(accentColor);
        kuukausisuodatin.setFont(labelFont);
        kuukausisuodatin.setTextFill(accentColor);
        vuosiSuodatin.setFont(labelFont);
        vuosiSuodatin.setTextFill(accentColor);
        kayttajaValitsinLabel.setFont(labelFont);
        kayttajaValitsinLabel.setTextFill(accentColor);
        budjettiLabel.setFont(budjettiFont);
        budjettiLabel.setTextFill(accentColor);
        uusiKategoriaLabel.setFont(labelFont);
        uusiKategoriaLabel.setTextFill(accentColor);
        ostosField.setFont(inputFont);
        hintaField.setFont(inputFont);
        paivamaaraField.setFont(inputFont);
        kuvausField.setFont(inputFont);
        
        ostosField.setMaxWidth(110);
        hintaField.setMaxWidth(110);
        kuvausField.setMaxWidth(110);
        uusiKategoriaField.setMaxWidth(110);
        
        
		grid.add(ostosLabel, 0, 0);
		grid.add(ostosField, 0, 1);
		grid.add(hintaLabel, 1, 0);
		grid.add(hintaField, 1, 1);
		grid.add(paivamaaraLabel, 2, 0);
		grid.add(pvmValitsin, 2, 1);
		grid.add(kategoriaLabel, 3, 0);
		grid.add(kategoriaBox, 3, 1);
		grid.add(kuvausLabel, 4, 0);
		grid.add(kuvausField, 4, 1);
		grid.add(budjettiLabel, 6, 9);
		grid.add(lisaaButton, 0, 2);
		
	
		grid.add(kayttajaValitsinLabel, 6, 0);
		grid.add(userProfileSelector, 6, 1);
		grid.add(lisaaKayttajaButton, 6, 2);
		grid.add(kayttajaAsetusButton, 6, 3);
		
		grid.add(uusiKategoriaLabel, 7, 0);
		grid.add(uusiKategoriaField, 7, 1);
		grid.add(kategoriaButton, 7, 2);
		grid.add(muokkaaKategoriaaButton, 7, 3);
		
		GridPane.setColumnSpan(kulutlista, 5);
		GridPane.setColumnSpan(budjettiLabel, 2);

		grid.add(kategoriaBoxSuodatus, 2, 8);
		grid.add(kuukausiValitsin, 3, 8);
		grid.add(vuosiValitsin, 4, 8);
	
		grid.add(kategoriasuodatin, 2, 7);
		grid.add(kuukausisuodatin, 3, 7);
		grid.add(vuosiSuodatin, 4, 7);
		
		grid.add(kulutlista, 0, 9);
		grid.add(muokkaaOstostaButton, 0, 10);
		grid.add(kuluDiagrammiButton, 1, 10);
		grid.add(kulutusTrendiButton, 2, 10);
		
		GridPane.setHalignment(budjettiLabel, HPos.CENTER);
		/* GridPane.setHalignment(kategoriasuodatin, HPos.RIGHT);
		GridPane.setHalignment(kuukausisuodatin, HPos.RIGHT);
		GridPane.setHalignment(vuosiSuodatin, HPos.RIGHT);
		GridPane.setHalignment(kategoriaBoxSuodatus, HPos.RIGHT);
		GridPane.setHalignment(vuosiValitsin, HPos.RIGHT);
		GridPane.setHalignment(kuukausiValitsin, HPos.RIGHT); */
		
		
		pvmValitsin.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		        setPaivamaara(pvmValitsin.getValue());
		    }
		});
		
		lisaaButton.setOnAction((event) -> {
			lisaaKulu();
		});
		
		kategoriaButton.setOnAction((event) -> {
			lisaaUusiKategoria();
		});
		lisaaKayttajaButton.setOnAction((event) -> {
			luoKayttajaIkkuna();
		});
		kayttajaAsetusButton.setOnAction((event) -> {
			luoAsetusIkkuna();
		});
		kulutusTrendiButton.setOnAction((event) -> {
			luoKuluGraph();
		});
		
		kuluDiagrammiButton.setOnAction((event) -> {
			luoKuluDiagrammi();
		});
		
		muokkaaOstostaButton.setOnAction((event) ->{
			avaaMuokkausnakymaKulu();
		});
		
		muokkaaKategoriaaButton.setOnAction((event) ->{
			avaaMuokkausnakymaKategoria();
		});
		
		hbox.getChildren().add(grid);
		
		return hbox;
	}
	
	public void lisaaKulu() {
		Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		String kategorianNimi = kategoriaBox.getSelectionModel().getSelectedItem();
		
		try {
			String nimi = ostosField.getText();
			double hinta = Double.parseDouble(hintaField.getText());
			Kategoria kategoria = kontrolleri.getKategoria(kategorianNimi, kayttaja.getNimimerkki());
			String kuvaus = kuvausField.getText();
			
			if(kayttaja.getMaksimibudjetti() >= hinta) {
				kontrolleri.lisaaKulu(nimi, hinta, paivamaara, kategoria, kayttaja, kuvaus);
			} else {
				System.out.println("Kulun summa on liian suuri budjettiin nähden.");
				JOptionPane.showConfirmDialog(null, "Kulun summa on liian suuri budjettiisi.", "Kulun summassa virhe", JOptionPane.ERROR_MESSAGE);
			}
			budjettiLabel.setText("Budjettia jäljellä:\n" + String.format("%.2f",budjettiaJaljella - hinta) + " €");
			
		} catch (NumberFormatException nfe) {
			System.out.println("Numeroarvojen sijasta yritettiin syöttää muuta...");
			JOptionPane.showConfirmDialog(null, "Syötä numeroarvot niitä pyydettäessä.", "Syöttövirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			System.out.println("Joku vikana arvoja syötettäessä...");
			JOptionPane.showConfirmDialog(null, "Syötä oikeantyyppiset arvot.", "Syöttövirhe", JOptionPane.ERROR_MESSAGE);
		}
		
		kulut = kontrolleri.getKulut(kayttaja.getKayttajaID());
		setKulut(kulut);
		
		ostosField.clear();
		hintaField.clear();
		kategoriaBox.getSelectionModel().select("Yleinen");
		kuvausField.clear();
	}
	
	public void lisaaUusiKategoria() {
		Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		kontrolleri.lisaaKategoria(uusiKategoriaField.getText(), kayttaja.getNimimerkki());
		kategoriaBox.getItems().add(uusiKategoriaField.getText());
		kategoriaBoxSuodatus.getItems().add(uusiKategoriaField.getText());
		uusiKategoriaField.clear();
	}
	
	public void valitseKayttaja() {
		int selectedUser = userProfileSelector.getSelectionModel().getSelectedIndex() +1;
        kayttajanhallinta.kirjoitaKayttajaID(selectedUser);
        kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(selectedUser));
        kulut = kontrolleri.getKulut(selectedUser);
		setKulut(kulut);
		kategoriaBox.getItems().clear();
		kategoriaBox.getItems().addAll(kontrolleri.getKategorianimet(kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki()));
		kategoriaBox.getSelectionModel().select("Yleinen");
		kategoriaBoxSuodatus.getItems().clear();
		kategoriaBoxSuodatus.getItems().addAll(kontrolleri.getKategorianimet(kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki()));
		kategoriaBoxSuodatus.getItems().add("Kaikki");
		kategoriaBoxSuodatus.getSelectionModel().select("Kaikki");
		budjettiaJaljellaLaskuri();
        System.out.println("Logging in user: " + selectedUser);
	}
	
	public double budjettiaJaljellaLaskuri() {
		budjettiaJaljella = kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti();
		for(Kulu kulu: kulut) {
			if(kulu.getPaivamaara().getMonthValue() == LocalDate.now().getMonthValue() && kulu.getPaivamaara().getYear() == LocalDate.now().getYear()) {
			budjettiaJaljella -= kulu.getSumma();
			}
		};
		budjettiLabel.setText("Budjettia jäljellä:\n" + String.format("%.2f",budjettiaJaljella ) + " €");
		
		return budjettiaJaljella;
	}
	
	public void suodata() {
		ObservableList<Kulu> alkuperainenList = FXCollections.observableList(kulut);
		String valittuKategoria = kategoriaBoxSuodatus.getSelectionModel().getSelectedItem();
        if (valittuKategoria.equals("Kaikki")) {
            kulutlista.setItems(alkuperainenList);
        } else {
            List<Kulu> suodatetutKulut = kulut.stream()
                .filter(kulu -> kulu.getKategoria().getNimi().equals(valittuKategoria))
                .collect(Collectors.toList());
            ObservableList<Kulu> suodatetutKulutList = FXCollections.observableList(suodatetutKulut);
            kulutlista.setItems(suodatetutKulutList);
        }
	}
	
	public void avaaMuokkausnakymaKulu() {

	    StackPane root = new StackPane();
	    root.setStyle("-fx-background-color: #DAE3E5;");
	    Scene scene = new Scene(root, 400, 400);
	    Stage stage = new Stage();

	    Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();

	    Button tallennaButton = new Button("Tallenna muutos");
	    tallennaButton.setStyle("-fx-background-color: #507DBC; -fx-text-fill: white; -fx-font-weight: bold;");
	    Button poistaButton = new Button("Poista kulu");
	    poistaButton.setStyle("-fx-background-color: #507DBC; -fx-text-fill: white; -fx-font-weight: bold;");

	    Label uusiNimiLabel = new Label("Anna uusi nimi");
	    Label uusiHintaLabel = new Label("Anna uusi hinta");
	    Label uusiKuvausLabel = new Label("Anna uusi kuvaus");

	    TextField uusiNimiField = new TextField();
	    TextField uusiHintaField = new TextField();
	    TextField uusiKuvausField = new TextField();

	    Label uusiKategoriaLabel = new Label("Valitse uusi kategoria");
	    ComboBox<String> muokkausBox = new ComboBox<>();
	    muokkausBox.setEditable(false);
	    List<String> kaikkiKategoriat = kontrolleri.getKategorianimet(kayttaja.getNimimerkki());
	    muokkausBox.getItems().addAll(kaikkiKategoriat);
	    Button tallennaKategoriaButton = new Button("Tallenna uusi kategoria");
	    tallennaKategoriaButton.setStyle("-fx-background-color: #507DBC; -fx-text-fill: white; -fx-font-weight: bold;");

	    VBox vbox = new VBox(10);
	    vbox.setStyle("-fx-padding: 10px;");
	    vbox.getChildren().addAll(uusiNimiLabel, uusiNimiField, uusiHintaLabel, uusiHintaField, uusiKuvausLabel, uusiKuvausField, tallennaButton, uusiKategoriaLabel, muokkausBox, tallennaKategoriaButton, poistaButton);
	    root.getChildren().add(vbox);

	    tallennaButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		Kulu kulu = kulutlista.getSelectionModel().getSelectedItem();
	    		int id = kulu.getKuluID();
	    		
	    		try {
	    			String nimi = uusiNimiField.getText();
		    		double hinta = Double.parseDouble(uusiHintaField.getText());
			        String kuvaus = uusiKuvausField.getText();
			        kayttaja.setMaksimibudjetti(kayttaja.getMaksimibudjetti() + kontrolleri.getKulu(id).getSumma());
			        kontrolleri.muokkaaKulua(id, hinta, nimi, kuvaus);
		    		kayttaja.setMaksimibudjetti(kayttaja.getMaksimibudjetti() - hinta);
	    		} catch (NumberFormatException nfe) {
	    			System.out.println("Numeroarvojen sijasta yritettiin syöttää muuta...");
	    			JOptionPane.showConfirmDialog(null, "Syötä numeroarvot niitä pyydettäessä.", "Syöttövirhe", JOptionPane.ERROR_MESSAGE);
	    		} catch (Exception e) {
	    			System.out.println("Joku vikana arvoja syötettäessä...");
	    			JOptionPane.showConfirmDialog(null, "Syötä oikeantyyppiset arvot.", "Syöttövirhe", JOptionPane.ERROR_MESSAGE);
	    		}
	    		
		        setKulut(kulut);
		        budjettiLabel.setText("Budjetti:\n" + String.format("%.2f",kayttaja.getMaksimibudjetti()) + " €");
		        stage.close();
	      }
	    });
	    
	    tallennaKategoriaButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		Kulu kulu = kulutlista.getSelectionModel().getSelectedItem();
	    		int id = kulu.getKuluID();
	    		String kategoriaNimi = muokkausBox.getSelectionModel().getSelectedItem();
	    		Kategoria uusiKategoria = kontrolleri.getKategoria(kategoriaNimi, kayttaja.getNimimerkki());
	    		kontrolleri.muutaKulunKategoria(id, uusiKategoria);
		        setKulut(kulut);
		        stage.close();
	      }
	    });
	    
	    poistaButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
		    public void handle(ActionEvent event) {
	    		Kulu kulu = kulutlista.getSelectionModel().getSelectedItem();
	    		
	    		int id = kulu.getKuluID();
	    		int valinta = JOptionPane.showConfirmDialog(null, "Haluatko varmasti poistaa kulun?", "Mieti vielä kerran...",JOptionPane.OK_CANCEL_OPTION);
	    		if(valinta == 0) {
	    			kayttaja.setMaksimibudjetti(kayttaja.getMaksimibudjetti() + kontrolleri.getKulu(id).getSumma());
		    		kontrolleri.poistaKulu(id);
		    		kulut = kontrolleri.getKulut(kayttaja.getKayttajaID());
		    		setKulut(kulut);
		    		kategoriaBox.getItems().clear();
		    		kategoriaBox.getItems().addAll(kontrolleri.getKategorianimet(kayttaja.getNimimerkki()));
		    		budjettiLabel.setText("Budjetti:\n" + String.format("%.2f",kayttaja.getMaksimibudjetti()) + " €");
		    		stage.close();
	    		}
	    		stage.close();
	    	}
	    });

	    stage.setScene(scene);
	    stage.setTitle("Muokkaa kulua");
	    stage.show();
	}
	
	public void avaaMuokkausnakymaKategoria() {
	    StackPane root = new StackPane();
	    root.setStyle("-fx-background-color: #DAE3E5;");
	    Scene scene = new Scene(root, 400, 400);
	    Stage stage = new Stage();

	    Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();

	    Button tallennaButton = new Button("Tallenna muutos");
	    tallennaButton.setStyle("-fx-background-color: #507DBC; -fx-text-fill: white; -fx-font-weight: bold;");
	    Button poistaButton = new Button("Poista kategoria");
	    poistaButton.setStyle("-fx-background-color: #507DBC; -fx-text-fill: white; -fx-font-weight: bold;");

	    ComboBox<String> muokkausBox = new ComboBox<>();
	    muokkausBox.setEditable(false);
	    List<String> kaikkiKategoriat = kontrolleri.getKategorianimet(kayttaja.getNimimerkki());
	    List<String> muokattavatKategoriat = new ArrayList<String>();
	    for (String nimi : kaikkiKategoriat) {
	        if(!(nimi.equals("Yleinen"))) {
	            muokattavatKategoriat.add(nimi);
	        }
	    }
	    System.out.println(muokattavatKategoriat);
	    muokkausBox.getItems().addAll(muokattavatKategoriat);

	    Label infoteksti = new Label("Valitse valikosta muokattava tai poistettava kategoria");
	    infoteksti.setStyle("-fx-font-family: Arial;");
	    Label uusiNimiLabel = new Label("Anna uusi nimi");
	    uusiNimiLabel.setStyle("-fx-font-family: Arial;");

	    TextField uusiNimiField = new TextField();

	    VBox vbox = new VBox();
	    vbox.setSpacing(10);
	    vbox.setPadding(new Insets(10, 10, 10, 10));
	    vbox.getChildren().addAll(infoteksti, muokkausBox, uusiNimiLabel, uusiNimiField, tallennaButton, poistaButton);
	    root.getChildren().add(vbox);
	    
	    tallennaButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		String kategoriaNimi = muokkausBox.getSelectionModel().getSelectedItem();
	    		Kategoria muokattavaKategoria = kontrolleri.getKategoria(kategoriaNimi, kayttaja.getNimimerkki());
	    		
	    		String nimi = uusiNimiField.getText();
	    		
	    		kontrolleri.muokkaaKategoriaa(muokattavaKategoria.getKategoriaID(), nimi);
		        uusiNimiField.clear();
		        kategoriaBox.getItems().clear();
				kategoriaBox.getItems().addAll(kontrolleri.getKategorianimet(kayttaja.getNimimerkki()));
				kategoriaBox.getSelectionModel().select("Yleinen");
				setKulut(kulut);
		        stage.close();
	      }
	    });
	    
	    poistaButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
		    public void handle(ActionEvent event) {
	    		String kategoriaNimi = muokkausBox.getSelectionModel().getSelectedItem();
	    		Kategoria poistettavaKategoria = kontrolleri.getKategoria(kategoriaNimi, kayttaja.getNimimerkki());
	    		
	    		int valinta = JOptionPane.showConfirmDialog(null, "Haluatko varmasti poistaa kategorian? Poistetun kategorian kulut siirtyvät yleiseen kategoriaan", "Mieti vielä kerran...",JOptionPane.OK_CANCEL_OPTION);
	    		if(valinta == 0) {
	    			kontrolleri.poistaKategoria(poistettavaKategoria.getKategoriaID(), kayttaja);
	    			kategoriaBox.getItems().remove(kategoriaNimi);
	    			kategoriaBox.getSelectionModel().select("Yleinen");
	    			kategoriaBoxSuodatus.getItems().remove(kategoriaNimi);
	    			setKulut(kulut);
		    		stage.close();
	    		}
	    		stage.close();
	    	}
	    });
	    
	    stage.setScene(scene);
	    stage.setTitle("Muokkaa kategorioita");
	    stage.show();
	}
	
	
	
	public void setKulut(List<Kulu> kulut) {
		ObservableList<Kulu> observableKulut = FXCollections.observableList(kulut);
		this.kulutlista.setItems(observableKulut);
	}
	
	public void luoKayttajaIkkuna() {
	    StackPane root = new StackPane();
	    root.setStyle("-fx-background-color: #DAE3E5;"); // set background color
	    Scene scene = new Scene(root, 400, 400);
	    Stage stage = new Stage();
	    Label label = new Label("Luo uusi käyttäjätunnus:");
	    TextField textField = new TextField();
	    textField.setStyle("-fx-font-family: Arial; -fx-padding: 10px;"); // set font and padding
	    Label label2 = new Label("Aseta kuukausittainen budjettisi:");
	    TextField textField2 = new TextField();
	    textField2.setStyle("-fx-font-family: Arial; -fx-padding: 10px;"); // set font and padding
	    Button button = new Button("Luo käyttäjä");
	    button.setStyle("-fx-background-color: #507DBC; -fx-text-fill: white; -fx-padding: 10px; -fx-font-weight: bold;"); // set button styles

	    VBox vbox = new VBox();
	    vbox.setSpacing(10);
	    vbox.setPadding(new Insets(20)); // add margin to VBox
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
	                textField.clear();
	                textField2.clear();
	                userProfileSelector.getItems().add(username);
	                stage.close();
	            }
	        }
	    });

	    stage.setScene(scene);
	    stage.setTitle("Luo käyttäjä");
	    stage.show();
	}
	
	public void luoAsetusIkkuna() {
	    StackPane root = new StackPane();
	    root.setStyle("-fx-background-color: #DAE3E5;"); 
	    Scene scene = new Scene(root, 400, 400);
	    Stage stage = new Stage();
	    Label label = new Label("Käyttäjän " + kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki() + " kuukausittainen budjetti on: " + String.format("%.2f",kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti()) );
	    label.setStyle("-fx-font-family: Arial; "); 
	    Label label2 = new Label("Aseta kuukausittainen budjettisi:");
	    label2.setStyle("-fx-font-family: Arial;");
	    TextField textField = new TextField();
	    textField.setStyle("-fx-font-family: Arial;");
	    Button tallennaButton = new Button("Tallenna");
	    tallennaButton.setStyle("-fx-background-color: #507DBC; -fx-text-fill: white; -fx-font-weight: bold;"); 
	    Button poistaButton = new Button("Poista kaikki lisäämäsi kulut ja kategoriat");
	    poistaButton.setStyle("-fx-background-color: #507DBC; -fx-text-fill: white; -fx-font-weight: bold;"); 

	    VBox vbox = new VBox();
	    vbox.setSpacing(10);
	    vbox.setPadding(new Insets(20)); 
	    vbox.getChildren().addAll(label, label2, textField, tallennaButton, poistaButton);
	    root.getChildren().add(vbox);

	    tallennaButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            double budjetti = Double.parseDouble(textField.getText());
	            kontrolleri.paivitaBudjetti(kayttajanhallinta.getKirjautunutKayttaja().getKayttajaID(), budjetti);
	            kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(kayttajanhallinta.lueKayttajaID()));
	            label.setText("Käyttäjän " + kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki() + " kuukausittainen budjetti on: " + kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti());
	            textField.clear();
	            budjettiLabel.setText("Budjetti:\n" + String.format("%.2f",kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti()) + " €");
	        }
	    });
	    
	    poistaButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	      public void handle(ActionEvent event) {
	        kontrolleri.poistaKayttajanTiedot(kayttajanhallinta.getKirjautunutKayttaja().getKayttajaID());
	        valitseKayttaja();
	      }
	    });

	      stage.setScene(scene);
	      stage.setTitle("Käyttäjäasetukset");
	      stage.show();	
	}
	
	private void setPaivamaara(LocalDate valittupaiva) {
		paivamaara = valittupaiva;
	}

	public void luoKuluGraph() {
		
		LineChart<String, Number> lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
		lineChart.setTitle("Kulutusennuste");

		// Initialize a variable to keep track of the total expenditure
		int kulutSumma = 0;
		int lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).getDayOfMonth();
		// Create a series for the chart data
		XYChart.Series<String, Number> series = new XYChart.Series<>();

		// Get a list of expenditures for the logged in user
		//List<Kulu> data = kontrolleri.getKulut(kayttajanhallinta.getKirjautunutKayttaja().getKayttajaID());

		
		int currentMonth = LocalDate.now().getMonthValue();
		int selectedMonth = kuukausiValitsin.getSelectionModel().getSelectedIndex();
		int selectedYearIndex = vuosiValitsin.getSelectionModel().getSelectedIndex();
		
		if(selectedMonth == 0 && selectedYearIndex == 0) {
			series.setName("Kumuloituneet kulut: " + LocalDate.now().getMonth().getValue() + "." + Integer.toString((LocalDate.now().getYear())));
		}
		else if(selectedMonth == 0 && selectedYearIndex != 0) {
			series.setName("Kumuloituneet kulut: " + LocalDate.now().getMonth().getValue() + "." + Integer.toString(LocalDate.now().getYear() - selectedYearIndex + 1));
		}
		else {
			series.setName("Kumuloituneet kulut: " + kulut.get(0).getPaivamaara().getMonth().getValue() + "." + Integer.toString(kulut.get(0).getPaivamaara().getYear()));
		}
		// Sort the list of expenditures based on their date
		Collections.sort(kulut, new Comparator<Kulu>() {
		  public int compare(Kulu k1, Kulu k2) {
		    return k1.getPaivamaara().compareTo(k2.getPaivamaara());
		  }
		});

		// Initialize two arrays to store the x and y values of the chart data
		double[] xValues = new double[kulut.size()];
		double[] yValues = new double[kulut.size()];

		// Initialize a variable to keep track of the number of data points
		int count = 0;

		// Loop through the list of expenditures
		for (int i = 0; i < kulut.size(); i++) {
		  // Get the date object of the current expenditure
		  LocalDate date = kulut.get(i).getPaivamaara();

		  // If the date is in the current month, add the data to the x and y arrays
		  if (selectedMonth == 0 && selectedYearIndex == 0) {
		  if (date.getMonthValue() == currentMonth) {
		    xValues[count] = date.getDayOfMonth();
		    kulutSumma += kulut.get(i).getSumma();
		    yValues[count] = kulutSumma;
		    count++;
		  }
		  }
		  else if(selectedMonth == 0 && selectedYearIndex != 0) {
			  int selectedYear = LocalDate.now().getYear() - selectedYearIndex + 1;
			  if (date.getMonthValue() == currentMonth && date.getYear() == selectedYear) {
				    xValues[count] = date.getDayOfMonth();
				    kulutSumma += kulut.get(i).getSumma();
				    yValues[count] = kulutSumma;
				    count++;
				  }
		  }
		  else {
			  	xValues[count] = date.getDayOfMonth();
			    kulutSumma += kulut.get(i).getSumma();
			    yValues[count] = kulutSumma;
			    count++;
		  }
		}

		// Create a new instance of SimpleRegression class which is a class in Apache Commons Math library 
		// that implements simple linear regression.
		SimpleRegression regression = new SimpleRegression();

		// For each data point, add the x and y values to the regression model.
		for (int i = 0; i < xValues.length; i++) {
		    regression.addData(xValues[i], yValues[i]);
		}

		// This code block generates a series of data points to be displayed on the x-axis and y-axis of the line chart.
		// The purpose of this code is to display the expenditures on every day of the current month, not just on the days where there are expenditures.
		// It does this by looping through every day of the current month and checking if there is a corresponding expenditure for that day.
		// If there is an expenditure for that day, it adds that day's date and the cumulative expenditure up to that day to the series.
		// If there is no expenditure for that day, it adds that day's date and a value of 0 to the series.
		for (int i = 1; i <= lastDayOfMonth; i++) {
		    boolean found = false;
		    // loop through all the expenditures so far in the current month
		    for (int j = 0; j < count; j++) {
		        // if there is an expenditure on the current day of the loop
		        if ((int) xValues[j] == i) {
		            // format the day of the month as a two digit string (e.g. "01", "02", ..., "31")
		            String dayOfMonth = String.format("%02d.", (int) xValues[j]);
		            // add the day of the month and the cumulative expenditure up to that day to the series
		            series.getData().add(new XYChart.Data<>(dayOfMonth, yValues[j]));
		            found = true;
		            break;
		        }
		    }
		    // if there is no expenditure on the current day of the loop
		    if (!found) {
		        // format the day of the month as a two digit string (e.g. "01", "02", ..., "31")
		        String dayOfMonth = String.format("%02d.", i);
		        // add the day of the month and a value of 0 to the series
		        series.getData().add(new XYChart.Data<>(dayOfMonth, 0));
		    }
		    
		}


		// Add the series to the line chart.
		lineChart.getData().add(series);

		// Calculate the slope and intercept of the regression line.
		double slope = regression.getSlope();
		double intercept = regression.getIntercept();

		// Create a new series for the extrapolated data.
		XYChart.Series<String, Number> extrapolationSeries = new XYChart.Series<>();
		extrapolationSeries.setName("Ennuste loppukuun kuluista");

		// Add extrapolated data points to the series, using the slope and intercept of the regression line so that the trend is clearly visible in to the future.
		for (int i = 1; i <= lastDayOfMonth + 3; i++) {
		    String dayOfMonth = String.format("%02d.", i);
		    extrapolationSeries.getData().add(new XYChart.Data<>(dayOfMonth, slope * i + intercept));
		}

		// Add the extrapolated series to the line chart.
		lineChart.getData().add(extrapolationSeries);
		
		// New series is created to display the max budget line
		double maxBudget = kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti();
		XYChart.Series<String, Number> maxBudgetSeries = new XYChart.Series<>();
		maxBudgetSeries.setName("Max budjetti");
		maxBudgetSeries.getData().add(new XYChart.Data<>(String.format("%02d.", 1), maxBudget));
		maxBudgetSeries.getData().add(new XYChart.Data<>(String.format("%02d.", lastDayOfMonth), maxBudget));
		lineChart.getData().add(maxBudgetSeries);
		
		lineChart.setStyle("-fx-background-color: #DAE3E5;");

		Label info = new Label("Graafeja ei piirry jos ei ole olemassa vähintäänkin kahta datapistettä erillisiltä päiviltä. Ennuste toimii parhaiten jos dataa on usealta päivältä.");
		info.setAlignment(Pos.CENTER);
	    VBox vbox = new VBox();
	    vbox.getChildren().addAll(lineChart,info);
	    vbox.setSpacing(10); 
	    vbox.setAlignment(Pos.CENTER); 
	    vbox.setPrefHeight(600); 
	    lineChart.setPrefHeight(550); 
		Scene scene = new Scene(vbox, 800, 600);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	public void luoKuluDiagrammi() {
	    Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
	    //List<Kulu> kulut = kontrolleri.getKulut(kayttaja.getKayttajaID());
	    List<Kategoria> kategoriat = kontrolleri.getKategoriat(kayttaja.getNimimerkki());
	    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

	    Map<Kategoria, Double> kuluSums = new HashMap<>();
	    for (Kulu kulu : kulut) {
	        Kategoria kategoria = kulu.getKategoria();
	        double sum = kuluSums.getOrDefault(kategoria, 0.0);
	        kuluSums.put(kategoria, sum + kulu.getSumma());
	    }

	    for (Kategoria kategoria : kategoriat) {
	        double sum = kuluSums.getOrDefault(kategoria, 0.0);
	        PieChart.Data data = new PieChart.Data(kategoria.getNimi(), sum);
	        pieChartData.add(data);
	    }

	    pieChartData.forEach(data -> data.setName(data.getName() + " " + data.getPieValue() + " €"));
	    

	    PieChart pieChart = new PieChart(pieChartData);
	    pieChart.setTitle("Kulut kategorioittain");
	    pieChart.lookup(".chart-title").setStyle("-fx-padding: 10;");
	    pieChart.setLabelLineLength(50);
	    pieChart.setLabelsVisible(true);
	    pieChart.setLegendVisible(false);

	    Scene scene = new Scene(new Group(pieChart), 500, 400);
	    
	    pieChart.setStyle("-fx-background-color: #DAE3E5;");


	    Stage stage = new Stage();
	    stage.setTitle("Kuludiagrammi");
	    stage.setScene(scene);
	    stage.show();
	}
	
	private void suodataAika() {
	    int selectedMonth = kuukausiValitsin.getSelectionModel().getSelectedIndex();
	    int selectedYearIndex = vuosiValitsin.getSelectionModel().getSelectedIndex();
	    int selectedYear = LocalDate.now().getYear() - selectedYearIndex + 1;
	    List<Kulu> suodatetutKulut = new ArrayList<>();
	    kulut = kontrolleri.getKulut(kayttajanhallinta.getKirjautunutKayttaja().getKayttajaID());
	    if (selectedMonth == 0 && selectedYearIndex == 0){
	        setKulut(kulut);
	      }
	    else {
	    	  for (Kulu kulu : kulut) {
	    		  if ((kulu.getPaivamaara().getMonthValue() == selectedMonth || selectedMonth == 0 )&& (kulu.getPaivamaara().getYear() == selectedYear || selectedYearIndex == 0) ) {
	    			  suodatetutKulut.add(kulu);
	        }
	    }
	    	setKulut(suodatetutKulut);
	  	    kulut = suodatetutKulut;
	      }
	    
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}