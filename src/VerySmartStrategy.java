import java.util.ArrayList;

/**
 * Created by harishmanikantan on 3/7/17.
 */
public interface VerySmartStrategy {

    /**
     * Every time a game action takes place, the game engine invokes the following function on each player.
     * @param recordedPlay an object representing the information of the play that just occurred and its results.
     */
    public void playOccurred(RecordedPlay recordedPlay, ArrayList<Integer> playersHandSize);
}
