package game.controller.states;

import game.controller.Controller;

public class Configuring extends ControllerState {
    /**
     * The constructor, calls the super constructor
     *
     * @param controller (Controller) The injected controller
     */
    public Configuring(Controller controller) {
        super(controller);
    }

    /**
     * {@inheritDoc} <br/>
     * Shows the configuration screen in the UI
     */
    @Override
    public void entry() {
        controller.getUI().showConfigScreen();
    }

    /**
     * {@inheritDoc} <br/>
     * Sets the configurations and switches the state to TilePicking
     */
    @Override
    public void act() {
        controller.setConfig(controller.getUI().getConfiguration());
        controller.goToState(new TilePicking(controller));
    }

    /**
     * {@inheritDoc} <br/>
     * Starts the game
     */
    @Override
    public void exit() {
        controller.initGame();
    }
}
