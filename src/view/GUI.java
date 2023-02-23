package view;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import controller.IKontrolleri;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kayttajanHallinta.KayttajanHallinta;
import model.Budjettilaskuri;
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
	Label kulutLabel;
	Label kayttajaValitsinLabel;
	List<Kulu> kulut = new ArrayList<>();
	ListView<Kulu> kulutlista = new ListView<Kulu>();
	ComboBox<String> userProfileSelector = new ComboBox<>();
	KayttajanHallinta kayttajanhallinta = KayttajanHallinta.getInstance();
	List<Kulu> suodatetutKulut = new ArrayList<>();
	DatePicker pvmValitsin = new DatePicker();
	LocalDate paivamaara;
	
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
		kategoriaBox.getItems().addAll(kontrolleri.getKategorianimet(kayttaja.getNimimerkki()));
	    
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
		
		lisaaButton = new Button("Lisää ostos");
		muokkaaOstostaButton = new Button("Muokkaa ostosta");
		lisaaKayttajaButton = new Button("Lisää uusi käyttäjä");
		kayttajaAsetusButton = new Button("Käyttäjäasetukset");
		uusiKategoriaLabel = new Label("Uusi kategoria");
		uusiKategoriaField = new TextField();
		kategoriaButton = new Button("Lisää kategoria");
		kulutusTrendiButton = new Button("Näytä kulutus graafi");
		kuluDiagrammiButton = new Button("Näytä kuludiagrammi");
		
		kayttajaValitsinLabel = new Label("Valitse käyttäjä");
        userProfileSelector.getItems().addAll(kontrolleri.getKayttajat());
        userProfileSelector.getSelectionModel().select(kayttajanhallinta.getKirjautunutKayttaja().getKayttajaID()-1);
        userProfileSelector.setOnAction(event -> {
            valitseKayttaja();
        });
        
        budjettiLabel = new Label("Budjetti:\n" + String.format("%.2f",kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti()) + " €");
        
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
		grid.add(kayttajaValitsinLabel, 6, 0);
		grid.add(userProfileSelector, 6, 1);
		grid.add(budjettiLabel, 6, 3);
		grid.add(lisaaKayttajaButton, 7, 1);
		grid.add(kayttajaAsetusButton, 7, 2);
		grid.add(lisaaButton, 0, 2);
		grid.add(kulutusTrendiButton, 7, 4);
		
		grid.add(uusiKategoriaLabel, 0, 3);
		grid.add(uusiKategoriaField, 0, 4);
		grid.add(kategoriaButton, 0, 5);
		GridPane.setColumnSpan(kulutlista, 5);
		grid.add(kategoriaBoxSuodatus, 4, 7);
		grid.add(kulutlista, 0, 8);
		grid.add(kuluDiagrammiButton, 0, 9);
		grid.add(muokkaaOstostaButton, 1, 9);
		
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
			avaaMuokkausnakyma();
		});
		
		hbox.getChildren().add(grid);
		
		return hbox;
	}
	
	public void lisaaKulu() {
		String nimi = ostosField.getText();
		double hinta = Double.parseDouble(hintaField.getText());
		Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		List<String> kategorianimet = kontrolleri.getKategorianimet(kayttaja.getNimimerkki());
	
		String kategorianNimi = kategoriaBox.getSelectionModel().getSelectedItem();
		Kategoria kategoria = kontrolleri.getKategoria(kategorianNimi, kayttaja.getNimimerkki());
		String kuvaus = kuvausField.getText();
		kontrolleri.lisaaKulu(nimi, hinta, paivamaara, kategoria, kayttaja, kuvaus);
		//JOptionPane.showConfirmDialog(null, "Kulun summa on liian suuri budjettiisi.", "Kulun summassa virhe",JOptionPane.OK_OPTION);
		kulut = kontrolleri.getKulut(kayttajanhallinta.getKirjautunutKayttaja().getKayttajaID());
		setKulut(kulut);
		budjettiLabel.setText("Budjetti:\n" + String.format("%.2f",kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti()) + " €");
		
		boolean onkoListalla = false;
		if(kategorianimet.contains(kategorianNimi)) {
			onkoListalla = true;
		}
		if(onkoListalla == false) {
			kategoriaBox.getItems().add(kategorianNimi);
		}
		System.out.println(onkoListalla);
		
		ostosField.clear();
		hintaField.clear();
		kategoriaBox.getSelectionModel().clearSelection();
		kuvausField.clear();
	}
	
	public void lisaaUusiKategoria() {
		Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		kontrolleri.lisaaKategoria(uusiKategoriaField.getText(), kayttaja.getNimimerkki());
		kategoriaBox.getItems().add(uusiKategoriaField.getText());
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
        System.out.println("Logging in user: " + selectedUser);
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
	
	public void avaaMuokkausnakyma() {
		
		StackPane root = new StackPane();
	    Scene scene = new Scene(root, 400, 400);
	    Stage stage = new Stage();
	    
	    Button tallennaButton = new Button("Tallenna muutos");
	    Button poistaButton = new Button("Poista kulu");
	    
	    Label uusiNimiLabel = new Label("Anna uusi nimi");
		Label uusiHintaLabel = new Label("Anna uusi hinta");
		Label uusiKuvausLabel = new Label("Anna uusi kuvaus");
		
		TextField uusiNimiField = new TextField();
		TextField uusiHintaField = new TextField();
		TextField uusiKuvausField = new TextField();
		
	    VBox vbox = new VBox();
	    vbox.getChildren().addAll(uusiNimiLabel, uusiNimiField, uusiHintaLabel, uusiHintaField, uusiKuvausLabel, uusiKuvausField, tallennaButton, poistaButton);
	    root.getChildren().add(vbox);

	    tallennaButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		Kulu kulu = kulutlista.getSelectionModel().getSelectedItem();
	    		int id = kulu.getKuluID();
	    		String nimi = uusiNimiField.getText();
	    		double hinta = Double.parseDouble(uusiHintaField.getText());
		        String kuvaus = uusiKuvausField.getText();
	    		
	    		kontrolleri.muokkaaKulua(id, hinta, nimi, kuvaus);
		        uusiNimiField.clear();
		        uusiHintaField.clear();
		        uusiKuvausField.clear();
		        setKulut(kulut);
		        stage.close();
	      }
	    });
	    
	    poistaButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
		    public void handle(ActionEvent event) {
	    		Kulu kulu = kulutlista.getSelectionModel().getSelectedItem();
	    		Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
	    		int id = kulu.getKuluID();
	    		int valinta = JOptionPane.showConfirmDialog(null, "Haluatko varmasti poistaa kulun?", "Mieti vielä kerran...",JOptionPane.OK_CANCEL_OPTION);
	    		if(valinta == 0) {
	    			kayttajanhallinta.getKirjautunutKayttaja().setMaksimibudjetti(kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti() + kontrolleri.getKulu(id).getSumma());
		    		kontrolleri.poistaKulu(id);
		    		kulut = kontrolleri.getKulut(kayttajanhallinta.getKirjautunutKayttaja().getKayttajaID());
		    		setKulut(kulut);
		    		kategoriaBox.getItems().clear();
		    		budjettiLabel.setText("Budjetti:\n" + String.format("%.2f",kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti()) + " €");
		    		kategoriaBox.getItems().addAll(kontrolleri.getKategorianimet(kayttaja.getNimimerkki()));
		    		stage.close();
	    		}
	    		stage.close();
	    	}
	    });

	      stage.setScene(scene);
	      stage.show();
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
	          stage.close();
	        }
	      }
	    });

	      stage.setScene(scene);
	      stage.show();
	}
	
	public void luoAsetusIkkuna() {
		StackPane root = new StackPane();
	    Scene scene = new Scene(root, 400, 400);
	    Stage stage = new Stage();
	    Label label = new Label("Käyttäjän " + kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki() + " kuukausittainen budjetti on: " + String.format("%.2f",kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti()) );
	    Label label2 = new Label("Aseta kuukausittainen budjettisi:");
	    TextField textField = new TextField();
	    Button button = new Button("Tallenna");

	    VBox vbox = new VBox();
	    vbox.getChildren().addAll(label, label2, textField, button);
	    root.getChildren().add(vbox);

	    button.setOnAction(new EventHandler<ActionEvent>() {
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

	      stage.setScene(scene);
	      stage.show();	
	}
	
	private void setPaivamaara(LocalDate valittupaiva) {
		paivamaara = valittupaiva;
	}

	public void luoKuluGraph() {
		
		LineChart<String, Number> lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
		lineChart.setTitle("Kulutus trendi");

		// Initialize a variable to keep track of the total expenditure
		int kulutSumma = 0;
		int lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).getDayOfMonth();
		// Create a series for the chart data
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName(LocalDate.now().getMonth().toString());

		// Get a list of expenditures for the logged in user
		List<Kulu> data = kontrolleri.getKulut(kayttajanhallinta.getKirjautunutKayttaja().getKayttajaID());

		
		int currentMonth = LocalDate.now().getMonthValue();

		// Sort the list of expenditures based on their date
		Collections.sort(data, new Comparator<Kulu>() {
		  public int compare(Kulu k1, Kulu k2) {
		    return k1.getPaivamaara().compareTo(k2.getPaivamaara());
		  }
		});

		// Initialize two arrays to store the x and y values of the chart data
		double[] xValues = new double[data.size()];
		double[] yValues = new double[data.size()];

		// Initialize a variable to keep track of the number of data points
		int count = 0;

		// Loop through the list of expenditures
		for (int i = 0; i < data .size(); i++) {
		  // Get the date object of the current expenditure
		  LocalDate date = data.get(i).getPaivamaara();

		  // If the date is in the current month, add the data to the x and y arrays
		  if (date.getMonthValue() == currentMonth) {
		    xValues[count] = date.getDayOfMonth();
		    kulutSumma += data.get(i).getSumma();
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
		Scene scene = new Scene(lineChart, 800, 600);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	public void luoKuluDiagrammi() {
		Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
	    List<String> kategoriat = kontrolleri.getKategorianimet(kayttaja.getNimimerkki());
	    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

	    PieChart.Data firstData = new PieChart.Data(kategoriat.get(0), 0);
	    pieChartData.add(firstData);

	    for (int i = 1; i < kategoriat.size(); i++) {
	        PieChart.Data data = new PieChart.Data(kategoriat.get(i), i);
	        pieChartData.add(data);
	    }

	    PieChart pieChart = new PieChart(pieChartData);
	    pieChart.setTitle("Kulut kategorioittain");

	    Scene scene = new Scene(new Group(pieChart), 500, 400);

	    Stage stage = new Stage();
	    stage.setScene(scene);
	    stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}