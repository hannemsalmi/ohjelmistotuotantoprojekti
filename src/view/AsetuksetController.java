package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
	private Label uusiNimiLabel;
	@FXML
	private TextField uusiNimiField;
	@FXML
	private Label uusiBudjettiLabel;
	@FXML
	private TextField uusiBudjettiField;
	@FXML
	private Button tallennaKayttajaButton;
	
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
	
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
	}
}
