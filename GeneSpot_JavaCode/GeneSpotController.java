/**
 * Created by austinward on 12/17/16.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GeneSpotController{


    private final ObservableList<String> searchByChoices = FXCollections.observableArrayList("Species", "Kingdom", "Type",
            "Accesion #", "tax ID");

    @FXML
    private ChoiceBox<String> searchBy;

    @FXML
    private void initialize(){

        searchBy.setValue("Species");
        searchBy.setItems(searchByChoices);
    }
}

