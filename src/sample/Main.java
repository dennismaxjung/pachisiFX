package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;

import sample.helpers.PlayerHelper;
import sample.helpers.SerializeHelper;
import sample.models.Board;
import sample.models.Player;
import sample.models.PlayerColor;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Main extends Application {


    public static int playerCount;
    public static Board board;
    public static boolean autoForced = true;
    public static boolean gameLoaded = false;



    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("playGame.fxml"));
        FXMLLoader loaderGui = new FXMLLoader(getClass().getResource("startGame.fxml"));
        Parent parentroot = loaderGui.load();

        StartGameController startGameController = loaderGui.getController();
        startGameController.start.setOnAction(event -> {
            if(!startGameController.playerCount.getSelectionModel().isEmpty()){
                autoForced = startGameController.autoForced.isSelected();
                playerCount = (int) startGameController.playerCount.getValue();
                if(!gameLoaded) {
                    board = new Board(generatePlayers((int) startGameController.playerCount.getValue()));
                }
                Parent game = null;

                try {
                    game = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                primaryStage.setScene(new Scene(game,primaryStage.getScene().getWidth(),primaryStage.getScene().getHeight()));

            }
        });
        primaryStage.setTitle("Mensch Ärgere Dich Nicht!");
        Scene startScene = new Scene(parentroot,515,515);
        primaryStage.setScene(startScene);

        //primaryStage.setResizable(false);
        primaryStage.setMinHeight(515);
        primaryStage.setMinWidth(515);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd_hh-mm-ss");
       primaryStage.setOnCloseRequest(event -> {
           if(board != null) {
               Stage stage = (Stage) event.getSource();
               javafx.stage.Window theStage = stage.getScene().getWindow();

               FileChooser fileChooser = new FileChooser();
               fileChooser.setInitialFileName("pachisi_" + LocalDateTime.now().format(formatter) + ".save");

               File tmp = fileChooser.showSaveDialog(theStage);
               if (tmp != null) {
                   SerializeHelper.saveBinary(board, tmp);
               }
           }
        });
        primaryStage.show();


        //Game game = new Game();
        //game.StartNewGame();


    }


    private static List<Player> generatePlayers(int count)
    {
        List<Player> tmp = new ArrayList<>();
        int i;
        for(i = 0; i < count ; i++)
        {
            tmp.add(PlayerHelper.createPlayer("Player", PlayerColor.values()[i]));
        }
        return tmp;
    }


    public static void main(String[] args) {
        launch(args);

    }
}
