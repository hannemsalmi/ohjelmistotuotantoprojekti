package view;

import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kayttajanHallinta.KayttajanHallinta;
import model.Kategoria;
import model.Kayttaja;

/**
 * KulunMuokkausController implements a controller class for KulunMuokkaus.fxml.
 * @author hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public class KulunMuokkausController implements ViewController{
	
	@FXML
	private AnchorPane ap;
	@FXML
	private BorderPane bp;
	@FXML
	private HBox sisalto;
	
	@FXML
	private VBox vasen;
	@FXML
	private Label nimiLabel;
	@FXML
	private TextField nimiField;
	@FXML
	private Label hintaLabel;
	@FXML
	private TextField hintaField;
	@FXML
	private Label kuvausLabel;
	@FXML
	private TextField kuvausField;
	@FXML
	private Button tallennaVasen;
	
	@FXML
	private VBox oikea;
	@FXML
	private VBox kategoria;
	@FXML
	private Label kategoriaLabel;
	@FXML
	private ComboBox<String> kategoriaBox;
	@FXML
	private Button tallennaKategoria;
	@FXML
	private VBox poista;
	@FXML
	private Label poistaLabel;
	@FXML
	private Button poistaButton;
	
	private ViewHandler vh;
	private KayttajanHallinta kayttajanhallinta;
	private int kuluId;

	/**
	 * Initiates KulunMuokkausController when it is opened.
	 * @param ViewHandler The class which controls the view changes and functions.
	 */
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
		if(!(vh.getKieli())) {
			asetaKieli();
		}
		kayttajanhallinta = vh.getKayttajanhallinta();
		
		initKategoriaBox();
	}
	
	/**
	 * A method for changing the language of the graphical user interface.
	 */
	public void asetaKieli() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		nimiLabel.setText(english.getString("nimi"));
		hintaLabel.setText(english.getString("uusiHinta"));
		kuvausLabel.setText(english.getString("uusiKuvaus"));
		tallennaVasen.setText(english.getString("tallenna"));
		kategoriaLabel.setText(english.getString("uusiKategoria"));
		tallennaKategoria.setText(english.getString("tallenna"));
		poistaLabel.setText(english.getString("kulunPoisto"));
		poistaButton.setText(english.getString("poisto"));
	}

	/**
	 * A method used for saving the modified expense and updating the graphical user interface to have the new data of the expense.
	 */
	public void tallennaNimiHintaKuvaus() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		try {
			String nimi = nimiField.getText();
    		double hinta = Double.parseDouble(hintaField.getText());
	        String kuvaus = kuvausField.getText();
	        vh.getKontrolleri().muokkaaKulua(kuluId, hinta, nimi, kuvaus);
		} catch (NumberFormatException nfe) {
			System.out.println("Numeroarvojen sijasta yritettiin syöttää muuta...");
			if(vh.getKieli()) {
				JOptionPane.showConfirmDialog(null, finnish.getString("numeroVaroitus"), finnish.getString("syöttöVirhe"), JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showConfirmDialog(null, english.getString("numeroVaroitus"), english.getString("syöttöVirhe"), JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			System.out.println("Joku vikana arvoja syötettäessä...");
			if(vh.getKieli()) {
				JOptionPane.showConfirmDialog(null, finnish.getString("tyyppiVirhe"), finnish.getString("syöttöVirhe"), JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showConfirmDialog(null, english.getString("tyyppiVirhe"), english.getString("syöttöVirhe"), JOptionPane.ERROR_MESSAGE);
			}
		}
		
		teeLopputoimet();
	}
	
	/**
	 * A method used for saving the new category for the expense.
	 * Updates the expense list to reflect the change.
	 */
	public void tallennaKategoria() {
		String kategoriaNimi = kategoriaBox.getSelectionModel().getSelectedItem();
		Kategoria uusiKategoria = vh.getKontrolleri().getKategoria(kategoriaNimi, kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki());
		vh.getKontrolleri().muutaKulunKategoria(kuluId, uusiKategoria);
		
		teeLopputoimet();
	}
	
	/**
	 * A method used for removing an expense from the expense list and the database. 
	 */
	public void poistaKulu() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		if(vh.getKieli()) {
			int valinta = JOptionPane.showConfirmDialog(null, finnish.getString("kuluPoistoVaroitus"), finnish.getString("harkitse"),JOptionPane.OK_CANCEL_OPTION);
			if(valinta == 0) {
	    		vh.getKontrolleri().poistaKulu(kuluId);
			}
		} else {
			int valinta = JOptionPane.showConfirmDialog(null, english.getString("kuluPoistoVaroitus"), english.getString("harkitse"),JOptionPane.OK_CANCEL_OPTION);
			if(valinta == 0) {
	    		vh.getKontrolleri().poistaKulu(kuluId);
			}
		}
		
		teeLopputoimet();
	}
	
	/**
	 * Initiates the combobox for category selection.
	 */
	private void initKategoriaBox() {
		Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		List<String> kaikkiKategoriat = vh.getKontrolleri().getKategorianimet(kayttaja.getNimimerkki());
	    kategoriaBox.getItems().addAll(kaikkiKategoriat);
	}
	
	/**
	 * A method used for updating the expense listing based on the modifications user made to expenses.
	 */
	private void teeLopputoimet() {
		ViewController aktiivinen = vh.getAktiivinen();
		((KulutController) aktiivinen).paivitaKulut();
		
		Stage stage = vh.getMuokkaaKuluaStage();
		stage.close();
	}

	/**
	 * Gets the id of the expense being modified from the KulutController and sets it to KulunMuokkausController. 
	 * @param kuluId The id of the expense.
	 */
	public void setKuluId(int kuluId) {
		this.kuluId = kuluId;
	}
}
