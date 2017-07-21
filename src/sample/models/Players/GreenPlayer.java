package sample.models.Players;

import sample.models.Player;
import sample.models.PlayerColor;

/**
 * Created by Dennis on 29.06.2017.
 */
public class GreenPlayer extends Player {
    public GreenPlayer(String name) {
        super(name, 9 , 10, PlayerColor.Green);
    }
}
