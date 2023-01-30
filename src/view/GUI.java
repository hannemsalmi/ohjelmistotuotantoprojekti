package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.IKontrolleriVtoM;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Kategoria;
import model.Kayttaja;
import model.Kulu;
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
	List<Kulu> kulut;
	TextField kulutField;
	
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
		
		
		kulutField = new TextField();
		kulut = kontrolleri.getKulut();
		setKulut(kulut);
		
		ostosLabel = new Label("Ostos");
		hintaLabel = new Label("Hinta");
		paivamaaraLabel = new Label("Päivämäärä");
		
		lisaaButton = new Button("Lisää ostos");
		lisaaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				kontrolleri.lisaaKulu();
			}
		});
		
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
	
	public Kulu getKulu( ) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			return new Kulu(ostosField.getText(), Double.parseDouble(hintaField.getText()), LocalDate.parse(paivamaaraField.getText(), formatter), 
					new Kategoria("testikategoria"),new Kayttaja("testi", 1000.00), "testikuvaus");
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public void setKulut(List<Kulu> kulut) {
		String kulutteksti = kulut.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
		this.kulutField.setText(kulutteksti);
	}
	
		
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}