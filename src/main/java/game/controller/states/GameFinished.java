package game.controller.states;

import game.controller.Controller;
import game.player.Player;

public class GameFinished extends ControllerState {
    /**
     * The constructor, calls the super constructor
     *
     * @param controller (Controller) The injected controller
     */
    public GameFinished(Controller controller) {
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
        controller.getUI().showGameFinishedScreen();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void act() {
        if (controller.getUI().getScreen().selectRestartOrQuit()) {
            controller.goToState(new Configuring(controller));
        } else {
            controller.quit();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {

    }
}
