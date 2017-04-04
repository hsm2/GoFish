import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * Method where GoFishGame is created and played
     * @param args
     */
    public static void main(String[] args) {
        displayOptions();
	    List<String> input = getUserInput();

        GoFishGame goFish = new GoFishGame(input);
        goFish.play();
    }

    /**
     * Displays options of player strategies to choose from
     */
    private static void displayOptions() {
        System.out.println("Available Strategies - 'naive', 'smart' and 'naive_smart'");
        System.out.println("Enter space separated strategies for total players");
    }

    /**
     * Gets user input and returns a list of 'naive', 'smart' and 'very_smart'
     * @return
     */
    public static List<String> getUserInput() {
        Scanner scanner = new Scanner(System.in);
        List strategies = new ArrayList();

        scanner = new Scanner(scanner.nextLine());

        while (scanner.hasNext()) {
            String option = scanner.next();

            strategies.add(option);
        }
        return strategies;
    }
}
