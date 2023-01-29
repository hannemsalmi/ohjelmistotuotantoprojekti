package view;

import controller.IKontrolleriVtoM;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import controller.Kontrolleri;

public class GUI extends Application implements IGUI{
	private IKontrolleriVtoM kontrolleri;
	Label ostosLabel;
	Label hintaLabel;
	Label paivamaaraLabel;
	Button lisaaButton;
	TextField ostosField;
	TextField hintaField;
	TextField paivamaaraField;
	
	public void init() {
		kontrolleri = new Kontrolleri(this);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Budjettisovellus");
			HBox root = luoHBox();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HBox luoHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(20, 15, 20, 15));
		hbox.setSpacing(10);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 20, 10, 20));
		
		ostosLabel = new Label("Ostos");
		hintaLabel = new Label("Hinta");
		paivamaaraLabel = new Label("Päivämäärä");
		
		lisaaButton = new Button("Lisää ostos");
		
		ostosField = new TextField();
		hintaField = new TextField();
		paivamaaraField = new TextField();
		
		grid.add(ostosLabel, 0, 0);
		grid.add(ostosField, 0, 1);
		grid.add(hintaLabel, 1, 0);
		grid.add(hintaField, 1, 1);
		grid.add(paivamaaraLabel, 2, 0);
		grid.add(paivamaaraField, 2, 1);
		grid.add(lisaaButton, 0, 2);
		
		hbox.getChildren().add(grid);
		
		return hbox;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}