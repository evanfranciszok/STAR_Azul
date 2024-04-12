package game.components;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Field {
    /**
     * The center of the field
     */
    private final TokenPoolInterface center;
    /**
     * The factories on the field
     */
    private final List<TokenPoolInterface> factories;

    /**
     * The constructor
     *
     * @param factory_count (int) The amount of factories on the field
     * @throws InvalidParameterException When the amount of factories is less than 1
     *                                   or more than 9
     */
    // @ requires factory_count >= 1 && factory_count <= 9;
    // @ ensures factories.size() == factory_count;
    public Field(int factory_count) throws InvalidParameterException {
        if (factory_count < 1 || factory_count > 9) {
            throw new InvalidParameterException("Field must have between 1 and 9 factories");
        }
        center = TokenPoolInterface.construct("Center");
        factories = new ArrayList<>(factory_count);
        // @ loop_invariant factories.size() == i && i >= 0 && i <= factory_count;
        // @ decreases factory_count - i;
        for (int i = 0; i < factory_count; i++) {
            factories.add(TokenPoolInterface.construct("Factory " + (i + 1)));
        }
    }

    /**
     * Getter for the center
     *
     * @return The center
     */
    // @ ensures \result == center;
    public TokenPoolInterface getCenter() {
        return center;
    }

    /**
     * Attempt to fill all factories using the available tiles in the @p bag
     *
     * @param bag (Bag) The bag from which the tiles can be grabbed
     * @return Whether enough tiles were available to fill the factories
     */
    // @ ensures (\forall TokenPoolInterface factory; factories.contains(factory);
    // factory.getContents().size() == 4);
    // @ ensures (\forall TokenPoolInterface factory; factories.contains(factory);
    // @ factory.getContents().size() == \old(factory.getContents().size()) + (4 -
    // \old(factory.getContents().size())));
    // @ ensures (\forall Tile t; \old(bag.contains(t)); (\exists TokenPoolInterface
    // factory; factories.contains(factory);
    // @ factory.getContents().contains(t)));
    // @ ensures \result == (\forall TokenPoolInterface factory;
    // factories.contains(factory);
    // @ factory.getContents().size() == 4);
    public boolean fillFactoriesFromBag(Bag bag) {
        if (center.isEmpty()) {
            center.addTile(Tile.STARTING_PLAYER_TILE);
        }
        // @ loop_invariant bag.contains(Tile.STARTING_PLAYER_TILE) ==
        // \old(bag.contains(Tile.STARTING_PLAYER_TILE));
        // @ loop_invariant (\forall TokenPoolInterface factory;
        // factories.contains(factory);
        // @ factory.getContents().size() == \old(factory.getContents().size()) + (4 -
        // \old(factory.getContents().size())));
        // @ decreases bag.size();
        for (TokenPoolInterface factory : factories) {
            while (factory.getContents().size() < 4) {
                if (bag.isEmpty()) {
                    return false;
                }
                factory.addTile(bag.extractOneTile());
            }
        }
        return true;
    }

    /**
     * Getter for the factories
     *
     * @return The factories
     */
    // @ ensures \result.equals(factories);
    public List<TokenPoolInterface> getFactories() {
        return factories;
    }

    /**
     * Getter for the non-empty factories
     *
     * @return All non-empty factories
     */
    // @ ensures \result.equals(\old(factories.stream().filter(factory ->
    // !factory.isEmpty()).toList()));
    // @ ensures \result.size() == (\old(factories.stream().filter(factory ->
    // !factory.isEmpty()).toList()).size());
    public List<TokenPoolInterface> getNonEmptyFactories() {
        return new ArrayList<>(factories.stream().filter(factory -> !factory.isEmpty()).toList());
    }

}
