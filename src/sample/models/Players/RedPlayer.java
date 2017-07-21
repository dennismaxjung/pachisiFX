package sample.models.Players;

import sample.models.Player;
import sample.models.PlayerColor;

/**
 * Created by Dennis on 29.06.2017.
 */
public class RedPlayer extends Player {
    public RedPlayer(String name) {
        super(name, 29 , 30, PlayerColor.Red);
    }
}
