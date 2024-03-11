package game.controller;

/**
 * Configuration used by the Controller to initialise the game
 *
 * @param humans    (int) The amount of humans that will be playing the game
 * @param computers (int) The amount of AI players to add to the game
 */
public record Configuration(int humans, int computers) {
}
