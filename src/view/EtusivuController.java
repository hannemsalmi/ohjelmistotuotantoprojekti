package view;

import org.json.JSONObject;

import controller.IKontrolleri;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import kayttajanHallinta.KayttajanHallinta;

public class EtusivuController implements ViewController{
	@FXML
	private AnchorPane ap;
	@FXML
	private BorderPane bp;
	@FXML
	private Label label;
	@FXML
	private VBox shoppingListVBox;

	@FXML
	private VBox reminderListVBox;
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

}
