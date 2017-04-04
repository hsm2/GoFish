import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Created by harishmanikantan on 3/4/17.
 */
public class GoFishGame {

    protected Deck deck = new Deck();

    protected List<Player> players = new ArrayList<>();
    protected ArrayList<Hand> playerHands = new ArrayList<>();
    protected HashSet<Integer> playersOut = new HashSet<>();
    protected ArrayList<Integer> playerHandsSizes = new ArrayList<>();

    protected Player currentPlayer;
    protected int currentPlayerIndex;
    protected Hand currentHand;

    protected int numBooksWon;
    protected int maxBooksPlayer = -1;
    protected int winningPlayerIndex = -1;

    /**
     * Constructor where methods to create players and deal cards are called
     * @param input
     */
    public GoFishGame(List<String> input) {
        createPlayers(input);

        int dealerPlayer = getDealer();
        dealCards(dealerPlayer);
    }

    /**
     * This method controls the game, gets every player's play, gets the result of a play and updates the game
     */
    public int play() {

        while (isGameOn()) {

            if (currentHand.size() == 0 && deck.isEmpty()) {
                playersOut.add(currentPlayer.getPlayerNumber());
                updatePlayersOut(currentPlayerIndex);

                updateCurrentPlayer(null);
                continue;
            }
            else if (currentHand.size() == 0 && !deck.isEmpty()) {
                Card card = deck.draw();
                currentHand.cards.add(card);
            }

            Play play = currentPlayer.doTurn(currentHand);

            List<Card> cardsReturned = getCardsReturned(play);
            currentHand.cards.addAll(cardsReturned);

            int cardRankToCheck = -1;

            if (cardsReturned.isEmpty() && !deck.isEmpty()) {
                Card newCard = deck.draw();
                currentHand.cards.add(newCard);

                cardRankToCheck = newCard.getRank();
            }
            else if (!cardsReturned.isEmpty()){
                cardRankToCheck = cardsReturned.get(0).getRank();
            }

            RecordedPlay recordedPlay = new RecordedPlay(currentPlayer.getPlayerNumber(), play.getTargetPlayer(), play.getRank(), cardsReturned);

            updatePlayerRecords(recordedPlay);
            updateBooksOfCurrentPlayer();
            updateGameRecords();
            updateCurrentPlayer(cardsReturned);

            displayCurrentStatus(recordedPlay);
            displayIfBookExists(recordedPlay.getSourcePlayer(), cardRankToCheck);
        }

        System.out.println("\nPlayer" + winningPlayerIndex + " won the game!\n");

        for (Player player : players) {
            System.out.println("Player" + player.getPlayerNumber() + " won " + player.getBooksWon().size() + " books");
        }

        return winningPlayerIndex;
    }

    /**
     * This method adds book to the current player if it gets a book in its turn
     */
    public void updateBooksOfCurrentPlayer() {
        Card book = currentPlayer.getBook(currentHand);

        if (book != null) {
            currentPlayer.addBooksWon(book);
            removeBookFromHand(book);
        }
    }

    /**
     * Removes 4 cards which is the book from the hand
     * @param setCard
     */
    public void removeBookFromHand(Card setCard) {
        for (int i = 0; i < currentHand.cards.size(); i++) {
            if (currentHand.cards.get(i).getRank() == setCard.getRank()) {
                currentHand.cards.remove(i);
                i = i - 1;
            }
        }
    }

    /**
     * Updates every player's record of players that are out of the game
     * @param playerOutIndex
     */
    public void updatePlayersOut(int playerOutIndex) {
        for (Player player : players) {
            player.addPlayersOut(playerOutIndex);
        }
    }

    /**
     * Updates the maximum number of books won by a player and updates the player accordingly
     */
    public void updateGameRecords() {
        int books = 0;

        for (int i = 0; i < playerHands.size(); i++) {
            playerHandsSizes.set(i, playerHands.get(i).size());
        }

        for (Player player : players) {
            ArrayList<Integer> booksWonPlayer = player.getBooksWon();
            books += booksWonPlayer.size();

            if (booksWonPlayer.size() > maxBooksPlayer) {
                maxBooksPlayer = booksWonPlayer.size();
                winningPlayerIndex = player.getPlayerNumber();
            }

        }

        numBooksWon = books;
    }

    /**
     * Displays the latest recorded play of the game
     * @param recordedPlay
     */
    public void displayCurrentStatus(RecordedPlay recordedPlay) {
        System.out.println("Player" + recordedPlay.getSourcePlayer() + " asks "
                + "Player" + recordedPlay.getTargetPlayer() + " for "
                + getCardName(recordedPlay.getRank()) + " and got " +
                + recordedPlay.getCardsReturned().size() + " card(s).");

    }

    /**
     * Display if current player made a book in its turn
     * @param playerIndex
     * @param rank
     */
    public void displayIfBookExists(int playerIndex, int rank) {
        ArrayList<Integer> sourcePlayerBooksWon = players.get(playerIndex).getBooksWon();

        if (sourcePlayerBooksWon.contains(rank)) {
            System.out.println("Player" + playerIndex + " made a book of " + getCardName(rank));
        }
    }

    /**
     * Get card name based on rank
     * @param rank
     * @return
     */
    public String getCardName(int rank) {
        if (rank == 0) {
            return "Aces";
        }
        if (rank < 10) {
            return (rank + 1) + "s";
        }
        if (rank == 10) {
            return "Jacks";
        }
        if (rank == 11) {
            return "Queens";
        }
        if (rank == 12) {
            return "Kings";
        }
        return "";
    }

    /**
     * Changes next player if no cards are returned
     * @param cardsReturned
     */
    public void updateCurrentPlayer(List<Card> cardsReturned) {

        if (cardsReturned == null || cardsReturned.size() == 0) {
            int numberOfPlayers = players.size();
            playerHands.set(currentPlayerIndex, currentHand);

            int newCurrentPlayer = (currentPlayerIndex + 1) % numberOfPlayers;

            while (playersOut.contains(newCurrentPlayer)) {
                newCurrentPlayer = (newCurrentPlayer + 1) % numberOfPlayers;
            }

            currentPlayer = players.get(newCurrentPlayer);
            currentPlayerIndex = newCurrentPlayer;
            currentHand = playerHands.get(newCurrentPlayer);

        }

    }

    /**
     * Updates players' records based on the latest recorded play
     * @param recordedPlay
     */
    public void updatePlayerRecords(RecordedPlay recordedPlay) {
        for (Player player : players) {
            if (player.getPlayerType().equals("very_smart")) {
                player.playOccurred(recordedPlay, playerHandsSizes);
                continue;
            }

            player.playOccurred(recordedPlay);
        }
    }

    /**
     * Gets cards returned from the play's target player of the play's requested rank
     * @param play
     * @return
     */
    public List<Card> getCardsReturned(Play play) {
        Player targetPlayer = players.get(play.getTargetPlayer());

        Hand targetHand = playerHands.get(play.getTargetPlayer());
        int targetRank = play.getRank();

        return targetPlayer.getCards(targetHand, targetRank);
    }

    /**
     * Checks if game is on
     * @return
     */
    public boolean isGameOn() {
        if ((players.size() - playersOut.size()) <= 1) {
            return false;
        }
        if (numBooksWon == 13) {
            return false;
        }
        return true;
    }

    /**
     * Deals 1 card each to all players and returns the player number that has the lowest card rank
     * @return
     */
    public int getDealer() {
        Deck deck = new Deck();
        int min = 15; //Maximum rank is 13
        int dealer = 0;

        for (int i = 0; i < players.size(); i++) {
            Card card = deck.draw();
            if (card.getRank() < min) {
                dealer = i;
                min = card.getRank();
            }
        }

        return dealer;
    }

    /**
     * Deals 5 cards each to all the players
     * @param playerIndex
     */
    public void dealCards(int playerIndex) {
        int numberOfPlayers = players.size();

        int dealerPlayerIndex = playerIndex;

        for (int i = 0; i < numberOfPlayers; i++) {
            playerHands.add(null);
            playerHandsSizes.add(0);
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            Hand hand = new Hand(deck.draw(5));
            playerHands.set(playerIndex, hand);
            playerHandsSizes.set(playerIndex, 5);

            playerIndex++;
            playerIndex = playerIndex % numberOfPlayers;
        }

        currentPlayer = players.get(dealerPlayerIndex);
        currentPlayerIndex = dealerPlayerIndex;
        currentHand = playerHands.get(dealerPlayerIndex);

    }

    /**
     * Creates player objects based on the player strategies inputted
     * @param input
     */
    public void createPlayers(List<String> input) {
        int numberOfPlayers = input.size();

        for (int i = 0; i < numberOfPlayers; i++) {
            String playerType = input.get(i);

            if (playerType.equals("naive")) {
                players.add(new NaivePlayer(i, numberOfPlayers));
            }
            else if (playerType.equals("smart")) {
                players.add(new SmartPlayer(i, numberOfPlayers));
            }
            else if (playerType.equals("very_smart")) {
                players.add(new VerySmartPlayer(i, numberOfPlayers));
            }
        }
    }

}
