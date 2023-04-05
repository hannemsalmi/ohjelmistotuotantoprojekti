package view;

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
	
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
		kayttajanhallinta = vh.getKayttajanhallinta();
		kaikkiKulut = vh.getKontrolleri().getKulut(kayttajanhallinta.lueKayttajaID());
		naytaKuluDiagrammi();
	}
	
	public void naytaKuluDiagrammi() {
	    Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
	    List<Kategoria> kategoriat = vh.getKontrolleri().getKategoriat(kayttaja.getNimimerkki());
	    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

	    Map<Kategoria, Double> kuluSums = new HashMap<>();
	    for (Kulu kulu : kaikkiKulut) {
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
}
