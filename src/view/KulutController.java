package view;

import java.util.ResourceBundle;

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
		if(!(vh.getKieli())) {
			asetaKieli();
		}

		kayttajanhallinta = vh.getKayttajanhallinta();
		kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		
		initKulut();
		initKategoria();
		initSuodatus();
	}
	
	public void asetaKieli() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ostos.setText(english.getString("ostos"));
		hinta.setText(english.getString("hinta"));
		paivamaara.setText(english.getString("päivämäärä"));
		kategoria.setText(english.getString("kategoria"));
		kuvaus.setText(english.getString("kuvaus"));
		luo.setText(english.getString("luokategoria"));
		tallennaKulu.setText(english.getString("tallennaostos"));
		tallennaKategoria.setText(english.getString("luokategoria"));
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
		String valittuKategoria = valitseKategoria.getSelectionModel().getSelectedItem();
		int valittuKuukausi = valitseKuukausi.getSelectionModel().getSelectedIndex();
	    int valittuVuosiIndeksi = valitseVuosi.getSelectionModel().getSelectedIndex();
	    int valittuVuosi = LocalDate.now().getYear() - valittuVuosiIndeksi + 1;
	    
	    List<Kulu> suodatetutKulut = new ArrayList<>();
	    List<Kulu> valiaikaisetKulut = new ArrayList<>();
	    List<Kulu> toisetValiaikaisetKulut = new ArrayList<>();
	    
	    //vain kategoriaa on muutettu
	    if (!valittuKategoria.equals("Kaikki") && valittuKuukausi == 0 && valittuVuosiIndeksi == 0) {
	    	suodatetutKulut = kaikkiKulut.stream()
	            .filter(kulu -> kulu.getKategoria().getNimi().equals(valittuKategoria))
	            .collect(Collectors.toList());
	    	setKulut(suodatetutKulut);
	    //vain kuukautta muutettu
	    } else if (valittuKategoria.equals("Kaikki") && valittuKuukausi != 0 && valittuVuosiIndeksi == 0) {
	    	for (Kulu kulu : kaikkiKulut) {
	    		if ((kulu.getPaivamaara().getMonthValue() == valittuKuukausi)) {
	    			  suodatetutKulut.add(kulu);
	    		}
	    	}
		    setKulut(suodatetutKulut);
	    //vain vuotta muutettu
	    } else if (valittuKategoria.equals("Kaikki") && valittuKuukausi == 0 && valittuVuosiIndeksi != 0) {
	    	for (Kulu kulu : kaikkiKulut) {
	    		if ((kulu.getPaivamaara().getYear() == valittuVuosi)) {
	    			  suodatetutKulut.add(kulu);
	    		}
	    	}
		    setKulut(suodatetutKulut);
		//kategoriaa ja kuukautta muutettu
	    } else if (!valittuKategoria.equals("Kaikki") && valittuKuukausi != 0 && valittuVuosiIndeksi == 0) {
	    	valiaikaisetKulut = kaikkiKulut.stream()
		        .filter(kulu -> kulu.getKategoria().getNimi().equals(valittuKategoria))
		        .collect(Collectors.toList());
	    	for (Kulu kulu : valiaikaisetKulut) {
	    		if ((kulu.getPaivamaara().getMonthValue() == valittuKuukausi)) {
	    			suodatetutKulut.add(kulu);
	    		}
	    	}
	    	setKulut(suodatetutKulut);
	    // kategoriaa ja vuotta muutettu
	    } else if (!valittuKategoria.equals("Kaikki") && valittuKuukausi == 0 && valittuVuosiIndeksi != 0) {
	    	valiaikaisetKulut = kaikkiKulut.stream()
			    .filter(kulu -> kulu.getKategoria().getNimi().equals(valittuKategoria))
			    .collect(Collectors.toList());
	    	for (Kulu kulu : valiaikaisetKulut) {
	    		if ((kulu.getPaivamaara().getYear() == valittuVuosi)) {
	    			suodatetutKulut.add(kulu);
	    		}
	    	}
	    	setKulut(suodatetutKulut);
	    // kuukautta ja vuotta muutettu
	    } else if (valittuKategoria.equals("Kaikki") && valittuKuukausi != 0 && valittuVuosiIndeksi != 0) {
	    	for (Kulu kulu : kaikkiKulut) {
	    		if ((kulu.getPaivamaara().getMonthValue() == valittuKuukausi)) {
	    			valiaikaisetKulut.add(kulu);
	    		}
	    	}
	    	for (Kulu kulu : valiaikaisetKulut) {
	    		if ((kulu.getPaivamaara().getYear() == valittuVuosi)) {
	    			suodatetutKulut.add(kulu);
	    		}
	    	}
	    	setKulut(suodatetutKulut);
	    //kategoriaa, kuukautta ja vuotta muutettu
	    } else if (!valittuKategoria.equals("Kaikki") && valittuKuukausi != 0 && valittuVuosiIndeksi != 0) {
	    	valiaikaisetKulut = kaikkiKulut.stream()
		        .filter(kulu -> kulu.getKategoria().getNimi().equals(valittuKategoria))
		        .collect(Collectors.toList());
	    	for (Kulu kulu : valiaikaisetKulut) {
	    		if ((kulu.getPaivamaara().getMonthValue() == valittuKuukausi)) {
	    			  toisetValiaikaisetKulut.add(kulu);
	    		}
	    	}
	    	for (Kulu kulu : toisetValiaikaisetKulut) {
	    		if ((kulu.getPaivamaara().getYear() == valittuVuosi)) {
	    			suodatetutKulut.add(kulu);
	    		}
	    	}
	    	setKulut(suodatetutKulut);
	    } else {
		    //näytetään defaultilla kaikki
		    setKulut(kaikkiKulut);
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
		budjetti.setText("Budjettisi on \n" + String.format("%.2f", budjettiaJaljellaLaskuri()) + " €");
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
