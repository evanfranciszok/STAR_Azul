package game;

import game.controller.Controller;
import game.ui.cli.CLI;

public class Main {
    /**
     * The main function
     * Creates the game and starts it
     *
     * @param args (String[]) The command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Running...");
        Controller game = new Controller(new CLI());
        game.run();
    }
}