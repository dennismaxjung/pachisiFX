package sample.helpers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import sample.models.PlayerColor;
import sample.models.Tuple;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dennis on 16.07.2017.
 */
public class StaticHelpers {

    private static List<List<Tuple<Integer,Integer>>> starts = Arrays.asList(
            Arrays.asList(new Tuple<>(10,0),new Tuple<>(10,1),new Tuple<>(9,0),new Tuple<>(9,1)),
            Arrays.asList(new Tuple<>(0,0),new Tuple<>(0,1),new Tuple<>(1,0),new Tuple<>(1,1)),
            Arrays.asList(new Tuple<>(10,10),new Tuple<>(10,9),new Tuple<>(9,10),new Tuple<>(9,9)),
            Arrays.asList(new Tuple<>(0,10),new Tuple<>(0,9),new Tuple<>(1,10),new Tuple<>(1,9))
    );

    private static List<List<Tuple<Integer,Integer>>> targets = Arrays.asList(
            Arrays.asList(new Tuple<>(5,1),new Tuple<>(5,2),new Tuple<>(5,3),new Tuple<>(5,4)),
            Arrays.asList(new Tuple<>(1,5),new Tuple<>(2,5),new Tuple<>(3,5),new Tuple<>(4,5)),
            Arrays.asList(new Tuple<>(9,5),new Tuple<>(8,5),new Tuple<>(7,5),new Tuple<>(6,6)),
            Arrays.asList(new Tuple<>(5,9),new Tuple<>(5,8),new Tuple<>(5,7),new Tuple<>(5,6))
    );

    private static List<Tuple<Integer, Integer>> fields = Arrays.asList(
            new Tuple<>(6, 0),
            new Tuple<>(6, 1),
            new Tuple<>(6, 2),
            new Tuple<>(6, 3),
            new Tuple<>(6, 4),
            new Tuple<>(7, 4),
            new Tuple<>(8, 4),
            new Tuple<>(9, 4),
            new Tuple<>(10, 4),
            new Tuple<>(10, 5),
            new Tuple<>(10, 6),
            new Tuple<>(9, 6),
            new Tuple<>(8, 6),
            new Tuple<>(7, 6),
            new Tuple<>(6, 6),
            new Tuple<>(6, 7),
            new Tuple<>(6, 8),
            new Tuple<>(6, 9),
            new Tuple<>(6, 10),
            new Tuple<>(5, 10),
            new Tuple<>(4, 10),
            new Tuple<>(4, 9),
            new Tuple<>(4, 8),
            new Tuple<>(4, 7),
            new Tuple<>(4, 6),
            new Tuple<>(3, 6),
            new Tuple<>(2, 6),
            new Tuple<>(1, 6),
            new Tuple<>(0, 6),
            new Tuple<>(0, 5),
            new Tuple<>(0, 4),
            new Tuple<>(1, 4),
            new Tuple<>(2, 4),
            new Tuple<>(3, 4),
            new Tuple<>(4, 4),
            new Tuple<>(4, 3),
            new Tuple<>(4, 2),
            new Tuple<>(4, 1),
            new Tuple<>(4, 0),
            new Tuple<>(5, 0)
    );




    public static Tuple<Integer, Integer> getField(int fieldNr) {
        return fields.get(fieldNr);
    }

    public static Tuple<Integer,Integer> getTarget(int pos, PlayerColor color)
    {
        return targets.get(getIdOfPlayerColor(color)).get(pos);
    }
    public static Tuple<Integer,Integer> getStart(int pos, PlayerColor color)
    {
        return starts.get(getIdOfPlayerColor(color)).get(pos);
    }

    public static Label generateNewPlayerStone(PlayerColor color, int number)
    {
        Label newLabel = new Label();
        newLabel.alignmentProperty().set(Pos.CENTER);
        newLabel.contentDisplayProperty().set(ContentDisplay.CENTER);
        newLabel.textProperty().setValue( Integer.toString(number));
        newLabel.textAlignmentProperty().set(TextAlignment.CENTER);
        newLabel.textFillProperty().set(Paint.valueOf("WHITE"));
        newLabel.fontProperty().set(Font.font("Arial",25.0));
        newLabel.setStyle("-fx-font-weight: bold");
        Circle tmp = new Circle(15.0,getColor(color));
        tmp.strokeProperty().set(Paint.valueOf("BLACK"));
        tmp.strokeTypeProperty().set(StrokeType.INSIDE);
        newLabel.graphicProperty().set(tmp);
        return newLabel;
    }

    public static Timeline flasher;

    public static void blink(Label playerStone)
    {
        flasher = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(0.5), e -> {
            markStoneAsActive(playerStone);
        }),
                new KeyFrame(javafx.util.Duration.seconds(1.0), e -> {
                    markStoneAsInActive(playerStone);
                })
        );
        flasher.setCycleCount(Animation.INDEFINITE);
        flasher.play();
    }

    public static void stopBlink(Label playerStone)
    {
        flasher.stop();
    }


    public static void markStoneAsActive(Label playerStone)
    {
        Circle tmp = (Circle) playerStone.getGraphic();
        tmp.strokeTypeProperty().setValue(StrokeType.OUTSIDE);
        tmp.strokeProperty().set(Paint.valueOf("WHITE"));
        tmp.strokeWidthProperty().set(6);



    }
    public static void markStoneAsInActive(Label playerStone)
    {
        Circle tmp = (Circle) playerStone.getGraphic();
        tmp.strokeWidthProperty().set(1);
        tmp.strokeProperty().set(Paint.valueOf("BLACK"));
        tmp.strokeTypeProperty().set(StrokeType.INSIDE);
    }

    public static Paint getColor(PlayerColor color)
    {
        switch (color) {
            case Blue:
                return Paint.valueOf("#1e92ff");

            case Red:
                return Paint.valueOf("#ff1f1f");

            case Green:
                return Paint.valueOf("#21ff58");

            case Yellow:
                return Paint.valueOf("#fcff1f");

            default:
                return Paint.valueOf("BLACK");
        }
    }

    public static int getIdOfPlayerColor(PlayerColor color )
    {
        int k;
        for(k=0;k < PlayerColor.values().length;k++)
        {
            if(PlayerColor.values()[k] == color)
            {
                return k;
            }
        }
        return 0;
    }


}
