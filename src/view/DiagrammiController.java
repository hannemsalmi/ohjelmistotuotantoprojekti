package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kayttajanHallinta.KayttajanHallinta;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;

public class DiagrammiController implements ViewController {
	@FXML
	private AnchorPane ap;
	@FXML
	private BorderPane bp;
	@FXML
	private HBox kaikkiSisalto;
	@FXML
	private VBox chartBox;
	@FXML
	private PieChart pieChart;
	
	@FXML
	private VBox suodatus;
	@FXML
	private Label kulutus;
	@FXML
	private VBox kuukausi;
	@FXML
	private Label kuukausiLabel;
	@FXML
	private ComboBox<String> kuukausiBox;
	@FXML
	private VBox vuosi;
	@FXML
	private Label vuosiLabel;
	@FXML
	private ComboBox<String> vuosiBox;
	
	private ViewHandler vh;
	private KayttajanHallinta kayttajanhallinta;
	private List<Kulu> kaikkiKulut;
	private List <Kulu> suodatetut;
	
	
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
		kayttajanhallinta = vh.getKayttajanhallinta();
		kaikkiKulut = vh.getKontrolleri().getKulut(kayttajanhallinta.lueKayttajaID());
		suodatetut = kaikkiKulut;
		initSuodatus();
		naytaKuluDiagrammi();
		laskeKulutusYhteensa();
	}
	
	public void naytaKuluDiagrammi() {
	    Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
	    List<Kategoria> kategoriat = vh.getKontrolleri().getKategoriat(kayttaja.getNimimerkki());
	    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

	    Map<Kategoria, Double> kuluSums = new HashMap<>();
	    for (Kulu kulu : suodatetut) {
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
	    
	    pieChart.setData(pieChartData);
	    pieChart.setLabelLineLength(50);
	    pieChart.setLabelsVisible(true);
	    pieChart.setLegendVisible(false);
	}
	
	public void suodataAika() {
	    int valittuKuukausi = kuukausiBox.getSelectionModel().getSelectedIndex();
	    int valittuVuosiIndeksi = vuosiBox.getSelectionModel().getSelectedIndex();
	    int valittuVuosi = LocalDate.now().getYear() - valittuVuosiIndeksi + 1;
	    
	    List<Kulu> suodatetutKulut = new ArrayList<>();
	    
	    if (valittuKuukausi == 0 && valittuVuosiIndeksi == 0){
	        suodatetutKulut.addAll(kaikkiKulut);
	      }
	    else {
	    	  for (Kulu kulu : kaikkiKulut) {
	    		  if ((kulu.getPaivamaara().getMonthValue() == valittuKuukausi || valittuKuukausi == 0 )&& (kulu.getPaivamaara().getYear() == valittuVuosi || valittuVuosiIndeksi == 0) ) {
	    			  suodatetutKulut.add(kulu);
		        }
		    }
	    }
		suodatetut = suodatetutKulut;
		naytaKuluDiagrammi();
		laskeKulutusYhteensa();
	}
	
	public void laskeKulutusYhteensa() {
		double kokonaiskulutus = 0;
		
		for (Kulu kulu : suodatetut) {
			kokonaiskulutus += kulu.getSumma();
		}
		
		kulutus.setText("Kulutus yhteensä:\n" + String.format("%.2f",kokonaiskulutus ) + " €");
	}
	
	public void initSuodatus() {
		kuukausiBox.getItems().add("Kaikki");
		kuukausiBox.getItems().add("Tammi");
		kuukausiBox.getItems().add("Helmi");
		kuukausiBox.getItems().add("Maalis");
		kuukausiBox.getItems().add("Huhti");
		kuukausiBox.getItems().add("Touko");
		kuukausiBox.getItems().add("Kesä");
		kuukausiBox.getItems().add("Heinä");
		kuukausiBox.getItems().add("Elo");
		kuukausiBox.getItems().add("Syys");
		kuukausiBox.getItems().add("Loka");
		kuukausiBox.getItems().add("Marras");
		kuukausiBox.getItems().add("Joulu");
		kuukausiBox.getSelectionModel().select("Kaikki");
		vuosiBox.getItems().add("Kaikki");
		for (int i = LocalDate.now().getYear(); i >= LocalDate.now().getYear() - 5; i--) {
			vuosiBox.getItems().add(Integer.toString(i));
	    }
		vuosiBox.getSelectionModel().select("Kaikki");
	}
}
