package game.controller.states;

import game.controller.Controller;
import game.ui.UI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class ConfiguringTest {
    Configuring configuringState;
    Controller controllerMock;
    UI uiMock;

    @BeforeEach
    void before() {
        uiMock = mock(UI.class);
        controllerMock = mock(Controller.class);
        when(controllerMock.getUI()).thenReturn(uiMock);
        configuringState = new Configuring(controllerMock);
    }

    @Test
    void entry() {
        configuringState.entry();
        verify(uiMock).showConfigScreen();
    }

    @Test
    void act() {
        configuringState.act();
        verify(uiMock).getConfiguration();
        ArgumentCaptor<ControllerState> stateArgumentCaptor = ArgumentCaptor.forClass(ControllerState.class);
        verify(controllerMock).goToState(stateArgumentCaptor.capture());
        Assertions.assertEquals(TilePicking.class, stateArgumentCaptor.getValue().getClass());
    }

    @Test
    void exit() {
        configuringState.exit();
        verify(controllerMock).initGame();
    }
}
