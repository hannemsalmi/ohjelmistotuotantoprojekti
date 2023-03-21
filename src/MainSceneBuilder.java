import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSceneBuilder extends Application{
	private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        
    	avaaUlkoasu();
    }

    public void avaaUlkoasu() {
        try {
            Parent ulkoasu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/Ulkoasu.fxml")));
            primaryStage.setTitle("Budjettisovellus");
            primaryStage.setScene(new Scene(ulkoasu, 800, 500));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

