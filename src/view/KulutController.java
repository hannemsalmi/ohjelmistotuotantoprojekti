package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kayttajanHallinta.KayttajanHallinta;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;

public class KulutController implements ViewController{
	@FXML
	private AnchorPane ap;
	@FXML
	private BorderPane bp;
	
	@FXML
	private HBox ylaBox;
	@FXML
	private HBox lisaaKulu;

	@FXML
	private VBox lisaaOstos;
	@FXML
	private Label ostos;
	@FXML
	private TextField syotaOstos;
	
	@FXML
	private VBox lisaaHinta;
	@FXML
	private Label hinta;
	@FXML
	private TextField syotaHinta;
	
	@FXML
	private VBox lisaaPaivamaara;
	@FXML
	private Label paivamaara;
	@FXML
	private DatePicker syotaPaivamaara;
	
	@FXML
	private VBox lisaaKategoria;
	@FXML
	private Label kategoria;
	@FXML
	private ComboBox<String> syotaKategoria;
	
	@FXML
	private VBox lisaaKuvaus;
	@FXML
	private Label kuvaus;
	@FXML
	private TextField syotaKuvaus;
	@FXML
	private Button tallennaKulu;
	
	@FXML 
	private VBox luoKategoria;
	@FXML 
	private Label luo;
	@FXML 
	private TextField uusiKategoria;
	@FXML 
	private Button tallennaKategoria;
	
	@FXML
	private HBox naytaKulut;
	@FXML
	private ListView<Kulu> kulutListView;
	
	@FXML
	private VBox suodatus;
	@FXML
	private Label budjetti;
	@FXML
	private Label kategoriaSuodatus;
	@FXML
	private ComboBox<String> valitseKategoria;
	@FXML
	private Label kuukausiSuodatus;
	@FXML
	private ComboBox<String> valitseKuukausi;
	@FXML
	private Label vuosiSuodatus;
	@FXML
	private ComboBox<String> valitseVuosi;
	
	private ViewHandler vh;
	private KayttajanHallinta kayttajanhallinta;
	private Kayttaja kayttaja;
	private List<Kulu> kaikkiKulut;
	
	
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
		kayttajanhallinta = vh.getKayttajanhallinta();
		kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		
		initKulut();
		initKategoria();
		initSuodatus();
	}
	
	public void lisaaKulu() {
		kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		String kategorianNimi = syotaKategoria.getSelectionModel().getSelectedItem();
		
		try {
			String ostoksenNimi = syotaOstos.getText();
			double ostoksenHinta = Double.parseDouble(syotaHinta.getText());
			LocalDate ostoksenPvm = syotaPaivamaara.getValue();
			Kategoria ostoksenKategoria = vh.getKontrolleri().getKategoria(kategorianNimi, kayttaja.getNimimerkki());
			String ostoksenKuvaus = syotaKuvaus.getText();
			
			if(kayttaja.getMaksimibudjetti() >= ostoksenHinta) {
				vh.getKontrolleri().lisaaKulu(ostoksenNimi, ostoksenHinta, ostoksenPvm, ostoksenKategoria, kayttaja, ostoksenKuvaus);
			} else {
				System.out.println("Kulun summa on liian suuri budjettiin nähden.");
				JOptionPane.showConfirmDialog(null, "Kulun summa on liian suuri budjettiisi.", "Kulun summassa virhe", JOptionPane.ERROR_MESSAGE);
			}
			
		} catch (NumberFormatException nfe) {
			System.out.println("Numeroarvojen sijasta yritettiin syöttää muuta...");
			JOptionPane.showConfirmDialog(null, "Syötä numeroarvot niitä pyydettäessä.", "Syöttövirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			System.out.println("Jokin vikana arvoja syötettäessä...");
			JOptionPane.showConfirmDialog(null, "Syötä oikeantyyppiset arvot.", "Syöttövirhe", JOptionPane.ERROR_MESSAGE);
		}
		
		kaikkiKulut = vh.getKontrolleri().getKulut(kayttaja.getKayttajaID());
		setKulut(kaikkiKulut);
		budjetti.setText("Budjettia jäljellä:\n" + String.format("%.2f",budjettiaJaljellaLaskuri()) + " €");
		syotaOstos.clear();
		syotaHinta.clear();
		syotaKuvaus.clear();
		syotaKategoria.getSelectionModel().select("Yleinen");
		suodataAika();
		suodata();
		
	}
	
	public void setKulut(List<Kulu> kulut) {
		ObservableList<Kulu> observableKulut = FXCollections.observableList(kulut);
		this.kulutListView.setItems(observableKulut);
	}
	
	public double budjettiaJaljellaLaskuri() {
		Double budjettiaJaljella = kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti();
		for(Kulu kulu: kaikkiKulut) {
			if(kulu.getPaivamaara().getMonthValue() == LocalDate.now().getMonthValue() && kulu.getPaivamaara().getYear() == LocalDate.now().getYear()) {
			budjettiaJaljella -= kulu.getSumma();
			}
		};
		budjetti.setText("Budjettia jäljellä:\n" + String.format("%.2f",budjettiaJaljella ) + " €");
		
		return budjettiaJaljella;
	}
	
	public void lisaaUusiKategoria() {
		kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		vh.getKontrolleri().lisaaKategoria(uusiKategoria.getText(), kayttaja.getNimimerkki());
		syotaKategoria.getItems().add(uusiKategoria.getText());
		valitseKategoria.getItems().add(uusiKategoria.getText());
		uusiKategoria.clear();
	}
	
	public void suodata() {
		ObservableList<Kulu> alkuperainenList = FXCollections.observableList(kaikkiKulut);
		String valittuKategoria = valitseKategoria.getSelectionModel().getSelectedItem();
        if (valittuKategoria.equals("Kaikki")) {
            kulutListView.setItems(alkuperainenList);
        } else {
            List<Kulu> suodatetutKulut = kaikkiKulut.stream()
                .filter(kulu -> kulu.getKategoria().getNimi().equals(valittuKategoria))
                .collect(Collectors.toList());
            ObservableList<Kulu> suodatetutKulutList = FXCollections.observableList(suodatetutKulut);
            kulutListView.setItems(suodatetutKulutList);
        }
	}
	
	public void suodataAika() {
	    int valittuKuukausi = valitseKuukausi.getSelectionModel().getSelectedIndex();
	    int valittuVuosiIndeksi = valitseVuosi.getSelectionModel().getSelectedIndex();
	    int valittuVuosi = LocalDate.now().getYear() - valittuVuosiIndeksi + 1;
	    List<Kulu> suodatetutKulut = new ArrayList<>();
	    if (valittuKuukausi == 0 && valittuVuosiIndeksi == 0){
	        setKulut(kaikkiKulut);
	      }
	    else {
	    	  for (Kulu kulu : kaikkiKulut) {
	    		  if ((kulu.getPaivamaara().getMonthValue() == valittuKuukausi || valittuKuukausi == 0 )&& (kulu.getPaivamaara().getYear() == valittuVuosi || valittuVuosiIndeksi == 0) ) {
	    			  suodatetutKulut.add(kulu);
	        }
	    }
	    setKulut(suodatetutKulut);
	    }
	}
	
	public void initKulut() {
		kaikkiKulut = vh.getKontrolleri().getKulut(kayttajanhallinta.lueKayttajaID());
		setKulut(kaikkiKulut);
	}
	
	public void initKategoria() {
		syotaKategoria.getItems().addAll(vh.getKontrolleri().getKategorianimet(kayttaja.getNimimerkki()));
		syotaKategoria.getSelectionModel().select("Yleinen");
	}
	
	public void initSuodatus() {
		budjetti.setText("Budjettisi on \n" + String.format("%.2f", kayttaja.getMaksimibudjetti()) + " €");
		valitseKategoria.getItems().add("Kaikki");
		valitseKategoria.getItems().addAll(vh.getKontrolleri().getKategorianimet(kayttaja.getNimimerkki()));
		valitseKategoria.getSelectionModel().select("Kaikki");
		valitseKuukausi.getItems().add("Kaikki");
		valitseKuukausi.getItems().add("Tammi");
		valitseKuukausi.getItems().add("Helmi");
		valitseKuukausi.getItems().add("Maalis");
		valitseKuukausi.getItems().add("Huhti");
		valitseKuukausi.getItems().add("Touko");
		valitseKuukausi.getItems().add("Kesä");
		valitseKuukausi.getItems().add("Heinä");
		valitseKuukausi.getItems().add("Elo");
		valitseKuukausi.getItems().add("Syys");
		valitseKuukausi.getItems().add("Loka");
		valitseKuukausi.getItems().add("Marras");
		valitseKuukausi.getItems().add("Joulu");
		valitseKuukausi.getSelectionModel().select("Kaikki");
		valitseVuosi.getItems().add("Kaikki");
		for (int i = LocalDate.now().getYear(); i >= LocalDate.now().getYear() - 5; i--) {
			valitseVuosi.getItems().add(Integer.toString(i));
	    }
		valitseVuosi.getSelectionModel().select("Kaikki");
	}
}
