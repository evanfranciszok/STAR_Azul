package game.components.borrowed;

import java.util.EnumMap;
import java.util.Map;

public class TilePile {
    private final Map<Color, Integer> counters;

    public TilePile() {
        this.counters = new EnumMap<>(Color.class);
        counters.put(Color.BLUE, 0);
        counters.put(Color.WHITE, 0);
        counters.put(Color.BLACK, 0);
        counters.put(Color.YELLOW, 0);
        counters.put(Color.RED, 0);
    }

    public int getCounter(Color color) {
        return this.counters.get(color);
    }

    public Map<Color, Integer> getCounters() {
        return this.counters;
    }

    public void setCounter(Color color, int amount) {
        this.counters.put(color, amount);
    }

    public void setCounters(Map<Color, Integer> counters) {
        this.counters.putAll(counters);
    }

    public int getTotalAmount() {
        return getCounters().values().stream().mapToInt(Integer::intValue).sum();
    }

    public void resetCounters() {
        counters.replaceAll((k, v) -> 0);
    }

    public boolean isEmpty() {
        for (int count : counters.values()) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    public void add(Color color, int amount) {
        int currentAmount = this.counters.get(color);
        this.counters.put(color, currentAmount + amount);
    }

    public void add(Map<Color, Integer> additions) {
        additions.forEach(this::add);
    }

    public int remove(Color color) {
        int amount = this.counters.get(color);
        this.setCounter(color, 0);

        return amount;
    }
}
