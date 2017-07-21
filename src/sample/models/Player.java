package sample.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 29.06.2017.
 */
public abstract class Player implements Serializable {

    public Player(String name, int targetNr, int startNr, PlayerColor color)
    {
        this.name = name;
        this.targetNr = targetNr;
        this.startNr = startNr;
        this.color = color;

        this.target = new Figure[4];
        this.start = new ArrayList<Figure>(){{add(new Figure(1,color)); add(new Figure(2,color)); add(new Figure(3,color)); add(new Figure(4,color));}};
    }

    private String name;

    private int targetNr;

    private int startNr;

    public Figure[] target;

    public List<Figure> start;

    private PlayerColor color;

    public String getName()
    {return name;}

    public int getTargetNr()
    {return targetNr;}

    public int getStartNr() {
        return startNr;
    }

    public PlayerColor getColor() {
        return color;
    }

    public int roleTheDice()
    {
        int dice = (int)(6.0 * Math.random()) + 1;;
        //Main.playerInterfac.showDiceValue(dice);

        return dice;
    }

    public int figuresAtStart()
    {

       return start.size();
    }

    public boolean isStartEmpty()
    {
        if(start.isEmpty())
        {
            return true;
        }
        else{
            return false;
        }
    }

    public Figure takeFigureFromStart()
    {
        if (!isStartEmpty()) {
            return start.remove(0);
        }
        else{
            return null;
        }
    }

    public void setFigureBackToStart(Figure figure)
    {
        start.add(figure);
    }

    public boolean isFigureOnTargetPosition(int position)
    {
        if(target.length > position-1 && target[position-1] == null)
        {
            return false;
        }
        else {
            return  true;
        }
    }

    public void setFigureToTargetPosition(int position, Figure figure)
    {
        target[position-1] = figure;
    }

}
