import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainSceneBuilder extends Application{
	private Stage primaryStage;
	double x,y = 0;

    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        
    	avaaUlkoasu();
    }

    public void avaaUlkoasu() {
        try {
            Parent ulkoasu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/Ulkoasu.fxml")));
            Scene scene = new Scene(ulkoasu);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            
            //sovelluksen liikuttelun metodit
            ulkoasu.setOnMousePressed(event -> {
            	x = event.getSceneX();
            	y = event.getSceneY();
            });
            ulkoasu.setOnMouseDragged(event -> {
            	primaryStage.setX(event.getScreenX() - x);
            	primaryStage.setY(event.getScreenY() - y);
            });
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

