package view;

import controller.Kontrolleri;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EtusivuController implements ViewController{
	@FXML
	private AnchorPane ap;
	@FXML
	private BorderPane bp;
	@FXML
	private Label label;
	
	private ViewHandler vh;
	
	private Kontrolleri kontrolleri;
	
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
	}
}
