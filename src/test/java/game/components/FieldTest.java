package game.components;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.mockito.Mockito.*;

public class FieldTest {
    private static final int nFactories = 7;
    Field field;

    @BeforeEach
    void before() {
        field = new Field(nFactories);
    }

    @Test
    public void construction() {
        for (int i = 1; i <= 9; i++) {
            Field localField = new Field(i);
            Assertions.assertNotNull(localField.getCenter());
            Assertions.assertEquals(i, localField.getFactories().size());

        }
    }

    @Test
    public void getNonEmptyFactories() {
        Assertions.assertEquals(0, field.getNonEmptyFactories().size());
        field.getFactories().get(4).addTile(Tile.BLUE);
        field.getFactories().get(1).addTile(Tile.BLUE);
        Assertions.assertEquals(2, field.getNonEmptyFactories().size());
        Assertions.assertEquals(field.getFactories().get(1), field.getNonEmptyFactories().get(0));
        Assertions.assertEquals(field.getFactories().get(4), field.getNonEmptyFactories().get(1));
    }

    @Test
    public void fillFactoriesFromBagEnoughAvailable() {
        Bag bagMock = mock(Bag.class);
        when(bagMock.extractOneTile()).thenReturn(Tile.RED);
        field.fillFactoriesFromBag(bagMock);
        verify(bagMock, times(28)).extractOneTile();
        for (TokenPoolInterface factory :
                field.getFactories()) {
            Assertions.assertEquals(4, factory.getContents().size());
            for (Tile tile : factory.getContents()) {
                Assertions.assertEquals(Tile.RED, tile);
            }
        }
    }

    @Test
    public void fillFactoriesFromBagNotEnoughAvailable() {
        Bag bagMock = mock(Bag.class);
        when(bagMock.extractOneTile()).thenReturn(Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED).thenThrow(new RuntimeException("Should not be thrown"));
        when(bagMock.isEmpty()).thenReturn(false, false, false, false, false, true);
        boolean gotEnoughTiles = field.fillFactoriesFromBag(bagMock);
        Assertions.assertFalse(gotEnoughTiles);
        verify(bagMock, times(5)).extractOneTile();
        TokenPoolInterface firstFactory = field.getFactories().get(0);
        Assertions.assertEquals(4, firstFactory.getContents().size());
        for (Tile tile : firstFactory.getContents()) {
            Assertions.assertEquals(Tile.RED, tile);
        }
        TokenPoolInterface secondFactory = field.getFactories().get(1);
        Assertions.assertEquals(1, secondFactory.getContents().size());
        for (Tile tile : secondFactory.getContents()) {
            Assertions.assertEquals(Tile.RED, tile);
        }

        for (int i = 2; i < field.getFactories().size(); i++) {
            TokenPoolInterface factory = field.getFactories().get(i);
            Assertions.assertEquals(0, factory.getContents().size());
        }
    }

    @Test
    public void constructionWithTooHighFactoryCount() {
        InvalidParameterException exception = Assertions.assertThrows(
                InvalidParameterException.class,
                () -> new Field(10),
                "InvalidParameterException was expected"
        );
        Assertions.assertEquals("field needs at least 1 and at most 9 factories", exception.getMessage());
    }

    @Test
    public void constructionWithTooLowFactoryCount() {
        InvalidParameterException exception = Assertions.assertThrows(
                InvalidParameterException.class,
                () -> new Field(0),
                "InvalidParameterException was expected"
        );
        Assertions.assertEquals("field needs at least 1 and at most 9 factories", exception.getMessage());
        exception = Assertions.assertThrows(
                InvalidParameterException.class,
                () -> new Field(-1),
                "InvalidParameterException was expected"
        );
        Assertions.assertEquals("field needs at least 1 and at most 9 factories", exception.getMessage());
    }

}
