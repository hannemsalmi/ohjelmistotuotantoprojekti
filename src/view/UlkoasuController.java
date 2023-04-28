package view;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * UlkoasuController implements a controller class for Ulkoasu.fxml.
 * This class includes the navigation buttons of the left side of the graphical user interface and the name tab.
 * In ViewHandler, this class works as the variable called root.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
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

	/**
	 * Initiates UlkoasuController when it is opened.
	 * @param ViewHandler The class which controls the view changes and functions.
	 */
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
	}
	
	/**
	 * A method which opens the Etusivu.fxml into the central part of the borderpane of this class.
	 */
	@FXML
	private void clickEtusivu() {
		vh.naytaEtusivu();
	}
	
	/**
	 * A method which opens the Kulut.fxml into the central part of the borderpane of this class.
	 */
	@FXML
	private void clickKulut() {
		vh.naytaKulut();
	}
	
	/**
	 * A method which opens the Diagrammi.fxml into the central part of the borderpane of this class.
	 */
	@FXML
	private void clickDiagrammi() {
		vh.naytaDiagrammi();
	}
	
	/**
	 * A method which opens the Ennuste.fxml into the central part of the borderpane of this class.
	 */
	@FXML
	private void clickEnnuste() {
		vh.naytaEnnuste();
	}
	
	/**
	 * A method which opens the Asetukset.fxml into the central part of the borderpane of this class.
	 */
	@FXML
	private void clickAsetukset() {
		vh.naytaAsetukset();
	}
	
	/**
	 * A method for changing the language of the graphical user interface to be English.
	 */
	@FXML
	private void kieliEnglanniksi() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		etusivu.setText(english.getString("etusivu"));
		kulut.setText(english.getString("kulut"));
		kuludiagrammi.setText(english.getString("kuludiagrammi"));
		ennuste.setText(english.getString("ennuste"));
		asetukset.setText(english.getString("asetukset"));
		vh.setKieli(false);
	}
	
	/**
	 * A method for changing the language of the graphical user interface to be Finnish.
	 */
	@FXML
	private void kieliSuomeksi() {
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		etusivu.setText(finnish.getString("etusivu"));
		kulut.setText(finnish.getString("kulut"));
		kuludiagrammi.setText(finnish.getString("kuludiagrammi"));
		ennuste.setText(finnish.getString("ennuste"));
		asetukset.setText(finnish.getString("asetukset"));
		vh.setKieli(true);
	}
}
