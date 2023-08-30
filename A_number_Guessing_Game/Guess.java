
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        int chips = Integer.parseInt(args[0]);
        int range = Integer.parseInt(args[1]);
        int option = Integer.parseInt(args[2]);

        int[][] costs = calculateCosts(chips, range);
        int[][] guesses = calculateGuesses(chips, range, costs);

        printResults(option, chips, range, costs, guesses);
        if (option == 3) {
            playGame(chips, range, guesses);
        }
    }

    private static int[][] calculateCosts(int chips, int range) {
        int[][] costs = new int[chips + 1][range + 1];
        for (int i = 1; i <= range; ++i) {
            costs[1][i] = i;
        }
        for (int i = 1; i <= chips; ++i) {
            costs[i][1] = 1;
        }

        for (int chipCount = 2; chipCount <= chips; ++chipCount) {
            for (int rangeCount = 2; rangeCount <= range; ++rangeCount) {
                costs[chipCount][rangeCount] = range + 1;
                for (int guessIndex = 1; guessIndex <= rangeCount; ++guessIndex) {
                    int cost = Math.max(costs[chipCount - 1][guessIndex - 1], costs[chipCount][rangeCount - guessIndex]) + 1;
                    if (cost < costs[chipCount][rangeCount]) {
                        costs[chipCount][rangeCount] = cost;
                    }
                }
            }
        }
        return costs;
    }

    private static int[][] calculateGuesses(int chips, int range, int[][] costs) {
        int[][] guesses = new int[chips + 1][range + 1];
        for (int i = 1; i <= range; ++i) {
            guesses[1][i] = 1;
        }
        for (int i = 1; i <= chips; ++i) {
            guesses[i][1] = 1;
        }

        for (int chipCount = 2; chipCount <= chips; ++chipCount) {
            for (int rangeCount = 2; rangeCount <= range; ++rangeCount) {
                int minCost = range + 1;
                for (int guessIndex = 1; guessIndex <= rangeCount; ++guessIndex) {
                    int cost = Math.max(costs[chipCount - 1][guessIndex - 1], costs[chipCount][rangeCount - guessIndex]) + 1;
                    if (cost < minCost) {
                        minCost = cost;
                        guesses[chipCount][rangeCount] = guessIndex;
                    }
                }
            }
        }
        return guesses;
    }

    private static void printResults(int option, int chips, int range, int[][] costs, int[][] guesses) {
        if (option == 0 || option == 3) {
            System.out.printf("For a target number between 0 and %d, with %d chips, it takes at most %d questions to identify the target number in the worst case.%n", range, chips, costs[chips][range]);
        }

        if (option == 1 || option == 2) {
            for (int chipCount = 1; chipCount <= chips; ++chipCount) {
                for (int rangeCount = 1; rangeCount <= range; ++rangeCount) {
                    System.out.printf("costs[%d][%d] = %d, ", chipCount, rangeCount, costs[chipCount][rangeCount]);
                }
                System.out.printf("%n");
            }
        }
        if (option == 2) {
            for (int chipCount = 1; chipCount <= chips; ++chipCount) {
                for (int rangeCount = 1; rangeCount <= range; ++rangeCount) {
                    System.out.printf("guesses[%d][%d] = %d, ", chipCount, rangeCount, guesses[chipCount][rangeCount]);
                }
                System.out.printf("\n");
            }
        }
    }
    private static void playGame(int chips, int range, int[][] guesses) {
        Scanner scanner = new Scanner(System.in);
        int remainingChips = chips;
        int remainingRange = range;
        int targetLowerBound = 0;
        int questionCount = 1;

        System.out.println("LETS PLAY A GAME!!!!!!!!");
        System.out.printf("Please pick a number between 0 and %d in your mind, and let's play the game. :-|%n", range);

        while (remainingRange > 0) {
            int optimalGuessIndex = guesses[remainingChips][remainingRange];
            System.out.printf("Number of Chips Remaining = %d. Question %d: Is the target integer less than %d? (Y/N) ", remainingChips, questionCount++, targetLowerBound + optimalGuessIndex);
            char response = scanner.next().charAt(0);

            if (response != 'Y' && response != 'y') {
                targetLowerBound += optimalGuessIndex;
                remainingRange -= optimalGuessIndex;
            } else {
                remainingChips--;
                remainingRange = optimalGuessIndex - 1;
            }
        }

        System.out.printf("I... NAILED...IT! The target number is %d!! ;-)%n", targetLowerBound);
    }
}
