package game.controller;

import game.components.Bag;
import game.components.Field;
import game.components.Tile;
import game.controller.states.Configuring;
import game.controller.states.ControllerState;
import game.player.AI;
import game.player.Human;
import game.player.Player;
import game.player.aistrategies.RandomAIPlayerStrategy;
import game.ui.UI;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    /**
     * The user interface
     */
    private final UI ui;
    /**
     * The discard pile
     */
    private final List<Tile> discardPile;
    /**
     * The field with factories
     */
    private Field field;
    /**
     * The players playing the game
     */
    private List<Player> players;
    /**
     * The bag with the remaining tiles
     */
    private Bag bag;
    /**
     * The current state of the controller
     */
    private ControllerState state;
    /**
     * The configuration
     */
    private Configuration config;
    /**
     * The current player
     */
    private int currentPlayerIndex;
    /**
     * Whether the game has been stopped.
     * If this is set to true, the controller will exit after the current call to state.act() finishes.
     */
    private boolean stopped;

    /**
     * The constructor <br/>
     * Starts the configuration screen int the UI
     *
     * @param ui (UI) The injected user interface
     */
    public Controller(UI ui) {
        this.config = new Configuration(1, 3);
        this.ui = ui;
        this.discardPile = new ArrayList<>();
        this.stopped = false;
        ui.setController(this);
        goToState(new Configuring(this));
    }

    /**
     * Initializes the game based on the configuration,
     * Players and field is set based on this configuration
     */
    public void initGame() {
        field = new Field((config.computers() + config.humans()) * 2 + 1);
        bag = new Bag();
        field.fillFactoriesFromBag(bag);
        players = new ArrayList<>();
        for (int i = 0; i < config.humans(); i++) {
            players.add(new Human(ui, "Player " + (1 + i)));
        }
        for (int i = 0; i < config.computers(); i++) {
            players.add(new AI(new RandomAIPlayerStrategy(this), "Player " + (1 + config.humans() + i) + " (AI)"));
        }
        this.currentPlayerIndex = 0;
    }

    /**
     * Increment to the next player
     */
    public void incrementCurrentPlayer() {
        if (++currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
    }

    /**
     * Getter for the current player index
     *
     * @return The current player index
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Getter for the current player
     *
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Setter for the current player
     *
     * @param player (Player) The new current player
     */
    public void setCurrentPlayer(Player player) {
        currentPlayerIndex = players.indexOf(player);
    }

    /**
     * Run the controller
     */
    public void run() {
        while (!stopped) {
            state.act();
        }
    }

    /**
     * Set the new state, calls the exit function of the old state
     *
     * @param newState (ControllerState) The new state
     */
    public void goToState(ControllerState newState) {
        if (state != null) {
            state.exit();
        }
        state = newState;
        state.entry();
    }

    /**
     * Getter for the current state
     *
     * @return (ControllerState) the current state
     */
    public ControllerState getState() {
        return state;
    }

    /**
     * Refill the factories from the bag
     */
    public void refillFactories() {
        if (!field.fillFactoriesFromBag(bag)) {
            bag.refill(discardPile);
            field.fillFactoriesFromBag(bag);
        }
    }

    /**
     * Get a reference to the User Interface
     *
     * @return (UI) the UI
     */
    public UI getUI() {
        return ui;
    }

    /**
     * Getter for the field
     *
     * @return The field
     */
    public Field getField() {
        return field;
    }

    /**
     * Getter for the bag
     *
     * @return The bag
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * Getter for the players list
     *
     * @return The players list
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Getter for the configuration
     *
     * @return The configuration
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * Setter for the configuration
     *
     * @param config (Configuration) the new configuration
     */
    public void setConfig(Configuration config) {
        this.config = config;
    }

    /**
     * Getter for the discard pile
     *
     * @return The discard pile
     */
    public List<Tile> getDiscardPile() {
        return discardPile;
    }

    /**
     * Determine the Players that won the game. May return 1 or more winners
     *
     * @return (List < Player >) The players that won the game
     */
    public List<Player> determineWinners() {
        int highestScore = ui.getController().getPlayers().stream().mapToInt(player -> player.getBoard().getScore()).max().orElse(0);
        List<Player> winners = ui.getController().getPlayers().stream().filter(
                player -> player.getBoard().getScore() == highestScore
        ).toList();

        if (winners.size() > 1) {
            int mostFilledrows = winners.stream().mapToInt(player -> player.getBoard().getWall().getNumberOfFilledRows()).max().orElse(0);
            winners = winners.stream().filter(
                    player -> player.getBoard().getWall().getNumberOfFilledRows() == mostFilledrows
            ).toList();
        }
        return winners;
    }

    /**
     * Instruct the controller to stop after the current call to state.act() returns
     */
    public void quit() {
        stopped = true;
    }
}
