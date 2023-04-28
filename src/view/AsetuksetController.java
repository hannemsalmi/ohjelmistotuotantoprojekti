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
import kayttajanHallinta.KayttajanHallinta;
import model.Kategoria;
import model.Kayttaja;

/**
 * AsetuksetController implements a controller class for Asetukset.fxml.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public class AsetuksetController implements ViewController{
	@FXML
	private AnchorPane ap;
	@FXML
	private BorderPane bp;
	@FXML
	private HBox sisalto;
	@FXML
	private VBox kayttajaAsetukset;
	@FXML
	private Label kayttajaAsetuksetLabel;
	@FXML
	private VBox muokattavaKayttaja;
	@FXML
	private Label muokattavaKayttajaLabel;
	@FXML
	private ComboBox<String> kayttajaBox;
	@FXML
	private Label uusiBudjettiLabel;
	@FXML
	private TextField uusiBudjettiField;
	@FXML
	private Button tallennaKayttajaButton;
	
	@FXML
	private VBox profiilinTyhjennys;
	@FXML
	private Label profiilinTyhjennysLabel;
	@FXML
	private ComboBox<String> tyhjennysBox;
	@FXML
	private Button tyhjennaButton;
	
	@FXML
	private VBox kategoriaAsetukset;
	@FXML
	private Label kategoriaAsetuksetLabel;
	@FXML
	private VBox muokattavaKategoria;
	@FXML
	private Label muokatavaKategoriaLabel;
	@FXML
	private ComboBox<String> kategoriaBox;
	@FXML
	private Label uusiKategoriaLabel;
	@FXML
	private TextField uusiKategoriaField;
	@FXML
	private Button tallennaUusiKategoria;
	
	@FXML
	private VBox poistettavaKategoria;
	@FXML
	private Label poistettavaKategoriaLabel;
	@FXML
	private ComboBox<String> poistettavaKategoriaBox;
	@FXML
	private Button poistaKategoriaButton;
	@FXML
	private Label muokkausInfo;
	
	private ViewHandler vh;
	private KayttajanHallinta kayttajanhallinta;
	
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
		
		initKayttajat();
		initKategoriat();
	}
	
	/**
	 * A method for changing the language of the graphical user interface.
	 */
	public void asetaKieli() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		kayttajaAsetuksetLabel.setText(english.getString("käyttäjäAsetukset"));
		muokattavaKayttajaLabel.setText(english.getString("muokattavaProfiili"));
		uusiBudjettiLabel.setText(english.getString("uusiBudjetti"));
		tallennaKayttajaButton.setText(english.getString("tallenna"));
		profiilinTyhjennysLabel.setText(english.getString("profiilinTyhjennys"));
		tyhjennaButton.setText(english.getString("tyhjennä"));
		kategoriaAsetuksetLabel.setText(english.getString("kategoriaAsetukset"));
		muokatavaKategoriaLabel.setText(english.getString("muokattavaKategoria"));
		uusiKategoriaLabel.setText(english.getString("uusiNimi"));
		tallennaUusiKategoria.setText(english.getString("tallenna"));
		poistettavaKategoriaLabel.setText(english.getString("poistettavaKategoria"));
		poistaKategoriaButton.setText(english.getString("poisto"));
		muokkausInfo.setText(english.getString("muokkausInfo"));
	}
	
	/**
	 * A method used for setting a new max budget for a selected user.
	 */
	public void tallennaUusiBudjetti() {
		double budjetti = Double.parseDouble(uusiBudjettiField.getText());
		int valittuKayttaja = kayttajaBox.getSelectionModel().getSelectedIndex() +1;
		
        vh.getKontrolleri().paivitaBudjetti(valittuKayttaja, budjetti);
        
        kayttajaBox.getSelectionModel().clearSelection();
        uusiBudjettiField.clear();
	}
	
	/**
	 * A method used for deleting all the data from a selected user.
	 */
	public void tyhjennaTiedot() {
		int valittuKayttaja = tyhjennysBox.getSelectionModel().getSelectedIndex() +1;
		vh.getKontrolleri().poistaKayttajanTiedot(valittuKayttaja);
		tyhjennysBox.getSelectionModel().clearSelection();
	}
	
	/**
	 * A method used for changing the name of a selected category from the active profile.
	 */
	public void tallennaUusiKategorianimi() {
		String vanhaNimi = kategoriaBox.getSelectionModel().getSelectedItem();
		Kategoria muokattavaKategoria = vh.getKontrolleri().getKategoria(vanhaNimi, kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki());
		String nimi = uusiKategoriaField.getText();
		vh.getKontrolleri().muokkaaKategoriaa(muokattavaKategoria.getKategoriaID(), nimi);
		
		uusiKategoriaField.clear();
		kategoriaBox.getSelectionModel().clearSelection();
		kategoriaBox.getItems().clear();
		kategoriaBox.getItems().addAll(vh.getKontrolleri().getKategorianimet(kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki()));
	}
	
	/**
	 * A method used for deleting a category from the active profile.
	 */
	public void poistaKategoria() {
		String poistettava = poistettavaKategoriaBox.getSelectionModel().getSelectedItem();
		Kategoria poistettavaKategoria = vh.getKontrolleri().getKategoria(poistettava, kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki());
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		
		if(vh.getKieli()) {
			int valinta = JOptionPane.showConfirmDialog(null, finnish.getString("kategorianPoistoVaroitus"), finnish.getString("harkitse"),JOptionPane.OK_CANCEL_OPTION);
			if(valinta == 0) {
				vh.getKontrolleri().poistaKategoria(poistettavaKategoria.getKategoriaID(), kayttajanhallinta.getKirjautunutKayttaja());
				kategoriaBox.getItems().remove(poistettava);
				poistettavaKategoriaBox.getItems().remove(poistettava);
				poistettavaKategoriaBox.getSelectionModel().clearSelection();
			}
		} else {
			int valinta = JOptionPane.showConfirmDialog(null, english.getString("kategorianPoistoVaroitus"), english.getString("harkitse"),JOptionPane.OK_CANCEL_OPTION);
			if(valinta == 0) {
				vh.getKontrolleri().poistaKategoria(poistettavaKategoria.getKategoriaID(), kayttajanhallinta.getKirjautunutKayttaja());
				kategoriaBox.getItems().remove(poistettava);
				poistettavaKategoriaBox.getItems().remove(poistettava);
				poistettavaKategoriaBox.getSelectionModel().clearSelection();
			}
		}
		
	}
	
	/**
	 * Initiates the user data for comboboxes.
	 */
	private void initKayttajat() {
		kayttajaBox.getItems().addAll(vh.getKontrolleri().getKayttajat());
		tyhjennysBox.getItems().addAll(vh.getKontrolleri().getKayttajat());
	}
	
	/**
	 * Initiates the category data for comboboxes.
	 */
	private void initKategoriat() {
		List<String> kategoriat = vh.getKontrolleri().getKategorianimet(kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki());
		
		kategoriaBox.getItems().addAll(kategoriat);
		kategoriaBox.getItems().remove("Yleinen");
		poistettavaKategoriaBox.getItems().addAll(kategoriat);
		poistettavaKategoriaBox.getItems().remove("Yleinen");
	}
}
