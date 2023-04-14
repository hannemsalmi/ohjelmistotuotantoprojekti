package view;

import java.util.List;

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

	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
		kayttajanhallinta = vh.getKayttajanhallinta();
		
		initKategoriaBox();
	}

	public void tallennaNimiHintaKuvaus() {
		try {
			String nimi = nimiField.getText();
    		double hinta = Double.parseDouble(hintaField.getText());
	        String kuvaus = kuvausField.getText();
	        vh.getKontrolleri().muokkaaKulua(kuluId, hinta, nimi, kuvaus);
		} catch (NumberFormatException nfe) {
			System.out.println("Numeroarvojen sijasta yritettiin syöttää muuta...");
			JOptionPane.showConfirmDialog(null, "Syötä numeroarvot niitä pyydettäessä.", "Syöttövirhe", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			System.out.println("Joku vikana arvoja syötettäessä...");
			JOptionPane.showConfirmDialog(null, "Syötä oikeantyyppiset arvot.", "Syöttövirhe", JOptionPane.ERROR_MESSAGE);
		}
		
		teeLopputoimet();
	}
	
	public void tallennaKategoria() {
		String kategoriaNimi = kategoriaBox.getSelectionModel().getSelectedItem();
		Kategoria uusiKategoria = vh.getKontrolleri().getKategoria(kategoriaNimi, kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki());
		vh.getKontrolleri().muutaKulunKategoria(kuluId, uusiKategoria);
		
		teeLopputoimet();
	}
	
	public void poistaKulu() {
		int valinta = JOptionPane.showConfirmDialog(null, "Haluatko varmasti poistaa kulun?", "Mieti vielä kerran...",JOptionPane.OK_CANCEL_OPTION);
		if(valinta == 0) {
    		vh.getKontrolleri().poistaKulu(kuluId);
		}
		
		teeLopputoimet();
	}
	
	private void initKategoriaBox() {
		Kayttaja kayttaja = kayttajanhallinta.getKirjautunutKayttaja();
		List<String> kaikkiKategoriat = vh.getKontrolleri().getKategorianimet(kayttaja.getNimimerkki());
	    kategoriaBox.getItems().addAll(kaikkiKategoriat);
	}
	
	private void teeLopputoimet() {
		ViewController aktiivinen = vh.getAktiivinen();
		((KulutController) aktiivinen).paivitaKulut();
		
		Stage stage = vh.getMuokkaaKuluaStage();
		stage.close();
	}

	public void setKuluId(int kuluId) {
		this.kuluId = kuluId;
	}
}
