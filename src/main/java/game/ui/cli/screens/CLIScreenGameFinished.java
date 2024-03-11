package game.ui.cli.screens;

import game.player.Player;
import game.ui.UI;
import game.ui.UIScreen;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CLIScreenGameFinished extends UIScreen {
    /**
     * The constructor
     *
     * @param ui (CLI) The injected CLI
     */
    public CLIScreenGameFinished(UI ui) {
        super(ui);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateScreen() {
        ui.wipeScreen();
        System.out.println("GAME FINISHED");
        printWinners();
        printScores();
    }

    /**
     * Print the scores of all players
     * The scores are sorted by descending score
     */
    private void printScores() {
        System.out.println("Scores:");
        List<Player> sortedPlayers = ui.getController().getPlayers();
        sortedPlayers.sort((p1, p2) -> {
            if (p1.getBoard().getScore() != p2.getBoard().getScore()) {
                return p2.getBoard().getScore() - p1.getBoard().getScore();
            } else {
                return p2.getBoard().getWall().getNumberOfFilledRows() - p1.getBoard().getWall().getNumberOfFilledRows();
            }
        });
        for (Player player : sortedPlayers) {
            System.out.printf("[%s] Score: %d, Filled rows: %d\n",
                    player.getName(),
                    player.getBoard().getScore(),
                    player.getBoard().getWall().getNumberOfFilledRows()
            );
        }
    }

    /**
     * Print which player has won the game, or players in case of a tie
     */
    private void printWinners() {
        List<Player> winners = ui.getController().determineWinners();

        if (winners.size() == 1) {
            System.out.printf("The winner is: %s!\n", winners.get(0).getName());
        } else {
            System.out.print("The winners are:");
            for (Player winner : winners) {
                System.out.printf(" [%s]", winner.getName());
            }
            System.out.println(" !");
        }
    }

    /**
     * Select whether to play another game or quit the application
     *
     * @return true if the game should be restarted, false if the application should exit
     */
    @Override
    public boolean selectRestartOrQuit() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            updateScreen();
            System.out.println("Please choose to r(estart) or q(uit) the game.");
            String input = scanner.nextLine();
            if (Pattern.compile("^r(?:estart)?$", Pattern.CASE_INSENSITIVE).matcher(input).find()) {
                return true;
            } else if (Pattern.compile("^q(?:uit)?$", Pattern.CASE_INSENSITIVE).matcher(input).find()) {
                return false;
            }
        }
    }
}
