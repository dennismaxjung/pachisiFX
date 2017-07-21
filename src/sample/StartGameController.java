package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import sample.helpers.SerializeHelper;
import sample.models.Board;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dennis on 16.07.2017.
 */
public class StartGameController implements Initializable {
    @FXML
    public Button start;



    @FXML
    public void loadGame(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Window theStage = source.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        File tmp = fileChooser.showOpenDialog(theStage);
        Main.board = SerializeHelper.<Board>readBinary(tmp);
        playerCount.setValue(Main.board.getPlayerCount());
        Main.gameLoaded = true;
        playerCount.setDisable(true);

    }


    @FXML
    public ChoiceBox playerCount;

    @FXML
    public CheckBox autoForced;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }



}
