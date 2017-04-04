import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by harishmanikantan on 3/7/17.
 */
public class GoFishGameTest {

    @Before
    public void createGame() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("naive");
        input.add("smart");
        input.add("naive");

        GoFishGame goFishGame = new GoFishGame(input);

    }

    @Test
    public void updateBooksOfCurrentPlayer() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("naive");
        input.add("smart");
        input.add("naive");

        GoFishGame goFishGame = new GoFishGame(input);

        Card five1 = new Card(4, Card.Suit.DIAMONDS);
        Card five2 = new Card(4, Card.Suit.CLUBS);
        Card five3 = new Card(4, Card.Suit.SPADES);
        Card five4 = new Card(4, Card.Suit.HEARTS);

        Card seven = new Card(6, Card.Suit.HEARTS);
        Card nine = new Card(8, Card.Suit.HEARTS);

        List<Card> cardsWithBook = new ArrayList<>();
        cardsWithBook.add(five1);
        cardsWithBook.add(five2);
        cardsWithBook.add(five3);
        cardsWithBook.add(five4);
        cardsWithBook.add(seven);
        cardsWithBook.add(nine);

        Hand hand = new Hand(cardsWithBook);

        goFishGame.currentHand = hand;
        goFishGame.updateBooksOfCurrentPlayer();

        ArrayList<Integer> booksWon = goFishGame.currentPlayer.getBooksWon();

        assertTrue(booksWon.size() == 1);
        assertTrue(booksWon.get(0) == 4);
    }

    @Test
    public void removeBookFromHand() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("naive");
        input.add("smart");
        input.add("naive");

        GoFishGame goFishGame = new GoFishGame(input);

        Card five1 = new Card(4, Card.Suit.DIAMONDS);
        Card five2 = new Card(4, Card.Suit.CLUBS);
        Card five3 = new Card(4, Card.Suit.SPADES);
        Card five4 = new Card(4, Card.Suit.HEARTS);

        Card seven = new Card(6, Card.Suit.HEARTS);
        Card nine = new Card(8, Card.Suit.HEARTS);

        List<Card> cardsWithBook = new ArrayList<>();
        cardsWithBook.add(five1);
        cardsWithBook.add(five2);
        cardsWithBook.add(five3);
        cardsWithBook.add(five4);
        cardsWithBook.add(seven);
        cardsWithBook.add(nine);

        Hand hand = new Hand(cardsWithBook);

        goFishGame.currentHand = hand;
        goFishGame.removeBookFromHand(five1);

        assertTrue(goFishGame.currentHand.size() == 2);
        assertTrue(goFishGame.currentHand.hasRank(6));
        assertTrue(goFishGame.currentHand.hasRank(8));
    }

    @Test
    public void updatePlayersOut() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("naive");
        input.add("smart");
        input.add("naive");

        GoFishGame goFishGame = new GoFishGame(input);

        goFishGame.updatePlayersOut(0);
        goFishGame.updatePlayersOut(1);

        assertTrue(goFishGame.currentPlayer.isPlayerOut(0));
        assertTrue(goFishGame.currentPlayer.isPlayerOut(1));
    }

    @Test
    public void updateGameRecords() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("naive");
        input.add("smart");
        input.add("naive");

        GoFishGame goFishGame = new GoFishGame(input);

        goFishGame.currentPlayer.addBooksWon(new Card(0, Card.Suit.SPADES));
        goFishGame.updateGameRecords();
        assertTrue(goFishGame.numBooksWon == 1);

        goFishGame.currentPlayer.addBooksWon(new Card(2, Card.Suit.SPADES));
        goFishGame.updateGameRecords();
        assertTrue(goFishGame.numBooksWon == 2);

        goFishGame.currentPlayer.addBooksWon(new Card(3, Card.Suit.SPADES));
        goFishGame.updateGameRecords();
        assertTrue(goFishGame.numBooksWon == 3);
    }


    @Test
    public void getCardName() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("naive");
        input.add("smart");
        input.add("naive");

        GoFishGame goFishGame = new GoFishGame(input);

        assertEquals("Aces", goFishGame.getCardName(0));
        assertEquals("Kings", goFishGame.getCardName(12));
        assertEquals("Queens", goFishGame.getCardName(11));
        assertEquals("Jacks", goFishGame.getCardName(10));
        assertEquals("9s", goFishGame.getCardName(8));
        assertEquals("7s", goFishGame.getCardName(6));
    }

    @Test
    public void updateCurrentPlayer() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("naive");
        input.add("smart");
        input.add("naive");
        input.add("naive");

        GoFishGame goFishGame = new GoFishGame(input);

        List<Card> cardsReturned = new ArrayList<>();
        cardsReturned.add(new Card(5, Card.Suit.DIAMONDS));

        int previousPlayerIndex = goFishGame.currentPlayerIndex;
        goFishGame.updateCurrentPlayer(cardsReturned);
        int nextPlayerIndex = goFishGame.currentPlayerIndex;

        assertTrue(previousPlayerIndex == nextPlayerIndex);

        cardsReturned.remove(0);

        previousPlayerIndex = goFishGame.currentPlayerIndex;
        goFishGame.updateCurrentPlayer(cardsReturned);
        nextPlayerIndex = goFishGame.currentPlayerIndex;

        assertFalse(previousPlayerIndex == nextPlayerIndex);

        goFishGame.currentPlayer = goFishGame.players.get(1);
        goFishGame.currentHand = goFishGame.playerHands.get(1);
        goFishGame.currentPlayerIndex = 1;

        goFishGame.playersOut.add(0);
        goFishGame.updateCurrentPlayer(cardsReturned);

        assertTrue(goFishGame.currentPlayerIndex == 2 || goFishGame.currentPlayerIndex == 3);
    }

    @Test
    public void getCardsReturned() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("naive");
        input.add("smart");
        input.add("naive");
        input.add("naive");

        GoFishGame goFishGame = new GoFishGame(input);

        Card five1 = new Card(4, Card.Suit.DIAMONDS);
        Card five2 = new Card(4, Card.Suit.CLUBS);
        Card five3 = new Card(4, Card.Suit.SPADES);
        Card five4 = new Card(4, Card.Suit.HEARTS);
        Card seven = new Card(6, Card.Suit.HEARTS);
        Card nine = new Card(8, Card.Suit.HEARTS);

        List<Card> cardsWithBook = new ArrayList<>();
        cardsWithBook.add(five1);
        cardsWithBook.add(five2);
        cardsWithBook.add(five3);
        cardsWithBook.add(five4);
        cardsWithBook.add(seven);
        cardsWithBook.add(nine);

        Hand hand = new Hand(cardsWithBook);
        goFishGame.playerHands.set(0, hand);

        Play play = new Play(0,4);
        List<Card> cardsReturned = goFishGame.getCardsReturned(play);
        assertTrue(cardsReturned.size() == 4);
        assertTrue(cardsReturned.get(0).getRank() == 4);

        play = new Play(0, 6);
        cardsReturned = goFishGame.getCardsReturned(play);
        assertTrue(cardsReturned.size() == 1);
        assertTrue(cardsReturned.get(0).getRank() == 6);

        play = new Play(0, 10);
        cardsReturned = goFishGame.getCardsReturned(play);
        assertTrue(cardsReturned.size() == 0);
    }

    @Test
    public void isGameOn() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("naive");
        input.add("smart");
        input.add("naive");

        GoFishGame goFishGame = new GoFishGame(input);

        goFishGame.numBooksWon = 2;
        assertTrue(goFishGame.isGameOn());

        goFishGame.playersOut.add(0);
        assertTrue(goFishGame.isGameOn());

        goFishGame.playersOut.add(1);
        assertFalse(goFishGame.isGameOn());

        goFishGame.numBooksWon = 13;
        assertFalse(goFishGame.isGameOn());
    }

}