import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by harishmanikantan on 3/7/17.
 */
public class VerySmartPlayer extends Player{

    protected HashMap<Integer, Boolean[]> playersWithCards = new HashMap<>();
    protected HashMap<Integer, Boolean[]> playersWithoutCards = new HashMap<>();
    protected ArrayList<Integer> playersHandSizes = new ArrayList<>();

    /**
     * Constructor that takes playerNumber and numberOfPlayers and initializes them and member variables
     * @param playerNumber
     * @param numberOfPlayers
     */
    public VerySmartPlayer(int playerNumber, int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            Boolean[] initialBoolArray1 = new Boolean[13];
            Arrays.fill(initialBoolArray1, false);

            Boolean[] initialBoolArray2 = new Boolean[13];
            Arrays.fill(initialBoolArray2, false);

            playersWithCards.put(i, initialBoolArray1);
            playersWithoutCards.put(i, initialBoolArray2);
            playersHandSizes.add(0);
        }

        playerType = "very_smart";
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
            int playerWithoutCardRank = getPlayerWithoutCardRank(cardRank);

            if (playerWithoutCardRank == -1) {
                continue;
            }

            return new Play(playerWithoutCardRank, cardRank);
        }

        int maxSize = -1;
        int maxPlayer = -1;

        for (int i = 0; i < numberOfPlayers; i++) {
            if (!playersOut.contains(i) && i != playerNumber) {
                if (playersHandSizes.get(i) > maxSize) {
                    maxSize = playersHandSizes.get(i);
                    maxPlayer = i;
                }
            }
        }

        if (maxPlayer != -1) {
            return new Play(maxPlayer, cardRanksInDecreasingCount[0]);
        }

        return null;
    }

    /**
     * Returns player that does not have card
     * @param cardRank
     * @return
     */
    public Integer getPlayerWithoutCardRank(int cardRank) {
        int maxSize = -1;
        int maxPlayer = -1;

        for (int playerIndex = 0; playerIndex < numberOfPlayers; playerIndex++) {
            Boolean[] playerCards = playersWithoutCards.get(playerIndex);

            if (!playerCards[cardRank] && !isPlayerOut(playerIndex) && playerIndex != playerNumber) {
                if (playersHandSizes.get(playerIndex) > maxSize) {
                    maxSize = playersHandSizes.get(playerIndex);
                    maxPlayer = playerIndex;
                }
            }
        }

        return maxPlayer;
    }

    /**
     * Returns player that has card
     * @param cardRank
     * @return
     */
    public Integer getPlayerWithCardRank(int cardRank) {
        int maxSize = -1;
        int maxPlayer = -1;

        for (int playerIndex = 0; playerIndex < numberOfPlayers; playerIndex++) {
            Boolean[] playerWithCards = playersWithCards.get(playerIndex);
            Boolean[] playerWithoutCards = playersWithoutCards.get(playerIndex);

            if (playerWithCards[cardRank] && !isPlayerOut(playerIndex) && playerIndex != playerNumber && !playerWithoutCards[cardRank]) {
                if (playersHandSizes.get(playerIndex) > maxSize) {
                    maxSize = playersHandSizes.get(playerIndex);
                    maxPlayer = playerIndex;
                }
            }
        }

        return maxPlayer;
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
    public void playOccurred(RecordedPlay recordedPlay, ArrayList<Integer> playersHandSize) {
        int sourcePlayer = recordedPlay.getSourcePlayer();
        int targetPlayer = recordedPlay.getTargetPlayer();
        int rank = recordedPlay.getRank();
        List<Card> cards = recordedPlay.getCardsReturned();

        this.playersHandSizes = playersHandSize;

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
