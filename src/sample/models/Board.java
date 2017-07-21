package sample.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dennis on 29.06.2017.
 */
public class Board implements Serializable {

    public Board(List<Player> players) {
        this.fields= new Figure[40];
        this.players = players;
    }

    public Figure[] fields;

    private List<Player> players;

    private void giveFigureBackToPlayerStart(Figure figure)
    {
        players.stream().filter(p -> p.getColor() == figure.getColor()).findFirst().get().setFigureBackToStart(figure);
    }

    public int getPlayerCount()
    {
        return players.size();
    }

    public Player getPlayer(PlayerColor color)
    {
        if(players.stream().filter(p->p.getColor() == color).count() > 0) {
            return players.stream().filter(p -> p.getColor() == color).findFirst().get();
        }
        else{
            return null;
        }
    }

    public int getFieldOfFigure(Figure figure)
    {
        return Arrays.asList(fields).indexOf(figure);
    }

    public void setFigureToField(int position, Figure figure) {

        int currentPosition = getFieldOfFigure(figure);
        if(currentPosition != -1) {
            fields[getFieldOfFigure(figure)] = null;
        }



        Player currentPlayer = getPlayer(figure.getColor());
        int getTargetFields = position - getPlayer(figure.getColor()).getTargetNr();
        if(currentPosition!= -1 && currentPosition <= currentPlayer.getTargetNr() && position > currentPlayer.getTargetNr() && getTargetFields <= 4 && !currentPlayer.isFigureOnTargetPosition(getTargetFields))
        {
          currentPlayer.setFigureToTargetPosition(getTargetFields,figure);
          return;
        }

        if(isFieldEmpty(position%40))
        {
            fields[position%40] = figure;
        }
        else if (fields[position%40].getColor() != figure.getColor()) {
            giveFigureBackToPlayerStart(fields[position%40]);
            fields[position%40] = figure;
        }
    }

    public Figure getFigureOfField(int field)
    {
        return fields[field];
    }

    public boolean isFieldEmpty(int position)
    {
        if(fields[position%40] == null)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isFieldOtherPlayer(int position, PlayerColor color)
    {
        if(!isFieldEmpty(position) && fields[position].getColor() != color)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean playerHasNoFiguresOnFields(PlayerColor color)
    {
        if(Arrays.stream(fields).filter(p-> p != null).filter(p->p.getColor() == color).count() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public List<Figure> playerHasToBeat(int dice, PlayerColor color)
    {
        List<Figure> figures = new ArrayList<>();
        Arrays.stream(fields).filter(p->p != null).filter(p->p.getColor() == color).forEach(figures::add);

        List<Figure> removeFigures = new ArrayList<Figure>();

        for (Figure figure : figures)
        {
            int currentField = Arrays.asList(fields).indexOf(figure);
            int nextField = currentField+dice;
            nextField = nextField%40;
            if((currentField <= getPlayer(color).getTargetNr() && nextField > getPlayer(color).getTargetNr()) || !isFieldOtherPlayer(nextField,color)) // || isFieldEmpty(nextField))
            {
                removeFigures.add(figure);
            }
        }

        figures.removeAll(removeFigures);

       return figures;
    }

    public List<Figure> getMovableFigures(int dice, PlayerColor color)
    {
        List<Figure> figures = new ArrayList<>();
        Arrays.stream(fields).filter(p->p != null).filter(p->p.getColor() == color).forEach(figures::add);

        List<Figure> removeFigures = new ArrayList<Figure>();

        for (Figure figure : figures) {
            int currentField = Arrays.asList(fields).indexOf(figure);
            int nextField = currentField+dice;
            int getTargetFields = nextField - getPlayer(color).getTargetNr();

           if(currentField <= getPlayer(color).getTargetNr() && nextField > getPlayer(color).getTargetNr() && (getTargetFields > 4 || getPlayer(color).isFigureOnTargetPosition(getTargetFields)))
            {
                removeFigures.add(figure);
            }

            if(!removeFigures.contains(figure) && !isFieldOtherPlayer(nextField%40,color) && !isFieldEmpty(nextField%40))
            {
                removeFigures.add(figure);
            }

        }


        figures.removeAll(removeFigures);
        return  figures;
    }
}
