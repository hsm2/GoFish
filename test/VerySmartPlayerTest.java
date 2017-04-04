import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by harishmanikantan on 3/7/17.
 */
public class VerySmartPlayerTest {


    @Test
    public void getPlayerWithoutCardRank() throws Exception {
        VerySmartPlayer verySmartPlayer = new VerySmartPlayer(0,5);

        //Setting that ith player does not have ith card rank
        for (int i = 0; i < 5; i++) {
            Boolean[] playerRanks = verySmartPlayer.playersWithoutCards.get(i);
            playerRanks[i] = true;

            verySmartPlayer.playersWithoutCards.put(i,playerRanks);
        }

        //Checking if ith player does not have ith rank
        for (int i = 0; i < 5; i++) {
            assertTrue(verySmartPlayer.getPlayerWithoutCardRank(i) != i);
        }
    }

    @Test
    public void getPlayerWithCardRank() throws Exception {
        VerySmartPlayer verySmartPlayer = new VerySmartPlayer(0,5);

        for (int i = 0; i < 5; i++) {
            Boolean[] playerRanks = verySmartPlayer.playersWithCards.get(i);
            playerRanks[i] = true;

            if (i % 2 == 1) {
                Boolean[] ranks = verySmartPlayer.playersWithoutCards.get(i);
                ranks[i] = false;

                verySmartPlayer.playersWithCards.put(i, ranks);
            }

            verySmartPlayer.playersWithoutCards.put(i, playerRanks);
        }

        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                assertTrue(verySmartPlayer.getPlayerWithCardRank(i) != i);
            }
            else {
                assertFalse(verySmartPlayer.getPlayerWithCardRank(i) == i);
            }
        }

    }

    @Test
    public void getCardsInDecreasingCount() throws Exception {
        VerySmartPlayer verySmartPlayer = new VerySmartPlayer(0, 0);

        List<Card> cards = new ArrayList<>();

        //13 card counts for 13 card ranks
        cards.add(new Card(0, Card.Suit.DIAMONDS));
        cards.add(new Card(0, Card.Suit.HEARTS));
        cards.add(new Card(0, Card.Suit.SPADES));
        cards.add(new Card(5, Card.Suit.HEARTS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(7, Card.Suit.SPADES));
        cards.add(new Card(8, Card.Suit.SPADES));

        Integer[] cardsInDecreasingCount = verySmartPlayer.getCardsInDecreasingCount(cards);

        assertTrue(cardsInDecreasingCount.length == 4);

        assertTrue(cardsInDecreasingCount[0] == 0);
        assertTrue(cardsInDecreasingCount[1] == 5);
        assertTrue(cardsInDecreasingCount[2] == 7);
        assertTrue(cardsInDecreasingCount[3] == 8);

    }

    @Test
    public void getMaxCardRank() throws Exception {
        VerySmartPlayer verySmartPlayer = new VerySmartPlayer(0, 0);

        Integer[] cardCount = new Integer[13];

        Arrays.fill(cardCount, 0);

        cardCount[0] = 6;
        cardCount[1] = 8;
        cardCount[2] = 1;
        cardCount[3] = 3;
        cardCount[4] = 5;
        cardCount[7] = 9;
        cardCount[9] = 10;
        cardCount[10] = 2;
        cardCount[11] = 7;

        assertTrue(verySmartPlayer.getMaxCardRank(cardCount) == 9);
        cardCount[9] = 0;

        assertTrue(verySmartPlayer.getMaxCardRank(cardCount) == 7);
        cardCount[7] = 0;

        assertTrue(verySmartPlayer.getMaxCardRank(cardCount) == 1);
        cardCount[1] = 0;

        assertTrue(verySmartPlayer.getMaxCardRank(cardCount) == 11);
    }


    @Test
    public void doTurn() throws Exception {
        VerySmartPlayer verySmartPlayer = new VerySmartPlayer(1, 3);

        Boolean[] player0With = verySmartPlayer.playersWithCards.get(0);
        Boolean[] player0Without = verySmartPlayer.playersWithoutCards.get(0);

        Boolean[] player2With = verySmartPlayer.playersWithCards.get(2);
        Boolean[] player2Without = verySmartPlayer.playersWithoutCards.get(2);

        player0With[3] = true;
        player0With[4] = true;
        player0With[5] = true;
        player0With[6] = true;
        player0Without[3] = true;
        player0Without[10] = true;

        player2With[4] = true;
        player2With[5] = true;
        player2Without[4] = true;

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(9, Card.Suit.CLUBS));
        cards.add(new Card(10, Card.Suit.SPADES));
        cards.add(new Card(3, Card.Suit.SPADES));

        //Play 1; Hand = 3, 9, 10, 3
        Hand hand = new Hand(cards);
        Play play = verySmartPlayer.doTurn(hand);

        assertTrue(play.getTargetPlayer() == 2);
        assertTrue(play.getRank() == 3);

        //Play 2; Hand = 3, 9, 10, 6
        cards.remove(3);
        cards.add(new Card(6, Card.Suit.DIAMONDS));

        hand = new Hand(cards);
        play = verySmartPlayer.doTurn(hand);

        assertTrue(play.getTargetPlayer() == 0);
        assertTrue(play.getRank() == 6);

        //Play 3; Hand = 6, 9, 10, 6
        cards.remove(0);
        cards.add(new Card(6, Card.Suit.SPADES));

        hand = new Hand(cards);
        play = verySmartPlayer.doTurn(hand);

        assertTrue(play.getTargetPlayer() == 0);
        assertTrue(play.getRank() == 6);

        //Play 4; Hand = 6, 9, 10, 6
        player2With[6] = true;

        verySmartPlayer.playersHandSizes.set(0, 10);
        verySmartPlayer.playersHandSizes.set(2, 9);

        play = verySmartPlayer.doTurn(hand);

        assertTrue(play.getTargetPlayer() == 0);
        assertTrue(play.getRank() == 6);

        //Play 5: Hand = 1, 9, 8, 11
        cards.set(0, new Card(1, Card.Suit.DIAMONDS));
        cards.set(2, new Card(8, Card.Suit.SPADES));
        cards.set(3, new Card(11, Card.Suit.SPADES));

        hand = new Hand(cards);

        verySmartPlayer.playersHandSizes.set(0, 5);
        verySmartPlayer.playersHandSizes.set(2, 6);

        play = verySmartPlayer.doTurn(hand);

        assertTrue(play.getTargetPlayer() == 2);
        assertTrue(play.getRank() == 1);
    }

    @Test
    public void playOccurred() throws Exception {
        VerySmartPlayer verySmartPlayer = new VerySmartPlayer(1,3);

        List<Card> cardsReturned = new ArrayList<>();
        cardsReturned.add(new Card(9, Card.Suit.SPADES));

        RecordedPlay recordedPlay = new RecordedPlay(1, 2, 4, cardsReturned);
        verySmartPlayer.playOccurred(recordedPlay, new ArrayList<Integer>());

        assertTrue(verySmartPlayer.playersWithCards.get(1)[4]);
        assertTrue(verySmartPlayer.playersWithoutCards.get(2)[4]);

        cardsReturned.add(new Card(9, Card.Suit.HEARTS));
        cardsReturned.add(new Card(9, Card.Suit.CLUBS));

        recordedPlay = new RecordedPlay(1, 2, 4, cardsReturned);
        verySmartPlayer.playOccurred(recordedPlay, new ArrayList<Integer>());

        assertFalse(verySmartPlayer.playersWithCards.get(1)[4]);
    }

}