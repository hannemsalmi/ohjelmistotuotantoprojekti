package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class DiagrammiController implements ViewController {
	@FXML
	private AnchorPane ap;
	@FXML
	private BorderPane bp;
	@FXML
	private Label label;
	
	private ViewHandler vh;
	
	@Override
	public void init(ViewHandler viewHandler) {
		vh = viewHandler;
	}
}
