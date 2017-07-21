package sample.models;

import java.io.Serializable;

/**
 * Created by Dennis on 29.06.2017.
 */
public class Figure implements Serializable {

    public Figure(int id, PlayerColor color) {
        this.id = id;
        this.color = color;
    }

    private int id;
    private PlayerColor color;

    public int getId() {
        return id;
    }

    public PlayerColor getColor() {
        return color;
    }
}
