package sample.models.Players;

import sample.models.Player;
import sample.models.PlayerColor;

/**
 * Created by Dennis on 29.06.2017.
 */
public class BluePlayer extends Player {
    public BluePlayer(String name) {
        super(name, 39 , 0, PlayerColor.Blue);
    }
}
