package game.controller.states;

import game.controller.Controller;

public abstract class ControllerState {

    /**
     * A reference to the controller the states belong to
     */
    protected final Controller controller;

    /**
     * The constructor, sets the controller
     *
     * @param controller (Controller) The injected controller
     */
    public ControllerState(Controller controller) {
        this.controller = controller;
    }

    /**
     * State entry function
     */
    public abstract void entry();

    /**
     * State act function
     */
    public abstract void act();

    /**
     * State exit function
     */
    public abstract void exit();
}
