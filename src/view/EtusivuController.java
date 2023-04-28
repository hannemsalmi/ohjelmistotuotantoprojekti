package view;

import java.util.ResourceBundle;

import org.json.JSONObject;

import controller.IKontrolleri;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kayttajanHallinta.KayttajanHallinta;

/**
 * EtusivuController implements a controller class for Etusivu.fxml.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public class EtusivuController implements ViewController{
	@FXML
	private AnchorPane ap;
	@FXML
	private BorderPane bp;
	@FXML
	private HBox sisalto;
	@FXML
	private VBox tervetuloa;
	@FXML
	private Label tervetuloaLabel;
	@FXML
	private VBox profiili;
	@FXML
	private Label profiiliLabel;
	@FXML
	private ComboBox<String> profiiliBox;
	@FXML
	private VBox uusiProfiili;
	@FXML
	private Label uusiProfiiliLabel;
	@FXML
	private Button uusiProfiiliButton;
	
	@FXML
	private VBox ostoslista;
	@FXML
	private Label ostoslistaLabel;
	@FXML
	private VBox shoppingListVBox;
	
	@FXML
	private VBox muistilista;
	@FXML
	private Label muistilistaLabel;
	@FXML
	private VBox reminderListVBox;
	
	
	private ViewHandler vh;
	private IKontrolleri kontrolleri;
	
	KayttajanHallinta kayttajanhallinta;
	
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
		
		kontrolleri = vh.getKontrolleri();
		kayttajanhallinta = vh.getKayttajanhallinta();
		kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(kayttajanhallinta.lueKayttajaID()));
		paivitaTervehdys();
		initProfiiliBox();
		
		try {
			displayOstoslista();
		} catch (Exception e) {
			System.out.println("Ei toiminut listojen avaus");
		}
	}
	
	/**
	 * A method for changing the language of the graphic user interface.
	 */
	public void asetaKieli() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		profiiliLabel.setText(english.getString("profiiliValinta"));
		uusiProfiiliLabel.setText(english.getString("luoProfiiliTeksti"));
		uusiProfiiliButton.setText(english.getString("luoProfiili"));
		ostoslistaLabel.setText(english.getString("ostoslista"));
		muistilistaLabel.setText(english.getString("muistilista"));
	}
	
	/**
	 * A method which is used to creating a shopping list and a reminder list based on the expences of the user.
	 * AI api is used for generating the lists.
	 */
	public void displayOstoslista() {
	    Task<Void> task = new Task<Void>() {
	        @Override
	        protected Void call() throws Exception {
	        	String responseJson = kontrolleri.sendOstoslistaRequest();
	            JSONObject response = new JSONObject(responseJson);
	            String messageContent = response.getJSONObject("message").getString("content");

	            // Extract shopping and reminder lists as strings
	            String shoppingListStr = messageContent.substring(messageContent.indexOf("shopping list:") + 14, messageContent.indexOf("),") + 1);
	            String reminderListStr = messageContent.substring(messageContent.indexOf("reminder list:") + 14);

	            // Split the shopping list string into items
	            String[] shoppingListItems = shoppingListStr.split(", item=");

	            Platform.runLater(() -> {
	                shoppingListVBox.getChildren().clear();
	                reminderListVBox.getChildren().clear();

	                // Display the shopping list items
	                System.out.println("Shopping List:");
	                for (String item : shoppingListItems) {
	                    item = item.replace("item=", "").replace(")", "").trim();
	                    System.out.println("- " + item);
	                    shoppingListVBox.getChildren().add(new Label("- " + item));
	                }

	                // Split the reminder list string into items
	                String[] reminderListItems = reminderListStr.split("\\), \\(");

	                // Display the reminder list items
	                System.out.println("\nReminder List:");
	                for (String item : reminderListItems) {
	                    item = item.replace("(", "").replace(")", "");
	                    String[] parts = item.split(", ");
	                    String task = parts[0].replace("item=", "").trim();
	                    String price = parts[1].replace("price=", "").replace(" e", "").trim() + "€";
	                    String dueDate = parts[2].replace("duedate=", "").trim();

	                    System.out.println("- " + task + " (" + price + ", " + dueDate + ")");
	                    reminderListVBox.getChildren().add(new Label("- " + task + " (" + price + ", " + dueDate + ")"));
	                }
	            });

	            return null;
	        }
	    };

	    Thread thread = new Thread(task);
	    thread.setDaemon(true);
	    thread.start();
	}
	
	/**
	 * A method for selecting the active profile.
	 */
	public void valitseKayttaja() {
		int valittuKayttaja = profiiliBox.getSelectionModel().getSelectedIndex() +1;
        kayttajanhallinta.kirjoitaKayttajaID(valittuKayttaja);
        kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(valittuKayttaja));
        System.out.println("Logging in user: " + valittuKayttaja);
        paivitaTervehdys();
	}
	
	/**
	 * A method used for creating a new user profile.
	 */
	public void luoUusiKayttaja() {
		vh.luoUusiKayttaja();
		vh.paivitaKayttaja();
		paivitaTervehdys();
	}
	
	/**
	 * A method which updates the welcome message based on the profile active right now.
	 */
	public void paivitaTervehdys() {
		ResourceBundle english = ResourceBundle.getBundle("Bundle_English");
		ResourceBundle finnish = ResourceBundle.getBundle("Bundle_Finnish");
		if(vh.getKieli()) {
			tervetuloaLabel.setText(finnish.getString("tervetuloa") + kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki());
		} else {
			tervetuloaLabel.setText(english.getString("tervetuloa") + kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki());
		}
		profiiliBox.getSelectionModel().select(kayttajanhallinta.getKirjautunutKayttaja().getNimimerkki());
	}
	
	/**
	 * Initiates the data in user profile combobox.
	 */
	private void initProfiiliBox() {
		profiiliBox.getItems().addAll(kontrolleri.getKayttajat());
	}
}
