package game.ui.gui;

import game.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GUITest {
    GUI gui;
    Controller controller;

    @BeforeEach
    public void initGUI() {
        gui = new GUI();
        controller = new Controller(gui);
    }

    @Test
    public void wipeScreen() {
        // Should not throw an exception
        gui.wipeScreen();
    }
}
