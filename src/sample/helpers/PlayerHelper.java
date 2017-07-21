package sample.helpers;

import sample.models.PlayerColor;
import sample.models.Player;
import sample.models.Players.BluePlayer;
import sample.models.Players.GreenPlayer;
import sample.models.Players.RedPlayer;
import sample.models.Players.YellowPlayer;

/**
 * Created by Dennis on 30.06.2017.
 */
public class PlayerHelper {

    public static Player createPlayer(String name, PlayerColor color)
    {
        switch (color) {
            case Blue:
                return new BluePlayer(name);
            case Red:
                return new RedPlayer(name);
            case Green:
                return new GreenPlayer(name);
            case Yellow:
                return new YellowPlayer(name);
            default:
                return null;
        }
    }
}
