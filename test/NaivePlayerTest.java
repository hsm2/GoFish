import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by harishmanikantan on 3/7/17.
 */
public class NaivePlayerTest {

    @Test
    public void getRandomCard() throws Exception {
        NaivePlayer naivePlayer = new NaivePlayer(0,0);

        Card five = new Card(4, Card.Suit.DIAMONDS);
        Card six = new Card(5, Card.Suit.CLUBS);
        Card two = new Card(1, Card.Suit.SPADES);
        Card three = new Card(2, Card.Suit.HEARTS);
        Card seven = new Card(6, Card.Suit.HEARTS);
        Card nine = new Card(8, Card.Suit.HEARTS);

        List<Card> cards = new ArrayList<>();
        cards.add(five);
        cards.add(six);
        cards.add(two);
        cards.add(three);
        cards.add(seven);
        cards.add(nine);

        Hand hand = new Hand(cards);

        for (int i = 0; i < 10; i++) {
            assertTrue(cards.contains(naivePlayer.getRandomCard(hand)));
        }

    }

    @Test
    public void getRandomPlayerNumber() throws Exception {
        NaivePlayer naivePlayer = new NaivePlayer(1, 5);
        naivePlayer.addPlayersOut(0);
        naivePlayer.addPlayersOut(2);

        List<Integer> invalidPlayers = new ArrayList<>();
        invalidPlayers.add(0);
        invalidPlayers.add(2);

        for (int i = 0; i < 10; i++) {
            int playerNumber = naivePlayer.getRandomPlayerNumber();
            assertTrue(playerNumber != 1 && !invalidPlayers.contains(playerNumber));
        }

        naivePlayer.addPlayersOut(3);
        naivePlayer.addPlayersOut(4);

        invalidPlayers.add(3);
        invalidPlayers.add(4);

        int playerNumber = naivePlayer.getRandomPlayerNumber();
        assertTrue(playerNumber == -1);

    }

    @Test
    public void doTurn() throws Exception {
        NaivePlayer naivePlayer = new NaivePlayer(1, 5);
        naivePlayer.addPlayersOut(0);
        naivePlayer.addPlayersOut(2);

        Card five = new Card(4, Card.Suit.DIAMONDS);
        Card six = new Card(5, Card.Suit.CLUBS);
        Card two = new Card(1, Card.Suit.SPADES);
        Card three = new Card(2, Card.Suit.HEARTS);
        Card seven = new Card(6, Card.Suit.HEARTS);
        Card nine = new Card(8, Card.Suit.HEARTS);

        List<Card> cards = new ArrayList<>();
        cards.add(five);
        cards.add(six);
        cards.add(two);
        cards.add(three);
        cards.add(seven);
        cards.add(nine);

        List<Integer> cardRanks = new ArrayList<>();
        cardRanks.add(4);
        cardRanks.add(5);
        cardRanks.add(1);
        cardRanks.add(2);
        cardRanks.add(6);
        cardRanks.add(8);

        Hand hand = new Hand(cards);

        for (int i = 0; i < 10; i++) {
            Play play = naivePlayer.doTurn(hand);
            int rank = play.getRank();
            int targetPlayerNumber = play.getTargetPlayer();

            assertTrue(cardRanks.contains(rank));
            assertTrue(!naivePlayer.isPlayerOut(targetPlayerNumber));
            assertTrue(targetPlayerNumber != 1);
        }

        naivePlayer.addPlayersOut(3);
        naivePlayer.addPlayersOut(4);

        Play play = naivePlayer.doTurn(hand);

        assertNull(play);
    }

}