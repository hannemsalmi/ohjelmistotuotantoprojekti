package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Täältä löytyy navigointiboxin toiminnallisuudet ja sovelluksen nimibox
//ViewHandlerissa tämän tiedoston sisältö nimellä root 
public class UlkoasuController implements ViewController{
	@FXML
	private BorderPane bp;
	@FXML
	private HBox hbox;
	@FXML
	private Label budjettisovellus;
	
	@FXML
	private VBox vbox;
	@FXML
	private Button etusivu;
	@FXML
	private Button kulut;
	@FXML
	private Button kuludiagrammi;
	@FXML
	private Button ennuste;
	@FXML
	private Button asetukset;
	
	private ViewHandler vh;
	
	public UlkoasuController() {
	}

	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
	}
	
	@FXML
	private void clickEtusivu() {
		vh.naytaEtusivu();
	}
	
	@FXML
	private void clickKulut() {
		vh.naytaKulut();
	}
	
	@FXML
	private void clickDiagrammi() {
		vh.naytaDiagrammi();
	}
	
	@FXML
	private void clickEnnuste() {
		vh.naytaEnnuste();
	}
	
	@FXML
	private void clickAsetukset() {
		vh.naytaAsetukset();
	}
}
