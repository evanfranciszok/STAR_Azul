package game.ui.cli.screens;

import game.controller.Configuration;
import game.controller.Controller;
import game.ui.cli.CLI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

public class CLIScreenConfiguringTest {
    CLIScreenConfiguring cliScreenConfiguring;
    CLI ui;
    Controller controller;

    @BeforeEach
    public void initCLIScreenConfiguring() {
        ui = new CLI();
        controller = new Controller(ui);
        controller.initGame();
        cliScreenConfiguring = new CLIScreenConfiguring(ui);
    }

    @Test
    public void getConfigurationValidCombinations() {
        int[] humanConfigs = {0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 4};
        int[] computerConfigs = {2, 3, 4, 1, 2, 3, 0, 1, 2, 0, 1, 0};
        for (int i = 0; i < humanConfigs.length; ++i) {
            String input = "h" + humanConfigs[i] + "\nc" + computerConfigs[i] + "\nY";
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            Configuration config = cliScreenConfiguring.getConfiguration();
            Assertions.assertEquals(humanConfigs[i], config.humans());
            Assertions.assertEquals(computerConfigs[i], config.computers());
        }
    }

    @Test
    public void getConfigurationInvalidCombinations() {
        int[] humanConfigs = {4, 2, 1, 4};
        int[] computerConfigs = {1, 3, 4, 2};
        int[] expectedHumans = {3, 1, 0, 2};
        int[] expectedComputers = {1, 3, 4, 2};
        for (int i = 0; i < humanConfigs.length; ++i) {
            String input = "h" + humanConfigs[i] + "\nc" + computerConfigs[i] + "\nY";
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            Configuration config = cliScreenConfiguring.getConfiguration();
            Assertions.assertEquals(expectedHumans[i], config.humans());
            Assertions.assertEquals(expectedComputers[i], config.computers());
        }
    }

    @Test
    public void getConfigurationMultipleModifications() {
        int finalNHumans = 2;
        int finalNComputers = 0;
        String input = "h1\nc1\nh" + finalNHumans + "\nc" + finalNComputers + "\nY";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Configuration config = cliScreenConfiguring.getConfiguration();
        Assertions.assertEquals(finalNHumans, config.humans());
        Assertions.assertEquals(finalNComputers, config.computers());
    }

    @Test
    public void getConfigurationFullWords() {
        int finalNHumans = 2;
        int finalNComputers = 1;
        String input = "human" + finalNHumans + "\ncomputer" + finalNComputers + "\nY";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Configuration config = cliScreenConfiguring.getConfiguration();
        Assertions.assertEquals(finalNHumans, config.humans());
        Assertions.assertEquals(finalNComputers, config.computers());
    }

    @Test
    public void getConfigurationFullWordsPlural() {
        int finalNHumans = 2;
        int finalNComputers = 1;
        String input = "humans" + finalNHumans + "\ncomputers" + finalNComputers + "\nY";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Configuration config = cliScreenConfiguring.getConfiguration();
        Assertions.assertEquals(finalNHumans, config.humans());
        Assertions.assertEquals(finalNComputers, config.computers());
    }

    @Test
    public void getConfigurationWhitespace() {
        int finalNHumans = 2;
        int finalNComputers = 1;
        String input = "h \t " + finalNHumans + "\nc   \t" + finalNComputers + "\nY";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Configuration config = cliScreenConfiguring.getConfiguration();
        Assertions.assertEquals(finalNHumans, config.humans());
        Assertions.assertEquals(finalNComputers, config.computers());
    }

    @Test
    public void getConfigurationEqualSign() {
        int finalNHumans = 2;
        int finalNComputers = 1;
        String input = "h =" + finalNHumans + "\nc=" + finalNComputers + "\nY";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Configuration config = cliScreenConfiguring.getConfiguration();
        Assertions.assertEquals(finalNHumans, config.humans());
        Assertions.assertEquals(finalNComputers, config.computers());
    }

    @Test
    public void getConfigurationInvalidValues() {
        int finalNHumans = 3;
        int finalNComputers = 1;
        // Start with valid configuration, then attempt to apply invalid changes. These changes should not be applied.
        String input = "h" + finalNHumans + "\nc" + finalNComputers + "\nh-1\nc5\nhT\nc&\nc12\nY";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Configuration config = cliScreenConfiguring.getConfiguration();
        Assertions.assertEquals(finalNHumans, config.humans());
        Assertions.assertEquals(finalNComputers, config.computers());
    }

    @Test
    public void getConfigurationLessThanTwoPlayers() {
        int finalNHumans = 1;
        int finalNComputers = 1;
        // Start with valid configuration, then attempt to change one of the amounts to 0, which would cause the total to become 1.
        // These changes should not be applied.
        String input = "h" + finalNHumans + "\nc" + finalNComputers + "\nh0\nc0\nY";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Configuration config = cliScreenConfiguring.getConfiguration();
        Assertions.assertEquals(finalNHumans, config.humans());
        Assertions.assertEquals(finalNComputers, config.computers());
    }

    @Test
    public void getConfigurationInvalidSyntax() {
        int finalNHumans = 3;
        int finalNComputers = 1;
        // Start with valid configuration, then attempt to apply changes containing syntax errors. These changes should not be applied.
        String input = "h" + finalNHumans + "\nc" + finalNComputers + "\njiodfs1\ncoputer5\n\n  \n \n A\n!@#$%^&*()2\nY";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Configuration config = cliScreenConfiguring.getConfiguration();
        Assertions.assertEquals(finalNHumans, config.humans());
        Assertions.assertEquals(finalNComputers, config.computers());
    }
}

