import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewHandler;

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
