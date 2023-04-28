import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewHandler;

/**
 * A main class used for launching the new graphical user interface built with SceneBuilder.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public class MainView extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewHandler vh = new ViewHandler(primaryStage);
		vh.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
