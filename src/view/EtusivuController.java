package view;

import org.json.JSONObject;

import controller.IKontrolleri;
import controller.Kontrolleri;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import kayttajanHallinta.KayttajanHallinta;

public class EtusivuController implements ViewController{
	@FXML
	private AnchorPane ap;
	@FXML
	private BorderPane bp;
	@FXML
	private Label label;
	
	private ViewHandler vh;
	
	private IKontrolleri kontrolleri;
	
	KayttajanHallinta kayttajanhallinta = KayttajanHallinta.getInstance();
	
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
		kontrolleri = vh.getKontrolleri();
		kayttajanhallinta.setKirjautunutKayttaja(kontrolleri.getKayttaja(kayttajanhallinta.lueKayttajaID()));
		try {
			displayOstoslista();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void displayOstoslista() throws Exception {
	    String responseJson = kontrolleri.sendOstoslistaRequest();
	    JSONObject response = new JSONObject(responseJson);
	    JSONObject message = response.getJSONObject("message");
	    String messageContent = message.getString("content");
	    String[] lists = messageContent.split("\\n\\n");
	    String[] shoppingListItems = lists[0].replace("Shopping List:\n", "").split("\\n- ");
	    String[] reminderListItems = lists[1].replace("Reminder List:\n", "").split("\\n- ");
	    
	    System.out.println("Shopping List:");
	    for (String item : shoppingListItems) {
	        System.out.println("- " + item);
	    }
	    
	    System.out.println("\nReminder List:");
	    for (String item : reminderListItems) {
	        String[] parts = item.split(" - Due date: ");
	        System.out.println("- " + parts[0] + " (Due date: " + parts[1] + ")");
	    }
	}


}
