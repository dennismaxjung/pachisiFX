package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import sample.helpers.SerializeHelper;
import sample.helpers.StaticHelpers;
import sample.models.Board;
import sample.models.Figure;
import sample.models.Player;
import sample.models.PlayerColor;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlayGameController implements Initializable {


    private List<List<Label>> playerStones = new ArrayList<>();

    @FXML
    private GridPane board;

    @FXML
    private StackPane showDice;

    private Dice dice;
    private long player =0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dice = new Dice(showDice);


        for (int i = 0; i < Main.playerCount; i++)
        {
            List<Label> newPlayerStones = new ArrayList<>();
            int k = 0;
            for (k = 1; k < 5; k++) {
                newPlayerStones.add(StaticHelpers.generateNewPlayerStone(PlayerColor.values()[i], k));
            }
            playerStones.add(newPlayerStones);
        }

        //Figure tmp = Main.board.getPlayer(PlayerColor.Blue).takeFigureFromStart();
        //Main.board.fields[10] = tmp;
        reloadBoard();
        NewTurn(PlayerColor.Blue);
        //showDice.setOnMouseClicked(event -> Main.board = SerializeHelper.<Board>readBinary("D:\\board.ser"));
        //SerializeHelper.test(Main.board);
        //SerializeHelper.read();
        //showDice.setOnMouseClicked(event ->  NewTurn(PlayerColor.values()[(int)(player%Main.playerCount)]));
        //showDice.setOnMouseClicked(event -> dice.StartDiceing((int) (6.0 * Math.random()) + 1, StaticHelpers.getColor(PlayerColor.values()[test++ % Main.board.getPlayerCount()])));
    }

        private void moveFigure(Label figure,int field)
        {

            board.getChildren().remove(figure);
            board.add(figure,StaticHelpers.getField(field%40).x,StaticHelpers.getField(field%40).y);
        }

        private void reloadBoard()
        {
            for (List<Label> player :playerStones){
                for (Label stone: player)
                {
                    board.getChildren().remove(stone);
                }
            }

            // ZIELE
            for (int i = 0; i < Main.playerCount; i++)
            {
                for(int ta = 0; ta < 4; ta++) {

                    if (Main.board.getPlayer(PlayerColor.values()[i]) != null && Main.board.getPlayer(PlayerColor.values()[i]).isFigureOnTargetPosition(ta+1)) {

                        board.add(playerStones.get(StaticHelpers.getIdOfPlayerColor(PlayerColor.values()[i])).get(Main.board.getPlayer(PlayerColor.values()[i]).target[ta].getId()-1),StaticHelpers.getTarget(ta,PlayerColor.values()[i]).x,StaticHelpers.getTarget(ta,PlayerColor.values()[i]).y);

                    }
                }
            }

            // HOMES
            for (int i = 0; i < Main.playerCount; i++)
            {
                for (Figure fig:Main.board.getPlayer(PlayerColor.values()[i]).start)
                {
                    board.add(playerStones.get(StaticHelpers.getIdOfPlayerColor(PlayerColor.values()[i])).get(fig.getId()-1),StaticHelpers.getStart(fig.getId()-1,PlayerColor.values()[i]).x,StaticHelpers.getStart(fig.getId()-1,PlayerColor.values()[i]).y);

                }
                /*for(int ta = 0; ta < 4; ta++) {

                    if (Main.board.getPlayer(PlayerColor.values()[i]) != null && Main.board.getPlayer(PlayerColor.values()[i]).start.size() > ta) {

                        board.add(playerStones.get(StaticHelpers.getIdOfPlayerColor(PlayerColor.values()[i])).get(Main.board.getPlayer(PlayerColor.values()[i]).start.get(ta).getId()-1),StaticHelpers.getStart(ta,PlayerColor.values()[i]).x,StaticHelpers.getStart(ta,PlayerColor.values()[i]).y);

                    }
                }*/
            }


            // Spielbrett
            int i;
            for(i =0; i< Main.board.fields.length; i++)
            {
                if(Main.board.fields[i] != null)
                {
                    Figure fig = Main.board.fields[i];

                    PlayerColor test = fig.getColor();

                    int tmp = StaticHelpers.getIdOfPlayerColor(fig.getColor());

                    List<Label> tmp2 = playerStones.get(StaticHelpers.getIdOfPlayerColor(fig.getColor()));

                    Paint tmpp = ((Circle)tmp2.get(0).getGraphic()).getFill();

                    board.add(playerStones.get(StaticHelpers.getIdOfPlayerColor(fig.getColor())).get(fig.getId()-1),StaticHelpers.getField(i).x,StaticHelpers.getField(i).y);
                }
            }
            showDice.toFront();
        }


        private void NewTurn(PlayerColor currentPlayerColor)
        {

            this.currentPlayer = Main.board.getPlayer(currentPlayerColor);
            reloadBoard();

            // WIN?

            for (int i = 0; i < Main.playerCount; i++)
            {
                int targetcount = 0;
                for(int ta = 1; ta <= 4; ta++) {

                    if (Main.board.getPlayer(PlayerColor.values()[i]).isFigureOnTargetPosition(ta)) {
                        targetcount++;
                    }
                }
                if(targetcount >= 4)
                {

                    System.out.println("GEWONNENNNNNNNNNN!!!!!");

                    return;
                }

            }


            //Main.playerInterfac.turnOfPlayer(currentPlayer);

            if(countDice <= 3 && throwDiceAgain)
            {
                throwDiceAgain = false;
                System.out.println("Player: "+ currentPlayerColor.toString()+" - Versuch: " + countDice + " - Again: " + throwDiceAgain);
                //Main.playerInterfac.printBoard(board);
                int diced = currentPlayer.roleTheDice();
                dice.StartDiceing(diced,StaticHelpers.getColor(currentPlayerColor));
                dice.addMyEventListener(null);
                dice.addMyEventListener(e -> {
                    checkMoves(diced);
                    //dice.addMyEventListener(null);
                });
            }
            else{
                player++;
                System.out.println("Nächster Spieler ist dran: ");
                if(player >= Main.playerCount)
                {
                    player = 0;
                }
                countDice = 1;
                throwDiceAgain = true;
                NewTurn(PlayerColor.values()[(int)(player%Main.playerCount)]);
            }



        }


    private void checkMoves(int dice)
    {
        reloadBoard();
        if (dice == 6)
        {
            throwDiceAgain = true;
        }


        // Wenn Noch figuren zuhause sind und das startFeld leer ist
        if (dice == 6 && !currentPlayer.isStartEmpty() && (Main.board.isFieldEmpty(currentPlayer.getStartNr()) || Main.board.isFieldOtherPlayer(currentPlayer.getStartNr(),currentPlayer.getColor()))) {
            // Nehme neue figur aus dem Zuhause und setze es auf start

            Figure tmpFig = currentPlayer.takeFigureFromStart();

            Main.board.setFigureToField(currentPlayer.getStartNr(), tmpFig);

            if(Main.autoForced) {
                NewTurn(PlayerColor.values()[(int) (player % Main.playerCount)]);
            }
            else {
                Label figLabel = playerStones.get(StaticHelpers.getIdOfPlayerColor(tmpFig.getColor())).get(tmpFig.getId() - 1);
                Timeline flasher = new Timeline(
                        new KeyFrame(javafx.util.Duration.seconds(0.5), e -> {
                            StaticHelpers.markStoneAsActive(figLabel);
                        }),
                        new KeyFrame(javafx.util.Duration.seconds(1.0), e -> {
                            StaticHelpers.markStoneAsInActive(figLabel);
                        })
                );

                flasher.setCycleCount(Animation.INDEFINITE);
                flasher.play();

                figLabel.setOnMouseClicked(event -> {
                    figLabel.setOnMouseClicked(null);
                    flasher.stop();
                    StaticHelpers.markStoneAsInActive(figLabel);
                    reloadBoard();
                    NewTurn(PlayerColor.values()[(int) (player % Main.playerCount)]);
                });
            }

        }
        // Wenn noch figuren zuhause sind und der start noch blockiert ist von einer eigenen figur
        else if(/*dice == 6 && */!currentPlayer.isStartEmpty() && !Main.board.isFieldEmpty(currentPlayer.getStartNr()) && !Main.board.isFieldOtherPlayer(currentPlayer.getStartNr(),currentPlayer.getColor()))
        {
            int nextPosition = currentPlayer.getStartNr()+dice;
            if(Main.board.isFieldEmpty(nextPosition) || Main.board.isFieldOtherPlayer(nextPosition,currentPlayer.getColor()))
            {

                Figure tmpFig = Main.board.getFigureOfField(currentPlayer.getStartNr());
                Main.board.setFigureToField(nextPosition, tmpFig);

                if(Main.autoForced) {
                    NewTurn(PlayerColor.values()[(int) (player % Main.playerCount)]);
                }
                else {
                    Label figLabel = playerStones.get(StaticHelpers.getIdOfPlayerColor(tmpFig.getColor())).get(tmpFig.getId() - 1);
                    Timeline flasher = new Timeline(
                            new KeyFrame(javafx.util.Duration.seconds(0.5), e -> {
                                StaticHelpers.markStoneAsActive(figLabel);
                            }),
                            new KeyFrame(javafx.util.Duration.seconds(1.0), e -> {
                                StaticHelpers.markStoneAsInActive(figLabel);
                            })
                    );

                    flasher.setCycleCount(Animation.INDEFINITE);
                    flasher.play();

                    figLabel.setOnMouseClicked(event -> {
                        figLabel.setOnMouseClicked(null);
                        flasher.stop();
                        StaticHelpers.markStoneAsInActive(figLabel);
                        reloadBoard();

                        NewTurn(PlayerColor.values()[(int) (player % Main.playerCount)]);
                    });
                }

            }
            else
            {
                letPlayerChoose(dice);
            }


            // Bewege figur von start weg, wenn diese jeoch eine eigene schlagen würde, dann bewege diese (rekursiv)
            // oder zwinge bewegung nur wenn nicht dieses ziel wieder von einer anderen blockiert wird

        }
        // Wenn keine spieler auf dem feld ist und nicht sowieso schon nochmal würfeln darf, darf er nochmal würfeln
        else if(Main.board.playerHasNoFiguresOnFields(currentPlayer.getColor()) && throwDiceAgain == false)
        {
            throwDiceAgain = true;
            countDice ++;
            NewTurn(PlayerColor.values()[(int)(player%Main.playerCount)]);
        }
        // Wenn ein schlage vorgang erzwungen werden muss
        else if(Main.board.playerHasToBeat(dice,currentPlayer.getColor()).size() > 0) {
            List<Figure> beatMoveFigures = Main.board.playerHasToBeat(dice, currentPlayer.getColor());
            // überprüfe ob es nur eins ist
            if (beatMoveFigures.size() == 1) {
                Figure fig = beatMoveFigures.get(0);
                Main.board.setFigureToField(Main.board.getFieldOfFigure(fig) + dice, fig);

                if(Main.autoForced) {
                    NewTurn(PlayerColor.values()[(int) (player % Main.playerCount)]);
                }
                else {
                    Label figLabel = playerStones.get(StaticHelpers.getIdOfPlayerColor(fig.getColor())).get(fig.getId() - 1);
                    Timeline flasher = new Timeline(
                            new KeyFrame(javafx.util.Duration.seconds(0.5), e -> {
                                StaticHelpers.markStoneAsActive(figLabel);
                            }),
                            new KeyFrame(javafx.util.Duration.seconds(1.0), e -> {
                                StaticHelpers.markStoneAsInActive(figLabel);
                            })
                    );

                    flasher.setCycleCount(Animation.INDEFINITE);
                    flasher.play();

                    figLabel.setOnMouseClicked(event -> {
                        figLabel.setOnMouseClicked(null);
                        flasher.stop();
                        StaticHelpers.markStoneAsInActive(figLabel);
                        reloadBoard();

                        NewTurn(PlayerColor.values()[(int) (player % Main.playerCount)]);
                    });
                }

            } else {
                letPlayerChoose(dice,beatMoveFigures);
                //Main.board.setFigureToField(Main.board.getFieldOfFigure(fig)+dice,fig);
                //NewTurn(PlayerColor.values()[(int)(player%Main.playerCount)]);

            }
        }
        else
        {
            letPlayerChoose(dice);
        }


    }

    private void letPlayerChoose(int dice, List<Figure> movableFigures)
    {
        if(movableFigures.size() != 0) {

            List<Timeline> tmpAnimation = new ArrayList<>();

            for (Figure fig :  movableFigures)
            {
                Label figLabel = playerStones.get(StaticHelpers.getIdOfPlayerColor(fig.getColor())).get(fig.getId()-1);
                Timeline flasher = new Timeline(
                        new KeyFrame(javafx.util.Duration.seconds(0.5), e -> {
                            StaticHelpers.markStoneAsActive(figLabel);
                        }),
                        new KeyFrame(javafx.util.Duration.seconds(1.0), e -> {
                            StaticHelpers.markStoneAsInActive(figLabel);
                        })
                );

                flasher.setCycleCount(Animation.INDEFINITE);
                flasher.play();
                tmpAnimation.add(flasher);
                //StaticHelpers.blink(figLabel);

                figLabel.setOnMouseClicked(event -> {
                    Main.board.setFigureToField(Main.board.getFieldOfFigure(fig)+dice,fig);
                    figLabel.setOnMouseClicked(null);
                    reloadBoard();
                    for (Figure fig2: movableFigures)
                    {
                        for (Timeline time:tmpAnimation) {
                            time.stop();

                        }
                        Label tmp = playerStones.get(StaticHelpers.getIdOfPlayerColor(fig2.getColor())).get(fig2.getId()-1);
                        tmp.setOnMouseClicked(null);
                        StaticHelpers.markStoneAsInActive(tmp);

                    }
                    NewTurn(PlayerColor.values()[(int)(player%Main.playerCount)]);
                    //showDice.setOnMouseClicked(event2 ->  NewTurn(PlayerColor.values()[(int)(player%Main.playerCount)]));
                });
            }

        }
        else
        {
            countDice++;
            NewTurn(PlayerColor.values()[(int)(player%Main.playerCount)]);
        }
    }

    private void letPlayerChoose(int dice)
    {
        List<Figure> movableFigures = Main.board.getMovableFigures(dice,currentPlayer.getColor());

        letPlayerChoose(dice , movableFigures);


    }





    private int countDice = 1;

    private boolean throwDiceAgain = true;

    private Player currentPlayer;
}
