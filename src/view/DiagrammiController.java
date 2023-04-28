package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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

/**
 * DiagrammiController implements a controller class for Diagrammi.fxml.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
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
	
	/**
	 * Initiates DiagrammiController when it is opened.
	 * @param ViewHandler The class which controls the view changes and functions.
	 */
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
		if(!(vh.getKieli())) {
			asetaKieli();
		}
		kayttajanhallinta = vh.getKayttajanhallinta();
		kaikkiKulut = vh.getKontrolleri().getKulut(kayttajanhallinta.lueKayttajaID());
		suodatetut = kaikkiKulut;
		initSuodatus();
		naytaKuluDiagrammi();
		laskeKulutusYhteensa();
	}
	
	/**
	 * A method for changing the language of the graphic user interface.
	 */
	public void asetaKieli() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		pieChart.setTitle(english.getString("otsikko"));
		kulutus.setText(english.getString("kulutus"));
		kuukausiLabel.setText(english.getString("kuukausi"));
		vuosiLabel.setText(english.getString("vuosi"));
	}
	
	/**
	 * A method which creates a diagram of the expenses during a selected month.
	 */
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
	
	/**
	 * A method used for filtering expenses by the month and the year.
	 * Filtered expenses are then displayed in the diagram and the info of the amount of money used during the selected month will be updated.
	 */
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
	
	/**
	 * A method which calculates the expenditure of the selected month.
	 */
	public void laskeKulutusYhteensa() {
		double kokonaiskulutus = 0;
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		
		for (Kulu kulu : suodatetut) {
			kokonaiskulutus += kulu.getSumma();
		}
		
		if(vh.getKieli()) {
			kulutus.setText(finnish.getString("yhteisKulutus") + String.format("%.2f",kokonaiskulutus ) + " €");
		} else {
			kulutus.setText(english.getString("yhteisKulutus") + String.format("%.2f",kokonaiskulutus ) + " €");
		}
	}
	
	/**
	 * Initiates the comboboxes for filtering.
	 */
	public void initSuodatus() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		if(vh.getKieli()) {
			kuukausiBox.getItems().add(finnish.getString("kaikki"));
			kuukausiBox.getItems().add(finnish.getString("tammi"));
			kuukausiBox.getItems().add(finnish.getString("helmi"));
			kuukausiBox.getItems().add(finnish.getString("maalis"));
			kuukausiBox.getItems().add(finnish.getString("huhti"));
			kuukausiBox.getItems().add(finnish.getString("touko"));
			kuukausiBox.getItems().add(finnish.getString("kesä"));
			kuukausiBox.getItems().add(finnish.getString("heinä"));
			kuukausiBox.getItems().add(finnish.getString("elo"));
			kuukausiBox.getItems().add(finnish.getString("syys"));
			kuukausiBox.getItems().add(finnish.getString("loka"));
			kuukausiBox.getItems().add(finnish.getString("marras"));
			kuukausiBox.getItems().add(finnish.getString("joulu"));
			kuukausiBox.getSelectionModel().select(finnish.getString("kaikki"));
			vuosiBox.getItems().add(finnish.getString("kaikki"));
			for (int i = LocalDate.now().getYear(); i >= LocalDate.now().getYear() - 5; i--) {
				vuosiBox.getItems().add(Integer.toString(i));
		    }
			vuosiBox.getSelectionModel().select(finnish.getString("kaikki"));
		} else {
			kuukausiBox.getItems().add(english.getString("kaikki"));
			kuukausiBox.getItems().add(english.getString("tammi"));
			kuukausiBox.getItems().add(english.getString("helmi"));
			kuukausiBox.getItems().add(english.getString("maalis"));
			kuukausiBox.getItems().add(english.getString("huhti"));
			kuukausiBox.getItems().add(english.getString("touko"));
			kuukausiBox.getItems().add(english.getString("kesä"));
			kuukausiBox.getItems().add(english.getString("heinä"));
			kuukausiBox.getItems().add(english.getString("elo"));
			kuukausiBox.getItems().add(english.getString("syys"));
			kuukausiBox.getItems().add(english.getString("loka"));
			kuukausiBox.getItems().add(english.getString("marras"));
			kuukausiBox.getItems().add(english.getString("joulu"));
			kuukausiBox.getSelectionModel().select(english.getString("kaikki"));
			vuosiBox.getItems().add(english.getString("kaikki"));
			for (int i = LocalDate.now().getYear(); i >= LocalDate.now().getYear() - 5; i--) {
				vuosiBox.getItems().add(Integer.toString(i));
		    }
			vuosiBox.getSelectionModel().select(english.getString("kaikki"));
		}
		
	}
}
