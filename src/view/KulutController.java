package view;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
	private TextField syotaPaivamaara;
	
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
	
	private ViewHandler vh;
	
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
		if(!(vh.getKieli())) {
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
	}
}
