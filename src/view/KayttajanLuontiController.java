package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kayttajanHallinta.KayttajanHallinta;

public class KayttajanLuontiController implements ViewController {

	@FXML
	private AnchorPane ap;
	@FXML
	private BorderPane bp;
	@FXML
	private VBox sisalto;
	
	@FXML
	private VBox kayttaja;
	@FXML
	private Label kayttajaLabel;
	@FXML
	private TextField kayttajaField;
	
	@FXML
	private VBox budjetti;
	@FXML
	private Label budjettiLabel;
	@FXML
	private TextField budjettiField;
	
	@FXML
	private VBox tallenna;
	@FXML
	private Button tallennaButton;
	
	private ViewHandler vh;
	private KayttajanHallinta kayttajanhallinta;
	
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
		kayttajanhallinta = vh.getKayttajanhallinta();
	}

	public void luoUusiKayttaja() {
		String username = kayttajaField.getText();
        double budjetti = Double.parseDouble(budjettiField.getText());
        if (!username.isEmpty()) {
            vh.getKontrolleri().lisaaKayttaja(username, budjetti);
            vh.getKontrolleri().lisaaKategoria("Yleinen", username);
            kayttajanhallinta.kirjoitaKayttajaID(1);
            kayttajanhallinta.setKirjautunutKayttaja(vh.getKontrolleri().getKayttaja(kayttajanhallinta.lueKayttajaID()));
            Stage stage = vh.getKayttajaStage();
            stage.close();
        }
	}
}
