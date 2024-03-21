package game.controller.states;

import game.controller.Controller;
import game.player.Player;

public class GameForfeit extends ControllerState {
    /**
     * The constructor, calls the super constructor
     *
     * @param controller (Controller) The injected controller
     */
    public GameForfeit(Controller controller) {
        super(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void entry() {
        for (Player player : controller.getPlayers()) {
            player.getBoard().finalizeScore();
        }
        controller.getCurrentPlayer().getBoard().setScore(0);
        controller.getUI().showGameFinishedScreen();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void act() {
        controller.quit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {

    }
}
