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

/**
 * KulutController implements a controller class for Kulut.fxml.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
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
	private VBox kulutJaOhjeistus;
	@FXML
	private ListView<Kulu> kulutListView;
	@FXML
	private Label ohjeistus;
	
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
	
	/**
	 * Initiates AsetuksetController when it is opened.
	 * @param ViewHandler The class which controls the view changes and functions.
	 */
	@Override
	public void init(ViewHandler viewHandler) { 
		vh = viewHandler;
		if(!(vh.getKieli())) {
			asetaKieli();
		}

		kayttajanhallinta = vh.getKayttajanhallinta();
		kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		
		initKulut();
		initKategoriaJaPvm();
		initSuodatus();
	}
	
	/**
	 * A method for changing the language of the graphic user interface.
	 */
	public void asetaKieli() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ostos.setText(english.getString("ostos"));
		hinta.setText(english.getString("hinta"));
		paivamaara.setText(english.getString("päivämäärä"));
		kategoria.setText(english.getString("kategoria"));
		kuvaus.setText(english.getString("kuvaus"));
		luo.setText(english.getString("luoKategoria"));
		tallennaKulu.setText(english.getString("tallennaOstos"));
		tallennaKategoria.setText(english.getString("luoKategoria"));
		kategoriaSuodatus.setText(english.getString("kategoriaSuodatus"));
		kuukausiSuodatus.setText(english.getString("kuukausiSuodatus"));
		vuosiSuodatus.setText(english.getString("vuosiSuodatus"));
		ohjeistus.setText(english.getString("ohjeistus"));
	}
	
	/**
	 * A method used for adding the expense to the expense listing and the database.
	 * There are checks to see that the values inserted are in correct format.
	 * All the fields are resetted after saving the expense.
	 */
	public void lisaaKulu() {
		kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		String kategorianNimi = syotaKategoria.getSelectionModel().getSelectedItem();
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		
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
				if(vh.getKieli()) {
					JOptionPane.showConfirmDialog(null, finnish.getString("budjettiVaroitus"), finnish.getString("summaVirhe"), JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(null, english.getString("budjettiVaroitus"), english.getString("summaVirhe"), JOptionPane.ERROR_MESSAGE);
				}
			}
			
		} catch (NumberFormatException nfe) {
			System.out.println("Numeroarvojen sijasta yritettiin syöttää muuta...");
			if(vh.getKieli()) {
				JOptionPane.showConfirmDialog(null, finnish.getString("numeroVaroitus"), finnish.getString("syöttöVirhe"), JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showConfirmDialog(null, english.getString("numeroVaroitus"), english.getString("syöttöVirhe"), JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			System.out.println("Jokin vikana arvoja syötettäessä...");
			if(vh.getKieli()) {
				JOptionPane.showConfirmDialog(null, finnish.getString("tyyppiVirhe"), finnish.getString("syöttöVirhe"), JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showConfirmDialog(null, english.getString("tyyppiVirhe"), english.getString("syöttöVirhe"), JOptionPane.ERROR_MESSAGE);
			}
		}
		
		kaikkiKulut = vh.getKontrolleri().getKulut(kayttaja.getKayttajaID());
		setKulut(kaikkiKulut);
		if(vh.getKieli()) {
			budjetti.setText(finnish.getString("jäljellä") + String.format("%.2f",budjettiaJaljellaLaskuri()) + " €");
		} else {
			budjetti.setText(english.getString("jäljellä") + String.format("%.2f",budjettiaJaljellaLaskuri()) + " €");
		}
		syotaOstos.clear();
		syotaHinta.clear();
		syotaKuvaus.clear();
		if(vh.getKieli()) {
			syotaKategoria.getSelectionModel().select(finnish.getString("yleinen"));
		} else {
			syotaKategoria.getSelectionModel().select(english.getString("yleinen"));
		}
		LocalDate paiva = LocalDate.now();
		syotaPaivamaara.setValue(paiva);
		suodata();
	}
	
	/**
	 * A method used for updating the expense listing visible for the user.
	 * @param kulut A list which includes all the expenses the user has.
	 */
	public void setKulut(List<Kulu> kulut) {
		ObservableList<Kulu> observableKulut = FXCollections.observableList(kulut);
		this.kulutListView.setItems(observableKulut);
	}
	
	/**
	 * A method used for calculating the remaining budget of the month.
	 * @return The double value of the money user still has left to spend in this month.
	 */
	public double budjettiaJaljellaLaskuri() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		Double budjettiaJaljella = kayttajanhallinta.getKirjautunutKayttaja().getMaksimibudjetti();
		for(Kulu kulu: kaikkiKulut) {
			if(kulu.getPaivamaara().getMonthValue() == LocalDate.now().getMonthValue() && kulu.getPaivamaara().getYear() == LocalDate.now().getYear()) {
			budjettiaJaljella -= kulu.getSumma();
			}
		};
		if(vh.getKieli()) {
			budjetti.setText(finnish.getString("jäljellä") + String.format("%.2f",budjettiaJaljella ) + " €");
		} else {
			budjetti.setText(english.getString("jäljellä") + String.format("%.2f",budjettiaJaljella ) + " €");
		}
		
		return budjettiaJaljella;
	}
	
	/**
	 * A method used for adding a new category for the active profile.
	 */
	public void lisaaUusiKategoria() {
		kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		vh.getKontrolleri().lisaaKategoria(uusiKategoria.getText(), kayttaja.getNimimerkki());
		syotaKategoria.getItems().add(uusiKategoria.getText());
		valitseKategoria.getItems().add(uusiKategoria.getText());
		uusiKategoria.clear();
	}
	
	/**
	 * A method which filters the expense listing based on a selected category, month and year.
	 */
	public void suodata() {
		String valittuKategoria = valitseKategoria.getSelectionModel().getSelectedItem();
		int valittuKuukausi = valitseKuukausi.getSelectionModel().getSelectedIndex();
	    int valittuVuosiIndeksi = valitseVuosi.getSelectionModel().getSelectedIndex();
	    int valittuVuosi = LocalDate.now().getYear() - valittuVuosiIndeksi + 1;
	    
	    List<Kulu> suodatetutKulut = new ArrayList<>();
	    List<Kulu> valiaikaisetKulut = new ArrayList<>();
	    List<Kulu> toisetValiaikaisetKulut = new ArrayList<>();
	    
	    ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
	    
		if(vh.getKieli()) {
			//vain kategoriaa on muutettu
		    if (!valittuKategoria.equals(finnish.getString("kaikki")) && valittuKuukausi == 0 && valittuVuosiIndeksi == 0) {
		    	suodatetutKulut = kaikkiKulut.stream()
		            .filter(kulu -> kulu.getKategoria().getNimi().equals(valittuKategoria))
		            .collect(Collectors.toList());
		    	setKulut(suodatetutKulut);
		    //vain kuukautta muutettu
		    } else if (valittuKategoria.equals(finnish.getString("kaikki")) && valittuKuukausi != 0 && valittuVuosiIndeksi == 0) {
		    	for (Kulu kulu : kaikkiKulut) {
		    		if ((kulu.getPaivamaara().getMonthValue() == valittuKuukausi)) {
		    			  suodatetutKulut.add(kulu);
		    		}
		    	}
			    setKulut(suodatetutKulut);
		    //vain vuotta muutettu
		    } else if (valittuKategoria.equals(finnish.getString("kaikki")) && valittuKuukausi == 0 && valittuVuosiIndeksi != 0) {
		    	for (Kulu kulu : kaikkiKulut) {
		    		if ((kulu.getPaivamaara().getYear() == valittuVuosi)) {
		    			  suodatetutKulut.add(kulu);
		    		}
		    	}
			    setKulut(suodatetutKulut);
			//kategoriaa ja kuukautta muutettu
		    } else if (!valittuKategoria.equals(finnish.getString("kaikki")) && valittuKuukausi != 0 && valittuVuosiIndeksi == 0) {
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
		    } else if (!valittuKategoria.equals(finnish.getString("kaikki")) && valittuKuukausi == 0 && valittuVuosiIndeksi != 0) {
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
		    } else if (valittuKategoria.equals(finnish.getString("kaikki")) && valittuKuukausi != 0 && valittuVuosiIndeksi != 0) {
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
		    } else if (!valittuKategoria.equals(finnish.getString("kaikki")) && valittuKuukausi != 0 && valittuVuosiIndeksi != 0) {
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
		} else {
			//vain kategoriaa on muutettu
		    if (!valittuKategoria.equals(english.getString("kaikki")) && valittuKuukausi == 0 && valittuVuosiIndeksi == 0) {
		    	suodatetutKulut = kaikkiKulut.stream()
		            .filter(kulu -> kulu.getKategoria().getNimi().equals(valittuKategoria))
		            .collect(Collectors.toList());
		    	setKulut(suodatetutKulut);
		    //vain kuukautta muutettu
		    } else if (valittuKategoria.equals(english.getString("kaikki")) && valittuKuukausi != 0 && valittuVuosiIndeksi == 0) {
		    	for (Kulu kulu : kaikkiKulut) {
		    		if ((kulu.getPaivamaara().getMonthValue() == valittuKuukausi)) {
		    			  suodatetutKulut.add(kulu);
		    		}
		    	}
			    setKulut(suodatetutKulut);
		    //vain vuotta muutettu
		    } else if (valittuKategoria.equals(english.getString("kaikki")) && valittuKuukausi == 0 && valittuVuosiIndeksi != 0) {
		    	for (Kulu kulu : kaikkiKulut) {
		    		if ((kulu.getPaivamaara().getYear() == valittuVuosi)) {
		    			  suodatetutKulut.add(kulu);
		    		}
		    	}
			    setKulut(suodatetutKulut);
			//kategoriaa ja kuukautta muutettu
		    } else if (!valittuKategoria.equals(english.getString("kaikki")) && valittuKuukausi != 0 && valittuVuosiIndeksi == 0) {
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
		    } else if (!valittuKategoria.equals(english.getString("kaikki")) && valittuKuukausi == 0 && valittuVuosiIndeksi != 0) {
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
		    } else if (valittuKategoria.equals(english.getString("kaikki")) && valittuKuukausi != 0 && valittuVuosiIndeksi != 0) {
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
		    } else if (!valittuKategoria.equals(english.getString("kaikki")) && valittuKuukausi != 0 && valittuVuosiIndeksi != 0) {
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
	}
	
	/**
	 * A method which opens the view (KulunMuokkaus.fxml) where it is possible to modify the expense.
	 */
	public void avaaMuokkausnakymaKulu() {
		Kulu kulu = kulutListView.getSelectionModel().getSelectedItem();
		int kuluId = kulu.getKuluID();
	    
		vh.avaaKulunMuokkaus(kuluId);
	}
	
	/**
	 * A method used for refreshing the expense listing and the money-left-for-this-month value.
	 */
	public void paivitaKulut() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		kaikkiKulut = vh.getKontrolleri().getKulut(kayttaja.getKayttajaID());
		setKulut(kaikkiKulut);
		syotaKategoria.getItems().clear();
		syotaKategoria.getItems().addAll(vh.getKontrolleri().getKategorianimet(kayttaja.getNimimerkki()));
		if(vh.getKieli()) {
			budjetti.setText(finnish.getString("jäljellä") + String.format("%.2f",budjettiaJaljellaLaskuri()) + " €");
		} else {
			budjetti.setText(english.getString("jäljellä") + String.format("%.2f",budjettiaJaljellaLaskuri()) + " €");
		}
	}
	
	/**
	 * A method used for initiating the expense listing.
	 */
	public void initKulut() {
		kaikkiKulut = vh.getKontrolleri().getKulut(kayttajanhallinta.lueKayttajaID());
		setKulut(kaikkiKulut);
	}
	
	/**
	 * A method used for initiating the category and date values for the comboboxes used for adding the expense.
	 */
	public void initKategoriaJaPvm() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		syotaKategoria.getItems().addAll(vh.getKontrolleri().getKategorianimet(kayttaja.getNimimerkki()));
		if(vh.getKieli()) {
			syotaKategoria.getSelectionModel().select(finnish.getString("yleinen"));
		} else {
			syotaKategoria.getSelectionModel().select(english.getString("yleinen"));
		}
		
		LocalDate paiva = LocalDate.now();
		syotaPaivamaara.setValue(paiva);
	}
	
	/**
	 * Initiates the category, month and year values for the comboboxes which are used to filter the expenses of the expense listing.
	 */
	public void initSuodatus() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		if(vh.getKieli()) {
			budjetti.setText(finnish.getString("jäljellä") + String.format("%.2f", budjettiaJaljellaLaskuri()) + " €");
			valitseKategoria.getItems().add(finnish.getString("kaikki"));
			valitseKategoria.getItems().addAll(vh.getKontrolleri().getKategorianimet(kayttaja.getNimimerkki()));
			valitseKategoria.getSelectionModel().select(finnish.getString("kaikki"));
			valitseKuukausi.getItems().add(finnish.getString("kaikki"));
			valitseKuukausi.getItems().add(finnish.getString("tammi"));
			valitseKuukausi.getItems().add(finnish.getString("helmi"));
			valitseKuukausi.getItems().add(finnish.getString("maalis"));
			valitseKuukausi.getItems().add(finnish.getString("huhti"));
			valitseKuukausi.getItems().add(finnish.getString("touko"));
			valitseKuukausi.getItems().add(finnish.getString("kesä"));
			valitseKuukausi.getItems().add(finnish.getString("heinä"));
			valitseKuukausi.getItems().add(finnish.getString("elo"));
			valitseKuukausi.getItems().add(finnish.getString("syys"));
			valitseKuukausi.getItems().add(finnish.getString("loka"));
			valitseKuukausi.getItems().add(finnish.getString("marras"));
			valitseKuukausi.getItems().add(finnish.getString("joulu"));
			valitseKuukausi.getSelectionModel().select(finnish.getString("kaikki"));
			valitseVuosi.getItems().add(finnish.getString("kaikki"));
			for (int i = LocalDate.now().getYear(); i >= LocalDate.now().getYear() - 5; i--) {
				valitseVuosi.getItems().add(Integer.toString(i));
		    }
			valitseVuosi.getSelectionModel().select(finnish.getString("kaikki"));
		} else {
			budjetti.setText(english.getString("jäljellä") + String.format("%.2f", budjettiaJaljellaLaskuri()) + " €");
			valitseKategoria.getItems().add(english.getString("kaikki"));
			valitseKategoria.getItems().addAll(vh.getKontrolleri().getKategorianimet(kayttaja.getNimimerkki()));
			valitseKategoria.getSelectionModel().select(english.getString("kaikki"));
			valitseKuukausi.getItems().add(english.getString("kaikki"));
			valitseKuukausi.getItems().add(english.getString("tammi"));
			valitseKuukausi.getItems().add(english.getString("helmi"));
			valitseKuukausi.getItems().add(english.getString("maalis"));
			valitseKuukausi.getItems().add(english.getString("huhti"));
			valitseKuukausi.getItems().add(english.getString("touko"));
			valitseKuukausi.getItems().add(english.getString("kesä"));
			valitseKuukausi.getItems().add(english.getString("heinä"));
			valitseKuukausi.getItems().add(english.getString("elo"));
			valitseKuukausi.getItems().add(english.getString("syys"));
			valitseKuukausi.getItems().add(english.getString("loka"));
			valitseKuukausi.getItems().add(english.getString("marras"));
			valitseKuukausi.getItems().add(english.getString("joulu"));
			valitseKuukausi.getSelectionModel().select(english.getString("kaikki"));
			valitseVuosi.getItems().add(english.getString("kaikki"));
			for (int i = LocalDate.now().getYear(); i >= LocalDate.now().getYear() - 5; i--) {
				valitseVuosi.getItems().add(Integer.toString(i));
		    }
			valitseVuosi.getSelectionModel().select(english.getString("kaikki"));
		}
	}
}
