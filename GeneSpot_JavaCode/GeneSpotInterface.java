/**
 * Created by austinward on 12/16/16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GeneSpotInterface extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("GeneSpotGUI.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("The Gene Spot");
            primaryStage.setScene(scene);
            primaryStage.show(); // raises curtain for graph

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
