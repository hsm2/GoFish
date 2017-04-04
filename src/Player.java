import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by harishmanikantan on 3/4/17.
 */
public class Player implements PlayerStrategy, VerySmartStrategy {

    protected int playerNumber;
    protected String playerType;
    protected int numberOfPlayers;
    private ArrayList<Integer> booksWon = new ArrayList<>();
    protected ArrayList<Integer> playersOut = new ArrayList<>();

    /**
     * Default empty constructor
     */
    Player() {

    }

    public String getPlayerType() {
        return playerType;
    }
    /**
     * Adds playerNumber that is out of the game
     * @param playerOutNumber
     */
    public void addPlayersOut(int playerOutNumber) {
        playersOut.add(playerOutNumber);
    }

    /**
     * Checks if a particular player is out of the game
     * @param playerOutNumber
     * @return
     */
    public boolean isPlayerOut(int playerOutNumber) {
        return playersOut.contains(playerOutNumber);
    }

    /**
     * Adds book to the set of books won by player
     * @param card
     */
    public void addBooksWon(Card card) {
        booksWon.add(card.getRank());
    }

    /**
     * Returns the set of books won in an arraylist
     * @return
     */
    public ArrayList<Integer> getBooksWon() {
        return booksWon;
    }

    /**
     * Gets the player's number
     * @return
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Gets book from player's hand if it exists
     * @param hand
     * @return
     */
    public Card getBook(Hand hand) {
        HashMap<Integer, Integer> cardBook = new HashMap<>();

        for (Card card : hand.cards) {
            if (cardBook.containsKey(card.getRank())) {
                cardBook.put(card.getRank(), cardBook.get(card.getRank()) + 1);

                if (cardBook.get(card.getRank()) == 4) {
                    return card;
                }
            }
            else {
                cardBook.put(card.getRank(), 1);
            }
        }

        return null;
    }

    /**
     * If player's hand has the rank asked for, it returns all cards of the rank
     * @param hand
     * @param rank
     * @return
     */
    public List<Card> getCards(Hand hand, int rank) {
        List<Card> returnedCards = new ArrayList<>();

        if (hand.hasRank(rank)) {
            for (int i = 0; i < hand.size(); i++) {
                if (hand.cards.get(i).getRank() == rank) {
                    returnedCards.add(hand.cards.get(i));
                    hand.cards.remove(i);
                    i = i - 1;
                }
            }
        }

        return returnedCards;
    }

    /**
     * Initializes the player's number and the total number of players in the game
     * @param yourPlayerNumber a number between 0 and (totalNumberOfPlayers - 1) specifying the current player
     * @param totalNumberOfPlayers a positive integer indicating the total number of players in the game
     */
    @Override
    public void initialize(int yourPlayerNumber, int totalNumberOfPlayers) {
        playerNumber = yourPlayerNumber;
        numberOfPlayers = totalNumberOfPlayers;
    }

    /**
     * Method is overrided in SmartPlayer/NaivePlayer
     * @param hand The current state of the player's hand when they are to act
     * @return
     */
    @Override
    public Play doTurn(Hand hand) {
        return null;
    }

    /**
     * Method is overrided in SmartPlayer/NaivePlayer
     * @param recordedPlay an object representing the information of the play that just occurred and its results.
     */
    @Override
    public void playOccurred(RecordedPlay recordedPlay) {

    }

    @Override
    public void playOccurred(RecordedPlay recordedPlay, ArrayList<Integer> playersHandSize) {

    }
}
