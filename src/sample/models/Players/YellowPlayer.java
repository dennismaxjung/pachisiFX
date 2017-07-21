package sample.models.Players;

import sample.models.Player;
import sample.models.PlayerColor;

/**
 * Created by Dennis on 29.06.2017.
 */
public class YellowPlayer extends Player {
    public YellowPlayer(String name) {
        super(name, 19 , 20, PlayerColor.Yellow);
    }
}
