import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by harishmanikantan on 3/10/17.
 */
public class PlayerStrategyTest {
    @Test
    public void mainTest1() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("smart");
        input.add("naive");
        input.add("naive");

        GoFishGame goFishGame;
        int gamesWon[] = {0, 0, 0};

        for (int i = 0; i < 100; i++) {
            goFishGame = new GoFishGame(input);
            gamesWon[goFishGame.play()] += 1;
        }

        System.out.println("\nPlayer0 won " + gamesWon[0] + " games.");
        System.out.println("Player1 won " + gamesWon[1] + " games.");
        System.out.println("Player2 won " + gamesWon[2] + " games.");
    }

    @Test
    public void mainTest2() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("smart");
        input.add("smart");
        input.add("naive");

        GoFishGame goFishGame;
        int gamesWon[] = {0, 0, 0};

        for (int i = 0; i < 100; i++) {
            goFishGame = new GoFishGame(input);
            gamesWon[goFishGame.play()] += 1;
        }

        System.out.println("\nPlayer0 won " + gamesWon[0] + " games.");
        System.out.println("Player1 won " + gamesWon[1] + " games.");
        System.out.println("Player2 won " + gamesWon[2] + " games.");
    }

    @Test
    public void mainTest3() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("smart");
        input.add("smart");
        input.add("very_smart");

        GoFishGame goFishGame;
        int gamesWon[] = {0, 0, 0};

        for (int i = 0; i < 100; i++) {
            goFishGame = new GoFishGame(input);
            gamesWon[goFishGame.play()] += 1;
        }

        System.out.println("\nPlayer0 won " + gamesWon[0] + " games.");
        System.out.println("Player1 won " + gamesWon[1] + " games.");
        System.out.println("Player2 won " + gamesWon[2] + " games.");
    }

    @Test
    public void mainTest4() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("very_smart");
        input.add("naive");
        input.add("naive");

        GoFishGame goFishGame;
        int gamesWon[] = {0, 0, 0};

        for (int i = 0; i < 100; i++) {
            goFishGame = new GoFishGame(input);
            gamesWon[goFishGame.play()] += 1;
        }

        System.out.println("\nPlayer0 won " + gamesWon[0] + " games.");
        System.out.println("Player1 won " + gamesWon[1] + " games.");
        System.out.println("Player2 won " + gamesWon[2] + " games.");
    }

}