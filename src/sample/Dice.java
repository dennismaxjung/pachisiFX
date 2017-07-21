package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import sample.models.Tuple;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

/**
 * Created by Dennis on 16.07.2017.
 */
public class Dice {
    private Node dice;
    private GridPane diceGrid;
    private Rectangle diceRect;

    protected List<EventListener> listenerList = new ArrayList<>();

    public void addMyEventListener(ActionListener listener) {
        if(listener != null) {
            listenerList.add(listener);
        }
        else {
        listenerList = new ArrayList<>();
        }
    }
    public void removeMyEventListener(ActionListener listener) {
        listenerList.remove(listener);
    }
    void fireMyEvent(java.awt.event.ActionEvent evt) {
        List<EventListener> listeners = listenerList;
        for (EventListener listener : listeners) {
            ((ActionListener) listener).actionPerformed(evt);

        }
    }




    public Dice(Node dice)
    {

        this.dice = dice;
        diceGrid = (GridPane) dice.lookup("#diceGrid");
        diceRect = (Rectangle) dice.lookup("#diceRect");
        rescale(0.4);
        diceGrid.getChildren().clear();
        setDiceingAnimation();
        setResizeBig();
        setResizeSmall();
    }


    public void StartDiceing(int toDice, Paint color)
    {
        System.out.println(toDice + " - "+ color.toString());

        diceRect.setFill(color);
        diceing.setOnFinished(event -> {
            printDiceValue(toDice);
            resizeSmall.play();});
        resizeBig.play();

    }

    private Timeline diceing;

    private Timeline resizeBig;

    private Timeline resizeSmall;

    private void setDiceingAnimation()
    {
        diceing = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(0.125), e -> {
                    diceGrid.getChildren().clear();
                    int x = (int)(6.0 * Math.random()+1);
                    printDiceValue(x);

                })

        );
        diceing.setCycleCount(20);

    }

    /**
     *  Animation for make the dice big
     */
    private void setResizeBig()
    {
            resizeBig = new Timeline(
                    new KeyFrame(javafx.util.Duration.seconds(0.0125), e -> {
                        if(dice.getScaleX() >= 2)
                        {
                            resizeBig.stop();
                            diceing.play();
                        }
                        else{

                            rescale(dice.getScaleX()+0.01);
                        }
                    })

            );
            resizeBig.setCycleCount(Animation.INDEFINITE);


    }

    /**
     *  Animation for make the dice small
     */
    private void setResizeSmall()
    {
        resizeSmall = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(0.0025), e -> {
                    if(dice.getScaleX() <= 0.4)
                    {
                        resizeSmall.stop();
                        fireMyEvent(new java.awt.event.ActionEvent(this,1,""));

                    }
                    else{

                        rescale(dice.getScaleX()-0.01);
                    }
                })

        );
        resizeSmall.setCycleCount(Animation.INDEFINITE);

    }


    /**
     * Generates new circle with radius 13 and black filled
     * @return a new Circle
     */
    private Circle getDiceEye()
    {
        return new Circle(13, Paint.valueOf("BLACK"));
    }

    private void rescale(double scale)
    {
        dice.setScaleX(scale);
        dice.setScaleY(scale);
        dice.setScaleZ(scale);
    }

    private void printDiceValue(int value)
    {
        diceGrid.getChildren().clear();
        for (Tuple<Integer,Integer> dot :dots.get(value-1)) {
            diceGrid.add(getDiceEye(),dot.x, dot.y);
        }
    }

    private static List<List<Tuple<Integer,Integer>>> dots = Arrays.asList(
            Arrays.asList( new Tuple<>(1, 1)),
            Arrays.asList( new Tuple<>(0, 2),new Tuple<>(2, 0)),
            Arrays.asList( new Tuple<>(0, 2), new Tuple<>(1,1),new Tuple<>(2, 0)),
            Arrays.asList(new Tuple<>(0, 0),new Tuple<>(2, 2),new Tuple<>(0, 2),new Tuple<>(2, 0)),
            Arrays.asList(new Tuple<>(1, 1),new Tuple<>(0, 0),new Tuple<>(2, 2),new Tuple<>(0, 2),new Tuple<>(2, 0)),
            Arrays.asList(new Tuple<>(0, 0),new Tuple<>(0, 1),new Tuple<>(0, 2),new Tuple<>(2, 0),new Tuple<>(2,1),new Tuple<>(2,2))
    );


}
