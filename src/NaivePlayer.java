import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by harishmanikantan on 3/4/17.
 */
public class NaivePlayer extends Player {

    /**
     * Constructor that takes playerNumber and numberOfPlayers and initializes them
     * @param playerNumber
     * @param numberOfPlayers
     */
    public NaivePlayer(int playerNumber, int numberOfPlayers) {
        playerType = "naive";
        super.initialize(playerNumber, numberOfPlayers);
    }

    /**
     * Gets a random card from hand
     * @param hand
     * @return
     */
    public Card getRandomCard(Hand hand) {
        Random random = new Random();

        int index = random.nextInt(hand.size());
        return hand.getCard(index);
    }

    /**
     * Gets a random player in the game other than players out and itself
     * @return
     */
    public int getRandomPlayerNumber() {
        if (numberOfPlayers - playersOut.size() <= 1) {
            return -1;
        }

        Random random = new Random();
        int index = playerNumber;

        while (index == playerNumber || isPlayerOut(index)) {
            index = random.nextInt(numberOfPlayers);
        }

        return index;
    }

    /**
     * Creates a play and returns it
     * @param hand The current state of the player's hand when they are to act
     * @return
     */
    @Override
    public Play doTurn(Hand hand) {
        if (hand.cards.isEmpty()) {
            return null;
        }

        int randomPlayer = getRandomPlayerNumber();

        if (randomPlayer == -1) {
            return null;
        }

        return new Play(randomPlayer, getRandomCard(hand).getRank());
    }

    /**
     * Does nothing for NaivePlayer
     * @param recordedPlay an object representing the information of the play that just occurred and its results.
     */
    @Override
    public void playOccurred(RecordedPlay recordedPlay) {

    }

}
