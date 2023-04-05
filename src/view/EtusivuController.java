package view;

import org.json.JSONArray;
import org.json.JSONObject;

import controller.IKontrolleri;
import controller.Kontrolleri;
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

	        	    // Extract the shopping list and reminder list as JSON arrays
	        	    int shoppingListStart = messageContent.indexOf('[');
	        	    int shoppingListEnd = messageContent.indexOf(']');
	        	    String shoppingListJson = messageContent.substring(shoppingListStart, shoppingListEnd + 1);

	        	    int reminderListStart = messageContent.lastIndexOf('[');
	        	    int reminderListEnd = messageContent.lastIndexOf(']');
	        	    String reminderListJson = messageContent.substring(reminderListStart, reminderListEnd + 1);

	        	    JSONArray shoppingListArray = new JSONArray(shoppingListJson);
	        	    JSONArray reminderListArray = new JSONArray(reminderListJson);

	            Platform.runLater(() -> {
	                shoppingListVBox.getChildren().clear();
	                reminderListVBox.getChildren().clear();

	             // Display the shopping list items
	                System.out.println("Shopping List:");
	                for (int i = 0; i < shoppingListArray.length(); i++) {
	                    String item = shoppingListArray.getString(i);
	                    System.out.println("- " + item);
	                    shoppingListVBox.getChildren().add(new Label("- " + item));
	                }

	                // Display the reminder list items
	                System.out.println("\nReminder List:");
	                for (int i = 0; i < reminderListArray.length(); i++) {
	                    JSONObject reminder = reminderListArray.getJSONObject(i);
	                    String task = reminder.getString("bill_name");
	                    String dueDate = reminder.getString("due_date");
	                    System.out.println("- " + task + " (Due date: " + dueDate + ")");
	                    reminderListVBox.getChildren().add(new Label("- " + task + " (Due date: " + dueDate + ")"));
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
