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
import kayttajanHallinta.KayttajanHallinta;
import model.Kategoria;
import model.Kayttaja;

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
	
	private ViewHandler vh;
	private KayttajanHallinta kayttajanhallinta;
	
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
		kayttajanhallinta = vh.getKayttajanhallinta();
		
		initKayttajat();
		initKategoriat();
	}
	
	public void tallennaUusiBudjetti() {
		double budjetti = Double.parseDouble(uusiBudjettiField.getText());
		int valittuKayttaja = kayttajaBox.getSelectionModel().getSelectedIndex() +1;
		
        vh.getKontrolleri().paivitaBudjetti(valittuKayttaja, budjetti);
        
        kayttajaBox.getSelectionModel().clearSelection();
        uusiBudjettiField.clear();
	}
	
	public void tyhjennaTiedot() {
		int valittuKayttaja = tyhjennysBox.getSelectionModel().getSelectedIndex() +1;
		vh.getKontrolleri().poistaKayttajanTiedot(valittuKayttaja);
		tyhjennysBox.getSelectionModel().clearSelection();
	}
	
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
	
	public void poistaKategoria() {
		String poistettava = poistettavaKategoriaBox.getSelectionModel().getSelectedItem();
		Kategoria poistettavaKategoria = vh.getKontrolleri().getKategoria(poistettava, kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki());
		
		int valinta = JOptionPane.showConfirmDialog(null, "Haluatko varmasti poistaa kategorian? Poistetun kategorian kulut siirtyvät yleiseen kategoriaan", "Mieti vielä kerran...",JOptionPane.OK_CANCEL_OPTION);
		if(valinta == 0) {
			vh.getKontrolleri().poistaKategoria(poistettavaKategoria.getKategoriaID(), kayttajanhallinta.getKirjautunutKayttaja());
			kategoriaBox.getItems().remove(poistettava);
			poistettavaKategoriaBox.getItems().remove(poistettava);
			poistettavaKategoriaBox.getSelectionModel().clearSelection();
		}
	}
	
	private void initKayttajat() {
		kayttajaBox.getItems().addAll(vh.getKontrolleri().getKayttajat());
		tyhjennysBox.getItems().addAll(vh.getKontrolleri().getKayttajat());
	}
	
	private void initKategoriat() {
		List<String> kategoriat = vh.getKontrolleri().getKategorianimet(kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki());
		
		kategoriaBox.getItems().addAll(kategoriat);
		kategoriaBox.getItems().remove("Yleinen");
		poistettavaKategoriaBox.getItems().addAll(kategoriat);
		poistettavaKategoriaBox.getItems().remove("Yleinen");
	}
}
