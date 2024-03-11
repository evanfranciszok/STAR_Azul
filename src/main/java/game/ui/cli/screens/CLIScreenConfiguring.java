package game.ui.cli.screens;

import game.controller.Configuration;
import game.ui.UIScreen;
import game.ui.cli.CLI;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.min;

public class CLIScreenConfiguring extends UIScreen {
    /**
     * The current configuration
     */
    private Configuration currentConfig;

    /**
     * The constructor
     *
     * @param ui (CLI) The injected CLI
     */
    public CLIScreenConfiguring(CLI ui) {
        super(ui);
        currentConfig = ui.getController().getConfig();
    }

    /**
     * {@inheritDoc} <br/>
     * Update the screen by wiping and printing the following:
     * The amount of human players
     * The amount of AI players
     */
    @Override
    public void updateScreen() {
        ui.wipeScreen();
        System.out.printf("Amount of <Human> players: %d%n", currentConfig.humans());
        System.out.printf("Amount of <Computer> players: %d%n", currentConfig.computers());
    }

    /**
     * A parser to parse the input and turn in into a configuration
     *
     * @param regexPattern (String) The regex pattern to compare the input against
     * @param input        (String) The input string
     * @return The parsed integer
     */
    private Integer parseConfig(String regexPattern, String input) {
        try {
            Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (NumberFormatException ignored) {
        }
        return null;
    }

    /**
     * Confirm the input
     *
     * @param input (String) The input to confirm
     * @return true if the input is valid else false
     */
    private boolean parseConfirm(String input) {
        Pattern confirmPattern = Pattern.compile("^y(?:es)?$", Pattern.CASE_INSENSITIVE);
        Matcher confirmMatcher = confirmPattern.matcher(input);
        return confirmMatcher.find();
    }

    /**
     * Get the configuration from the command line
     *
     * @return The configuration
     */
    @Override
    public Configuration getConfiguration() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            updateScreen();
            System.out.println("Change configuration (e.g. \"human=1\" or \"c2\" ) or confirm configuration with: y(es).");
            String input = scanner.nextLine();

            if (parseConfirm(input)) {
                break;
            }

            Integer humans = parseConfig("^h(?:umans?)?\\s*=?\\s*(\\d+)$", input);
            if (humans != null) {
                if (humans > 4 || humans + currentConfig.computers() < 2) {
                    // Do not accept this configuration change
                    continue;
                }
                currentConfig = new Configuration(humans, min(currentConfig.computers(), 4 - humans));
            }

            Integer computers = parseConfig("^c(?:omputers?)?\\s*=?\\s*(\\d+)$", input);
            if (computers != null) {
                if (computers > 4 || currentConfig.humans() + computers < 2) {
                    // Do not accept this configuration change
                    continue;
                }
                currentConfig = new Configuration(min(currentConfig.humans(), 4 - computers), computers);
            }
        }
        return currentConfig;
    }
}
