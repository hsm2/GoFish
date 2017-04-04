import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by harishmanikantan on 3/4/17.
 */
public class SmartPlayer extends Player implements PlayerStrategy {

    protected HashMap<Integer, Boolean[]> playersWithCards = new HashMap<>();
    protected HashMap<Integer, Boolean[]> playersWithoutCards = new HashMap<>();

    /**
     * Constructor that takes playerNumber and numberOfPlayers and initializes them and member variables
     * @param playerNumber
     * @param numberOfPlayers
     */
    public SmartPlayer(int playerNumber, int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            Boolean[] initialBoolArray1 = new Boolean[13];
            Arrays.fill(initialBoolArray1, false);

            Boolean[] initialBoolArray2 = new Boolean[13];
            Arrays.fill(initialBoolArray2, false);

            playersWithCards.put(i, initialBoolArray1);
            playersWithoutCards.put(i, initialBoolArray2);
        }

        playerType = "smart";
        super.initialize(playerNumber, numberOfPlayers);
    }

    /**
     * Creates a play and returns it
     * @param hand The current state of the player's hand when they are to act
     * @return
     */
    @Override
    public Play doTurn(Hand hand) {
        Integer[] cardRanksInDecreasingCount = getCardsInDecreasingCount(hand.cards);

        for (int i = 0; i < cardRanksInDecreasingCount.length; i++) {
            int cardRank = cardRanksInDecreasingCount[i];
            int playerWithCardRank = getPlayerWithCardRank(cardRank);

            if (playerWithCardRank == -1) {
                continue;
            }

            return new Play(playerWithCardRank, cardRank);
        }

        for (int i = 0; i < cardRanksInDecreasingCount.length; i++) {
            int cardRank = cardRanksInDecreasingCount[i];
            int playerWithOutCardRank = getPlayerWithoutCardRank(cardRank);

            if (playerWithOutCardRank == -1) {
                continue;
            }

            return new Play(playerWithOutCardRank, cardRank);
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            if (!playersOut.contains(i) && i != playerNumber) {
                return new Play(i, cardRanksInDecreasingCount[0]);
            }
        }

        return null;
    }

    /**
     * Returns player that does not have card
     * @param cardRank
     * @return
     */
    public int getPlayerWithoutCardRank(int cardRank) {
        for (int playerIndex = 0; playerIndex < numberOfPlayers; playerIndex++) {
            Boolean[] playerCards = playersWithoutCards.get(playerIndex);

            if (!playerCards[cardRank] && !isPlayerOut(playerIndex) && playerIndex != playerNumber) {
                return playerIndex;
            }
        }

        return -1;
    }

    /**
     * Returns player that has card
     * @param cardRank
     * @return
     */
    public int getPlayerWithCardRank(int cardRank) {
        for (int playerIndex = 0; playerIndex < numberOfPlayers; playerIndex++) {
            Boolean[] playerWithCards = playersWithCards.get(playerIndex);
            Boolean[] playerWithoutCards = playersWithoutCards.get(playerIndex);

            if (playerWithCards[cardRank] && !isPlayerOut(playerIndex) && playerIndex != playerNumber && !playerWithoutCards[cardRank]) {
                return playerIndex;
            }
        }
        return -1;
    }

    /**
     * Returns an array of card ranks in decreasing order of count
     * @param cards
     * @return
     */
    public Integer[] getCardsInDecreasingCount(List<Card> cards) {
        Integer[] cardCount = new Integer[13];
        Arrays.fill(cardCount, 0);

        int numberOfUniqueCards = 0;

        for (Card card : cards) {
            if (cardCount[card.getRank()] == 0) {
                numberOfUniqueCards++;
            }
            cardCount[card.getRank()]++;
        }

        Integer[] decreasingOrderCardCount = new Integer[numberOfUniqueCards];

        for (int i = 0; i < numberOfUniqueCards; i++) {
            int maxCard = getMaxCardRank(cardCount);
            decreasingOrderCardCount[i] = maxCard;

            cardCount[maxCard] = 0;
        }

        return decreasingOrderCardCount;
    }

    /**
     * Returns card rank of the card that has the most count in a card rank array
     * @param cardCount
     * @return
     */
    public Integer getMaxCardRank(Integer[] cardCount) {
        int maxCount = 0;
        int maxCard = -1;

        for (int i = 0; i < cardCount.length; i++) {
            if (cardCount[i] > maxCount) {
                maxCount = cardCount[i];
                maxCard = i;
            }
        }

        return maxCard;
    }

    /**
     * Updates playersWithCards and playersWithoutCards after every play
     * @param recordedPlay an object representing the information of the play that just occurred and its results.
     */
    @Override
    public void playOccurred(RecordedPlay recordedPlay) {
        int sourcePlayer = recordedPlay.getSourcePlayer();
        int targetPlayer = recordedPlay.getTargetPlayer();
        int rank = recordedPlay.getRank();
        List<Card> cards = recordedPlay.getCardsReturned();

        Boolean[] sourcePlayerWithCards = playersWithCards.get(sourcePlayer);
        sourcePlayerWithCards[rank] = (cards.size() != 3);

        Boolean[] sourcePlayerWithoutCards = playersWithoutCards.get(sourcePlayer);
        sourcePlayerWithoutCards[rank] = false;

        Boolean[] targetPlayerCards = playersWithoutCards.get(targetPlayer);
        targetPlayerCards[rank] = true;

        playersWithCards.put(sourcePlayer, sourcePlayerWithCards);
        playersWithoutCards.put(targetPlayer, targetPlayerCards);
        playersWithoutCards.put(sourcePlayer, sourcePlayerWithoutCards);

    }

}
