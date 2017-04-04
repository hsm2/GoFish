import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by harishmanikantan on 3/7/17.
 */
public class PlayerTest {

    @Test
    public void getPlayerType() throws Exception {
        NaivePlayer naivePlayer = new NaivePlayer(0, 0);
        assertEquals(naivePlayer.getPlayerType(), "naive");

        SmartPlayer smartPlayer = new SmartPlayer(0,0);
        assertEquals(smartPlayer.getPlayerType(), "smart");

        VerySmartPlayer verySmartPlayer = new VerySmartPlayer(0, 0);
        assertEquals(verySmartPlayer.getPlayerType(), "very_smart");
    }

    @Test
    public void isPlayerOut() throws Exception {
        NaivePlayer testPlayer = new NaivePlayer(0, 0);

        testPlayer.addPlayersOut(0);
        testPlayer.addPlayersOut(1);
        testPlayer.addPlayersOut(2);

        assertTrue(testPlayer.isPlayerOut(0));
        assertTrue(testPlayer.isPlayerOut(1));
        assertTrue(testPlayer.isPlayerOut(2));

        assertFalse(testPlayer.isPlayerOut(3));
    }

    @Test
    public void getBooksWon() throws Exception {
        NaivePlayer testPlayer = new NaivePlayer(0, 0);

        testPlayer.addBooksWon(new Card(5, Card.Suit.CLUBS));
        testPlayer.addBooksWon(new Card(10, Card.Suit.DIAMONDS));
        testPlayer.addBooksWon(new Card(11, Card.Suit.SPADES));

        ArrayList<Integer> books = testPlayer.getBooksWon();

        assertTrue(books.get(0) == 5);
        assertTrue(books.get(1) == 10);
        assertTrue(books.get(2) == 11);

        assertFalse(books.get(1) == -1);
    }

    @Test
    public void getPlayerNumber() throws Exception {
        NaivePlayer goodTestPlayer1 = new NaivePlayer(3, 7);
        NaivePlayer goodTestPlayer2 = new NaivePlayer(4, 9);

        NaivePlayer badTestPlayer1 = new NaivePlayer(1, 9);

        assertTrue(goodTestPlayer1.getPlayerNumber() == 3);
        assertTrue(goodTestPlayer2.getPlayerNumber() == 4);

        assertFalse(badTestPlayer1.getPlayerNumber() == 0);
    }

    @Test
    public void getBook() throws Exception {
        NaivePlayer testPlayer = new NaivePlayer(0, 0);

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

        List<Card> cardsWithoutBook = new ArrayList<>();
        cardsWithoutBook.add(five1);
        cardsWithoutBook.add(five2);
        cardsWithoutBook.add(seven);
        cardsWithoutBook.add(nine);

        Hand handWithBook = new Hand(cardsWithBook);
        Hand handWithoutBook = new Hand(cardsWithoutBook);

        assertTrue(testPlayer.getBook(handWithBook) == five4);
        assertNull(testPlayer.getBook(handWithoutBook));
    }

    @Test
    public void getCards() throws Exception {
        NaivePlayer testPlayer = new NaivePlayer(0, 0);

        Card five1 = new Card(4, Card.Suit.DIAMONDS);
        Card five2 = new Card(4, Card.Suit.CLUBS);
        Card five3 = new Card(4, Card.Suit.SPADES);
        Card five4 = new Card(4, Card.Suit.HEARTS);

        Card seven = new Card(6, Card.Suit.HEARTS);
        Card nine = new Card(8, Card.Suit.HEARTS);

        List<Card> cards = new ArrayList<>();
        cards.add(five1);
        cards.add(five2);
        cards.add(five3);
        cards.add(five4);
        cards.add(seven);
        cards.add(nine);

        Hand hand = new Hand(cards);

        List<Card> fiveCards = testPlayer.getCards(hand, five1.getRank());
        List<Card> sevenCards = testPlayer.getCards(hand, seven.getRank());
        List<Card> nineCards = testPlayer.getCards(hand, nine.getRank());

        assertTrue(fiveCards.size() == 4);
        assertTrue(sevenCards.size() == 1);
        assertTrue(nineCards.size() == 1);

        assertTrue(sevenCards.get(0).getRank() == 6);
        assertTrue(nineCards.get(0).getRank() == 8);

        for (Card card : fiveCards) {
            assertTrue(card.getRank() == 4);
        }
    }

}